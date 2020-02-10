package com.tibco.cep.driver.jms;

import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * This class creates a JMSDestination, used temporarily for serialization related actions.
 * @author moshaikh
 *
 */
public class JMSTemporaryDestination extends JMSDestination {

	public JMSTemporaryDestination(BaseJMSChannel channel, DestinationConfig config) {
		super(channel, config);
	}
	
	public JMSTemporaryDestination(BaseJMSChannel channel, ExpandedName responseEvent, String serializerClass) throws Exception {
		this(channel, new JMSTempDestinationConfig(responseEvent, serializerClass));
	}
}
