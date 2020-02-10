package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnProcessSelectionDialog;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.graph.properties.filter.BpmnProcessTreeViewerFilter;
import com.tibco.cep.bpmn.ui.wizards.NewBPMNProcessWizard;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * 
 * @author majha
 *
 */
public class GeneralCallActivityPropertySection extends GeneralTaskPropertySection {
	
	private static int status;
	private static String processPath;
	
	public GeneralCallActivityPropertySection() {
		super();
	}
	
	@Override
	protected boolean isNodeTypePropertyVisible() {
		return false;
	}
	
	@Override
	protected void createProperties() {		
		super.createProperties();
		this.timeoutEnable.setEnabled(false);
		this.timeoutEnable.setSelection(true);
	}
	protected String getAttrNameForTaskSelection() {
		EClass type = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		String name = null;
		if (type.equals(BpmnModelClass.CALL_ACTIVITY))
			name= BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT;
		else
			name = super.getAttrNameForTaskSelection();
		isCallActivity = true;
		return name;
		
	}
	
	protected ViewerFilter getViewFilter(){
		TSGraph rootGraph = ((TSEGraph)fTSENode.getOwnerGraph()).getGreatestAncestor();
		EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		return new BpmnProcessTreeViewerFilter(process);
	}
	
	protected StudioFilteredResourceSelectionDialog getSeletionDialog() {
		TSGraph rootGraph = ((TSEGraph) fTSENode.getOwnerGraph())
				.getGreatestAncestor();
		EObjectWrapper<EClass, EObject> process = EObjectWrapper
				.wrap((EObject) rootGraph.getUserObject());
		Object input = getProject();

		String project = ((IProject) input).getName();
		return new BpmnProcessSelectionDialog(Display.getDefault()
				.getActiveShell(), project, resourceText.getText().trim(),
				process);
	}
	
	public void refresh() {
		super.refresh();
		this.timeoutEnable.setEnabled(false);
		this.timeoutEnable.setSelection(true);
	}
	
	
	@Override
	protected IHyperlinkListener onResourceHyperlinkClick(final IDiagramEntitySelection select, 
			Hyperlink link,
			final Control control,
			final IEditorPart editor,
			final String projectName,
			final boolean isDestination,
			final boolean isNonEntity, 
			final ELEMENT_TYPES[] types, 
			boolean customFunction){
		
		
		IHyperlinkListener listener = new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				
				try{
					EObject userObject = (EObject) fTSENode.getUserObject();
					EObjectWrapper<EClass, EObject> userObjectWrap = EObjectWrapper.wrap(userObject);
					EObject attribute = (EObject) userObjectWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT);
					
					if (attribute != null) {
						EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(attribute);
						openProcessEditor(processWrapper);
					}
					else{
						
						openNewResourceLink(select, control,ELEMENT_TYPES.PROCESS, projectName);
					}
					
					System.out.println();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//handleLinkEvent(select, control, editor, projectName, isDestination, isNonEntity, types);
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void linkExited(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}
		};
		
		link.addHyperlinkListener(listener);

