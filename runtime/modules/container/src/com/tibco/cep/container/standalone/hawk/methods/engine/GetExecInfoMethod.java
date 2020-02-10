package com.tibco.cep.container.standalone.hawk.methods.engine;


import COM.TIBCO.hawk.ami.*;
import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 29, 2006
 * Time: 2:12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetExecInfoMethod extends AmiMethod {


    protected HawkRuleAdministrator hma;


    public GetExecInfoMethod(HawkRuleAdministrator hma) {
        super("GetExecInfo", "Gets engine execution information", AmiConstants.METHOD_TYPE_INFO);
        this.hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        return null;
    }


    public AmiParameterList getReturns() {
        final AmiParameterList amiparameterlist = new AmiParameterList();
        amiparameterlist.addElement(new AmiParameter("Status", "Engine status (ACTIVE, SUSPENDED, STANDBY or STOPPING)", this.getStatus()));
        amiparameterlist.addElement(new AmiParameter("Uptime", "Elapsed time since RuleSessionProvider was started (milliseconds)", this.getUptime()));
        amiparameterlist.addElement(new AmiParameter("Threads", "Number of RuleSessions in engine.", this.getThreads()));
        amiparameterlist.addElement(new AmiParameter("Version", "Engine version.", this.getVersion()));
        return amiparameterlist;
    }//getReturns


    private String getStatus() {
        final RuleServiceProvider rsp = this.hma.getServiceProvider();
        if (rsp instanceof FTAsyncRuleServiceProviderImpl) {
            final FTNode currentNode;
            try {
                currentNode = ((FTAsyncRuleServiceProviderImpl) rsp).getCurrentNode();
            } catch (Exception e) {
                return "STANDBY";
            }

            switch (currentNode.getNodeState()) {
                case FTNode.NODE_SHUTDOWN:
                    return "STOPPING";
                case FTNode.NODE_CREATED:
                case FTNode.NODE_INACTIVE:
                case FTNode.NODE_JOINED_CACHE:
                case FTNode.NODE_WAIT_FOR_ACTIVATION:
                case FTNode.NODE_WAIT_RTC_COMPLETE:
                    return "STANDBY";
            }
        }
        return "ACTIVE";
    }


    private String getThreads() {
        try {
            return "" + this.hma.getServiceProvider().getProject().getDeployedBEArchives().size();
        } catch (Exception e) {
            return "1";
        }
    }


    private String getUptime() {
        return "" + (System.currentTimeMillis() - this.hma.getStartTime());
    }


    private Object getVersion() {
        return cep_containerVersion.version + "." + cep_containerVersion.build;
//        try {
//            return "" + this.hma.getServiceProvider().getProject().getVersion();
//        }
//        catch (Exception e) {
//            return "";
//        }
    }


    public AmiParameterList onInvoke(AmiParameterList amiparameterlist) throws AmiException {
        try {
            return this.getReturns();
        }
        catch (Exception exception) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, exception.getMessage());
        }
    }


}//class

