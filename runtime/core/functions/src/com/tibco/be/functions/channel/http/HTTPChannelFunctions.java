package com.tibco.be.functions.channel.http;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;

import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.driver.http.HttpDestination.HttpMessageContext;
import com.tibco.cep.driver.http.client.HTTPConnectionInfo;
import com.tibco.cep.driver.http.client.impl.httpcomponents.HttpComponentsClientService;
import com.tibco.cep.driver.http.server.utils.SSLUtils;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.net.mime.Base64Codec;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "HTTP",
        synopsis = "HTTP Client functions")
public class HTTPChannelFunctions {

    public enum SupportedStoreTypes {
        JKS, PKCS12;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getPostData",
        signature = "Object getPostData(SimpleEvent event)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "Request event on HTTP channel/destination")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The post data"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the payload of a SimpleEvent as a byte[] object that can be used as HTTP POST data.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static Object getPostData(SimpleEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Input event cannot be null");
        }
        EventPayload eventPayload = event.getPayload();

        if (eventPayload != null) {
            try {
                return eventPayload.toBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "toBase64",
        signature = "String toBase64(String content)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "content", type = "String", desc = "The content to encode into base64.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Base64 encoded string."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Encodes a String to Base64.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String encodeBase64(String content) {
        try {
        	byte[] bytes = content.getBytes("UTF-8");
            return Base64Codec.encodeBase64(bytes);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "fromBase64",
        signature = "String fromBase64(String content, String encoding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "content", type = "String", desc = "The base64 content to decode"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The encoding which will be $1UTF-8$1 if null or empty value specified.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Decoded from base64."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Decodes a String from Base64.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String decodeBase64(String content, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = "UTF-8";
        }
        try {
            return Base64Codec.decodeBase64(content, encoding);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "encodeURL",
        signature = "String encodeURL(String url, String encoding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "url", type = "String", desc = "The URL content to encode."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The encoding which will be $1UTF-8$1 if null or empty value specified.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The encoded url."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Encodes a URL",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String encodeURL(String url, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = "UTF-8";
        }
        try {
            return URLEncoder.encode(url, encoding);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "decodeURL",
        signature = "String decodeURL(String url, String encoding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "url", type = "String", desc = "The URL content to decode."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "The encoding which will be $1UTF-8$1 if null or empty value specified.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The decoded url."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Decodes a URL.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String decodeURL(String url, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = "UTF-8";
        }
        try {
            return URLDecoder.decode(url, encoding);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sendEvent",
        signature = "Event sendEvent(SimpleEvent requestEvent, String responseEventURI, long timeoutMillis, String url)",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.channel.sendEvent", value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEvent", type = "SimpleEvent", desc = "The input event to send"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEventURI", type = "String", desc = "URI of event to be created once response received"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeoutMillis", type = "long", desc = "time out for the operation, -1 signifies wait forever."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "url", type = "String", desc = "The endpoint URL to send the request. The default destination of the requestEvent would be appended to the URL to form the final endpoint URL.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = "event The response event"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Send a synchronous HTTP request. The requestEvent's default destination URI is appended to the URL. The requestEvent properties and payload are converted to HTTP headers and POST data respectively.\nIf event payload is null, HTTP GET will be used, else HTTP POST will be used.\nThis will return an event of type responseEventURI as a response encapsulating response headers as properties and post data if any as event payload.\nThis function can also be used to send SOAP requests. For SOAP requests, the SOAPAction will be taken from a corresponding event property or if not present,\nthe default destination of the request event will be used.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Event sendEvent(SimpleEvent requestEvent, String responseEventURI, long timeoutMillis, String url) {
        try {
            return HttpComponentsClientService.sendEvent(requestEvent, responseEventURI, timeoutMillis, url);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createKeystore",
        signature = "Object createKeystore(String ksFilePath, String ksType, String ksPassword)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ksFilePath", type = "String", desc = "The absolute path of keystore file."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ksType", type = "String", desc = "The type of keystore. JKS, and PKCS12 supported."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ksPassword", type = "String", desc = "Obfuscated password for the keystore")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the Keystore object"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates and returns a Keystore object using the given parameters.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createKeystore(String ksFilePath,
                                        String ksType,
                                        String ksPassword) {
        KeyStore keyStore;

        if (ksFilePath == null) {
            throw new IllegalArgumentException("File path for keystore cannot be null");
        }
        File ksFile = new File(ksFilePath);
        if (!ksFile.exists()) {
            throw new IllegalArgumentException("File path for keystore does not exist");
        }
        //Check if the file can be read, and not a directory
        if (ksFile.isDirectory()) {
            throw new IllegalArgumentException("The keystore file path cannot be a directory");
        }
        if (!ksFile.canRead()) {
            throw new IllegalArgumentException("The keystore file specified cannot be read");
        }
        if (ksPassword == null) {
            throw new IllegalArgumentException("The keystore password cannot be null");
        }
        //Upper case the keystore type
        ksType = ksType.toUpperCase();

        try {
            SupportedStoreTypes supportedStoreTypes = SupportedStoreTypes.valueOf(ksType);
        } catch (Exception e) {
            throw new RuntimeException(ksType + " is not a supported keystore type");
        }

        try {
            char[] password = null;
            if (ObfuscationEngine.hasEncryptionPrefix(ksPassword)) {
                //Deobfuscate the password
                password = ObfuscationEngine.decrypt(ksPassword);
            } else {
                password = ksPassword.toCharArray();
            }
            keyStore = KeyStore.getInstance(ksType);
            //Initialize the store
            FileInputStream fis = new FileInputStream(ksFile);

            keyStore.load(fis, password);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (AXSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyStore;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "readCertificateFromKeystore",
        signature = "Object readCertificateFromKeystore(Object keystore, String alias)",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.channel.readCertificateFromKeystore", value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "keystore", type = "Object", desc = "Keystore object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "alias", type = "String", desc = "Alias associated to the keystore")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "An object representing the certificate"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a certificate from the keystore using this alias.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object readCertificateFromKeystore(Object keystore,
                                                     String alias) {
        Certificate certificate;

        if (!(keystore instanceof KeyStore)) {
            throw new IllegalArgumentException("Invalid keystore argument");
        }
        KeyStore ks = (KeyStore) keystore;

        try {
            if (!ks.isCertificateEntry(alias)) {
                throw new IllegalArgumentException("No certificate for alias " + alias);
            }
            //Retrieve the cert
            certificate = ks.getCertificate(alias);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        return certificate;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "loadTrustedCertificates",
        signature = "Object loadTrustedCertificates(String trustedCertsFolder, String passwordToSet)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "trustedCertsFolder", type = "String", desc = "The relative path of the folder containing the certificates. Certificates folder has to be inside project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "passwordToSet", type = "String", desc = "The password to check the integrity of the keystore")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "certificates keystore object"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Loads and returns a KeyStore from the trusted certificates folder.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object loadTrustedCertificates(String trustedCertsFolder,
                                                 String passwordToSet) {
        final DeployedProject project = RuleServiceProviderManager.getInstance().getDefaultProvider().getProject();
        final ArchiveResourceProvider provider = project.getSharedArchiveResourceProvider();
        try {
            final KeyStore keyStore = SSLUtils.createKeystore(
                    trustedCertsFolder, passwordToSet, provider, project.getGlobalVariables(), true);
            return keyStore;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "sendRequest",
        signature = "Event sendRequest(String url, SimpleEvent requestEvent, String responseEventURI, long timeoutMillis, Object httpConnectionInfo)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "url", type = "String", desc = "The URL for the endpoint."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEvent", type = "SimpleEvent", desc = "The input event to send"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "responseEventURI", type = "String", desc = "URI of event to be created once response received"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeoutMillis", type = "long", desc = "time out for the operation, -1 signifies wait forever."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "httpConnectionInfo", type = "Object", desc = "HTTP Connection Info object"),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = "event The response event"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends a secure/non secure synchronous HTTP request. "
                + "The event properties are converted to HTTP headers. "
                + "The event payload is converted to POST data. "
                + "Default behavior, if the event payload is null, HTTP GET will be used, else HTTP POST will be used. Alternatively, if HTTP.ConnectionInfo.setHttpMethod is set, then that method type will be used."
                + "This will return an event of type responseEventURI as a response encapsulating "
                + "response headers as properties and post data if any as event payload."
                + "<p>This function can also be used to send SOAP requests. "
                + "For SOAP requests, the SOAPAction will be taken from a corresponding event property, "
                + "or, if the property is not present, the default destination of the request event.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Event sendRequest(String url, SimpleEvent requestEvent, String responseEventURI, long timeoutMillis, Object httpConnectionInfo) {
        try {
        	if (httpConnectionInfo == null) {
        		throw new IllegalArgumentException("connectionInfo cannot be null.");
        	}
        	if (!(httpConnectionInfo instanceof HTTPConnectionInfo)) {
        		throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
        	}

        	return HttpComponentsClientService.sendRequest(url, requestEvent, responseEventURI, timeoutMillis, (HTTPConnectionInfo) httpConnectionInfo);
        } catch (Exception ex) {
            throw (ex instanceof RuntimeException) ? (RuntimeException)ex : new RuntimeException(ex);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "sendAsynchronousRequest",
        signature = "String sendAsynchronousRequest(String url, SimpleEvent requestEvent, String correlationId, "
                + "String successCallbackRuleFunctionURL, String errorCallbackRuleFunctionURL, Object httpConnectionInfo)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "url", type = "String", desc = "The URL for the endpoint."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEvent", type = "SimpleEvent", desc = "The input event to send"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "correlationId", type = "String", desc = "An optional id to correlate request and response."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "successCallbackRuleFunctionURL", type = "String", desc = "The fully qualified path of a rule function to be invoked for success case."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "errorCallbackRuleFunctionURL", type = "String", desc = "Error case callback rulefunction. Can be null."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "httpConnectionInfo", type = "Object", desc = "HTTP Connection Info object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "the correlation id"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sends an secure/non secure asynchronous HTTP request. "
        		+ "The event payload is converted to POST data. "
                + "Default behavior, if the event payload is null, HTTP GET will be used, else HTTP POST will be used. Alternatively, if HTTP.ConnectionInfo.setHttpMethod is set, then that method type will be used."
                + "The requesting thread continues processing. "
                + "The response is handled asynchronously in the callback rule function. "
                + "The signature of the success RuleFunction should be "
                + "$1void ruleFunction (String correlationId, Event request, Event response$1) "
                + "whereas that of the error RuleFunction should be "
                + "$1void ruleFunction (String correlationId, Event request, Exception exception)$1 "
                + "where the request event is the event that initiated the request, "
                + "the response event is the HTTP response mapped to an Event and "
                + "correlationId is the correlationId that was specified in the original request "
                + "or that was returned by the sendAsynchronousRequest function. "
                + "<p>This function can also be used in SOAP requests. "
                + "For SOAP requests, the SOAPAction will be taken from a corresponding event property, "
                + "or, if this not present, the default destination of the request event will be used.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String sendAsynchronousRequest(String url,
                                                 SimpleEvent requestEvent,
                                                 String correlationId,
                                                 String successCallbackRuleFunctionURL,
                                                 String errorCallbackRuleFunctionURL,
                                                 Object httpConnectionInfo) {
    	try {
    		if (httpConnectionInfo == null) {
    			throw new IllegalArgumentException("connectionInfo cannot be null.");
    		}
    		if (!(httpConnectionInfo instanceof HTTPConnectionInfo)) {
    			throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
    		}

    		return HttpComponentsClientService.sendAsyncRequest(url,
    				requestEvent,
    				correlationId,
    				successCallbackRuleFunctionURL,
    				errorCallbackRuleFunctionURL,
    				(HTTPConnectionInfo) httpConnectionInfo);
        } catch (Exception ex) {
        	throw (ex instanceof RuntimeException) ? (RuntimeException)ex : new RuntimeException(ex);
        }
    }

	@com.tibco.be.model.functions.BEFunction(
        name = "getRequestURI",
        signature = "String getRequestURI(SimpleEvent requestEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestEvent", type = "SimpleEvent", desc = "The input event to send")          
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Request URI"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the request URI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getRequestURI(SimpleEvent requestEvent) {
		EventContext eventContext = requestEvent.getContext();
		if (eventContext instanceof HttpMessageContext) {
			HttpMessageContext httpMsgContext = (HttpMessageContext) eventContext;

			return httpMsgContext.getRequest().getRequestURI();
		}

		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(encodeBase64("admin"));
	}
}
