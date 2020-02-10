package com.tibco.cep.modules.db.functions;

import java.util.ArrayList;
import java.util.List;

public class SQLPattern {
	
	public static final int DIRECT_MATCH = 1;
	
	public static final int RANGE = 2;
	
	public static final int COLLECTION = 3;
	
	
	public static final int INT = 1;
	
	public static final int LONG = 2;
	
	public static final int BOOLEAN = 3;

	public static final int DOUBLE = 4;
	
	public static final int DATETIME = 5;
	
	public static final int STRING = 6;
	

	private String pattern;
	
	private int patternType;
	
	private int valueType;
	
	private List values = new ArrayList();
	
	public SQLPattern(){
	}
	
	public SQLPattern(String pattern, int patternType){
		this.pattern = pattern;
		this.patternType = patternType;
	}
	
	public String getPattern(){
		return this.pattern;
	}

	public int getPatternType(){
		return this.patternType;
	}
	
	public int getValueType(int valueType){
		return this.valueType;
	}
	
	public List getPatternValues(){
		return this.values;
	}
	
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	public void setPatternType(int patternType){
		this.patternType = patternType;
	}
	
	public void setValueType(int valueType){
		this.valueType = valueType;
	}
	
	public void addPatternValue(Object o){
		this.values.add(o);
	}
	public void setPatternValues(List l) {
		this.values.addAll(l);
	}
}
