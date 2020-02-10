package com.tibco.cep.studio.wizard.as.internal.ui.components;

import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_CHANNEL_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_DESTINATION_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_ERROR_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_WARNING_16X16;
import static com.tibco.cep.studio.wizard.as.internal.utils.UIUtils.createTreeNodesForChannels;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel._PROP_NAME_CHANNELS;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel._PROP_NAME_CURRENT_SELECTED_CHANNEL;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel._PROP_NAME_CURRENT_SELECTED_SPACEDEF;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.H_SCROLL;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.SINGLE;
import static org.eclipse.swt.SWT.V_SCROLL;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters.CollectionNotEmptyToBooleanConverter;
import com.tibco.cep.studio.wizard.as.internal.ui.tree.SpacePatternFilter;
import com.tibco.cep.studio.wizard.as.internal.ui.tree.converters.SpecifiedTypeToTreeNodeConverter;
import com.tibco.cep.studio.wizard.as.internal.ui.tree.converters.TreeNodeToSpecifiedTypeConverter;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;

public class ASSpaceSelectionWizardPageComponent extends Composite {

	// Controller
	private IASSpaceSelectionWizardPageController controller;

	// Resources
	private Image                                 channelImage;
	private Image                                 spaceImage;
	private Image                                 errorImage;
	private Image                                 warningImage;

	// UI
	private Composite                             cardsContainer;
	private StackLayout                           cardsContainerLayout;
	private Composite                             bizContainer;
	private Composite                             warnContainer;
	private Label                                 warningLabel;
	private Composite                             innerContainer;
	private Label                                 spaceLabel;
	private TreeViewer                            treeViewer;
	private Button                                refreshButton;

	// Bindings
	private DataBindingContext                    bc;

	// Handler
	private Handler                               handler;


	public ASSpaceSelectionWizardPageComponent(Composite parent, IASSpaceSelectionWizardPageController controller) {
		super(parent, NONE);
		this.controller = controller;
		initialize();
	}

	private void initialize() {
	    try {
        	initResources();
        	initUI();
        	attachBindings();
        	initListeners();
        	initData();
	    }
        catch (ExceptionInInitializerError ex) {
            cardsContainerLayout.topControl = warnContainer;
            cardsContainer.layout();
        }
	}

	private void initListeners() {
		this.handler = new Handler();

		refreshButton.addSelectionListener(handler);
		treeViewer.addDoubleClickListener(handler);
		controller.getModel().addPropertyChangeListener(_PROP_NAME_CHANNELS, handler);
	}

	private void initResources() {
		this.channelImage = StudioUIPlugin.getDefault().getImage(_IMAGE_CHANNEL_16X16);
		this.spaceImage = StudioUIPlugin.getDefault().getImage(_IMAGE_DESTINATION_16X16);
		this.errorImage = ASPlugin.getDefault().getImage(_IMAGE_ERROR_16X16);
		this.warningImage = ASPlugin.getDefault().getImage(_IMAGE_WARNING_16X16);
	}

	private void initUI() {
		this.setLayout(new FillLayout());

		cardsContainer = new Composite(this, NONE);
		cardsContainerLayout = new StackLayout();
		cardsContainer.setLayout(cardsContainerLayout);

		bizContainer = new Composite(cardsContainer, NONE);
		createBusinessComponents();

		warnContainer = new Composite(cardsContainer, NONE);
		createWarningComponents();

		cardsContainerLayout.topControl = bizContainer;
	}

	private void createBusinessComponents() {
		GridLayout layout = new GridLayout();
		bizContainer.setLayout(layout);

		this.spaceLabel = new Label(bizContainer, NONE);
		GridData layoutData = new GridData(FILL, CENTER, true, false);
		spaceLabel.setLayoutData(layoutData);

		createInnerContainer();
		createTreeView();

		createRefreshButton();
	}

	private void createWarningComponents() {
	    warnContainer.setLayout(new FillLayout());
	    warningLabel = new Label(warnContainer, NONE);
	    warningLabel.setText(Messages.getString("Common.as_lib_not_in_path")); //$NON-NLS-1$
	}

	private void createRefreshButton() {
		refreshButton = new Button(bizContainer, NONE);
		GridData layoutData = new GridData();
		refreshButton.setLayoutData(layoutData);
    }

	private void createInnerContainer() {
		innerContainer = new Composite(bizContainer, NONE);

		GridData layoutData = new GridData(FILL, FILL, true, true);
		innerContainer.setLayoutData(layoutData);

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		innerContainer.setLayout(layout);
	}

