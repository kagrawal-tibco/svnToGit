package com.tibco.cep.designtime.model.process;



/*
* Author: Suresh Subramani / Date: 12/11/11 / Time: 12:52 PM
*/
public interface TaskModel extends BaseModelType {

    TransitionModel[] getOutgoingTransitions();

    TransitionModel[] getIncomingTransitions();

    String getInputMapper();

    String getOutputMapper();

    <T> T cast(Class<T> typeOf);
}
