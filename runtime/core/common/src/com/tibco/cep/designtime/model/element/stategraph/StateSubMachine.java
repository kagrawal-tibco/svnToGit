package com.tibco.cep.designtime.model.element.stategraph;


public interface StateSubMachine extends StateComposite{
    /**
     * Get the URI of this submachine.
     *
     * @return The URI of this submachine.
     */
    public String getSubmachineURI();
    
    public StateMachine getReferencedStateMachine();


    /**
     * Whether or not this submachine call can be on an overridden machine.
     *
     * @return
     */
    public boolean callExplicitly();
    
    public boolean preserveForwardCorrelate();

}
