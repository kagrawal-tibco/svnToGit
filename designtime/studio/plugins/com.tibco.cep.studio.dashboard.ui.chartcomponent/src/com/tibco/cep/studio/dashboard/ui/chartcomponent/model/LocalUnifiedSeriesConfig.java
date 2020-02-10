package com.tibco.cep.studio.dashboard.ui.chartcomponent.model;

import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTwoDimSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalUnifiedSeriesConfig extends LocalTwoDimSeriesConfig {

	public static final String TYPE = LocalUnifiedSeriesConfig.class.getSimpleName();

	public static final String PROP_KEY_SERIES_TYPE = "SeriesType";

	private static final String[] TYPES_TO_BE_UNIFIED = new String[] { BEViewsElementNames.CHART_SERIES_CONFIG, BEViewsElementNames.RANGE_PLOT_CHART_SERIES_CONFIG, BEViewsElementNames.TEXT_SERIES_CONFIG };

	private String nativeType;

	private List<String> nativeProperties;

	private List<String> nativeParticles;

	private LocalUnifiedSeriesConfig() {
		super(null, TYPE, "");
	}

	LocalUnifiedSeriesConfig(LocalElement parentElement, String name) {
		super(parentElement, TYPE, name);
	}

	LocalUnifiedSeriesConfig(LocalElement parentElement, LocalSeriesConfig seriesConfig) {
		super(parentElement, TYPE, seriesConfig.getName());
		populate(seriesConfig);
	}

	@Override
	public void setupProperties() {
		for (String type : TYPES_TO_BE_UNIFIED) {
			//add properties
			List<String> existingNames = getPropertyNames();
	    	List<SynProperty> propertyList = ViewsConfigReader.getInstance().getPropertyList(type);
	    	for (SynProperty synProperty : propertyList) {
	    		if (existingNames.contains(synProperty.getName()) == false) {
	    			//add iff it does not already exist
	    			addProperty(this,(ISynXSDAttributeDeclaration) synProperty.clone());
	    		}
			}
	    	//add particles
	    	existingNames = getParticleNames();
	    	List<LocalParticle> particleList = ViewsConfigReader.getInstance().getParticleList(type);
	    	for (LocalParticle localParticle : particleList) {
	    		if (existingNames.contains(localParticle.getName()) == false) {
	    			//add iff it does not already exist
	    			addParticle((LocalParticle) localParticle.clone());
	    		}
			}
		}
		//add series type as a property
		addProperty(this,new SynOptionalProperty(PROP_KEY_SERIES_TYPE,new SynStringType()));
	}

	private void populate(LocalSeriesConfig seriesConfig) {
		//PATCH set the eobject since search needs it
		setEObject(seriesConfig.getEObject());
		//set the native type , we will use it for validation
		nativeType = seriesConfig.getInsightType();
		nativeProperties = seriesConfig.getPropertyNames();
		nativeParticles = seriesConfig.getParticleNames(true);
		//transfer properties
		List<String> names = seriesConfig.getPropertyNames();
		for (String name : names) {
//			System.out.println("LocalUnifiedSeriesConfig.populate()::Transfering " + seriesConfig+ "/" + name + "{" + seriesConfig.getPropertyValue(name) + "} to " + this);
			SynProperty property = (SynProperty) seriesConfig.getProperty(name);
			if (property.isArray() == true) {
				((SynProperty)getProperty(name)).setValues(property.getValues());
			}
			else {
				setPropertyValue(name, seriesConfig.getPropertyValue(name));
			}
		}
		//transfer action rule
		LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
		//load the action rule properties
		names = actionRule.getPropertyNames();
		for (String name : names) {
			actionRule.getPropertyValue(name);
		}
		//load the action rule particles
		names = actionRule.getParticleNames(true);
		for (String particleName : names) {
			actionRule.getChildren(particleName);
		}
		addElement(BEViewsElementNames.ACTION_RULE, actionRule);
		getParticle(BEViewsElementNames.ACTION_RULE).setInitialized(true);
		if (nativeType.equals(BEViewsElementNames.TEXT_SERIES_CONFIG) == true) {
			//remove either text or indicator's properties, we do this after we populate the properties to prevent NPE in SynProperty
			String propertyPrefix = "indicator";
			if (seriesConfig.isPropertyValueSameAsDefault("TextValueColumnField") == true) {
				propertyPrefix = "text";
			}
			//we don't have indicator , remove all indicator properties
			ListIterator<String> listIterator = nativeProperties.listIterator();
			while (listIterator.hasNext()) {
				String propertyName = (String) listIterator.next();
				if (propertyName.toLowerCase().startsWith(propertyPrefix) == true) {
					listIterator.remove();
				}
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
		return new LocalUnifiedSeriesConfig();
	}

	@Override
	public LocalElement createLocalElement(String elementType) {
		String currInsightType = insightType;
		insightType = BEViewsElementNames.CHART_SERIES_CONFIG;
		try {
			return super.createLocalElement(elementType);
		} finally {
			insightType = currInsightType;
		}
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
	}

	@Override
	public String getElementLocatorKey() {
		return getID();
	}

	protected boolean isToBeValidated(SynProperty prop) {
		return nativeProperties.contains(prop.getName());
	}

	protected String getCategoryFieldPropertyName(){
		String propertyName = "CategoryAxisField";
		if (BEViewsElementNames.TEXT_SERIES_CONFIG.equals(nativeType) == true) {
			propertyName = "CategoryColumnField";
		}
		return propertyName;
	}

	protected String getCategoryDisplayLabelPropertyName(){
		String propertyName = "CategoryAxisFieldDisplayFormat";
		if (BEViewsElementNames.TEXT_SERIES_CONFIG.equals(nativeType) == true) {
			propertyName = "CategoryColumnFieldDisplayFormat";
		}
		return propertyName;
	}

	@Override
	protected List<Object> getCategoryFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
	}

	protected String getValueFieldPropertyName(){
		String propertyName = "ValueAxisField";
		if (BEViewsElementNames.TEXT_SERIES_CONFIG.equals(nativeType) == true) {
			propertyName = "TextValueColumnField";
			try {
				if (isPropertyValueSameAsDefault("IndicatorValueColumnField") == false){
					propertyName = "IndicatorValueColumnField";
				}
			} catch (Exception e) {
				//ignore
			}
		}
		return propertyName;
	}

	protected String getValueDisplayLabelPropertyName(){
		String propertyName = "ValueAxisFieldDisplayFormat";
		if (BEViewsElementNames.TEXT_SERIES_CONFIG.equals(nativeType) == true) {
			propertyName = "TextValueColumnFieldDisplayFormat";
			try {
				if (isPropertyValueSameAsDefault("IndicatorValueColumnFieldDisplayFormat") == false){
					propertyName = "IndicatorValueColumnFieldDisplayFormat";
				}
			} catch (Exception e) {
				//ignore
			}
		}
		return propertyName;
	}

	protected String getTooltipPropertyName(){
		String propertyName = "ValueAxisFieldTooltipFormat";
		if (BEViewsElementNames.TEXT_SERIES_CONFIG.equals(nativeType) == true) {
			propertyName = "TextValueColumnFieldTooltipFormat";
			try {
				if (isPropertyValueSameAsDefault("IndicatorValueColumnFieldTooltipFormat") == false){
					propertyName = "IndicatorValueColumnFieldTooltipFormat";
				}
			} catch (Exception e) {
				//ignore
			}
		}
		return propertyName;
	}

	@Override
	protected List<Object> getValueFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_FIELD);
	}

	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		if (nativeParticles.contains(particle.getName()) == true) {
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

	public void sync(LocalUnifiedSeriesConfig seriesConfig) {
		LocalElement actionRule = getElement(BEViewsElementNames.ACTION_RULE);
		LocalDataSource dataSource = (LocalDataSource) actionRule.getElement(BEViewsElementNames.DATA_SOURCE);

		LocalElement incomingActionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
		LocalDataSource incomingDataSource = (LocalDataSource) incomingActionRule.getElement(BEViewsElementNames.DATA_SOURCE);

		if (dataSource.equals(incomingDataSource) == true) {
			//we are using the same data source, lets sync the properties if needed
			for (String propertyName : getPropertyNames()) {
				SynProperty property = (SynProperty) getProperty(propertyName);
				if (property.getInternalStatus().equals(InternalStatusEnum.StatusModified) == false){
					//the property has not been modified, so check if it has been already set
					if (property.isArray() == false) {
						setPropertyValue(propertyName, seriesConfig.getPropertyValue(propertyName));
					}
					else {
						setPropertyValues(propertyName, ((SynProperty)seriesConfig.getProperty(propertyName)).getValues());
					}
				}
			}
			//also sync the action rule properties
			for (String propertyName : actionRule.getPropertyNames()) {
				SynProperty property = (SynProperty) actionRule.getProperty(propertyName);
				if (property.getInternalStatus().equals(InternalStatusEnum.StatusModified) == false){
					//the property has not been modified, so check if it has been already set
					if (property.isArray() == false) {
						actionRule.setPropertyValue(propertyName, incomingActionRule.getPropertyValue(propertyName));
					}
					else {
						actionRule.setPropertyValues(propertyName, ((SynProperty)incomingActionRule.getProperty(propertyName)).getValues());
					}
				}
			}
			//also sync the action rule query params , they could change
			List<LocalElement> queryParams = actionRule.getChildren(LocalActionRule.ELEMENT_KEY_QUERY_PARAM);
			for (LocalElement queryParam : queryParams) {
				//find the query param in the incoming rule
				LocalElement incomingQueryParam = incomingActionRule.getParticle(LocalActionRule.ELEMENT_KEY_QUERY_PARAM).getActiveElement(queryParam.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
				if (incomingQueryParam == null || incomingQueryParam.getPropertyValue(LocalQueryParam.PROP_KEY_TYPE).equals(queryParam.getPropertyValue(LocalQueryParam.PROP_KEY_TYPE)) == false) {
					//the queryParam is no longer valid , remove it
					actionRule.removeElement(queryParam);
				}
			}
			List<LocalElement> incomingQueryParams = incomingActionRule.getChildren(LocalActionRule.ELEMENT_KEY_QUERY_PARAM);
			for (LocalElement incomingQueryParam : incomingQueryParams) {
				//find the incoming query param in the rule
				LocalElement queryParam = actionRule.getParticle(LocalActionRule.ELEMENT_KEY_QUERY_PARAM).getActiveElement(incomingQueryParam.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
				if (queryParam == null) {
					//the queryParam is new, create it
					actionRule.addElement(LocalActionRule.ELEMENT_KEY_QUERY_PARAM, (LocalElement) incomingQueryParam.clone());
				}
			}
		}
	}

}