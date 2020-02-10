package com.tibco.cep.dashboard.psvr.vizengine.actions;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.Parameter;
import com.tibco.cep.dashboard.psvr.ogl.model.ToggleActionConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.types.BooleanOptions;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;

public final class ActionConfigUtils {
	
	private static Parameter[] EMPTY_ARRAY = new Parameter[0];

    public static ActionConfigType createActionConfig(String text,CommandType command,boolean disabled,boolean defaultItem,String[][] configParams,String[][] dynamicParams,String[][] params,ToggleActionConfig toggleActionConfigType){
        ActionConfigType actionConfig = new ActionConfigType();
        actionConfig.setText(text);
        actionConfig.setCommand(command);
        actionConfig.setDisabled(BooleanOptions.valueOf(Boolean.toString(disabled)));
        actionConfig.setDefaultItem(BooleanOptions.valueOf(Boolean.toString(defaultItem)));
        actionConfig.setConfigParam(getParameters(configParams));
        actionConfig.setDynamicParam(getParameters(dynamicParams));
        actionConfig.setParam(getParameters(params));
        if (toggleActionConfigType != null){
            actionConfig.setToggleActionConfig(toggleActionConfigType);
        }
        return actionConfig;
    }
    
    private static Parameter[] getParameters(String[][] parameters){
    	if (parameters == null){
    		return EMPTY_ARRAY;
    	}
    	Parameter[] paramObjs = new Parameter[parameters.length];
    	for (int i = 0; i < parameters.length; i++) {
			String[] parameter = parameters[i];
			paramObjs[i] = new Parameter();
			paramObjs[i].setName(parameter[0]);
			paramObjs[i].setContent(parameter[1]);
		}
		return paramObjs;
    }

    public static ActionConfigType createActionConfig(String text,CommandType command,boolean disabled,String[][] configParams,String[][] dynamicParams,String[][] params,ToggleActionConfig toggleActionConfigType){
        return createActionConfig(text,command,disabled,false,configParams,dynamicParams,params,toggleActionConfigType);
    }

    public static ActionConfigType createActionConfigSet(String text,boolean disabled,List<ActionConfigType> children){
        ActionConfigType actionConfigSet = new ActionConfigType();
        actionConfigSet.setText(text);
        actionConfigSet.setDisabled(BooleanOptions.valueOf(Boolean.toString(disabled)));
        if (children != null) {
			Iterator<ActionConfigType> childrenIter = children.iterator();
			while (childrenIter.hasNext()) {
				ActionConfigType child = childrenIter.next();
				actionConfigSet.addActionConfig(child);
			}
		}
		return actionConfigSet;
    }

    public static ToggleActionConfig createToggleActionConfig(String text,CommandType command,Parameter[] configParams,Parameter[] dynamicParams,Parameter[] params){
        ToggleActionConfig toggleActionConfigType = new ToggleActionConfig();
        toggleActionConfigType.setText(text);
        toggleActionConfigType.setCommand(command);
        if (configParams != null && configParams.length != 0){
            toggleActionConfigType.setConfigParam(configParams);
        }
        if (dynamicParams != null && dynamicParams.length != 0){
            toggleActionConfigType.setDynamicParam(dynamicParams);
        }  
        if (params != null && params.length != 0){
            toggleActionConfigType.setParam(params);
        }
        return toggleActionConfigType;
    }

}