package com.tibco.cep.studio.ui.providers;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.core.functions.model.EMFModelFunctionCategory;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.views.CatalogFunctionsView.CustomFunctionsCatalogNode;
import com.tibco.cep.studio.ui.views.CatalogFunctionsView.FunctionsCatalogNode;

public class CatalogFunctionsLabelProvider extends LabelProvider implements ILightweightLabelDecorator {

	@Override
	public Image getImage(Object element) {
		if (element instanceof EMFModelFunctionCategory) {
			Entity entity = ((EMFModelFunctionCategory) element).getEntity();
			if (entity instanceof TimeEvent) {
				return StudioUIPlugin.getDefault().getImage("icons/time-event.gif");
			} else if (entity instanceof Event) {
				return StudioUIPlugin.getDefault().getImage("icons/event.png");
			} else if (entity instanceof Channel) {
				return StudioUIPlugin.getDefault().getImage("icons/channel.png");
			} else if (entity instanceof Metric) {
				return StudioUIPlugin.getDefault().getImage("icons/metric_16x16.gif");
			} else if (entity instanceof Concept) {
				return StudioUIPlugin.getDefault().getImage("icons/concept.png");
			}
		} else if (element instanceof FunctionsCategory) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		} else if (element instanceof Predicate) {
			return StudioUIPlugin.getDefault().getImage("icons/function.png");
		} else if (element instanceof FunctionsCatalog) {
			return StudioUIPlugin.getDefault().getImage("icons/library_obj.gif");
		} else if (element instanceof FunctionsCatalogNode) {
			int type = ((FunctionsCatalogNode) element).getType();
			if (type == FunctionsCatalogNode.STATIC_CATALOG) {
				return StudioUIPlugin.getDefault().getImage("icons/standardfunction_16x16.png");
			} else if (type == FunctionsCatalogNode.ONTOLOGY_CATALOG) {
				return StudioUIPlugin.getDefault().getImage("icons/ontologyfunction_16x16.png");
			} else if (type == FunctionsCatalogNode.CUSTOM_CATALOG) {
				return StudioUIPlugin.getDefault().getImage("icons/customfunction_16x16.png");
			}
			return StudioUIPlugin.getDefault().getImage("icons/library_obj.gif");
		} else if (element instanceof CustomFunctionsCatalogNode) {
			return StudioUIPlugin.getDefault().getImage("icons/customfunction_16x16.png");
		}

		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof FunctionsCategory) {
			String name = ((FunctionsCategory) element).getName().localName;
			if (name.endsWith(".Ontology")) {
				return name.substring(0, name.indexOf(".Ontology"));
			}
			return name;
		} else if (element instanceof Predicate) {
			Predicate p = (Predicate) element;
			return p.getName().localName;
		} else if (element instanceof FunctionsCatalog) {
			return "Catalog Functions";
		} else if (element instanceof FunctionsCatalogNode) {
			int type = ((FunctionsCatalogNode) element).getType();
			if (type == FunctionsCatalogNode.STATIC_CATALOG) {
				return "Built-in Functions";
			} else if (type == FunctionsCatalogNode.ONTOLOGY_CATALOG) {
				return "Ontology Functions";
			} else if (type == FunctionsCatalogNode.CUSTOM_CATALOG) {
				return "Custom Functions";
			}
			return "Catalog Functions";
		} else if (element instanceof CustomFunctionsCatalogNode) {
			return "Custom Functions";
		}
		return super.getText(element);
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof Predicate) {
			Predicate p = (Predicate) element;
			if (!p.isValidInCondition()) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/function-action-overlay.png"), IDecoration.BOTTOM_RIGHT);
			}
			if (p.isValidInBUI()) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/function_bui-overlay.gif"), IDecoration.BOTTOM_LEFT);
			} 
			if (element instanceof JavaStaticFunctionWithXSLT) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/function_mapper-overlay.gif"), IDecoration.TOP_LEFT);
			} 
			if (p.isValidInQuery()) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/function_query-overlay.gif"), IDecoration.TOP_RIGHT);
			}
		}
	}

}
