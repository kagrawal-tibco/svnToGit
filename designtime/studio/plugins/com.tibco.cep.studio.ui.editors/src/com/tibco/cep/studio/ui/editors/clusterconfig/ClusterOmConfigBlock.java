package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.BdbOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.Connection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObjectDefault;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObjectOverrides;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObjects;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ClusterInfo;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DbConcepts;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MemoryOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ObjectManagement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ObjectManager;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/*
@author ssailapp
@date Mar 9, 2010 2:39:25 PM
 */

public class ClusterOmConfigBlock extends ClusterConfigBlock {
	private Tree trCluster;
	private Button bAdd, bRemove;
	
	public ClusterOmConfigBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		super(page, modelmgr);
		EXPAND_LEVEL = 4;
	}

	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(ClusterInfo.class, new NodeClusterInfoPage(modelmgr, viewer));
//		detailsPart.registerPage(BdbOm.class, new NodeBdbPage(modelmgr, viewer));
		detailsPart.registerPage(CacheOm.class, new NodeCacheOmPage(modelmgr, viewer));
		detailsPart.registerPage(BackingStore.class, new NodeBackingStorePage(modelmgr, viewer));
		detailsPart.registerPage(Connection.class, new NodeBackingStoreConnectionPage(modelmgr, viewer));
		detailsPart.registerPage(DbConcepts.class, new NodeDbConceptsPage(modelmgr, viewer));
		detailsPart.registerPage(DomainObjectDefault.class, new NodeDomainObjectDefaultPage(modelmgr, viewer));
		detailsPart.registerPage(DomainObject.class, new NodeDomainObjectPage(modelmgr, viewer));
		detailsPart.registerPage(PropertyElementList.class, new NodePropertyElementListPage(modelmgr, viewer));	
		super.registerPages(detailsPart);
	}
	
	@Override
	public void createContent(IManagedForm managedForm) {
		super.createContent(managedForm);
		sashForm.setWeights(new int[]{35,65});
	}
	
	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.setText("Cluster");
		//section.setDescription("Define the Cluster configuration.");
		section.marginWidth = 5;
		section.marginHeight = 5;
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		
		trCluster = toolkit.createTree(client, SWT.MULTI);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 80;
		trCluster.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		
		Composite buttonsClient = new Composite(client, SWT.NONE);
		buttonsClient.setLayout(new GridLayout(1, false));
		buttonsClient.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		bAdd = toolkit.createButton(buttonsClient, "Add", SWT.PUSH);
		bAdd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bAdd.addListener(SWT.Selection, getClusterOmBlockAddListener(parent.getShell()));
		bRemove = toolkit.createButton(buttonsClient, "Remove", SWT.PUSH);
		bRemove.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		bRemove.addListener(SWT.Selection, getClusterOmBlockRemoveListener());
		
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(trCluster);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
		addDoubleClickListener(viewer);
		
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		registerContextMenu(); 
		registerSelectionListener();
		resetActionButtons();
		BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
	}

	private void registerContextMenu() {
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				if (viewer.getSelection().isEmpty()) {
					return;
				}
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					if (selection.size() != 1)
						return;
					Object selObj = selection.getFirstElement();
					if (selObj != null) {
						if (selObj instanceof CacheOm) {
							manager.add(new ChangeOmAction("Change to In Memory", ObjectManagement.MEMORY_MGR));
//							if (!AddonUtil.isExpressEdition())
//								manager.add(new ChangeOmAction("Change to Berkeley DB", ObjectManagement.BDB_MGR));
						} else if (selObj instanceof BdbOm) {
							manager.add(new ChangeOmAction("Change to In Memory", ObjectManagement.MEMORY_MGR));
							if (!AddonUtil.isExpressEdition())
								manager.add(new ChangeOmAction("Change to Cache", ObjectManagement.CACHE_MGR));
						} else if (selObj instanceof MemoryOm) {
							if (!AddonUtil.isExpressEdition()) {
								manager.add(new ChangeOmAction("Change to Cache", ObjectManagement.CACHE_MGR));
//								manager.add(new ChangeOmAction("Change to Berkeley DB", ObjectManagement.BDB_MGR));
							}
						} else if (selObj instanceof DomainObjectOverrides) {
							//manager.add(new AddDomainObjectAction(selObj));
						} else if (selObj instanceof DomainObject) {
							//manager.add(new RemoveDomainObjectAction(selObj));
						}
					}
				}
			}
		});
		menuMgr.setRemoveAllWhenShown(true);
		viewer.getControl().setMenu(menu);
	}
	
	class ChangeOmAction extends Action {
		String type;
		public ChangeOmAction(String label, String type) {
			super(label , ClusterConfigImages.getImageDescriptor(ClusterConfigImages.IMG_CLUSTER_PROPERTY));
			this.type = type;
		}
		public void run() {
			modelmgr.setOmMgr(type);
			modelmgr.updateCacheOmValues(Elements.EXPLICIT_TUPLE.localName, "true");	
			BlockUtil.refreshViewer(viewer, EXPAND_LEVEL);
			if (type.equals(ObjectManagement.MEMORY_MGR)) {
				BlockUtil.selectObject(viewer, modelmgr.getModel().om.memOm);
			} 
//			else if (type.equals(ObjectManagement.BDB_MGR)) {
//				BlockUtil.selectObject(viewer, modelmgr.getModel().om.bdbOm);
//			} 
			else if (type.equals(ObjectManagement.CACHE_MGR)) {
				BlockUtil.selectObject(viewer, modelmgr.getModel().om.cacheOm);
			}
		}
	}
	
	/*
	class AddDomainObjectAction extends Action {
		Object selObj;
		public AddDomainObjectAction(Object selObj) {
			super("Add Override" , ClusterConfigImages.getImageDescriptor(ClusterConfigImages.IMG_TOOLBAR_LIST_ADD));
			this.selObj = selObj;
		}
		public void run() {
			addDomainObject(selObj);
		}
	}
	*/
	
	class RemoveDomainObjectAction extends Action {
		Object selObj;
		public RemoveDomainObjectAction(Object selObj) {
			super("Remove" , StudioUIPlugin.getImageDescriptor(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE));
			this.selObj = selObj;
		}
		public void run() {
			removeDomainObject(selObj);
		}
	}
	
	private void addDomainObject(final Shell shell, Object selObj) {
		//Object newObj = modelmgr.addDomainObject("/uri");
		//BlockUtil.refreshViewer(viewer, selObj, newObj);
		
		CheckedTreeSelectionDialog dialog = new CheckedTreeSelectionDialog(shell, new EntitySelectionLabelProvider(), new EntitySelectionTreeContentProvider());
		dialog.setTitle("Entity Selection");
		dialog.setMessage("Select the entities for project [" + modelmgr.project.getName() + "]:");
		dialog.setEmptyListMessage("There are no entities to select in this project.");
		dialog.setInput(page.getEditor().getEditorInput());
		dialog.setBlockOnOpen(true);
		int ret = dialog.open();
		if (ret == CheckedTreeSelectionDialog.CANCEL)
			return;
				
		Object[] newObjs = dialog.getResult();
		Object newSelObj = null;
		for (Object newObj: newObjs) {
			EntityElement ee = (EntityElement) newObj;
			Object addedDomainObj = modelmgr.addDomainObject(ee);
			if (newSelObj == null)
				newSelObj = addedDomainObj;
		}
		BlockUtil.refreshViewer(viewer, selObj, newSelObj);
	}
	
	private void removeDomainObject(Object selObj) {
		boolean updated = modelmgr.removeDomainObject((DomainObject)selObj);
		if (updated) {
			BlockUtil.refreshViewer(viewer);
			resetActionButtons();
		}
	}
	
	private Listener getClusterOmBlockAddListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					if (selection.size() != 1)
						return;
					Object selObj = selection.getFirstElement();
					if (selObj instanceof DomainObjectOverrides) {
						addDomainObject(shell, selObj);
					}
				}
			}
		};
		return listener;
	}

	private Listener getClusterOmBlockRemoveListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					for (Object selObj: selection.toList()) {
					//Object selObj = selection.getFirstElement();
						if (selObj instanceof DomainObject) {
							removeDomainObject(selObj);
						}
					}
				}
			}
		};
		return listener;
	}

	@Override
	protected void enableActionButtons(Object selObj) {
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
		if (selObj instanceof DomainObjectOverrides) {
			bAdd.setEnabled(true);
		} else if (selObj instanceof DomainObject) {
			bRemove.setEnabled(true);
		}
	}

	@Override
	protected void resetActionButtons() {
		bAdd.setEnabled(false);
		bRemove.setEnabled(false);
	}
	
	class MasterContentProvider extends ClusterConfigBlockMasterContentProvider {

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof CacheOm) {
				CacheOm cacheOm = (CacheOm) element;
				return new Object[]{ cacheOm.bs, cacheOm.domainObjects };
			} else if (element instanceof BackingStore) {
				BackingStore bs = (BackingStore) element;
				return new Object[]{ bs.primaryConnection };
			} else if (element instanceof DomainObjects) {
				DomainObjects domainObjs = (DomainObjects) element;
				return new Object[]{ domainObjs.domainObjDefault, domainObjs.domainObjOverrides }; 
			} else if (element instanceof DomainObjectOverrides) {
				return ((DomainObjectOverrides)element).overrides.toArray(new DomainObject[0]);
			}
			return new String[0];
		}
		
		@Override
		public Object[] getElements(Object inputElement) {
			ArrayList<Object> elements = new ArrayList<Object>();
			elements.add(modelmgr.getClusterInfo());
			if (modelmgr.getOmMgr().equals(ObjectManagement.MEMORY_MGR)) {
				elements.add(modelmgr.getModel().om.memOm);
			} else if (modelmgr.getOmMgr().equals(ObjectManagement.CACHE_MGR)) {
				if (!AddonUtil.isExpressEdition()) {
					elements.add(modelmgr.getModel().om.cacheOm);
				} else {	// Default to In-Memory
					modelmgr.setOmMgr(ObjectManagement.MEMORY_MGR);
					elements.add(modelmgr.getModel().om.memOm);
				}
			} 
//			else if (modelmgr.getOmMgr().equals(ObjectManagement.BDB_MGR)) {
//				if (!AddonUtil.isExpressEdition()) {
//					elements.add(modelmgr.getModel().om.bdbOm);
//				} else {	// Default to In-Memory
//					modelmgr.setOmMgr(ObjectManagement.MEMORY_MGR);
//					elements.add(modelmgr.getModel().om.memOm);
//				}
//			}
			if (AddonUtil.isDataModelingAddonInstalled())
				elements.add(modelmgr.getModel().om.dbConcepts);
			elements.add(modelmgr.getPropertyElementList());
			return (elements.toArray(new Object[0]));
		}
	}

	class MasterLabelProvider extends ClusterConfigBlockMasterLabelProvider {
		@Override
		public Image getImage(Object element) {
			if (element instanceof ClusterInfo) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GENERAL));
			} else if (element instanceof ObjectManager) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_OBJECT_MGMT));
			} else if (element instanceof BackingStore) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_BACKING_STORE));
			} else if (element instanceof Connection) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_CONNECTION));
			} else if (element instanceof DbConcepts) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DBCONCEPTS));
			} else if (element instanceof DomainObjects) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DOMAIN_OBJECTS));
			} else if (element instanceof DomainObject) {
				//return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DOMAIN_OBJECT));
				return getEntityImage(((DomainObject)element).entity);
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		
		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof ClusterInfo) {
				return ("General");
			} else if (element instanceof BdbOm) {
				return ("Object Management: [Berkeley DB]");				
			} else if (element instanceof CacheOm) {
				return ("Object Management: [Cache]");
			} else if (element instanceof MemoryOm) {
				return ("Object Management: [In Memory]");
			} else if (element instanceof BackingStore) {
				return ("Backing Store");
			} else if (element instanceof Connection) {
				Connection con = (Connection)element;
				if (con.isPrimary)
					return ("Connection");
				else
					return ("Secondary Connection");
			} else if (element instanceof DomainObjects) {
				return ("Domain Objects");
			} else if (element instanceof DomainObjectDefault) {
				return ("Default");
			} else if (element instanceof DomainObjectOverrides) {
				return ("Overrides");
			} else if (element instanceof DomainObject) {
				return ((DomainObject)element).values.get(ConfigNS.Elements.URI.localName);
			} else if (element instanceof DbConcepts) {
				return ("Database Concepts");
			} else if (element instanceof PropertyElementList) {
				return ("Properties");
			}
			return element.toString();
		}
	}
	
	class EntitySelectionTreeContentProvider extends ClusterConfigBlockMasterContentProvider {

		private ArrayList<EntityElement> entities;
		
		class Folder {
			String name;
			ArrayList<Folder> subfolders;
			ArrayList<EntityElement> elements;
			
			public Folder() {
				subfolders = new ArrayList<Folder>();
				elements = new ArrayList<EntityElement>();
			}
		}
		
		private void processEntities() {
			
		}
		
		public EntitySelectionTreeContentProvider() {
			entities = new ArrayList<EntityElement>();
			ArrayList<EntityElement> projEntities = ClusterConfigProjectUtils.getProjectDomainObjectOverrideEntities(modelmgr.project);
			ArrayList<EntityElement> cddEntities = new ArrayList<EntityElement>();
			
			for (DomainObject domainObj: modelmgr.getModel().om.cacheOm.domainObjects.domainObjOverrides.overrides) {
				if (domainObj.entity != null) {
					cddEntities.add(domainObj.entity);
				}
			}
			
			for (EntityElement projEntity: projEntities) {
				boolean found = false;
				for (EntityElement cddEntity: cddEntities) {
					if (cddEntity.getFolder().equals(projEntity.getFolder()) && cddEntity.getName().equals(projEntity.getName())) {
						found = true;
						break;
					}
				}
				if (!found)
					entities.add(projEntity);
			}
			
			processEntities();
		}
		
		@Override
		public Object[] getChildren(Object parentElement) {
			return new String[0];
		}

		@Override
		public Object[] getElements(Object inputElement) {
			/*
			Set<String> rootPath = new HashSet<String>();
			for (EntityElement ee: entities) {
				String path = ee.getFolder();
				String pathTok[] = path.split("/");
				if (pathTok.length > 0) {
					rootPath.add(pathTok[1]);
				}
			}
			return rootPath.toArray(new String[0]);
			*/
			return entities.toArray(new EntityElement[0]);
		}
	}
	
	class EntitySelectionLabelProvider extends ClusterConfigBlockMasterLabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof EntityElement) {
				return getEntityImage((EntityElement) element);
			} else if (element instanceof String) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP));
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof EntityElement) {
				EntityElement ee = (EntityElement) element;
				return ee.getFolder() + ee.getName();
			}
			return element.toString();
		}
	}
}