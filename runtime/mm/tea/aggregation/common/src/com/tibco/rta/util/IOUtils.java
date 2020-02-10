package com.tibco.rta.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class IOUtils {

    /**
     * @param inputStream
     * @param contentLength
     * @return
     * @throws java.io.IOException
     */
    public static byte[] readInputStream(InputStream inputStream, int contentLength) throws IOException {
        if (inputStream != null) {
            if (contentLength <= 0) {
                return new byte[0];
            }
            
            ReadableByteChannel channel = Channels.newChannel(inputStream);
            byte[] postData = new byte[contentLength];
            ByteBuffer buf = ByteBuffer.allocateDirect(contentLength);
            int numRead = 0;
            int counter = 0;
            while (numRead >= 0) {
                ((Buffer)buf).rewind();
                //Read bytes from the channel
                numRead = channel.read(buf);
                ((Buffer)buf).rewind();
                for (int i = 0; i < numRead; i++) {
                    postData[counter++] = buf.get();
                }
            }
            return postData;
        }
        return null;
    }

    /**
     * Convert to java object serialized bytes
     *
     * @return
     * @throws Exception
     */
    public static <S extends Serializable> byte[] serialize(S object) throws Exception {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ioe) {
            throw new Exception(ioe);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }

    /**
     * Deserialize java object
     * @param bytes
     * @param <S>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <S extends Serializable> S deserialize(byte[] bytes) throws Exception {
        return deserialize(new ByteArrayInputStream(bytes));
    }

    /**
     * Deserialize java object
     * @param inputStream
     * @param <S>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <S extends Serializable> S deserialize(InputStream inputStream) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(inputStream);
            Object object = ois.readObject();
            return (S)object;
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }
    
    /**
     * Utility method to clone objects
     * 
     * @param objectToClone
     * @return
     * @throws Exception
     */
    public static <S extends Serializable> S cloneObject(S objectToClone) throws Exception {
		byte[] serizlized = IOUtils.serialize(objectToClone);
		return IOUtils.deserialize(serizlized);
    }

    /**
     * A faster way of converting a <code>byte[]</code> to <code>String</code> object.
     * <p>
     *     This is especially useful when the buffer sizes are very large, where<code>String.getBytes(String)</code> is not optimal
     * </p>
     * @param bytesObject
     * @param encoding
     * @return
     */
    public static String convertByteArrayToString(Object bytesObject, String encoding) {
        if (!(bytesObject instanceof byte[])) {
            throw new IllegalArgumentException("Input should be a byte[]");
        }
        byte[] bytes = (byte[])bytesObject;
        CharsetDecoder decoder = Charset.forName(encoding).newDecoder();
        ByteBuffer bbuf = ByteBuffer.wrap(bytes);
        CharBuffer cBuff = CharBuffer.allocate(bbuf.capacity());
        decoder.decode(bbuf, cBuff, false);
        //Flip the buffer, and take the position to 0.
        cBuff.flip();
        return cBuff.toString();
    }

    /**
     * A faster way of converting a <code>String</code> to <code>byte[]</code> object.
     * <p>
     *     This is especially useful when the buffer sizes are very large, where<code>new String(bytes)</code> is not optimal
     * </p>
     * @param string
     * @param encoding
     * @return
     */
    public static byte[] convertStringToByteArray(String string, String encoding) throws CharacterCodingException {
        if (string == null) {
            return null;
        }
        CharsetEncoder encoder = Charset.forName(encoding).newEncoder();
        CharBuffer cBuff = CharBuffer.wrap(string);
        ByteBuffer byteBuffer = encoder.encode(cBuff);
        //No need to flip the returned buffer
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        return bytes;
    }
}
