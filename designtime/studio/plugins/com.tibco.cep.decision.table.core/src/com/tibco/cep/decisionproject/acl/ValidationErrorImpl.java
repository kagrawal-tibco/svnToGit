package com.tibco.cep.decisionproject.acl;
public class ValidationErrorImpl implements ValidationError {

	private int errorCode;
	private String errorMessage;
	private Object errorSource;
	
	public ValidationErrorImpl(){
		
	}
	public ValidationErrorImpl(int errorCode,String errorMessage,Object errorSource){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorSource = errorSource;
	}
	public int getErrorCode() {
		return errorCode;
	}
 
	public String getErrorMessage() {
		return errorMessage;
	}

	public Object getErrorSource() {
		return errorSource;
	}

	public void setErrorCode(int code) {
		this.errorCode = code;
	}

	public void  setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorSource(Object errorSource) {
		this.errorSource = errorSource;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj != null && !(obj instanceof ValidationError)) return false;
		if (this == null && obj == null) return true;
		if (this == null && obj != null) return false;
		if (this != null && obj == null) return false;
		ValidationError o = (ValidationError)obj;
		if (errorCode == o.getErrorCode() && errorSource.equals(o.getErrorSource())
				&& errorMessage.equals(o.getErrorMessage())){
			return true;
		}
		return false;
	
	}
	@Override
	public int hashCode() {
		int hashCode = 7;
		if (errorSource != null){
			hashCode =  hashCode + errorCode + errorSource.hashCode();
		}
		if (errorMessage != null){
			hashCode = hashCode +errorMessage.hashCode();
		}
		return hashCode + errorCode;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(getErrorCode());
		sb.append(" :: ");
		sb.append(getErrorSource());
		sb.append(" :: ");
		sb.append(getErrorMessage());
		return sb.toString();
	}

}
