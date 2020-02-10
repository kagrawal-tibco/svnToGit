package com.tibco.cep.dashboard.psvr.mal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesColor;
import com.tibco.cep.dashboard.psvr.mal.model.MALSkin;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
public final class MALSkinIndexer {

	private Logger logger;

	private MALSkin insightSkin;

	private HashMap<String, MALComponentColorSet> compTypeToDefaultComponentSetMap;

	private HashMap<String, List<MALComponentColorSet>> compTypeToComponentSetsMap;

	private Set<MALElement> elements;

	public MALSkinIndexer(Logger logger,MALSkin insightSkin) throws MALException {
		this.logger = logger;
		this.insightSkin = insightSkin;
		this.compTypeToDefaultComponentSetMap = new HashMap<String, MALComponentColorSet>();
		this.compTypeToComponentSetsMap = new HashMap<String, List<MALComponentColorSet>>();
		indexDefaultComponentSets();
		indexAllComponentSets();
		indexAllElements();
	}

	/**
	 * @throws MALException
	 *
	 */
	private void indexDefaultComponentSets() throws MALException {
		MALComponentColorSet[] defaultColorSets = insightSkin.getDefaultComponentColorSet();
		for (int i = 0; i < defaultColorSets.length; i++) {
			MALComponentColorSet defaultColorSet = defaultColorSets[i];
			String componentDefinitionType = resolveComponentDefinitionType(defaultColorSet);
			if (StringUtil.isEmptyOrBlank(componentDefinitionType) == true) {
				logger.log(Level.WARN, "No component is using " + defaultColorSet + ", dropping it from index...");
				//PORT not sure if we need if (compTypeToDefaultComponentSetMap.containsKey(componentDefinitionType) == true) here
			} else if (compTypeToDefaultComponentSetMap.containsKey(componentDefinitionType) == true) {
				throw new MALException(componentDefinitionType + " has more then one component color set definition mapped to it");
			} else {
				compTypeToDefaultComponentSetMap.put(componentDefinitionType, defaultColorSet);
			}
		}
	}

	/**
	 * @throws MALException
	 *
	 */
	private void indexAllComponentSets() throws MALException {
		MALComponentColorSet[] colorSets = insightSkin.getComponentColorSet();
		for (int i = 0; i < colorSets.length; i++) {
			MALComponentColorSet colorSet = colorSets[i];
			String componentDefinitionType = resolveComponentDefinitionType(colorSet);
			if (StringUtil.isEmptyOrBlank(componentDefinitionType) == true) {
				logger.log(Level.WARN, "No component is using " + colorSet + ", dropping it from index...");
				//PORT not sure if we need if (compTypeToDefaultComponentSetMap.containsKey(componentDefinitionType) == true) here
			}
			List<MALComponentColorSet> componentColorSetList = compTypeToComponentSetsMap.get(componentDefinitionType);
			if (componentColorSetList == null) {
				componentColorSetList = new LinkedList<MALComponentColorSet>();
				compTypeToComponentSetsMap.put(componentDefinitionType, componentColorSetList);
			}
			componentColorSetList.add(colorSet);
		}
	}

	//PORT resolveComponentDefinitionType should be added to the plug-in system
	private String resolveComponentDefinitionType(MALComponentColorSet colorSet) {
		String definitionType = colorSet.getDefinitionType().toLowerCase();
		int idx = definitionType.indexOf("colorset");
		if (idx == -1){
			throw new IllegalArgumentException(colorSet+" does not have a valid definition type");
		}
		return colorSet.getDefinitionType().substring(0,idx);
	}

	private void indexAllElements() {
		elements = new HashSet<MALElement>();
		elements.add(insightSkin);
		for (MALComponentColorSet colorset : insightSkin.getComponentColorSet()) {
			elements.add(colorset);
			if (colorset instanceof MALChartComponentColorSet){
				for (MALSeriesColor color : ((MALChartComponentColorSet)colorset).getSeriesColor()) {
					elements.add(color);
				}
			}
		}
	}

	public final MALSkin getInsightSkin() {
		return insightSkin;
	}

	public final MALComponentColorSet getDefaultColorSet(MALComponent component) {
		String definitionType = component.getDefinitionType();
		if (definitionType.equalsIgnoreCase("trendchartcomponent")) {
			definitionType = "ChartComponent";
		}
		return compTypeToDefaultComponentSetMap.get(definitionType);
	}

	public final List<MALComponentColorSet> getColorSetList(MALComponent component) {
		String definitionType = component.getDefinitionType();
		if (definitionType.equalsIgnoreCase("trendchartcomponent")) {
			definitionType = "ChartComponent";
		}
		return compTypeToComponentSetsMap.get(definitionType);
	}

	public final MALComponentColorSet getColorSet(MALComponent component, int index) {
		if (index == -1){
			return getDefaultColorSet(component);
		}
		List<MALComponentColorSet> colorSetList = getColorSetList(component);
		if (colorSetList == null || colorSetList.isEmpty() == true) {
			return getDefaultColorSet(component);
		}
		if (index >= colorSetList.size()) {
			index = 0;
		}
		return (MALComponentColorSet) colorSetList.get(index);
	}

	final void destroy() {
		compTypeToDefaultComponentSetMap.clear();
		compTypeToComponentSetsMap.clear();
	}

	public final Collection<MALElement> elements(){
		return elements;
	}
}