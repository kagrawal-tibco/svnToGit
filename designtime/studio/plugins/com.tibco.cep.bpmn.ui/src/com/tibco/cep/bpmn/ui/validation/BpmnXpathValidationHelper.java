package com.tibco.cep.bpmn.ui.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.XPathBooleanExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.properties.MapperControl;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection.EntityMapperContext;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CodeErrorMessage;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.ErrCheckErrorList;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

public class BpmnXpathValidationHelper {

	private static final String XPATH_ERROR = BpmnMessages.getString("bpmnXpathValidationHelper_XPATH_ERROR_label");
	
	public static List<String> validateXPathFunction(EObject flowElement, EObject process, IProject project) {
		List<String> errors = new ArrayList<String>();
		String xpath = "";
		if(flowElement.eClass().equals(BpmnModelClass.RECEIVE_TASK)||
				flowElement.eClass().equals(BpmnModelClass.PARALLEL_GATEWAY)||
				flowElement.eClass().equals(BpmnModelClass.SEQUENCE_FLOW)){
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(flowElement);
			

			if (flowElement.eClass().equals(BpmnModelClass.SEQUENCE_FLOW)) {
				EObject source = wrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
				if (source.eClass().equals(BpmnModelClass.EXCLUSIVE_GATEWAY)
						|| source.eClass().equals(BpmnModelClass.INCLUSIVE_GATEWAY)
						|| source.eClass().equals(BpmnModelClass.COMPLEX_GATEWAY)) {
					EObjectWrapper<EClass, EObject> sourceWrap = EObjectWrapper
							.wrap(source);
					EObject attribute = sourceWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
					if (attribute != null) {
						EObjectWrapper<EClass, EObject> attributeWrap = EObjectWrapper
								.wrap(attribute);
						String id = attributeWrap
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						String seqId = wrapper
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						if (!id.equals(seqId)) {
							EObject expression = (EObject) wrapper
									.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION);
							if (expression != null) {
								EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper
										.wrap(expression);
								xpath = (String) expressionWrapper
										.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
								
								if(xpath.trim().isEmpty()){
									errors.add(XPATH_ERROR +  BpmnMessages.getString("bpmnXpathValidationHelper_error_expressionMissing_message"));
								}
							}
						}
					}

				}
				

			} else if(flowElement.eClass().equals(BpmnModelClass.RECEIVE_TASK)){
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrapper);
				if(valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY)){
					xpath = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
				}
				if(xpath.trim().isEmpty())
					errors.add(XPATH_ERROR +  "Job key expression is missing");
			}else if(flowElement.eClass().equals(BpmnModelClass.PARALLEL_GATEWAY)){
				EObjectWrapper<EClass, EObject> flowElementWrapper = EObjectWrapper.wrap(flowElement);
				int incoming = 0;
				EList<EObject> listAttribute = flowElementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
				if(listAttribute != null)
					incoming = listAttribute.size();
				
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrapper);
				if(valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION)){
					xpath = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION);
				}
				if(incoming > 1 && xpath.trim().isEmpty())
					errors.add(XPATH_ERROR + BpmnMessages.getString("bpmnXpathValidationHelper_error_mergeExpMissing_message"));
			}
		}

		EObjectWrapper<EClass, EObject> objectWrapper = EObjectWrapper
				.wrap(flowElement);
		///xslt validation for loop characteristics
		if (BpmnModelClass.ACTIVITY
				.isSuperTypeOf(objectWrapper.getEClassType())
				|| (objectWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS))) {
			Object attribute = objectWrapper
					.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
			EObjectWrapper<EClass, EObject> loopWrapper = null;
			if (attribute != null)
				loopWrapper = EObjectWrapper.wrap((EObject) attribute);
			if (BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS
					.isInstance(attribute)) {
				attribute = loopWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT);
				if (attribute != null && !attribute.toString().isEmpty()) {
					xpath = attribute.toString();
				}

			}

			if (BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS
					.isInstance(attribute)) {
				attribute = loopWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM);
				System.out.println();
				if (attribute != null && !attribute.toString().isEmpty()) {
					xpath = attribute.toString();
				}

			}
		}
		///xslt validation for loop characteristicsv--end

		if(xpath.trim().isEmpty()){
			return errors;
		}
		
		try {
			EntityMapperContext mctx = new MapperPropertySection.EntityMapperContext(
					project);
			MapperControl mc = new MapperControl(mctx, null);
			mctx.setMapper(mc);
			if(flowElement.eClass().equals(BpmnModelClass.RECEIVE_TASK)){
				Object attachedResource = BpmnModelUtils.getAttachedResource(flowElement);
				if(attachedResource != null && attachedResource instanceof String){
					String event = (String)attachedResource;
					if(!event.trim().isEmpty()){
						DesignerElement element = IndexUtils.getElement(
								project.getName(), event);
						mctx.addDefinition(element.getName(), event,
								false);
					}
					
				}
				
			}
//			else{
				CommonOntologyAdapter adaptor = new CommonOntologyAdapter(project.getName());
				ProcessAdapter createAdapter = new ProcessAdapter(process, adaptor);
				mctx.addDefinition(MapperConstants.JOB, createAdapter, false);
//			}
			VariableDefinitionList vdlist = new VariableDefinitionList();
			vdlist.add(new VariableDefinition(
					ExpandedName.makeName(GlobalVariablesProvider.NAME), 
					SmSequenceTypeFactory.create(mctx.getGlobalVariables().toSmElement(false))));
			List<VariableDefinition> list = mctx.getDefinitions();
			for (VariableDefinition variableDefinition : list) {
				vdlist.add(variableDefinition);
			}
			
			
			VariableDefinitionList vdl = makeInputVariableDefinitions(mctx);
			

			XiNode xpathNode = null;
			try {
				xpathNode = XSTemplateSerializer.deSerializeXPathString(xpath);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			HashMap<?, ?> map = XSTemplateSerializer.getNSPrefixesinXPath(xpathNode);
			Iterator<?> itr = map.keySet().iterator();
			NamespaceContextRegistry nsmapper = MapperCoreUtils.getNamespaceMapper();
			while (itr.hasNext()) {
				String pfx = (String)itr.next();
				String uri = (String)map.get(pfx);
				nsmapper.getOrAddPrefixForNamespaceURI(uri, pfx);
			}
			ExprContext ec = new ExprContext(vdl, StudioCorePlugin.getUIAgent(project.getName()).getFunctionResolver()).createWithNamespaceMapper(nsmapper);
			// end initialization
			
			// actual error checking code
			ErrCheckErrorList errorsList = MapperCoreUtils.getErrors(XSTemplateSerializer.getXPathExpressionAsStringValue(xpath), ec, nsmapper, null, false);
			errors.addAll(checkErrorList(errorsList, xpathNode));
			if(errors.size() == 0){
				if(flowElement.eClass().equals(BpmnModelClass.SEQUENCE_FLOW)){
					XPathTypeReport typeReport = getTypeReport(XSTemplateSerializer.getXPathExpressionAsStringValue(xpath), ec);
					if(typeReport != null && typeReport.xtype != null){
						SmSequenceType xtype = typeReport.xtype;
						XPathBooleanExpressionValidator validator = new XPathBooleanExpressionValidator();
						boolean valid = validator.isValid(xtype);
						if(!valid)
							errors.add(XPATH_ERROR+BpmnMessages.getString("bpmnXpathValidationHelper_error_expToBoolean_message"));
					}
				}
			}
		} catch (Exception e) {
		}
		
		return  errors;
	}
	
	private static List<String> checkErrorList(ErrCheckErrorList errors, XiNode xpath) {
		List<String> elist = new ArrayList<String>();
		if (errors == null || errors.getCount() == 0) {
			return elist;
		}
		int count = errors.getCount();
		if (count > 0) {
			CodeErrorMessage[] messages = errors.getMessages();
			for (int i = 0; i < messages.length; i++) {
				TextRange textRange = messages[i].getTextRange();
				String details = null;
				if (textRange != null) {
					try {
						int start = textRange.getStartPosition();
						int end = textRange.getEndPosition();
						if (start >= 0 && start < end);
						details = xpath.getStringValue().trim().substring(start, end);
					} catch (Exception e) {
					}
				}
				String message = XPATH_ERROR+messages[i].getMessage();
				if (details != null) {
					message += " (" + details + ")";
				}
				if (messages[i].getSeverity() == CodeErrorMessage.TYPE_ERROR) {
					elist.add(message);
				} 
			}
		}
		
		return elist;
	}
	
	private static VariableDefinitionList makeInputVariableDefinitions(EntityMapperContext mctx) {

		VariableDefinitionList vdlist = new VariableDefinitionList();
		if (mctx != null) {
			
			vdlist.add(new VariableDefinition(
					ExpandedName.makeName(GlobalVariablesProvider.NAME), 
					SmSequenceTypeFactory.create(mctx.getGlobalVariables().toSmElement(false))));
			
			List<VariableDefinition> definitions = mctx.getDefinitions();
			for (VariableDefinition variableDefinition : definitions) {
				vdlist.add(variableDefinition);
			}
			
		}
		
		return vdlist;
	}
	
    public static XPathTypeReport getTypeReport(String xpath, ExprContext exprContext)
    {
            Parser ret = new Parser(Lexer.lex(xpath));
            ErrorMessageList syntaxErrors = ret.getErrorMessageList();
            Expr expr = ret.getExpression();

        XPathTypeReport retTypeReport;
        if (xpath.length()==0) {
            retTypeReport = new XPathTypeReport();
        } else {
            // now do semantic check:
            if (exprContext!=null) { // if null, don't do any semantic checking.
                ExprContext context = exprContext;
                retTypeReport = expr.evalType(context);
                if (syntaxErrors.size()>0)
                {
                    // combine the syntax & type errors:
                    retTypeReport = new XPathTypeReport(retTypeReport.xtype,syntaxErrors.addMessages(retTypeReport.errors));
                }
            } else {
                // no syntax errors, nothing to check.
                retTypeReport = new XPathTypeReport();
            }
        }
        
        return retTypeReport;
    }
}
