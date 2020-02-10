package com.tibco.cep.runtime.session.impl;

import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;

/* used for hotdeploy of new concepts and changed concept properties */
public interface HotDeployListener {
	void entitiesAdded() throws Exception;
	void entitiesChanged(Collection<Class<Entity>> changedClasses);
}
