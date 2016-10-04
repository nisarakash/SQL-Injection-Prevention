
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sqlPrevention.truthValues.Value;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.ESqlStatementType;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TSourceToken;
import gudusoft.gsqlparser.TSourceTokenList;
import gudusoft.gsqlparser.TStatementList;
import gudusoft.gsqlparser.nodes.TExpression;

public class SqlInjection {

	public static void main(String[] args) {
		String sqlQuery = "select id from tt where id='ff' and 1 != 1";
		boolean obj = false;
		try {
			obj = isSQLInjected(sqlQuery);
		} catch (SQLException e) {
		}
		System.out.println("Result : " + obj);
	}

	public static boolean isSQLInjected(String sqlQuery) throws SQLException {
		TGSqlParser parser = new TGSqlParser(EDbVendor.dbvmysql);
		parser.sqltext = sqlQuery;
		int i = parser.parse();
		if (i == 0) {
			if (parser.getSqlstatements().get(0).sqlstatementtype == ESqlStatementType.sstselect) {
				if (checkAlwaysTrueOrFalse(parser) || checkUnion(parser) || checkQueryStack(parser)
						|| checkComment(parser)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			throw new SQLException();
		}
	}

	private static boolean checkComment(TGSqlParser parser) {
		TSourceToken st = parser.sourcetokenlist.get(parser.sourcetokenlist.size() - 1);
		Pattern slashStarCommentPattern = Pattern.compile("^(/\\*)[\\w|\\s|\\W|\\S]+(\\*/)$");
		Matcher m = slashStarCommentPattern.matcher(st.toString());
		if ((st.toString().startsWith("--")) || m.find()) {
			return true;
		}
		return false;
	}

	private static boolean checkUnion(TGSqlParser parser) {
		TSourceTokenList stList = parser.sourcetokenlist;
		for (int i = 0; i < stList.size(); i++) {
			TSourceToken st = stList.get(i);
			if ((st.toString().equalsIgnoreCase("union")) || (st.toString().equalsIgnoreCase("intersect"))
					|| (st.toString().equalsIgnoreCase("minus"))) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkQueryStack(TGSqlParser parser) {
		TStatementList list = parser.getSqlstatements();
		if (list.size() > 1) {
			return true;
		}
		return false;
	}

	private static boolean checkAlwaysTrueOrFalse(TGSqlParser parser) {
		try {

			TStatementList list = parser.getSqlstatements();
			if (list.size() > 0) {
				TExpression expr = list.get(0).getWhereClause().getCondition();
				ExpressionVisit ev = new ExpressionVisit();
				expr.postOrderTraverse(ev);
				Value value = ev.stack.pop();
				if (value == null || value.type.equals("unknown")) {
					return false;
				}
				if (value.type.equals("true") || value.type.equals("false")) {

					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;

	}

}
