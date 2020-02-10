package com.tibco.cep.container.standalone.hawk.methods.om;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ForceOMCheckpointMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public ForceOMCheckpointMethod(HawkRuleAdministrator hma) {
        super("forceOMCheckpoint", "Forces a Object Store checkpoint of a Session.", AmiConstants.METHOD_TYPE_ACTION);
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
                throw new Exception("Please provide a session name.");
            } else {
                final RuleSession ruleSession = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(wmName);
                if (null == ruleSession) {
                    throw new Exception("Please provide a valid session name.");
                }
//                if (ruleSession.getObjectManager() instanceof PersistentStore) {
//                    ((PersistentStore) ruleSession.getObjectManager()).forceCheckpoint();
//                } else {
//                    throw new Exception("Session '" + wmName + "' does not use a persistent Object Manager.");
//                }
            }
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return null;
    }//onInvoke


}//class

