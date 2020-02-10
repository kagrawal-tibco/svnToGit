package com.tibco.cep.studio.dashboard.ui.viewers;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * A helper class used to indicate whether the contained element is selected.
 *
 * This is mainly used in check box table.
 *
 */
public class ElementCheckBoxWrapper {

    private boolean isChecked = false;

    private boolean isDefaultChecked = false;

    private LocalElement localElement = null;

    /**
     *
     */
    public ElementCheckBoxWrapper(LocalElement localElement) {
        this.localElement = localElement;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isDefaultChecked() {
        return isDefaultChecked;
    }

    public void setDefaultChecked(boolean isDefaultChecked) {
        this.isDefaultChecked = isDefaultChecked;
    }

    public String getDescription() throws Exception {
        return localElement.getDescription();
    }

    public String getDisplayName() throws Exception {
    	if (localElement.hasProperty(LocalConfig.PROP_KEY_DISPLAY_NAME)){
    		return localElement.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME);
    	}
        return localElement.getName();
    }

    public String getName() {
        return localElement.getName();
    }

    public boolean isExisting() {
        return localElement.isExisting();
    }

    public boolean isModified() {
        return localElement.isModified();
    }

    public boolean isNew() {
        return localElement.isNew();
    }

    public void setNew() throws Exception {
        localElement.setNew();
    }

    public void setRemoved() throws Exception {
        localElement.setRemoved();
    }

    public boolean equals(Object obj) {
    	if (obj instanceof ElementCheckBoxWrapper) {
    		ElementCheckBoxWrapper other = (ElementCheckBoxWrapper) obj;
    		return localElement.equals(other.localElement);
    	}
        return false;
    }

    public static ElementCheckBoxWrapper[] convertToWrapper(LocalElement[] localElementArray) {

        ElementCheckBoxWrapper[] wrappers = new ElementCheckBoxWrapper[localElementArray.length];
        for (int i = 0; i < wrappers.length; i++) {
            wrappers[i] = new ElementCheckBoxWrapper(localElementArray[i]);
        }

        return wrappers;
    }

    public static ElementCheckBoxWrapper[] convertToWrapper(List<LocalElement> localElementList) {
        ElementCheckBoxWrapper[] wrappers = new ElementCheckBoxWrapper[localElementList.size()];
        for (int i = 0; i < wrappers.length; i++) {
			wrappers[i] = new ElementCheckBoxWrapper(localElementList.get(i));
        }
        return wrappers;
    }

    public LocalElement getLocalElement() {
        return localElement;
    }

    public void setLocalElement(LocalElement localElement) {
        this.localElement = localElement;
    }

    public void setLocalElementChecked(LocalElement localElement, boolean setChecked) {
        this.localElement = localElement;
        setChecked(setChecked);
    }

	public String getFolder() {
		return localElement.getFolder();
	}

	public String getScopeName() {
		return localElement.getFolder();
	}
}
