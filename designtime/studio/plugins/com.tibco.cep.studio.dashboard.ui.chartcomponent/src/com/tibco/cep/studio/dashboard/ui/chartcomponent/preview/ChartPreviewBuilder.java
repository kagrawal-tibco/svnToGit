package com.tibco.cep.studio.dashboard.ui.chartcomponent.preview;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.preview.IEmbeddedPresentationServer;
import com.tibco.cep.studio.dashboard.preview.SeriesDataSet;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.dashboard.utils.SystemElementsCreator;

class ChartPreviewBuilder {

//	private static enum PAIRING_STATUS { NONE, STARTED, COMPLETED };

	private static final String EMBEDDED_PRESENTATION_SERVER_IMPL = "com.tibco.cep.dashboard.integration.embedded.PresentationServer";

	private LocalECoreFactory localECoreFactory;

	private boolean enabled;

	private Map<String, Boolean> previewableProperties;

	private IEmbeddedPresentationServer embeddedPresentationServer;

	private String completeXML;

	private Map<String, SeriesDataSet> dataSetMap;

	//The underlying firing mechanism is complex, the same key gets fired twice , so when we move series up and down, we get 4 unique events instead of two
	//removing the logic which looks for a pair, since we cancel pending jobs, in most cases the last sort order change will be honored
//	private String sortOrderChangeOwner;

//	private PAIRING_STATUS sortOrderChangePairing;

	ChartPreviewBuilder(LocalECoreFactory localECoreFactory) throws Exception {
		this.localECoreFactory = localECoreFactory;
		enabled = true;
		try {
			loadPreviewableProperties();
		} catch (IOException e) {
			enabled = false;
			throw new Exception("could not load previewable properties information, disabling chart preview...", e);
		}
		try {
			createEmbeddedPresentationServer();
		} catch (Exception e) {
			enabled = false;
			throw new Exception("could not load embedded presentation server, disabling chart preview...", e);
		} catch (Throwable t) {
			enabled = false;
			throw new Exception("could not load embedded presentation server, disabling chart preview...", t);
		}
//		sortOrderChangePairing = PAIRING_STATUS.NONE;
		dataSetMap = new HashMap<String, SeriesDataSet>();
	}

