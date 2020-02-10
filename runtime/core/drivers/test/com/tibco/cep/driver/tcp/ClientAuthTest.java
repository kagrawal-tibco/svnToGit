package com.tibco.cep.driver.tcp;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.io.*;
import java.net.URL;

import com.tibco.cep.mgmtserver.rms.MethodBuilder;
import com.tibco.cep.mgmtserver.rms.builder.impl.PostMethodBuilder;
import com.tibco.cep.mgmtserver.rms.utils.RequestHeaders;
import com.tibco.cep.decision.util.IOUtils;
import com.tibco.be.functions.string.StringHelper;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 2, 2009
 * Time: 5:04:56 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ClientAuthTest {
    static String urlString = "https://localhost:8000/Transport/SSLChannel/SSLDestination";
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
            method = methodBuilder.buildMethod(urlString,
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

    public static void main(String[] args) throws Exception {
    String host = null;
    int port = -1;
    String path = null;
    System.setProperty("javax.net.ssl.trustStore", "E:\\software\\workingdir\\http_channel_SSL\\Keystore.jks");
    System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    for (int i = 0; i < args.length; i++)
        System.out.println(args[i]);

    if (args.length < 3) {
        System.out.println(
        "USAGE: java SSLSocketClientWithClientAuth " +
        "host port requestedfilepath");
        System.exit(-1);
    }

    try {
        host = args[0];
        port = Integer.parseInt(args[1]);
        path = args[2];
    } catch (IllegalArgumentException e) {
         System.out.println("USAGE: java SSLSocketClientWithClientAuth " +
         "host port requestedfilepath");
         System.exit(-1);
    }

    try {

        /*
           * Set up a key manager for client authentication
           * if asked by the server.  Use the implementation's
           * default TrustStore and secureRandom routines.
           */
        SSLSocketFactory factory = null;
        try {
        SSLContext ctx;
        KeyManagerFactory kmf;
        KeyStore ks;
        char[] passphrase = "changeit".toCharArray();

        ctx = SSLContext.getInstance("TLSv1");
        kmf = KeyManagerFactory.getInstance("SunX509");

        ks = KeyStore.getInstance("JKS");

        ks.load(new FileInputStream("E:\\software\\workingdir\\http_channel_SSL\\Keystore.jks"), passphrase);
        SecureRandom random = new SecureRandom();
        random.nextInt();
        kmf.init(ks, passphrase);
        KeyManager[] managers = kmf.getKeyManagers();
            for (KeyManager manager : managers) {
                if (manager instanceof X509ExtendedKeyManager) {

                }
            }
            TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
            tmf.init(ks);
        ctx.init(managers, tmf.getTrustManagers(), random);

        factory = ctx.getSocketFactory();

        } catch (Exception e) {
        throw new IOException(e.getMessage());
        }

        SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
        socket.setNeedClientAuth(true);
        socket.setUseClientMode(true);
        /*
           * send http request
           *
           * See SSLSocketClient.java for more information about why
           * there is a forced handshake here when using PrintWriters.
           */
        //socket.setSoTimeout(1000000000);
        socket.startHandshake();

        PrintWriter out = new PrintWriter(
                  new BufferedWriter(
                  new OutputStreamWriter(
                       socket.getOutputStream())));
        out.println("GET " + path + " HTTP/1.0");
        out.println(message);
        out.println();
        out.flush();

       /* URL url = new URL(urlString);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.set;
        connection.setConnectTimeout(100000000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream os = connection.getOutputStream();
        os.write(message.getBytes());*/
        //execute(new PostMethodBuilder());
        /*
           * Make sure there were no surprises
           */
        /*if (out.checkError())
        System.out.println(
            "SSLSocketClient: java.io.PrintWriter error");*/

        /* read response */
        BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null)
        System.out.println(inputLine);

        in.close();
        //out.close();
        //socket.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

