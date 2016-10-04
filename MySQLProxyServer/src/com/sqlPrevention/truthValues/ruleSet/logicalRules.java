package com.sqlPrevention.truthValues.ruleSet;

import com.sqlPrevention.truthValues.Value;

public class logicalRules {
	public static Value logicalRule(Value obj1, Value obj2, String logicalCondition, String nodeValue) {

		if (obj1.type.equals("unknown") && obj2.type.equals("unknown")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "unknown");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "unknown");
			}
		}
		if (obj1.type.equals("unknown") && obj2.type.equals("true")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "unknown");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "true");
			}
		}
		if (obj1.type.equals("unknown") && obj2.type.equals("false")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "false");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "unknown");
			}
		}
		if (obj1.type.equals("true") && obj2.type.equals("unknown")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "unknown");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "true");
			}
		}
		if (obj1.type.equals("true") && obj2.type.equals("true")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "true");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "true");
			}
		}
		if (obj1.type.equals("true") && obj2.type.equals("false")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "false");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "true");
			}
		}
		if (obj1.type.equals("false") && obj2.type.equals("unknown")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "false");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "unknown");
			}
		}
		if (obj1.type.equals("false") && obj2.type.equals("true")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "false");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "true");
			}
		}
		if (obj1.type.equals("false") && obj2.type.equals("false")) {
			if (logicalCondition.equals("and")) {
				return new Value(nodeValue, "false");
			}
			if (logicalCondition.equals("or")) {
				return new Value(nodeValue, "false");
			}
		}

		return null;
	}

}
