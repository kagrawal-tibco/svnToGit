package com.tibco.cep.studio.core.index.jobs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.IndexModificationAdapter;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.update.IndexUpdateVisitor;
import com.tibco.cep.studio.core.index.update.IndexVerificationVisitor;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

public class LoadIndexJob extends IndexJob<DesignerProject> {

	private IProject fProject;

	public LoadIndexJob(IProject project) {
		super("Loading index for "+project.getName());
		this.fProject = project;
	}

	@Override
	public IStatus runJob(IProgressMonitor monitor) {
		printDebug("running load job for "+fProject.getName());
		if (!fProject.isOpen() || !CommonUtil.isStudioProject(fProject)) {
			return new IndexJobResult<DesignerProject>(Status.ERROR, StudioCorePlugin.PLUGIN_ID, 
					"Failed to load project "+fProject.getName()+ ": project is not associated with the studio nature", null);
		}
		String indexLocation = IndexUtils.getIndexLocation(fProject.getName());
		URI uri = URI.createFileURI(indexLocation);

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		try {
			resource.load(null);
		} catch (FileNotFoundException e) {
			// index has not yet been created, fall through and create it
			return new IndexJobResult<DesignerProject>(Status.INFO, StudioCorePlugin.PLUGIN_ID, 
					"Index file not found for "+fProject.getName(), null);
		} catch (IOException e) {
			e.printStackTrace();
			return new IndexJobResult<DesignerProject>(Status.ERROR, StudioCorePlugin.PLUGIN_ID, 
					"Failed to load project "+fProject.getName(),null, e);
		}

		if (resource.getContents().size() > 0) {
			DesignerProject index = (DesignerProject) resource.getContents().get(0);
			setProperty(getKey(), index);
			index.eAdapters().add(new IndexModificationAdapter(index));
			IndexVerificationVisitor verificationVisitor = new IndexVerificationVisitor(index.getName());
			index.accept(verificationVisitor);

			List<IResource> invalidResources = verificationVisitor.getInvalidResources();
			IndexUpdateVisitor updater = new IndexUpdateVisitor(index);

			HashMap<IFile,List<TypeElement>> duplicateElements = verificationVisitor.getDuplicateElements();
			if (duplicateElements.size() > 0) {
				Set<IFile> keySet = duplicateElements.keySet();
				for (IFile file : keySet) {
					List<? extends DesignerElement> list = duplicateElements.get(file);
					updater.removeDesignerElements(file, list, index);
					if (!invalidResources.contains(file)) {
						invalidResources.add(file);
					}
				}
			}
			
			if (invalidResources.size() > 0) {
				for (IResource invalidResource : invalidResources) {
					try {
						if (!(invalidResource instanceof IFile)) {
							continue;
						}
//						invalidResource.touch(new NullProgressMonitor()); // touch requires a scheduling rule, which we cannot guarantee.  Need to directly re-index file
						updater.reindexFile(IResourceDelta.CHANGED, (IFile) invalidResource, index, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return new IndexJobResult(Status.OK, StudioCorePlugin.PLUGIN_ID, 
					"Project loaded successfully", index);
		}
//		// index does not exist, create it
//		DesignerProject createResult = createIndex();
//
//		if (createResult != null) {
//			return new IndexJobResult(Status.OK, StudioCorePlugin.PLUGIN_ID, 
//					"Project loaded successfully", createResult);
//		}
		return new IndexJobResult(Status.ERROR, StudioCorePlugin.PLUGIN_ID, 
				"Failed to load project "+fProject.getName(), null);
	}

	@Override
	public boolean belongsTo(Object family) {
		return fProject.equals(family);
	}

	public IProject getProject() {
		return fProject;
	}

	private void printDebug(String message) {
		if (StudioCorePlugin.fDebug) {
			System.out.println(message);
		}
	}

}
