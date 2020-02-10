package com.tibco.cep.repo;



import java.io.InputStream;

import com.tibco.objectrepo.vfile.VFileStream;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 18, 2006
 * Time: 11:38:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceProvider {
    int STOP = 1;
    int CONTINUE = 2;
    int SUCCESS = 4;
    int FAILED = 8;

    int SUCCESS_STOP = 5;
    int SUCCESS_CONTINUE = 6;
    int FAILED_STOP = 9;
    int FAILED_CONTINUE = 10;

    void init() throws Exception;

    /**
     *
     * @param uri the fullpath identifier of the resource.
     * @return boolean if it supports deserializing or not.
     */
    boolean supportsResource(String uri);

    /**
     *
     * @param uri Uri of the resource.
     * @param is InputStream of the resource,
     * @param project The context
     * @param stream
     * @return int, Status code, indicating whether the project should continue with other providers for deserializing
     * @throws Exception
     */
    int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception;

    


}
