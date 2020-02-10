/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.common;

import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.UTF8_ENCODING;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.SSOConstants.ATTR_SSOTOKEN_ID;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.SSOConstants.SSO_SUBJECT_EX_NAME;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.SSOConstants.SSO_TOKEN_EX_NAME;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.SSOConstants.SSO_TOKEN_ISSUEINSTANT_EX_NAME;
import static com.tibco.be.baas.security.authn.saml.utils.GenericSecurityConstants.SSOConstants.SSO_TOKEN_ISSUER_EX_NAME;

import java.io.StringWriter;
import java.net.URL;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

import org.xml.sax.SAXException;

import com.tibco.be.util.XiSupport;
import com.tibco.net.mime.Base64Codec;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * An abstract notion of an sso token to be used
 * for exchanging between browser/IdPs/SPs.
 * @author Aditya Athalye
 * Date : 17 Oct, 2011
 */
public class SSOToken implements Principal {

    /**
     * The principal of this token.
     */
    private String subject;
    
    /**
     * The issuer URL.
     */
    private URL tokenIssuer;
    
    /**
     * Time of issue.
     */
    private Date issueInstant;
    
    /**
     * Auto generated UUID
     */
    private String uuid;
    
    public SSOToken(String subject, URL tokenIssuer, Date issueInstant) {
        this.subject = subject;
        this.tokenIssuer = tokenIssuer;
        this.issueInstant = issueInstant;
        //Generate UUID at construction time itself
        uuid = UUID.randomUUID().toString();
    }
    
    public Date getIssueInstant() {
        return issueInstant;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public URL getTokenIssuer() {
        return tokenIssuer;
    }

    @Override
    public String getName() {
        return subject;
    }
    
    
    /**
     * Base64 encoded form of the serialized string.
     * @return
     * @throws Exception 
     */
    public String toBase64() throws Exception {
        String serialized = serialize();
        return Base64Codec.encodeBase64(serialized);
    }
    
    /**
     * Serialize the token to XML.
     * @return
     * @throws SAXException 
     */
    public String serialize() throws SAXException {
        XiFactory factory = XiSupport.getXiFactory();
        XiNode document = factory.createDocument();
        XiNode tokenNode = factory.createElement(SSO_TOKEN_EX_NAME);
        tokenNode.setAttributeStringValue(ATTR_SSOTOKEN_ID, uuid);
        document.appendChild(tokenNode);
        XiNode subjectNode = factory.createElement(SSO_SUBJECT_EX_NAME);
        subjectNode.setStringValue(subject);
        tokenNode.appendChild(subjectNode);
        XiNode issuerNode = factory.createElement(SSO_TOKEN_ISSUER_EX_NAME);
        issuerNode.setStringValue(tokenIssuer.toString());
        tokenNode.appendChild(issuerNode);
        XiNode issuerInstantNode = factory.createElement(SSO_TOKEN_ISSUEINSTANT_EX_NAME);
        issuerInstantNode.setStringValue(issueInstant.toString());
        tokenNode.appendChild(issuerInstantNode);

        //serialize it
        StringWriter stringWriter = new StringWriter();
        DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(stringWriter, UTF8_ENCODING);
        document.serialize(handler);
        
        return stringWriter.toString();
    }
    
}
