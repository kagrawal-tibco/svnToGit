package com.tibco.rta.test.publish;

import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.rta.RtaSession;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.FunctionDescriptor;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.impl.FunctionDescriptorImpl;
import com.tibco.rta.model.impl.FunctionDescriptorImpl.FunctionParamImpl;
import com.tibco.rta.model.impl.FunctionDescriptorImpl.FunctionParamValueImpl;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.RuleFactory;
import com.tibco.rta.model.rule.RuleFactoryEx;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.impl.ActionFunctionDescriptorImpl;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.rule.mutable.MutableTimeBasedConstraint;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;

public class TestRule {
	
	
	public static void createRule(RtaSession session, QueryByFilterDef setCondition, QueryByFilterDef clearCondition, String ruleName, int caseval) throws Exception
	{
		MutableRuleDef ruleDef;
		
		RuleFactory factory = new RuleFactoryEx();
        ruleDef = factory.newRuleDef(ruleName /*+ Math.round(Math.random())*/);

        ruleDef.setScheduleName("schedule1");
        ruleDef.setUserName("userName1");
        ruleDef.setSetCondition(setCondition);
        ruleDef.setClearCondition(clearCondition);


        /*InvokeConstraint invokeConstraint1 = factory.newInvokeConstraint(Constraint.TIMED);
        MutableTimeBasedConstraint tbc = (MutableTimeBasedConstraint) invokeConstraint1;
        tbc.setInvocationFrequency(10000);
        tbc.setMaxInvocationCount(100);
        tbc.setTimeConstraint(TimeBasedConstraint.Constraint.TILL_CONDITION_CLEARS);*/
        
        InvokeConstraint invokeConstraint1 = factory.newInvokeConstraint(Constraint.ONCE_ONLY);
     
        
        ActionFunctionDescriptor sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action");
        ActionFunctionDescriptor sendToSessionActionFn1 = null;
        FunctionParam param = null;
        
        switch(caseval)
        {
        	case 1: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action");
        			break;
        	case 3: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action");
					break;
        	/*case 4: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action1");
        			break;*/
					
        	case 31: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("warning");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
	            param = sendToSessionActionFn1.getFunctionParam("healthValue");
	            paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("ok");
	            sendToSessionActionFn1.addFunctionParamValue(paramValue);
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
	    	case 32: 
			{
				sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
				param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("critical");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
			}
					
        	case 41: 
        		{
        			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
        			param = sendToSessionActionFn.getFunctionParam("healthValue");
		            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
		            paramValue.setName(param.getName());
		            paramValue.setDataType(param.getDataType());
		            paramValue.setIndex(param.getIndex());
		            paramValue.setDescription(param.getDescription());
		            paramValue.setValue("warning");
		            sendToSessionActionFn.addFunctionParamValue(paramValue);
		            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
		            
		            
		            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
		            param = sendToSessionActionFn1.getFunctionParam("healthValue");
		            paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
		            paramValue.setName(param.getName());
		            paramValue.setDataType(param.getDataType());
		            paramValue.setIndex(param.getIndex());
		            paramValue.setDescription(param.getDescription());
		            paramValue.setValue("ok");
		            sendToSessionActionFn1.addFunctionParamValue(paramValue);
		            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
		            break;
        		}
        	case 42: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("critical");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 43: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("warning");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 44: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("critical");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 45: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("ok");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 51: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("warning");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
	            param = sendToSessionActionFn1.getFunctionParam("healthValue");
	            paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("ok");
	            sendToSessionActionFn1.addFunctionParamValue(paramValue);
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
	    	case 52: 
			{
				sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
				param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("critical");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
			}
	    	case 53: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("warning");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 54: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("critical");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 55: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("ok");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Log-Action");
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
        	case 1000: 
    		{
    			sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
    			param = sendToSessionActionFn.getFunctionParam("healthValue");
	            ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("11111");
	            sendToSessionActionFn.addFunctionParamValue(paramValue);
	            ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
	            
	            
	            sendToSessionActionFn1 = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("SetHealthAction");
	            param = sendToSessionActionFn1.getFunctionParam("healthValue");
	            paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
	            paramValue.setName(param.getName());
	            paramValue.setDataType(param.getDataType());
	            paramValue.setIndex(param.getIndex());
	            paramValue.setDescription(param.getDescription());
	            paramValue.setValue("22222");
	            sendToSessionActionFn1.addFunctionParamValue(paramValue);
	            
	           
	            ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, sendToSessionActionFn1, invokeConstraint1);
	            break;
    		}
		       	 	
		           
        	case 5: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action1");
					break;
        	case 6: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action1");
					break;
        	case 7: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action1");
					break;
        	case 8: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action1");
					break;
        	case 2001: sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Health-Action1");
					break;		
        }
        
        /*ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
        ActionDef clearSessionAction = factory.newClearActionDef(ruleDef,setSendSessionAction);*/

        System.out.println("Rule Created..");
        session.createRule(ruleDef);
        
        /*RuleDef cr = session.getRule(ruleName);
        if(cr instanceof MutableRuleDef)
        {
        	((MutableRuleDef)cr).setEnabled(false);
        }
        
        session.updateRule(cr);*/
        

	}

}
