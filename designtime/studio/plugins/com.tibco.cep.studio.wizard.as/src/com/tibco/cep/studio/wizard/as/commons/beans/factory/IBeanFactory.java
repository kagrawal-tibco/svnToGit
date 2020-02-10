package com.tibco.cep.studio.wizard.as.commons.beans.factory;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.exception.CanNotCreateBeanException;

public interface IBeanFactory
{

	Object create() throws CanNotCreateBeanException;

	Object create(Object[] args) throws CanNotCreateBeanException;
}
