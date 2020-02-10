package com.tibco.cep.webstudio.client.decisiontable;

import com.smartgwt.client.util.BooleanCallback;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class CustomBooleanCallBack implements BooleanCallback {

	private boolean condition = true;

	public boolean isCondition() {
		return condition;
	}

	public void setCondition(boolean condition) {
		this.condition = condition;
	}

}
