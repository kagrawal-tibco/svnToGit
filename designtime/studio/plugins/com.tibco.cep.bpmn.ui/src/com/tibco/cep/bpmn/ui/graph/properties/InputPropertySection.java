package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Panel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.swing.JRootPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.SimpleNamespaceContextRegistry;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditor;
import com.tibco.cep.studio.ui.editors.events.NSUtilitiesConverter;
import com.tibco.cep.studio.ui.xml.wizards.PayloadEditor;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * 
 * @author ggrigore
 * 
 */
public class InputPropertySection extends
		AbstractBpmnPropertySection {

	protected Composite composite;
	protected PayloadEditor payloadEditor;
	private ParameterEditor paramEditor;
	private Event event;
	protected Ontology ontology;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent, SWT.EMBEDDED);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.marginHeight = 0;
		fillLayout.marginWidth = 0;
		fillLayout.spacing = 0;
		parent.setLayout(fillLayout);

		Container panel = getSwingContainer(composite);
		panel.setBackground(Color.RED);
		String projectName = null;
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite()
				.getPage().getActiveEditor();
		fProject = ((BpmnEditorInput) fEditor.getEditorInput())
				.getFile().getProject();
		if (fProject == null || fEditor == null) {
			return;
		}
		projectName = getProject().getName();
		try {
			setOntology(new CommonOntologyAdapter(projectName));
		} catch (Exception e1) {
			BpmnUIPlugin.log(e1);
		}
		payloadEditor = new PayloadEditor(projectName);
		String payloadSchemaAsString;
		if (getEvent() == null) {
			payloadSchemaAsString = "";
		} else {
			payloadSchemaAsString = getEvent().getPayloadSchemaAsString();
		}

		paramEditor = payloadEditor.getEditorPanel();
		paramEditor.setNamespaceImporter(getNamespaceImporter(null));
		paramEditor.setImportRegistry(getImportRegistry(null));

		if (payloadSchemaAsString != null) {
			setPayload(paramEditor, payloadSchemaAsString);
		} else {
			paramEditor.readSchemaNode(null);
		}
		panel.add(paramEditor);
		paramEditor.addContentChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                XiNode node = paramEditor.writeSchemaNode(ExpandedName.makeName("payload"));
                if(getEvent() != null) {
                	
//                	if(event.isSoapEvent()){
//                		boolean valid = SOAPEventPayloadBuilder.validatePayload((Element)node);
//                		System.out.println("validSOAPPayload---------"+valid);
//                	}
                	@SuppressWarnings("unused")
					String schemaText = XiSerializer.serialize(node);
//                CompoundCommand command = new CompoundCommand();
//                command.append(new SetCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_PayloadString(), schemaText));
//                
//                try {
//                	// update namespaces
//                	event.getNamespaceEntries().clear(); // not sure we want to clear, or just check for duplicates
//                	Iterator prefixes = paramEditor.getNamespaceImporter().getPrefixes();
//                	while (prefixes.hasNext()) {
//                		String prefix = (String) prefixes.next();
//                		String namespace = paramEditor.getNamespaceImporter().getNamespaceURIForPrefix(prefix);
//                		NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
//                		entry.setPrefix(prefix);
//                		entry.setNamespace(namespace);
//                		command.append(new AddCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_NamespaceEntries(), entry));
//                	}
//                	
//                	// update imports
//                	event.getRegistryImportEntries().clear(); // not sure we want to clear, or just check for duplicates
//            		com.tibco.cep.studio.mapper.xml.nsutils.ImportRegistryEntry[] imports = paramEditor.getImportRegistry().getImports();
//            		for (com.tibco.cep.studio.mapper.xml.nsutils.ImportRegistryEntry importEntry : imports) {
//                		ImportRegistryEntry registryEntry = EventFactory.eINSTANCE.createImportRegistryEntry();
//                		registryEntry.setNamespace(importEntry.getNamespaceURI());
//                		registryEntry.setLocation(importEntry.getLocation());
//                		command.append(new AddCommand(getEditingDomain(), event, EventPackage.eINSTANCE.getEvent_RegistryImportEntries(), registryEntry));
//					}
//
//                } catch (PrefixNotFoundException e1) {
//                	e1.printStackTrace();
//                }
//                getEditingDomain().getCommandStack().execute(command);
                }
               

            }
        });

	}
	
	protected EditingDomain getEditingDomain() {
		if (fEditor instanceof IEditingDomainProvider) {
			IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) fEditor;
			return editingDomainProvider.getEditingDomain() ;
		}
		return null ; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
	 * shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	/*
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh
	 * ()
	 */
	public void refresh() {
		if (fTSENode != null) {
			Object obj = fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_ACTION);
			if (obj instanceof String) {

				Entity entity = getOntology().getEntity((String) obj);
				if (entity instanceof Event) {
					setEvent((Event) entity);
					paramEditor.setNamespaceImporter(getNamespaceImporter(getEvent()));
					paramEditor.setImportRegistry(getImportRegistry(getEvent()));
					setPayload(paramEditor, getEvent().getPayloadSchemaAsString());
				}
			}
		} else {
			setEvent(null);
			paramEditor.setNamespaceImporter(getNamespaceImporter(getEvent()));
			paramEditor.setImportRegistry(getImportRegistry(getEvent()));
			setPayload(paramEditor, "");
		}
		if (fTSEEdge != null) {
		}
		if (fTSEGraph != null) {
			setEvent(null);
			paramEditor.setNamespaceImporter(getNamespaceImporter(getEvent()));
			paramEditor.setImportRegistry(getImportRegistry(getEvent()));
			setPayload(paramEditor, "");
		}
	}


	@SuppressWarnings("serial")
	protected Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}

	public NamespaceContextRegistry getNamespaceImporter(Event event) {
		if (event == null)
			return new SimpleNamespaceContextRegistry();

		com.tibco.xml.NamespaceMapper beMapper = (com.tibco.xml.NamespaceMapper) event
				.getPayloadNamespaceImporter();
		NamespaceMapper bwMapper = NSUtilitiesConverter
				.ConvertToBWNamespaceMapper(beMapper);
		return bwMapper;
	}

	public ImportRegistry getImportRegistry(Event event) {
		if (event == null)
			return new DefaultImportRegistry();
		com.tibco.xml.ImportRegistry beRegistry = event
				.getPayloadImportRegistry();
		ImportRegistry bwRegistry = NSUtilitiesConverter
				.ConvertToBWImportRegistry(beRegistry);

		return bwRegistry;
	}

	/**
	 * @param paramEditor
	 * @param payloadSchemaAsString
	 */
	private void setPayload(ParameterEditor paramEditor,
			String payloadSchemaAsString) {
		try {
			if (payloadSchemaAsString.length() > 0) {
				XiNode payloadPropertyNode = XiParserFactory.newInstance()
						.parse(
								new InputSource(new ByteArrayInputStream(
										payloadSchemaAsString.getBytes("UTF-8"))));
				XiNode node = payloadPropertyNode.getFirstChild();
				paramEditor.readSchemaNode(node);
			} else {
				paramEditor.setSchemaText("");
			}

		} catch (SAXException e1) {
			BpmnUIPlugin.log(e1);
		} catch (IOException e1) {
			BpmnUIPlugin.log(e1);
		}
	}

	private Event getEvent() {
		return event;
	}

	private void setEvent(Event event) {
		this.event = event;
	}

	private Ontology getOntology() {
		return ontology;
	}

	private void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}

	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		// TODO Auto-generated method stub
		
	}

}