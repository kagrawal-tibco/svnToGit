package com.tibco.cep.studio.core.index.jobs;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * 
 * @author majha
 *
 */
public class StudioIndexRule implements ISchedulingRule {
	
	
	private String projectName;

	public StudioIndexRule(String projName) {
		this.projectName = projName;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {		
		return rule == this;
	}
	
	public String getProjectName() {
		return projectName;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (contains(rule)) {
            return true;
        }
		
		return false;
	}

}
