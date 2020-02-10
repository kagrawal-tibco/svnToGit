package com.tibco.cep.dashboard.integration.be;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKeys;
import com.tibco.cep.dashboard.config.PropertyKey.DATA_TYPE;

public interface BEIntegrationProperties extends PropertyKeys {

	public PropertyKey AUTO_CHANNEL_CREATION_ENABLE = new PropertyKey("auto.channelscreation.enable","Decides whether to auto create channels needed for outside communication",DATA_TYPE.Boolean,Boolean.TRUE);

	public PropertyKey HTTP_SHARED_RESOURCE_REF = new PropertyKey("http.shared.resource.ref","The reference of the http shared resource to use for http configuration", DATA_TYPE.String, null);

}
