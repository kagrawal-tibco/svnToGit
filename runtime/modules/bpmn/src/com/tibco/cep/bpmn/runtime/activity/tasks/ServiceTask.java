package com.tibco.cep.bpmn.runtime.activity.tasks;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author pdhar
 * 
 */
public class ServiceTask extends AbstractTask {
	public static final String Message_Node_Name = "message";
	public static final String ONE_WAY_SOAP_MESSAGE="oneWayMessage";

	private String id;

	private WebServiceTaskHelper helper;

	public ServiceTask() {

	}

	@Override
	public void init(InitContext context, Object... args) throws Exception {
		// TODO Auto-generated method stub
		super.init(context, args);


		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
				.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(flowNodeWrapper);
		this.id = flowNodeWrapper
				.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		
		EEnumLiteral propType = valueWrapper
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE);
		if (propType != null && propType.equals(BpmnModelClass.ENUM_WS_BINDING_JMS))
			helper = new JmsWebServiceTaskHelper(flowNodeWrapper);
		else
			helper = new HttpWebServiceTaskHelper(flowNodeWrapper);

	}

	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		
		TaskResult result = null;

		try {
			Object variable = vars.getVariable(Message_Node_Name);
			if (variable != null && variable instanceof XiNode) {
				XiNode soapNode = (XiNode) variable;
				XiNode messageNode = XiSupport.getXiFactory().createElement(new ExpandedName(Message_Node_Name));
				messageNode.appendChild(soapNode);
				XiNode soapResponse = helper.sendWebServiceRequest(messageNode);
				vars.setVariable(Message_Node_Name, soapResponse);
				result =  new DefaultResult(TaskResult.Status.OK, soapResponse);
			}else{
				throw new Exception(
						"No soap message attached with WebService task " + id);
			}

			
		}	catch (Throwable throwable) {
			
			logger.log(Level.ERROR,
					"Exception processing the WebService Task ", throwable);
			result =  new ExceptionResult(throwable);
		}
		// vars.setVariable("return", obj);
		
		return result; // needed for debugger exit task notification

	}

}
