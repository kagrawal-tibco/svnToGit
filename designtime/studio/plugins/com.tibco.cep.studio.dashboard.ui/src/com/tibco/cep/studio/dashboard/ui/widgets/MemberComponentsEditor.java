package com.tibco.cep.studio.dashboard.ui.widgets;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.progress.WorkbenchJob;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponent;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.search.LocalElementPropertyMatcher;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxWrapper;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;

public class MemberComponentsEditor {

	private static final TableColumnInfo[] COLUMNS = new TableColumnInfo[]{
		new TableColumnInfo(TableColumnInfo.SELECT, TableColumnInfo.SELECT, true, true, false, new ColumnWeightData(1, 50, true)),
		new TableColumnInfo(TableColumnInfo.NAME, TableColumnInfo.NAME, true, false, false, new ColumnWeightData(5, 200, true)),
		new TableColumnInfo(TableColumnInfo.FOLDER, TableColumnInfo.FOLDER, true, false, false, new ColumnWeightData(5, 200, true)),
		new TableColumnInfo(TableColumnInfo.DISPLAY_NAME, TableColumnInfo.DISPLAY_NAME, true, false, false, new ColumnWeightData(5, 200, false)),
	};

	private Composite clientComposite;

	private int orientation;

	private int selectionStyle;

	private ElementCheckBoxSelectionViewer selectionViewer;

	private Text txt_Description;

	private String columnLabelPrefix;

	private String particleName;

	private boolean enableSearch;

	private Label lbl_SearchPrompt;

	private Text txt_Search;

	private boolean processEvents;

	private LocalElement localElement;

	private List<LocalElement> enumeration;

	private FilterJob filterJob;

	public MemberComponentsEditor(String columnLabelPrefix, String particleName, FormToolkit toolkit, Composite parent, int style, boolean enableSearch) {
		this.columnLabelPrefix = columnLabelPrefix;
		if (this.columnLabelPrefix == null) {
			this.columnLabelPrefix = "";
		}
		this.particleName = particleName;
		orientation = checkOrientation(style);
		selectionStyle = checkSelectionStyle(style);
		this.enableSearch = enableSearch;
		createControl(particleName, toolkit, parent, style);
		processEvents = true;
	}

	private int checkOrientation(int style){
		if ((style & SWT.HORIZONTAL) == SWT.HORIZONTAL){
			return SWT.HORIZONTAL;
		}
		if ((style & SWT.VERTICAL) == SWT.VERTICAL){
			return SWT.VERTICAL;
		}
		return SWT.VERTICAL;
	}

	private int checkSelectionStyle(int style){
		if ((style & SWT.SINGLE) == SWT.SINGLE){
			return SWT.SINGLE;
		}
		if ((style & SWT.MULTI) == SWT.MULTI){
			return SWT.MULTI;
		}
		return SWT.SINGLE;
	}

