package com.tibco.cep.studio.ui.forms;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_HTTP;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_LOCAL;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_KAFKA;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_KAFKA_STREAMS;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createExtendedConfiguration;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createExtendedPropertyInstanceWidgets;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.createPropertyInstanceWidgets;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getInstanceDriverType;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.isOnlyExtendedPropertiesPresent;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormFeederDelegate;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractChannelFormViewer extends AbstractMasterDetailsFormViewer{
	
	protected Channel channel;
	protected Text channelNameText;
	protected Text channelDescText;
	protected Combo driverCombo;
	protected Combo methodsConfigCombo;
	protected Label methodsConfigLabel;

	protected Section configSection;
	protected ScrolledPageBook configPageBook;
	protected ScrolledPageBook extndConfigPageBook;
	protected Composite extndComposite;

	public static final String CONFIG_EMPTY_PAGE = "EmptyDetailsInfo";
	public static final String RESOURCE_CONFIG_PAGE = "Resource";

	protected static final int Ws1 = 35;
	protected static final int Ws2 = 120;
	
	protected Text resourceText;

	protected Text serviceText;
	protected Text networkText;
	protected Text destinationText;

	protected Text providerURL;
	protected Text userName;
	protected Text password;
	protected Text isTransacted;
	protected Text clientId;
	
	protected Combo serverTypeCombo;

	protected Composite details; 
	protected TableViewer viewer; 

	protected ChannelFormFeederDelegate delegate;

	protected AbstractSaveableEntityEditorPart editor;
	
	protected String[] driversArray;
	protected PropertyMap properties;
	protected HashMap<Object,Object> controls;
	protected Section extndconfigSection;
	
	public AbstractSaveableEntityEditorPart getEditor() {
		return editor;
	}

	protected void setEditor(AbstractSaveableEntityEditorPart editor) {
		this.editor = editor;
	}

	protected TableModel getExtnPropertyTableModel() {
		Vector<String> columnIdentifiers = new Vector<String>(2);
		columnIdentifiers.add("Name");
		columnIdentifiers.add("Value");
		return new DefaultTableModel(columnIdentifiers, 0);
	}

	protected EditingDomain getEditingDomain() {
		if (editor instanceof IEditingDomainProvider) {
			IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) editor;
			return editingDomainProvider.getEditingDomain() ;
		}
		return null ; 
	}

	public class ConfigModifyListener implements ModifyListener{

		public ConfigModifyListener(){
			//TODO
		}

		public void modifyText(ModifyEvent e) {
			
			String methodType = methodsConfigCombo.getText();
			
			CONFIG_METHOD configMethod = methodType.equals("Resource") ? CONFIG_METHOD.REFERENCE : CONFIG_METHOD.PROPERTIES;
			if (channel.getDriver().getConfigMethod().equals(configMethod)) {
				return;
			}
			
			if (methodType.equals("Resource")) {
				configPageBook.showPage(RESOURCE_CONFIG_PAGE);
				configSection.setText("Resource       ");
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.heightHint = 50;
				details.setLayoutData(data);
				setConfigVisible();
				channel.getDriver().setConfigMethod(configMethod);
				//Do not Removing the old Properties available
//				channel.getDriver().setProperties(null);
				editor.modified();
			} else {
				configSection.setText("Properties");
				String driverType = driverCombo.getText();

				if(!channel.getDriver().getConfigMethod().getName().equalsIgnoreCase("Properties")){
					channel.getDriver().setConfigMethod(configMethod);
					
					properties = channel.getDriver().getProperties();
					if(properties == null){
						properties = getInstanceDriverType(delegate, channel.getDriver().getDriverType().getName());
						channel.getDriver().setProperties(properties);
						createPropertyInstanceWidgets(properties,channel.getDriver().getDriverType().getName(), controls);
					}
					
					// BE-24990: workaround for case in which driver.xml was updated with new properties, but the channel was created against old drivers.xml.  In this case, the added properties are missing from .channel file
					List<PropertyConfiguration> driverProperties = delegate.getDriverProperties(driverType);
					if (properties.getProperties().size() < driverProperties.size()) {
						PropertyMap updatedMap = getInstanceDriverType(delegate, channel.getDriver().getDriverType().getName());
						addMissingProperties(properties, updatedMap);
					}
					if (driverType != null) {
						configPageBook.showPage(driverType);
						GridData data = new GridData(GridData.FILL_HORIZONTAL);
						data.heightHint = driverProperties.size()* 45;
						details.setLayoutData(data);
					}
					boolean isOnlyExtendedConfig = isOnlyExtendedPropertiesPresent(driverType, delegate);
					if (isOnlyExtendedConfig) {
						configSection.setExpanded(false);
						configSection.setEnabled(false);
						configSection.layout();
					} else {
						setConfigVisible();
					}
					editor.modified();
				}
			}
		}
		private void setConfigVisible(){
			configSection.setEnabled(true);
			configSection.setExpanded(false);
			configSection.setExpanded(true);
			configSection.layout();
		}
	}

	public class DriverModifyListener implements ModifyListener {

		private String selectedDriverType;

		public DriverModifyListener() {
			//TODO
		}

		public String getDriverType() {
			return selectedDriverType;
		}

		public void modifyText(ModifyEvent e) {
			selectedDriverType = driverCombo.getText();
			String methodType = methodsConfigCombo.getText();
			
			//don't show Resource section for local driver-type
			if (DRIVER_LOCAL.equals(selectedDriverType)){
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.heightHint = 0;
				details.setLayoutData(data);
				setConfigVisible(false);
				setMethodsSectionVisible(false);
				configPageBook.showEmptyPage();
			} else {
				GridData data = new GridData(GridData.FILL_HORIZONTAL);
				data.heightHint = 50;
				details.setLayoutData(data);
				setConfigVisible(true);
				setMethodsSectionVisible(true);
				//Populating the existing Config Methods
				//Resource
				if (channel.getDriver().getConfigMethod() != null) {
					String reference = channel.getDriver().getReference();
					if (reference != null) {
						resourceText.setText(reference);
					}
				}
			}
			
			if (!channel.getDriver().getDriverType().getName().equals(selectedDriverType)) {
				Command command = null;
				if (channel.getDriver().getDriverType().getName().equals(DriverManagerConstants.DRIVER_HTTP)) {
					DriverConfig newDriverConfig = ChannelFactory.eINSTANCE.createDriverConfig();
					newDriverConfig.setChannel(channel);
					command = new SetCommand(getEditingDomain(), channel,
							ChannelPackage.eINSTANCE.getChannel_Driver(),
							newDriverConfig) ;			
					EditorUtils.executeCommand(editor, command);
					channel.getDriver().setConfigMethod(CONFIG_METHOD.REFERENCE);
				}
				if (DriverManagerConstants.DRIVER_HTTP == selectedDriverType.intern()) { 
					DriverConfig newDriverConfig = 	ChannelFactory.eINSTANCE.createHttpChannelDriverConfig();
					newDriverConfig.setChannel(channel);
					command = new SetCommand(getEditingDomain(), channel,
							ChannelPackage.eINSTANCE.getChannel_Driver(),
							newDriverConfig) ;	
					EditorUtils.executeCommand(editor, command);
				}
				
				DRIVER_TYPE newDriverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();
				newDriverType.setName(selectedDriverType);
				
			    command = new SetCommand(getEditingDomain(), channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_DriverType(), newDriverType);			
			    EditorUtils.executeCommand(editor, command);
				
			    command = new SetCommand(getEditingDomain(), channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Label(), newDriverType);			
			    EditorUtils.executeCommand(editor, command);
				
				command = new SetCommand(getEditingDomain(), channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Properties(), null);			
				EditorUtils.executeCommand(editor, command);
			    
				RemoveCommand removeCommand = new RemoveCommand(editor.getEditingDomain(), channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Destinations(), channel.getDriver().getDestinations());
				EditorUtils.executeCommand(editor, removeCommand);
			    
			    properties = getInstanceDriverType(delegate, channel.getDriver().getDriverType().getName());
				
			    command = new SetCommand(getEditingDomain(),channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Properties(), properties) ;			
			    EditorUtils.executeCommand(editor, command);
			    
			    createPropertyInstanceWidgets(properties,channel.getDriver().getDriverType().getName(), controls);
			    
			    resourceText.setText("");

			    command = new SetCommand(getEditingDomain(),channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Reference(), null) ;			
			    EditorUtils.executeCommand(editor, command);
				
				command = new SetCommand(getEditingDomain(),channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_ExtendedConfiguration(), null) ;			
				EditorUtils.executeCommand(editor, command);
				
				command = new SetCommand(getEditingDomain(),channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Choice(), null) ;			
				EditorUtils.executeCommand(editor, command);
				
				if (delegate.getExtendedConfiguration(selectedDriverType).size() > 0) {
					createExtendedConfiguration(channel.getDriver(), delegate, selectedDriverType);
					createExtendedPropertyInstanceWidgets(channel.getDriver().getExtendedConfiguration(), channel.getDriver().getDriverType().getName(), controls);
				}
				
				setExtendedConfigurationVisible(selectedDriverType);
				
				if (DriverManagerConstants.DRIVER_HTTP == selectedDriverType.intern()) { 
					if (serverTypeCombo != null) {
						serverTypeCombo.setText("TOMCAT");
					}
					serverTypeCombo.setEnabled(false);
					channel.getDriver().setConfigMethod(CONFIG_METHOD.REFERENCE);
				}
				
				try {
					((ChannelFormEditor)getEditor()).showAdvancedPage();
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
				
				//For HTTP Channel, setMethodConfig as Resource 
				//Disabling Methods Of Configuration Combo field 
				if (DRIVER_HTTP.equals(channel.getDriver().getDriverType().getName())) {
					methodsConfigCombo.setText("Resource");
					methodsConfigCombo.setEnabled(false);
					configPageBook.showPage(RESOURCE_CONFIG_PAGE);
				} else if (DRIVER_KAFKA.equals(channel.getDriver().getDriverType().getName())
						|| DRIVER_KAFKA_STREAMS.equals(channel.getDriver().getDriverType().getName())) {
					methodsConfigCombo.setText("Resource");
					methodsConfigCombo.setText("Properties");
					methodsConfigCombo.setEnabled(false);
				} else {
					if (methodType.equals("Resource")) {
						methodsConfigCombo.setText("Properties");
						methodsConfigCombo.setText("Resource");
					} else {
						methodsConfigCombo.setText("Resource");
						methodsConfigCombo.setText("Properties");
					}
					methodsConfigCombo.setEnabled(true);
				}
			}
		}
		
		private void setConfigVisible(boolean visible) {
			configSection.setExpanded(false);
			configSection.setExpanded(true);
			configSection.setVisible(visible);
		}
		
		private void setMethodsSectionVisible(boolean visible) {
			methodsConfigLabel.setVisible(visible);
			methodsConfigCombo.setVisible(visible);
			
		}
	}

	public class ExtendConfigModifyListener implements ModifyListener {

		private String selectedDriverType;

		public ExtendConfigModifyListener() {
			//TODO
		}

		public String getDriverType() {
			return selectedDriverType;
		}

		public void modifyText(ModifyEvent e) {
			Combo type = null;

			if(e.widget instanceof Combo) {
				type = (Combo) e.widget;
				final Choice choice = channel.getDriver().getChoice();
				if(choice == null)
					return;
				if(choice.getValue().equals(type.getItem(type.getSelectionIndex()))) {
					return;
				}
				selectedDriverType = driverCombo.getText();
				Command command = new SetCommand(getEditingDomain(),channel.getDriver(), ChannelPackage.eINSTANCE.getDriverConfig_Choice(), null) ;			
				EditorUtils.executeCommand(editor, command);
				if(type != null) {
					EditorUtils.createExtendedConfigurationForChoice(channel.getDriver(), delegate, selectedDriverType, type.getItem(type.getSelectionIndex()));
				}
			}

		}
	}
	/**
	 * @param driverType
	 */
	protected void setExtendedConfigurationVisible(String driverType) {
		List<PropertyConfiguration> extendedConfigurations = delegate.getExtendedConfiguration(driverType);
		if (extendedConfigurations.size() > 0) {
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.heightHint = extendedConfigurations.size() * 30;
			extndComposite.setLayoutData(data);
			extndconfigSection.setEnabled(true);
			extndconfigSection.setExpanded(true);
			extndConfigPageBook.showPage(driverType);
		} else {
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.heightHint = 0;
			extndComposite.setLayoutData(data);
			extndConfigPageBook.showEmptyPage();
			extndconfigSection.setExpanded(false);
			extndconfigSection.setEnabled(false);
			getForm().getBody().layout();
		}
	}
	
	public void addMissingProperties(PropertyMap currProperties, PropertyMap driverMap) {
		EList<Entity> driverProps = driverMap.getProperties();
		EList<Entity> currPropList = currProperties.getProperties();
		for (Entity entity : driverProps) {
			if (!propertyExists(entity, currPropList)) {
				currPropList.add(EcoreUtil.copy(entity));
			}
		}
	}

	private boolean propertyExists(Entity entity, EList<Entity> currPropList) {
		for (Entity propEntity : currPropList) {
			if (propEntity.getName().equals(entity.getName())) {
				return true;
			}
		}
		return false;
	}

	public ChannelFormFeederDelegate getDelegate() {
		return delegate;
	}
	
	public Combo getDriverCombo() {
		return driverCombo;
	}

	public Channel getChannel() {
		return channel;
	}

        public TableViewer getViewer() {
                return viewer;
        }
}
