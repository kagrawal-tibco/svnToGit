package com.tibco.cep.studio.wizard.ftl.pages;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;

public class SelectFilesWizardPage extends FTLWizardPage {
	private Text sourceFileText = null;
	private String filePath = null;
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public SelectFilesWizardPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.selectFilesWizard.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.selectFilesWizard.desc"));
		setPageComplete(false);
		setPrepared(true);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		setControl(container);
		
		final Label selLabel = new Label(container, SWT.NONE);
		final GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		selLabel.setLayoutData(gridData);
		selLabel.setText(Messages.getString("ftl.wizard.page.selectFilesWizard.label"));		
		
		final Label textLabel = new Label(container, SWT.NONE);
		final GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		textLabel.setLayoutData(textGridData);
		textLabel.setText(FTLWizardConstants.PAGE_NAME_SELECT_FILE_TEXT_LABEL);
	    
		sourceFileText = new Text(container, SWT.BORDER);
		sourceFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sourceFileText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				updatePageComplete();
			}
		});
		
		final Button button = new Button(container, SWT.NULL);
		button.addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent e) {
				 browseForSourceFile();
			 }
		});
		button.setText("Browse...");
		setControl(container);
	}
	
	private void updatePageComplete() {
		IPath sourceLoc = getSourceLocation();
		if(sourceLoc == null || !sourceLoc.toFile().exists() || sourceLoc.lastSegment().indexOf(".json") == -1) {
			 updateStatus(Messages.getString("ftl.wizard.page.selectFilesWizard.tip"));
			 return;
		}
		updateStatus(null);
		filePath = sourceFileText.getText().toString();
		setPageComplete(true);
	}
	
	protected void browseForSourceFile() {
		IPath path = browse(getSourceLocation(), false);
		if(path == null) {
			return;
		}
		IPath rootLoc = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		if(rootLoc.isPrefixOf(path)) {
			path = path.setDevice(null).removeFirstSegments(rootLoc.segmentCount());
		}
		sourceFileText.setText(path.toString());
	}
	
	private IPath browse(IPath path, boolean mustExit) {
		FileDialog dialog = new FileDialog(getShell(), mustExit ? SWT.OPEN : SWT.SAVE);
		if(path != null) {
			if(path.segmentCount() > 1) {
				dialog.setFilterPath(path.removeLastSegments(1).toOSString());
			} 
			if(path.segmentCount() > 0) {
				dialog.setFilterPath(path.lastSegment());
			}
		}
		String result = dialog.open();
		if(result != null) {
			return new Path(result);
		} else {
			return null;
		}
	}
	
	private IPath getSourceLocation() {
		String text = sourceFileText.getText().trim();
		if(text.length() == 0) {
			return null;
		}
		IPath path = new Path(text);
		if(!path.isAbsolute()) {
			path = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(path);
		}
		return path;
	}
}
