/**
 * 
 */
package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.LDMConnection;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * @author vpatil
 *
 */
public class NodeLDMConnectionConfigPage extends ClusterNodeDetailsPage {
	
	private LDMConnection ldmConnection;
	private GvField tUri, tUserName, tUserPassword, tInitialSize, tMaxSize;
	
	public NodeLDMConnectionConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		tUri = createGVTextField("LDM Url: ", Elements.LDM_URL.localName, false);
		tUserName = createGVTextField("User Name: ", Elements.SECURITY_USER_NAME.localName, false);
		tUserPassword = createGVTextField("Password: ", Elements.SECURITY_USER_PASSWORD.localName, true);
		tInitialSize = createGVTextField("Initial Size: ", Elements.INITIAL_SIZE.localName, false);
		tMaxSize = createGVTextField("Max Size: ", Elements.MAX_SIZE.localName, false);
	}
	
	private GvField createGVTextField(String label, String modelId, boolean isPassword) {
		PanelUiUtil.createLabel(client, label);
		GvField gvField = (!isPassword) ? GvUiUtil.createTextGv(client, "") : GvUiUtil.createPasswordGv(client, "");
		setGvFieldListeners(gvField, SWT.Modify, modelId);
		return gvField;
	}

	@Override
	protected void setGvFieldListeners(GvField gvField, int eventType, String modelId) {
		gvField.setFieldListener(eventType, getValuesModifyListener(gvField.getField(), modelId));
		gvField.setGvListener(getValuesModifyListener(gvField.getGvText(), modelId));
	}
	
	private Listener getValuesModifyListener(final Control field, final String key) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					boolean updated = modelmgr.updateLDMConnectionProperty(ldmConnection, key, ((Text) field).getText());
					if (updated)
						BlockUtil.refreshViewer(viewer);
				}
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			ldmConnection = ((LDMConnection) ssel.getFirstElement());
		else
			ldmConnection = null;
		
		update();
	}
	
	private void update() {
		if (ldmConnection != null) {
			tUri.setValue(ldmConnection.ldmUrl);
			tUserName.setValue(ldmConnection.userName);
			tUserPassword.setValue(ldmConnection.userPassword);
			tInitialSize.setValue(ldmConnection.initialSize);
			tMaxSize.setValue(ldmConnection.maxSize);
		}
	}

	@Override
	public Listener getListener(Control field, String key) {
		return null;
	}

	@Override
	public String getValue(String key) {
		return null;
	}
}
