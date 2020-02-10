package com.tibco.rta.client;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/1/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BytesServiceResponse extends ServiceResponse<byte[]> {

    private byte[] payload;

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getPayload() {
        return payload;
    }
}
