/**
 * 
 */
package com.tibco.cep.bemm.monitoring.metric.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ssinghal
 *
 */
public class TeaJmxInterceptorFactory {
	
	static Map<String,TeaJmxInterceptor> instanceMap = new ConcurrentHashMap<String,TeaJmxInterceptor>();
	
	
	public static TeaJmxInterceptor getInstance(String className, boolean nonSingleton) throws Exception{
		
		TeaJmxInterceptor inceptor = null;
		
		if(nonSingleton){
			inceptor = (TeaJmxInterceptor) Class.forName(className).newInstance();
		}else{
			if(instanceMap.containsKey(className))
				inceptor = instanceMap.get(className);
			else{
				synchronized (instanceMap) {
					if(!instanceMap.containsKey(className)){
						inceptor = (TeaJmxInterceptor) Class.forName(className).newInstance();
						instanceMap.put(className, inceptor);
					}
				}
				inceptor = instanceMap.get(className); 
			}
		}
		return inceptor;
	}
	
	public static TeaJmxInterceptor getInstance(String className) throws Exception{
		
		return getInstance(className, false);
		
	}

}
