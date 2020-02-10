package com.tibco.cep.bemm.monitoring.metric.interceptor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.interceptor.AbstractKeyBasedJmxInterceptor;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx.JmxAccumulator;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;


/**
 * @author ssinghal
 *
 */
public class BucketAverageJmxInterceptor extends AbstractKeyBasedJmxInterceptor{
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BucketAverageJmxInterceptor.class);

	
	@Override
	public void init(Agent agent, List<Attribute> attrList, Map<String, Object> genericMap ){}
	
	@Override
	public Attribute performAction(Attribute attr, Monitorable monitorable, List<Attribute> attrList, Map<String, Object> genericMap) {
		String keyFinalAveragedTnxVal=prepareKey(monitorable,attr.getName());
		String keyTotalHits=prepareKey(monitorable,attr.getName()+".TotalHits");
		
		if(getValueMap().get(keyFinalAveragedTnxVal)!=null ){
			
			Double prevVal = (Double) getValueMap().get(keyFinalAveragedTnxVal);
			int prevCount=(int) getValueMap().get(keyTotalHits);
			
			Double newVal = prevVal + (Double)attr.getValue();
			int newCount = prevCount + 1;
			
			getValueMap().put(keyFinalAveragedTnxVal, newVal);
			getValueMap().put(keyTotalHits, newCount);
			
			Double avgVal = null;
			if( (genericMap!=null) && ((int)genericMap.get("hits") == newCount) ){
				avgVal = newVal/newCount;
				getValueMap().put(keyTotalHits, 0);
				getValueMap().put(keyFinalAveragedTnxVal, 0d);
			}
			attr.setValue(avgVal); //setting null value is part of usecase here
			
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				printMap(keyFinalAveragedTnxVal, keyTotalHits);
			}
			return attr;
		}
		else{
			getValueMap().put(keyFinalAveragedTnxVal, attr.getValue());
			getValueMap().put(keyTotalHits, 0);
			attr.setValue(attr.getValue());
			printMap(keyFinalAveragedTnxVal, keyTotalHits);
			return attr;
		}
	}
	
	private void printMap(String keyFinalAveragedTnxVal, String keyTotalHits){
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Key["+keyFinalAveragedTnxVal+"] value["+getValueMap().get(keyFinalAveragedTnxVal)+"]");
			LOGGER.log(Level.DEBUG, "Key["+keyTotalHits+"] value["+getValueMap().get(keyTotalHits)+"]");
		}
	}

	@Override
	public Object performAction() {
		return null;
	}
	
	

}
