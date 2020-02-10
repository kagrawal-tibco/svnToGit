package com.tibco.cep.designtime.model.service.channel;


import java.util.Map;

/*
* User: Nicolas Prade
* Date: Oct 1, 2009
* Time: 4:57:01 PM
*/


public interface ChannelDescriptor
        extends Map<String, PropertyDescriptor> {


    DestinationDescriptor getDestinationDescriptor();


}
