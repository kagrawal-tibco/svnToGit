package com.tibco.cep.dashboard.test;

import java.util.HashMap;
import java.util.Random;

import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.PageConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PanelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PartitionConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.types.PanelState;
import com.tibco.cep.dashboard.psvr.ogl.model.types.PartitionState;
import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.kernel.service.logging.Level;

public class SaveLayoutTest extends BasicReadTest {
	
	private static final PanelState[] PANEL_STATES = new PanelState[]{
		PanelState.NORMAL,
		PanelState.MINIMIZED,
//		PanelState.MAXIMIZED
	};

	protected void saveLayout(PagesConfig layout) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("layout", OGLMarshaller.getInstance().marshall(null, layout));
		BizRequest requestXML = getXMLRequest("savelayout", map);
		client.execute(requestXML);
	}

	public static void main(String[] args) {
		SaveLayoutTest test = new SaveLayoutTest();
		try {
			test.setup(args);
			test.start();
			String[] usernameAndPassword = test.loginUsingCommandArgs();
			String role = test.setRoleUsingCommandArgs();
			PagesConfig pagesConfig = test.getLayout(null);
			test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, pagesConfig));
			Random random = new Random();
			for (PageConfig pageConfig : pagesConfig.getPageConfig()) {
				for (PartitionConfig partitionConfig : pageConfig.getPartitionConfig()) {
					int span = random.nextInt(100);
					while (span == 0){
						span = random.nextInt(100);
					}
					partitionConfig.setSpan(span+"%");
					partitionConfig.setState(partitionConfig.getState().equals(PartitionState.OPEN) ? PartitionState.CLOSED : PartitionState.OPEN);
					for (PanelConfig panelConfig : partitionConfig.getPanelConfig()) {
						span = random.nextInt(100);
						while (span == 0){
							span = random.nextInt(100);
						}
						panelConfig.setSpan(span+"%");
						PanelState panelState = PANEL_STATES[random.nextInt(PANEL_STATES.length)];
						while (panelState.equals(panelConfig.getState()) == true){
							panelState = PANEL_STATES[random.nextInt(PANEL_STATES.length)];
						}
						panelConfig.setState(panelState);
					}
				}
			}
			test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, pagesConfig));
			test.saveLayout(pagesConfig);
			test.logout();
			test.login(usernameAndPassword[0], usernameAndPassword[1]);
			test.setRole(role);
			pagesConfig = test.getLayout(null);
			test.logger.log(Level.INFO,OGLMarshaller.getInstance().marshall(null, pagesConfig));
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + SaveLayoutTest.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null) {
				test.shutdown();
			}
		}
	}
}
