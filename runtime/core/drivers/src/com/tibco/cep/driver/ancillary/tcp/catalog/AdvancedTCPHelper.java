package com.tibco.cep.driver.ancillary.tcp.catalog;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.tcp.ReaderImpl;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Communication",
        category = "TCP.Advanced",
        synopsis = "Advanced TCP client/server communication functions that allow direct access to the underlying Java\ninfrastructure.")
/*
* Author: Ashwin Jayaprakash Date: Apr 2, 2009 Time: 10:37:06 AM
*/
public class AdvancedTCPHelper {
    @com.tibco.be.model.functions.BEFunction(
        name = "getReaderInputStream",
        synopsis = "Returns the underlying java.io.InputStream of the session.",
        signature = "Object getReaderInputStream(String sessionNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Cast to java.io.InputStream"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getReaderInputStream(String sessionNickName) {
        Session session = TCPHelper.ALL_SESSIONS.get(sessionNickName);

        Reader reader = session.getReader();
        if (reader instanceof ReaderImpl) {
            ReaderImpl readerImpl = (ReaderImpl) reader;

            return readerImpl.getInputStream();
        }

        throw new RuntimeException(
                "Unable to retrieve the InputStream from the Reader associated with the Session.");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "readIntoByteArray",
        synopsis = "Reads the information from the TCP stream directly into the byte array provided.",
        signature = "int readIntoByteArray(String sessionNickName, int maxNumBytesToRead, Object byteArray)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxNumBytesToRead", type = "int", desc = "reading stops and returns the data collected so far."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "byteArray", type = "Object", desc = "into which the data will be read from offset 0. (For use in custom Java code).")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "<code>-1</code> if End-of-stream is reached or the number of bytes that were read into the array\npassed."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int readIntoByteArray(String sessionNickName, int maxNumBytesToRead,
                                        Object byteArray) {
        if ((byteArray instanceof byte[]) == false) {
            throw new IllegalArgumentException(
                    TCPHelper.MSGPRE_BYTE_ARRAY_ARG_WRONG + maxNumBytesToRead);
        }

        byte[] bytes = (byte[]) byteArray;
        if (bytes.length < maxNumBytesToRead) {
            throw new IllegalArgumentException(
                    TCPHelper.MSGPRE_BYTE_ARRAY_ARG_WRONG + maxNumBytesToRead);
        }

        //------------

        Session session = TCPHelper.ALL_SESSIONS.get(sessionNickName);

        Reader reader = session.getReader();
        try {
            boolean eof = false;
            int offset = 0;
            int remainingToRead = maxNumBytesToRead;

            for (; remainingToRead > 0;) {
                int numRead = reader.read(bytes, offset, remainingToRead);

                //EOF.
                if (numRead == -1) {
                    eof = true;
                    break;
                }

                offset = offset + numRead;
                remainingToRead = remainingToRead - numRead;
            }

            if (eof && offset == 0) {
                return -1;
            }

            return offset;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
