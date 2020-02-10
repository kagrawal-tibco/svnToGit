package com.tibco.cep.bemm.monitoring.metric.interceptor.impl;

import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.interceptor.AbstractKeyBasedJmxInterceptor;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;

public class AvgTimePerTxnInterceptor extends AbstractKeyBasedJmxInterceptor{

	/*Agent agent;
	List<Attribute> attrList=new ArrayList<Attribute>();*/
	
	@Override
	public void init(Agent agent, List<Attribute> attrList, Map<String, Object> genericMap ){
		/*this.agent = agent;
		this.attrList = attrList;*/
	}
	
	@Override
	public Attribute performAction(Attribute attr, Monitorable agent, List<Attribute> attrList, Map<String, Object> genericMap) {
		
		String keyAvgTxnTime=prepareKey(agent,"AvgSuccessfulTxnTimeMillis");
		String keyTotalTxn=prepareKey(agent,"TotalSuccessfulTxns");
		
		Double currentAvgTxnTime = (Double)MonitoringUtils.getAttributeValue("AvgSuccessfulTxnTimeMillis",attrList);
		Long currentTotalTxn=(Long) MonitoringUtils.getAttributeValue("TotalSuccessfulTxns",attrList);
		
		if(getValueMap().get(keyAvgTxnTime)!=null && getValueMap().get(keyTotalTxn)!=null){
			
			Double prevAvgTxnTime = (Double) getValueMap().get(keyAvgTxnTime);
			Long prevTotalTxn=(Long) getValueMap().get(keyTotalTxn);
			
			getValueMap().put(keyAvgTxnTime, currentAvgTxnTime);
			getValueMap().put(keyTotalTxn, currentTotalTxn);
			
			Double total=(currentAvgTxnTime*currentTotalTxn)- (prevAvgTxnTime*prevTotalTxn);
			Long diff=currentTotalTxn-prevTotalTxn;
			
			
			if(diff>0 && total >0){
				attr.setValue(total/diff);
			}
			else{
				attr.setValue(0d);
			}
			return attr;
		}
		else{
			getValueMap().put(keyAvgTxnTime, currentAvgTxnTime);
			getValueMap().put(keyTotalTxn, currentTotalTxn);
			attr.setValue(currentAvgTxnTime);
			return attr;
		}
	}


	@Override
	public Object performAction() {
		return null;
	}
	
	

}
