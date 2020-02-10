package com.tibco.cep.studio.ui.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.MemberElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class GeneralIndexLabelProvider extends LabelProvider {

	private String projectName;

	public GeneralIndexLabelProvider(String projectName) {
		this.projectName = projectName;
	}

	public GeneralIndexLabelProvider() {
	}

	public String getText(Object obj) {
		if (obj instanceof DesignerElement) {
			return ((DesignerElement)obj).getName();
		}
		if (obj instanceof Entity) {
			return ((Entity)obj).getName();
		}
		if (obj instanceof ArchiveResource) {
			return ((ArchiveResource)obj).getName();
		}
		if (obj instanceof Implementation) {
			return ((Implementation)obj).getName();
		}
		if (obj instanceof VariableDefinition) {
			return ((VariableDefinition) obj).getName();
		}
		if (obj instanceof ElementReference) {
			return ((ElementReference) obj).getName();
		}
		if (obj instanceof Symbol) {
			return ((Symbol) obj).getIdName();
		}
		return obj.toString();
	}
	
	public Image getImage(Object element) {
		if (element instanceof Symbol && projectName != null) {
			String type = ((Symbol) element).getType();
			element = IndexUtils.getElement(projectName, ModelUtils.convertPackageToPath(type));
		}
		if (element instanceof DesignerProject) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
		} else if (element instanceof EntityElement) {
			element = ((EntityElement)element).getEntity();
		}
		
		// designer elements
		if (element instanceof RuleElement) {
			return StudioUIPlugin.getDefault().getImage("icons/rules_folder.png");
		}
		if (element instanceof ElementContainer) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		if (element instanceof ArchiveElement) {
			return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
		}
		
		// archives
		if (element instanceof EnterpriseArchive) {
			return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
		} else if (element instanceof BusinessEventsArchiveResource) {
			return StudioUIPlugin.getDefault().getImage("icons/beArchive16x16.gif");
		} else if (element instanceof SharedArchive) {
			return StudioUIPlugin.getDefault().getImage("icons/sharedArchive16x16.gif");
		} else if (element instanceof ProcessArchive) {
			return StudioUIPlugin.getDefault().getImage("icons/processArchive16x16.gif");
		} else if (element instanceof IFile) {
			return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
		}
		
		// rules
		if (element instanceof RuleFunction) {
			return StudioUIPlugin.getDefault().getImage("icons/rule-function.png");
		} else if (element instanceof Rule) {
			return StudioUIPlugin.getDefault().getImage("icons/rules.png");
		} 
		
		// entities
		if (element instanceof Scorecard) {
			return StudioUIPlugin.getDefault().getImage("icons/scorecard.png");
		} else if (element instanceof Concept) {
			return StudioUIPlugin.getDefault().getImage("icons/concept.png");
		} else if (element instanceof Event) {
			return StudioUIPlugin.getDefault().getImage("icons/event.png");
		} else if (element instanceof Channel) {
			return StudioUIPlugin.getDefault().getImage("icons/channel.png");
		} else if (element instanceof Destination) {
			return StudioUIPlugin.getDefault().getImage("icons/destination.png");
		} else if (element instanceof MemberElement) {
			return StudioUIPlugin.getDefault().getImage("icons/StartState.png");
		} else if (element instanceof PropertyDefinition) {
			return getPropertyImage((PropertyDefinition) element);
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
	
	private Image getPropertyImage(PropertyDefinition element) {
		switch (element.getType()) {
		case BOOLEAN:
			return StudioUIPlugin.getDefault().getImage("icons/iconBoolean16.gif");

		case CONCEPT:
			return StudioUIPlugin.getDefault().getImage("icons/iconConcept16.gif");

		case CONCEPT_REFERENCE:
			return StudioUIPlugin.getDefault().getImage("icons/iconConceptRef16.gif");

		case DATE_TIME:
			return StudioUIPlugin.getDefault().getImage("icons/iconDate16.gif");

		case INTEGER:
			return StudioUIPlugin.getDefault().getImage("icons/iconInteger16.gif");

		case LONG:
			return StudioUIPlugin.getDefault().getImage("icons/iconLong16.gif");

		case DOUBLE:
			return StudioUIPlugin.getDefault().getImage("icons/iconReal16.gif");

		case STRING:
			return StudioUIPlugin.getDefault().getImage("icons/iconString16.gif");

		default:
			break;
		}
		return null;
	}

}
