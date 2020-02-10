package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableDependencyCalculator extends RulesDependencyCalculator {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.dependency.RulesDependencyCalculator#calculateDependencies(org.eclipse.core.resources.IResource)
	 */
	@Override
	public List<IResource> calculateDependencies(IResource resource) {
		if (!enablesFor(resource)) {
			return null;
		}
		List<Object> dependencies = new ArrayList<Object>();
		DesignerElement element = IndexUtils.getElement(resource);
		if (element instanceof DecisionTableElement) {
			processVRF(resource.getProject().getName(), element, dependencies);
		}
		List<IResource> files = new ArrayList<IResource>();
		for (Object object : dependencies) {
			if (object instanceof IResource) {
				files.add((IResource) object);
			}
		}
		return files;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.dependency.RulesDependencyCalculator#calculateDependencies(java.io.File, java.io.File)
	 */
	@Override
	public List<File> calculateDependencies(File projectDir, File resource) {
		if (!enablesFor(resource)) {
			return null;
		}
		IPath projPath = new Path(projectDir.getAbsolutePath());
		IPath filePath = new Path(resource.getAbsolutePath());
		if (projPath.isPrefixOf(filePath)) {
			filePath = filePath.removeFirstSegments(projPath.segmentCount());
			filePath = filePath.setDevice(null);
			filePath = filePath.removeFileExtension();
		}
		List<Object> dependencies = new ArrayList<Object>();
		DesignerElement element = CommonIndexUtils.getElement(projectDir.getName(), filePath.toString());
		if (element instanceof DecisionTableElement) {
			processVRF(projectDir.getName(), element, dependencies);
		}
		List<File> files = new ArrayList<File>();
		for (Object object : dependencies) {
			if (object instanceof File) {
				files.add((File) object);
			}
		}
		return files;
	}

	
	/**
	 * @param projectName
	 * @param element
	 * @param dependencies
	 */
	private void processVRF(String projectName, DesignerElement element, List<Object> dependencies) {
		Implementation impl = (Implementation) ((DecisionTableElement) element).getImplementation();
		String vrfPath = impl.getImplements();
		RuleElement ruleElement = IndexUtils.getRuleElement(projectName, vrfPath, ELEMENT_TYPES.RULE_FUNCTION);
		IFile ruleFnFile = IndexUtils.getFile(projectName, ruleElement);
		addDependency(dependencies, ruleFnFile);
		processScope(null, projectName, ruleElement.getScope(), dependencies);
	}
}