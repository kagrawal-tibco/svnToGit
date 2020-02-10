/**
 * 
 */
package com.tibco.be.functions.decisiontable;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl;
import com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl;

/**
 * Utility functions to get details around Decision Table
 * 
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Studio",
        category = "DecisionTable",
        synopsis = "Utility functions to get details around Decision Table")
public class DecisionTableHelper {
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getRuleCount",
        signature = "int getRuleCount(String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Decision Table.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Rule count of the Decision Table"),
        version = "5.5.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns row count of the Decision Table.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static int getRuleCount(String uri) {
		int ruleCount = 0;
		
		DecisionTableElement decisionTable = getDecisionTable(uri);				
		if (decisionTable != null) {
			TableImpl table = (TableImpl) ((decisionTable instanceof SharedDecisionTableElementImpl) ? ((SharedDecisionTableElementImpl)decisionTable).getSharedImplementation() : ((DecisionTableElementImpl)decisionTable).getImplementation());
			if (table != null) {
				ruleCount = table.getDecisionTable().getRule().size();
			}
		} else {
			throw new IllegalArgumentException("Lookup for decision table [" + uri + "] failed.");
		}
		return ruleCount;
	}

	/**
	 * Look up decision table from the Index
	 * 
	 * @param uri
	 * @return
	 */
	private static DecisionTableElement getDecisionTable(String uri) {
		DecisionTableElement decisionTable = null;
		
		String projectName = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProject().getName();
		
		DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
        if (index != null) {        	
        	for (DecisionTableElement dtElement : index.getDecisionTableElements()) {
        		String path  = dtElement.getFolder() + dtElement.getName();
        		if (path.equals(uri)) {
        			decisionTable = dtElement;
        			break;
        		}
        	}
        } else {
			throw new IllegalArgumentException("Project [" + projectName + "] index not found.");
		}
        
        return decisionTable;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getColumnCount",
        signature = "int getColumnCount(String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Decision Table.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Column count of the Decision Table"),
        version = "5.5.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns column count of the Decision Table.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static int getColumnCount(String uri) {
		int columnCount = 0;
		
		DecisionTableElement decisionTable = getDecisionTable(uri);				
		if (decisionTable != null) {
			TableImpl table = (TableImpl) ((decisionTable instanceof SharedDecisionTableElementImpl) ? ((SharedDecisionTableElementImpl)decisionTable).getSharedImplementation() : ((DecisionTableElementImpl)decisionTable).getImplementation());
			if (table != null) {
				columnCount = table.getDecisionTable().getColumns().getColumn().size();
				
			}
		} else {
			throw new IllegalArgumentException("Lookup for decision table [" + uri + "] failed.");
		}
		return columnCount;
	}
}
