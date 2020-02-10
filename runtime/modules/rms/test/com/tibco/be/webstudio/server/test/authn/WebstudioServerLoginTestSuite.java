package com.tibco.be.webstudio.server.test.authn;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.tibco.be.webstudio.server.test.AbstractWebstudioServerTestSuite;
import com.tibco.net.mime.Base64Codec;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/12/11
 * Time: 10:23 AM
 * All login/logout test cases should be added here.
 */
public class WebstudioServerLoginTestSuite extends AbstractWebstudioServerTestSuite {


    public WebstudioServerLoginTestSuite() {
    }


    /**
     * Basic login test for Webstudio.
     */
    @Test
    public void adminLogin() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/login";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("username", "admin");
        FileOutputStream fos = null;
        try {
            request.setParameter("password", Base64Codec.encodeBase64("admin"));
            WebResponse response = wc.getResponse(request);
            String[] cookies = response.getHeaderFields("Set-Cookie");
            fos = new FileOutputStream(adminCookieFilePath);
            fos.write(cookies[1].getBytes("UTF-8"));
            Assert.assertEquals(200, response.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
         * Jdoe login test for Webstudio.
         */
    @Test
    public void jdoeLogin() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/login";

        GetMethodWebRequest request =
            new GetMethodWebRequest(WS_BASE_URL + opsContext);
        request.setParameter("username", "jdoe");
        FileOutputStream fos = null;
        try {
            request.setParameter("password", Base64Codec.encodeBase64("jdoe"));
            WebResponse response = wc.getResponse(request);
            String[] cookies = response.getHeaderFields("Set-Cookie");
            fos = new FileOutputStream(businessUserCookieFilePath);
            fos.write(cookies[1].getBytes("UTF-8"));
            Assert.assertEquals(200, response.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
