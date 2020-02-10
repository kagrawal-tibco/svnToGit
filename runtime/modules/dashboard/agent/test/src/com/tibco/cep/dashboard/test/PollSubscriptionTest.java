package com.tibco.cep.dashboard.test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.UpdatePackets;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.tools.SubscriptionTest;
import com.tibco.cep.kernel.service.logging.Level;

public class PollSubscriptionTest extends SubscriptionTest {

	protected UpdatePackets getLatestData() throws IOException, OGLException {
		HashMap<String, String> map = new HashMap<String, String>();
		BizRequest requestXML = getXMLRequest("getallcomponentsdata", map);
		logger.log(Level.INFO, requestXML.toXML());
		String dataXML = client.execute(requestXML);
		return (UpdatePackets) OGLUnmarshaller.getInstance().unmarshall(UpdatePackets.class, dataXML);
	}

	@Override
	public void launch(String[] args) throws IllegalArgumentException, Exception {
		setup(args);
		start();
		loginUsingCommandArgs();
		setRoleUsingCommandArgs();
		String periodAsStr = arguments.pop();
		long period = Long.parseLong(periodAsStr);
		PagesConfig pagesConfig = getLayout(null);
		Collection<ComponentDefinition> compcfgs = parsePagesConfig(pagesConfig).values();
		String channelName = "Client_"+Thread.currentThread().getName();
		if (compcfgs.size() > 0) {
			createChannel(channelName);
			startChannel();
		}
		for (ComponentDefinition componentConfig : compcfgs) {
			logger.log(Level.INFO, "Getting component config for " + componentConfig.getName() + "," + componentConfig.getTitle());
			VisualizationModel visualizationModel = getComponentConfig(componentConfig.getId(), componentConfig.getType(), null);
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationModel));
			logger.log(Level.INFO, "Getting component data for " + componentConfig.getName() + "," + componentConfig.getTitle());
			VisualizationData visualizationData = getComponentData(componentConfig.getId(), componentConfig.getType(), null);
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationData));
			logger.log(Level.INFO, "Subscribing " + componentConfig.getName() + " on channel name '"+channelName+"'");
			subscribe(componentConfig.getId());
			logger.log(Level.INFO, "Subscribed " + componentConfig.getName() + " on channel name '"+channelName+"'");
		}
		//start the timer thread
		final Timer timer = new Timer("Latest Data Poller", false);
		timer.schedule(new LatestDataPollerTask(), 0, period);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				timer.cancel();
				shutdown();
			}

		}));
	}

	public static void main(String[] args) {
		PollSubscriptionTest test = new PollSubscriptionTest();
		try {
			test.launch(args);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + PollSubscriptionTest.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role> <period in msecs>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class LatestDataPollerTask extends TimerTask {

		@Override
		public void run() {
			logger.log(Level.INFO, "Getting latest data...");
			try {
				UpdatePackets latestData = getLatestData();
				logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, latestData));
			} catch (Exception e) {
				e.printStackTrace();
				cancel();
			}
		}

	}

}
