package com.tibco.cep.query.rest.common;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 2/13/14
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestFilterBuilder<T> implements FilterBuilder<T> {

    MultivaluedMap<String, String> queryParameters;
    Filter<T> filter;

    public RestFilterBuilder(MultivaluedMap<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }


    private String buildComparisonfilter(String key, String value) {


        value = value.substring(1, value.length() - 1);

        if(value.startsWith("like_"))
        {
            //todo
        }


        //***Handle Escape Sequence***
        if (value.startsWith("\\")) {
            value = value.replaceFirst("\\\\", "");
        }

        String operator = extractOperator(value);
        // value = extractValue(value);

        if (operator.equals("=")) {
            return key + operator + value;
        }
        return key + /*operator +*/ value;
    }

    public /*String*/void buildFilter(/*MultivaluedMap<String, String> queryParameters*/) {
        StringBuilder filterBuilder = new StringBuilder();

        String leftFilter = null;

        for (Map.Entry mEntry : queryParameters.entrySet()) {
            if (queryParameters.size() == 1) {
                //return buildComparisonfilter((String) mEntry.getKey(), String.valueOf(mEntry.getValue()));
                filter = new RestFilter(buildComparisonfilter((String) mEntry.getKey(), String.valueOf(mEntry.getValue())));
            } else {
                if (leftFilter == null) {
                    leftFilter = buildComparisonfilter((String) mEntry.getKey(), String.valueOf(mEntry.getValue()));
                } else {
                    String rightFilter = buildComparisonfilter((String) mEntry.getKey(), String.valueOf(mEntry.getValue()));
                    leftFilter = convert(leftFilter, " and ", rightFilter);
                }
            }
        }

        //  return leftFilter;
        if (filter == null) {
            filter = (Filter<T>) new RestFilter(leftFilter);
        }
    }

/*
    public String buildLogicalAndFilter(MultivaluedMap<String, String> queryParameters)
    {

      StringBuilder filter = new StringBuilder();
      int filterCount = 0;

      for(Map.Entry mEntry : queryParameters.entrySet())
      {
          String key = (String) mEntry.getKey();
          String value = String.valueOf(mEntry.getValue());
//          value =  value.substring(1, value.length()-1);
//          String operator = extractOperator(value);
//          value = extractValue(value);

          if(queryParameters.size() > 2)
          {
              retur
          }
          else
          {

            String leftFilter = buildComparisonfilter(key, value);
            String rightFilter = buildComparisonfilter(key, value);

            return convert(leftFilter," and ", rightFilter);
          }

//          if(filterCount > 0)
//          {
//              filter.append(" and ");
//              filter.append("(");
//          }
//
//       filter.insert(0, "(");
//       filter.append(mEntry.getKey());
//
//
//
//          //Assuming that the list contains just a single value
//
//       String value = String.valueOf(mEntry.getValue());
//       value =  value.substring(1, value.length()-1);
//
//       value = extractValue(value);
//
//
//       filter.append(value);
//       filter.append(")");
//
//          filterCount++;
      }

      return filter.toString();

    }
*/

    private String extractOperator(String value) {

        String operator = null;

        if (value.startsWith(Prefix.GREATER_THAN_EQUAL)) {
            operator = ">=";
        } else if (value.startsWith(Prefix.LESS_THAN_EQUAL)) {
            operator = "<=";
        } else if (value.startsWith(Prefix.GREATER_THAN)) {
            operator = ">";
        } else if (value.startsWith(Prefix.LESS_THAN)) {
            operator = "<";
        } else {
            operator = "=";
        }

        return operator;
    }


    private String extractValue(String value) {


        if (value.startsWith(Prefix.GREATER_THAN_EQUAL)) {
            value = value.substring(Prefix.GREATER_THAN_EQUAL.length());
        } else if (value.startsWith(Prefix.LESS_THAN_EQUAL)) {
            value = value.substring(Prefix.LESS_THAN_EQUAL.length());
        } else if (value.startsWith(Prefix.GREATER_THAN)) {
            value = value.substring(Prefix.GREATER_THAN.length());
        } else if (value.startsWith(Prefix.LESS_THAN)) {
            value = value.substring(Prefix.LESS_THAN.length());
        } else if (value.startsWith(Prefix.ESCAPE_CHAR)) {
            value = value.substring(Prefix.ESCAPE_CHAR.length());
            value = escapeSeqHelper(value);
        } else {
            return value;
        }

        return value;
    }

    private String escapeSeqHelper(String value) {
        if (value.startsWith(Prefix.GREATER_THAN_EQUAL)) {
            value = value.replace(Prefix.GREATER_THAN_EQUAL, ">=");
        } else if (value.startsWith(Prefix.LESS_THAN_EQUAL)) {
            value = value.replace(Prefix.LESS_THAN_EQUAL, "<=");
        } else if (value.startsWith(Prefix.GREATER_THAN)) {
            value = value.replace(Prefix.GREATER_THAN, ">");
        } else if (value.startsWith(Prefix.LESS_THAN)) {
            value = value.replace(Prefix.LESS_THAN, "<");
        } else {
            value = value.replace(Prefix.EQUALS, "=");
        }

        return value;
    }

    private String convert(String key, String operator, String value) {
        return "(" + key + " and " + value + ")";
    }

    @Override
    public T getFilter() {
        return filter.getFilter();
    }
}
