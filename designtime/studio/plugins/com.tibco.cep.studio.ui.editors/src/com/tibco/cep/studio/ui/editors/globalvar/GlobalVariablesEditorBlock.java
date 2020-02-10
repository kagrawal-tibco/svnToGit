package com.tibco.cep.studio.ui.editors.globalvar;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesDescriptor;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesGroup;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.Messages;

/*
@author ssailapp
@date Jan 6, 2010 2:44:07 PM
 */

public class GlobalVariablesEditorBlock extends MasterDetailsBlock {
	private FormPage page;
	private GlobalVariablesModelMgr modelmgr;
	private Tree trGvs;
	private Button bAdd, bAddGrp, bRemove;
	private TreeViewer viewer;

	public GlobalVariablesEditorBlock(FormPage page, GlobalVariablesModelMgr modelmgr) {
		this.page = page;
		this.modelmgr = modelmgr;
	}

	@Override
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(GlobalVariablesDescriptor.class, new GlobalVariablesDescriptorPage(modelmgr, viewer));
		detailsPart.registerPage(GlobalVariablesGroup.class, new GlobalVariablesGroupPage(modelmgr, viewer));
	}
	
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Global Variables and Groups");
		//section.setDescription("Define the global variables and groups that will be used in this project");
		section.setDescription("");
		section.marginWidth = 10;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trGvs = toolkit.createTree(client, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		trGvs.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAdd = toolkit.createButton(buttonsClient, "Add Variable", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getAddGvListener(parent.getShell()));
		bAddGrp = toolkit.createButton(buttonsClient, "Add Group", SWT.PUSH);
		bAddGrp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAddGrp.addListener(SWT.Selection, getAddGvGroupListener(parent.getShell()));
		toolkit.createLabel(buttonsClient, "");
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getRemoveGvListener());
		
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trGvs);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		
		setEnabled(false);
		
		addDoubleClickListener(viewer);
		registerContextMenu();
		registerSelectionListener();
		resetActionButtons();
		
		viewer.expandToLevel(modelmgr.getModel().curPrGrp, 3);
		
		bAdd.setEnabled(false);
		bAddGrp.setEnabled(false);
		bRemove.setEnabled(false);
	}

	protected void addDoubleClickListener(final TreeViewer viewer) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				BlockUtil.refreshViewer(viewer,modelmgr.getModel().curPrGrp, obj, null);
			}
		});
	}
	
	private void resetActionButtons() {
		bRemove.setEnabled(false);
	}
	
	private Listener getAddGvListener(Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					GlobalVariablesDescriptor newDesc = null;
					if (selObj != null) {
						if (selObj instanceof ProjectGroup) {
							ProjectGroup prGrp = (ProjectGroup)selObj;
							GlobalVariablesGroup gvgrp = (GlobalVariablesGroup)prGrp.getGrpVarRoot();
							newDesc = modelmgr.addGlobalVariablesDescriptor(gvgrp);
							modelmgr.getModel().gvGrp = gvgrp;
						}
						if (selObj instanceof GlobalVariablesGroup) {
							newDesc = modelmgr.addGlobalVariablesDescriptor((GlobalVariablesGroup) selObj);
						} else if (selObj instanceof GlobalVariablesDescriptor) {
							newDesc = modelmgr.addGlobalVariablesDescriptor(((GlobalVariablesDescriptor) selObj).parentGrp);
						}
						if (newDesc != null) {
							BlockUtil.refreshViewer(viewer, modelmgr.getModel().curPrGrp, newDesc.parentGrp, newDesc);
						}
					} 
				}
			}
		};
		return listener;
	}
	
	private Listener getAddGvGroupListener(Shell shell) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					GlobalVariablesGroup newGrp = null;
					if (selObj != null) {
						if (selObj instanceof ProjectGroup) {
							ProjectGroup prGrp = (ProjectGroup)selObj;
							GlobalVariablesGroup gvgrp = (GlobalVariablesGroup)prGrp.getGrpVarRoot();
							newGrp = modelmgr.addGlobalVariablesGroup(gvgrp);
							modelmgr.getModel().gvGrp = gvgrp;
						}
						if (selObj instanceof GlobalVariablesGroup) {
							newGrp = modelmgr.addGlobalVariablesGroup((GlobalVariablesGroup) selObj);
						} else if (selObj instanceof GlobalVariablesDescriptor) {
							newGrp = modelmgr.addGlobalVariablesGroup(((GlobalVariablesDescriptor) selObj).parentGrp);
						}
						if (newGrp != null) {
							BlockUtil.refreshViewer(viewer, modelmgr.getModel().curPrGrp, newGrp.parentGrp, newGrp);
						}
					} 
				}
			}
		};
		return listener;
	}

	private Listener getRemoveGvListener() {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					if (selObj != null) {
						GlobalVariablesGroup prGrp = null;
						if (selObj instanceof GlobalVariablesGroup) {
							GlobalVariablesGroup gvp = (GlobalVariablesGroup) selObj;
							prGrp = gvp.parentGrp;
							modelmgr.removeGlobalVariablesGroup(gvp);
							BlockUtil.refreshViewer(viewer, modelmgr.getModel().curPrGrp, prGrp, null);
						} else if (selObj instanceof GlobalVariablesDescriptor) {
							GlobalVariablesDescriptor gvd = (GlobalVariablesDescriptor) selObj;
							prGrp = gvd.parentGrp;
							modelmgr.removeGlobalVariablesDescriptor(gvd);
						}
						BlockUtil.refreshViewer(viewer, modelmgr.getModel().curPrGrp, prGrp, null);
						resetActionButtons();
					}
				}
			}
		};
		return listener;
	}

	private void registerContextMenu() {
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				if(viewer.getSelection().isEmpty()) {
					return;
				}

				if(viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					if (selObj != null) {
						if (selObj instanceof GlobalVariablesGroup && 
								!((GlobalVariablesGroup)selObj).projectLib) {
							manager.add(new AddGlobalVariableAction((GlobalVariablesGroup)selObj));
							manager.add(new AddGlobalVariableGroupAction((GlobalVariablesGroup)selObj));
							manager.add(new RemoveGlobalVariablesGroupAction((GlobalVariablesGroup)selObj));
						} else if (selObj instanceof GlobalVariablesDescriptor && 
								!((GlobalVariablesDescriptor)selObj).projectLib) {
							manager.add(new AddGlobalVariableAction((GlobalVariablesDescriptor)selObj));
							manager.add(new AddGlobalVariableGroupAction((GlobalVariablesDescriptor)selObj));
							manager.add(new RemoveGlobalVariableAction((GlobalVariablesDescriptor)selObj));
						}
					}
				}
			}
		});
		menuMgr.setRemoveAllWhenShown(true);
		viewer.getControl().setMenu(menu);
	}
	
	private void registerSelectionListener() {
		ISelectionChangedListener listener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					if (selection.isEmpty()) {
						setEnabled(false);
					}
					Object element = selection.getFirstElement();
					if (element != null) {
						if (element instanceof ProjectLibGroup) {
							setEnabled(false);
						}
						if (element instanceof ProjectGroup) {
							ProjectGroup grp = (ProjectGroup)element;
							if (grp.isProjectLib()) {
								setEnabled(false);
							} else {
								bAdd.setEnabled(true);
								bAddGrp.setEnabled(true);
								bRemove.setEnabled(false);
							}
						}
						else if (element instanceof GlobalVariablesGroup) {
							GlobalVariablesGroup grp = (GlobalVariablesGroup)element;
							if (grp.isProjectLib()) {
								setEnabled(false);
							} else {
								setEnabled(true);
							}
						}
						else if (element instanceof GlobalVariablesDescriptor) {
							GlobalVariablesDescriptor grp = (GlobalVariablesDescriptor)element;
							if (grp.isProjectLib()) {
								setEnabled(false);
							} else {
								bAdd.setEnabled(false);
								bAddGrp.setEnabled(false);
								bRemove.setEnabled(true);
							}
						}
					}
				}
			}
		};
		viewer.addSelectionChangedListener(listener);
	}
	
	private void setEnabled(boolean e) {
		bAdd.setEnabled(e);
		bRemove.setEnabled(e);
		bAddGrp.setEnabled(e);
	}
	
	class AddGlobalVariableGroupAction extends Action {
		private GlobalVariablesGroup gvGrp;
		private GlobalVariablesDescriptor gvDesc = null;
		
		public AddGlobalVariableGroupAction() {
			super("Add Group");
		}
		
		public AddGlobalVariableGroupAction(GlobalVariablesGroup group) {
			this();
			this.gvGrp = group;
		}

		public AddGlobalVariableGroupAction(GlobalVariablesDescriptor desc) {
			this();
			this.gvDesc = desc;
		}
		
		public void run() {
			GlobalVariablesGroup newGrp = null;
			if (gvGrp != null)
				newGrp = modelmgr.addGlobalVariablesGroup(gvGrp);
			else if (gvDesc != null)
				newGrp = modelmgr.addGlobalVariablesGroup(gvDesc.parentGrp);
			BlockUtil.refreshViewer(viewer, modelmgr.getModel().curPrGrp, newGrp.parentGrp, newGrp);
		}
	}
	
	class AddGlobalVariableAction extends Action {
		private GlobalVariablesGroup gvGrp = null;
		private GlobalVariablesDescriptor gvDesc = null;

		public AddGlobalVariableAction() {
			super("Add Variable");
		}
		
		public AddGlobalVariableAction(GlobalVariablesGroup group) {
			this();
			this.gvGrp = group;
		}
		
		public AddGlobalVariableAction(GlobalVariablesDescriptor desc) {
			this();
			this.gvDesc = desc;
		}
		
		public void run() {
			GlobalVariablesDescriptor newDesc = null;
			
			if (gvGrp != null)
				newDesc = modelmgr.addGlobalVariablesDescriptor(gvGrp);
			else if (gvDesc != null)
				newDesc = modelmgr.addGlobalVariablesDescriptor(gvDesc.parentGrp);
			
			BlockUtil.refreshViewer(viewer,modelmgr.getModel().curPrGrp, newDesc.parentGrp, newDesc);
		}
	}
	
	class RemoveGlobalVariableAction extends Action {
		private GlobalVariablesDescriptor gvDesc = null;

		public RemoveGlobalVariableAction(GlobalVariablesDescriptor desc) {
			super("Remove");
			this.gvDesc = desc;
		}

		public void run() {
			if (gvDesc != null) {
				modelmgr.removeGlobalVariablesDescriptor(gvDesc);
				BlockUtil.refreshViewer(viewer);
				resetActionButtons();
			}
		}
	}
	
	class RemoveGlobalVariablesGroupAction extends Action {
		private GlobalVariablesGroup gvGrp = null;

		public RemoveGlobalVariablesGroupAction(GlobalVariablesGroup desc) {
			super("Remove");
			this.gvGrp = desc;
		}

		public void run() {
			if (gvGrp != null) {
				modelmgr.removeGlobalVariablesGroup(gvGrp);
				BlockUtil.refreshViewer(viewer);
				resetActionButtons();
			}
		}
	}
	
	class MasterContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof ProjectLibGroup) {
				return ((ProjectLibGroup)element).getProjectGroupArray();
			}
			if (element instanceof ProjectGroup) {
				return ((ProjectGroup)element).getGlobalVariables();
			}
			if (element instanceof GlobalVariablesGroup) {
				return (modelmgr.getGroupGvs((GlobalVariablesGroup)element));
			}
			return new String[0];
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return modelmgr.getProjectGroups();
		}

		@Override	
		public void dispose() {
			// TODO Auto-generated method stub
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
		}
	}

	@SuppressWarnings("unchecked")
	class MasterLabelProvider extends StyledCellLabelProvider implements IColorProvider {
		private ArrayList listeners;
		
		
		public MasterLabelProvider() { 
			 listeners = new ArrayList();
		}
		
	   public Image getImage(Object element) {
			if (element instanceof ProjectLibGroup) {
				return StudioUIPlugin.getDefault().getImage("icons/library_obj.gif");
			}
			if (element instanceof ProjectGroup) {
				ProjectGroup grp = (ProjectGroup)element;
				if (grp.isProjectLib()) {
					return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
				}
				return StudioUIPlugin.getDefault().getImage("icons/designer_project.gif");
			} else if (element instanceof GlobalVariablesGroup) {
				return StudioUIPlugin.getDefault().getImage("icons/group.png");
			} else if (element instanceof GlobalVariablesDescriptor) {
				String type = ((GlobalVariablesDescriptor) element).getType();
				if (GlobalVariablesModel.TYPE_STRING.equals(type)) {
					return StudioUIPlugin.getDefault().getImage("icons/iconString16.gif");
				} else if (GlobalVariablesModel.TYPE_INTEGER.equals(type)) {
					return StudioUIPlugin.getDefault().getImage("icons/iconInteger16.gif");
				} else if (GlobalVariablesModel.TYPE_BOOLEAN.equals(type)) {
					return StudioUIPlugin.getDefault().getImage("icons/iconBoolean16.gif");
				} else if (GlobalVariablesModel.TYPE_PASSWORD.equals(type)) {
					return StudioUIPlugin.getDefault().getImage("icons/no_type.png");
				}
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);			
		}
	   
	   /* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.StyledCellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
	 */
	public void update(ViewerCell cell) {
		   Object element = cell.getElement();
		   StyledString styledString = getStyledText(element);
		   cell.setText(styledString.toString());
		   cell.setStyleRanges(styledString.getStyleRanges());
		   cell.setImage(getImage(element));
		   super.update(cell);
		 }

		/**
		 * @param element
		 * @return
		 */
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof ProjectLibGroup) {
				return "Project Dependencies";
			}
			if (element instanceof ProjectGroup) {
				ProjectGroup grp = (ProjectGroup)element;
				if (grp.isProjectLib()) {
					return grp.getProjectName() + "--Project Library";
				}
				return grp.getProjectName();
			} else if (element instanceof GlobalVariablesGroup) {
				GlobalVariablesGroup gvp = (GlobalVariablesGroup)element;
				return gvp.name;
			} else if (element instanceof GlobalVariablesDescriptor) {
				GlobalVariablesDescriptor gvd = (GlobalVariablesDescriptor)element;
				return gvd.getName();
			} 
			return element.toString();
		}
		
		@Override
		public void addListener(ILabelProviderListener listener) {
			listeners.add(listener);
		}
		
		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			//dispose images here
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.BaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
		 */
		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
		
		@Override
		public void removeListener(ILabelProviderListener listener) {
			listeners.remove(listener);
		}

		public StyledString getStyledText(Object element) {
			String text = getText(element);
			StyledString styledString = new StyledString(getText(element));
			String suffix = Messages.getString("studio.navigator.label.read.only");
			if (element instanceof ProjectLibGroup) {
				styledString.setStyle(0, text.length(),  new GVStyler(SWT.COLOR_BLUE));
			}
			if (element instanceof ProjectGroup) {
				ProjectGroup gvp = (ProjectGroup)element;
				if (gvp.isProjectLib()) {
					styledString.setStyle(0, text.length(),  new GVStyler(SWT.COLOR_DARK_GRAY));
				}
			}
			if (element instanceof GlobalVariablesGroup) {
				GlobalVariablesGroup gvp = (GlobalVariablesGroup)element;
				if (gvp.isProjectLib()){
					styledString.setStyle(0,text.length(),  new GVStyler(SWT.COLOR_DARK_GRAY));
					styledString.append(suffix,  StyledString.DECORATIONS_STYLER);
				}
			} else if (element instanceof GlobalVariablesDescriptor) {
				GlobalVariablesDescriptor gvd = (GlobalVariablesDescriptor)element;
				if (gvd.isProjectLib()){
					styledString.setStyle(0, text.length(),  new GVStyler(SWT.COLOR_DARK_GRAY));
					styledString.append(suffix,  StyledString.DECORATIONS_STYLER);
				}
			} 
			return styledString;
		}

		@Override
		public Color getBackground(Object element) {
			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (element instanceof ProjectLibGroup) {
				return ColorConstants.blue;
			}
			if (element instanceof ProjectGroup) {
				ProjectGroup grp = (ProjectGroup)element;
				if (grp.isProjectLib()) {
					return ColorConstants.darkGray;
				}
			}
			else if (element instanceof GlobalVariablesGroup) {
				GlobalVariablesGroup grp = (GlobalVariablesGroup)element;
				if (grp.isProjectLib()) {
					return ColorConstants.gray;
				}
			}
			else if (element instanceof GlobalVariablesDescriptor) {
				GlobalVariablesDescriptor grp = (GlobalVariablesDescriptor)element;
				if (grp.isProjectLib()) {
					return ColorConstants.gray;
				}
			}
			return null;
		}

//		@Override
//		public String getDescription(Object anElement) {
//			return getText(anElement);
//		}
	}
	
	
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		//TODO - Add toolbar icons, if we need to toggle between horizontal and vertical views
	}

}
