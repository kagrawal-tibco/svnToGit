package com.tibco.cep.studio.debug.input;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.model.IDebugTarget;

public class RuleDeployerTask extends  AbstractVmTask {

    private List<String> rules = new ArrayList<String>();

    private boolean deploy;

    public RuleDeployerTask(IDebugTarget target,boolean deploy, List<String> rules) {
    	super(target);
        this.deploy = deploy;
        this.rules = rules;
    }

    public String[] getRules() {
        return rules.toArray(new String[rules.size()]);
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public boolean isDeploy() {
        return deploy;
    }

    public void setDeploy(boolean deploy) {
        this.deploy = deploy;
    }
    
    
    @Override
    public Object getKey() {
    	return hashCode();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deploy ? 1231 : 1237);
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleDeployerTask other = (RuleDeployerTask) obj;
		if (deploy != other.deploy)
			return false;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
    
}
