package com.tibco.cep.studio.ui.search;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.text.Match;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class StudioElementSearchQuery implements IStudioSearchQuery {

	private StudioElementSearchResult fResult;
	private EObject fReference;
	private String fProjectName;

	public StudioElementSearchQuery(String projectName, EObject reference) {
		this.fProjectName = projectName;
		this.fReference = reference;
	}

	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		if (fResult != null) {
			fResult.removeAll();
		}
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask("Searching for references", 5);
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(fProjectName);
		if (project == null || !project.isAccessible()) {
			return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, "Project '"+fProjectName+"' is no longer accessible.  Search cannot proceed.");
		}
		
		monitor.subTask("Resolving selection...");
		Object element = null;
		String elementName = getElementName();
		if (fReference instanceof VariableDefinition) {
			element = fReference;
		} else if (fReference instanceof ElementReference) {
			element = ElementReferenceResolver.resolveElement((ElementReference) fReference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope((ElementReference) fReference)));
		} else {
			element = fReference;
		}
		monitor.worked(1);
		if (element != null) {
			System.out.println("el: " + element);
			System.out.println("name: " + elementName);
			SearchResult result = SearchUtils.searchIndex(element, fProjectName, elementName, new SubProgressMonitor(monitor, 3));
			monitor.subTask("Collecting results...");
			for (EObject object : result.getExactMatches()) {
				Match match = getElementMatch(object);
				NestedStudioElementMatch nestedMatch = new NestedStudioElementMatch((IStudioMatch) match, object);
				((IStudioMatch) match).setNestedMatch(nestedMatch);
				fResult.addMatch(match);
			}
			for (EObject object : result.getInexactMatches()) {
				Match match = getElementMatch(object);
				NestedStudioElementMatch nestedMatch = new NestedStudioElementMatch((IStudioMatch) match, object);
				((IStudioMatch) match).setNestedMatch(nestedMatch);
				nestedMatch.setExact(false);
				fResult.addMatch(match);
			}
			monitor.done();
		}

		return Status.OK_STATUS;
	}

	private String getElementName() {
		if (fReference instanceof VariableDefinition) {
			return ((VariableDefinition) fReference).getName();
		} else if (fReference instanceof ElementReference) {
			return ((ElementReference) fReference).getName();
		} else if (fReference instanceof DesignerElement) {
			return ((DesignerElement) fReference).getName();
		} else if (fReference instanceof Entity) {
			return ((Entity) fReference).getName();
		}
		return "";
	}

	private Match getElementMatch(EObject object) {
		Resource resource = object.eResource();
		IFile file = null;
		if (resource != null) {
			IProject project = getProject((EObject)object);
			if (object instanceof Entity) {
				file = IndexUtils.getFile(project.getName(), (Entity)object);
			} else if (object instanceof ElementReference) {
				object = IndexUtils.getVariableContext(object);
			} 
			if (object instanceof TypeElement) {
				file = IndexUtils.getFile(project.getName(), (TypeElement)object);
			}
		}
		else {
			file = SearchUtils.getFile(object);
		}
		if (object instanceof ElementMatch) {
			object = ((ElementMatch) object).getMatchedElement();
		}
		if (object instanceof SharedElement) {
			return new StudioSharedElementMatch((SharedElement) object, getOffset(object), getLength(object));
		}
		return new StudioFileMatch(file, getOffset(object), getLength(object));
	}

	private IProject getProject(EObject object) {
		if (object instanceof ElementReference) {
			object = IndexUtils.getRootContainer(object);
		}
		if (object instanceof DesignerProject) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(((DesignerProject) object).getName());
		}
		if (object instanceof EntityElement) {
			object = ((EntityElement) object).getEntity();
		}
		if (object instanceof Entity) {
			Entity entity = (Entity) object;
			return ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
		}
		if (object instanceof RuleElement) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(((RuleElement) object).getIndexName());
		}
		return null;
	}

	private int getOffset(EObject object) {
		if (object instanceof ElementReference) {
			ElementReference element = (ElementReference) object;
			return element.getOffset();
		}
		if (object instanceof VariableDefinition) {
			VariableDefinition element = (VariableDefinition) object;
			return element.getOffset();
		}
		return 0;
	}

	private int getLength(EObject object) {
		if (object instanceof ElementReference) {
			ElementReference element = (ElementReference) object;
			return element.getLength();
		}
		if (object instanceof VariableDefinition) {
			VariableDefinition element = (VariableDefinition) object;
			return element.getLength();
		}
		return 0;
	}

	public boolean canRerun() {
		return true;
	}

	public boolean canRunInBackground() {
		return true;
	}

	public String getLabel() {
		return "'"+getElementName()+"'";
	}

	public ISearchResult getSearchResult() {
		if (fResult == null) {
			fResult = new StudioElementSearchResult(this);
		}
		return fResult;
	}


}
