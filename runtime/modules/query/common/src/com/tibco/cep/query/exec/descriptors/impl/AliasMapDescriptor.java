package com.tibco.cep.query.exec.descriptors.impl;

import org.antlr.stringtemplate.StringTemplate;

import com.tibco.cep.query.exec.codegen.QueryTemplateRegistry;
import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.util.Escaper;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.utils.TypeHelper;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 28, 2008
 * Time: 2:23:54 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Describes at generation time a runtime alias Map.
 *
 * @see AliasMapEntryDescriptor
 */
public class AliasMapDescriptor extends TupleInfoDescriptorImpl {


    protected static final QueryTemplateRegistry TEMPLATE_REGISTRY = QueryTemplateRegistry.getInstance();

    protected final StringTemplate TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP;


    public AliasMapDescriptor(ModelContext ctx) {
        super(ctx);

        try {
            TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getTupleFromAliasMap");
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }


    public String getColumnGetterCode(CharSequence baseMapCode, TupleInfoColumnDescriptor column) {
        TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP.reset();
        TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP.setAttribute("map", baseMapCode);
        TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP.setAttribute("valueClassName", TypeHelper.getBoxedName(column.getClassName()));
        TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP.setAttribute("escapedKey", Escaper.toJavaStringSource(column.getName()));
        return TEMPLATE_FOR_GET_TUPLE_FROM_ALIAS_MAP.toString();
    }


}
