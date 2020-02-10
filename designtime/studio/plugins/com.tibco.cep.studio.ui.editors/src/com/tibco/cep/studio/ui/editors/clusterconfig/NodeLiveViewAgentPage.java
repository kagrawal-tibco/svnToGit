package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * @author vpatil
 *
 */
public class NodeLiveViewAgentPage extends NodeAgentAbstractPage {

	private LiveViewAgent lvAgent;
	private Group gpublisher;
	private Text tPublisherQueueSize, tPublisherThreadCount, tMaxActive;
	
	public NodeLiveViewAgentPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);

		gpublisher = new Group(client, SWT.NONE);
		gpublisher.setText("Publisher");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gpublisher.setLayoutData(gd);
		gpublisher.setLayout(new GridLayout(2, false));
		
		tPublisherQueueSize = createPropertyTextField(gpublisher, "Queue Size: ", Elements.PUBLISHER_QUEUE_SIZE.localName);
		tPublisherThreadCount = createPropertyTextField(gpublisher, "Thread Count: ", Elements.PUBLISHER_THREAD_COUNT.localName);
		
		gpublisher.pack();
		
		tMaxActive = createPropertyTextField(client, "Max Active: ", Elements.MAX_ACTIVE.localName);

		createPropertiesGroup();
	}
	
	private Text createPropertyTextField(Composite composite, String label, String modelId) {
		PanelUiUtil.createLabel(composite, label);
		Text tField = PanelUiUtil.createText(composite);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getTextModifyListener(final Text tField, final String modelId) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateBaseLVAgentProperty(lvAgent, modelId, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			lvAgent = ((LiveViewAgent) ssel.getFirstElement());
		else
			lvAgent = null;
		
		if (lvAgent != null) {
			tPublisherQueueSize.setText(lvAgent.publisherQueueSize);
			tPublisherThreadCount.setText(lvAgent.publisherThreadCount);
			tMaxActive.setText(lvAgent.maxActive);
		}
	}
}