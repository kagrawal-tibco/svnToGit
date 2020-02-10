package com.tibco.cep.runtime.service.ft;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 3, 2006
 * Time: 1:43:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTNodeController {

    public String getControllerName();

    public void nodeStarted() throws Exception;

    public void initAll() throws Exception;

    public void initChannels() throws Exception;

    public void setInactive() throws Exception;

    public void startChannels() throws Exception;

    public void stopChannels() throws Exception;

    public void waitForRuleCycles() throws Exception;

    public void stopNode() throws Exception;

    public void initRuleSessions() throws Exception;

    public void waitForActivation() throws Exception;

//    public void initRuleAdministrator();
    public void suspendRTC() throws Exception;

    public void activateRTC() throws Exception;

    public byte[] getNodeArchiveDigest() throws Exception;

    public void shutdown() throws Exception;

    public void waitBeforeStart() throws Exception;

}
