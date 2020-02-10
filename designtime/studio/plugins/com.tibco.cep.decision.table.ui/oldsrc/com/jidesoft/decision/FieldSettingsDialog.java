/*
 * @(#)FieldSettingsDialog.java 8/15/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.decision;

import static com.tibco.cep.decision.table.utils.DecisionTableUtil.fieldSettingsDialog;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.swing.JComponent;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;

class FieldSettingsDialog extends StandardDialog {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4754813492498852313L;

	public static int RESULT_HIDE = 100;

    private FieldSettingsPanel _fieldSettingsPanel;
    
    private DecisionTablePane _decisionTablePane;
    
    /**
     * The associated field box
     */
    private DecisionFieldBox _decisionFieldBox;

    public FieldSettingsDialog(Frame owner, 
    		                   DecisionTablePane decisionTablePane, 
    		                   DecisionFieldBox decisionFieldBox,
    		                   String title) throws HeadlessException {
        super(owner, title);
        _decisionTablePane = decisionTablePane;
        _decisionFieldBox = decisionFieldBox;
        fieldSettingsDialog = this;
    }

    public FieldSettingsDialog(Dialog owner, 
    		                   DecisionTablePane decisionTablePane, 
    		                   DecisionFieldBox decisionFieldBox,
    		                   String title) throws HeadlessException {
        super(owner, title);
        _decisionTablePane = decisionTablePane;
        _decisionFieldBox = decisionFieldBox;
        fieldSettingsDialog = this;
    }

    @Override
    public JComponent createBannerPanel() {
        return null;
    }

    @Override
    public JComponent createContentPanel() {
        _fieldSettingsPanel = _decisionTablePane.createFieldSettingsPanel(_decisionFieldBox);
        setInitFocusedComponent(_fieldSettingsPanel.getInitFocusedComponent());
        return _fieldSettingsPanel;
    }

    @Override
    public ButtonPanel createButtonPanel() {
        return _decisionTablePane.createFieldSettingsDialogButtonPanel(this);
    }

    public FieldSettingsPanel getFieldSettingsPanel() {
        return _fieldSettingsPanel;
    }
}