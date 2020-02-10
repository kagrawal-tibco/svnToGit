package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;

public class RelatedFilterValueImpl extends RuleTemplateInstanceObject implements RelatedFilterValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7944188680574035730L;
	private List<RelatedLink> links;

	public RelatedFilterValueImpl() {
	}

	@Override
	public List<RelatedLink> getLinks() {
		if (this.links == null) {
			this.links = new ArrayList<RelatedLink>();
		}
		return links;
	}

	@Override
	public void addLink(RelatedLink link) {
		if (!getLinks().contains(link)) {
			getLinks().add(link);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.ADDED, LINKS_FEATURE_ID, this, link);
			fireInstanceChanged(changeEvent);
		}
	}

	@Override
	public void removeLink(RelatedLink link) {
		if (getLinks().contains(link)) {
			getLinks().remove(link);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.REMOVED, LINKS_FEATURE_ID, this, link);
			fireInstanceChanged(changeEvent);
		}
	}
}
