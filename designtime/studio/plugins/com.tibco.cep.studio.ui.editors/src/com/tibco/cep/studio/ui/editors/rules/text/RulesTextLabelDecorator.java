package com.tibco.cep.studio.ui.editors.rules.text;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.be.model.functions.impl.ModelJavaFunction;
import com.tibco.be.model.functions.utils.FunctionHelpBundle;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.FunctionsHelpBundleMananger;
import com.tibco.cep.studio.core.functions.model.EMFMetricMethodModelFunction;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.util.FunctionHoverUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EventDiagramManager;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.views.FunctionHover;

public class RulesTextLabelDecorator extends LabelProvider {

	@Override
	public Image getImage(Object element) {
//		update for domain models and rule template views
		if (element instanceof VariableDefinition) {
			String type = ((VariableDefinition) element).getType();
			return getImageForType(type);
		}
		if (element instanceof JavaStaticFunction) {
			return EditorsUIPlugin.getDefault().getImage("icons/processArchive16x16.gif");
		}
		if (element instanceof FunctionsCategory) {
			return EditorsUIPlugin.getDefault().getImage("icons/functions_category.gif");
		}
		if (element instanceof Entity) {
			return getEntityImage((Entity)element);
		}
		if (element instanceof EntityElement) {
			return getEntityImage(((EntityElement) element).getEntity());
		}
		if (element instanceof RuleElement) {
			return EditorsUIPlugin.getDefault().getImage("icons/rule-function.png");
		}
		if (element instanceof ArchiveElement) {
			return EditorsUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
		}
		if (element instanceof ElementContainer) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		return super.getImage(element);
	}

	public static Image getEntityImage(Entity element) {
		if (element instanceof Scorecard) {
			return EditorsUIPlugin.getDefault().getImage("icons/scorecard.png");
		}
		//INFO Anand Added to fix 1-APDXAR START
		if (element instanceof Metric) {
			return EditorsUIPlugin.getDefault().getImage("icons/metric_16x16.gif");
		}
		//INFO Anand Added to fix 1-APDXAR END
		if (element instanceof Concept) {
			return EditorsUIPlugin.getDefault().getImage("icons/concept.png");
		}
		if (element instanceof TimeEvent) {
			return EditorsUIPlugin.getDefault().getImage("icons/time-event.gif");
		}
		if (element instanceof SimpleEvent) {
			return EditorsUIPlugin.getDefault().getImage("icons/event.png");
		}
		if (element instanceof Channel) {
			return EditorsUIPlugin.getDefault().getImage("icons/channel_16x16.png");
		}
		if (element instanceof StateMachine) {
			return EditorsUIPlugin.getDefault().getImage("icons/state_machine.png");
		}
		if (element instanceof PropertyDefinition) {
			return getPropertyImage((PropertyDefinition) element);
		}
		if (element instanceof RuleTemplateView) {
			return EditorsUIPlugin.getDefault().getImage("icons/rulesTemplate.png");
		}
		if (element instanceof Domain) {
			return EditorsUIPlugin.getDefault().getImage("icons/domainModelView_16x15.png");
		}

		return null;
	}

	private static Image getPropertyImage(PropertyDefinition element) {
		switch (element.getType()) {
		case BOOLEAN:
			return EditorsUIPlugin.getDefault().getImage("icons/iconBoolean16.gif");

		case CONCEPT:
			return EditorsUIPlugin.getDefault().getImage("icons/iconConcept16.gif");

		case CONCEPT_REFERENCE:
			return EditorsUIPlugin.getDefault().getImage("icons/iconConceptRef16.gif");

		case DATE_TIME:
			return EditorsUIPlugin.getDefault().getImage("icons/iconDate16.gif");

		case INTEGER:
			return EditorsUIPlugin.getDefault().getImage("icons/iconInteger16.gif");

		case LONG:
			return EditorsUIPlugin.getDefault().getImage("icons/iconLong16.gif");

		case DOUBLE:
			return EditorsUIPlugin.getDefault().getImage("icons/iconReal16.gif");

		case STRING:
			return EditorsUIPlugin.getDefault().getImage("icons/iconString16.gif");

		default:
			break;
		}
		return null;
	}

