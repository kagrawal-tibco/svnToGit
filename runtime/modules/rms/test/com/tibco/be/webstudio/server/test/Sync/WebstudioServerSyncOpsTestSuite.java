package com.tibco.be.webstudio.server.test.Sync;

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
 * Date: 27/4/12
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebstudioServerSyncOpsTestSuite extends AbstractWebstudioServerTestSuite {

    /**
         * Test checkout operation for webstudio.
         */
    @Test
    public void adminSyncOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/synchronize";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/Sync/SyncRequest.xml");

        try {
            PostMethodWebRequest request =
                new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
            request.setHeaderField("username", "admin");
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getText());
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
    public void businessUserSyncOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/synchronize";

        InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/Sync/SyncRequest.xml");

        try {
            PostMethodWebRequest request =
                new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
            request.setHeaderField("username", "admin");
            byte[] cookieBytes = IOUtils.readBytes(businessUserCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getText());
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
}
