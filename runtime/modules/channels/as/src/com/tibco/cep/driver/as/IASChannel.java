package com.tibco.cep.driver.as;

import com.tibco.as.space.Metaspace;
import com.tibco.cep.runtime.channel.Channel;

public interface IASChannel extends Channel {

	Metaspace getMetaspace();

}
