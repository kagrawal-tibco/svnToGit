package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;

public class BuilderSubClauseImpl extends RuleTemplateInstanceObject implements BuilderSubClause {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3623001703019191269L;
	private List<Filter> filters;

	public BuilderSubClauseImpl() {
	}

	@Override
	public List<Filter> getFilters() {
		if (this.filters == null) {
			this.filters = new ArrayList<Filter>();
		}
		return this.filters;
	}

	@Override
	public void addFilter(Filter filter) {
		if (!getFilters().contains(filter)) {
			getFilters().add(filter);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.ADDED, FILTERS_FEATURE_ID, this, filter);
			fireInstanceChanged(changeEvent);
		}
	}
	
	@Override
	public void addFilter(int index, Filter filter) {
		if (!getFilters().contains(filter)) {
			getFilters().add(index, filter);
		}
	}
	
	@Override
	public void addFilter(Filter filter, boolean fireEvents) {
		if (!getFilters().contains(filter)) {
			getFilters().add(filter);
			if (fireEvents) {
				InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.ADDED,
						FILTERS_FEATURE_ID, this, filter);
				fireInstanceChanged(changeEvent);
			}
		}
	}

	@Override
	public void removeFilter(Filter filter) {
		if (getFilters().contains(filter)) {
			getFilters().remove(filter);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.REMOVED, FILTERS_FEATURE_ID, this, filter);
			fireInstanceChanged(changeEvent);
		}
	}
	
	@Override
	public void removeFilter(Filter filter, boolean fireEvents) {
		if (getFilters().contains(filter)) {
			getFilters().remove(filter);
			if (fireEvents) {
				InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.REMOVED,
						FILTERS_FEATURE_ID, this, filter);
				fireInstanceChanged(changeEvent);
			}
		}
	}
}
