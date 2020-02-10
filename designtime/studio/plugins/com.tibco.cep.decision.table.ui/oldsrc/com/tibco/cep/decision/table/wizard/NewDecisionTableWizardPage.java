/**
 * 
 */
package com.tibco.cep.decision.table.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.provider.excel.ExcelImportListener;
import com.tibco.cep.decision.table.spi.ExcelConversionOps;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.common.legacy.adapters.RuleFunctionModelTransformer;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.filter.VirtualRuleFunctionOnlyFilter;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.widgets.VirtualRuleFunctionSelector;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

/**
 * @author hitesh
 *
 */
public class NewDecisionTableWizardPage<V extends IVRFSelectionWizard> extends EntityFileCreationWizard implements SelectionListener, ModifyListener {

	private Text virtualRFText;
	private String virtualRFPath;
	//Work with the new model
	private AbstractResource template;
	private	String projectName;
	private Text fileLocationText;
	private Button importBrowsebutton;
	private Button vrfBrowseButton;
	private boolean duplicateDT = false;
	private DesignerElement duplicateElement;
	private Button showDescription;
	private boolean importDM;
	private IFile duplicateResource; 
	
	/**
	 * A flag to indicate whether the wizard
	 * is an import wizard or new creation wizard
	 */
	private boolean isImportWizard;
	private boolean DT_NAME_CHANGE;
	
