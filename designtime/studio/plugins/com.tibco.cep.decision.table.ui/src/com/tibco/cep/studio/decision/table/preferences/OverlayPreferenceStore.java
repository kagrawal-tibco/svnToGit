package com.tibco.cep.studio.decision.table.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;

public class OverlayPreferenceStore implements IPreferenceStore {

	private IPreferenceStore currentPreferenceStore;
	private IPreferenceStore targetPreferenceStore;
	
	private OverlayKey[] keys;

	public OverlayKey[] getKeys() {
		return keys;
	}

	public OverlayPreferenceStore(IPreferenceStore preferenceStore, OverlayKey[] keys) {
		this.targetPreferenceStore = preferenceStore;
		this.keys = keys;
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		targetPreferenceStore.addPropertyChangeListener(listener);		
		currentPreferenceStore.addPropertyChangeListener(listener);
	}

	public boolean contains(String name) {
		return getCurrentPreferenceStore().contains(name);
	}

	private IPreferenceStore getCurrentPreferenceStore() {
		if (currentPreferenceStore == null) {
			currentPreferenceStore = new PreferenceStore();
		}
		return currentPreferenceStore;
	}
	public void firePropertyChangeEvent(String name, Object oldValue,
			Object newValue) {
		getCurrentPreferenceStore().firePropertyChangeEvent(name, oldValue, newValue);		
	}

	public boolean getBoolean(String name) {
		return getCurrentPreferenceStore().getBoolean(name);
	}

	public boolean getDefaultBoolean(String name) {
		return getCurrentPreferenceStore().getDefaultBoolean(name);
	}

	public double getDefaultDouble(String name) {
		return getCurrentPreferenceStore().getDefaultDouble(name);
	}

	public float getDefaultFloat(String name) {
		return getCurrentPreferenceStore().getDefaultFloat(name);
	}

	public int getDefaultInt(String name) {
		return getCurrentPreferenceStore().getDefaultInt(name);
	}

	public long getDefaultLong(String name) {
		return getCurrentPreferenceStore().getDefaultLong(name);
	}

	public String getDefaultString(String name) {
		return getCurrentPreferenceStore().getDefaultString(name);
	}

	public double getDouble(String name) {
		return getCurrentPreferenceStore().getDouble(name);
	}

	public float getFloat(String name) {
		return getCurrentPreferenceStore().getFloat(name);
	}

	public int getInt(String name) {
		return getCurrentPreferenceStore().getInt(name);
	}

	public long getLong(String name) {
		return getCurrentPreferenceStore().getLong(name);
	}

	public String getString(String name) {
		return getCurrentPreferenceStore().getString(name);
	}

	public boolean isDefault(String name) {
		return getCurrentPreferenceStore().isDefault(name);
	}

	public boolean needsSaving() {
		return getCurrentPreferenceStore().needsSaving();
	}

	public void putValue(String name, String value) {
		getCurrentPreferenceStore().putValue(name, value);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		if (targetPreferenceStore != null) {
			targetPreferenceStore.removePropertyChangeListener(listener);		
		}
		if (currentPreferenceStore != null) {
			currentPreferenceStore.removePropertyChangeListener(listener);
		}
	}

	public void setDefault(String name, double value) {
		getCurrentPreferenceStore().setDefault(name, value);
	}

	public void setDefault(String name, float value) {
		getCurrentPreferenceStore().setDefault(name, value);
	}

	public void setDefault(String name, int value) {
		getCurrentPreferenceStore().setDefault(name, value);
	}

	public void setDefault(String name, long value) {
		getCurrentPreferenceStore().setDefault(name, value);
	}

	public void setDefault(String name, String defaultObject) {
		getCurrentPreferenceStore().setDefault(name, defaultObject);
	}

	public void setDefault(String name, boolean value) {
		getCurrentPreferenceStore().setDefault(name, value);
	}

	public void setToDefault(String name) {
		getCurrentPreferenceStore().setToDefault(name);
	}

	public void setValue(String name, double value) {
		getCurrentPreferenceStore().setValue(name, value);
	}

	public void setValue(String name, float value) {
		getCurrentPreferenceStore().setValue(name, value);
	}

