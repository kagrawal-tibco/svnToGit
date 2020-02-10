/**
 * 
 */
package com.tibco.cep.studio.core.repo.emf;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.Workspace;

/**
 * @author aathalye
 * @deprecated
 *
 */
public class EMFWorkspace implements Workspace<EMFProject> {
	
	/**
	 * This "shadowing" was needed because the base variable is not protected
	 */
	protected Map<String, EMFProject> projects = new HashMap<String, EMFProject>();

    private static Workspace singleton;

    static {
		System.setProperty("com.tibco.cep.repo.workspaceFactory", EMFWorkspace.class.getName());
	}


	/**
	 * 
	 */
	public EMFWorkspace() {
	}



    public static synchronized Workspace getInstance() {
        if (singleton == null) {
            String clazzName = System.getProperty("com.tibco.cep.repo.workspaceFactory");
            try {
                if (clazzName == null) {
                    clazzName = EMFWorkspace.class.getName();
                }
                final Class clazz = Class.forName(clazzName);
                singleton = (Workspace) clazz.newInstance();
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Invalid workspace factory provided. Switching to default implementation: "
                        + EMFWorkspace.class.getName());
                singleton = new EMFWorkspace();
            }
        }

        return singleton;
    }


	/**
	 * 
	 * @param projectName
	 * @param providers
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public EMFProject loadProject(List providers, String path)
			throws Exception {
		EMFProject project = this.getProject(path);
		if(project == null) {
			project =  new EMFProject(providers,path);
			project.load();
			projects.put(path, project);
		}
		return project;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEWorkspace#reloadProject(java.util.List, java.lang.String, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EMFProject reloadProject(List providers, String path, boolean isMutable)
			throws Exception {
		deleteProject(path);
		return loadProject(providers, path, isMutable);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEWorkspace#getProject(java.lang.String)
	 */
	@Override
	public EMFProject getProject(String name) {
		return projects.get(name);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#deleteProject(java.lang.String)
	 */
	@Override
	public EMFProject deleteProject(String name) {
		return projects.remove(name);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#getProjects()
	 */
	@Override
	public Collection<EMFProject> getProjects() {
		return projects.values();
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#loadProject(java.util.List, java.lang.String, boolean)
	 */
	@Override
	public EMFProject loadProject(List<ResourceProvider> providers, String path, boolean isMutable)
			throws Exception {
		EMFProject project = getProject(path);
        if (project == null) {
        	project = new EMFProject(providers, path);        	
            project.load();
            projects.put(path, project);
        }
        return project;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#loadProject(java.lang.String, boolean)
	 */
	@Override
	public EMFProject loadProject(String path, boolean isMutable) throws Exception {
		return loadProject(new ArrayList<ResourceProvider>(), path, isMutable);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#loadProject(java.lang.String)
	 */
	@Override
	public EMFProject loadProject(String path) throws Exception {
		 return loadProject(new ArrayList<ResourceProvider>(), path);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#newProject(java.lang.String)
	 */
	@Override
	public EMFProject newProject(String path) throws Exception {
		EMFProject project = new EMFProject(path);
        projects.put(path, project);
        return project;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#reloadProject(java.util.List, java.lang.String)
	 */
	@Override
	public EMFProject reloadProject(List<ResourceProvider> providers, String path) throws Exception {
		return reloadProject(providers, path, false);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#reloadProject(java.lang.String, boolean)
	 */
	@Override
	public EMFProject reloadProject(String path, boolean isMutable) throws Exception {
		return reloadProject(new ArrayList<ResourceProvider>(), path, isMutable);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.Workspace#reloadProject(java.lang.String)
	 */
	@Override
	public EMFProject reloadProject(String path) throws Exception {
		return reloadProject(new ArrayList<ResourceProvider>(), path);
	}	
	
}
