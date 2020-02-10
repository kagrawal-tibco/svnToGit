package com.tibco.cep.dashboard.plugin.beviews;

import java.io.IOException;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.ClientConfiguration;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.plugin.beviews.runtime.CategoryValuesConsolidatorCache;
import com.tibco.cep.dashboard.psvr.mal.MALGlobalElementsCache;
import com.tibco.cep.dashboard.psvr.mal.model.MALHeader;
import com.tibco.cep.dashboard.psvr.mal.model.MALLogin;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.StartUp;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;

public class BEViewsStartup extends StartUp {

	private DeployedWebRoot deployedWebRoot;

	BEViewsStartup(PlugIn plugIn, DeployedWebRoot deployedWebRoot) {
		super(plugIn);
		this.deployedWebRoot = deployedWebRoot;
	}

	@Override
	public void startup() throws PluginException {
		//create the 'clientconfig.xml'
		try {
			String clientConfigXML = ClientConfiguration.getInstance().toXML(true);
			deployedWebRoot.createFile("clientconfig.xml", clientConfigXML.getBytes());
		} catch (IOException ex) {
			throw new PluginException("could not create clientconfig.xml", ex);
		}
		//initialize backing store flag
		BackingStore.getInstance().init(plugIn.getProperties(), logger, plugIn.getServiceContext());
		//initialize the entity cache (entity cache depends on the backing store flag)
		try {
			EntityCache.getInstance().init(logger, MODE.SERVER, plugIn.getProperties(), plugIn.getServiceContext());
			EntityCache.getInstance().start();
		} catch (ManagementException ex) {
			throw new PluginException("could not initialize entity cache ",ex);
		}
		//initialize the source element cache (used by tuple schema factory)
		try {
			MALSourceElementCache.getInstance().init(logger, MODE.SERVER, plugIn.getProperties(), plugIn.getServiceContext());
			MALSourceElementCache.getInstance().start();
		} catch (ManagementException ex) {
			throw new PluginException("could not initialize entity cache ",ex);
		}
		//initialize the category value consolidator cache
		try {
			CategoryValuesConsolidatorCache.getInstance().init(logger);
		} catch (NamingException e) {
			throw new PluginException("could not initialize category values consolidating cache",e);
		}
		//initialize the entity visualizer provider (used in query manager and drilldown to decide which fields to expose and which to not)
		EntityVisualizerProvider.getInstance().init(plugIn.getProperties(), logger, exceptionHandler, messageGenerator);
		//initialize the query executor factory (depends on the backing store flag)
		try {
			ViewsQueryExecutorFactory.getInstance().init(plugIn.getProperties(), logger, exceptionHandler, messageGenerator, plugIn.getServiceContext());
		} catch (Exception e) {
			throw new PluginException("could not initialize the query executor factory", e);
		}
		//extract header & login images and copy them under the deployed web root
		SharedArchiveResourceProvider sharedArchiveResourceProvider = plugIn.getServiceContext().getRuleServiceProvider().getProject().getSharedArchiveResourceProvider();
		//copy login and header images files
		MALLogin login = MALGlobalElementsCache.getInstance().getLogin();
		if (login != null) {
			String imageURL = login.getImageURL();
			if (StringUtil.isEmptyOrBlank(imageURL) == false) {
				byte[] resourceAsByteArray = sharedArchiveResourceProvider.getResourceAsByteArray(imageURL);
				if (resourceAsByteArray != null && resourceAsByteArray.length > 0) {
					//create a file matching the URL under the deployed web root
					try {
						deployedWebRoot.createFile("images/" + imageURL, resourceAsByteArray);
					} catch (IOException e) {
						exceptionHandler.handleException(String.format("could not deploy %s",imageURL), e, Level.WARN);
					}
				}
			}
		}
		MALHeader header = MALGlobalElementsCache.getInstance().getHeader();
		if (header != null) {
			String imageURL = header.getBrandingImage();
			if (StringUtil.isEmptyOrBlank(imageURL) == false) {
				byte[] resourceAsByteArray = sharedArchiveResourceProvider.getResourceAsByteArray(imageURL);
				if (resourceAsByteArray != null && resourceAsByteArray.length > 0) {
					//create a file matching the URL under the deployed web root
					try {
						deployedWebRoot.createFile("images/" + imageURL, resourceAsByteArray);
					} catch (IOException e) {
						exceptionHandler.handleException(String.format("could not deploy %s",imageURL), e, Level.WARN);
					}
				}
			}
		}
	}

}