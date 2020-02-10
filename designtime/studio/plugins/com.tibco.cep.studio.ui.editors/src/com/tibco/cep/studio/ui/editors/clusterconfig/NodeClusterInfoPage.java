package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ClusterInfo;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Mar 9, 2010 3:22:54 PM
 */

public class NodeClusterInfoPage extends ClusterNodeDetailsPage {
	
	private Text tMessageEncoding;
	private Label lName, lAuthor, lDate, lComment;
	private Text tName, tAuthor, tDate, tComment;
	private ClusterInfo clusterInfo;
	private GvField tClusterName;
	
	public NodeClusterInfoPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
	//	Label lClusterName = PanelUiUtil.createLabel(client, "Cluster Name: ");
	//	tClusterName = PanelUiUtil.createText(client);
//	/	tClusterName.addListener(SWT.Modify, getClusterNameModifyListener());
		
		tClusterName = createGvTextField(client,"Cluster Name: ", Elements.NAME.localName);
		
//		PanelUiUtil.createLabel(client, "Message Encoding: ");
//		tMessageEncoding = PanelUiUtil.createText(client);
//		tMessageEncoding.addListener(SWT.Modify, getMessageEncodingChangeListener());

		createRevisionGroup(client);
		
		/*
		PanelUiUtil.createLabel(client, "Object Management: ");
		String omTypes[] = new String[]{ObjectManagement.MEMORY_MGR, ObjectManagement.CACHE_MGR, ObjectManagement.BDB_MGR};
		cObjMgrType = PanelUiUtil.createComboBox(client, omTypes);
		cObjMgrType.addListener(SWT.Selection, getOmTypeChangeListener());
		*/
		
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private void createRevisionGroup(Composite client) {
		lAuthor = PanelUiUtil.createLabel(client, "Author: ");
		tAuthor = PanelUiUtil.createText(client);
		tAuthor.addListener(SWT.Modify, getRevisionModifyListener(Elements.AUTHOR.localName, tAuthor));

		lComment = PanelUiUtil.createLabel(client, "Comment: ");
		tComment = PanelUiUtil.createText(client);
		tComment.addListener(SWT.Modify, getRevisionModifyListener(Elements.COMMENT.localName, tComment));
		
		lName = PanelUiUtil.createLabel(client, "Version: ");
		tName = PanelUiUtil.createText(client);
		tName.setEnabled(false);
		//tName.addListener(SWT.Modify, getRevisionModifyListener(Elements.VERSION.localName, tName));
		
		lDate = PanelUiUtil.createLabel(client, "Date: ");
		tDate = PanelUiUtil.createText(client);
		tDate.setEnabled(false);
	}
	
	private Listener getRevisionModifyListener(final String key, final Text tField) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateRevision(key, tField.getText());
			}
		};
		return listener;
	}
	
	/*private Listener getClusterNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateClusterName(tClusterName.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}*/
	
	private Listener getMessageEncodingChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
//				modelmgr.updateMessageEncoding(tMessageEncoding.getText());
			}
		};
		return listener;
	}
	
	/*
	private Listener getOmTypeChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String omType = cObjMgrType.getText();
				boolean updated = modelmgr.setOmMgr(omType);
				if (updated)
					BlockUtil.refreshViewer(viewer, 3);
			}
		};
		return listener;
	}
	*/
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			clusterInfo = ((ClusterInfo) ssel.getFirstElement());
		else
			clusterInfo = null;
		update();
	}
	
	private void update() {
		if (clusterInfo != null) {
			if(GvUtil.isGlobalVar(clusterInfo.name)){
				tClusterName.setGvModeValue(clusterInfo.name);
				tClusterName.onSetGvMode();
			}else{
				((Text)tClusterName.getField()).setText(clusterInfo.name);
			}
		//	tClusterName.setText(clusterInfo.name);
			//tMessageEncoding.setText(clusterInfo.messageEncoding);
			tName.setText(modelmgr.getRevisionVersion());
			tAuthor.setText(modelmgr.getRevisionAuthor());
			tDate.setText(modelmgr.getRevisionDate());
			tComment.setText(modelmgr.getRevisionComment());
			validateFields();
		}
	}
	
	public boolean validateFields() {
		boolean valid = true;
		return valid;
	}

	@Override
	public Listener getListener(final Control field, final String key) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(field instanceof Text){
					if(GvUtil.isGlobalVar(((Text)field).getText())){
						boolean updated = modelmgr.updateClusterName(tClusterName.getGvText().getText());
						if (updated)
							BlockUtil.refreshViewer(viewer);
					}else{
						boolean updated = modelmgr.updateClusterName(((Text)tClusterName.getField()).getText());
						if (updated)
							BlockUtil.refreshViewer(viewer);
					}
				}
				
			}
		};
	}

	@Override
	public String getValue(String key) {
		if(modelmgr.getClusterName() == null){
			return "";
		}
		return modelmgr.getClusterName();
	}
}