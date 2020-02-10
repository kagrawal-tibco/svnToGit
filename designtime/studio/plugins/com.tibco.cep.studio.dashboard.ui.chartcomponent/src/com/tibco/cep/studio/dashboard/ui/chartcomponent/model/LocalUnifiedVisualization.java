package com.tibco.cep.studio.dashboard.ui.chartcomponent.model;

import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalUnifiedVisualization extends LocalVisualization {

	public static final String TYPE = LocalUnifiedVisualization.class.getSimpleName();

	private static final String[] TYPES_TO_BE_UNIFIED = new String[] { BEViewsElementNames.BAR_CHART_VISUALIZATION, BEViewsElementNames.LINE_CHART_VISUALIZATION,
			BEViewsElementNames.AREA_CHART_VISUALIZATION, BEViewsElementNames.RANGE_PLOT_CHART_VISUALIZATION, BEViewsElementNames.PIE_CHART_VISUALIZATION,
			BEViewsElementNames.SCATTER_CHART_VISUALIZATION, BEViewsElementNames.TEXT_VISUALIZATION };

	public static final String PROP_KEY_VIZ_TYPES = "VizType";

	@SuppressWarnings("unused")
	private String nativeType;

	private List<String> nativeProperties;

	private List<String> nativeParticles;

	private LocalUnifiedVisualization() {
		super(null, TYPE, "");
	}

	LocalUnifiedVisualization(LocalElement parentElement, String name) {
		super(parentElement, TYPE, name);
	}

	LocalUnifiedVisualization(LocalElement parentElement, LocalVisualization visualization) {
		super(parentElement, TYPE, visualization.getName());
		populate(visualization);
	}

	@Override
	public void setupProperties() {
		for (String type : TYPES_TO_BE_UNIFIED) {
			// add properties
			List<String> existingNames = getPropertyNames();
			List<SynProperty> propertyList = ViewsConfigReader.getInstance().getPropertyList(type);
			for (SynProperty synProperty : propertyList) {
				if (existingNames.contains(synProperty.getName()) == false) {
					// add iff it does not already exist
					addProperty(this, (ISynXSDAttributeDeclaration) synProperty.clone());
				}
			}
		}
		// add visualization type as a property
		SynOptionalProperty property = new SynOptionalProperty(PROP_KEY_VIZ_TYPES, new SynStringType());
		property.setArray(true);
		addProperty(this, property);
		// add unified series config as a particle
		addParticle(new LocalParticle(LocalUnifiedSeriesConfig.TYPE, 0, -1));
	}

	void populate(LocalVisualization visualization) {
		//PATCH set the eobject since search needs it
		setEObject(visualization.getEObject());
		//set the native type , we will use it for validation
		nativeType = visualization.getInsightType();
		nativeProperties = visualization.getPropertyNames();
		nativeParticles = visualization.getParticleNames(true);
		// transfer properties
		List<String> names = visualization.getPropertyNames();
		for (String name : names) {
//			System.out.println("LocalUnifiedVisualization.populate()::Transfering " + visualization + "/" + name + "{" + visualization.getPropertyValue(name) + "} to " + this);
			SynProperty property = (SynProperty) visualization.getProperty(name);
			if (property.isArray() == true) {
				((SynProperty) getProperty(name)).setValues(property.getValues());
			} else {
				setPropertyValue(name, visualization.getPropertyValue(name));
			}
		}
		// set PROP_KEY_VIZ_TYPE
		((SynProperty)getProperty(PROP_KEY_VIZ_TYPES)).addValue(visualization.getInsightType());
		// transfer particles
		names = visualization.getParticleNames(true);
		for (String visualizationParticleName : names) {
			LocalParticle componentParticle = visualization.getParticle(visualizationParticleName);
			List<LocalElement> children = visualization.getChildren(visualizationParticleName);
			if (componentParticle.isReference() == false) {
				// we are dealing with seriesconfigs
				for (LocalElement child : children) {
//					System.err.println("LocalUnifiedVisualization.populate()::Creating " + visualization + "/" + visualizationParticleName + "{" + child + "} in " + this);
					LocalUnifiedSeriesConfig unifiedSeriesConfig = new LocalUnifiedSeriesConfig(this, (LocalSeriesConfig) child);
					unifiedSeriesConfig.setPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE, visualization.getInsightType());
					addElement(LocalUnifiedSeriesConfig.TYPE, unifiedSeriesConfig);
				}
				getParticle(LocalUnifiedSeriesConfig.TYPE).setInitialized(true);
			} else {
				for (LocalElement child : children) {
					//System.err.println("LocalUnifiedVisualization.populate()::Transferring " + visualization + "/" + visualizationParticleName + "{" + child + "} to " + this);
					addElement(visualizationParticleName, child);
				}
				getParticle(visualizationParticleName).setInitialized(true);
			}
		}
	}

	@Override
	public void parseMDProperty(String propertyName) {
		//PATCH need to make sure that setting each property to default is fine
		SynProperty property = (SynProperty) getProperty(propertyName);
		String defaultValue = property.getDefault();
		setPropertyValue(propertyName, defaultValue);
	}

	@Override
	public Object cloneThis() throws Exception {
		return new LocalUnifiedVisualization();
	}

	@Override
	public LocalElement createLocalElement(String elementType) {
		LocalParticle particle = getParticle(elementType);
		if (elementType.equals(LocalUnifiedSeriesConfig.TYPE) == true) {
			particle.setInitialized(true);
			String name = getNewName(LocalUnifiedSeriesConfig.TYPE, BEViewsElementNames.SERIES_CONFIG);
			LocalUnifiedSeriesConfig unifiedChartSeriesConfig = new LocalUnifiedSeriesConfig(this, name);
			unifiedChartSeriesConfig.setFolder(getFolder());
			unifiedChartSeriesConfig.setNamespace(getNamespace());
			unifiedChartSeriesConfig.setOwnerProject(getOwnerProject());
			particle.addElement(unifiedChartSeriesConfig);
			return unifiedChartSeriesConfig;
		}
		return super.createLocalElement(elementType);
	}

	@Override
	public BEViewsElement createMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("createMDChild");
	}

	@Override
	public void deleteMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("deleteMDChild");
	}

	@Override
	public void loadChild(String childrenType, String childName) {
		throw new UnsupportedOperationException("loadChild");
	}

	@Override
	public void loadChildByID(String childrenType, String childID) {
		throw new UnsupportedOperationException("loadChildByID");
	}

	@Override
	public void loadChildren(String childrenType) {
		throw new UnsupportedOperationException("loadChildren");
	}

	@Override
	public String getElementLocatorKey() {
		return getID();
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		if (nativeProperties.contains(prop.getName()) == true) {
			super.validateProperty(prop);
		}
	}

	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		if (nativeParticles.contains(particle.getName()) == true) {
			super.validateParticle(particle);
		}
		if (particle.getName().equals(LocalUnifiedSeriesConfig.TYPE) == true) {
			super.validateParticle(particle);
		}
	}

	@Override
	protected boolean validateReferences(LocalParticle particle) throws Exception {
		if (nativeParticles.contains(particle.getName()) == true) {
			return super.validateReferences(particle);
		}
		return true;
	}

	public void sync(LocalUnifiedVisualization visualization) {
		//sync properties
		for (String propertyName : getPropertyNames()) {
			SynProperty property = (SynProperty) getProperty(propertyName);
			if (property.getInternalStatus().equals(InternalStatusEnum.StatusModified) == false){
				//the property has not been modified, so check if it has been already set
				if (property.isArray() == false) {
					setPropertyValue(propertyName, visualization.getPropertyValue(propertyName));
				}
				else {
					setPropertyValues(propertyName, ((SynProperty)visualization.getProperty(propertyName)).getValues());
				}
			}
		}
		//sync series configs
		List<LocalElement> seriesConfigs = getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			LocalUnifiedSeriesConfig vizSeriesConfig = (LocalUnifiedSeriesConfig) visualization.getElementByID(LocalUnifiedSeriesConfig.TYPE, seriesConfig.getID());
			if (vizSeriesConfig != null) {
				//do a sync
				((LocalUnifiedSeriesConfig) seriesConfig).sync(vizSeriesConfig);
			}
		}
		//TODO should I worry about new series configs coming from outside
	}
}
