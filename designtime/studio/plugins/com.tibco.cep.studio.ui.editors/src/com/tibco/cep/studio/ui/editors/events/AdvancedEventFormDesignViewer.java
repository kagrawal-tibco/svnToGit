package com.tibco.cep.studio.ui.editors.events;

import static com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils.openElement;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getGlobalVariableDefs;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setupSourceViewerDecorationSupport;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.updateDeclarations;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDeclarationTableEditableSupport;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setDropTarget;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setEditContextMenuSupport;

import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.adapters.EventAdapter;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditor;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorContentAssistant;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPartitionScanner;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractEventFormViewer;
import com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.xml.wizards.PayloadEditor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver.PrefixNotFoundException;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;

/**
 * 
 * @author sasahoo
 *
 */
public class AdvancedEventFormDesignViewer extends AbstractEventFormViewer implements IResolutionContextProvider, IStudioRuleSourceCommon{

	private Section payloadsection;
    private Section expiryActionSection;
    protected SourceViewer actionsSourceViewer;
    private Table declarationsTable;
    private PayloadEditor payloadEditor;

    protected Document document; // For Expiry Action Source
    private Compilable rule = null;
    public static final Font swtFont = new Font(Display.getDefault(), "Courier New", 10, SWT.NULL);
    
    private boolean intialized = true;
	private EventExpiryRuleSourceViewerConfiguration sourceViewerConfiguration;
	private EventExpiryRuleDocumentListener doclistener; 

    /**
     * @param editor
     */
    public AdvancedEventFormDesignViewer(EventFormEditor editor) {
        this.editor = editor;
        if (editor != null && editor.getEditorInput() instanceof  EventFormEditorInput) {
            this.event = ((EventFormEditorInput) editor.getEditorInput()).getSimpleEvent();
        } else{
            this.event = editor.getSimpleEvent();
        }
    }
    
