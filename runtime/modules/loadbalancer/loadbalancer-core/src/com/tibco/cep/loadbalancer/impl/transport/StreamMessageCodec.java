package com.tibco.cep.loadbalancer.impl.transport;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.loadbalancer.message.MessageCodec;
import com.tibco.cep.loadbalancer.util.CustomObjectInputStream;
import com.tibco.cep.util.DirectAccessByteArrayOS;
import com.tibco.cep.util.ReusableDirectAccessByteArrayIS;
import com.tibco.cep.util.ReusableDirectAccessByteArrayOS;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Apr 9, 2010 / Time: 4:09:38 PM
*/

public class StreamMessageCodec implements MessageCodec {
    /**
     * {@value}
     */
    public static final int HEADER_MAGIC_NUMBER = 0xA6B7C8D9;

    protected Encoder encoder;

    protected Decoder decoder;

    public StreamMessageCodec() {
        this.encoder = new Encoder();
        this.decoder = new Decoder();
    }

    /**
     * @return
     * @see #HEADER_MAGIC_NUMBER
     */
    @Override
    public int getMagicHeader() {
        return HEADER_MAGIC_NUMBER;
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        decoder.setClassLoader(classLoader);
    }

    /**
     * @param data
     * @param offset
     * @param length
     * @return
     * @throws StreamDecodeComboException
     */
    @Override
    public Object read(byte[] data, int offset, int length) throws StreamDecodeComboException {
        return decoder.read(data, offset, length);
    }

    @Override
    public int write(Object message, OutputStream outputStream) throws IOException {
        return encoder.write(message, outputStream);
    }

    @Override
    public void discard() {
        if (encoder != null)
            encoder.discard();
        encoder = null;

        if (decoder != null)
            decoder.discard();
        decoder = null;
    }

    //-----------------

    public static class Encoder {
        /**
         * First is the {@link #HEADER_MAGIC_NUMBER} and then the length (integer) of the content in bytes. Finally the
         * messages bytes.
         */
        protected ReusableDirectAccessByteArrayOS byteArrayOS;

        protected ByteBuffer byteBufferToWriteMsgLength;

        public Encoder() {
            this.byteArrayOS = new ReusableDirectAccessByteArrayOS();
            this.byteBufferToWriteMsgLength = prepMsgLengthBuffer(this.byteArrayOS.toByteArray());
        }

        private static ByteBuffer prepMsgLengthBuffer(byte[] sourceArray) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(sourceArray);
            ((Buffer)byteBuffer).position(4);
            ((Buffer)byteBuffer).mark();

            return byteBuffer;
        }

        private static void writeInt(int v, OutputStream stream) throws IOException {
            stream.write((v >>> 24) & 0xFF);
            stream.write((v >>> 16) & 0xFF);
            stream.write((v >>> 8) & 0xFF);
            stream.write((v >>> 0) & 0xFF);
        }

