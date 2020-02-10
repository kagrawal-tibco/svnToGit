package com.tibco.rta.service.rules;

import java.util.Properties;

public interface SnapshotRuleEvalService {

	void init(Properties configuration) throws Exception;

	void start();

	void stop();

}
