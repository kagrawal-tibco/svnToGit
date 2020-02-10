package com.tibco.cep.repo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.tibco.cep.repo.mutable.MutableBEProject;
import com.tibco.cep.repo.mutable.MutableProject;
import com.tibco.objectrepo.vfile.VFileFactory;

/**
 * This class provides helper methods to create, load, and delete projects.
 * @see Project
 *
 * A Sample code for use is
 *  BEWorkspace space = BEWorkspace.getInstance();
 *  Project project = space.newProject(MyResourceProviders, "C:/Projects")
 * @see ResourceProvider
 * @deprecated
 */
public class BEWorkspace implements Workspace {
    static com.tibco.cep.repo.Workspace gWorkspace = null;
    HashMap projects = new HashMap();

    static {
        System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl");
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
    }

    public static synchronized com.tibco.cep.repo.Workspace getInstance() {
        if (gWorkspace != null) return gWorkspace;

        String clazzName = System.getProperty("com.tibco.cep.repo.workspaceFactory");

        try {
            if (clazzName == null) clazzName = BEWorkspace.class.getName();
            Class clazz = Class.forName(clazzName);
            gWorkspace = (Workspace)clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid Workspace Factory provided... switching to default implementation :" + BEWorkspace.class.getName());
            gWorkspace = new BEWorkspace();
        }
        return gWorkspace;
    }


    /**
     * A New Project is always Mutable.
     * @param path
     * @return
     * @throws Exception
     */
    public MutableProject newProject(String path) throws Exception
    {
        MutableProject project = (MutableProject)new MutableBEProject(path).createProject();
        projects.put(path, project);
        return project;
    }

    public Project loadProject(String path) throws Exception {
        return loadProject(new ArrayList(), path);

    }

    public Project loadProject(String path, boolean isMutable) throws Exception {
        return loadProject(new ArrayList(), path, isMutable);
    }

    public Project loadProject(List providers, String path) throws Exception {

        return loadProject(providers, path, false);

    }

    /* (non-Javadoc)
     * @see com.tibco.cep.repo.Workspace#loadProject(java.util.List, java.lang.String, boolean)
     */
    public Project loadProject(List providers, String path, boolean isMutable) throws Exception
    {
        Project project = getProject(path);
        if (project == null) {
            if (isMutable) {
                project = new MutableBEProject(providers, path);
            }
            else {
                project = new BEProject(providers, path);
            }
            project.load();
            projects.put(path, project);
        }
        return project;
    }


    /**
     * Loads project from a vFileFactory. This is always a ReadOnly Project
     * @param factory
     * @return
     * @throws Exception
     */
    public Project loadProject(VFileFactory factory) throws Exception {
        Project project = new BEProject(factory);
        project.load();
        return project;
    }


    public Project reloadProject(String path) throws Exception {
        return reloadProject(new ArrayList(), path);
    }

    public Project reloadProject(String path, boolean isMutable) throws Exception {
         return reloadProject(new ArrayList(), path, isMutable);
     }


    public Project reloadProject(List providers, String path) throws Exception {
        return reloadProject(providers, path, false);

    }

    /* (non-Javadoc)
     * @see com.tibco.cep.repo.Workspace#reloadProject(java.util.List, java.lang.String, boolean)
     */
    public Project reloadProject(List providers, String path, boolean isMutable) throws Exception {

        Project project;
        if (isMutable) {
            project = new MutableBEProject(providers, path);
        }
        else {
            project = new BEProject(providers, path);
        }
        project.load();
        projects.put(path, project);

        return project;
    }


    public Collection getProjects() {
        return projects.values();
    }


    public Project getProject(String name) {
        return (Project)projects.get(name);
    }

    public Project deleteProject(String name) {
        return (Project)projects.remove(name);
    }



}