	private Image getImageForType(String type) {
		if (type == null) {
			return null;
		}
		// primitive types
		if ("String".equals(type)) {
			return EditorsUIPlugin.getDefault().getImage("icons/iconString16.gif");
		}
		if ("int".equals(type)) {
			return EditorsUIPlugin.getDefault().getImage("icons/iconInteger16.gif");
		}
		if ("boolean".equals(type)) {
			return EditorsUIPlugin.getDefault().getImage("icons/iconBoolean16.gif");
		}
		if ("double".equals(type)) {
			return EditorsUIPlugin.getDefault().getImage("icons/iconReal16.gif");
		}
		if ("long".equals(type)) {
			return EditorsUIPlugin.getDefault().getImage("icons/iconLong16.gif");
		}
		if ("Object".equals(type)) {
			return EditorsUIPlugin.getDefault().getImage("icons/Property.png");
		}

		// could be a concept/event
		return EditorsUIPlugin.getDefault().getImage("icons/outline_node.png");
	}

	@Override
	public String getText(Object element) {
		if (element instanceof PropertyDefinition) {
			return ((PropertyDefinition) element).getName();
		}
		if (element instanceof Entity) {
			return ((Entity) element).getName();
		}
		if (element instanceof EntityElement) {
			return ((EntityElement) element).getName();
		}
		if (element instanceof RuleElement) {
			return ((RuleElement) element).getName();
		}
		if (element instanceof VariableDefinition) {
			return ((VariableDefinition) element).getName();
		}
		if (element instanceof ElementContainer) {
			return ((ElementContainer) element).getName();
		}
		if (element instanceof JavaStaticFunction) {
			return ((JavaStaticFunction) element).getName().getLocalName();
		}
		if (element instanceof ModelJavaFunction) {
			return ((ModelJavaFunction) element).getName().getLocalName();
		}
		if (element instanceof FunctionsCategory) {
			return ((FunctionsCategory) element).getName().getLocalName();
		}
		if (element instanceof ArchiveElement) {
			return ((ArchiveElement) element).getName();
		}

		return "";
	}

	/**
	 * Calculate information for this element, used by text hover, content assist information, etc <br><br>
	 * NOTE: The <code>projectName</code> parameter is only used when calculating information for static functions.
	 * If <code>element</code> is known to be something else, then <code>projectName</code> can safely be set
	 * to null.
	 * @param element
	 * @param projectName
	 * @return
	 */
	public static String getInfo(Object element, String projectName) {
		return getInfo(element, null, projectName);
	}

	public static String getInfo(Object element, ElementReference reference, String projectName) {
		boolean isMethod = reference == null ? false : reference.isMethod();
		if (element instanceof PropertyDefinition) {
			return getPropertyInfo(((PropertyDefinition) element));
		}
		if (element instanceof Entity) {
			return getEntityInfo(projectName, (Entity) element, reference);
		}
		if (element instanceof EntityElement) {
			Entity entity = ((EntityElement) element).getEntity();
			return getEntityInfo(projectName, entity, reference);
		}
		if (element instanceof RuleElement) {
			return getRuleInfo((RuleElement) element, isMethod);
		}
		if (element instanceof VariableDefinition) {
			return getVariableInfo((VariableDefinition) element);
		}
		if (element instanceof ElementContainer) {
			return getContainerInfo((ElementContainer) element);
		}
		if (element instanceof JavaStaticFunction) {
			return getStaticFunctionInfo((JavaStaticFunction)element, projectName);
		}
		if(element instanceof EMFModelJavaFunction) {
			return getModelJavaFunctionInfo((EMFModelJavaFunction)element,projectName);
		}
		if (element instanceof FunctionsCategory) {
			return getFunctionsCategoryInfo((FunctionsCategory)element);
		}
		if (element instanceof FunctionRec) {
			return getMethodInfo((FunctionRec) element);
		}
		if (element instanceof ElementReference) {
			return ((ElementReference) element).getName();
		}
		if (element instanceof String) {
			return (String) element;
		}
		return "";
	}

	private static String getModelJavaFunctionInfo(EMFModelJavaFunction element, String projectName) {


		if (element.getAnnotation() != null) {
			BEFunction ann = element.getAnnotation();
			String categoryName = element.getName().getNamespaceURI();
			return FunctionHoverUtil.getDynamicToolTip(categoryName, ann);
		}
		return "";
	}

