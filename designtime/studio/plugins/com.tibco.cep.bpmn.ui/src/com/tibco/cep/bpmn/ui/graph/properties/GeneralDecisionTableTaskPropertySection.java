package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.VrfImplDeployedXPathWizard;
import com.tibco.cep.bpmn.ui.XPathBooleanExpressionValidator;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * 
 * @author majha
 * @param <V>
 * 
 */

public class GeneralDecisionTableTaskPropertySection<V> extends
		GeneralTaskPropertySection {

	private VRFImplementationViewer vrfImplementationViewer;

	public GeneralDecisionTableTaskPropertySection() {
		super();
	}

	@Override
	protected void refreshResouceWidget(
			EObjectWrapper<EClass, EObject> userObjWrapper) {
		// TODO Auto-generated method stub
		super.refreshResouceWidget(userObjWrapper);
		vrfImplementationViewer.updateVrfTable(userObjWrapper);

	}

	@Override
	protected void updateForVrfResourceChange() {
		// TODO Auto-generated method stub
		super.updateForVrfResourceChange();
		EObject userObject = (EObject) fTSENode.getUserObject();
		vrfImplementationViewer.updateVrfTable(EObjectWrapper.wrap(userObject));
	}

	@Override
	protected void createResourceProperty() {
		// TODO Auto-generated method stub
		super.createResourceProperty();
		vrfImplementationViewer = new VRFImplementationViewer();
		vrfImplementationViewer
				.createControlPart(getWidgetFactory(), composite);
	}
	
	

	 
	class VRFImplementationViewer {

		public final String IMPLEMETATION = Messages
				.getString("Implementation_Uri");

//		public final String EXPRESSION = Messages.getString("Expression");

//		public final String[] PROPS = { IMPLEMETATION, EXPRESSION };

		private TableViewer dtTableViewer;
		private ToolItem upButton;
		private ToolItem downButton;
		ArrayList<EObject> implementationList;
		EObjectWrapper<EClass, EObject> currentVrf;

		public VRFImplementationViewer() {
		}

		protected void createControlPart(
				TabbedPropertySheetWidgetFactory factory, Composite parent) {

			Composite client = factory.createComposite(parent);

			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			client.setLayout(layout);
			GridData gd = new GridData();
			gd.heightHint = 130;
			gd.widthHint = 700;
			gd.horizontalSpan = 2;
			client.setLayoutData(gd);

			createRfImplTable(client);
			createToolbar(client, false);
			downButton.setEnabled(false);
			upButton.setEnabled(false);

		}

		protected ToolBar createToolbar(Composite parent, boolean duplicate) {
			ToolBar toolBar = new ToolBar(parent, SWT.VERTICAL | SWT.RIGHT
					| SWT.FLAT);
			toolBar.setBackground(Display.getDefault().getSystemColor(
					SWT.COLOR_WHITE));
			toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			upButton = new ToolItem(toolBar, SWT.PUSH);
			Image upImg = EditorsUIPlugin.getDefault().getImage(
					"icons/arrow_up.png");
			upButton.setImage(upImg);
			upButton.setToolTipText(BpmnMessages.getString("exclusiveGatewayProp_upButton_label"));
			upButton.setText(BpmnMessages.getString("exclusiveGatewayProp_upButton_label"));
			upButton.setEnabled(false);
			upButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					int index = dtTableViewer.getTable().getSelectionIndex();
					moveUp(index);
				}
			});

			downButton = new ToolItem(toolBar, SWT.PUSH);
			Image downImg = EditorsUIPlugin.getDefault().getImage(
					"icons/arrow_down.png");
			downButton.setImage(downImg);
			downButton.setToolTipText(BpmnMessages.getString("exclusiveGatewayProp_downButton_label"));
			downButton.setText(BpmnMessages.getString("exclusiveGatewayProp_downButton_label"));
			downButton.setEnabled(false);
			downButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					int index = dtTableViewer.getTable().getSelectionIndex();
					moveDown(index);
				}
			});

			GridData gridData = new GridData();
			gridData.verticalIndent = 20;
			toolBar.setLayoutData(gridData);
			
			toolBar.pack();
			return toolBar;
		}

		
		protected void createRfImplTable(Composite parent) {
			dtTableViewer = new TableViewer(parent, SWT.FULL_SELECTION
					| SWT.CHECK);

			GridData data = new GridData();
			data.heightHint = 80;
			data.widthHint = 600;
			data.verticalSpan = 2;
			data.horizontalSpan = 1;
			dtTableViewer.getTable().setLayoutData(data);
			dtTableViewer.getTable().setLinesVisible(true);
			dtTableViewer.getTable().setHeaderVisible(true);
			TableViewerColumn col = new TableViewerColumn(dtTableViewer,
					SWT.NONE);
			TableColumn UriColumn = col.getColumn();
			UriColumn.setText(IMPLEMETATION);
			col.setLabelProvider(new CellLabelProvider() {
				
				@Override
				public void update(ViewerCell cell) {
					EObject vrfIml = (EObject) cell.getElement();
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
							.wrap(vrfIml);
					String text =(String) wrap
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
										
			        cell.setText(text);
			        //cell.setForeground(Display.getDefault().getSystemColor(
					//		SWT.COLOR_BLUE));
					TableItem item = (TableItem) cell.getItem();
					item.setChecked((Boolean) wrap
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED));
				}

			});

