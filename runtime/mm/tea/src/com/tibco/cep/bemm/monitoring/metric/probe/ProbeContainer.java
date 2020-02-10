package com.tibco.cep.bemm.monitoring.metric.probe;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.AccumulatorPlugin;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.AbstractAccumulator;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;


public class ProbeContainer {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ProbeContainer.class);
	private String pluginDir;
	private Map<Object,PollerProbe> pollerRegistry=new HashMap<Object,PollerProbe>();
	
	public ProbeContainer(String pluginDir) {
		this.pluginDir = pluginDir;
	}
	public ProbeContainer getInstance()
	{
		try {
			return BEMMServiceProviderManager.getInstance().getAggregationService().getProbeContainer();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void start() {
		List<AccumulatorPlugin> accumulators = null;
		try {
			accumulators = getAccumulators();
		} catch (Exception e) {
			try {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.PARSING_CONFIGURED_PLUGINS_ERROR), e);
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
		}

		for (AccumulatorPlugin accumulator : accumulators) {
			
			if (LOGGER.isEnabledFor(Level.INFO)) {
				LOGGER.log(Level.INFO, "Initializing Plugin/Accumulator [%s]...", accumulator.getName());
			}
			try {
				String className = accumulator.getClassName();
				ClassLoader loader = URLClassLoader.newInstance(new URL[0], getClass().getClassLoader());
				Class<?> clz = Class.forName(className, true, loader);				
				Object obj = clz.newInstance();
				if (obj instanceof AbstractAccumulator) {
					// poller
					AbstractAccumulator acc = (AbstractAccumulator) obj;
					PollerProbe poller = new PollerProbe(accumulator.getSpmSchema(), acc);
					poller.init(accumulator.getConfig().getProperty(),accumulator);	
					poller.start(accumulator.getMapEntry());
					pollerRegistry.put(obj.getClass(), poller);
				}
			    else {
					throw new Exception(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVALID_ACCUMULATOR_OBJECT,obj));
				}
				
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.STARTED_SUCCESS, accumulator.getName()));
				}
				
			} catch (Exception e) {
				try {
					LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INITIALIZING_ERROR, accumulator.getName()),e);
				} catch (ObjectCreationException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private List<AccumulatorPlugin> getAccumulators() throws Exception{
		List<AccumulatorPlugin> accumulators = new ArrayList<AccumulatorPlugin>();
		// get configured pligin.xml files		
		File pluginDirectory = new File(pluginDir);
		if (!pluginDirectory.exists() || !pluginDirectory.isDirectory()) {
			throw new Exception(String.format(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.PLUGIN_DIRECTORY_DOES_NOT_EXIST, pluginDir)));
		}
		
		File[] fileset = pluginDirectory.listFiles();
		for (File file : fileset) {
			if (file.isDirectory()) {
				continue;
			} else if (!(file.getAbsolutePath().endsWith("plugin.xml"))) {
				continue;
			}
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, " Parsing XML File =[%s]", file);
			}
			try {
				AccumulatorPlugin plugin=parse(new FileInputStream(file));
				accumulators.add(plugin);
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.PARSING_XML_FILE_ERROR, e, file.getAbsolutePath()),e);
			}
		}
		return accumulators;
				
	}
	public PollerProbe getPoller(Object obj) {
		return pollerRegistry.get(obj);
	}
	public void stop(Object obj) {
		pollerRegistry.get(obj).stop();
	}
	public void stopAll() {
		for (Map.Entry<Object,PollerProbe> entry : pollerRegistry.entrySet()) {
			if(entry.getValue()!=null)
				entry.getValue().stop();
		}
		
	}
	public AccumulatorPlugin parse(InputStream inputStream) throws Exception {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(AccumulatorConstants.PLUGIN_CONFIG_JAXB_PKG, this.getClass().getClassLoader());
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		AccumulatorPlugin plugin=(AccumulatorPlugin)jaxbUnmarshaller.unmarshal(inputStream);
		return plugin;
	}	
	
}
