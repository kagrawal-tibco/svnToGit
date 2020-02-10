package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfigsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;

/*
@author ssailapp
@date Jan 15, 2010 11:17:20 PM
 */

public abstract class ClusterConfigBlock extends MasterDetailsBlock {
	protected FormPage page;
	protected ClusterConfigModelMgr modelmgr;
	protected TreeViewer viewer;
	protected int EXPAND_LEVEL = 1;
	
	public ClusterConfigBlock(FormPage page, ClusterConfigModelMgr modelmgr) {
		this.page = page;
		this.modelmgr = modelmgr;
	}

	@Override
	public void createContent(IManagedForm managedForm) {
		super.createContent(managedForm);
		sashForm.setWeights(new int[]{30,70});
	}
	
	public void updateBlock() {
		if (viewer != null) {
			if (viewer.getTree() != null) {
				if (!viewer.getTree().isDisposed()) {
					//viewer.getTree().notifyListeners(SWT.Selection, new Event());
				}
			}
			BlockUtil.refreshViewer(viewer);
			BlockUtil.expandViewer(viewer, EXPAND_LEVEL);
		}
	}
	
	@Override
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(Rule.class, new NodeRulePage(modelmgr, viewer));
		detailsPart.registerPage(Destination.class, new NodeDestinationPage(modelmgr, viewer));
		detailsPart.registerPage(Function.class, new NodeFunctionPage(modelmgr, viewer));
		detailsPart.registerPage(Process.class, new NodeProcessPage(modelmgr, viewer));
	}

	@Override
	protected abstract void createMasterPart(IManagedForm managedForm, Composite parent);
	protected abstract void enableActionButtons(Object object);
	protected abstract void resetActionButtons();

	protected void addDoubleClickListener(final TreeViewer viewer) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				BlockUtil.refreshViewer(viewer, obj, null);
			}
		});
	}
	
	protected void registerSelectionListener() {
		ISelectionChangedListener listener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					if (selObj != null) {
						enableActionButtons(selObj);
					}
				}
			}
		};
		viewer.addSelectionChangedListener(listener);
	}
	
	protected Listener getMoveUpFunctionsListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer == null)
					return;
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					if (selObj instanceof FunctionElement) { 
						boolean updated = modelmgr.moveUpFunction((FunctionElement)selObj);
						if (updated) {
							BlockUtil.refreshViewer(viewer);
							enableActionButtons(selObj);
						}
					}
				}
			}
		};
		return listener;	
	}
	
	protected Listener getMoveDownFunctionsListener(final Shell shell) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (viewer == null)
					return;
				if (viewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
					Object selObj = selection.getFirstElement();
					if (selObj instanceof FunctionElement) { 
						boolean updated = modelmgr.moveDownFunction((FunctionElement)selObj);
						if (updated) {
							BlockUtil.refreshViewer(viewer);
							enableActionButtons(selObj);
						}
					}
					
				}
			}
		};
		return listener;	
	}
	
	@Override
	protected void createToolBarActions(IManagedForm managedForm) {
		//TODO - Add toolbar icons, if we need to toggle between horizontal and vertical views
	}
	
	abstract class ClusterConfigBlockMasterContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof RuleElementsList) {
				return ((RuleElementsList)element).ruleElements.toArray(new RulesGrp[0]);
			} else if (element instanceof RulesGrp) {
				return modelmgr.getRules((RulesGrp)element);
			} if (element instanceof DestinationElementsList) {
				return ((DestinationElementsList)element).destinationElements.toArray(new DestinationsGrp[0]);
			} else if (element instanceof DestinationsGrp) {
				return modelmgr.getDestinations((DestinationsGrp)element);
			} if (element instanceof FunctionElementsList) {
				return ((FunctionElementsList)element).functionElements.toArray(new FunctionsGrp[0]);
			} else if (element instanceof FunctionsGrp) {
				return modelmgr.getFunctions((FunctionsGrp)element);
			} if (element instanceof ProcessElementsList) {
				return ((ProcessElementsList)element).processElements.toArray(new ProcessesGrp[0]);
			} else if (element instanceof ProcessesGrp) {
				return modelmgr.getProcesses((ProcessesGrp)element);
			} if (element instanceof LogConfigsList) {
				return ((LogConfigsList)element).logConfigs.toArray(new LogConfig[0]);
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
		public abstract Object[] getElements(Object inputElement);
		
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	class ClusterConfigBlockMasterLabelProvider implements ILabelProvider {
		private ArrayList listeners;
		
		public ClusterConfigBlockMasterLabelProvider() { 
			 listeners = new ArrayList();
		}
		
		@Override
		public Image getImage(Object element) {
			if (element instanceof RuleElementsList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof RulesGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP));
			} else if (element instanceof Rule) {
				if (((Rule)element).isRef)
					return ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE);
				else if(((Rule)element).isTemplate)
					return ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_RULE_TEMPLATE);
				else
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_RULES));
			} else if (element instanceof DestinationElementsList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof DestinationsGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP));
			} else if (element instanceof Destination) {
				if (!((Destination)element).isRef)
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_DESTINATION));
				else
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE));
			} else if (element instanceof FunctionElementsList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof FunctionsGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP));
			} else if (element instanceof Function) {
				if (!((Function) element).isRef){
					if(((Function) element).parent instanceof ProcessAgent)
						return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROCESS));
					else
						return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_FUNCTION));
				}
				else
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_REFERENCE));
			} else if (element instanceof ProcessElementsList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof ProcessesGrp) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_GROUP));
			} else if (element instanceof Process) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROCESS));
			} else if (element instanceof LogConfigsList) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
			} else if (element instanceof LogConfig) {
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_LOG_CONFIG));
			} 
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

		protected Image getEntityImage(EntityElement entity) {
			if (entity != null) {
				if (entity.getElementType() == ELEMENT_TYPES.CONCEPT) 
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_CONCEPT));
				else if (entity.getElementType() == ELEMENT_TYPES.SCORECARD)
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_SCORECARD));
				else if (entity.getElementType() == ELEMENT_TYPES.TIME_EVENT)
					return (EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
				else if (entity.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT)
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_SIMPLEEVENT));
				else if (entity.getElementType() == ELEMENT_TYPES.METRIC)
					return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_METRIC));		
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
		
		@Override
		public String getText(Object element) {
			if (element == null)
				return ("");
			if (element instanceof RuleElementsList) {
				return ("Rules");
			} else if (element instanceof RulesGrp) {
				return ((RulesGrp)element).id;
			} else if (element instanceof Rule) {
				return ((Rule)element).uri;
			} else if (element instanceof DestinationElementsList) {
				return ("Input Destinations");
			} else if (element instanceof DestinationsGrp) {
				return ((DestinationsGrp)element).id;
			} else if (element instanceof Destination) {
				return ((Destination)element).id;
			} else if (element instanceof FunctionElementsList) {
				return ("Functions");
			} else if (element instanceof FunctionsGrp) {
				return ((FunctionsGrp)element).id;
			} else if (element instanceof Function) {
				return ((Function) element).uri;
			} else if (element instanceof ProcessElementsList) {
				return ("Processes");
			} else if (element instanceof ProcessesGrp) {
				return ((ProcessesGrp)element).id;
			} else if (element instanceof Process) {
				return ((Process) element).uri;
			} else if (element instanceof LogConfigsList) {
				return ("Log Configurations");
			} else if (element instanceof LogConfig) {
				return ((LogConfig)element).id;
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

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
		
		@Override
		public void removeListener(ILabelProviderListener listener) {
			listeners.remove(listener);
		}
	}
	
}
