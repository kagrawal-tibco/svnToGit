package com.tibco.cep.designtime.model.process;

/*
* Author: Suresh Subramani / Date: 12/11/11 / Time: 12:52 PM
*/
public interface TransitionModel extends BaseModelType {

    <T> T cast(Class<T> typeOf);

    TaskModel from();

    TaskModel to();

    String condition();
}
