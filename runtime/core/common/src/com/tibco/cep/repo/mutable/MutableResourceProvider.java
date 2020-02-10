package com.tibco.cep.repo.mutable;

import java.util.List;

import com.tibco.cep.repo.ResourceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:42:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableResourceProvider extends ResourceProvider {

    boolean isDirty();

    void clearDirty();

    List getChangeList();

}
