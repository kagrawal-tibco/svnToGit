package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.objectrepo.object.ObjectProvider;

/**
 * The floating dialog that does searches in bindings.
 */
public class BindingFindDialog extends GenericFindDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public BindingFindDialog(BindingEditor editor, UIAgent uiAgent, ActionListener disposeListener) {
      super(SwingUtilities.getWindowAncestor(editor), uiAgent, new BindingFindWindow(editor), disposeListener);
   }

   public BindingFindDialog(BindingEditor editor, UIAgent uiAgent, ActionListener disposeListener, ObjectProvider op) {
      super(SwingUtilities.getWindowAncestor(editor), uiAgent, new BindingFindWindow(null), disposeListener, op);
   }
}
