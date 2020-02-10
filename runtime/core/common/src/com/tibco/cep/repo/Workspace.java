package com.tibco.cep.repo;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 5, 2006
 * Time: 8:47:00 PM
 * To change this template use File | Settings | File Templates.
 * @deprecated
 */
public interface Workspace<P extends Project> {
    P newProject(String path) throws Exception;

    P loadProject(String path) throws Exception;

    P loadProject(String path, boolean isMutable) throws Exception;

    P loadProject(List<ResourceProvider> providers, String path) throws Exception;

    P loadProject(List<ResourceProvider> providers, String path, boolean isMutable) throws Exception;

    P reloadProject(String path) throws Exception;

    P reloadProject(String path, boolean isMutable) throws Exception;

    P reloadProject(List<ResourceProvider> providers, String path) throws Exception;

    P reloadProject(List<ResourceProvider> providers, String path, boolean isMutable) throws Exception;

    Collection<P> getProjects();

    P getProject(String name);

    P deleteProject(String name);
}
