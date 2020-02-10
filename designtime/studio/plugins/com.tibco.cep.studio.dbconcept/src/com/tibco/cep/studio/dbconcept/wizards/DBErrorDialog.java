package com.tibco.cep.studio.dbconcept.wizards;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @see org.eclipse.jface.dialogs.ErrorDialog
 * @see org.eclipse.core.runtime.IStatus

 */
public class DBErrorDialog extends IconAndMessageDialog {

	public static boolean AUTOMATED_MODE = false;
	private static final int LIST_ITEM_COUNT = 7;
	private static final String NESTING_INDENT = "  "; 
	private Button detailsButton;
	private String title;
	private Text details;
	private boolean listCreated = false;
	private int displayMask = 0xFFFF;
	private IStatus status;
	private Clipboard clipboard;
	private boolean shouldIncludeTopLevelErrorInDetails = false;
	private String detailsDescription;
	
	private  Button yesButton ;
	private  Button noButton ;
	
	private boolean userInputStatus = false;
	
	private boolean iStatus = true;
	
	public boolean getUserInput() {
		return userInputStatus;
	}
	
	/**
	 * @param parent
	 * @param pluginId
	 * @param dialogTitle
	 * @param message
	 * @param e
	 * @return
	 */
	public static int openError(Shell parent,String pluginId, String dialogTitle, String message, Throwable e ) {
		return openError(parent, dialogTitle, message, new Status(IStatus.ERROR, pluginId, 0,e.toString(), e));
	}

