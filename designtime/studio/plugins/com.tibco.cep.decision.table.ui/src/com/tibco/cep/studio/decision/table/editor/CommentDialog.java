package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A dialog window for adding and editing comments for a DT cell.
 */
public class CommentDialog extends Dialog {

	 public CommentDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	 }

	 private Text comment;
	 private String text = null;

	
	 
	 @Override
	 protected Control createDialogArea(Composite parent) {

		// Layout of parent is set to GridLayout in createContents
		((GridLayout) parent.getLayout()).marginTop = 10;
		((GridLayout) parent.getLayout()).marginWidth = 10;
		 
		// The text fields will grow with the size of the dialog
	    GridData gridData = new GridData();
	    gridData.grabExcessVerticalSpace = true;
	    gridData.verticalAlignment = GridData.FILL;
	    
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalAlignment = GridData.FILL;

	    comment = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
	    comment.setLayoutData(gridData);
	    if(text == null)
	    	comment.setText("");
	    else
	    	comment.setText(text);
	    
	    return parent;
	  }

	  @Override
	  protected Point getInitialSize() {
		getShell().setText("Comments");
		return getShell().computeSize(425, 125, true);
	  }
	 
	  @Override
	  protected void createButtonsForButtonBar(Composite parent) {

		((GridLayout) parent.getLayout()).marginTop = -5;
		((GridLayout) parent.getLayout()).marginBottom = -5;   
		((GridLayout) parent.getLayout()).marginRight = -12;   
		GridData gridData = new GridData();
	    gridData.verticalAlignment = GridData.END;
	    gridData.horizontalSpan = 1;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalAlignment = SWT.RIGHT;
	    parent.setLayoutData(gridData);
	    // Create OK button
	    //TODO add string from resource bundle or messages
//	    createOkButton(parent, OK, DecisionResources.getResourceBundle(Locale.getDefault()).getString("CommentDialog.ok"), true);
	    createOkButton(parent, OK, "OK", true);
	    
	    // Create Cancel button
	    Button cancelButton = 
//	        createButton(parent, CANCEL, DecisionResources.getResourceBundle(Locale.getDefault()).getString("CommentDialog.cancel"), false);
	    		createButton(parent, CANCEL, "Cancel", false);
	    // Add a SelectionListener
	    cancelButton.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent e) {
	        setReturnCode(CANCEL);
	        close();
	      }
	    });
	  }

	  private Button createOkButton(Composite parent, int id, 
	    String label,
	    boolean defaultButton) {
	    // increment the number of columns in the button bar
	    ((GridLayout) parent.getLayout()).numColumns++;
	    Button button = new Button(parent, SWT.PUSH);
	    button.setText(label);
	    button.setFont(JFaceResources.getDialogFont());
	    button.setData(new Integer(id));
	    // Add a SelectionListener

	    button.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	          okPressed();
	      }
	    });
	    if (defaultButton) {
	      Shell shell = parent.getShell();
	      if (shell != null) {
	        shell.setDefaultButton(button);
	      }
	    }
	    setButtonLayoutData(button);
	    return button;
	  }

	  
	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

	  // Copy textFields because the UI gets disposed
	  // and the Text Fields are not accessible any more.
	  private void saveInput() {
	    text = comment.getText();
	  }

	  @Override
	  protected void okPressed() {
	    saveInput();
	    super.okPressed();
	  }

	  public String getFirstName() {
	    return text;
	  }

	  public void setText(String string) {
		text = string;
	  }

	  public String getText() {
		return text;
	  }

}