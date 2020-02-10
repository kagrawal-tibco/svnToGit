
package com.tibco.cep.container.standalone.hawk.methods.wm;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActivateRuleMethod
        extends AmiMethod {
    protected HawkRuleAdministrator m_hma;

    public ActivateRuleMethod(
            HawkRuleAdministrator hma) {
        super("activateRule", "Activate a Rule in the Session", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }//constr

    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session (optional).", ""));
        args.addElement(new AmiParameter("URI", "URI of the Rule.", ""));
        return args;
    }//getArguments

    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Session", "Name of the Session (optional).", ""));
        values.addElement(new AmiParameter("URI", "URI of the Rule.", ""));
        values.addElement(new AmiParameter("Activated", "Is the Rule activated?", false));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(
            AmiParameterList values,
            String ruleUri,
            WorkingMemory wm)
            throws AmiException {
        final Rule rule = wm.getRuleLoader().activateRule(ruleUri);
        if (rule == null) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid Rule URI");
        }

        values.addElement(new AmiParameter("Session", "Name of the Session (optional).", wm.getName()));
        values.addElement(new AmiParameter("URI", "URI of the Rule.", rule.getUri()));
        values.addElement(new AmiParameter("Activated", "Is the Rule activated?", rule.isActive()));
        //values.addElement(new AmiParameter("Nb Rules", "Number of rules in the RuleSet.", ruleSet.getNbRules()));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String ruleUri = inParams.getString(1);
            if (ruleUri == null || ruleUri.trim().length() == 0) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Rule URI is empty");
            }

            final String wmName = inParams.getString(0);
            if (wmName == null || wmName.trim().length() == 0) {
                RuleSession[] archs = m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for (RuleSession rs : archs) {
                    this.fillInOneReturnsEntry(values, ruleUri, ((RuleSessionImpl) rs).getWorkingMemory());
                }
            } else {
                this.fillInOneReturnsEntry(values, ruleUri, ((RuleSessionImpl)
                        m_hma.getServiceProvider().getRuleRuntime().getRuleSession(wmName)).getWorkingMemory());
            }
            
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

