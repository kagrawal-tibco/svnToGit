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
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class NodeLoadBalancerPairConfigPage extends NodeLoadBalancerConfigPage {

	private Text tJMSDestination,tKey, tRouter, tReceiver;
	private Button bJMSDestination, bKey, bRouter, bReceiver;
	protected Destination destinationObject;
	private AgentClass routerAgent;
	private AgentClass receiverAgent;

	public NodeLoadBalancerPairConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	public void createFields(Composite comp) {
		
		tJMSDestination = createTextField(comp, "JMS Destination: ", LoadBalancerPairConfig.ELEMENT_CHANNEL);
		tJMSDestination.setEditable(false);
		tJMSDestination.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tJMSDestination.addKeyListener(new KeyListener() {
			
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
		bJMSDestination = PanelUiUtil.createBrowsePushButton(comp, tJMSDestination);
		bJMSDestination.addListener(SWT.Selection, getDestinationChangeListener());
		
		tKey = createTextField(comp, "Key: ", LoadBalancerPairConfig.ELEMENT_KEY);
		tKey.setEditable(false);
		tKey.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tKey.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==SWT.BS){
					tKey.setText("");
				}
			}
		});
		bKey = PanelUiUtil.createBrowsePushButton(comp, tKey);
		bKey.addListener(SWT.Selection, getKeyChangeListener(this));

		tRouter = createTextField(comp, "Router: ", LoadBalancerPairConfig.ELEMENT_ROUTER);
		tRouter.setEditable(false);
		tRouter.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tRouter.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==SWT.BS){
					tRouter.setText("");
					routerAgent = null;
				}
			}
		});
		bRouter = PanelUiUtil.createBrowsePushButton(comp, tRouter);
		bRouter.addListener(SWT.Selection, getRouterChangeListener());

		tReceiver = createTextField(comp, "Receiver: ", LoadBalancerPairConfig.ELEMENT_RECEIVER);
		tReceiver.setEditable(false);
		tReceiver.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tReceiver.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==SWT.BS){
					tReceiver.setText("");
					receiverAgent = null;
				}
			}
		});
		bReceiver = PanelUiUtil.createBrowsePushButton(comp, tReceiver);
		bReceiver.addListener(SWT.Selection, getReceiverChangeListener());
	}

	private Listener getDestinationChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				LoadBalancerChannelSelectionDialog dialog = new LoadBalancerChannelSelectionDialog(client.getShell());
				ArrayList<String> filterList = new ArrayList<String>();
				ArrayList<String> destinationList = new ArrayList<String>();
				Map<String,Destination> jmsDestinationMap = ClusterConfigProjectUtils.getJMSDestinationsMap(modelmgr.project);
				destinationList.addAll(jmsDestinationMap.keySet());
				dialog.open(destinationList, filterList);
				String selDestinaion = dialog.getSelectedDestination();
				if (selDestinaion.trim().equals(""))
					return;
				tJMSDestination.setText(selDestinaion);
				onDestinationSelect(jmsDestinationMap.get(selDestinaion));
			}
		};
		return listener;
	}
	
    private Listener getKeyChangeListener(final NodeLoadBalancerPairConfigPage pairConfigPage) {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                LoadBalancerKeySelectionDialog dialog = new LoadBalancerKeySelectionDialog(client.getShell(),
                        pairConfigPage);
                if (destinationObject != null) {

                    EntityElement entityElement = ClusterConfigProjectUtils.getEntityElementForPath(modelmgr.project,
                            destinationObject.getEventURI());
                    if (entityElement != null) {
                        ArrayList<String> filterList = new ArrayList<String>();
                        ArrayList<String> propertyArrayList;
                        propertyArrayList = ClusterConfigProjectUtils.getEntityElementProperties(entityElement);
                        dialog.open(propertyArrayList, filterList);
                        String selKey = dialog.getSelectedKey();
                        if (selKey.trim().equals(""))
                            return;
                        tKey.setText(selKey);
                    }
                }
            }
        };
        return listener;
    }

	private Listener getRouterChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				AgentSelectionDialog dialog = new AgentSelectionDialog(client.getShell(), modelmgr);
				
				ArrayList<AgentClass> agentClasses = new ArrayList<AgentClass>();
				ArrayList<AgentClass> filterGrp = new ArrayList<AgentClass>();
				setAgentsAndFilters(agentClasses,filterGrp);
				if(receiverAgent!=null){
					filterGrp.add(receiverAgent);
				}
				
				dialog.open(agentClasses, filterGrp);
				if(dialog.getSelectedAgentClass().size()!=0){
					AgentClass selAgent = dialog.getSelectedAgentClass().get(0);
					if (selAgent==null)
						return;
					routerAgent = selAgent;
					tRouter.setText(routerAgent.name);
				}
			}
		};
		return listener;
	}

	private Listener getReceiverChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				AgentSelectionDialog dialog = new AgentSelectionDialog(client.getShell(), modelmgr);
				ArrayList<AgentClass> agentClasses = new ArrayList<AgentClass>();
				ArrayList<AgentClass> filterGrp = new ArrayList<AgentClass>();
				setAgentsAndFilters(agentClasses,filterGrp);
				if(routerAgent!=null){
					filterGrp.add(routerAgent);
				}
				
				dialog.open(agentClasses, filterGrp);
				if(dialog.getSelectedAgentClass().size()!=0){
					AgentClass selAgent = dialog.getSelectedAgentClass().get(0);
					if (selAgent==null)
						return;
					receiverAgent = selAgent;
					tReceiver.setText(receiverAgent.name);
				}
			}
		};
		return listener;
	}

	protected void update() {
		super.update();
		if (config != null) {
			
			tJMSDestination.setText(config.values.get(LoadBalancerConfig.ELEMENT_CHANNEL));
			
			if( (destinationObject==null) && (!tJMSDestination.getText().equals("")) ){
				String destinationURI = tJMSDestination.getText();
				StringBuilder strBuilder =  new StringBuilder(destinationURI);
				strBuilder.replace(destinationURI.lastIndexOf("/"), destinationURI.lastIndexOf("/") + 1, ".channel/" );
				destinationURI = strBuilder.toString(); 
				destinationObject = CommonIndexUtils.getDestination(modelmgr.project.getName(),destinationURI);
			}
			
			if(tJMSDestination.getText().equals("")){
				tKey.setText("");
				tKey.setEnabled(false);
				bKey.setEnabled(false);
				
				tRouter.setText("");
				tRouter.setEnabled(false);
				bRouter.setEnabled(false);
				
				tReceiver.setText("");
				tReceiver.setEnabled(false);
				bReceiver.setEnabled(false);
				
			}
			else{
				tKey.setText(config.values.get(LoadBalancerPairConfig.ELEMENT_KEY));
				tRouter.setText(config.values.get(LoadBalancerPairConfig.ELEMENT_ROUTER));
				tReceiver.setText(config.values.get(LoadBalancerPairConfig.ELEMENT_RECEIVER));
				
				tKey.setEnabled(true);
				bKey.setEnabled(true);
				
				tRouter.setEnabled(true);
				bRouter.setEnabled(true);
				
				tReceiver.setEnabled(true);
				bReceiver.setEnabled(true);
				
				if((routerAgent==null) && (!tRouter.getText().equals("")) ){
					routerAgent = modelmgr.getAgentClass(tRouter.getText());
				}
				
				if((receiverAgent==null) && (!tReceiver.getText().equals("")) ){
					receiverAgent = modelmgr.getAgentClass(tReceiver.getText());
				}
			}
		}
	}
	
	protected void onDestinationDelete() {
		
		tJMSDestination.setText("");
		destinationObject = null;
		
		tKey.setText("");
		tKey.setEnabled(false);
		bKey.setEnabled(false);
		
		tRouter.setText("");
		tRouter.setEnabled(false);
		bRouter.setEnabled(false);
		
		tReceiver.setText("");
		tReceiver.setEnabled(false);
		bReceiver.setEnabled(false);
		
		routerAgent=null;
		receiverAgent=null;
	}
	
	protected void onDestinationSelect(Destination newDestinaiton) {
		if(newDestinaiton!=null && destinationObject!=newDestinaiton){
			tKey.setText("");
			tRouter.setText("");
			tReceiver.setText("");
			routerAgent=null;
			receiverAgent=null;
			
			destinationObject = newDestinaiton;
		}
		
		tKey.setEnabled(true);
		tKey.setEditable(false);
		tKey.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		bKey.setEnabled(true);
		
		tRouter.setEnabled(true);
		tRouter.setEditable(false);
		tRouter.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		bRouter.setEnabled(true);
		
		tReceiver.setEnabled(true);
		tReceiver.setEditable(false);
		tReceiver.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		bReceiver.setEnabled(true);
			
	}
	
	
	private void setAgentsAndFilters(ArrayList<AgentClass> agentClasses, ArrayList<AgentClass> filterGrp){
		agentClasses.addAll(modelmgr.getAgentClasses());
		for(AgentClass agent : agentClasses){
			ArrayList<DestinationElement> agentDestinations;
		
			if(agent.type.equals(AgentClass.AGENT_TYPE_INFERENCE)){
				InfAgent inferenceAgent = (InfAgent)agent ; 
				agentDestinations = inferenceAgent.agentDestinationsGrpObj.agentDestinations;
			}
			else if(agent.type.equals(AgentClass.AGENT_TYPE_QUERY)){
				QueryAgent queryAgent = (QueryAgent)agent ; 
				agentDestinations = queryAgent.agentDestinationsGrpObj.agentDestinations;
			}
			else if(agent.type.equals(AgentClass.AGENT_TYPE_DASHBOARD)){
				DashboardAgent dashboardAgent = (DashboardAgent)agent ; 
				agentDestinations = dashboardAgent.agentDestinationsGrpObj.agentDestinations;
			}
			else if(agent.type.equals(AgentClass.AGENT_TYPE_PROCESS)){
				ProcessAgent processAgent = (ProcessAgent)agent ; 
				agentDestinations = processAgent.agentDestinationsGrpObj.agentDestinations;
			}
			else{
				filterGrp.add(agent);
				continue;
			}
			boolean found = false;
			for(DestinationElement destination : agentDestinations){
				
				if(destination instanceof AgentDestinationsGrpElement){
					ArrayList<DestinationElement> destinations = ((AgentDestinationsGrpElement)destination).destinationsGrp.destinations;
					 for(DestinationElement destGrpDestinations : destinations){
						 if (destGrpDestinations instanceof ClusterConfigModel.Destination){
							 if(tJMSDestination.getText().equals(((ClusterConfigModel.Destination)destGrpDestinations).destinationVal.get(Elements.URI.localName))){
								found = true;
								break;
							 }
						 }
					 }
					 if(found){
						 break;
					 }
				}
				else if(destination instanceof DestinationsGrp){
					ArrayList<DestinationElement> destinations = ((DestinationsGrp)destination).destinations;
					 for(DestinationElement destGrpDestinations : destinations){
						 if (destGrpDestinations instanceof ClusterConfigModel.Destination){
							 if(tJMSDestination.getText().equals(((ClusterConfigModel.Destination)destGrpDestinations).destinationVal.get(Elements.URI.localName))){
								found = true;
								break;
							 }
						 }
					 }
					 if(found){
						 break;
					 }
				}
				else if (destination instanceof ClusterConfigModel.Destination){
					if(tJMSDestination.getText().equals(((ClusterConfigModel.Destination)destination).destinationVal.get(Elements.URI.localName))){
						found = true;
						break;
					}
				}
			}
			if(!found){
				filterGrp.add(agent);
			}
		}
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
