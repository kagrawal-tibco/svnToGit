package com.tibco.cep.runtime.service.management.process;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 21, 2009
 * Time: 10:30:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EngineChannelsMBean {

    //operations
    public void ReconnectChannels(String channelURI) throws Exception;
//    public void resumeChannels(String channelURI);        //behavior overridden by resumeDestinations
    public void ResumeDestinations(String channelURI, String destName) throws Exception;
//    public void suspendChannels(String channelURI);       //behavior overridden by suspendDestinations
    public void SuspendDestinations(String channelURI, String destName) throws Exception;

    //attributes
    public TabularDataSupport GetDestinations(String channelURI, String destName) throws Exception;
    public TabularDataSupport GetChannels(String channelURI) throws Exception;
    public TabularDataSupport GetSessionInputDestinations(String sessionName) throws Exception;
}
