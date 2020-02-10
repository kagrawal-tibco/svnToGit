package com.tibco.cep.dashboard.test;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntity;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntityField;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntityFieldCondition;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerModel;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DataType;
import com.tibco.cep.kernel.service.logging.Level;

public class QueryManagerTest extends DrillDownTest {

	protected QueryManagerModel getQueryManagerModel(String sessionid) throws Exception{
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		BizRequest requestXML = getXMLRequest("getquerymgrmodel", parameters);
		logger.log(Level.INFO, "Getting query manager model...");
		String modelXML = client.execute(requestXML);
		logger.log(Level.INFO, modelXML);
		return (QueryManagerModel) OGLUnmarshaller.getInstance().unmarshall(QueryManagerModel.class, modelXML);
	}

	protected QueryManagerModel getEntityFields(String sessionid, String typeid) throws Exception{
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		parameters.put("typeid", typeid);
		BizRequest requestXML = getXMLRequest("getentityfields", parameters);
		logger.log(Level.INFO, "Getting entity fields for "+typeid);
		String modelXML = client.execute(requestXML);
		logger.log(Level.INFO, modelXML);
		return (QueryManagerModel) OGLUnmarshaller.getInstance().unmarshall(QueryManagerModel.class, modelXML);
	}

	protected void executeQuery(String sessionid, QueryManagerModel queryMgrModel) throws Exception{
		String queryMgrModelXML = OGLMarshaller.getInstance().marshall(null, queryMgrModel);
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		parameters.put("querymgrmodel", queryMgrModelXML);
		BizRequest requestXML = getXMLRequest("createquery", parameters);
		logger.log(Level.INFO, "Executing query "+queryMgrModelXML);
		String response = client.execute(requestXML);
		logger.log(Level.INFO, response);
	}

	protected void exportQueryData(String sessionid, QueryManagerModel queryMgrModel) throws Exception{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionid", sessionid);
		//simulate the dialog opening
		BizRequest requestXML = getXMLRequest("getquerymgrexportoptionsdefaults", parameters);
		String optionsXML = client.execute(requestXML);
		logger.log(Level.INFO, "Query Export Dialog Options are "+optionsXML);
		String queryMgrModelXML = OGLMarshaller.getInstance().marshall(null, queryMgrModel);
		parameters.put("querymgrmodel", queryMgrModelXML);
		parameters.put("type", "csv");
		parameters.put("includesystemfields", "false");
		//parameters.put("depth", "0");
		parameters.put("pertypecount", "3");
		parameters.put("overallcount", "5");
		requestXML = getXMLRequest("exportquerydata", parameters);
		String exportResultXML = client.execute(requestXML);
		logger.log(Level.INFO, "Query Export Data Is "+exportResultXML);
	}

	@Override
	protected void testSearchPage(String sessionid) throws Exception {
		//get query manager model
		getQueryManagerModel(sessionid);
		super.testSearchPage(sessionid);
	}

	protected void simulateQueryManagerInteractions(String sessionid) throws Exception {
		//get query manager model
		QueryManagerModel queryManagerModel = getQueryManagerModel(sessionid);
		for (QueryManagerEntity qmEntity : queryManagerModel.getQueryManagerEntity()) {
			String typeId = qmEntity.getId();
			// fire command "getentityfields" and get QueryManagerModel XML with selected typeId
			QueryManagerModel qModel = getEntityFields(sessionid, typeId);
			QueryManagerEntity selectedEntity = qModel.getQueryManagerEntity()[0];
			selectedEntity.clearQueryManagerEntityField();
			for (QueryManagerEntityField qmEntityField : selectedEntity.getQueryManagerEntityField()) {
				if (qmEntityField.getCondition() == null) {
					// TODO update the condition(s): set new condition...
					QueryManagerEntityFieldCondition defaultCondition = qmEntityField.getDefaultCondition();
					if (defaultCondition == null) {
						throw new Exception("No default condition for "+qmEntity.getName()+"/"+qmEntityField.getName());
					}
					if (defaultCondition.getValueCount() == 0){
						switch (qmEntityField.getDataType().getType()) {
							case DataType.BOOLEAN_TYPE :
								defaultCondition.addValue(String.valueOf(new Random().nextBoolean()));
								break;
							case DataType.DATETIME_TYPE :
								defaultCondition.addValue(String.valueOf(new Date().getTime()));
								break;
							case DataType.DOUBLE_TYPE :
								defaultCondition.addValue(String.valueOf(new Random().nextDouble()));
								break;
							case DataType.FLOAT_TYPE :
								defaultCondition.addValue(String.valueOf(new Random().nextFloat()));
								break;
							case DataType.INT_TYPE :
								defaultCondition.addValue(String.valueOf(new Random().nextInt()));
								break;
							case DataType.LONG_TYPE :
								defaultCondition.addValue(String.valueOf(new Random().nextLong()));
								break;
							case DataType.SHORT_TYPE :
								defaultCondition.addValue(String.valueOf(new Random().nextInt()));
								break;
							case DataType.STRING_TYPE :
								defaultCondition.addValue("test");
								break;
						}
					}
					qmEntityField.setDefaultCondition(null);
					qmEntityField.setCondition(defaultCondition);
				}
			}
			executeQuery(sessionid, qModel);
			threadLocalIndentator.set(new Indentor());
			simulateSimpleDrillDown(sessionid, null);
			exportQueryData(sessionid, qModel);
		}
	}

	public static void main(String[] args) {
		QueryManagerTest test = new QueryManagerTest();
		try{
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
						test.simulateQueryManagerInteractions(sessionid);
						return;
					}
				}
			}
		}catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + QueryManagerTest.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null) {
				test.shutdown();
			}
		}
	}
}
