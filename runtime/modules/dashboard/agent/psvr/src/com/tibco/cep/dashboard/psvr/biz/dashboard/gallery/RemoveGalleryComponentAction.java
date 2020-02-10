package com.tibco.cep.dashboard.psvr.biz.dashboard.gallery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGallery;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGalleryCategory;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALTransaction;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException.CAUSE_OP;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 * 
 */
public class RemoveGalleryComponentAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String componentid = request.getParameter("componentid");
		if (StringUtil.isEmptyOrBlank(componentid) == true) {
			return handleError(getMessage("removegallerycomponent.invalid.componentid"));
		}
		//create new presentation context
		PresentationContext ctx;
		try {
			ctx = new PresentationContext(token);
		} catch (Exception ex) {
			return handleError(getMessage("bizaction.tokenprofile.retrieval.failure",getMessageGeneratorArgs(token)));
		}
		//get token role profile
		TokenRoleProfile profile = ctx.getTokenRoleProfile();
		//get the gallery
		MALComponentGallery gallery = profile.getComponentGallery();
		//search the component
		MALComponent component = gallery.searchComponent(componentid);
		if (component == null){
			//component not found , bail out
			return handleError(getMessage("removegallerycomponent.nonexistent.componentid",getMessageGeneratorArgs(token, componentid)));
		}
		//remove the component series config's from data source cache 
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				try {
					DataSourceHandlerCache.getInstance().removeDataSourceHandler(seriesConfig, ctx);
				} catch (Exception e) {
					String message = getMessage("removegallerycomponent.datasrchandler.unloading.failure", getMessageGeneratorArgs(token, URIHelper.getURI(seriesConfig)));
					exceptionHandler.handleException(message, e, Level.WARN);
				}
			}
		}		
		//get all the categories in which the component exists
		List<MALComponentGalleryCategory> categories = gallery.getCategoriesFor(componentid);
		Map<String,MALComponent> resettedComponents = new HashMap<String, MALComponent>();
		try {
			//get the transaction 
			MALTransaction transaction = profile.getMALSession().beginTransaction();
			//either remove or reset the component in each category
			for (MALComponentGalleryCategory category : categories) {
				if (category.isEditable() == true) {
					//if the user owns the component, then physically remove it 
					category.removeComponent(component);
				} else {
					//this component is a customization , we reset to original hidden component
					MALComponent originalComponent = category.resetComponent(component);
					resettedComponents.put(category.getName(), originalComponent);
				}
			}
			//delete the component
			transaction.delete(component);
			//commit all the changes
			transaction.commit();
			return handleSuccess("");
		} catch (MALTransactionException ex){
			CAUSE_OP causeOperation = ex.getCauseOperation();
			String msgKey = "bizaction.transaction.creation.failure";
			if (causeOperation.equals(CAUSE_OP.COMMIT) == true) {
				msgKey = "bizaction.transaction.commit.failure";
				for (MALComponentGalleryCategory category : categories) {
					if (category.isEditable() == true) {
						//add the component back
						try {
							category.addComponent(component);
						} catch (MALException ignoreEx) {
							//INFO Ignoring MALException thrown by category.addComponent(component); 
						}
					} else {
						//replace the original with component
						MALComponent originalComponent = resettedComponents.get(category.getName());
						category.replace(originalComponent, component);
					}
					category.resetChanges();
				}
			}
			if (causeOperation.equals(CAUSE_OP.EXECUTION) == true) {
				msgKey = "bizaction.transaction.execution.failure";
			}
			MALTransaction transaction = profile.getMALSession().getTransaction();
			if (transaction != null){
				try {
					transaction.rollBack();
				} catch (MALTransactionException e) {
					//log original exception
					exceptionHandler.handleException(getMessage(msgKey+".log",getMessageGeneratorArgs(token,command, request.toXML())),ex);
					msgKey = "bizaction.transaction.rollback.failure";
				}
			}			
			return handleError(getMessage(msgKey,getMessageGeneratorArgs(token)),ex);
		} finally {
			MALTransaction transaction = profile.getMALSession().getTransaction();
			if (transaction != null){
				transaction.close();
			}
			if (ctx != null){
				ctx.close();
			}
		}
	}

}