		return listener;

		
	}
	
	
	public void openNewResourceLink(IDiagramEntitySelection select, final Control control, ELEMENT_TYPES fEntityType, String projectName) {
	
		NewBPMNProcessWizard wiz=new NewBPMNProcessWizard(select, projectName, true);
		if (wiz != null) {
			runWizard(select, control, wiz);
		}
		EObjectWrapper<EClass, EObject> createdProcessModel = wiz.getCreatedProcessModelForCallActivityTask();
//		EEnumLiteral enumLiteral = BpmnModelClass.ENUM_PROCESS_TYPE_EXECUTABLE;
//		createdProcessModel.setAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE, enumLiteral);
		
		HashMap<String, Object> updateMap= new HashMap<String, Object>();
		if(createdProcessModel!=null){
			updateMap.put(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT, createdProcessModel.getEInstance());
			updatePropertySection(updateMap);
			refreshResouceWidget(createdProcessModel);
		}
	}
	
	private boolean runWizard(IDiagramEntitySelection select, final Control control, final AbstractNewEntityWizard wiz) {
		status = -1;
		try{
			Display.getDefault().syncExec(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					wiz.setOpenEditor(true);
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wiz) {
						@Override
						protected void createButtonsForButtonBar(Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.create();
					status = dialog.open();
				}});
			if (status == WizardDialog.CANCEL) {
				return false;
			}
			else if (status == WizardDialog.OK) {
				
				//waitForUpdate();
				
				IFile file = select.getEntityFile();
				String folder = StudioResourceUtils.getFolder(file);
				String name = file.getName().replace(CommonIndexUtils.DOT + file.getFileExtension(), "");
				if (control instanceof Text) {
					Text text = (Text)control;
					text.setText(folder + name);
					return true;		
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;		
	}
	
	private void openProcessEditor(EObjectWrapper<EClass, EObject> processWrapper){
		String folder = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String name = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String processPath =folder+IPath.SEPARATOR+name+"."+BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
		IFile file = this.getDiagramManager().getProject().getFile(processPath);
		if(!file.exists()){
			IFile fileLoc=IndexUtils.getLinkedResource(this.getDiagramManager().getProject().getName(),folder+IPath.SEPARATOR+name);
			if(fileLoc!=null)
				file=fileLoc;	
		}
		BpmnEditorInput input = new BpmnEditorInput(file);
		IWorkbenchPage activePage =  this.getDiagramManager().getEditor().getSite().getPage();
		openEditor(activePage, input,BpmnEditor.ID);	
	}
	


	protected String getAttributeValue(
			EObjectWrapper<EClass, EObject> userObjWrapper, String attrName) {
		if (BpmnModelClass.CALL_ACTIVITY.isSuperTypeOf(userObjWrapper.getEClassType())
				&& userObjWrapper.containsAttribute(attrName)) {
			EObject attribute = (EObject) userObjWrapper.getAttribute(attrName);
			if (attribute != null) {
				EObjectWrapper<EClass, EObject> processWrap = EObjectWrapper
						.wrap(attribute);
				String folder = processWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
				String name = processWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				String fullpath = null;
				if(folder!=null && folder.endsWith(Character.toString(IPath.SEPARATOR)))
					fullpath = folder+name;
				else
					fullpath = folder+IPath.SEPARATOR+name;
				return fullpath;
			}

		}
		return super.getAttributeValue(userObjWrapper, attrName);

	}
	
	protected void refreshResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		if (resourceText != null)
			resourceText.setText("");
		String taskName = null;
		EObject referencedProcess = null;
		String attrName = getAttrNameForTaskSelection();
		if (userObjWrapper != null && attrName != null) {
			 if(userObjWrapper.containsAttribute(attrName)) {
				referencedProcess = (EObject) userObjWrapper.getAttribute(attrName);
				taskName = getAttributeValue(userObjWrapper,  attrName);
			}
		}
		
		if (taskName != null && !taskName.isEmpty() && referencedProcess != null ) {
			boolean found = true;
			EObjectWrapper<EClass, EObject> resReferencedWrapper = EObjectWrapper.wrap((EObject)referencedProcess);
			String refProject =resReferencedWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
			String projectName = BpmnModelUtils.getProcess(userObjWrapper.getEInstance()).getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
			resourceText.setText(taskName);
			this.resource = referencedProcess;
			IFile linkFile=BpmnIndexUtils.getFile(this.getProject().getName(), (EObject)referencedProcess);
			if (linkFile!=null && linkFile.isLinked(IFile.CHECK_ANCESTORS)){
				if ((linkFile== null) ||!linkFile.exists()) {
					found=false;
				}
			}
			else if(refProject!=null && !refProject.equals(projectName)){
				found = false;
				
			}else{
				IFile file = BpmnIndexUtils.getFile(fProject.getName(),referencedProcess);
				found = (file != null) && file.exists();
			}
			
			if (!found) 
				resourceText.setForeground(COLOR_RED);
			else
				resourceText.setForeground(COLOR_BLACK);
		}
	}
	
	@Override
	protected ELEMENT_TYPES[] getElementsTypeSupportedForAction() {
		ELEMENT_TYPES[] types = new ELEMENT_TYPES[] { ELEMENT_TYPES.PROCESS };
		return types;
	}
	
	protected ISelection getPopupTreeSelection(Control parentControl,Object input,ViewerFilter [] viewerFilter) {
		
		@SuppressWarnings("unused")
		FileTreeContentProvider contentProvider = new FileTreeContentProvider();
		@SuppressWarnings("unused")
		FileTreeLabelProvider labelProvider = new FileTreeLabelProvider();
		return super.getPopupTreeSelection(parentControl, input, viewerFilter);
//		return getPopupTreeSelection(parentControl, input, labelProvider, contentProvider, viewerFilter);
	}
	
	private BpmnIndex getBpmnOntology() {
		EObject index = BpmnIndexUtils.getIndex(getProject());
		return new DefaultBpmnIndex(index);
	}
	
	@Override
	protected Object getResource() {
		String resourcePath = resourceText.getText();
		resourcePath = resourcePath.replace("\\", "/");
		EObject resource = null;
		boolean found = false;
		Collection<EObject> procs = getBpmnOntology().getAllProcesses();
		for(EObject proc:procs) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(proc);
			String folder = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			String name = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			String fullpath = null;
			
			if(folder.endsWith(Character.toString(IPath.SEPARATOR)))
				fullpath = folder+name;
			else
				fullpath = folder+IPath.SEPARATOR+name;
			
			if (fullpath.equals(resourcePath)) {
				resource = processWrapper.getEInstance();
				found = true;
				break;
			}
		}
		if (found )
			resourceText.setForeground(COLOR_BLACK);
		else{
			resourceText.setForeground(COLOR_RED);
		}
		
		
		return resource;
	}
	

	
	/**
	 * This class provides the content for the tree in FileTree
	 */

	class FileTreeContentProvider implements ITreeContentProvider {
	  /**
	   * Gets the children of the specified object
	   * 
	   * @param arg0
	   *            the parent object
	   * @return Object[]
	   */
	  public Object[] getChildren(Object arg0) {
		  try {
			  if(arg0 instanceof IContainer) {
					return ((IContainer)arg0).members();
			  }
		  } catch (CoreException e) {
			  BpmnUIPlugin.log(e);
		  }
		  return new Object[0];
	  }
	  
	  /**
	   * Gets the parent of the specified object
	   * 
	   * @param arg0
	   *            the object
	   * @return Object
	   */
	  public Object getParent(Object arg0) {
	    return null;
	  }

	  /**
	   * Returns whether the passed object has children
	   * 
	   * @param arg0
	   *            the parent object
	   * @return boolean
	   */
	  public boolean hasChildren(Object arg0) {
	    // Get the children
	    Object[] obj = getChildren(arg0);

	    // Return whether the parent has children
	    return obj == null ? false : obj.length > 0;
	  }

	  /**
	   * Gets the root element(s) of the tree
	   * 
	   * @param arg0
	   *            the input data
	   * @return Object[]
	   */
	  public Object[] getElements(Object arg0) {
	    // the root nodes in the file system
	    return getChildren(arg0);
	  }

	  /**
	   * Disposes any created resources
	   */
	  public void dispose() {
	    // Nothing to dispose
	  }

	  /**
	   * Called when the input changes
	   * 
	   * @param arg0
	   *            the viewer
	   * @param arg1
	   *            the old input
	   * @param arg2
	   *            the new input
	   */
	  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

	  }
	  
	}

	/**
	 * This class provides the labels for the file tree
	 */

	class FileTreeLabelProvider implements ILabelProvider {
		/**
		 * Gets the image to display for a node in the tree
		 * 
		 * @param arg0
		 *            the node
		 * @return Image
		 */
		public Image getImage(Object arg0) {
			return null;
		}

		/**
		 * Gets the text to display for a node in the tree
		 * 
		 * @param arg0
		 *            the node
		 * @return String
		 */
		public String getText(Object arg0) {
			if (arg0 instanceof IResource) {
				// Get the name of the file
				String text = ((IResource) arg0).getName();

				// If name is blank, get the path
				if (text.length() == 0) {
					text = ((IResource) arg0).getFullPath().toOSString();
				}
				// Check the case settings before returning the text
				return text;
			}
			return null;
		}

		/**
		 * Called when this LabelProvider is being disposed
		 */
		public void dispose() {
		}

		/**
		 * Returns whether changes to the specified property on the specified
		 * element would affect the label for the element
		 * 
		 * @param arg0
		 *            the element
		 * @param arg1
		 *            the property
		 * @return boolean
		 */
		public boolean isLabelProperty(Object arg0, String arg1) {
			return false;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

	}

}