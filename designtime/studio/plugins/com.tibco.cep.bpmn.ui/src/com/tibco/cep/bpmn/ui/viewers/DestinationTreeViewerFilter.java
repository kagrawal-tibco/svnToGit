package com.tibco.cep.bpmn.ui.viewers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.viewers.TreeViewerFilter;

/**
 * @author majha
 * 
 */
public class DestinationTreeViewerFilter extends TreeViewerFilter {

	private boolean onlyForBroadcast;
	private boolean onlyForQueue;

	public DestinationTreeViewerFilter(IProject project, ELEMENT_TYPES[] types,
			boolean onlyForBroadcast, boolean onlyForQueue) {
		super(project, types);
		this.onlyForBroadcast = onlyForBroadcast;
		this.onlyForQueue = onlyForQueue;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean select = super.select(viewer, parentElement, element);
		if (element instanceof ChannelDestinationNode) {
			
			ChannelDestinationNode node = (ChannelDestinationNode) element;
			Entity entity = node.getEntity();
			if (entity instanceof Destination) {
				Destination dest = (Destination) entity;
				if(onlyForBroadcast){
					select = false;
					DRIVER_TYPE driverType = dest.getDriverConfig().getDriverType();
					if (driverType.getName().equals(DriverManagerConstants.DRIVER_RV)) {
						select = true;
					} else if (driverType.getName().equals(DriverManagerConstants.DRIVER_JMS)) {
						PropertyMap instance = dest.getProperties();
						boolean isTopic = true;
						for (int i = 0; i < instance.getProperties().size(); i++) {
							Entity prop = instance.getProperties().get(i);
							if (prop instanceof SimpleProperty) {
								SimpleProperty simpleProperty = (SimpleProperty) prop;
								if (simpleProperty.getName().equals("Queue")) {
									isTopic = !Boolean.parseBoolean(simpleProperty
											.getValue());
								}
							}
						}
						select = isTopic;
					}
				}else if(onlyForQueue){
					select = false;
					DRIVER_TYPE driverType = dest.getDriverConfig().getDriverType();
					if (driverType.getName().equals(DriverManagerConstants.DRIVER_HTTP)) {
						select = true;
					} else if (driverType.getName().equals(DriverManagerConstants.DRIVER_JMS)) {
						PropertyMap instance = dest.getProperties();
						boolean isQueue = true;
						for (int i = 0; i < instance.getProperties().size(); i++) {
							Entity prop = instance.getProperties().get(i);
							if (prop instanceof SimpleProperty) {
								SimpleProperty simpleProperty = (SimpleProperty) prop;
								if (simpleProperty.getName().equals("Queue")) {
									isQueue = Boolean.parseBoolean(simpleProperty
											.getValue());
								}
							}
						}
						select = isQueue;
					}
				}
				
			}
		}else if(element instanceof IFile ){
			IFile file = (IFile)element;
			if(file.getFileExtension().equals("channel")){
				DesignerElement res = IndexUtils
						.getElement(file);
				EntityElement eEle = (EntityElement)res;
				if(onlyForBroadcast)
					select = hasBroadcastDestination((Channel)eEle.getEntity());
				else if(onlyForQueue)
					select = hasQueueDestination((Channel)eEle.getEntity());
			}
			
		}
		return select;
	}
	
	
	private boolean hasBroadcastDestination(Channel channel){
		for(Destination dest:channel.getDriver().getDestinations()){
			if(isBroadcastDestination(dest)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isBroadcastDestination(Destination dest) {
		boolean broadcast = false;
		DRIVER_TYPE driverType = dest.getDriverConfig().getDriverType();
		if (driverType.getName().equals(DriverManagerConstants.DRIVER_RV)) {
			broadcast = true;
		} else if (driverType.getName().equals(
				DriverManagerConstants.DRIVER_JMS)) {
			PropertyMap instance = dest.getProperties();
			for (int i = 0; i < instance.getProperties().size(); i++) {
				Entity prop = instance.getProperties().get(i);
				if (prop instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty) prop;
					if (simpleProperty.getName().equals("Queue")) {
						broadcast = !Boolean.parseBoolean(simpleProperty
								.getValue());
					}
				}
			}
		}
		return broadcast;
	}
	
	private boolean hasQueueDestination(Channel channel){
		for(Destination dest:channel.getDriver().getDestinations()){
			if(isQueueDestination(dest)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isQueueDestination(Destination dest) {
		boolean queue = false;
		DRIVER_TYPE driverType = dest.getDriverConfig().getDriverType();
		if (driverType.getName().equals(DriverManagerConstants.DRIVER_HTTP)) {
			queue = true;
		} else if (driverType.getName().equals(
				DriverManagerConstants.DRIVER_JMS)) {
			PropertyMap instance = dest.getProperties();
			for (int i = 0; i < instance.getProperties().size(); i++) {
				Entity prop = instance.getProperties().get(i);
				if (prop instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty) prop;
					if (simpleProperty.getName().equals("Queue")) {
						queue = Boolean.parseBoolean(simpleProperty
								.getValue());
					}
				}
			}
		}
		return queue;
	}

}