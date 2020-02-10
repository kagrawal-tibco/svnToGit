package com.tibco.cep.studio.dashboard.core.model.SYN.providers;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.interfaces.ISynPropertyInstanceProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDPropertyProvider;

/**
 * @
 *
 */
public class SynPropertyInstanceProvider extends SynXSDPropertyProvider implements ISynPropertyInstanceProvider {

    public SynPropertyInstanceProvider() {
        super();
    }

    public String getPropertyValue(String key) {
        SynProperty attr = (SynProperty) attributeMap.get(key);
        if (null != attr.getValue()) {
            return attr.getValue();
        }
        throw new IllegalArgumentException(key + " does not exist");
    }

    public void setPropertyValue(String key, String value) {
        SynProperty attr = (SynProperty) attributeMap.get(key);
        if (null != attr) {
            attr.setValue(value);
        }
        else {
            // We're trying to set a non-existent property.
            // Removing this exception is currently a hack, and is caused by
            // removing properties from TextVisualizations when they're part of
            // a pagesetselector component. These TextVisualizations should
            // really
            // become a new visualization type instead of being a partial
            // TextVisualization.
            /*
             * throw new SynUnsupportedOperationException( "This method is only
             * supported for subclasses of
             * com.tibco.cep.designer.dashboard.core.model.SYN.SynProperty");
             */
        }

    }

    /**
     * Convenience method to handle special cases of data binding framework
     * where the parent selection updates the child property with same value
     * as the earlier selected value.
     * Added on 12th May 2008 by ashima as Fix for Bug# 8523 and 8657
     * Fire Property Change is not triggered in case the modified
     * value name is same as earlier set value though their IDs are different
     *
     * @param key
     * @return
     * @throws Exception
     */
	public void setPropertyValueForce(String key, String value) {
		SynProperty attr = (SynProperty) attributeMap.get(key);
		if (null != attr) {
			attr.setValueForce(value);
		}
	}

	public void setPropertyValues(String key, List<String> value) {
		SynProperty attr = (SynProperty) attributeMap.get(key);

		if (null != attr) {
			attr.setValues(value);
		} else {
			throw new UnsupportedOperationException("This method is only supported for subclasses of com.tibco.cep.designer.dashboard.core.model.SYN.SynProperty");
		}
	}

	public boolean hasProperty(String key) {
		return attributeMap.containsKey(key);
	}

}
