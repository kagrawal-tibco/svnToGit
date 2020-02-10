package com.tibco.cep.bemm.common.taskdefs;

/**
 * @author dijadhav
 *
 */
public interface Task {
	Object perform() throws Throwable;

	String getTaskName();

	void stop();
}
