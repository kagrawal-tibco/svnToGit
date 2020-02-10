package com.tibco.cep.designtime.model.service.channel.impl;

import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: aditya
 * Date: 31/8/11
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebApplicationDescriptorImpl implements WebApplicationDescriptor{

    private String contextURI;
	private String webAppResourcePath;

	public WebApplicationDescriptorImpl() {
		
	}
	 
    public WebApplicationDescriptorImpl(String contextURI, String webAppResourcePath) {
        this.contextURI = contextURI;
        this.webAppResourcePath = webAppResourcePath;
    }

    /**
     * @return
     */
    @Override
    public String getContextURI() {
        return contextURI;
    }

    /**
     * @return
     */
    @Override
    public String getWebAppResourcePath() {
        return webAppResourcePath;
    }
    
    @Override
    public void setContextURI(String contextURI) {
		this.contextURI = contextURI;
	}

    @Override
	public void setWebAppResourcePath(String webAppResourcePath) {
		this.webAppResourcePath = webAppResourcePath;
	}
}
