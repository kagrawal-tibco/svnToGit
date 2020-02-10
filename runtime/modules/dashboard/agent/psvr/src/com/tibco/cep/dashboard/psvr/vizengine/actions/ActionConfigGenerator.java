package com.tibco.cep.dashboard.psvr.vizengine.actions;

import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.EngineHandler;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

/**
 * @author apatil
 *
 * The <code>ActionConfigGenerator</code> provides the base for any action
 * config generation. This class and all its sub classes and the
 * <code>ActionConfigGeneratorFactory</code> should not be the core part of
 * the engine. These are basically peripheral services. We need to change the
 * design to allow peripheral services to be hooked up into the engine. We need
 * to allow out of box integration of peripheral services. We need to make the
 * peripheral services complete their task in a stipulated time. We cannot let
 * the peripheral services to compromise the speed and performance of the core
 * engine. If a peripheral service takes more then the stipulated time, we could
 * either log a warning message OR drop the execution of the peripheral service.
 */
public abstract class ActionConfigGenerator extends EngineHandler {

	public static final String SESSION_ID = "session.id";

    public static final String CURRENTPAGE_ID_DYN_PARAM = "currentpage.id";

    public static final String CURRENTPANEL_ID_DYN_PARAM = "currentpanel.id";

    public static final String CURRENTCOMPONENT_ID_DYN_PARAM = "currentcomponent.id";

    //Anand - Added on 11/23/06 bug # 6521
    public static final String CURRENTSERIES_ID_DYN_PARAM = "currentdatacolumn.seriesid";

    public static final String CURRENTCOMPONENT_TITLE_DYN_PARAM = "currentcomponent.title";

    //Anand - Added on 11/23/06 bug # 6521
    public static final String CURRENTDATAROW_ID_DYN_PARAM = "currentdatarow.id";

    public static final String CURRENTDATAROW_LINK_DYN_PARAM = "currentdatarow.link";

    public static final String CURRENTDATACOLUMN_ID_DYN_PARAM = "currentdatacolumn.id";

    public static final String CURRENTDATACOLUMN_LINK_DYN_PARAM = "currentdatacolumn.link";

    public static final String CURRENTDATACOLUMN_VALUE_DYN_PARAM = "currentdatacolumn.value";

    public static final String CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_DYN_PARAM = "currentdatacolumn.typespec.hrefprms";

    public abstract List<ActionConfigType> generateActionConfigs(MALElement element, Map<String,String> dynParamSubMap, PresentationContext pCtx) throws VisualizationException;

}