        public int write(Object message, OutputStream outputStream) throws IOException {
            try {
                writeInt(HEADER_MAGIC_NUMBER, byteArrayOS);

                //Blank out the first 4 bytes so that we can write the length later.
                byteArrayOS.write(0);
                byteArrayOS.write(0);
                byteArrayOS.write(0);
                byteArrayOS.write(0);

                //-----------------

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOS);
                objectOutputStream.writeObject(message);
                objectOutputStream.close();

                //-----------------

                int totalContentLength = byteArrayOS.size();

                //-----------------

                byte[] bytes = byteArrayOS.toByteArray();
                //ByteAOS has expanded, so the underlying array is new.
                if (byteBufferToWriteMsgLength.array() != bytes) {
                    //Re-wrap around the new array.
                    byteBufferToWriteMsgLength = prepMsgLengthBuffer(bytes);
                }

                //-----------------

                //Exclude the header and length fields.
                int actualMsgLength = totalContentLength - 8;
                //Write the length of the content at the beginning of the array.
                byteBufferToWriteMsgLength.putInt(actualMsgLength);

                outputStream.write(bytes, 0, totalContentLength);

                //-----------------

                return totalContentLength;
            }
            finally {
                ((Buffer)byteBufferToWriteMsgLength).reset();
                byteArrayOS.reset();
            }
        }

        public void discard() {
            try {
                byteArrayOS.close();
                byteArrayOS = null;
            }
            catch (IOException e) {
            }

            byteBufferToWriteMsgLength = null;
        }
    }

    public static class Decoder {
        protected int byteCounter;

        protected ByteBuffer byteCounterByteBuffer;

        protected DirectAccessByteArrayOS actualMessageBytes;

        protected ReusableDirectAccessByteArrayIS tempIS;

        protected ClassLoader classLoader;

        public Decoder() {
            this.byteCounter = 0;
            this.byteCounterByteBuffer = ByteBuffer.allocate(8);

            this.actualMessageBytes = new DirectAccessByteArrayOS();

            this.tempIS =
                    new ReusableDirectAccessByteArrayIS(actualMessageBytes.toByteArray(), 0, actualMessageBytes.size());
        }

        public void setClassLoader(ClassLoader classLoader) {
            if (this.classLoader == null) {
                this.classLoader = classLoader;
            }
        }

        /**
         * @param data
         * @param offset
         * @param length
         * @return Single message or a {@link List} or <code>null</code>.
         * @throws StreamDecodeComboException
         */
        public Object read(byte[] data, int offset, int length) throws StreamDecodeComboException {
            int localLength = length;
            int localOffset = offset;

            LinkedList<Object> retList = null;
            Object retVal = null;

            for (; localLength > 0;) {
                if (byteCounter == 0) {
                    int remaining = byteCounterByteBuffer.remaining();

                    int m = Math.min(remaining, localLength);
                    byteCounterByteBuffer.put(data, localOffset, m);

                    //Received all 8 bytes for reconstructing the header and length.
                    if (byteCounterByteBuffer.remaining() == 0) {
                        ((Buffer)byteCounterByteBuffer).flip();

                        int msgHeader = byteCounterByteBuffer.getInt();
                        if (msgHeader != HEADER_MAGIC_NUMBER) {
                            throw new StreamDecodeComboException("Encountered a corrupt message header on the stream",
                                    (retList == null) ? retVal : retList);
                        }

                        byteCounter = byteCounterByteBuffer.getInt();
                        ((Buffer)byteCounterByteBuffer).clear();
                    }

                    localOffset = localOffset + m;
                    localLength = localLength - m;
                }

                if (localLength > 0) {
                    int c = Math.min(localLength, byteCounter);

                    actualMessageBytes.write(data, localOffset, c);

                    byteCounter = byteCounter - c;
                    localOffset = localOffset + c;
                    localLength = localLength - c;
                }

                if (byteCounter == 0) {
                    Object o = null;

                    try {
                        o = onEntireMessageReceived();
                    }
                    catch (Exception e) {
                        throw new StreamDecodeComboException(e, (retList == null) ? retVal : retList);
                    }

                    if (retList == null) {
                        if (retVal == null) {
                            retVal = o;
                        }
                        else {
                            retList = new LinkedList<Object>();
                            retList.add(retVal);
                            retList.add(o);

                            retVal = null;
                        }
                    }
                    else {
                        retList.add(o);
                    }
                }
            }

            return (retList == null) ? retVal : retList;
        }

        private Object onEntireMessageReceived() throws ClassNotFoundException, IOException {
            Object returnVal = null;

            try {
                int s = actualMessageBytes.size();

                if (s > 0) {
                    tempIS.reuse(actualMessageBytes.toByteArray(), 0, s);

                    CustomObjectInputStream ois = new CustomObjectInputStream(tempIS);
                    ois.setCustomClassLoader(classLoader);
                    returnVal = ois.readObject();

                    tempIS.close();
                }
            }
            finally {
                actualMessageBytes.reset();
            }

            return returnVal;
        }

        public void discard() {
            byteCounterByteBuffer = null;

            try {
                actualMessageBytes.close();
                actualMessageBytes = null;
            }
            catch (IOException e) {
            }

            try {
                tempIS.close();
                tempIS = null;
            }
            catch (IOException e) {
            }
        }
    }

    /**
     * If the decoder has accumulated a few small messages and and is still decoding the next message from the same
     * array of bytes, then see {@link #getBatchValues()} to retrieve them with the exception.
     */
    public static class StreamDecodeComboException extends Exception {
        protected transient Object batchValues;

        public StreamDecodeComboException(Object batchValues) {
            this.batchValues = batchValues;
        }

        public StreamDecodeComboException(String message, Object batchValues) {
            super(message);
            this.batchValues = batchValues;
        }

        public StreamDecodeComboException(String message, Throwable cause, Object batchValues) {
            super(message, cause);
            this.batchValues = batchValues;
        }

        public StreamDecodeComboException(Throwable cause, Object batchValues) {
            super(cause);
            this.batchValues = batchValues;
        }

        @Optional
        public Object getBatchValues() {
            return batchValues;
        }
    }

    @Override
    public void reset() {
        if (encoder == null) {
            encoder = new Encoder();
        }
        if (decoder == null) {
            decoder = new Decoder();
        }
    }
}
