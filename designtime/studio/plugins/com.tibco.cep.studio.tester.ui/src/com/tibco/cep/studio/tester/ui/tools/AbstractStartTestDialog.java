package com.tibco.cep.studio.tester.ui.tools;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractStartTestDialog extends Dialog implements SelectionListener, ModifyListener {

	protected String title; 
	protected Table testDataSelectTable;
    protected int fWidth = 60;
    protected Button select;
    protected IProject project;
    protected Combo projectsCombo;
    protected Combo ruleSessionsCombo;
    protected Text testerSessionText;
    protected List testerSessionsList;
    protected Button createButton;
	protected Button cancelButton;
	protected Button okButton;
	private Composite workArea;
	private Label titleLabel;
	private Label titleImageLabel;
	private Label bottomFillerLabel;
	private Label leftFillerLabel;
	private RGB titleAreaRGB;
	Color titleAreaColor;
	private String message = ""; 
	private String errorMessage;
	private Text messageLabel;
	private Label messageImageLabel;
	private Image messageImage;
	private boolean showingError = false;
	private boolean titleImageLargest = true;
	private int messageLabelHeight;
	private Image titleAreaImage;
	// Space between an image and a label
	private static final int H_GAP_IMAGE = 5;
	protected boolean valid = true;
	protected int selectedIndex = -1;

	protected Set<TableItem> checkedTestaData =  new HashSet<TableItem>();
//	protected Map<String, TesterSession> testerRunSessionMap = new HashMap<String, TesterSession>();
	protected Set<File> checkedScorecardTestaDataFiles =  new HashSet<File>();
	protected Set<File> checkedNonScorecardTestaDataFiles =  new HashSet<File>();
	protected Set<String> checkedTestaDataFiles =  new HashSet<String>();
	
	/**
	 * Image registry key for error message image.
	 */
	public static final String DLG_IMG_TITLE_ERROR = DLG_IMG_MESSAGE_ERROR;

	/**
	 * Image registry key for banner image (value
	 * <code>"dialog_title_banner_image"</code>).
	 */
	public static final String DLG_IMG_TITLE_BANNER = "dialog_title_banner_image";
	
	/**
	 * @param parentShell
	 */
	protected AbstractStartTestDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}
	
	/*
	 * @see Dialog.createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayoutData(new GridData(GridData.FILL_BOTH));
		// initialize the dialog units
		initializeDialogUnits(contents);
		FormLayout layout = new FormLayout();
		contents.setLayout(layout);
		// Now create a work area for the rest of the dialog
		workArea = new Composite(contents, SWT.NONE);
		GridLayout childLayout = new GridLayout();
		childLayout.marginHeight = 0;
		childLayout.marginWidth = 0;
		childLayout.verticalSpacing = 0;
		workArea.setLayout(childLayout);
		Control top = createTitleArea(contents);
		resetWorkAreaAttachments(top);
		workArea.setFont(JFaceResources.getDialogFont());
		// initialize the dialog units
		initializeDialogUnits(workArea);

		// create the dialog area and button bar
		dialogArea = createDialogArea(workArea);
		buttonBar = createButtonBar(workArea);

		// Apply to the parent so that the message gets it too.
		applyDialogFont(parent);
		return parent;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private Control createTitleArea(Composite parent) {
		// add a dispose listener
		parent.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (titleAreaColor != null) {
					titleAreaColor.dispose();
				}
			}
		});
		// Determine the background color of the title bar
		Display display = parent.getDisplay();
		Color background;
		Color foreground;
		if (titleAreaRGB != null) {
			titleAreaColor = new Color(display, titleAreaRGB);
			background = titleAreaColor;
			foreground = null;
		} else {
			background = JFaceColors.getBannerBackground(display);
			foreground = JFaceColors.getBannerForeground(display);
		}

		parent.setBackground(background);
		int verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		int horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		// Dialog image @ right
		titleImageLabel = new Label(parent, SWT.CENTER);
		titleImageLabel.setBackground(background);
		if (titleAreaImage == null)
			titleImageLabel.setImage(JFaceResources
					.getImage(DLG_IMG_TITLE_BANNER));
		else
			titleImageLabel.setImage(titleAreaImage);

		FormData imageData = new FormData();
		imageData.top = new FormAttachment(0, 0);
		// Note: do not use horizontalSpacing on the right as that would be a
		// regression from
		// the R2.x style where there was no margin on the right and images are
		// flush to the right
		// hand side. see reopened comments in 41172
		imageData.right = new FormAttachment(100, 0); // horizontalSpacing
		titleImageLabel.setLayoutData(imageData);
		// Title label @ top, left
		titleLabel = new Label(parent, SWT.LEFT);
		JFaceColors.setColors(titleLabel, foreground, background);
		titleLabel.setFont(JFaceResources.getBannerFont());
		titleLabel.setText("Enter configurations for Start Test");//$NON-NLS-1$
		FormData titleData = new FormData();
		titleData.top = new FormAttachment(0, verticalSpacing);
		titleData.right = new FormAttachment(titleImageLabel);
		titleData.left = new FormAttachment(0, horizontalSpacing);
		titleLabel.setLayoutData(titleData);
		// Message image @ bottom, left
		messageImageLabel = new Label(parent, SWT.CENTER);
		messageImageLabel.setBackground(background);
		// Message label @ bottom, center
		messageLabel = new Text(parent, SWT.WRAP | SWT.READ_ONLY);
		JFaceColors.setColors(messageLabel, foreground, background);
		messageLabel.setText(" \n "); // two lines//$NON-NLS-1$
		messageLabel.setFont(JFaceResources.getDialogFont());
		messageLabelHeight = messageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		// Filler labels
		leftFillerLabel = new Label(parent, SWT.CENTER);
		leftFillerLabel.setBackground(background);
		bottomFillerLabel = new Label(parent, SWT.CENTER);
		bottomFillerLabel.setBackground(background);
		setLayoutsForNormalMessage(verticalSpacing, horizontalSpacing);
		determineTitleImageLargest();
		if (titleImageLargest) {
			return titleImageLabel;
		}
		return messageLabel;
	}
	
	/**
	 * Reset the attachment of the workArea to now attach to top as the top
	 * control.
	 * 
	 * @param top
	 */
	private void resetWorkAreaAttachments(Control top) {
		FormData childData = new FormData();
		childData.top = new FormAttachment(top);
		childData.right = new FormAttachment(100, 0);
		childData.left = new FormAttachment(0, 0);
		childData.bottom = new FormAttachment(100, 0);
		workArea.setLayoutData(childData);
	}
	
	/**
	 * Determine if the title image is larger than the title message and message
	 * area. This is used for layout decisions.
	 */
	private void determineTitleImageLargest() {
		int titleY = titleImageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		int verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		int labelY = titleLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		labelY += verticalSpacing;
		labelY += messageLabelHeight;
		labelY += verticalSpacing;
		titleImageLargest = titleY > labelY;
	}

	/**
	 * @param verticalSpacing
	 * @param horizontalSpacing
	 */
	private void setLayoutsForNormalMessage(int verticalSpacing,
			int horizontalSpacing) {
		FormData messageImageData = new FormData();
		messageImageData.top = new FormAttachment(titleLabel, verticalSpacing);
		messageImageData.left = new FormAttachment(0, H_GAP_IMAGE);
		messageImageLabel.setLayoutData(messageImageData);
		FormData messageLabelData = new FormData();
		messageLabelData.top = new FormAttachment(titleLabel, verticalSpacing);
		messageLabelData.right = new FormAttachment(titleImageLabel);
		messageLabelData.left = new FormAttachment(messageImageLabel,
				horizontalSpacing);
		messageLabelData.height = messageLabelHeight;
		if (titleImageLargest)
			messageLabelData.bottom = new FormAttachment(titleImageLabel, 0,
					SWT.BOTTOM);
		messageLabel.setLayoutData(messageLabelData);
		FormData fillerData = new FormData();
		fillerData.left = new FormAttachment(0, horizontalSpacing);
		fillerData.top = new FormAttachment(messageImageLabel, 0);
		fillerData.bottom = new FormAttachment(messageLabel, 0, SWT.BOTTOM);
		bottomFillerLabel.setLayoutData(fillerData);
		FormData data = new FormData();
		data.top = new FormAttachment(messageImageLabel, 0, SWT.TOP);
		data.left = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(messageImageLabel, 0, SWT.BOTTOM);
		data.right = new FormAttachment(messageImageLabel, 0);
		leftFillerLabel.setLayoutData(data);
	}
	
	/**
	 * @param newErrorMessage
	 */
	public void setErrorMessage(String newErrorMessage) {
		// Any change?
		if (errorMessage == null ? newErrorMessage == null : errorMessage
				.equals(newErrorMessage))
			return;
		errorMessage = newErrorMessage;

		// Clear or set error message.
		if (errorMessage == null) {
			if (showingError) {
				// we were previously showing an error
				showingError = false;
			}
			// show the message
			// avoid calling setMessage in case it is overridden to call
			// setErrorMessage,
			// which would result in a recursive infinite loop
			if (message == null) // this should probably never happen since
				// setMessage does this conversion....
				message = ""; //$NON-NLS-1$
			updateMessage(message);
			messageImageLabel.setImage(messageImage);
			setImageLabelVisible(messageImage != null);
		} else {
			// Add in a space for layout purposes but do not
			// change the instance variable
			String displayedErrorMessage = " " + errorMessage; //$NON-NLS-1$
			updateMessage(displayedErrorMessage);
			if (!showingError) {
				// we were not previously showing an error
				showingError = true;
				messageImageLabel.setImage(JFaceResources
						.getImage(DLG_IMG_TITLE_ERROR));
				setImageLabelVisible(true);
			}
		}
		layoutForNewMessage();
	}

	/**
	 * Re-layout the labels for the new message.
	 */
	private void layoutForNewMessage() {
		int verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		int horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		// If there are no images then layout as normal
		if (errorMessage == null && messageImage == null) {
			setImageLabelVisible(false);
			setLayoutsForNormalMessage(verticalSpacing, horizontalSpacing);
		} else {
			messageImageLabel.setVisible(true);
			bottomFillerLabel.setVisible(true);
			leftFillerLabel.setVisible(true);
			/**
			 * Note that we do not use horizontalSpacing here as when the
			 * background of the messages changes there will be gaps between the
			 * icon label and the message that are the background color of the
			 * shell. We add a leading space elsewhere to compendate for this.
			 */
			FormData data = new FormData();
			data.left = new FormAttachment(0, H_GAP_IMAGE);
			data.top = new FormAttachment(titleLabel, verticalSpacing);
			messageImageLabel.setLayoutData(data);
			data = new FormData();
			data.top = new FormAttachment(messageImageLabel, 0);
			data.left = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(messageLabel, 0, SWT.BOTTOM);
			data.right = new FormAttachment(messageImageLabel, 0, SWT.RIGHT);
			bottomFillerLabel.setLayoutData(data);
			data = new FormData();
			data.top = new FormAttachment(messageImageLabel, 0, SWT.TOP);
			data.left = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(messageImageLabel, 0, SWT.BOTTOM);
			data.right = new FormAttachment(messageImageLabel, 0);
			leftFillerLabel.setLayoutData(data);
			FormData messageLabelData = new FormData();
			messageLabelData.top = new FormAttachment(titleLabel,
					verticalSpacing);
			messageLabelData.right = new FormAttachment(titleImageLabel);
			messageLabelData.left = new FormAttachment(messageImageLabel, 0);
			messageLabelData.height = messageLabelHeight;
			if (titleImageLargest)
				messageLabelData.bottom = new FormAttachment(titleImageLabel,
						0, SWT.BOTTOM);
			messageLabel.setLayoutData(messageLabelData);
		}
		// Do not layout before the dialog area has been created
		// to avoid incomplete calculations.
		if (dialogArea != null)
			workArea.getParent().layout(true);
	}

	/**
	 * @param newMessage
	 */
	public void setMessage(String newMessage) {
		setMessage(newMessage, IMessageProvider.NONE);
	}

	/**
	 * @param newMessage
	 * @param newType
	 */
	public void setMessage(String newMessage, int newType) {
		Image newImage = null;
		if (newMessage != null) {
			switch (newType) {
			case IMessageProvider.NONE:
				break;
			case IMessageProvider.INFORMATION:
				newImage = JFaceResources.getImage(DLG_IMG_MESSAGE_INFO);
				break;
			case IMessageProvider.WARNING:
				newImage = JFaceResources.getImage(DLG_IMG_MESSAGE_WARNING);
				break;
			case IMessageProvider.ERROR:
				newImage = JFaceResources.getImage(DLG_IMG_MESSAGE_ERROR);
				break;
			}
		}
		showMessage(newMessage, newImage);
	}

	/**
	 * @param newMessage
	 * @param newImage
	 */
	private void showMessage(String newMessage, Image newImage) {
		// Any change?
		if (message.equals(newMessage) && messageImage == newImage) {
			return;
		}
		message = newMessage;
		if (message == null)
			message = "";//$NON-NLS-1$
		// Message string to be shown - if there is an image then add in
		// a space to the message for layout purposes
		String shownMessage = (newImage == null) ? message : " " + message; //$NON-NLS-1$  
		messageImage = newImage;
		if (!showingError) {
			// we are not showing an error
			updateMessage(shownMessage);
			messageImageLabel.setImage(messageImage);
			setImageLabelVisible(messageImage != null);
			layoutForNewMessage();
		}
	}

	/**
	 * @param newMessage
	 */
	private void updateMessage(String newMessage) {
		messageLabel.setText(newMessage);
	}
	
	/**
	 * @param visible
	 */
	private void setImageLabelVisible(boolean visible) {
		messageImageLabel.setVisible(visible);
		bottomFillerLabel.setVisible(visible);
		leftFillerLabel.setVisible(visible);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, "Run", true);
