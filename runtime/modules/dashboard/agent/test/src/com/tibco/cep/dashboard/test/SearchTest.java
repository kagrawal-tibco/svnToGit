package com.tibco.cep.dashboard.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentCategory;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

public class SearchTest extends BasicReadTest {

	protected List<ComponentDefinition> search(String criteria) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("criteria", criteria);
		BizRequest request = getXMLRequest("searchcomps", parameters);
		String results = client.execute(request);
		ComponentCategory searchCategory = (ComponentCategory) OGLUnmarshaller.getInstance().unmarshall(ComponentCategory.class, results);
		return Arrays.asList(searchCategory.getComponentDefinition());
	}

	public static void main(String[] args) {
		SearchTest test = new SearchTest();
		try {
			test.setup(args);
			test.start();
			test.loginUsingCommandArgs();
			test.setRoleUsingCommandArgs();
			List<ComponentDefinition> results = test.search("");
			ComponentCategory category = new ComponentCategory();
			category.setComponentDefinition(results.toArray(new ComponentDefinition[results.size()]));
			test.logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, category));
			for (ComponentDefinition componentConfig : results) {
				test.logger.log(Level.INFO,"Getting component config for "+componentConfig.getName()+","+componentConfig.getTitle());
				VisualizationModel visualizationModel = test.getComponentConfig(componentConfig.getId(),componentConfig.getType(),null);
				test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationModel));
				test.logger.log(Level.INFO,"Getting component data for "+componentConfig.getName()+","+componentConfig.getTitle());
				VisualizationData visualizationData = test.getComponentData(componentConfig.getId(),componentConfig.getType(),null);
				test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationData));
			}
		} catch (IllegalArgumentException ex){
			System.err.println(ex.getMessage());
			System.err.println("Usage : java "+SearchTest.class.getName()+" [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null){
				test.shutdown();
			}
		}
	}
}
