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
public class GetRuleMethod
        extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public GetRuleMethod(HawkRuleAdministrator hma) {
        super("getRule", "Retrieves a Rule.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        args.addElement(new AmiParameter("URI", "URI of the Rule", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        AmiParameterList values= new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("URI", "URI of the Rule", ""));
        values.addElement(new AmiParameter("Priority", "Priority of the rule.", 0));
        //values.addElement(new AmiParameter("Nb times fired", "Number of times the rule has been fired.", 0));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(
            AmiParameterList values,
            int line,
            Rule rule,
            String wmName) {
        values.addElement(new AmiParameter("Line", "Line Number.", line));
        String ruleName = rule.getName();
//        String prefix = BEProperties.getInstance().getString("be.codegen.rootPackage", "be.gen");
//        if(ruleName.startsWith(prefix)) {
//            ruleName = ruleName.substring(prefix.length() +1);
//        }
        values.addElement(new AmiParameter("Session", "Name of the Session.",  wmName));
        values.addElement(new AmiParameter("URI", "URI of the Rule", rule.getUri()));
        if(ruleName.lastIndexOf(".") >= 0) {
            ruleName = ruleName.substring(ruleName.lastIndexOf(".") +1);
        }
        values.addElement(new AmiParameter("Priority", "Priority of the Rule.", rule.getPriority()));
        // todo values.addElement(new AmiParameter("Nb times fired", "Number of times the Rule has been fired.", rule.getTotalNumFired()));
    }//fillInOneReturnsEntry


    void fillInReturnsEntries(
            AmiParameterList values,
            int line,
            String ruleUri,
            WorkingMemory wm) {
        
        for (Rule rule : wm.getRuleLoader().getDeployedRules()) {
             if (rule.getUri().equals(ruleUri)) {
                 this.fillInOneReturnsEntry(values, line, rule, wm.getName());
                 return;
             }//if
        }//for
    }//fillInReturnsEntries


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String wmName = inParams.getString(0);
            final String ruleUri = inParams.getString(1);
            if ((null == ruleUri) || (ruleUri.trim().length() == 0)) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Please provide a rule URI.");
            }
            if ((null == wmName) || "".equals(wmName.trim())) {
                final RuleSession[] sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
                for (int i=0; i<sessions.length; i++) {
                    this.fillInReturnsEntries(values, i, ruleUri, ((RuleSessionImpl) sessions[i]).getWorkingMemory());
                }
            } else {
                this.fillInReturnsEntries(values, 0, ruleUri,
                        ((RuleSessionImpl) this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(wmName))
                                .getWorkingMemory());
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke

}//class

