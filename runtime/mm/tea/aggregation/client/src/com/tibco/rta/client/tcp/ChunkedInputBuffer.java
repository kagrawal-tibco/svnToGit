package com.tibco.rta.client.tcp;

import com.tibco.rta.client.BytesServiceResponse;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.util.ParserCursor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/1/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 * TODO use an event based approach similar to SAX and notify
 * TODO listeners about chunk events.
 */
public class ChunkedInputBuffer {

    private static final int CR = '\r';

    private ServiceInvocationListener serviceInvocationListener;

    public ChunkedInputBuffer(ServiceInvocationListener serviceInvocationListener) {
        this.serviceInvocationListener = serviceInvocationListener;
    }

    /**
     * Call upon every socket read
     * @param bytes
     */
    public void transfer(byte[] bytes) {
        ParserCursor parserCursor = new ParserCursor(0, bytes.length);
        BytesServiceResponse bytesServiceResponse;

        for (int loop = 0, length = bytes.length; loop <= length; loop++) {
            if (loop == length || CR == bytes[loop]) {
                int parserPos = parserCursor.getPos();
                int chunkLength = loop - parserPos;

                byte[] intermediate = new byte[chunkLength];
                System.arraycopy(bytes, parserPos, intermediate, 0, chunkLength);
                //Update position of cursor
                parserCursor.updatePos(loop);
                bytesServiceResponse = new BytesServiceResponse();
                bytesServiceResponse.setPayload(intermediate);

                if (serviceInvocationListener != null) {
                    serviceInvocationListener.serviceInvoked(bytesServiceResponse);
                }
            }
        }
    }
}
