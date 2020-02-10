package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.StudioUIAgent;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditWindow;
import com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.cache.TnsCache;
import com.tibco.xml.tns.impl.TargetNamespaceCache;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * 
 * @author sasahoo
 *
 */
public class ExpressionPropertySection extends MapperPropertySection {
	
	private Ontology ontology;
	protected EntityMapperContext enmContext;
	private boolean isListenerAttached;
	private ChangeListener listener;
	protected EObject userObject;
	protected String currentXslt;
	protected UIAgent uiAgent;
	protected  XPathEditWindow xpeWindow;
	protected NamespaceContextRegistry nsmapper;
	protected String xpathString = null;
	private EObjectWrapper<EClass, EObject> useInstance;
	
	public ExpressionPropertySection(){
		this.listener = getMapperChangeListener();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection#aboutToBeHidden()
	 */
	@Override
	public void aboutToBeHidden() {
		if (!mapperComposite.isDisposed()) {
			isListenerAttached = false;
			xpeWindow.removeChangeListener(listener);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection#aboutToBeShown()
	 */
	@Override
	public void aboutToBeShown() {
		if (!mapperComposite.isDisposed() && !isListenerAttached) {
			isListenerAttached = true;
			xpeWindow.addChangeListener(listener);
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.disposed = false;
		this.fPropertySheetPage = (PropertySheetPage) tabbedPropertySheetPage;
		setMapperComposite(fPropertySheetPage.getWidgetFactory().createComposite(parent, SWT.EMBEDDED));
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.marginHeight = 0;
		fillLayout.marginWidth = 0;
		fillLayout.spacing = 0;
		parent.setLayout(fillLayout);
		
		Container panel = getSwingContainer(getMapperComposite());
		panel.setBackground(Color.RED);
		
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite()
				.getPage().getActiveEditor();
		fProject = ((BpmnEditorInput) fEditor.getEditorInput())
				.getFile().getProject();
		if (fProject == null || fEditor == null) {
			return;
		}
		try {
			setOntology(new CommonOntologyAdapter(getProject().getName()));
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
		enmContext = new EntityMapperContext(fProject);
		MapperControl mc = new MapperControl(enmContext, null);
		enmContext.setMapper(mc);
		initAgent();
	    xpeWindow = new XPathEditWindow(uiAgent);
	    xpeWindow.readPreferences(uiAgent, "xpath.dialog");
	    panel.add(xpeWindow, BorderLayout.CENTER);
	    
	    getMapperComposite().layout();
		
	}
	
	public void initAgent() {
		if (getMapperContext() != null) {
			UIAgent agent = StudioCorePlugin.getUIAgent(getMapperContext()
					.getProject().getName());
			setUIAgent(new DelegatingUIAgent(agent));
		} else {
			String projName = null;
			if (fProject != null) {
				projName = fProject.getName();
			}
			UIAgent agent = new StudioUIAgent(getFrame(), projName);
			setUIAgent(new DelegatingUIAgent(agent));
		}
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		xpathString = RulesEditorUtils.XPATH_PREFIX;
		if (fTSEEdge != null) {
			userObject = (EObject)fTSEEdge.getUserObject();
			useInstance = EObjectWrapper.wrap(userObject);
			if(useInstance.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)){
				EObject condExpr = (EObject)useInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION);
				if (condExpr != null) {
					EObjectWrapper<EClass, EObject> conExprInstance = EObjectWrapper.wrap(condExpr);
					Object object = conExprInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
					if (object != null) {
						xpathString = object.toString();
					}
				}
			}
		}
		enmContext.clearDefinitions();
		EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
		EObject bpmnIndex = BpmnCorePlugin.getDefault()
				.getBpmnModelManager().getBpmnIndex(fProject);
		ProcessAdapter createAdapter = new ProcessAdapter(
				process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
		boolean isSubprocessAdded = false;
		SmElement smElement = null;
		if(fTSEEdge != null){
			TSGraph ownerGraph = fTSEEdge.getOwnerGraph();
			if(ownerGraph != null){
				Object userObject = ownerGraph.getUserObject();
				if(userObject != null && userObject instanceof EObject){
					EObject eObj = (EObject)userObject;
					if(eObj.eClass().equals(BpmnModelClass.SUB_PROCESS)){
						try {
							EObjectWrapper<EClass, EObject> subProcess = EObjectWrapper.wrap(eObj);
							CommonOntologyAdapter adaptor = new CommonOntologyAdapter(fProject.getName());
							smElement = enmContext.addDefinitionForSubprocess(MapperConstants.JOB, subProcess, createAdapter,adaptor, false);
							isSubprocessAdded = true;
						} catch (Exception e) {
							BpmnUIPlugin.log(e);
						}
						
					}
				}
			}
			
		}
		if(!isSubprocessAdded){
			enmContext.addDefinition("job"/*createAdapter.getName()*/, createAdapter, false);
		   smElement = enmContext.getProcessSmElement(createAdapter);
		}
		if(smElement != null){
			final SmSequenceType seType = SmSequenceTypeFactory.create(smElement);
			final String projectName = fEditor.getProject().getName();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					refreshXPathEditor(seType, projectName);
				}
			});
		}
		
		
	}
	
	/**
	 * @param type
	 */
	@SuppressWarnings("rawtypes")
	private void refreshXPathEditor (SmSequenceType type, String projectName) {
		VariableDefinitionList vdlist = new VariableDefinitionList();
		vdlist.add(new VariableDefinition(
				ExpandedName.makeName(GlobalVariablesProvider.NAME), 
				SmSequenceTypeFactory.create(enmContext.getGlobalVariables().toSmElement(false))));
		List<VariableDefinition> list = enmContext.getDefinitions();
		for (VariableDefinition variableDefinition : list) {
			vdlist.add(variableDefinition);
		}
		
    	XiNode xpath = null;
		try {
            xpath = XSTemplateSerializer.deSerializeXPathString(xpathString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HashMap map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
        Iterator itr = map.keySet().iterator();
        nsmapper = MapperCoreUtils.getNamespaceMapper();
        while (itr.hasNext()) {
            String pfx = (String)itr.next();
            String uri = (String)map.get(pfx);
            nsmapper.getOrAddPrefixForNamespaceURI(uri, pfx);
        }
        ExprContext ec = new ExprContext(vdlist, StudioCorePlugin.getUIAgent(projectName).getFunctionResolver()).createWithNamespaceMapper(nsmapper);
        setMode(StatementPanel.FIELD_TYPE_XPATH);
        setSelection(ec, ec, nsmapper, XSTemplateSerializer.getXPathExpressionAsStringValue(xpath));
        xpeWindow.setTypeChecker(MapperCoreUtils.makeTypeChecker(type));
	}

	/**
	 * @param formulaType
	 */
	public void setMode(int formulaType) {
		xpeWindow.setMode(formulaType);
	}

	/**
	 * @param treeContext
	 * @param formulaContext
	 * @param namespaceContextRegistry
	 * @param xpath
	 */
	public void setSelection(ExprContext treeContext, 
			                 ExprContext formulaContext, 
			                 NamespaceContextRegistry namespaceContextRegistry, 
			                 String xpath) {
		xpeWindow.setTreeExprContext(treeContext);
		xpeWindow.setNamespaceImporter(namespaceContextRegistry);
		xpeWindow.setTextExprContext(formulaContext);
		xpeWindow.setFormula(xpath);
	}

	@SuppressWarnings("unused")
	private Ontology getOntology() {
		return ontology;
	}

	private void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}

	
	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		fireUpdate(updateList);
	}
	
	private void fireUpdate(Map<String, Object> updateList){
		if(updateList.size() == 0)
			return ;
		
		EClass nodeType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage.getEditor().getBpmnGraphDiagramManager();
		AbstractEdgeCommandFactory cf = (AbstractEdgeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSEEdge> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, fTSEEdge, PropertiesType.GENERAL_PROPERTIES, updateList);
			
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	
	private class WidgetListener implements ChangeListener, FocusListener{
		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			if(userObject != null){
				 String newXPathText = xpeWindow.getFormula();
				if (newXPathText == null ) {
					newXPathText = "";
				}
				if(!newXPathText.isEmpty())
					newXPathText = replaceMapperString(newXPathText, nsmapper);
				Map<String, Object> updateList = new HashMap<String, Object>();
				updateList.put(BpmnMetaModelConstants.E_ATTR_BODY, newXPathText);
				updatePropertySection(updateList);
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	/**
	 * @param xpathString
	 * @param nsmapper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String replaceMapperString(final String xpathString, NamespaceContextRegistry nsmapper) {
		if (xpathString != null) {
			final List vars = XSTemplateSerializer.searchForVariableNamesinExpression(xpathString);
			HashMap pfxs = MapperCoreUtils.getPrefix2Namespaces(nsmapper);
			String newXslt = XSTemplateSerializer.serializeXPathString(xpathString, pfxs, vars);
			return newXslt;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection#getWidgetListener()
	 */
	@Override
	ChangeListener getMapperChangeListener() {
		return new WidgetListener();
	}
	
	@Override
	FocusListener getTextAreaFocusListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}

	
	/**
	 * @return the MapperContext
	 */
	public MapperContext getMapperContext() {
		return enmContext;
	}

	/**
	 * @return the agent
	 */
	public UIAgent getUIAgent() {
		return uiAgent;
	}

	/**
	 * @param uiAgent
	 *            the uiAgent to set
	 */
	public void setUIAgent(UIAgent uiAgent) {
		this.uiAgent = uiAgent;
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	public class DelegatingUIAgent implements UIAgent {

		UIAgent delegate;
		TargetNamespaceCache tnsCache;

		public DelegatingUIAgent(UIAgent agent) {
			setDelegate(agent);
		}

		/**
		 * @param delegate
		 *            the delegate to set
		 */
		public void setDelegate(UIAgent delegate) {
			this.delegate = delegate;
		}

		private UIAgent getDelegate() {
			return delegate;
		}

		@Override
		public String getAbsoluteURIFromProjectRelativeURI(String location) {
			return getDelegate().getAbsoluteURIFromProjectRelativeURI(location);
		}

		@Override
		public Font getAppFont() {
			return getDelegate().getAppFont();
		}

		@Override
		public Frame getFrame() {
			return getDelegate().getFrame();
		}

		@Override
		public FunctionResolver getFunctionResolver() {
			return getDelegate().getFunctionResolver();
		}
		
		@Override
		public void openResource(String location) {
			getDelegate().openResource(location);
			
		}

		@Override
		public String getProjectName() {
			return getDelegate().getProjectName();
		}

		@Override
		public String getProjectRelativeURIFromAbsoluteURI(String location) {
			return getDelegate().getProjectRelativeURIFromAbsoluteURI(location);
		}

		@Override
		public String getRootProjectPath() {
			return getDelegate().getRootProjectPath();
		}

		@Override
		public Font getScriptFont() {
			return getDelegate().getScriptFont();
		}

		@Override
		public SmNamespaceProvider getSmNamespaceProvider() {
			return getDelegate().getSmNamespaceProvider();
		}

		@Override
		public StylesheetResolver getStyleSheetResolver() {
			return getDelegate().getStyleSheetResolver();
		}

		@Override
		public TnsCache getTnsCache() {
			if(tnsCache == null) {
				if(getProjectName() != null) {
					tnsCache = (TargetNamespaceCache) getDelegate().getTnsCache();
				} else {
					tnsCache = new TargetNamespaceCache();
				}
			}
			return tnsCache;
		}

		@Override
		public String getUserPreference(String name) {
			return getDelegate().getUserPreference(name);
		}

		@Override
		public String getUserPreference(String name, String defaultValue) {
			return getDelegate().getUserPreference(name, defaultValue);
		}

		@Override
		public void setUserPreference(String name, String val) {
			getDelegate().setUserPreference(name, val);
		}

		@Override
		public SmComponentProviderEx getSmComponentProviderEx() {
			return getDelegate().getSmComponentProviderEx();
		}
	}
}