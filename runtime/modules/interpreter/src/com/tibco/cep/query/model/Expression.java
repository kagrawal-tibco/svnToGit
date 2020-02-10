/**
 * 
 */
package com.tibco.cep.query.model;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.model.validation.Resolvable;

/**
 * @author pdhar
 *
 */
public interface Expression extends QueryContext, TypedContext, Resolvable {

    public static long validReturnTypeMasks=  TypeInfoImpl.TYPE_VOID
                                            | TypeInfoImpl.TYPE_ATOM
                                            | TypeInfoImpl.TYPE_ARRAY
                                            | TypeInfoImpl.TYPE_OBJECT;




    /**
     * @return boolean true if the expression contains property reference
     */
    boolean hasPropertyReference() throws Exception;

    /**
     * @return boolean true if the expression contains expression reference
     */
    boolean hasAttributeReference()throws Exception;


    /**
     * @return boolean true if the expression contain either Rule function,aggregate function or Catalog function reference
     */
    boolean hasFunctionReference() throws Exception;


    /**
     * @return boolean true if the expression contains RuleFunction reference
     */
    boolean hasRuleFunctionReference() throws Exception;

    /**
     * @return boolean true if the expression contains Catalog Function reference
     */
    boolean hasCatalogFunctionReference() throws Exception;

    /**
     * @return boolean true if the expression contain aggregate function reference
     */
    boolean hasAggregateFunctionReference() throws Exception;


    
    ModelContext getIdentifiedContext() throws ResolveException;

    String getExpressionText();
}
