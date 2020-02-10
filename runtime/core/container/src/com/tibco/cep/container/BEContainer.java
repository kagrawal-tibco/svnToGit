package com.tibco.cep.container;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 4, 2004
 * Time: 11:27:23 AM
 * To change this template use Options | File Templates.
 */
public abstract class BEContainer {


    /**
     * Used by TIBCO Administrator to find on which containers an application can be deployed.
     */
    public static final String CONTAINER_NAME;
    /**
     * Used by TIBCO Administrator to find on which containers an application can be deployed.
     */
    public static final String CONTAINER_VERSION;
    /**
     * For information purposes.
     */
    public static final String CONTAINER_BUILD_ID;
    public static final String CONTAINER_COMPONENT_TYPE;

    static {
        String version = cep_containerVersion.version;
        if ((null != version) && !version.matches("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+")) {
            if (version.matches("[0-9]+\\.[0-9]+\\.[0-9]+")) {
                version += ".0";
            } else if (version.matches("[0-9]+\\.[0-9]+")) {
                version += ".0.0";
            } else if (version.matches("[0-9]+")) {
                version += ".0.0.0";
            } else {
                version = null;
            }//else
        }//if
//        if (null == version) {
//            ResourceBundle bundle = ResourceManager.getInstance().addResourceBundle(BEContainer.class.getName(), null);
//            version = bundle.getString("container.version");
//        }//if
        CONTAINER_VERSION = version;
        CONTAINER_BUILD_ID = cep_containerVersion.build;
        CONTAINER_COMPONENT_TYPE = cep_containerVersion.getComponent();
        CONTAINER_NAME = cep_containerVersion.container_id;
    }//static
}//class