	public static String getMetaInfo(Object element) {
		if (element instanceof PropertyDefinition) {
			return "Property Definition";
		}
		if (element instanceof EntityElement) {
			element = ((EntityElement) element).getEntity();
		}
		if (element instanceof Scorecard) {
			return "Scorecard";
		}
		if (element instanceof Concept) {
			return "Concept";
		}
		if (element instanceof Event) {
			return "Event";
		}
		if (element instanceof RuleElement) {
			return "Rule";
		}
		if (element instanceof VariableDefinition) {
			element = "Type "+((VariableDefinition) element).getType();
		}
		if (element instanceof ElementContainer) {
			return "Folder";
		}
		if (element instanceof JavaStaticFunction) {
			return "Catalog Function";
		}
		if (element instanceof FunctionsCategory) {
			return "Functions Category";
		}
		if (element instanceof ElementReference) {
			return "Element reference";
		}
		if (element instanceof String) {
			return (String) element;
		}
		return "";
	}

	public static String getVariableInfo(VariableDefinition var) {
		StringBuffer buffer = new StringBuffer();
		if (var instanceof GlobalVariableDef) {
			buffer.append("Parameter:\n\t");
		} else {
			buffer.append("Local variable:\n\t");
		}
		buffer.append("\t");
		buffer.append("Name: ");
		buffer.append(var.getName());
		buffer.append("\n\t\tType: ");
		buffer.append(var.getType());
		if (var.isArray()) {
			buffer.append("[]");
		}
		buffer.append("\n");

		return buffer.toString();
	}

