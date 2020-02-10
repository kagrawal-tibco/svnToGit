package com.tibco.rta.service.persistence.db;

import java.util.List;

import com.tibco.rta.log.Level;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.SortOrder;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.query.filter.InFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.filter.LikeFilter;
import com.tibco.rta.query.filter.LogicalFilter;
import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.filter.RelationalFilter;


public class DBFilterBasedMetricNodeBrowser extends DBAbstractMetricNodeBrowser {
	QueryByFilterDef queryDef;
	Filter filter;

	DBFilterBasedMetricNodeBrowser(DatabasePersistenceService pServ, QueryByFilterDef queryDef) throws Exception {
		super(pServ, queryDef.getSchemaName(), queryDef.getCubeName(), queryDef.getHierarchyName());
		this.queryDef = queryDef;
		this.filter = queryDef.getFilter();
		String query = "SELECT * FROM " + tableName;
		if (filter != null) {
			query = query + " WHERE " + filterToQueryString(filter);
		}

		List<MetricFieldTuple> orderByTuples = queryDef.getOrderByTuples();
		if (orderByTuples != null && !orderByTuples.isEmpty()) {
			query = query + getOrderByQuerySubStr(orderByTuples);
			if (queryDef.getSortOrder() != null) {
				if (queryDef.getSortOrder().equals(SortOrder.DESCENDING)) {
					query += " DESC ";
				}
			}
		}
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "SQL query for DBFilterBasedMetricNodeBrowser = [%s] ", query);
		}

		executeQuery(pServ, query);
	}

	public String filterToQueryString(Filter filter) {
		StringBuilder sb = new StringBuilder();
        if (filter instanceof LogicalFilter) {
            LogicalFilter lf = (LogicalFilter) filter;
            String keyword = null;
            if (filter instanceof AndFilter) {
                keyword = " AND ";
            } else if (filter instanceof OrFilter) {
                keyword = " OR ";
            }

            Filter[] filters = lf.getFilters();

            if (filters.length == 0) {
                return "";
            } else if (filters.length == 1) {
                return " ( " + filterToQueryString(filters[0]) + " ) ";
            } else {
                sb.append(" ( ");
                sb.append(filterToQueryString(filters[0]));
                sb.append( " ) ");
                for (int i = 1; i < filters.length; i++) {
                    sb.append(keyword);
                    sb.append(" ( ");
                    sb.append(filterToQueryString(filters[i]));
                    sb.append( " ) ");
                }
                return sb.toString();
            }

        } else if (filter instanceof RelationalFilter) {
            RelationalFilter rf = (RelationalFilter) filter;
            MetricQualifier mq = rf.getMetricQualifier();
            FilterKeyQualifier fq = rf.getKeyQualifier();
            String key = rf.getKey();
            Object value = rf.getValue();

            String operator = null;
            if (rf instanceof EqFilter) {
                operator = " = ";
            } else if (rf instanceof NEqFilter) {
                operator = " != ";
            } else if (rf instanceof GEFilter) {
                operator = " >= ";
            } else if (rf instanceof GtFilter) {
                operator = " > ";
            } else if (rf instanceof LEFilter) {
                operator = " <= ";
            } else if (rf instanceof LtFilter) {
                operator = " < ";
            } else if (filter instanceof InFilter) {
            	return inFilterToQueryString((InFilter)filter, sb);                        	            
            } else if (filter instanceof LikeFilter) {
            	return likeFilterToQueryString((LikeFilter)filter, sb);                        	            
            }

            String escStr = "";

            if (mq != null) {
            	constructQueryForMQ(mq, escStr, operator, value, sb, key);
            } else if (fq != null) {
            	constructQueryForFQ(fq, escStr, operator, value, sb, key);
            }

            return sb.toString();
        } else if (filter instanceof NotFilter) {
        	NotFilter nf = (NotFilter) filter;
        	return " NOT ( " + filterToQueryString(nf.getBaseFilter()) + " ) ";
        }

        return sb.toString();
    }

	private String likeFilterToQueryString(LikeFilter likeFilter, StringBuilder sb) {
		MetricQualifier mq = likeFilter.getMetricQualifier();
		FilterKeyQualifier fq = likeFilter.getKeyQualifier();
		Object value = likeFilter.getValue();
		String key = likeFilter.getKey();
		String escStr = "";
		String operator = "LIKE";
		if (mq != null) {
			if (mq == MetricQualifier.DIMENSION_LEVEL) {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_NAME_FIELD).append(" ").append(operator).append(" ");
			} else if (mq == MetricQualifier.DIMENSION_LEVEL_NO) {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_FIELD).append(" ").append(operator).append(" ");
			} else if (mq == MetricQualifier.CREATED_TIME) {
				sb.append(" ").append(DBConstant.CREATED_DATE_TIME_FIELD).append(" ").append(operator).append(" ");
			} else if (mq == MetricQualifier.UPDATED_TIME) {
				sb.append(" ").append(DBConstant.UPDATED_DATE_TIME_FIELD).append(" ").append(operator).append(" ");
			} else if (mq == MetricQualifier.IS_PROCESSED) {
				sb.append(" ").append(DBConstant.IS_PROCESSED).append(" ").append(operator).append(" ");
			}
		} else if (fq != null) {
			sb.append(" ").append(key).append(" ").append(operator);
		}

		if (value != null) {
			if (value instanceof String || value instanceof Boolean) {
				escStr = "'";
			} else {
				escStr = "";
			}
			sb.append(escStr).append(value).append(escStr);
		} else {
			sb.append(" IS NULL ");
		}
		return sb.toString();
	}

	private void constructQueryForFQ(FilterKeyQualifier fq, String escStr, String operator, Object value, StringBuilder sb, String key) {
		if (value instanceof String) {
            escStr = "'";
        }

		if (value != null) {
			if (value instanceof Boolean) {
				 escStr = "'";
				 String strValue = getBooleanValue((Boolean)value);
				sb.append(" ").append(key).append(operator).append(escStr).append(strValue).append(escStr);
			} else {
				sb.append(" ").append(key).append(operator).append(escStr).append(value).append(escStr);
			}
		} else {
			sb.append(" ").append(key).append(" IS NULL ");
		}

		
	}

	private void constructQueryForMQ(MetricQualifier mq, String escStr, String operator, Object value, StringBuilder sb, String key) {
        if (mq == MetricQualifier.DIMENSION_LEVEL) {
            escStr = "'";
			if (value != null) {
				if (value instanceof Boolean) {
					escStr = "'";
					String strValue = getBooleanValue((Boolean) value);
					sb.append(" ").append(DBConstant.DIMENSION_LEVEL_NAME_FIELD).append(operator).append(escStr).append(strValue).append(escStr);
				} else {
					sb.append(" ").append(DBConstant.DIMENSION_LEVEL_NAME_FIELD).append(" ").append(operator).append(escStr).append(value).append(escStr);
				}
			} else {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_NAME_FIELD).append(" IS NULL ");
			}
        } else if (mq == MetricQualifier.CREATED_TIME) {
        	escStr = "'";
			if (value != null) {
				if("Oracle".equals(pService.getConnectionpool().getDatabaseType())) {
					String dateValue = "to_timestamp('"+value+"', 'RRRR-mm-DD HH24.MI.SS.FF')";
					sb.append(" ").append(DBConstant.CREATED_DATE_TIME_FIELD).append(" ").append(operator).append(dateValue);
				}
				else {
					sb.append(" ").append(DBConstant.CREATED_DATE_TIME_FIELD).append(" ").append(operator).append(escStr).append(value).append(escStr);	
				}
				
			} else {
				sb.append(" ").append(DBConstant.CREATED_DATE_TIME_FIELD).append(" IS NULL ");
			}
        } else if (mq == MetricQualifier.UPDATED_TIME) {
        	escStr = "'";
			if (value != null) {
				if("Oracle".equals(pService.getConnectionpool().getDatabaseType())) {
					String dateValue = "to_timestamp('"+value+"', 'RRRR-mm-DD HH24.MI.SS.FF')";
					sb.append(" ").append(DBConstant.UPDATED_DATE_TIME_FIELD).append(" ").append(operator).append(dateValue);
				}
				else {
					sb.append(" ").append(DBConstant.UPDATED_DATE_TIME_FIELD).append(" ").append(operator).append(escStr).append(value).append(escStr);	
				}
			} else {
				sb.append(" ").append(DBConstant.UPDATED_DATE_TIME_FIELD).append(" IS NULL ");
			}
        } else if (mq == MetricQualifier.DIMENSION_LEVEL_NO) {
        	escStr = "";
			if (value != null) {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_FIELD).append(" ").append(operator).append(escStr).append(value).append(escStr);
			} else {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_FIELD).append(" IS NULL ");
			}
        } else if (mq == MetricQualifier.IS_PROCESSED) {
        	escStr = "'";
			if (value != null) {
				if (value instanceof Boolean) {
					escStr = "'";
					String strValue = getBooleanValue((Boolean) value);
					sb.append(" ").append(DBConstant.IS_PROCESSED).append(operator).append(escStr).append(strValue).append(escStr);
				} else {
					sb.append(" ").append(DBConstant.IS_PROCESSED).append(" ").append(operator).append(escStr).append(value).append(escStr);
				}
			} else {
				sb.append(" ").append(DBConstant.IS_PROCESSED).append(" IS NULL ");
			}        }

		
	}

	private String inFilterToQueryString(InFilter inFilter, StringBuilder sb) {
		MetricQualifier mq = inFilter.getMetricQualifier();
		FilterKeyQualifier fq = inFilter.getKeyQualifier();
		List<Object> values = inFilter.getInSet();
		String key = inFilter.getKey();
		String escStr = "";
		String operator = "IN";
		if (mq != null) {
			if (mq == MetricQualifier.DIMENSION_LEVEL) {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_NAME_FIELD).append(" ").append(operator);
			} else if (mq == MetricQualifier.DIMENSION_LEVEL_NO) {
				sb.append(" ").append(DBConstant.DIMENSION_LEVEL_FIELD).append(" ").append(operator);
			} else if (mq == MetricQualifier.CREATED_TIME) {
				sb.append(" ").append(DBConstant.CREATED_DATE_TIME_FIELD).append(" ").append(operator);
			} else if (mq == MetricQualifier.UPDATED_TIME) {
				sb.append(" ").append(DBConstant.UPDATED_DATE_TIME_FIELD).append(" ").append(operator);
			}
		} else if (fq != null) {
			sb.append(" ").append(key).append(" ").append(operator);
		}

		if (values.size() != 0) {
			sb.append(" ( ");
			for (int i = 0; i < values.size(); i++) {
				if (values.get(i) instanceof String || values.get(i) instanceof Boolean) {
					escStr = "'";
				} else {
					escStr = "";
				}

				if (values.get(i) != null) {
					sb.append(escStr).append(values.get(i)).append(escStr);
				} else {
					sb.append(escStr).append("NULL").append(escStr);
				}
				if (i < values.size() - 1) {
					sb.append(" ").append(",").append(" ");
				}

			}
			sb.append(" ) ");
			return sb.toString();
		}

		return "";
	}		
		 
	
	private String getBooleanValue(Boolean value) {
		if (value) {
			return "Y";
		}
		return "N";
	}

}
