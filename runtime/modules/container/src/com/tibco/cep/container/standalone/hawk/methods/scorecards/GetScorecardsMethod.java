package com.tibco.cep.container.standalone.hawk.methods.scorecards;

import java.util.Collection;
import java.util.Iterator;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
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
public class GetScorecardsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public GetScorecardsMethod(HawkRuleAdministrator hma) {
        super("getScorecards", "Retrieves all the Scorecards of a Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("Id", "Id of the Scorecard.", 0L));
        values.addElement(new AmiParameter("External Id", "External Id of the Scorecard.", ""));
        values.addElement(new AmiParameter("Type", "Class of the Scorecard.", ""));
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int line, String name, Concept concept) {
        values.addElement(new AmiParameter("Line", "Line Number.", line));
        values.addElement(new AmiParameter("Session", "Name of the Session.", name));
        values.addElement(new AmiParameter("Id", "Id of the Scorecard.", concept.getId()));
        values.addElement(new AmiParameter("External Id", "External Id of the Scorecard.", concept.getExtId()));

        String type = concept.getClass().getName();
        String prefix = m_hma.getServiceProvider().getProperties().getProperty("be.codegen.rootPackage", "be.gen");
        if(type.startsWith(prefix)) {
            type = type.substring(prefix.length() +1);
        }
        values.addElement(new AmiParameter("Type", "Class of the Scorecard.", type));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            String sessionName = inParams.getString(0);

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
                final Collection scorecards = session.getRuleServiceProvider().getTypeManager().getTypeDescriptors(
                        TypeManager.TYPE_NAMEDINSTANCE);
                for (Iterator it = scorecards.iterator(); it.hasNext(); line++) {
                    final TypeManager.TypeDescriptor desc = (TypeManager.TypeDescriptor) it.next();
                    final Concept c = (Concept)
                            session.getObjectManager().getNamedInstance(desc.getURI(), desc.getImplClass());
                    if(c != null) 
                        this.fillInOneReturnsEntry(values, line, sessionName, c);
                }
            }//for

            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


}//class

