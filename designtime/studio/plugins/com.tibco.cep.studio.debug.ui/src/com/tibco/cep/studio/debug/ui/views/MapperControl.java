package com.tibco.cep.studio.debug.ui.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.StudioTraxSupport;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingRunner;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultStylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.ReadFromXSLT;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.StudioUIAgent;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.DebugInputTask;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingTree;
import com.tibco.cep.studio.mapper.ui.data.param.BinaryCategory;
import com.tibco.cep.studio.mapper.ui.data.param.BooleanCategory;
import com.tibco.cep.studio.mapper.ui.data.param.DateTimeCategory;
import com.tibco.cep.studio.mapper.ui.data.param.DecimalCategory;
import com.tibco.cep.studio.mapper.ui.data.param.IntegerCategory;
import com.tibco.cep.studio.mapper.ui.data.param.StringCategory;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.SmComponentProviderExOnSmNamespaceProvider;
import com.tibco.xml.schema.impl.SmNamespaceProviderImpl;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.TargetNamespaceProvider;
import com.tibco.xml.tns.cache.TnsCache;
import com.tibco.xml.tns.impl.TargetNamespaceCache;
@SuppressWarnings({"rawtypes","unchecked"})
public class MapperControl {

	public static final String ENTITY_URI = "entity_uri";
	public static final String DESTINATION_URI = "destination_uri";
	public static final String RULESESSION_URI = "rulesession_uri";
	protected final static SmElement schema = DebuggerService.getDebugInputType();
	
	protected IRuleRunTarget debugTarget;
	UIAgent uiAgent;
	
	protected Composite parent;
	protected Composite frameParent;
	protected SmElement entitySchema;
	
	private Container mapperPanel;
	private Frame awtframe;
	
	private BindingEditorPanel bindingEditorPanel;
	private BindingEditor bindingEditor;
	private ExprContext expressionContext;
	
	protected String entityURI;
	protected String destinationURI;
	protected String ruleSessionURI;

	/**
	 * @param agent
	 */
	public MapperControl(IRuleRunTarget debugTarget, Composite parent) {
		super();
		this.debugTarget = debugTarget;
		this.parent = parent;
		if (!MapperUtils.isSWTMapper(debugTarget.getProjectName())) {
			setFrameParent(new Composite(parent,SWT.FILL|SWT.EMBEDDED));
			GridLayout gl = new GridLayout();
			gl.marginWidth = 0;
			gl.marginHeight = 0;
			GridData gd = new GridData(GridData.VERTICAL_ALIGN_FILL |
					GridData.HORIZONTAL_ALIGN_FILL |
					GridData.GRAB_HORIZONTAL |
					GridData.GRAB_VERTICAL);
			getFrameParent().setLayout(gl);
			getFrameParent().setLayoutData(gd);
			initFrame(getFrameParent());
			initAgent();
			createControl();
		}
	}

	/**
	 * @return the debugTarget
	 */
	public IRuleRunTarget getDebugTarget() {
		if (debugTarget instanceof NullDebugTarget) {
			return null;
		}
		return debugTarget;
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

	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}
	
	

	/**
	 * @return the frameParent
	 */
	public Composite getFrameParent() {
		return frameParent;
	}

	/**
	 * @param frameParent the frameParent to set
	 */
	public void setFrameParent(Composite frameParent) {
		this.frameParent = frameParent;
	}

	/**
	 * @return the awtframe
	 */
	public Frame getFrame() {
		return awtframe;
	}

	/**
	 * @param awtframe
	 *            the Frame to set
	 */
	public void setFrame(Frame awtframe) {
		this.awtframe = awtframe;
	}

	/**
	 * @return the schema
	 */
	public SmElement getDebugSchema() {
		return schema;
	}


	/**
	 * @return the mapperPanel
	 */
	public Container getMapperPanel() {
		return mapperPanel;
	}

	/**
	 * @param mapperPanel
	 *            the mapperPanel to set
	 */
	public void setMapperPanel(Container mapperPanel) {
		this.mapperPanel = mapperPanel;
	}

