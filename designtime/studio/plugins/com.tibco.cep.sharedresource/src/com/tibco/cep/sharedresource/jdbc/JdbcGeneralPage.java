package com.tibco.cep.sharedresource.jdbc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;

/*
@author ssailapp
@date Dec 29, 2009 11:46:43 PM
 */

public class JdbcGeneralPage extends AbstractSharedResourceEditorPage {

	@SuppressWarnings({"unused"})
	private JdbcConfigModelMgr modelmgr;
	
	public JdbcGeneralPage(JdbcConfigEditor editor, Composite parent, JdbcConfigModelMgr modelmgr) {
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
		JdbcConfigurationUi configurationUi = new JdbcConfigurationUi(this.modelmgr);
		configurationUi.createConfigSectionContents(sConfig);
		configurationUi.setInput(modelmgr);
		if(modelmgr.getProject() !=null){
			configurationUi.validateFieldsForGv();
		}
		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	
	/*
	public JdbcGeneralPage(IEditorInput editorInput, JdbcConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;

		createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editorInput.getName(), editor);

		initDriversMap();
		initXaDsClassMap();
		
		Composite sConfig = sectionProvider.createSectionPart("Configuration", false);
		JdbcConfiguration configuration = new JdbcConfiguration();
		configuration.createConfigSectionContents(sConfig); 
		
		managedForm.getForm().reflow(true);
//		parent.layout();
//		parent.pack();
	}
	*/
	
	public String getName() {
		return ("Configuration");
	}

	@Override
	public boolean validateFields() {
		return true;
	}

	@Override
	public Listener getListener(Control field, String key) {
		return null;
	}
}

