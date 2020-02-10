package com.tibco.cep.studio.sb.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.driver.sb.SBConstants;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.util.PasswordUtil;
import com.tibco.cep.studio.sb.ui.util.Messages;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class SBConnectionWizardPage extends WizardPage {

	private static final String[] schemaExtensions = new String[] { "*.sbapp", "*.sbint" };
	private static String[] sourceItems;
	private int schemaSource = 0;
	
	static {
		sourceItems = new String[2];
		sourceItems[SBServerDetails.SRC_SERVER] = "StreamBase Server";
		sourceItems[SBServerDetails.SRC_NAMED_SCHEMA] = "StreamBase Named Schema";
	}

	private Composite sourceDetailsComposite;
	private Composite namedSchemaDetailsComposite;
//	protected String serverURI;
//	protected String uname;
//	protected String password;
//	protected String namedSchemaLocation;
	private Text serverURIText;
	private Text passText;
	private Text unameText;
	private Text sbChannelText;
	private IProject currentProject;
	
	private SBServerDetails sbServerDetails = new SBServerDetails();
	protected boolean createChannel;
	private Text namedSchemaLocText;
	
	protected SBConnectionWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		setMessage(Messages.getString("new.sbwizard.connection.desc"));
	}

	@Override
	public void createControl(Composite parent) {
		final Composite comp = new Composite(parent, SWT.NULL);
		comp.setLayout(new GridLayout(2, true));
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		Label configType = new Label(comp, SWT.NULL);
		configType.setText("StreamBase schema source: ");
		
		final Combo schemaSourceCombo = new Combo(comp, SWT.READ_ONLY);
		schemaSourceCombo.setItems(sourceItems);
		schemaSourceCombo.select(SBServerDetails.SRC_SERVER);
		
		addServerDetails(comp);
		addNamedSchemaDetails(comp);
		
		schemaSourceCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selIdx = schemaSourceCombo.getSelectionIndex();
				if (selIdx != schemaSource) {
					schemaSource = selIdx;
					updateSourceDetails();
					comp.layout();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		setControl(comp);
	}

	protected void updateSourceDetails() {
		sbServerDetails.setConfigType(schemaSource);
		switch (schemaSource) {
		case SBServerDetails.SRC_SERVER:
			((GridData)namedSchemaDetailsComposite.getLayoutData()).exclude = true;
			((GridData)sourceDetailsComposite.getLayoutData()).exclude = false;
			sourceDetailsComposite.setVisible(true);
			namedSchemaDetailsComposite.setVisible(false);
			setMessage(Messages.getString("new.sbwizard.connection.desc"));
			setErrorMessage(null);
			setPageComplete(true);
			break;

		case SBServerDetails.SRC_NAMED_SCHEMA:
		{
			((GridData)namedSchemaDetailsComposite.getLayoutData()).exclude = false;
			((GridData)sourceDetailsComposite.getLayoutData()).exclude = true;
			namedSchemaDetailsComposite.setVisible(true);
			sourceDetailsComposite.setVisible(false);
			setMessage(Messages.getString("new.sbwizard.connection.typecheck.info"), INFORMATION);
			if (namedSchemaLocText.getText() != null && namedSchemaLocText.getText().trim().length() > 0) {
				boolean valid = validateSchemaLoc(namedSchemaLocText.getText());
				setPageComplete(valid);
				if (!valid) {
					setErrorMessage(Messages.getString("new.sbwizard.connection.file.invalid"));
				}
			} else {
				setPageComplete(false);
			}
			break;
		}	
		default:
			break;
		}
	}

	private void addNamedSchemaDetails(Composite comp) {
		namedSchemaDetailsComposite = new Composite(comp, SWT.NULL);
		namedSchemaDetailsComposite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		namedSchemaDetailsComposite.setLayoutData(data);
		
		Group namedSchemaGroup = new Group(namedSchemaDetailsComposite, SWT.NONE);
		namedSchemaGroup.setText("Named schema details");
		namedSchemaGroup.setLayout(new GridLayout(3, false));
		namedSchemaGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label l = new Label(namedSchemaGroup, SWT.NULL);
		l.setText("StreamBase Named Schema location: ");
		
		namedSchemaLocText = new Text(namedSchemaGroup, SWT.BORDER);
		namedSchemaLocText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button browseButton = new Button(namedSchemaGroup, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseNamedSchema(namedSchemaLocText);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		namedSchemaLocText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				String locText = namedSchemaLocText.getText();
				if (validateSchemaLoc(locText)) {
					sbServerDetails.setNamedSchemaPath(locText);
					setErrorMessage(null);
					setPageComplete(true);
				} else if (locText != null && locText.length() > 0) {
					setErrorMessage(Messages.getString("new.sbwizard.connection.file.invalid"));
					setPageComplete(false);
				} else {
					setErrorMessage(null);
				}
			}
		});
		namedSchemaDetailsComposite.setVisible(false);
	}

	protected boolean validateSchemaLoc(String locText) {
		return new File(locText).exists();
	}

	protected void browseNamedSchema(Text namedSchemaLocText) {
		String text = namedSchemaLocText.getText();
		FileDialog dialog = new FileDialog(getWizard().getContainer().getShell());
		dialog.setFilterPath(text);
		dialog.setFilterExtensions(schemaExtensions);
		String file = dialog.open();
		if (file != null && file.length() > 0) {
			namedSchemaLocText.setText(file);
		}
	}

	private void addServerDetails(Composite comp) {
		sourceDetailsComposite = new Composite(comp, SWT.NULL);
		sourceDetailsComposite.setLayout(new GridLayout(3, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		sourceDetailsComposite.setLayoutData(data);

		final Button popFromChLabel = new Button(sourceDetailsComposite, SWT.CHECK);
		popFromChLabel.setText("Populate server details from existing StreamBase Channel");
		GridData lData = new GridData();
		lData.horizontalSpan = 3;
		popFromChLabel.setLayoutData(lData);
		Label sbChLabel = new Label(sourceDetailsComposite, SWT.NULL);
		sbChLabel.setText("StreamBase Channel: ");
		
		sbChannelText = new Text(sourceDetailsComposite, SWT.BORDER);
		sbChannelText.setEnabled(false);
		sbChannelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sbChannelText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if (validateChannel()) {
					updateServerDetailsFromChannel();
				}
			}
		});
		final Button browseButton = new Button(sourceDetailsComposite, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.setEnabled(false);
		browseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseSBChannel();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		popFromChLabel.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				sbChannelText.setEnabled(popFromChLabel.getSelection());
				browseButton.setEnabled(popFromChLabel.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		Group serverGroup = new Group(sourceDetailsComposite, SWT.NONE);
		serverGroup.setText("Server details");
		serverGroup.setLayout(new GridLayout(2, false));
		GridData sgData = new GridData(GridData.FILL_HORIZONTAL);
		sgData.horizontalSpan = 3;
		serverGroup.setLayoutData(sgData);
		
		Label l = new Label(serverGroup, SWT.NULL);
		l.setText("StreamBase Server URI: ");
		
		serverURIText = new Text(serverGroup, SWT.BORDER);
		serverURIText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverURIText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				sbServerDetails.setSbServerURI(serverURIText.getText());
			}
		});
		serverURIText.setText("sb://localhost:10000/");
		
		Label unameLabel = new Label(serverGroup, SWT.NULL);
		unameLabel.setText("Username (optional): ");
		
		unameText = new Text(serverGroup, SWT.BORDER);
		unameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		unameText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				sbServerDetails.setUsername(unameText.getText());
			}
		});

		Label passLabel = new Label(serverGroup, SWT.NULL);
		passLabel.setText("Password (optional): ");
		
		passText = new Text(serverGroup, SWT.BORDER | SWT.PASSWORD);
		passText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		passText.setEchoChar('*');
		passText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				sbServerDetails.setPassword(passText.getText());
			}
		});
		

		final Button createChannelButton = new Button(serverGroup, SWT.CHECK);
		createChannelButton.setText("Create new StreamBase Channel from Server details");
		createChannelButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				createChannel = createChannelButton.getSelection();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	protected void updateServerDetailsFromChannel() {
		Channel channel = IndexUtils.getChannel(getProjectName(), sbChannelText.getText());
		DriverConfig driver = channel.getDriver();
		CONFIG_METHOD configMethod = driver.getConfigMethod();
		if (configMethod == CONFIG_METHOD.REFERENCE) {
			updateFromSharedResource(driver);
		} else {
			PropertyMap properties = driver.getProperties();
			EList<Entity> props = properties.getProperties();
			for (Entity entity : props) {
				String val = ((SimpleProperty)entity).getValue();
				if (val == null) {
					val = "";
				}
				if (SBConstants.CHANNEL_PROPERTY_SERVER_URI.getLocalName().equals(entity.getName())) {
					serverURIText.setText(val);
				} else if (SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.getLocalName().equals(entity.getName())) {
					passText.setText(PasswordUtil.getDecodedString(val));
				} else if (SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.getLocalName().equals(entity.getName())) {
					unameText.setText(val);
				}
			}
		}
		
	}

	private void updateFromSharedResource(DriverConfig driver) {
		String sharedResPath = driver.getReference();
		IProject proj = getProject();
		IFile file = proj.getFile(new Path(sharedResPath));
		if (file.exists()) {
			InputStream contents = null;
			try {
				contents = file.getContents();
				XiNode rootNode = XiSupport.getParser().parse(new InputSource(contents));
				XiNode configNode = XiChild.getChild(rootNode.getFirstChild(), SBConstants.XML_NODE_CONFIG);

				String serverURI = XiChild.getString(configNode, SBConstants.CHANNEL_PROPERTY_SERVER_URI);
				serverURIText.setText(serverURI == null ? "" : serverURI);
				String userName = XiChild.getString(configNode, SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME);
				unameText.setText(userName == null ? "" : userName);
				String password = XiChild.getString(configNode, SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD);
				passText.setText(password == null ? "" : PasswordUtil.getDecodedString(password)); 
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					contents.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private IProject getProject() {
		return currentProject;
	}

	public boolean shouldCreateChannel() {
		return createChannel;
	}

	protected boolean validateChannel() {
		Channel channel = IndexUtils.getChannel(getProjectName(), sbChannelText.getText());
		if (channel == null) {
			setErrorMessage("Channel cannot be found");
			return false;
		}
		DRIVER_TYPE driverType = channel.getDriver().getDriverType();
		if (!driverType.getName().equals("StreamBase")) {
			setErrorMessage("Selected Channel is not a StreamBase Channel");
			return false;
		}
		return true;
	}

	private String getProjectName() {
		return getProject() != null ? getProject().getName() : "";
	}

	protected void browseSBChannel() {
		String projectName = getProjectName();
		StudioResourceSelectionDialog dialog = new StudioResourceSelectionDialog(getShell(), projectName, sbChannelText.getText(), new ELEMENT_TYPES[] { ELEMENT_TYPES.CHANNEL });
		dialog.setInput(getProject());
		if (dialog.open() == IStatus.OK) {
			Object[] result = dialog.getResult();
			if (result != null && result.length > 0) {
				Object obj = result[0];
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					String fullPath = IndexUtils.getFullPath(file);
					sbChannelText.setText(fullPath);
				} else if (obj instanceof SharedEntityElement) {
					Entity sharedEntity = ((SharedEntityElement) obj).getSharedEntity();
					if(sharedEntity != null){
						sbChannelText.setText(sharedEntity.getFullPath());
					}
				} else{
					return;
				}
			}
		}
	}

	public void setProject(IProject project) {
		this.currentProject = project;
		if (currentProject != null) {
			getServerDetails().setGlobalVariables(GlobalVariablesMananger.getInstance().getProvider(currentProject.getName()));
		}
	}

	public SBServerDetails getServerDetails() {
		return sbServerDetails;
	}
	
}
