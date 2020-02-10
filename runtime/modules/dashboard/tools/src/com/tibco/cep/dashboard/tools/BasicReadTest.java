package com.tibco.cep.dashboard.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.mal.managers.MALChartComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALPageSelectorComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALStateMachineComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALTextComponentManager;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentCategory;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentGallery;
import com.tibco.cep.dashboard.psvr.ogl.model.HeaderConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.LoginCustomization;
import com.tibco.cep.dashboard.psvr.ogl.model.PageConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageSetSelectorModel;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PanelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PartitionConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessModel;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.kernel.service.logging.Level;

public class BasicReadTest extends AuthenticationTest implements Launchable {

	protected LoginCustomization getLoginCustomization() throws Exception {
		BizRequest requestXML = getXMLRequest("getlogincustomization", null);
		String loginCustomizationXML = client.execute(requestXML);
		return (LoginCustomization) OGLUnmarshaller.getInstance().unmarshall(LoginCustomization.class, loginCustomizationXML);
	}

	protected HeaderConfig getHeaderConfig() throws Exception {
		BizRequest requestXML = getXMLRequest("getheaderconfig", null);
		String headerConfigurationXML = client.execute(requestXML);
		return (HeaderConfig) OGLUnmarshaller.getInstance().unmarshall(HeaderConfig.class, headerConfigurationXML);
	}

	protected PagesConfig getLayout(String pageid) throws Exception {
		Map<String,String> parameters = new HashMap<String, String>();
		if (pageid != null){
			parameters.put("pageid", pageid);
		}
		BizRequest requestXML = getXMLRequest("getlayout", parameters);
		String layoutXML = client.execute(requestXML);
		return (PagesConfig) OGLUnmarshaller.getInstance().unmarshall(PagesConfig.class, layoutXML);
	}

	protected ComponentGallery getGallery() throws Exception {
		BizRequest requestXML = getXMLRequest("getgallery", null);
		String galleryXML = client.execute(requestXML);
		return (ComponentGallery) OGLUnmarshaller.getInstance().unmarshall(ComponentGallery.class, galleryXML);
	}

	protected Map<String, ComponentDefinition> parsePagesConfig(PagesConfig pagesConfig) {
		Map<String, ComponentDefinition> compcfgs = new LinkedHashMap<String, ComponentDefinition>();
		for (PageConfig pageConfig : pagesConfig.getPageConfig()) {
			for (PartitionConfig partitionConfig : pageConfig.getPartitionConfig()) {
				for (PanelConfig panelConfig : partitionConfig.getPanelConfig()) {
					for (ComponentDefinition componentConfig : panelConfig.getComponentConfig()) {
						compcfgs.put(componentConfig.getId(), componentConfig);
					}
				}
			}
		}
		return compcfgs;
	}

	protected Map<String, ComponentDefinition> parseComponentGallery(ComponentGallery gallery) {
		Map<String, ComponentDefinition> cfgs = new HashMap<String, ComponentDefinition>();
		for (ComponentCategory subCategory : gallery.getComponentCategory()) {
			parseComponentCategories(subCategory, cfgs);
		}
		return cfgs;
	}

	protected void parseComponentCategories(ComponentCategory category, Map<String, ComponentDefinition> cfgs) {
		for (ComponentDefinition compCfg : category.getComponentDefinition()) {
			cfgs.put(compCfg.getId(), compCfg);
		}
		for (ComponentCategory subCategory : category.getComponentCategory()) {
			parseComponentCategories(subCategory, cfgs);
		}
	}

