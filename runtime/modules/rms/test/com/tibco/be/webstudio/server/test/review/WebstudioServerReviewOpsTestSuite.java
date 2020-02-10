package com.tibco.be.webstudio.server.test.review;

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
 * Date: 22/4/12
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebstudioServerReviewOpsTestSuite extends AbstractWebstudioServerTestSuite {

    /**
         * Test checkout operation for webstudio.
         */
    @Test
    public void fetchReviewTaskListOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/workList";

        try {
            GetMethodWebRequest request = new GetMethodWebRequest(WS_BASE_URL + opsContext);
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getText());
            Assert.assertEquals(200, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
         * Test checkout operation for webstudio.
         */
    @Test
    public void fetchReviewTaskDetailsOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/workItemDetail";

        try {
            GetMethodWebRequest request = new GetMethodWebRequest(WS_BASE_URL + opsContext);
            request.setParameter("revisionId", "10000");
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getText());
            Assert.assertEquals(200, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
         * Test fetch contents of artifact for review
         */
    @Test
    public void fetchReviewTaskDetailsContentsOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/reviewContents";

        try {
            GetMethodWebRequest request = new GetMethodWebRequest(WS_BASE_URL + opsContext);
            request.setParameter("revisionId", "10002");
            request.setParameter("artifactPath", "/Rule_Templates/Instance_SpecialOffers_Nested");
            request.setParameter("artifactExtn", "ruletemplateinstance");
            byte[] cookieBytes = IOUtils.readBytes(adminCookieFilePath);
            request.setHeaderField("Cookie", new String(cookieBytes, "UTF-8"));
            WebResponse response = wc.getResponse(request);
            System.out.println(response.getText());
            Assert.assertEquals(200, response.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
             * Test checkout operation for webstudio.
             */
    @Test
    public void performStatusChangesOpSuccess() {
        WebConversation wc = new WebConversation();
        String opsContext = "/ws/statusChange";

        InputStream inputStream =
            this.getClass().getClassLoader().getResourceAsStream("com/tibco/be/webstudio/server/test/review/StatusChangesRequest.xml");

        try {
            PostMethodWebRequest request =
                new PostMethodWebRequest(WS_BASE_URL + opsContext, inputStream, "text/xml");
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
}
