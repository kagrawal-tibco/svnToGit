package com.tibco.cep.studio.core.index.resolution;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public interface IResolutionContextProvider {

	public IResolutionContext getResolutionContext(ElementReference elementReference);

	public IResolutionContext getResolutionContext(ElementReference reference, ScopeBlock scope);
	
}
