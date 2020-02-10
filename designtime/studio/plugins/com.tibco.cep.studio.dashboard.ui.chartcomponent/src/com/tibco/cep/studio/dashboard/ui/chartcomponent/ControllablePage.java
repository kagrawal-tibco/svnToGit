package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

public interface ControllablePage {
	
	public void setExceptionHandler(ExceptionHandler exceptionHandler);

	public void disable();
	
	public void enable();
	
	public void refresh() throws Exception;
	
	public void refreshPreview(LocalElement localElement);
}
