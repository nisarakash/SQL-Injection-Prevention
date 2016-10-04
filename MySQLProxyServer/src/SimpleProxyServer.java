import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleProxyServer {
	public static void main(String[] args) throws IOException {
		
		if(args.length < 3) {
			System.err.println("Usage: <hostname> <remoteport> <localport>");
			return;
		}
		
		try {
			String host = args[0];
			int remoteport = Integer.parseInt(args[1]);
			int localport = Integer.parseInt(args[2]);
			System.out.println("Starting proxy for " + host + ":" + remoteport + " on port " + localport);
			runServer(host, remoteport, localport);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runServer(String host, int remoteport, int localport) throws IOException {
		ServerSocket ss = new ServerSocket(localport);

		final byte[] request = new byte[1024];
		byte[] reply = new byte[4096];

		while (true) {
			Socket client = null, server = null;
			try {
				client = ss.accept();

				final InputStream streamFromClient = client.getInputStream();
				final OutputStream streamToClient = client.getOutputStream();

				try {
					server = new Socket(host, remoteport);
				} catch (IOException e) {
					PrintWriter out = new PrintWriter(streamToClient);
					out.flush();
					client.close();
					continue;
				}

				final InputStream streamFromServer = server.getInputStream();
				final OutputStream streamToServer = server.getOutputStream();

				Thread t = new Thread() {
					public void run() {
						int bytesRead;
						try {
							while ((bytesRead = streamFromClient.read(request)) != -1) {
								String reqStr = new String(request);
								int commandId = (int) reqStr.charAt(4);
								String query = null;
								Pattern systemGeneratedQueryPattern = Pattern
										.compile("^(/\\*)[\\w|\\s|\\W|\\S]+(\\*/)[\\w|\\s|\\W|\\S]+$");
								if (commandId == 3) {
									query = reqStr.substring(5, bytesRead);
									System.out.println("Query : "+query);
									Matcher m = systemGeneratedQueryPattern.matcher(query);
									if (m.find()) {
										streamToServer.write(request, 0, bytesRead);
										streamToServer.flush();
									} else {
										if (SqlInjection.isSQLInjected(query)) {
											byte[] bb = { 0x19, 0x00, 0x00, 0x00, (byte) 0xff, 0x29, 0x04, 0x53, 0x51,
													0x4c, 0x20, 0x49, 0x6e, 0x6a, 0x65, 0x63, 0x74, 0x69, 0x6f, 0x6e,
													0x20, 0x50, 0x6f, 0x73, 0x73, 0x69, 0x62, 0x6c, 0x65 };
											streamToClient.write(bb, 0, bb.length);
											streamToClient.flush();
										} else {
											streamToServer.write(request, 0, bytesRead);
											streamToServer.flush();
										}
									}
								} else {
									streamToServer.write(request, 0, bytesRead);
									streamToServer.flush();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							streamToServer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};

				t.start();

				int bytesRead;
				try {
					while ((bytesRead = streamFromServer.read(reply)) != -1) {
						streamToClient.write(reply, 0, bytesRead);
						streamToClient.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				streamToClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (server != null)
						server.close();
					if (client != null)
						client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}