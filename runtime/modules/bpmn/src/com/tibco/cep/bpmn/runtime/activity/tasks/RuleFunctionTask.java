package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.kernel.model.rule.RuleFunction.ParameterDescriptor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * @author pdhar
 *
 */
public class RuleFunctionTask extends AbstractTask {

    private RuleFunction rulefunction;




    public RuleFunctionTask() {
        // TODO Auto-generated constructor stub
    }


    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);
        EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
        EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
        String resource= (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
        Ontology ontology = getInitContext().getProcessModel().getOntology();
        this.rulefunction = ontology.getRuleFunction(resource);
    }




    /* (non-Javadoc)
      * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
      */
    @Override
    public TaskResult execute(Job context, Variables vars, Task loopTask) {
    	TaskResult result = null;
        try {
            RuleSession rs = context.getProcessAgent().getRuleSession();
            Symbols dtArgs = getRulefunction().getArguments();
            Object[] args = new Object[dtArgs.size()];
            int i=0;
            com.tibco.cep.kernel.model.rule.RuleFunction rfInstance = ((BEClassLoader)rs.getRuleServiceProvider().getTypeManager()).getRuleFunctionInstance(rulefunction.getFullPath());
            for(Iterator<Symbol> it = dtArgs.getSymbolsList().iterator();it.hasNext();){
                Symbol s = it.next();
                ParameterDescriptor paramDescriptor = rfInstance.getParameterDescriptors()[i];
                args[i++] = getTypedValue(vars, s, paramDescriptor);
            }
            Object obj = rs.invokeFunction(rulefunction.getFullPath(),args, true);

            result =  new DefaultResult(TaskResult.Status.OK,  obj);
        }

        catch (Throwable throwable) {
            logger.log(Level.ERROR, "Exception processing the RuleFunctionTask ", throwable);
            result = new ExceptionResult(throwable);
        }
        return result;  // needed for debugger exit task notification

    }

    private Object getTypedValue(Variables vars, Symbol s, ParameterDescriptor paramDescriptor) {
    	Object variable = vars.getVariable(s.getName());
    	return castAsType(variable, s, paramDescriptor);
	}

	private Object castAsType(Object variable, Symbol symbol, ParameterDescriptor paramDescriptor) {
		String type = symbol.getType();
		boolean array = symbol.isArray();
		if (variable == null) {
			return null;
		}
		RDFUberType rdfType = RDFTypes.getType(type);
		if (array) {
			Object[] castArr = null;
			if (variable instanceof Object[]) {
				Object[] varArr = (Object[]) variable;
				castArr = castAsEmptyArray(varArr.length, rdfType);
				for (int i=0; i<varArr.length; i++) {
					castArr[i] = castAsType(varArr[i], rdfType);
				}
			} else {
				castArr = castAsEmptyArray(1, rdfType);
				castArr[0] = variable;
			}
			// need to convert object array to concrete expected type
	        Class classType = paramDescriptor.getType();
	        if (classType.getComponentType().isPrimitive()) {
	        	return convertWrapperArrayToPrimitive(castArr, classType);
	        }
	        return java.util.Arrays.copyOf(castArr, castArr.length, classType);
		}
		return castAsType(variable, rdfType);
	}

	private Object convertWrapperArrayToPrimitive(Object[] castArr,
			Class classType) {
		if (int[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Integer[])castArr);
		}
		if (double[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Double[])castArr);
		}
		if (boolean[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Boolean[])castArr);
		}
		if (long[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Long[])castArr);
		}
		return castArr;
	}


	private Object[] castAsEmptyArray(int length, RDFUberType rdfType) {
		if (RDFTypes.STRING.equals(rdfType)) {
			return new String[length];
		} else if (RDFTypes.INTEGER.equals(rdfType)) {
			return new Integer[length];
		} else if (RDFTypes.DOUBLE.equals(rdfType)) {
			return new Double[length];
		} else if (RDFTypes.BOOLEAN.equals(rdfType)) {
			return new Boolean[length];
		} else if (RDFTypes.LONG.equals(rdfType)) {
			return new Long[length];
		}
		return new Object[length];
	}


	protected Object castAsType(Object variable, RDFUberType rdfType) {
		if (variable instanceof PropertyAtom) {
//			 do this here or during code generation?
			variable = ((PropertyAtom) variable).getValue();
		}
		if (RDFTypes.STRING.equals(rdfType)) {
			return String.valueOf(variable);
		} else if (RDFTypes.INTEGER.equals(rdfType)) {
			return Integer.valueOf(variable.toString());
		} else if (RDFTypes.DOUBLE.equals(rdfType)) {
			return Double.valueOf(variable.toString());
		} else if (RDFTypes.BOOLEAN.equals(rdfType)) {
			return Boolean.valueOf(variable.toString());
		} else if (RDFTypes.LONG.equals(rdfType)) {
			return Long.valueOf(variable.toString());
		}
		return variable;
	}


	public RuleFunction getRulefunction() {
        return rulefunction;
    }


}
