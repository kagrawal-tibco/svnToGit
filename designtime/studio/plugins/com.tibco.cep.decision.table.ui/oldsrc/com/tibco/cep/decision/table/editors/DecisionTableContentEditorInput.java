/**
 * 
 */
package com.tibco.cep.decision.table.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

import com.tibco.cep.decision.table.editors.dnd.DefaultDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.LegacyDecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.RuleIdGenerator;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

/**
 * Wrapper {@link IEditorInput} which works with "content" instead of
 * {@link IFile}
 * @author aathalye
 *
 */
@SuppressWarnings({"rawtypes"})
public class DecisionTableContentEditorInput implements IDecisionTableEditorInput {
	
	private IStorageEditorInput storageEditorInput;
	
	private Table tableEModel;
	
	private RuleFunction virtualRuleFunction;
	
	private DecisionTableColumnIdGenerator columnIdGenerator;
	
	private RuleIdGenerator ruleIdGenerator;

    private IDecisionTableDnDDataConverter decisionDnDDataConverter;	
	
	/**
	 * Wrapper editor input.
	 * @param storageEditorInput
	 */
	public DecisionTableContentEditorInput(IStorageEditorInput storageEditorInput) {
		this.storageEditorInput = storageEditorInput;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.FileEditorInput#getStorage()
	 */
	@Override
	public IStorage getStorage() {
		try {
			return storageEditorInput.getStorage();
		} catch (CoreException e) {
			DecisionTableUIPlugin.log(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#getName()
	 */
	@Override
	public String getName() {
		return storageEditorInput.getName();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#getProjectName()
	 */
	@Override
	public String getProjectName() {
		//Hate to add checks like these
		if (storageEditorInput instanceof JarEntryEditorInput) {
			return ((JarEntryEditorInput)storageEditorInput).getProjectName();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#getResourcePath()
	 */
	@Override
	public String getResourcePath() {
		//Not required.
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#loadModel()
	 */
	@Override
	public void loadModel() {
		try {
			LegacyDecisionTableUtil.loadModel(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return true;
	}
	
	

	@Override
	public void refresh(IProgressMonitor monitor) throws CoreException {
		
	}

	/**
	 * @param storageEditorInput the storageEditorInput to set
	 */
	public final void setStorageEditorInput(IStorageEditorInput storageEditorInput) {
		this.storageEditorInput = storageEditorInput;
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

	/**
	 * @param columnIdGenerator the columnIdGenerator to set
	 */
	public final void setColumnIdGenerator(DecisionTableColumnIdGenerator columnIdGenerator) {
		this.columnIdGenerator = columnIdGenerator;
	}

	/**
	 * @param ruleIdGenerator the ruleIdGenerator to set
	 */
	public final void setRuleIdGenerator(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}

	/**
	 * @return the tableEModel
	 */
	public final Table getTableEModel() {
		return tableEModel;
	}

	/**
	 * @return the virtualRuleFunction
	 */
	public final RuleFunction getVirtualRuleFunction() {
		return virtualRuleFunction;
	}

	/**
	 * @return the columnIdGenerator
	 */
	public final DecisionTableColumnIdGenerator getColumnIdGenerator() {
		return columnIdGenerator;
	}

	/**
	 * @return the ruleIdGenerator
	 */
	public final RuleIdGenerator getRuleIdGenerator() {
		return ruleIdGenerator;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	@Override
	public boolean exists() {
		return storageEditorInput.exists();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return storageEditorInput.getImageDescriptor();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	@Override
	public IPersistableElement getPersistable() {
		return storageEditorInput.getPersistable();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return storageEditorInput.getToolTipText();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class adapter) {
		return storageEditorInput.getAdapter(adapter);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.IDecisionTableEditorInput#saveModel(com.tibco.cep.decisionproject.ontology.RuleFunction, com.tibco.cep.decision.table.model.dtmodel.Table)
	 */
	@Override
	public IStatus saveModel(RuleFunction virtualRuleFunction, Table tableEModel) {
		throw new UnsupportedOperationException("Save operation not supported on a read-only editor input");
	}

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