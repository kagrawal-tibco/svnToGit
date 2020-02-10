package com.tibco.cep.dashboard.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentGallery;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PanelConfig;

public class ComponentGalleryUsageTest extends RemoveComponentFromPanelTest {

	protected void addComponent(String pageid, String panelid, String compid) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pageid", pageid);
		map.put("panelid", panelid);
		map.put("componentid", compid);
		BizRequest requestXML = getXMLRequest("addcomponent",map);
		client.execute(requestXML);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComponentGalleryUsageTest test = new ComponentGalleryUsageTest();
		try {
			test.setup(args);
			test.start();
			test.loginUsingCommandArgs();
			test.setRoleUsingCommandArgs();

			PagesConfig pagesConfig = test.getLayout(null);
			ComponentGallery gallery = test.getGallery();
			Map<String, ComponentDefinition> galleryCompCfgs = test.parseComponentGallery(gallery);

			Random random = new Random();
			List<PanelConfig> metricPanels = test.getMetricPanels(pagesConfig);
			int idx = 0;
			if (metricPanels.size() > 1){
				idx = random.nextInt(metricPanels.size());
			}
			PanelConfig panel = metricPanels.get(idx);

			for (ComponentDefinition panelCompConfig : panel.getComponentConfig()) {
				galleryCompCfgs.remove(panelCompConfig.getId());
			}
			if (galleryCompCfgs.isEmpty() == false){
				int index = random.nextInt(galleryCompCfgs.size());
				ComponentDefinition ComponentDefinition = galleryCompCfgs.get(galleryCompCfgs.keySet().toArray(new String[galleryCompCfgs.size()])[index]);
				Map<String, String> map = test.getParentage(pagesConfig, panel);
				test.addComponent(map.get("pageid"), panel.getId(), ComponentDefinition.getId());
			}
			test.logout();
		} catch (IllegalArgumentException ex){
			System.err.println(ex.getMessage());
			System.err.println("Usage : java "+ComponentGalleryUsageTest.class.getName()+" [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null){
				test.shutdown();
			}
		}
	}

}