	/**
	 * @param parent
	 * @param dialogTitle
	 * @param message
	 * @param status
	 * @return
	 */
	public static int openError(Shell parent, String dialogTitle, String message, IStatus status) {
		return openError(parent, dialogTitle, message, status, IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
	}

	/**
	 * @param parent
	 * @param dialogTitle
	 * @param message
	 * @param detailsDescription
	 * @return
	 */
	public static int openError(Shell parent, String dialogTitle,String message, String detailsDescription) {
		DBErrorDialog dialog = new DBErrorDialog(parent, dialogTitle, message, detailsDescription);
		return dialog.open();
	}
	
	/**
	 * @param parentShell
	 * @param title
	 * @param message
	 * @param status
	 * @param displayMask
	 * @return
	 */
	public static int openError(Shell parentShell, String title, String message, IStatus status, int displayMask) {
		DBErrorDialog dialog = new DBErrorDialog(parentShell, title, message,status, displayMask);
		return dialog.open();
	}

	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param message
	 * @param status
	 * @param displayMask
	 */
	public DBErrorDialog(Shell parentShell, String dialogTitle, String message, IStatus status, int displayMask) {
		super(parentShell);
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		this.message = message == null ? status.getMessage(): JFaceResources.format("Reason", new Object[]{message, status.getMessage()}); 
		this.status = status;
		this.displayMask = displayMask;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}
	
	public DBErrorDialog(Shell parentShell, String dialogTitle, String message, IStatus status, int displayMask,boolean bNotRequired) {
		super(parentShell);
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		this.message = message == null ? status.getMessage(): JFaceResources.format("Reason", new Object[]{message, status.getMessage()}); 
		this.status = status;
		this.displayMask = displayMask;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		iStatus=bNotRequired;
	}

	/**
	 * @param parentShell
	 * @param title
	 * @param message
	 * @param detailsDescription
	 */
	public DBErrorDialog(Shell parentShell, String title, String message,String detailsDescription) {
		super(parentShell);
		this.title = title == null ? JFaceResources.getString("Problem_Occurred") :title;
		this.message = message;
		this.detailsDescription = detailsDescription;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	protected void buttonPressed(int id) {
		if (id == IDialogConstants.DETAILS_ID) {
			toggleDetailsArea();
		} else {
			super.buttonPressed(id);
		}
	}

	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		
		if(iStatus) {
			yesButton = createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, true);
			yesButton.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) { 
					userInputStatus = true;
					okPressed();
				}
			});
		} 
		
		if (iStatus) {
			noButton = createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
		} else {
			noButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		}
		createDetailsButton(parent);
		noButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) { 
				userInputStatus = false;
				cancelPressed();
			}
		});
	}

	/**
	 * @param parent
	 */
	protected void createDetailsButton(Composite parent) {
		if (shouldShowDetailsButton()) {
			detailsButton = createButton(parent, IDialogConstants.DETAILS_ID,
					IDialogConstants.SHOW_DETAILS_LABEL, false);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		createMessageArea(parent);
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.numColumns = 2;
		composite.setLayout(layout);
		GridData childData = new GridData(GridData.FILL_BOTH);
		childData.horizontalSpan = 2;
		composite.setLayoutData(childData);
		composite.setFont(parent.getFont());
		return composite;
	}

	/*
	 * @see IconAndMessageDialog#createDialogAndButtonArea(Composite)
	 */
	protected void createDialogAndButtonArea(Composite parent) {
		super.createDialogAndButtonArea(parent);
		if (this.dialogArea instanceof Composite) {
			Composite dialogComposite = (Composite) dialogArea;
			if (dialogComposite.getChildren().length == 0) {
				new Label(dialogComposite, SWT.NULL);
			}
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
	 */
	protected Image getImage() {
		if (status != null) {
			if (status.getSeverity() == IStatus.WARNING) {
				return getWarningImage();
			}
			if (status.getSeverity() == IStatus.INFO) {
				return getInfoImage();
			}
		}
		//If it was not a warning or an error then return the error image
		return getErrorImage();
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Text createDropDownDetails(Composite parent) {
		details = new Text(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL| SWT.MULTI| SWT.READ_ONLY);
		details.setBackground(details.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		populate(details);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		data.heightHint = details.getLineHeight() * LIST_ITEM_COUNT;
		data.horizontalSpan = 2;
		details.setLayoutData(data);
		details.setFont(parent.getFont());
		Menu copyMenu = new Menu(details);
		MenuItem copyItem = new MenuItem(copyMenu, SWT.NONE);
		copyItem.addSelectionListener(new SelectionListener() {
			/*
			 * @see SelectionListener.widgetSelected (SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				copyToClipboard();
			}

			/*
			 * @see SelectionListener.widgetDefaultSelected(SelectionEvent)
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
				copyToClipboard();
			}
		});
		copyItem.setText(JFaceResources.getString("copy")); //$NON-NLS-1$
		details.setMenu(copyMenu);
		listCreated = true;
		return details;
	}

	/*
	 * (non-Javadoc) Method declared on Window.
	 */
	public int open() {
		if (!AUTOMATED_MODE) {
			if(status !=null && shouldDisplay(status, displayMask)){
				return super.open();
			}
			return super.open();
		}
		setReturnCode(OK);
		return OK;
	}

	/**
	 * @param detailsToPopulate
	 */
	private void populate(Text detailsToPopulate) {
		if(status!=null){
			populateDetails(detailsToPopulate, status, 0, shouldIncludeTopLevelErrorInDetails);
		}else{
			details.setText(detailsDescription);
		}

	}

	/**
	 * @param listToPopulate
	 * @param buildingStatus
	 * @param nesting
	 * @param includeStatus
	 */
	private void populateDetails(Text listToPopulate, IStatus buildingStatus,
			int nesting, boolean includeStatus) {

		if (!buildingStatus.matches(displayMask)) {
			return;
		}

		Throwable t = buildingStatus.getException();
		boolean isCoreException= t instanceof CoreException;
		boolean incrementNesting= false;

		if (includeStatus) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < nesting; i++) {
				sb.append(NESTING_INDENT);
			}
			String message = buildingStatus.getMessage();
			sb.append(message);
			listToPopulate.append(sb.toString());
			incrementNesting= true;
		}

		if (!isCoreException && t != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < nesting; i++) {
				sb.append(NESTING_INDENT);
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw );

			String message = sw.getBuffer().toString();
			if (message == null) {
				message = t.toString();
			}

			sb.append(message);
			listToPopulate.append(sb.toString());
			incrementNesting= true;
		}

		if (incrementNesting) {
			nesting++;
		}

		// Look for a nested core exception
		if (isCoreException) {
			CoreException ce = (CoreException)t;
			IStatus eStatus = ce.getStatus();
			// Only print the exception message if it is not contained in the parent message
			if (message == null || message.indexOf(eStatus.getMessage()) == -1) {
				populateDetails(listToPopulate, eStatus, nesting, true);
			}
		}

		// Look for child status
		IStatus[] children = buildingStatus.getChildren();
		for (int i = 0; i < children.length; i++) {
			populateDetails(listToPopulate, children[i], nesting, true);
		}
	}

	/**
	 * @param status
	 * @param mask
	 * @return
	 */
	protected static boolean shouldDisplay(IStatus status, int mask) {
		IStatus[] children = status.getChildren();
		if (children == null || children.length == 0) {
			return status.matches(mask);
		}
		for (int i = 0; i < children.length; i++) {
			if (children[i].matches(mask)) {
				return true;
			}
		}
		return false;
	}

	private void toggleDetailsArea() {
		Point windowSize = getShell().getSize();
		Point oldSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (listCreated) {
			details.dispose();
			listCreated = false;
			detailsButton.setText(IDialogConstants.SHOW_DETAILS_LABEL);
		} else {
			details = createDropDownDetails((Composite) getContents());
			detailsButton.setText(IDialogConstants.HIDE_DETAILS_LABEL);
		}
		Point newSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		getShell().setSize(new Point(windowSize.x, windowSize.y+ (newSize.y - oldSize.y)));
	}

	/**
	 * @param buildingStatus
	 * @param buffer
	 * @param nesting
	 */
	private void populateCopyBuffer(IStatus buildingStatus, StringBuffer buffer, int nesting) {
		if (!buildingStatus.matches(displayMask)) {
			return;
		}
		for (int i = 0; i < nesting; i++) {
			buffer.append(NESTING_INDENT);
		}
		buffer.append(buildingStatus.getMessage());
		buffer.append("\n"); 

		// Look for a nested core exception
		Throwable t = buildingStatus.getException();
		if (t instanceof CoreException) {
			CoreException ce = (CoreException)t;
			populateCopyBuffer(ce.getStatus(), buffer, nesting + 1);
		}

		IStatus[] children = buildingStatus.getChildren();
		for (int i = 0; i < children.length; i++) {
			populateCopyBuffer(children[i], buffer, nesting + 1);
		}
	}

	private void copyToClipboard() {
		if (clipboard != null) {
			clipboard.dispose();
		}
		StringBuffer statusBuffer = new StringBuffer();
		populateCopyBuffer(status, statusBuffer, 0);
		clipboard = new Clipboard(details.getDisplay());
		clipboard.setContents(new Object[] { statusBuffer.toString() },
				new Transfer[] { TextTransfer.getInstance() });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {
		if (clipboard != null) {
			clipboard.dispose();
		}
		return super.close();
	}

	protected final void showDetailsArea() {
		if (!listCreated) {
			Control control = getContents();
			if (control != null && ! control.isDisposed()) {
				toggleDetailsArea();
			}
		}
	}

	/**
	 * @return
	 */
	protected boolean shouldShowDetailsButton() {
		if(status!=null){
			return status.isMultiStatus() || status.getException() != null;
		}
		return true;
	}

	/**
	 * @param status
	 */
	protected final void setStatus(IStatus status) {
		if (this.status != status) {
			this.status = status;
		}
		shouldIncludeTopLevelErrorInDetails = true;
		if (listCreated) {
			repopulateDetails();
		}
	}

	private void repopulateDetails() {
		if (details != null && !details.isDisposed()) {
			details.setText("");
			populate(details);
		}
	}
}