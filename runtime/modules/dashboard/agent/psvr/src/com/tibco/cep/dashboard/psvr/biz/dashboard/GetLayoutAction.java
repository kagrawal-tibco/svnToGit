package com.tibco.cep.dashboard.psvr.biz.dashboard;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.biz.BaseSessionedAction;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALDashboardPageManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayout;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.LayoutConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.LayoutConstraints;
import com.tibco.cep.dashboard.psvr.ogl.model.PageConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PanelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PartitionConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.types.BooleanOptions;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DisplayMode;
import com.tibco.cep.dashboard.psvr.ogl.model.types.PanelState;
import com.tibco.cep.dashboard.psvr.ogl.model.types.PartitionState;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ScrollBarConfig;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRenderer;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRendererFactory;
import com.tibco.cep.dashboard.psvr.vizengine.LayoutConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.LayoutConfigGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.PagePreProcessor;
import com.tibco.cep.dashboard.psvr.vizengine.PagePreProcessor.PreProcessorResults;
import com.tibco.cep.dashboard.psvr.vizengine.PagePreProcessorFactory;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class GetLayoutAction extends BaseSessionedAction {

	@Override
	protected BizResponse doSessionedExecute(SecurityToken token, BizSessionRequest request) {
		PresentationContext ctx = null;
		try {
			//create a context
			ctx = new PresentationContext(token);
			ViewsConfigHelper viewsConfigHelper = ctx.getViewsConfigHelper();
			//init to current page
			MALPage page = viewsConfigHelper.getCurrentPage();
			//get page id from request
			String pageID = request.getParameter("pageid");
			if (StringUtil.isEmptyOrBlank(pageID) == false) {
				//check if the page id matches a page
				page = viewsConfigHelper.getPageByID(pageID);
				if (page != null && page.getDefinitionType().equals(MALDashboardPageManager.DEFINITION_TYPE) == true) {
					//the request page is a dashboard page, set it as the current page
					viewsConfigHelper.setCurrentPageID(pageID);
				}
			}
			else {
				//we did not get a page id , lets look for a page type
				//get page type from request
				String pageType = request.getParameter("pagetype");
				if (StringUtil.isEmptyOrBlank(pageType) == false) {
					page = viewsConfigHelper.getPageByType(pageType);
					if (page != null && page.getDefinitionType().equals(MALDashboardPageManager.DEFINITION_TYPE) == true) {
						//the request page is a dashboard page, set it as the current page
						viewsConfigHelper.setCurrentPageID(pageID);
					}
				}
			}
			if (page == null) {
				return handleError(getMessage("getlayout.nonexistent.defaultpage", getMessageGeneratorArgs(token, URIHelper.getURI(viewsConfigHelper.getViewsConfig()))));
			}
			//pre-process the page
			PreProcessorResults results = preprocess(request, page, ctx);
			//generate the layout
			PagesConfig pagesConfig = generateLayout(token, page, ctx);
			if (results != null) {
				for (PageConfig pageConfig : pagesConfig.getPageConfig()) {
					//add variables
					pageConfig.setVariable(results.getVariables());
					//add actions
					pageConfig.setAction(results.getActions());
				}
			}
			String layoutXML = OGLMarshaller.getInstance().marshall(token, pagesConfig);
			return handleSuccess(layoutXML);
		} catch (MALException e) {
			return handleError(getMessage("bizaction.view.loading.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("bizaction.view.notfound.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (PluginException e) {
			return handleError(getMessage("getlayout.layout.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("getlayout.layout.generation.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "layout configuration")), e);
		} finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	private PreProcessorResults preprocess(BizSessionRequest request, MALPage page, PresentationContext ctx) throws PluginException, RequestProcessingException {
		PagePreProcessor preProcessor = PagePreProcessorFactory.getInstance().getPreProcessor(page);
		if (preProcessor != null) {
			return preProcessor.preprocess(page, request, ctx);
		}
		return null;
	}

	private PagesConfig generateLayout(SecurityToken token, MALPage page, PresentationContext ctx) throws PluginException, RequestProcessingException {
		// PORT figure out if we can get rid of pagesconfig from the system
		PagesConfig pagesConfig = new PagesConfig();
		// we will set the pagesConfig@id as defaultPage.getId()
		pagesConfig.setId(page.getId());
		// we will set the pagesConfig@name defaultPage.getName()
		pagesConfig.setName(StringUtil.isEmptyOrBlank(page.getDisplayName()) ? page.getName() : page.getDisplayName());
		// set the default page
		pagesConfig.setDefaultPage(page.getId());
		// create page configuration
		PageConfig pageConfig = new PageConfig();
		pageConfig = new PageConfig();
		pageConfig.setId(page.getId());
		pageConfig.setName(StringUtil.isEmptyOrBlank(page.getDisplayName()) ? page.getName() : page.getDisplayName());
		pageConfig.setDisplayMode(DisplayMode.valueOf(page.getDisplayMode().toString()));
		pageConfig.setType(page.getDefinitionType());
		MALPartition[] partitions = page.getPartition();
		PartitionConfig[] partitionConfigs = new PartitionConfig[partitions.length];
		for (int j = 0; j < partitions.length; j++) {
			// we assume all partitions to be vertical
			MALPartition partition = partitions[j];
			if (partition.getVertical() == false) {
				MessageGeneratorArgs args = getMessageGeneratorArgs(token, URIHelper.getURI(partition));
				throw new RequestProcessingException(getMessage("getlayout.invalid.partitiontype", args));
			}
			partitionConfigs[j] = new PartitionConfig();
			partitionConfigs[j].setId(partition.getId());
			partitionConfigs[j].setSpan(partition.getSpan());
			partitionConfigs[j].setState(PartitionState.valueOf(partition.getState().toString()));
			// if we have more then one partition then we make the
			// first partition of each defaultPage dock-able for now
			boolean dockable = (j == 0) && (partitions.length > 1);
			partitionConfigs[j].setDockable(BooleanOptions.valueOf(Boolean.toString(dockable)));
			MALPanel[] panels = partition.getPanel();
			PanelConfig[] panelConfigs = new PanelConfig[panels.length];
			for (int k = 0; k < panels.length; k++) {
				boolean warningLogged = false;
				MALPanel panel = panels[k];
				panelConfigs[k] = new PanelConfig();
				panelConfigs[k].setId(panel.getId());
				String panelTitle = panel.getDisplayName();
				if (panelTitle == null || panelTitle.trim().length() == 0) {
					panelTitle = panel.getName();
				}
				panelConfigs[k].setTitle(panelTitle);
				panelConfigs[k].setSpan(panel.getSpan());
				panelConfigs[k].setScrollBars(ScrollBarConfig.valueOf(panel.getScrollBar().toString()));
				panelConfigs[k].setMaximizable(BooleanOptions.valueOf(Boolean.toString(panel.getMaximizable())));
				panelConfigs[k].setMinimizable(BooleanOptions.valueOf(Boolean.toString(panel.getMinimizable())));
				panelConfigs[k].setState(PanelState.valueOf(panel.getState().toString()));
				MALComponent[] components = panel.getComponent();
				MALLayout malLayout = panel.getLayout();
				if (malLayout != null) {
					// Architect does not remove a layout from the panel ,
					// but makes all the properties in it null
					// making the properties null, makes the componentwidth
					// and componentheight 0
					if (malLayout.getComponentHeight() != 0 && malLayout.getComponentWidth() != 0) {
						LayoutConfigGenerator layoutGenerator = LayoutConfigGeneratorFactory.getInstance().getGenerator(malLayout);
						LayoutConfig layoutConfig = layoutGenerator.generate(malLayout);
						panelConfigs[k].setLayoutConfig(layoutConfig);
					} else {
						if (logger.isEnabledFor(Level.DEBUG)) {
							MessageGeneratorArgs args = getMessageGeneratorArgs(token, URIHelper.getURI(panel));
							logger.log(Level.WARN, getMessage("getlayout.invalid.layout", args));
						}
						malLayout = null;
					}
				}
				for (int l = 0; l < components.length; l++) {
					MALComponent component = components[l];
					ComponentDefinition componentDefinition = new ComponentDefinition();
					componentDefinition.setId(component.getId());
					componentDefinition.setTitle(component.getDisplayName());
					componentDefinition.setName(component.getName());
					ComponentRenderer compRenderer = ComponentRendererFactory.getInstance().getRenderer(component);
					componentDefinition.setMovieURL(compRenderer.getComponentRenderer());
					componentDefinition.setType(component.getDefinitionType());
					MALLayoutConstraint malLayoutConstraint = component.getLayoutConstraint();
					if (malLayoutConstraint != null) {
						if (malLayout == null) {
							MessageGeneratorArgs args = getMessageGeneratorArgs(token, URIHelper.getURI(panel));
							throw new RequestProcessingException(getMessage("getlayout.nonexistent.layout", args));
						}
						LayoutConfigGenerator layoutGenerator = LayoutConfigGeneratorFactory.getInstance().getGenerator(malLayout);
						LayoutConstraints layoutConstraints = layoutGenerator.getLayoutConstraints(malLayoutConstraint);
						componentDefinition.setLayoutConstraints(layoutConstraints);

					} else if (malLayout != null) {
						if (warningLogged == false) {
							MessageGeneratorArgs args = getMessageGeneratorArgs(token, URIHelper.getURI(panel));
							logger.log(Level.WARN, getMessage("getlayout.unwanted.layout", args));
							// we don't want a layout in the panel
							panelConfigs[k].setLayoutConfig(null);
							warningLogged = true;
						}
					}
					panelConfigs[k].addComponentConfig(componentDefinition);
				}
			}
			partitionConfigs[j].setPanelConfig(panelConfigs);
		}
		pageConfig.setPartitionConfig(partitionConfigs);
		pagesConfig.addPageConfig(pageConfig);
		return pagesConfig;
	}

}
