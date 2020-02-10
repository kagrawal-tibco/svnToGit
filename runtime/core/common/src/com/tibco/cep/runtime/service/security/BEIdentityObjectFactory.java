package com.tibco.cep.runtime.service.security;


import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.security.AXSecurityException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author ishaan
 * @version May 3, 2006, 6:07:53 PM
 */
public class BEIdentityObjectFactory {


    public static final ExpandedName OBJECT_TYPE = ExpandedName.makeName("objectType");
    public static final String JMS_SSL_ID = "identity";
    public static final String SSL_NODE_NS = "http://www.tibco.com/xmlns/aemeta/services/2002";
    public static final ExpandedName JMS_SSL_ID_EN = ExpandedName.makeName(SSL_NODE_NS, JMS_SSL_ID);
    public static final String REPO_NODE_NS = "http://www.tibco.com/xmlns/repo/types/2002";
    public static final ExpandedName REPO_NODE = ExpandedName.makeName(REPO_NODE_NS, "repository");
    public static final ExpandedName REPO_IDENTITY = ExpandedName.makeName(JMS_SSL_ID);



    public BEIdentityObjectFactory()
    {
    }


    public static BEIdentity createIdentityObject(
            XiNode documentNode,
            GlobalVariables globalVariables)
            throws AXSecurityException
    {
        XiNode elementNode = documentNode.getFirstChild();
        ExpandedName name = elementNode.getName();
        //if (!IDENTITY_NODE_EN.equals(name)) {
        if (!JMS_SSL_ID_EN.equals(name)) {
            // This is needed to handle the "\n" characters
            elementNode = elementNode.getNextSibling();
        } else if (documentNode.getName().equals(REPO_NODE)) {
            elementNode = XiChild.getChild(documentNode, REPO_IDENTITY);
        }
        final String strObjectType = globalVariables.substituteVariables(
                XiChild.getString(elementNode, OBJECT_TYPE)).toString();
        if ("url".equals(strObjectType)) {
            return new BEKeystoreIdentity(strObjectType, elementNode, globalVariables);
        } else if ("usernamePassword".equals(strObjectType)) {
            return new BEUserIdPasswordIdentity(elementNode, globalVariables);
        } else if ("certPlusKeyURL".equals(strObjectType)) {
            return new BECertPlusKeyIdentity(strObjectType, elementNode);
        }
        return null;
    }


    public static BEIdentity createIdentityObject(
            Identity identity,
            GlobalVariables globalVariables)
            throws AXSecurityException
    {
    	final Object raw = identity.getObjectType();
    	
    	final ObjectTypeEnum objectType;
    	if (raw instanceof CharSequence) {
    		objectType = ObjectTypeEnum.getByName(globalVariables.substituteVariables((CharSequence) raw).toString());
    		if (null == objectType) {
    			return null;
    		}
    	} else if (raw instanceof ObjectTypeEnum) {
    		objectType = (ObjectTypeEnum) raw;
    	} else {
    		throw new IllegalArgumentException("Unknown ObjectType class: " + raw);
    	}
        switch(objectType) {
            case CERT_PLUS_KEY_URL:
                return new BECertPlusKeyIdentity(identity, globalVariables);
            case URL:
                return new BEKeystoreIdentity(identity, globalVariables);
            case USERNAME_PASSWORD:
                return new BEUserIdPasswordIdentity(identity, globalVariables);
            default:
                return null;
        }
    }

}
