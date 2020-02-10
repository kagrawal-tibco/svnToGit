package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.rule.Symbol;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 11:18:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableSymbol extends Symbol, MutationObservableContainer {


    /**
     * @param name a String which is a valid identifier name (not already in use in this Symbol's parent).
     * @return true iif the symbol was successfully renamed.
     */
    boolean setName(String name);


    /**
     * @param entityPath a String which is either an RDF Type, or an Entity path. If it is an entity path, it begins with Folder.FOLDER_SEPARATOR_CHAR.
     */
    void setType(String entityPath);
}
