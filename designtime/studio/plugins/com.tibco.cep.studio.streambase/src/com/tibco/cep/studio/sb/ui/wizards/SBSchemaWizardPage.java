package com.tibco.cep.studio.sb.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.streambase.sb.DataType;
import com.streambase.sb.EntityType;
import com.streambase.sb.Schema;
import com.streambase.sb.Schema.Field;
import com.streambase.sb.SchemaUtil;
import com.streambase.sb.SchemaUtil.SchemaProvider;
import com.streambase.sb.StreamBaseException;
import com.streambase.sb.StreamProperties;
import com.streambase.sb.XmlInterpretationException;
import com.streambase.sb.client.StreamBaseClient;
import com.streambase.sb.client.StreamBaseURI;
import com.tibco.cep.driver.sb.SBConstants;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

public class SBSchemaWizardPage extends WizardPage {

	private static final String SCHEMA_DECL = "schema";
	private static final String SCHEMAS_ELEMENT = "named-schemas";
	private static final String STREAM_ELEMENT = "stream";
	private static final String OUTPUT_STREAM_ELEMENT = "output-stream";
	private static final String NAME_ATTR = "name";

	private Map<String, Schema> schemaMap = new HashMap<String, Schema>();
	private List schemasList;
	private Tree structureTree;
	private String selectedSchemaName;
	private boolean inputStreamSchema;
	