	public static String getStaticFunctionInfo(JavaStaticFunction element, String projectName) {
		if (element.getToolTip() != null && element.getToolTip().length() > 0) {
			return element.getToolTip();
		}

		if (element.getMethod() != null && element.getMethod().isAnnotationPresent(BEFunction.class)) {
			BEFunction ann = element.getMethod().getAnnotation(BEFunction.class);
			String categoryName = element.getName().getNamespaceURI();
			return FunctionHoverUtil.getDynamicToolTip(categoryName, ann);
		}
		else if(element.getMethod() != null && FunctionHover.isAnnotationPresent(element.getMethod(), BEFunction.class.getName())){
			Annotation ann = FunctionHover.getAnnotation(element.getMethod(),BEFunction.class.getName());
			if(ann != null) {
				String categoryName = element.getName().getNamespaceURI();
				return FunctionHoverUtil.getDynamicToolTip(categoryName,ann);
			}
		}

		FunctionHelpBundle helpBundle = FunctionsHelpBundleMananger.getInstance().getHelpBundle(projectName);
		if (helpBundle != null) {
			String tooltip = helpBundle.tooltip(element);
			if (tooltip != null && tooltip.endsWith(Messages.getString("catalog.functionsView.tooltip"))) {
				tooltip = Messages.getString("catalog.functionsView.functionhover.defaultText", element.getName().localName);
			}
			if (tooltip != null) {
				return tooltip;
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("Static function:\n Name:");
		buffer.append(element.getName());
		buffer.append("\n\tMethod: ");
		Method method = element.getMethod();
		buffer.append(element.getReturnClass().getSimpleName());
		Class[] cl = element.getArguments();
		buffer.append(" ");
		buffer.append(method.getName());
		buffer.append(" (");
		if(cl.length > 0){
			buffer.append(cl[0].getSimpleName());
			for(int k = 1 ;k<cl.length; k++){
				buffer.append(", ");
				buffer.append(cl[k].getSimpleName());
			}
		}
		buffer.append(")");
		return buffer.toString();
	}

	public static String getContainerInfo(ElementContainer element) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Folder: ");
		buffer.append(element.getName());

		EList<DesignerElement> entries = element.getEntries();
		buffer.append("\n contains ");
		buffer.append(entries.size());
		buffer.append(" elements:\n");
		for (DesignerElement designerElement : entries) {
			buffer.append(" - ");
			buffer.append(designerElement.getName());
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public static String getEntityInfo(String projectName, Entity entity, ElementReference reference) {
		boolean isMethod = reference == null ? false : reference.isMethod();
		if (isMethod) {
			String info = getModelFunctionInfo(entity, reference);
			if (info != null) {
				return info;
			}
		}

		if (entity instanceof Concept) {
			String tooltip = ConceptDiagramManager.createToolTip((Concept) entity);
			return convertToolTip(tooltip);
		} else if (entity instanceof Event) {
			String tooltip = EventDiagramManager.createToolTip(projectName, (Event) entity);
			return convertToolTip(tooltip);
		} else if (entity instanceof PropertyDefinition) {
			return getPropertyInfo(((PropertyDefinition) entity));
		} else if (entity instanceof RuleTemplateView) {
			return getTemplateViewInfo((RuleTemplateView) entity);
		} else if (entity instanceof Domain) {
			return getDomainInfo((Domain) entity);
		}
		return entity.toString();
	}

	private static String getDomainInfo(Domain entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Domain Model definition:\n\t");
		buffer.append("Name: ");
		buffer.append(entity.getName());
		buffer.append("\n\t");
		buffer.append("Type: ");
		buffer.append(entity.getDataType().getLiteral());
		buffer.append("\n\t");
		buffer.append("Number of entries: ");
		buffer.append(entity.getEntries().size());
		buffer.append("\n");

		return buffer.toString();
	}

	public static FunctionRec[] getModelFunctions(Entity element, ElementReference reference) {
		if (element instanceof Metric){
			if (reference != null){
				return new FunctionRec[]{ElementReferenceResolver.getModelFunction(element, reference)};
			}
			String fnName = ModelUtils.convertPathToPackage(element.getFullPath());
			String[] availableFunctionNames = EMFMetricMethodModelFunction.getAvailableFunctions();
			FunctionRec[] functions = new FunctionRec[availableFunctionNames.length];
			for (int i = 0; i < availableFunctionNames.length; i++) {
				functions[i] = ElementReferenceResolver.getModelFunction(element.getOwnerProjectName(),fnName+"."+availableFunctionNames[i]);
			}
			return functions;
		}
		return new FunctionRec[]{ElementReferenceResolver.getModelFunction(element,reference)};
	}

	public static String getModelFunctionInfo(Entity element, ElementReference reference) {
		FunctionRec modelFunction = ElementReferenceResolver.getModelFunction(element, reference);
		return getMethodInfo(modelFunction);
	}

	public static String getMethodInfo(FunctionRec function) {
		if (function != null && function.function instanceof ModelFunction) {
			return ((ModelFunction) function.function).inline();
		}
		return null;
	}

	public static String getRuleInfo(RuleElement element, boolean isMethod) {
		if (true) { // TODO : get smarter about when to show html, when not to (i.e. for 'rank' statements, do not show html)
			FunctionRec ruleModelFunction = ElementReferenceResolver.getRuleModelFunction(element);
			String info = getMethodInfo(ruleModelFunction);
			if (info != null) {
				return info;
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("Rule: ");
		buffer.append(element.getName());
		buffer.append("\n");
		buffer.append("Folder: ");
		buffer.append(element.getFolder());
		buffer.append("\n");
		EList<GlobalVariableDef> globalVariables = element.getGlobalVariables();
		buffer.append("Parameters:\n");
		for (GlobalVariableDef var : globalVariables) {
			buffer.append("\t");
			buffer.append(getVariableInfo(var));
		}

		return buffer.toString();
	}

	public static String getPropertyInfo(PropertyDefinition propertyDefinition) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Property definition:\n\t");
		buffer.append("Name: ");
		buffer.append(propertyDefinition.getName());
		buffer.append(" Type:");
		buffer.append(propertyDefinition.getType());
		buffer.append("\n");

		return buffer.toString();
	}

	public static String getTemplateViewInfo(RuleTemplateView templateView) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Rule Template View definition:\n\t");
		buffer.append("Name: ");
		buffer.append(templateView.getName());
		buffer.append("\n");

		return buffer.toString();
	}

	public static String getFunctionsCategoryInfo(FunctionsCategory element) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Function category: \n Name:");
		buffer.append(element.getName());
		buffer.append("\n Functions/Subcategories\n\t");
		for (Iterator iterator = ((FunctionsCategory) element).all(); iterator.hasNext();) {
			Object type = iterator.next();
			String name = "";
			if (type instanceof FunctionsCategory) {
				name = ((FunctionsCategory) type).getName().getLocalName();
				buffer.append(name);
				buffer.append(" (SubCategory)\n\t");
			} else if (type instanceof JavaStaticFunction) {
				name = ((JavaStaticFunction) type).getName().getLocalName();
				buffer.append(name);
				buffer.append(" (Function)\n\t");
			}
		}

		return buffer.toString();
	}

	private static String convertToolTip(String tooltip) {
		tooltip = tooltip.replaceAll("\\n", "\n");
		tooltip = tooltip.replaceAll("<b>", "\n");
		tooltip = tooltip.replaceAll("</b>", "");
		tooltip = tooltip.replaceAll("<p>", "");
		tooltip = tooltip.replaceAll("</p>", "");
		tooltip = tooltip.replaceAll("<i>", "");
		tooltip = tooltip.replaceAll("</i>", "");

		return tooltip;
	}

}
