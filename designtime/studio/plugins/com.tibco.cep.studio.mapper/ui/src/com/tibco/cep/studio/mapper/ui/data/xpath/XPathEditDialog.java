package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel;
import com.tibco.cep.studio.mapper.ui.data.utils.BetterJDialog;
import com.tibco.cep.studio.mapper.ui.data.utils.OKCancelPanel;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;

public class XPathEditDialog extends BetterJDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private XPathEditWindow mWindow;
   private String mText;
   private ArrayList<ActionListener> m_applyActionListeners = new ArrayList<ActionListener>();
   private JButton m_okButton;
   static String DIALOG_TITLE;

   //                           InputStream categoryInputStream,


   public XPathEditDialog(UIAgent uiAgent,
                          Frame fr,
                          boolean isModal) {
      super(uiAgent, fr);
      setup(isModal);
   }

   public XPathEditDialog(UIAgent uiAgent,
                          Dialog fr,
                          boolean isModal) {
      super(uiAgent, fr);
      setup(isModal);
   }
/*
   public XPathEditDialog(DesignerDocument doc,
                          XmluiAgent xmluiAgent,
                          Frame fr,
                          FunctionResolver fResolver,
                          boolean isModal) {
      this(uiAgent, fr, fResolver, null, isModal);
   }

   public XPathEditDialog(DesignerDocument doc,
                          XmluiAgent xmluiAgent,
                          Dialog fr,
                          FunctionResolver fResolver,
                          boolean isModal) {
      this(uiAgent, fr, fResolver, null, isModal);
   }
*/

/*
   public static XPathEditDialog create(DesignerDocument doc, XmluiAgent xmluiAgent,
                                        Window window,
                                        FunctionResolver fResolver, boolean isModal) {
      return create(doc, xmluiAgent, window, fResolver, null, isModal);
   }
*/
   public static XPathEditDialog create(UIAgent uiAgent,
                                        Window window,
                                        boolean isModal) {
      if (window instanceof Frame) {
         return new XPathEditDialog(uiAgent, (Frame) window, isModal);
      }
      else {
         return new XPathEditDialog(uiAgent, (Dialog) window, isModal);
      }
   }

   public static String showDialog(UIAgent uiAgent,
                                   Window window,
                                   ExprContext treeContext,
                                   ExprContext context,
                                   NamespaceContextRegistry namespaceContextRegistry,
                                   XiNode inputData,
                                   XTypeChecker optionalTypeChecker,
                                   TreeState inputTreeState,
                                   String xpath) {
      return showDialog(uiAgent, window, treeContext, context, namespaceContextRegistry, inputData,
                        optionalTypeChecker, inputTreeState, xpath, false);
   }
/*
   public static String showDialog(DesignerDocument document,
                                   XmluiAgent xmluiAgent,
                                   Window window,
                                   ExprContext treeContext,
                                   ExprContext context,
                                   NamespaceContextRegistry namespaceContextRegistry,
                                   XiNode inputData,
                                   XTypeChecker optionalTypeChecker,
                                   TreeState inputTreeState,
                                   String xpath,
                                   FunctionResolver fResolver) {
      return showDialog(document, xmluiAgent, window, treeContext, context, namespaceContextRegistry, inputData,
                        optionalTypeChecker, inputTreeState, xpath, fResolver, false);
   }
*/

