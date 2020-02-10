package com.tibco.cep.studio.ui.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class SpecificIndexLabelProvider extends LabelProvider {

	private static final GeneralIndexLabelProvider fGeneralProvider = new GeneralIndexLabelProvider();
	public String getText(Object obj) {
		StringBuffer buffer = new StringBuffer();
		if (obj instanceof DesignerProject) {
			buffer.append(((DesignerProject)obj).getName());
			buffer.append(" -- Project");
			return buffer.toString();
		}
		if (obj instanceof PropertyDefinition) {
			PropertyDefinition prop = (PropertyDefinition) obj;
			buffer.append(prop.getName());
			buffer.append(" property definition for ");
			EObject container = prop.eContainer();
			if (container instanceof Concept) {
				buffer.append("concept ");
				buffer.append(((Concept) container).getOwnerProjectName());
				buffer.append(((Concept) container).getFolder());
				buffer.append(((Concept) container).getName());
			} else if (container instanceof Event) {
				buffer.append("event ");
				buffer.append(((Event) container).getOwnerProjectName());
				buffer.append(((Event) container).getFolder());
				buffer.append(((Event) container).getName());
			}
			return buffer.toString();
		}
		if (obj instanceof Entity) {
			buffer.append(((Entity)obj).getName());
			buffer.append(" [");
			buffer.append(((Entity) obj).getOwnerProjectName());
			buffer.append("]");
			buffer.append(" -- ");
			buffer.append(((Entity) obj).getFolder());
			return buffer.toString();
		}
		if (obj instanceof ArchiveResource) {
			buffer.append(((ArchiveResource)obj).getName());
			return buffer.toString();
		}
		if (obj instanceof Implementation) {
			buffer.append(((Implementation)obj).getName());
			buffer.append(" -- ");
			buffer.append(((Implementation) obj).getFolder());
			return buffer.toString();
		}
		if (obj instanceof ScopeBlock) {
			buffer.append("Scope ");
			buffer.append(((ScopeBlock) obj).getType());
			return buffer.toString();
		}
		if (obj instanceof CompilableScopeEntry) {
			buffer.append("Compilable Scope Entry for ");
			buffer.append(((CompilableScopeEntry)obj).getRuleName());
			return buffer.toString();
		}
		if (obj instanceof VariableDefinition) {
			VariableDefinition def = (VariableDefinition) obj;
			buffer.append(def.getName());
			if (def instanceof LocalVariableDef) {
				buffer.append(" -- Local variable in ");
			} else {
				buffer.append(" -- Parameter for ");
			}
			DesignerElement parentElement = IndexUtils.getVariableContext((EObject) obj);
			if (parentElement != null) {
				if (parentElement instanceof RuleElement) {
					buffer.append(((RuleElement) parentElement).getIndexName());
					buffer.append(((RuleElement) parentElement).getFolder());
				}
				buffer.append(parentElement.getName());
			}
			buffer.append(", Type: ");
			buffer.append(def.getType());
			buffer.append(" [");
			buffer.append(def.getOffset());
			buffer.append(",");
			buffer.append(def.getLength());
			buffer.append("]");
			return buffer.toString();
		}
		if (obj instanceof ElementReference) {
			ElementReference def = (ElementReference) obj;
			DesignerElement parentElement = IndexUtils.getVariableContext((EObject) obj);
			buffer.append(def.getName());
			buffer.append(" -- reference in ");
			if (parentElement != null) {
				buffer.append(parentElement.getName());
			}
			buffer.append(" [");
			buffer.append(def.getOffset());
			buffer.append(",");
			buffer.append(def.getLength());
			buffer.append("]");

			return buffer.toString();
		}
		if (obj instanceof DesignerElement) {
			buffer.append(((DesignerElement)obj).getName());
		}
		if (obj instanceof EntityElement) {
			buffer.append(" -- (path: ");
			buffer.append(((EntityElement)obj).getEntity().getOwnerProjectName());
			buffer.append(((EntityElement)obj).getFolder());
			buffer.append(") -- (type ");
			buffer.append(((EntityElement)obj).getElementType());
			buffer.append(")");
		}
		if (obj instanceof ArchiveElement) {
			buffer.append(" -- (path: ");
			DesignerProject owner = getOwner((EObject) obj);
			if (owner != null) {
				buffer.append(owner.getName());
			}
			buffer.append(((ArchiveElement)obj).getFolder());
			buffer.append(") -- (Archive)");
		}
		if (obj instanceof DecisionTableElement) {
			buffer.append(" -- (path: ");
			DesignerProject owner = getOwner((EObject) obj);
			if (owner != null) {
				buffer.append(owner.getName());
			}
			buffer.append(((DecisionTableElement)obj).getFolder());
			buffer.append(") -- (Implementation)");
		}
		if (obj instanceof RuleElement) {
			buffer.append(" -- (path: ");
			DesignerProject owner = getOwner((EObject) obj);
			if (owner != null) {
				buffer.append(owner.getName());
			}
			buffer.append(((RuleElement)obj).getFolder());
			buffer.append(") -- (type ");
			buffer.append(((RuleElement)obj).getElementType());
			buffer.append(")");
		}
		if (obj instanceof ElementContainer) {
			buffer.append(" (");
			buffer.append(((ElementContainer) obj).getEntries().size());
			buffer.append(" entries");
			buffer.append(")");
		}
		return buffer.toString();
	}
	
	private DesignerProject getOwner(EObject obj) {
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof DesignerProject) {
				return (DesignerProject) obj;
			}
		}
		return null;
	}

	public Image getImage(Object element) {
		return fGeneralProvider.getImage(element);
	}
}
