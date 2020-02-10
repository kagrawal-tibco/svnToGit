package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;

public class StatesSelectionDialog extends SelectionStatusDialog {
	
	private static enum TYPES {
		SIMPLE,
		COMPOSITE,
		CONCURRENT,
		STATE_MACHINE,
		CALL_STATE_MACHINE
	};
	
	private StateMachine stateMachine;
	
	private CheckboxTreeViewer treeViewer;
	
	private ITreeContentProvider treeViewerContentProvider;
	
	private Map<TYPES,Image> images;

	public StatesSelectionDialog(Shell parent, StateMachine stateMachine) {
		super(parent);
		this.stateMachine = stateMachine;
		setTitle("State Selection Dialog");
		images = new HashMap<TYPES, Image>();
		images.put(TYPES.SIMPLE,StateMachinePlugin.getImageDescriptor("icons/SimpleState.png").createImage(true));
		images.put(TYPES.COMPOSITE,StateMachinePlugin.getImageDescriptor("icons/composite.png").createImage(true));
		images.put(TYPES.CONCURRENT,StateMachinePlugin.getImageDescriptor("icons/concurrent.png").createImage(true));
		images.put(TYPES.STATE_MACHINE,StateMachinePlugin.getImageDescriptor("icons/state_machine.png").createImage(true));
		images.put(TYPES.CALL_STATE_MACHINE,StateMachinePlugin.getImageDescriptor("icons/sub_machinestate.png").createImage(true));
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent,SWT.NONE);
		composite.setLayout(new GridLayout());
		
		Composite btnHolder = new Composite(composite,SWT.NONE);
		btnHolder.setLayout(new GridLayout(3,true));
		
		
		Button btn_SelectAll = new Button(btnHolder,SWT.PUSH);
		btn_SelectAll.setText("Select All");
		btn_SelectAll.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		btn_SelectAll.addSelectionListener(new AbstractSelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object[] rootElements = ((ITreeContentProvider)treeViewer.getContentProvider()).getElements(stateMachine);
				for (Object rootElement : rootElements) {
					treeViewer.setSubtreeChecked(rootElement, true);	
				}
				validate();
			}
		
		});
		
		Button btn_selectNone = new Button(btnHolder,SWT.PUSH);
		btn_selectNone.setText("Select None");
		btn_selectNone.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		btn_selectNone.addSelectionListener(new AbstractSelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object[] rootElements = treeViewerContentProvider.getElements(stateMachine);
				for (Object rootElement : rootElements) {
					treeViewer.setSubtreeChecked(rootElement, false);	
				}
				validate();
			}
		
		});		
		
		Button btn_inverseSelection = new Button(btnHolder,SWT.PUSH);
		btn_inverseSelection.setText("Inverse Selection");
		btn_inverseSelection.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		btn_inverseSelection.addSelectionListener(new AbstractSelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object[] rootElements = treeViewerContentProvider.getElements(stateMachine);
				for (Object rootElement : rootElements) {
					inverseSelection(rootElement);	
				}
//				inverseSelection(treeViewer.getTree().getItems());
//				validate();
			}
		
		});	

		btnHolder.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,true));
		
		Group treeHolder = new Group(composite,SWT.NONE);
		treeHolder.setText("State Browser");
		FillLayout treeHolderLayout = new FillLayout();
		treeHolderLayout.marginHeight = 5;
		treeHolderLayout.marginWidth = 5;
		treeHolder.setLayout(treeHolderLayout);
		Tree tree = new Tree(treeHolder,SWT.V_SCROLL|SWT.H_SCROLL|SWT.CHECK);
		treeViewer = new CheckboxTreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		treeViewer.addFilter(new ViewerFilter(){

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof StateStart){
					return false;
				}
				if (element instanceof StateEnd){
					return false;
				}
				return true;
			};
			
		});
		treeViewer.setLabelProvider(new LabelProvider(){
			
			@Override
			public String getText(Object element) {
				if (element instanceof State){
					return ((State) element).getName();
				}
				if (element instanceof StateMachine){
					return ((StateMachine) element).getName();
				}
				return super.getText(element);
			}
			
			@Override
			public Image getImage(Object element) {
				if (element instanceof StateSimple){
					return images.get(TYPES.SIMPLE);
				}
				if (element instanceof StateComposite){
					if (((StateComposite) element).isConcurrentState() == true) {
						return images.get(TYPES.CONCURRENT);	
					}
					return images.get(TYPES.COMPOSITE);
				}
				if (element instanceof StateMachine){
					return images.get(TYPES.STATE_MACHINE);
				}
				if (element instanceof StateSubmachine){
					return images.get(TYPES.CALL_STATE_MACHINE);
				}
				return null;
			}
		});
		
		treeViewerContentProvider = new StateMachineTreeContentProvider();
		treeViewer.setContentProvider(treeViewerContentProvider);
		treeViewer.setInput(stateMachine);
		
		treeViewer.getTree().addSelectionListener(new SelectionAdapter(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (treeViewer.getExpandedState(e.item.getData()) == true){
					treeViewer.collapseToLevel(e.item.getData(), 0);
				}
				else {
					treeViewer.expandToLevel(e.item.getData(), 1);
				}
			}

		});
		
		treeViewer.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				validate();				
			}
			
		});
		
		treeHolder.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		
		GridData compositeLayoutData = new GridData(SWT.FILL,SWT.FILL,true,true);
		compositeLayoutData.widthHint = 350;
		composite.setLayoutData(compositeLayoutData);
		
		validate();
		
		return composite;
	}
	
	private void inverseSelection(Object element) {
		treeViewer.setChecked(element, !treeViewer.getChecked(element));
		Object[] children = treeViewerContentProvider.getChildren(element);
		for (Object child : children) {
			inverseSelection(child);
		}
	}

	@Override
	protected void cancelPressed() {
		setResult(null);
		super.cancelPressed();
	}

	@Override
	protected void computeResult() {
		setResult(Arrays.asList(treeViewer.getCheckedElements()));
	}
	
	private void validate() {
		if (treeViewer.getCheckedElements() == null || treeViewer.getCheckedElements().length == 0) {
			updateStatus(new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID,Status.ERROR, "Select A State",null));
			return;
		}
		updateStatus(new Status(Status.OK, DashboardStateMachineComponentPlugin.PLUGIN_ID, Status.OK, "", null));
	}
	
	@Override
	public boolean close() {
		for (Image image : images.values()) {
			image.dispose();
		}
		images.clear();
		images = null;
		return super.close();
	}
	
	class StateMachineTreeContentProvider implements ITreeContentProvider {
		
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof StateMachine){
				return ((StateMachine) parentElement).getStateEntities().toArray();
			}
			if (parentElement instanceof StateComposite){
				StateComposite stateComposite = (StateComposite) parentElement;
				if (stateComposite.isConcurrentState() == false){
					return stateComposite.getStateEntities().toArray();
				}
			}
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof EObject){
				return ((EObject) element).eContainer();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length != 0;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof StateMachine){
				//return new Object[]{inputElement};
				return ((StateMachine) inputElement).getStateEntities().toArray();
			}
			return new Object[0];
		}

		@Override
		public void dispose() {
			//do nothing
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		
	}

}
