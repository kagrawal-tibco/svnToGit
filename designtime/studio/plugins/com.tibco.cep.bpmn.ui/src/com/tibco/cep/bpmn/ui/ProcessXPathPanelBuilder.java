package com.tibco.cep.bpmn.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.graph.properties.MapperControl;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection.EntityMapperContext;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.StudioUIAgent;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
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
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.cache.TnsCache;
import com.tibco.xml.tns.impl.TargetNamespaceCache;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessXPathPanelBuilder {
	
	protected EntityMapperContext enmContext;
	protected String currentXslt;
	protected UIAgent uiAgent;
	protected  XPathEditWindow xpeWindow;
	
	protected NamespaceContextRegistry nsmapper;
	protected String xpathString = null;
	protected Frame frame;
	protected String projectName;
	
	
	/**
	 * @param frame
	 * @param panel
	 * @param fProject
	 * @param process
	 * @param xpath
	 */
	public ProcessXPathPanelBuilder(Frame frame, 
								 	Container panel, 
								 	IProject fProject,
								 	EObjectWrapper<EClass, EObject> process, 
								 	String xpathString, String...loopArgs) {
		this.frame = frame;
		this.xpathString = xpathString;
		projectName = fProject.getName();
		enmContext = new EntityMapperContext(fProject);
		MapperControl mc = new MapperControl(enmContext, null);
		enmContext.setMapper(mc);
		SmElement smElement = null;
		if(BpmnModelClass.PROCESS.equals(process.getEClassType())){
			EObject bpmnIndex = BpmnCorePlugin.getDefault()
					.getBpmnModelManager().getBpmnIndex(fProject);
			ProcessAdapter createAdapter = new ProcessAdapter(
					process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex));
			enmContext.addDefinition("job"/*createAdapter.getName()*/, createAdapter, false);
			smElement = enmContext.getProcessSmElement(createAdapter);
		}else if (process.getEClassType().equals(
				BpmnModelClass.RECEIVE_TASK)){
			String attachedResource = (String) BpmnModelUtils
					.getAttachedResource(process.getEInstance());
			if (attachedResource != null
					&& !attachedResource.trim().isEmpty()) {
				DesignerElement element = IndexUtils.getElement(
						fProject.getName(), attachedResource);
				String variable = "";
				if (element != null
						&& !(variable = element.getName()).trim()
								.isEmpty()) {
					if (process.getEClassType().equals(
							BpmnModelClass.RECEIVE_TASK)){
						enmContext.addDefinition(variable, attachedResource,
								false);
						smElement  =MapperControl.getSmElementFromPath(projectName, attachedResource);
					}
				} 
			}
			
		}else if(BpmnModelClass.SUB_PROCESS.equals(process.getEClassType())){
			EObjectWrapper<EClass, EObject> subProcess = process;
			EObject bpmnIndex = BpmnCorePlugin.getDefault()
					.getBpmnModelManager().getBpmnIndex(fProject);
			EObject eContainer = subProcess.getEInstance().eContainer();
			if(eContainer.eClass().equals(BpmnModelClass.PROCESS)){
				ProcessAdapter createAdapter = new ProcessAdapter(
						eContainer, new ProcessOntologyAdapter(bpmnIndex));
				try {
					CommonOntologyAdapter adaptor = new CommonOntologyAdapter(fProject.getName());
					smElement = enmContext.addDefinitionForSubprocess(MapperConstants.JOB, subProcess, createAdapter,adaptor, false);
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
		}
		if(loopArgs != null && loopArgs.length == 3){
			String localName = loopArgs[0];
			String enttype = loopArgs[1];
			String entityPath = loopArgs[2];
			enmContext.addSimpleTypeDefinitionforLoopVar(process, localName, enttype, entityPath);
		}
		
		SmSequenceType seType = null;
		if(smElement != null){
			seType = SmSequenceTypeFactory.create(smElement);
		}
		
		initAgent();
	    xpeWindow = new XPathEditWindow(uiAgent);
	    xpeWindow.readPreferences(uiAgent, "xpath.dialog");
	    panel.add(xpeWindow, BorderLayout.CENTER);
	    if(seType != null)
	    	populateSequence(seType);
	    else if(BpmnModelClass.COMPLEX_GATEWAY.equals(process.getEClassType()))
	    	populateSequenceForActivationCount();
	    	
	}
	
	/**
	 * @return
	 */
	public String getXPath() {
		String newXPathText = xpeWindow.getFormula();
		if (newXPathText == null || newXPathText.length() == 0) {
			return null;
		}
		newXPathText = replaceMapperString(newXPathText, nsmapper);
		return newXPathText;
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

	public XPathEditWindow getXpeWindow() {
		return xpeWindow;
	}
	
	private void populateSequence (SmSequenceType type) {
		
		if (xpathString == null) {
			xpathString = RulesEditorUtils.XPATH_PREFIX;
		}
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
		HashMap<?,?> map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
        Iterator<?> itr = map.keySet().iterator();
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
	
	private void populateSequenceForActivationCount () {
		
		if (xpathString == null) {
			xpathString = RulesEditorUtils.XPATH_PREFIX;
		}
		VariableDefinitionList vdlist = new VariableDefinitionList();
		vdlist.add(new VariableDefinition(
				ExpandedName.makeName("activationCount"), SmSequenceTypeFactory.createElement(XSDL.INTEGER)));

    	XiNode xpath = null;
		try {
            xpath = XSTemplateSerializer.deSerializeXPathString(xpathString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HashMap<?,?> map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
        Iterator<?> itr = map.keySet().iterator();
        nsmapper = MapperCoreUtils.getNamespaceMapper();
        while (itr.hasNext()) {
            String pfx = (String)itr.next();
            String uri = (String)map.get(pfx);
            nsmapper.getOrAddPrefixForNamespaceURI(uri, pfx);
        }
        ExprContext ec = new ExprContext(vdlist, StudioCorePlugin.getUIAgent(projectName).getFunctionResolver()).createWithNamespaceMapper(nsmapper);
        setMode(StatementPanel.FIELD_TYPE_XPATH);
        setSelection(ec, ec, nsmapper, XSTemplateSerializer.getXPathExpressionAsStringValue(xpath));
        SmSequenceType createElement = SmSequenceTypeFactory.createElement(XSDL.INTEGER);
        xpeWindow.setTypeChecker(MapperCoreUtils.makeTypeChecker(createElement));
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
	
	public void initAgent() {
		if (enmContext != null) {
			UIAgent agent = StudioCorePlugin.getUIAgent(enmContext
					.getProject().getName());
			setUIAgent(new DelegatingUIAgent(agent));
		} else {
			UIAgent agent = new StudioUIAgent(frame, projectName);
			setUIAgent(new DelegatingUIAgent(agent));
		}
	}
	
	/**
	 * @param uiAgent
	 *            the uiAgent to set
	 */
	public void setUIAgent(UIAgent uiAgent) {
		this.uiAgent = uiAgent;
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