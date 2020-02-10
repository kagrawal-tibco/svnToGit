package com.tibco.cep.studio.core.validation;
/**
 * 
 * @author rmishra
 *
 */

public class ValidationError implements IValidationError {
	private Object source;
	private String msg;
	private boolean isWarning;
	
	public boolean isWarning() {
		return isWarning;
	}

	public void setWarning(boolean isWarning) {
		this.isWarning = isWarning;
	}

	public ValidationError(Object source , String msg){
		this.source = source;
		this.msg = msg;
	}

	@Override
	public String getMessage() {		
		return msg;
	}

	@Override
	public Object getSource() {		
		return source;
	}

	@Override
	public void setMessage(String msg) {
		this.msg = msg;

	}

	@Override
	public void setSource(Object source) {
		this.source = source;

	}


}
