
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
public class DeactivateRuleMethod
        extends AmiMethod {


    protected HawkRuleAdministrator m_hma;

    public DeactivateRuleMethod(HawkRuleAdministrator hma) {
        super("deactivateRule", "Deactivate a Rule in the Session", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }//constr

    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        args.addElement(new AmiParameter("URI", "URI of the RuleSet", ""));
        return args;
    }//getArguments

    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("URI", "URI of the Rule.", ""));
        values.addElement(new AmiParameter("Deactivated", "Is the Rule deactivated?", false));
        //values.addElement(new AmiParameter("Nb Rules", "Number of rules in the RuleSet.", 0));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(
            AmiParameterList values,
            String ruleName,
            WorkingMemory wm)
            throws AmiException {
        final Rule rule = wm.getRuleLoader().deactivateRule(ruleName);
        if (rule == null) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid RuleSet URI");
        }

        values.addElement(new AmiParameter("Session", "Name of the Session.", wm.getName()));
        values.addElement(new AmiParameter("URI", "URI of the Rule.", rule.getUri()));
        values.addElement(new AmiParameter("Deactivated", "Is the Rule deactivated?", !rule.isActive()));
        //values.addElement(new AmiParameter("Nb Rules", "Number of rules in the RuleSet.", ruleSet.getNbRules()));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        AmiParameterList values = new AmiParameterList();
        try {
            final String ruleName = inParams.getString(1);
            if ((ruleName == null) || (ruleName.trim().length() == 0)) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Rule URI is empty");
            }

            final String wmName = inParams.getString(0);
            if (wmName == null || wmName.trim().length() == 0) {
                RuleSession[] archs = m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for (int j = 0; j < archs.length; j++) {
                    this.fillInOneReturnsEntry(values, ruleName, ((RuleSessionImpl) archs[j]).getWorkingMemory());
                }
            } else {
                this.fillInOneReturnsEntry(values, ruleName, ((RuleSessionImpl)
                        m_hma.getServiceProvider().getRuleRuntime().getRuleSession(wmName)).getWorkingMemory());
            }
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return values;
    }//onInvoke

}//class

