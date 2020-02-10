package com.tibco.cep.sharedresource.jms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.sharedresource.jndi.JndiPropsTableProviderModel;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
 @author ssailapp
 @date Jan 22, 2010 3:52:02 PM
 */

public class JmsConAdvancedPage extends AbstractSharedResourceEditorPage {

	private JmsConModelMgr modelmgr;

	private Composite tableComp;
	private Table taProps;
	private TableProviderUi taPropsProviderUi;

	private Label lTopicFactory, lQueueFactory, lJndiPropsTable;
	private GvField gtTopicFactory, gtQueueFactory;
    private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();

	public JmsConAdvancedPage(JmsConEditor editor, Composite parent, JmsConModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;

		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		initialiseGvFieldTypeMap();
//		createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

		Composite sAdvanced = sectionProvider.createSectionPart("Advanced", false);
		createAdvancedSectionContents(sAdvanced);

		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	
    private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("TopicFactoryName", "String");
        gvTypeFieldMap.put("QueueFactoryName", "String");
 	}


	private void createAdvancedSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));

		lTopicFactory = PanelUiUtil.createLabel(comp, "Topic Connection Factory*: ");
		gtTopicFactory = createGvTextField(comp, modelmgr, "TopicFactoryName");

		lQueueFactory = PanelUiUtil.createLabel(comp, "Queue Connection Factory*: ");
		gtQueueFactory = createGvTextField(comp, modelmgr, "QueueFactoryName");

		tableComp = new Composite(parent, SWT.NONE);
		tableComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tableComp.setLayout(new GridLayout(1, false));
		lJndiPropsTable = PanelUiUtil.createLabel(tableComp, "Optional JNDI Properties:");
		String columns[] = new String[] { "Name", "Type", "Value" };
		JndiPropsTableProviderModel tableModel = new JndiPropsTableProviderModel();
		taPropsProviderUi = new TableProviderUi(tableComp, columns, true, tableModel);
		taProps = taPropsProviderUi.createTable();

		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
		for (LinkedHashMap<String, String> map : modelmgr.getModel().jndiProps) {
			ArrayList<String> rowData = new ArrayList<String>();
			for (int i = 0; i < JmsConModelMgr.keys.length; i++) {
				rowData.add(map.get(JmsConModelMgr.keys[i]));
			}
			tableData.add(rowData);
		}
		taPropsProviderUi.setTableData(tableData);
		taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 0));
		taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 1, 0, TableProviderUi.TYPE_COMBO, tableModel.types));
		taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 2));
		taProps.addListener(SWT.Modify, getTableModifyListener());

		enableJndiAdvancedTab();
		
		validateFields();
		comp.pack();
	}

	public void enableJndiAdvancedTab() {
		if (lTopicFactory == null)
			return;


		boolean enableFactory = modelmgr.getBooleanValue("UseJNDI");
		boolean enableTable = enableFactory	& !modelmgr.getBooleanValue("UseSharedJndiConfig");

		lTopicFactory.setEnabled(enableFactory);
		gtTopicFactory.getField().setEnabled(enableFactory);
		lQueueFactory.setEnabled(enableFactory);
		gtQueueFactory.getField().setEnabled(enableFactory);

		tableComp.setEnabled(enableTable);
		lJndiPropsTable.setEnabled(enableFactory);
		taPropsProviderUi.getToolBar().setEnabled(enableFactory);
		taProps.setEnabled(enableFactory);
	}

	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					modelmgr.updateStringValue(key, ((Text) field).getText());
					validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
				}
				validateFields();
			}
		};
		return listener;
	}

	private Listener getTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateJndiProperties(taProps);
			}
		};
		return listener;
	}

	@Override
	public boolean validateFields() {
		boolean valid = true;
	
		//Commenting below code as current implementation of PanelUiUtil.validateTextField() always return true
		
//		if (modelmgr.getStringValue("TopicFactoryName").trim().equals("") && 
//				modelmgr.getStringValue("QueueFactoryName").trim().equals("")) {
//			valid &= PanelUiUtil.validateTextField(tTopicFactory, true, false);
//			valid &= PanelUiUtil.validateTextField(tQueueFactory, true, false);
//		} else {
//			valid &= PanelUiUtil.validateTextField(tTopicFactory, false, false);
//			valid &= PanelUiUtil.validateTextField(tQueueFactory, false, false);
//		}
		return valid;
	}

	public String getName() {
		return ("Advanced");
	}
}