	public void setValue(String name, int value) {
		getCurrentPreferenceStore().setValue(name, value);
	}

	public void setValue(String name, long value) {
		getCurrentPreferenceStore().setValue(name, value);
	}

	public void setValue(String name, String value) {
		getCurrentPreferenceStore().setValue(name, value);
	}

	public void setValue(String name, boolean value) {
		getCurrentPreferenceStore().setValue(name, value);
	}

	public void performApply() {
		performOk();
	}

	public void performDefaults() {
		loadDefaults();
	}

	private void loadDefaults() {
		for (OverlayKey key : keys) {
			loadDefaultProperty(key);
		}
	}

	public void load() {
		for (OverlayKey key : keys) {
			loadProperty(key);
		}
	}
	
	private void loadDefaultProperty(OverlayKey key) {
		OVERLAYKEYTYPES type = key.getType();
		switch (type) {
		case STRING:
		{
			String defaultValue = targetPreferenceStore.getDefaultString(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
		}

		case INT:
		{
			int defaultValue = targetPreferenceStore.getDefaultInt(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
		}
			
		case DOUBLE:
		{
			double defaultValue = targetPreferenceStore.getDefaultDouble(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
			break;
		}
			
		case LONG:
		{
			long defaultValue = targetPreferenceStore.getDefaultLong(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
			break;
		}
			
		case BOOLEAN:
		{
			boolean defaultValue = targetPreferenceStore.getDefaultBoolean(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
			break;
		}
			
		default:
			break;
		}
	}

	private void loadProperty(OverlayKey key) {
		OVERLAYKEYTYPES type = key.getType();
		switch (type) {
		case STRING:
		{
			String defaultValue = targetPreferenceStore.getString(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
		}
		
		case INT:
		{
			int defaultValue = targetPreferenceStore.getInt(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
		}
		
		case DOUBLE:
		{
			double defaultValue = targetPreferenceStore.getDouble(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
			break;
		}
		
		case LONG:
		{
			long defaultValue = targetPreferenceStore.getLong(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
			break;
		}
		
		case BOOLEAN:
		{
			boolean defaultValue = targetPreferenceStore.getBoolean(key.getKey());
			getCurrentPreferenceStore().setValue(key.getKey(), defaultValue);
			break;
		}
		
		default:
			break;
		}
	}
	
	public boolean performOk() {
		for (OverlayKey key : keys) {
			setProperty(key);
		}
		return true;
	}

	private void setProperty(OverlayKey key) {
		OVERLAYKEYTYPES type = key.getType();
		switch (type) {
		case STRING:
		{
			String origProp = targetPreferenceStore.getString(key.getKey());
			String currentProp = getCurrentPreferenceStore().getString(key.getKey());
			
			if (currentProp != null && !currentProp.equals(origProp)) {
				targetPreferenceStore.setValue(key.getKey(), currentProp);
			}
			break;
		}

		case INT:
		{
			int origProp = targetPreferenceStore.getInt(key.getKey());
			int currentProp = getCurrentPreferenceStore().getInt(key.getKey());
			
			if (!(currentProp == origProp)) {
				targetPreferenceStore.setValue(key.getKey(), currentProp);
			}
			break;
		}
			
		case DOUBLE:
		{
			double origProp = targetPreferenceStore.getDouble(key.getKey());
			double currentProp = getCurrentPreferenceStore().getDouble(key.getKey());
			
			if (!(currentProp == origProp)) {
				targetPreferenceStore.setValue(key.getKey(), currentProp);
			}
			break;
		}
			
		case LONG:
		{
			long origProp = targetPreferenceStore.getLong(key.getKey());
			long currentProp = getCurrentPreferenceStore().getLong(key.getKey());
			
			if (!(currentProp == origProp)) {
				targetPreferenceStore.setValue(key.getKey(), currentProp);
			}
			break;
		}
			
		case BOOLEAN:
		{
			boolean origProp = targetPreferenceStore.getBoolean(key.getKey());
			boolean currentProp = getCurrentPreferenceStore().getBoolean(key.getKey());
			
			if (!(currentProp == origProp)) {
				targetPreferenceStore.setValue(key.getKey(), currentProp);
			}
			break;
		}
			
		default:
			break;
		}
	}

}
