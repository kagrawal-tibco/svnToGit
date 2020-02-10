package com.tibco.cep.studio.core.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.DisplayUtils;

public class DisplayModelSearchParticipant implements ISearchParticipant {

	public DisplayModelSearchParticipant() {
		super();
	}

	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {
		
		SearchResult result = new SearchResult();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(project);
		if (resolvedElement instanceof EntityElement) {
			resolvedElement = ((EntityElement) resolvedElement).getEntity();
		}
		if (resolvedElement instanceof Entity) {
			searchDisplayModels((Entity) resolvedElement, index, nameToFind, monitor, result);
		}
		return result;

	}

	protected void searchDisplayModels(Entity resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		Entity root = resolvedElement;
		if (resolvedElement instanceof PropertyDefinition) {
			root = ((PropertyDefinition) resolvedElement).getOwner();
		}
		IFile[] displayModelFiles = DisplayUtils.getDisplayModelFilesForEntity(root, resolvedElement instanceof PropertyDefinition);
		monitor.beginTask("Searching display models", displayModelFiles.length);
		for (IFile dispModelFile : displayModelFiles) {
			processDisplayModelFile(dispModelFile, resolvedElement, result);
			monitor.worked(1);
		}

	}
	
	private void processDisplayModelFile(IFile dispModelFile,
			Entity entity, SearchResult result) {
		if (entity instanceof Concept || entity instanceof Event) {
			createAndAddMatch(dispModelFile, dispModelFile.getProject().getName(), "Display Model association", dispModelFile.getFullPath().toString(), result);
			return;
		}
		Properties props = new Properties();
		InputStream contents = null;
		try {
			contents = dispModelFile.getContents();
			props.load(contents);
			Set<Object> keySet = props.keySet();
			for (Object keyObj : keySet) {
				String key = (String) keyObj;
				String[] split = key.split("\\.");
				String propName = split[0];
				if (entity.getName().equals(propName)) {
					createAndAddMatch(dispModelFile, dispModelFile.getProject().getName(), "Property reference in display model", dispModelFile.getFullPath().toString(), result);
				}
			}
		} catch (IOException | CoreException | NullPointerException e) {
			StudioCorePlugin.log(e);
		} finally {
			if (contents != null) {
				try {
					contents.close();
				} catch (IOException e) {
				}
			}
		}
	}

	protected boolean isEqual(Object element, Object resolvedElement) {
		// need to fill this out, or add "equals" method to all possible comparables
		if (element == null) {
			return false;
		}
		if (element instanceof PropertyDefinition
				&& resolvedElement instanceof PropertyDefinition) {
			PropertyDefinition prop1 = (PropertyDefinition) element;
			PropertyDefinition prop2 = (PropertyDefinition) resolvedElement;
			if (prop1.getName().equals(prop2.getName()) 
					&& (prop1.getOwnerPath() != null && prop1.getOwnerPath().equals(prop2.getOwnerPath()))
					&& prop1.getType().equals(prop2.getType())) {
				return true; // TODO : is this sufficient?
			}
			return false;
		}
		if (resolvedElement instanceof EntityElement && element instanceof Entity) {
			resolvedElement = ((EntityElement)resolvedElement).getEntity();
		}
		if (element instanceof Entity && resolvedElement instanceof Entity) {
			return IndexUtils.areEqual((Entity)element, (Entity)resolvedElement);
		}
		return element.equals(resolvedElement);
	}

	private void createAndAddMatch(IFile file, String projectName,
			String label, String pathToEntity, SearchResult result) {
		NonEntityMatch match = SearchFactory.eINSTANCE.createNonEntityMatch();
		match.setProjectName(projectName);
		IPath path = file.getFullPath().removeFirstSegments(1);
		match.setFilePath(path.toString());
		match.setMatch(pathToEntity);
		ElementMatch elementMatch = SearchFactory.eINSTANCE.createElementMatch();
		elementMatch.setMatchedElement(match);
		elementMatch.setLabel(label);
		result.addExactMatch(elementMatch);
	}

}
