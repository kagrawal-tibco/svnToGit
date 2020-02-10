package com.tibco.rta.common.service;

import com.tibco.rta.model.runtime.ServerConfiguration;

import java.util.Collection;

public interface RuntimeService extends StartStopService {

	Collection<ServerConfiguration> getRuntimeConfiguration();

}
