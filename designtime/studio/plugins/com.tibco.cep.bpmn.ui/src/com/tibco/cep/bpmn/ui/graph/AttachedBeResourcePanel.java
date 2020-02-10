package com.tibco.cep.bpmn.ui.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.ui.BpmnProcessSelectionDialog;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.BpmnSupportedEmfType;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.util.PanelUiUtil;


/**
 * 
 * @author majha
 *
 */
public class AttachedBeResourcePanel {
	public final static Color COLOR_RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	public final static Color COLOR_BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	
	private AttachedBEResourceChangeListener listener;
	protected Text resourceText;
	protected Label resourceLabel;
	protected Button browseButton;
	private Composite parent;
	private FormToolkit toolkit;
	private WidgetListener resourceWidgetListeener;
	private IProject project;
	private EClass modelType;
	private AttachedBERuleResourcePanel attachedBERuleResourcePanel;
	private StackLayout stackLayout;
	private Composite ruleResourceComposite;
	private Composite browseComposite;
	private Composite stackComposite;
	private EClass extendedType;

	public AttachedBeResourcePanel(AttachedBEResourceChangeListener listener, IProject project , Composite parent, FormToolkit toolkit){
		this.listener = listener;
		this.parent = parent;
		this.toolkit = toolkit;
		this.project = project;

		this.resourceWidgetListeener = new WidgetListener();
		createPanel();
	}
	
	private void createPanel(){
		// Add Event
		resourceLabel =toolkit.createLabel(parent, "Resource:", SWT.NONE);
		GridData gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gridData.verticalIndent = 5;
		resourceLabel.setLayoutData(gridData);
		stackComposite = toolkit.createComposite(
				parent);
		stackLayout = new StackLayout();
		stackComposite.setLayout(stackLayout);
		
		browseComposite = toolkit.createComposite(
				stackComposite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);

		resourceText = PanelUiUtil.createText(browseComposite);
		resourceText.setEditable(true);
		gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gridData.widthHint = 300;
		resourceText.setLayoutData(gridData);

		browseButton = new Button(browseComposite, SWT.NONE);
		browseButton.setText(BpmnMessages.getString("browse_label"));	
		setVisible(false);
		
		ruleResourceComposite = toolkit.createComposite(
				stackComposite);
		ruleResourceComposite.setLayout(new GridLayout(2,false));
		attachedBERuleResourcePanel = new AttachedBERuleResourcePanel(listener, project, ruleResourceComposite);
		stackLayout.topControl = browseComposite;
		stackComposite.layout(true);
		addListener();
	}
	
	
	
	private void setEnabled(boolean enable){
		resourceText.setEnabled(enable);
		browseButton.setEnabled(enable);
	}
	
	private void setVisible(boolean visible){
		resourceLabel.setVisible(visible);
		resourceText.setVisible(visible);
		browseButton.setVisible(visible);
	}
	
	public void selectionChanged(Object selectedObject) {
		if (selectedObject instanceof BpmnPaletteGroupItem) {
			setVisible(true);
			BpmnPaletteGroupItem paletteInfo = (BpmnPaletteGroupItem) selectedObject;
			if (paletteInfo != null) {
				BpmnCommonPaletteGroupEmfItemType itemType = (BpmnCommonPaletteGroupEmfItemType) paletteInfo
						.getItemType();
				String localName = itemType.getEmfType().localName;
				BpmnSupportedEmfType bpmnSupportedEmfType = BpmnSupportedEmfType
						.getSupportedTypeMap().get(localName);
				boolean canAttachBEResource = bpmnSupportedEmfType.canAttachBEResource();
				if(canAttachBEResource){
					modelType =BpmnMetaModel.getInstance().getEClass( itemType.getEmfType());
					if(itemType.getExtendedType() != null)
						extendedType =BpmnMetaModel.getInstance().getEClass( itemType.getExtendedType());
					if(modelType.equals(BpmnModelClass.START_EVENT) || modelType.equals(BpmnModelClass.END_EVENT)){
						if(itemType.getExtendedType() == null)
							canAttachBEResource = false;
					}
				}
				if (canAttachBEResource) {
					modelType =BpmnMetaModel.getInstance().getEClass( itemType.getEmfType());
					if(modelType.equals(BpmnModelClass.INFERENCE_TASK)){
						stackLayout.topControl = ruleResourceComposite;
						stackComposite.layout(true);
						attachedBERuleResourcePanel.selectionChanged(paletteInfo);
						setEnabled(false);
//						removeListener();
					}else{
						stackLayout.topControl = browseComposite;
						stackComposite.layout(true);
						setEnabled(true);
//						addListener();
						resourceText.setText(paletteInfo.getAttachedResource());	
					}
					
				} else {
					stackLayout.topControl = browseComposite;
					stackComposite.layout(true);
					setEnabled(false);
					resourceText.setText("");
//					removeListener();
				}

			}
		} else if (selectedObject instanceof BpmnPaletteGroup) {
			setVisible(false);
			resourceText.setText("");
//			removeListener();
		}
		getResource();
	}
	
//	public void refreshResouceWidget(String resourcePath) {
//		if (resourceText != null)
//			resourceText.setText("");
//			DesignerElement element = IndexUtils.getElement(
//					project.getName(), resourcePath);
//			
//			resourceText.setText(resourcePath);
//			String resource = getResource();
//			if (element != null){
//				Bpmn
//				resourceText.setForeground(COLOR_BLACK);
//			}
//			else
//				resourceText.setForeground(COLOR_RED);
//			
//	}
	
	
	private void addListener() {
		resourceText.addModifyListener(resourceWidgetListeener);
		browseButton.addSelectionListener(resourceWidgetListeener);
	}
	
//	private void removeListener(){
//		resourceText.removeModifyListener(resourceWidgetListeener);
//		browseButton.removeSelectionListener(resourceWidgetListeener);
//	}
	
