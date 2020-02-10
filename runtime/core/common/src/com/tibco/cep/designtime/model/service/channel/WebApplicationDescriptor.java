package com.tibco.cep.designtime.model.service.channel;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 31/8/11
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WebApplicationDescriptor {

    /**
     *
     * @return
     */
    String getContextURI();

    /**
     *
     * @return
     */
    String getWebAppResourcePath();
    
    void setContextURI(String contextURI);
    
    void setWebAppResourcePath(String webAppResourcePath);
    
}