	private void createTreeView() {
		PatternFilter filteredTreeFilter = new SpacePatternFilter();
		FilteredTree filterTree = new FilteredTree(innerContainer, SINGLE | H_SCROLL | V_SCROLL | BORDER, filteredTreeFilter, true);
		GridData layoutData = new GridData(FILL, FILL, true, true);
		filterTree.setLayoutData(layoutData);
//		filterTree.getFilterControl().setEnabled(false);
//		filterTree.getFilterControl().setEditable(false);
		treeViewer = filterTree.getViewer();
		treeViewer.setContentProvider(new TreeNodeContentProvider());
		treeViewer.setLabelProvider(new LabelProvider() {

			@Override
			public Image getImage(Object element) {
				TreeNode node = (TreeNode) element;
				Object value = node.getValue();
				Image image = null;
				if (value instanceof Channel) {
					image = channelImage;
				}
				else if (value instanceof SpaceDef) {
					image = spaceImage;
				}
				else if (value instanceof IStatus) {
					IStatus status = (IStatus) value;
					switch (status.getSeverity()) {
						case IStatus.ERROR:
							image = errorImage;
							break;
						case IStatus.WARNING:
							image = warningImage;
							break;
						default:
							break;
					}
				}

				return image;
			}

			@Override
			public String getText(Object element) {
				String text = ""; //$NON-NLS-1$
				if (null != element) {
					TreeNode node = (TreeNode) element;
					Object value = node.getValue();
					if (value instanceof Channel) {
						text = ((Channel) value).getName();
					}
					else if (value instanceof SpaceDef) {
						text = ((SpaceDef) value).getName();
					}
					else if (value instanceof IStatus) {
						text = ((IStatus) value).getMessage();
					}
					else {
						text = value.toString();
					}
				}
				return text;
			}

		});
	}

	private void attachBindings() {
		bc = new DataBindingContext();

		IASSpaceSelectionWizardPageModel model = controller.getModel();
		// Current Channel
		IObservableValue treeSelObValue = ViewersObservables.observeSingleSelection(treeViewer);
		IObservableValue modelCurrentSelChannelObValue = BeansObservables.observeValue(model, _PROP_NAME_CURRENT_SELECTED_CHANNEL);
		bc.bindValue(treeSelObValue, modelCurrentSelChannelObValue,
		        new UpdateValueStrategy().setConverter(new TreeNodeToSpecifiedTypeConverter(Channel.class)),
		        new UpdateValueStrategy().setConverter(new SpecifiedTypeToTreeNodeConverter(Channel.class, treeViewer)));

		// Current SpaceDef
		treeSelObValue = ViewersObservables.observeSingleSelection(treeViewer);
		IObservableValue modelCurrentSelSpaceDefObValue = BeansObservables.observeValue(model, _PROP_NAME_CURRENT_SELECTED_SPACEDEF);
		bc.bindValue(treeSelObValue, modelCurrentSelSpaceDefObValue,
		        new UpdateValueStrategy().setConverter(new TreeNodeToSpecifiedTypeConverter(SpaceDef.class)),
		        new UpdateValueStrategy().setConverter(new SpecifiedTypeToTreeNodeConverter(SpaceDef.class, treeViewer)));

		// Channel List
		IObservableValue refreshEnabledObValue = SWTObservables.observeEnabled(refreshButton);
		IObservableValue modelChannelsObValue = BeansObservables.observeValue(model, _PROP_NAME_CHANNELS);
		bc.bindValue(refreshEnabledObValue, modelChannelsObValue,
		        new UpdateValueStrategy(POLICY_NEVER),
		        new UpdateValueStrategy().setConverter(new CollectionNotEmptyToBooleanConverter()));
	}

	private void initData() {
		spaceLabel.setText(Messages.getString("ASSpaceSelectionWizardPageComponent.label_space")); //$NON-NLS-1$
		refreshButton.setText(Messages.getString("ASSpaceSelectionWizardPageComponent.button_refresh")); //$NON-NLS-1$

		controller.refresh(); // to refresh channel list
	}

	private void refreshTreeViewer(List<Channel> channels) {
		treeViewer.setInput(createTreeNodesForChannels(channels, controller));
		treeViewer.expandToLevel(2);
	}


	private class Handler implements SelectionListener, PropertyChangeListener, IDoubleClickListener {

		@Override
		public void widgetSelected(SelectionEvent evt) {
			controller.refresh();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent evt) {
		}

		@SuppressWarnings("unchecked")
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
	        List<Channel> newChannels = (List<Channel>) evt.getNewValue();
	        if (null != newChannels) {
	        	refreshTreeViewer(newChannels);
	        }
        }

		@Override
		public void doubleClick(DoubleClickEvent event) {
			IStructuredSelection s = (IStructuredSelection) event.getSelection();
			Object element = s.getFirstElement();
			if (treeViewer.isExpandable(element)) {
				treeViewer.setExpandedState(element, !treeViewer.getExpandedState(element));
			}
			else {
				TreeNode treeNode = (TreeNode) element;
				Object userObject = treeNode.getValue();
				if (null != userObject && userObject instanceof SpaceDef) {
					try {
						controller.advanceToNextPage();
					}
					catch (Exception ex) {
						ASPlugin.openError(
						        getShell(),
						        Messages.getString("ASSpaceSelectionWizardPageComponent.title_error"), Messages.getString("ASSpaceSelectionWizardPageComponent.failed_to_show_next_page"), ex); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
			}
		}

	}

	// class ASElementContentProvider extends WorkbenchContentProvider {
	// private DeferredTreeContentManager manager;
	//
	// public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	// {
	// if (viewer instanceof AbstractTreeViewer) {
	// manager = new DeferredTreeContentManager((AbstractTreeViewer) viewer);
	// }
	// super.inputChanged(viewer, oldInput, newInput);
	// }
	//
	// public boolean hasChildren(Object element) {
	// if (element == null)
	// return false;
	// else
	// return true;
	// }
	//
	// public Object[] getChildren(Object parentElement) {
	// if (parentElement instanceof String) {
	// Object[] root = { "root" };
	// return root;
	// }
	// if (manager != null) {
	// Object[] children = manager.getChildren(parentElement);
	// return children;
	// }
	// return super.getChildren(parentElement);
	// }
	// }

}
