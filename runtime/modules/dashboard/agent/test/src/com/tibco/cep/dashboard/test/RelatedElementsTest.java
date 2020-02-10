package com.tibco.cep.dashboard.test;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.DrillDownModel;
import com.tibco.cep.dashboard.psvr.ogl.model.PageConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.Variable;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntity;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerModel;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.VariableType;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

public class RelatedElementsTest extends QueryManagerTest {

	protected void simulateRelatedViewPageInteractions(String sessionid) throws Exception {
		//get all the entities
		QueryManagerModel model = getQueryManagerModel(sessionid);
		QueryManagerEntity[] entities = model.getQueryManagerEntity();
		for (QueryManagerEntity entity : entities) {
			List<String> links = getRelatedElementsViewLinks(sessionid, entity.getId());
			for (String link : links) {
				String relatedViewPageSessionId = launchRelatedViewPage(link);
				simulateSimpleDrillDown(relatedViewPageSessionId, null);
			}
		}
	}

	protected List<String> getRelatedElementsViewLinks(String sessionid, String entityID) throws Exception {
		List<String> urls = new LinkedList<String>();
		QueryManagerModel selectionModel = new QueryManagerModel();
		selectionModel.setSelectedEntityID(entityID);
		executeQuery(sessionid, selectionModel);
		DrillDownModel data = getAllData(sessionid, null, null, false, null);
		if (data != null && data.getTextModelCount() != 0) {
			TextModel textModel = data.getTextModel(0);
			if (textModel.getVisualizationData() != null && textModel.getVisualizationData().getDataRowCount() > 0) {
				for (DataRow dataRow : textModel.getVisualizationData().getDataRow()) {
					if (dataRow.getTemplateType().equals(RowConfigType.DATA) == true) {
						if (StringUtil.isEmptyOrBlank(dataRow.getLink()) == false) {
							urls.add(dataRow.getLink());
						}
					}
				}
			}
		}
		return urls;
	}

	protected String launchRelatedViewPage(String relatedViewLink) throws Exception {
		URL url = new URL("http://localhost" + relatedViewLink);
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
		return null;
	}

	public static void main(String[] args) {
		RelatedElementsTest test = new RelatedElementsTest();
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
						test.simulateRelatedViewPageInteractions(sessionid);
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
