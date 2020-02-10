package com.tibco.cep.webstudio.model.rule.instance.impl;

import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;

public class RelatedLinkImpl extends RuleTemplateInstanceObject implements RelatedLink {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8063953785980705956L;
	private String linkText;
	private String linkType;

	@Override
	public String getLinkText() {
		return this.linkText;
	}

	@Override
	public void setLinkText(String value) {
		this.linkText = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, LINK_TEXT_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}
	
	@Override
	public String getLinkType() {
		return this.linkType;
	}
	
	@Override
	public void setLinkType(String type) {
		this.linkType = type;		
	}
}
