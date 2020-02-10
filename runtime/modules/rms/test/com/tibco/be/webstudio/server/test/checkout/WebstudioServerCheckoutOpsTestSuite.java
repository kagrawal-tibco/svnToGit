package com.tibco.be.webstudio.server.test.checkout;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.tibco.be.webstudio.server.test.AbstractWebstudioServerTestSuite;
import com.tibco.cep.security.authz.utils.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/3/12
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebstudioServerCheckoutOpsTestSuite extends AbstractWebstudioServerTestSuite {

    /**
             * Test checkout operation for webstudio.
             */
    @Test
    public void fetchArtifactNamesOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/fetchNew";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/checkout/Admin-CheckoutRequest.xml");

        try {
            GetMethodWebRequest request = new GetMethodWebRequest(WS_BASE_URL + opsContext);
            request.setParameter("projectName", "CreditCardApplication");
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getResponseMessage());
            Assert.assertEquals(200, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
         * Test checkout operation for webstudio.
         */
    @Test
    public void admincheckoutOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/fetchNew";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/checkout/Admin-CheckoutRequest.xml");

        try {
            PostMethodWebRequest request =
                new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
            request.setHeaderField("username", "admin");
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getResponseMessage());
            Assert.assertEquals(200, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
         * Test checkout operation for webstudio.
         */
    @Test
    public void businessUsercheckoutOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/fetchNew";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/checkout/BusinessUser-CheckoutRequest.xml");

        try {
            PostMethodWebRequest request =
                new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
            request.setHeaderField("username", "admin");
            byte[] cookieBytes = IOUtils.readBytes(businessUserCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getResponseMessage());
            Assert.assertEquals(200, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Fetch user's workspace contents
     */
    @Test
    public void fetchWSContentsOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/workspace";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("username", "admin");
        try {
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            Assert.assertEquals(200, response.getResponseCode());
            System.out.println(response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch contents of RT from workspace/SCS.
     */
    @Test
    public void fetchRTWithViewContentsSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/artifactContent";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("artifactExtension", "ruletemplate");
        request.setParameter("artifactPath", "/Rule_Templates/Applicant_PreScreen_WithView");
        request.setParameter("projectName", "CreditCardApplication");

        try {
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            Assert.assertEquals(200, response.getResponseCode());
            System.out.println(response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
         * Fetch contents of RT from workspace/SCS.
         */
    @Test
    public void adminFetchRTForBuilderContentsSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/artifactContent";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("artifactExtension", "ruletemplate");
        request.setParameter("artifactPath", "/Rule_Templates/SpecialOffers");
        request.setParameter("projectName", "CreditCardApplication");

        try {
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            Assert.assertEquals(200, response.getResponseCode());
            System.out.println(response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
         * Fetch contents of RT from workspace/SCS.
         */
    @Test
    public void businessUserFetchRTForBuilderContentsSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/artifactContent";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("artifactExtension", "ruletemplate");
        request.setParameter("artifactPath", "/Rule_Templates/SpecialOffers");
        request.setParameter("projectName", "CreditCardApplication");

        try {
            byte[] cookieBytes = IOUtils.readBytes(businessUserCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            Assert.assertEquals(200, response.getResponseCode());
            System.out.println(response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
         * Fetch contents of RTI from workspace/SCS.
         */
    @Test
    public void fetchRTIContentsSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/artifactContent";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("artifactExtension", "ruletemplateinstance");
        request.setParameter("artifactPath", "/Rule_Templates/HelloWorld");
        request.setParameter("projectName", "CreditCardApplication");

        try {
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            Assert.assertEquals(200, response.getResponseCode());
            System.out.println(response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
