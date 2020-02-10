package com.tibco.cep.designtime.model.service.channel.mutable;


import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.service.channel.Channel;


/**
 * A MutableChannel can be thought of as the "medium" through which Events flow.
 *
 * @author ishaan
 * @version Apr 28, 2004 6:31:16 PM
 */

public interface MutableChannel extends Channel, MutableEntity {


    public MutableChannel setDriver(String driverClassName);


    public MutableChannel setDriver(MutableDriverConfig Driver);

}//interface
