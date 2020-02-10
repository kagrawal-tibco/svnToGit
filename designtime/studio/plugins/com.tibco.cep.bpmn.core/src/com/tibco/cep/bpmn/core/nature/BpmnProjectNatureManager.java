package com.tibco.cep.bpmn.core.nature;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;


/**
 * 
 * @author majha
 *
 */
public class BpmnProjectNatureManager {

	public static BpmnProjectNatureManager instance;

	private Set<IProject> bpmnProjects;
	private Set<IProject> checkedProjects;

	private BpmnProjectNatureManager() {
		bpmnProjects = new HashSet<IProject>();
		checkedProjects = new HashSet<IProject>();
		initialize();
	}

	synchronized public static final BpmnProjectNatureManager getInstance() {
		if (instance == null)
			instance = new BpmnProjectNatureManager();
		return instance;
	}
	
	private void initialize(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			checkForBpmnProject(projects[i]);
		}
	}
	
	public boolean isBpmnProject(IProject project) {
		boolean isBpmnProject = CommonUtil.isStudioProject(project)
				&& checkForBpmnProject(project);

		return isBpmnProject;
	}
	
	
	public void enableBpmnNature(IProject project, boolean enable) {
		if (project.isOpen()){
			synchronized (bpmnProjects) {
				if (enable && CommonUtil.isStudioProject(project))
					bpmnProjects.add(project);
				else 
					bpmnProjects.remove(project);
			}	
		}
	}
	
	/**
	 * Convenience method to return all projects in the workspace with the Studio nature
	 * @return
	 */
	public IProject[] getAllBpmnProjects() {
		synchronized (bpmnProjects) {
			IProject[] array = bpmnProjects.toArray(new IProject[bpmnProjects.size()]);
			return array;
		}
	}
	
	private boolean checkForBpmnProject(final IProject project) {
		if (!project.isOpen())
			return false;
		
		boolean bpmnNature = false;
		if (checkedProjects.contains(project)) {
			return bpmnProjects.contains(project);
		}
		try {
			synchronized (bpmnProjects) {
				bpmnNature = bpmnProjects.contains(project);
				if (!bpmnNature) {
					IResourceVisitor visitor = new IResourceVisitor() {

						@Override
						public boolean visit(IResource resource)
								throws CoreException {

							if (resource instanceof IContainer
									&& !bpmnProjects.contains(project)) {
								return true;
							} else if (resource instanceof IFile) {
								IFile file = (IFile) resource;
								if (BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION.equals(file
										.getFileExtension())) {
									enableBpmnNature(project, true);
								}
							}
							return false;
						}

					};

					project.accept(visitor);
					bpmnNature = bpmnProjects.contains(project);
					checkedProjects.add(project);
				}
			}

		} catch (Exception e) {
			BpmnCorePlugin.log(e);
		}
		return bpmnNature;
	}
}
