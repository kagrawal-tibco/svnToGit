package com.tibco.cep.decision.table.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.decision.table.editors.dnd.DefaultDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.RuleIdGenerator;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;

/**
 * This is an abstract class for any Decision Table Editor input.
 * Subclasses should use this to fetch Decision Table data from
 * various file sources, or to persist data to various file sources.
 * <p>
 * It is important to note that the source should be an {@link IFile}
 * </p>
 * @author TIBCO BusinessEvents
 *
 */
public abstract class AbstractDecisionTableEditorInput extends FileEditorInput implements IDecisionTableEditorInput {

	protected Table tableEModel;
	
	protected RuleFunction virtualRuleFunction;
	
	protected DecisionTableColumnIdGenerator columnIdGenerator;
	
	protected RuleIdGenerator ruleIdGenerator;

    private IDecisionTableDnDDataConverter decisionDnDDataConverter;	

	/**
	 * @param file
	 */
	public AbstractDecisionTableEditorInput(IFile file) {
		super(file);
	}
	
	public DecisionTableColumnIdGenerator getColumnIdGenerator() {
		return columnIdGenerator;
	}

	/**
	 * @param columnIdGenerator
	 */
	public void setColumnIdGenerator(DecisionTableColumnIdGenerator columnIdGenerator) {
		this.columnIdGenerator = columnIdGenerator;
	}

	public RuleIdGenerator getRuleIdGenerator() {
		return ruleIdGenerator;
	}

	/**
	 * @param ruleIdGenerator
	 */
	public void setRuleIdGenerator(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}
	
	public Table getTableEModel() {
		return tableEModel;
	}
	
	public RuleFunction getVirtualRuleFunction() {
		return virtualRuleFunction;
	}
	

	/**
	 * @param tableEModel the tableEModel to set
	 */
	public final void setTableEModel(Table tableEModel) {
		this.tableEModel = tableEModel;
	}

	/**
	 * @param virtualRuleFunction the virtualRuleFunction to set
	 */
	public final void setVirtualRuleFunction(RuleFunction virtualRuleFunction) {
		this.virtualRuleFunction = virtualRuleFunction;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return false;
	}
	
	
	
	@Override
	public void refresh(IProgressMonitor monitor) throws CoreException {
		getFile().refreshLocal(IFile.DEPTH_ONE, monitor);
		
	}

	/**
	 * Saves a {@link RuleFunction} and {@link Table} to appropriate
	 * data store. 
	 * <p> 
	 * Subclasses should override this method to save in-memory EMF models
	 * for DTable and its implemented Virtual Rule Function to persistent store
	 * of their choice.
	 * </p> 
	 * <p>
	 * <b><i>Only in special case would one need to save rule function along with the DTable.
	 * Usually the Rule function changes would be handled via its own editor.
	 * </i></b>
	 * @param virtualRuleFunction -> The {@link RuleFunction} implemented by this Decision Table.
	 * @param tableEModel -> The {@link Table} EMF model of this Decision Table.
	 * @return an {@link IStatus} indicating result of the save operation.
	 */
	public abstract IStatus saveModel(RuleFunction virtualRuleFunction, Table tableEModel);


    /**
     * @see com.tibco.cep.decision.table.editors.IDecisionTableEditorInput#getDnDDataConverter()
     * 
     * @return
     * @author Sid 26/07/2011.
     */
    public IDecisionTableDnDDataConverter getDnDDataConverter() {
        if (decisionDnDDataConverter == null) {
            decisionDnDDataConverter = new DefaultDecisionTableDnDDataConverter();
        }

        return decisionDnDDataConverter; 
    }

}
