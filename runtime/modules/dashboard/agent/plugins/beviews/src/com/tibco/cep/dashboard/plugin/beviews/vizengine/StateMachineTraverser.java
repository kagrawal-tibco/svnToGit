package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.ogl.model.ActivityConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.types.BooleanOptions;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Traverses a <code>StateMachine</code> to generate the <code>ProcessConfig</code> Graph.
 * 
 * @author anpatil
 * @see StateMachine
 * @see ProcessConfig
 */
class StateMachineTraverser implements StateMachineParser{

	private Logger logger;

	private ActivityConfigCreator configCreator;

	private ProcessConfig rootProcessConfig;

	private Map<String, ActivityConfig> activities;

	/**
	 * Create an instance of the StateMachineTraverser. If <code>ActivityConfigCreator</code> is <code>null</code> then empty <code>ActivityConfig</code> are created
	 * 
	 * @param logger
	 *            The <code>Logger</code> to use for logging messages
	 * @param configCreator
	 *            The <code>ActivityConfigCreator</code> to use for creating the <code>ActivityConfig</code>.
	 */
	StateMachineTraverser(Logger logger, ActivityConfigCreator configCreator) {
		this.logger = logger;
		this.configCreator = configCreator;
		activities = new HashMap<String, ActivityConfig>();
	}

	/**
	 * Process the provided <code>StateMachine</code> to generate the <code>ProcessConfig</code>
	 * 
	 * @param stateMachine
	 *            The <code>StateMachine</code> to be traversed
	 * @param ctx
	 *            The <code>PresentationContext</code> to be used
	 * @throws VisualizationException
	 * @throws PluginException
	 * @throws IOException
	 * @throws MALException
	 * @throws ElementNotFoundException
	 * @see {@link #getRootProcessConfig()}
	 */
	public void process(StateMachine stateMachine, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		rootProcessConfig = new ProcessConfig();
		processStateMachine(stateMachine.getName(), rootProcessConfig, stateMachine.getStateEntities(), ctx);
	}

	/**
	 * Traverse the provided <code>StateMachine</code>. All the generated <code>ActivityConfig</code> are created as children of <code>ProcessConfig</code>
	 * 
	 * @param processConfig
	 *            The <code>ProcessConfig</code> to use as the container for <code>ActivityConfig</code>
	 * @param stateMachine
	 *            The <code>StateMachine</code> to be traversed
	 * @param ctx
	 *            The <code>PresentationContext</code> to be used
	 * @throws VisualizationException
	 * @throws PluginException
	 * @throws IOException
	 * @throws MALException
	 * @throws ElementNotFoundException
	 */
	private void processStateMachine(String stateMachineName, ProcessConfig processConfig, List<StateEntity> states, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		if (logger.isEnabledFor(Level.INFO) == true) {
			logger.log(Level.INFO, "Processing StateMachine[name=" + stateMachineName + "]...");
		}		
		// find starting point
		StateStart startingPoint = findStartingPoint(states);
		if (startingPoint == null) {
			throw new VisualizationException("No starting state found in " + stateMachineName);
		}
		//compute depth for the entire state machine 
		Map<State, Integer> depthRecord = new LinkedHashMap<State, Integer>();
		computeDepth(startingPoint, 0, depthRecord);
		//process all the steps using the depth record as the reference
		processStates(processConfig,depthRecord, ctx);
	}

	private void computeDepth(State state, int startingDepth, Map<State, Integer> depthRecord) {
		List<StateTransition> outgoingTransitions = state.getOutgoingTransitions();
		for (StateTransition stateTransition : outgoingTransitions) {
			State toState = stateTransition.getToState();
			if ((toState instanceof StateEnd) == false && depthRecord.containsKey(toState) == false) {
				depthRecord.put(toState, new Integer(startingDepth));
				computeDepth(toState, startingDepth + 1, depthRecord);
			}
		}
	}
	
