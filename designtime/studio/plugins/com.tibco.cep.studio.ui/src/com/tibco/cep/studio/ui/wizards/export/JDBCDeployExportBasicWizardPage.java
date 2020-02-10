package com.tibco.cep.studio.ui.wizards.export;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.ResourceSelector;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class JDBCDeployExportBasicWizardPage extends WizardPage {

	private static final String CDD_FILE_EXTENSION = "cdd"; //$NON-NLS-1$

//	private static final String BASE_TYPE_FILE_LOCATION = "jdbc_export_basetype_location"; //$NON-NLS-1$

	private static final String JDBC_EXPORT_OUTPUT_LOCATION = "jdbc_export_output_location"; //$NON-NLS-1$

//	private static final String DEFAULT_BASE_TYPE_FILE_LOCATION = StudioProjectConfigurationManager.getInstance().getResolvedVariablePath(new Path("BE_HOME/bin/base_types.xml"), false).toString(); //$NON-NLS-1$

	private IProject project;

	private Collection<String> supportedDataBaseTypes;

	// -database [jdbc.deploy.database.type]
	private Combo cDatabaseType;
	private String dataBaseType;

	// -ansi
	private Button bAnsi;
	private boolean ansi;

	// -optimize
	private Button bOptimize;
	private boolean optimize;
	
	// -expandMaxStringSize
	private Button bExpandMaxStringSize;
	private boolean expandMaxStringSize;


	// -cdd [com.tibco.be.config.path]
	private Text tCddLocation;
	private Button bCddBrowse;
	private String cddPath;

	// prefixed to output field name
	private Text tOutputLocation;
	private Button bOutputLocationBrowse;
	private String outputDirectory;

	// -o [jdbcdeploy.schema.output.file]
	private Text tOuputFileName;
	private String outputFileName;

	// -schema [jdbcdeploy.bootstrap.basetype.file]
//	private Text tBaseTypeFileName;
//	private String baseTypeFileName;
//	private Button bBaseTypeFileBrowse;

	private WidgetListener widgetListener = null;

	protected JDBCDeployExportBasicWizardPage(IProject project, Collection<String> supportedDataBaseTypes) {
		super(JDBCDeployExportBasicWizardPage.class.getSimpleName());
		this.project = project;
		this.widgetListener = new WidgetListener();
		setTitle(Messages.getString("jdbc.scripts.export.wizard.basicpage.title")); //$NON-NLS-1$
		setMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.description"));//$NON-NLS-1$
		this.supportedDataBaseTypes = supportedDataBaseTypes;
	}

	@Override
	public void createControl(Composite parent) {
		Composite parentComposite = new Composite(parent, SWT.NULL);
		parentComposite.setLayout(new GridLayout(3, false));
		parentComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// database type
		PanelUiUtil.createLabel(parentComposite, "Database Type :").setToolTipText("-database"); //$NON-NLS-1$
		cDatabaseType = PanelUiUtil.createComboBox(parentComposite, supportedDataBaseTypes.toArray(new String[supportedDataBaseTypes.size()]), SWT.READ_ONLY | SWT.SINGLE);
		cDatabaseType.setLayoutData(PanelUiUtil.getGridData(2));
		cDatabaseType.addSelectionListener(this.widgetListener);

		// ansi control
		PanelUiUtil.createLabel(parentComposite, "Generate ANSI Scripts :").setToolTipText("-ansi"); //$NON-NLS-1$
		bAnsi = PanelUiUtil.createCheckBox(parentComposite, "");
		bAnsi.addSelectionListener(this.widgetListener);
		PanelUiUtil.createLabelFiller(parentComposite);

		// optimize
		PanelUiUtil.createLabel(parentComposite, "Generate Optimized Scripts :").setToolTipText("-optimize"); //$NON-NLS-1$
		bOptimize = PanelUiUtil.createCheckBox(parentComposite, "");
		bOptimize.addSelectionListener(this.widgetListener);
		PanelUiUtil.createLabelFiller(parentComposite);
		
		// expand max string size
		PanelUiUtil.createLabel(parentComposite, "Use Extended Max String Size :").setToolTipText("-maxstringsize"); //$NON-NLS-1$
		bExpandMaxStringSize = PanelUiUtil.createCheckBox(parentComposite, "");
		bExpandMaxStringSize.addSelectionListener(this.widgetListener);
		PanelUiUtil.createLabelFiller(parentComposite);

		// cdd control
		PanelUiUtil.createLabel(parentComposite, "Cluster Deployment Descriptor :").setToolTipText("-c"); //$NON-NLS-1$
		Composite comp = PanelUiUtil.getTextButtonComposite(parentComposite, 2, 2);
		tCddLocation = PanelUiUtil.createTextSpan(comp, 1);
		tCddLocation.addModifyListener(this.widgetListener);
		bCddBrowse = PanelUiUtil.createBrowsePushButton(comp, tCddLocation);
		bCddBrowse.addSelectionListener(this.widgetListener);

		// output folder control
		PanelUiUtil.createLabel(parentComposite, "Output Directory :"); //$NON-NLS-1$
		comp = PanelUiUtil.getTextButtonComposite(parentComposite, 2, 2);
		tOutputLocation = PanelUiUtil.createTextSpan(comp, 1);
		tOutputLocation.addModifyListener(this.widgetListener);
		bOutputLocationBrowse = PanelUiUtil.createBrowsePushButton(comp, tOutputLocation);
		bOutputLocationBrowse.addSelectionListener(this.widgetListener);

		// output file name
		PanelUiUtil.createLabel(parentComposite, "Output File Name :").setToolTipText("-o"); //$NON-NLS-1$
		tOuputFileName = PanelUiUtil.createTextSpan(parentComposite, 2);
		tOuputFileName.addModifyListener(this.widgetListener);

		// base type filed name
//		PanelUiUtil.createLabel(parentComposite, "Base Types :").setToolTipText("-schema"); //$NON-NLS-1$
//		comp = PanelUiUtil.getTextButtonComposite(parentComposite, 2, 2);
//		tBaseTypeFileName = PanelUiUtil.createTextSpan(comp, 1);
//		tBaseTypeFileName.addModifyListener(this.widgetListener);
//		bBaseTypeFileBrowse = PanelUiUtil.createBrowsePushButton(comp, tBaseTypeFileName);
//		bBaseTypeFileBrowse.addSelectionListener(this.widgetListener);

		setControl(parentComposite);

		// set defaults
		cDatabaseType.select(0);
		bAnsi.setSelection(true);
		String outputLoc = getDialogSettings().get(JDBC_EXPORT_OUTPUT_LOCATION);
		tOutputLocation.setText(outputLoc == null ? "" : outputLoc);
//		String baseTypeFileLoc = getDialogSettings().get(BASE_TYPE_FILE_LOCATION);
//		tBaseTypeFileName.setText(baseTypeFileLoc == null ? DEFAULT_BASE_TYPE_FILE_LOCATION : baseTypeFileLoc);
//		List<IResource> cdds = new LinkedList<IResource>();
//		findCDDs(project, cdds);
//		if (cdds.size() == 1) {
//			tCddLocation.setText(cdds.get(0).getProjectRelativePath().toString());
//		}
	}

	@SuppressWarnings("unused")
	private void findCDDs(IContainer container, List<IResource> cdds) {
		try {
			IResource[] members = container.members();
			for (IResource member : members) {
				if (CDD_FILE_EXTENSION.equals(member.getFileExtension()) == true) {
					cdds.add(member);
				}
				if (member instanceof IContainer) {
					findCDDs((IContainer) member, cdds);
				}
			}
		} catch (CoreException e) {
			MultiStatus status = new MultiStatus(StudioUIPlugin.PLUGIN_ID, IStatus.ERROR, "could not search for cdd's in "+project.getName(),null); //$NON-NLS-1$
			status.add(e.getStatus());
			StudioUIPlugin.log(status);
		}
	}

	private void handleWidgetSelection(final Object source) {
		if (source == bCddBrowse) {
			ResourceSelector fileSelector = new ResourceSelector(getShell());
			fileSelector.setTitle("Cluster Deployment Descriptor Selector"); //$NON-NLS-1$
			fileSelector.setMessage("Select a cluster deployment descriptor"); //$NON-NLS-1$
			fileSelector.setAllowMultiple(false);
			fileSelector.setEmptyListMessage("No Cluster Deployment Descriptor found"); //$NON-NLS-1$
			fileSelector.setInput(project);
			fileSelector.setInitialSelection(project.findMember(tCddLocation.getText()));
			fileSelector.addFilter(new FileExtensionFilter(new String[] { CDD_FILE_EXTENSION }));
			fileSelector.setExpandLevel(AbstractTreeViewer.ALL_LEVELS);
			fileSelector.setValidator(new FileOnlySelectionStatusValidator());
			if (fileSelector.open() == Dialog.OK) {
				if (fileSelector.getFirstResult() instanceof IResource) {
					IResource firstResult = (IResource) fileSelector.getFirstResult();
					tCddLocation.setText(firstResult.getProjectRelativePath().toString());
				}
			}
		} else if (source == bOutputLocationBrowse) {
			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.setMessage("Select a output directory for the scripts:"); //$NON-NLS-1$
			dialog.setText("Output Directory Selection");
			String existingPath = tOutputLocation.getText();
			if (existingPath != null && existingPath.trim().isEmpty() == false) {
				File path = new File(existingPath);
				dialog.setFilterPath(path.exists() == true ? path.getAbsolutePath() : null);
			}
			String selection = dialog.open();
			if (selection != null) {
				tOutputLocation.setText(selection);
			}
		} else if(source == cDatabaseType) {
			String selectedDataBaseType = cDatabaseType.getItem(cDatabaseType.getSelectionIndex());
			//Oracle 12c supports varchar or nvarchar upto 32767 byte, when MAX_STRING_SIZE set to EXTENDED
			//https://docs.oracle.com/database/121/REFRN/GUID-D424D23B-0933-425F-BC69-9C0E6724693C.htm#REFRN10321
			if("oracle".equals(selectedDataBaseType)) {
				bExpandMaxStringSize.setEnabled(true);
			} else {
				bExpandMaxStringSize.setSelection(false);
				bExpandMaxStringSize.setEnabled(false);
			}
		}/*else if (source == bBaseTypeFileBrowse) {
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			dialog.setText("Select a base type XML File)"); //$NON-NLS-1$
			dialog.setFilterNames(new String[] { "Base Type XML File" }); //$NON-NLS-1$
			dialog.setFilterExtensions(new String[] { "*.xml" });
			String existingPath = tBaseTypeFileName.getText();
			if (existingPath == null || existingPath.trim().isEmpty() == true) {
				existingPath = DEFAULT_BASE_TYPE_FILE_LOCATION;
			}
			File path = new File(existingPath);
			if (path.exists() == true) {
				dialog.setFileName(path.getAbsolutePath());
			}
			String selection = dialog.open();
			if (selection != null) {
				tBaseTypeFileName.setText(selection);
			}
		}*/
	}

	private void validate() {
		setPageComplete(false);
		// data base type
		dataBaseType = cDatabaseType.getItem(cDatabaseType.getSelectionIndex());
		// ansi
		ansi = bAnsi.getSelection();
		// optimize
		optimize = bOptimize.getSelection();
		// maxstringsize
		expandMaxStringSize = bExpandMaxStringSize.getSelection();
		// cdd location validation if provided
		String tempCDDPath = tCddLocation.getText();
		if (tempCDDPath != null &&  tempCDDPath.trim().isEmpty() == false) {
			// find the cdd location
			if (project.findMember(tempCDDPath) == null) {
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.invalid.cdd.msg")); //$NON-NLS-1$
				return;
			}
			cddPath = tempCDDPath;
		}
		// output folder
		outputDirectory = tOutputLocation.getText();
		if (outputDirectory == null || outputDirectory.trim().isEmpty() == true) {
			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.no.outputdir.msg")); //$NON-NLS-1$
			outputDirectory = null;
			return;
		}
		File outputDir = new File(outputDirectory);
		if (outputDir.exists() == false) {
			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.nonexistent.output.dir.msg")); //$NON-NLS-1$
			outputDirectory = null;
			return;
		}
		if (outputDir.isFile() == true) {
			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.invalid.outputdir.msg")); //$NON-NLS-1$
			outputDirectory = null;
			return;
		}
		if (outputDir.canWrite() == false) {
			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.unwritable.outputdir.msg")); //$NON-NLS-1$
			outputDirectory = null;
			return;
		}
		// output location is valid
		outputFileName = tOuputFileName.getText();
		if (outputFileName == null || outputFileName.isEmpty() == true || outputFileName.trim().isEmpty() == true) {
			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.invalid.outputfileprefix.msg")); //$NON-NLS-1$
			outputFileName = null;
			return;
		}
//		// base types file validation
//		baseTypeFileName = tBaseTypeFileName.getText();
//		if (baseTypeFileName == null || baseTypeFileName.trim().isEmpty() == true) {
//			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.no.basetypefile.msg")); //$NON-NLS-1$
//			baseTypeFileName = null;
//			return;
//		}
//		File baseTypeFile = new File(baseTypeFileName);
//		if (baseTypeFile.exists() == false) {
//			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.nonexistent.basetypefile.msg")); //$NON-NLS-1$
//			baseTypeFileName = null;
//			return;
//		}
//		if (baseTypeFile.isFile() == false) {
//			setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.basicpage.invalid.basetypefile.msg")); //$NON-NLS-1$
//			baseTypeFileName = null;
//			return;
//		}
		getDialogSettings().put(JDBC_EXPORT_OUTPUT_LOCATION, outputDirectory);
//		getDialogSettings().put(BASE_TYPE_FILE_LOCATION, baseTypeFileName);
		setErrorMessage(null);
		setPageComplete(true);
	}

	public String getDatabaseType() {
		return dataBaseType;
	}

	public boolean isANSI() {
		return ansi;
	}

	public boolean isOptimize() {
		return optimize;
	}

	public boolean isExpandMaxStringSize() {
		return expandMaxStringSize;
	}

	public String getCDDPath() {
		return cddPath;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

//	public String getBaseTypeFileName() {
//		return baseTypeFileName;
//	}

	private class WidgetListener extends SelectionAdapter implements ModifyListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			handleWidgetSelection(e.getSource());
			validate();
		}

		@Override
		public void modifyText(ModifyEvent e) {
			validate();
		}
	}

	private class FileOnlySelectionStatusValidator implements ISelectionStatusValidator {

		@Override
		public IStatus validate(Object[] selection) {
			for (Object object : selection) {
				if (object instanceof IFolder) {
					return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, null);
				}
			}
			return new Status(IStatus.OK, StudioCorePlugin.PLUGIN_ID, null);
		}

	}

}