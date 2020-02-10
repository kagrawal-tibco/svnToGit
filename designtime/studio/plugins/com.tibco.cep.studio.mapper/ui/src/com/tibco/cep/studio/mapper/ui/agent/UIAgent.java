package com.tibco.cep.studio.mapper.ui.agent;

import java.awt.Font;
import java.awt.Frame;

import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.tns.cache.TnsCache;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 21, 2009
 * Time: 10:06:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UIAgent {

    /**
     * Refactored from DesignerApp - just return the System Application Font
     * @return
     */
    Font getAppFont();

    /**
     * Refactored from DesignerApp - return the System Script Font
     * @return
     */
    Font getScriptFont();

    /**
     * Provide the value back from System for this prefernce
     * @param name
     * @return
     */
    String getUserPreference(String name);

    /**
     * Provide the value back from System for this prefernce
     * @param name
     * @return
     */
    String getUserPreference(String name, String defaultValue);

    /**
     * Set the Preference.
     * @param name
     * @param val
     */
    void setUserPreference(String name, String val);

    /**
     * Refactored from RepoAgent - Should return an instanceof FunctionResolver
     * @return
     */
    FunctionResolver getFunctionResolver();


    /**
     * Refactored from a bunch utility calls.
     * Typically return an instanceof the DefaultStyleSheetResolver.
     * @return
     */
    StylesheetResolver getStyleSheetResolver();

    /**
     * A default javax.swing.Frame - Refactored from DesignerDocument
     * @return
     */
    Frame getFrame();

    /**
     * Refactored from RepoAgent - Given a relative location, find the absolute location
     * RYANTODO
     * @param location
     * @return
     */
    String getAbsoluteURIFromProjectRelativeURI(String location);

    /**
     * The namespace cache.
     * @return
     */
    TnsCache getTnsCache();

    /**
     * Refactored from RepoAgent - Given a Fullpath, get the relative location.
     * @param location
     * @return
     */
    String getProjectRelativeURIFromAbsoluteURI(String location);

    String getRootProjectPath();
    
    String getProjectName();
    
    SmNamespaceProvider getSmNamespaceProvider();
    SmComponentProviderEx getSmComponentProviderEx();

    /**
     * Given the project relative location, opens the resource in Studio
     * @param location
     */
	void openResource(String location);

}
