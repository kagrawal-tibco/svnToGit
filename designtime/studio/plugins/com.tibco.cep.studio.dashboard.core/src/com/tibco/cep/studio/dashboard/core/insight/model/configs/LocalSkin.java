package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalSkin extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.SKIN;
	
	private static final List<String> KNOWN_TYPES = Arrays.asList(BEViewsElementNames.CHART_COLOR_SET, BEViewsElementNames.TEXT_COLOR_SET);

	public LocalSkin() {
		super(THIS_TYPE);
	}

	public LocalSkin(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalSkin(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	public List<LocalElement> getFamily() throws Exception {
		HashSet<LocalElement> family = new HashSet<LocalElement>();
		//get all component color sets
		List<LocalElement> allReferences = getAllReferences(false);
		for (LocalElement localElement : allReferences) {
			if (family.contains(localElement) == false){
				family.add(localElement);
				//get all series colors for chart component color set
				family.addAll(localElement.getAllReferences(false));
			}
		}
		return new LinkedList<LocalElement>(family);
	}
	
	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		if (particle.getName().equals("ComponentColorSet") == true){
			Map<String, List<LocalElement>> colorSetsByType = sortElementByType(getChildren("ComponentColorSet"));
			if (colorSetsByType.isEmpty() == true){
				addValidationErrorMessage("No color set selected for "+KNOWN_TYPES);
			}
			else {
				for (String knownType : KNOWN_TYPES) {
					List<LocalElement> colorSets = colorSetsByType.get(knownType);
					if (colorSets == null || colorSets.isEmpty() == true) {
						addValidationErrorMessage("No color set selected for " + knownType);
					}
				}
			}
		}
		else if (particle.getName().equals("DefaultComponentColorSet") == true){
			Map<String, List<LocalElement>> defaultColorSetsByType = sortElementByType(getChildren("DefaultComponentColorSet"));
			if (defaultColorSetsByType.isEmpty() == true){
				addValidationErrorMessage("No default color set selected for "+KNOWN_TYPES);
			}
			else {
				for (String knownType : KNOWN_TYPES) {
					List<LocalElement> colorSets = defaultColorSetsByType.get(knownType);
					if (colorSets == null || colorSets.isEmpty() == true) {
						addValidationErrorMessage("No default color set selected for " + knownType);
					}
				}				
			}
		}
		else {
			super.validateParticle(particle);
		}
	}

	private Map<String,List<LocalElement>> sortElementByType(List<LocalElement> elements) {
		Map<String,List<LocalElement>> map = new HashMap<String, List<LocalElement>>();
		for (LocalElement element : elements) {
			String elementType = element.getElementType();
			List<LocalElement> list = map.get(elementType);
			if (list == null){
				list = new LinkedList<LocalElement>();
				map.put(elementType, list);
			}
			list.add(element);
		}
		return map;
	}
}
