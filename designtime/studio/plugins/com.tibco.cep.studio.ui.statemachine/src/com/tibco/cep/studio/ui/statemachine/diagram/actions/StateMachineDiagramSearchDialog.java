package com.tibco.cep.studio.ui.statemachine.diagram.actions;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.actions.DiagramSearchAction;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DiagramSearchDialog;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * @author sasahoo
 */
public class StateMachineDiagramSearchDialog extends DiagramSearchDialog implements ModifyListener, SelectionListener {

	private Combo textCombo;
	private Button caseSCheckButton;
	private Button stateCheckButton;
	private Button transCheckButton;
	private Group searachByNameLabelGroup;
	private Button byNameCheckButton;
	private Button byLabelCheckButton;
	private Composite composite;
	private Composite searchGrpContainer;
	private Group searchForGroup; 
	private String searchText="";
	private boolean isSearchForState = true;
	private boolean isSearchByLabel = true;
    protected Set<TSEObject> matchingEdgeSetByLabel = new HashSet<TSEObject>();
    protected Set<TSEObject> matchingEdgeSetByName = new HashSet<TSEObject>();
    
	/**
	 * @param page
	 * @param dialogTitle
	 */
	public StateMachineDiagramSearchDialog(IWorkbenchPage page, String dialogTitle, DiagramSearchAction action) {
		super(page, dialogTitle, action);
		setShellStyle(SWT.CLOSE);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramSearchDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		composite = (Composite)super.createContents(parent);
		buttonBar = createButtonBar(composite);
		composite.getShell().setSize(500, 250);
		return composite;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramSearchDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite generalContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		generalContainer.setLayout(layout);
		generalContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		createLabel(generalContainer, "Search string");
		
		textCombo = createCombo(generalContainer);
		textCombo.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		textCombo.addModifyListener(this);
		
		caseSCheckButton = new Button(generalContainer, SWT.CHECK);
		caseSCheckButton.setText("Case Sensitive");
		caseSCheckButton.addSelectionListener(this);
		
		Composite searchForContainer = new Composite(container, SWT.NONE);
		layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		searchForContainer.setLayout(layout);
		searchForContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		searchForGroup = new Group(searchForContainer, SWT.NONE);
		layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		searchForGroup.setText("Search for");
		searchForGroup.setLayout(layout);
		searchForGroup.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		searchGrpContainer = new Composite(searchForGroup, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		searchGrpContainer.setLayout(layout);
		searchGrpContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		stateCheckButton = new Button(searchGrpContainer, SWT.RADIO);
		stateCheckButton.setText("State");
		stateCheckButton.addSelectionListener(this);
		stateCheckButton.setSelection(true);
		
		transCheckButton = new Button(searchGrpContainer, SWT.RADIO);
		transCheckButton.setText("Transition");
		transCheckButton.addSelectionListener(this);
		
		searachByNameLabelGroup = new Group(searchForGroup, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		searachByNameLabelGroup.setText("Search by");
		searachByNameLabelGroup.setLayout(layout);
		searachByNameLabelGroup.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		byLabelCheckButton = new Button(searachByNameLabelGroup, SWT.RADIO);
		byLabelCheckButton.setText("Label");
		byLabelCheckButton.addSelectionListener(this);
		byLabelCheckButton.setSelection(true);
		
		byNameCheckButton = new Button(searachByNameLabelGroup, SWT.RADIO);
		byNameCheckButton.setText("Name");
		byNameCheckButton.addSelectionListener(this);
		
        errorMessageText = new Text(container, SWT.READ_ONLY | SWT.WRAP);
        GridData gd = new GridData(GridData.GRAB_HORIZONTAL| GridData.HORIZONTAL_ALIGN_FILL);
        gd.heightHint = 20;
        errorMessageText.setLayoutData(gd);
        errorMessageText.setBackground(errorMessageText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
         
        applyDialogFont(container);
        
        searachByNameLabelGroup.setEnabled(false);
        byNameCheckButton.setEnabled(false);
        byLabelCheckButton.setEnabled(false);
        
        return container;
    }
	
	/**
	 * @param container
	 * @return
	 */
	protected Text createText(Composite container) {
		Text text = new Text(container, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		text.setLayoutData(gData);
		return text;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create Search, Next and Cancel buttons by default
		searchButton = createButton(parent, IDialogConstants.OK_ID, "Search", false);
		searchButton.setImage(DiagrammingPlugin.getDefault().getImage("icons/search_16x16.png"));
		searchButton.setEnabled(false);
//		nextButton = createButton(composite, IDialogConstants.NEXT_ID,IDialogConstants.NEXT_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	/**
	 * @param container
	 * @return
	 */
	protected Combo createCombo(Composite container) {
		Combo combo = new Combo(container, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		combo.setLayoutData(gData);
		return combo;
	}
	
	/**
	 * @param container
	 * @param label
	 * @return
	 */
	protected Label createLabel(Composite container, String label) {
		return createLabel(container, label, 0);
	}
	
	/**
	 * @param container
	 * @param labelstr
	 * @param indent
	 * @return
	 */
	protected Label createLabel(Composite container, String labelstr, int indent) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = indent;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		searchText = textCombo.getText();
		if(textCombo.getText()!=null && !(textCombo.getText().equalsIgnoreCase(""))){
			searchButton.setEnabled(true);
		}else{
			searchButton.setEnabled(false);
		}
			
		setErrorMessage(null);
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		setErrorMessage(null);
		if(e.getSource() == stateCheckButton) {
			if (stateCheckButton.getSelection()) {
				isSearchForState = true;
				searachByNameLabelGroup.setEnabled(false);
				byNameCheckButton.setEnabled(false);
				byLabelCheckButton.setEnabled(false);
			}
		}
		if(e.getSource() == transCheckButton) {
			if (transCheckButton.getSelection()) {
				isSearchForState = false;
				searachByNameLabelGroup.setEnabled(true);
				byNameCheckButton.setEnabled(true);
				byLabelCheckButton.setEnabled(true);
			}
		}
		if (e.getSource() == byLabelCheckButton) {
			if (byLabelCheckButton.getSelection()) {
				isSearchByLabel = true;
			}
		}
		if (e.getSource() == byNameCheckButton) {
			if (byNameCheckButton.getSelection()) {
				isSearchByLabel = false;
			}
		}
		if (e.getSource() ==  caseSCheckButton) {
			 matchcase = caseSCheckButton.getSelection();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramSearchDialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {
		try {
			if (buttonId == IDialogConstants.OK_ID) {
				DiagramManager diagramManager = DiagramUtils.getDiagramManager(page);
				if (diagramManager !=null) {
					this.graphManager = diagramManager.getGraphManager();
					this.drawingCanvas = diagramManager.getDrawingCanvas();
					if (!searchText.isEmpty()) {
						boolean available = false;
						for (String s : textCombo.getItems()) {
							if (s.equals(searchText)) {
								available = true;
							}
						}
						if (!available) {
							textCombo.add(searchText);
						}
					}
					boolean nf = false;
					unselectGraphObjects(); 
					if (isSearchForState) {
						findNode(searchText);
						if (matchingNodeSet.isEmpty()) {
							nf = true;
						} else {
							display(matchingNodeSet);
						}
					} else {
						findEdges(searchText, isSearchByLabel);
						if(isSearchByLabel) {
							if (matchingEdgeSetByLabel.isEmpty()) {
								nf = true;
							} else {
								display(matchingEdgeSetByLabel);
							}
						} else {
							if (matchingEdgeSetByName.isEmpty()) {
								nf = true;
							} else {
								display(matchingEdgeSetByName);
							}
						}
					}
					if (nf) {
						setErrorMessage("Not Found");
					}
				}
			}
			if (buttonId == IDialogConstants.CANCEL_ID) {
				super.buttonPressed(buttonId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param text
	 * @param isSearchByLabel
	 * @return
	 */
	public void findEdges(String text, boolean isSearchByLabel) {
		matchingEdgeSetByLabel.clear();
		matchingEdgeSetByName.clear();
		for (TSEEdge edge : edgeSet) {
			if (edge.getUserObject() != null) {
				StateTransition transition = (StateTransition)edge.getUserObject();
				if(isSearchByLabel) {
					if (matchcase == true ? transition.getLabel().equals(text): transition.getLabel().equalsIgnoreCase(text)) {
						matchingEdgeSetByLabel.add(edge);
					}
				} else {
					if (matchcase == true ? transition.getName().equals(text):transition.getName().equalsIgnoreCase(text)) {
						matchingEdgeSetByName.add(edge);
					}
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	public boolean close() {
		action.setDisposed(true);
		return super.close();
	}
}