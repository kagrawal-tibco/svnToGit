package com.tibco.cep.studio.ui.editors.channels;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.ChannelFormEditorInput;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.forms.AbstractChannelSaveableEditorPart;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ChannelFormEditor extends AbstractChannelSaveableEditorPart implements IGotoMarker {

	public final static String ID = "com.tibco.cep.studio.ui.editors.editor.channel";
	public ChannelFormViewer channelFormViewer;
	public ChannelAdvancedFormViewer channelFormAdvancedViewer;
	private ChannelFormFeederDelegate delegate;
	
	protected boolean destinationReset = false;
	protected boolean channelConfigurationReset = false;
	
	private String choiceValue = null;
	private String propertyName = null;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage() throws PartInitException {
		//Hard cord for AS new property compability
		//Fix BE-18884 (Same as BE-18553)
		Channel channel = (Channel) getEntity();
		String timescopeStr =  "TimeScope";
		String distributionScopeStr = "DistributionScope";
		String prefetchStr = "Prefetch";
        if (channel.getDriver().getDriverType().getName().equals("ActiveSpaces")) {
        	EList<Destination> dests = channel.getDriver().getDestinations();
    		for (Destination dest : dests) {
			   PropertyMap pmap = dest.getProperties();
			   EList<Entity> props = pmap.getProperties();
			   boolean hasTimeScope = false;
			   boolean hasDistributionScope = false;
			   boolean hasPrefetch = false;
			   for (Entity entity : props) {
				   if(entity.getName().equals(timescopeStr)){
					   hasTimeScope = true;
					   break;
				   }
			   }
			   if(!hasTimeScope){
				   SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
			       sp.setName("TimeScope");
			       sp.setValue("ALL");
			       props.add(sp);
			   } 
			   for (Entity entity : props) {
				   if(entity.getName().equals(distributionScopeStr)){
					   hasDistributionScope = true;
					   break;
				   }
			   }
			   if(!hasDistributionScope){
				   SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
			       sp.setName("DistributionScope");
			       sp.setValue("ALL");
			       props.add(sp);
			   } 
			   for (Entity entity : props) {
				   if(entity.getName().equals(prefetchStr)){
					   hasPrefetch = true;
					   break;
				   }
			   }
			   if(!hasPrefetch){
				   SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
			       sp.setName("Prefetch");
			       sp.setValue("-1");
			       props.add(sp);
			   } 
    		}
        	
        }else if(channel.getDriver().getDriverType().getName().equals("JMS")){
        	handleJMSAddedProperties();
        }
		
		channelFormViewer = new ChannelFormViewer(this, this.project.getName());
		channelFormViewer.createPartControl(getContainer());
		pageIndex = addPage(channelFormViewer.getControl());
		setPageText(pageIndex, "Channel");
		this.setActivePage(0);
		this.setForm(channelFormViewer.getForm());
		try {
			delegate = new ChannelFormFeederDelegate(this.project.getName());
			showAdvancedPage();
		} catch (final Exception e) {
			throw new PartInitException(e.getMessage());
		}
	}
	
	private void handleJMSAddedProperties() {
		
		String sharedSubscriptionName =  "SharedSubscriptionName";
		EList<Destination> dests = channel.getDriver().getDestinations();
		for (Destination dest : dests) {
			   PropertyMap pmap = dest.getProperties();
			   EList<Entity> props = pmap.getProperties();
			   handlePropAdd(props, sharedSubscriptionName, "");
			   //add additional props here in future
 		}
	}
	
	private void handlePropAdd(EList<Entity> props, String addedPropName, String addedPropVal){
		boolean hasProperty = false;
		for (Entity entity : props) {
			   if(entity.getName().equals(addedPropName)){
				   hasProperty = true;
				   break;
			   }
		   }
		   if(!hasProperty){
			   SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
		       sp.setName(addedPropName);
		       sp.setValue(addedPropVal);
		       props.add(sp);
		   } 
	}

	/**
	 * Show Advanced Page for Meta data properties 
	 * @throws PartInitException
	 */
	public void showAdvancedPage() throws PartInitException {
		traverseDriverExtendedConfiguration();
		if (choiceValue != null) {
			addAdvancedPage();
		} else {
			if (getPageCount() > 1) {
				removePage(1);
				if(channelFormAdvancedViewer != null) {
					channelFormAdvancedViewer.dispose();
				}
			}
		}
		((CTabFolder) getContainer()).redraw();
	}

	/**
	 * Add Advanced Page
	 * @throws PartInitException
	 */
	private void addAdvancedPage() throws PartInitException {
		channelFormAdvancedViewer = new ChannelAdvancedFormViewer(this, this.project.getName(), channelFormViewer.getChannel(),this.project);
		channelFormAdvancedViewer.createPartControl(getContainer(), channelFormViewer.getChannel());
		pageIndex = addPage(channelFormAdvancedViewer.getControl());
		setPageText(pageIndex, "Advanced");
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			createModel(input);
			Channel channel = getChannel();
//			channel.eAdapters().add(new ChannelAdapterImpl(this));
 			setChannel(channel);
			setProject(file.getProject());
			if (input instanceof ChannelFormEditorInput) {
				channelFormEditorInput = (ChannelFormEditorInput)input;
			} else {
				channelFormEditorInput = new ChannelFormEditorInput(file, channel);
			}
			
			super.init(site, channelFormEditorInput);
			
			site.getPage().addPartListener(partListener);
		} else {
			super.init(site, input);
			Entity e = getEntity();
			if (e instanceof Channel) {
				setChannel((Channel) e);
			}
		}
	}
	
	@Override
	public void dispose(){
		try{
			if (channelFormViewer != null) {
				channelFormViewer.dispose();
			}
			if(channelFormAdvancedViewer != null) {
				channelFormAdvancedViewer.dispose();
			}
			if(partListener != null && getSite() != null && getSite().getPage() != null) {
				getSite().getPage().removePartListener(partListener);
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			super.dispose();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This listens for when the outline becomes active
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated(IWorkbenchPart p) {
			if (p == ChannelFormEditor.this) {
				handleActivate();
				Entity entity = StudioUIPlugin.getDefault().getSelectedEntity();
				if (entity != null && entity instanceof Destination) {
					Destination destination = (Destination)entity;
					channelFormViewer.setDefaultDestinationSelection(destination.getName());
				}
				StudioUIPlugin.getDefault().setSelectedEntity(null);
			}
		}

		public void partBroughtToTop(IWorkbenchPart p) {
		}

		public void partClosed(IWorkbenchPart p) {
		}

		public void partDeactivated(IWorkbenchPart p) {
		}

		public void partOpened(IWorkbenchPart p) {
		}
	};
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		createModel(file);
		setEntity(null);
		Channel channel = getChannel();	
        ((ChannelFormEditorInput)getEditorInput()).setChannel(channel);
        channelFormViewer.doRefresh(channel);
        if(channelFormAdvancedViewer != null) {
        	channelFormAdvancedViewer.doRefresh(channel);
        }
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
	
	public void traverseDriverExtendedConfiguration() {
		ExtendedConfiguration extendedConfiguration = channel.getDriver().getExtendedConfiguration();
		choiceValue = null;
		propertyName = null;
		if(extendedConfiguration != null) {
			final String type = channel.getDriver().getDriverType().getName();
			if (delegate.getExtendedConfiguration(type).size() > 0) {
				final List<String> extenConfigChoices = delegate.getExtenConfigChoices(type);
                final EList<SimpleProperty> properties = extendedConfiguration.getProperties();
              
				boolean found = false;
				for(final String choice:extenConfigChoices) {
					for(final SimpleProperty property:properties) {
						if(property.getValue().equals(choice)) {
							List<PropertyConfiguration> driverProperties = delegate.getExtendedConfiguration(type);
							if(driverProperties != null ) {
								for (PropertyConfiguration propConfig : driverProperties) {
									if(property.getName().equals(propConfig.getPropertyName())) {
										propertyName = propConfig.getDisplayName();
										break;
									}
								}
							}
							choiceValue = property.getValue();
							found = true;
							break;
						}
						
					}
					if(found) {
						break;
					}
				}
			}
		}
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if(newPageIndex == 1 ) {
			
			traverseDriverExtendedConfiguration();
			
			Control[] children = channelFormAdvancedViewer.getExtndComposite().getChildren();
			for(Control child:children) {
				child.dispose();
			}
			if(choiceValue != null) {
				if (channel.getDriver().getChoice() == null || !channel.getDriver().getChoice().getValue().equals(choiceValue)) {
					EditorUtils.createExtendedConfigurationForChoice(channel.getDriver(), delegate, channel.getDriver().getDriverType().getName(), choiceValue);
				}
				channelFormAdvancedViewer.createPagePerChoice(choiceValue, propertyName);
			}
			channelFormAdvancedViewer.setExtendedConfigurationVisible(choiceValue, channel.getDriver().getDriverType().getName());
			//channelFormAdvancedViewer.setExtendedConfigurationVisible(choiceValue, channel.getDriver().getDriverType().getName());
			
			// Workaround for Linux Platforms
			// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
			// user needs to close/re-open the editors
			StudioUIUtils.resetPerspective();
			
		} else {
			if(channelFormAdvancedViewer != null) {
				channelFormAdvancedViewer.setExtendedConfigurationVisible(null,null);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractChannelSaveableEditorPart#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (IGotoMarker.class.equals(adapter)) {
			return this;
		} else {
			return super.getAdapter(adapter);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorPart#setFocus()
	 */

	public void setFocus() {
		super.setFocus();
		channelFormViewer.getViewer().getTable().setFocus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		try {
			if (marker == null) {
				return;
			}
			String destination = marker.getAttribute(IResourceValidator.DESTINATION_NAME_ATTRIBUTE, null);
			if (destination != null) {
				channelFormViewer.setDefaultDestinationSelection(destination);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isChannelConfigurationReset() {
		return channelConfigurationReset;
	}

	public void setChannelConfigurationReset(boolean channelConfigurationReset) {
		this.channelConfigurationReset = channelConfigurationReset;
	}

	public boolean isDestinationReset() {
		return destinationReset;
	}

	public void setDestinationReset(boolean destinationReset) {
		this.destinationReset = destinationReset;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#updateTab()
	 */
	protected void updateTab() {
		//TODO : auto generated
	}
}