	protected PagesConfig readPage(String pageid) throws Exception {
		PagesConfig pagesConfig = getLayout(pageid);
		logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, pagesConfig));
		Collection<ComponentDefinition> compcfgs = parsePagesConfig(pagesConfig).values();
		for (ComponentDefinition componentConfig : compcfgs) {
			logger.log(Level.INFO, "Getting component config for " + componentConfig.getName() + "," + componentConfig.getTitle());
			VisualizationModel visualizationModel = getComponentConfig(componentConfig.getId(), componentConfig.getType(), null);
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationModel));
			logger.log(Level.INFO, "Getting component data for " + componentConfig.getName() + "," + componentConfig.getTitle());
			VisualizationData visualizationData = getComponentData(componentConfig.getId(), componentConfig.getType(), null);
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationData));
		}
		return pagesConfig;
	}

	protected ComponentGallery readGallery() throws Exception {
		ComponentGallery gallery = getGallery();
		logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, gallery));
		Collection<ComponentDefinition> galleryCompcfgs = parseComponentGallery(gallery).values();
		for (ComponentDefinition galleryComponentConfig : galleryCompcfgs) {
			logger.log(Level.INFO, "Getting component config for " + galleryComponentConfig.getName() + "," + galleryComponentConfig.getTitle());
			VisualizationModel visualizationModel = getComponentConfig(galleryComponentConfig.getId(), galleryComponentConfig.getType(), null);
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationModel));
			logger.log(Level.INFO, "Getting component data for " + galleryComponentConfig.getName() + "," + galleryComponentConfig.getTitle());
			VisualizationData visualizationData = getComponentData(galleryComponentConfig.getId(), galleryComponentConfig.getType(), null);
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationData));
		}
		return gallery;
	}

	protected List<PanelConfig> getMetricPanels(PagesConfig pagesConfig) {
		List<PanelConfig> pnlCfgs = new ArrayList<PanelConfig>();
		for (PageConfig pageConfig : pagesConfig.getPageConfig()) {
			for (PartitionConfig partitionConfig : pageConfig.getPartitionConfig()) {
				for (PanelConfig panelConfig : partitionConfig.getPanelConfig()) {
					if (panelConfig.getLayoutConfig() != null) {
						pnlCfgs.add(panelConfig);
					}
				}
			}
		}
		return pnlCfgs;
	}

	protected Map<String, String> getParentage(PagesConfig pagesConfig, Object childConfig) {
		Map<String, String> map = new HashMap<String, String>();
		for (PageConfig pageConfig : pagesConfig.getPageConfig()) {
			map.put("pageid", pageConfig.getId());
			if (pageConfig == childConfig) {
				return map;
			}
			for (PartitionConfig partitionConfig : pageConfig.getPartitionConfig()) {
				map.put("partitionid", partitionConfig.getId());
				if (partitionConfig == childConfig) {
					return map;
				}
				for (PanelConfig panelConfig : partitionConfig.getPanelConfig()) {
					map.put("panelid", panelConfig.getId());
					if (panelConfig == childConfig) {
						return map;
					}
					for (ComponentDefinition componentConfig : panelConfig.getComponentConfig()) {
						map.put("componentid", componentConfig.getId());
						if (componentConfig == childConfig) {
							return map;
						}
					}
				}
			}
		}
		return map;
	}

	protected VisualizationModel getComponentConfig(String compid, String compType, String mode) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("componentid", compid);
		if (mode != null) {
			map.put("mode", mode);
		}
		BizRequest requestXML = getXMLRequest("getcomponentconfig", map);
		String configXML = client.execute(requestXML);
		if (compType.equals(MALChartComponentManager.DEFINITION_TYPE) == true) {
			return (VisualizationModel) OGLUnmarshaller.getInstance().unmarshall(ChartModel.class, configXML);
		} else if (compType.equals(MALTextComponentManager.DEFINITION_TYPE) == true) {
			return (VisualizationModel) OGLUnmarshaller.getInstance().unmarshall(TextModel.class, configXML);
		} else if (compType.equals(MALPageSelectorComponentManager.DEFINITION_TYPE) == true) {
			return (VisualizationModel) OGLUnmarshaller.getInstance().unmarshall(PageSetSelectorModel.class, configXML);
		} else if (compType.equals(MALStateMachineComponentManager.DEFINITION_TYPE) == true) {
			return (VisualizationModel) OGLUnmarshaller.getInstance().unmarshall(ProcessModel.class, configXML);
		}
		throw new Exception("Unknown component type for parsing [" + compType + "]");
	}

	protected VisualizationData getComponentData(String compid, String compType, String mode) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("componentid", compid);
		if (mode != null) {
			map.put("mode", mode);
		}
		BizRequest requestXML = getXMLRequest("getcomponentdata", map);
		String dataXML = client.execute(requestXML);
		return (VisualizationData) OGLUnmarshaller.getInstance().unmarshall(VisualizationData.class, dataXML);
	}

	@Override
	protected void shutdown() {
		try {
			logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.shutdown();
	}

	@Override
	public void launch(String[] args) throws IllegalArgumentException, Exception {
		try {
			setup(args);
			start();
			loginUsingCommandArgs();
			setRoleUsingCommandArgs();
			LoginCustomization loginCustomization = getLoginCustomization();
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, loginCustomization));
			HeaderConfig headerConfig = getHeaderConfig();
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, headerConfig));
			readPage(null);
			readGallery();
		} finally {
			shutdown();
		}
	}


	public static void main(String[] args) {
		BasicReadTest test = new BasicReadTest();
		try {
			test.launch(args);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + test.getClass().getName() + test.getArgmentUsage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getArgmentUsage() {
		return "remotepullrequesturl streamingportnumber username password role";

	}


}
