package com.tibco.be.webstudio.server.test.save;

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
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebstudioServerSaveOpsTestSuite extends AbstractWebstudioServerTestSuite {

    /**
     * Save success case.
     */
    @Test
    public void saveLocalChangesViewOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/save";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/save/SaveChanges_RTIView.xml");
        PostMethodWebRequest request =
            new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
        try {
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
        }
    }

    /**
         * Save success case. - Admin user
         */
    @Test
    public void saveLocalChangesAdminBuilderOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/save";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/save/SaveChanges_Admin_RTIBuilder.xml");
        PostMethodWebRequest request =
            new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
        try {
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
        }
    }

    /**
         * Save success case.  - Business User
         */
    @Test
    public void saveLocalChangesBusinessUserBuilderOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/save";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/save/SaveChanges_BusinessUser_RTIBuilder.xml");
        PostMethodWebRequest request =
            new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
        try {
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
        }
    }


    /**
     * Save op test without login.
     */
    @Test
    public void saveLocalChangesOpNoLogin() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/save";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/save/SaveChanges_RTIView.xml");
        PostMethodWebRequest request =
            new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
        try {
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getResponseMessage());
            Assert.assertEquals(500, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save op test without workspace created yet.
     */
    @Test
    public void saveLocalChangesOpNoWorkspace() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/save";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/save/SaveChanges_RTIView.xml");
        PostMethodWebRequest request =
            new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
        try {
            request.setHeaderField("username", "admin");
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getResponseMessage());
            Assert.assertEquals(500, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
