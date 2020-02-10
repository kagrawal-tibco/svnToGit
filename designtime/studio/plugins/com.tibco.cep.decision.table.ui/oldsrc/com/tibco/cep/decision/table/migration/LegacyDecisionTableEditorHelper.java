/**
 * 
 */
package com.tibco.cep.decision.table.migration;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.DefaultDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.RuleIdGenerator;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;

/**
 * @author aathalye
 *
 */
public class LegacyDecisionTableEditorHelper {
	
	
	/**
	 * The {@link Table} object to work with
	 */
	private Table tableEModel;
	
	/**
	 * The {@link TableRuleSet} decision table object to work with
	 */
	private TableRuleSet exceptionTable;
	
	/**
	 * The {@link TableRuleSet} exception table object to work with
	 */
	private TableRuleSet decisionTable;
	
	/**
	 * The {@link RuleFunction} resource to create the table for
	 */
	private RuleFunction templateResource;
	
	/**
	 * The name of the table to create
	 */
	private String tableName;
	
	private String folderPath;
	
	/**
	 * 
	 * @param projectName
	 * @param tableName
	 * @param templateResource
	 * @param folderPath
	 */
	public LegacyDecisionTableEditorHelper(final String projectName,
			                               final String tableName,
			                               final RuleFunction templateResource,
			                               final String folderPath) {
		this.tableName = tableName;
		this.templateResource = templateResource;
		this.folderPath = folderPath;
	}
	
	/**
	 * Build model pre-requisites for the decision table editor
	 * @return
	 */
	public Table buildModelDesiderata() {
		tableEModel = DtmodelFactory.eINSTANCE.createTable();
		decisionTable = DtmodelFactory.eINSTANCE.createTableRuleSet();
		tableEModel.setDecisionTable(decisionTable);
		exceptionTable = DtmodelFactory.eINSTANCE.createTableRuleSet();
		tableEModel.setExceptionTable(exceptionTable);
		if (templateResource instanceof RuleFunction) {
			tableEModel.setName(tableName);
			String folder = templateResource.getFolder();
			String templateResourceName = templateResource.getName();
			String templatePath = folder + templateResourceName;
			tableEModel.setImplements(templatePath);
			folderPath = (folderPath == null || folderPath.length() == 0) ? "/" : "/" + folderPath + "/";
			tableEModel.setFolder(folderPath);
			tableEModel.getArgument().addAll(templateResource.getArguments().getArgument());
		}
		return tableEModel;
	}
	
	
	
	/**
	 * <p>
	 * Creates a new {@link DefaultDecisionTableEditorInput} and sets the rule id generator,
	 * and column id generators for it.
	 * </p>
	 * @return the {@link DefaultDecisionTableEditorInput} instance required by the {@link DecisionTableEditor}
	 */
	public DefaultDecisionTableEditorInput buildEditorInput(IFile file) {
		DefaultDecisionTableEditorInput editorInput = new DefaultDecisionTableEditorInput(file, templateResource);
		DecisionTableColumnIdGenerator columnIdGenerator = new DecisionTableColumnIdGenerator();
		columnIdGenerator.initializeCounter(0, 0);
		editorInput.setColumnIdGenerator(columnIdGenerator);
		// set Rule id generator
		RuleIdGenerator ruleIdGenerator = new RuleIdGenerator();
		ruleIdGenerator.intializeIdCounter(0, 0);
		editorInput.setRuleIdGenerator(ruleIdGenerator);
		return editorInput;
	}
}
