package com.tibco.cep.studio.ui.validation;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.common.validation.ISimpleProblemHandler;

class XSDProblemHandler implements ISimpleProblemHandler {

	List<String> errors = new ArrayList<String>();
	
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public void handleProblem(String file, String message) {
		errors.add(message);
	}
	
}