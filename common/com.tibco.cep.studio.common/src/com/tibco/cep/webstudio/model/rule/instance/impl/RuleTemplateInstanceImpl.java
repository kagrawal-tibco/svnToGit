package com.tibco.cep.webstudio.model.rule.instance.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.model.rule.instance.Actions;
import com.tibco.cep.webstudio.model.rule.instance.Binding;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.InstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;

public class RuleTemplateInstanceImpl extends RuleTemplateInstanceObject implements RuleTemplateInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1764000255791298697L;
	private Actions actions;
	private List<Binding> bindings;
	public MultiFilter conditionFilter;
	private String implementsPath;
	private String name;
    private String id;
    private String description;
    private int priority;


    @Override
	public Actions getActions() {
		if (this.actions == null) {
			this.actions = new ActionsImpl();
		}
		return this.actions;
	}

	@Override
	public MultiFilter getConditionFilter() {
		return this.conditionFilter;
	}

	@Override
	public String getImplementsPath() {
		return this.implementsPath;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setActions(Actions value) {
		this.actions = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, ACTIONS_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void setConditionFilter(MultiFilter value) {
		this.conditionFilter = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, CONDITION_FILTER_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void setImplementsPath(String value) {
		this.implementsPath = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, IMPLEMENTS_PATH_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void setName(String value) {
		this.name = value;
		InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.CHANGED, NAME_FEATURE_ID, this, value);
		fireInstanceChanged(changeEvent);
	}

	@Override
	public void addBinding(Binding binding) {
		if (!getBindings().contains(binding)) {
			getBindings().add(binding);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.ADDED, BINDINGS_FEATURE_ID, this, binding);
			fireInstanceChanged(changeEvent);
		}
	}

	@Override
	public List<Binding> getBindings() {
		if (bindings == null) {
			bindings = new ArrayList<Binding>();
		}
		return bindings;
	}

	@Override
	public void removeBinding(Binding binding) {
		if (getBindings().contains(binding)) {
			getBindings().remove(binding);
			InstanceChangedEvent changeEvent = new InstanceChangedEvent(IInstanceChangedEvent.REMOVED, BINDINGS_FEATURE_ID, this, binding);
			fireInstanceChanged(changeEvent);
		}
	}


    @Override
    public String getId() {
        return id;
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getDescription() {
    	return this.description;
    }
    
    @Override
    public void setDescription(String description) {
    	this.description = description;
    }
    
    @Override
    public int getPriority() {
    	return this.priority;
    }
    
    @Override
    public void setPriority(int priority) {
    	this.priority = priority;
    }
}
