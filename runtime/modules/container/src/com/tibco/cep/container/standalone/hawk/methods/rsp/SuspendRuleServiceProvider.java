package com.tibco.cep.container.standalone.hawk.methods.rsp;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;


/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: May 22, 2008
 * Time: 2:42:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class SuspendRuleServiceProvider extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public SuspendRuleServiceProvider(HawkRuleAdministrator hma) {

        super("suspendRuleServiceProvider", "Suspends the RuleServiceProvider", AmiConstants.METHOD_TYPE_ACTION);
        this.m_hma = hma;
    }//constr

    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
//        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
//        args.addElement(new AmiParameter("FileName", "Name of the file that the profiling data is output to.", ""));
//        args.addElement(new AmiParameter("Level", "Level of depth that profiling data will be collected. -1 for all level, 1 for only RTC level.", (int)-1));
//        args.addElement(new AmiParameter("Duration", "Time duration in seconds that the profile data will be collected. If <= 0, Profiler will be on until explicitly turned off.", (long)-1L));
        return args;
    }

     public AmiParameterList getReturns() {
        return null;
    }

    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            RuleServiceProvider rsp =this.m_hma.getServiceProvider();
            if(rsp instanceof RuleServiceProviderImpl) {
              ((RuleServiceProviderImpl)rsp).suspendRSP(RuleServiceProviderImpl.SUSPEND_MODE_ADMIN);
            }
            this.m_hma.getLogger().log(Level.INFO, "RuleServiceProvider %s has been suspended", rsp.getName());
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return null;
    }//onInvoke
}