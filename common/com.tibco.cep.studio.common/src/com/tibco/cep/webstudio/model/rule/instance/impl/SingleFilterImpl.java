package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;


public class SingleFilterImpl extends RuleTemplateInstanceObject implements SingleFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279590747562894504L;
	private FilterValue filterValue;
	private List<RelatedLink> links;
	private String operator;

	public SingleFilterImpl() {
		this.links = new ArrayList<RelatedLink>();
		this.filterValue = new SimpleFilterValueImpl();
	}

	@Override
	public FilterValue getFilterValue() {
		return this.filterValue;
	}

	@Override
	public List<RelatedLink> getLinks() {
		if (this.links == null) {
			this.links = new ArrayList<RelatedLink>();
		}
		return this.links;
	}

	@Override
	public String getOperator() {
		return this.operator;
	}

	@Override
	public void setFilterValue(FilterValue value) {
		this.filterValue = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, FILTER_VALUE_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void setOperator(String value) {
		this.operator = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, OPERATOR_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void addRelatedLink(RelatedLink link) {
		if (!getLinks().contains(link)) {
			getLinks().add(link);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.ADDED, LINKS_FEATURE_ID, this, link);
			fireInstanceChanged(changeEvent);
		}
	}
	
	@Override
	public void removeRelatedLink(RelatedLink link) {
		if (getLinks().contains(link)) {
			getLinks().remove(link);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.REMOVED, LINKS_FEATURE_ID, this, link);
			fireInstanceChanged(changeEvent);
		}
	}
	
	@Override
	public String getFilterId() {
		return filterId;
	}
	
	@Override
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
}
