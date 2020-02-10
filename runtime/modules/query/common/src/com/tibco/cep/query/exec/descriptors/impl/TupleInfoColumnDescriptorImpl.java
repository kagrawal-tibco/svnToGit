package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.runtime.model.TypeManager;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 16, 2008
 * Time: 6:05:15 PM
 */

/**
 * Describes at generation time a column in a runtime TupleInfo.
 *
 * @see TupleInfoDescriptor
 */
public class TupleInfoColumnDescriptorImpl implements TupleInfoColumnDescriptor {


    protected String classGetter;
    protected String className;
    protected ModelContext modelContext;
    protected String name;
    protected TupleInfoDescriptor tupleInfoDescriptor;
    protected TypeInfo typeInfo;


    /**
     * @param name                String name of the column.
     * @param typeInfo            TypeInfo describing the type of the column value.
     * @param runtimeClassName    String name of the type of the column at runtime.
     * @param ctx                 ModelContext associated with the column if any, else null.
     * @param tupleInfoDescriptor TupleInfoDescriptorImpl associated with the column value if it is a tuple, else null.
     * @throws Exception upon problem
     */
    public TupleInfoColumnDescriptorImpl(String name, TypeInfo typeInfo, String runtimeClassName, ModelContext ctx,
                                         TupleInfoDescriptor tupleInfoDescriptor)
            throws Exception {
        this(name, typeInfo, runtimeClassName, runtimeClassName + ".class", ctx, tupleInfoDescriptor);
    }


    /**
     * @param name                String name of the column.
     * @param typeInfo            TypeInfo describing the type of the column value.
     * @param runtimeClassName    String name of the type of the column at runtime.
     * @param runtimeClassGetter  String source code to get the runtime class of the column value.
     * @param ctx                 ModelContext associated with the column if any, else null.
     * @param tupleInfoDescriptor TupleInfoDescriptorImpl associated with the column value if it is a tuple, else null.
     * @throws Exception upon problem
     */
    public TupleInfoColumnDescriptorImpl(String name, TypeInfo typeInfo, String runtimeClassName,
                                         String runtimeClassGetter, ModelContext ctx,
                                         TupleInfoDescriptor tupleInfoDescriptor)
            throws Exception {
        if ((null == name) || (null == typeInfo) || "".equals(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.typeInfo = typeInfo;
        this.classGetter = runtimeClassGetter;
        this.className = runtimeClassName;
        this.modelContext = ctx;
        this.tupleInfoDescriptor = tupleInfoDescriptor;
    }


    /**
     * @param name                String name of the column.
     * @param typeInfo            TypeInfo of the column value.
     * @param ctx                 ModelContext associated with the column if any, else null.
     * @param tupleInfoDescriptor TupleInfoDescriptorImpl associated with the column value if it is a tuple, else null.
     * @param tm                  TypeManager used to find the runtime type of the column value.
     * @throws Exception upon problem
     */
    public TupleInfoColumnDescriptorImpl(String name, TypeInfo typeInfo, ModelContext ctx,
                                         TupleInfoDescriptor tupleInfoDescriptor, TypeManager tm)
            throws Exception {
        this(name, typeInfo, typeInfo.getRuntimeClass(tm).getCanonicalName(), ctx, tupleInfoDescriptor);
    }


    /**
     * Gets the source code to get the runtime class of the column value.
     * @return String source code to get the runtime class of the column value.
     */
    public String getClassGetter() {
        return this.classGetter;
    }


    /**
     * Gets the String name of the runtime class of the column value.
     * @return String name of the runtime class of the column value.
     */
    public String getClassName() {
//        String className = this.className;
//        if(className.startsWith("[")) {
//            className = className.substring("[".length());
//        }
//        if (className.startsWith("L")) {
//            className = className.substring("L".length(), className.length() - ";".length());
//        }
        return className;
    }


    /**
     * Gets the ModelContext associated to the column.
     * @return ModelContext associated to the column.
     */
    public ModelContext getModelContext() {
        return this.modelContext;
    }


    /**
     * Gets the String name of the column.
     * @return String name of the column.
     */
    public String getName() {
        return this.name;
    }


    /**
     * Gets the TupleInfoDescriptor describing the contained TupleInfo, if any, else null.
     * @return TupleInfoDescriptor describing the contained TupleInfo, if any, else null.
     */
    public TupleInfoDescriptor getTupleInfoDescriptor() {
        return this.tupleInfoDescriptor;
    }


    /**
     * Gets the TypeInfo describing the runtime type of the column value.
     * @return TypeInfo describing the runtime type of the column value.
     */
    public TypeInfo getTypeInfo() {
        return this.typeInfo;
    }


}
