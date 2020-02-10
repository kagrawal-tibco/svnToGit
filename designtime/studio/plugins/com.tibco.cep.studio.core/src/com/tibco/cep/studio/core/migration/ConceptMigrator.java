package com.tibco.cep.studio.core.migration;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/*
@author ssailapp
@date Feb 16, 2011
 */

public class ConceptMigrator extends EntityMigrator {

	public ConceptMigrator() {
		extension = CommonIndexUtils.CONCEPT_EXTENSION;
	}

}
