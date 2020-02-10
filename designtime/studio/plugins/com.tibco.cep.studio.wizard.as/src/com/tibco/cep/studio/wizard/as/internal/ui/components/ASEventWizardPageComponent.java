package com.tibco.cep.studio.wizard.as.internal.ui.components;

import static com.tibco.cep.designtime.core.model.PROPERTY_TYPES.BOOLEAN;
import static com.tibco.cep.designtime.core.model.PROPERTY_TYPES.DATE_TIME;
import static com.tibco.cep.designtime.core.model.PROPERTY_TYPES.DOUBLE;
import static com.tibco.cep.designtime.core.model.PROPERTY_TYPES.INTEGER;
import static com.tibco.cep.designtime.core.model.PROPERTY_TYPES.STRING;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_PROPTYPES_BOOLEAN_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_PROPTYPES_DATETIME_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_PROPTYPES_DOUBLE_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_PROPTYPES_INTEGER_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_PROPTYPES_LONG_16X16;
import static com.tibco.cep.studio.wizard.as.ASConstants._IMAGE_PROPTYPES_STRING_16X16;
import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.getEAttribute;
import static com.tibco.cep.studio.wizard.as.internal.utils.UIUtils.createDecorator;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel._PROP_NAME_SIMPLE_EVENT;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.Modify;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.SINGLE;
import static org.eclipse.swt.layout.GridData.HORIZONTAL_ALIGN_END;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.StringNotEmptyValidator;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.ValidatorGroup;
import com.tibco.cep.studio.wizard.as.internal.beans.databinding.validators.ResourceNameValidator;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;

public class ASEventWizardPageComponent extends Composite {

//	private static final String[]        _TABLE_COLUMN_NAMES = { " ", "Name", "Type" };

	// Controller
	private IASEventWizardPageController controller;

	// Resources
	private Map<PROPERTY_TYPES, Image>   images;

	// UI
	private Composite                    container;
	private Label                        nameLabel;
	private Text                         nameText;
//  private TableViewer tableViewer;
	private ControlDecoration            nameTextNotEmptyDecoration;
	private ControlDecoration            nameTextResNameDecoration;

	// Bindings
	private DataBindingContext           bc;
	private Binding                      eventNameBinding;

	// Handler
	private Handler                      handler;


	public ASEventWizardPageComponent(Composite parent, IASEventWizardPageController controller) {
		super(parent, NONE);
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		initResources();
		initUI();
		attachBindings();
		initListeners();
		initData();
	}

	private void initResources() {
		images = new HashMap<PROPERTY_TYPES, Image>();
		images.put(INTEGER, StudioUIPlugin.getDefault().getImage(_IMAGE_PROPTYPES_INTEGER_16X16));
		images.put(PROPERTY_TYPES.LONG, StudioUIPlugin.getDefault().getImage(_IMAGE_PROPTYPES_LONG_16X16));
		images.put(STRING, StudioUIPlugin.getDefault().getImage(_IMAGE_PROPTYPES_STRING_16X16));
		images.put(DOUBLE, StudioUIPlugin.getDefault().getImage(_IMAGE_PROPTYPES_DOUBLE_16X16));
		images.put(DATE_TIME, StudioUIPlugin.getDefault().getImage(_IMAGE_PROPTYPES_DATETIME_16X16));
		images.put(BOOLEAN, StudioUIPlugin.getDefault().getImage(_IMAGE_PROPTYPES_BOOLEAN_16X16));
	}

	private void initUI() {
		this.setLayout(new FillLayout());
		container = new Composite(this, NONE);
		createComponentsWithGridLayout();
	}

	private void createComponentsWithGridLayout() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 8;
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		container.setLayout(layout);

		// Name
		nameLabel = new Label(container, NONE);
		GridData layoutData = new GridData(HORIZONTAL_ALIGN_END);
		nameLabel.setLayoutData(layoutData);
		nameText = new Text(container, SINGLE | BORDER);
		layoutData = new GridData(FILL, FILL, true, false);
		nameText.setLayoutData(layoutData);
		nameTextNotEmptyDecoration = createDecorator(nameText, Messages.getString("ASEventWizardPageComponent.non_null_validation", Messages.getString("ASEventWizardPageComponent.prop_name"))); //$NON-NLS-1$
		nameTextResNameDecoration = createDecorator(nameText, Messages.getString("ASEventWizardPageComponent.res_name_validation", Messages.getString("ASEventWizardPageComponent.prop_name"))); //$NON-NLS-1$ $NON-NLS-2$

		// Event
//		createTableViewer(container);
	}

	private void attachBindings() {
		bc = new DataBindingContext();
	}

	private void initListeners() {
		handler = new Handler();

		controller.getModel().addPropertyChangeListener(_PROP_NAME_SIMPLE_EVENT, handler);
	}

	private void initData() {
		nameLabel.setText(Messages.getString("ASEventWizardPageComponent.label_name")); //$NON-NLS-1$
	}

