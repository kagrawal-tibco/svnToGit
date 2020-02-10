package com.tibco.cep.runtime.service.management;

public interface HotDeployMethods {
    void loadAndDeploy();

    /**
     * Deploy an external class matching the specified rule function, and
     * implementation specified by implName.
     * <p>
     * Useful for remote BUI deployment where deploy activity
     * can be carried out for a single Table post approval process
     * </p>
     *
     * @param vrfURI:   Fully qualified name of the virtual Rule Function
     * @param implName: Name of the {@link com.tibco.cep.decisionproject.ontology.Implementation}
     */
    void loadAndDeploy(String vrfURI, String implName);
    
    /**
     * Unload external class matching the specified rule function, and
     * implementation specified by implName.
     * <p>
     * If the rule function does not exist, and Exception will be thrown
     * </p>
     * <p>
     * Use this function to specifically unload single class instances instead of
     * all unload.
     * </p>
     *
     * @param vrfURI:   Fully qualified name of the virtual Rule Function
     * @param implName: Name of the {@link com.tibco.cep.decisionproject.ontology.Implementation}
     * @throws Exception
     */
    void unloadClass(String vrfURI, String implName) throws Exception;
}
