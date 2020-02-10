package com.tibco.cep.runtime.service.tester.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;

public class SchedulerTask {
	
	private String schedulerId;
	private String workKey;
	private WorkEntry entry;
	
	public SchedulerTask(String schedulerId, String workKey, WorkEntry entry) {
		this.schedulerId = schedulerId;
		this.workKey = workKey;
		this.entry = entry;
	}

	public String getSchedulerId() {
		return schedulerId;
	}
	
	public String getWorkKey() {
		return workKey;
	}
	
	public WorkEntry getEntry() {
		return entry;
	}
	
	public Object getTarget() {
		try {
			Method method = entry.getClass().getMethod("getEvent");
			if (method != null) {
				return method.invoke(entry);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
