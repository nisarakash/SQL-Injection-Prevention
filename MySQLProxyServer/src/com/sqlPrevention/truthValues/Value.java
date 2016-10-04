package com.sqlPrevention.truthValues;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Value {
	public String type = null;
	public String value = null;
	
	public Value(String value, String type) {
		super();
		this.type = type;
		this.value = value;
	}

	public Pattern intPattern = Pattern.compile("^\\d+$");
	public Pattern stringPattern = Pattern.compile("^['|\"|`]\\w+['|\"|`]$");
	public Pattern unknownPattern = Pattern.compile("^[a-zA-Z]\\w+$");
	
	public Value(String value) {
		this.value = value;
		Matcher m = null;
		if((m = intPattern.matcher(value)) !=null && m.find()) {
			type = "int";
		}
		else if((m = stringPattern.matcher(value)) !=null && m.find()) {
			type = "string";
		}
		else{
			type = "invalid";
		}
	}
	
}
