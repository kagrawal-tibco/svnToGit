package com.tibco.cep.query.exec.descriptors;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 8, 2008
 * Time: 8:18:41 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Describes at generation time a column in a runtime TupleInfo.
 */
public interface TupleInfoColumnDescriptor {

    String getClassGetter();

    String getClassName();

    ModelContext getModelContext();

    String getName();

    TupleInfoDescriptor getTupleInfoDescriptor();

    TypeInfo getTypeInfo();

}
