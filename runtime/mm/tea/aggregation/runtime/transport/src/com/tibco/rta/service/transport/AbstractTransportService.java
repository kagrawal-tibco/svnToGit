package com.tibco.rta.service.transport;

import com.tibco.rta.common.service.MessageContext;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/10/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTransportService<C extends AbstractRtaConnectorInfo> extends AbstractStartStopServiceImpl implements TransportService {
    
    protected C connectorInfo;

    protected void setConnectorInfo(C connectorInfo) {
        this.connectorInfo = connectorInfo;
    }
    
	@Override
	public void handleError(MessageContext context) {
		// TODO Auto-generated method stub
		
	}
}
