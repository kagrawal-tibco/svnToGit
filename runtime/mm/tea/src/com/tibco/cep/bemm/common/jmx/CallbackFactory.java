/**
 * 
 */
package com.tibco.cep.bemm.common.jmx;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 * @author ssinghal
 *
 */
public class CallbackFactory {
	
	private static ObjectName getObjectName(String mbeanName) throws Exception{
		return new ObjectName(mbeanName);
	}
	
	public static Callback getAttributesCb(final String mbeanName, final String[] attributeNames) throws Exception{
		return new Callback() {
			@Override
			public Object perform(MBeanServerConnection conn) throws Exception{
				try {
					return conn.getAttributes(getObjectName(mbeanName), attributeNames);
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
				} catch (ReflectionException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}
	
	public static Callback getAttributeCb(final String mbeanName, final String attributeName) throws Exception{
		return new Callback() {
			@Override
			public Object perform(MBeanServerConnection conn) throws Exception{
				try {
					return conn.getAttribute(getObjectName(mbeanName), attributeName);
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
				} catch (ReflectionException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}

}
