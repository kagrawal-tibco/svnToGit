package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown.nextgen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataConvertor;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataNodeMerger;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataProvider;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownRequest;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownResponse;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataNode;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.DrillDownModel;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class GetDrillDownDataAction extends BaseDrillDownAction {

	private DrillDownDataProvider drillDownDataProvider;

	private DrillDownDataConvertor drillDownDataConvertor;

	private DrillDownDataNodeMerger drillDownDataNodeMerger;

	private ActionConfigType enabledExportAction;

	private ActionConfigType disabledExportAction;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		drillDownDataProvider = new DrillDownDataProvider(logger, properties, exceptionHandler, messageGenerator);
		drillDownDataConvertor = new DrillDownDataConvertor(logger, properties, exceptionHandler, messageGenerator);
		drillDownDataNodeMerger = new DrillDownDataNodeMerger(logger, properties, exceptionHandler, messageGenerator);
		enabledExportAction = ActionConfigUtils.createActionConfig("Export", CommandType.SHOWDIALOG, false, new String[][] { new String[] { "dialogname", "drilldownexport" } }, null, null, null);
		disabledExportAction = ActionConfigUtils.createActionConfig("Export", CommandType.SHOWDIALOG, true, null, null, null, null);
	}

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			//create drill down request
			DrillDownRequest drillDownRequest = getRequest(token, request);
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "Processing "+command+" using "+drillDownRequest);
			}
			//get data associated with the request
			DrillDownResponse drillDownResponse = drillDownDataProvider.executeQuery(drillDownRequest);
			//convert data for session storage
			Map<String, List<DrillDownDataNode>> dataNodeMap = drillDownDataConvertor.convertDataToNodes(drillDownRequest, drillDownResponse);
			//create the drill down data tree
			DrillDownDataTree drillDownDataTree = (DrillDownDataTree) request.getSession().getAttribute(DrillDownDataTree.class.getName());
			if (drillDownRequest.getPath() == null) {
				if (drillDownDataTree == null) {
					drillDownDataTree = new DrillDownDataTree();
					request.getSession().setAttribute(DrillDownDataTree.class.getName(), drillDownDataTree);
				}
				else {
					drillDownDataTree.clear();
				}
			}
			//merge the data with existing dataset in the session
			drillDownDataNodeMerger.mergeData(drillDownRequest, drillDownDataTree, dataNodeMap);
			//convert data for outside world consumption
			DrillDownModel drillDownModel = drillDownDataConvertor.convertDataToXML(drillDownRequest, drillDownResponse);
			//add action config if we are dealing with root node
			if (drillDownRequest.getPath() == null) {
				//select which action to use
				ActionConfigType exportAction = disabledExportAction;
				for (TextModel textModel : drillDownModel.getTextModel()) {
					if (textModel.getVisualizationData() != null && textModel.getVisualizationData().getDataRowCount() > 0){
						exportAction = enabledExportAction;
					}
				}
				drillDownModel.setActionConfig(ActionConfigUtils.createActionConfigSet("ROOT", true, Arrays.asList(exportAction)));
			}
			//marshal the text model
			String xml = OGLMarshaller.getInstance().marshall(token, drillDownModel);
			String message = null;
			if (drillDownResponse.isPageMaxCountReached() == true) {
				message = messageGenerator.getMessage("drilldown.page.max.count.reached", getMessageGeneratorArgs(token));
			}
			else {
				for (String key : drillDownResponse.getKeys()) {
					if (drillDownResponse.isTableMaxCountReached(key) == true) {
						if (message == null) {
							message = messageGenerator.getMessage("drilldown.single.table.max.count.reached", getMessageGeneratorArgs(token));
						}
						else {
							message = messageGenerator.getMessage("drilldown.many.table.max.count.reached", getMessageGeneratorArgs(token));
							break;
						}
					}
				}
			}
			return handleSuccess(message, xml);
		} catch (QueryException e) {
			return handleError(getMessage("drilldown.query.execution.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (OGLException e) {
			return handleError(getMessage("drilldown.xml.marshalling.failure", getMessageGeneratorArgs(token, e)), e);
		}
	}

}
