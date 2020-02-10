package com.tibco.be.model.functions;

import java.util.Iterator;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 4:34:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FunctionsCategory {

    /**
     *
     * @return
     */
    ExpandedName getName();

    /**
     *
     * @param predicate
     */
    void registerPredicate(Predicate predicate) throws Exception;


    /**
     *
     * @param functionCategory
     * @throws Exception
     */
    void registerSubCategory(FunctionsCategory functionCategory) throws Exception;


    /**
     *
     * @param predicate
     * @throws Exception
     */
    void deregisterPredicate(Predicate predicate, boolean deep) throws Exception;

    /**
     *
     * @param functionCategory
     * @throws Exception
     */
    void deregisterSubCategory(FunctionsCategory functionCategory, boolean deep) throws Exception;


    /**
     *
     * @param functionsCategoryName
     * @return
     * @throws Exception
     */
    FunctionsCategory getSubCategory(ExpandedName functionsCategoryName) throws Exception;

    /**
     *
     * @param predicateName
     * @return
     * @throws Exception
     */
    Predicate getPredicate(ExpandedName predicateName, boolean deep) throws Exception;

    /**
     * All Nodes at this level
     * @return
     */
    Iterator<?> all();

    /**
     *
     * @return All Sub Categories at this level
     */
    Iterator<FunctionsCategory> allSubCategories();

    /**
     *
     * @return All Functions at this level
     */
    Iterator<Predicate> allFunctions();
    /**
     *
     * @return
     */
    int size();

    /**
     *
     */
    void prepare();

    /**
     * 
     * @param functionName
     * @param byPassPath
     * @return
     */
    Object lookup (String functionName, boolean byPassPath);
    
    /**
     * @param visitor
     * @return
     */
    void accept(FunctionsCatalogVisitor visitor);
}
