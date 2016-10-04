README:

Name: Revanth Patil
ASU ID: 1210442269
Name: Akash Nishar
ASU ID: 1207689739

This is a stand alone java application which uses the networking concepts to create a simple proxy server that take a MySQL request from client or jdbc library 
and pass it to MySQL server, if the SQL query is safe to run. This application checks the first order SQL injection present, then the it is detected and query is not forwarded MySQL database. It checks for Tautology, Use of comment, Use of Union and Multiple query types of injection in a sql query. 

The application s packed in a JAR file - MySQLProxyServer.jar

This proxy Server needs to be started before the client starts.
To run this jar file use the following command:
java -jar /path/to/file/MySQLProxyServer.jar <hostname> <remotePort> <localPort>

<hostname> address of Mysql server
<remotePort> is the Mysql server port
<localport> is the proxy server

After the proxy-server is running, developer needs to include the correct port number(<localport>) to connect to proxy server.

Start running the client. It connects to proxy before connecting to the MySQL server.
