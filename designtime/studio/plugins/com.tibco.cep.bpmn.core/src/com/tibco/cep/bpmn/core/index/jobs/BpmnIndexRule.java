package com.tibco.cep.bpmn.core.index.jobs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import com.tibco.cep.studio.core.index.jobs.StudioIndexRule;

/**
 * 
 * @author majha
 *
 */

public class BpmnIndexRule implements ISchedulingRule {
	
	private String projectName;

	public BpmnIndexRule(String projName) {
		this.projectName = projName;
	}

	@Override
	public boolean contains(ISchedulingRule rule) {	
		if(rule instanceof IResource){
			IResource res = (IResource)rule;
			if(res.getProject() != null)
				// some time I noticed in load index job, some out of sync resource getting refreshed in another job
				//that job should be allowed inside refreshed job
				return res.getProject().getName().equals(projectName);
			
		}
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
		if(rule instanceof StudioIndexRule) {
			return ((StudioIndexRule)rule).getProjectName().equals(getProjectName());
		}
		
		if(rule instanceof BpmnIndexRule) {
			return ((BpmnIndexRule)rule).getProjectName().equals(getProjectName());
		}
		return false;
	}

}
