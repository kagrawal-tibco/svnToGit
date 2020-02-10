package com.tibco.cep.repo.mutable;

import com.tibco.cep.repo.Project;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:43:23 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableProject extends Project {

    void save() throws Exception;

    void saveAs(String projectPath) throws Exception;

    boolean isDirty();
}
