package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.objectrepo.object.ObjectProvider;

/**
 * The floating dialog that does searches in bindings.
 */
public class GenericFindDialog extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private GenericFindWindow mWindow;
   private UIAgent uiAgent;
   private ActionListener mDisposeListener;

   public GenericFindDialog(Window window, UIAgent uiAgent, FindWindowPlugin plugin, ActionListener disposeListener) {
      this(window, uiAgent, plugin, disposeListener, null);
   }

   public GenericFindDialog(Window window, UIAgent uiAgent, FindWindowPlugin plugin, ActionListener disposeListener, ObjectProvider globalSearch) {
      super((Frame) window); // hate this restriction...
      this.uiAgent = uiAgent;
      if (uiAgent == null) {
         throw new RuntimeException();
      }
      DisplayFileGenericFindSelectionHandler g = new DisplayFileGenericFindSelectionHandler(uiAgent, getContentPane());
      mWindow = new GenericFindWindow(uiAgent,
                                      plugin,
                                      g,
                                      false);
      mDisposeListener = disposeListener;
      getContentPane().add(mWindow);
      pack();
      Dimension d = PreferenceUtils.readDimension(uiAgent,
                                                  "binding.find.lastWindowSize",
                                                  new Dimension(300, 100), // min size
                                                  Toolkit.getDefaultToolkit().getScreenSize(), // max size.
                                                  new Dimension(400, 300)  // default size.
      );
      setSize(d);
      Point loc = PreferenceUtils.readLocation(uiAgent,
                                               "binding.find.lastWindowLocation",
                                               null,
                                               d // size used.
      );
      if (loc != null) {
         setLocation(loc);
      }
      else {
         setLocationRelativeTo(mWindow);
      }
      setTitle("Find");
      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      setModal(false);
      setVisible(true);
   }

   public void dispose() {
      savePreferences();
      super.dispose();
      mDisposeListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "dispose")); // whatever, just a callback.
   }

   private void savePreferences() {
      PreferenceUtils.writeDimension(uiAgent, "binding.find.lastWindowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, "binding.find.lastWindowLocation", getLocation());
   }

   public void reshow() {
      mWindow.reshow();
   }
}
