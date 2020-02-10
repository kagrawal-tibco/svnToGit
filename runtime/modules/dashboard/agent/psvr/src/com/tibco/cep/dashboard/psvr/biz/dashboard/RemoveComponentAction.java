package com.tibco.cep.dashboard.psvr.biz.dashboard;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.mal.MALTransaction;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener.OPERATION;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException.CAUSE_OP;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

public class RemoveComponentAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String pageid = request.getParameter("pageid");
		if (StringUtil.isEmptyOrBlank(pageid) == true){
			return handleError("removecomponent.invalid.pageid");
		}
		
		String panelid = request.getParameter("panelid");
		if (StringUtil.isEmptyOrBlank(panelid) == true){
			return handleError("removecomponent.invalid.panelid");
		}
		
		String componentid = request.getParameter("componentid");
		if (StringUtil.isEmptyOrBlank(componentid) == true){
			return handleError("removecomponent.invalid.componentid");
		}
		
		TokenRoleProfile tokenRoleProfile;
		try {
			tokenRoleProfile = getTokenRoleProfile(token);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("bizaction.tokenprofile.retrieval.failure",getMessageGeneratorArgs(token)),e);
		}
		ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
		
		MALPage page = viewsConfigHelper.getPageByID(pageid);
		if (page == null){
			return handleError(getMessage("removecomponent.nonexistent.pageid",getMessageGeneratorArgs(token,pageid))); 
		}
		
		MALPanel panel = viewsConfigHelper.getPanelInPage(page, panelid);
		if (panel == null){
			return handleError(getMessage("removecomponent.nonexistent.panelid",getMessageGeneratorArgs(token,panelid,page)));
		}
		
		int componentIndex = viewsConfigHelper.getComponentIndexInPanel(panel,componentid);
		if (componentIndex == -1){
			return handleError(getMessage("removecomponent.nonexistent.componentid",getMessageGeneratorArgs(token,componentid, URIHelper.getURI(panel))));
		}
		MALComponent component = panel.getComponent(componentIndex);

        MALSession session = tokenRoleProfile.getMALSession();
        String panelURI = URIHelper.getURI(panel);
        
        try {
        	//begin a new transaction
			MALTransaction transaction = session.beginTransaction();
			//prepare the system for panel changes
	        viewsConfigHelper.firePrepareForChange(panel);
	        //create a user preference
	        transaction.createUserPreference();
	        //fire a component deletion pre-operation
			viewsConfigHelper.firePreOp(panelURI, component, null, OPERATION.DELETE);
			//remove the actual component from the panel
			panel.removeComponent(component);
			//fire a component deletion post-operation
			viewsConfigHelper.firePostOp(panelURI, component, null, OPERATION.DELETE);
			//commit the transaction
			transaction.commit();
			//fire panel changes completion
			viewsConfigHelper.fireChangeCompleted(panel);
			return handleSuccess("");
		} catch (MALException ex){
			CAUSE_OP causeOperation = CAUSE_OP.COMMIT;
			if (ex instanceof MALTransactionException){
				causeOperation = ((MALTransactionException)ex).getCauseOperation();
			}
			String msgKey = "bizaction.transaction.creation.failure";
			if (causeOperation.equals(CAUSE_OP.COMMIT) == true) {
				msgKey = "bizaction.transaction.commit.failure";
				//add the component back
				panel.addComponent(componentIndex,component);
				//fire panel changes abort 
				viewsConfigHelper.fireChangeAborted(panel);
				//reset the panel
				panel.resetPropertyTracking();
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
					msgKey = "bizaction.transaction.rollback.failure";
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

}
