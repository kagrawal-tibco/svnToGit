package com.tibco.cep.studio.dashboard.ui.editors.views.system;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynColorType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynPrimitiveType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.ui.images.KnownImageKeys;
import com.tibco.cep.studio.dashboard.ui.utils.SynColorUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class SystemElementsPage extends FormPage {

	private static final String DASHBOARD_SYSTEM_SKIN_ELEMENTS = "System Elements";

	private final LocalECoreFactory coreFactory;
	private final List<Entity> entities;

	private SashForm sashForm;

	private TreeViewer elementBrowserTreeViewer;

	private ScrolledComposite elementDetailsCompositeScrollableHolder;
	private Composite elementDetailsComposite;

	public SystemElementsPage(FormEditor editor, LocalECoreFactory coreFactory, List<Entity> entities) {
		super(editor, "editor.system", "System Skin Viewer");
		this.coreFactory = coreFactory;
		this.entities = entities;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		try {
			String[] elementTypes = new String[] { BEViewsElementNames.SERIES_COLOR, BEViewsElementNames.CHART_COLOR_SET, BEViewsElementNames.TEXT_COLOR_SET, BEViewsElementNames.SKIN };
			for (String elementType : elementTypes) {
				List<LocalElement> elements = coreFactory.getChildren(elementType);
				for (Iterator<LocalElement> itElements = elements.iterator(); itElements.hasNext();) {
					LocalElement element = itElements.next();
					for (String particleName : element.getParticleNames(true)) {
						element.getChildren(particleName);
					}
				}
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not initialize system element page for " + getEditorInput(), e));
		}

	}

	@Override
	protected void createFormContent(final IManagedForm mform) {
		ScrolledForm scrolledForm = mform.getForm();
		scrolledForm.setText("System Skin Viewer");
		scrolledForm.setBackgroundImage(DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_FORM_BANNER));

		Composite formBody = scrolledForm.getBody();

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 5;
		formBody.setLayout(layout);

		sashForm = new SashForm(formBody, SWT.HORIZONTAL | SWT.SMOOTH);
		sashForm.SASH_WIDTH = 5;
		mform.getToolkit().adapt(sashForm);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		sashForm.setLayoutData(gd);

		// show the tree view
		Group browserGroup = new Group(sashForm, SWT.NONE);
		browserGroup.setText("Skin Elements");
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 5;
		fillLayout.marginWidth = 5;
		browserGroup.setLayout(fillLayout);
		Tree elementBrowserTree = new Tree(browserGroup, SWT.NONE);
		elementBrowserTreeViewer = new TreeViewer(elementBrowserTree);
		elementBrowserTreeViewer.setContentProvider(new SystemElementsTreeContentProvider());
		elementBrowserTreeViewer.setLabelProvider(new SystemElementsTreeLabelProvider());
		elementBrowserTreeViewer.setInput(entities);
		mform.getToolkit().adapt(browserGroup);

		// show element details view
		Group elementDetailsGroup = new Group(sashForm, SWT.NONE);
		elementDetailsGroup.setText("Element Properties");
		elementDetailsGroup.setLayout(layout);
		elementDetailsCompositeScrollableHolder = new ScrolledComposite(elementDetailsGroup, SWT.V_SCROLL);
		elementDetailsCompositeScrollableHolder.setLayoutData(gd);
		mform.getToolkit().adapt(elementDetailsCompositeScrollableHolder);
		mform.getToolkit().adapt(elementDetailsGroup);

		sashForm.setWeights(new int[] { 40, 60 });

		elementBrowserTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				Object element = selection.getFirstElement();
				try {
					if (element instanceof String) {
						showElementProperties(null, mform);
					} else {
						showElementProperties((LocalElement) element, mform);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});

		elementBrowserTreeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				Object element = selection.getFirstElement();
				if (elementBrowserTreeViewer.isExpandable(element) == true) {
					if (elementBrowserTreeViewer.getExpandedState(element) == false) {
						elementBrowserTreeViewer.expandToLevel(element, 1);
					} else {
						elementBrowserTreeViewer.collapseToLevel(element, 1);
					}
				}
			}

		});
	}

	private void showElementProperties(LocalElement localElement, IManagedForm mform) throws Exception {
		if (elementDetailsComposite != null) {
			elementDetailsComposite.dispose();
		}
		FormToolkit toolkit = mform.getToolkit();
		elementDetailsComposite = mform.getToolkit().createComposite(elementDetailsCompositeScrollableHolder, SWT.NONE);
		toolkit.adapt(elementDetailsComposite);

		if (localElement == null) {
			elementDetailsComposite.setLayout(new GridLayout());

			Label label = toolkit.createLabel(elementDetailsComposite, "Please select the non-folder item in the left"); //$NON-NLS-1$
			label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

			elementDetailsCompositeScrollableHolder.setContent(elementDetailsComposite);
			elementDetailsComposite.layout(true);
			elementDetailsCompositeScrollableHolder.setExpandHorizontal(true);
			elementDetailsCompositeScrollableHolder.setExpandVertical(true);

		} else {
			elementDetailsComposite.setLayout(new GridLayout(2, false));

			createTextField(mform, elementDetailsComposite, (SynProperty) localElement.getProperty(LocalConfig.PROP_KEY_NAME), false);

			if (localElement.hasProperty(LocalConfig.PROP_KEY_DISPLAY_NAME)) {
				createTextField(mform, elementDetailsComposite, (SynProperty) localElement.getProperty(LocalConfig.PROP_KEY_DISPLAY_NAME), false);
			}
			if (localElement.hasProperty(LocalConfig.PROP_KEY_DESCRIPTION)) {
				createTextField(mform, elementDetailsComposite, (SynProperty) localElement.getProperty(LocalConfig.PROP_KEY_DESCRIPTION), true);
			}

			for (Iterator<ISynXSDAttributeDeclaration> iter = localElement.getProperties().iterator(); iter.hasNext();) {
				SynProperty property = (SynProperty) iter.next();
				SynPrimitiveType type = (SynPrimitiveType) property.getTypeDefinition();
				if (type instanceof SynColorType) {
					createColorField(mform, elementDetailsComposite, property);
					continue;
				}
			}

			for (Iterator<LocalParticle> itParticles = localElement.getParticles(true).iterator(); itParticles.hasNext();) {
				LocalParticle particle = itParticles.next();
				createParticleList(mform, elementDetailsComposite, particle);
			}
			elementDetailsCompositeScrollableHolder.setContent(elementDetailsComposite);
			elementDetailsCompositeScrollableHolder.setExpandHorizontal(true);
			elementDetailsCompositeScrollableHolder.setExpandVertical(false);
			elementDetailsComposite.pack(true);
		}
	}

	private void createParticleList(IManagedForm mform, Composite client, final LocalParticle particle) throws Exception {

		FormToolkit toolkit = mform.getToolkit();
		final Label label = toolkit.createLabel(client, particle.getName() + ":"); //$NON-NLS-1$
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		label.setLayoutData(gd);

		List<LocalElement> elements = particle.getElements();
		Iterator<LocalElement> itElements = elements.iterator();

		while (itElements.hasNext()) {
			final LocalElement element = itElements.next();
			Hyperlink hlChildElement = toolkit.createHyperlink(client, element.getName(), SWT.NONE);
			hlChildElement.addHyperlinkListener(new IHyperlinkListener() {

				@Override
				public void linkActivated(HyperlinkEvent e) {
					elementBrowserTreeViewer.setSelection(new StructuredSelection(element), true);
				}

				@Override
				public void linkEntered(HyperlinkEvent e) {
				}

				@Override
				public void linkExited(HyperlinkEvent e) {
				}

			});
			if (itElements.hasNext()) {
				toolkit.createLabel(client, "");
			}
		}
	}

	private void createTextField(IManagedForm mform, Composite client, final SynProperty property, boolean multiLine) throws Exception {
		FormToolkit toolkit = mform.getToolkit();
		final Label label = toolkit.createLabel(client, property.getName() + ":"); //$NON-NLS-1$

		label.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		Text text = null;
		if (multiLine) {
			text = toolkit.createText(client, property.getValue(), SWT.READ_ONLY | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER); //$NON-NLS-1$
			gd.heightHint = 50;
		} else {
			text = toolkit.createText(client, property.getValue(), SWT.READ_ONLY | SWT.NONE);
		}
		text.setLayoutData(gd);
	}

	private void createColorField(IManagedForm mform, Composite client, final SynProperty property) throws Exception {
		FormToolkit toolkit = mform.getToolkit();
		final Label label = toolkit.createLabel(client, property.getName() + ":");
		label.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		CLabel colorLabel = new CLabel(client, SWT.NONE);
		String value = property.getValue();
		if (value == null || value.length() == 0) {
			value = "000000";
		}
		colorLabel.setImage(createImage(value));
		colorLabel.setText("0x" + value);
		mform.getToolkit().adapt(colorLabel, false, false);

		colorLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
	}

	private Image createImage(String colorHexValue) {
		GC gc = null;
		try {
			Image image = new Image(Display.getCurrent(), 15, 15);
			if (colorHexValue != null && colorHexValue.trim().length() != 0) {
				gc = new GC(image);
				gc.setBackground(new Color(Display.getCurrent(), SynColorUtils.StringToRGB(colorHexValue)));
				gc.fillRectangle(0, 0, 14, 14);
				gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
				gc.drawRectangle(0, 0, 14, 14);
			}
			return image;
		} finally {
			if (gc != null) {
				gc.dispose();
			}
		}
	}

	class SystemElementsTreeContentProvider implements ITreeContentProvider {

		private static final String SERIES_COLORS = "Series colors";
		private static final String CHART_COLOR_SETS = "Chart color sets";
		private static final String TEXT_COLOR_SETS = "Text color sets";
		private static final String SKINS = "Skins";

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement == null) {
				return new String[] { DASHBOARD_SYSTEM_SKIN_ELEMENTS };
			}
			if (parentElement instanceof String) {
				if (parentElement.equals(DASHBOARD_SYSTEM_SKIN_ELEMENTS)) {
					return new String[] { SKINS, CHART_COLOR_SETS, TEXT_COLOR_SETS, SERIES_COLORS };
				} else {
					String elementType = null;
					try {
						if (parentElement.equals(SERIES_COLORS)) {
							elementType = BEViewsElementNames.SERIES_COLOR;
						} else if (parentElement.equals(CHART_COLOR_SETS)) {
							elementType = BEViewsElementNames.CHART_COLOR_SET;
						} else if (parentElement.equals(TEXT_COLOR_SETS)) {
							elementType = BEViewsElementNames.TEXT_COLOR_SET;
						} else if (parentElement.equals(SKINS)) {
							elementType = BEViewsElementNames.SKIN;
						}
						List<LocalElement> elements = coreFactory.getChildren(elementType);
						for (Iterator<LocalElement> itElements = elements.iterator(); itElements.hasNext();) {
							LocalElement element = itElements.next();
							boolean exist = false;
							for (Entity entity : entities) {
								if (element.getID().endsWith(entity.getGUID())) {
									exist = true;
									break;
								}
							}
							if (!exist) {
								itElements.remove();
							}
						}
						return elements.toArray(new LocalElement[elements.size()]);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			}
			throw new IllegalArgumentException("Invalid parent element passed" + parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof String) {
				if (element.equals(DASHBOARD_SYSTEM_SKIN_ELEMENTS))
					return null;
				return DASHBOARD_SYSTEM_SKIN_ELEMENTS;
			}
			if (element instanceof LocalElement) {
				String insightType = ((LocalConfig) element).getInsightType();
				if (insightType.equals(BEViewsElementNames.SERIES_COLOR)) {
					return SERIES_COLORS;
				} else if (insightType.equals(BEViewsElementNames.CHART_COLOR_SET)) {
					return CHART_COLOR_SETS;
				} else if (insightType.equals(BEViewsElementNames.TEXT_COLOR_SET)) {
					return TEXT_COLOR_SETS;
				} else if (insightType.equals(BEViewsElementNames.SKIN)) {
					return SKINS;
				}
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object parentElement) {
			if (parentElement == null) {
				return true;
			}
			if (parentElement instanceof String) {
				return true;
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return new String[] { DASHBOARD_SYSTEM_SKIN_ELEMENTS };
		}

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	class SystemElementsTreeLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			if (element instanceof String) {
				if (element.equals(DASHBOARD_SYSTEM_SKIN_ELEMENTS) == true) {
					return DashboardUIPlugin.getInstance().getImageRegistry().get("system_16x16.png");
				}
				return DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_FOLDER);
			} else if (element instanceof LocalElement) {
				return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get((LocalElement)element);
			}
			return super.getImage(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof String) {
				return (String) element;
			}
			if (element instanceof LocalElement) {
				return ((LocalElement) element).getName();
			}
			return "";
		}
	}

}