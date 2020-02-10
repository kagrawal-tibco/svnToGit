/*
 * @(#)FieldSettingsPanel.java 8/15/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.decision;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.jidesoft.decision.validation.FieldValidationContext;
import com.jidesoft.decision.validation.FieldValidationContext.FieldValidationContextType;
import com.jidesoft.decision.validation.FieldValidationResult;
import com.jidesoft.decision.validation.IFieldValidationListener;
import com.jidesoft.decision.validation.impl.DefaultFieldNameValidationListener;
import com.jidesoft.swing.SelectAllUtils;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.RenameCommand;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnAliasCommandListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnRenameCommandListener;
import com.tibco.cep.decision.table.command.memento.ColumnAliasStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.language.ArrayParseUtil;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * The field settings panel which is used by the field settings dialog when user
 * right clicks on the field box to customize the settings.
 */
@SuppressWarnings("serial")
public class FieldSettingsPanel extends JPanel {
	
	/**
	 * The column name if renamed
	 */
	private JTextField _nameField;

	/**
	 * A user friendly name for the column
	 */
	private JTextField _aliasField;

	/**
	 * Field id
	 */
	private JTextField _idField;

	private DecisionField _field;

	private DecisionTablePane _decisionTablePane;
	
	/**
	 * Trigger for the validation chain.
	 */
	private IFieldValidationListener<DecisionField> initialFieldValidationListener = new DefaultFieldNameValidationListener();

	private boolean showColumnAlias = DecisionTableUIPlugin.getDefault()
			.getPreferenceStore().getBoolean(PreferenceConstants.SHOW_COLUMN_ALIAS);

	public FieldSettingsPanel(DecisionTablePane decisionTablePane, DecisionFieldBox decisionFieldBox) {
		_decisionTablePane = decisionTablePane;
		this._field = decisionFieldBox.getField();
		installComponents();
		installListeners();
	}
	
	
	/**
	 * Installs all the components on the field settings panel. Subclass can
	 * override this methods to add more components or customize existing
	 * components.
	 */
	protected void installComponents() {
		JLabel nameLabel = new JLabel(_decisionTablePane.getResourceString("FieldSettings.name"));
		nameLabel.setDisplayedMnemonic(_decisionTablePane.getResourceString(
				"FieldSettings.name.mnemonic").charAt(0));
		_nameField = new JTextField(20);
		//Check if it is array access
		if (ArrayParseUtil.isArrayAccessColumn(_field.getName())) {
			_nameField.setEnabled(false);
			_nameField.setToolTipText(_decisionTablePane.getResourceString("FieldSettings.Name.Tooltip"));
		}
		nameLabel.setLabelFor(_nameField);

		JLabel aliasLabel = new JLabel(_decisionTablePane
				.getResourceString("FieldSettings.alias")
				+ "  ");
		nameLabel.setDisplayedMnemonic(_decisionTablePane.getResourceString(
				"FieldSettings.alias.mnemonic").charAt(0));
		_aliasField = new JTextField(40);
		nameLabel.setLabelFor(_aliasField);

		JLabel idLabel = new JLabel(_decisionTablePane
				.getResourceString("FieldSettings.id")
				+ "      ");
		nameLabel.setDisplayedMnemonic(_decisionTablePane.getResourceString(
				"FieldSettings.id.mnemonic").charAt(0));
		_idField = new JTextField(40);
		_idField.setEditable(false);
		nameLabel.setLabelFor(_idField);

		JPanel namePanel = new JPanel(new BorderLayout(6, 6));
		namePanel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
		namePanel.add(nameLabel, BorderLayout.BEFORE_LINE_BEGINS);
		namePanel.add(_nameField, BorderLayout.CENTER);

		JPanel aliasPanel = new JPanel(new BorderLayout(6, 6));
		aliasPanel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
		aliasPanel.add(aliasLabel, BorderLayout.BEFORE_LINE_BEGINS);
		aliasPanel.add(_aliasField, BorderLayout.CENTER);

		JPanel idPanel = new JPanel(new BorderLayout(6, 6));
		idPanel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
		idPanel.add(idLabel, BorderLayout.BEFORE_LINE_BEGINS);
		idPanel.add(_idField, BorderLayout.CENTER);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(namePanel, BorderLayout.PAGE_START);
		panel.add(aliasPanel, BorderLayout.CENTER);
		panel.add(idPanel, BorderLayout.PAGE_END);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		add(panel);
	}

