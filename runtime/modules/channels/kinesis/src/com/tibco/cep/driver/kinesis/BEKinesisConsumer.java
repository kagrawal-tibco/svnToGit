package com.tibco.cep.driver.kinesis;

import com.tibco.cep.driver.kinesis.KinesisDestination;
import com.tibco.cep.kernel.service.logging.Logger;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;
import com.tibco.cep.driver.kinesis.KinesisProcessorFactory;

import java.util.*;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.*;
import com.tibco.cep.kernel.service.logging.Level;

public class BEKinesisConsumer implements Runnable {
		
	private KinesisDestination ownerDestination;
	private Worker worker;
	private Logger logger;
	private KinesisChannel kinesischannel;
	private IRecordProcessorFactory recordProcessorFactory;
	private KinesisClientLibConfiguration kclConfig;
	
	public BEKinesisConsumer(KinesisDestination kinesisDestination) throws Exception {
		this.ownerDestination = kinesisDestination;
		this.logger = ownerDestination.getLogger();
		String applicationName = (String) ownerDestination.getChannel().getGlobalVariableValue(ownerDestination.getDestinationProperties().getProperty(KinesisProperties.KEY_DESTINATION_APPLICATION_NAME));	
		String streamName = (String) ownerDestination.getChannel().getGlobalVariableValue(ownerDestination.getDestinationProperties().getProperty(KinesisProperties.KEY_DESTINATION_STREAM_NAME));
		String region = (String) ownerDestination.getChannel().getGlobalVariableValue(ownerDestination.getDestinationProperties().getProperty(KinesisProperties.KEY_DESTINATION_REGION_NAME));
		if (region == null) {
			throw new Exception((String) ownerDestination.getChannel().getGlobalVariableValue(ownerDestination.getDestinationProperties().getProperty(KinesisProperties.KEY_DESTINATION_REGION_NAME)) + " is not a valid AWS region.");
		}
		kinesischannel = (KinesisChannel) ownerDestination.getChannel();

		AWSCredentialsProvider credentialsProvider = kinesischannel.getCredentialsProvider();
		String workerId = String.valueOf(UUID.randomUUID());
		kclConfig = new KinesisClientLibConfiguration(applicationName, streamName, credentialsProvider, workerId)
				.withRegionName(region);
		kclConfig.withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);
		int max_records = (int) ownerDestination.getChannel().getGlobalVariableValue(ownerDestination.getDestinationProperties().getProperty(KinesisProperties.KEY_DESTINATION_MAX_RECORDS));
		kclConfig.withMaxRecords(max_records);
		recordProcessorFactory = new KinesisProcessorFactory(ownerDestination);
	}

	@Override
	public void run() {
		try {
			if (worker == null) {
				worker = new Worker.Builder().recordProcessorFactory(recordProcessorFactory).config(kclConfig).build();
				worker.run();
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "Error starting worker");
			throw new RuntimeException(e);
		}
	}
    
	public void suspend() {
		if (worker != null) {
			worker.shutdown();
			worker = null;
		}
	}
}