	private void processStates(ProcessConfig processConfig, Map<State, Integer> depthRecord, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		for (Map.Entry<State, Integer> entry : depthRecord.entrySet()) {
			State state = entry.getKey();
			Integer depth = entry.getValue();
			ActivityConfig stateActivityConfig = getActivityConfig(processConfig, state, ctx);
			if (depth == 0){
				// we update state as root
				if (logger.isEnabledFor(Level.INFO) == true) {
					logger.log(Level.INFO, "Setting " + state.getName() + " as root...");
				}				
				//we are dealing with starting states
				stateActivityConfig.setRoot(BooleanOptions.TRUE);
				stateActivityConfig.setSource("-1");
			}
			//process outgoing connections
			for (StateTransition stateTransition : state.getIncomingTransitions()) {
				State fromState = stateTransition.getFromState();
				if (fromState instanceof StateStart){
					continue;
				}
				Integer fromStateDepth = depthRecord.get(fromState);
				int diff = depth - fromStateDepth;
				if (diff == 0){
					if (fromState == state){
						//we are dealing with a loopback to the same state
						if (logger.isEnabledFor(Level.INFO) == true) {
							logger.log(Level.INFO, "Setting " + fromState.getName() + " as loopback on " + state.getName() + "...");
						}
						stateActivityConfig.setLoopBack(addTo(stateActivityConfig.getLoopBack(), fromState));						
					}
					else {
						//we are dealing with same level source 
						if (logger.isEnabledFor(Level.INFO) == true) {
							logger.log(Level.INFO, "Setting " + fromState.getName() + " as source on " + state.getName() + "...");
						}
						stateActivityConfig.setSource(addTo(stateActivityConfig.getSource(), fromState));						
					}
				}
				else if (diff == 1){
					//we are dealing with a direct source
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Setting " + fromState.getName() + " as source on " + state.getName() + "...");
					}
					stateActivityConfig.setSource(addTo(stateActivityConfig.getSource(), fromState));						
				}
				else if (diff < 0){
					//we are dealing with a loop back
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Setting " + state.getName() + " as loop back on " + fromState.getName() + "...");
					}
					ActivityConfig fromStateActivityConfig = getActivityConfig(processConfig, fromState, ctx);
					fromStateActivityConfig.setLoopBack(addTo(fromStateActivityConfig.getLoopBack(), state));							
				}
				else {
					//we are dealing with a skip to 
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Setting " + state.getName() + " as skip to on " + fromState.getName() + "...");
					}
					ActivityConfig fromStateActivityConfig = getActivityConfig(processConfig, fromState, ctx);
					fromStateActivityConfig.setSkipTo(addTo(fromStateActivityConfig.getSkipTo(), state));								
				}
			}
			if (state instanceof StateComposite) {
				StateComposite fromCompositeState = (StateComposite) state;
				if (fromCompositeState.isConcurrentState() == true) {
					// INFO Skipping concurrent state processing
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Skipping concurrent state processing for " + state.getName() + "...");
					}
				} else if (state instanceof StateMachine) {
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Processing " + state.getName() + " as state machine...");
					}
					ProcessConfig childProcessConfig = new ProcessConfig();
					stateActivityConfig.setProcessConfig(childProcessConfig);
					processStateMachine(state.getName(), childProcessConfig, ((StateMachine) state).getStateEntities(), ctx);
				} else if (state instanceof StateSubmachine) {
					// INFO Skipping state sub machine (external machine invocation)
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Skipping sub state machine processing for " + state.getName() + "...");
					}
				} else {
					if (logger.isEnabledFor(Level.INFO) == true) {
						logger.log(Level.INFO, "Processing child state machine processing in " + state.getName() + "...");
					}
					ProcessConfig childProcessConfig = new ProcessConfig();
					stateActivityConfig.setProcessConfig(childProcessConfig);
					processStateMachine(state.getName(), childProcessConfig, fromCompositeState.getStateEntities(), ctx);
				}
			}			
		}
	}	

	private String addTo(String ids, State state) {
		if (StringUtil.isEmptyOrBlank(ids) == true) {
			return state.getGUID();
		}
		return ids + "," + state.getGUID();
	}

	// private boolean isImmediateSource(State fromState, State state) {
	// LinkedList<StateTransition> outgoingTransitions = state.getOutgoingTransitions();
	// for (StateTransition stateTransition : outgoingTransitions) {
	// if (stateTransition.getFromState() == fromState){
	// return true;
	// }
	// }
	// return false;
	// }

	private StateStart findStartingPoint(List<StateEntity> entities) {
		for (StateEntity stateEntity : entities) {
			if (stateEntity instanceof StateStart) {
				return (StateStart) stateEntity;
			}
		}
		return null;
	}

	private ActivityConfig getActivityConfig(ProcessConfig processConfig, State state, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		String stateId = state.getGUID();
		ActivityConfig activityConfig = activities.get(stateId);
		if (activityConfig == null) {
			if (configCreator != null) {
				activityConfig = configCreator.createActivityConfig(state, ctx);
			} else {
				activityConfig = new ActivityConfig();
				activityConfig.setActivityID(stateId);
				activityConfig.setTitle(state.getName());
			}
			processConfig.addActivityConfig(activityConfig);
			activities.put(state.getGUID(), activityConfig);
		}
		return activityConfig;
	}

	public ProcessConfig getRootProcessConfig() {
		return rootProcessConfig;
	}

	interface ActivityConfigCreator {

		ActivityConfig createActivityConfig(State state, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException, PluginException;
	}

}