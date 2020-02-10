package com.tibco.cep.studio.debug.ui.views;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISaveablePart2;

import com.tibco.cep.studio.debug.core.model.RtcAgendaFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

/*
 @author ssailapp
 @date Jun 26, 2009 4:57:55 PM
 */
@SuppressWarnings({"unused"})
public class RuleDebugAgendaView extends AbstractDebugView implements
		IDebugContextListener {

	class RuleAgendaContentProvider implements ITreeContentProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
				if(inputElement instanceof RuleDebugStackFrame) {
					RuleDebugStackFrame sf = (RuleDebugStackFrame) inputElement;
						try {
							return sf.getVariables();
						} catch (DebugException e) {
							handleException(e);
						}
				} else if(inputElement instanceof IValue ) {
					IValue val = (IValue) inputElement;
					if(val != null) {
						try {
							return val.getVariables();
						} catch (DebugException e) {
							handleException(e);
						}
					}
				} else if(inputElement instanceof IVariable) {
					IVariable v = (IVariable) inputElement;
					try {
						return new Object[] { v.getValue() };
					} catch (DebugException e) {
						handleException(e);
					}					
				} 
		
			return new Object[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		public Object getParent(Object element) {
			if (element == null) {
				return null;
			}
			if(element instanceof RuleDebugStackFrame) {
				
			} else if (element instanceof IVariable) {
				
			} else if (element instanceof IValue) {
				IValue val = (IValue) element;
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		public boolean hasChildren(Object element) {
			try {
				if(element instanceof RuleDebugStackFrame) {
					RuleDebugStackFrame sf = (RuleDebugStackFrame) element;
					return sf.hasVariables();
				} else if( element instanceof IVariable) {
					IVariable var = (IVariable) element;
					IValue val = var.getValue();
					return val != null;
				} else if (element instanceof IValue) {
					IValue  val = (IValue) element;
					return val.hasVariables();
				}
			} catch (Exception e) {
				handleException(e);
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			treeViewer.getTree().clearAll(true);
		}

		
		
	}


	class RuleAgendaLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		FontRegistry registry = new FontRegistry();

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {

			switch(columnIndex) {
			case 0:
				if(element instanceof IVariable) {
					IVariable var = (IVariable) element;
					try {
						return var.getName();
					} catch (DebugException e) {
						handleException(e);
						return "";
					}
				}
			case 1:
				if(element instanceof IVariable) {
					IVariable var = (IVariable) element;
					try {
						IValue val = var.getValue();
						return val.getValueString();
					} catch (DebugException e) {
						handleException(e);
						return "";
					}
				}
			default:
				return "";
			}
		}

	}


	public static final String RULE_AGENDA_VIEW_ID = "com.tibco.cep.studio.debug.ui.views.ruleagendaview";


	

	private Composite parent;

	private TreeViewer treeViewer;
	
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.AbstractDebugView#configureToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	@Override
	protected void configureToolBar(IToolBarManager tbm) {
		
	}
	
	protected void contextActivated(ISelection selection) {
		if (!isAvailable() || !isVisible()) {
			return;
		}
		if (selection instanceof IStructuredSelection) {
			Object source = ((IStructuredSelection)selection).getFirstElement();
			Object adapted = DebugPlugin.getAdapter(source, RuleDebugStackFrame.class);
			if(adapted instanceof RuleDebugStackFrame) {
				RuleDebugStackFrame  sf = (RuleDebugStackFrame) getInputContext(adapted);
				treeViewer.setInput(sf);
				System.out.println();
			} else {
				treeViewer.setInput(null);
			}
			//fInputService.resolveViewerInput(source);
			// extract RuleStackFrame and get AgendaFrame
			//			treeViewer.setInput(input);
		}
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.AbstractDebugView#createActions()
	 */
	@Override
	protected void createActions() {
		// TODO Auto-generated method stub
		
	}

	private void createColumnViewers() {
		createTreeViewerColumn("Variable"); //$NON-NLS-1$
		createTreeViewerColumn("Value");	//$NON-NLS-1$	
		Tree tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		
		for (int i=0; i<tree.getColumnCount(); i++) {
			tree.getColumn(i).pack();
		}
		
		tree.getColumn(0).setWidth(250);
		tree.getColumn(1).setWidth(750);
	}
	
	private TreeViewerColumn createTreeViewerColumn(String colName) {
		TreeViewerColumn column = new TreeViewerColumn(treeViewer, SWT.NONE);
		column.getColumn().setText(colName);
		column.getColumn().setResizable(true);
		column.getColumn().setMoveable(false);
		return column;
	}
	
	@Override
	protected Viewer createViewer(Composite parent) {
		this.parent = parent;
		this.treeViewer = new TreeViewer(parent,SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		treeViewer.getTree().setLinesVisible(true);
		createColumnViewers();
		treeViewer.setLabelProvider(new RuleAgendaLabelProvider());
		treeViewer.setContentProvider(new RuleAgendaContentProvider());
		DebugUITools.addPartDebugContextListener(getSite(), this);
		return treeViewer;
	}
	
	public void dispose() {
        DebugUITools.removePartDebugContextListener(getSite(), this);
		if (treeViewer != null) {
			treeViewer = null;
		}
		super.dispose();
	}
	
	@Override
	public void debugContextChanged(DebugContextEvent event) {
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			contextActivated(event.getContext());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.AbstractDebugView#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	protected void fillContextMenu(IMenuManager menu) {
		
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.AbstractDebugView#getHelpContextId()
	 */
	@Override
	protected String getHelpContextId() {
		return null;
	}

	public Object getInputContext(Object context) {
		if (context instanceof RuleDebugStackFrame) {
			RtcAgendaFrame rtcFrame = ((RuleDebugStackFrame) context)
					.getRtcAgendaFrame();
			return rtcFrame;
		} else {
			return context;
		}
	}

	public void handleException(Exception e) {
		showMessage(e.getMessage());
		StudioDebugUIPlugin.log(e);
	}

	public boolean isAvailable() {
		return !(getViewer() == null || getViewer().getControl() == null || getViewer().getControl().isDisposed());
	}

	public int promptToSaveOnClose() {
		return ISaveablePart2.YES;
	}
	
	

}
