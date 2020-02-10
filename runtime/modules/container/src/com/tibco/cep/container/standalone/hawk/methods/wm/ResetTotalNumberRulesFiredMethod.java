package com.tibco.cep.container.standalone.hawk.methods.wm;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResetTotalNumberRulesFiredMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public ResetTotalNumberRulesFiredMethod(HawkRuleAdministrator hma) {
        super("resetTotalNumberRulesFired", "Resets the total number of rules fired to zero.", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        return null;
    }//getReturns


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final String wmName = inParams.getString(0);
            if (wmName == null || wmName.trim().length() == 0) {
                RuleSession[] archs = m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for(int i = 0; i < archs.length; i++) {
                    WorkingMemory wm = ((RuleSessionImpl)archs[i]).getWorkingMemory();
                    wm.resetTotalNumberRulesFired();
                }
            } else {
                WorkingMemory wm = ((RuleSessionImpl)
                        m_hma.getServiceProvider().getRuleRuntime().getRuleSession(wmName)).getWorkingMemory();
                wm.resetTotalNumberRulesFired();
            }
            return new AmiParameterList();
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

