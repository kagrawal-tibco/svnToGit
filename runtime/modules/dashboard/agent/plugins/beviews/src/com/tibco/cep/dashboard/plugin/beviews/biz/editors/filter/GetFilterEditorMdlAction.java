package com.tibco.cep.dashboard.plugin.beviews.biz.editors.filter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALQueryParam;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.DimensionDataSet;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.FilterDimension;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.FilterEditorModel;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryInterpreter;
import com.tibco.cep.studio.dashboard.core.variables.SYSVariableProvider;
import com.tibco.cep.studio.dashboard.core.variables.VariableArgument;
import com.tibco.cep.studio.dashboard.core.variables.VariableExpression;
import com.tibco.cep.studio.dashboard.core.variables.VariableParser;

/**
 * @author apatil
 */
public class GetFilterEditorMdlAction extends BaseAuthenticatedAction {

	private int maxContentDataSetSize;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		maxContentDataSetSize = (Integer) BEViewsProperties.FILTER_PARAMETER_DATASET_MAX_COUNT.getValue(properties);
	}

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String componentID = request.getParameter("compid");
		if (StringUtil.isEmptyOrBlank(componentID) == true) {
			return handleError(getMessage("getfiltermodel.invalid.componentid"));
		}
		String seriesID = request.getParameter("seriescfgid");
		if (StringUtil.isEmptyOrBlank(seriesID) == true) {
			return handleError(getMessage("getfiltermodel.invalid.seriesid"));
		}

		PresentationContext ctx = null;
		MALSeriesConfig seriesConfig = null;

		try {
			ctx = new PresentationContext(token, true);

			TokenRoleProfile tokenRoleProfile = ctx.getTokenRoleProfile();
			ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
			MALComponent component = viewsConfigHelper.getComponentById(componentID);
			if (component == null) {
				return handleError(getMessage("getfiltermodel.nonexistent.component", getMessageGeneratorArgs(token, componentID)));
			}

			for (MALVisualization visualization : component.getVisualization()) {
				for (MALSeriesConfig tempSeriesConfig : visualization.getSeriesConfig()) {
					if (tempSeriesConfig.getId().equals(seriesID) == true) {
						seriesConfig = tempSeriesConfig;
						break;
					}
				}
			}
			if (seriesConfig == null) {
				return handleError(getMessage("getfiltermodel.nonexistent.series", getMessageGeneratorArgs(token, seriesID, component.toString())));
			}

			MALQueryParam[] params = seriesConfig.getActionRule().getParams();

			MALSourceElement sourceElement = ctx.getDataSourceHandler(seriesConfig).getSourceElement();

			BEViewsQueryInterpreter queryValidator = new BEViewsQueryInterpreter(Arrays.asList((Metric) sourceElement.getSource()));
			queryValidator.interpret(seriesConfig.getActionRule().getDataSource().getQuery());
			Collection<String> variableNames = queryValidator.getVariableNames();

			List<FilterDimension> dimensions = new ArrayList<FilterDimension>(params.length);

			for (int i = 0; i < params.length; i++) {
				MALQueryParam param = params[i];
				if (variableNames.contains(param.getName()) == false) {
					String msg = getMessage("getfiltermodel.spurious.parameter", getMessageGeneratorArgs(token, URIHelper.getURI(seriesConfig), param.getName(), variableNames));
					logger.log(Level.WARN, msg);
				} else {
					MALFieldMetaInfo field = sourceElement.getField(queryValidator.getFieldName(param.getName()));
					FilterDimension filterDimension = generateFilterDimension(token, sourceElement, param, field);
					if (filterDimension != null) {
						dimensions.add(filterDimension);
					}
				}
			}

			FilterEditorModel filterEditorModel = new FilterEditorModel();
			filterEditorModel.setFilterDimension(dimensions.toArray(new FilterDimension[dimensions.size()]));

			return handleSuccess(OGLMarshaller.getInstance().marshall(token, filterEditorModel));
		} catch (MALException e) {
			return handleError(getMessage("getfiltermodel.viewsconfig.fetch.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("getfiltermodel.viewsconfig.notfound.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (VisualizationException e) {
			return handleError(getMessage("getfiltermodel.datasourcehandler.fetch.failure", getMessageGeneratorArgs(token, e, URIHelper.getURI(seriesConfig))), e);
		} catch (OGLException e) {
			return handleError(getMessage("getfiltermodel.marshalling.failure", getMessageGeneratorArgs(token, e, "filter editor model")), e);
		} finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	protected FilterDimension generateFilterDimension(SecurityToken token, MALSourceElement metric, MALQueryParam queryParam, MALFieldMetaInfo field) {
		//get the actual value
		String value = queryParam.getValue();
		try {
			//parse the value
			VariableExpression variableExpression = VariableParser.getInstance().parse(value);
			//check if the value is editable
			if (variableExpression != null && variableExpression.hasVariables() == true) {
				if (variableExpression.getVariables().size() > 1) {
					return null;
				}
				if (VariableParser.getInstance().getVariableProvider(variableExpression.getFirstVariable().getIdentifier()).isEditable() == false) {
					return null;
				}
			}
			//we have a editable value
			FilterDimension filterDimension = new FilterDimension();
			//id
			filterDimension.setId(queryParam.getName());
			//data type
			if (field.isNumeric() == true) {
				filterDimension.setDataType("numeric");
			} else if (field.isDate() == true) {
				filterDimension.setDataType("date");
			} else if (field.getDataType().equals(BuiltInTypes.BOOLEAN) == true){
				filterDimension.setDataType("string"); //front end does not understand boolean
			} else {
				filterDimension.setDataType("string");
			}
			// name
			StringBuilder sb = new StringBuilder(field.getName());
			sb.append("[");
			sb.append(queryParam.getName());
			sb.append("]");
			if (variableExpression != null && variableExpression.hasVariables() == true) {
				//indicate to the user that we are using variables
				sb.append("*");
			}
			filterDimension.setName(sb.toString());
			if (variableExpression != null) {
				value = VariableInterpreter.getInstance().interpret(variableExpression, new VariableContext(logger, token, properties, null), false);
			}
			if (StringUtil.isEmptyOrBlank(value) == false) {
				filterDimension.setValue(value);
			}
			//data set
			List<String> dataSet = new LinkedList<String>(getDataSet(token, metric, field, null, -1));
			DimensionDataSet dimDataSet = new DimensionDataSet();
			dimDataSet.setMaxSize(dataSet.size());
			Iterator<String> dataSetIter = dataSet.iterator();
			while (dataSetIter.hasNext()) {
				StringBuilder values = new StringBuilder();
				String dataSetValue = dataSetIter.next();
				values.append("\"");
				values.append(dataSetValue);
				values.append("\"");
				dimDataSet.addValue(values.toString());
			}
			filterDimension.setDimensionDataSet(dimDataSet);
			return filterDimension;
		} catch (ParseException e) {
			exceptionHandler.handleException(getMessage("getfiltermodel.paramvalue.parse.failure", getMessageGeneratorArgs(token, e, value, URIHelper.getURI(queryParam))), e);
			return null;
		}
	}

	protected Collection<String> getDataSet(SecurityToken token, MALSourceElement metric, MALFieldMetaInfo field, String searchQuery, int index) {
		Set<String> dataset = new LinkedHashSet<String>();
		QueryExecutor queryExecutor = null;
		ResultSet result = null;
		if (field.isDate() == true) {
			List<VariableArgument> arguments = new SYSVariableProvider().getSupportedArguments();
			for (VariableArgument variableArgument : arguments) {
				dataset.add(variableArgument.getName());
			}
		} else if (field.getDataType().equals(BuiltInTypes.BOOLEAN) == true) {
			dataset.add(Boolean.TRUE.toString());
			dataset.add(Boolean.FALSE.toString());
		} else if (field.isNumeric() == false) {
			if ((Boolean)BEViewsProperties.FILER_EDITOR_DATASET_GENERATION_ENABLE.getValue(properties) == true) {
				try {
					Entity entity = EntityCache.getInstance().getEntity(metric.getId());
					String query = "select distinct " + field.getName() + " from " + entity.getFullPath()+";";
					logger.log(Level.DEBUG, "query: " + query);
					queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
					result = queryExecutor.executeQuery(new ViewsQuery(query, "", QueryParams.NO_QUERY_PARAMS, metric.getId()));
					List<Tuple> tuples = result.getTuples(maxContentDataSetSize);
					for (Tuple tuple : tuples) {
						FieldValue fieldValue = tuple.getFieldValueByName(field.getName());
						dataset.add(fieldValue.toString());
					}
				} catch (QueryException qe) {
					exceptionHandler.handleException(getMessage("viewdatasrchandler.query.cursoriteration.failure", getMessageGeneratorArgs(token, qe)), qe);
				} finally {
					if (result != null) {
						try {
							result.close();
						} catch (QueryException ignore) {
						}
					}
					if (queryExecutor != null) {
						queryExecutor.close();
					}
				}
			}
		}
		return dataset;
	}

}