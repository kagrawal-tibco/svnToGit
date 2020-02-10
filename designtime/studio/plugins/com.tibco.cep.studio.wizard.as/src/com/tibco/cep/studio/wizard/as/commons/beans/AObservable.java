package com.tibco.cep.studio.wizard.as.commons.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import com.tibco.cep.studio.wizard.as.commons.support.beans.ExtendedPropertyChangeSupport;

public abstract class AObservable implements IObservable, Serializable {

	private ExtendedPropertyChangeSupport	changeSupport;

	private VetoableChangeSupport			vetoSupport;

	public final synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new ExtendedPropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(listener);
	}

	public final synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(listener);
	}

	public final synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new ExtendedPropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public final synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public final synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (vetoSupport == null) {
			vetoSupport = new VetoableChangeSupport(this);
		}
		vetoSupport.addVetoableChangeListener(listener);
	}

	public final synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
		if (listener == null || vetoSupport == null) {
			return;
		}
		vetoSupport.removeVetoableChangeListener(listener);
	}

	public final synchronized void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (vetoSupport == null) {
			vetoSupport = new VetoableChangeSupport(this);
		}
		vetoSupport.addVetoableChangeListener(propertyName, listener);
	}

	public final synchronized void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
		if (listener == null || vetoSupport == null) {
			return;
		}
		vetoSupport.removeVetoableChangeListener(propertyName, listener);
	}

	public final synchronized PropertyChangeListener[] getPropertyChangeListeners() {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners();
	}

	public final synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners(propertyName);
	}

	public final synchronized VetoableChangeListener[] getVetoableChangeListeners() {
		if (vetoSupport == null) {
			return new VetoableChangeListener[0];
		}
		return vetoSupport.getVetoableChangeListeners();
	}

	public final synchronized VetoableChangeListener[] getVetoableChangeListeners(String propertyName) {
		if (vetoSupport == null) {
			return new VetoableChangeListener[0];
		}
		return vetoSupport.getVetoableChangeListeners(propertyName);
	}

	protected final void firePropertyChange(PropertyChangeEvent event) {
		PropertyChangeSupport aChangeSupport = this.changeSupport;
		if (aChangeSupport == null) {
			return;
		}
		aChangeSupport.firePropertyChange(event);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeSupport aChangeSupport = this.changeSupport;
		if (aChangeSupport == null) {
			return;
		}
		aChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue, boolean checkIdentity) {

		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue, checkIdentity);
	}

	protected final void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
		PropertyChangeSupport aChangeSupport = this.changeSupport;
		if (aChangeSupport == null) {
			return;
		}
		aChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChange(String propertyName, double oldValue, double newValue) {
		firePropertyChange(propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
	}

	protected final void firePropertyChange(String propertyName, float oldValue, float newValue) {
		firePropertyChange(propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
	}

	protected final void firePropertyChange(String propertyName, int oldValue, int newValue) {
		PropertyChangeSupport aChangeSupport = this.changeSupport;
		if (aChangeSupport == null) {
			return;
		}
		aChangeSupport.firePropertyChange(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
	}

	protected final void firePropertyChange(String propertyName, long oldValue, long newValue) {
		firePropertyChange(propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
	}

	protected final void fireMultiplePropertiesChanged() {
		firePropertyChange(null, null, null);
	}

	protected final void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
		PropertyChangeSupport aChangeSupport = this.changeSupport;
		if (aChangeSupport == null) {
			return;
		}
		aChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	protected final void fireIndexedPropertyChange(String propertyName, int index, int oldValue, int newValue) {
		if (oldValue == newValue) {
			return;
		}
		fireIndexedPropertyChange(propertyName, index, Integer.valueOf(oldValue), Integer.valueOf(newValue));
	}

	protected final void fireIndexedPropertyChange(String propertyName, int index, boolean oldValue, boolean newValue) {
		if (oldValue == newValue) {
			return;
		}
		fireIndexedPropertyChange(propertyName, index, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
	}

	protected final void fireVetoableChange(PropertyChangeEvent event) throws PropertyVetoException {
		VetoableChangeSupport aVetoSupport = this.vetoSupport;
		if (aVetoSupport == null) {
			return;
		}
		aVetoSupport.fireVetoableChange(event);
	}

	protected final void fireVetoableChange(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException {
		VetoableChangeSupport aVetoSupport = this.vetoSupport;
		if (aVetoSupport == null) {
			return;
		}
		aVetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected final void fireVetoableChange(String propertyName, boolean oldValue, boolean newValue) throws PropertyVetoException {
		VetoableChangeSupport aVetoSupport = this.vetoSupport;
		if (aVetoSupport == null) {
			return;
		}
		aVetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected final void fireVetoableChange(String propertyName, double oldValue, double newValue) throws PropertyVetoException {
		fireVetoableChange(propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
	}

	protected final void fireVetoableChange(String propertyName, int oldValue, int newValue) throws PropertyVetoException {
		VetoableChangeSupport aVetoSupport = this.vetoSupport;
		if (aVetoSupport == null) {
			return;
		}
		aVetoSupport.fireVetoableChange(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
	}

	protected final void fireVetoableChange(String propertyName, float oldValue, float newValue) throws PropertyVetoException {
		fireVetoableChange(propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
	}

	protected final void fireVetoableChange(String propertyName, long oldValue, long newValue) throws PropertyVetoException {
		fireVetoableChange(propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
	}

}
