package com.tibco.cep.dashboard.plugin.beviews.biz.editors.filter;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.runtime.CategoryValuesConsolidatorCache;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALTransaction;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener.OPERATION;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException.CAUSE_OP;
import com.tibco.cep.dashboard.psvr.mal.model.MALActionRule;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALQueryParam;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.FilterDimension;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.FilterEditorModel;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.variables.VariableArgument;
import com.tibco.cep.studio.dashboard.core.variables.VariableExpression;
import com.tibco.cep.studio.dashboard.core.variables.VariableParser;
import com.tibco.cep.studio.dashboard.core.variables.VariableProvider;

public class UpdateFilterEditorMdlAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String componentID = request.getParameter("compid");
		if (StringUtil.isEmptyOrBlank(componentID) == true) {
			return handleError(getMessage("updatefiltermodel.invalid.componentid"));
		}
		String seriesID = request.getParameter("seriescfgid");
		if (StringUtil.isEmptyOrBlank(seriesID) == true) {
			return handleError(getMessage("updatefiltermodel.invalid.seriesid"));
		}

		PresentationContext ctx = null;
		TokenRoleProfile tokenRoleProfile = null;
		ViewsConfigHelper viewsConfigHelper = null;
		MALComponent component = null;
		Map<String,Map<String,String>> existingValuesBySeries = new HashMap<String, Map<String,String>>();

		try {
			ctx = new PresentationContext(token, true);
			tokenRoleProfile = ctx.getTokenRoleProfile();
			viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
			component = viewsConfigHelper.getComponentById(componentID);

			if (component == null) {
				return handleError(getMessage("updatefiltermodel.nonexistent.component", getMessageGeneratorArgs(token, componentID)));
			}

			FilterEditorModel userEnteredFilterModel = parseFilterEditorModel(token,request);

			Boolean applyToAll = Boolean.valueOf(request.getParameter("applytoall"));

			MALTransaction transaction = tokenRoleProfile.getMALSession().beginTransaction();
			viewsConfigHelper.firePrepareForChange(component);
			String parentPath = URIHelper.getURI(component);
	        //create a user preference
	        transaction.createUserPreference();
			if (applyToAll == true){
				for (MALVisualization visualization : component.getVisualization()) {
					for (MALSeriesConfig tempSeriesConfig : visualization.getSeriesConfig()) {
						viewsConfigHelper.firePreOp(parentPath, tempSeriesConfig, null, OPERATION.UPDATE);
						Map<String, String> existingValues = updateFilterParams(token, tempSeriesConfig.getActionRule(),userEnteredFilterModel, ctx);
						existingValuesBySeries.put(tempSeriesConfig.getId(), existingValues);
						viewsConfigHelper.firePostOp(parentPath, tempSeriesConfig, null, OPERATION.UPDATE);
					}
				}
			}
			else {
				MALSeriesConfig seriesConfig = null;
				for (MALVisualization visualization : component.getVisualization()) {
					for (MALSeriesConfig tempSeriesConfig : visualization.getSeriesConfig()) {
						if (tempSeriesConfig.getId().equals(seriesID) == true) {
							seriesConfig = tempSeriesConfig;
							break;
						}
					}
				}
				if (seriesConfig == null) {
					return handleError(getMessage("updatefiltermodel.nonexistent.series", getMessageGeneratorArgs(token, seriesID, component.toString())));
				}
				viewsConfigHelper.firePreOp(parentPath, seriesConfig, null, OPERATION.UPDATE);
				Map<String, String> existingValues = updateFilterParams(token, seriesConfig.getActionRule(),userEnteredFilterModel, ctx);
				existingValuesBySeries.put(seriesConfig.getId(), existingValues);
				viewsConfigHelper.firePostOp(parentPath, seriesConfig, null, OPERATION.UPDATE);
			}
			transaction.commit();
			viewsConfigHelper.fireChangeCompleted(component);
			try {
				CategoryValuesConsolidatorCache.getInstance().getConsolidator(token, component, ctx).setCategoryValues(null);
			} catch (Exception e) {
				return handleError("Failed to refresh "+component.getDisplayName(), e);
			}
			return handleSuccess("");
		} catch (MALTransactionException ex) {
			CAUSE_OP causeOperation = CAUSE_OP.COMMIT;
			if (ex instanceof MALTransactionException){
				causeOperation = ((MALTransactionException)ex).getCauseOperation();
			}
			String msgKey = "updatefiltermodel.transaction.creation.failure";
			if (causeOperation.equals(CAUSE_OP.COMMIT) == true) {
				msgKey = "updatefiltermodel.transaction.commit.failure";
				//roll back all changes
				for (MALVisualization visualization : component.getVisualization()) {
					for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
						Map<String, String> existingValues = existingValuesBySeries.get(seriesConfig.getId());
						if (existingValues != null){
							MALQueryParam[] queryParams = seriesConfig.getActionRule().getParams();
							for (MALQueryParam queryParam : queryParams) {
								String existingValue = existingValues.get(queryParam.getName());
								if (existingValue != null){
									queryParam.setValue(existingValue);
									queryParam.resetPropertyTracking();
								}
							}
						}
					}
				}
			}
			//fire panel changes abort
			viewsConfigHelper.fireChangeAborted(component);
			if (causeOperation.equals(CAUSE_OP.EXECUTION) == true) {
				if (StringUtil.isEmptyOrBlank(ex.getMessage()) == true) {
					msgKey = "updatefiltermodel.transaction.genericexecution.failure";
				}
				else {
					msgKey = "updatefiltermodel.transaction.specificexecution.failure";
				}
			}
			MALTransaction transaction = MALTransaction.getCurrentTransaction();
			if (transaction != null){
				try {
					transaction.rollBack();
				} catch (MALTransactionException e) {
					//handle original exception
					exceptionHandler.handleException(getMessage(msgKey+".log",getMessageGeneratorArgs(token,command,request.toXML())),ex);
					msgKey = "updatefiltermodel.transactionrollback.failure";
				}
			}
			return handleError(getMessage(msgKey,getMessageGeneratorArgs(token,ex)),ex);
		} catch (MALException e) {
			return handleError(getMessage("updatefiltermodel.viewsconfig.fetch.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("updatefiltermodel.viewsconfig.notfound.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("updatefiltermodel.general.processing.failure", getMessageGeneratorArgs(token,e)), e);
		} finally {
			MALTransaction transaction = MALTransaction.getCurrentTransaction();
			if (transaction != null){
				transaction.close();
			}
		}
	}

	protected Map<String, String> updateFilterParams(SecurityToken token, MALActionRule actionRule, FilterEditorModel filterEditorModel, PresentationContext ctx) throws MALTransactionException {
		if (actionRule.getParamsCount() == 0){
			return Collections.emptyMap();
		}
		Map<String, String> existingValues = new HashMap<String, String>();
		for (MALQueryParam parameter : actionRule.getParams()) {
			for (FilterDimension dimension : filterEditorModel.getFilterDimension()) {
				if (dimension.getId().equals(parameter.getName()) == true){
					//user entered value
					String userEnteredValue = dimension.getValue();
					//the expression of the user entered value
					VariableExpression userEnteredVariableExpression = null;
					//is user entered value variable based
					boolean userEnteredValueHasVariables = false;
					//attempt to parse the user entered value against known variable provider(s) arguments
					for (VariableProvider variableProvider : VariableParser.getInstance().getVariableProviders()) {
						for (VariableArgument argument : variableProvider.getSupportedArguments()) {
							//PATCH this should be against the id
							if (argument.getName().equals(userEnteredValue) == true) {
								//we have a match , reassign userEnteredValue to be the id of the argument;
								userEnteredValue = argument.getId();
								break;
							}
						}
						if (userEnteredValue.equals(dimension.getValue()) == false) {
							//we have a hit
							break;
						}
					}
					//attempt to parse the user entered value as a variable expression
					try {
						userEnteredVariableExpression = VariableParser.getInstance().parse(userEnteredValue);
						userEnteredValueHasVariables = userEnteredVariableExpression != null && userEnteredVariableExpression.hasVariables() == true;
					} catch (ParseException ex) {
						throw new MALTransactionException(CAUSE_OP.EXECUTION,ex.getMessage());
					}
					//check if user entered value is a variable, if then attempt to parsed based on the parameter datatype
					if (userEnteredValueHasVariables == false) {
						//check if userEnteredValue is valid as per datatype
						try {
							BuiltInTypes.resolve(parameter.getType()).valueOf(userEnteredValue);
						} catch (IllegalArgumentException e) {
							throw new MALTransactionException(CAUSE_OP.EXECUTION, e.getMessage());
						}
					}
					//parameter value
					String parameterValue = parameter.getValue();
					try {
						//parse the parameter value
						VariableExpression parameterValueVariableExpression = VariableParser.getInstance().parse(parameterValue);
						//is parameter value variable based
						boolean parameterValueHasVariables = parameterValueVariableExpression != null && parameterValueVariableExpression.hasVariables() == true;
						//check if the value has changed
						boolean valueChanged = !userEnteredValue.equals(parameterValue);
						//are we dealing with variables
						if (parameterValueHasVariables) {
							//parameter has variables
							if (userEnteredValueHasVariables == false) {
								//user did not enter a variable
								//interpret the variable expression
								String interpretedParameterValue = VariableInterpreter.getInstance().interpret(parameterValueVariableExpression, new VariableContext(logger, ctx.getSecurityToken(), properties, null), false);
								valueChanged = !interpretedParameterValue.equals(userEnteredValue);
							}
							else {
								//user entered an variable, check the raw variable (they might interpret to the same value, but we need to honor user change)
							}
						}
						if (valueChanged == true){
							existingValues.put(parameter.getName(), parameterValue);
							parameter.setValue(userEnteredValue);
						}
					} catch (ParseException e) {
						exceptionHandler.handleException(getMessage("updatefiltermodel.queryparamvariable.parse.failure", getMessageGeneratorArgs(token,e, parameterValue, URIHelper.getURI(parameter))), e);
					}
				}
			}
		}
		return existingValues;
	}

    private FilterEditorModel parseFilterEditorModel(SecurityToken token, BizRequest request) throws RequestProcessingException {
        String filterMdlXML = request.getParameter("filtermodel");
        if (StringUtil.isEmptyOrBlank(filterMdlXML) == true) {
            throw new RequestProcessingException("no filter model xml specified");
        }
        FilterEditorModel userEnteredFilterModel = null;
        try {
            logger.log(Level.DEBUG, "Received [" + filterMdlXML + "]");
            userEnteredFilterModel = (FilterEditorModel) OGLUnmarshaller.getInstance().unmarshall(FilterEditorModel.class, filterMdlXML);
        } catch (OGLException e) {
            String exMsg = exceptionHandler.getMessage(e);
            exMsg = getMessage("updatefiltermodel.filtermodel.unmarshall.failure", getMessageGeneratorArgs(token,exMsg));
            throw new RequestProcessingException(exMsg, e);
        }
        return userEnteredFilterModel;
    }

}