//			col = new TableViewerColumn(dtTableViewer, SWT.NONE);
//			TableColumn resPathColumn = col.getColumn();
//			resPathColumn.setText(EXPRESSION);
//			col.setLabelProvider(new CellLabelProvider() {
//				@Override
//				public void update(ViewerCell cell) {
//					EObject vrfIml = (EObject) cell.getElement();
//					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
//							.wrap(vrfIml);
//					cell.setText((String) wrap
//							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION));
//
//				}
//			});
//			col.setEditingSupport(new ExpressionEditingSupport(dtTableViewer));


			dtTableViewer.setContentProvider(new ArrayContentProvider());

			autoTableLayout(dtTableViewer.getTable());
			
			dtTableViewer.getTable().addListener(SWT.MouseHover, new Listener() {
				
				final Cursor handcur=new Cursor(dtTableViewer.getTable().getFont().getDevice(),SWT.CURSOR_HAND);
				final Cursor arrowcur=new Cursor(dtTableViewer.getTable().getFont().getDevice(),SWT.CURSOR_ARROW);
				
				@Override
				public void handleEvent(Event event) {
					String ext="";
					boolean flag=isInsideTableViewer(dtTableViewer,event,false,ext);
					
				     if (flag){
				    	// dtTableViewer.getTable().setToolTipText("double click to open in new editor");
				     	dtTableViewer.getTable().setCursor(handcur);
				     }
				     else{
				    	// dtTableViewer.getTable().setToolTipText("");
				    	 dtTableViewer.getTable().setCursor(arrowcur);
				     }
				}
				
				
			});
			dtTableViewer.getTable().addListener(SWT.MouseDown,new Listener() {

				@Override
				public void handleEvent(Event event) {
					
					String ext=CommonIndexUtils.RULE_FN_IMPL_EXTENSION;
					isInsideTableViewer(dtTableViewer,event,true,ext);
				}
				
			});
			dtTableViewer.getTable().addSelectionListener(
					new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							
							if (e.detail == SWT.CHECK) {
								TableItem item = (TableItem) e.item;
								EObject impl = (EObject) item.getData();
								EObjectWrapper<EClass, EObject> implementation = EObjectWrapper
										.wrap(impl);
								implementation
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED,
												item.getChecked());
								HashMap<String, Object> updateList = new HashMap<String, Object>();
								updateList
										.put(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS,
												implementationList);
								updatePropertySection(updateList);
							}
							enableButtons();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							// TODO Auto-generated method stub

						}
					});

		}

		private void enableButtons() {
			if (dtTableViewer.getTable().getItems().length <= 1) {
				upButton.setEnabled(false);
				downButton.setEnabled(false);
			} else {
				TableItem[] items = dtTableViewer.getTable().getSelection();
				if (items.length == 1) {
					int length = dtTableViewer.getTable().getItems().length;
					if (dtTableViewer.getTable().getItem(0).equals(items[0])) {
						upButton.setEnabled(false);
						downButton.setEnabled(true);
					} else if (dtTableViewer.getTable().getItem(length - 1)
							.equals(items[0])) {
						upButton.setEnabled(true);
						downButton.setEnabled(false);
					} else {
						upButton.setEnabled(true);
						downButton.setEnabled(true);
					}
				} else {
					upButton.setEnabled(false);
					downButton.setEnabled(false);
				}
			}

		}

		protected void autoTableLayout(Table table) {
			TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(
					table);
			for (int loop = 0; loop < table.getColumns().length; loop++) {
				autoTableLayout.addColumnData(new ColumnWeightData(1));
			}
			table.setLayout(autoTableLayout);
		}

		public void updateVrfTable(
				EObjectWrapper<EClass, EObject> userObjWrapper) {
			currentVrf = userObjWrapper;
			refreshImplementationList();
			dtTableViewer.setInput(implementationList);

		}

		private void refreshImplementationList() {
			String attrName = BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS;
			if (currentVrf != null && attrName != null) {
				if (ExtensionHelper.isValidDataExtensionAttribute(currentVrf,
						attrName)) {
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(currentVrf);
					if (valueWrapper != null) {
						implementationList = new ArrayList<EObject>(
								valueWrapper.getListAttribute(attrName));
						String vrfPath = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
						addMissingImplementations(implementationList, vrfPath);
					}
				}
			}
		}

		private void addMissingImplementations(
				ArrayList<EObject> implementationList, String vrfPath) {
			List<DesignerElement> allElements = CommonIndexUtils.getAllElements(fProject.getName(), ELEMENT_TYPES.DECISION_TABLE);
			for (DesignerElement designerElement : allElements) {
				if(designerElement instanceof DecisionTableElement){
					DecisionTableElement table =(DecisionTableElement) designerElement;
					EObject impl = table.getImplementation();
					if(impl instanceof com.tibco.cep.decision.table.model.dtmodel.Table){
						com.tibco.cep.decision.table.model.dtmodel.Table implementation =(com.tibco.cep.decision.table.model.dtmodel.Table) impl;
						String implementsURI= implementation.getImplements();
						if(implementsURI.equals(vrfPath)){
							String uri = table.getFolder()+table.getName();
							if(!isDecisionTableUriPresent(implementationList, uri))
								implementationList.add(getDiagramManager().getModelController().createVrfImplementation(uri).getEInstance());
						}
					}
				}
			}
		}
		
		private boolean isDecisionTableUriPresent(ArrayList<EObject> implementationList, String newuri){
			for (EObject eObject : implementationList) {
				EObjectWrapper<EClass, EObject> implementation = EObjectWrapper.wrap(eObject);
				String uri = implementation.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
				if(uri.equalsIgnoreCase(newuri)){
					return true;
				}
			}	
			return false;
		}

		private void moveUp(int oldPosition) {
			EObject remove = implementationList.remove(oldPosition);
			implementationList.add(oldPosition - 1, remove);
			HashMap<String, Object> updateList = new HashMap<String, Object>();
			updateList.put(
					BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS,
					implementationList);
			updatePropertySection(updateList);

			dtTableViewer.refresh();

			enableButtons();
		}

		private void moveDown(int oldPosition) {
			EObject remove = implementationList.remove(oldPosition);
			implementationList.add(oldPosition + 1, remove);
			HashMap<String, Object> updateList = new HashMap<String, Object>();
			updateList.put(
					BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS,
					implementationList);
			updatePropertySection(updateList);

			dtTableViewer.refresh();
			enableButtons();
		}

	}

