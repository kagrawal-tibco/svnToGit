package com.tibco.cep.diagramming.drawing;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.actions.DiagramSearchAction;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramSearchDialog extends Dialog {
   
    protected String title;
    protected String value = "";
    protected Button searchButton, nextButton;
    protected Text text;
    protected Text errorMessageText;
    protected IWorkbenchPage page;
    protected TSEGraphManager graphManager;
    protected DrawingCanvas drawingCanvas;
    protected Set<TSENode> nodeSet = new HashSet<TSENode>(); 
    protected Set<TSEGraph> graphSet = new HashSet<TSEGraph>();
    protected Set<TSEEdge> edgeSet = new HashSet<TSEEdge>();
    protected Set<TSEObject> matchingNodeSet = new HashSet<TSEObject>();
 	protected String errorMessage;
    protected boolean matchcase = false;
    protected DiagramSearchAction action;
    
	public DiagramSearchDialog(IWorkbenchPage page,String dialogTitle, DiagramSearchAction action) {
		super(page.getWorkbenchWindow().getShell());
		this.title = dialogTitle;
		this.page = page;
		this.action = action;
		setShellStyle(SWT.SHELL_TRIM);//for non modal search dialog
	}
	
	protected Control createContents(Composite parent) {
		// create the top level composite for the dialog
		Composite composite = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
		// initialize the dialog units
		initializeDialogUnits(composite);
		// create the dialog area and button bar
		dialogArea = createDialogArea(composite);
		return composite;
	}
    /*
     * (non-Javadoc) Method declared on Dialog.
     */
	protected void buttonPressed(int buttonId) {
		DiagramManager diagramManager = DiagramUtils.getDiagramManager(page);
		if(diagramManager !=null){
			this.graphManager = diagramManager.getGraphManager();
			this.drawingCanvas = diagramManager.getDrawingCanvas();
			if (buttonId == IDialogConstants.OK_ID) {
				try{
					value = text.getText();
					unselectGraphObjects(); 
					findNode(value);
					if (!(matchingNodeSet.isEmpty())) {
						display(matchingNodeSet);
					}else{
						setErrorMessage("Not Found");
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			} 
    	}
    	super.buttonPressed(buttonId);
    }
    
    /**
     * @param selectedSet
     */
    public void display(final Set<TSEObject> selectedSet) {
    	for (final TSEObject object : selectedSet ){
    		if (!(object.isSelected())) {
    			object.setSelected(true);
    			SwingUtilities.invokeLater(new Runnable() {
    				/* (non-Javadoc)
    				 * @see java.lang.Runnable#run()
    				 */
    				public void run() {
    					TSConstPoint constPoint = null;
    					if(object instanceof TSENode){
    						((TSENode)object).setSelected(true);
    						constPoint= ((TSENode)object).getCenter();
    					}
    					if(object instanceof TSEEdge){
    						((TSEEdge)object).setSelected(true);
    						constPoint= ((TSEEdge)object).getSourcePoint();
    					}
    					drawingCanvas.centerPointInCanvas(constPoint, false);
    					drawingCanvas.setZoomLevelInteractive(1.0);
    				}
    			});
    		}
    	}
    }

	/**
	 * Notifies that the ok button of this dialog has been pressed.
	 * <p>
	 * The <code>Dialog</code> implementation of this framework method sets
	 * this dialog's return code to <code>Window.OK</code> and closes the
	 * dialog. Subclasses may override.
	 * </p>
	 */
	protected void okPressed() {
		setReturnCode(OK);
	}

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (title != null) {
			shell.setText(title);
		}
    }

	@SuppressWarnings("unchecked")
	public void findNode(String name) {
		TSENode node = null;
		this.matchingNodeSet.clear();
		Iterator<TSENode> iter = nodeSet.iterator();
		while (iter.hasNext()) {
			node = (TSENode) iter.next();
			List<TSENodeLabel> nodeLabels = node.labels();
			Iterator<TSENodeLabel> nodeLabelIterator = nodeLabels.iterator();
			if ((node.getText() != null) && (matchcase == true ? 
					node.getText().equals(name) : node.getText().equalsIgnoreCase(name))) {
				if (!(this.matchingNodeSet.contains(node))) {
					this.matchingNodeSet.add(node);
				}
			}
			if ((node.getName().toString() != null) && (matchcase == true ? 
					node.getName().toString().equals(name) : node.getName().toString().equalsIgnoreCase(name))) {
				if (!(this.matchingNodeSet.contains(node))) {
					this.matchingNodeSet.add(node);
				}
			}
			while (nodeLabelIterator.hasNext()) {
				TSENodeLabel nodeLabel = (TSENodeLabel) nodeLabelIterator.next();
				if ((nodeLabel.getName().toString() != null) && (
						matchcase == true ? nodeLabel.getName().toString().equals(name) : nodeLabel.getName().toString().equalsIgnoreCase(name))) {
					if (!(this.matchingNodeSet.contains(node))) {
							this.matchingNodeSet.add(node);
					}
				}
				if ((nodeLabel.getText() != null) && (	matchcase == true ? nodeLabel.getText().equals(name) : nodeLabel.getText().equalsIgnoreCase(name))) {
					if (!(this.matchingNodeSet.contains(node))) {
							this.matchingNodeSet.add(node);
					}
				}
			}
		}
	}
	
	public void unselectGraphObjects() {
		TSENode node = null;
		TSEEdge edge = null;
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		traverseEntireDiagram(graph, nodeSet, graphSet, edgeSet);
		Iterator<?> iter = nodeSet.iterator();
		while (iter.hasNext()) {
			node = (TSENode) iter.next();
			if(node.isSelected()){
				node.setSelected(false);
			}
		}
		
		iter = edgeSet.iterator();
		while (iter.hasNext()) {
			edge = (TSEEdge) iter.next();
			if(edge.isSelected()){
				edge.setSelected(false);
			}
		}
		drawingCanvas.drawGraph();
		drawingCanvas.repaint();
	}

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    @SuppressWarnings("static-access")
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout();
        layout.numColumns =3;
        text = new Text(composite, getInputTextStyle());
        GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
        gd.heightHint = 20;
        gd.widthHint = 200;
        text.setLayoutData(gd);
        text.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				//setErrorMessage(null);
				Text text = (Text) e.widget;
		        if(text.getText()!=null && !text.getText().isEmpty()){
		        	searchButton.setEnabled(true);
		        };
				
			}});
        searchButton = createButton(composite, IDialogConstants.OK_ID,"Search", true);
        searchButton.setImage(DiagrammingPlugin.getDefault().getImage("icons/search_16x16.png"));
        searchButton.setEnabled(false);
