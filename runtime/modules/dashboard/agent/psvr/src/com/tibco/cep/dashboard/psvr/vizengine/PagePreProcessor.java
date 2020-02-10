package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.Variable;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author anpatil
 * 
 */
public abstract class PagePreProcessor extends EngineHandler {

	public abstract PreProcessorResults preprocess(MALPage page, BizSessionRequest request, PresentationContext pCtx) throws RequestProcessingException;

	public static class PreProcessorResults {

		private List<Variable> variables;

		private List<ActionConfigType> actions;

		public PreProcessorResults() {
			variables = new LinkedList<Variable>();
			actions = new LinkedList<ActionConfigType>();
		}

		public void addVariable(Variable variable) {
			variables.add(variable);
		}

		public void clearVariables() {
			variables.clear();
		}
		
		public Variable[] getVariables() {
			return variables.toArray(new Variable[variables.size()]);
		}

		public void addAction(ActionConfigType e) {
			actions.add(e);
		}

		public void clearActions() {
			actions.clear();
		}
		
		public ActionConfigType[] getActions() {
			return actions.toArray(new ActionConfigType[actions.size()]);
		}

	}

}