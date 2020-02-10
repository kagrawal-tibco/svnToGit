package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSchemaComponent;

/**
 * @
 *  
 */
public class SynXSDAnnotation implements ISynXSDAnnotation {

    
    private ISynXSDSchemaComponent container;
    
    private String appInfo = "";

    private String documentation = "";

    /**
     * @return Returns the appInfo.
     */
    public String getAppInfo() {
        return appInfo;
    }

    /**
     * @param appInfo
     *            The appInfo to set.
     */
    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    /**
     * @return Returns the documentation.
     */
    public String getDocumentation() {
        return documentation;
    }

    /**
     * @param documentation
     *            The documentation to set.
     */
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public Object cloneThis()  {
        SynXSDAnnotation sClone = new SynXSDAnnotation();
        sClone.setAppInfo(getAppInfo());
        sClone.setDocumentation(getDocumentation());
        return sClone;
    }

    public String getName() {
        return null;
    }

    public String getTargetNameSpace() {
        return null;
    }
    /**
     * @return Returns the container.
     */
    public ISynXSDSchemaComponent getContainer() {
        return container;
    }
    /**
     * @param container The container to set.
     */
    public void setContainer(ISynXSDSchemaComponent container) {
        this.container = container;
    }
}
