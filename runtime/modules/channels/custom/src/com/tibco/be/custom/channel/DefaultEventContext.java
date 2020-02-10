package com.tibco.be.custom.channel;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Default implementation for the EventContext.
 * If user does not provide implementation for a custom EventContext , DefaultEventContext will be used.
 * @see BaseDestination#getEventContext(Event)
 * @.category public-api
 * @since 5.4
 */


public class DefaultEventContext implements EventContext{
	
	/**
	 * The associated event
	 * @.category public-api
	 */
	Event event;
	
	/**
	 * logger
	 * @.category public-api
	 */
	Logger logger=null;
	
	/**
	 * Instance of the associated destination
	 * @.category public-api
	 */
	BaseDestination destination;

	/**
	 * Initializes an instance of <code>DefaultEventContext</code>
	 * 
	 * @param event Event the associated event
	 * @param destination the instance of the underlying transport.
	 * @.category public-api
	 * 
	 */
	public DefaultEventContext(Event event,BaseDestination destination){
		this.destination=destination;
		this.logger=destination.getLogger();
		this.event=event;
	}
	
	/**
	 * Default implementation for reply.Does nothing.
	 * @param replyEvent Reply event
	 * @.category public-api
	 */
	@Override
	public boolean reply(Event replyEvent) {
		logger.log(Level.DEBUG, "DefaultEventContext:Reply [%s]", destination.getUri());
		return true;
	}
	
	/**
	 * Default implementation for getDestination.Returns the associated destination.
	 * 
	 * @return BaseDestination destination.
	 * @.category public-api
	 */
	@Override
	public BaseDestination getDestination() {
		return destination;
	}
	
	/**
	 * Default implementation for acknowledge.Does nothing.
	 * @.category public-api
	 */
	@Override
	public void acknowledge() {
		logger.log(Level.DEBUG, "DefaultEventContext:Acknowledge [%s]", destination.getUri());
	}
	
	/**
	 * Default implementation for rollback.Does nothing.
	 * @.category public-api
	 */
	@Override
	public void rollback() {
		logger.log(Level.DEBUG, "DefaultEventContext:Rollback [%s]", destination.getUri());
	}
	
	/**
	 * Default implementation for getMessage.Does nothing/returns null.
	 * 
	 * @return Object defaults to null.
	 * @.category public-api
	 */
	@Override
	public Object getMessage() {
		return null;
	}

}
