package com.tibco.cep.dashboard.test;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.DrillDownModel;
import com.tibco.cep.dashboard.psvr.ogl.model.PageConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.Variable;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.DialogOption;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.DialogOptions;
import com.tibco.cep.dashboard.psvr.ogl.model.types.VariableType;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

//PATCH fix for the multi group by scenario
public class DrillDownTest extends BasicReadTest {

	protected static ThreadLocal<Indentor> threadLocalIndentator = new ThreadLocal<Indentor>();

	protected String launchSearchPage() throws IOException, OGLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("pagetype", MALSearchPageManager.DEFINITION_TYPE);
		BizRequest requestXML = getXMLRequest("getlayout", parameters);
		String layoutXML = client.execute(requestXML);
		logger.log(Level.INFO, layoutXML);
		PagesConfig pagesConfig = (PagesConfig) OGLUnmarshaller.getInstance().unmarshall(PagesConfig.class, layoutXML);
		PageConfig pageConfig = pagesConfig.getPageConfig(0);
		for (Variable variable : pageConfig.getVariable()) {
			if ((variable.getType().equals(VariableType.HIDDEN) == true) && variable.getName().equals("sessionid") == true) {
				return variable.getContent();
			}
		}
		return null;
	}

	protected void testSearchPage(String sessionid) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		BizRequest requestXML = getXMLRequest("getdata", parameters);
		String dataXML = client.execute(requestXML);
		logger.log(Level.INFO, "Search Page drilldown data is "+dataXML);
	}

	protected List<String> getDrillDownLinks(VisualizationModel model, VisualizationData data) {
		List<String> links = new LinkedList<String>();
		for (DataRow dataRow : data.getDataRow()) {
			for (DataColumn dataCol : dataRow.getDataColumn()) {
				String link = dataCol.getLink();
				if (StringUtil.isEmptyOrBlank(link) == false) {
					links.add(link);
				}
			}
		}
		return links;
	}

	protected String launchDrillDownLink(String drilldownLink) throws OGLException, IOException {
		URL url = new URL("http://localhost" + drilldownLink);
		String query = url.getQuery();
		String[] params = query.split("&");
		Map<String, String> parameters = new HashMap<String, String>();
		for (String param : params) {
			String[] keyValuePair = param.split("=");
			parameters.put(keyValuePair[0], keyValuePair[1]);
		}
		BizRequest requestXML = getXMLRequest("getlayout", parameters);
		String layoutXML = client.execute(requestXML);
		logger.log(Level.INFO, layoutXML);
		PagesConfig pagesConfig = (PagesConfig) OGLUnmarshaller.getInstance().unmarshall(PagesConfig.class, layoutXML);
		PageConfig pageConfig = pagesConfig.getPageConfig(0);
		for (Variable variable : pageConfig.getVariable()) {
			if ((variable.getType().equals(VariableType.HIDDEN) == true) && variable.getName().equals("sessionid") == true) {
				return variable.getContent();
			}
		}
		//
		return null;
	}

	protected DrillDownModel getData(String sessionid, String path, String sortByField, boolean ascending, String groupByField, int index) throws IOException, OGLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		if (path != null) {
			parameters.put("path", path);
		}
		if (sortByField != null) {
			parameters.put("sortfield", sortByField);
			parameters.put("sortfielddirection", ascending == true ? "asc" : "desc");
		}
		if (groupByField != null) {
			parameters.put("groupbyfield", groupByField);
		}
		parameters.put("startindex", String.valueOf(index));
		BizRequest requestXML = getXMLRequest("getdata", parameters);
		String dataXML = client.execute(requestXML);
		StringBuilder sb = new StringBuilder();
		sb.append("Getting drilldown data under [" + path);
		if (sortByField != null) {
			sb.append(";" + sortByField + "@" + ascending);
		}
		if (groupByField != null) {
			sb.append(";" + groupByField);
		}
		sb.append(",");
		sb.append(index);
		sb.append("]");
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + sb.toString() + "=" + dataXML);
		return (DrillDownModel) OGLUnmarshaller.getInstance().unmarshall(DrillDownModel.class, dataXML);
	}

	protected DrillDownModel getAllData(String sessionid, String path, String sortByField, boolean ascending, String groupByField) throws IOException, OGLException {
		DrillDownModel dataSet = getData(sessionid, path, sortByField, ascending, groupByField, -1);
		if (dataSet == null || dataSet.getTextModelCount() == 0 || dataSet.getTextModel(0).getVisualizationData() == null) {
			return dataSet;
		}
		TextModel textModel = dataSet.getTextModel(0);
		while (textModel.getTextConfig().getMaxRows() > textModel.getVisualizationData().getDataRowCount()) {
			DrillDownModel nextDataSet = getData(sessionid, path, sortByField, ascending, groupByField, textModel.getVisualizationData().getDataRowCount());
			// merge the nextDataSet with dataSet
			if (nextDataSet != null && nextDataSet.getTextModelCount() == 1 && nextDataSet.getTextModel(0).getVisualizationData() != null) {
				for (DataRow row : nextDataSet.getTextModel(0).getVisualizationData().getDataRow()) {
					textModel.getVisualizationData().addDataRow(row);
				}
			}
		}
		return dataSet;
	}

	protected void removeData(String sessionid, String path) throws IOException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		if (path != null) {
			parameters.put("path", path);
		}
		BizRequest requestXML = getXMLRequest("removedata", parameters);
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Removing drilldown data under " + path);
		client.execute(requestXML);
	}

	protected void simulateSimpleDrillDown(String sessionid, String path) throws IOException, OGLException {
		DrillDownModel data = getAllData(sessionid, path, null, false, null);
		if (data != null && data.getTextModelCount() == 1) {
			TextModel textModel = data.getTextModel(0);
			if (textModel.getVisualizationData() != null && textModel.getVisualizationData().getDataRowCount() > 0) {
				if (path == null) {
					path = textModel.getTextConfig().getComponentID();
	//				path = "";
				}
				// we have some data coming in
				DataRow[] dataRows = textModel.getVisualizationData().getDataRow();
				for (DataRow dataRow : dataRows) {
					if (dataRow.getVisualType().equalsIgnoreCase("typedatarow") == true) {
						threadLocalIndentator.get().indent();
						simulateSimpleDrillDown(sessionid, path + "/" + dataRow.getId());
						threadLocalIndentator.get().undent();
					}
					if (dataRow.getVisualType().equalsIgnoreCase("typerow") == true) {
	//					for (DataColumn dataCol : dataRow.getDataColumn()) {
							threadLocalIndentator.get().indent();
							simulateSimpleDrillDown(sessionid, path + "/" + dataRow.getId());
							threadLocalIndentator.get().undent();
	//					}
					}
				}
			}
		}
		simulateExport(sessionid);
		// once we have finished with the current path, we will collapse it
		removeData(sessionid, path);
	}

	protected void simulateDrillDownWithSort(String sessionid, String path, String sortField, boolean ascending) throws IOException, OGLException {
		DrillDownModel data = getAllData(sessionid, path, sortField, ascending, null);
		if (data != null && data.getTextModelCount() == 1) {
			TextModel textModel = data.getTextModel(0);
			if (textModel.getVisualizationData() != null && textModel.getVisualizationData().getDataRowCount() > 0) {
				if (path == null) {
					path = textModel.getTextConfig().getComponentID();
	//				path = "";
				}
				// we have some data coming in
				DataRow[] dataRows = textModel.getVisualizationData().getDataRow();
				DataRow headerRow = null;
				for (DataRow dataRow : dataRows) {
					if (dataRow.getVisualType().equalsIgnoreCase("typetableheaderrow") == true) {
						headerRow = dataRow;
	//				} else if (dataRow.getVisualType().equalsIgnoreCase("typedatarow") == true) {
	//					threadLocalIndentator.get().indent();
	//					simulateDrillDownWithSort(sessionid, path + "/" + dataRow.getId(), null, true);
	//					threadLocalIndentator.get().undent();
	//				} else if (dataRow.getVisualType().equalsIgnoreCase("typerow") == true) {
	//					for (DataColumn dataCol : dataRow.getDataColumn()) {
	//						threadLocalIndentator.get().indent();
	//						simulateDrillDownWithSort(sessionid, path + "/" + dataRow.getId(), null, true);
	//						threadLocalIndentator.get().undent();
	//					}
	//				}
					}else {
						threadLocalIndentator.get().indent();
						simulateDrillDownWithSort(sessionid, path + "/" + dataRow.getId(), null, true);
						threadLocalIndentator.get().undent();
					}
				}
				// now apply sorting
				if (sortField == null && headerRow != null) {
					for (DataColumn dataCol : headerRow.getDataColumn()) {
						logger.log(Level.INFO, threadLocalIndentator.get().toString()+"Applying "+dataCol.getDisplayValue()+"/true as sort on "+path);
						simulateDrillDownWithSort(sessionid, path, dataCol.getDisplayValue(), true);
						logger.log(Level.INFO, threadLocalIndentator.get().toString()+"Applying "+dataCol.getDisplayValue()+"/false as sort on "+path);
						simulateDrillDownWithSort(sessionid, path, dataCol.getDisplayValue(), false);
					}
				}
			}
		}
		simulateExport(sessionid);
		// once we have finished with the current path, we will collapse it
		removeData(sessionid, path);
	}

	protected void simulateDrillDownWithGroupBy(String sessionid, String path, String groupByField) throws IOException, OGLException {
		DrillDownModel data = getAllData(sessionid, path, null, false, groupByField);
		if (data != null && data.getTextModelCount() == 1) {
			TextModel textModel = data.getTextModel(0);
			if (textModel.getVisualizationData() != null && textModel.getVisualizationData().getDataRowCount() > 0) {
				if (path == null) {
					path = textModel.getTextConfig().getComponentID();
	//				path = "";
				}
				// we have some data coming in
				DataRow[] dataRows = textModel.getVisualizationData().getDataRow();
				Map<String,ActionConfigType> indexedActionConfigs = new HashMap<String, ActionConfigType>();
				for (ActionConfigType actionConfig : textModel.getActionConfig()) {
					String qualifier = actionConfig.getQualifier();
					if (StringUtil.isEmptyOrBlank(qualifier) == true) {
						qualifier = "*";
					}
					indexedActionConfigs.put(qualifier, actionConfig);
				}
				for (DataRow dataRow : dataRows) {
					if (dataRow.getVisualType().equalsIgnoreCase("typedatarow") == true) {
						threadLocalIndentator.get().indent();
						simulateDrillDownWithGroupBy(sessionid, path + "/" + dataRow.getId(), null);
						threadLocalIndentator.get().undent();
					} else if (dataRow.getVisualType().equalsIgnoreCase("typerow") == true) {
	//					for (DataColumn dataCol : dataRow.getDataColumn()) {
							String childPath = path + "/" + dataRow.getId();
							//do normal drilldown
							threadLocalIndentator.get().indent();
							simulateDrillDownWithGroupBy(sessionid, childPath, null);
							//do group by
							ActionConfigType groupByActionConfig = indexedActionConfigs.get(dataRow.getId());
							if (groupByActionConfig == null) {
								groupByActionConfig = indexedActionConfigs.get("*");
							}
							if (groupByActionConfig != null) {
								if (groupByActionConfig.getActionConfigCount() > 0) {
									for (ActionConfigType actionConfig : groupByActionConfig.getActionConfig()) {
										if (actionConfig.getText().equalsIgnoreCase("groupby") == true) {
											for (ActionConfigType groupByFieldAction : actionConfig.getActionConfig()) {
												logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Applying " + groupByFieldAction.getText() + " as group by on " + childPath);
												simulateDrillDownWithGroupBy(sessionid, childPath, groupByFieldAction.getText());
											}
										}
									}
								}
							}
							threadLocalIndentator.get().undent();
	//					}
					} else if (dataRow.getVisualType().equalsIgnoreCase("groupbyrow") == true) {
	//					for (DataColumn dataCol : dataRow.getDataColumn()) {
							String childPath = path + "/" + dataRow.getId();
							threadLocalIndentator.get().indent();
							//do normal drilldown
							simulateDrillDownWithGroupBy(sessionid, childPath, null);
							//do group by
							ActionConfigType groupByActionConfig = textModel.getActionConfigCount() > 0 ? textModel.getActionConfig(0) : null;
							if (groupByActionConfig != null) {
								if (groupByActionConfig.getActionConfigCount() > 0) {
									for (ActionConfigType actionConfig : groupByActionConfig.getActionConfig()) {
										if (actionConfig.getText().equalsIgnoreCase("groupby") == true) {
											for (ActionConfigType groupByFieldAction : actionConfig.getActionConfig()) {
												logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Applying " + groupByFieldAction.getText() + " as group by on " + childPath);
												simulateDrillDownWithGroupBy(sessionid, childPath, groupByFieldAction.getText());
											}
										}
									}
								}
							}
							threadLocalIndentator.get().undent();
	//					}
					}
					// now apply sorting
				}
			}
		}
		simulateExport(sessionid);
		// once we have finished with the current path, we will collapse it
		removeData(sessionid, path);
	}