	/**
	 * @return the bindingEditor
	 */
	public BindingEditor getBindingEditor() {
		return bindingEditor;
	}

	/**
	 * @param bindingEditor
	 *            the bindingEditor to set
	 */
	public void setBindingEditor(BindingEditor bindingEditor) {
		this.bindingEditor = bindingEditor;
	}

	/**
	 * @return the expressionContext
	 */
	public ExprContext getExpressionContext() {
		return expressionContext;
	}

	/**
	 * @param expressionContext
	 *            the expressionContext to set
	 */
	public void setExpressionContext(ExprContext expressionContext) {
		this.expressionContext = expressionContext;
	}

	/**
	 * @return the bindingEditorPanel
	 */
	public BindingEditorPanel getBindingEditorPanel() {
		return bindingEditorPanel;
	}

	/**
	 * @param bindingEditorPanel
	 *            the bindingEditorPanel to set
	 */
	public void setBindingEditorPanel(BindingEditorPanel bindingEditorPanel) {
		this.bindingEditorPanel = bindingEditorPanel;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return getBindingEditorPanel().isEnabled();
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(final boolean enabled) {
		getBindingEditorPanel().setEnabled(enabled);
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return getFrame().isVisible();
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(final boolean visible) {
//		if(!getFrameParent().isDisposed()) {
//			getFrameParent().setVisible(visible);
//			getFrame().setVisible(visible);
//		}
//		if(getFrame().isDisplayable()) {
////			getFrame().setVisible(visible);
//			if(visible) {
//				getFrame().setAlwaysOnTop(true);
//				getFrame().toFront();
//				getFrame().requestFocus();
//				getFrame().setAlwaysOnTop(false);
//				
//			} else {
//				getFrame().toBack();
//				getFrame().setAlwaysOnTop(false);
//			}
//		}
	}
	

	/**
	 * @return the entitySchema
	 */
	protected SmElement getEntitySchema() {
		if(entitySchema == null)
			return getDebugSchema();
		else
			return entitySchema;
	}

	/**
	 * @param entitySchema the entitySchema to set
	 */
	protected void setEntitySchema(SmElement entitySchema) {
		this.entitySchema = entitySchema;
	}

	/**
	 * @return the entityURI
	 */
	public String getEntityURI() {
		return entityURI;
	}

	/**
	 * @param entityURI
	 *            the entityURI to set
	 */
	public void setEntityURI(String entityURI) {
		if(entityURI != null && !entityURI.equals(this.entityURI)) {
			this.entityURI = entityURI;
			MutableElement element = buildElement(entityURI);
			setEntitySchema(element);
			updateMapperPanel(null);
		}
	}

	/**
	 * @return the destinationURI
	 */
	public String getDestinationURI() {
		return destinationURI;
	}

	/**
	 * @param destinationURI
	 *            the destinationURI to set
	 */
	public void setDestinationURI(String destinationURI) {
		if(destinationURI != null && !destinationURI.equals(this.destinationURI)){
			this.destinationURI = destinationURI;
		}
	}

	/**
	 * @return the ruleSessionURI
	 */
	public String getRuleSessionURI() {
		return ruleSessionURI;
	}

	/**
	 * @param ruleSessionURI
	 *            the ruleSessionURI to set
	 */
	public void setRuleSessionURI(String ruleSessionURI) {
		if(ruleSessionURI != null && !ruleSessionURI.equals(this.ruleSessionURI)) {
			this.ruleSessionURI = ruleSessionURI;
		}
	}

	public void initAgent() {
		if (getDebugTarget() != null) {
			UIAgent agent = StudioCorePlugin.getUIAgent(getDebugTarget()
					.getProjectName());
			setUIAgent(new DelegatingUIAgent(agent));
		} else {
			UIAgent agent = new StudioUIAgent(getFrame(), debugTarget.getProjectName());
			setUIAgent(new DelegatingUIAgent(agent));
		}
	}

	protected void createControl() {
		mapperPanel = getSwingContainer(getFrame());
		bindingEditorPanel = new BindingEditorPanel(getUIAgent());

		bindingEditor = bindingEditorPanel.getEditor();

		bindingEditor.setPreferredSize(new Dimension(500, 300));

		bindingEditor.setInputLabel("Scope Variables");
		bindingEditor.setInputLabelTooltip("My tooltip");

		bindingEditor.setOutputLabel("DebugInput");
		bindingEditor.setOutputRootDisplayName("DebugInput");
		
//		Icon dispIcon = JIconsFactory.getImageIcon(JIconsFactory.J32);
//		bindingEditor.setInputRootDisplayIcon(dispIcon);

		bindingEditor.setSubstitutionDialogCallback(new TypeCategory[] {
				StringCategory.INSTANCE, IntegerCategory.INSTANCE,
				DecimalCategory.INSTANCE, BooleanCategory.INSTANCE,
				DateTimeCategory.INSTANCE, BinaryCategory.INSTANCE, },
				new TypeCategory[] {});
		bindingEditorPanel.setPreferredSize(new Dimension(600, 400));
		bindingEditorPanel.setBorder(BorderFactory.createEmptyBorder());
		mapperPanel.add(bindingEditorPanel, BorderLayout.CENTER);
		updateMapperPanel(null);
		getParent().layout();
//		Display.getDefault().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//			}
//		});

	}

	/**
	 * @param xslt
	 * @param element
	 * @param agent
	 */
	public void updateMapperPanel(String xslt) {

		VariableDefinitionList vdl = makeInputVariableDefinitions();
		final TemplateEditorConfiguration tec = new TemplateEditorConfiguration();

		NamespaceMapper nsm = getNamespaceMapper();
		TemplateBinding template = getBinding(xslt);

		StylesheetBinding sb = (StylesheetBinding) template.getParent();

		if (sb != null) {
			BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo()
					.getNamespaceDeclarations();
			for (int i = 0; i < nd.length; i++) {

				nsm.registerOrGetPrefixForNamespaceURI(nd[i].getNamespace(),
						nd[i].getPrefix());
			}
		}

		expressionContext = new ExprContext(vdl, getUIAgent()
				.getFunctionResolver()).createWithNamespaceMapper(nsm);
		TargetNamespaceProvider targetNamespaceProvider = ((TargetNamespaceCache) getUIAgent()
				.getTnsCache()).getNamespaceProvider();
		SmNamespaceProvider smNamespaceProvider = new SmNamespaceProviderImpl(
				targetNamespaceProvider);
		expressionContext = expressionContext
				.createWithInputAndOutputSchemaAndComponentProvider(smNamespaceProvider, new SmComponentProviderExOnSmNamespaceProvider(smNamespaceProvider));
//		expressionContext = expressionContext
//		.createWithInputAndOutputSchemaProvider(smNamespaceProvider);
		tec.setExprContext(expressionContext);

		tec.setImportRegistry(new DefaultImportRegistry());
		tec.setStylesheetResolver(new DefaultStylesheetResolver());

		tec.setBinding(template);
		CoercionSet cs = new CoercionSet();
		tec.setCoercionSet((CoercionSet) cs.clone());
		tec.setExpectedOutput(SmSequenceTypeFactory.create(getEntitySchema()));
		BindingTree tree = bindingEditor.getBindingTree();
		tree.setSelectionPath(null);
		tree.setEditable(true);
		bindingEditor.setTemplateEditorConfiguration(tec);
		bindingEditorPanel.setConfiguration(tec, bindingEditorPanel
				.getEditorState());
		bindingEditorPanel.resetUndoManager();
	}

	/**
	 * @return
	 */
	private VariableDefinitionList makeInputVariableDefinitions() {

		VariableDefinitionList vdlist = new VariableDefinitionList();
		if (getDebugTarget() != null) {
			vdlist.add(new VariableDefinition(
					ExpandedName.makeName(GlobalVariablesProvider.NAME), SmSequenceTypeFactory
							.create(getDebugTarget().getBEProject()
									.getGlobalVariables().toSmElement())));
		}

		return vdlist;
	}
	

	/**
	 * @param xslt
	 * @return
	 */
	private TemplateBinding getBinding(String xslt) {
		TemplateBinding tb = null;
		if ((xslt != null) && (xslt.length() != 0)) {
			StylesheetBinding ssb = ReadFromXSLT.read(xslt);
			tb = BindingManipulationUtils.getNthTemplate(ssb, 0);
		} else {
			tb = new TemplateBinding(BindingElementInfo.EMPTY_INFO, null, "/*");
		}
		return tb;
	}

	/**
	 * @return
	 */
	NamespaceMapper getNamespaceMapper() {

		NamespaceMapper nsmapper = new DefaultNamespaceMapper("xsd");
		((DefaultNamespaceMapper) nsmapper).addXSDNamespace();
		return nsmapper;
	}

	/**
	 * @param entityPath
	 * @return
	 */
	private MutableElement buildElement(String entityPath) {
		MutableSchema mutableSchema = SmFactory.newInstance().createMutableSchema();
		MutableType type = MutableSupport.createType(mutableSchema, "debug_input");
//		type.setDerivationMethod(SmComponent.EXTENSION);
		SmType fnType = getDebugSchema().getType();
		copyType(fnType, type);
		if (getDebugTarget() != null && entityPath != null) {
			String name = entityPath.substring(entityPath.lastIndexOf('/') + 1);
			String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityPath;
			ExpandedName exname = ExpandedName.makeName(nsURI, name);
			SmElement entityElement = getDebugTarget().getBEProject()
					.getSmElement(exname);

			MutableSupport.addOptionalElement(type, entityElement);
		}
		MutableElement fElement = MutableSupport.createElement(mutableSchema,
				getDebugSchema().getName(), type);
		return fElement;
	}

	/**
	 * @param src
	 * @param dest
	 */
	protected void copyType(SmType src, MutableType dest) {
		SmModelGroup grp = src.getContentModel();
		if (grp == null)
			return;
		Iterator itr = grp.getParticles();
		while (itr.hasNext()) {
			SmParticle particle = (SmParticle) itr.next();
			SmParticleTerm term = particle.getTerm();
			if (!XSDL.ANYTYPE_NAME.localName.equalsIgnoreCase(term.getName()))
				MutableSupport.addParticleTerm(dest, term, particle
						.getMinOccurrence(), particle.getMaxOccurrence());
		}
	}

	@SuppressWarnings("serial")
	protected Container getSwingContainer(Frame parent) {
		JPanel panel = new JPanel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		panel.setName("Input");
		panel.setLayout(new BorderLayout());
		panel.setBorder(createFullBorder());
		parent.add(panel);
		parent.setFocusable(true);
		JApplet applet = new JApplet();
		applet.setLayout( new BorderLayout() );
		parent.add(applet);
		applet.add(panel, BorderLayout.CENTER);

		return panel;
	}

	protected void initFrame(Composite parent) {
		setFrame(SWT_AWT.new_Frame(parent));
		new SyncXErrorHandler().installHandler();
	}

	static Border createSpaceBorder() {
		return BorderFactory.createEmptyBorder(2, 2, 2, 2); // gigantic border!
	}

	public static Border createFullBorder() {
		return BorderFactory.createCompoundBorder(createSpaceBorder(),
				BorderFactory.createEtchedBorder());
	}

	/**
	 * @return
	 */
	private XiNode getXSLTNode() {
		try {
			String xslt = getXSLTString();
			if (null == xslt)
				return null;
			XiNode node = XiParserFactory.newInstance().parse(
					new InputSource(new StringReader(xslt)));
			node.normalize();
			return node.getFirstChild();
		} catch (SAXException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return null;
	}

	/**
	 * @return
	 */
	protected String getXSLTString() {
		getBindingEditorPanel().stopEditing();
		TemplateBinding tb = getBindingEditorPanel().getCurrentTemplate();
		NamespaceMapper nsm = getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding) tb.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo()
				.getNamespaceDeclarations();
		for (int i = 0; i < nd.length; i++) {
			nsm.registerOrGetPrefixForNamespaceURI(nd[i].getNamespace(), nd[i]
					.getPrefix());
		}

		String xslt = BindingRunner.getXsltFor(tb, nsm);
		return xslt;
	}

	/**
	 * @param fileName
	 */
	public void saveDebugInputModel(String fileName) {
		if (getDebugTarget() instanceof NullDebugTarget) {
			return;
		}
		try {
			XiNode doc = XiFactoryFactory.newInstance().createDocument();
			XiNode ele = doc.appendElement(ExpandedName.makeName(
					"http://www.tibco.com/be/debug/input", "debug-input"));
			if (getEntityURI() != null) {
				ele.appendElement(ExpandedName.makeName(ENTITY_URI),
						getEntityURI());
			}
			if (getDestinationURI() != null) {
				ele.appendElement(ExpandedName.makeName(DESTINATION_URI),
						getDestinationURI());
			}
			if (getRuleSessionURI() != null) {
				ele.appendElement(ExpandedName.makeName(RULESESSION_URI),
						getRuleSessionURI());
			}

			XiNode xsltNode = getXSLTNode();

			if (xsltNode != null) {
				ele.appendChild(xsltNode.copy());
			}

			FileOutputStream fos = new FileOutputStream(fileName);
			XiSerializer.serialize(doc, fos, "UTF-8", true);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param fileName
	 */
	public void loadDebugInputModel(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			XiNode doc = XiParserFactory.newInstance().parse(
					new InputSource(fis));

			XiNode debugElement = XiChild.getChild(doc, ExpandedName.makeName(
					"http://www.tibco.com/be/debug/input", "debug-input"));
			XiNode entityNode = XiChild.getChild(debugElement, ExpandedName
					.makeName(ENTITY_URI));
			XiNode destNode = XiChild.getChild(debugElement, ExpandedName
					.makeName(DESTINATION_URI));
			XiNode ruleNode = XiChild.getChild(debugElement, ExpandedName
					.makeName(RULESESSION_URI));

			XiNode xsltNode = XiChild.getChild(debugElement, ExpandedName
					.makeName("http://www.w3.org/1999/XSL/Transform",
							"stylesheet"));

			if (entityNode != null) {
				setEntityURI(entityNode.getStringValue());
				// entityName.setText(entityNode.getStringValue());
				// if(getDebugTarget() != null){
				// DesignerElement element =
				// IndexUtils.getElement(getDebugTarget().getWorkspaceProject()
				// .getName(), entityNode.getStringValue());
				// entityName.setData(element);
				// }
			}

			if (destNode != null) {
				setDestinationURI(destNode.getStringValue());
				// destinationName.setText(destNode.getStringValue());
				// if(getDebugTarget() != null){
				// Destination dest =
				// IndexUtils.getDestination(getDebugTarget().getWorkspaceProject().getName(),
				// destNode.getStringValue());
				// destinationName.setData(dest);
				// }
			}

			if (ruleNode != null) {
				setRuleSessionURI(ruleNode.getStringValue());
				// ruleSessionName.setText(ruleNode.getStringValue());
			}

			SmElement fElement = buildElement(getEntityURI());
			setEntitySchema(fElement);
			String xslt = XiSerializer.serialize(xsltNode);
			updateMapperPanel(xslt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VmTask assertDebugInput() {
		try {
			if (getDebugTarget() != null) {

				XiNode gVarsNode = getDebugTarget().getBEProject()
						.getGlobalVariables().toXiNode();

				String xslt = getXSLTString();
				if (xslt == null) {
					MessageBox messageBox = 
							  new MessageBox(StudioDebugUIPlugin.getActiveWorkbenchShell(),SWT.OK |SWT.ICON_ERROR);
						messageBox.setMessage("No Input data specified in the mapper.");
						if (messageBox.open() == SWT.OK) {
							return null;
						}
				}
				XiNode result = StudioTraxSupport.doTransform(xslt,
						new XiNode[] { gVarsNode });
				XiNode debugInput = XiChild.getChild(result, ExpandedName
						.makeName("debug_input"));
				if (debugInput != null) {
					Iterator<XiNode> children = debugInput.getChildren();
					String xmlData = null;
					while (children.hasNext()) {
						XiNode node = (XiNode) children.next();
						if (node.getNodeKind().equals(XmlNodeKind.ELEMENT)) {
							// mark this as an inline assert, which will determine whether to add concept references to WM
							node.setAttributeStringValue(ExpandedName.makeName("inlineAssert"), "true");
							StringWriter sw = new StringWriter();
							XiSerializer.serialize(node,sw,ModelUtils.DEFAULT_ENCODING,false);
							xmlData = sw.toString();
							break;
						}
					}
					
					if (xmlData == null) {
						xmlData = getEmptyXmlData();
					}

					/*
					 * This method should be invoked on the server. Use the
					 * invokeMethod with the appropriate thread reference.
					 * DebuggerServiceImpl.newDebugTask(entityURI, xmlData,
					 * sessionURI, destURI);
					 */
					VmTask task = new DebugInputTask(getDebugTarget(),xmlData, getEntityURI(),
							getDestinationURI(), getRuleSessionURI());
					getDebugTarget().addInputVmTask(task);
					return task;
				} else {
					String entityPath = getEntityURI();
					String name = entityPath.substring(entityPath.lastIndexOf('/') + 1);
					String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityPath;
					ExpandedName exname = ExpandedName.makeName(nsURI, name);
					XiNode doc = XiSupport.getXiFactory().createDocument();
					doc.appendElement(exname);
					StringWriter sw = new StringWriter();
					XiSerializer.serialize(doc,sw,ModelUtils.DEFAULT_ENCODING,true);
					String xmlData = sw.toString();
					
					if (xmlData == null) {
						xmlData = getEmptyXmlData();
					}

					/*
					 * This method should be invoked on the server Use the
					 * invokeMethod with the appropriate thread reference.
					 * DebuggerServiceImpl.newDebugTask(entityURI, xmlData,
					 * sessionURI, destURI);
					 */
					MessageBox messageBox = 
						  new MessageBox(StudioDebugUIPlugin.getActiveWorkbenchShell(),SWT.OK|SWT.CANCEL|SWT.ICON_WARNING);
					messageBox.setMessage("No Input data specified in the mapper.\nDo you want to continue to assert?");
					if (messageBox.open() == SWT.OK) {
						VmTask task = new DebugInputTask(getDebugTarget(),xmlData, getEntityURI(),
								  getDestinationURI(), getRuleSessionURI());
					  getDebugTarget().addInputVmTask(task);
					  return task;
					}
				}
			}
		} catch (Exception e) {
			StudioDebugUIPlugin.errorDialog(StudioDebugUIPlugin.getActiveWorkbenchShell(), "Input Error", 
					"Exception occured while sending input to BE process.\nPlease check the error log for more details.",(Exception)e);
		}
		return null;

	}
	
	public String getEmptyXmlData(){
		String entityURI = getEntityURI();
		String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:Value xmlns:ns0=\"www.tibco.com/be/ontology"+ entityURI +"\" extId=\"\" Id=\"\"/>";
		return xmlData;
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
//			return MapperControl.this.getFrame();
			return getDelegate().getFrame();
		}

		@Override
		public FunctionResolver getFunctionResolver() {
			return getDelegate().getFunctionResolver();
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
				if(getProjectName() != null && !getProjectName().trim().isEmpty()) {
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

		@Override
		public void openResource(String location) {
			getDelegate().openResource(location);
		}

	}

	public void dispose() {
		getFrame().dispose();
		getFrameParent().dispose();
		
	}

	

}