//      nextButton = createButton(composite, IDialogConstants.NEXT_ID,IDialogConstants.NEXT_LABEL, true);
        
        createButton(composite, IDialogConstants.CANCEL_ID,IDialogConstants.CANCEL_LABEL, false);
        
        errorMessageText = new Text(parent, SWT.READ_ONLY | SWT.WRAP);
        gd = new GridData(GridData.GRAB_HORIZONTAL| GridData.HORIZONTAL_ALIGN_FILL);
        gd.heightHint = 20;
        errorMessageText.setLayoutData(gd);
        errorMessageText.setBackground(errorMessageText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
         
        text.setFocus();
        if (value != null) {
            text.setText(value);
            text.selectAll();
        }
        applyDialogFont(composite);
        return composite;
    }

    /**
     * Returns the ok button.
     * 
     * @return the ok button
     */
    protected Button getOkButton() {
        return searchButton;
    }

    /**
     * Returns the next button.
     * 
     * @return the next button
     */
    protected Button getNextButton() {
    	return nextButton;
    }
    /**
     * Returns the text area.
     * 
     * @return the text area
     */
    protected Text getText() {
        return text;
    }

    /**
     * Returns the string typed into this input dialog.
     * 
     * @return the input string
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets or clears the error message.
     * If not <code>null</code>, the OK and Next buttons are disabled.
     * 
     * @param errorMessage
     *            the error message, or <code>null</code> to clear
     */
    public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    	if (errorMessageText != null && !errorMessageText.isDisposed()) {
    		errorMessageText.setText(errorMessage == null ? " \n " : errorMessage); //$NON-NLS-1$
    		boolean hasError = errorMessage != null && (StringConverter.removeWhiteSpaces(errorMessage)).length() > 0;
    		errorMessageText.setEnabled(hasError);
    		errorMessageText.setVisible(hasError);
    		errorMessageText.getParent().update();
    	}
    }

    protected int getInputTextStyle() {
		return SWT.SINGLE | SWT.BORDER;
	}
    
    /**
	 * @param graph
	 * @param nodeSet
	 * @param graphSet
	 * @param edgeSet
	 */
	@SuppressWarnings("unchecked")
	protected void traverseEntireDiagram(TSEGraph graph, 
			                        Set<TSENode> nodeSet, 
			                        Set<TSEGraph> graphSet, 
			                        Set<TSEEdge> edgeSet){
		graphSet.add(graph);
		edgeSet.addAll(graph.edges());
		for(Object obj: graph.nodes()){
			TSENode node = (TSENode)obj;
			nodeSet.add(node);
			if(node.getChildGraph() != null){
				traverseEntireDiagram((TSEGraph)node.getChildGraph(), nodeSet, graphSet, edgeSet);
			}
		}
	}
}
