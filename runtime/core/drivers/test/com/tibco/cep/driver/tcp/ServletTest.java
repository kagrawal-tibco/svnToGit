package com.tibco.cep.driver.tcp;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.tibco.cep.mgmtserver.rms.MethodBuilder;
import com.tibco.cep.mgmtserver.rms.builder.impl.PostMethodBuilder;
import com.tibco.cep.mgmtserver.rms.builder.impl.GetMethodBuilder;
import com.tibco.cep.mgmtserver.rms.utils.RequestHeaders;
import com.tibco.cep.decision.util.IOUtils;
import com.tibco.be.functions.string.StringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 26, 2009
 * Time: 4:59:05 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ServletTest {

    public static void main(String[] r) {
        MethodBuilder<PostMethod> methodBuilder = new PostMethodBuilder();
        System.setProperty("javax.net.ssl.trustStore", "E:\\software\\workingdir\\http_channel_SSL\\Keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("javax.net.ssl.keyStore","E:\\software\\workingdir\\http_channel_SSL\\Keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","changeit");
        execute(methodBuilder);
    }
    //static String url = "https://localhost:8888/Common/Resources/Channels/SSLChannel/SSLDestination";
    static String url = "https://localhost:8000/Transport/SSLChannel/SSLDestination";
    static String message = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ret=\"www.tibco.com/be/ontology/EDS/Model/Concepts/EXTENDED/RetrieveQuoteGroupbyQuoteGroupIDRQ\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ret:RetrieveQuoteGroupbyQuoteGroupIDRQ Id=\"?\" extId=\"?\">\n" +
            "         <!--Optional:-->\n" +
            "         <QuoteGroupID>520</QuoteGroupID>\n" +
            "         <!--Optional:-->\n" +
            "         <MessageRequest Id=\"?\" extId=\"?\">\n" +
            "            <!--Optional:-->\n" +
            "            <CorrelationHandleID>1</CorrelationHandleID>\n" +
            "            <!--Optional:-->\n" +
            "            <HostCarrierCode>A</HostCarrierCode>\n" +
            "         </MessageRequest>\n" +
            "      </ret:RetrieveQuoteGroupbyQuoteGroupIDRQ>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
    static void execute(MethodBuilder<PostMethod> methodBuilder) {
        PostMethod method = null;
        try {
            HttpClient client = new HttpClient();
            RequestHeaders reqHeaders = new RequestHeaders();
            reqHeaders.setUsername("test");
            method = methodBuilder.buildMethod(url,
                "RetrieveQuoteGroupbyQuoteGroupIDRQ", "www.tibco.com/be/ontology/EDS/Model/Events/RetrieveQuoteGroupbyQuoteGroupIDRQ",
                reqHeaders, null);
            method.setRequestEntity(new StringRequestEntity(message));
            HttpMethodParams params = new HttpMethodParams();

            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                // This should not come for get
                InputStream responseBody = method.getResponseBodyAsStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                IOUtils.writeBytes(responseBody, bos);
                byte[] bytes = bos.toByteArray();
                System.out.println(StringHelper.convertByteArrayToString(bytes, "UTF-8"));
            }

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {           // Release the connection.
            method.releaseConnection();
        }
    }
}
