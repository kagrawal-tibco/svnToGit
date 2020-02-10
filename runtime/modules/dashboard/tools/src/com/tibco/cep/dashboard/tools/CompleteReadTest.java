package com.tibco.cep.dashboard.tools;

import java.util.Collection;
import java.util.HashSet;

import com.tibco.cep.dashboard.psvr.mal.managers.MALPageSelectorComponentManager;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.HeaderConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.LoginCustomization;
import com.tibco.cep.dashboard.psvr.ogl.model.PageSetRowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PageSetSelectorModel;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.kernel.service.logging.Level;

public class CompleteReadTest extends BasicReadTest implements Launchable {

	protected void readAll() throws Exception{
		HashSet<String> pageIds = new HashSet<String>();
		HashSet<String> vistedPageIds = new HashSet<String>();
		String pageId = null;
		do {
			PagesConfig pagesConfig = readPage(pageId);
			Collection<ComponentDefinition> componentConfigs = parsePagesConfig(pagesConfig).values();
			for (ComponentDefinition componentConfig : componentConfigs) {
				if (MALPageSelectorComponentManager.DEFINITION_TYPE.equals(componentConfig.getType()) == true){
					PageSetSelectorModel visualizationModel = (PageSetSelectorModel) getComponentConfig(componentConfig.getId(), componentConfig.getType(), null);
					PageSetRowConfig[] pageSetRowConfigs = visualizationModel.getPageSetSelectorConfig().getPageSetRowConfig();
					for (PageSetRowConfig pageSetRowConfig : pageSetRowConfigs) {
						pageIds.add(pageSetRowConfig.getPagesetID());
					}
				}
			}
			readGallery();
			vistedPageIds.add(pagesConfig.getDefaultPage());
			pageId = null;
			for (String tempPageId : pageIds) {
				if (vistedPageIds.contains(tempPageId) == false){
					pageId = tempPageId;
					break;
				}
			}
		} while (pageId != null);
	}

	@Override
	public void launch(String[] args) throws IllegalArgumentException, Exception {
		try {
			setup(args);
			start();
			loginUsingCommandArgs();
			setRoleUsingCommandArgs();
			LoginCustomization loginCustomization = getLoginCustomization();
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, loginCustomization));
			HeaderConfig headerConfig = getHeaderConfig();
			logger.log(Level.INFO, OGLMarshaller.getInstance().marshall(null, headerConfig));
			readAll();
		} finally {
			shutdown();
		}
	}

	public static void main(String[] args) {
		CompleteReadTest test = new CompleteReadTest();
		try {
			test.launch(args);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + test.getClass().getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (test != null) {
				test.shutdown();
			}
		}
	}

}
