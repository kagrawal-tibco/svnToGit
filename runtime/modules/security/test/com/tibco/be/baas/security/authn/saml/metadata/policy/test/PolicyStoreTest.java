/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.policy.test;

import static org.junit.Assert.*;

import com.tibco.be.baas.security.authn.saml.metadata.policy.spi.PolicyStoreService;
import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy;
import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue;
import com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType;
import com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyFactory;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;
import com.tibco.be.baas.security.authn.saml.utils.SAMLModelSerializationUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.emf.ecore.EPackage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for policy store services.
 * @see PolicyStoreService
 * @author aditya
 */
public class PolicyStoreTest {
    
    private static PolicyStoreService policyStoreService;
    
    
    public PolicyStoreTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        policyStoreService = new PolicyStoreService();
        final EPackage.Registry registry = EPackage.Registry.INSTANCE;
        registry.put(PolicyPackage.eNS_URI, PolicyPackage.eINSTANCE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testGetLoadedTemplates() {
        try {
            Set<PolicyTemplateType> policyTemplates = policyStoreService.getLoadedTemplates();
            assertTrue(policyTemplates.size() == 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PolicyStoreTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testAddNewAuthnPolicy() {
        try {
            AuthnPolicy authnPolicy = PolicyFactory.eINSTANCE.createAuthnPolicy();
            authnPolicy.setID("FILE_POLICY");
            PolicyConfigType policyConfigType = PolicyFactory.eINSTANCE.createPolicyConfigType();
            ConfigPropertyType configPropertyType = PolicyFactory.eINSTANCE.createConfigPropertyType();
            configPropertyType.setName("FileName");
            ConfigPropertyValue configPropertyValue = PolicyFactory.eINSTANCE.createConfigPropertyValue();
            configPropertyValue.setValue("Hello.java");
            DataTypeType dataTypeType = DataTypeType.STRING;
            configPropertyType.setDataType(dataTypeType);
            configPropertyType.getPropertyValue().add(configPropertyValue);
            policyConfigType.getConfigProperty().add(configPropertyType);
            authnPolicy.setPolicyConfig(policyConfigType);
            Boolean bool = policyStoreService.addPolicyInstance(authnPolicy);
            assertTrue(bool);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PolicyStoreTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testSerializeEObject() {
        try {
            String filePolicy = "/media/Windows7/dev/be/5.1/runtime/modules/security/project/BAAS/Authentication/MDS/Schemas/FilePolicy_emf.xml";
            File file = new File(filePolicy);
            AuthnPolicyTemplate policyTemplateType = PolicyFactory.eINSTANCE.createAuthnPolicyTemplate();
            policyTemplateType.setID("FILE_POLICY");
            PolicyConfigType policyConfigType = PolicyFactory.eINSTANCE.createPolicyConfigType();
            ConfigPropertyType configPropertyType = PolicyFactory.eINSTANCE.createConfigPropertyType();
            configPropertyType.setName("FileName");
            ConfigPropertyValue configPropertyValue = PolicyFactory.eINSTANCE.createConfigPropertyValue();
            configPropertyValue.setValue("Hello.java");
            DataTypeType dataTypeType = DataTypeType.STRING;
            configPropertyType.setDataType(dataTypeType);
            configPropertyType.getPropertyValue().add(configPropertyValue);
            policyConfigType.getConfigProperty().add(configPropertyType);
            policyTemplateType.setPolicyConfig(policyConfigType);
//            StringWriter stringWriter = new StringWriter();
            SAMLModelSerializationUtils.marshallEObject(file, policyTemplateType, new HashMap<Object, Object>());
            assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PolicyStoreTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testAddDuplicateIdPolicyTemplate() {
        try {
            String filePolicy = "/media/Windows7/dev/be/5.1/runtime/modules/security/project/BAAS/Authentication/MDS/Schemas/FilePolicy.xml";
            File file = new File(filePolicy);
            DocumentRoot documentRoot = (DocumentRoot)SAMLModelSerializationUtils.unmarshallEObject(new FileInputStream(file));
            PolicyTemplateType authnPolicy = documentRoot.getAuthnPolicy();
            Boolean bool = policyStoreService.addPolicyInstance(authnPolicy);
            assertTrue(!bool);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PolicyStoreTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }
}
