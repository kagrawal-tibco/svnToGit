/**
 * 
 */
package com.tibco.cep.diagramming.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.service.layout.TSLayoutConstants;

/**
 * @author sshekhar
 *
 */
public class LeftLayoutActionDelegate implements IEditorActionDelegate {

	private IWorkbenchPage page;
	private BpmnEditor editor ;
	private BpmnDiagramManager diagramManager ;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run( IAction action ) {
		
		 editor = ( BpmnEditor )page.getActiveEditor() ;
		 diagramManager = ( BpmnDiagramManager )editor.getDiagramManager() ;
		 try {
			 leftAlignNodes();
		 } catch ( Exception e ) {
			 e.printStackTrace() ;
		 }
	}

	public void leftAlignNodes(){
		boolean isSubprocess = false;
		List< TSENode > nodes = diagramManager.findNodesOfType( ( TSEGraph ) diagramManager.getGraphManager().getMainDisplayGraph() , BpmnModelClass.FLOW_NODE );
		
		List< TSENode > outgoingNode=new ArrayList< TSENode >();
		List< TSENode > otherNodes=new ArrayList< TSENode >();
		
		TSENode subProc = null ;
		for( TSENode node : nodes ) {
			EObject eObj = ( EObject ) node.getUserObject();
			EObjectWrapper< EClass , EObject > eobjWrap = EObjectWrapper.wrap( eObj );
//			System.out.println( eobjWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
			if ( BpmnModelClass.SUB_PROCESS.isInstance( eObj ) ) {
				subProc = node ;
			}
//			List outGoingEdgeList=( EList )eobjWrap.getListAttribute( BpmnMetaModelConstants.E_ATTR_OUTGOING );
			EObject eobjP = BpmnModelUtils.getFlowElementContainer( eObj );
			EList< EObject > listAttribute = null ;
			if (  eobjWrap.containsAttribute( BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS ) )
				 listAttribute = eobjWrap
						.getListAttribute( BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS ) ;
			
			if ( eobjP != null && BpmnModelClass.SUB_PROCESS.isSuperTypeOf( eobjP.eClass() ) ) {
				if ( listAttribute != null && listAttribute.size() != 0 && subProc != null ) {
					
					outgoingNode.add( subProc );
					isSubprocess = true ;
				}
			}
			
			else if ( eobjWrap.isInstanceOf( BpmnModelClass.START_EVENT )  ) {
				outgoingNode.add( node );
			}
			else {
				otherNodes.add( node ) ;
			}
				
		}
		if ( !isSubprocess && subProc != null ) {
			otherNodes.add( subProc );
		}
		
		( ( BpmnLayoutManager )( editor ).getLayoutManager() ).addAlignmentConstraint( outgoingNode ,TSLayoutConstants.ORIENTATION_VERTICAL );
		( ( BpmnLayoutManager )( editor ).getLayoutManager() ).addAlignmentConstraint( otherNodes ,TSLayoutConstants.ORIENTATION_HORIZONTAL );
		( ( BpmnLayoutManager )( editor ).getLayoutManager() ).addSequenceConstraint( outgoingNode , TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM );
		( ( BpmnLayoutManager )( editor ).getLayoutManager() ).setHierarchicalOptions();
		
		( ( BpmnLayoutManager )( editor ).getLayoutManager() ).callInteractiveGlobalLayout();

		//Resetting Editor
		( (DiagramManager) diagramManager).resetDiagramEditor();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor!=null)
			page =  targetEditor.getEditorSite().getPage();

	}

	public IWorkbenchPage getPage() {
		return page;
	}

	public void setPage(IWorkbenchPage page) {
		this.page = page;
	}

	public BpmnEditor getEditor() {
		return editor;
	}

	public void setEditor(BpmnEditor editor) {
		this.editor = editor;
	}

	public BpmnDiagramManager getDiagramManager() {
		return diagramManager;
	}

	public void setDiagramManager(BpmnDiagramManager diagramManager) {
		this.diagramManager = diagramManager;
	}

}