//		okButton.setEnabled(false);
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 4;
		layout.marginWidth = 4;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());
		// Build the separator line
		Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		

		
		Group childContainerGroup = createChildContainer(composite);
		
		createLabel(childContainerGroup, "Project:");
		projectsCombo = new Combo(childContainerGroup, SWT.BORDER | SWT.READ_ONLY);
		projectsCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		IProject[] studioProjects = CommonUtil.getAllStudioProjects();
		for (IProject project : studioProjects){
			if (project != null && project.isOpen()) {
				projectsCombo.add(project.getName());
			}
		}
		
		projectsCombo.addModifyListener(this);
		
		new Label(composite, SWT.NONE).setVisible(false);
		
		testDataSelectTable = new Table(composite, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
		testDataSelectTable.setLayout(new GridLayout());
		testDataSelectTable.addSelectionListener(this);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 138;
		testDataSelectTable.setLayoutData(data);
		testDataSelectTable.setLinesVisible(true);
		testDataSelectTable.setHeaderVisible(true);
		addColumns();
		createSelectButton(composite);
		
		Group container = new Group(composite, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createLabel(container, "Rule Sessions:");
		Composite childContainer = createChildContainer(container, 2);
		ruleSessionsCombo = new Combo(childContainer, SWT.BORDER | SWT.READ_ONLY);
		ruleSessionsCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ruleSessionsCombo.addModifyListener(this);
		Button button = new Button(childContainer, SWT.NONE);
		button.setText("Create");//Layout
		button.setVisible(false);
		
		createLabel(container, "Tester Session:");	
		childContainer = createChildContainer(container, 2);
			
		testerSessionText = new Text(childContainer, SWT.BORDER);
		testerSessionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		testerSessionText.addModifyListener(this);
		createButton = new Button(childContainer, SWT.NONE);
		createButton.setText("Add New");
		createButton.addSelectionListener(this);
		createButton.setEnabled(false);
		
		createLabel(container, "Tester Sessions:").setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		childContainer = createChildContainer(container, 2);
		testerSessionsList = new List(childContainer, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		testerSessionsList.addSelectionListener(this);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 50;
		testerSessionsList.setLayoutData(data);
		button = new Button(childContainer, SWT.NONE);
		button.setText("Create");//Layout
		button.setVisible(false);
		if(project != null){
			projectsCombo.setText(project.getName());
		}
		MenuManager popupMenu = new MenuManager();
	    popupMenu.add(new RemoveSessionAction(){});
	    Menu menu = popupMenu.createContextMenu(testerSessionsList);
	    testerSessionsList.setMenu(menu);
	    
	    validate();
	    
		return parent;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private Composite createChildContainer(Composite parent, int col){
		Composite childContainer = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(col, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		return childContainer;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private Group createChildContainer(Composite parent){
		Group childContainer = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		return childContainer;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	protected Control createSelectButton(Composite parent) {
		select = new Button(parent, SWT.CHECK);
		select.setText("Select / deselect all");
		select.setSelection(false);
		select.addSelectionListener(this);
		return parent;
	}
		
	protected void addColumns() {
		TableColumn resPathColumn = new TableColumn(testDataSelectTable, SWT.NULL);
		resPathColumn.setText("Test Data");
		autoTableLayout();
		createTestData();
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
		
	protected void autoTableLayout() {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(testDataSelectTable);
		for (@SuppressWarnings("unused") TableColumn column : testDataSelectTable.getColumns()) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		testDataSelectTable.setLayout(autoTableLayout);
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param checkedItems
	 * @param itemToSearch
	 * @return
	 */
	protected boolean contains(Set<TableItem> checkedItems, TableItem itemToSearch) {
		for (TableItem checkedItem : checkedItems) {
			if (checkedItem.getText(0).equals(itemToSearch.getText(0))) {
				return true;
			}
		}
		return false;
	}
	
	protected abstract void createTestData();
	
	private class RemoveSessionAction extends org.eclipse.jface.action.Action {
		public RemoveSessionAction() {
			super("Stop");
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
//			if (RSP == null) {
//				return;
//			}
//			if (testerSessionsList.getSelectionIndices().length > 0) {
//				for(int index : testerSessionsList.getSelectionIndices()){
//					String sessionName = testerSessionsList.getItem(index);
//					TesterSession session = getTesterSession(sessionName);
//					if (session != null) {
//						session.stop();
//					}
//				}
//				testerSessionsList.remove(testerSessionsList.getSelectionIndices());
//			}
		}
	}
	
	/**
	 * @param seesionName
	 * @return
	 */
//	protected boolean isDuplicateSession(String seesionName){
//		for (TesterSession session : RSP.getAllConnectedSessions()) {
//			if (session.getSessionName().equals(seesionName)) {
//				return true;
//			}
//		}
//		return false;
//	}
	

	/**
	 * @param newTitle
	 */
	public void setTitle(String newTitle) {
		if (titleLabel == null)
			return;
		String title = newTitle;
		if (title == null)
			title = "";//$NON-NLS-1$
		titleLabel.setText(title);
	}
	
	/**
	 * @param sessionName
	 * @return
	 */
//	public TesterSession getTesterSession(String sessionName) {
//		for (TesterSession session : RSP.getAllConnectedSessions()) {
//			if (session.getSessionName().equals(sessionName)) {
//				return session;
//			}
//		}
//		return null;
//	}
	
	protected void validate() {
		
	}
}