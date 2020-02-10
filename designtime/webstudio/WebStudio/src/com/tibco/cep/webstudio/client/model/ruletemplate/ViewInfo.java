package com.tibco.cep.webstudio.client.model.ruletemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model to represent View details for Rule Template View/Instance
 * 
 * @author Vikram Patil
 */
public class ViewInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String html;
	private List<BindingInfo> bindings;
	private String script;

	public ViewInfo() {
		super();
	}

	public List<BindingInfo> getBindings() {
		if (this.bindings == null) {
			this.bindings = new ArrayList<BindingInfo>();
		}
		return this.bindings;
	}

	public void setBindings(List<BindingInfo> bindings) {
		this.bindings = bindings;
	}

	public ViewInfo(String html) {
		this.setHtml(html);
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}


	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
