package com.tibco.cep.studio.dashboard.ui.wizards.view;

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
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.progress.WorkbenchJob;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.search.LocalElementPropertyMatcher;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxWrapper;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 * @author rmayala
 *
 */
public class ViewPageSelectionWizardPage extends WizardPage {

	private static final TableColumnInfo[] COLUMNS = new TableColumnInfo[]{
		new TableColumnInfo(TableColumnInfo.SELECT, TableColumnInfo.SELECT, true, true, false, new ColumnWeightData(1, 50, true)),
		new TableColumnInfo(TableColumnInfo.DEFAULT_SELECT, TableColumnInfo.DEFAULT_SELECT, true, false, true, new ColumnWeightData(1, 50, true)),
		new TableColumnInfo(TableColumnInfo.NAME, TableColumnInfo.NAME, true, false, false, new ColumnWeightData(5, 200, true)),
		new TableColumnInfo(TableColumnInfo.FOLDER, TableColumnInfo.FOLDER, true, false, false, new ColumnWeightData(5, 200, true)),
		new TableColumnInfo(TableColumnInfo.DISPLAY_NAME, TableColumnInfo.DISPLAY_NAME, true, false, false, new ColumnWeightData(5, 200, false)),
	};

	private boolean processEvents;
	private FilterJob filterJob;
	private Composite clientComposite;
	private ElementCheckBoxSelectionViewer selectionViewer;
	private Text txt_Description;
	private List<LocalElement> enumeration;
	private LocalConfig localConfig;

	protected ViewPageSelectionWizardPage(String pageName) {
		super(pageName);
		processEvents = true;
	}

	@Override
	public void createControl(Composite parent) {

		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new FillLayout());
		pageComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		clientComposite = new Composite(pageComposite, SWT.NONE);

		GridLayout clientCompositeLayout = new GridLayout(1, true);
		clientCompositeLayout.marginHeight = 0;
		clientCompositeLayout.marginWidth = 0;
		clientComposite.setLayout(clientCompositeLayout);
		createSearchWidget(clientComposite);

		// component selector
		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(clientComposite, COLUMNS);
		final LocalElement tempParentElement = new LocalConfig(null, "PlaceHolder");
		selectionViewer = new ElementCheckBoxSelectionViewer(clientComposite, tempParentElement, BEViewsElementNames.PAGE, "DefaultPage",table);
		selectionViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		selectionViewer.getTable().addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection.size() == 1 && e.keyCode == SWT.SPACE) {
					ElementCheckBoxWrapper wrapper = (ElementCheckBoxWrapper) selection.getFirstElement();
					selectionViewer.getCellModifier().modify(selectionViewer.getTable().getSelection()[0], TableColumnInfo.SELECT, Boolean.toString(!wrapper.isChecked()));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// do nothing;
			}
		});

		// component description group
		Group componentDescGrp = new Group(clientComposite, SWT.NONE);
		componentDescGrp.setText("Description");
		GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		componentDescGrp.setLayout(layout);
		componentDescGrp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		txt_Description = new Text(componentDescGrp, SWT.WRAP | SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL);
		txt_Description.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		// add event hook ups
		selectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (processEvents == true) {
					ISelection selection = event.getSelection();
					updateDescription(selection);
				}

				// To validate, if no page is selected, disable next and finish button.
				List<LocalElement> selectedPages = localConfig.getChildren(BEViewsElementNames.PAGE);
				if (selectedPages == null || selectedPages.isEmpty() || getDefaultPageLocalElement() == null) {
					setPageComplete(false);
					setErrorMessage("A Default page must be specified.");
				} else {
					setPageComplete(true);
					setErrorMessage(null);
				}
			}

		});


		setControl(pageComposite);

	}

	private void createSearchWidget(Composite composite) {
		// search prompt
		Label lbl_SearchPrompt = new Label(composite, SWT.NONE);
		lbl_SearchPrompt.setText("Enter a name or pattern:");
		// search text
		final Text txt_Search = new Text(composite, SWT.SINGLE | SWT.BORDER);

		txt_Search.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

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
				// do nothing
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

	private void filter(String pattern) {
		if (filterJob != null) {
			filterJob.cancel();
		}
		if (pattern != null && StringUtil.isEmpty(pattern) == false) {
			filterJob = new FilterJob(pattern);
			filterJob.schedule();
		} else {
			this.selectionViewer.setElementChoices(enumeration);
			if (localConfig != null) {
				this.selectionViewer.setSelectedElements(localConfig.getChildren(BEViewsElementNames.PAGE), getDefaultPageLocalElement());
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
			this.matchers[0] = new LocalElementPropertyMatcher(LocalElement.PROP_KEY_NAME, "*" + pattern, true);
			this.matchers[1] = new LocalElementPropertyMatcher(LocalConfig.PROP_KEY_DISPLAY_NAME, "*" + pattern, true);
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
			if (localConfig != null) {
				selectionViewer.setSelectedElements(localConfig.getChildren(BEViewsElementNames.PAGE), getDefaultPageLocalElement());
			}
			return Status.OK_STATUS;
		}
	}

	public void setLocalConfig(LocalConfig locConfig) {
		localConfig = locConfig;
		selectionViewer.setParentElement(localConfig);
		List<LocalElement> pages = localConfig.getRoot().getChildren(BEViewsElementNames.DASHBOARD_PAGE);
		if (pages != null) {
			selectionViewer.setElementChoices(pages);
		}
		enumeration = pages;
		selectionViewer.setElementChoices(enumeration);
		selectionViewer.setSelectedElements(this.localConfig.getChildren(BEViewsElementNames.PAGE), getDefaultPageLocalElement());

	}

	private LocalElement getDefaultPageLocalElement() {
		LocalElement defaultElement = null;
		if (localConfig != null) {
			List<LocalElement> defaultPages = localConfig.getChildren("DefaultPage");
			if (defaultPages != null && !defaultPages.isEmpty()) {
				defaultElement = defaultPages.get(0);
			}
		}
		return defaultElement;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	public boolean isPageComplete() {
		if (localConfig == null) {
			return true;
		}

		List<LocalElement> selectedPages = localConfig.getChildren(BEViewsElementNames.PAGE);
		if (selectedPages == null || selectedPages.isEmpty() || getDefaultPageLocalElement() == null) {
			return false;
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#setPageComplete(boolean)
	 */
	public void setPageComplete(boolean complete) {
        if (isCurrentPage()) {
			getContainer().updateButtons();
		}
    }
}