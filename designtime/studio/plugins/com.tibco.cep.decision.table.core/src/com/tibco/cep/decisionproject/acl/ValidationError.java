package com.tibco.cep.decisionproject.acl;
public interface ValidationError {
	
	static int ERROR_FN_ACCESS_DENIED = 0;
	/**
	 * 
	 * @return
	 */	
	  public int getErrorCode(); 
	  /**
	   * 
	   * @param code
	   */
	  public void setErrorCode(int code); 
	  /**
	   * 
	   * @return
	   */
	  public String getErrorMessage();	 
	  /**
	   * 
	   * @param errorMessage
	   */
	  public void setErrorMessage(String errorMessage); 

	   //What was the source of error. A Decision table, or a domain model, or a rule id in a DT etc.
	  /**
	   * 
	   */
	  public Object getErrorSource();	 
	  /**
	   * 
	   * @param errorSource
	   */
	  public void setErrorSource(Object errorSource);

}
