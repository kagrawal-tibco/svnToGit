package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynNumericType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;

public class PropertyEnumerationContentProvider implements IStructuredContentProvider {
	
	private Object[] enumerations;

	@Override
	public Object[] getElements(Object inputElement) {
		return enumerations;
	}

	@Override
	public void dispose() {
		enumerations = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof SynProperty){
			SynProperty property = (SynProperty) newInput;
			ISynXSDTypeDefinition typeDefinition = property.getTypeDefinition();
			if (typeDefinition instanceof SynStringType) {
				if (property.getParent() instanceof LocalConfig) {
//					LocalConfig parent = (LocalConfig) property.getParent();
//					try {
//						enumerations = parent.getPropertyEnums(property.getName());
//					} catch (Exception e) {
//						throw new RuntimeException("could not read property enumerations for "+parent+"@"+property.getName(),e);
//					}
					List<Object> enumerationsList = ((SynStringType)typeDefinition).getEnumerations();
					enumerations = enumerationsList.toArray(new Object[enumerationsList.size()]);
				}
				else {
					List<Object> enumerationsList = ((SynStringType)typeDefinition).getEnumerations();
					enumerations = enumerationsList.toArray(new Object[enumerationsList.size()]);
				}
			}
			else if (typeDefinition instanceof SynNumericType) {
				SynNumericType numericType = (SynNumericType) typeDefinition;
				List<Object> enumerationsList = createRangeEnumeration(numericType.getMinInclusive(), numericType.getMaxInclusive());
				enumerations = enumerationsList.toArray(new Object[enumerationsList.size()]);
			}			
		}
	}
	
	private List<Object> createRangeEnumeration(String minInclusive, String maxInclusive) {
		List<Object> values = new ArrayList<Object>();
		if (minInclusive == null || minInclusive.trim().length() == 0) {
			return values;
		}
		if (maxInclusive == null || maxInclusive.trim().length() == 0) {
			return values;
		}

		try {
			int min = Integer.parseInt(minInclusive);
			int max = Integer.parseInt(maxInclusive);

			for (int i = min; i <= max; i++) {
				values.add(String.valueOf(i));
			}

			return values;
		} catch (NumberFormatException e) {
			return values;
		}
	}	

}
