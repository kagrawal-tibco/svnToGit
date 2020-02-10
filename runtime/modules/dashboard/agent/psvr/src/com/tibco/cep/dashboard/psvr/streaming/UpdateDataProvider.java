package com.tibco.cep.dashboard.psvr.streaming;

import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

/**
 * @author apatil
 *
 */
//TODO Better Name
public interface UpdateDataProvider {
	
    public List<Tuple> getData(PresentationContext ctx) throws VisualizationException;
    
}
