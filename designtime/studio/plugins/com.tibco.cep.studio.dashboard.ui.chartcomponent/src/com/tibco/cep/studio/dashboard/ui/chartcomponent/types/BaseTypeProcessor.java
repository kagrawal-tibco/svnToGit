package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class BaseTypeProcessor implements TypeProcessor {
	
	protected Collection<String> getVizualizationTypes(LocalElement localElement) throws Exception {
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		SynProperty property = (SynProperty) visualization.getProperty(LocalUnifiedVisualization.PROP_KEY_VIZ_TYPES);
		return new ArrayList<String>(property.getValues());
	}
	
	public void subTypeChanged(LocalElement localElement, ChartSubType oldSubType, ChartSubType newSubType) throws Exception{
		//does nothing
	}

	public void prepareForEditing(LocalElement localElement) throws Exception {
		//does nothing
	}
	
	public void seriesAdded(LocalElement localElement, LocalElement series) throws Exception{
		//does nothing;
	}
	
	public void seriesRemoved(LocalElement localElement, LocalElement series) throws Exception{
		//does nothing;
	}
	
	@Override
	public LocalElement createNativeElement(LocalElement localElement) throws Exception {
		LocalElement nativeComponent = createNativeComponent(localElement.getRoot(), localElement.getName());
		transferComponentLevelDetails(localElement, nativeComponent);
		//process visualization
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		LocalElement nativeVisualization = createNativeVisualization(nativeComponent,visualization.getName());
		transferProperties(visualization,nativeVisualization);
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			LocalElement nativeSeriesConfig = createNativeSeriesConfig(nativeVisualization, seriesConfig.getName());
			transferProperties(seriesConfig, nativeSeriesConfig);
			//transfer action rule 
			LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
			nativeSeriesConfig.addElement(BEViewsElementNames.ACTION_RULE, (LocalElement) actionRule.clone());
		}
		return nativeComponent;
	}

	protected abstract LocalElement createNativeComponent(LocalElement parent, String name) throws Exception;

	protected void transferComponentLevelDetails(LocalElement localElement, LocalElement nativeComponent) throws Exception {
		transferProperties(localElement, nativeComponent);
		//related components 
		List<LocalElement> relatedComponents = localElement.getChildren(BEViewsElementNames.RELATED_COMPONENT);
		for (LocalElement relatedComponent : relatedComponents) {
			nativeComponent.addElement(BEViewsElementNames.RELATED_COMPONENT, relatedComponent);
		}
	}

	protected void transferProperties(LocalElement localElement, LocalElement nativeElement) throws Exception {
		List<String> names = nativeElement.getPropertyNames();
		for (String name : names) {
			transferProperty(name, localElement, nativeElement);
		}
	}

	protected void transferProperty(String name, LocalElement localElement, LocalElement nativeElement) throws Exception {
		SynProperty property = (SynProperty) localElement.getProperty(name);
		if (property.isAlreadySet() == true && localElement.isPropertyValueSameAsDefault(name) == false) {
			if (property.isArray() == false) {
//					System.out.println("BaseTypeProcessor.transferProperties():Transferring "+name+"["+localElement.getPropertyValue(name)+"] from "+localElement+" to "+nativeElement);
				nativeElement.setPropertyValue(name, localElement.getPropertyValue(name));
			}
			else {
				List<String> values = property.getValues();
//					System.out.println("BaseTypeProcessor.transferProperties():Transferring "+name+"["+values+"] from "+localElement+" to "+nativeElement);
				nativeElement.setPropertyValues(name, values);
			}
		}
	}
	
	public abstract LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception;
	
	public abstract LocalElement createNativeSeriesConfig(LocalElement parent, String name) throws Exception;

}