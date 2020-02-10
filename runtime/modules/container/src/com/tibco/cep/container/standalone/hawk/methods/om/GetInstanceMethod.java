package com.tibco.cep.container.standalone.hawk.methods.om;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiMethodType;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetInstanceMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GetInstanceMethod.class);


    public GetInstanceMethod(HawkRuleAdministrator hma) {
        super("getInstance", "Retrieves an Instance from the Session.", AmiConstants.METHOD_TYPE_INFO, "Line");
        this.m_hma = hma;
    }//constr


    public GetInstanceMethod(
            HawkRuleAdministrator hma,
            String name,
            String description,
            AmiMethodType type,
            String lineIndex) {
        super(name, description, type, lineIndex);
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        args.addElement(new AmiParameter("Id", "Id of the Instance.", ""));
        args.addElement(new AmiParameter("External", "True if using the instance's external Id, false if using the internal Id.", true));
        args.addElement(new AmiParameter("URI", "Path to the concept type in the project.", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("Type", "Attribute or Property.", ""));
        values.addElement(new AmiParameter("Name", "Name of the Attribute or Property.", ""));
        values.addElement(new AmiParameter("Value", "Value of the Attribute or Property.", ""));
        return values;
    }//getReturns


    protected void fillInOneReturnsEntry(AmiParameterList values, int line, String name, EntityHawkAttribute attribute) {
        values.addElement(new AmiParameter("Line", "Line number.", line));
        values.addElement(new AmiParameter("Session", "Name of the Session.", name));
        values.addElement(new AmiParameter("Type", "Attribute or Property.",
                (EntityHawkAttribute.ATTRIBUTE == attribute.getType()) ? "Attribute" : "Property"));
        values.addElement(new AmiParameter("Name", "Name of the Attribute or Property.", attribute.getName()));

        String value = attribute.getValue();
        if ((attribute.getType() == EntityHawkAttribute.ATTRIBUTE)
                && EntityHawkAttribute.TYPE.equals(attribute.getName())) {
            final String prefix = BEProperties.getInstance().getString("be.codegen.rootPackage", "be.gen");
            if (value.startsWith(prefix)) {
                value = value.substring(prefix.length() + 1);
            }
        }
        values.addElement(new AmiParameter("Value", "Value of the Attribute or Property.", value));
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String conceptID = inParams.getString(1);
            final boolean isExternal = inParams.getBoolean(2).booleanValue();
            String sessionName = inParams.getString(0);
            final String uri = (inParams.size() > 3) ? inParams.getString(3) : null;

            if ((null == conceptID) || "".equals(conceptID.trim())) {
                throw new Exception("Please provide a concept ID.");
            }

            RuleSession[] sessions;
            if ((null == sessionName) || "".equals(sessionName.trim())) {
                sessions = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSessions();
            } else {
                final RuleSession s = this.m_hma.getServiceProvider().getRuleRuntime().getRuleSession(sessionName);
                if (null == s) {
                    throw new Exception("Invalid session name: " + sessionName);
                }
                sessions = new RuleSession[] { s };
            }

            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                final RuleSession session = sessions[i];
                sessionName = session.getName();
                final Concept concept = this.getConcept(conceptID, isExternal, session, uri);
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


    protected Concept getConcept(String id, boolean isExternal, RuleSession session, String uri) throws Exception {
        if (isExternal) {
            if (uri != null) {
                //extID with URI
                return (Concept) session.getObjectManager().getElementByUri(id,uri);
            }
            //extID only - no URI
            return (Concept) session.getObjectManager().getElement(id);
        } else {
            return (Concept) session.getObjectManager().getElement(Long.parseLong(id));
        }
    }


    protected List getEntityHawkAttributes(Concept concept) {
        final List attrList = new LinkedList();
        attrList.add(new EntityHawkAttribute("id", String.valueOf(concept.getId()), EntityHawkAttribute.ATTRIBUTE));
        attrList.add(new EntityHawkAttribute(EntityImpl.ATTRIBUTE_EXTID, concept.getExtId(), EntityHawkAttribute.ATTRIBUTE));
        attrList.add(new EntityHawkAttribute("type", concept.getClass().getName(), EntityHawkAttribute.ATTRIBUTE));
        //attrList.add(new EntityHawkAttribute("status", decodeStatus(), EntityHawkAttribute.ATTRIBUTE));  //todo - add this

        final Property[] properties = concept.getProperties();
        for (int i = 0; i < properties.length; i++) {
            final Property p = properties[i];
            if(p instanceof PropertyAtom) {
                attrList.add(new EntityHawkAttribute(properties[i].getName(),
                        ((PropertyAtom) properties[i]).getString(), EntityHawkAttribute.PROPERTY));
            } else {  //must be array
                final PropertyArray parr = (PropertyArray) p;
                for(int j =0; j < parr.length(); j++) {
                    attrList.add(new EntityHawkAttribute(parr.getName() + "[" + j +"]",
                            ((PropertyAtom)parr.get(j)).getString(), EntityHawkAttribute.PROPERTY));
                }
            }
        }
        return attrList;
    }


}//class

