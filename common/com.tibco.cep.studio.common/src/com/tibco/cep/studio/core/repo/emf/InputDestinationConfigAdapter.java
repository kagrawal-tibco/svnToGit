/**
 * 
 */
package com.tibco.cep.studio.core.repo.emf;

import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;

/**
 * @author aathalye
 *
 */
public class InputDestinationConfigAdapter
        implements ArchiveInputDestinationConfig {
	
	/**
	 * The adapted {@linkplain InputDestination}
	 */
	private InputDestination adapted;
	
	public InputDestinationConfigAdapter(final InputDestination adapted) {
		this.adapted = adapted;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEArchiveResource.InputDestinationConfig#getDestinationURI()
	 */
	
	public String getDestinationURI() {
		return adapted.getDestinationURI();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEArchiveResource.InputDestinationConfig#getNumWorker()
	 */
	
	public int getNumWorker() {
		return adapted.getWorkers().getValue();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEArchiveResource.InputDestinationConfig#getPreprocessor()
	 */
	
	public String getPreprocessor() {
		//Get path of the preprocessor RF
		return adapted.getPreprocessor();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEArchiveResource.InputDestinationConfig#getQueueSize()
	 */
	
	public int getQueueSize() {
		return adapted.getQueueSize();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.BEArchiveResource.InputDestinationConfig#getWeight()
	 */
	
	public int getWeight() {
		throw new UnsupportedOperationException("Not sure if this is needed");
	}

    public String getThreadingModel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ArchiveInputDestinationConfig#getThreadAffinityRuleFunction()
	 */
	public String getThreadAffinityRuleFunction() {
		return adapted.getThreadAffinityRuleFunction();
	}

}