//	class ExpressionEditingSupport extends EditingSupport {
//
//		private final TableViewer viewer;
//
//		public ExpressionEditingSupport(TableViewer viewer) {
//			super(viewer);
//			this.viewer = viewer;
//		}
//
//		@Override
//		protected CellEditor getCellEditor(Object element) {
//			return new ExpressionCelEditor(viewer.getTable());
//
//		}
//
//		@Override
//		protected boolean canEdit(Object element) {
//			return true;
//		}
//
//		@Override
//		protected Object getValue(Object element) {
//			EObject vrfIml = (EObject) element;
//			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(vrfIml);
//			return wrap
//					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
//
//		}
//
//		@Override
//		protected void setValue(Object element, Object value) {
//			EObject vrfIml = (EObject) element;
//			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(vrfIml);
//			wrap.setAttribute(
//					BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION, value);
//			viewer.refresh();
//		}
//	}

	class ExpressionCelEditor extends DialogCellEditor {

		public ExpressionCelEditor(Table table) {
			super(table);
		}

		@Override
		protected void updateContents(Object value) {
			@SuppressWarnings("unused")
			String old = getDefaultLabel().getText();
			if (value == null) {
				value = "";
			}
			getDefaultLabel().setText((String) value);
		}

		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			TableItem tableItem = vrfImplementationViewer.dtTableViewer
					.getTable().getSelection()[0];
			EObject data = (EObject) tableItem.getData();
			final EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(data);
			String oldValue = wrap
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
			Display.getCurrent().syncExec(new Runnable() {
				@Override
				public void run() {
					try {
						EObjectWrapper<EClass, EObject> process = getDiagramManager()
								.getModelController().getModelRoot();
						VrfImplDeployedXPathWizard wizard = new VrfImplDeployedXPathWizard(
								getProject(), wrap, new XPathBooleanExpressionValidator(),process);
						WizardDialog dialog = new WizardDialog(fEditor
								.getSite().getShell(), wizard) {
							@Override
							protected void createButtonsForButtonBar(
									Composite parent) {
								super.createButtonsForButtonBar(parent);
								Button finishButton = getButton(IDialogConstants.FINISH_ID);
								finishButton.setText(IDialogConstants.OK_LABEL);
							}
						};
						dialog.setMinimumPageSize(700, 500);
						try {
							dialog.create();
						} catch (RuntimeException e) {
							if (e.getCause() instanceof InterruptedException) {
								return;
							}
						}
						dialog.open();
					} catch (Exception e) {
						BpmnUIPlugin.log(e);
					}
				}
			});
			
			String newValue = wrap
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
			
			if(!oldValue.equals(newValue)){
				HashMap<String, Object> updateList = new HashMap<String, Object>();
				updateList.put(
						BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS,
						vrfImplementationViewer.implementationList);
				updatePropertySection(updateList);
			}
			
			

			return newValue;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.properties.GeneralNodePropertySection#resourceBrowse()
	 */
	public void resourceBrowse() {
		Object input = getProject();
		String project = ((IProject)input).getName();
		StudioFilteredResourceSelectionDialog selectionDialog = 
				new StudioFilteredResourceSelectionDialog(Display.getDefault().getActiveShell(), project, 
						                                  resourceText.getText().trim(), 
						                                  getElementsTypeSupportedForAction(), 
						                                  true, 
						                                  true, 
						                                  false);
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
				if(!path.startsWith("/"))
					path="/"+path;
				resourceText.setText(path);
			}
		}
	}


}
