package com.tibco.cep.studio.tester.ui.editor.data;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.tester.core.model.EntityType;
import com.tibco.cep.studio.tester.core.model.InvocationObjectType.InvocationObjectTypeEnum;
import com.tibco.cep.studio.tester.core.model.ReteObjectType;
import com.tibco.cep.studio.tester.core.model.ReteObjectType.ReteChangeType;
import com.tibco.cep.studio.tester.core.model.RuleType;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.data.TestResultsContentProvider.ParentNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class TestResultsLabelProvider extends LabelProvider {

	private HashMap<String, Image> imageCache = new HashMap<String, Image>();
	public static final String CLASS = TestResultsLabelProvider.class.getName();
	private static final int TIBCO_BE_NS_LENGTH = "www.tibco.com/be/ontology".length();
	
	/**
	 * Keep a reference to this
	 */
	@SuppressWarnings("unused")
	private TreeViewer treeViewer;
	
	/**
	 * @param treeViewer
	 * @param projectName
	 */
	public TestResultsLabelProvider(final TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	
	/*
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element) {
		ImageDescriptor descriptor = null;
		Image image = null;
		try {
			if (element instanceof ReteObjectElement) {
				ReteObjectElement retObject = (ReteObjectElement)element;
				ReteObjectType object = retObject.getReteObjectType();
				if (object.getConcept() != null) {
					if (object.getConcept().isIsScorecard()) {
						descriptor = StudioUIPlugin.getImageDescriptor("icons/scorecard.png");
					} else {
						descriptor = StudioUIPlugin.getImageDescriptor("icons/concept.png");
					}
				}
				if (object.getEvent() != null) {
					if (object.getEvent().isIsTimeEvent()) {
						descriptor = StudioUIPlugin.getImageDescriptor("icons/time-event.gif");
					} else {
						descriptor = StudioUIPlugin.getImageDescriptor("icons/event.png");
					}
				}
				if (object.getRule() != null) {
					descriptor = StudioUIPlugin.getImageDescriptor("icons/rules.png");
				}
			} 
			if (element instanceof InvocationObjectElement) {
				InvocationObjectElement invokElement = (InvocationObjectElement)element;
				descriptor = getInvokeObjectImage(invokElement.getInvocationObjectType().getType());
			}
			if (element instanceof ConceptTypeElement) {
				ConceptTypeElement conceptTypeElement = (ConceptTypeElement)element;
				if (conceptTypeElement.getConceptType().isIsScorecard()) {
					descriptor = StudioUIPlugin.getImageDescriptor("icons/scorecard.png");
				} else {
					descriptor = StudioUIPlugin.getImageDescriptor("icons/concept.png");
				}
			}
			if (element instanceof ParentNode) {
				if (((ParentNode) element).isStartState()) {
					return StudioUIPlugin.getDefault().getImage("icons/startState.png");
				}
				return StudioUIPlugin.getDefault().getImage("icons/endState.png");
			}
			if (element instanceof EventTypeElement) {
				descriptor = StudioUIPlugin.getImageDescriptor("icons/event.png");
			}
			if (descriptor == null) {
				StudioTesterUIPlugin.debug(CLASS, "Image missing for type ");
				return null;
			}
			image = (Image)imageCache.get(descriptor);
			if (image == null) {
				image = descriptor.createImage();
				imageCache.put(element.toString(), image);
			}
			if (element instanceof ConceptTypeElement 
					|| element instanceof EventTypeElement) {
				image = new DecorationOverlayIcon(image, 
						StudioTesterUIPlugin.getImageDescriptor("icons/causal_8x7.gif"),IDecoration.TOP_LEFT).createImage();
			}
			if (element instanceof InvocationObjectElement) {
				image = new DecorationOverlayIcon(image, 
						StudioTesterUIPlugin.getImageDescriptor("icons/InvokeObject_8x7.gif"),IDecoration.TOP_LEFT).createImage();
			}
		} catch (Exception e) {
			StudioTesterUIPlugin.log(e);
		}
		return image;
	}

	/*
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) {
		String label = "<none>";
		try {
			if (element instanceof ReteObjectElement) {
				ReteObjectElement retObject = (ReteObjectElement)element;
				ReteObjectType object = retObject.getReteObjectType();
				if (object.getConcept() != null) {
					label = getLabel(object.getConcept());
				} else if (object.getEvent() != null) {
					label = getLabel(object.getEvent());
					if (object.getEvent().isIsTimeEvent() && 
							object.getChangeType() == ReteChangeType.SCHEDULEDTIMEEVENT) {
						label = label + " [Scheduled]";
					}
				} else if (object.getRule() != null) {
					label = getLabel(object.getRule());
				}
			} 
			if (element instanceof InvocationObjectElement) {
				InvocationObjectElement invokElement = (InvocationObjectElement)element;
				label = invokElement.getInvocationObjectType().getNamespace().getValue() + " [Invoked]";
			}
			if (element instanceof ConceptTypeElement) {
				ConceptTypeElement conceptTypeElement = (ConceptTypeElement)element;
				String namespace = getLabel (conceptTypeElement.getConceptType());
				label = namespace + " [Causal]";
			}
			if (element instanceof EventTypeElement) {
				EventTypeElement eventTypeElement = (EventTypeElement)element;
				String namespace = getLabel (eventTypeElement.getEventType());
				label = namespace + " [Causal]";
			}
			if (element instanceof ParentNode) {
				return ((ParentNode) element).getLabel();
			}
		} catch (Exception e) {
			StudioTesterUIPlugin.log(e);
		}
		return label;
	}
	
	/**
	 * @param type
	 * @return
	 */
	private ImageDescriptor getInvokeObjectImage(InvocationObjectTypeEnum type) {
		ImageDescriptor descriptor = null;
		switch (type) {
		case RULE:
			descriptor = StudioUIPlugin.getImageDescriptor("icons/rules.png");
			break;
		case RULEFUNCTION:
			descriptor = StudioUIPlugin.getImageDescriptor("icons/rule-function.png");
			break;
		}
		return descriptor;
	}
	
	/**
	 * @param entityType
	 * @return
	 */
	private String getLabel(RuleType entityType) {
		String namespace = entityType.getNamespace();
		if (namespace != null) {
			namespace = namespace.substring(TIBCO_BE_NS_LENGTH, namespace.length());
		}
		namespace = namespace +  "{name = " + entityType.getName() + "}";
		return namespace;
	}
	
	/**
	 * @param entityType
	 * @return
	 */
	private String getLabel(EntityType entityType) {
		String namespace = entityType.getNamespace();
		if (namespace != null) {
			namespace = namespace.substring(TIBCO_BE_NS_LENGTH, namespace.length());
		}
		namespace = namespace +  "{id = " + entityType.getId() + "}";
		return namespace;
	}

	public void dispose() {
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();) {
			i.next().dispose();
		}
		imageCache.clear();
	}
}