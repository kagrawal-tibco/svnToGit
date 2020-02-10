package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalStateVisualization extends LocalVisualization {

	private static final String THIS_TYPE = BEViewsElementNames.STATE_VISUALIZATION;

	public static final String ELEMENT_KEY_STATE_SERIESCONFIG = "StateSeriesConfig";

	public static final String ELEMENT_KEY_STATE = "State";

	public LocalStateVisualization() {
		super(THIS_TYPE);
	}

	public LocalStateVisualization(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalStateVisualization(LocalElement parentConfig, String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
	protected void loadChildrenFrom(String elementType, String childrenType, BEViewsElement persistedElement) {
		if (ELEMENT_KEY_STATE.equals(childrenType) == true){
			//we are dealing with state , which is stored as a GUID
			StateVisualization stateVisualization = (StateVisualization) persistedElement;
			String staterefid = stateVisualization.getStateRefID();
			//get the state machine from the parent
			LocalExternalReference stateMachineRef = (LocalExternalReference) getParent().getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE);
			if (stateMachineRef == null || stateMachineRef.getEObject() == null) {
				//throw new Exception("No state machine found in "+getParent().getDisplayableName());
				return;
			}
			StateMachine stateMachine = (StateMachine) stateMachineRef.getEObject();
			State state = findState(stateMachine.getStateEntities(),staterefid);
			if (state != null) {
				addElement(childrenType, new LocalExternalReference(state));
			}
			return;
		}
		super.loadChildrenFrom(elementType, childrenType, persistedElement);
	}

	private State findState(List<StateEntity> stateEntities, String staterefid) {
		//search in the incoming list first
		for (StateEntity stateEntity : stateEntities) {
			if (stateEntity instanceof State && staterefid.equals(stateEntity.getGUID()) == true) {
				return (State) stateEntity;
			}
		}
		//search in the children of the incoming list
		for (StateEntity stateEntity : stateEntities) {
			if (stateEntity instanceof StateComposite) {
				State state = findState(((StateComposite)stateEntity).getStateEntities(),staterefid);
				if (state != null) {
					return state;
				}
			}
		}
		return null;
	}

	@Override
	protected void synchronizeMDReferenceChildren(BEViewsElement mdElement, LocalParticle particle) {
		if (ELEMENT_KEY_STATE.equals(particle.getName()) == true) {
			StateVisualization stateVisualization = (StateVisualization) mdElement;
			//reset the id
			stateVisualization.setStateRefID(null);
			if (particle.getActiveElementCount() == 1) {
				LocalExternalReference stateRef = (LocalExternalReference) particle.getElement(false, 0);
				String stateID = stateRef.getID();
				if (stateID == null || stateID.trim().length() == 0) {
					throw new IllegalArgumentException(stateRef.getEObject()+" referenced in "+getParent().getDisplayableName()+"/"+getDisplayableName()+" does not have a valid GUID");
				}
				stateVisualization.setStateRefID(stateID);
			}
			return;
		}
		synchronizeMDReferenceChildren(mdElement, particle);
	}

	@Override
	protected LocalElement convertMdElementToLocalElement(Entity mdElement, String elementType) {
		if (elementType.equals(BEViewsElementNames.STATE)) {
			return new LocalExternalReference(mdElement);
		}
		return super.convertMdElementToLocalElement(mdElement, elementType);
	}

	@Override
	public List<Object> getEnumerations(String propName) {
		if (propName.equals(BEViewsElementNames.RELATED_COMPONENT)) {
			try {
				List<Object> returns = new ArrayList<Object>();
				List<LocalElement> chartComponents = getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
				for (LocalElement localElement : chartComponents) {
					returns.add(localElement);
				}
//				for (Iterator<LocalElement> itComponents = chartComponents.iterator(); itComponents.hasNext();) {
//					LocalElement chartComponent = (LocalElement) itComponents.next();
//					if (chartComponent.getID().equals(this.getID()) == false) {
//						returns.add(chartComponent);
//					}
//				}
				return returns;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return super.getEnumerations(propName);
	}

	@Override
	public boolean isValid() throws Exception {
		boolean valid = super.isValid();
		List<LocalElement> seriesConfigs = getChildren(ELEMENT_KEY_STATE_SERIESCONFIG);
		int contentSeriesCfgs = 0;
		int indicatorSeriesCfgs = 0;
		int unknownSeriesCfgs = 0;
		for (LocalElement seriesCfg : seriesConfigs) {
			if (StateMachineComponentHelper.isContentSeriesConfig((LocalStateSeriesConfig) seriesCfg) == true){
				contentSeriesCfgs++;
			}
			else if (StateMachineComponentHelper.isIndicatorSeriesConfig((LocalStateSeriesConfig) seriesCfg) == true){
				indicatorSeriesCfgs++;
			}
			else {
				unknownSeriesCfgs++;
			}
		}
		if (contentSeriesCfgs > 1){
			addValidationMessage(new SynValidationErrorMessage("Found more then one content settings", getURI()));
			valid = false;
		}
		if (indicatorSeriesCfgs > 1){
			addValidationMessage(new SynValidationErrorMessage("Found more then one indicator settings", getURI()));
			valid = false;
		}
		if (unknownSeriesCfgs > 0){
			addValidationMessage(new SynValidationErrorMessage("Found "+unknownSeriesCfgs+" settings", getURI()));
			valid = false;
		}
		return valid;
	}

	@Override
	public String getDisplayableName() {
		StringBuilder sb = new StringBuilder("settings for ");
		sb.append(getURI());
		return sb.toString();
	}

	@Override
	public String getURI() {
		try {
			LocalElement stateRef = getElement(LocalStateVisualization.ELEMENT_KEY_STATE);
			if (stateRef == null) {
				return super.getDisplayableName();
			}
			List<String> path = new LinkedList<String>();
			fillPath((State) stateRef.getEObject(),path);
			Collections.reverse(path);
			//remove first element
			path.remove(0);
			return "/"+StringUtil.fromList(path, "", "/", "");
		} catch (Exception e) {
			return super.getURI();
		}
	}

	private void fillPath(State object, List<String> path) {
		path.add(object.getName());
		StateEntity parent = object.getParent();
		if (parent instanceof State) {
			fillPath((State) parent, path);
		}
	}
}