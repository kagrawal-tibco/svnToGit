package com.tibco.cep.dashboard.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.Parameter;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.FilterDimension;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.FilterEditorModel;
import com.tibco.cep.dashboard.psvr.ogl.model.types.BooleanOptions;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

public class FilterEditorTest extends BasicReadTest {

	private Random random;

	protected void setup(String[] args) {
		super.setup(args);
		random = new Random();
	}

	protected FilterEditorModel getFilterEditorModel(String componentid, String seriesid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("compid", componentid);
		map.put("seriescfgid", seriesid);
		BizRequest requestXML = getXMLRequest("getfeditormodel", map);
		String modelXML = client.execute(requestXML);
		return (FilterEditorModel) OGLUnmarshaller.getInstance().unmarshall(FilterEditorModel.class, modelXML);
	}

	protected void updateFilterEditorModel(String componentid, String seriesid, FilterEditorModel model) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("compid", componentid);
		map.put("seriescfgid", seriesid);
		map.put("filtermodel", OGLMarshaller.getInstance().marshall(null, model));
		BizRequest requestXML = getXMLRequest("updatefeditormodel", map);
		client.execute(requestXML);
		return;
	}

	protected List<ActionConfigType> searchActionConfigType(ActionConfigType[] actionConfigs, CommandType command, String configParamName, String configParamValue) {
		List<ActionConfigType> searchHits = new LinkedList<ActionConfigType>();
		for (ActionConfigType actionConfig : searchHits) {
			if (command.equals(actionConfig.getCommand()) == true) {
				for (Parameter configParam : actionConfig.getConfigParam()) {
					if (configParam.getName().equals(configParamName) == true) {
						if (configParam.getContent().equals(configParamValue) == true) {
							searchHits.add(actionConfig);
						}
					}
				}
			}
			searchHits.addAll(searchActionConfigType(actionConfig.getActionConfig(), command, configParamName, configParamValue));
		}
		return searchHits;
	}

	protected void modifyFilterEditorModel(FilterEditorModel model) {
		FilterDimension dimension = null;
		if (model.getFilterDimensionCount() == 1){
			dimension = model.getFilterDimension(0);
		}
		else {
			dimension = model.getFilterDimension(random.nextInt(model.getFilterDimensionCount()));
		}
		String value = dimension.getDimensionDataSet().getValue()[random.nextInt(dimension.getDimensionDataSet().getValueCount())];
		value = value.substring(1, value.length()-1);
//		dimension.setValue(value);
		dimension.setValue("Anand"+value);
		dimension.setDimensionDataSet(null);
	}


	public static void main(String[] args) {
		FilterEditorTest test = new FilterEditorTest();
		try {
			test.setup(args);
			test.start();
			test.loginUsingCommandArgs();
			test.setRoleUsingCommandArgs();
			PagesConfig pagesConfig = test.getLayout(null);
			test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, pagesConfig));
			Collection<ComponentDefinition> compcfgs = test.parsePagesConfig(pagesConfig).values();
			for (ComponentDefinition componentConfig : compcfgs) {
				test.logger.log(Level.INFO, "Getting component config for " + componentConfig.getName() + "," + componentConfig.getTitle());
				VisualizationModel visualizationModel = test.getComponentConfig(componentConfig.getId(), componentConfig.getType(), null);
				test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, visualizationModel));
				List<ActionConfigType> searchHits = test.searchActionConfigType(visualizationModel.getActionConfig(), CommandType.SHOWDIALOG, "dialogname", "filtereditor");
				for (ActionConfigType searchHit : searchHits) {
					if (searchHit.getDisabled().equals(BooleanOptions.FALSE) == true) {
						String seriesid = null;
						for (Parameter parameter : searchHit.getParam()) {
							if (parameter.getName().equals("seriesid") == true){
								seriesid = parameter.getContent();
							}
						}
						for (int i = 0; i < 2; i++) {
							test.logger.log(Level.INFO, "Getting filter editor model for " + componentConfig.getName() + "," + componentConfig.getTitle()+"/"+seriesid);
							FilterEditorModel filterEditorModel = test.getFilterEditorModel(componentConfig.getId(), seriesid);
							test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, filterEditorModel));
							test.modifyFilterEditorModel(filterEditorModel);
							test.updateFilterEditorModel(componentConfig.getId(), seriesid, filterEditorModel);
						}
						return;
					}
				}
			}
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + FilterEditorModel.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null) {
				test.shutdown();
			}
		}
	}

}
