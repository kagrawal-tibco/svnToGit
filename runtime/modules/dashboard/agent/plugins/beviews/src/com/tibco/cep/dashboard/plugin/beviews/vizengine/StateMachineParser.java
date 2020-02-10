package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;

import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessConfig;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.designtime.core.model.states.StateMachine;


public interface StateMachineParser {
	
	public abstract void process(StateMachine stateMachine, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException;
	public abstract ProcessConfig getRootProcessConfig();
	
}
