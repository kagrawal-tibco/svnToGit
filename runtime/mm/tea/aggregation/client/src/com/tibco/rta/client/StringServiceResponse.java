package com.tibco.rta.client;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/1/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringServiceResponse extends ServiceResponse<String> {

    private String payload;


    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
