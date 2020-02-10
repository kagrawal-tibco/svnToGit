package com.tibco.cep.studio.core.migration;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;


/*
@author ssailapp
@date Feb 25, 2011
 */

public class TimeEventMigrator extends EntityMigrator {

	public TimeEventMigrator() {
		extension = CommonIndexUtils.TIME_EXTENSION;
	}
}
