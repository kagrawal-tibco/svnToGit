package com.tibco.cep.dashboard.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

public class RemoveComponentFromPanelTest extends BasicReadTest {

	protected void removeComponent(String pageid, String panelid, String compid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pageid", pageid);
		map.put("panelid", panelid);
		map.put("componentid", compid);
		BizRequest requestXML = getXMLRequest("removecomponent",map);
		client.execute(requestXML);
	}

	public static void main(String[] args) {
		RemoveComponentFromPanelTest test = new RemoveComponentFromPanelTest();
		try {
			test.setup(args);
			test.start();
			String[] usernameAndPassword = test.loginUsingCommandArgs();
			String role = test.setRoleUsingCommandArgs();
			PagesConfig pagesConfig = test.getLayout(null);
			ArrayList<ComponentDefinition> compcfgs = new ArrayList<ComponentDefinition>(test.parsePagesConfig(pagesConfig).values());
			if (compcfgs.isEmpty() == true){
				test.logger.log(Level.INFO, "No component(s) to remove");
				return;
			}
			Random random = new Random();
			ComponentDefinition compToRemove = compcfgs.get(random.nextInt(compcfgs.size()));
			Map<String, String> parentage = test.getParentage(pagesConfig, compToRemove);
			test.removeComponent(parentage.get("pageid"), parentage.get("panelid"), compToRemove.getId());
			test.logout();
			test.login(usernameAndPassword[0],usernameAndPassword[1]);
			test.setRole(role);
			pagesConfig = test.getLayout(null);
			compcfgs = new ArrayList<ComponentDefinition>(test.parsePagesConfig(pagesConfig).values());
			for (ComponentDefinition componentConfig : compcfgs) {
				test.logger.log(Level.INFO,"Getting component config for "+componentConfig.getName()+","+componentConfig.getTitle());
				VisualizationModel visualizationModel = test.getComponentConfig(componentConfig.getId(),componentConfig.getType(),null);
				test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationModel));
				test.logger.log(Level.INFO,"Getting component data for "+componentConfig.getName()+","+componentConfig.getTitle());
				VisualizationData visualizationData = test.getComponentData(componentConfig.getId(),componentConfig.getType(),null);
				test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationData));
			}
			if (compcfgs.isEmpty() == false) {
				compToRemove = compcfgs.get(random.nextInt(compcfgs.size()));
				parentage = test.getParentage(pagesConfig, compToRemove);
				test.removeComponent(parentage.get("pageid"), parentage.get("panelid"), compToRemove.getId());
				pagesConfig = test.getLayout(null);
				compcfgs = new ArrayList<ComponentDefinition>(test.parsePagesConfig(pagesConfig).values());
				for (ComponentDefinition componentConfig : compcfgs) {
					test.logger.log(Level.INFO,"Getting component config for "+componentConfig.getName()+","+componentConfig.getTitle());
					VisualizationModel visualizationModel = test.getComponentConfig(componentConfig.getId(),componentConfig.getType(),null);
					test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationModel));
					test.logger.log(Level.INFO,"Getting component data for "+componentConfig.getName()+","+componentConfig.getTitle());
					VisualizationData visualizationData = test.getComponentData(componentConfig.getId(),componentConfig.getType(),null);
					test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, visualizationData));
				}
			}
			else {
				test.logger.log(Level.INFO, "No component(s) to remove");
			}
		} catch (IllegalArgumentException ex){
			ex.printStackTrace();
			System.err.println("Usage : java "+RemoveComponentFromPanelTest.class.getName()+" [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null){
				test.shutdown();
			}
		}
	}
}
