package com.tibco.cep.sharedresource.mqtt;

import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SchemaEditorUtil;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;

/**
 * @author ssinghal
 *
 */
public class MqttEditor extends AbstractSharedResourceEditor{
	
	private MqttModelMgr modelmgr;
	private MqttGeneralPage page;
	
	
	protected void createGeneralPage() {
		this.page = new MqttGeneralPage(this, getContainer(), this.modelmgr);
		int index = addPage(this.page.getControl());
		setPageText(index, this.page.getName());
		this.controls.add(this.page.getControl());
	}

	public String getTitle() {
		String displayName = SchemaEditorUtil.getDisplayName(getEditorFileName(), "MQTT Connection", ".sharedmqttcon");
		return displayName;
	}

	protected void loadModel() {
		this.modelmgr = new MqttModelMgr(project, this);
		this.modelmgr.parseModel();
	}

	protected String saveModel() {
		return this.modelmgr.saveModel();
	}

	protected AbstractSharedResourceEditorPage[] getSchemaEditorPages() {
		return new AbstractSharedResourceEditorPage[] { page };
	}

	protected FormPage[] getEditorPages() {
		return null;
	}

}
