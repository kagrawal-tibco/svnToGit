package com.tibco.cep.dashboard.psvr.editors;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;

public interface EditorTransaction {
	
	public void setViewConfig(MALView viewsConfig) throws EditorException;
	
	public void setPageBeingEdited(MALPage page) throws EditorException;
	
	public void setPanelBeingEdited(MALPanel panel) throws EditorException;
	
	public void setComponentBeingEdited(MALComponent component) throws EditorException;
	
	public void commit() throws EditorException;
	
	public void rollback() throws EditorException;
	
	public MALComponent getComponent();

	public void close() throws EditorException;

}
