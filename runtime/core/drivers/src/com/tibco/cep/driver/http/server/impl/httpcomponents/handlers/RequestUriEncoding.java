package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * A helper for http request URI encoding
 *
 * @author vjavere
 */
public class RequestUriEncoding {
    private String uriEncoding;

    private final String Default_ENCODING = "UTF-8";

    boolean useBodyEncoding = false;

    private LogManager m_LogManager;

    private Logger m_Logger;


    private void initialize(Properties beProperties) {
        String defaultEncoding = beProperties.getProperty(
                HttpChannelConstants.DEFAULT_ENCODING, Default_ENCODING);
        uriEncoding = beProperties.getProperty(HttpChannelConstants.URI_ENCODING, null);
        if (uriEncoding != null)
            useBodyEncoding = false;
        else {
            useBodyEncoding = Boolean.valueOf(beProperties.getProperty(
                    HttpChannelConstants.USE_BODY_ENCODING_FOR_URI, "false"));
            uriEncoding = defaultEncoding;

        }

        m_LogManager = LogManagerFactory.getLogManager();
        // Get the logger from LogManager
        m_Logger = m_LogManager.getLogger(RequestUriEncoding.class);

    }

    public RequestUriEncoding() {
        this(null);
    }

//	public RequestUriEncoding(Properties beProperties) {
//		//LogManagerFactory.getLogManager().getLogger(RequestUriEncoding.class).log(Level.INFO, ResourceManager.getInstance().formatMessage("decoding ", beProperties));
//		if (beProperties != null) {
//			initialize(beProperties);
//		}
//	}

    public RequestUriEncoding(HttpChannel httpChannel) {
        if (httpChannel != null) {
            initialize(httpChannel.getConfig().getProperties());
        }
    }

    public String getDecodedURI(HttpRequest request) throws UnsupportedEncodingException {
        String charset = null;

        if (useBodyEncoding) {
            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request)
                        .getEntity();
                charset = EntityUtils.getContentCharSet(entity);
            } else {
                Header firstHeader = request.getFirstHeader("content-type");
                if (firstHeader != null) {
                    HeaderElement[] values = firstHeader.getElements();
                    if (values.length > 0) {
                        NameValuePair param = values[0].getParameterByName("charset");
                        if (param != null) {
                            charset = param.getValue();
                        }
                    }
                }
            }
        } else {
            charset = uriEncoding;
        }

        if (charset == null || charset.isEmpty()) {
            charset = Default_ENCODING;
        }

        String decode = request.getRequestLine().getUri();

        m_Logger.log(Level.DEBUG, "Request Line URI of received request :" + decode);
        try {
            decode = URLDecoder.decode(decode, charset);
        } catch (Exception e) {
            m_Logger.log(Level.DEBUG, e, "Error happened while decoding request line URI : %1$s ", e.getMessage());
        }

        //LogManagerFactory.getLogManager().getLogger(RequestUriEncoding.class).log(Level.INFO, ResourceManager.getInstance().formatMessage("decoding ", charset));
        return decode;

    }

}