	public String getResource(){
		String resourcePath = resourceText.getText();
		if(!resourcePath.trim().isEmpty()){
			boolean valid = false;
			resourcePath = resourcePath.replace("\\", "/");
			if(BpmnModelClass.CALL_ACTIVITY.isSuperTypeOf(modelType)){
				EObject index = BpmnIndexUtils.getIndex(project.getName());
				if(index != null){
					DefaultBpmnIndex defaultBpmnIndex = new DefaultBpmnIndex(index);
					EObject processByPath = defaultBpmnIndex.getProcessByPath(resourcePath);
					if(processByPath != null){
						valid = true;
					}
				}
			}else if(BpmnModelClass.SERVICE_TASK.isSuperTypeOf(modelType)){
				try {
					IFile file = project.getFile(resourcePath);
					String pathWsdl = file.getLocation().toPortableString()+".wsdl"; 
					File file2 = new File(pathWsdl);
					valid = file2.exists();
				} catch (Exception e) {
					valid = false;
				}
				
			}else{
				DesignerElement element = IndexUtils.getElement(
						project.getName(), resourcePath);
				
				if(element != null){
					ELEMENT_TYPES elementType = element.getElementType();
					List<ELEMENT_TYPES> elementTypes = Arrays.asList(getElementsTypeSupportedForAction());
					if(elementTypes.contains(elementType))
						valid = true;
				}
			}
			
			if (valid)
				resourceText.setForeground(COLOR_BLACK);
			else{
				resourceText.setForeground(COLOR_RED);
				resourcePath = null;
			}	
		}else
			resourceText.setForeground(COLOR_BLACK);

		return resourcePath;
	}
	
	
	protected ELEMENT_TYPES[] getElementsTypeSupportedForAction() {

		ELEMENT_TYPES[] types = new ELEMENT_TYPES[0];
		if (modelType.equals(BpmnModelClass.RULE_FUNCTION_TASK))
			types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION };
		else if (modelType.equals(BpmnModelClass.BUSINESS_RULE_TASK))
			types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION };
		else if (modelType.equals(BpmnModelClass.INFERENCE_TASK))
			types = new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE };
		else if (modelType.equals(BpmnModelClass.SEND_TASK)
				|| modelType.equals(BpmnModelClass.RECEIVE_TASK))
			types = new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT };
		else if(BpmnModelClass.EVENT.isSuperTypeOf(modelType)){
			if(extendedType == null)
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT };
			else if(extendedType.equals(BpmnModelClass.TIMER_EVENT_DEFINITION)){
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.TIME_EVENT };
			}else
				types = new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT };
		}
		else if(BpmnModelClass.CALL_ACTIVITY.isSuperTypeOf(modelType))
			types = new ELEMENT_TYPES[] { ELEMENT_TYPES.PROCESS };

		return types;
	}
	
//	protected ViewerFilter getViewFilter(){
//		ELEMENT_TYPES[] types = getElementsTypeSupportedForAction();
//		if(types.length == 0) {
//			if (modelType.equals(BpmnModelClass.CALL_ACTIVITY))
//					return new BpmnProcessTreeViewerFilter(null);
//			return null;
//		}
//		
//		return new TreeViewerFilter(project, types);
//	}
	
