package com.tibco.cep.designtime.model.rule;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 11:19:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Symbol {


    Domain getDomain();


    String getName();


    Symbols getSymbols();


    /**
     * @return either an RDF Type, or an Entity path. If it is an entity path, it begins with Folder.FOLDER_SEPARATOR_CHAR.
     */
    String getType();


    String getTypeWithExtension();


    /**
     * @return String extension of the type if any, including the '.', else "".
     */
    String getTypeExtension();
    
    /**
     * @return boolean true if this Symbol is an array type (i.e. has a trailing '[]'), false otherwise
     */
    boolean isArray();

}
