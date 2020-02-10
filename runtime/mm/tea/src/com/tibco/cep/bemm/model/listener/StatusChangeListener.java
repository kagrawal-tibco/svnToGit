package com.tibco.cep.bemm.model.listener;

import com.tibco.cep.bemm.model.Monitorable;

public interface StatusChangeListener {

	void onChange(Monitorable monitorableEntity, String oldStatus, String newStatus);
}
