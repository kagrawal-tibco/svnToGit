package com.tibco.cep.dashboard.plugin.beviews.data;

import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS;
import com.tibco.cep.studio.dashboard.core.variables.VariableExpression;
import com.tibco.cep.studio.dashboard.core.variables.VariableParser;

public class QueryParamConvertor {

	private Logger logger;

	private String loggingIdentifier;

	private Properties properties;

	private QueryParams queryParams;

	public QueryParamConvertor(Logger logger, String loggingIdentifier, Properties properties, QueryParams queryParams) {
		this.logger = logger;
		this.loggingIdentifier = loggingIdentifier;
		this.properties = properties;
		this.queryParams = queryParams;
	}

	public void convert(PresentationContext pCtx) {
		for (String paramName : queryParams.getParameterNames()) {
			ParsedQueryParam parsedQueryParam  = new ParsedQueryParam(queryParams.getParameterType(paramName), queryParams.getParameter(paramName));
			String convertedValue = parsedQueryParam.getValue(pCtx);
			if (convertedValue.equals(queryParams.getParameter(paramName)) == false) {
				if (logger.isEnabledFor(Level.DEBUG) == true){
					logger.log(Level.DEBUG, "Converted "+queryParams.getParameter(paramName)+" to "+convertedValue + " for "+loggingIdentifier);
				}
				queryParams.addParameter(paramName, queryParams.getParameterType(paramName), convertedValue);
			}
		}
	}

	private class ParsedQueryParam {

		private DataType dataType;

		private String value;

		private VariableExpression variableExpression;

		private PRE_DEFINED_BINDS backwardCompatibleBind;

		private ParsedQueryParam(DataType dataType, String value) {
			this.dataType = dataType;
			this.value = value;
			if (StringUtil.isEmptyOrBlank(value) == false) {
				try {
					variableExpression = VariableParser.getInstance().parse(value);
				} catch (ParseException e) {
					variableExpression = null;
				}
				if (variableExpression == null || variableExpression.getVariables().isEmpty() == true) {
					variableExpression = null;
					if (dataType.equals(BuiltInTypes.DATETIME) == true) {
						backwardCompatibleBind = BEViewsQueryDateTypeInterpreter.getPredefinedBind(value);
					}
				}
			}
		}

		private String getValue(PresentationContext pCtx) {
			if (StringUtil.isEmptyOrBlank(value) == true) {
				Comparable<?> defaultValue = dataType.getDefaultValue();
				if (dataType.equals(BuiltInTypes.DATETIME) == true) {
					return BEViewsQueryDateTypeInterpreter.DATETIME_CONVERTOR.format(defaultValue);
				}
				if (defaultValue instanceof Double) {
					defaultValue = ((Double)defaultValue).intValue();
				}
				return String.valueOf(defaultValue);
			}
			String convertedValue = value;
			if (backwardCompatibleBind != null) {
				convertedValue = BEViewsQueryDateTypeInterpreter.convertBindValueToString(value);
			}
			else if (variableExpression != null) {
				VariableContext variableContext = new VariableContext(logger, pCtx.getSecurityToken(), properties, null);
				convertedValue = VariableInterpreter.getInstance().interpret(variableExpression, variableContext, false);
			}
			if (dataType.equals(BuiltInTypes.DATETIME) == true) {
				// check if the incoming value can be safely converted to a date
				Date date = BEViewsQueryDateTypeInterpreter.convertBindValue(convertedValue);
				if (date == null) {
					// no we cannot convert incoming value to a date, log an warning and default to current date
					convertedValue = BEViewsQueryDateTypeInterpreter.DATETIME_CONVERTOR.format(new Date());
					logger.log(Level.WARN, "could not convert \"" + value + "\", defaulting to " + convertedValue);
				} else {
					// yahoo, we have a valid value, standardize it
					convertedValue = BEViewsQueryDateTypeInterpreter.DATETIME_CONVERTOR.format(date);
				}
			}
			return convertedValue;
		}
	}
}
