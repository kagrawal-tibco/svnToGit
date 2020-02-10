package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class NodeLoadBalancerAdhocConfigPage extends NodeLoadBalancerConfigPage {

	private Text tLocalDestination;
	private Button bLocalDestination;
	protected Destination destinationObject;
	
	public NodeLoadBalancerAdhocConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	@Override
	public void createFields(Composite comp) {
		tLocalDestination = createTextField(comp, "Local Destination: ", LoadBalancerConfig.ELEMENT_CHANNEL);
		tLocalDestination.setEditable(false);
		tLocalDestination.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tLocalDestination.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==SWT.BS){
					onDestinationDelete();					
				}
			}
		});
		bLocalDestination = PanelUiUtil.createBrowsePushButton(comp, tLocalDestination);
		bLocalDestination.addListener(SWT.Selection, getDestinationChangeListener());
	}
	
	private Listener getDestinationChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				LoadBalancerChannelSelectionDialog dialog = new LoadBalancerChannelSelectionDialog(client.getShell());
				ArrayList<String> filterList = new ArrayList<String>();
				ArrayList<String> destinationList = new ArrayList<String>();
				Map<String,Destination> jmsDestinationMap = ClusterConfigProjectUtils.getLocalDestinationsMap(modelmgr.project);
				destinationList.addAll(jmsDestinationMap.keySet());
				dialog.open(destinationList, filterList);
				String selDestinaion = dialog.getSelectedDestination();
				if (selDestinaion.trim().equals(""))
					return;
				tLocalDestination.setText(selDestinaion);
				onDestinationSelect(jmsDestinationMap.get(selDestinaion));
			}
		};
		return listener;
	}
	
protected void onDestinationDelete() {
		tLocalDestination.setText("");
		destinationObject = null;
	}
	
	protected void onDestinationSelect(Destination newDestinaiton) {
		if(newDestinaiton!=null && destinationObject!=newDestinaiton){
			destinationObject = newDestinaiton;
		}
	}
	@Override
	protected void update() {
		super.update();
		tLocalDestination.setText(config.values.get(LoadBalancerConfig.ELEMENT_CHANNEL));
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}