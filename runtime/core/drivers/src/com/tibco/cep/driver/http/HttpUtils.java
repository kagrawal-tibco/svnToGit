package com.tibco.cep.driver.http;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.util.ByteArrayBuffer;

import java.io.IOException;
import java.io.InputStream;

import static com.tibco.be.util.BEStringUtilities.convertToValidTibcoIdentifier;

/**
 * This class provides utility methods for Http Request and Response Handling 
 *
 */
public class HttpUtils {
	
	public static String DESTINATION_URI = "destinationURI";

	public static String HTTP_REQ_CORRELATION_ID = "correlationId";
	
	public static String HTTP_RESPONSE_SUCCESS_CALLBACK_URI = "successCallbackRuleFunctionURL";

    public static String HTTP_RESPONSE_ERROR_CALLBACK_URI = "errorCallbackRuleFunctionURL";
	
	public static String HTTP_RESPONSE_EVENT_TYPE = "responseEventType";
	/**
	 * This method builds the destination URL for dispatching from the Http Request URI.
	 * It appends the channel name to the destination name with a '/'
	 * @param requestURI - URI of the request
	 * @param destinationName - Destination Name
	 * @return
	 */
	public static String buildDestinationURL(String requestURI,
            String destinationName) {
		StringBuilder sb = new StringBuilder();
		//Get channel name from it
		String channelName = convertToValidTibcoIdentifier(requestURI, false);
		if (!channelName.startsWith("/")) {
			sb.append("/");
		}
		sb.append(channelName);
		sb.append("/");
		sb.append(destinationName);
		return sb.toString();
	}


    /**
     *
     * @param inputStream
     * @param contentLength
     * @return
     * @throws IOException
     */
    public static byte[] readAndFilterByteStream(InputStream inputStream,
                                                int contentLength) throws IOException {
        if (contentLength < 0) {
            contentLength = 4096;
        }
        ByteArrayBuffer buffer = new ByteArrayBuffer(contentLength);
        byte[] temp = new byte[4096];
        int l;
        int counter = 0;
        int offset = 0;
        while ((l = inputStream.read(temp)) != -1) {
            buffer.append(temp, 0, l);
            if (counter == 0) {
                for (int loop = 0; loop < buffer.length(); loop++) {
                    int b = buffer.byteAt(loop);
                    if (!((b & 0xff) < 0x10)) {
                        //Break loop since non CRLF encountered
                        break;
                    }
                    //Increment offset
                    offset++;
                }
                counter++;
            }
        }
        //Copy into different array from offset
        byte[] origArray = buffer.toByteArray();
        byte[] returnArray = new byte[origArray.length - offset];
        System.arraycopy(origArray, offset, returnArray, 0, returnArray.length);
        return returnArray;
    }
    
    /**
     * Check if the response entity is Gzip compressed, if yes, wrap the entity in a Gzip decompressor else return as is
     * @param entity
     * @return
     */
    public static HttpEntity decompressEntity(HttpEntity entity) {
    	Header contentEncodingHeader = entity.getContentEncoding();

    	if (contentEncodingHeader != null) {
    	    HeaderElement[] encodings = contentEncodingHeader.getElements();
    	    for (int i = 0; i < encodings.length; i++) {
    	        if (encodings[i].getName().equalsIgnoreCase("gzip")) {
    	            entity = new GzipDecompressingEntity(entity);
    	            break;
    	        }
    	    }
    	}
    	
    	return entity;
    }
}
