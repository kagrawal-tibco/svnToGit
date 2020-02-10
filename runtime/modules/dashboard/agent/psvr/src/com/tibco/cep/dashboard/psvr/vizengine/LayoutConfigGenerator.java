package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.mal.model.MALLayout;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayoutConstraint;
import com.tibco.cep.dashboard.psvr.ogl.model.LayoutConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.LayoutConstraints;

/**
 * @author anpatil
 *
 */
public abstract class LayoutConfigGenerator extends EngineHandler {
   
   public abstract LayoutConfig generate(MALLayout layout);

   public abstract LayoutConstraints getLayoutConstraints(MALLayoutConstraint malLayoutConstraint);
}