	private void loadPreviewableProperties() throws IOException {
		previewableProperties = new HashMap<String, Boolean>();
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream("previewableprops.properties"));
		for (Map.Entry<Object, Object> propsEntrySet : props.entrySet()) {
			System.out.println("ChartPreviewBuilder.loadPreviewableProperties("+propsEntrySet.getKey()+","+propsEntrySet.getValue()+")");
			previewableProperties.put(String.valueOf(propsEntrySet.getKey()), Boolean.valueOf(String.valueOf(propsEntrySet.getValue())));
		}
	}

	private void createEmbeddedPresentationServer() throws Exception {
		Class<? extends IEmbeddedPresentationServer> embeddedPSVRImplClass = (Class<? extends IEmbeddedPresentationServer>) Class.forName(EMBEDDED_PRESENTATION_SERVER_IMPL).asSubclass(IEmbeddedPresentationServer.class);
		embeddedPresentationServer = embeddedPSVRImplClass.newInstance();
		Skin defaultSkin = null;
		List<LocalElement> skins = localECoreFactory.getChildren(BEViewsElementNames.SKIN);
		for (LocalElement skin : skins) {
			if (((LocalSkin)skin).isSystem() == true) {
				defaultSkin = (Skin) skin.getEObject();
			}
		}
		if (defaultSkin == null) {
			//we don't have a skin , create a new one
			List<Entity> systemElements = new SystemElementsCreator(localECoreFactory.getProject().getName(),"","").create();
			for (Entity systemElement : systemElements) {
				if (systemElement instanceof Skin) {
					defaultSkin = (Skin) systemElement;
					break;
				}
			}
		}
		if (defaultSkin != null) {
			embeddedPresentationServer.setSkin(defaultSkin);
			embeddedPresentationServer.start();
		}
		else {
			//we don't have a skin
			embeddedPresentationServer.shutdown();
			embeddedPresentationServer = null;
			throw new Exception("Missing default skin");
		}
	}

	boolean isPreviewable(String owner, String name, InternalStatusEnum status) {

		if (enabled == false){
			return false;
		}
//		if (LocalElement.PROP_KEY_SORTING_ORDER.equals(name) == true) {
//			if (sortOrderChangeOwner == null) {
//				sortOrderChangeOwner = owner;
//				sortOrderChangePairing = PAIRING_STATUS.STARTED;
//			}
//			else if (sortOrderChangeOwner.equals(owner) == true){
//				if (sortOrderChangePairing.compareTo(PAIRING_STATUS.STARTED) == 0) {
//					sortOrderChangePairing = PAIRING_STATUS.COMPLETED;
//				}
//				else {
//					sortOrderChangeOwner = null;
//					sortOrderChangePairing = PAIRING_STATUS.NONE;
//				}
//			}
//			else {
//				sortOrderChangePairing = PAIRING_STATUS.COMPLETED;
//			}
//		}
//		else {
//			sortOrderChangeOwner = null;
//			sortOrderChangePairing = PAIRING_STATUS.NONE;
//		}
		boolean isPreviewable = previewableProperties.containsKey(name);
//		System.out.println("ChartPreviewBuilder.isPreviewable(owner=" + owner+ ",name="+ name + ",status" + status + ",pairingowner="+sortOrderChangeOwner+",pairingstatus"+sortOrderChangePairing+")->" + isPreviewable);
		System.out.println("ChartPreviewBuilder.isPreviewable(owner=" + owner+ ",name="+ name + ",status" + status + ")->" + isPreviewable);
		return isPreviewable;
	}

	boolean regenerateData(String name){
		if (enabled == false){
			return false;
		}
		Boolean regenerate = previewableProperties.get(name);
		if (regenerate == null) {
			return false;
		}
		return regenerate;
	}

	void build(Component component, boolean regenerateData) throws Exception {
		if (enabled == false){
			return;
		}
//		if (sortOrderChangeOwner != null && (sortOrderChangePairing.compareTo(PAIRING_STATUS.COMPLETED) != 0)) {
//			return;
//		}
		try {
			Map<String, SeriesConfig> seriesConfigs = getSeries(component);
			//remove all unwanted series data sets
			dataSetMap.keySet().retainAll(seriesConfigs.keySet());
			if (regenerateData == true) {
				dataSetMap.clear();
			}
			else {
				//do we need to regenerate the data ?
				regenerateData =  dataSetMap.size() != seriesConfigs.size();
			}
			if (regenerateData == true) {
				//get the category values and the min, max values
				LinkedList<String> categoryValues = new LinkedList<String>();
				double minValue = 0;
				double maxValue = 0;
				double difference = 0;
				PropertyMap extendedPropertiesHolder = component.getExtendedProperties();
				if (extendedPropertiesHolder != null) {
					List<Entity> extendedProperties = extendedPropertiesHolder.getProperties();
					for (Entity extendedProperty : extendedProperties) {
						if (extendedProperty.getName().equals("Category") == true) {
							PropertyMap categoryExtendedPropertiesHolder = extendedProperty.getExtendedProperties();
							if (categoryExtendedPropertiesHolder != null) {
								List<Entity> categoryValueProperties = categoryExtendedPropertiesHolder.getProperties();
								for (Entity categoryValueProperty : categoryValueProperties) {
									categoryValues.add(categoryValueProperty.getName());
								}
							}
						} else if (extendedProperty.getName().equals("MinValue") == true) {
							try {
								minValue = Double.parseDouble(((SimpleProperty) extendedProperty).getValue());
							} catch (NumberFormatException e) {
								minValue = Double.NaN;
							}
						} else if (extendedProperty.getName().equals("MaxValue") == true) {
							try {
								maxValue = Double.parseDouble(((SimpleProperty) extendedProperty).getValue());
							} catch (NumberFormatException e) {
								maxValue = Double.NaN;
							}
						}
					}
					if (Double.isNaN(minValue) == false && Double.isNaN(maxValue) == false && minValue < maxValue) {
						difference = maxValue - minValue;
					}
					else {
						minValue = 0;
						maxValue = 0;
						difference = 0;
					}
				}
				for (SeriesConfig seriesConfig : seriesConfigs.values()) {
					if (dataSetMap.containsKey(seriesConfig.getGUID()) == false) {
						dataSetMap.put(seriesConfig.getGUID(), generateDataSet(seriesConfig, categoryValues, minValue, difference));
					}
				}
			}
			completeXML = embeddedPresentationServer.getComponentConfigWithData(component, dataSetMap.values().toArray(new SeriesDataSet[dataSetMap.size()]));
		} catch (Exception e) {
			throw new Exception("could not generate preview data, disabling chart preview...", e);
		} finally {
//			sortOrderChangeOwner = null;
//			sortOrderChangePairing = PAIRING_STATUS.NONE;
		}
	}

	String getCompleteXML() {
		return completeXML;
	}

	void dispose() {
		if (embeddedPresentationServer != null) {
			embeddedPresentationServer.shutdown();
		}
		localECoreFactory = null;
		dataSetMap.clear();
		dataSetMap = null;
	}

	private SeriesDataSet generateDataSet(SeriesConfig seriesConfig, List<String> categoryValues, double minValue, double difference) {
		Random random = new Random();
		SeriesDataSet seriesData = new SeriesDataSet(seriesConfig.getGUID(),seriesConfig.getName());
		for (String categoryValue : categoryValues) {
			seriesData.add(categoryValue, minValue + difference * random.nextDouble());
		}
		return seriesData;
	}

	private Map<String, SeriesConfig> getSeries(Component component){
		Map<String, SeriesConfig> seriesConfigs = new LinkedHashMap<String, SeriesConfig>();
		for (Visualization visualization : component.getVisualization()) {
			for (SeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				seriesConfigs.put(seriesConfig.getGUID(), seriesConfig);
			}
		}
		return seriesConfigs;
	}

	class UpdatePreview {

		boolean update;

		boolean regeneratedata;

		long timestamp;

		UpdatePreview(boolean update, boolean regeneratedata) {
			super();
			update(update, regeneratedata);
		}

		void update(boolean update, boolean regeneratedata) {
			this.update = update;
			this.regeneratedata = regeneratedata;
			this.timestamp = System.currentTimeMillis();
		}

	}

}