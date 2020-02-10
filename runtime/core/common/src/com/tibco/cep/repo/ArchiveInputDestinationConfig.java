package com.tibco.cep.repo;

/**
 * This interface represents the Input Destination configuration in the BE Archive.
 * It contains the Input Destination URI and the Preprocessor RuleFunction URI.
 */
public interface ArchiveInputDestinationConfig {

    /**
     * URI of the input destination
     * @return the input destination URI
     */
    String getDestinationURI();

    /**
     * The preprocessor for this input destination
     * @return the preprocessor for this input destination
     */
    String getPreprocessor();

    int getNumWorker();

    int getQueueSize();

    int getWeight();

    /**
     * The threading model used by this endpoint.
     * @see com.tibco.cep.runtime.session.RuleSessionConfig.ThreadingModel
     * @return
     */
    String getThreadingModel();
    
    String getThreadAffinityRuleFunction();

}
