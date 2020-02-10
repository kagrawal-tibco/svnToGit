package com.tibco.cep.studio.dashboard.ui.wizards.metric;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;

/**
 */
public class MetricFieldModificationWizardPage extends WizardPage {

	public static enum FIELD_TYPE { GROUP_BY , AGGR };

	private FIELD_TYPE fieldType;

	private AttributeViewer attributeViewer;

	//toolkit to use with groupByFieldViewer
	private FormToolkit toolKit;

	private LocalMetric metric;

	private boolean processFieldModifications;

	private boolean processDescModifications;

	private LocalMetricFieldAdditionListener metricFieldAdditionListener;

	protected MetricFieldModificationWizardPage(FIELD_TYPE fieldType) {
		super(MetricFieldModificationWizardPage.class.getName());
		this.fieldType = fieldType;
		this.processFieldModifications = true;
		this.processDescModifications = true;
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout());

		pageComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		toolKit = new FormToolkit(Display.getCurrent());

		if (fieldType.compareTo(FIELD_TYPE.GROUP_BY) == 0) {
			attributeViewer = new GroupByFieldViewer(toolKit, pageComposite, true, LocalMetric.ELEMENT_KEY_FIELD);
		}
		else {
			attributeViewer = new AggregateFieldViewer(toolKit, pageComposite, true, LocalMetric.ELEMENT_KEY_FIELD);
		}

		attributeViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Group grpDesc = new Group(pageComposite, SWT.NONE);
		grpDesc.setText("Description");
		FillLayout grpDescLayout = new FillLayout();
		grpDescLayout.marginHeight = grpDescLayout.marginWidth = 5;
		grpDesc.setLayout(grpDescLayout);

		final Text txt_Desc = new Text(grpDesc, SWT.BORDER | SWT.V_SCROLL);

		grpDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		attributeViewer.getTableViewer().addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (processFieldModifications == true && element instanceof LocalMetricField) {
					LocalMetricField metricField = (LocalMetricField) element;
					boolean isGroupBy = metricField.isGroupBy();
					if (fieldType.compareTo(FIELD_TYPE.GROUP_BY) == 0 && isGroupBy == true) {
						return true;
					}
					if (fieldType.compareTo(FIELD_TYPE.AGGR) == 0 && isGroupBy == false) {
						return true;
					}
				}
				return false;
			}
		});

		attributeViewer.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				processDescModifications = false;
				try {
					txt_Desc.setText("");
					txt_Desc.setEnabled(false);
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					Object[] fields = selection.toArray();
					if (fields != null && fields.length == 1) {
						LocalMetricField field = (LocalMetricField) fields[0];
						txt_Desc.setText(field.getDescription());
						txt_Desc.setEnabled(true);
					}
				} finally {
					processDescModifications = true;
				}
			}
		});

		txt_Desc.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (processDescModifications == false) {
					return;
				}
				IStructuredSelection selection = (IStructuredSelection) attributeViewer.getTableViewer().getSelection();
				LocalMetricField field = (LocalMetricField) selection.getFirstElement();
				field.setDescription(txt_Desc.getText());
			}
		});

		setControl(pageComposite);
	}

	public void setLocalMetric(LocalMetric metric) {
		this.metric = metric;
		try {
			attributeViewer.setLocalElement(this.metric);
			metricFieldAdditionListener = new LocalMetricFieldAdditionListener();
			this.metric.subscribeToAll(metricFieldAdditionListener);
			this.metric.subscribeForPropertyChange(metricFieldAdditionListener, LocalMetricField.PROP_KEY_IS_GROUP_BY);
			this.metric.subscribeForPropertyChange(metricFieldAdditionListener, LocalMetricField.PROP_KEY_AGG_FUNCTION);
			this.metric.subscribeForPropertyChange(metricFieldAdditionListener, LocalMetricField.PROP_KEY_DATA_TYPE);
			this.metric.subscribeForPropertyChange(metricFieldAdditionListener, LocalElement.PROP_KEY_NAME);

			for (LocalElement field : this.metric.getChildren(LocalMetric.ELEMENT_KEY_FIELD)) {
				LocalMetricField metricField = (LocalMetricField) field;
				boolean isGroupBy = metricField.isGroupBy();
				if (fieldType.compareTo(FIELD_TYPE.GROUP_BY) == 0 && isGroupBy == true) {
					attributeViewer.getTableViewer().setSelection(new StructuredSelection(field));
					break;
				}
				if (fieldType.compareTo(FIELD_TYPE.AGGR) == 0 && isGroupBy == false) {
					attributeViewer.getTableViewer().setSelection(new StructuredSelection(field));
					break;
				}
			}
			validate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		if (this.metric != null && this.metricFieldAdditionListener != null) {
			this.metric.unsubscribeForPropertyChange(metricFieldAdditionListener, LocalMetricField.PROP_KEY_IS_GROUP_BY);
			this.metric.unsubscribeForPropertyChange(metricFieldAdditionListener, LocalMetricField.PROP_KEY_AGG_FUNCTION);
			this.metric.unsubscribeForPropertyChange(metricFieldAdditionListener, LocalMetricField.PROP_KEY_DATA_TYPE);
			this.metric.unsubscribeForPropertyChange(metricFieldAdditionListener, LocalElement.PROP_KEY_NAME);
			this.metric.unsubscribeAll(metricFieldAdditionListener);
		}
		if (toolKit != null) {
			toolKit.dispose();
		}
		super.dispose();
	}

	public void setProcessFieldModifications(boolean processFieldModifications) {
		this.processFieldModifications = processFieldModifications;
	}

	private void validate() {
		String msg = null;
		boolean valid = true;
		try {
			for (LocalElement field : this.metric.getChildren(LocalMetric.ELEMENT_KEY_FIELD)) {
				LocalMetricField metricField = (LocalMetricField) field;
				boolean isGroupBy = metricField.isGroupBy();
				if (fieldType.compareTo(FIELD_TYPE.GROUP_BY) == 0 && isGroupBy == true) {
					valid = metricField.isValid();
					if (valid == false) {
						msg = metricField.getValidationMessage().getMessage();
						break;
					}
				}
				if (fieldType.compareTo(FIELD_TYPE.AGGR) == 0 && isGroupBy == false) {
					valid = metricField.isValid();
					if (valid == false) {
						msg = metricField.getValidationMessage().getMessage();
						break;
					}
				}
			}
			if (valid == true) {
				LocalParticle fieldParticle = metric.getParticle(LocalMetric.ELEMENT_KEY_FIELD);
				valid = fieldParticle.isValid();
				if (valid == false) {
					msg = fieldParticle.getValidationMessage().getMessage();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			valid = false;
		} finally {
			setErrorMessage(msg);
			setPageComplete(valid);
		}
	}

	class LocalMetricFieldAdditionListener implements ISynElementChangeListener {

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			if (processFieldModifications == true) {
				validate();
			}
		}

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			if (processFieldModifications == true && fieldType.compareTo(FIELD_TYPE.GROUP_BY) == 0) {
				if (newElement instanceof LocalMetricField) {
					LocalMetricField metricField = (LocalMetricField) newElement;
					//convert the element to group by
					metricField.setGroupBy(true);
					metricField.setGroupByPosition(metric.getMaxGroupByPosition());
					metricField.setDataType(PROPERTY_TYPES.STRING.getLiteral());
				}
				validate();
			}
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			//do nothing
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			//do nothing
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			//do nothing
		}

		@Override
		public String getName() {
			return "Metric Field Modification Wizard Page Listener";
		}

	}

}