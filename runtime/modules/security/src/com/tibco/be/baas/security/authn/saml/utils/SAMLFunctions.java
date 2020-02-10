/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.utils;


import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.protocol.SAMLObjectCompositeFactory;
import com.tibco.be.baas.security.authn.saml.protocol.impl.AuthnRequestTypeComposite;
import com.tibco.be.baas.security.authn.saml.protocol.impl.SAMLResponseTypeComposite;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import org.eclipse.emf.ecore.xmi.XMLResource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Security",
        category = "SAML",
        synopsis = "Functions for SAML support.")
 public class SAMLFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "createSAMLAssertionResponseContent",
        synopsis = "Generate a SAML assertion response for an authn query request from SP.",
        signature = "String createSAMLAssertionResponseContent(Concept assertionComposite)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "assertionComposite", type = "Concept", desc = "The BAAS_C_AssertionComposite concept.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Generate a SAML assertion response for an authn query request from SP.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String createSAMLAssertionResponseContent(Concept assertionComposite) {
        String samlResponseContent = null;
        Map<String, Object> assertionAttributes = createAssertionAttributes(assertionComposite);
        StringWriter stringWriter = null;
        try {
            SAMLResponseTypeComposite responseTypeComposite = SAMLObjectCompositeFactory.getSAMLObjectComposite(ResponseType.class);
            ResponseType responseType = responseTypeComposite.composeSAMLObject(assertionAttributes);
            //Serialize it
            stringWriter = new StringWriter();
            Map<Object, Object> options = new HashMap<Object, Object>();
            options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
            options.put(XMLResource.OPTION_ENCODING, GenericSecurityConstants.UTF8_ENCODING);

            SAMLModelSerializationUtils.marshallEObject(stringWriter, responseType, null);
            samlResponseContent = stringWriter.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                stringWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return samlResponseContent;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createAuthnAssertionRequestToIdp",
        synopsis = "Generate a SAML authn assertion request for BAAS IdP.",
        signature = "String createAuthnAssertionRequestToIdp(Concept authnRequestComposite)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "authnRequestComposite", type = "Concept", desc = "The BAAS_C_AssertionComposite concept.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Generate a SAML authn assertion request for BAAS IdP. To be implemented on SP side.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String createAuthnAssertionRequestToIdp(Concept authnRequestComposite) {
        String samlAuthnAssnRequestContent = null;
        Map<String, Object> authnAssnRequestAttributes = createAuthnAssnRequestAttributes(authnRequestComposite);
        StringWriter stringWriter = null;
        try {
            AuthnRequestTypeComposite authnRequestTypeComposite = SAMLObjectCompositeFactory.getSAMLObjectComposite(AuthnRequestType.class);
            AuthnRequestType authnRequestType = authnRequestTypeComposite.composeSAMLObject(authnAssnRequestAttributes);
            //Serialize it
            stringWriter = new StringWriter();
            Map<Object, Object> options = new HashMap<Object, Object>();
	    options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
            options.put(XMLResource.OPTION_ENCODING, GenericSecurityConstants.UTF8_ENCODING);

            SAMLModelSerializationUtils.marshallEObject(stringWriter, authnRequestType, options);
            samlAuthnAssnRequestContent = stringWriter.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                stringWriter.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return samlAuthnAssnRequestContent;
    }

    /**
     *
     * @param authnRequestComposite
     * @return
     */
    private static Map<String, Object> createAuthnAssnRequestAttributes(Concept authnRequestComposite) {
        //Extract its properties and fill map
        Property issuerUrlProperty = authnRequestComposite.getProperty("issuerUrl");
        String issuerURL = (String)((PropertyAtomSimple)issuerUrlProperty).getValue();

        Property issuerInstantProperty = authnRequestComposite.getProperty("issueInstant");
        GregorianCalendar issuerInstant = (GregorianCalendar)((PropertyAtomSimple)issuerInstantProperty).getValue();

        Property assnConsumerIndexProperty = authnRequestComposite.getProperty("assnConsumerIndex");
        int assnConsumerIndex = (Integer)((PropertyAtomSimple)assnConsumerIndexProperty).getValue();

        Property authnRequestIdProperty = authnRequestComposite.getProperty("authnRequestId");
        String authnRequestId = (String)((PropertyAtomSimple)authnRequestIdProperty).getValue();

        Map<String, Object> objectAttributes = new HashMap<String, Object>();
        objectAttributes.put("ID", authnRequestId);
        objectAttributes.put("ISSUE_INSTANT", issuerInstant);
        objectAttributes.put("ASSN_CS_IDX", assnConsumerIndex);
        objectAttributes.put("ISSUER_URL", issuerURL);

        return objectAttributes;
    }

    /**
     *
     * @param assertionComposite
     * @return
     */
    private static Map<String, Object> createAssertionAttributes(Concept assertionComposite) {
        //extract its properties and fill map
        Property issuerUrlProperty = assertionComposite.getProperty("issuerUrl");
        String issuerURL = (String)((PropertyAtomSimple)issuerUrlProperty).getValue();

        Property issuerInstantProperty = assertionComposite.getProperty("issueInstant");
        GregorianCalendar issuerInstant = (GregorianCalendar)((PropertyAtomSimple)issuerInstantProperty).getValue();

        Property subjectNameProperty = assertionComposite.getProperty("subjectName");
        String subjectName = (String)((PropertyAtomSimple)subjectNameProperty).getValue();

        Property assertionIdProperty = assertionComposite.getProperty("assertionId");
        String assertionId = (String)((PropertyAtomSimple)assertionIdProperty).getValue();

        Property authnInstantProperty = assertionComposite.getProperty("authnInstant");
        GregorianCalendar authnInstant = (GregorianCalendar)((PropertyAtomSimple)authnInstantProperty).getValue();

        Property cond_notBeforeProperty = assertionComposite.getProperty("condition_notBefore");
        GregorianCalendar notBefore = (GregorianCalendar)((PropertyAtomSimple)cond_notBeforeProperty).getValue();

        Property cond_notAfterProperty = assertionComposite.getProperty("condition_notAfter");
        GregorianCalendar notAfter = (GregorianCalendar)((PropertyAtomSimple)cond_notAfterProperty).getValue();

        Property authnAssnReqIdProperty = assertionComposite.getProperty("authnAssnReqId");
        String authnAssnReqId = (String)((PropertyAtomSimple)authnAssnReqIdProperty).getValue();

        Property audiences = assertionComposite.getProperty("audiences");
        PropertyArray audiencesPropertyArray = (PropertyArray)audiences;
        PropertyAtom[] propertyAtoms = audiencesPropertyArray.toArray();
        List<String> audiencesList = new ArrayList<String>(propertyAtoms.length);

        for (PropertyAtom propertyAtom : propertyAtoms) {
            audiencesList.add(propertyAtom.getString());
        }

        Map<String, Object> objectAttributes = new HashMap<String, Object>();
        objectAttributes.put("ID", assertionId);
        objectAttributes.put("ISSUER_URL", issuerURL);
        objectAttributes.put("AUTHN_INSTANT", authnInstant);
        objectAttributes.put("SUBJECT_NAME", subjectName);
        objectAttributes.put("RESPONSE_TO", authnAssnReqId);
        objectAttributes.put("ISSUE_INSTANT", issuerInstant);
        objectAttributes.put("NOT_BEFORE", notBefore);
        objectAttributes.put("NOT_ON_AFTER", notAfter);
        objectAttributes.put("AUDIENCE", audiencesList);

        return objectAttributes;
    }
}
