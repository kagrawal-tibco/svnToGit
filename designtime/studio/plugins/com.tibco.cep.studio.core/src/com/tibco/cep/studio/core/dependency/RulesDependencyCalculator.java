package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class RulesDependencyCalculator extends DefaultDependencyCalculator implements IDependencyCalculatorExtension {

	@Override
	public List<IResource> calculateDependencies(IResource resource) {
		if (!enablesFor(resource)) {
			return null;
		}
		List<Object> dependencies = new ArrayList<Object>();
		DesignerElement element = IndexUtils.getElement(resource);
		if (element instanceof RuleElement) {
			processScope(null, resource.getProject().getName(), ((RuleElement) element).getScope(), dependencies);
		}
		List<IResource> files = new ArrayList<IResource>();
		for (Object object : dependencies) {
			if (object instanceof IResource) {
				files.add((IResource) object);
			}
		}
		return files;
	}

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
		if (element instanceof RuleElement) {
			processScope(projectDir, projectDir.getName(), ((RuleElement) element).getScope(), dependencies);
		}
		List<File> files = new ArrayList<File>();
		for (Object object : dependencies) {
			if (object instanceof File) {
				files.add((File) object);
			}
		}

		return files;
	}

	public void processScope(File projectDir, String projectName, ScopeBlock scope, List<Object> dependencies) {
		if (scope == null) {
			return;
		}
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference elementReference : refs) {
			Object resolvedElement = ElementReferenceResolver.resolveElement(elementReference,
				ElementReferenceResolver.createResolutionContext(scope));
			// recursively process this rule
			if (resolvedElement instanceof SharedElement) {
				// TODO : Display message that there is a dependency on existing proj lib
				String path = ((SharedElement) resolvedElement).getArchivePath();
				logDependentProjLib(path);
				continue;
			} else if (resolvedElement instanceof TypeElement) {
				processAndAddDependency(projectDir, dependencies, projectName, (TypeElement) resolvedElement);
			} else if (resolvedElement instanceof Entity) {
				if (resolvedElement instanceof PropertyDefinition) {
					PropertyDefinition propDef = (PropertyDefinition) resolvedElement;
					resolvedElement = ((PropertyDefinition) resolvedElement).getOwner();
					if (resolvedElement == null && propDef.eContainer() instanceof Entity) {
						// check whether this is contained by a shared element
						Entity e = (Entity) propDef.eContainer();
						if (e.eContainer() instanceof SharedElement) {
							// TODO : Display message that there is a dependency on existing proj lib
							String path = ((SharedElement) e.eContainer()).getArchivePath();
							logDependentProjLib(path);
							continue;
						}
					}
				}
				IFile file = IndexUtils.getFile(projectName, (Entity) resolvedElement);
				addDependency(dependencies, file);
			}
		}
		if (scope.getChildScopeDefs().size() > 0) {
			EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
			for (ScopeBlock scopeBlock : childScopeDefs) {
				processScope(projectDir, projectName, scopeBlock, dependencies);
			}
		}
	}

	private void processAndAddDependency(List<Object> dependencies, String projectName, Entity entity) {
		if (entity == null) {
			return;
		}
		IFile file = IndexUtils.getFile(projectName, entity);
		if (file == null || !file.exists()) {
			if (entity.eIsProxy()) {
				URI proxyURI = ((EObjectImpl)entity).eProxyURI();
				String fileName = proxyURI.toFileString();
				addDependency(dependencies, new File(fileName));
			} else {
				String fileName = entity.eResource().getURI().toFileString();
				addDependency(dependencies, new File(fileName));
			}
		} else {
			addDependency(dependencies, file);
		}
	}

	private void processAndAddDependency(File projectDir, List<Object> dependencies, String projectName, TypeElement element) {
		if (element instanceof EntityElement) {
			processAndAddDependency(dependencies, projectName, ((EntityElement) element).getEntity());
			return;
		}
		IFile file = IndexUtils.getFile(projectName, element);
		if (file != null && file.getLocation() == null) {
			IPath relPath = file.getFullPath().removeFirstSegments(1);
			File depFile = new File(projectDir.getAbsolutePath()+File.separator+relPath.toString());
			addDependency(dependencies, depFile);
			return;
		}
		addDependency(dependencies, file);
	}

}
