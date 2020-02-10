package com.tibco.cep.repo.provider;


import java.util.List;

import com.tibco.cep.repo.ResourceProvider;


/*
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 22, 2006
 * Time: 12:17:02 PM
 */


public interface SchemaResourceProvider extends ResourceProvider {


    public void clearDirty();


    public List getChangeList();


    public boolean isDirty();

}
