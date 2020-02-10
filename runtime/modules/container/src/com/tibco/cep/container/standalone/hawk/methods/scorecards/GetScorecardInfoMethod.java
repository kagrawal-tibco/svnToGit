package com.tibco.cep.container.standalone.hawk.methods.scorecards;


import java.util.Iterator;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.container.standalone.hawk.methods.om.EntityHawkAttribute;
import com.tibco.cep.container.standalone.hawk.methods.om.GetInstanceMethod;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetScorecardInfoMethod extends GetInstanceMethod {


    public GetScorecardInfoMethod(HawkRuleAdministrator hma) {
        super(hma, "getScorecard", "Retrieves a Scorecard of a Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        args.addElement(new AmiParameter("URI", "URI of the Scorecard.", ""));
        return args;
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String id = inParams.getString(1);
            String sessionName = inParams.getString(0);

            if ((null == id) || "".equals(id.trim())) {
                throw new Exception("Please provide a URI.");
            }

            RuleSession[] sessions;
            if ((null == sessionName) || "".equals(sessionName.trim())) {
                sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
            } else {
                final RuleSession s = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
                if (null == s) {
                    throw new Exception("Invalid session name: " + sessionName);
                }
                sessions = new RuleSession[]{s};
            }

            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                final RuleSession session = sessions[i];
                sessionName = session.getName();
                final TypeManager.TypeDescriptor desc =
                        session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(id);
                final Concept concept = (Concept)
                        session.getObjectManager().getNamedInstance(desc.getURI(), desc.getImplClass());

                if (null != concept) {
                    for (Iterator it = getEntityHawkAttributes(concept).iterator(); it.hasNext(); line++) {
                        this.fillInOneReturnsEntry(values, line, sessionName, (EntityHawkAttribute) it.next());
                    }//for
                }//if
            }//for

            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


}//class

