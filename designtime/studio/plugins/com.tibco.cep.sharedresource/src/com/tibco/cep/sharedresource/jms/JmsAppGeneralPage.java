package com.tibco.cep.sharedresource.jms;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Dec 31, 2009 12:05:37 AM
 */

public class JmsAppGeneralPage extends AbstractSharedResourceEditorPage {
	
	private JmsAppModelMgr modelmgr;
	private Text tDesc;
	private Table taProps;
	private TableProviderUi taPropsProviderUi;
	
    private String types[] = new String[] { "boolean", "byte", "char", "double", "float", "int", "long", "short", "string" };
    private String cardinality[] = new String[] { "optional", "required" };
	
	public JmsAppGeneralPage(JmsAppEditor editor, Composite parent, JmsAppModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;
		
		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
//		createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

		Composite sConfig = sectionProvider.createSectionPart("Configuration", false);
		createConfigSectionContents(sConfig); 
		
		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}

	private void createConfigSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		Label lDesc = PanelUiUtil.createLabel(comp, "Description: ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.setText(modelmgr.getStringValue("description"));
		tDesc.addListener(SWT.Modify, getListener(tDesc, "description"));
		
		Composite tableComp = new Composite(parent, SWT.NONE);
		tableComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tableComp.setLayout(new GridLayout(1, false));
		PanelUiUtil.createLabel(tableComp, "Properties:");
		String columns[] = new String[]{ "Property Name", "Type", "Cardinality"};
		JmsAppTableProviderModel tableModel = new JmsAppTableProviderModel(modelmgr);  
		taPropsProviderUi = new TableProviderUi(tableComp, columns, true, tableModel);
		taProps = taPropsProviderUi.createTable();
		
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (LinkedHashMap<String, String> map: modelmgr.getModel().appProps) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	for (int i=0; i<JmsAppModelMgr.keys.length; i++) {
	    		rowData.add(map.get(JmsAppModelMgr.keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taPropsProviderUi.setTableData(tableData);
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 0));
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 1, 0, TableProviderUi.TYPE_COMBO, types));
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 2, 0, TableProviderUi.TYPE_COMBO, cardinality));
        taProps.addListener(SWT.Modify, getTableModifyListener());
		
        validateFields();
        
		comp.pack();
	}
	
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (key.equals("description")) {
					modelmgr.updateStringValue(key, tDesc.getText());
				}
			}
		};
		return listener;
	}
	
	private Listener getTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateApplicationProperties(taProps);
			}
		};
		return listener;
	}
	
	@Override
	public boolean validateFields() {
		boolean valid = true;
		return valid;
	}
	
	public String getName() {
		return ("Configuration");
	}
}
