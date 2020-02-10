package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public interface TypeProcessor {
	
	public boolean isAcceptable(LocalElement localElement) throws Exception;
	
	public String[] getSubTypes(LocalElement localElement) throws Exception;
	
	public void subTypeChanged(LocalElement localElement, ChartSubType oldSubType, ChartSubType newSubType) throws Exception;
	
	public void prepareForEditing(LocalElement localElement) throws Exception;
	
	public void seriesAdded(LocalElement localElement, LocalElement series) throws Exception;
	
	public void seriesRemoved(LocalElement localElement, LocalElement series) throws Exception;
	
	public LocalElement createNativeElement(LocalElement localElement) throws Exception;
	
}
