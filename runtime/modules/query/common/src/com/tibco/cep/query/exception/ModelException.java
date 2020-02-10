package com.tibco.cep.query.exception;

import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jun 12, 2007
 * Time: 11:53:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelException extends Exception {
    ModelContext context;
    String msgkey = null;
    Exception encapsulated = null;
    boolean containsEncapsulated = false;

    public ModelException(ModelContext qc, String msgkey) {
        this.context = qc;
        this.msgkey = msgkey;
    }
    
    public ModelException(ModelContext qc, Exception e) {
        this.context = qc;
        this.encapsulated = e;
        this.containsEncapsulated = true;
    }
    

    public ModelContext getContext() {
        return context;
    }
    
    public boolean containsEncapsulatedException() {
    	return containsEncapsulated;
    }
    
    public Exception getEncapsulatedException() {
    	return encapsulated;
    }

    public String getMessage() {
    	if(msgkey != null){
    		return context.getResourceManager().getMessage(this.msgkey);
    	} 
    	else if(encapsulated != null) {
    		if (encapsulated instanceof ModelException) {
				ModelException qe = (ModelException) encapsulated;
				if(qe.containsEncapsulatedException()){
					return qe.getEncapsulatedException().getMessage();
				} else {
					return qe.getMessage();
				}
				
			} else {
				return encapsulated.getMessage();
			}
    	}
		return context.getResourceManager().getMessage(this.msgkey);
    }


}
