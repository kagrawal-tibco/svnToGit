package com.tibco.cep.studio.rms.history;

import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.REVISION_COLS;
import static com.tibco.cep.studio.rms.history.RMSHistoryRevisionUtils.REVISION_DETAIL_COLS;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils;
import com.tibco.cep.studio.rms.history.actions.DiffWorkingCopyAction;
import com.tibco.cep.studio.rms.history.actions.OpenDetailsArtifactAction;
import com.tibco.cep.studio.rms.history.actions.PreviousRevisionDiffAction;
import com.tibco.cep.studio.rms.history.actions.RevertFromRevisionAction;
import com.tibco.cep.studio.rms.history.actions.RevisionDetailsDiffAction;
import com.tibco.cep.studio.rms.history.actions.RevisionMasterCopyCompareAction;
import com.tibco.cep.studio.rms.history.model.HistoryRevisionItem;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSHistoryFormViewer extends AbstractRMSClientFormViewer {

	private RMSHistoryEditor editor;
	
	private String title;
	
	private String projectName;
	
	private List<HistoryRevisionItem> revisionItems;
	
	private String resourcePath;
	
	/**
	 * @param editor
	 */
	public RMSHistoryFormViewer(RMSHistoryEditor editor) {
		this.editor = editor;
		init();
	}
	
	private void init() {
		if (editor != null && editor.getEditorInput() instanceof  RMSHistoryEditorInput) {
			RMSHistoryEditorInput historyEditorInput = (RMSHistoryEditorInput)editor.getEditorInput();
			resourcePath = historyEditorInput.getResourcePath();
			projectName = historyEditorInput.getLocalProjectName();
			title = Messages.getString("rms.history.edit.title", projectName + resourcePath);
		}
	}

	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		super.createPartControl(container, title, RMSUIPlugin.getImageDescriptor("icons/rms_his.png").createImage());
		sashForm.setWeights(defaultWeightPropertySections);// setting the default weights for the available sections.
		createToolBarActions();
		populateRevisionDetails();
	}
	
	public void populateRevisionDetails() {
		RMSHistoryEditorInput historyEditorInput = (RMSHistoryEditorInput)editor.getEditorInput();
		//Get various params
		String historyURL = historyEditorInput.getHistoryURL();
		String projectName = historyEditorInput.getRepoProjectName();
		String resourcePath = historyEditorInput.getResourcePath();
		
		revisionItems = 
			ArtifactsManagerUtils.fetchResourceHistory(historyURL, projectName, resourcePath);
		
		for (HistoryRevisionItem revisionItem : revisionItems) {
			revisionTableViewer.add(revisionItem);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.AbstractRMSClientFormViewer#createRevisionsPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createRevisionsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		revisionsSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		revisionsSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		revisionsSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		revisionsSection.setFont(new org.eclipse.swt.graphics.Font(Display.getCurrent(), new FontData("Tahoma", 8, SWT.BOLD)));
		revisionsSection.addExpansionListener(new ExpansionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.forms.events.ExpansionAdapter#expansionStateChanged(org.eclipse.ui.forms.events.ExpansionEvent)
			 */
			public void expansionStateChanged(ExpansionEvent e) {
				adjustSections(e, true);
			}
		});
		revisionsSection.setText("Revisions");
		Composite sectionClient = toolkit.createComposite(revisionsSection, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		sectionClient.setLayoutData(layoutData);
	
		createRevisionTablePart(sectionClient);
		revisionsSection.setClient(sectionClient);
		toolkit.paintBordersFor(sectionClient);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.AbstractRMSClientFormViewer#createDetailsPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDetailsPart(final IManagedForm managedForm,Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		detailsSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		detailsSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		detailsSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		detailsSection.setFont(new org.eclipse.swt.graphics.Font(Display.getCurrent(), new FontData("Tahoma", 8, SWT.BOLD)));
		detailsSection.addExpansionListener(new ExpansionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.forms.events.ExpansionAdapter#expansionStateChanged(org.eclipse.ui.forms.events.ExpansionEvent)
			 */
			public void expansionStateChanged(ExpansionEvent e) {
				adjustSections(e, false);
			}
		});
		detailsSection.setText("Details");
		Composite sectionClient = toolkit.createComposite(detailsSection, SWT.NONE);
		detailsSection.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		sectionClient.setLayoutData(layoutData);
		
		createDetailsTablePart(sectionClient);
		detailsSection.setExpanded(false);
		toolkit.paintBordersFor(sectionClient);
	}
	
	/**
	 * @param parent
	 */
	private void createRevisionTablePart(Composite parent) {
		revisionTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		revisionTable.setLayout(new GridLayout());
		revisionTable.addSelectionListener(this);
		revisionTable.setLinesVisible(true);
		
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 1000;
		revisionTable.setLayoutData(gd);
		
		revisionTable.setHeaderVisible(true);

		revisionTableViewer = new TableViewer(revisionTable);
		revisionTableViewer.setColumnProperties(REVISION_COLS);
		
		RevisionTableSorter tableSorter = new RevisionTableSorter();
		revisionTableViewer.setSorter(tableSorter);
		int index = 0;
		for (final String col : REVISION_COLS) {
			final TableColumn column = new TableColumn(revisionTable, SWT.NONE);
			column.setText(col);
			column.setMoveable(false);
			column.setResizable(true);
			if (index == 0) { //for col data ascending/ descending, can be commented not to allow
//				column.addSelectionListener(new RevisionTableSortSelectionListener(revisionTableViewer, tableSorter, index));
			}
			index ++;
		}
	
		revisionTableViewer.setLabelProvider(new HistoryRevisionViewerLabelProvider());
		revisionTableViewer.setContentProvider(new HistoryRevisionViewerContentProvider());

		//TODO Change This
		revisionTableViewer.addSelectionChangedListener(new RevisionTableSelectionListener());
		revisionTableViewer.getTable().setLinesVisible(true);
		
		revisionTableViewer.setCellModifier(new HistoryRevisionViewerCellModifier(revisionTableViewer));
		revisionTableViewer.setCellEditors(new CellEditor[] {null, null, null, null, new TextCellEditor(revisionTable)});
		
		autoTableLayout(revisionTable, new Integer[] {1, 1, 1, 1, 1});
		RMSHistoryRevisionUtils.createTableEditor(revisionTableViewer, 4);
		
//		HistoryRevisionDiffAction hisDiffAction = new HistoryRevisionDiffAction(revisionTableViewer);
//		RevertToRevisionAction revertToRevisionAction = new RevertToRevisionAction(revisionTableViewer);
//		RevisionCheckoutAction revCheckoutAction = new RevisionCheckoutAction(revisionTableViewer);
//		setContextMenu(revisionTable, new Action[]{revertToRevisionAction, null, 
//				                                   revCheckoutAction});
	}
	
	/**
	 * @param parent
	 */
	private void createDetailsTablePart(Composite parent) {
		detailsTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		detailsTable.setLayout(new GridLayout());
		detailsTable.addSelectionListener(this);
		detailsTable.setLinesVisible(true);
		
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 1000;
		detailsTable.setLayoutData(gd);
		
		detailsTable.setHeaderVisible(true);
		for (String col : REVISION_DETAIL_COLS) {
			TableColumn column = new TableColumn(detailsTable, SWT.NONE);
			column.setText(col);
			column.setMoveable(false);
			column.setResizable(true);
		}
		detailsTableViewer = new TableViewer(detailsTable);
		detailsTableViewer.setComparer(new HistoryViewItemComparer());
		detailsTableViewer.setLabelProvider(new RevisionDetailsViewerLabelProvider(detailsTableViewer, resourcePath));
		detailsTableViewer.setContentProvider(new RevisionDetailsViewerContentProvider());
		detailsTableViewer.setCellModifier(new RevisionDetailsViewerCellModifier(detailsTableViewer));
		detailsTableViewer.setCellEditors(new CellEditor[] {null, null});
		detailsTableViewer.setColumnProperties(REVISION_DETAIL_COLS);
		detailsTableViewer.addSelectionChangedListener(new RevisionDetailsTableSelectionListener());
		detailsTableViewer.getTable().setLinesVisible(true);
		autoTableLayout(detailsTable, new Integer[] {25, 75, 25});
		
		DiffWorkingCopyAction diffWorkingCopyAction = new DiffWorkingCopyAction(editor, detailsTableViewer);
		RevisionDetailsDiffAction revDiffAction = new RevisionDetailsDiffAction(detailsTableViewer, editor);
		//the following menu action would move to right click resource RMS -> Compare with Approved copy/revision 
//		LocalMasterCopyCompareAction masterCopyCompareAction = new LocalMasterCopyCompareAction(detailsTableViewer, editor);
		RevisionMasterCopyCompareAction revisionMasterCopyCompareAction = new RevisionMasterCopyCompareAction(detailsTableViewer, editor);
		OpenDetailsArtifactAction openDetailsArtifactAction = new OpenDetailsArtifactAction(detailsTableViewer, editor);
		PreviousRevisionDiffAction previousRevisionDiffAction = new PreviousRevisionDiffAction(detailsTableViewer, editor);
		RevertFromRevisionAction revertFromRevisionAction = new RevertFromRevisionAction(detailsTableViewer, editor);
		setContextMenu(detailsTable, new Action[]{diffWorkingCopyAction,
												  revDiffAction, 
												  previousRevisionDiffAction,
				                                  revisionMasterCopyCompareAction,
				                                  null,
				                                  openDetailsArtifactAction, 
				                                  null, 
				                                  revertFromRevisionAction});
	}

	@Override
	protected void createGeneralPart(ScrolledForm form, FormToolkit toolkit) {
		// TODO Auto-generated method stub
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private class RevisionTableSelectionListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				Object[] selectedObjects = structuredSelection.toArray();
				// Clear any previous details since revision selected would be
				// different.
				detailsTable.removeAll();
				int topIndex = -1;
				for (Object selectedObject : selectedObjects) {
					if (selectedObject instanceof HistoryRevisionItem) {
						List<String> list = new ArrayList<String>();
						HistoryRevisionItem historyRevisionItem = (HistoryRevisionItem) selectedObject;
						for (int row = revisionTable.getItemCount() - 1; row >= 0; row--) {
							HistoryRevisionItem item = (HistoryRevisionItem)revisionTableViewer.getElementAt(row);
							if (item == historyRevisionItem) {
								break;
							} else {
								list.add(item.getRevisionId());
							}
						}
						int index = 0;
						RevisionDetailsItem[] revisionDetailsItems = historyRevisionItem.getRevisionDetails();
						for (RevisionDetailsItem revisionDetailsItem : revisionDetailsItems) {
							if (revisionDetailsItem.getArtifactPath().equals(resourcePath)) {
								topIndex = index; 
							}
							revisionDetailsItem.setRevisions(list);
							detailsTableViewer.add(revisionDetailsItem);
							index++;
						}
					}
				}
				if (topIndex != -1) {
					detailsTable.setTopIndex(topIndex);
				}
			}
		}
	}
	
	private class RevisionDetailsTableSelectionListener implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			// TODO Auto-generated method stub
			
		}
	
	}

	/**
	 * @param limit
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.AbstractRMSClientFormViewer#fetchNextRevisions(int)
	 */
	protected void fetchNextRevisions(int limit) {
		//TODO: populate Table	
	}

	/**
	 * @param limit
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.AbstractRMSClientFormViewer#fetchPreviousRevisions(int)
	 */
	protected void fetchPreviousRevisions(int limit) {
		//TODO: populate Table	
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.AbstractRMSClientFormViewer#refresh()
	 */
	protected void refresh() {
		for (int index = 0; index < revisionTableViewer.getTable().getItemCount(); index++) {
			HistoryRevisionItem element = (HistoryRevisionItem)revisionTableViewer.getElementAt(index);
			revisionTableViewer.remove(element);
		}
		revisionTableViewer.refresh();
		populateRevisionDetails();
		for (int index = 0; index < detailsTableViewer.getTable().getItemCount(); index++) {
			RevisionDetailsItem element = (RevisionDetailsItem)detailsTableViewer.getElementAt(index);
			detailsTableViewer.remove(element);
		}
	}
	
	/**
	 * Update editor title, label provider and refreshes actual widgets
	 */
	public void doRefresh() {
		init();
		updateTitle(title);
		((RevisionDetailsViewerLabelProvider)detailsTableViewer.getLabelProvider()).setResourcePath(resourcePath);
		refresh();
	}
}