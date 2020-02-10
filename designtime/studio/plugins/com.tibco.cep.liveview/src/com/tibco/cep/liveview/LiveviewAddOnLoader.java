/**
 * 
 */
package com.tibco.cep.liveview;

import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;

/**
 * @author vpatil
 *
 */
public class LiveviewAddOnLoader extends AbstractAddOnLoader<CoreOntologyAdapter> implements IAddOnLoader<CoreOntologyAdapter> {
	private static final String LV_PLUGIN_ID = "com.tibco.cep.liveview";//$NON-NLS-1$
	public LiveviewAddOnLoader() {
	}
	@Override
	public AddOn getAddOn() throws Exception {
		return super.loadAddOn(LV_PLUGIN_ID);
	}
	
	@Override
	public CoreOntologyAdapter getOntology(String projectName) {
		return new CoreOntologyAdapter(StudioProjectCache.getInstance().getIndex(projectName));
	}
}