	protected SBSchemaWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NULL);
		comp.setLayout(new GridLayout());
		
		Label schemasLabel = new Label(comp, SWT.NULL);
		schemasLabel.setText("Available schemas:");
		
		schemasList = new List(comp, SWT.NONE);
		GridData listData = new GridData(GridData.FILL_HORIZONTAL);
		listData.minimumHeight = 100;
		schemasList.setLayoutData(listData);
		schemasList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateStructureTree(schemasList.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Label strLabel = new Label(comp, SWT.NULL);
		strLabel.setText("Schema Structure");
		structureTree = new Tree(comp, SWT.CHECK);
		structureTree.setLayoutData(new GridData(GridData.FILL_BOTH));

		setControl(comp);
		setPageComplete(false);
	}
	
	public String getSelectedSchemaName() {
		return selectedSchemaName;
	}
	
	public boolean isInputStreamSchema() {
		return inputStreamSchema;
	}
	
	protected void updateStructureTree(String[] selection) {
		structureTree.removeAll();
		if (selection.length != 1) {
			setPageComplete(false);
			return;
		}
		String key = selection[0];
		Schema schema = schemaMap.get(key);
		selectedSchemaName = key.substring(0, key.indexOf('(')-1);
		inputStreamSchema = key.contains("(Input Stream");
		Field[] fields = schema.getFields();
		for (Field field : fields) {
			if (field.getDataType() == DataType.CAPTURE) {
				// apparently these are left out of the schema at runtime, hide them from here (? -- BE-23111)
				continue;
			}
			
			TreeItem item = new TreeItem(structureTree, SWT.NULL);
			String fieldStr = field.getName() + " (" + field.getDataType().getName() + ")";
			item.setText(fieldStr);	
			item.setData(field);
			switch (field.getDataType()) {
			case BLOB:
			case TUPLE:
			case FUNCTION:
			case CAPTURE:
				// don't check these by default.  If checked, these types will be converted to a String
				continue;
				
			default:
				break;
			}
			if (SBConstants.PAYLOAD_FIELD.equals(field.getName())) {
				continue;
			}
			item.setChecked(true);
		}
		setPageComplete(true);
	}
	
	public Field[] getSelectedFields() {
		TreeItem[] selection = structureTree.getItems();
		java.util.List<Field> selectedFields = new ArrayList<Schema.Field>();
		for (TreeItem treeItem : selection) {
			if (!treeItem.getChecked()) {
				continue;
			}
			Object data = treeItem.getData();
			if (data instanceof Field) {
				selectedFields.add((Field)data);
			}
		}
		return (Field[]) selectedFields.toArray(new Field[selectedFields.size()]);
	}

	@Override
	public void setVisible(boolean visible) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage("Select available schema");
		if (visible) {
			schemasList.removeAll();
			structureTree.removeAll();
			String[] items = getSchemaList();
			schemasList.setItems(items);
		}
		super.setVisible(visible);
	}

	private String[] getSchemaList() {
		schemaMap.clear();
		NewSBWizard wiz = (NewSBWizard) getWizard();
		SBServerDetails details = wiz.getServerDetails();
		int configType = details.getConfigType();
		if (configType == SBServerDetails.SRC_NAMED_SCHEMA) {
			String namedSchemaPath = details.getNamedSchemaPath();
			return getNamedSchemaList(namedSchemaPath);
		} else {
			return getServerSchemaList(details);
		}
	}

	private String[] getServerSchemaList(SBServerDetails details) {
		StreamBaseClient client = null;
		StreamBaseURI uri = null;
		try {
			String serverUri = details.getSbServerURI(true);
			String uname = details.getUsername(true);
			String password = details.getPassword(true);
			if (password != null && password.startsWith("#!")) {
				password = new String(ObfuscationEngine.decrypt(password));
			}

			uri = new StreamBaseURI(serverUri);
			if (uname != null && uname.trim().length() > 0 && password != null && password.trim().length() > 0) {
				// only set uname/pass if both are specified
				uri = new StreamBaseURI(uri.getHost(), uri.getContainer(), uri.getPort(), uname, password);
			}
			client = new StreamBaseClient(uri);
			Set<StreamProperties> streamProps = client.getAllStreamProperties(EntityType.INPUT_STREAMS);
			for (StreamProperties streamProperties : streamProps) {
				String name = streamProperties.getName();
				schemaMap.put(name + " (Input Stream Schema)", streamProperties.getSchema());
			}
			streamProps = client.getAllStreamProperties(EntityType.OUTPUT_STREAMS);
			for (StreamProperties streamProperties : streamProps) {
				String name = streamProperties.getName();
				schemaMap.put(name + " (Output Stream Schema)", streamProperties.getSchema());
			}
		} catch (StreamBaseException e) {
			if (uri != null && uri.getPassword() != null && uri.getPassword().length() > 0) {
				setErrorMessage("Error connecting to StreamBase Server ["+uri.getBaseURI()+"] with the specified credentials");
			} else {
				setErrorMessage("Error connecting to StreamBase Server ["+e.getMessage()+"]");
			}
		} catch (AXSecurityException e) {
			setErrorMessage("Error connecting to StreamBase Server ["+e.getMessage()+"]");
		}
		return schemaMap.keySet().toArray(new String[schemaMap.keySet().size()]);
	}

	private String[] getNamedSchemaList(String namedSchemaPath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		File schemaFile = new File(namedSchemaPath);
		if (!schemaFile.exists()) {
			setErrorMessage("Schema file does not exist");
			return new String[0];
		}
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document parse = builder.parse(schemaFile);
			NodeList childNodes = parse.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node instanceof Element) {
					collectSchemas((Element) node);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return schemaMap.keySet().toArray(new String[schemaMap.keySet().size()]);
	}

	private void collectSchemas(Element element) {
		NodeList childNodes = element.getChildNodes();
		String nodeName = element.getNodeName();
		
		if (SCHEMAS_ELEMENT.equals(nodeName) || STREAM_ELEMENT.equals(nodeName) || OUTPUT_STREAM_ELEMENT.equals(nodeName)) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node instanceof Element && SCHEMA_DECL.equals(node.getNodeName())) {
					createSchema(element, nodeName, node);
				}
			}
			return;
			
		} else if (SCHEMA_DECL.equals(nodeName)) {
			createSchema(element, nodeName, element);
		}
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node instanceof Element) {
				collectSchemas((Element) node);
			}
		}
	}

	private void createSchema(Element element, String nodeName, Node node) {
		if (element.getParentNode() != null && "field".equals(element.getParentNode().getNodeName())) {
			return;
		}
		try {
			SchemaProvider schemaProvider = new SchemaProvider() {
				
				@Override
				public Schema getSchemaByHash(byte[] arg0) throws StreamBaseException {
					return null;
				}
				
				@Override
				public Schema getNamedSchema(String name) {
					Collection<Schema> values = schemaMap.values();
					for (Schema schema : values) {
						if (name.equals(schema.getName())) {
							return schema;
						}
					}
					return null;
				}
			};
			Schema schema = SchemaUtil.createSchema((Element) node, ByteOrder.nativeOrder(), schemaProvider);
			String type = "Schema";
			String schemaName = "Default";
			if (SCHEMAS_ELEMENT.equals(nodeName)) {
				type = " (Named Schema)";
				schemaName = node.getAttributes().getNamedItem(NAME_ATTR).getNodeValue();
			} else if (SCHEMA_DECL.equals(nodeName)) {
				type = " (Schema)";
				schemaName = element.getAttributes().getNamedItem(NAME_ATTR) != null ? element.getAttributes().getNamedItem(NAME_ATTR).getNodeValue() : "[no name]";
				if (schemaName.indexOf(':') != -1) {
					schemaName = schemaName.substring(schemaName.lastIndexOf(':')+1);
				}
			} else if (STREAM_ELEMENT.equals(nodeName)) {
				type = " (Input Stream Schema)";
				schemaName = element.getAttributes().getNamedItem(NAME_ATTR).getNodeValue();
			} else if (OUTPUT_STREAM_ELEMENT.equals(nodeName)) {
				type = " (Output Stream Schema)";
				schemaName = element.getAttributes().getNamedItem(NAME_ATTR).getNodeValue();
			}
			schemaMap.put(schemaName + type, schema);
		} catch (XmlInterpretationException e) {
			e.printStackTrace();
		}
	}

}
