package com.tibco.cep.runtime.model.element;

import com.tibco.cep.kernel.model.entity.StateMachineElement;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineCompositeState;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 19, 2006
 * Time: 9:29:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateMachineConcept extends ContainedConcept, StateMachineElement {
    /**
     *
     * @return true if the machine is in progress, false if closed
     */
    public boolean isMachineStarted();


    /**
     *
     * @return
     */
    public boolean isMachineClosed();

    /**
     *
     * @return
     */
    public PropertyStateMachineCompositeState getMachineRoot();

    boolean isActive(PropertyStateMachineState state);

    boolean isReady(PropertyStateMachineState state);

    boolean isTimeoutSet(PropertyStateMachineState state);

    boolean isComplete(PropertyStateMachineState state);

    boolean isExited(PropertyStateMachineState state);

    boolean isCompleteOrExited(PropertyStateMachineState state);

    boolean isAmbiguous(PropertyStateMachineState state);

    void serialize(StateMachineSerializer serializer);

    void deserialize(StateMachineDeserializer deserializer);
}
