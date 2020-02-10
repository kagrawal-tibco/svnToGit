package com.tibco.cep.studio.mapper.ui.data.formula;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.SoftDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;

/**
 * (Prototype work --- generalizing XPathEditWindow, largely a save as, later just make XPathEditWindow a subclass)
 * The large (i.e. large real estate) generic formula editor.<br>
 * A window that edits xpaths, AVTs, and constants with a data-tree, etc., on the left side.
 */
public class FormulaDesignerWindow extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private ArrayList<ChangeListener> m_changeListeners = new ArrayList<ChangeListener>();
   private ChangeListener m_myListener = new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
         formulaWindowChanged();
      }
   };

   // General:
   // Main area:
   private JSplitPane mMainSplitter;
   private SoftDragNDropHandler mSoftDragNDropHandler;

   // Left side:
   private JComponent mInputWindow;
   private JScrollPane m_inputWindowScroller;
   private JTabbedPane mLeftSide;

   // Right side:
   private FormulaWindow m_formulaWindow;

   public FormulaDesignerWindow() {
      super(new BorderLayout());

      // Left side of splitter is the data/functions area.
      // Right side is the text entry area:
      mMainSplitter = new JSplitPane();
      mMainSplitter.setBorder(DisplayConstants.getEmptyBorder());

      mMainSplitter.setLeftComponent(buildLeftSide());
      add(mMainSplitter);
      add(new JSeparator(), BorderLayout.SOUTH);

      mMainSplitter.setDividerLocation(250); // default value.

      mSoftDragNDropHandler = new SoftDragNDropHandler(this, new JComponent[]{mInputWindow});
   }

   public void setFormulaWindow(FormulaWindow formulaWindow) {
      if (m_formulaWindow != null) {
         m_formulaWindow.removeChangeListener(m_myListener);
      }
      m_formulaWindow = formulaWindow;
      if (m_formulaWindow != null) {
         m_formulaWindow.addChangeListener(m_myListener);
      }
      JComponent c = (formulaWindow == null) ? new JLabel("") : (JComponent) formulaWindow;
      mMainSplitter.setRightComponent(c);
   }

   public void setInputWindow(JComponent input) {
      if (input == mInputWindow) {
         return;
      }
      mSoftDragNDropHandler.removeListenerFrom(mInputWindow);
      mInputWindow = input;
      mSoftDragNDropHandler.addListenerTo(mInputWindow);

      m_inputWindowScroller.setViewportView(input);
   }

   /**
    * Adds a left-hand-side tab under the pre-defined name 'functions'.
    */
   public void addFormulaTab(FormulaFunctionPanel functions) {
      mLeftSide.add(XPathEditResources.FUNCTIONS_LABEL, functions);
      mSoftDragNDropHandler.addListenerTo(functions.getTree());
   }

   /**
    * Adds a left-hand-side tab under the pre-defined name 'constants'.
    */
   public void addConstantsTab(FormulaFunctionPanel constants) {
      mLeftSide.add(XPathEditResources.CONSTANTS_LABEL, constants);
      mSoftDragNDropHandler.addListenerTo(constants.getTree());
   }

   /**
    * Reads the preferences such as window positionings to the app.
    *
    * @param fromApp The application to write preferences to.
    * @param prefix  The prefix, i.e. 'myarea.editor'.  Should not end with a '.'.
    */
   public void readPreferences(UIAgent uiAgent, String prefix) {
      if (prefix.endsWith(".")) {
         throw new IllegalArgumentException("Shouldn't end with .");
      }
      int dividerLoc = PreferenceUtils.readInt(uiAgent, prefix + ".splitLocation", 400);
      setDividerLocation(dividerLoc);
      //int docDividerLoc = PreferenceUtils.readInt(fromApp,prefix + ".docSplitLocation",150);
      //setDocDividerLocation(docDividerLoc);

      /*int textDividerLoc = PreferenceUtils.readInt(fromApp, prefix + ".textSplitLocation",150);
      setTextDividerLocation(textDividerLoc);*/

      int showingIndex = PreferenceUtils.readInt(uiAgent, prefix + ".showingIndex", 0);
      if (showingIndex < mLeftSide.getTabCount()) {
         mLeftSide.setSelectedIndex(showingIndex);
      }
   }

   /**
    * Reads the preferences such as window positionings to the app.
    *
    * @param fromApp The application to write preferences to.
    * @param prefix  The prefix, i.e. 'myarea.editor'.  Should not end with a '.'.
    */
   public void writePreferences(UIAgent uiAgent, String prefix) {
      if (prefix.endsWith(".")) {
         throw new IllegalArgumentException("Shouldn't end with .");
      }
      int dividerLoc = getDividerLocation();
      PreferenceUtils.writeInt(uiAgent, prefix + ".splitLocation", dividerLoc);
//      int docDividerLoc = PreferenceUtils.readInt(uiAgent, prefix + ".docSplitLocation", 150);
      //setDocDividerLocation(docDividerLoc);

      /*int textDividerLoc = PreferenceUtils.readInt(fromApp, prefix + ".textSplitLocation",150);
      setTextDividerLocation(textDividerLoc);*/

      int si = mLeftSide.getSelectedIndex();
      PreferenceUtils.writeInt(uiAgent, prefix + ".showingIndex", si);
   }

   /**
    * Sets the divider location between the InputTree & the other area.
    */
   private void setDividerLocation(int location) {
      mMainSplitter.setDividerLocation(location);
   }

   private int getDividerLocation() {
      return mMainSplitter.getDividerLocation();
   }

   /*
   private int getDocDividerLocation()
   {
       return mBuilder.getDividerLocation();
   }

   private void setDocDividerLocation(int loc)
   {
       mBuilder.setDividerLocation(loc);
   }*/

   private JComponent buildLeftSide() {
      JTabbedPane jtp = new JTabbedPane();
      HorzSizedScrollPane scroller = new HorzSizedScrollPane(mInputWindow);
      m_inputWindowScroller = scroller;
      scroller.setBorder(DisplayConstants.getInternalBorder());
      jtp.add(XPathEditResources.DATA_LABEL, scroller);
//        jtp.add(XPathEditResources.CONSTANTS_LABEL,buildConstantsPiece());
      jtp.setBorder(DisplayConstants.getInternalBorder());
      mLeftSide = jtp;
      return jtp;
   }

   /*private JComponent buildConstantsPiece() {
       mConstants = new FormulaFunctionPanel(false, BWUtilities.getRepoAgent(m_designerDocument).getFunctionResolver());
       //mBuilder.setBorder(createBorderFactory.createEmptyBorder(6,6,5,5));
       return mConstants;
   }*/

   /**
    * Sets the formula, will not trigger a change event.
    */
   public void setFormula(String formula) {
      if (m_formulaWindow == null) {
         return;
      }
      m_formulaWindow.setFormula(formula);
   }

   /**
    * Gets the formula.
    */
   public String getFormula() {
      if (m_formulaWindow == null) {
         return "";
      }
      return m_formulaWindow.getFormula();
   }

   public void paint(Graphics g) {
      super.paint(g);
      mSoftDragNDropHandler.paintDragging(g);
   }

   public void addChangeListener(ChangeListener changeListener) {
      m_changeListeners.add(changeListener);
   }

   public void removeChangeListener(ChangeListener changeListener) {
      m_changeListeners.remove(changeListener);
   }

   private void formulaWindowChanged() {
      for (int i = 0; i < m_changeListeners.size(); i++) {
         ChangeListener cl = m_changeListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }
}
