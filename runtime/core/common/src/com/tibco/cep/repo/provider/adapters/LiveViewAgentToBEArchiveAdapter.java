/**
 * 
 */
package com.tibco.cep.repo.provider.adapters;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.be.util.packaging.Constants;

/**
 * @author vpatil
 *
 */
public class LiveViewAgentToBEArchiveAdapter extends AbstractAgentToBEArchiveAdapter {
	
	public LiveViewAgentToBEArchiveAdapter(
            ClusterConfig config,
            LiveViewAgentClassConfig agentGroupConfig,
            String agentKey) {
        super(config, agentGroupConfig, agentKey);
	}
	
	public Constants.ArchiveType getType() {
    	return Constants.ArchiveType.LIVEVIEW;
    }

}
