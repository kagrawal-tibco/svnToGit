package com.tibco.cep.studio.mapper.ui.data.formula;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.VerticalLabelPanel;
import com.tibco.cep.studio.mapper.ui.data.utils.VerticalLayoutPanel;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditWindowErrorArea;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathTextArea;
import com.tibco.cep.studio.mapper.ui.data.xpath.XTypeChecker;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceResolver;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;
import com.tibco.xml.xdata.UtilitySchema;

/**
 * A FormulaWindow which has an area for showing the type result/errors.
 */
public class TextBasedFormulaWindow extends FormulaWindow {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// Right side:
   private JPanel mContextPanel;
   private JLabel mContextField;
   private JPanel m_contextAndText;
   private XPathEditWindowErrorArea m_errorArea;
   private DataTree mOutputWindowTree;
   private JComponent mOutputWindowArea; // Contains mErrorArea and mOutputWindow
   private XPathTextArea mFormulaField;
   private ArrayList<ChangeListener> m_changeListeners = new ArrayList<ChangeListener>();
   private JLabel m_xpathFormulaLabel; // for switching to AVT.
   private JSplitPane mTextSplitter;
   private boolean m_showComputedType = true;

   private boolean m_setting;
   private UIAgent uiAgent;

   public TextBasedFormulaWindow(UIAgent uiAgent) {
	   this.uiAgent = uiAgent;
      add(buildComponents());

      mFormulaField.requestFocus();

      // Without this, the underlining in the text area <may> not show up (until the user types).... there appears to
      // be some sort of initialization timing issue & this was an ugly, but very simple and effective way to
      // fix it:
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            // kick off another error check (for underlining)
            mFormulaField.startErrorCheck();
         }
      });
      DocumentListener dl = new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
            changed();
         }

         public void insertUpdate(DocumentEvent e) {
            changed();
         }

         public void removeUpdate(DocumentEvent e) {
            changed();
         }
      };
      mFormulaField.getDocument().addDocumentListener(dl);
   }

   public void setEditable(boolean editable) {
      mFormulaField.setEditable(editable);
   }

   public boolean isEditable() {
      return mFormulaField.isEditable();
   }

   public void setFormulaLabel(String label) {
      m_xpathFormulaLabel.setText(label);
   }

   public String getFormulaLabel() {
      return m_xpathFormulaLabel.getText();
   }

   public XPathTextArea getTextArea() {
      return mFormulaField;
   }

   /**
    * Enable/disable showing the 'context' area, on by default.
    */
   public void setShowsContext(boolean showsContext) {
      mContextPanel.setVisible(showsContext);
   }

   public boolean getShowsContext() {
      return mContextPanel.isVisible();
   }

   public void setShowComputedType(boolean showComputedType) {
      if (m_showComputedType != showComputedType) {
         m_showComputedType = showComputedType;
         updateOutputWindow();
      }
   }

   public boolean getShowComputedType() {
      return m_showComputedType;
   }

   public void setLanguageDescription(LanguageDescription languageDescription) {
      mFormulaField.setTokenMarker(languageDescription.getTokenMarker());
      mFormulaField.getPainter().setStyles(languageDescription.getSyntaxStyles());
      //WCETODO remove me. mFormulaField.setCodeErrorFinder(languageDescription.getCodeErrorFinder());
   }

   public void readPreferences(UIAgent uiAgent, String prefix) {
      int docDividerLoc = PreferenceUtils.readInt(uiAgent, prefix + ".docSplitLocation", 150);
      mTextSplitter.setDividerLocation(docDividerLoc);
   }

   public void writePreferences(UIAgent uiAgent, String prefix) {
      PreferenceUtils.writeInt(uiAgent, prefix + ".docSplitLocation", mTextSplitter.getDividerLocation());
   }

   private void setContext(SmSequenceType context, NamespaceResolver resolver) {
      String[] steps = null;
      if (context != null) {
         steps = context.getNodePath(resolver);
      }
      if (steps == null) {
         steps = new String[0];
      }
      StringBuffer t = new StringBuffer();
      for (int i = 0; i < steps.length; i++) {
         if (i > 0) {
            t.append('/');
         }
         t.append(steps[i]);
      }
      if (context.getParticleTerm() == UtilitySchema.EMPTY_ELEMENT) {
         mContextField.setText(XPathEditResources.NO_ELEMENT_LABEL);
      }
      else {
         mContextField.setText(t.toString());
      }
   }

   private JComponent buildPreviewPiece() {
      mOutputWindowTree = new DataTree(uiAgent);
      mOutputWindowTree.setDragEnabled(false);

      JPanel errOrOutput = new JPanel(new BorderLayout(0, 4));

      m_errorArea = new XPathEditWindowErrorArea(new XPathEditWindowErrorArea.FixCallback() {
         public void setFixed(String newExpression) {
            mFormulaField.setText(newExpression);
         }
      }, uiAgent.getAppFont());

      errOrOutput.add(m_errorArea, BorderLayout.NORTH);
      JLabel previewLabel = new JLabel(XPathEditResources.EVALTO_LABEL); // will be filled in.

      HorzSizedScrollPane output = new HorzSizedScrollPane(mOutputWindowTree);
      mOutputWindowArea = new VerticalLabelPanel(previewLabel, output);
      errOrOutput.add(mOutputWindowArea);
      return errOrOutput;
   }

   private JComponent buildComponents() {
      mContextPanel = buildContextPiece();

      mFormulaField = new XPathTextArea(uiAgent);
      mFormulaField.addErrorCheckListener(new ChangeListener() {
         public void stateChanged(ChangeEvent ce) {
            updateOutputWindow();
         }
      });
      mFormulaField.setAllowsEmptyString(true);
      mFormulaField.setMinimumSize(new Dimension(100, 100));
      //mFormulaField.setRows(7);

      VerticalLabelPanel labelT = new VerticalLabelPanel(XPathEditResources.XPATH_FORMULA_LABEL, mFormulaField);
      m_xpathFormulaLabel = labelT.getLabel();
      m_contextAndText = new VerticalLayoutPanel(mContextPanel, labelT);

      JComponent previewPiece = buildPreviewPiece();
      JSplitPane formulaPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, m_contextAndText, previewPiece);
      mTextSplitter = formulaPanel;
      m_contextAndText.setBorder(DisplayConstants.getInternalBorder());
      previewPiece.setBorder(DisplayConstants.getInternalBorder());

      return formulaPanel;
   }

   private JPanel buildContextPiece() {
      mContextField = new JLabel();
      JScrollPane scrollIfNeeded = new JScrollPane(mContextField);
      scrollIfNeeded.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
      scrollIfNeeded.getHorizontalScrollBar().setPreferredSize(new Dimension(50, 8)); // mini scroll bar --- normally this scroll bar is invisible.
      scrollIfNeeded.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      return new VerticalLabelPanel(XPathEditResources.EVALCONTEXT_LABEL, scrollIfNeeded);
   }

   public void setTextDividerLocation(int loc) {
      mTextSplitter.setDividerLocation(loc);
   }

   public int getTextDividerLocation() {
      return mTextSplitter.getDividerLocation();
   }

   public void setNamespaceImporter(NamespaceContextRegistry namespaceContextRegistry) {
      mFormulaField.setNamespaceImporter(namespaceContextRegistry);
      m_errorArea.setNamespaceImporter(namespaceContextRegistry);
   }

   public void setTextExprContext(ExprContext state) {
      mFormulaField.setExprContext(state);
      setContext(state.getInput(), state.getNamespaceMapper());
      mFormulaField.startErrorCheck();
   }

   /**
    * Sets the formula, will not trigger a change event.
    */
   public void setFormula(String formula) {
      m_setting = true;
      mFormulaField.setText(formula == null ? "" : formula);
      mFormulaField.startErrorCheck();
      m_setting = false;
   }

   /**
    * Gets the formula.
    */
   public String getFormula() {
      return mFormulaField.getText();
   }

   public void setTypeChecker(XTypeChecker typeChecker) {
      mFormulaField.setTypeChecker(typeChecker);
   }

   public XTypeChecker getTypeChecker() {
      return mFormulaField.getTypeChecker();
   }

   
   public void addChangeListener(ChangeListener changeListener) {
      m_changeListeners.add(changeListener);
   }

   public void removeChangeListener(ChangeListener changeListener) {
      m_changeListeners.remove(changeListener);
   }

   private void changed() {
      if (m_setting) {
         return;
      }
      for (int i = 0; i < m_changeListeners.size(); i++) {
         ChangeListener cl = m_changeListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }

   private void updateOutputWindow() {
      /*if (mFormulaField.isRunningErrorReport())
      {
          // don't update until it is actually done, o.w. flickery and annoying.
          return;
      }*/
      XPathTypeReport report = mFormulaField.getTypeReport();
      if (report.xtype != null && !SmSequenceTypeSupport.isPreviousError(report.xtype) && report.errors.getErrorCount() == 0) {
         // Successfully got output (may have had warnings, but those never killed anyone)   (yet!)
         if (m_showComputedType) {
            mOutputWindowTree.setRootType(report.xtype);
            mOutputWindowArea.setVisible(true);
         }
         else {
            mOutputWindowArea.setVisible(false);
         }
      }
      else {
         // couldn't compute output; don't display.
         mOutputWindowArea.setVisible(false);
      }
      m_errorArea.update(mFormulaField.getExpression(), report);

      mOutputWindowTree.revalidate();
      mOutputWindowTree.repaint();

      // Determine if we should be showing the bottom area:
      if (m_errorArea.isShowingError() || mOutputWindowArea.isVisible() || m_showComputedType) {
         showSplitter();
      }
      else {
         hideSplitter();
      }
   }

   public XPathTypeReport getTypeReport() {
	   return mFormulaField.getTypeReport();
   }
   
   private void hideSplitter() {
      if (getComponentCount() > 0 && getComponent(0) == m_contextAndText) {
         return;// already hidden.
      }
      removeAll();
      mTextSplitter.remove(m_contextAndText);
      add(m_contextAndText);
      revalidate();

      // Don't lose focus there:
      mFormulaField.requestFocus();
   }

   private void showSplitter() {
      if (getComponentCount() > 0 && getComponent(0) == mTextSplitter) {
         return;// already shown.
      }
      mTextSplitter.setLeftComponent(m_contextAndText);
      removeAll();
      add(mTextSplitter);
      revalidate();

      // Don't lose focus there:
      mFormulaField.requestFocus();
   }
}
