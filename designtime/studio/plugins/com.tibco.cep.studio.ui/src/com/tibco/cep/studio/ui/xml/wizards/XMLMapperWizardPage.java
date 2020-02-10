package com.tibco.cep.studio.ui.xml.wizards;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingRunner;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.mapper.ui.BEMapperInputOutputAdapter;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.mapper.ui.util.SWTMapperUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.LocalVariableSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.xml.actions.XMLMapperListeners;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.cep.studio.ui.xml.utils.Messages;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.build.MutableElement;


/**
 * 
 * @author sasahoo, ggrigore
 *
 */
public class XMLMapperWizardPage extends AbstractMapperWizardPage {
   
	private static final String UPDATE_INSTANCE_NAME = "updateInstance";
	private static final String CREATE_INSTANCE_NAME = "createInstance";
	private static final String CREATE_EVENT_NAME = "createEvent";
	private static final String CREATE_UPDATE_METRIC_NAME = "createOrUpdateMetric";
	
	
	private String validEntityPath;
	private Text functionNameText;
	private Text entityPath;
    private Button button;	
    private XMLMapperForm inputMapper;
//    private IEMapperControl mapperControl;
	private MapperInvocationContext context;
	private BEMapperInputOutputAdapter inputOutputAdapter;

	public String getNewXSLT() {
		if (MapperUtils.isSWTMapper(context.getProjectName())) {
			String newXslt = context.getXslt();
//			System.out.println(newXslt);
			return newXslt;
		} else {
			final BindingEditorPanel bePanel = getInputMapper().getBePanel();
			bePanel.stopEditing();
			TemplateBinding tb = bePanel.getCurrentTemplate();
			if (tb != null) {
				NamespaceContextRegistry nsm = MapperUtils.getNamespaceMapper();
				StylesheetBinding sb = (StylesheetBinding)tb.getParent();
				BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
				for (int i=0; i<nd.length; i++) {
					nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
				}
				String xsltTemplate = BindingRunner.getXsltFor(tb, nsm );
				StudioUIPlugin.debug(xsltTemplate);
				ArrayList<String> params = new ArrayList<String>();
				params.add(validEntityPath);
				CoercionSet cs = bePanel.getCurrentCoercionSet();
				String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, MapperCoreUtils.serializeCoercionSet(cs));
				return newXslt;
			}
		}
		return "";
	}
	
	private XMLMapperForm getInputMapper() {
		return inputMapper;
	}

	protected XMLMapperWizardPage(MapperInvocationContext context) {
		super(Messages.getString("mapper.window.title"), context.getProjectName());
		setTitle(Messages.getString("mapper.title"));
		setDescription(Messages.getString("mapper.description"));
		setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/mapper.png"));
		if (!context.isMapperEditable()) {
			setMessage(Messages.getString("mapper.read.only"));
		}
		this.context = context;
	}

	@Override
	protected void createFunctionPart(final IManagedForm managedForm, Composite parent) {
		functionSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		functionSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		functionSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		functionSection.setText(Messages.getString("mapper.section.function"));
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		functionSection.setLayoutData(gd);		
		
		Composite sectionClient = toolkit.createComposite(functionSection, SWT.BORDER);
		functionSection.setClient(sectionClient);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		sectionClient.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		sectionClient.setLayoutData(gd);		
        
		toolkit.createLabel(sectionClient, Messages.getString("mapper.function.name"),  SWT.NONE | SWT.READ_ONLY);
		
		functionNameText = toolkit.createText(sectionClient,"",  SWT.BORDER | SWT.READ_ONLY);
		ExpandedName fnName = context.getFunction().getName();
		functionNameText.setText(fnName.namespaceURI+"."+fnName.localName);
		functionNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) { /* TODO */ }
		});
		functionNameText.setEnabled(false);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);		
		functionNameText.setLayoutData(gd);
		
		toolkit.createLabel(sectionClient,"");
		String labelStr = isUpdateInstance() ? Messages.getString("mapper.instance.name") : Messages.getString("mapper.entity.path");
		toolkit.createLabel(sectionClient, labelStr,  SWT.NONE);
		entityPath = toolkit.createText(sectionClient,"",  SWT.BORDER);
	
		if (context.getFunction() != null) {
			@SuppressWarnings("rawtypes")
			List receivingParams = XSTemplateSerializer.getReceivingParms(context.getXslt());
			if (context.getFunction() != null) {
				if (receivingParams.size() > 0) {
					String path = (String) receivingParams.get(0);
					entityPath.setText(path);
					validEntityPath = path;
				} else {
					entityPath.setText("");
				}
			}
		} else {
			entityPath.setText("");
		}
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		entityPath.setLayoutData(gd);
		entityPath.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent event) { 
				String text = entityPath.getText();
				if (checkPath(text)) {
					validEntityPath = text;
					updateMapper();
				}
			} 
		});
		entityPath.setEnabled(context.isMapperEditable());
		
		button = new Button(sectionClient, SWT.NONE);
		button.setText(Messages.getString("mapper.browse"));
		button.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				try {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(context.getProjectName());
					SelectionStatusDialog dialog = null;
					if (isUpdateInstance()) {
						List<VariableDefinition> vars = context.getDefinitions();
						List<VariableDefinition> conceptVars = new ArrayList<>();
						if (null != vars && !vars.isEmpty()) {
							// Filter concepts
							filterConcepts(project, vars, conceptVars);
						}

						String message = "Select concept instance to update";
						String title = "Concept selector";
						dialog = new LocalVariableSelectionDialog(getShell(), title, message, project.getName(),conceptVars);
					} else {
						dialog = new StudioResourceSelectionDialog(getShell(), project.getName(),entityPath.getText(), getElementType());
						((StudioResourceSelectionDialog)dialog).setInput(project);
					}
					
					if (dialog.open() == IStatus.OK) {
						Object[] result = dialog.getResult();
						if (result != null && result.length > 0) {
							Object obj = result[0];
							if (obj instanceof IFile) {
								IFile file = (IFile) obj;
								String fullPath = IndexUtils.getFullPath(file);
								entityPath.setText(fullPath);
							}else if (obj instanceof SharedEntityElement) {
				                Entity sharedEntity = ((SharedEntityElement) obj).getSharedEntity();
				                if(sharedEntity != null){
				                	entityPath.setText(sharedEntity.getFullPath());
				                }
							}else if (obj instanceof VariableDefinition) {
								String type = ((VariableDefinition) obj).getType();
								String var = "$" + ((VariableDefinition) obj).getName();// + " [" + ModelUtils.convertPackageToPath(type) + "]";
								entityPath.setText(var);
							}else{
								return;
							}
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) { } 
		});		
		button.setEnabled(context.isMapperEditable());
		
		toolkit.paintBordersFor(sectionClient);		
		
		checkPath(entityPath.getText());
	}

	@Override
	public boolean canFlipToNextPage() {
		if (!context.isMapperEditable()) {
			return false;
		}
		return super.canFlipToNextPage();
	}

	protected boolean checkPath(String path) {
		if (path == null || path.trim().length() == 0) {
			setErrorMessage("Please enter a valid entity path");
			setPageComplete(false);
			return false;
		}
		MapperInvocationContext invocationContext = ((XMLMapperWizard) getWizard()).getContext();
		if (isUpdateInstance()) {
			String varName = path.trim();
			int idx = path.indexOf('[');
			if (idx > 1) {
				varName = path.substring(0, idx).trim();
			}
			if (varName.charAt(0) == '$') {
				varName = varName.substring(1);
			}
			List<VariableDefinition> definitions = invocationContext.getDefinitions();
			for (VariableDefinition variableDefinition : definitions) {
				if (varName.equals(variableDefinition.getName())) {
					path = ModelUtils.convertPackageToPath(variableDefinition.getType());
					break;
				}
			}
		}
		MutableElement element = MapperUtils.buildElement(invocationContext, path);
		if (element == null) {
			setPageComplete(false);
			setErrorMessage("Entity '"+path+"' does not exist");
			return false;
		}
		setErrorMessage(null);
		setPageComplete(true);
		return true;
//		String invocProjectName = invocationContext.getProjectName();
//		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(invocProjectName);
//		String folderPath = "";
//		String fileName = path;
//		int idx = path.lastIndexOf('/');
//		if (idx >= 0) {
//			folderPath = path.substring(0, idx);
//			fileName = path.substring(idx+1);
//		}
//		IContainer folder = folderPath.trim().length() == 0 ? project : project.getFolder(folderPath);
//		if (!folder.exists()) {
//			setErrorMessage("Folder does not exist");
//			return false;
//		}
//		try {
//			IResource[] members = folder.members();
//			for (IResource resource : members) {
//				if (resource instanceof IFile) {
//					if (fileName.trim().equalsIgnoreCase(resource.getFullPath().removeFileExtension().lastSegment())) {
//						setErrorMessage(null);
//						return true;
//					}
//				}
//			}
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
//		setErrorMessage("Entity does not exist");
//		return false;
	}

	protected void updateMapper() {
		try {
			updateSchema();
			if (MapperUtils.isSWTMapper(context.getProjectName())) {
				inputOutputAdapter.getMapperControl().setInput(inputOutputAdapter);
			} else if (inputMapper != null) {
				XMLMapperListeners.updateMapperPanel(context, inputMapper.getCache(), inputMapper.getBePanel(), inputMapper.getEditor());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSchema() {
		// for now, we need to get the current xslt, deserialize, and reserialize with the new entity path
		// inefficient, but works until we change the storage format of the xslt
//		String xslt = context.getXslt();
//		String exXslt = XSTemplateSerializer.deSerialize(xslt, new ArrayList<Object>(), new ArrayList<Object>());
		ArrayList<String> params = new ArrayList<String>();
		params.add(getXSLTParam());
		context.setParamName(getXSLTParam());
		String newXslt = XSTemplateSerializer.serialize("", params, new ArrayList<Object>());
		context.setXslt(newXslt);
		String path = getEntityPath();
		if (isUpdateInstance()) {
			String varName = path.trim();
			int idx = path.indexOf('[');
			if (idx > 1) {
				varName = path.substring(0, idx).trim();
			}
			if (varName.charAt(0) == '$') {
				varName = varName.substring(1);
			}
			List<VariableDefinition> definitions = context.getDefinitions();
			for (VariableDefinition variableDefinition : definitions) {
				if (varName.equals(variableDefinition.getName())) {
					path = ModelUtils.convertPackageToPath(variableDefinition.getType());
					break;
				}
			}
		}
		Entity targetEntity = IndexUtils.getEntity(context.getProjectName(), path);
		if (MapperUtils.isSWTMapper(context.getProjectName())) {
			inputOutputAdapter.getMapperArgs().setTargetElement(targetEntity);
		}
	}

	private String getXSLTParam() {
		String path = entityPath.getText();
		if (isUpdateInstance()) {
			int idx = path.indexOf('[');
			if (idx > 0) {
				return path.substring(0, idx).trim();
			}
		} 
		return path;
	}

//	private String[] getElementTypes() {
//		// get the ELEMENT_TYPES based on the function
//		if (CREATE_INSTANCE_NAME.equals(context.getFunction().getName().localName)) {
//			return new String[] { IndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT) };
//		} else if (CREATE_EVENT_NAME.equals(context.getFunction().getName().localName)) {
//			return new String[] { IndexUtils.getFileExtension(ELEMENT_TYPES.SIMPLE_EVENT) };
//		} else if (CREATE_UPDATE_METRIC_NAME.equals(context.getFunction().getName().localName)) {
//			return new String[] { IndexUtils.getFileExtension(ELEMENT_TYPES.METRIC) };
//		}
//		return null;
//	}
	
	private boolean isUpdateInstance() {
		return UPDATE_INSTANCE_NAME.equals(context.getFunction().getName().localName);
	}
	
	private ELEMENT_TYPES getElementType() {
		// get the ELEMENT_TYPES based on the function
		if (CREATE_INSTANCE_NAME.equals(context.getFunction().getName().localName)) {
			return ELEMENT_TYPES.CONCEPT;
		} else if (CREATE_EVENT_NAME.equals(context.getFunction().getName().localName)) {
			return ELEMENT_TYPES.SIMPLE_EVENT;
		} else if (CREATE_UPDATE_METRIC_NAME.equals(context.getFunction().getName().localName)) {
			return ELEMENT_TYPES.METRIC;
		} else if (UPDATE_INSTANCE_NAME.equals(context.getFunction().getName().localName)) {
			return ELEMENT_TYPES.CONCEPT;
		}
		return null;
	}

	@Override
	protected void createInputPart(final IManagedForm managedForm, Composite parent) {
		inputSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED);
		inputSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		inputSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		inputSection.setText(Messages.getString("mapper.section.input"));
		
		if (MapperUtils.isSWTMapper(context.getProjectName())) {
			Composite composite = new Composite(inputSection,SWT.BORDER);
			GridLayout gl = new GridLayout();
			gl.marginHeight = 2;
			gl.marginWidth = 0;
			gl.horizontalSpacing = 0;
			gl.verticalSpacing = 0;
			composite.setLayout(gl);
			composite.setLayoutData(new GridData(GridData.FILL_BOTH));
			composite.setBackground(ColorConstants.menuBackground);
			inputOutputAdapter = SWTMapperUtils.createMapperControl(composite, context);
//			mapperControl = inputOutputAdapter.getMapperControl();
			
			GridData gd = new GridData(GridData.FILL_BOTH);
			inputSection.setLayoutData(gd);		
			GridLayout l = new GridLayout();
			inputSection.setLayout(l);
			inputSection.setClient(composite);
		} else {
			Composite composite = new Composite(inputSection,SWT.EMBEDDED);
			TableWrapData td = new TableWrapData(TableWrapData.FILL);
			td.grabHorizontal = true;
			td.heightHint = 200;
			inputSection.setLayoutData(td);
			inputSection.setClient(composite);
			
			Container panel = getSwingContainer(composite);
			panel.setLayout(new BorderLayout());
			inputMapper = new XMLMapperForm(context.getProjectName());
			panel.add(inputMapper,BorderLayout.CENTER);
			try {
				XMLMapperListeners.updateMapperPanel(context, inputMapper.getCache(), inputMapper.getBePanel(), inputMapper.getEditor());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void createXSLTTemplatePart(final IManagedForm managedForm, Composite parent){}

	private String getEntityPath() {
		return entityPath.getText();
	}

	/**
	 * Filter the concepts from project and given paths
	 * 
	 * @param project
	 *            - Current Project
	 * @param vars
	 *            - Scope/Local variables from rule function
	 * @param conceptVars
	 *            - Variable of type concepts
	 */
	private void filterConcepts(IProject project, List<VariableDefinition> vars, List<VariableDefinition> conceptVars) {
		for (VariableDefinition variableDefinition : vars) {
			String type = variableDefinition.getType();
			String path = ModelUtils.convertPackageToPath(type);
			path = path + ".concept";
			IResource resource = project.findMember(path);
			if (null != resource) {
				conceptVars.add(variableDefinition);
			}
		}
	}
}