	private void createControl(final String particleName, FormToolkit toolkit, Composite parent, int style){
		clientComposite = new Composite(parent, SWT.NONE);
		if (toolkit != null) {
			toolkit.adapt(clientComposite);
		}
		GridLayout clientCompositeLayout = null;
		if (orientation == SWT.VERTICAL) {
			clientCompositeLayout = new GridLayout(1,true);
		}
		else {
			clientCompositeLayout = new GridLayout(2,false);
		}
		clientCompositeLayout.marginHeight = 0;
		clientCompositeLayout.marginWidth = 0;
		clientComposite.setLayout(clientCompositeLayout);

		if (enableSearch == true) {
			//search prompt
			lbl_SearchPrompt = new Label(clientComposite, SWT.NONE);
			if (orientation == SWT.HORIZONTAL) {
				lbl_SearchPrompt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			}
			lbl_SearchPrompt.setText("Enter a name or pattern:");
			if (toolkit != null) {
				toolkit.adapt(lbl_SearchPrompt, true, true);
			}

			//search text
			txt_Search = new Text(clientComposite, SWT.SINGLE | SWT.BORDER);
			if (toolkit != null) {
				toolkit.adapt(txt_Search, true, true);
			}
			if (orientation == SWT.HORIZONTAL) {
				txt_Search.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			}
			else {
				txt_Search.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			}

			txt_Search.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					if (processEvents == true) {
						filter(txt_Search.getText());
					}
				};

			});

			txt_Search.addKeyListener(new KeyListener() {

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.keyCode == SWT.ARROW_DOWN) {
						selectionViewer.getControl().setFocus();
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {
					//do nothing
				}
			});

			txt_Search.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (filterJob != null) {
						filterJob.cancel();
					}
				}
			});
		}

		//component selector
		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(clientComposite, getPrefixedColumns());
		final LocalChartComponent tempParentElement = new LocalChartComponent(null,"PlaceHolder");
		selectionViewer = new ElementCheckBoxSelectionViewer(clientComposite, tempParentElement, particleName, table);
		selectionViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		selectionViewer.getTable().addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection.size() == 1 && e.keyCode == SWT.SPACE) {
					ElementCheckBoxWrapper wrapper = (ElementCheckBoxWrapper) selection.getFirstElement();
					selectionViewer.getCellModifier().modify(selectionViewer.getTable().getSelection()[0], TableColumnInfo.SELECT, Boolean.toString(!wrapper.isChecked()));
//					if (wrapper.isChecked() == true) {
//						localElement.removeElement(particleName, wrapper.getLocalElement().getName(), wrapper.getLocalElement().getFolder());
//					}
//					else {
//						localElement.addElement(particleName, wrapper.getLocalElement());
//					}
//					wrapper.setChecked(!wrapper.isChecked());
//					selectionViewer.refresh();
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				//do nothing;

			}
		});

		// component description group
		Group componentDescGrp = new Group(clientComposite, SWT.NONE);
		componentDescGrp.setText("Description");
		if (toolkit != null) {
			toolkit.adapt(componentDescGrp);
		}
		GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		componentDescGrp.setLayout(layout);
		if (orientation == SWT.HORIZONTAL) {
			GridData componentDescGrpLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
			componentDescGrpLayoutData.widthHint = 150;
			//componentDescGrpLayoutData.heightHint = 50;
			componentDescGrp.setLayoutData(componentDescGrpLayoutData);
		}
		else {
			componentDescGrp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		}
		txt_Description = new Text(componentDescGrp, SWT.WRAP| SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL);
		if (toolkit != null) {
			toolkit.adapt(txt_Description, true, true);
		}
		txt_Description.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		// add event hook ups
		selectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (processEvents == true) {
					ISelection selection = event.getSelection();
					updateDescription(selection);
				}
			}

		});

	}

	protected TableColumnInfo[] getPrefixedColumns() {
		TableColumnInfo[] newColumns = new TableColumnInfo[COLUMNS.length];
		int i = 0;
		for (TableColumnInfo column : COLUMNS) {
			String title = column.getTitle();
			if (title.equals(TableColumnInfo.SELECT) == false) {
				title = columnLabelPrefix+title;
				newColumns[i] = new TableColumnInfo(column.getId(), title, column.isImageRequired(), column.isMultiSelect(), column.isSingleSelect(), column.getLayoutData());
			}
			else {
				newColumns[i] = new TableColumnInfo(column.getId(), title, column.isImageRequired(), selectionStyle == SWT.MULTI, selectionStyle == SWT.SINGLE, column.getLayoutData());
			}

			i++;
		}
		return newColumns;
	}

	public Control getControl(){
		return clientComposite;
	}

	public void setEnabled(boolean enabled) {
		txt_Search.setEnabled(enabled);
		selectionViewer.getTable().setEnabled(enabled);
	}

	public boolean isEnabled(){
		return txt_Search.isEnabled();
	}

	public void setProcessEvents(boolean processEvents) {
		this.processEvents = processEvents;
	}

	public boolean isProcessEvents() {
		return processEvents;
	}

	public void setLocalElement(LocalElement localElement) {
		this.localElement = localElement;
		this.selectionViewer.setParentElement(localElement);
		this.selectionViewer.setSelectedElements(localElement.getChildren(particleName));
	}

	public void setEnumeration(List<LocalElement> enumeration) {
		this.enumeration = enumeration;
		if (enableSearch == true) {
			filter(txt_Search.getText());
		}
		else {
			this.selectionViewer.setElementChoices(enumeration);
			if (localElement != null) {
				this.selectionViewer.setSelectedElements(localElement.getChildren(particleName));
				updateDescription(this.selectionViewer.getSelection());
			}
		}
	}

	private void filter(String pattern) {
		if (filterJob != null) {
			filterJob.cancel();
		}
		if (pattern != null && StringUtil.isEmpty(pattern) == false){
			filterJob = new FilterJob(pattern);
			filterJob.schedule();
		}
		else {
			this.selectionViewer.setElementChoices(enumeration);
			if (localElement != null) {
				this.selectionViewer.setSelectedElements(localElement.getChildren(particleName));
				updateDescription(this.selectionViewer.getSelection());
			}
		}
	}

	protected void updateDescription(ISelection selection) {
		txt_Description.setText("");
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1) {
				ElementCheckBoxWrapper selectedCompWrapper = (ElementCheckBoxWrapper) structuredSelection.getFirstElement();
				try {
					txt_Description.setText(selectedCompWrapper.getLocalElement().getDescription());
				} catch (Exception ignore) {
				}
			}
		}
	}

	private class FilterJob extends WorkbenchJob {

		private LocalElementPropertyMatcher[] matchers;

		public FilterJob(String pattern) {
			super("Filter Chart Components");
			this.matchers = new LocalElementPropertyMatcher[2];
			this.matchers[0] = new LocalElementPropertyMatcher(LocalElement.PROP_KEY_NAME, "*"+pattern, true);
			this.matchers[1] = new LocalElementPropertyMatcher(LocalConfig.PROP_KEY_DISPLAY_NAME, "*"+pattern, true);
			this.setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			if (clientComposite.isDisposed() == true) {
				return Status.CANCEL_STATUS;
			}
			if (monitor.isCanceled() == true) {
				return Status.CANCEL_STATUS;
			}
			List<LocalElement> filteredList = new LinkedList<LocalElement>();
			for (LocalElement localElement : enumeration) {
				for (LocalElementPropertyMatcher matcher : matchers) {
					boolean match = matcher.match(localElement);
					if (match == true) {
						filteredList.add(localElement);
						break;
					}
				}
				if (monitor.isCanceled() == true) {
					return Status.CANCEL_STATUS;
				}
			}
			selectionViewer.setElementChoices(filteredList);
			if (localElement != null) {
				selectionViewer.setSelectedElements(localElement.getChildren(particleName));
			}
			return Status.OK_STATUS;
		}

	}

}