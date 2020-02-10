package com.tibco.cep.sharedresource.rsp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Feb 22, 2010 7:26:15 PM
 */

public class RspGeneralPage extends AbstractSharedResourceEditorPage {
	
	private RspConfigModelMgr modelmgr;
	private Text tDesc, tUrl;
	private Button bUrlBrowse;
	
	public RspGeneralPage(RspConfigEditor editor, Composite parent, RspConfigModelMgr modelmgr) {
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
		comp.setLayout(new GridLayout(3, false));
		
		Label lDesc = PanelUiUtil.createLabel(comp, "Description:   ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.setText(modelmgr.getStringValue("description"));
		tDesc.addListener(SWT.Modify, getListener(tDesc, "description"));
		PanelUiUtil.createLabel(comp, "");

		PanelUiUtil.createLabel(comp, "Repo URL/EAR Path: ");
		Composite urlComp = new Composite(comp, SWT.NONE);
		urlComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		urlComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tUrl = PanelUiUtil.createText(urlComp);
		tUrl.setText(modelmgr.getStringValue("repourl"));
		tUrl.addListener(SWT.Modify, getListener(tUrl, "repourl"));
		bUrlBrowse = PanelUiUtil.createBrowsePushButton(urlComp, tUrl);
		bUrlBrowse.addListener(SWT.Selection, PanelUiUtil.getFileBrowseListener(modelmgr.getProject(), urlComp, null, tUrl)); 
		urlComp.pack();
		
		validateFields();
		comp.pack();
	}

	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					modelmgr.updateStringValue(key, ((Text)field).getText());
				}
				validateFields();
			}
		};
		return listener;
	}
	
	@Override
	public String getName() {
		return ("Configuration");
	}

	@Override
	public boolean validateFields() {
		return true;
	}

}
