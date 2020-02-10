package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.runtime.model.TypeManager;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 28, 2008
 * Time: 2:13:52 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Describes at generation time one of the entries in a runtime alias Map.
 *
 * @see AliasMapDescriptor
 */
public class AliasMapEntryDescriptor extends TupleInfoColumnDescriptorImpl {


    public AliasMapEntryDescriptor(String name, TypeInfo typeInfo, ModelContext ctx,
                                   TupleInfoDescriptor tupleInfoDescriptor, TypeManager typeManager)
            throws Exception {
        super(name, typeInfo, ctx, tupleInfoDescriptor, typeManager);
    }


    public AliasMapEntryDescriptor(String name, TypeInfo typeInfo, String runtimeClassName, ModelContext ctx,
                                   TupleInfoDescriptor tupleInfoDescriptor)
            throws Exception {
        super(name, typeInfo, runtimeClassName, ctx, tupleInfoDescriptor);
    }


    public AliasMapEntryDescriptor(String name, TypeInfo typeInfo, String runtimeClassName, String runtimeClassGetter,
                                   ModelContext ctx, TupleInfoDescriptor tupleInfoDescriptor)
            throws Exception {
        super(name, typeInfo, runtimeClassName, runtimeClassGetter, ctx, tupleInfoDescriptor);
    }
}
