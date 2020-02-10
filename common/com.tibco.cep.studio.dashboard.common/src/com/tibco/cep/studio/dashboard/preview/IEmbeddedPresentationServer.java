package com.tibco.cep.studio.dashboard.preview;

import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;

public interface IEmbeddedPresentationServer {
	
	public void setSkin(Skin skin) throws Exception;
	
	public void start() throws Exception;
	
	public String getComponentConfigWithData(Component component, SeriesDataSet[] data) throws Exception;
	
	public void shutdown();

}
