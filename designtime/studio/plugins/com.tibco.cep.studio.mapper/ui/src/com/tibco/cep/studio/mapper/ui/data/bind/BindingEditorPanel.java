package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportGenerationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.virt.BindingVirtualizer;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeComparator;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.LazyComponentLoader;
import com.tibco.cep.studio.mapper.ui.data.utils.LazyComponentLoaderRunnable;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;
import com.tibco.xml.tns.parse.TnsErrorSeverity;

/**
 * An xslt template content editor, this wraps {@link BindingEditor} providing higher level control of loading,
 * specifically background loading.
 */
public class BindingEditorPanel extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LazyComponentLoader m_bindingEditorLoader;
    private BindingEditor m_bindingEditor;
    private UIAgent uiAgent;
    private boolean m_showingError; // true if error is showing.
    private SmSequenceType m_lastInputTerm = SMDT.VOID; // for detecting change in context.
    private TemplateEditorConfiguration m_currentConfig;
    private boolean m_setting; // if setting config.
    private ArrayList<ChangeListener> m_listeners = new ArrayList<ChangeListener>();
//    private StylesheetResolver m_stylesheetResolver = null;

    public BindingEditorPanel(UIAgent uiAgent)
    {
       this(uiAgent, null, true);
    }
   /**
    *
    * @param doc
    * @param xmluiAgent
    * @param stylesheetResolver
    * @param fResolver
    * @param categoryInputStream the InputStream from which to retrieve the
    * xpath function categories; if null, will use default functions
    */
   public BindingEditorPanel(UIAgent uiAgent,
                             InputStream categoryInputStream)
   {
      this(uiAgent, categoryInputStream, true);
   }
   public BindingEditorPanel(UIAgent uiAgent,
                             boolean showXmlEdittingButtons) {
      this(uiAgent, null, showXmlEdittingButtons);
   }
   /**
    *
     * @param doc
    * @param xmluiAgent
    * @param stylesheetResolver
    * @param fResolver
    * @param categoryInputStream the InputStream from which to retrieve the
    * xpath function categories; if null, will use default functions
    * @param showXmlEdittingButtons
    */
    public BindingEditorPanel(UIAgent uiAgent,
                              InputStream categoryInputStream,
                              boolean showXmlEdittingButtons)
    {
      super(new BorderLayout());
      this.uiAgent = uiAgent;
      if (uiAgent==null)
      {
          throw new NullPointerException("Null agent");
      }
      m_bindingEditor = new BindingEditor(uiAgent,
                                          categoryInputStream,
                                          showXmlEdittingButtons);
      m_bindingEditorLoader = new LazyComponentLoader();
      m_bindingEditor.addValueChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent ce) {
              if (!m_setting)
              {
                  fireListeners();
              }
          }
      });
      add(m_bindingEditorLoader);
      setStylesheetResolver(uiAgent.getStyleSheetResolver());
   }

   public void setStylesheetResolver(StylesheetResolver stylesheetResolver) {
//      m_stylesheetResolver = stylesheetResolver;
   }

    /**
     * Gets the underlying editor; perhaps deprecate later, but for now required for getting/setting several properties.
     * @return The underlying editor component.
     */
    public BindingEditor getEditor()
    {
        return m_bindingEditor;
    }

    /**
     * Stops the editor from editing, saves preferences and closes the editor
     */
    public void close()
    {
        if (m_bindingEditorLoader!=null)
        {
            BindingEditor be = m_bindingEditor;
            be.stopEditing();

            // stores preferences:
            be.savePreferences();

            // WCETODO these should be per activity not per view....
//            this.lastBindingTreeState = be.getBindingTreeState();
//            this.lastTypesTreeState = be.getInputTreeState();
            be.close();
        }
    }

    /**
     * Implementation override, allows panel to be enabled/disabled for read-only mode.
     * @param enabled
     */
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        m_bindingEditor.setEnabled(enabled);
    }

    public void setError(String msg)
    {
        JTextArea jta = new JTextArea(msg);
        jta.setEditable(false);
        JScrollPane scroll = new JScrollPane(jta);

        JLabel lbl = new JLabel(ResourceBundleManager.getMessage("ae.inputbindingview.error.label"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(lbl,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        showErrorPanel(panel);
    }

    public void setNoInput()
    {
        String msg = ResourceBundleManager.getMessage("ae.inputbindingview.noinputdata.label");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(msg),BorderLayout.NORTH);
        showErrorPanel(panel);
    }

    private void showErrorPanel(JComponent jc)
    {
        m_bindingEditorLoader.setComponent(jc);
        m_showingError = true;
        m_lastInputTerm = SMDT.VOID;
    }

    /**
     * Refreshes the view given new variable list.
     */
    public void refresh(VariableDefinitionList vars)
    {
        if (m_currentConfig==null)
        {
            return; // just in case.
        }
        TemplateEditorConfiguration tec = getRefreshConfig(m_showingError);
        m_currentConfig = tec;
        m_currentConfig.setExprContext(m_currentConfig.getExprContext().createWithVariableList(vars));
        BindingEditorState state = getEditorState();
        setConfiguration(m_currentConfig,state);
    }

    /**
     * Refreshes the view, checking for a change in input type.
     */
    public void refresh(SmSequenceType inputType)
    {
        refresh(inputType,null,null);
    }

    /**
     * Refreshes the view, checking for a change in input type.
     */
    public void refresh(SmSequenceType inputType, 
                        SmNamespaceProvider optionalUpdatedProvider,
                        SmComponentProviderEx optionalUpdatedComponentProvider)
    {
        if (inputType==null || SmSequenceTypeSupport.isVoid(inputType))
        {
            setNoInput();
            return;
        }
        boolean wasShowingError = m_showingError;
        m_showingError = false;
        XTypeComparator.Properties props = new XTypeComparator.Properties();
        // 'exact' setting below required o.w. small changes in input type (i.e. between a String subtype & a String, won't be detected, fixes 1-1MNFTB
        props.setExactPrimitives(true);
        if (!wasShowingError && XTypeComparator.compareAssignability(inputType,m_lastInputTerm,props)==XTypeComparator.EQUALS)
        {
            if (inputType.getParticleTerm()!=null && inputType.getParticleTerm().getErrors(TnsErrorSeverity.ERROR,false).hasNext())
            {
                // it is an error marker, go ahead & refresh, the errors may have changed (not detected in the compare Equivalence above)
            }
            else
            {
                // no change, don't do nothing.
                return;
            }
        }
        // It did change, so update.
        m_setting = true;
        try
        {
            m_lastInputTerm = inputType; // remember so we can tell if it changed.
            TemplateEditorConfiguration tec = getRefreshConfig(wasShowingError);

            if (tec==null)
            {
                return;
            }
            tec.setExpectedOutput(inputType);
            if (optionalUpdatedProvider!=null)
            {
                tec.setExprContext(tec.getExprContext().createWithInputAndOutputSchemaAndComponentProvider(optionalUpdatedProvider, 
                                                                                                           optionalUpdatedComponentProvider));
            }
            tec.setStylesheetResolver(uiAgent.getStyleSheetResolver());
            runLoad(tec,null);
        }
        finally
        {
            m_setting = false;
        }
    }

    private TemplateEditorConfiguration getRefreshConfig(boolean wasShowingError)
    {
        TemplateEditorConfiguration tec;
        if (m_bindingEditorLoader.isLoaded() && !wasShowingError && m_bindingEditor.getTemplateEditorConfiguration().getBinding()!=null)
        {
            tec = m_bindingEditor.getTemplateEditorConfiguration();

            TemplateBinding b = normalizeTemplate(tec.getBinding());
            tec = (TemplateEditorConfiguration)tec.clone();
            tec.setBinding(b);
        }
        else
        {
            tec = m_currentConfig;
        }
        return tec;
    }

    private TemplateBinding normalizeTemplate(TemplateBinding tb)
    {
        // We need the stylesheet to be normalized, too, because it contains most of the namespace decls; things
        // like xsi:type need to look at those:
        StylesheetBinding sb = (StylesheetBinding) tb.getParent();
        StylesheetBinding nsb = (StylesheetBinding) BindingVirtualizer.normalize(sb,null);
        return (TemplateBinding) nsb.getChild(0);
    }

    /**
     * Sets the configuration for the editor; the editor is setup in a background thread so this call will return
     * (possibly) before the editor display is going.
     * @param tec The configuration.
     * @param state The optional state to which the tree should be restored, can be null indicating don't attempt a restore.
     */
    public void setConfiguration(TemplateEditorConfiguration tec, BindingEditorState state)
    {
        // Remember so we can tell if it changed in refresh()
        m_lastInputTerm = tec.getExpectedOutput();
        runLoad(tec,state);
    }

    /**
     * Tells editor that it was reset so it can reset the undo state (this is until Designer supports undo/redo).
     */
    public void resetUndoManager()
    {
        m_bindingEditor.getBindingTree().resetUndoManager();
    }

    public TemplateEditorConfiguration getConfiguration()
    {
        return m_currentConfig;
    }

    private void runLoad(final TemplateEditorConfiguration tec, final BindingEditorState state)
    {
        m_currentConfig = tec;
        if (tec.getExprContext()==null)
        {
            throw new NullPointerException();
        }
        if (tec.getExprContext().getInputSchemaProvider()==null)
        {
            throw new NullPointerException();
        }
        LazyComponentLoaderRunnable r = new LazyComponentLoaderRunnable()
        {
            public Component run(CancelChecker cancelChecker)
            {
                //WCETODO move this...
//WCETODO redo                checkPendingContentModelChanges(tec.getExprContext(),tec.getBinding(),tec.getSchemaProvider());

                NamespaceContextRegistry ni = tec.getExprContext().getNamespaceMapper();
                TemplateEditorConfiguration tec2 = TemplateReportGenerationUtils.createConfiguration(tec,ni,cancelChecker);
                if (cancelChecker.hasBeenCancelled())
                {
                    return null;
                }

                // Now insert marker comments:
                m_bindingEditor.getBindingTree().getFormulaCache().clear();
                BindingDisplayUtils.addMarkers(tec2,m_bindingEditor.getBindingTree().getFormulaCache());
                if (cancelChecker.hasBeenCancelled())
                {
                    return null;
                }

                m_bindingEditor.setTemplateEditorConfiguration(tec2);
                if (cancelChecker.hasBeenCancelled())
                {
                    return null;
                }
                m_bindingEditor.getBindingTree().waitForReport(cancelChecker);
                if (cancelChecker.hasBeenCancelled())
                {
                    return null;
                }
                if (state!=null)
                {
                    m_bindingEditor.setInputTreeState(state.m_leftState);
                    m_bindingEditor.setBindingTreeState(state.m_rightState);
                }
                if (cancelChecker.hasBeenCancelled())
                {
                    return null;
                }

                //m_bindingEditor.setIsDebuggerActive(isDebuggerActive);

                m_bindingEditor.loadPreferences();
                if (cancelChecker.hasBeenCancelled())
                {
                    return null;
                }

                return m_bindingEditor;
            }
        };
        m_bindingEditorLoader.setComponentLoading(r);
    }

    /**
     * Gets the current value or null if error is showing (or is still loading).
     * @return The current template or null.
     */
    public TemplateBinding getCurrentTemplate()
    {
        if (m_bindingEditorLoader.isLoaded() && !m_showingError)
        {
            TemplateEditorConfiguration tec = m_bindingEditor.getTemplateEditorConfiguration();
            if (tec == null || tec.getBinding()==null || tec.getBinding().getParent()==null) // hacky, clean up .getParent() can be null if we're displaying 'bad input' error (?)
            {
                return null;
            }
            TemplateBinding nb = normalizeTemplate(tec.getBinding());
            return nb;
        }
        return null;
    }

    public CoercionSet getCurrentCoercionSet()
    {
        if (m_bindingEditorLoader.isLoaded() && !m_showingError)
        {
            TemplateEditorConfiguration tec = m_bindingEditor.getTemplateEditorConfiguration();
            return tec.getCoercionSet();
        }
        return null;
    }

    /*
    private boolean checkPendingContentModelChanges(ExprContext ec, TemplateBinding b, SmNamespaceProvider sp)
    {
        int sz = m_pendingInlineContentModelChanges.size();
        TemplateReportArguments tra = new TemplateReportArguments();
        tra.setFullFixAnalysis(false);
        tra.setRecordingMissing(false);
        //WCETODO turn off more stuff in the report to make it go faster.

        for (int i=0;i<sz;i++)
        {
            XsdContentModelChangeEvent ce = (XsdContentModelChangeEvent) m_pendingInlineContentModelChanges.get(i);
            SmParticleTerm el = (SmParticleTerm) ce.getOriginalComponent();
            TemplateEditorConfiguration tec = new TemplateEditorConfiguration(ec,XTypeFactory.create(el),b);
            TemplateReport report = TemplateReport.create(tec,m_bindingEditor.getBindingTree().getFormulaCache(),sp,tra);
            if (BindingContentModelChangeUpdater.update(report,ce,false))
            {
                if (InputBindingViewAutoUpdateDialog.showDialog(this))
                {
                    updatePendingContentModelChanges(tec,sp,tra);
                    m_pendingInlineContentModelChanges.clear();
                    return true;
                }
                else
                {
                    m_pendingInlineContentModelChanges.clear();
                    return false;
                }
            }
        }
        m_pendingInlineContentModelChanges.clear();
        return false;
    }

    private void updatePendingContentModelChanges(TemplateEditorConfiguration tec, SmNamespaceProvider sp, TemplateReportArguments tra)
    {
        int sz = m_pendingInlineContentModelChanges.size();
        for (int i=0;i<sz;i++)
        {
            // Recreate report for every change:
            TemplateEditorConfiguration tec2 = (TemplateEditorConfiguration) tec.clone();
            XsdContentModelChangeEvent ce = (XsdContentModelChangeEvent) m_pendingInlineContentModelChanges.get(i);
            tec2.setExpectedOutput(XTypeFactory.create((SmElement)ce.getOriginalComponent()));
            TemplateReport report = TemplateReport.create(tec2,m_bindingEditor.getBindingTree().getFormulaCache(),sp,tra);
            BindingContentModelChangeUpdater.update(report,ce,true);
        }
    } */

    public BindingEditorState getEditorState()
    {
        return new BindingEditorState(m_bindingEditor.getInputTreeState(),m_bindingEditor.getBindingTreeState());
    }

    public void setEditorState(BindingEditorState state)
    {
        if (state==null)
        {
            return;
        }
        m_bindingEditor.setInputTreeState(state.m_leftState);
        m_bindingEditor.setBindingTreeState(state.m_rightState);
    }
    /**
     * Adds a change listener, the listener will <b>not</b> be fired during {@link #setConfiguration} or {@link #refresh}.
     */
    public void addValueChangeListener(ChangeListener cl)
    {
        m_listeners.add(cl);
    }

    public void fireListeners()
    {
        for (int i=0;i<m_listeners.size();i++)
        {
            ChangeListener cl = m_listeners.get(i);
            cl.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Stops any editing (i.e. inline typing, etc.) that is active.
     */
    public void stopEditing()
    {
        // don't want change notifications based on this!
        m_setting = true;
        try
        {
            m_bindingEditor.stopEditing();
        }
        finally
        {
            m_setting = false;
        }
    }

    public void cut()
    {
        if (!m_bindingEditorLoader.isLoaded())
        {
            return;
        }
        m_bindingEditor.cut();
    }

    public void copy()
    {
        if (!m_bindingEditorLoader.isLoaded())
        {
            return;
        }
        m_bindingEditor.copy();
    }

    /**
     * paste into the editor
     */
    public void paste()
    {
        if (!m_bindingEditorLoader.isLoaded())
        {
            return;
        }
        m_bindingEditor.paste();
    }

    /**
     * delete from the editor
     */
    public void delete()
    {
        if (!m_bindingEditorLoader.isLoaded())
        {
            return;
        }
        m_bindingEditor.delete();
    }
}
