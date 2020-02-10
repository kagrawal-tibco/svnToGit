package com.tibco.cep.dashboard.psvr.biz.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.ArrayUtil;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener.OPERATION;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.mal.MALTransaction;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException.CAUSE_OP;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.mal.model.types.PanelStateEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.PartitionStateEnum;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ComponentDefinition;
import com.tibco.cep.dashboard.psvr.ogl.model.PageConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PagesConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PanelConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.PartitionConfig;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SaveLayoutAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		//get the raw layout
		String rawLayout = request.getParameter("layout");
		if (StringUtil.isEmptyOrBlank(rawLayout) == true){
			return handleError("savelayout.invalid.layout");
		}
		//parse layout
		PagesConfig layout;
		try {
			layout = (PagesConfig) OGLUnmarshaller.getInstance().unmarshall(PagesConfig.class, rawLayout);
		} catch (OGLException e) {
			return handleError("savelayout.layoutparsing.failure");
		}
		//get the token role profile
		TokenRoleProfile tokenRoleProfile;
		try {
			tokenRoleProfile = getTokenRoleProfile(token);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("bizaction.tokenprofile.retrieval.failure",getMessageGeneratorArgs(token)),e);
		}
		//get the views config helper
		ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
		//get the session
		MALSession session = tokenRoleProfile.getMALSession();
		//map to record existing values to revert in case of failure
		Map<String,String[]> existingValues = new HashMap<String, String[]>();
		//list of pages which have been modified
		List<MALPage> pages = new ArrayList<MALPage>(layout.getPageConfigCount());
		try {
			//begin a transaction
			MALTransaction transaction = session.beginTransaction();
			for (PageConfig pageConfig : layout.getPageConfig()) {
				MALPage page = viewsConfigHelper.getPageByID(pageConfig.getId());
				if (page == null){
					return handleError(getMessage("savelayout.invalid.pageid", getMessageGeneratorArgs(token,pageConfig.getId())));
				}
		        //create a user preference
		        transaction.createUserPreference();
				//add the page to the modified pages list
				pages.add(page);
				//prepare the system for page changes
				viewsConfigHelper.firePrepareForChange(page);
				//go thru the partitions
				for (PartitionConfig partitionConfig : pageConfig.getPartitionConfig()) {
					for (MALPartition partition : page.getPartition()) {
						if (partition.getId().equals(partitionConfig.getId()) == true){
							//we have a matching partition, lets process the changes
					        //fire a partition change pre-operation
							String pageURI = URIHelper.getURI(page);
							viewsConfigHelper.firePreOp(pageURI, partition, null, OPERATION.UPDATE);
							//record the existing values
							existingValues.put(partition.getId(), new String[]{partition.getSpan(),partition.getState().toString()});
							//update the values
							partition.setSpan(partitionConfig.getSpan());
							partition.setState(PartitionStateEnum.valueOf(partitionConfig.getState().toString()));
							//fire a partition change post-operation
							viewsConfigHelper.firePostOp(pageURI, partition, null, OPERATION.UPDATE);
							//go thru the panels
							for (PanelConfig panelConfig : partitionConfig.getPanelConfig()) {
								MALPanel panel = viewsConfigHelper.getPanelInPage(page, panelConfig.getId());
								if (panel == null){
									return handleError(getMessage("savelayout.invalid.panelid", getMessageGeneratorArgs(token,panelConfig.getId(),page.getName())));
								}
								//fire a panel change pre-operation
								String partitionURI = URIHelper.getURI(partition);
								viewsConfigHelper.firePreOp(partitionURI, panel, null, OPERATION.UPDATE);
								//extract existing component ids
								String[] componentIds = getComponentIds(panel.getComponent());
								//record the existing values
								String[] existingPanelValues = new String[componentIds.length+2];
								existingPanelValues[0] = panel.getSpan();
								existingPanelValues[1] = panel.getState().toString();
								System.arraycopy(componentIds, 0, existingPanelValues, 2, componentIds.length);
								existingValues.put(panel.getId(), existingPanelValues);
								//update the values
								panel.setSpan(panelConfig.getSpan());
								panel.setState(PanelStateEnum.valueOf(panelConfig.getState().toString()));
								//check if component positions have changed
								String[] componentConfigIds = getComponentIds(panelConfig.getComponentConfig());
								if (ArrayUtil.compare(componentIds, componentConfigIds) == false){
									MALComponent[] reOrderedComponents = new MALComponent[componentConfigIds.length];
									for (int i = 0; i < componentConfigIds.length; i++) {
										reOrderedComponents[i] = viewsConfigHelper.getComponentById(componentConfigIds[i]);
									}
									panel.setComponent(reOrderedComponents);
								}
								//fire a panel change post-operation
								viewsConfigHelper.firePostOp(partitionURI, panel, null, OPERATION.UPDATE);
							}
						}
					}
				}
			}
			//commit the transaction
			transaction.commit();
			//fire page changes completion
			for (MALPage page : pages) {
				viewsConfigHelper.fireChangeCompleted(page);
			}
			return handleSuccess("");
		} catch (MALTransactionException ex) {
			CAUSE_OP causeOperation = CAUSE_OP.COMMIT;
			if (ex instanceof MALTransactionException){
				causeOperation = ((MALTransactionException)ex).getCauseOperation();
			}
			String msgKey = "bizaction.transaction.creation.failure";
			if (causeOperation.equals(CAUSE_OP.COMMIT) == true) {
				msgKey = "bizaction.transaction.commit.failure";
				//roll back all changes
				for (MALPage page : pages) {
					for (MALPartition partition : page.getPartition()) {
						String[] existingValue = existingValues.get(partition.getId());
						if (existingValue != null){
							partition.setSpan(existingValue[0]);
							partition.setState(PartitionStateEnum.valueOf(existingValue[1]));
							partition.resetPropertyTracking();
						}
						for (MALPanel panel : partition.getPanel()) {
							existingValue = existingValues.get(panel.getId());
							if (existingValue != null){
								panel.setSpan(existingValue[0]);
								panel.setState(PanelStateEnum.valueOf(existingValue[1]));
								String[] existingCompIds = new String[existingValue.length-2];
								System.arraycopy(existingValue, 2, existingCompIds, 0, existingCompIds.length);
								if (ArrayUtil.compare(existingCompIds, getComponentIds(panel.getComponent())) == false){
									MALComponent[] originalComponents = new MALComponent[existingCompIds.length];
									for (int i = 0; i < existingCompIds.length; i++) {
										originalComponents[i] = viewsConfigHelper.getComponentById(existingCompIds[i]);
									}
									panel.setComponent(originalComponents);
								}
								panel.resetPropertyTracking();
							}
						}
					}
					page.resetPropertyTracking();
					//fire panel changes abort
					viewsConfigHelper.fireChangeAborted(page);
				}
			}
			if (causeOperation.equals(CAUSE_OP.EXECUTION) == true) {
				msgKey = "bizaction.transaction.execution.failure";
			}
			MALTransaction transaction = session.getTransaction();
			if (transaction != null){
				try {
					transaction.rollBack();
				} catch (MALTransactionException e) {
					//handle original exception
					exceptionHandler.handleException(getMessage(msgKey+".log",getMessageGeneratorArgs(token,command,request.toXML())),ex);
					msgKey = "savelayout.transactionrollback.failure";
				}
			}
			return handleError(getMessage(msgKey,getMessageGeneratorArgs(token)),ex);
		} finally {
			MALTransaction transaction = session.getTransaction();
			if (transaction != null){
				transaction.close();
			}
		}
	}

	private String[] getComponentIds(MALComponent[] components) {
		String[] ids = new String[components.length];
		for (int i = 0; i < components.length; i++) {
			ids[i] = components[i].getId();
		}
		return ids;
	}

	private String[] getComponentIds(ComponentDefinition[] componentDefinitions) {
		String[] ids = new String[componentDefinitions.length];
		for (int i = 0; i < componentDefinitions.length; i++) {
			ids[i] = componentDefinitions[i].getId();
		}
		return ids;
	}

}
