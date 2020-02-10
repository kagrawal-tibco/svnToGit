package com.tibco.cep.studio.dbconcept.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dbconcept.ModulePlugin;

/**
 * Common root for Object Choosers
 * 
 * @author mjha
 */
public class BaseObjectSelector extends SelectionStatusDialog {

	protected String emptyListMessage = "No entries available.";

	protected TreeViewer treeViewer = null;

	protected int fWidth = 60;

	protected int fHeight = 18;

	protected Object input = null;

	protected boolean allowMultiple = false;

	private int expandLevel = 0;

	private boolean noSelectionIsOK = false;

	private String invalidSelectionMessage = ""; //$NON-NLS-1$

	private ISelectionStatusValidator validator = null;

	protected List<ViewerFilter> viewerFilters = null;

	private ViewerSorter viewerSorter = null;

	private INavigatorContentService navigatorService = null;

	protected boolean treeIsEmpty = false;

	/** Return value for CLEAR button pressed */
	public static final int CLEAR = IDialogConstants.CLIENT_ID + 1;

	private Button clearBtn;

	private boolean clearEnabled = false;
	
	public Button createMkDirButton;

	public class EClassStatusValidator implements ISelectionStatusValidator {

		private final List<?> eClasses;

		private boolean allowMultiple;

		public EClassStatusValidator(EClass eClass) {
			this(Collections.singletonList(eClass));
		}

		public EClassStatusValidator(List<?> eClasses) {
			this(eClasses, false);
		}

		public EClassStatusValidator(List<?> eClasses, boolean allowMultiple) {
			this.eClasses = eClasses;
			this.allowMultiple = allowMultiple;
		}

		public void setAllowMultiple(boolean allowMultiple) {
			this.allowMultiple = allowMultiple;
		}

		protected boolean extraValidation(EObject eObj) {
			return true;
		}

		public IStatus validate(Object[] selection) {
			if (selection != null && (selection.length == 1 || allowMultiple)) {
				@SuppressWarnings("unused")
				boolean valid = selection.length > 0;
				EObject eo = null;
				for (int i = 0; i < selection.length; i++) {
					if (selection[i] instanceof EObject) {
						eo = (EObject) selection[i];
					} else if (selection[i] instanceof IAdaptable) {
						eo = (EObject) ((IAdaptable) selection[i])
								.getAdapter(EObject.class);
					}
					if (eo != null) {
						boolean cl = false;
						for (Iterator<?> iter = eClasses.iterator(); iter
								.hasNext();) {
							EClass eClass = (EClass) iter.next();
							if (eClass.isSuperTypeOf(eo.eClass())) {
								cl = true;
								break;
							}
						}

						if (cl) {
							cl = extraValidation(eo);
						}

						if (!cl) {
							valid = false;
							break;
						}
					} else {
						valid = false;
					}
				}
				// if (valid) {
				// String msg;
				// if (selection.length == 1) {
				// if (eo != null) {
				// msg = String
				// .format(
				// Messages.BaseObjectPicker_selectedObj_shortdesc,
				// WorkingCopyUtil.getText(eo));
				// } else {
				// msg = String.valueOf(selection[0]);
				// }
				// } else {
				// msg = Messages.BaseObjectPicker_multipleSelection_shortdesc;
				// }
				// return new Status(Status.OK, XpdResourcesUIActivator.ID,
				// Status.OK, msg, null);
				// }
			}

			return new Status(Status.ERROR, ModulePlugin.PLUGIN_ID,
					Status.ERROR, invalidSelectionMessage, null);
		}
	}

	/**
	 * Common object picker
	 * 
	 * @param parent
	 */
	public BaseObjectSelector(Shell parent) {
		super(parent);
		setHelpAvailable(false);
		setResult(new ArrayList<Object>(0));
		setStatusLineAboveButtons(true);
	}

	/**
	 * Set the input for the viewer
	 * 
	 * @param input
	 */
	public void setInput(Object input) {
		this.input = input;
	}

	/**
	 * Allow multiple selection in the tree
	 * 
	 * @param allowMultiple
	 *            Set to <code>true</code> to allow multiple selections
	 */
	public void setAllowMultiple(boolean allowMultiple) {
		this.allowMultiple = allowMultiple;

		if (this.validator instanceof EClassStatusValidator) {
			((EClassStatusValidator) this.validator)
					.setAllowMultiple(allowMultiple);
		}

	}