/*	protected void drilldownApplyingSortAndGroupBy(String sessionid, String path, String sortField, boolean ascending, String groupByField, int index) throws IOException, OGLException {
		DrillDownModel data = getData(sessionid, path, sortField, ascending, groupByField, index);
		if (data != null && data.getTextModelCount() == 1) {
			TextModel textModel = data.getTextModel(0);
			if (textModel.getVisualizationData() != null && textModel.getVisualizationData().getDataRowCount() > 0) {
				if (path == null) {
					path = textModel.getTextConfig().getComponentID();
				}
				// we have some data coming in
				DataRow[] dataRows = textModel.getVisualizationData().getDataRow();
				DataRow headerRow = null;
				Map<String,ActionConfigType> indexedActionConfigs = new HashMap<String, ActionConfigType>();
				for (ActionConfigType actionConfig : textModel.getActionConfig()) {
					String qualifier = actionConfig.getQualifier();
					if (StringUtil.isEmptyOrBlank(qualifier) == true) {
						qualifier = "*";
					}
					indexedActionConfigs.put(qualifier, actionConfig);
				}
				for (DataRow dataRow : dataRows) {
					if (dataRow.getVisualType().equalsIgnoreCase("typetableheaderrow") == true) {
						headerRow = dataRow;
					} else if (dataRow.getVisualType().equalsIgnoreCase("typedatarow") == true) {
						// drilldownApplyingSortAndGroupBy(sessionid, path + "/" + dataRow.getId(), null, true, null, -1);
					} else if (dataRow.getVisualType().equalsIgnoreCase("typerow") == true) {
						for (DataColumn dataCol : dataRow.getDataColumn()) {
							// drilldownApplyingSortAndGroupBy(sessionid, path + "/" + dataCol.getId(), null, true, null, -1);
						}
					} else if (dataRow.getVisualType().equalsIgnoreCase("groupbyrow") == true) {
						for (DataColumn dataCol : dataRow.getDataColumn()) {
							drilldownApplyingSortAndGroupBy(sessionid, path + "/" + dataCol.getId(), null, true, null, -1);
						}
					}
				}
				// now apply group by
//				if (groupByField == null && groupByActionConfig != null) {
//					if (groupByActionConfig.getActionConfigCount() > 0) {
//						for (ActionConfigType actionConfig : groupByActionConfig.getActionConfig()) {
//							if (actionConfig.getText().equalsIgnoreCase("groupby") == true) {
//								for (ActionConfigType groupByFieldAction : actionConfig.getActionConfig()) {
//									drilldownApplyingSortAndGroupBy(sessionid, path, sortField, ascending, groupByFieldAction.getText(), -1);
//								}
//							}
//						}
//					}
//				}
				// now apply sorting
				// if (sortField == null && headerRow != null) {
				// for (DataColumn dataCol : headerRow.getDataColumn()) {
				// drilldownApplyingSortAndGroupBy(sessionid, path, dataCol.getDisplayValue(), true, -1);
				// drilldownApplyingSortAndGroupBy(sessionid, path, dataCol.getDisplayValue(), false, -1);
				// }
				// }
			}
		}
		// TODO export API
		// once we have finished with the current path, we will collapse it
		removeData(sessionid, path);
	}*/

	protected void simulateExport(String sessionid) throws IOException, OGLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		//simulate the dialog opening
		BizRequest requestXML = getXMLRequest("getdrilldownexportoptionsdefaults", parameters);
		String optionsXML = client.execute(requestXML);
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Export Dialog Options are "+optionsXML);
		DialogOptions options = (DialogOptions) OGLUnmarshaller.getInstance().unmarshall(DialogOptions.class, optionsXML);

		//simulate the actual export
		for (DialogOption option : options.getDialogOption()) {
			parameters.put(option.getName(), option.getContent());
		}

		requestXML = getXMLRequest("exportdrilldowndata", parameters);
		String exportedData = client.execute(requestXML);
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Export Data Is "+exportedData);

		//switch system fields settings
		parameters.put("includesystemfields", "true".equals(parameters.get("includesystemfields")) ? "false" : "true");

		requestXML = getXMLRequest("exportdrilldowndata", parameters);
		exportedData = client.execute(requestXML);
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Export Data Is "+exportedData);

		//try other type
		parameters.put("type", "csv".equals(parameters.get("type")) ? "xml" : "csv");

		requestXML = getXMLRequest("exportdrilldowndata", parameters);
		exportedData = client.execute(requestXML);
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Export Data Is "+exportedData);

		parameters.put("includesystemfields", "true".equals(parameters.get("includesystemfields")) ? "false" : "true");

		requestXML = getXMLRequest("exportdrilldowndata", parameters);
		exportedData = client.execute(requestXML);
		logger.log(Level.INFO, threadLocalIndentator.get().toString() + "Export Data Is "+exportedData);

	}

	static class Indentor {

		private StringBuilder sb;

		Indentor() {
			sb = new StringBuilder();
		}

		void indent() {
			sb.append(" ");
		}

		void undent() {
			sb.deleteCharAt(sb.length() - 1);
		}

		void reset() {
			sb.setLength(0);
		}

		@Override
		public String toString() {
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		DrillDownTest test = new DrillDownTest();
		try {
			test.setup(args);
			test.start();
			test.loginUsingCommandArgs();
			test.setRoleUsingCommandArgs();
			test.testSearchPage(test.launchSearchPage());
			PagesConfig pagesConfig = test.getLayout(null);
			test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, pagesConfig));
			Collection<ComponentDefinition> compcfgs = test.parsePagesConfig(pagesConfig).values();
			for (ComponentDefinition componentConfig : compcfgs) {
				if (componentConfig.getType().equalsIgnoreCase("chartcomponent") || componentConfig.getType().equalsIgnoreCase("textcomponent")) {
					test.logger.log(Level.INFO, "Getting component config for " + componentConfig.getName() + "," + componentConfig.getTitle());
					VisualizationModel visualizationModel = test.getComponentConfig(componentConfig.getId(), componentConfig.getType(), null);
					test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationModel));
					test.logger.log(Level.INFO, "Getting component data for " + componentConfig.getName() + "," + componentConfig.getTitle());
					VisualizationData visualizationData = test.getComponentData(componentConfig.getId(), componentConfig.getType(), null);
					test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationData));
					List<String> drillDownLinks = test.getDrillDownLinks(visualizationModel, visualizationData);
					for (String drillDownLink : drillDownLinks) {
						String sessionid = test.launchDrillDownLink(drillDownLink);
						threadLocalIndentator.set(new Indentor());
//						test.simulateSimpleDrillDown(sessionid, null);
//						test.simulateDrillDownWithSort(sessionid, null, null, false);
						test.simulateDrillDownWithGroupBy(sessionid, null, null);
						return;
					}
				}
			}
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + BasicReadTest.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null) {
				test.shutdown();
			}
		}
	}
}
