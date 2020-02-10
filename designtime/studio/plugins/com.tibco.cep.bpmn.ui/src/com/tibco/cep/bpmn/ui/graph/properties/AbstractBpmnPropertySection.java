package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverviewAndDiagram;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Panel;
import java.util.List;
import java.util.Map;

import javax.swing.JRootPane;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IEditorStatusLine;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.IProcessXPathValidate;
import com.tibco.cep.bpmn.ui.ProcessXPathWizard;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tibco.cep.studio.ui.viewers.PopupTreeViewer;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public abstract class AbstractBpmnPropertySection extends AbstractPropertySection {
	public static Color COLOR_RED;
	public static Color COLOR_BLACK;
	public static Color COLOR_GRAY;

	static {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				COLOR_RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);				
				COLOR_BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
				COLOR_GRAY = new Color(Display.getDefault(), 232,232,232);
			}
		});
	}
	
	protected PropertySheetPage fPropertySheetPage;
	protected TSEGraph fTSEGraph;
	protected TSENode fTSENode;
	protected TSEEdge fTSEEdge;
	protected List<TSEObject> fNodeList;
	protected IProject fProject;
	protected BpmnEditor fEditor;
	private IEditorStatusLine fStatusLine;
	protected boolean disposed;
	protected Frame awtframe;
	protected TSEConnector fTSEConnector;
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.disposed = false;
		this.fPropertySheetPage = (PropertySheetPage) tabbedPropertySheetPage;
		super.createControls(parent, tabbedPropertySheetPage);
		//Added for (BE-18336)
		if (fEditor==null && fPropertySheetPage !=null)
			fEditor=(BpmnEditor) fPropertySheetPage.getSite().getPage().getActiveEditor();
		if (fPropertySheetPage !=null && fPropertySheetPage.getEditor()!=null )
			parent.setEnabled(fPropertySheetPage.getEditor().isEditable());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IGraphSelection)) {
			return;
		}
		
		IGraphSelection graphSelection = (IGraphSelection) selection;
		if(graphSelection.isEmpty()) {
			return;
		}
		TSEObject graphObject = graphSelection.getGraphObject();
		fEditor = (BpmnEditor) getPart();
		fProject = ((BpmnEditorInput)fEditor.getEditorInput()).getFile().getProject();
		fStatusLine= (IEditorStatusLine) fEditor.getAdapter(IEditorStatusLine.class);
		
		if (graphObject instanceof TSEGraph) {
			fTSEGraph = (TSEGraph) graphObject;
			fNodeList = graphSelection.toList();
			if (fTSEGraph.getParent() instanceof TSENode)
				fTSENode = (TSENode) fTSEGraph.getParent();
			fTSEEdge = null;
			fTSEConnector = null;
		}
		if (graphObject instanceof TSEEdge) {
			fTSEEdge = (TSEEdge) graphObject;
			fNodeList = graphSelection.toList();
			fTSENode = null;
			fTSEGraph = null;
			fTSEConnector = null;
		}
		if (graphObject instanceof TSENode) {
			fTSENode = (TSENode) graphObject;
			fNodeList = graphSelection.toList();
			fTSEEdge = null;
			fTSEGraph = null;
			fTSEConnector = null;
		}
		if (graphObject instanceof TSEConnector) {
			fTSEConnector = (TSEConnector) graphObject;
			fNodeList = graphSelection.toList();
			fTSEEdge = null;
			fTSEGraph = null;
			fTSENode = null;
		}
	}
	
	protected void asyncEditorModified(){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				fEditor.modified();
			}});
	}
	
	protected BpmnDiagramManager getDiagramManager() {
		return (BpmnDiagramManager) this.fEditor.getDiagramManager();
	}
	
	
	public PropertySheetPage getPropertySheetPage() {
		return fPropertySheetPage;
	}
	
	protected IProject getProject() {
		return fProject;
	}
	
	abstract protected void updatePropertySection(Map<String, Object> updateList);
	/**
	 * @param parent
	 * @return
	 */
	@SuppressWarnings("serial")
	protected Container getSwingContainer(Composite parent) {
		java.awt.Frame frame = SWT_AWT.new_Frame(parent);
		setFrame(frame);
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				try {
					paint(g);
				} catch (Exception e) {
					// 
				}
				
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}
	
	
	/**
	 * Reports any status to the current active workbench shell
	 * @param message the message to display
	 */
	protected void report(final String message,final boolean autoClear) {
		BpmnUIPlugin.getStandardDisplay().asyncExec(new Runnable() {
			public void run() {
				if (fStatusLine != null) {
					fStatusLine.setMessage(true, message, null);
				}
				if (message != null && BpmnUIPlugin.getActiveWorkbenchShell() != null) {
					Display.getCurrent().beep();
				}
			}
		});
		
		if(autoClear) {
			Job clearStatusJob  = new Job("Clear status line"){
				
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					report(message,false);
					return Status.OK_STATUS;
				}
			};
			clearStatusJob.schedule(30000);
		}
	}
	
	/**
	 * @param parentControl
	 * @param input
	 * @param viewerFilter
	 * @return
	 */
	protected ISelection getPopupTreeSelection(Control parentControl,Object input,ViewerFilter [] viewerFilter) {
		if(parentControl == null) {
			return null;
		}
		PopupTreeViewer treeViewer = new PopupTreeViewer(parentControl.getShell());
		if(viewerFilter != null) {
			treeViewer.setViewerFilter(viewerFilter);
		}
		if(input == null) {
			return null;
		}
		treeViewer.setInput(input);
		treeViewer.expandAll();
		Point location = parentControl.getLocation();
		Point dloc = parentControl.getParent().toDisplay(location);
		Rectangle bounds = parentControl.getBounds();
		bounds.x=dloc.x;
		bounds.y=dloc.y;
		return treeViewer.open(bounds);
	}
	
	/**
	 * @param parentControl
	 * @param input
	 * @param labelProvider
	 * @param contentProvider
	 * @param viewerFilter
	 * @return
	 */
	protected ISelection getPopupTreeSelection(
			Control parentControl,
			Object input,
			IBaseLabelProvider labelProvider,
			ITreeContentProvider contentProvider,
			ViewerFilter [] viewerFilter) {
		if(parentControl == null) {
			return null;
		}
		PopupTreeViewer treeViewer = new PopupTreeViewer(parentControl.getShell(),labelProvider,contentProvider,SWT.NONE);
		if(viewerFilter != null) {
			treeViewer.setViewerFilter(viewerFilter);
		}
		if(input == null) {
			return null;
		}
		treeViewer.setInput(input);
		treeViewer.expandAll();
		Point location = parentControl.getLocation();
		Point dloc = parentControl.getParent().toDisplay(location);
		Rectangle bounds = parentControl.getBounds();
		bounds.x=dloc.x;
		bounds.y=dloc.y;
		return treeViewer.open(bounds);
	}

	protected TSEGraph getGraph() {
		return fTSEGraph;
	}

	protected TSENode getNode() {
		return fTSENode;
	}

	protected TSEEdge getEdge() {
		return fTSEEdge;
	}
	
	@Override
	public void dispose() {
		this.disposed = true;
		super.dispose();
	}
	
	public boolean isDisposed() {
		return disposed;
	}
	
	protected void refreshOverviewView(){
		refreshOverview(getDiagramManager().getEditor().getEditorSite(), true, true);
	}
	
	public void doRefresh(TSEObject object, String label){
		fPropertySheetPage.doRefreshTitleBar(object, label);// Refresh Title for Property Name Change
		refreshOverviewAndDiagram(fEditor.getEditorSite(), true, true, fEditor.getBpmnGraphDiagramManager(), true);
	}
	
	protected TSEGraph getRootGraph(TSEGraph graph){
		EObject userObject = (EObject) graph.getUserObject();
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
//		EObjectWrapper<EClass, EObject> processWrapper = null;
		if(userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
			return graph;
		} else	if(userObjWrapper.isInstanceOf(BpmnModelClass.LANE)) {
			TSGraphMember parent = graph.getParent();
			if(parent != null && parent instanceof TSNode) {
//				TSNode parentNode = (TSNode) parent;
				TSGraphObject parentOwner = parent.getOwner();
				if(parentOwner != null && parentOwner instanceof TSEGraph) {
					TSEGraph parentGraph = (TSEGraph) parentOwner;
					if(parentGraph.getParent() == null) { // root graph for process
						// then this lane is a top level lane
						EObject pgUserObject = (EObject) parentGraph.getUserObject();
						EObjectWrapper<EClass, EObject> pgUserObjWrapper = EObjectWrapper.wrap(pgUserObject);
						if(pgUserObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)){
							return parentGraph;
						}								
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param text
	 */
	public void invokeProcessXPathDialog(final Text text, final EObjectWrapper<EClass, EObject> wrapper, final IProcessXPathValidate validator, final String...loopArgs ) {
		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					EObjectWrapper<EClass, EObject> process = getDiagramManager().getModelController().getModelRoot();
					ProcessXPathWizard wizard = new ProcessXPathWizard(getDiagramManager().getProject(), text,validator , wrapper, process, loopArgs);
					WizardDialog dialog = new WizardDialog(fEditor.getSite().getShell(), wizard) {
						@Override
						protected void createButtonsForButtonBar(Composite parent) {
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
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	/**
	 * @return the awtframe
	 */
	public Frame getFrame() {
		return awtframe;
	}

	/**
	 * @param awtframe
	 *            the Frame to set
	 */
	public void setFrame(Frame awtframe) {
		this.awtframe = awtframe;
	}


}