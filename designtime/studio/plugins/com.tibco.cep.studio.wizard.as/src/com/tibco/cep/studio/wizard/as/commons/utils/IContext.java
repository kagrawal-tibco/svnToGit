package com.tibco.cep.studio.wizard.as.commons.utils;

public interface IContext {

	void bind(String key, Object value);

	Object unbind(String key);

	Object get(String key);

}