	protected void installListeners() {
		SelectAllUtils.install(_nameField);
	}

	public DecisionField getField() {
		return _field;
	}

	public void setField(DecisionField field) {
		_field = field;
	}

	/**
	 * Loads the data from the DecisionField.
	 */
	public void loadData() {
		DecisionField field = getField();
		if (field == null) {
			return;
		}
		_nameField.setText(field.getName());
		_aliasField.setText(field.getAlias());
		_idField.setText(field.getId());
	}

	/**
	 * Saves the data to the DecisionField.
	 * <p>
	 * For tooltip see the @see
	 * </p>
	 * @see DecisionFieldBox#propertyChange(java.beans.PropertyChangeEvent)
	 * 
	 */
	public void saveData() {
		DecisionField field = getField();
		if (field == null) {
			return;
		}
		String oldName = field.getName();
		String newName = _nameField.getText();
		String oldAlias = (field.getAlias() == null) ? "" : field.getAlias();
		String newAlias = _aliasField.getText();
		
		Table tableEModel = _decisionTablePane.getDecisionTableEditor().getDecisionTableModelManager().getTabelEModel();

		String project = _decisionTablePane.getDecisionTableEditor().getProject().getName();
		
		if (!newName.equals(oldName)) {
			boolean isValid = validate(newName);
			if (!isValid) {
				return;
			}
		}

		String newTitle = null;
		if (showColumnAlias) {
			newTitle = (newAlias == null || newAlias.length() == 0 ? (newName == null
					|| newName.length() == 0 ? oldName : newName)
					: newAlias);
			field.setTitle(newTitle);
		} else {
			newTitle = (newName == null || newName.length() == 0 ? oldName : newName);
		}
		String projectName = _decisionTablePane.getDecisionTableEditor().getProject().getName();
//		_decisionTablePane.getDecisionDataModel().renameAlias(field, oldAlias, newAlias);
		if (!oldAlias.equals(newAlias)) {
			ICommandCreationListener<RenameCommand<ColumnAliasStateMemento>, ColumnAliasStateMemento> 
			commandCreationListener = new DecisionTableColumnAliasCommandListener(projectName, field, _decisionTablePane, newAlias);
			CommandFacade.getInstance().
				executeColumnAliasChange(project, 
						                 tableEModel,
						                 _decisionTablePane.getDecisionDataModel().getTableType(),
						                 commandCreationListener);
		}
		if (!newName.equals(oldName)) {
			ICommandCreationListener<RenameCommand<ColumnNameStateMemento>, ColumnNameStateMemento> 
				commandCreationListener = new DecisionTableColumnRenameCommandListener(projectName, field, _decisionTablePane, newName);
			CommandFacade.getInstance().
				executeColumnRename(project, 
						            tableEModel,
						            _decisionTablePane.getDecisionDataModel().getTableType(),
						            commandCreationListener);
		}
	}

	public Component getInitFocusedComponent() {
		return _nameField;
	}
	
	private void displayError(final FieldValidationResult fieldValidationResult) {
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(shell, "Field Rename Error", fieldValidationResult.getMessage());
			}
		}, false);
	}
	
	private boolean validate(String changedFieldName) {
		FieldValidationContext<DecisionField> fieldValidationContext = 
			new FieldValidationContext<DecisionField>(getField(), _decisionTablePane, FieldValidationContextType.FIELD_NAME);
		fieldValidationContext.setChangedFieldName(changedFieldName);
		FieldValidationResult fieldValidationResult = initialFieldValidationListener.validate(fieldValidationContext);
		int resultCode = fieldValidationResult.getResultCode();
		boolean isValid = resultCode == FieldValidationResult.SUCCESS_CODE;
		if (!isValid) {
			displayError(fieldValidationResult);
		}
		return isValid;
	}
}