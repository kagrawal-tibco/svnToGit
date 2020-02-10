package com.tibco.cep.bpmn.ui.properties;

import static com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil.refreshPalette;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editors.bpmnPalette.BpmnPaletteSelector;
import com.tibco.cep.bpmn.ui.utils.IgnoredSharedResourceFilter;
import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.NamePrefix;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.converter.sharedresource.SharedResourceElements;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.property.page.AbstractStudioPropertyPage;
import com.tibco.cep.studio.ui.views.FolderSelectionDialog;
import com.tibco.cep.studio.ui.views.TypedItemFilter;
import com.tibco.cep.studio.ui.views.TypedItemSelectionValidator;

/**
 * @author pdhar
 * 
 */
public class BpmnBuildPropertyPage extends AbstractStudioPropertyPage {
	private static IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(BpmnUIPlugin.PLUGIN_ID);
	private static final String DEFAULT_FOLDER = prefs.get(BpmnCoreConstants.PREF_CODEGEN_FOLDER, BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);

	private static final String DEFAULT_PROCESS_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX, BpmnCommonModelUtils.BPMN_PROCESS_PREFIX);

	private static final String DEFAULT_RULE_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX, BpmnCommonModelUtils.BPMN_RULE_PREFIX);

	private static final String DEFAULT_RULE_FUNCTION_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX,BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX);

	private static final String DEFAULT_CONCEPT_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX,BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX);

	private static final String DEFAULT_EVENT_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX,BpmnCommonModelUtils.BPMN_EVENT_PREFIX);

	private static final String DEFAULT_TIMEEVENT_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX,BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX);

	private static final String DEFAULT_SCORECARD_PREFIX = prefs.get(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX,BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX);

	private static final int TEXT_FIELD_WIDTH = 50;

	// code gen
	private Button browseButton;

	private CodegenWidgetListener codegenlistener;

	private TabItem codegenTab;

	private Text codegenFolderText;

	private Text processPrefixText;

	private Text rulePrefixText;

	private Text rulefunctionPrefixText;

	private Text conceptPrefixText;

	private Text eventPrefixText;

	private Text timeeventPrefixText;

	private Text scorecardPrefixText;

	// palette path
	private TabItem palettePathTab;

	private TreeViewer fPalettePathTreeViewer;

	private PalettePathWidgetListener palettePathlistener;

	private Button downButton;

	private Button upButton;

	private Button removeButton;

	private Button removeAllButton;

	private Button addButton;

	private Button addInternalButton;

	// id gen
	private TabItem idgenTab;

	private TableViewer fIdGenviewer;

	private IdGenWidgetListener idGenListener;

	// common
	private TabFolder parentFolder;

	//private boolean fChanged = false;

	/**
	 * Constructor for BpmnBuildPropertyPage.
	 */
	public BpmnBuildPropertyPage() {
		super(false);
		this.codegenlistener = new CodegenWidgetListener();
		this.palettePathlistener = new PalettePathWidgetListener();
		this.idGenListener = new IdGenWidgetListener();
	}

	@Override
	protected Control createContents(Composite parent) {
		IAdaptable element = getElement();
		if (element instanceof IProject) {
			IProject project = (IProject) element;
			if (!project.isOpen()) {
				noDefaultAndApplyButton();
			}
		}
		return super.createContents(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.studio.ui.property.page.AbstractStudioPropertyPage#
	 * createTabbedPages()
	 */
	protected void createTabbedPages(TabFolder parentFolder) {
		this.parentFolder = parentFolder;
		try {
//			this.codegenTab = createCodegenTab(parentFolder);
			this.palettePathTab = createPalettePathTab(parentFolder);
//			this.idgenTab = createIdGenTab(parentFolder);
			setValid(true);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}

	public TabFolder getParentFolder() {
		return parentFolder;
	}

	public TabItem getCodegenTab() {
		return codegenTab;
	}

	public TabItem getPalettePathTab() {
		return palettePathTab;
	}

	public TabItem getIdgenTab() {
		return idgenTab;
	}

	// //////////////////////////////////code gen tab
	// ////////////////////////////////////////////
	private TabItem createCodegenTab(TabFolder parentFolder) {
		TabItem tabItem = new TabItem(parentFolder, SWT.NONE);
		tabItem.setText(Messages.getString("bpmn.buildpath.tab.codegen.title"));//$NON-NLS-1$
		tabItem.setImage(StudioUIPlugin.getDefault().getImage("icons/customfunction_16x16.png")); //$NON-NLS-1$
		tabItem.setToolTipText(Messages.getString("bpmn.buildpath.tab.codegen.desc"));//$NON-NLS-1$
		Composite composite = new Composite(parentFolder, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		addSeparator(composite);
		addSecondSection(composite);
		tabItem.setControl(composite);
		return tabItem;
	}

	// wait for Update until Index Reference Update Job is finished
	public static void waitForUpdate() {
		IJobManager jobManager = Job.getJobManager();
		Job[] jobsOnProject = jobManager.find(IndexResourceChangeListener.UPDATE_INDEX_FAMILY);
		for (int i = 0; i < jobsOnProject.length; i++) {
			if (jobsOnProject[i] instanceof UpdateReferencedIndexJob) {
				UpdateReferencedIndexJob updateJob = (UpdateReferencedIndexJob) jobsOnProject[i];
				try {
					updateJob.join();
				} catch (InterruptedException e) {
					BpmnUIPlugin.log(e);
				}
			}
		}
	}

	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent, 2);

		// Label for path field
		Label pathLabel = new Label(composite, SWT.NONE);
		pathLabel.setText(Messages.getString("bpmn.buildpath.tab.codegen.project.label"));//$NON-NLS-1$

		// Path text field
		Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
		pathValueText.setText(((IResource) getElement()).getFullPath().toString().substring(1));
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addSecondSection(Composite parent) {
		Composite composite = createDefaultComposite(parent, 3);

		// Label for owner field
		Label folderNameLabel = new Label(composite, SWT.NONE);
		folderNameLabel.setText(Messages.getString("bpmn.buildpath.tab.codegen.folder.label"));//$NON-NLS-1$

		// codegen folder text field
		codegenFolderText = new Text(composite, SWT.SINGLE | SWT.BORDER);

		this.browseButton = new Button(composite, SWT.PUSH);
		this.browseButton.setText("&Browse");
		this.browseButton.addSelectionListener(codegenlistener);

		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		codegenFolderText.setLayoutData(gd);
		gd = new GridData();
		gd.horizontalSpan = 2;
		Label prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.process.prefix"));//$NON-NLS-1$
		this.processPrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		processPrefixText.setLayoutData(gd);

		prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.rule.prefix"));//$NON-NLS-1$
		this.rulePrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		rulePrefixText.setLayoutData(gd);

		prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.rulefunction.prefix"));//$NON-NLS-1$
		this.rulefunctionPrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		rulefunctionPrefixText.setLayoutData(gd);

		prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.concept.prefix"));//$NON-NLS-1$
		this.conceptPrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		conceptPrefixText.setLayoutData(gd);

		prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.event.prefix"));//$NON-NLS-1$
		this.eventPrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		eventPrefixText.setLayoutData(gd);

		prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.timeevent.prefix"));//$NON-NLS-1$
		this.timeeventPrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		timeeventPrefixText.setLayoutData(gd);

		prefixLabel = new Label(composite, SWT.NONE);
		prefixLabel.setText(Messages.getString("bpmn.core.codegen.scorecard.prefix"));//$NON-NLS-1$
		this.scorecardPrefixText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		scorecardPrefixText.setLayoutData(gd);

		IProject project = getProject();
		BpmnProcessSettings config = BpmnCorePlugin.getBpmnProjectConfiguration(project.getName());
		// Populate codegen folder text field
		try {
			String folder = config.getBuildFolder();
			// if (folder != null) {
			// String projectName =
			// BpmnIndexUtils.PATH_SEPARATOR+project.getName();
			// folder = folder.substring(projectName.length() + 1);
			// }
			codegenFolderText.setText((folder != null) ? folder : DEFAULT_FOLDER);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
			codegenFolderText.setText(DEFAULT_FOLDER);
		}
		if (config.getProcessPrefix() != null && !config.getProcessPrefix().isEmpty()) {
			processPrefixText.setText(config.getProcessPrefix());
		} else {
			processPrefixText.setText(DEFAULT_PROCESS_PREFIX);
		}
		if (config.getRulePrefix() != null && !config.getRulePrefix().isEmpty()) {
			rulePrefixText.setText(config.getRulePrefix());
		} else {
			rulePrefixText.setText(DEFAULT_RULE_PREFIX);
		}
		if (config.getRuleFunctionPrefix() != null && !config.getRuleFunctionPrefix().isEmpty()) {
			rulefunctionPrefixText.setText(config.getRuleFunctionPrefix());
		} else {
			rulefunctionPrefixText.setText(DEFAULT_RULE_FUNCTION_PREFIX);
		}
		if (config.getConceptPrefix() != null && !config.getConceptPrefix().isEmpty()) {
			conceptPrefixText.setText(config.getConceptPrefix());
		} else {
			conceptPrefixText.setText(DEFAULT_CONCEPT_PREFIX);
		}
		if (config.getEventPrefix() != null && !config.getEventPrefix().isEmpty()) {
			eventPrefixText.setText(config.getEventPrefix());
		} else {
			eventPrefixText.setText(DEFAULT_EVENT_PREFIX);
		}
		if (config.getTimeEventPrefix() != null && !config.getTimeEventPrefix().isEmpty()) {
			timeeventPrefixText.setText(config.getTimeEventPrefix());
		} else {
			timeeventPrefixText.setText(DEFAULT_TIMEEVENT_PREFIX);
		}
		if (config.getScorecardPrefix() != null && !config.getScorecardPrefix().isEmpty()) {
			scorecardPrefixText.setText(config.getScorecardPrefix());
		} else {
			scorecardPrefixText.setText(DEFAULT_SCORECARD_PREFIX);
		}

		// add listeners
		codegenFolderText.addModifyListener(codegenlistener);
		processPrefixText.addModifyListener(codegenlistener);
		rulePrefixText.addModifyListener(codegenlistener);
		rulefunctionPrefixText.addModifyListener(codegenlistener);
		conceptPrefixText.addModifyListener(codegenlistener);
		eventPrefixText.addModifyListener(codegenlistener);
		timeeventPrefixText.addModifyListener(codegenlistener);
		scorecardPrefixText.addModifyListener(codegenlistener);
	}

	private Composite createDefaultComposite(Composite parent, int columns) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = columns;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	class CodegenWidgetListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			final Object source = e.getSource();
//			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final BpmnProcessSettings config = getViewerInput();
			if (source == codegenFolderText) {
				IFolder initSelection = null;
				String codegenPath = null;
				final IProject project = getProject();
				if (codegenFolderText.getText() != null) {
					codegenPath = codegenFolderText.getText();
				}
				if (codegenPath != null) {
					initSelection = project.getFolder(codegenPath);
				}
				if (initSelection == null) {
					setErrorMessage(Messages.getString("bpmn.buildpath.tab.codegen.folder.not.exist", codegenFolderText.getText())); //$NON-NLS-1$
					setValid(false);
				} else {
					setErrorMessage(null);
					setValid(true);
					config.setBuildFolder(codegenPath);
				}
				//fChanged = true;
			} else if (source == processPrefixText) {
				if (!processPrefixText.getText().isEmpty()) {
					config.setProcessPrefix(processPrefixText.getText().trim());
				}
			} else if (source == rulePrefixText) {
				if (!rulePrefixText.getText().isEmpty()) {
					config.setRulePrefix(rulePrefixText.getText().trim());
				}
			} else if (source == rulefunctionPrefixText) {
				if (!rulefunctionPrefixText.getText().isEmpty()) {
					config.setRuleFunctionPrefix(rulefunctionPrefixText.getText().trim());
				}
			} else if (source == conceptPrefixText) {
				if (!conceptPrefixText.getText().isEmpty()) {
					config.setConceptPrefix(conceptPrefixText.getText().trim());
				}
			} else if (source == eventPrefixText) {
				if (!eventPrefixText.getText().isEmpty()) {
					config.setEventPrefix(eventPrefixText.getText().trim());
				}
			} else if (source == timeeventPrefixText) {
				if (!timeeventPrefixText.getText().isEmpty()) {
					config.setTimeEventPrefix(timeeventPrefixText.getText().trim());
				}
			} else if (source == scorecardPrefixText) {
				if (!scorecardPrefixText.getText().isEmpty()) {
					config.setScorecardPrefix(scorecardPrefixText.getText().trim());
				}
			}

		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.getSource() == browseButton) {
//				final IWorkspace workspace = ResourcesPlugin.getWorkspace();
				final IProject project = getProject();
				Class<?>[] acceptedClasses = new Class<?>[] { IProject.class, IFolder.class };
				ISelectionStatusValidator validator = new TypedItemSelectionValidator(acceptedClasses, false);
				List<IResource> rejectedElements = new ArrayList<IResource>();
				rejectedElements.add(project.findMember(SharedResourceElements.GLOBAL_VARIABLE_DEFAULT_FOLDER));
				ViewerFilter filter = new TypedItemFilter(acceptedClasses, rejectedElements.toArray());
				ILabelProvider lp = new WorkbenchLabelProvider();
				ITreeContentProvider cp = new WorkbenchContentProvider();
				IResource initSelection = null;
				if (codegenFolderText.getText() != null) {
					initSelection = project.findMember(codegenFolderText.getText());
				}
				FolderSelectionDialog dialog = new FolderSelectionDialog(getControl().getShell(), lp, cp);
				dialog.setTitle(Messages.getString("bpmn.buildpath.tab.sourcepath.folderdialog")); //$NON-NLS-1$
				dialog.setValidator(validator);
				dialog.setMessage(Messages.getString("bpmn.buildpath.tab.sourcepath.foldermsg"));//$NON-NLS-1$ 
				dialog.addFilter(filter);
				dialog.addFilter(new IgnoredSharedResourceFilter());
				dialog.setInput(project);
				dialog.setInitialSelection(initSelection);
				dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));

				if (dialog.open() == Window.OK) {
					IContainer result = (IContainer) dialog.getFirstResult();
					codegenFolderText.setText(result.getFullPath().removeFirstSegments(1).makeAbsolute().toPortableString());
				}
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////palette path tab
	// ////////////////////////////////////////////
	private TabItem createPalettePathTab(TabFolder parent) {
		TabItem tabItem = new TabItem(parent, SWT.NONE);
		tabItem.setText(Messages.getString("bpmn.buildpath.tab.palettepath.title"));//$NON-NLS-1$
		tabItem.setImage(StudioUIPlugin.getDefault().getImage("icons/customfunction_16x16.png")); //$NON-NLS-1$
		tabItem.setToolTipText(Messages.getString("bpmn.buildpath.tab.palettepath.desc"));//$NON-NLS-1$
		Composite control = new Composite(parent, SWT.WRAP);

		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		control.setLayout(layout);
		layout.numColumns = 2;

		Label desriptionLabel = new Label(control, SWT.WRAP);
		desriptionLabel.setText(Messages.getString("bpmn.buildpath.tab.palettepath.desc")); //$NON-NLS-1$
		desriptionLabel = new Label(control, SWT.WRAP);

		final BpmnProcessSettings input = getViewerInput();

		ITreeContentProvider contp = new PalettePathTreeContentProvider();
		IBaseLabelProvider labelProvider = new PalettePathTreeLabelProvider();
		fPalettePathTreeViewer = createTreeViewer(control, contp, labelProvider, SWT.BORDER | SWT.MULTI);
		if (input != null) {
			fPalettePathTreeViewer.setInput(input);
		}
		fPalettePathTreeViewer.addSelectionChangedListener(this.palettePathlistener);

		final Composite buttonComposite = new Composite(control, SWT.RIGHT);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		layout = new GridLayout();
		buttonComposite.setLayout(layout);
		layout.numColumns = 1;

		this.addInternalButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addInternalButton.setText(Messages.getString("bpmn.buildpath.tab.palettepath.addInternalButton")); //$NON-NLS-1$
		addInternalButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		addInternalButton.addSelectionListener(this.palettePathlistener);

		this.addButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		addButton.setText(Messages.getString("bpmn.buildpath.tab.palettepath.addButton")); //$NON-NLS-1$
		addButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		addButton.addSelectionListener(this.palettePathlistener);

		this.removeButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		removeButton.setText(Messages.getString("bpmn.buildpath.tab.palettepath.removeButton")); //$NON-NLS-1$
		removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		removeButton.setEnabled(input.getPalettePathEntries().size() > 0 && !fPalettePathTreeViewer.getSelection().isEmpty());
		removeButton.addSelectionListener(this.palettePathlistener);

		this.removeAllButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		removeAllButton.setText(Messages.getString("bpmn.buildpath.tab.palettepath.removeAllButton")); //$NON-NLS-1$
		removeAllButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		removeAllButton.setEnabled(input.getPalettePathEntries().size() > 0);
		removeAllButton.addSelectionListener(this.palettePathlistener);

		this.upButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		upButton.setText(Messages.getString("bpmn.buildpath.tab.palettepath.upButton")); //$NON-NLS-1$
		upButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		upButton.setEnabled(!fPalettePathTreeViewer.getSelection().isEmpty());
		upButton.addSelectionListener(this.palettePathlistener);

		this.downButton = new Button(buttonComposite, SWT.PUSH | SWT.CENTER);
		downButton.setText(Messages.getString("bpmn.buildpath.tab.palettepath.downButton")); //$NON-NLS-1$
		downButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
		downButton.setEnabled(!fPalettePathTreeViewer.getSelection().isEmpty());
		downButton.addSelectionListener(this.palettePathlistener);

		tabItem.setControl(control);
		return tabItem;
	}

	class PalettePathWidgetListener extends SelectionAdapter implements ISelectionChangedListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			final BpmnProcessSettings input = getViewerInput();

			Object source = e.getSource();
			if (source == addInternalButton) {

				BpmnPaletteSelector picker = new BpmnPaletteSelector(Display.getDefault().getActiveShell(), getProject());
				if (picker.open() == Dialog.OK) {
					if (picker.getFirstResult() != null) {
						addPalettePathLocation((picker.getFirstResult().toString()).substring(2));
					}
					//fChanged = true;
				}
				
				if (getErrorMessage() == null) {
					setValid(true);
				}
				
			} else if (source == addButton) {
				FileDialog fd = new FileDialog(addButton.getShell(), SWT.OPEN | SWT.MULTI);
				fd.setFilterExtensions(new String[] { "*." + BpmnCoreConstants.PALETTE_FILE_EXTN });
				String externalJarPath = fd.open();
				String[] jarPath={};
				if(externalJarPath!=null){ 
					jarPath = fd.getFileNames();
					} 
				
				//String[] jarPath = fd.getFileNames();
				for (String string : jarPath) {
					File f = new File(fd.getFilterPath() + File.separator + string);
					if (!f.isDirectory()) {
						addPalettePathLocation(fd.getFilterPath() + File.separator + string);
					} else {
						File[] listFiles = f.listFiles(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith("." + BpmnCoreConstants.PALETTE_FILE_EXTN);
							}
						});
						for (File file : listFiles) {
							addPalettePathLocation(file.getPath());
						}
					}
					//fChanged = true;
				}
				if (getErrorMessage() == null) {
					setValid(true);
				}
				//fChanged = true;
			} else if (source == removeButton) {
				ISelection selection = fPalettePathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					Object[] selectedElements = ((StructuredSelection) selection).toArray();
					for (Object element : selectedElements) {
						removePalettePathEntry(element);
					}
					//fChanged = true;
					if (getErrorMessage() == null) {
						setValid(true);
					}
					// added check to disable/enable Remove button
					removeButton.setEnabled(input.getPalettePathEntries().size() > 0 && !fPalettePathTreeViewer.getSelection().isEmpty());
					removeAllButton.setEnabled(input.getPalettePathEntries().size() > 0);
				}
			} else if (source == removeAllButton) {
				input.getPalettePathEntries().clear();
				removeAllButton.setEnabled(input.getPalettePathEntries().size() > 0);
				refreshTreeViewer(fPalettePathTreeViewer);
				//fChanged = true;
				
			} else if (source == upButton) {
				ISelection selection = fPalettePathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					if (((StructuredSelection) selection).size() < 1) {
						upButton.setEnabled(false);
						downButton.setEnabled(false);
						return;
					}
					if (((StructuredSelection) selection).size() > 1) {
						upButton.setEnabled(false);
						downButton.setEnabled(false);
						return;
					} else {
						Object element = ((StructuredSelection) selection).getFirstElement();
						if (element instanceof BpmnPalettePathEntry) {
//							BuildPathEntry changedEntry = (BuildPathEntry) element;

							if (element != null) {
								final int oldPosition = input.getPalettePathEntries().indexOf(element);
								if (oldPosition > 0) {
									input.getPalettePathEntries().move(oldPosition - 1, oldPosition);
									refreshTreeViewer(fPalettePathTreeViewer);
								}
							}
						}

					}
				}
			} else if (source == downButton) {
				ISelection selection = fPalettePathTreeViewer.getSelection();
				if (selection instanceof StructuredSelection) {
					if (((StructuredSelection) selection).size() < 1) {
						upButton.setEnabled(false);
						downButton.setEnabled(false);
						return;
					}
					if (((StructuredSelection) selection).size() > 1) {
						upButton.setEnabled(false);
						downButton.setEnabled(false);
						return;
					} else {
						Object element = ((StructuredSelection) selection).getFirstElement();
						if (element instanceof BpmnPalettePathEntry) {
//							BuildPathEntry changedEntry = (BuildPathEntry) element;

							if (element != null) {
								final int oldPosition = input.getPalettePathEntries().indexOf(element);
								final int size = input.getPalettePathEntries().size();
								if (oldPosition < (size - 1)) {
									input.getPalettePathEntries().move(oldPosition + 1, oldPosition);
									refreshTreeViewer(fPalettePathTreeViewer);
								}
							}
						}

					}
				}
			}
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			Object source = event.getSource();
			if (source == fPalettePathTreeViewer) {
				final BpmnProcessSettings input = getViewerInput();
				ISelection selection = event.getSelection();
				if (selection instanceof StructuredSelection) {

					int size = ((StructuredSelection) selection).size();
					if (size > 0) {
						if (size == 1) {
							StructuredSelection s = (StructuredSelection) selection;
							Object element = s.getFirstElement();
							if (element instanceof BpmnPalettePathEntry) {
								final int index = input.getPalettePathEntries().indexOf(element);
								final int numEntries = input.getPalettePathEntries().size();
								if (index <= (numEntries - 1) && index >= 0) {
									if (index == (numEntries - 1)) {
										upButton.setEnabled(true);
										downButton.setEnabled(false);
									} else if (index == 0) {
										downButton.setEnabled(true);
										upButton.setEnabled(false);
									} else {
										downButton.setEnabled(true);
										upButton.setEnabled(true);
									}
									removeButton.setEnabled(true);
								}
								if (index == 0 && index == (numEntries - 1)) {
									upButton.setEnabled(false);
									downButton.setEnabled(false);
								}
								// upButton.setEnabled(true);
								// removeButton.setEnabled(true);
							} else {
								upButton.setEnabled(false);
								downButton.setEnabled(false);
								removeButton.setEnabled(false);
							}
						} else {
							upButton.setEnabled(false);
							downButton.setEnabled(false);
							// addButton.setEnabled(false);
							// allowing selection size more than 1
							removeButton.setEnabled(true);
						}
					}
				}
			}

		}
	}

	protected BpmnProcessSettings getViewerInput() {
		IProject project = getProject();
		BpmnProcessSettings config = BpmnCorePlugin.getBpmnProjectConfiguration(project.getName());
		return config;
	}

	private void addPalettePathLocation(String palettePath) {
		BpmnProcessSettings configuration = getViewerInput();
		Iterator<BpmnPalettePathEntry> iterator = configuration.getPalettePathEntries().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getPath().equals(palettePath)) {
				// don't add duplicate entries
				return;
			}
		}
		BpmnPalettePathEntry entry = ConfigurationFactory.eINSTANCE.createBpmnPalettePathEntry();
		entry.setEntryType(LIBRARY_PATH_TYPE.BPMN_PALETTE_LIBRARY);
		entry.setPath(palettePath);
		File file = new File(palettePath);
		entry.setTimestamp(file.lastModified());

		configuration.getPalettePathEntries().add(entry);
		if(configuration != null)
		this.removeAllButton.setEnabled(configuration.getPalettePathEntries().size() > 0);
		refreshTreeViewer(fPalettePathTreeViewer);
	}

	protected void removePalettePathEntry(Object element) {
		if (!(element instanceof BuildPathEntry)) {
			return;
		}
		BuildPathEntry removedEntry = (BuildPathEntry) element;
		BpmnProcessSettings configuration = getViewerInput();

		if (removedEntry != null) {
			configuration.getPalettePathEntries().remove(removedEntry);
		}
		refreshTreeViewer(fPalettePathTreeViewer);
		//fChanged = true;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	// ////////////////////////////////// id gen tab
	// ////////////////////////////////////////////

	private TabItem createIdGenTab(TabFolder parent) {
		TabItem tabItem = new TabItem(parent, SWT.NONE);
		tabItem.setText(Messages.getString("bpmn.buildpath.tab.idgen.title"));//$NON-NLS-1$
		tabItem.setImage(StudioUIPlugin.getDefault().getImage("icons/customfunction_16x16.png")); //$NON-NLS-1$
		tabItem.setToolTipText(Messages.getString("bpmn.buildpath.tab.idgen.desc"));//$NON-NLS-1$
		Composite control = createComposite(parent, 1, 1, GridData.FILL_BOTH);

		Label desriptionLabel = new Label(control, SWT.WRAP);
		desriptionLabel.setText(Messages.getString("bpmn.buildpath.tab.idgen.desc")); //$NON-NLS-1$
		desriptionLabel = new Label(control, SWT.WRAP);

		final BpmnProcessSettings input = getViewerInput();

		createViewer(control);
		Set<NamePrefix> namePrefixMap = fillIdGenList(input);
		this.fIdGenviewer.setInput(namePrefixMap);
		fIdGenviewer.addSelectionChangedListener(this.idGenListener);

		tabItem.setControl(control);
		setValid(true);

		return tabItem;
	}

	private Set<NamePrefix> fillIdGenList(BpmnProcessSettings input) {
		Collection<EClass> allClasses = BpmnMetaModel.getAllClasses();
		Set<NamePrefix> namePrefixMap = new HashSet<NamePrefix>();
		EClass[] classFilter = new EClass[] { BpmnModelClass.FLOW_ELEMENT, BpmnModelClass.CHOREGRAPHY_SUB_PROCESS, BpmnModelClass.BOUNDARY_EVENT,
				BpmnModelClass.AD_HOC_SUB_PROCESS, BpmnModelClass.CHOREOGRAPHY_TASK, BpmnModelClass.TRANSACTION, BpmnModelClass.IMPLICIT_THROW_EVENT,
				BpmnModelClass.TASK, BpmnModelClass.DATA_OBJECT, BpmnModelClass.CALL_CHOREOGRAPHY_ACTIVITY, BpmnModelClass.SCRIPT_TASK,
				BpmnModelClass.USER_TASK, BpmnModelClass.DATA_STORE_REFERENCE, BpmnModelClass.CHOREOGRAPHY_ACTIVITY,

		};
		for (EClass type : allClasses) {
			if (Arrays.asList(classFilter).contains(type) || !BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(type) || type.isAbstract())
				continue;
			if (input != null) {
//				String name = type.getName();
				NamePrefix prefix = null;
				if (input.getNamePrefixMap().containsKey(type.getName())) {
					prefix = input.getNamePrefixMap().get(type.getName());
				} else {
					String pfx = ECoreHelper.toTitleCase(type.getName()).replace(' ', '_');
					prefix = ConfigurationFactory.eINSTANCE.createNamePrefix();
					prefix.setName(type.getName());
					prefix.setPrefix(pfx);
					input.getNamePrefixes().add(prefix);
				}
				namePrefixMap.add(prefix);
			}
		}
		return namePrefixMap;
	}

	public static Composite createComposite(Composite parent, int columns, int hspan, int fill) {
		Composite g = new Composite(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	private void createViewer(Composite parent) {
		this.fIdGenviewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(this.fIdGenviewer);
		this.fIdGenviewer.setContentProvider(new ArrayContentProvider());
		this.fIdGenviewer.setLabelProvider(new TypeNameLabelProvider());
	}

	// This will create the columns for the table
	private void createColumns(TableViewer viewer) {

		String[] titles = { "Type", "Name Prefix" };
		int[] bounds = { 200, 200 };

		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.setEditingSupport(new PrefixEditingSupport(viewer, i));

		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

//	public class TypeNameContentProvider extends ArrayContentProvider {
//		@Override
//		public Object[] getElements(Object inputElement) {
//			return super.getElements(inputElement);
//		}
//	}

	public class TypeNameLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof NamePrefix) {
				NamePrefix entry = (NamePrefix) element;
				switch (columnIndex) {
				case 0:
					return entry.getName();
				case 1:
					return entry.getPrefix();
				}
			}
			return null;
		}

	}

	public class PrefixCellEditorValidator implements ICellEditorValidator {
		@SuppressWarnings("unused")
		private ColumnViewer columnViewer;
		@SuppressWarnings("unused")
		private int column;

		public PrefixCellEditorValidator(ColumnViewer viewer, int column) {
			this.columnViewer = viewer;
			this.column = column;
		}

		@Override
		public String isValid(Object value) {
			String regex = "[\\-_a-zA-Z0-9]+";
			Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(String.valueOf(value));
			if (m.matches()) {
				return null;
			} else {
				return Messages.getString("bpmn.buildpath.tab.idgen.prefixFormat");
			}
		}
	}

	public class PrefixEditingSupport extends EditingSupport {
		private CellEditor editor;
		private int column;

		public PrefixEditingSupport(ColumnViewer viewer, int column) {
			super(viewer);

			// Create the correct editor based on the column index
			switch (column) {
			case 1:
				editor = new TextCellEditor(((TableViewer) viewer).getTable());
				// PrefixCellEditorValidator listener = new
				// PrefixCellEditorValidator(viewer,column);
				// editor.setValidator(listener);
				break;
			default:
				editor = null;
			}
			this.column = column;
		}

		@Override
		protected boolean canEdit(Object element) {
			switch (column) {
			case 1:
				return true;
			default:
				return false;
			}
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof NamePrefix) {
				NamePrefix entry = (NamePrefix) element;
				switch (this.column) {
				case 0:
					return entry.getName();
				case 1:
					return entry.getPrefix();
				default:
					break;
				}
			}

			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof NamePrefix) {
				NamePrefix entry = (NamePrefix) element;
				switch (this.column) {
				case 1:
					entry.setPrefix(String.valueOf(value));
					if (isValid(value)) {
						setErrorMessage(null);
						setValid(true);
					} else {
						setErrorMessage(Messages.getString("bpmn.buildpath.tab.idgen.prefixFormat"));
						setValid(false);
					}
					//fChanged = true;
					break;
				default:
					break;
				}
			}
			getViewer().update(element, null);
		}

		public boolean isValid(Object value) {
			String regex = "[\\-_a-zA-Z0-9]+";
			Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(String.valueOf(value));
			if (m.matches()) {
				return true;
			} else {
				return false;
			}
		}

	}

	class IdGenWidgetListener extends SelectionAdapter implements ISelectionChangedListener {
		@SuppressWarnings("unused")
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			Object source = event.getSource();
			if (source == fIdGenviewer) {
				final BpmnProcessSettings input = getViewerInput();
				ISelection selection = event.getSelection();
				if (selection instanceof StructuredSelection) {

					int size = ((StructuredSelection) selection).size();
					if (size > 0) {
						if (size == 1) {
							StructuredSelection s = (StructuredSelection) selection;
							Object element = s.getFirstElement();

						} else {

						}
					}
				}
			}

		}

		@SuppressWarnings("unused")
		@Override
		public void widgetSelected(SelectionEvent e) {
			final BpmnProcessSettings input = getViewerInput();
			Object source = e.getSource();
			if (source == fIdGenviewer) {

			}
		}

	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	// Wait for UpdateReferencedIndexJob to finish
	private static void refreshTreeViewer(TreeViewer treeViewer) {
		if (treeViewer == null)
			return;
		waitForUpdate();
		treeViewer.refresh();
	}

	/**
	 * Create tab's tree viewer.
	 * 
	 * @param parent
	 * @param treeContentProvider
	 * @param treeLabelProvider
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite parent, ITreeContentProvider treeContentProvider, IBaseLabelProvider treeLabelProvider) {
		return createTreeViewer(parent, treeContentProvider, treeLabelProvider, SWT.BORDER);
	}

	/**
	 * Create tab's tree viewer.
	 * 
	 * @param parent
	 * @param treeContentProvider
	 * @param treeLabelProvider
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite parent, ITreeContentProvider treeContentProvider, IBaseLabelProvider treeLabelProvider, int style) {
		Tree tree = new Tree(parent, style);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.verticalSpan = 2;
		tree.setLayoutData(data);
		tree.setFont(parent.getFont());

		TreeViewer treeViewer = new TreeViewer(tree);
		if (treeContentProvider != null) {
			treeViewer.setContentProvider(treeContentProvider);
		}
		if (treeLabelProvider != null) {
			treeViewer.setLabelProvider(treeLabelProvider);
		}
		return treeViewer;
	}

	@Override
	public boolean okToLeave() {
		//return super.okToLeave() && fChanged == false && isValid();
		return super.okToLeave() && isValid();
	}

	@Override
	public void applyData(Object data) {
		performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		int index = getParentFolder().getSelectionIndex();
		if (index != -1) {
			TabItem selection = getParentFolder().getItem(index);
			if (selection == this.codegenTab) {
				// Populate the owner text field with the default value
				codegenFolderText.setText(DEFAULT_FOLDER);
				processPrefixText.setText(DEFAULT_PROCESS_PREFIX);
				rulePrefixText.setText(DEFAULT_RULE_PREFIX);
				rulefunctionPrefixText.setText(DEFAULT_RULE_FUNCTION_PREFIX);
				conceptPrefixText.setText(DEFAULT_CONCEPT_PREFIX);
				eventPrefixText.setText(DEFAULT_EVENT_PREFIX);
				timeeventPrefixText.setText(DEFAULT_TIMEEVENT_PREFIX);
				scorecardPrefixText.setText(DEFAULT_SCORECARD_PREFIX);
			} else if (selection == this.palettePathTab) {

			} else if (selection == this.idgenTab) {
				Set<NamePrefix> namePrefixMap = fillIdGenList(getViewerInput());
				this.fIdGenviewer.setInput(namePrefixMap);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performCancel()
	 */
	@Override
	public boolean performCancel() {
		final IProject project = getProject();
		if (!project.isOpen())
			return true;

		StudioProjectConfigurationManager.getInstance().reload();
		//fChanged = false;
		return super.performCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		// store the value in the owner text field
		try {
			// ((IResource) getElement()).setPersistentProperty(
			// new QualifiedName("", OWNER_PROPERTY),
			// codegenFolderText.getText());

			final IProject project = getProject();
			if (!project.isOpen())
				return true;

			BpmnProcessSettings configuration = getViewerInput();

			/*if (!fChanged) {
				return true;
			}*/
			try {
				checkConfiguration(configuration);
				BpmnCorePlugin.saveBpmnProjectConfiguration(project.getName());
			} catch (Exception e) {
				StudioUIPlugin.errorDialog(getShell(), Messages.getString("bpmn.buildpath.tab.palettepath.title"),
						Messages.getString("bpmn.buildpath.tab.palettepath.save.error"), e);

				return false;
			}

			if (MessageDialog.openQuestion(getShell(), Messages.getString("bpmn.buildpath.tab.palettepath.rebuild.title"),
					Messages.getString("bpmn.buildpath.tab.palettepath.rebuild"))) {
				Job buildJob = new Job("Build project") {

					@Override
					protected IStatus run(final IProgressMonitor monitor) {
						try {
							project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
						} catch (CoreException e) {
							BpmnUIPlugin.log(e);
						}
						return Status.OK_STATUS;
					}
				};
				buildJob.schedule();
			}
			//fChanged = false;
			refreshPalette();
			return true;
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
			return false;
		}
	}

	private void checkConfiguration(BpmnProcessSettings configuration) {
	}

	/**
	 * get Project
	 * 
	 * @return
	 */
	public IProject getProject() {
		return ((IResource) getElement()).getProject();
	}

	@Override
	protected void createXPathField(Composite parent) {
		// TODO Auto-generated method stub
		
	}
}