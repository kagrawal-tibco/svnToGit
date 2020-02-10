package com.tibco.cep.studio.rms.ui.wizards;

import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.getArtifactImage;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * @author sasahoo
 */
public abstract class AbstractRMSArtifactsDialog extends AbstractArtifactsRunnableDialog implements TraverseListener {

	public static boolean AUTOMATED_MODE = false;
	
	private boolean forward = true;
	
	protected String title;
	
	protected Table detailsTable;
	
    protected int fWidth = 60;
    
    protected String comments;
    
    protected boolean isEditable = true;
    
    private boolean showComments =  false;
    
    private boolean showDetails =  false;

	protected Set<TableItem> checkedResources =  new HashSet<TableItem>();
	
	protected List<ArtifactOperation> artifactOperations = new ArrayList<ArtifactOperation>();
	protected List<Artifact> fetchedartifacts = new ArrayList<Artifact>();
	protected List<String> artifactPaths = new ArrayList<String>();
	protected List<String> artifactExtns = new ArrayList<String>();
	protected List<String> operations = new ArrayList<String>();
	
	protected StyledText commentsTextArea;
	
	protected Control detailsButtonsControl;
	
	protected Label commentsLabel;
	
	protected Group ckURLGroup;
	
    protected Button select;
    
	protected Table artifactsTable;
	
	/**
	 * The URL selection dialog
	 */
	protected Combo urlCombo;
	
	/**
	 * RMS projects selection combo
	 */
	protected Combo projectsCombo;
	
	protected String[] projectsList;
	
	protected String selectedProject;
	
	int imgChangeCnt =0;
    
    /**
     * @param parentShell
     * @param dialogTitle
     * @param showComments
     * @param isEditable
     * @param showDetails
     */
    public AbstractRMSArtifactsDialog(Shell parentShell, 
    		                          String dialogTitle, 
    		                          boolean showComments, 
    		                          boolean isEditable,
    		                          boolean showDetails) {
    	this(parentShell, dialogTitle, showComments, showDetails);
    	this.isEditable = isEditable;
    }
    
    /**
     * @param parentShell
     * @param dialogTitle
     * @param showComments
     * @param showDetails
     */
    public AbstractRMSArtifactsDialog(Shell parentShell, 
    		                          String dialogTitle, 
    		                          boolean showComments, 
    		                          boolean showDetails) {
    	this(parentShell, dialogTitle, showDetails);
    	this.showComments = showComments;
    }
    
	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param showDetails
	 */
	public AbstractRMSArtifactsDialog(Shell parentShell, String dialogTitle, boolean showDetails) {
		this(parentShell, dialogTitle);
		this.showDetails = showDetails;
	}
    
	/**
	 * @param parentShell
	 * @param dialogTitle
	 */
	public AbstractRMSArtifactsDialog(Shell parentShell, String dialogTitle) {
		super(parentShell);
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	protected abstract void addColumns();

	/*
	 * @see Dialog.createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// initialize the dialog units
		initializeDialogUnits(parent);
		Point defaultSpacing = LayoutConstants.getSpacing();
		GridLayoutFactory.fillDefaults().margins(LayoutConstants.getMargins())
				.spacing(defaultSpacing.x * 2,
				defaultSpacing.y).numColumns(getColumnCount()).applyTo(parent);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
		createDialogAndButtonArea(parent);
		return parent;
	}
	
	/**
	 * Auto Table Layout based on no of columns
	 */
	protected void autoTableLayout() {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(artifactsTable);
		for (@SuppressWarnings("unused") TableColumn column : artifactsTable.getColumns()) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		artifactsTable.setLayout(autoTableLayout);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
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

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		createDetailsButton(parent);
	}

	/**
	 * @param parent
	 */
	protected void createDetailsButton(Composite parent) {
		if (shouldShowDetailsButton()) {
			/*detailsButton = */createButton(parent, IDialogConstants.DETAILS_ID,
					IDialogConstants.SHOW_DETAILS_LABEL, false);
		}
	}
	
	/**
	 * @param parent
	 */
	protected Control createCommentsContents(Composite parent) {
		if (shouldShowComments()) {
			commentsLabel = new Label(parent, SWT.NONE);
			commentsLabel.setText("Comments");
			commentsTextArea = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
			GridData data = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
			data.heightHint = 80;
			data.widthHint = 300;
			commentsTextArea.setLayoutData(data);
			commentsTextArea.setEditable(isEditable);
			commentsTextArea.addModifyListener(this);
			commentsTextArea.setData(1);
		}
		return parent;
	}
	
	protected Control createSelectButton(Composite parent) {
		select = new Button(parent, SWT.CHECK);
		select.setText("Select / deselect all");
		select.setSelection(false);
		select.addSelectionListener(this);
		return parent;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	protected Control createTableArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
//		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
//		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(parent);

		artifactsTable = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
		artifactsTable.setLayout(new GridLayout());
		artifactsTable.addSelectionListener(this);

		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 138;
		
		artifactsTable.setLayoutData(data);
		artifactsTable.setFont(new Font(Display.getDefault(), "Tahoma", 10, SWT.NORMAL));
	
		artifactsTable.setLinesVisible(true);
		artifactsTable.setHeaderVisible(true);
		
		addColumns();
		
		createSelectButton(parent);

		return parent;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		createTableArea(parent);
		return null;
	}

	/*
	 * @see IconAndMessageDialog#createDialogAndButtonArea(Composite)
	 */
	protected void createDialogAndButtonArea(Composite parent) {
		// create the dialog area and button bar
		createProjectSelectionArea(parent);
		createCommentsContents(parent);
		dialogArea = createDialogArea(parent);
		buttonBar = createButtonBar(parent);

		// Apply to the parent so that the message gets it too.
		applyDialogFont(parent);
	}
	
	/**
	 * Projects and url components. Subclasses can choose however to
	 * add components apart from these.
	 * @param parent
	 */
	protected void createProjectSelectionArea(Composite parent) {
		//override this
	}
	
	protected Control createDetailsButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font
		// size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 0; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
		
		// Add the buttons to the button bar.
		createDetailButtonsForButtonBar(composite);
		return composite;
	}
	
