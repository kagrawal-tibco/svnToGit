package com.tibco.cep.studio.debug.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.views.breakpoints.BreakpointContainer;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.views.RuleBreakpointsView;
import com.tibco.cep.studio.debug.ui.views.RuleStackView;
@SuppressWarnings({"rawtypes","unchecked","restriction"})
public class StudioShowSupportedBreakpointsAction extends ToggleFilterAction implements ISelectionListener {
	public static final String PREFIX = IDebugUIConstants.PLUGIN_ID + "."; //$NON-NLS-1$
	
	/**
	 * The view associated with this action
	 */
	private RuleBreakpointsView fView;
	
	/**
	 * The list of identifiers for the current state
	 */
	private List fDebugTargets= new ArrayList(2);
	
	/**
	 * A viewer filter that selects breakpoints that have
	 * the same model identifier as the selected debug element
	 */
	class BreakpointFilter extends ViewerFilter {
		
		/**
		 * @see ViewerFilter#select(Viewer, Object, Object)
		 */
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof BreakpointContainer) {
				// Breakpoint containers are visible if any of their children are visible.
				IBreakpoint[] breakpoints = ((BreakpointContainer) element).getBreakpoints();
				for (int i = 0; i < breakpoints.length; i++) {
					if (select(viewer, element, breakpoints[i])) {
						return true;
					}
				}
				return false;
			}
			IBreakpoint breakpoint= (IBreakpoint)element;
			if (fDebugTargets.isEmpty()) {
				return true;
			}
			Iterator<?> iterator= fDebugTargets.iterator();
			while (iterator.hasNext()) {
				IDebugTarget target = (IDebugTarget) iterator.next();
				if (target.supportsBreakpoint(breakpoint)) {
					return true;
				}
				
			}
			return false;
		}

	}

	public StudioShowSupportedBreakpointsAction(StructuredViewer viewer, IViewPart view) {
		super();
		setText("Show Supported &Breakpoints"); 
		setToolTipText("Show Breakpoints Supported by Selected Target"); 
		setViewerFilter(new BreakpointFilter());
		setViewer(viewer);
		setImageDescriptor(StudioDebugUIPlugin.getImageDescriptor("icons/debugt_obj.gif"));
		setChecked(false);
		setId(StudioDebugUIPlugin.getUniqueIdentifier() + ".ShowSupportedBreakpointsAction"); //$NON-NLS-1$
		
		setView(view);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			this,
			PREFIX + "show_breakpoints_for_model_action_context");
		
	}
	
		
	public void dispose() {
		if (isChecked()) {
			getView().getSite().getPage().removeSelectionListener(RuleStackView.RULE_STACK_VIEW_ID, this);
		}
	}
	
	/**
	 * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss= (IStructuredSelection)selection;
			List debugTargets= getDebugTargets(ss);
			if (!isChecked()) {
				fDebugTargets= debugTargets;
				return;
			}
			if (debugTargets.isEmpty()) {
				 if(fDebugTargets.isEmpty()) {
					return;
				 } 
				 reapplyFilters(debugTargets);
				 return;
			}
			if (fDebugTargets.isEmpty()) {
				reapplyFilters(debugTargets);
				return;
			}
			
			if (debugTargets.size() == fDebugTargets.size()) {
				List copy= new ArrayList(debugTargets.size());
				Iterator<IDebugTarget> iter= fDebugTargets.iterator();
				while (iter.hasNext()) {
					IDebugTarget target = (IDebugTarget) iter.next();
					Iterator newDebugTargets= debugTargets.iterator();
					while (newDebugTargets.hasNext()) {
						IDebugTarget newTarget= (IDebugTarget)newDebugTargets.next();
						copy.add(newTarget);
						if (target.equals(newTarget)) {
							newDebugTargets.remove();
						}
					}
				}
				//check for real change
				if (debugTargets.isEmpty()) {
					return;
				}
				reapplyFilters(copy);
			} 
		}
	}

	
	/**
	 * Selection has changed in the debug view
	 * need to reapply the filters.
	 */
	protected void reapplyFilters(List debugTargets) {
		fDebugTargets= debugTargets;		
		getViewer().refresh();
	}
	
	protected IViewPart getView() {
		return fView;
	}

	protected void setView(IViewPart view) {
		fView = (RuleBreakpointsView) view;
	}
	
	protected List getDebugTargets(IStructuredSelection ss) {
		List debugTargets= new ArrayList(2);
		Iterator i= ss.iterator();
		while (i.hasNext()) {
			Object next= i.next();
			if (next instanceof IDebugElement) {
				debugTargets.add(((IDebugElement)next).getDebugTarget());
			} else if (next instanceof ILaunch) {
				IDebugTarget[] targets= ((ILaunch)next).getDebugTargets();
				for (int j = 0; j < targets.length; j++) {
					debugTargets.add(targets[j]);
				}
			} else if (next instanceof IProcess) {
				IDebugTarget target= (IDebugTarget)((IProcess)next).getAdapter(IDebugTarget.class);
				if (target != null) {
					debugTargets.add(target);
				}
			}	
		}
		return debugTargets;
	}
	
	/**
	 * Adds or removes the viewer filter depending
	 * on the value of the parameter.
	 */
	protected void valueChanged(boolean on) {
		if (getViewer().getControl().isDisposed()) {
			return;
		}
		if (on) {
			getView().getSite().getPage().addSelectionListener(RuleStackView.RULE_STACK_VIEW_ID, this);
			ISelection selection= getView().getSite().getPage().getSelection(RuleStackView.RULE_STACK_VIEW_ID);
			selectionChanged(null, selection);
		} else {
			getView().getSite().getPage().removeSelectionListener(RuleStackView.RULE_STACK_VIEW_ID, this);
		}
		super.valueChanged(on);
		fView.getViewer().refresh();
	}

}