/*
   public static String showDialog(DesignerDocument document,
                                   XmluiAgent xmluiAgent,
                                   Window window,
                                   ExprContext treeContext,
                                   ExprContext context,
                                   NamespaceContextRegistry namespaceContextRegistry,
                                   XiNode inputData,
                                   XTypeChecker optionalTypeChecker,
                                   TreeState inputTreeState,
                                   String xpath,
                                   FunctionResolver fResolver,
                                   boolean readOnly) {
      return showDialog(document, xmluiAgent, window, treeContext, context,
                        namespaceContextRegistry, inputData, optionalTypeChecker,
                        inputTreeState, xpath, fResolver, null, readOnly);
   }
*/
   /**
    * Returns null for cancel, empty string for empty xpath string.
    */
   public static String showDialog(UIAgent uiAgent,
                                   Window window,
                                   ExprContext treeContext,
                                   ExprContext context,
                                   NamespaceContextRegistry namespaceContextRegistry,
                                   XiNode inputData,
                                   XTypeChecker optionalTypeChecker,
                                   TreeState inputTreeState,
                                   String xpath,
                                   boolean readOnly) {
      XPathEditDialog bed = create(uiAgent, window, true);
      inputData = null;// yellow line eraser... add data back in...
      bed.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      bed.setMode(StatementPanel.FIELD_TYPE_XPATH);
      bed.setSelection(treeContext, context, namespaceContextRegistry, xpath);
      if (inputTreeState != null) {
         bed.mWindow.setInputTreeState(inputTreeState);
      }
      bed.mWindow.setTypeChecker(optionalTypeChecker);
      if (readOnly) {
         bed.m_okButton.setVisible(false);
      }
      bed.setModal(true);
      bed.setVisible(true);
      return bed.mText;
   }

   public void setEditable(boolean editable) {
      mWindow.setEditable(editable);
   }

   public void addApplyListener(ActionListener al) {
      m_applyActionListeners.add(al);
   }

   public static String getDialogTitle() {
      if (DIALOG_TITLE == null) {
         DIALOG_TITLE = XPathEditWindow.getBuilderString("title");
      }
      return DIALOG_TITLE;
   }

   /**
    * Extracts the current formula from the dialog.
    *
    * @return The formula (which may be XPath, AVT, etc.)
    */
   public String getFormula() {
      return mWindow.getFormula();
   }


   /**
    * The xpath builder is now somewhat of a misnomer; it now handles XPath, constants, AVTs, and not-editable.
    *
    * @param formulaType The formula type as enumerated in {@link com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel} for lack of a better place.
    */
   public void setMode(int formulaType) {
      mWindow.setMode(formulaType);
   }

   private void setup(boolean isModal) {
      setTitle(getDialogTitle());
      setIconImage(((ImageIcon)DataIcons.getWindowTitleIcon()).getImage());
      Dimension d = PreferenceUtils.readDimension(uiAgent,
                                                  "xpath.dialog.lastWindowSize",
                                                  new Dimension(200, 200), // min size
                                                  Toolkit.getDefaultToolkit().getScreenSize(), // max size.
                                                  new Dimension(700, 500)  // default size.
      );
      setSize(d);
      Point loc = PreferenceUtils.readLocation(uiAgent,
                                               "xpath.dialog.lastWindowLocation",
                                               null,
                                               d // size used.
      );
      if (loc != null) {
         setLocation(loc);
      }
      else {
         setLocationRelativeTo(mWindow);
      }
      // add sanity code to fit into window...
      XPathEditWindow bew = new XPathEditWindow(uiAgent);

      mWindow = bew;

      mWindow.readPreferences(uiAgent, "xpath.dialog");

      OKCancelPanel okcancel = new OKCancelPanel();
      m_okButton = okcancel.getOKButton();
      m_okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            okPressed();
         }
      });
      okcancel.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            cancelPressed();
         }
      });
      getRootPane().setDefaultButton(okcancel.getOKButton());

      JPanel main = new JPanel(new BorderLayout());
      main.add(okcancel, BorderLayout.SOUTH);
      main.add(mWindow, BorderLayout.CENTER);

      setContentPane(main);
      setModal(isModal);
      if (!isModal) {
         okcancel.setApplyCloseMode();
         okcancel.setEnabled(false);
         mWindow.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               m_okButton.setEnabled(true);
            }
         });
      }
   }

   public void setSelection(ExprContext treeContext, ExprContext formulaContext, NamespaceContextRegistry namespaceContextRegistry, String xpath) {
      mWindow.setTreeExprContext(treeContext);
      mWindow.setNamespaceImporter(namespaceContextRegistry);
      mWindow.setTextExprContext(formulaContext);

      mWindow.setFormula(xpath);
      // when formula is updated, disable (not changed yet):
      if (!isModal()) {
         m_okButton.setEnabled(false);
      }
   }

   public void dispose() {
      savePreferences();
      super.dispose();
   }

   private void savePreferences() {
      PreferenceUtils.writeDimension(uiAgent, "xpath.dialog.lastWindowSize", getSize());
      PreferenceUtils.writePoint(uiAgent, "xpath.dialog.lastWindowLocation", getLocation());
      mWindow.writePreferences(uiAgent, "xpath.dialog");
   }

   private void okPressed() {
      mText = mWindow.getFormula();
      if (isModal()) {
         dispose();
      }
      else {
         for (int i = 0; i < m_applyActionListeners.size(); i++) {
            ActionListener al = m_applyActionListeners.get(i);
            al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "hey dude"));
         }
         m_okButton.setEnabled(false);
      }
   }

   private void cancelPressed() {
      dispose();
   }
}
