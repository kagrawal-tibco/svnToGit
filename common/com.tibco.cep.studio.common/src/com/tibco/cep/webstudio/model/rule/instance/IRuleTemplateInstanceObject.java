package com.tibco.cep.webstudio.model.rule.instance;

import java.io.Serializable;

public interface IRuleTemplateInstanceObject extends Serializable {

	void addChangeListener(IInstanceChangedListener listener);

	void removeChangeListener(IInstanceChangedListener listener);
}
