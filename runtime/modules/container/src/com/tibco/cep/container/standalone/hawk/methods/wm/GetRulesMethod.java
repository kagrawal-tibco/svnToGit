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
public class GetRulesMethod
        extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public GetRulesMethod(
            HawkRuleAdministrator hma) {
        super("getRules", "Retrieves Rule's from the Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("URI", "URI of the Rule.", ""));
        values.addElement(new AmiParameter("Activated", "Is the Rule activated.", false));
        //values.addElement(new AmiParameter("Nb Rules", "Number of rules in the RuleSet.", 0));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int line, Rule rule, String wmName) {
        values.addElement(new AmiParameter("Line", "Line Number.", line));
        values.addElement(new AmiParameter("Session", "Name of the Session.", wmName));
        values.addElement(new AmiParameter("URI", "URI of the Rule.", rule.getUri()));
        values.addElement(new AmiParameter("Activated", "Is the Rule activated.", rule.isActive()));
        //values.addElement(new AmiParameter("Nb Rules", "Number of rules in the RuleSet.", ruleSet.getNbRules()));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        AmiParameterList values = null;
        try {
            int line = 0;
            values = new AmiParameterList();
            final String wmName = inParams.getString(0);
            if (wmName == null || wmName.trim().length() == 0) {
                RuleSession[] archs = m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for(int i = 0; i < archs.length; i++) {
                    WorkingMemory wm = ((RuleSessionImpl)archs[i]).getWorkingMemory();
                    if (null != wm) {
                        for (Rule rule : wm.getRuleLoader().getDeployedRules()) {
                            this.fillInOneReturnsEntry(values, ++line, rule, wm.getName());
                        }//for
                    }//if
                }
            } else {
                WorkingMemory wm = ((RuleSessionImpl)
                        m_hma.getServiceProvider().getRuleRuntime().getRuleSession(wmName)).getWorkingMemory();
                if (null != wm) {
                    for (Rule rule : wm.getRuleLoader().getDeployedRules()) {
                        this.fillInOneReturnsEntry(values, ++line, rule, wm.getName());
                    }//for
                }//if
            }
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return values;
    }//onInvoke

}//class

