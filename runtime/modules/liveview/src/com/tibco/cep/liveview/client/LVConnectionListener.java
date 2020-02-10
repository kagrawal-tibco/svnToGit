/**
 * 
 */
package com.tibco.cep.liveview.client;

import com.streambase.liveview.client.ConnectionClosedEvent;
import com.streambase.liveview.client.ConnectionListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author vpatil
 *
 */
public class LVConnectionListener implements ConnectionListener {
	
	private Logger logger;
	
	private LVConnectionPool lvConnectionPool;
	
	public LVConnectionListener(LVConnectionPool lvConnectionPool) {
		this.lvConnectionPool = lvConnectionPool;
		this.logger = LogManagerFactory.getLogManager().getLogger(LVConnectionListener.class);
	}
	
	@Override
	public void connectionClosed(ConnectionClosedEvent connectionCloseEvent) {
		try {
			lvConnectionPool.removeFromPool(connectionCloseEvent.getConnection());
			logger.log(Level.DEBUG, "Connection close event detected. Removed stale connection.");
		} catch (Exception e) {
			String exceptionMessage = String.format("Error removing stale connection from the pool.");
			logger.log(Level.ERROR, exceptionMessage);
		}
	}
}
