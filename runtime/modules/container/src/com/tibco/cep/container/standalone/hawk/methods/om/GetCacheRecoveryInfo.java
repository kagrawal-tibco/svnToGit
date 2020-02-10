package com.tibco.cep.container.standalone.hawk.methods.om;


import java.util.LinkedList;
import java.util.List;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiMethodType;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;

import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetCacheRecoveryInfo extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public GetCacheRecoveryInfo(HawkRuleAdministrator hma) {
        super("getCacheRecoveryInfo", "Get the Cache Recovery Info.", AmiConstants.METHOD_TYPE_INFO, "Line");
        this.m_hma = hma;
    }//constr


    public GetCacheRecoveryInfo(HawkRuleAdministrator hma, String name, String description, AmiMethodType type) {
        super(name, description, type);
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Session", "Name of the Session", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line number.", 0));
        values.addElement(new AmiParameter("Session", "Name of the Session.", ""));
        values.addElement(new AmiParameter("NumberOfHandlesLoaded", "Number of Handles loaded in the session", 0L));
        values.addElement(new AmiParameter("NumberOfHandlesInError", "Number of Handles not loaded due to errors", 0L));
        values.addElement(new AmiParameter("NumberOfHandlesInStore", "Number of Handles in the underlying CacheStore", 0L));
        return values;
    }//getReturns


//    protected void fillInOneReturnsEntry(AmiParameterList values, int line, String name, EntityHawkAttribute attribute) {
//        values.addElement(new AmiParameter("Line", "Line number.", line));
//        values.addElement(new AmiParameter("Session", "Name of the Session.", name));
//        values.addElement(new AmiParameter("Type", "Attribute or Property.",
//                (EntityHawkAttribute.ATTRIBUTE == attribute.getType()) ? "Attribute" : "Property"));
//        values.addElement(new AmiParameter("Name", "Name of the Attribute/Property.", attribute.getName()));
//
//        String value = attribute.getValue();
//        if ((attribute.getType() == EntityHawkAttribute.ATTRIBUTE)
//                && EntityHawkAttribute.TYPE.equals(attribute.getName())) {
//            final String prefix = BEProperties.getInstance().getString("be.codegen.rootPackage", "be.gen");
//            if (value.startsWith(prefix)) {
//                value = value.substring(prefix.length() + 1);
//            }
//        }
//        values.addElement(new AmiParameter("Value", "Value of the Attribute/Property.", value));
//    }


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
                sessions = new RuleSession[] { s };
            }

            int line = 1;
            for (int i = 0; i < sessions.length; i++) {
                final RuleSession session = sessions[i];
                sessionName = session.getName();
                if (session.getObjectManager() instanceof DistributedCacheBasedStore) {
                    DistributedCacheBasedStore store= (DistributedCacheBasedStore) session.getObjectManager();
                    values.addElement(new AmiParameter("Line", "Line number.", line));
                    values.addElement(new AmiParameter("Session", "Name of the Session.", sessionName));
                    values.addElement(new AmiParameter("NumberOfHandlesLoaded", "Number of Handles loaded in the session", store.getNumHandlesRecovered()));
                    values.addElement(new AmiParameter("NumberOfHandlesInError", "Number of Handles not loaded due to errors", store.getNumHandlesError()));
                    values.addElement(new AmiParameter("NumberOfHandlesInStore", "Number of Handles in the underlying CacheStore", store.getNumHandlesInStore()));
                    line++;
                }
            }//for

            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


    protected Concept getConcept(String id, boolean isExternal, RuleSession session) throws Exception {
        if (isExternal) {
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