//	private void createTableViewer(Composite parent) {
//		Composite tablePane = new Composite(parent, NONE);
//		FillLayout layout = new FillLayout();
//		// layout.marginHeight = 5;
//		// layout.marginWidth = 5;
//		tablePane.setLayout(layout);
//		GridData layoutData = new GridData(FILL_BOTH);
//		layoutData.horizontalSpan = 2;
//		tablePane.setLayoutData(layoutData);
//
//		Table table = new Table(tablePane, CHECK | MULTI | FULL_SELECTION | BORDER | V_SCROLL | H_SCROLL);
//		TableLayout tableLayout = new TableLayout();
//		table.setLayout(tableLayout);
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
//		createColumns(table);
//		tableViewer = new CheckboxTableViewer(table);
//		tableViewer.setContentProvider(new SimpleEventContentProvider());
//		tableViewer.setLabelProvider(new SimpleEventLabelProvider());
//		// tableViewer.setComparator(new ViewerComparator(new
//		// SimpleEventPropertyComparator()));
//	}

//	private void createColumns(Table table) {
//		for (String colName : _TABLE_COLUMN_NAMES) {
//			TableColumn tc = new TableColumn(table, NONE);
//			// tc.setAlignment(SWT.CENTER);
//			tc.setText(colName);
//			tc.pack();
//		}
//	}

	private void reAttachEventNameBinding(SimpleEvent event) {
		unattachSpecifiedBindings();

		String propName = "name"; //$NON-NLS-1$
		EList<EAttribute> allEventAttributes = EventFactory.eINSTANCE.getEventPackage().getEvent().getEAllAttributes();
		EAttribute nameAttribute = getEAttribute(allEventAttributes, propName);
		IObservableValue eventNameObValue = EMFObservables.observeValue(event, nameAttribute);

		IObservableValue nameTextObValue = SWTObservables.observeText(nameText, Modify);
		UpdateValueStrategy updateStrategy = new UpdateValueStrategy();
		IValidator strNotEmptyValidator = new StringNotEmptyValidator(Messages.getString("ASEventWizardPageComponent.non_null_validation"), nameTextNotEmptyDecoration);
		IValidator resNameValidator = new ResourceNameValidator("ASEventWizardPageComponent.res_name_validation", nameTextResNameDecoration);
		IValidator combinedValidator = new ValidatorGroup(Arrays.asList(strNotEmptyValidator, resNameValidator));
		updateStrategy.setAfterConvertValidator(combinedValidator); //$NON-NLS-1$
		eventNameBinding = bc.bindValue(nameTextObValue, eventNameObValue, updateStrategy, null);
	}

//	private void refreshTable(SimpleEvent event) {
//		tableViewer.setInput(event);
//		TableColumn[] tableCols = tableViewer.getTable().getColumns();
//		for (TableColumn tableCol : tableCols) {
//			tableCol.pack();
//		}
//	}

	private void unattachSpecifiedBindings() {
		if (null != eventNameBinding) {
			eventNameBinding.dispose();
			eventNameBinding = null;
		}
	}

	private class Handler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			SimpleEvent newEvent = (SimpleEvent) evt.getNewValue();
			if (null != newEvent) {
//				refreshTable(newEvent);
				reAttachEventNameBinding(newEvent);
			}
		}

	}

//	private class SimpleEventContentProvider implements IStructuredContentProvider {
//
//		@Override
//		public void dispose() {
//		}
//
//		@Override
//		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
//		}
//
//		@Override
//		public Object[] getElements(Object inputElement) {
//			Object[] elements = null;
//			if (inputElement instanceof SimpleEvent) {
//				SimpleEvent event = (SimpleEvent) inputElement;
//				List<Object[]> props = convertSimpleEventProperties(event);
//				elements = props.toArray();
//			}
//			return elements;
//		}
//
//	}
//
//	private class SimpleEventLabelProvider implements ITableLabelProvider {
//
//		@Override
//		public void addListener(ILabelProviderListener listener) {
//		}
//
//		@Override
//		public void dispose() {
//		}
//
//		@Override
//		public boolean isLabelProperty(Object element, String property) {
//			return false;
//		}
//
//		@Override
//		public void removeListener(ILabelProviderListener listener) {
//
//		}
//
//		@Override
//		public Image getColumnImage(Object element, int columnIndex) {
//			Image image = null;
//			if (2 == columnIndex) {
//				Object[] row = (Object[]) element;
//				PROPERTY_TYPES type = (PROPERTY_TYPES) row[2];
//				image = images.get(type);
//			}
//			return image;
//		}
//
//		@Override
//		public String getColumnText(Object element, int columnIndex) {
//			String text = "";
//			if (0 != columnIndex) {
//				Object[] row = (Object[]) element;
//				text = row[columnIndex].toString();
//			}
//			return text;
//		}
//
//	}

}
