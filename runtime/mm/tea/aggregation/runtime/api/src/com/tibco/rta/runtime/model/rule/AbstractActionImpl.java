package com.tibco.rta.runtime.model.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.tibco.rta.model.FunctionDescriptor.FunctionParamValue;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.impl.ActionDefImpl;

/**
 * 
 * Action implementations must extend this class.
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
abstract public class AbstractActionImpl implements Action {
	
	protected ActionDef actionDef;
	
	protected String name;
	
	protected Map<String, FunctionParamValue> functionParamVals = new HashMap<String, FunctionParamValue>();
	
	protected boolean isSetAction;
	
	//Note : Since only one state of an action is maintained , this threadlocal will be used to store attributes
	//which are specific to a particular action state to avoid overlapping.
	static final protected ThreadLocal<Map<String,Object>> threadLocalOfAction=new ThreadLocal<Map<String,Object>>() {
		 public Map<String,Object> initialValue() {
	            return new HashMap<String,Object>();
	     }
	};
	
//    protected String alertText;
//    
//    protected String alertDetails;

	protected Rule rule;
	
	public AbstractActionImpl(Rule rule, ActionDef actionDef) {
		this.rule = rule;
		this.actionDef = actionDef;
		((ActionDefImpl)this.actionDef).setRuleDef(rule.getRuleDef());
	}
	
	@Override
	public Collection<FunctionParamValue> getFunctionParamValues() {
		return functionParamVals.values();
	}
	
	@Override
	public FunctionParamValue getFunctionParamValue(String paramName) {
		return functionParamVals.get(paramName);
	}
	
	@Override
	public void addFunctionParamVal(FunctionParamValue val) {
		functionParamVals.put(val.getName(), val);
	}
	
	@Override
	public ActionDef getActionDef() {
		return actionDef;
	}
	
	@Override
	public void setActionDef(ActionDef actionDef) {
		this.actionDef = actionDef;
		this.name = actionDef.getName();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isSetAction() {
		return isSetAction;
	}

	@Override
	public void setSetAction(boolean isSetAction) {
		this.isSetAction = isSetAction;
	}
	
	@Override
	abstract public String getAlertText();
//	@Override
//	public String getAlertText() {
//		// TODO Auto-generated method stub
//		return alertText;
//	}
	
//	@Override
//	public void setAlertText(String text) {
//		this.alertText = text;
//	}
	
	@Override
//	public String getAlertDetails() {
//		return alertDetails;
//	}
	abstract public String getAlertDetails();
	
//	@Override
//	public void setAlertDetails(String alertDetails) {
//		this.alertDetails = alertDetails;
//	}
	
	@Override
	public String getAlertLevel() {
		return actionDef.getAlertLevel();
	}
	
}
