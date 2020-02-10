/**
 * 
 */
package com.tibco.cep.studio.tester.core.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;

/**
 * @author aathalye
 *
 */
public class TesterEngine {
	
	/**
	 * Keep only one instance of this
	 */
	public static TesterEngine INSTANCE = new TesterEngine();
	
	private Set<IRuleRunTarget> ruleRunTargets = new LinkedHashSet<IRuleRunTarget>();
	
	private TesterEngine() {}
	
		
	static {
//		StudioConfig.init();
	}
		
	/**
	 * Return a {@link List} of all currently running engines
	 * @return
	 */
	public Collection<String> getRunningSessions() {
		IRuleRunTarget runTarget = TesterCoreUtils.getRunTarget();
		Map<String,String>ruleSessions = runTarget.getRuleSessionMap();
		return ruleSessions.keySet();
	}
	
	/**
	 * Return a {@link List} of all currently running engines for a project
	 * @param project -> The project for which this is desired
	 * @return
	 */
	public Collection<String> getRunningSessions(String project) {
		//TODO Change this
		return getRunningSessions();
	}
	
	/**
	 * Add a new {@link IRuleRunTarget} to collection of running targets.
	 * @param runTarget
	 */
	public void addTarget(IRuleRunTarget runTarget) {
		ruleRunTargets.add(runTarget);
	}
	
	/**
	 * Remove an {@link IRuleRunTarget} from collection of running targets.
	 * @param runTarget
	 */
	public void removeTarget(IRuleRunTarget runTarget) {
		ruleRunTargets.remove(runTarget);
	}
	
	/**
	 * Get all rule session names belonging to the target
	 * @param runTarget
	 * @return
	 */
	public String[] getRuleSessions(IRuleRunTarget runTarget) {
		return runTarget.getRuleSessionMap().keySet().toArray(new String[0]);
	}
	
	/**
	 * Return the {@link Set} containing all current debug targets.
	 * <p>
	 * We do not want collection to be modifiable from outside.
	 * </p>
	 * @return
	 */
	public Set<IRuleRunTarget> getAllTargets() {
		return Collections.unmodifiableSet(ruleRunTargets);
	}
}
