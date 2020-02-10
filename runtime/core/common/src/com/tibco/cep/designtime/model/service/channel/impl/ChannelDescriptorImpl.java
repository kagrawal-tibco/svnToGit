package com.tibco.cep.designtime.model.service.channel.impl;


import java.util.LinkedHashMap;

import com.tibco.cep.designtime.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;

/*
* User: Nicolas Prade
* Date: Oct 1, 2009
* Time: 5:27:38 PM
*/


public class ChannelDescriptorImpl
        extends LinkedHashMap<String, PropertyDescriptor>
        implements ChannelDescriptor {


    private DestinationDescriptor destinationDescriptor;


    public ChannelDescriptorImpl(
            DestinationDescriptor destinationDescriptor) {
        this.destinationDescriptor = destinationDescriptor;
    }


    public DestinationDescriptor getDestinationDescriptor() {
        return this.destinationDescriptor;
    }


}
