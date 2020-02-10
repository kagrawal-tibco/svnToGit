package com.tibco.cep.studio.dashboard.core.listeners;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

public abstract class AbstractSynElementChangeListener implements ISynElementChangeListener {

	@Override
	public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {

	}

	@Override
	public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {

	}

	@Override
	public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {

	}

	@Override
	public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {

	}

}
