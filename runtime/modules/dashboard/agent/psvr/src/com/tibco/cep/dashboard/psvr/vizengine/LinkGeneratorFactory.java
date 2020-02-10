package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.psvr.mal.managers.MALRelatedPageManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

//TODO the pattern needs to be more plug-in aware
public class LinkGeneratorFactory extends ServiceDependent {

	private static LinkGeneratorFactory instance;

	public static final synchronized LinkGeneratorFactory getInstance() {
		if (instance == null) {
			instance = new LinkGeneratorFactory();
		}
		return instance;
	}

	private boolean useLegacyDrillDownPage;

	private LinkGeneratorFactory() {
		super("linkgeneratorfactory","Link Generator Factory");
	}

	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		useLegacyDrillDownPage = (Boolean) VizEngineProperties.LEGACY_SEARCH_PAGE_ENABLE.getValue(properties);
		super.init(parentLogger, mode, properties, serviceContext);
	}

	public LinkGenerator generateDrillLink(String baseURI, SecurityToken token, String componentID, String seriesId, String tupId) {
		LinkGenerator linkGenerator = new LinkGenerator(useLegacyDrillDownPage == false ? "page.html?" : "searchpageloader.html?");
		if (useLegacyDrillDownPage == false) {
			linkGenerator.addParameter("pagetype", MALSearchPageManager.DEFINITION_TYPE);
		}
		else {
			linkGenerator.addParameter("uri", baseURI);
		}
		linkGenerator.addParameter("stoken", token.toString());
		linkGenerator.addParameter("componentid", componentID);
		linkGenerator.addParameter("seriesid", seriesId);
		linkGenerator.addParameter("tupid", tupId);
		return linkGenerator;
	}

	public LinkGenerator generateSearchPageLink(String baseURI, SecurityToken token) {
		LinkGenerator linkGenerator = new LinkGenerator(useLegacyDrillDownPage == false ? "page.html?" : "searchpageloader.html?");
		if (useLegacyDrillDownPage == false) {
			linkGenerator.addParameter("pagetype", MALSearchPageManager.DEFINITION_TYPE);
		}
		else {
			linkGenerator.addParameter("uri", baseURI);
		}
		linkGenerator.addParameter("stoken", token.toString());
		return linkGenerator;
	}

	public LinkGenerator generateRelatedLink(String baseURI, SecurityToken token, String typeId, String tupId) {
		LinkGenerator linkGenerator = new LinkGenerator("page.html?");
		linkGenerator.addParameter("pagetype", MALRelatedPageManager.DEFINITION_TYPE);
		linkGenerator.addParameter("stoken", token.toString());
		linkGenerator.addParameter("typeid", typeId);
		linkGenerator.addParameter("tupid", tupId);
		return linkGenerator;
	}

}