    /**
     * @param container
     */
    /* (non-Javadoc)
     * @see com.tibco.cep.studio.ui.forms.AbstractEventFormViewer#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite container) {
        super.createPartControl(container);
        sashForm.setWeights(new int[] {50,154}); // setting the default weights for the available sections.
        initExpiryRule();

        //Making readonly widgets
		if(!getEditor().isEnabled()){
			readOnlyWidgets();
		}
   }

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractFormViewer#createGeneralPart(org.eclipse.ui.forms.widgets.ScrolledForm, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@Override
	protected void createGeneralPart(ScrolledForm form, FormToolkit toolkit) {
		createDeclarationsPart(form, toolkit);
	}
	
    /* (non-Javadoc)
     * @see com.tibco.cep.studio.ui.forms.AbstractFormViewer#createExpiryActionPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
     */
    protected void createExpiryActionPart(final IManagedForm managedForm, Composite parent) {
    	FormToolkit toolkit = managedForm.getToolkit();
        expiryActionSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED |Section.TWISTIE);
        expiryActionSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        expiryActionSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
        expiryActionSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if (e.getState() == true) {
					sashForm.setWeights(new int[] { 105,99 });//When both expanded
				} else {
					sashForm.setWeights(new int[] { 10,194 });
				}
				getForm().reflow(true);
			}
		});
        expiryActionSection.setText(Messages.getString("event.expiry.action"));
        Composite client = toolkit.createComposite(expiryActionSection, SWT.WRAP| SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		client.setLayout(layout);
		GridData gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=200;
        expiryActionSection.setLayoutData(gd);
        
        toolkit.paintBordersFor(client);
        expiryActionSection.setClient(client);
        DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
        EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
        OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);

        actionsSourceViewer = new SourceViewer(client, new VerticalRuler(20, access), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        actionsSourceViewer.getControl().setLayoutData( new GridData(GridData.FILL_BOTH));
        actionsSourceViewer.getTextWidget().setFont(swtFont);
          setupSourceViewerDecorationSupport(actionsSourceViewer, ruler, access, sharedColors);
        sourceViewerConfiguration = new EventExpiryRuleSourceViewerConfiguration(this, IRulesSourceTypes.ACTION_SOURCE, editor.getProject());
        actionsSourceViewer.configure(sourceViewerConfiguration);
    }

    @Override
	public void dispose() {
    	if (actionsSourceViewer != null) {
    		actionsSourceViewer.unconfigure();
    		actionsSourceViewer = null;
    	}
    	if (sourceViewerConfiguration != null) {
    		sourceViewerConfiguration.unconfigure();
    		sourceViewerConfiguration = null;
    	}
    	if (document != null) {
    		document.removeDocumentListener(doclistener);
    		document = null;
    	}
		super.dispose();
	}

	/* (non-Javadoc)
     * @see com.tibco.cep.studio.ui.forms.AbstractFormViewer#createPayloadPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createPayloadPart(IManagedForm managedForm, Composite parent) {
        FormToolkit toolkit = managedForm.getToolkit();
        payloadsection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED |Section.TWISTIE);
        payloadsection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        payloadsection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
        payloadsection.addExpansionListener(new ExpansionAdapter() {
            /**
             * @param e
             */
            public void expansionStateChanged(ExpansionEvent e) {
                getForm().reflow(true);
            }
        });
        payloadsection.setText(Messages.getString("PAYLOAD_SECTION"));
        Composite sectionClient = toolkit.createComposite(payloadsection, SWT.EMBEDDED);
        payloadsection.setClient(sectionClient);
        TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 1;

        TableWrapData td = new TableWrapData(TableWrapData.FILL);
        td.grabHorizontal = true;
        td.heightHint = 228;
        payloadsection.setLayoutData(td);

        Container panel = getSwingContainer(sectionClient);
        panel.setBackground(Color.WHITE);
        String projectName = null;
        if (editor != null){
            projectName = editor.getEntity().getOwnerProjectName();
        }
        if (projectName == null) {
            return;
        }
        payloadEditor = new PayloadEditor(projectName);
        String payloadSchemaAsString = this.event.getPayloadString();
        
		// Handling proj lib  -- Bugfix for BE - 18918 
        
		String projectNameForProjLib = this.editor.getProject().getName() ;
		IFile file = IndexUtils.getLinkedResource( projectNameForProjLib , event.getFullPath()) ;
		if (file != null && IndexUtils.isProjectLibType( file ) ) {
			 payloadEditor.setfProjectName( projectNameForProjLib ) ;
		} 
		//till here 
        
        
        createParamEditor(panel, payloadSchemaAsString);
    }

	private void createParamEditor(final Container panel, final String payloadSchemaAsString) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				final ParameterEditor paramEditor = payloadEditor.getEditorPanel();
		        paramEditor.setNamespaceImporter(getNamespaceImporter());
		        paramEditor.setImportRegistry(getImportRegistry());

		        if (payloadSchemaAsString != null) {
		            setPayload(paramEditor, payloadSchemaAsString);
		        }else{
		            paramEditor.readSchemaNode(null);
		        }
		        panel.add(paramEditor);
		        paramEditor.addContentChangeListener(new ChangeListener() {

		            @SuppressWarnings("unchecked")
					@Override
		            public void stateChanged(ChangeEvent e) {
		                XiNode node = paramEditor.writeSchemaNode(ExpandedName.makeName("payload"));
		               
		                if(event.isSoapEvent()){
		                    boolean valid = SOAPEventPayloadBuilder.validatePayload((Element)node);
//		                    System.out.println("validSOAPPayload---------"+valid);
		                }
		                String schemaText = XiSerializer.serialize(node);
		                if (node.getFirstChild() == null && schemaText != null) {
		                	if (schemaText.contains("<payload/>")) {
		                		// the payload is empty, set it to null (for instance, to allow other events to inherit from this event
		                		// is this the best way to check for an empty payload?
		                		schemaText = null;
		                	}
		                }
		                CompoundCommand command = new CompoundCommand();
		                command.append(new SetCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_PayloadString(), schemaText));
		                
		                try {
		                	// update namespaces
		                	event.getNamespaceEntries().clear(); // not sure we want to clear, or just check for duplicates
		                	Iterator prefixes = paramEditor.getNamespaceImporter().getPrefixes();
		                	while (prefixes.hasNext()) {
		                		String prefix = (String) prefixes.next();
		                		String namespace = paramEditor.getNamespaceImporter().getNamespaceURIForPrefix(prefix);
		                		NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
		                		entry.setPrefix(prefix);
		                		entry.setNamespace(namespace);
		                		command.append(new AddCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_NamespaceEntries(), entry));
		                	}
		                	
		                	// update imports
		                	event.getRegistryImportEntries().clear(); // not sure we want to clear, or just check for duplicates
		            		com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry[] imports = paramEditor.getImportRegistry().getImports();
		            		for (com.tibco.cep.mapper.xml.nsutils.ImportRegistryEntry importEntry : imports) {
		                		ImportRegistryEntry registryEntry = EventFactory.eINSTANCE.createImportRegistryEntry();
		                		registryEntry.setNamespace(importEntry.getNamespaceURI());
		                		registryEntry.setLocation(importEntry.getLocation());
		                		command.append(new AddCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_RegistryImportEntries(), registryEntry));
							}

		                } catch (PrefixNotFoundException e1) {
		                	e1.printStackTrace();
		                }
		                EditorUtils.executeCommand(editor, command);

		            }
		        });
			}
		});
	}

    private boolean findPrefixInNode(XiNode payloadNode, String prefix) {
    	if (payloadNode.getNodeKind() == XmlNodeKind.ELEMENT) {
    		return processChildNode(prefix, payloadNode);
    	}
    	return false;
    }

	private boolean processChildNode(String targetPrefix, XiNode node) {
		String ref = XiAttribute.getStringValue(node, "ref");
		if (ref == null) {
			ref = XiAttribute.getStringValue(node,"type");
		}
		if (ref != null) {
			String[] refs = ref.split(":");
			if (refs.length >= 1) {
				String pfx = refs[0];
				if (pfx.equals(targetPrefix)) {
					return true;
				}
			}
		}
		Iterator children = node.getChildren();
		while (children.hasNext()) {
			XiNode object = (XiNode) children.next();
			if (object.getNodeKind() == XmlNodeKind.ELEMENT) {
				if (processChildNode(targetPrefix, object)) {
					return true;
				}
			}
		}
		return false;
	}
    
    /**
     * @param paramEditor
     * @param payloadSchemaAsString
     */
    private void setPayload(ParameterEditor paramEditor,String payloadSchemaAsString){
        try {
            if (payloadSchemaAsString.length() > 0) {
                XiNode payloadPropertyNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(payloadSchemaAsString)));
                XiNode node = payloadPropertyNode.getFirstChild();
                paramEditor.readSchemaNode(node);
            }

        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void initExpiryRule() {
        rule = event.getExpiryAction();
        if(rule == null){
            rule = RuleFactory.eINSTANCE.createRule();
            event.setExpiryAction((Rule)rule);
            Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
            symbol.setType(event.getFullPath());
            symbol.setIdName(event.getName().toLowerCase());
            event.getExpiryAction().getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
        }
       updateDeclarations(declarationsTable, event.getExpiryAction(), event.getOwnerProjectName());
        IDocumentPartitioner partitioner = null;
        doclistener = new EventExpiryRuleDocumentListener();

        //For Expiry Action Source
        document = new Document(rule.getActionText()!= null ? rule.getActionText():"");
        document.addDocumentListener(doclistener);
        partitioner = new FastPartitioner(new RulesPartitionScanner(),
                new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER,  RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
        partitioner.connect(document);
        document.setDocumentPartitioner(partitioner);
        actionsSourceViewer.setDocument(document, new RulesAnnotationModel());

        TextViewerUndoManager actionSourceUndoManager =  new TextViewerUndoManager(10);
        actionSourceUndoManager.connect(actionsSourceViewer);

        setDeclarationTableEditableSupport(declarationsTable, 1, rule, -1, -1, -1, this, false);

        if(editor.isEnabled()){
        	setEditContextMenuSupport(actionsSourceViewer.getTextWidget(), actionSourceUndoManager, true, this);
        	setKeySupport(actionsSourceViewer.getTextWidget(), actionSourceUndoManager);
        	setDropTarget(actionsSourceViewer, null, null);
        }
        intialized =  true;
    }


    @Override
    public IResolutionContext getResolutionContext(ElementReference elementReference) {
        ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
        final SimpleResolutionContext context = new SimpleResolutionContext(scope);
        List<GlobalVariableDef> globalDefs = getGlobalVariableDefs(editor.getProject().getName(), declarationsTable);
        if (globalDefs == null) {
        	return context;
        }
        for (GlobalVariableDef globalVariableDef : globalDefs) {
            context.addGlobalVariable(globalVariableDef);
        }
        return context;
    }

    @Override
    public IResolutionContext getResolutionContext(ElementReference elementReference, ScopeBlock scope) {
        SimpleResolutionContext context = new SimpleResolutionContext(scope);
        List<GlobalVariableDef> globalDefs = getGlobalVariableDefs(editor.getProject().getName(), declarationsTable);
        if (globalDefs == null) {
        	return context;
        }
        for (GlobalVariableDef globalVariableDef : globalDefs) {
            context.addGlobalVariable(globalVariableDef);
        }
        return context;
    }

    /**
     *
     * @author sasahoo
     *
     */
    protected class EventExpiryRuleDocumentListener implements IDocumentListener{

        /* (non-Javadoc)
           * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
           */
        @Override
        public void documentAboutToBeChanged(DocumentEvent event) {
            // TODO Auto-generated method stub
        }

        /* (non-Javadoc)
           * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
           */
        @Override
        public void documentChanged(DocumentEvent event) {
            if(document!=null && event.getDocument() == document){
                if(rule != null && !rule.getActionText().equals(document.get())){
                    rule.setActionText(document.get());
                    editor.modified();
                }
            }
        }
    }
    
    /**
     * @param textWidget
     * @param undoManager
     */
    public void setKeySupport(final StyledText textWidget, final TextViewerUndoManager undoManager){
    	
    	textWidget.addKeyListener(new KeyAdapter(){
    		/* (non-Javadoc)
    		 * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
    		 */
    		@Override
    		public void keyReleased(KeyEvent e) {
    			if (e.stateMask == SWT.CTRL && e.keyCode == 'z') {
    				undoManager.undo();
    			}
    			if (e.stateMask == SWT.CTRL && e.keyCode == 'y') {
    				undoManager.redo();
    			}
    			if (e.stateMask == SWT.CTRL && e.keyCode == ' ') {
    				try {
    					if(sourceViewerConfiguration != null){
    						RulesEditorContentAssistant entityContentAssistant = 
    							(RulesEditorContentAssistant)sourceViewerConfiguration.getContentAssistant(actionsSourceViewer);
    						entityContentAssistant.showPossibleCompletions();
    					}
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
    			}
    			if (e.keyCode == SWT.F3) {
    				openDeclaration();
    			}
    		}});
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#updateDeclarationStatements(java.lang.String, java.lang.String, java.lang.String, int, int, int, com.tibco.cep.designtime.core.model.rule.Compilable)
      */
    @Override
    public boolean updateDeclarationStatements(String type, String id, String oldIdName,
                                            String newIdName, int ruleType, int blockType, int statementType, Compilable compilable) {
        boolean updated = StudioUIUtils.updateSymbol(type, oldIdName, newIdName, compilable);
        if(updated) {
        	editor.modified();
        	return true;
        }
        return false;
    }

    @Override
    public Compilable getCommonCompilable() {
        return rule;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#getRemoveDeclarationButton()
      */
    public ToolItem getRemoveDeclarationButton(){
        return null;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon#getDeclarationTable()
      */
    public Table getDeclarationTable(){
        return declarationsTable;
    }

    public ImportRegistry getImportRegistry() {
        Event event = getEventModel();
        if(event == null) return null;

        com.tibco.xml.ImportRegistry beRegistry = event.getPayloadImportRegistry();
        ImportRegistry bwRegistry = NSUtilitiesConverter.ConvertToBWImportRegistry(beRegistry);

        return bwRegistry;
    }

    public NamespaceContextRegistry getNamespaceImporter() {
        Event event = getEventModel();
        if(event == null) return null;

        com.tibco.xml.NamespaceMapper beMapper = (com.tibco.xml.NamespaceMapper) event.getPayloadNamespaceImporter();
        NamespaceMapper bwMapper = NSUtilitiesConverter.ConvertToBWNamespaceMapper(beMapper);
        return bwMapper;
    }

    private Event getEventModel() {
        return new EventAdapter(event, null);
    }
    
	/**
	 * @param form
	 * @param toolkit
	 */
	protected void createDeclarationsPart(ScrolledForm form, FormToolkit toolkit) {
		Section declarations = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED);
		declarations.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		declarations.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		declarations.setText(Messages.getString("rule.declarations"));
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		declarations.setLayoutData(gd);
		
		Composite client = toolkit.createComposite(declarations, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 0;
		client.setLayout(layout);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		client.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		declarations.setClient(client);
		
		declarationsTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION);
		declarationsTable.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 80;
		gd.widthHint = 100;
		declarationsTable.setLayoutData(gd);
	
		TableColumn termColumn = new TableColumn(declarationsTable, SWT.NULL);
		termColumn.setText(Messages.getString("rule.declaration.col.term"));
//		termColumn.setWidth(429);

		TableColumn aliasColumn = new TableColumn(declarationsTable, SWT.NULL);
		aliasColumn.setText(Messages.getString("rule.declaration.col.alias"));
//		aliasColumn.setWidth(430);
		
		declarationsTable.setLinesVisible(true);
		declarationsTable.setHeaderVisible(true);
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(declarationsTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		declarationsTable.setLayout(autoTableLayout);
	}

    public void addPayload(){
        if(event.isSoapEvent()){
            try {
                SOAPEventPayloadBuilder soapEventPayloadBuilder =  new SOAPEventPayloadBuilder();
                Element element = soapEventPayloadBuilder.getPayloadElement();
                String payloadSchemaAsString = XiSerializer.serialize(element);
                Command command = new SetCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_PayloadString(), payloadSchemaAsString);
                EditorUtils.executeCommand(editor, command);
                setPayload(payloadEditor.getEditorPanel(), event.getPayloadString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param changedEvent
     */
    public void doRefresh(SimpleEvent changedEvent){
    	this.event = changedEvent;
    	rule = event.getExpiryAction();
    	if(rule != null){
    		if(!intialized){
    			initExpiryRule();
    		}else{
    			if(actionsSourceViewer != null && actionsSourceViewer.getDocument() != null){
    				actionsSourceViewer.getDocument().set(rule.getActionText());
    			}
    		}
    	}
//    		IDocumentPartitioner partitioner = null;
//    		EventExpiryRuleDocumentListener listener = new EventExpiryRuleDocumentListener();
//    		//For Expiry Action Source
//    		document = new Document(rule.getActionText()!= null ? rule.getActionText():"");
//    		document.addDocumentListener(listener);
//    		partitioner = new FastPartitioner(new RulesPartitionScanner(),
//    				new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER,  RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
//    		partitioner.connect(document);
//    		document.setDocumentPartitioner(partitioner);
//    		actionsSourceViewer.setDocument(document, new RulesAnnotationModel());
//    	}
    }

	public PayloadEditor getPayloadEditor() {
		return payloadEditor;
	}

	@Override
	public ToolItem getDownDeclarationButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToolItem getUpDeclarationButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public void refreshFormEditorSourceViewerConfiguration() {
		if(sourceViewerConfiguration != null){
			sourceViewerConfiguration.getReconciler().getReconcilingStrategy(IDocument.DEFAULT_CONTENT_TYPE).reconcile(null);
		}
	}

	@Override
	public void openDeclaration() {
		if(actionsSourceViewer.getTextWidget().getCaretOffset() > 0 ) {
			openElement (actionsSourceViewer, 
					sourceViewerConfiguration.getResolutionContextProvider(),
					document, IRulesSourceTypes.ACTION_SOURCE, editor.getProject().getName());
		}
	}
	
	private void readOnlyWidgets(){
		diagramAction.setEnabled(false);
		dependencyDiagramAction.setEnabled(false);
		sequenceDiagramAction.setEnabled(false);
		
		actionsSourceViewer.setEditable(false);
		payloadEditor.getEditorPanel().setEnabled(false);
	}

	public void checkUnusedImports() {
        final ParameterEditor paramEditor = payloadEditor.getEditorPanel();
    	Iterator prefixes = paramEditor.getNamespaceImporter().getPrefixes();
        XiNode node = paramEditor.writeSchemaNode(ExpandedName.makeName("payload"));
        List<String> unusedImportEntries = new ArrayList<String>();
    	while (prefixes.hasNext()) {
    		String prefix = (String) prefixes.next();
    		if ("xsd".equals(prefix)) {
    			continue; // leave xsd imports
    		}
    		String namespace = null;
			try {
				namespace = paramEditor.getNamespaceImporter().getNamespaceURIForPrefix(prefix);
			} catch (PrefixNotFoundException e) {
				e.printStackTrace();
			}
			if (namespace == null) {
				continue;
			}
    		NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
    		entry.setPrefix(prefix);
    		entry.setNamespace(namespace);
    		if (!findPrefixInNode(node, prefix)) {
    			unusedImportEntries.add(namespace);
    		}
    	}

		if (unusedImportEntries.size() > 0) {
			StringBuffer buf = new StringBuffer();
			buf.append("The following namespaces no longer appear to be used in the Event payload:\n");
			for (String ns : unusedImportEntries) {
				buf.append(" - ");
				buf.append(ns);
				buf.append('\n');
			}
			buf.append("Would you like to remove them as namepace imports from the Event?");
			if (MessageDialog.openQuestion(new Shell(), "Unused imports", buf.toString())) {
				removeNamespaceEntries(unusedImportEntries);
				// reset namespace importer/import registry after update
		        paramEditor.setNamespaceImporter(getNamespaceImporter());
		        paramEditor.setImportRegistry(getImportRegistry());
			}
		}
	}

	private void removeNamespaceEntries(
			List<String> unusedEntries) {
		for (String ns : unusedEntries) {
			removeNamespaceEntry(ns);
			removeRegistryImportEntry(ns);
		}
	}

	private void removeNamespaceEntry(String ns) {
		EList<NamespaceEntry> entries = getEvent().getNamespaceEntries();
		NamespaceEntry toRemove = null;
		for (NamespaceEntry namespaceEntry : entries) {
			if (ns.equals(namespaceEntry.getNamespace())) {
				toRemove = namespaceEntry;
				break;
			}
		}
		if (toRemove != null) {
			entries.remove(toRemove);
		}
	}

	private void removeRegistryImportEntry(String namespace) {
		EList<ImportRegistryEntry> entries = getEvent().getRegistryImportEntries();
		ImportRegistryEntry toRemove = null;
		for (ImportRegistryEntry importRegistryEntry : entries) {
			if (namespace.equals(importRegistryEntry.getNamespace())) {
				toRemove = importRegistryEntry;
				break;
			}
		}
		if (toRemove != null) {
			entries.remove(toRemove);
		}
	}
	
}