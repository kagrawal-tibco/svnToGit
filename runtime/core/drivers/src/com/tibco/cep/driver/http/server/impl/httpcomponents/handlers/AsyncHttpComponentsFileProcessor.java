package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.nio.ContentDecoder;
import org.apache.http.nio.ContentDecoderChannel;
import org.apache.http.nio.FileContentDecoder;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.entity.ContentListener;
import org.apache.http.nio.entity.NFileEntity;

import com.tibco.cep.driver.http.server.utils.HttpChannelMimeMap;

/**
 * Request Processor to serve static html pages on the Asynchronous HTTP Core Server  
 * @author vjavere
 *
 */
public class AsyncHttpComponentsFileProcessor implements AsyncHttpComponentsRequestProcessor {

    /*
     * This class acts as a listener for the file transfer operation.
     * It is notified as the content is available for transfer
     */
    static class FileWriteListener implements ContentListener {
        private final File file;
        private final FileInputStream inputFile;
        private final FileChannel fileChannel;
        private final boolean useFileChannels;
        private long idx = 0;
        
        /**
         * Constructs a file write listener 
         * @param useFileChannels 
         * @throws IOException
         */
        public FileWriteListener(boolean useFileChannels) throws IOException {
            this.file = File.createTempFile("tmp", ".tmp", null);
            this.inputFile = new FileInputStream(file);
            this.fileChannel = inputFile.getChannel();
            this.useFileChannels = useFileChannels;
        }

        /**
         * This method is called when the content is made available
         * @param decoder decoder for the file content
         * @param ioctrl I/O operation notification controller
         */
        public void contentAvailable(ContentDecoder decoder, IOControl ioctrl)
                throws IOException {
            long transferred;
            if(useFileChannels && decoder instanceof FileContentDecoder) {
                transferred = ((FileContentDecoder) decoder).transfer(
                        fileChannel, idx, Long.MAX_VALUE);
            } else {
                transferred = fileChannel.transferFrom(
                        new ContentDecoderChannel(decoder), idx, Long.MAX_VALUE);
            }

            if(transferred > 0)
                idx += transferred;
        }
        
        /**
         * This method is called when the file content transfer is done
         */
        public void finished() {
            try {
                inputFile.close();
            } catch(IOException ignored) {}
            try {
                fileChannel.close();
            } catch(IOException ignored) {}
        }

    }
    private final String docRoot;

    private final boolean useFileChannels;
    private String defaultFileName; 
    private HttpChannelMimeMap mimeMap;
    /**
     * Construct the static file handler with location of static content files and a flag to indicate whether to
     * use NIO based file channels
     * @param docRoot location of static files
     * @param useFileChannels boolean to indicate whether to use file channels
     */
    public AsyncHttpComponentsFileProcessor(final String docRoot, boolean useFileChannels) {
        this.docRoot = docRoot;
        this.useFileChannels = useFileChannels;
        this.mimeMap = new HttpChannelMimeMap();
    }
    
    /**
     * The handler method to serve the files located on docRoot, if file does not exist it returns a
     * 404 HTTP error status response. If the file does not have read permission is returns access denied error
     *  @param request HTTP request
     *  @param response HTTP response
     */
    public void process(
            final ASyncHttpComponentsRequest request,
            final ASyncHttpComponentsResponse response) throws HttpException, IOException {
        String target = request.getRequestURI();
        File file = null;
        if(target.equals("/") && this.defaultFileName != null) {
        	file = new File(this.docRoot,this.defaultFileName);
        } else {
        	file = new File(this.docRoot, URLDecoder.decode(target, "UTF-8"));
        }
        if (!file.exists()) {
           AsyncNHttpErrorHandler.sendErrorResponse(response, AsyncNHttpErrorHandler.RESOURCE_NOT_AVAILABLE + file.getName(),HttpStatus.SC_NOT_FOUND);
        } else if (!file.canRead() || file.isDirectory()) {
            AsyncNHttpErrorHandler.sendErrorResponse(response, AsyncNHttpErrorHandler.ACCESS_DENIED + file.getName(), HttpStatus.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpStatus.SC_OK);
            NFileEntity entity = new NFileEntity(file, mimeMap.getMimeTypeForFile(file.getName()) , useFileChannels);
            response.setEntity(entity);
            response.sendResponse();
        }

    }
	public String getDefaultFileName() {
		return defaultFileName;
	}

	public void setDefaultFileName(String defaultFileName) {
		this.defaultFileName = defaultFileName;
	}

}