	protected void createDetailButtonsForButtonBar(Composite parent) {
		okButton =  createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Table createDropDownDetails(Composite parent) {
		detailsTable = new Table(parent, SWT.BORDER | SWT.SINGLE);
		detailsTable.setBackground(detailsTable.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		data.heightHint = 138 /*details.getLineHeight() * LIST_ITEM_COUNT*/;
		data.horizontalSpan = 2;
		detailsTable.setLayoutData(data);
		detailsTable.setFont(parent.getFont());

		TableColumn typeColumn = new TableColumn(detailsTable, SWT.NULL);
		typeColumn.setWidth(convertWidthInCharsToPixels(fWidth));
		
		
		if (needsProgressMonitor) {
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			// Insert a progress monitor
			progressMonitorPart = createProgressMonitorPart(parent, layout);
			progressMonitorPart.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			progressMonitorPart.setVisible(false);
			applyDialogFont(progressMonitorPart);
		}		
		detailsButtonsControl = createDetailsButtonBar(parent);
		
		return detailsTable;
	}
	
	/**
	 * Give control to concrete implementations to decide what and how to dispose.
	 * Also allows for doing any cleanup before dispose
	 */
	protected abstract void disposeComponents();
	
	protected void toggleDetailsArea() {
		Point windowSize = getShell().getSize();
		Point oldSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		disposeComponents();
		select.dispose();
		artifactsTable.dispose();
		buttonBar.dispose();
		detailsTable = createDropDownDetails((Composite) getContents());
		Point newSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		getShell().setSize(new Point(windowSize.x, windowSize.y + (newSize.y - oldSize.y)));
	}
	
	protected boolean performOk() {
		if (artifactOperations.size() > 0 && fetchedartifacts.size() > 0) {
			cancelPressed();
			return false;
		}
		
		if (projectsCombo.getText().isEmpty()) {
			return false;
		}
		Set<TableItem> selectedTableItems = checkedResources;
		if (checkedResources.size() == 0) {
			cancelPressed();
			return false;
		}
		
		for (TableItem tableItem : selectedTableItems) {
			//Items could be disposed if different project is selected
			//and number of items in previous selection is more than
			//those in current selection.
			if (!tableItem.isDisposed()) {
				artifactPaths.add(tableItem.getText(0));
				artifactExtns.add(tableItem.getText(2));
				operations.add(tableItem.getText(3));
			}
		}

		showDetailsArea();

		artifactOperations.clear();
		fetchedartifacts.clear();
		if (needsProgressMonitor) {
			progressMonitorPart.setVisible(true);
		}
		
		return true;
	}
	
	/**
	 * @param e
	 */
	public void keyTraversed(TraverseEvent e) {
		if (e.detail == SWT.TRAVERSE_RETURN) {
			e.doit = false;
			e.detail = SWT.TRAVERSE_NONE;
			try {
				if (e.getSource() == urlCombo) {
					String newText = urlCombo.getText();
					urlCombo.add(newText);
					urlCombo.setSelection(new Point(0, newText.length()));
				}
				if (e.getSource() == projectsCombo) {
					String newText = projectsCombo.getText();
					projectsCombo.add(newText);
					projectsCombo.setSelection(new Point(0, newText.length()));
				}
			}
			catch (Exception ex) {
				RMSUIPlugin.log(ex);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {
		return super.close();
	}

	protected final void showDetailsArea() {
		Control control = getContents();
		if (control != null && ! control.isDisposed()) {
			toggleDetailsArea();
		}
	}
	
	/**
	 * Populate RMS artifacts
	 */
	protected abstract void populateArtifacts();
	
	/**
	 * Get RMS artifacts
	 */
	protected void createArtifactsItem(Artifact artifact, 
			                           ArtifactOperation artifactOperation) {
		TableItem item = new TableItem(artifactsTable, SWT.CENTER);
		item.setImage(getArtifactImage(artifact.getArtifactType()));
		item.setText(0, artifact.getArtifactPath());
		item.setText(1, artifact.getArtifactType().getName());
		String extension = (artifact.getArtifactExtension() == null)
				? artifact.getArtifactType().getLiteral() : artifact.getArtifactExtension();
		item.setText(2, extension);
		if (select != null) item.setChecked(select.getSelection());
		
		switch (artifactOperation) {
		case ADD :
			item.setForeground(new Color(null, 0, 0, 255));
			break;
		case MODIFY :
			item.setForeground(new Color(null, 153, 51, 0));
			break;
		case DELETE :
			item.setForeground(new Color(null, 255, 0, 0));
			break;	
		}
		item.setText(3, artifactOperation.getName());
	}
	
	/**
	 * Get the number of columns in the layout of the Shell of the dialog.
	 */
	int getColumnCount() {
		return 2;
	}
	
	/**
	 * @return
	 */
	protected boolean shouldShowDetailsButton() {
		return showDetails;
	}
	
	/**
	 * @return
	 */
	protected boolean shouldShowComments() {
		return showComments;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(final ModifyEvent e) {
		Widget widget = (Widget) e.widget;
		Object data = widget.getData();
		if (widget instanceof StyledText) {
			StyledText textArea = (StyledText) widget;
			int num = ((Integer) data).intValue();
			switch (num) {
			case 1:
				comments = textArea.getText();
				break;
			}
		}
		if (e.getSource() == projectsCombo) {
			selectedProject = projectsCombo.getText();
			okButton.setEnabled(shouldOkEnable());
		}
	}
	
	

	/**
	 * @return
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/*
	 * Code for sorting the Columns in the Commit/Update Dialog for RMS.
	 */
	/**
	 * @param items
	 * @param var
	 * @param ascend
	 */
	public void compare(TableItem[] items,int var, boolean ascend) {
		int i = 0, j = 0;
		while (i < (items.length-1)) {
			j=0;
			while ( j < items.length-(i+1)) {
				imgChangeCnt = 0;
				String s1=items[j].getText(0);
				String s2=items[j+1].getText(0);
				if(var==0){	
					int x=s1.length()-1;

					while(s1.charAt(x)!='/'){
						x--;
					}
					int x1=s2.length()-1;
					while(s2.charAt(x1)!='/'){
						x1--;
					}
					swap(items,j,s1.substring(x+1, s1.length()),s2.substring(x1+1, s2.length()), ascend);
				} else if (var == 1) {
					s1=items[j].getText(1);
					s2=items[j+1].getText(1);
					swap(items,j,s1,s2,ascend);
				}
				else if (var == 2) {
					s1=items[j].getText(2);
					s2=items[j+1].getText(2);
					swap(items,j,s1,s2,ascend);
				}
				j++;
			}
			i++;
		}
	}

	/**
	 * @param items
	 * @param count
	 * @param first
	 * @param second
	 * @param ascend
	 */
	public void swap(TableItem[] items, int count, String first, String second, boolean ascend) {
		Collator collator = Collator.getInstance(Locale.getDefault());
		Image image;
		if (!ascend) {
			if (collator.compare(first, second) > 0) {
				int k=0;
				while ( k < 4) {
					first = items[count+1].getText(k);
					second = items[count].getText(k);
					if ( imgChangeCnt < 1 ) {
						image =items[count].getImage();
						items[count].setImage(items[count+1].getImage());
						items[count+1].setImage(image);
						imgChangeCnt++;
					}
					items[count].setText(k,first);
					items[count+1].setText(k,second);
					k++;
				}
			}
		} else {
			if (collator.compare(first, second) < 0) {
				int k=0;
				while (k < 4) {
					first=items[count+1].getText(k);
					second=items[count].getText(k);
					if ( imgChangeCnt < 1 ) {
						image =items[count].getImage();
						items[count].setImage(items[count+1].getImage());
						items[count+1].setImage(image);
						imgChangeCnt++;
					}
					items[count].setText(k,first);
					items[count+1].setText(k,second);
					k++;
				}
				forward = true;
		}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == select) {
			handleSelectArtifacts(select.getSelection());
		}
		if (e.item instanceof TableItem) {
			TableItem item = (TableItem)e.item;
			
			if (item.getChecked()) {
				if (!contains(checkedResources, item)) {
					checkedResources.add(item);
				}
			} else {
				if (contains(checkedResources, item)) {
					checkedResources.remove(item);
				}
			}
			if (checkedResources.size() == artifactsTable.getItems().length) {
				select.setSelection(true);
			} else {
				select.setSelection(false);
			}
		}
		okButton.setEnabled(shouldOkEnable());
	}
	
	/**
	 * @param e
	 */
	protected void sortColumns(SelectionEvent e) {
		if (e.getSource() instanceof TableColumn) {
			Collator collator = Collator.getInstance(Locale.getDefault());
			TableItem[] items= artifactsTable.getItems();
			TableColumn sortColumn = artifactsTable.getSortColumn();
			TableColumn currentColumn = (TableColumn) e.widget;
			int dir = artifactsTable.getSortDirection();
			if (sortColumn == currentColumn) {
				dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			} else {
				artifactsTable.setSortColumn(currentColumn);
				dir = SWT.UP;
			}
			forward = !forward ;
			TableColumn column=(TableColumn) e.widget;
			if(collator.compare(column.getText(),"Path")==0){
				compare(items,0,forward );
			}else if(collator.compare(column.getText(),"Type")==0){
				compare(items,1, forward );
			}
			else if(collator.compare(column.getText(),"Extension")==0){
				compare(items,2, forward );
			}
			artifactsTable.setSortDirection(dir);
		}
	}
	
	/**
	 * @param selected
	 */
	protected void handleSelectArtifacts(boolean selected) {
		for (TableItem item : artifactsTable.getItems()) {
			item.setChecked(selected);
			if (selected) {
				checkedResources.add(item);
			} else if (!(checkedResources.add(item))) {
				checkedResources.remove(item);
			}
		}
	}
	
	/**
	 * Enable OK Button iff project is selected and at least one artifact is selected
	 * @return
	 */
	protected boolean shouldOkEnable() {
		return (selectedProject != null && selectedProject.length() > 0) && checkedResources.size() > 0;
	}
	
	abstract protected void createDetailsItem(Artifact fetchedArtifact, 
            ArtifactOperation artifactOperation);
	
	protected boolean contains(Set<TableItem> checkedItems, TableItem itemToSearch) {
		for (TableItem checkedItem : checkedItems) {
			//This check is sufficient because resource paths are unique
			if (checkedItem.getText(0).equals(itemToSearch.getText(0))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param errorMessage
	 */
	protected void createItem(String message, boolean success) {
		if (detailsTable == null || detailsTable.isDisposed()) {
			return;
		}
		TableItem detailsItem = new TableItem(detailsTable, SWT.NONE);
		detailsItem.setText(0, message);
		if (success) {
			detailsItem.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		} else {
			detailsItem.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		}
		detailsTable.setTopIndex(detailsTable.getItemCount() - 1);
		detailsTable.redraw();
	}

	/**
	 * 
	 * @param fetchedArtifact
	 * @param artifactOperation
	 * @param isError
	 * @param isSuccess
	 * @param message
	 */
	protected void updateItems(final Artifact fetchedArtifact,
                               final ArtifactOperation artifactOperation, 
                               final boolean isError,
                               final boolean isSuccess,
                               final String message) {
		updateItems(fetchedArtifact, artifactOperation, isError, isSuccess, message, false);
	}
	
	/**
	 * 
	 * @param fetchedArtifact
	 * @param artifactOperation
	 * @param isError
	 * @param isSuccess
	 * @param message
	 * @param terminateOp
	 */
	protected void updateItems(final Artifact fetchedArtifact,
                               final ArtifactOperation artifactOperation, 
                               final boolean isError,
                               final boolean isSuccess,
                               final String message,
                               final boolean terminateOp) {
		try {
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				@Override
				public void run() {
					if (isError) {
						getShell().setText(title /*+ "Failed!"*/);
						createItem(message, false);
						detailsTable.setTopIndex(detailsTable.getItemCount() - 1);
						if (terminateOp) {
							throw new RuntimeException("Commit Failure due to " + message);
						}
					} else if (isSuccess) {
						getShell().setText(title /*+ "Successful"*/);
						createItem(message, true);
					} else {
						createDetailsItem(fetchedArtifact, artifactOperation);
					}
				}
			}, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void setWidth(int width) {
		this.fWidth = width;
	}
	
    public void setNeedsProgressMonitor(boolean needsProgressMonitor) {
        this.needsProgressMonitor = needsProgressMonitor;
    }

	/**
	 * @return the checkedResources
	 */
	public Set<TableItem> getCheckedResources() {
		return checkedResources;
	}

	

}