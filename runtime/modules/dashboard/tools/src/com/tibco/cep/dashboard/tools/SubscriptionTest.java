package com.tibco.cep.dashboard.tools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.kernel.service.logging.Level;

public class SubscriptionTest extends BasicReadTest implements Launchable {

	protected String channelName;
	protected boolean streaming;

	protected void createChannel(String channelName) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("createchannel", map);
		client.execute(requestXML);
		this.channelName = channelName;
	}

	protected void startChannel() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("startchannel", null);
		client.execute(requestXML);
	}

	protected void stopChannel() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("stopchannel", null);
		client.execute(requestXML);
	}

	protected void subscribe(String compid) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		map.put("componentid", compid);
		BizRequest requestXML = getXMLRequest("subscribe", map);
		client.execute(requestXML);
	}

	protected void unsubscribe(String compid) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		map.put("componentid", compid);
		BizRequest requestXML = getXMLRequest("unsubscribe", map);
		client.execute(requestXML);
	}

	protected void subscribeAll() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("subscribeall", map);
		client.execute(requestXML);
	}

	protected void unsubscribeAll() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("unsubscribeall", map);
		client.execute(requestXML);
	}

	protected void destroyChannel() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("destroychannel", null);
		client.execute(requestXML);
	}

	protected void startStreaming() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("startstreaming", map);
		client.startStreaming(requestXML);
		streaming = true;
	}

	protected void stopStreaming() throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", channelName);
		BizRequest requestXML = getXMLRequest("stopstreaming", map);
		client.stopStreaming(requestXML);
		streaming = false;
	}

	@Override
	protected void shutdown() {
		try {
			if (streaming == true) {
				stopStreaming();
			}
			if (channelName != null) {
				unsubscribeAll();
				stopChannel();
				destroyChannel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.shutdown();
	}

	@Override
	public void launch(String[] args) throws IllegalArgumentException, Exception {
		setup(args);
		start();
		loginUsingCommandArgs();
		setRoleUsingCommandArgs();
		PagesConfig pagesConfig = getLayout(null);
		Collection<ComponentDefinition> compcfgs = parsePagesConfig(pagesConfig).values();
		String channelName = "Client_"+Thread.currentThread().getName();
		if (compcfgs.size() > 0) {
			//createChannel("test");
			createChannel(channelName);
			startChannel();
		}
		for (ComponentDefinition componentConfig : compcfgs) {

			logger.log(Level.INFO, "Getting component config for " + componentConfig.getName() + "," + componentConfig.getTitle());
			/*VisualizationModel visualizationModel = */getComponentConfig(componentConfig.getId(), componentConfig.getType(), null);
			//logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationModel));
			logger.log(Level.INFO, "Getting component data for " + componentConfig.getName() + "," + componentConfig.getTitle());
			/*VisualizationData visualizationData = */getComponentData(componentConfig.getId(), componentConfig.getType(), null);
			//logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationData));
			logger.log(Level.INFO, "Subscribing " + componentConfig.getName() + " on channel name '"+channelName+"'");
			subscribe(componentConfig.getId());
			logger.log(Level.INFO, "Subscribed " + componentConfig.getName() + " on channel name '"+channelName+"'");
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					stopStreaming();
					shutdown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}));
		// streaming
		startStreaming();
	}

	public static void main(String[] args) {
		SubscriptionTest test = new SubscriptionTest();
		try {
			test.launch(args);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + SubscriptionTest.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
