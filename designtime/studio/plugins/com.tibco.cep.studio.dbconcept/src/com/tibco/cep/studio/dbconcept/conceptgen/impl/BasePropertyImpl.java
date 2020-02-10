package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;

public class BasePropertyImpl implements BaseProperty {
	
	protected String name;
	protected String alias;
	protected String defaultValue;
	protected int length;
	protected int precision;
	protected int type;
	protected boolean array;
	protected boolean nullable;
	protected int position;
	
	public BasePropertyImpl(String name, String alias){
		this.name = name;
		this.alias = alias;//getValidIdentifier(alias);
	}
	
    protected  String getValidIdentifier(String identifier) {
    	if(ModelNameUtil.isValidIdentifier((String) identifier))
			return identifier;
        if(identifier == null || identifier.length() <= 0) return identifier;
        StringBuffer bf = new StringBuffer();
        boolean start = true;
        for(int ii = 0; ii < identifier.length(); ii++) {
            char ch = identifier.charAt(ii);
            if(!start || (start = false)) {
                if(ModelNameUtil.isIdentifierPart(ch))
                	bf.append(ch);
            } else {
                if(ModelNameUtil.isIdentifierStart(ch)) 
                	bf.append(ch);
            }
        }
        return bf.toString();
    }
	
	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getAlias(String dictionary) {
		return alias;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public int getLength() {
		return length;
	}

	public int getPrecision() {
		return precision;
	}

	public int getType() {
		return type;
	}

	public boolean isArray() {
		return array;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAlias(String alias) {
		this.alias = alias;//getValidIdentifier(alias);;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	
	public void setType(String type) {

		if ("STRING".equalsIgnoreCase(type)) {
			this.type = BaseProperty.STRING;
		} else if ("DATE".equalsIgnoreCase(type)) {
			this.type = BaseProperty.DATE;
		} else if ("DATETIME".equalsIgnoreCase(type)) {
			this.type = BaseProperty.DATETIME;
		} else if ("INTEGER".equalsIgnoreCase(type)
				|| "INT".equalsIgnoreCase(type)) {
			this.type = BaseProperty.INTEGER;
		}else if ("LONG".equalsIgnoreCase(type)) {
			this.type = BaseProperty.LONG;
		} else if ("NUMBER".equalsIgnoreCase(type)) {
			this.type = BaseProperty.LONG;
		} else if ("FLOAT".equalsIgnoreCase(type)) {
			this.type = BaseProperty.DOUBLE;
		} else if ("DOUBLE".equalsIgnoreCase(type)) {
			this.type = BaseProperty.DOUBLE;
		} else if ("BOOLEAN".equalsIgnoreCase(type)
				|| "BOOL".equalsIgnoreCase(type)) {
			this.type = BaseProperty.BOOLEAN;
		} else {
			this.type = BaseProperty.STRING;
		}
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

}
