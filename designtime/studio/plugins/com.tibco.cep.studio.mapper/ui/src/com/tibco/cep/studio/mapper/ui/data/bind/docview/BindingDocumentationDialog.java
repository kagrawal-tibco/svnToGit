package com.tibco.cep.studio.mapper.ui.data.bind.docview;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.DataTypeTreeNode;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorResources;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;

/**
 * The floating dialog that shows documentation for bindings.
 */
public class BindingDocumentationDialog extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private BindingDocumentationWindow mWindow;
   private UIAgent uiAgent;

   public BindingDocumentationDialog(BindingEditor editor, UIAgent uiAgent) {
      super((Frame) SwingUtilities.getWindowAncestor(editor));
      this.uiAgent = uiAgent;
      mWindow = new BindingDocumentationWindow(uiAgent);
      setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      setTitle(BindingEditorResources.TYPE_DOCUMENTATION);
      getContentPane().add(mWindow);
      pack();
      Dimension d = PreferenceUtils.readDimension(uiAgent,
                                                  "binding.documentation.lastWindowSize",
                                                  new Dimension(300, 100), // min size
                                                  Toolkit.getDefaultToolkit().getScreenSize(), // max size.
                                                  new Dimension(400, 300)  // default size.
      );
      setSize(d);
      Point loc = PreferenceUtils.readLocation(uiAgent,
                                               "binding.documentation.lastWindowLocation",
                                               null,
                                               d // size used.
      );
      if (loc != null) {
         setLocation(loc);
      }
      else {
         setLocationRelativeTo(mWindow);
      }
      setModal(false);
      setVisible(true);
   }

   public void dispose() {
      savePreferences();
      super.dispose();
   }

   private void savePreferences() {
      PreferenceUtils.writeDimension(uiAgent, "binding.documentation.lastWindowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, "binding.documentation.lastWindowLocation", getLocation());
   }

   public void clear() {
      mWindow.clear();
   }

   public void set(BindingNode bn) {
      mWindow.set(bn);
   }

   public void set(DataTypeTreeNode bn) {
      mWindow.set(bn);
   }
}
