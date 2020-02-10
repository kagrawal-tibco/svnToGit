package com.tibco.cep.dashboard.test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ChartTypeConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.Parameter;
import com.tibco.cep.dashboard.psvr.ogl.model.SeriesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

public class ExternalLinkTest extends BasicReadTest {

	//PORT add support for table component later
	private List<Map<String, String>> getExternalLinkInfo(String componentType, VisualizationModel visualizationModel, DataRow dataRow) {
		List<Map<String, String>> links = new LinkedList<Map<String,String>>();
		if (componentType.equals("ChartComponent") == true){
			ChartModel chartModel = (ChartModel) visualizationModel;
			for (ChartTypeConfig chartTypeConfig : chartModel.getChartConfig().getChartTypeConfig()) {
				for (SeriesConfig seriesConfig : chartTypeConfig.getSeriesConfig()) {
					if (dataRow.getTemplateID().equals(seriesConfig.getName()) == true){
						if (seriesConfig.getActionConfig() != null && seriesConfig.getActionConfig().getActionConfigCount() > 0){
							for (ActionConfigType actionConfig : seriesConfig.getActionConfig().getActionConfig()) {
								if (actionConfig.getActionConfigCount() > 0){
									for (ActionConfigType externalLinkConfig:actionConfig.getActionConfig()) {
										if (externalLinkConfig.getCommand().equals(CommandType.LAUNCHINTERNALLINK) == true) {
											for (DataColumn dataColumn : dataRow.getDataColumn()) {
												Map<String, String> linkParams = new HashMap<String, String>();
												for (Parameter parameter : externalLinkConfig.getParam()) {
													linkParams.put(parameter.getName(), parameter.getContent());
												}
												for (TypeSpecificAttribute typeSpecificAttribute : dataColumn.getTypeSpecificAttribute()) {
													if (typeSpecificAttribute.getName().equals("hrefprms") == true) {
														String hrefprms = typeSpecificAttribute.getContent();
														if (StringUtil.isEmptyOrBlank(hrefprms) == false) {
															String[] params = hrefprms.split("&");
															for (String param : params) {
																String[] split = param.split("=");
																linkParams.put(split[0], split[1]);
															}
															links.add(linkParams);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return links;
	}

	private String getExternalLink(Map<String, String> parameters) throws IOException {
		BizRequest request = getXMLRequest("launchexternallink", parameters);
		return client.execute(request);
	}

	public static void main(String[] args) {
		ExternalLinkTest test = new ExternalLinkTest();
		try {
			test.setup(args);
			test.start();
			test.loginUsingCommandArgs();
			test.setRoleUsingCommandArgs();
			PagesConfig pagesConfig = test.getLayout(null);
			test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, pagesConfig));
			Collection<ComponentDefinition> compcfgs = test.parsePagesConfig(pagesConfig).values();
			for (ComponentDefinition componentConfig : compcfgs) {
				test.logger.log(Level.INFO,"Getting component config for "+componentConfig.getName()+","+componentConfig.getTitle());
				VisualizationModel visualizationModel = test.getComponentConfig(componentConfig.getId(),componentConfig.getType(),null);
				test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationModel));
				test.logger.log(Level.INFO,"Getting component data for "+componentConfig.getName()+","+componentConfig.getTitle());
				VisualizationData visualizationData = test.getComponentData(componentConfig.getId(),componentConfig.getType(),null);
				test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationData));
				for (DataRow dataRow : visualizationData.getDataRow()) {
					List<Map<String, String>> linkParams = test.getExternalLinkInfo(componentConfig.getType(),visualizationModel,dataRow);
					for (Map<String,String> map : linkParams) {
						test.logger.log(Level.INFO,map+" link is "+test.getExternalLink(map));
					}
				}
			}
		} catch (IllegalArgumentException ex){
			System.err.println(ex.getMessage());
			System.err.println("Usage : java "+BasicReadTest.class.getName()+" [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null){
				test.shutdown();
			}
		}
	}





}
