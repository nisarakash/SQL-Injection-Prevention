package com.sqlPrevention.truthValues.ruleSet;

import com.sqlPrevention.truthValues.Value;

public class comparisonRules {

	public static Value comparisonRule(Value obj1, Value obj2, String compareCondition, String nodeValue) {

		if (obj1.type.equals("unknown") && obj2.type.equals("unknown")) {
			return new Value(nodeValue, "unknown");
		}
		if (obj1.type.equals("unknown") && (obj2.type.equals("int") || obj2.type.equals("string"))) {
			return new Value(nodeValue, "unknown");
		}

		if ((obj1.type.equals("int") || obj1.type.equals("string")) && obj2.type.equals("unknown")) {
			return null;
		}

		if ((obj1.type.equals("int") || obj1.type.equals("string"))
				&& (obj2.type.equals("int") || obj2.type.equals("string"))) {
			if (obj1.type.equals("int") && obj2.type.equals("int")) {
				boolean flag;
				int val1 = Integer.parseInt(obj1.value);
				int val2 = Integer.parseInt(obj2.value);
				flag = integerComparison(val1, val2, compareCondition);
				if (flag) {
					return new Value(nodeValue, "true");
				} else {
					return new Value(nodeValue, "false");
				}
			}

			if (obj1.type.equals("string") && obj2.type.equals("int")) {
				boolean flag;
				flag = stringIntegerComparison(obj1.value, obj2.value, compareCondition);
				if (flag) {
					return new Value(nodeValue, "true");
				} else {
					return new Value(nodeValue, "false");
				}
			}

			if (obj1.type.equals("int") && obj2.type.equals("string")) {
				boolean flag;
				flag = integerStringComparison(obj1.value, obj2.value, compareCondition);
				if (flag) {
					return new Value(nodeValue, "true");
				} else {
					return new Value(nodeValue, "false");
				}
			}

			if (obj1.type.equals("string") && obj2.type.equals("string")) {
				boolean flag;
				flag = stringComparison(obj1.value, obj2.value, compareCondition);
				if (flag) {
					return new Value(nodeValue, "true");
				} else {
					return new Value(nodeValue, "false");
				}
			}
		}

		return null;
	}

	private static boolean integerStringComparison(String value, String value2, String compareCondition) {
		if (compareCondition.equals("=")) {
			return false;
		} else if (compareCondition.equals("!=")) {
			return true;
		} else if (compareCondition.equals(">")) {
			return true;
		} else if (compareCondition.equals("<")) {
			return false;
		} else if (compareCondition.equals(">=")) {
			return true;
		} else if (compareCondition.equals("<=")) {
			return false;
		} else {
			return false;
		}
	}

	private static boolean stringIntegerComparison(String value, String value2, String compareCondition) {
		if (compareCondition.equals("=")) {
			return false;
		} else if (compareCondition.equals("!=")) {
			return true;
		} else if (compareCondition.equals(">")) {
			return false;
		} else if (compareCondition.equals("<")) {
			return true;
		} else if (compareCondition.equals(">=")) {
			return false;
		} else if (compareCondition.equals("<=")) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean stringComparison(String value, String value2, String compareCondition) {

		if (compareCondition.equals("=")) {
			if (value.equals(value2)) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals("!=")) {
			if (!value.equals(value2)) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals(">")) {
			if (value.compareTo(value2) > 0) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals("<")) {
			if (value.compareTo(value2) < 0) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals(">=")) {
			if (value.compareTo(value2) >= 0) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals("<=")) {
			if (value.compareTo(value2) <= 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static boolean integerComparison(int val1, int val2, String compareCondition) {
		if (compareCondition.equals("=")) {
			if (val1 == val2) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals("!=")) {
			if (val1 != val2) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals(">")) {
			if (val1 > val2) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals("<")) {
			if (val1 < val2) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals(">=")) {
			if (val1 >= val2) {
				return true;
			} else {
				return false;
			}
		} else if (compareCondition.equals("<=")) {
			if (val1 <= val2) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
