package com.tibco.cep.dashboard.psvr.biz.dashboard;

import java.text.ParseException;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.MALGlobalElementsCache;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALDashboardPageManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALHeader;
import com.tibco.cep.dashboard.psvr.mal.model.MALHeaderLink;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.HeaderActionConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.HeaderConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageLevelHeaderConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageLevelImage;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.dashboard.psvr.vizengine.LinkGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class GetHeaderConfigurationAction extends BaseAuthenticatedAction {

	private static final ActionConfigType GALLERY_ACTION = ActionConfigUtils.createActionConfig(
			"Gallery",
			CommandType.SHOWDIALOG,
			false,
			false,
			new String[][] {
				new String[] { "dialogname","gallery" }
			},
			null,
			null,
			null
	);

	private static final ActionConfigType SAVE_ACTION = ActionConfigUtils.createActionConfig(
			"Save",
			CommandType.SAVE,
			false,
			false,
			null,
			null,
			null,
			null
	);

	private String baseURI;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		baseURI = String.valueOf(ConfigurationProperties.PULL_REQUEST_BASE_URL.getValue(properties));
	}

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		try {
			ViewsConfigHelper viewsConfigHelper = getTokenRoleProfile(token).getViewsConfigHelper();
			boolean disableSearch = viewsConfigHelper.getPagesByType(MALSearchPageManager.DEFINITION_TYPE).length == 0;
			HeaderConfig headerCfg = new HeaderConfig();
			for (MALPage dashboardPage : viewsConfigHelper.getPagesByType(MALDashboardPageManager.DEFINITION_TYPE)){
				PageLevelHeaderConfig pgsetHdrCfg = new PageLevelHeaderConfig();

				pgsetHdrCfg.setPageID(dashboardPage.getId());

				String title = dashboardPage.getDisplayName();
				if (StringUtil.isEmptyOrBlank(title) == true){
					title = dashboardPage.getName();
				}
				pgsetHdrCfg.setTitle(title);

				// set the branding image if any
				PageLevelImage pageSetLevelImage = new PageLevelImage();
				pageSetLevelImage.setUrl(getBrandingImage());
				pgsetHdrCfg.setPageLevelImage(pageSetLevelImage);

				// add all the header level links (enabled/disabled)
				HeaderActionConfig hdrActionCfg = new HeaderActionConfig();

				// add all user defined external links
				MALHeader insightHeader = MALGlobalElementsCache.getInstance().getHeader();
				if (insightHeader != null) {
					MALHeaderLink[] headerLinks = insightHeader.getHeaderLink();
					if(headerLinks != null && headerLinks.length > 0) {
						for(MALHeaderLink hLink:headerLinks) {
							String urlName = hLink.getUrlName();
							String urlLink = hLink.getUrlLink();
							logger.log(Level.DEBUG, "URL Name: "+ urlName+", URL Link: "+ urlLink);

							// call URL substitution framework to replace values in variables
							urlLink = generateLink(urlLink, token);

							if (urlLink != null) {
								hdrActionCfg.addActionConfig(ActionConfigUtils.createActionConfig(urlName,
																		CommandType.LAUNCHEXTERNALLINK,
																		false,
																		false,
																		null,
																		null,
																		new String[][] {
																				new String[] { "url",urlLink }
																			},
																		null));
							}
						}
					}
					else {
						logger.log(Level.DEBUG, "No header links found");
					}
				}

				//add search page link
				String searhPageLink = LinkGeneratorFactory.getInstance().generateSearchPageLink(baseURI, token).toString();

				hdrActionCfg.addActionConfig(ActionConfigUtils.createActionConfig("Search",
						CommandType.LAUNCHINTERNALLINK,
						disableSearch,
						false,
						null,
						null,
						new String[][] {
								new String[] { "url", searhPageLink }
							},
						null));

				// gallery link
				hdrActionCfg.addActionConfig(GALLERY_ACTION);

				// save dashboard layout link
				hdrActionCfg.addActionConfig(SAVE_ACTION);

				pgsetHdrCfg.setHeaderActionConfig(hdrActionCfg);

				headerCfg.addPageLevelHeaderConfig(pgsetHdrCfg);
			}
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, headerCfg));
		} catch (RequestProcessingException e) {
			return handleError(getMessage("getheadercustomization.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "header customization")), e);
		}
	}

	private String generateLink(String urlTemplate,SecurityToken token) {
		try {
			VariableContext variableContext = new VariableContext(logger, token, properties, null);
			String generatedLink = VariableInterpreter.getInstance().interpret(urlTemplate, variableContext, true);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Generated [" + generatedLink + "] from [" + urlTemplate + " ]");
			}
			return generatedLink;
		} catch (ParseException e) {
			logger.log(Level.WARN, "could not parse " + urlTemplate, e);
			return null;
		}
	}

	private String getBrandingImage() {
		MALHeader insightHeader = MALGlobalElementsCache.getInstance().getHeader();
		if (insightHeader != null) {
			String brandingImageURL = insightHeader.getBrandingImage();
			if (StringUtil.isEmptyOrBlank(brandingImageURL) == false) {
				//Modified By Anand to fix 10825 - 01/21/2011
				return "../images"+brandingImageURL;
			}
		}
		return "";
	}
}
