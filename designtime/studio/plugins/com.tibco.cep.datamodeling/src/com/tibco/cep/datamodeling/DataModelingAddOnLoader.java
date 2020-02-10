package com.tibco.cep.datamodeling;

import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;


/*
@author ssailapp
@date Feb 10, 2011
 */

public class DataModelingAddOnLoader extends AbstractAddOnLoader<CoreOntologyAdapter> implements IAddOnLoader<CoreOntologyAdapter> {
	
	public static final String DATA_MODELING_PLUGIN_ID = "com.tibco.cep.datamodeling.common"; //$NON-NLS-1$

	public DataModelingAddOnLoader() {
	}
	
	@Override
	public AddOn getAddOn() throws Exception {
		
		return super.loadAddOn(DATA_MODELING_PLUGIN_ID);
	}
	
	
	@Override
	public CoreOntologyAdapter getOntology(String projectName) {
		return new CoreOntologyAdapter(StudioProjectCache.getInstance().getIndex(projectName));
	}

//	@Override
//	public String getName() {
//		return AddonUtil.ADDON_DATAMODELING;
//	}
//
//	@Override
//	public String getAgentClass() {
//		return null;
//	}

}
