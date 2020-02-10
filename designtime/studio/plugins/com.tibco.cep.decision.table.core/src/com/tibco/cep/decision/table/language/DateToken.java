package com.tibco.cep.decision.table.language;

public class DateToken {
	
	enum DateTokenType {
		FUNCTION_TYPE,
		LITERAL_TYPE,
		NULL_TYPE,
		/**
		 * To be used when either of the above are not suit.
		 */
		MISCELLANEOUS_TYPE,
		OPERATOR_TYPE;
	}
	
	private DateTokenType tokenType;
	
	private String tokenExpression;

	public DateToken(DateTokenType tokenType, String tokenExpression) {
		this.tokenType = tokenType;
		this.tokenExpression = tokenExpression;
	}

	public DateTokenType getTokenType() {
		return tokenType;
	}

	public String getTokenExpression() {
		return tokenExpression;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DateToken)) {
			return false;
		}
		DateToken otherToken = (DateToken)other;
		if (otherToken.tokenType != this.tokenType) {
			return false;
		}
		if (!otherToken.tokenExpression.equals(this.tokenExpression)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder()
		       .append("[")
		       .append(tokenType)
		       .append("]")
		       .append("[")
		       .append(tokenExpression)
		       .append("]").toString();
	}
}
