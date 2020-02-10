package com.tibco.cep.studio.ui.property.page;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.views.StatusInfo;
import com.tibco.cep.studio.ui.views.TypedItemFilter;
import com.tibco.cep.studio.ui.views.TypedItemSelectionValidator;

public class NativeLibraryDialog extends StatusDialog {
	private String path;
	private Shell fShell;
	private Label desc;
	private Button browseExternalButton;
	private Button browseWorkspaceButton;
	private Text fTextControl;
	private WidgetListener widgetListener;
	private String fOrginalValue;
	private JavaLibEntry parentEntry;
	private IStatusChangeListener listener;
	
	public NativeLibraryDialog(Shell shell, String path, JavaLibEntry parentEntry) {
		super(shell);
		this.path = path;
		this.parentEntry = parentEntry;
		this.widgetListener = new WidgetListener();
		setTitle(Messages.getString("project.buildpath.tab.javalib.native.library.dialog"));
		this.listener= new IStatusChangeListener() {
			public void statusChanged(IStatus status) {
				updateStatus(status);				
			}
		};	
	}

	public String getNativeLibraryPath() {
		String val= path;
		if (val == null || val.length() == 0) {
			return null;
		}
		return new Path(val).toPortableString();
	}
	
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite= (Composite) super.createDialogArea(parent);
		Control inner= createDialogContents(composite);
		inner.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);	
		return composite;
	}
	
	protected Control createDialogContents(Composite parent) {
		fShell= parent.getShell();
		
		Composite inner= new Composite(parent, SWT.NONE);
		inner.setFont(parent.getFont());
		inner.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		int nColumns= 3;
		
		GridLayout layout= new GridLayout(nColumns, false);
		layout.marginWidth= 5;
		layout.marginWidth= 5;
		inner.setLayout(layout);
		
		DialogPixelConverter converter= new DialogPixelConverter(parent);

		this.desc= new Label(inner, SWT.LEFT | SWT.WRAP);
		desc.setFont(inner.getFont());
		desc.setText(Messages.getString("project.buildpath.dialog.native.library",parentEntry.getPath()));
		GridData gridData= new GridData(GridData.FILL, GridData.CENTER, false, false, 3, 1);
		gridData.widthHint= converter.convertWidthInCharsToPixels(80);
		desc.setLayoutData(gridData);
		
		
		
		this.fTextControl= new Text(inner, SWT.SINGLE | SWT.BORDER);
		fTextControl.setFont(parent.getFont());
		setWidthHint(fTextControl, converter.convertWidthInCharsToPixels(50));
		GridData gd= new GridData();
		gd.horizontalSpan= 2;
		gd.horizontalAlignment= GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		fTextControl.setLayoutData(gd);
		this.fTextControl.addModifyListener(this.widgetListener);
	
		this.browseExternalButton = new Button(inner,SWT.PUSH);
		this.browseExternalButton.setText(Messages.getString("project.buildpath.dialog.native.btn.External"));
		gd= new GridData();
		gd.horizontalSpan= 1;
		gd.horizontalAlignment= GridData.FILL;
		gd.widthHint = getButtonWidthHint(this.browseExternalButton);
		this.browseExternalButton.setLayoutData(gd);
		this.browseExternalButton.addSelectionListener(this.widgetListener);

		createEmptySpace(inner, 2);
		this.browseWorkspaceButton = new Button(inner,SWT.PUSH);
		this.browseWorkspaceButton.setText(Messages.getString("project.buildpath.dialog.native.btn.Workspace"));
		gd= new GridData();
		gd.horizontalSpan= 1;
		gd.horizontalAlignment= GridData.FILL;
		gd.widthHint = getButtonWidthHint(this.browseWorkspaceButton);
		this.browseWorkspaceButton.setLayoutData(gd);
		this.browseWorkspaceButton.addSelectionListener(this.widgetListener);
		
		if(path != null) {
			fTextControl.setText(Path.fromPortableString(path).toPortableString());
			this.fOrginalValue= path;
		} else {
			this.fOrginalValue= ""; //$NON-NLS-1$
		}
		fTextControl.setFocus();
		return parent;
	}
	
	
	/**
	 * Sets the width hint of a control. Assumes that GridData is used.
	 */
	public static void setWidthHint(Control control, int widthHint) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).widthHint= widthHint;
		}
	}
	
	public static void setHorizontalGrabbing(Control control) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).grabExcessHorizontalSpace= true;
		}
	}
	
	/**
	 * Returns a width hint for a button control.
	 * @param button the button
	 * @return the width hint
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		DialogPixelConverter converter= new DialogPixelConverter(button);
		int widthHint= converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}
	
	protected static GridData gridDataForLabel(int span) {
		GridData gd= new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan= span;
		return gd;
	}
	
	public static Control createEmptySpace(Composite parent, int span) {
		Label label= new Label(parent, SWT.LEFT);
		GridData gd= new GridData();
		gd.horizontalAlignment= GridData.BEGINNING;
		gd.grabExcessHorizontalSpace= false;
		gd.horizontalSpan= span;
		gd.horizontalIndent= 0;
		gd.widthHint= 0;
		gd.heightHint= 0;
		label.setLayoutData(gd);
		return label;
	}
	
	
	class WidgetListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			Object source = e.getSource();
			if(source == fTextControl) {
				listener.statusChanged(validatePath());
				path=fTextControl.getText();
			}
			
			
		}
		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if(source == NativeLibraryDialog.this.browseExternalButton) {
				String res = chooseExternal();
				if (res != null) {
					fTextControl.setText(res);
					path=res;
				}
			} else if(source == NativeLibraryDialog.this.browseWorkspaceButton){
				String res = chooseInternal();
				if (res != null) {
					fTextControl.setText(res);
					path=res;
				}
			}
		}
	}
	
	private IStatus validatePath() {
		StatusInfo status= new StatusInfo();
		String val= fTextControl.getText();
		if (val.length() == 0) {
			return status;
		}
		Path path= new Path(val);
		if (path.isAbsolute()) {
			if (!path.toFile().isDirectory()) {
				status.setWarning(Messages.getString("project.buildpath.dialog.native.folder.notexist")); 
				return status;
			}
		} else {
			if (!(ResourcesPlugin.getWorkspace().getRoot().findMember(path) instanceof IContainer)) {
				status.setWarning(Messages.getString("project.buildpath.dialog.native.folder.notexist.ws")); 
				return status;
			}
		}
		return status;
	}
	
	protected String chooseExternal() {
		IPath currPath= new Path(fTextControl.getText());
		if (currPath.isEmpty()) {
			currPath= Path.fromOSString(this.parentEntry.getPath()).removeLastSegments(2);
		} else {
			currPath= currPath.removeLastSegments(1);
		}
	
		DirectoryDialog dialog= new DirectoryDialog(fShell);
		dialog.setMessage(Messages.getString("project.buildpath.dialog.native.directory"));
		dialog.setText(Messages.getString("project.buildpath.dialog.native.directory.title"));
		dialog.setFilterPath(currPath.toOSString());
		String res= dialog.open();
		if (res != null) {
			return new Path(res).toPortableString();
		}
		return null;
	}
	
	/*
	 * Opens a dialog to choose an internal file.
	 */	
	protected String chooseInternal() {
		String initSelection= fTextControl.getText();
		
		ILabelProvider lp= new WorkbenchLabelProvider();
		ITreeContentProvider cp= new WorkbenchContentProvider();
		Class[] acceptedClasses= new Class[] { IProject.class, IFolder.class };
		TypedItemSelectionValidator validator= new TypedItemSelectionValidator(acceptedClasses, true);
		ViewerFilter filter= new TypedItemFilter(acceptedClasses);

		IResource initSel= null;
		IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
		if (initSelection.length() > 0) {
			initSel= root.findMember(new Path(initSelection));
		}
		if (initSel == null) {
			initSel= root.findMember(Path.fromOSString(this.parentEntry.getPath()).removeLastSegments(2));
		}

		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(fShell, lp, cp);
		dialog.setAllowMultiple(false);
		dialog.setValidator(validator);
		dialog.addFilter(filter);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		dialog.setTitle(Messages.getString("project.buildpath.dialog.native.directory.title")); 
		dialog.setMessage(Messages.getString("project.buildpath.dialog.native.directory")); 
		dialog.setInput(root);
		dialog.setInitialSelection(initSel);
		dialog.setHelpAvailable(false);
		if (dialog.open() == Window.OK) {
			IResource res= (IResource) dialog.getFirstResult();
			return res.getFullPath().makeRelative().toPortableString();
		}
		return null;
	}
	
	public static class DialogPixelConverter {
		
		private final FontMetrics fFontMetrics;
		
		public DialogPixelConverter(Control control) {
			this(control.getFont());
		}
		
		public DialogPixelConverter(Font font) {
			GC gc = new GC(font.getDevice());
			gc.setFont(font);
			fFontMetrics= gc.getFontMetrics();
			gc.dispose();
		}
		
		/*
		 * see org.eclipse.jface.dialogs.DialogPage#convertHeightInCharsToPixels(int)
		 */
		public int convertHeightInCharsToPixels(int chars) {
			return Dialog.convertHeightInCharsToPixels(fFontMetrics, chars);
		}

		/*
		 * see org.eclipse.jface.dialogs.DialogPage#convertHorizontalDLUsToPixels(int)
		 */
		public int convertHorizontalDLUsToPixels(int dlus) {
			return Dialog.convertHorizontalDLUsToPixels(fFontMetrics, dlus);
		}

		/*
		 * see org.eclipse.jface.dialogs.DialogPage#convertVerticalDLUsToPixels(int)
		 */
		public int convertVerticalDLUsToPixels(int dlus) {
			return Dialog.convertVerticalDLUsToPixels(fFontMetrics, dlus);
		}
		
		/*
		 * see org.eclipse.jface.dialogs.DialogPage#convertWidthInCharsToPixels(int)
		 */
		public int convertWidthInCharsToPixels(int chars) {
			return Dialog.convertWidthInCharsToPixels(fFontMetrics, chars);
		}	

	}
	public interface IStatusChangeListener {
		
		/**
		 * Notifies this listener that the given status has changed.
		 * 
		 * @param	status	the new status
		 */
		void statusChanged(IStatus status);
	}

}
