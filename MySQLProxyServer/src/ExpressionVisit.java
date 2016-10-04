

import java.util.Stack;

import com.sqlPrevention.truthValues.Value;
import com.sqlPrevention.truthValues.ruleSet.comparisonRules;
import com.sqlPrevention.truthValues.ruleSet.logicalRules;

import gudusoft.gsqlparser.nodes.IExpressionVisitor;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TParseTreeNode;

public class ExpressionVisit implements IExpressionVisitor {

	Stack<Value> stack = new Stack<>();

	@Override
	public boolean exprVisit(TParseTreeNode node, boolean arg1) {
		TExpression expr = (TExpression) node;
		Value left = null;
		Value right = null;
		Value obj = null;
		String nodeValue = node.toString();

		if (expr.getExpressionType().toString().equals("simple_object_name_t")) {
			obj = new Value(nodeValue, "unknown");
		}
		if (expr.getExpressionType().toString().equals("simple_constant_t")) {
			obj = new Value(nodeValue);
		}
		if (expr.getExpressionType().toString().equals("simple_comparison_t")) {
			right = stack.pop();
			left = stack.pop();
			String compareCondition = nodeValue.substring(left.value.length(),
					nodeValue.length() - right.value.length());
			compareCondition = compareCondition.trim();
			obj = comparisonRules.comparisonRule(left, right, compareCondition, nodeValue);
		}
		if (expr.getExpressionType().toString().equals("logical_and_t")
				|| expr.getExpressionType().toString().equals("logical_or_t")) {
			nodeValue = node.toString();
			right = stack.pop();
			left = stack.pop();
			String logicalCondition = nodeValue.substring(left.value.length(),
					nodeValue.length() - right.value.length());
			logicalCondition = logicalCondition.trim();
			obj = logicalRules.logicalRule(left, right, logicalCondition, nodeValue);
		}
		//System.out.println("Node : "+nodeValue +"\tType : "+obj.type);
		stack.push(obj);
		return true;
	}

}
