/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.baas.security.authn.saml.metadata.partner.test;


import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.PartnerRegistrationStatus;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.SAMLPartnerRegistrationService;
import com.tibco.be.baas.security.authn.saml.metadata.partner.spi.impl.SAMLPartnerRegistrationServiceImpl;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for partner registration service.
 * @see SAMLPartnerRegistrationService
 * @author aditya
 */
public class SAMLPartnerRegistrationServiceTest {
    
    private static SAMLPartnerRegistrationService samlPartnerRegistrationService;
    
   
    public SAMLPartnerRegistrationServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        samlPartnerRegistrationService = new SAMLPartnerRegistrationServiceImpl();
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
    public void testSPPartnerRegistration() {
//        EntityDescriptorType entityDescriptor = objectFactory.createEntityDescriptor();
//        entityDescriptor.setEntityID("www.tibco.com/be/baas/sp/Views2");
//        SPSSODescriptor sPSSODescriptorType = objectFactory.createSPSSODescriptor();
//        sPSSODescriptorType.setAuthnRequestsSigned(false);
//        EndpointType singleLogoutServiceType = objectFactory.createEndpointType();
//        singleLogoutServiceType.setLocation("http://localhost:6000/be/baas/channels/singlelogoutservice");
//        sPSSODescriptorType.getSingleLogoutServices().add(singleLogoutServiceType);
//        Organization organization = objectFactory.createOrganization();
//        LocalizedNameType localizedNameType = objectFactory.createLocalizedNameType();
//        localizedNameType.setValue("www.tibco.com");
//        organization.getOrganizationNames().add(localizedNameType);
//        sPSSODescriptorType.setOrganization(organization);
//        entityDescriptor.getRoleDescriptorsAndIDPSSODescriptorsAndSPSSODescriptors().add(sPSSODescriptorType);
//        try {
//            PartnerRegistrationStatus partnerRegistrationStatus = samlPartnerRegistrationService.registerSPPartner(entityDescriptor);
//            assertTrue(partnerRegistrationStatus.getStatusCode() == 1);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Logger.getLogger(SAMLPartnerRegistrationServiceTest.class.getName()).log(Level.SEVERE, null, ex);
//            fail(ex.getMessage());
//        }
    }
}
