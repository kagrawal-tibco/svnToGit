package com.tibco.cep.driver.jms.serializer;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageEOFException;
import java.io.IOException;
import java.io.InputStream;


// Ugly stuff from txml
class BytesMessageInputStream
        extends InputStream
{


    private BytesMessage message;


    public BytesMessageInputStream(
            BytesMessage message)
    {
        this.message = message;
    }


    @Override
    public int read(
            byte b[],
            int off,
            int len)
            throws IOException
    {
        try {
            if (off == 0) {
                return this.message.readBytes(b, len);
            } else {
                final byte[] temp = new byte[len];
                final int n = this.message.readBytes(temp, len);
                if (n > 0) {
                    System.arraycopy(temp, 0, b, off, n);
                }
                return n;
            }
        } catch (JMSException e) {
            throw new IOException(e.getMessage());
        }
    }


    public int read()
            throws IOException
    {
        try {
            final byte b = this.message.readByte();
            return ((int) b) & 0xff; // make it unsigned
        } catch (MessageEOFException e) {
            return -1;
        } catch (JMSException e) {
            throw new IOException(e.getMessage());
        }
    }

}
