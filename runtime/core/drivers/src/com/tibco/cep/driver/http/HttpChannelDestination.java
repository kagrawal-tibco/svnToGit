package com.tibco.cep.driver.http;

import java.util.Map;

import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.runtime.channel.JSONPayloadDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 2, 2008
 * Time: 6:22:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HttpChannelDestination extends HttpMethods, JSONPayloadDestination {

    void processMessage(HttpChannelServerRequest request, HttpChannelServerResponse response, String method) throws Exception;
    int sendMessage(HttpChannelClientRequest request, SimpleEvent requestEvent) throws Exception;
    int sendMessage(HttpChannelClient client, SimpleEvent requestEvent) throws Exception;
    SimpleEvent processResponse(HttpChannelClientResponse response, Map<String, Object> overrideData) throws Exception;
}
