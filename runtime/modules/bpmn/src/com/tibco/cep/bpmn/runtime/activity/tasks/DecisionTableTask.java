package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.functions.VRF.VRFFunctions;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.activity.results.OKResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;

/**
 * @author pdhar
 */
public class DecisionTableTask extends AbstractTask {
	
	private RuleFunction vrfFunction;
	private Map<String,VrfInfo> vrfExprMap = new HashMap<String,VrfInfo>();

	public DecisionTableTask() {
	}

	@Override
	public void init(InitContext context, Object... args) throws Exception {
		super.init(context, args);
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		
		String resource= (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
		Ontology ontology = getInitContext().getProcessModel().getOntology();
		this.vrfFunction = ontology.getRuleFunction(resource);
		EList<EObject> implementations = valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS);
		
		for(EObject impl:implementations) {
			ROEObjectWrapper<EClass, EObject> implw = ROEObjectWrapper.wrap(impl);
			String uri = (String) implw.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
			String implExpression = (String)implw.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
			boolean deployed = (Boolean) implw.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED);
			
			if (deployed) {
				VrfInfo vi = new VrfInfo();
				vi.expression = implExpression;
				vrfExprMap.put(uri, vi);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
	 */
	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		TaskResult result = null;
		
		try {
			Symbols dtArgs = getVrfFunction().getArguments();
			Object[] args = new Object[dtArgs.size()];
			
			int i=0;
			for(Iterator<Symbol> it = dtArgs.getSymbolsList().iterator();it.hasNext();){
				Symbol s = it.next();
				args[i++] = vars.getVariable(s.getName());
			}
			
			Map<String,Object> vrfResultMap  = new LinkedHashMap<String,Object>();
			Object vrfImpl = null;
			for (Entry<String, VrfInfo> vrfEntry: vrfExprMap.entrySet()) {
				String key = vrfEntry.getKey();
				key = key.substring(key.lastIndexOf('/') + 1);
				
				vrfImpl = VRFFunctions.getVRFImplByName(vrfFunction.getFullPath(), key);

				if (vrfImpl != null) {
					Object vrfresult = VRFFunctions.invokeVRFImpl(vrfImpl, args);
					vrfResultMap.put(vrfEntry.getKey(), vrfresult);
				} else {
					throw new Exception("VRF implementation (" + key + ") not found.");
				}
			}
			result = new OKResult(vrfResultMap);
		} catch (Throwable t) {
			result = new ExceptionResult(t);
		}
		
		return result; // needed for debugger exit task notification
	}
	
	public RuleFunction getVrfFunction() {
		return vrfFunction;
	}
	
	public static class VrfInfo {
		public String expression;
	}
}