	/**
	 * @param currentProjectName
	 * @param path
	 * @param types
	 */
	protected void setInitialEntitySelection(String currentProjectName,
			String path, ELEMENT_TYPES types) {
		if (path != null && !path.equalsIgnoreCase("")) {
			Entity entity = IndexUtils.getEntity(currentProjectName, path,
					types);
			if (entity != null) {
				IFile file = IndexUtils.getFile(currentProjectName, entity);
				setInitialSelection(file);
			}
		}
	}

	/**
	 * Set whether having nothing selected is ok. (This way we can distinguish
	 * between selection(s) removed and cancel).
	 * 
	 * @param noSelectionIsOK
	 */
	public void setNoSelectionIsOK(boolean noSelectionIsOK) {
		this.noSelectionIsOK = noSelectionIsOK;
	}

	/**
	 * Set the initial expand level for the tree control. 0 for none (default)
	 * and TreeViewer.ALL_LEVELS for all.
	 * 
	 * @param expandLevel
	 */
	public void setExpandLevel(int expandLevel) {
		this.expandLevel = expandLevel;
	}

	/**
	 * Set the initial selection in the tree
	 * 
	 * @param selection
	 */
	public void setInitialSelection(Object selection) {
		setInitialSelections(new Object[] { selection });
	}

	/**
	 * Set the invalid selection messsage
	 * 
	 * @param invalidSelectionMessage
	 */
	public void setInvalidSelectionMessage(String invalidSelectionMessage) {
		this.invalidSelectionMessage = invalidSelectionMessage;
	}

	/**
	 * Add tree filter. This may be called multiple times to add multiple
	 * filters
	 * 
	 * @param filter
	 */
	public void addFilter(ViewerFilter filter) {

		if (viewerFilters == null) {
			viewerFilters = new ArrayList<ViewerFilter>();
		}

		viewerFilters.add(filter);

	}

	/**
	 * Add the tree sorter
	 * 
	 * @param sorter
	 */
	public void setSorter(ViewerSorter sorter) {
		this.viewerSorter = sorter;
	}

	/**
	 * Set the validator for the selection in the tree
	 * 
	 * @param validator
	 */
	public void setValidator(ISelectionStatusValidator validator) {
		this.validator = validator;
	}

	public ISelectionStatusValidator getValidator() {
		return validator;
	}

	/**
	 * Set the empty tree message
	 * 
	 * @param msg
	 */
	public void setEmptyListMessage(String msg) {
		emptyListMessage = msg;
	}

	/**
	 * Sets the size of the tree in unit of characters.
	 * 
	 * @param width
	 *            the width of the tree.
	 * @param height
	 *            the height of the tree.
	 */
	public void setSize(int width, int height) {
		fWidth = width;
		fHeight = height;
	}

	@Override
	public boolean close() {
		// Dispose off the navigator service and remove the tree viewer
		if (navigatorService != null) {
			navigatorService.dispose();
			navigatorService = null;
		}

		if (treeViewer != null) {
			treeViewer = null;
		}
		return super.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#create()
	 */
	public void create() {
		BusyIndicator.showWhile(null, new Runnable() {
			public void run() {
				BaseObjectSelector.super.create();

				treeViewer.expandToLevel(expandLevel);

				// Make initial selection of elements provided
				List<?> initialElementSelections = getInitialElementSelections();
				if (initialElementSelections != null
						&& initialElementSelections.size() > 0) {
					treeViewer.setSelection(new StructuredSelection(
							initialElementSelections), true);
				} else {
					// If no selection set then set the first thing in list.
					TreeItem item = null;
					if (treeViewer.getTree().getItemCount() > 0) {
						item = treeViewer.getTree().getItem(0);
					}
					if (item != null && item.getData() != null) {
						treeViewer.setSelection(new StructuredSelection(item
								.getData()), true);
					}
				}
				updateOKStatus();

			}
		});
	}

	/**
	 * Returns the tree viewer.
	 * 
	 * @return the tree viewer
	 */
	protected TreeViewer getTreeViewer() {
		return treeViewer;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		// Create a message and tree view area
		Label messageLabel = createMessageArea(composite);
		TreeViewer treeViewer = createTreeViewer(composite);

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = convertHeightInCharsToPixels(fHeight);

		Tree treeWidget = treeViewer.getTree();
		treeWidget.setLayoutData(data);
		treeWidget.setFont(parent.getFont());

		// If the tree is empty then disable the controls
		if (treeIsEmpty) {
			messageLabel.setEnabled(false);
			treeWidget.setEnabled(false);

			updateStatus(new Status(Status.ERROR, ModulePlugin.PLUGIN_ID,
					Status.ERROR, emptyListMessage, null));

		}

		return composite;
	}

	@Override
	protected void computeResult() {
		setResult(((IStructuredSelection) treeViewer.getSelection()).toList());
	}

	/**
	 * Validate the receiver and update the ok status.
	 * 
	 */
	protected void updateOKStatus() {
		IStatus status = null;

		if (!treeIsEmpty) {
			if (validator != null) {

				Object[] res2 = getResult();
				if (noSelectionIsOK && (res2 == null || res2.length == 0)) {
					status = new Status(IStatus.OK, ModulePlugin.PLUGIN_ID,
							IStatus.OK, "", //$NON-NLS-1$
							null);
				} else {
					status = validator.validate(res2);
				}

			} else {
				status = new Status(IStatus.OK, ModulePlugin.PLUGIN_ID,
						IStatus.OK, "", //$NON-NLS-1$
						null);
			}
		} else {
			status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
					IStatus.ERROR, emptyListMessage, null);
		}
		if (status != null)
			updateStatus(status);
	}