	public NewDecisionTableWizardPage(String pageName, IStructuredSelection selection, 
			String type, String virtualRFPath, String projectName, boolean importWizard) {
		super(pageName, selection, type);
		this.virtualRFPath = virtualRFPath;
		this.projectName = projectName;
		this.isImportWizard = importWizard;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createLabel(container, Messages.getString("Select_VRF"));
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		virtualRFText = createText(childContainer);
		virtualRFText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		virtualRFText.addModifyListener(this);
		
		vrfBrowseButton = new Button(childContainer, SWT.NONE);
		vrfBrowseButton.setText(Messages.getString("Browse"));
		vrfBrowseButton.addSelectionListener(this);
		
		if (isImportWizard) {
			createLabel(container, Messages.getString("Select"));
			
			Composite subContainer = new Composite(container, SWT.NONE);
			GridLayout subContainerLayout = new GridLayout(2, false);
			subContainerLayout.marginWidth = 0;
			subContainerLayout.marginHeight = 0;

			subContainer.setLayout(subContainerLayout);
			subContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			fileLocationText = createText(subContainer);
			fileLocationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			fileLocationText.addModifyListener(this);
			
			importBrowsebutton = new Button(subContainer, SWT.NONE);
			importBrowsebutton.setText(Messages.getString("Browse"));
			importBrowsebutton.addSelectionListener(this);
		}
		
		createResourceContainer(container);
		
		createLabel(container, Messages.getString("wizard.desc"));
		_typeDesc = new Text(container, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		_typeDesc.setLayoutData(gd);
		
//		showDescription = new Button(container,SWT.CHECK);
//		showDescription.setText(Messages.getString("DECISION_TABLE_SHOW_DOMAIN_DESCRIPTION"));
		
		if (virtualRFPath != null) {
			DesignerElement element = 
				IndexUtils.getElement(this.projectName, virtualRFPath, ELEMENT_TYPES.RULE_FUNCTION);
			if (element instanceof RuleElement) {
				RuleElement ruleElement = (RuleElement)element;
				RuleFunction ruleFunction = (RuleFunction) ruleElement.getRule();
				if (ruleFunction.isVirtual()) {
					virtualRFText.setText(virtualRFPath);
					transformRuleFunction(ruleFunction);
				} else {
					virtualRFText.setText("");
				}
			}
		}
		setErrorMessage(null);
		setMessage(null);
		setControl(container);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == vrfBrowseButton) {
			if (projectName ==  null || projectName.length() == 0) {
				if (getContainerFullPath() != null) {
					IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
					if (resource.exists()) {
						project = resource.getProject();
						projectName = project.getName();
					}
				}
			}
			if (projectName == null) return;
			Set<String> extensions = new HashSet<String>();
	        extensions.add("rulefunction");
			VirtualRuleFunctionSelector picker = new VirtualRuleFunctionSelector(getShell(), projectName);
			picker.addFilter(new VirtualRuleFunctionOnlyFilter(extensions));
			
			if (picker.open() == Dialog.OK) {
				if (picker.getFirstResult() instanceof RuleFunction) {
					RuleFunction vrf = (RuleFunction) picker.getFirstResult();
					virtualRFText.setText(vrf.getFullPath());
					transformRuleFunction(vrf);
				}
			}
		} else if (e.getSource() == importBrowsebutton) {
			FileDialog fd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
			String[] extensions = { "*.xls", "*.prr" };
			fd.setText(Messages.getString("DT_ImportExcel_Action_Dialog_Title"));
			fd.setFilterExtensions(extensions);
			File excelFile = null;
			if (fileLocationText != null) {
				fd.setFileName(fileLocationText.getText());
				String fileName = fd.open();
				if (fileName != null) {
					fileLocationText.setText(fileName);
					excelFile = new File(fileName);
				}
			} else {
				String fullFilename = fd.open();
				if (fullFilename != null) {
					fileLocationText.setText(fullFilename);
					excelFile = new File(fullFilename);
				}
			}
			if (excelFile == null) {
				//Cancel from wizard case.
				setPageComplete(validatePage());
			} else {
				String resourceName = excelFile.getName();
				resourceName = resourceName.substring(0, resourceName.indexOf('.'));
				resourceContainer.setResourceName(resourceName);
				setPageComplete(validatePage());
			}
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		validatePage();
	}

	public void handleEvent(Event event) {
		DT_NAME_CHANGE = true;
		setPageComplete(validatePage());
		DT_NAME_CHANGE = false;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		String dtNameText = resourceContainer.getResourceName().toString();
		String virtualRuleFunctionPath = virtualRFText.getText().toString();
		String parentFolderText = "";
		String fullFileName = "";
		
		if (fileLocationText != null) {
			fullFileName = fileLocationText.getText();
		}
		
		if (getContainerFullPath() != null) {
			parentFolderText = getContainerFullPath().toString();
		}
		
		boolean vrfname = (virtualRuleFunctionPath.length() == 0) ? false : true;
		boolean dtname = (dtNameText.length() == 0) ? false : true;
		boolean parentFolderName = (parentFolderText.length() == 0) ? false : true;
		
		DesignerElement element = 
			IndexUtils.getElement(this.projectName, virtualRuleFunctionPath);
		if(element != null) {
			transformRuleFunction((RuleFunction) ((RuleElement) element).getRule());
		}
		if (element instanceof RuleElement) {
			try{
				RuleElement ruleElement = (RuleElement)element;
				if ((checkValidAccess(virtualRuleFunctionPath)) && super.validatePage() && (isImportWizard)) {
					if ((!(fullFileName.endsWith(".xls")))) {
						setErrorMessage(Messages.getString("Select_Excel_File"));
						setPageComplete(false);
						return false;
					}
					if (((fullFileName.endsWith(".xls"))) && (dtname) && (vrfname) && (parentFolderName) 
							&& (CommonUtil.isValidName(dtNameText.trim())) && !DT_NAME_CHANGE) {
						File file = new File(fullFileName.trim());
						if (file.exists()) {
							setErrorMessage(null);
							if (!validateExcelFile(file)) {
								setPageComplete(false);
								resourceContainer.setEnabled(false);
								return false;
							}
							setPageComplete(true);
							resourceContainer.setEnabled(true);
							return true;
						} else {
							setErrorMessage(Messages.getString("No_File_Exists"));
							setPageComplete(false);
							return false;
						}
					}
					
					if (template != null && template.getName() != null && dtNameText != null) {
						if ((template.getName().trim()).equalsIgnoreCase(dtNameText.trim())) {
							setErrorMessage("RuleFunction name and Implementation Name can not be same ");
							setPageComplete(false);
							return false;
						}
					}
				}
				
				if (ruleElement != null && (checkValidAccess(virtualRuleFunctionPath)) && super.validatePage() && (!(isImportWizard))) {
					setErrorMessage(null);
					setMessage(null);
					setPageComplete(true);
					return true;
				}
				
				if (ruleElement != null && (!(checkValidAccess(virtualRuleFunctionPath))) && super.validatePage()) {
					String operation = "create";
					setErrorMessage(Messages.getString("access.resource.error", operation));
					setPageComplete(false);
					return false;
				}
			}	catch(Exception e){
				//Handle exception if user performs operation in DM and is not logged in RMS
				setErrorMessage(e.getMessage());
				setPageComplete(false);
				return false;
			}
		}else {
			setErrorMessage(Messages.getString("Select_VRF_Error"));
			setPageComplete(false);
			return false;		
		}
		
		return super.validatePage();
	}

	private boolean validateExcelFile(File file) {
		FileInputStream fin = null;
		POIFSFileSystem poifs = null;
		try {
			fin = new FileInputStream(file);
			poifs = new POIFSFileSystem(fin);
			InputStream din = poifs.createDocumentInputStream("Workbook");
			// Request object is need for event model system
			HSSFRequest req = new HSSFRequest();
			// send a copy of the table to verifier to avoid data issues
			ExcelImportListener excelImportListener = new ExcelImportListener(getProject().getName(), 
					(AbstractResource)EcoreUtil.copy(template), importDM, 
					true, new HSSFWorkbook(poifs));
			//Register record listeners outside.
			ExcelConversionOps.registerRecordListeners(req, excelImportListener);
			processWorkbookEvents(fin, poifs, din, req);
			Collection<ValidationError> excelVldErrorCollection = excelImportListener.getExcelVldErrorCollection();
			if (excelVldErrorCollection.size() > 0) {
				StringBuffer buf = new StringBuffer();
				buf.append("Invalid Excel file (");
				int size = excelVldErrorCollection.size();
				int ctr = 1;
				buf.append(size);
				buf.append(" errors): ");
				for (ValidationError validationError : excelVldErrorCollection) {
					buf.append(validationError.getErrorMessage());
					if (ctr < size) {
						buf.append(", ");
					} else {
						buf.append('.');
					}
					ctr++;
				}
				setErrorMessage(buf.toString());
				return false;
			}
		} catch (Exception e) {
		} finally {
			try {
				fin.close();
			} catch (Exception e2) {
			}
		}

		return true;
	}

	private void processWorkbookEvents(FileInputStream fin,
			POIFSFileSystem poifs, InputStream din, HSSFRequest req) {
		try {
			ExcelConversionOps.processWorkbookEvents(fin, poifs, din, req);
		} catch (Exception e) {
			//Log it
			DecisionTableUIPlugin.log(e);
			setErrorMessage("Not a valid xcel file: " + e.getMessage());
			setPageComplete(false);
		}
	}
	
	public AbstractResource getTemplate() {
		return template;
	}
	
	private void transformRuleFunction(RuleFunction vrf) {
		template = OntologyFactory.eINSTANCE.createRuleFunction();
		new RuleFunctionModelTransformer().transform(vrf, (com.tibco.cep.decisionproject.ontology.RuleFunction)template);
	}
	
	 /**
     * @param vrfNameText
     * @return
     */
    private boolean checkValidAccess(String vrfNameText) {
    	if (Utils.isStandaloneDecisionManger()) {
			List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
			Permit permit = Permit.DENY;
			if (!(roles.isEmpty()) && !(vrfNameText.isEmpty())) {
				IAction action = ActionsFactory.getAction(ResourceType.RULEFUNCTION, "add_impl");
				permit = SecurityUtil.ensurePermission(this.projectName, vrfNameText, 
						ResourceType.RULEFUNCTION, roles, action, PermissionType.BERESOURCE);
			}
			if (Permit.DENY.equals(permit)) {
				return false;
			}
		}
    	return true;
    }
    
	public boolean checkExistingDecisionTable(IResource folder, String fileName, StringBuilder duplicateFile) {
		Object[] object = CommonUtil.getResources((IContainer)folder);
		for (Object obj : object) {
			if (!(obj instanceof IContainer)) {
				DesignerElement element = IndexUtils.getElement((IFile)obj);
				if (element != null) {
					if (element.getName().equalsIgnoreCase(fileName)) {
						duplicateFile.append(((IFile)obj).getName());	
						if (element.getElementType() == ELEMENT_TYPES.DECISION_TABLE) {
							this.duplicateDT = true;
							this.duplicateElement = element;
							this.duplicateResource = (IFile)obj;
							return false;
						} else {
							this.duplicateDT = false;
							return true;
						}
					} else {
						this.duplicateDT = false;
					}
				} else {
					//Handling NonEntityResources
					IFile file = (IFile)obj;
					String name = file.getName().replace("." + file.getFileExtension(), "");
					if (name.equalsIgnoreCase(fileName)) {
						duplicateFile.append(((IFile)obj).getName());
						if (file.getFileExtension().equals("rulefunctionimpl")) {
							this.duplicateDT = true;
							return false;
						} else {
							this.duplicateDT = false;
							return true;
						}
					} else {
						this.duplicateDT = false;
					}
				}
			}
		}
		return false;
	}
    /**
	 * @return the duplicateDT
	 */
	public boolean isduplicateDT() {
		return this.duplicateDT;
	}
	
	public boolean showDescription() {
		if (showDescription != null) {
			return showDescription.getSelection();
		}
		return false;
	}
	
	public boolean isImportDM() {
		return importDM;
	}
	
	/**
	 * @param overwriteDT the duplicateDT to set
	 */
	public void setduplicateDT(boolean overwriteDT) {
		this.duplicateDT = overwriteDT;
	}
	
	/**
	 * @return the duplicateElement
	 */
	public DesignerElement getDuplicateElement() {
		return this.duplicateElement;
	}
	
	public Text getFileLocationText() {
		return fileLocationText;
	}
	
	public IFile getDuplicateResource() {
		return duplicateResource;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#isDuplicateBEResource(org.eclipse.core.resources.IResource, java.lang.String, java.lang.StringBuilder)
	 */
	@Override
	protected boolean isDuplicateBEResource(IResource resource,
			String resourceName, StringBuilder duplicateFileName) {
		boolean result = false;
		IResource containerResource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
		String containerResourceName = resourceContainer.getResourceName();
		if (isImportWizard) {
			result = checkExistingDecisionTable(containerResource, containerResourceName, duplicateFileName);
		} else {
			result = StudioResourceUtils.isDuplicateBEResource(containerResource, containerResourceName, duplicateFileName);
		}
		return result;
	}
}