//	protected ISelection getPopupTreeSelection(
//			Control parentControl,
//			Object input,
//			IBaseLabelProvider labelProvider,
//			ITreeContentProvider contentProvider,
//			ViewerFilter [] viewerFilter) {
//		if(parentControl == null) {
//			return null;
//		}
//		PopupTreeViewer treeViewer = new PopupTreeViewer(parentControl.getShell(),labelProvider,contentProvider,SWT.NONE);
//		if(viewerFilter != null) {
//			treeViewer.setViewerFilter(viewerFilter);
//		}
//		if(input == null) {
//			return null;
//		}
//		treeViewer.setInput(input);
//		treeViewer.expandAll();
//		Point location = parentControl.getLocation();
//		Point dloc = parentControl.getParent().toDisplay(location);
//		Rectangle bounds = parentControl.getBounds();
//		bounds.x=dloc.x;
//		bounds.y=dloc.y;
//		return treeViewer.open(bounds);
//	}
//	
//	protected ISelection getPopupTreeSelection(Control parentControl,Object input,ViewerFilter [] viewerFilter) {
//		if(parentControl == null) {
//			return null;
//		}
//		PopupTreeViewer treeViewer = new PopupTreeViewer(parentControl.getShell());
//		if(viewerFilter != null) {
//			treeViewer.setViewerFilter(viewerFilter);
//		}
//		if(input == null) {
//			return null;
//		}
//		treeViewer.setInput(input);
//		treeViewer.expandAll();
//		Point location = parentControl.getLocation();
//		Point dloc = parentControl.getParent().toDisplay(location);
//		Rectangle bounds = parentControl.getBounds();
//		bounds.x=dloc.x;
//		bounds.y=dloc.y;
//		return treeViewer.open(bounds);
//	}
	
	
	
	public void resourceBrowse() {
		
		StudioFilteredResourceSelectionDialog selectionDialog = getSeletionDialog();
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Object element = selectionDialog.getFirstResult();
			String path = "";
			if(element instanceof IFile ) {
				IResource res = (IFile) element;
				path = IndexUtils.getFullPath(res);
				resourceText.setText(path);
			} else if (element instanceof SharedEntityElement) {
				path = ((SharedEntityElement) element).getSharedEntity().getFullPath();
				resourceText.setText(path);
			}
		}
	}
	
	protected StudioFilteredResourceSelectionDialog getSeletionDialog() {
		String projectName = project.getName();
		ELEMENT_TYPES[] elementsTypeSupportedForAction = getElementsTypeSupportedForAction();
		if (BpmnModelClass.CALL_ACTIVITY.isSuperTypeOf(modelType))
			return new BpmnProcessSelectionDialog(Display.getDefault()
					.getActiveShell(), project.getName(), resourceText
					.getText().trim(), null);
		else if(BpmnModelClass.BUSINESS_RULE_TASK.isSuperTypeOf(modelType))
			return new StudioFilteredResourceSelectionDialog(Display
					.getDefault().getActiveShell(), projectName, resourceText
					.getText().trim(), elementsTypeSupportedForAction, true,
					true, false);
		else if(BpmnModelClass.RULE_FUNCTION_TASK.isSuperTypeOf(modelType))
			return new StudioFilteredResourceSelectionDialog(Display
					.getDefault().getActiveShell(), projectName, resourceText
					.getText().trim(), elementsTypeSupportedForAction, true,
					false, true);
		else if (modelType.equals(BpmnModelClass.SERVICE_TASK)) {
			
			List<String> list = new ArrayList<String>();
			list.add(CommonIndexUtils.WSDL_EXTENSION);
			
			return new StudioFilteredResourceSelectionDialog(Display.getDefault().getActiveShell(), project.getName(), 
	                resourceText.getText().trim() + CommonIndexUtils.DOT + CommonIndexUtils.WSDL_EXTENSION, 
	                list,new ViewerFilter[]{});
		}
			return new StudioFilteredResourceSelectionDialog(Display
					.getDefault().getActiveShell(), projectName, resourceText
					.getText().trim(), elementsTypeSupportedForAction, false,
					false, false);
	}
	


	private class WidgetListener extends SelectionAdapter  implements ModifyListener{

		public void modifyText(ModifyEvent e) {
			String resource = getResource();
			if(resource != null)
				listener.resourceChanged(resourceText.getText());
		}
		@Override
		public void widgetSelected(SelectionEvent e) {
			resourceBrowse();
			// ViewerFilter viewFilter= getViewFilter();
			// if(viewFilter == null) return;
			//
			// ViewerFilter[] filter = new ViewerFilter[]{viewFilter};
			// Object input = project;
			// ISelection selected = getPopupTreeSelection(resourceText, input,
			// filter);
			// if(selected instanceof IStructuredSelection) {
			// Object element =
			// ((IStructuredSelection)selected).getFirstElement();
			// String path = "";
			// if(element instanceof IFile ) {
			// IResource res = (IFile) element;
			// path = IndexUtils.getFullPath(res);
			// resourceText.setText(path);
			// } else if (element instanceof SharedEntityElement) {
			// path = ((SharedEntityElement)
			// element).getSharedEntity().getFullPath();
			// resourceText.setText(path);
			// }
			// }
		}
		
	}
	
	
}