	/**
	 * Create the tree viewer. This will use the Common Navigator service for
	 * the content and label provider. All registered filters and sorter will be
	 * applied.
	 * 
	 * @param composite
	 * @return
	 */
	protected TreeViewer createTreeViewer(Composite composite) {

		if (treeViewer == null) {
			treeViewer = new TreeViewer(composite, SWT.BORDER
					| (allowMultiple ? SWT.MULTI : SWT.SINGLE));
			// Get the navigator content service
			NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
			navigatorService = fact.createContentService(
					"com.tibco.cep.studio.projectexplorer.view", treeViewer); //$NON-NLS-1$

			if (navigatorService != null) {

				/*
				 * Deactivate the Java Element content service in the picker as
				 * it can cause undesirable effects. One commom problem is when
				 * a initial selection is made - this causes a
				 * CyclicPathException when it comes to finding the parents of
				 * the selected object
				 */
				INavigatorActivationService service = navigatorService
						.getActivationService();

				if (service != null) {
					service
							.deactivateExtensions(
									new String[] { "org.eclipse.jdt.java.ui.javaContent" }, false); //$NON-NLS-1$
				}

				// Set the content and label providers
				treeViewer.setContentProvider(navigatorService
						.createCommonContentProvider());
				treeViewer.setLabelProvider(navigatorService
						.createCommonLabelProvider());

				// Apply filters
				if (viewerFilters != null) {
					for (ViewerFilter filter : viewerFilters) {
						treeViewer.addFilter(filter);
					}
				}
				// Apply sorter
				if (viewerSorter != null) {
					treeViewer.setSorter(viewerSorter);
				}
				// Apply input
				treeViewer.setInput(input);
				// Check if tree is empty
				treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);

				// Listen to tree selection
				treeViewer
						.addSelectionChangedListener(new ISelectionChangedListener() {

							public void selectionChanged(
									SelectionChangedEvent event) {
								// Update result and check status of input
								setResult(((IStructuredSelection) event
										.getSelection()).toList());
								updateOKStatus();
							}

						});

				treeViewer.addDoubleClickListener(new IDoubleClickListener() {
					public void doubleClick(DoubleClickEvent event) {
						updateOKStatus();
						boolean okStatus = getOKButtonStatus();
						if (okStatus) {
							okPressed();
						}
					}
				});
			}
		}
		return treeViewer;
	}

	/**
	 * Use enablement of OK button to determine whether selection is valid.
	 * 
	 * @return
	 */
	protected boolean getOKButtonStatus() {
		Button okButton = getOkButton();
		if (okButton != null && !okButton.isDisposed()) {
			return okButton.isEnabled();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.SelectionDialog#createButtonsForButtonBar(org.
	 * eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);

		if (isClearEnabled()) {
			clearBtn = createButton(parent, CLEAR, "Clear", false);
			clearBtn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					setReturnCode(CLEAR);
					// Clear the result
					setResult(Collections.EMPTY_LIST);
					close();
				}
			});

		}
	}

	/**
	 * @return the clearEnabled
	 */
	public boolean isClearEnabled() {
		return clearEnabled;
	}

	/**
	 * @param clearEnabled
	 *            the clearEnabled to set
	 */
	public void setClearEnabled(boolean clearEnabled) {
		this.clearEnabled = clearEnabled;
	}

}