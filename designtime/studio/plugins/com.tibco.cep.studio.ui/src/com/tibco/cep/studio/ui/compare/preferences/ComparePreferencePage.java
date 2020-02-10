package com.tibco.cep.studio.ui.compare.preferences;

import static com.tibco.cep.studio.core.compare.util.CoreCompareUtils.load;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;
import com.tibco.cep.studio.core.compare.util.CompareMessages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.model.EMFResourceNode;
import com.tibco.cep.studio.ui.compare.provider.ResourceDiffLabelProvider;
import com.tibco.cep.studio.ui.compare.provider.ResourceStructureCreator;
import com.tibco.cep.studio.ui.preferences.OverlayPreferenceStore;

public class ComparePreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private static String[] colorPreferences 	 = new String[3];
	private static String[] colorPreferenceNames = new String[3];

	private ColorSelector 			fTextColorSelector;
	private List 					fCompareTypesList;
	private OverlayPreferenceStore 	fOverlayPreferenceStore;
	private StructureDiffViewer 	fStructureDiffViewer;
	private TextMergeViewer 		fTextMergeViewer;
	private Button fSyncScrollingBtn;
	private Button fConnectRangesBtn;
	private Button fHighlightChangesBtn;
	private Button fShowStructureCompareBtn; // initially show structural compare
	private Button fShowTextCompareBtn; // initially show text compare
	private Button fShowAdditionalInfoBtn;
	private Button fIgnoreWhitespaceBtn;
	private Button fAllowColumnMerge;

	static {
		colorPreferences[0] = ComparePreferenceConstants.COMPARE_ADDED_COLOR;
		colorPreferences[1] = ComparePreferenceConstants.COMPARE_REMOVED_COLOR;
		colorPreferences[2] = ComparePreferenceConstants.COMPARE_CHANGED_COLOR;
		
		colorPreferenceNames[0] = CompareMessages.getString("ComparePreferencePage.Added");
		colorPreferenceNames[1] = CompareMessages.getString("ComparePreferencePage.Removed");
		colorPreferenceNames[2] = CompareMessages.getString("ComparePreferencePage.Changed");
	}

	public ComparePreferencePage() {
		super();
		setPreferenceStore(StudioUIPlugin.getDefault()
				.getPreferenceStore());
		fOverlayPreferenceStore = new OverlayPreferenceStore(getPreferenceStore(), getOverlayKeys());
	}

	private OverlayKey[] getOverlayKeys() {
		OverlayKey[] keys = new OverlayKey[colorPreferences.length + 8];
		for (int i = 0; i < colorPreferences.length; i++) {
			keys[i] = new OverlayKey(colorPreferences[i], OVERLAYKEYTYPES.STRING);
		}
		keys[colorPreferences.length] = new OverlayKey(ComparePreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 1] = new OverlayKey(ComparePreferenceConstants.COMPARE_SYNC_SCROLL_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 2] = new OverlayKey(ComparePreferenceConstants.COMPARE_SINGLE_LINE_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 3] = new OverlayKey(ComparePreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 4] = new OverlayKey(ComparePreferenceConstants.COMPARE_SHOW_INFO_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 5] = new OverlayKey(ComparePreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 6] = new OverlayKey(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 7] = new OverlayKey(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS, OVERLAYKEYTYPES.BOOLEAN);
		return keys;
	}

	public ComparePreferencePage(String title) {
		super(title);
	}

	public ComparePreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 1000;
		data.minimumWidth = 1000;
		composite.setLayoutData(data);

		createPreviewer(composite);
		
		setControl(parent);
		return composite;
	}

	public void init(IWorkbench workbench) {
		fOverlayPreferenceStore.load();
	}

	private Composite createStructurePreviewerTab(Composite composite, DiffNode node) {
		
		Composite parent = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		fShowStructureCompareBtn = new Button(parent, SWT.CHECK);
		fShowStructureCompareBtn.setText(CompareMessages.getString("ComparePreferencePage.Show.StructuralCompare"));
		fShowStructureCompareBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP));
		fShowStructureCompareBtn.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fShowStructureCompareBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP, true);
					setErrorMessage(null);
					setValid(true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP, false);
					if (!fShowTextCompareBtn.getSelection()) {
						setErrorMessage(CompareMessages.getString("ComparePreferencePage.Error.MustShowTextOrStructureCompare"));
						setValid(false);
					}
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});
		
		fShowTextCompareBtn = new Button(parent, SWT.CHECK);
		fShowTextCompareBtn.setText(CompareMessages.getString("ComparePreferencePage.Show.TextCompare"));
		fShowTextCompareBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP));
		fShowTextCompareBtn.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				if (fShowTextCompareBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP, true);
					setErrorMessage(null);
					setValid(true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP, false);
					if (!fShowStructureCompareBtn.getSelection()) {
						setErrorMessage(CompareMessages.getString("ComparePreferencePage.Error.MustShowTextOrStructureCompare"));
						setValid(false);
					}

				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		
		// colors
		createColorGroup(parent);
		
		// preview
		Label previewLabel = new Label(parent, SWT.NONE);
		previewLabel.setText(CompareMessages.getString("ComparePreferencePage.Preview"));
		
		final Tree tree = new Tree(parent, SWT.FULL_SELECTION | SWT.BORDER);
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(CompareMessages.getString("ComparePreferencePage.DecisionTable"));
		column.setToolTipText(CompareMessages.getString("ComparePreferencePage.DecisionTable"));
		final TreeColumn leftColumn = new TreeColumn(tree, SWT.LEFT);
		final TreeColumn rightColumn = new TreeColumn(tree, SWT.LEFT);

		column.setWidth(300);
		leftColumn.setWidth(150);
		rightColumn.setWidth(150);
		
		CompareConfiguration config = new CompareConfiguration(fOverlayPreferenceStore);

		config.setLeftLabel(CompareMessages.getString("ComparePreferencePage.PreviewLabel1"));
		config.setRightLabel(CompareMessages.getString("ComparePreferencePage.PreviewLabel2"));
		
		fStructureDiffViewer = new StructureDiffViewer(tree, config) {
			
			protected void inputChanged(Object in, Object oldInput) {
				super.inputChanged(in, oldInput);
				if (in != oldInput) {
					if (in instanceof ICompareInput) {
						TableLayout layout = new TableLayout();
						layout.addColumnData(new ColumnWeightData(1));

						ICompareInput newInput = (ICompareInput) in;
						
						leftColumn.setText(getCompareConfiguration().getLeftLabel(newInput.getLeft()));
						leftColumn.setToolTipText(leftColumn.getText());
						rightColumn.setText(getCompareConfiguration().getRightLabel(newInput.getRight()));
						rightColumn.setToolTipText(rightColumn.getText());

						layout.addColumnData(new ColumnWeightData(1));
						layout.addColumnData(new ColumnWeightData(1));
						getTree().setLayout(layout);
						getTree().layout();
					}

				}
			}

			@Override
			protected void diff(IProgressMonitor monitor) {
				super.diff(monitor);
				Display.getDefault().asyncExec(new Runnable() {
				
					public void run() {
						expandAll();
					}
				
				});
			}

		};
		fStructureDiffViewer.setStructureCreator(new ResourceStructureCreator(fStructureDiffViewer));
		fStructureDiffViewer.setLabelProvider(new ResourceDiffLabelProvider(fOverlayPreferenceStore));
		fStructureDiffViewer.setInput(node);
		
		return parent;
	}

	protected void createColorGroup(Composite parent) {
		
		Group composite = new Group(parent, SWT.NULL);
		composite.setText(CompareMessages.getString("ComparePreferencePage.Colors"));
		composite.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(data);
		
		fCompareTypesList = new List(composite, SWT.BORDER);
		fCompareTypesList.setSize(200, 200);
		fCompareTypesList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fCompareTypesList.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
		
			}
		
		});
		
		Composite group = new Composite(composite, SWT.NULL);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(groupData);
		group.setLayout(new GridLayout());

		Label colorLabel = new Label(group, SWT.NULL);
		colorLabel.setText(CompareMessages.getString("ComparePreferencePage.Colors"));
		
		fTextColorSelector = new ColorSelector(group);
		fTextColorSelector.setColorValue(new RGB(200,200,200));
		fTextColorSelector.addListener(new IPropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent event) {
				int index = fCompareTypesList.getSelectionIndex();
				if (index != -1) {
					RGB rgb = fTextColorSelector.getColorValue();
					fOverlayPreferenceStore.setValue(colorPreferences[index], StringConverter.asString(rgb)); //$NON-NLS-1$ //$NON-NLS-2$
				}
				updateStructurePreviewer();
			}
		
		});
		
		fCompareTypesList.setItems(colorPreferenceNames);
		fCompareTypesList.select(0);
		updateColorSelector();
	}

	protected void update() {
		updateColorSelector();
		updateStructurePreviewer();
		updateTextPreviewer();
		updateMergeTab();
	}

	private void updateMergeTab() {
		fAllowColumnMerge.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS));
		
	}

	private void updateTextPreviewer() {
		fSyncScrollingBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_SYNC_SCROLL_PROP));
		fConnectRangesBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_SINGLE_LINE_PROP));
		fHighlightChangesBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP));
		fShowAdditionalInfoBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_SHOW_INFO_PROP));
		fIgnoreWhitespaceBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP));
		
		fTextMergeViewer.invalidateTextPresentation();		
		fTextMergeViewer.refresh();		
	}

	private void updateStructurePreviewer() {
		fShowStructureCompareBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP));
		fShowTextCompareBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP));
		fStructureDiffViewer.refresh(true);
	}

	private void updateColorSelector() {
		int selectionIndex = fCompareTypesList.getSelectionIndex();
		String rgbValue = fOverlayPreferenceStore.getString(colorPreferences[selectionIndex]);
		RGB rgb = StringConverter.asRGB(rgbValue);
		fTextColorSelector.setColorValue(rgb);
	}

	private Control createPreviewer(Composite parent) {
		DiffNode node = new DiffNode(Differencer.CHANGE,
				null, getEMFResourceNode(CompareMessages.getString("ComparePreferencePage.PreviewContents1"), "sampleConcept.concept", "001"),
				getEMFResourceNode(CompareMessages.getString("ComparePreferencePage.PreviewContents2"), "sampleConcept.concept", "002"));

		TabFolder folder = new TabFolder(parent, SWT.NONE);
		folder.setLayout(new GridLayout());

		TabItem structureCompareTab = new TabItem(folder, SWT.NONE);
		structureCompareTab.setText(CompareMessages.getString("ComparePreferencePage.StructureCompare"));
		Composite structurePreviewViewer = createStructurePreviewerTab(folder, node);
		structureCompareTab.setControl(structurePreviewViewer);

		TabItem textCompareTab = new TabItem(folder, SWT.NONE);
		textCompareTab.setText(CompareMessages.getString("ComparePreferencePage.TextCompare"));
		Composite textPreviewViewer = createTextPreviewerTab(folder, node);
		textCompareTab.setControl(textPreviewViewer);
		
		TabItem mergeTab = new TabItem(folder, SWT.NONE);
		mergeTab.setText(CompareMessages.getString("ComparePreferencePage.Merge.title"));
		Composite mergeViewer = createMergeTab(folder);
		mergeTab.setControl(mergeViewer);
		
		return parent;
	}
	

	/**
	 * @param str
	 * @param name
	 * @param version
	 * @return
	 */
	private EMFResourceNode getEMFResourceNode(String str, String name, String version) {
		byte[] contents = str.getBytes();
		ResourceSet resourceSet = new ResourceSetImpl();
		InputStream is = new ByteArrayInputStream(contents); 
		EObject model = null;
		try {
			model = load(is, name, resourceSet);
		} catch (IOException e) {
			StudioUIPlugin.log(e);
		}
		EMFResourceNode	node = new EMFResourceNode(model, version, true, false);	
		node.setContent(contents);
		return node;
	}

	private Composite createMergeTab(Composite composite) {
		Composite parent = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		fAllowColumnMerge = new Button(parent, SWT.CHECK);
		fAllowColumnMerge.setText(CompareMessages.getString("ComparePreferencePage.Merge.allowColumnMerge"));
		fAllowColumnMerge.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS));
		fAllowColumnMerge.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fAllowColumnMerge.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS, true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS, false);
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});

		
		return parent;
	}

	private Composite createTextPreviewerTab(Composite composite, DiffNode node) {
		
		Composite parent = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		fSyncScrollingBtn = new Button(parent, SWT.CHECK);
		fSyncScrollingBtn.setText(CompareMessages.getString("ComparePreferencePage.SyncScrolling"));
		fSyncScrollingBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_SYNC_SCROLL_PROP));
		fSyncScrollingBtn.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fSyncScrollingBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_SYNC_SCROLL_PROP, true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_SYNC_SCROLL_PROP, false);
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});

		fConnectRangesBtn = new Button(parent, SWT.CHECK);
		fConnectRangesBtn.setText(CompareMessages.getString("ComparePreferencePage.SingleLine"));
		fConnectRangesBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_SINGLE_LINE_PROP));
		fConnectRangesBtn.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fConnectRangesBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_SINGLE_LINE_PROP, true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_SINGLE_LINE_PROP, false);
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});

		fHighlightChangesBtn = new Button(parent, SWT.CHECK);
		fHighlightChangesBtn.setText(CompareMessages.getString("ComparePreferencePage.HighlightChanges"));
		fHighlightChangesBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP));
		fHighlightChangesBtn.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fHighlightChangesBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP, true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP, false);
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});

		fShowAdditionalInfoBtn = new Button(parent, SWT.CHECK);
		fShowAdditionalInfoBtn.setText(CompareMessages.getString("ComparePreferencePage.ShowAdditionalInfo"));
		fShowAdditionalInfoBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_SHOW_INFO_PROP));
		fShowAdditionalInfoBtn.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				if (fShowAdditionalInfoBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_SHOW_INFO_PROP, true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_SHOW_INFO_PROP, false);
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});


		fIgnoreWhitespaceBtn = new Button(parent, SWT.CHECK);
		fIgnoreWhitespaceBtn.setText(CompareMessages.getString("ComparePreferencePage.IgnoreWhitespace"));
		fIgnoreWhitespaceBtn.setSelection(fOverlayPreferenceStore.getBoolean(ComparePreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP));
		fIgnoreWhitespaceBtn.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				if (fIgnoreWhitespaceBtn.getSelection()) {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP, true);
				} else {
					fOverlayPreferenceStore.setValue(ComparePreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP, false);
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		
		
		final CompareConfiguration compareConfiguration = new CompareConfiguration(fOverlayPreferenceStore);
		
		compareConfiguration.setLeftLabel("application.rulefunctionimpl");	//$NON-NLS-1$
		compareConfiguration.setLeftEditable(false);
		
		compareConfiguration.setRightLabel("application.rulefunctionimpl");	//$NON-NLS-1$
		compareConfiguration.setRightEditable(false);

		Label previewLabel = new Label(parent, SWT.NONE);
		previewLabel.setText(CompareMessages.getString("ComparePreferencePage.Preview"));
		
		fTextMergeViewer = new TextMergeViewer(parent, SWT.BORDER, compareConfiguration);
		Control c = fTextMergeViewer.getControl();
		c.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (compareConfiguration != null)
					compareConfiguration.dispose();
			}
		});
		fTextMergeViewer.setInput(node);
		c.setLayoutData(new GridData(GridData.FILL_BOTH));

		return parent;
	}

	@Override
	protected void performApply() {
		performOk();
		update();
	}

	@Override
	protected void performDefaults() {
		fOverlayPreferenceStore.performDefaults();
		super.performDefaults();
		update();
		setValid(true);
		setErrorMessage(null);
	}

	@Override
	public boolean performOk() {
		boolean b = super.performOk();
		if (b) {
			return fOverlayPreferenceStore.performOk();
		}
		return b;
	}

}
