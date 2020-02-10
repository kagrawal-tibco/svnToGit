package com.tibco.cep.studio.core.index;

import java.util.Date;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexPackage;

public class IndexModificationAdapter implements Adapter {

	private DesignerProject fIndex;

	public IndexModificationAdapter(DesignerProject index) {
		this.fIndex = index;
	}

	@Override
	public Notifier getTarget() {
		return fIndex;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return true;
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (fIndex == notification.getNotifier()) {
			int featureID = notification.getFeatureID(DesignerProject.class);
			if (featureID == IndexPackage.DESIGNER_PROJECT__LAST_MODIFIED
					|| featureID == IndexPackage.DESIGNER_PROJECT__LAST_PERSISTED) {
				return;
			}
			this.fIndex.setLastModified(new Date());
		} else {
			System.out.println("not the same");
		}
	}

	@Override
	public void setTarget(Notifier newTarget) {
	}

}
