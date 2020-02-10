package com.tibco.cep.decision.table.editors;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.IEditorInput;

import com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.RuleIdGenerator;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;

/**
 * All {@link IEditorInput} implementations for decision table 
 * should implement this interface.
 * @author aathalye
 *
 */
public interface IDecisionTableEditorInput extends IEditorInput {
	
	/**
	 * The name of the Decision Table.
	 */
	public String getName();
	
	/**
	 * Get the BE resource path.
	 * <p>
	 * e.g : If a DTable with the name "DecisionTable" exists under folder
	 * VirtualRuleFunctionImpls under the BE studio project, this method should return
	 *"/VirtualRuleFunctionImpls/DecisionTable"
	 *</p>
	 * @return
	 */
	public String getResourcePath();
	
	/**
	 * BE Studio Project name.
	 * @return
	 */
	public String getProjectName();
	
	/**
	 * The {@link IStorage} for this editor input.
	 * @return
	 */
	public IStorage getStorage();
	
	/**
	 * Loads {@link RuleFunction} and {@link Table} from persistent store.
	 * <p>
	 * Subclasses should override this method and populate the virtual rule function
	 * and the Decision Table EMF model from the persistent store.
	 * </p>
	 * <p>
	 * The Decision Table Editor will then use the {@link #getVirtualRuleFunction()}
	 * and {@link #getTableEModel()} the loading of the UI model and display it
	 * </p>
	 * <p>
	 * Success of the loading procedure may be verified by checking value of table and/or virtual
	 * rule function with <code>null</code>.
	 * </p> 
	 */
	public abstract void loadModel();
	
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
	 * @param tableEModel the tableEModel to set
	 */
	public void setTableEModel(Table tableEModel);

	/**
	 * @param virtualRuleFunction the virtualRuleFunction to set
	 */
	public void setVirtualRuleFunction(RuleFunction virtualRuleFunction);
	
	/**
	 * @param columnIdGenerator
	 */
	public void setColumnIdGenerator(DecisionTableColumnIdGenerator columnIdGenerator);
	
	/**
	 * @param ruleIdGenerator
	 */
	public void setRuleIdGenerator(RuleIdGenerator ruleIdGenerator);
	
	/**
	 * 
	 * @return
	 */
	public Table getTableEModel();
	
	/**
	 * 
	 * @return
	 */
	public RuleFunction getVirtualRuleFunction();
	
	/**
	 * 
	 * @return
	 */
	public DecisionTableColumnIdGenerator getColumnIdGenerator();
	
	/**
	 * 
	 * @return
	 */
	public RuleIdGenerator getRuleIdGenerator();
	
	/**
	 * If true this editor input should not be used for any modification purposes.
	 * @return
	 */
	public boolean isReadOnly();
	
    /**
     * Gives the decisoin table editor input provider the opportunity to convert
     * dragged and dropped from items in its environment onto the decision
     * table.
     * 
     * @return The decision table drag-n-drop converter or <code>null</code> to
     *         use the default drop converter.
     * @author Sid 26/07/2011.
     */
     public IDecisionTableDnDDataConverter getDnDDataConverter();

	public void refresh(IProgressMonitor monitor) throws CoreException;
	
}
