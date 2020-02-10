package com.tibco.be.ws.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.ws.decisiontable.constraint.ColumnFilter;
import com.tibco.be.ws.decisiontable.constraint.DecisionTable;
import com.tibco.be.ws.decisiontable.constraint.DecisionTableAnalyzeAction;
import com.tibco.be.ws.decisiontable.constraint.DecisionTableAnalyzerUtils;
import com.tibco.be.ws.decisiontable.constraint.DecisionTableCoverageAction;
import com.tibco.be.ws.decisiontable.constraint.DecisionTableCreator;
import com.tibco.be.ws.decisiontable.constraint.DecisionTableTestDataCoverageAction;
import com.tibco.be.ws.decisiontable.constraint.TestDataModel;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;


/**
 * Catalog functions for Table Analyzer operations    
 * @author vdhumal
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Decision.TableAnalyzer",
        synopsis = "Functions for Table Analyzer operations.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision.TableAnalyzer", value=true))
        
public class WebStudioDecisionTableAnalyzerFunctions {

     @com.tibco.be.model.functions.BEFunction(
		 name = "analyze",
		 signature = "Object[] analyze(Object tableObj)",
		 params = {
				 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableObj", type = "Table", desc = "Decision table EMF Object."),
				 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleIds", type = "Object[]", desc = "Sub-Set of rules to analyze, if empty the complete Table will be analyzed.")
		 },
		 freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "ProblemEvent", desc = "table Analyze problems."),
		 version = "5.2.0",
		 see = "",
		 mapper = @com.tibco.be.model.functions.BEMapper(),
		 description = "Analyze Decision Table.",
		 cautions = "",
		 fndomain = {ACTION},
		 example = ""
    )	    
    public static Object[] analyze(Object tableObj, Object[] ruleIds) {
 		if (!(tableObj instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
 		Table table = (Table) tableObj;
    	DecisionTableCreator creator = new DecisionTableCreator(table);
		DecisionTable decisionTable = creator.createDecisionTable();
		decisionTable.optimize();
    	DecisionTableAnalyzeAction analyzeAction = new DecisionTableAnalyzeAction(table, decisionTable, ruleIds);
    	analyzeAction.run();
    	List<ProblemEvent> problemsList = analyzeAction.getProblems();
    	Object[] problems = null;
    	if (problemsList != null) {
    		problems = problemsList.toArray(new Object[problemsList.size()]);
    	} else {
    		problems = new Object[0];
    	}
    	
    	return problems;
	}  	

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemType",
        signature = "String getProblemType(Object problemEventObj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "ProblemEvent type."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the type of ProblemEvent.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemType(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
    	String fqnClass = problemEvent.getClass().getName();
    	String[] splits = fqnClass.split("\\.");
    	return splits[splits.length -1];
    } 
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemErrorCode",
        signature = "String getProblemErrorCode(Object problemEventObj)",
        params = {
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem Event Error code."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the problem error code.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemErrorCode(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
    	return problemEvent.getErrorCode();
    }  	

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemErrorMessage",
        signature = "String getProblemErrorMessage(Object problemEventObj)",
        params = {
        		 @com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem Event Error message."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the problem error message.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemErrorMessage(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
    	return problemEvent.getMessage();
    }  	

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemDetails",
        signature = "String getProblemDetails(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem Event details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the problem event details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemDetails(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
    	return problemEvent.getDetails();
    }  	

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemSeverity",
        signature = "int getProblemSeverity(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Problem Event Severity."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the problem event severity.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static int getProblemSeverity(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
    	return problemEvent.getSeverity();
    }  	

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemLocation",
        signature = "int getProblemLocation(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Problem Event Location."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the problem event location.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static int getProblemLocation(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
    	return problemEvent.getLocation();
    }  	

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemColumnName",
        signature = "String getProblemColumnName(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem column name."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the column with the problem.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemColumnName(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;
 		String columnName = null;
 		if (problemEvent instanceof RangeProblemEvent) {
 			RangeProblemEvent rangeProblem = (RangeProblemEvent) problemEvent;
 			columnName = rangeProblem.getGuiltyColumnName();
 		} else {
 			Object data = problemEvent.getData("columnName");
 			if (data != null) {
 				columnName = data.toString();
 			}	
 		} 		
 		return columnName;
    }     

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemRuleId",
        signature = "String getProblemRuleId(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem RuleId."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the ruleId for the ProblemEvent.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemRuleId(Object problemEventObj) {
    	String problemRuleId = null;
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;    	
    	if (problemEvent instanceof OverlappingRangeProblemEvent) {
    		OverlappingRangeProblemEvent overlappingRangeProblemEvent = (OverlappingRangeProblemEvent) problemEvent;
    		problemRuleId = String.valueOf(overlappingRangeProblemEvent.getProblemRuleId());
    	} else if (problemEvent instanceof ConflictingActionsProblemEvent) {
    		ConflictingActionsProblemEvent conflictingActionsProblemEvent = (ConflictingActionsProblemEvent) problemEvent;
    		problemRuleId = String.valueOf(conflictingActionsProblemEvent.getProblemRuleId());
    	} else if (problemEvent instanceof DuplicateRuleProblemEvent) {
    		DuplicateRuleProblemEvent duplicateRuleProblemEvent = (DuplicateRuleProblemEvent) problemEvent;
    		problemRuleId = String.valueOf(duplicateRuleProblemEvent.getDuplicateRuleID());
    	}
    	return problemRuleId;
    }  	
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getOtherRuleId",
        signature = "String getOtherRuleId(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Other RuleId."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the other ruleId for the ProblemEvent.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getOtherRuleId(Object problemEventObj) {
 		if (!(problemEventObj instanceof ProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ProblemEvent.class.getName()));
		}
 		ProblemEvent problemEvent = (ProblemEvent) problemEventObj;    	
    	String problemRuleId = null;
    	Object data = problemEvent.getData("otherRuleId");
    	if (data != null) {
    		problemRuleId = data.toString();
    	}
 		return problemRuleId;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemRangeType",
        signature = "String getProblemRangeType(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem range type."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the type of the Range problem.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemRangeType(Object problemEventObj) {
 		if (!(problemEventObj instanceof RangeProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", RangeProblemEvent.class.getName()));
		}
		RangeProblemEvent rangeProblemEvent = (RangeProblemEvent) problemEventObj;
		return rangeProblemEvent.getRangeTypeProblem().toString();    			
    }     

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemRangeMinValue",
        signature = "String getProblemRangeMinValue(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem range min value."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the min value of the Range.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemRangeMinValue(Object problemEventObj) {
 		if (!(problemEventObj instanceof RangeProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", RangeProblemEvent.class.getName()));
		}
   		RangeProblemEvent rangeProblemEvent = (RangeProblemEvent) problemEventObj;
   		
   		String minValue = "";
   		Object value=rangeProblemEvent.getMin();
		if (value instanceof Integer) {
			minValue = value.toString();
		} else if (value instanceof Long) {
			minValue = value.toString();
		} else if (value instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			minValue = format.format(value);
		}  	  	
    	return minValue;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemRangeMaxValue",
        signature = "String getProblemRangeMaxValue(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Problem range max value."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the max value of the Range.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getProblemRangeMaxValue(Object problemEventObj) {
 		if (!(problemEventObj instanceof RangeProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", RangeProblemEvent.class.getName()));
		}
   		RangeProblemEvent rangeProblemEvent = (RangeProblemEvent) problemEventObj;

   		String maxValue = "";
   		Object value=rangeProblemEvent.getMax();
		if (value instanceof Integer) {
			maxValue = value.toString();
		} else if (value instanceof Long) {
			maxValue = value.toString();
		} else if (value instanceof Date) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			maxValue = format.format(value);
		}  	  			   	
    	return maxValue;    	
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getProblemRangeChars",
        signature = "String[] getProblemRangeChars(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Range chars."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Range Chars.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String[] getProblemRangeChars(Object problemEventObj) {
 		if (!(problemEventObj instanceof RangeProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", RangeProblemEvent.class.getName()));
		}
   		RangeProblemEvent rangeProblemEvent = (RangeProblemEvent) problemEventObj;
   		String[] rangeChars = new String[2];
   		Object data = rangeProblemEvent.getData("rangeChar1");
   		if (data != null) {
   			rangeChars[0] = data.toString();
   		}
   		data = rangeProblemEvent.getData("rangeChar2");
   		if (data != null) {
   			rangeChars[1] = data.toString();
   		}
   		
   		return rangeChars;    	
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getMissingEqualsCriterion",
        signature = "String getMissingEqualsCriterion(Object problemEventObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "problemEventObj", type = "ProblemEvent", desc = "Problem.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Missing equals criteria."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the missing equals criteria.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getMissingEqualsCriterion(Object problemEventObj) {
 		if (!(problemEventObj instanceof MissingEqualsCriterionProblemEvent)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", MissingEqualsCriterionProblemEvent.class.getName()));
		}
    	MissingEqualsCriterionProblemEvent missingEqualsCriterionProblemEvent = (MissingEqualsCriterionProblemEvent) problemEventObj;
    	Object missingEqualsCriterion=	missingEqualsCriterionProblemEvent.getMissingEqualsCriterion(); 
       	return missingEqualsCriterion.toString();    	
    } 
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getAllColumnFilters",
        signature = "Object[] getAllColumnFilters(Object tableObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableObj", type = "Table", desc = "Decision Table model.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "All Column Filters."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the column filters.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static Object[] getAllColumnFilters(Object tableObj) {
 		if (!(tableObj instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
 		Table table = (Table) tableObj;
    	DecisionTableCreator creator = new DecisionTableCreator(table);
		DecisionTable decisionTable = creator.createDecisionTable();
		decisionTable.optimize();
		Set<String> columns = decisionTable.getColumns();
		List<ColumnFilter> columnFiltersList = new ArrayList<ColumnFilter>();
		for (String column : columns) {
			ColumnFilter columnFilter = DecisionTableAnalyzerUtils.getColumnFilter(column, decisionTable);
			if (columnFilter != null)
				columnFiltersList.add(columnFilter);
		}
 		
 		return columnFiltersList.toArray(new ColumnFilter[columnFiltersList.size()]);    	
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnName",
        signature = "String getColumnName(Object columnFilterObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnFilterObj", type = "ColumnFilter", desc = "Column Filter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Column name."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns column name.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String getColumnName(Object columnFilterObj) {
 		if (!(columnFilterObj instanceof ColumnFilter)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ColumnFilter.class.getName()));
		}
 		ColumnFilter columnFilter = (ColumnFilter) columnFilterObj;
 		return columnFilter.getColumnName();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "isRangeColumnFilter",
        signature = "boolean isRangeColumnFilter(Object columnFilterObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnFilterObj", type = "ColumnFilter", desc = "Column Filter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Whether ColumnFilter is range."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if column filter is range, false other-wise.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static boolean isRangeColumnFilter(Object columnFilterObj) {
 		if (!(columnFilterObj instanceof ColumnFilter)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ColumnFilter.class.getName()));
		}
 		ColumnFilter columnFilter = (ColumnFilter) columnFilterObj;
 		return columnFilter.isRangeFilter();
    }    

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnRangeFilter",
        signature = "String[] getColumnRangeFilter(Object columnFilterObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnFilterObj", type = "ColumnFilter", desc = "Column Filter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Min Max values of Range."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns Min Max values of Range.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static String[] getColumnRangeFilter(Object columnFilterObj) {
 		if (!(columnFilterObj instanceof ColumnFilter)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ColumnFilter.class.getName()));
		}
 		ColumnFilter columnFilter = (ColumnFilter) columnFilterObj;
 		Object[] range=columnFilter.getRange();
 		String [] ranges=new String[]{"",""};
 		if(null!=range && range.length>0){
 			if(range[0] instanceof Integer && range[1] instanceof Integer){
 				ranges[0]=range[0].toString();
 				ranges[1]=range[1].toString();
 			}else if(range[0] instanceof Long && range[1] instanceof Long){
 				ranges[0]=range[0].toString();
 				ranges[1]=range[1].toString();
 			}else if(range[0] instanceof Double && range[1] instanceof Double){
 				ranges[0]=range[0].toString();
 				ranges[1]=range[1].toString();
 			}else if(range[0] instanceof Date && range[1] instanceof Date){
 				DateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
 				ranges[0]=format.format(range[0]);
 				ranges[1]=format.format(range[1]);
 			}
 		}
 		return ranges; 		 		
    }    

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnEqualsFilter",
        signature = "Object[] getColumnEqualsFilter(Object columnFilterObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnFilterObj", type = "ColumnFilter", desc = "Column Filter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Array of items."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the array of items in the equals filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
	public static Object[] getColumnEqualsFilter(Object columnFilterObj) {
 		if (!(columnFilterObj instanceof ColumnFilter)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", ColumnFilter.class.getName()));
		}
 		ColumnFilter columnFilter = (ColumnFilter) columnFilterObj;
 		return columnFilter.getItems(); 		 		
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "computeCoverage",
        signature = "String[] computeCoverage(Object tableObj, Object[] columnFilterObjs)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableObj", type = "Table", desc = "Decision table EMF Object."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnFilterObjs", type = "ColumnFilter[]", desc = "Array of ColumnFilters.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Array of Decision table Rule Ids."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compute the  Decision Table Rules Coverage based on the filters.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
    public static String[] computeCoverage(Object tableObj, Object[] columnFilterObjs) {
 		if (!(tableObj instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
 		if (columnFilterObjs.length > 0 && !(columnFilterObjs[0] instanceof ColumnFilter)) {
			throw new IllegalArgumentException(String.format("Argument array elements should be of type [%s]", ColumnFilter.class.getName()));
		}
 		 		
 		Table table = (Table) tableObj;
 		ColumnFilter[] columnFilters = Arrays.copyOf(columnFilterObjs, columnFilterObjs.length, ColumnFilter[].class);
 		
 		DecisionTableCreator creator = new DecisionTableCreator(table);
		DecisionTable decisionTable = creator.createDecisionTable();
		decisionTable.optimize();
		
		DecisionTableCoverageAction showCoverageAction = new DecisionTableCoverageAction(table, decisionTable, columnFilters);
		showCoverageAction.run();
    	List<String> ruleIdsList = showCoverageAction.getCoveredTableRules();
    	String[] coveredRules = null;
    	if (ruleIdsList != null) {
    		coveredRules = ruleIdsList.toArray(new String[ruleIdsList.size()]);
    	} else {
    		coveredRules = new String[0];
    	}
    	
    	return coveredRules;
	}
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getTestDataEntity",
            signature = "String getTestDataEntity(String scsRootURL, String projectName, String testDataFile)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsRootURL", type = "String", desc = "SCS Root URL."),	
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "testDataFile", type = "String", desc = "TestData File.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "TestData entity path."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get TestData entity path.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getTestDataEntity(String scsRootURL, String projectName, String testDataFile) {
    	String entityPath = null;
    	try {
    		DecisionTableAnalyzerUtils.loadProjectIndex(scsRootURL, projectName, null);
    		String testDataFileLocation = scsRootURL + File.separator + projectName + testDataFile;
    		entityPath = DecisionTableAnalyzerUtils.getEntityInfo(testDataFileLocation);
    	} catch (Exception ex) {
    		throw new RuntimeException("Error getting Test Data entity.", ex);
    	}
    	return entityPath;
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getTestDataModel",
            signature = "Object getTestDataModel(String scsRootURL, String projectName, String entityPath, String testDataFile)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsRootURL", type = "String", desc = "SCS Root URL."),	
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "Concept/Event path."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "testDataFile", type = "String", desc = "TestData File.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "TestData model from the TestData file for the provided entity."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get TestData model from the TestData file for the provided entity.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object getTestDataModel(String scsRootURL, String projectName, String entityPath, String testDataFile) {
    	TestDataModel testDataModel = null;
    	try {
    		DecisionTableAnalyzerUtils.loadProjectIndex(scsRootURL, projectName, null);
    		testDataModel = DecisionTableAnalyzerUtils.getTestDataModel(scsRootURL, projectName, entityPath, testDataFile);
    	} catch (Exception ex) {
    		throw new RuntimeException("Error getting Test Data model.", ex);
    	}
    	return testDataModel;
    }	
 	
    @com.tibco.be.model.functions.BEFunction(
            name = "computeTestDataCoverage",
            signature = "Object computeTestDataCoverage(String scsRootURL, Object tableObj, String entityPath, Object testDataModelObj)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsRootURL", type = "String", desc = "SCS Root URL."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableObj", type = "Table", desc = "Decision table EMF Object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "Concept/Event path."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "testDataModelObj", type = "Object", desc = "TestData model.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Map of TestData record -> Rule Ids"),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Compute the TestData Coverage for the decision table.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	    
    public static Object computeTestDataCoverage(String scsRootURL, Object tableObj, String entityPath, Object testDataModelObj) {
 		if (!(tableObj instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
 		if (!(testDataModelObj instanceof TestDataModel)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TestDataModel.class.getName()));
		}
 		 		
 		Table table = (Table) tableObj;
 		TestDataModel testDataModel = (TestDataModel) testDataModelObj;
 		
 		DecisionTableCreator creator = new DecisionTableCreator(table);
		DecisionTable decisionTable = creator.createDecisionTable();
		decisionTable.optimize();
		
		DecisionTableTestDataCoverageAction showCoverageAction = new DecisionTableTestDataCoverageAction(scsRootURL, table, decisionTable, entityPath, testDataModel);
		showCoverageAction.run();
		Map<Integer, List<String>> testDataCoverageResult = showCoverageAction.getTestDataCoverageResult();    	
    	return testDataCoverageResult;
	}    
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getTestDataColumns",
            signature = "String[] getTestDataColumns(Object testDataModelObj)",
            params = {
           		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "testDataModelObj", type = "Object", desc = "TestData model.")	
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Array of Column names."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get TestData model colums.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String[] getTestDataColumns(Object testDataModelObj) {
 		if (!(testDataModelObj instanceof TestDataModel)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TestDataModel.class.getName()));
		}
 		TestDataModel testDataModel = (TestDataModel) testDataModelObj;
 		List<String> columnNames = testDataModel.getTableColumnNames();
 		return columnNames.toArray(new String[columnNames.size()]);
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getTestDataRecordCount",
            signature = "int getTestDataRecordCount(Object testDataModelObj)",
            params = {
           		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "testDataModelObj", type = "Object", desc = "TestData model.")	
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "No of records in the TestData."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the no of records in the TestData.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static int getTestDataRecordCount(Object testDataModelObj) {
 		if (!(testDataModelObj instanceof TestDataModel)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TestDataModel.class.getName()));
		}
 		TestDataModel testDataModel = (TestDataModel) testDataModelObj;
 		return testDataModel.getTestData().size();
    }    

    @com.tibco.be.model.functions.BEFunction(
            name = "getTestDataRecord",
            signature = "String[] getTestDataRecord(Object testDataModelObj, int index)",
            params = {
           		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "testDataModelObj", type = "Object", desc = "TestData model.")	
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "TestData record at the provided index."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get TestData record at the provided index.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String[] getTestDataRecord(Object testDataModelObj, int index) {
 		if (!(testDataModelObj instanceof TestDataModel)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", TestDataModel.class.getName()));
		}
 		TestDataModel testDataModel = (TestDataModel) testDataModelObj;
 		List<String> record = testDataModel.getTestData().get(index);
 		return record.toArray(new String[record.size()]);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "createColumnFilter",
        signature = "ColumnFilter createColumnFilter(String columnName, boolean isRangeFilter, String[] items, int[] range)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnName", type = "String", desc = "Column Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isRangeFilter", type = "boolean", desc = "Is Range."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "items", type = "String[]", desc = "Equals Filter Items."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "range", type = "Object[]", desc = "Min-Max values.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "ColumnFilter", desc = "Column Filter"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates the Column Filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )	    
    public static Object createColumnFilter(String columnName, boolean isRangeFilter, String[] items, Object[] range) {
    	ColumnFilter columnFilter = null;
    	if (columnName != null) {
    		columnFilter = new ColumnFilter(columnName);
    		if (isRangeFilter) {
    			columnFilter.setRange(range[0], range[1]);
    		} else {
    			for (String item : items) {
    				columnFilter.addItem(item);
    			}	
    		}
    	}    	
    	return columnFilter;
	}
    
}
