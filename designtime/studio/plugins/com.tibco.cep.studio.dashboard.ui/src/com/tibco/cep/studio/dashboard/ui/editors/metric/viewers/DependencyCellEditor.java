package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.forms.components.TextAndDialogCellEditor;

public class DependencyCellEditor extends TextAndDialogCellEditor {

	private static final String TITLE = "Auxiliary Fields Selector";

	private static final List<String> ACCEPTABLE_DATATYPES = Arrays.asList(new String[] { PROPERTY_TYPES.INTEGER.getLiteral(), PROPERTY_TYPES.LONG.getLiteral(), PROPERTY_TYPES.DOUBLE.getLiteral() });

	public DependencyCellEditor(Composite comp, String columnName, AbstractSaveableEntityEditorPart editor) {
		super(comp, columnName, editor);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		Table table = (Table) cellEditorWindow.getParent();
		TableItem[] selectedProperty = table.getSelection();
		LocalMetricField localMetricField = (LocalMetricField) selectedProperty[0].getData();
		if (columnName.equals("DependentFields")) {
			try {
				LocalDependencySelectionDialog dependencySelector = new LocalDependencySelectionDialog(editor.getSite().getShell(), localMetricField);
				dependencySelector.open();
				return localMetricField.getDependingFieldString();
			} catch (Exception e) {
				ILog log = DashboardUIPlugin.getInstance().getLog();
				log.log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not edit auxiliary fields on " + localMetricField.getName() + " in " + editor.getEditorInput(), e));
			}
		}
		return null;
	}

	class LocalDependencySelectionDialog extends TitleAreaDialog {

		private static final int RESET_ID = -1;

		private LocalMetric metric;

		private LocalMetricField metricField;

		private List<METRIC_AGGR_TYPE> exposedDependentTypes;

		private Map<String, LocalMetricField> fieldsMap;

		private Map<METRIC_AGGR_TYPE, LocalMetricField> dependentSystemFieldsMap;

		private Map<METRIC_AGGR_TYPE, LocalMetricField> originalFields;

		private Map<METRIC_AGGR_TYPE, Combo> combos;

		private ILog log;

		public LocalDependencySelectionDialog(Shell parentShell, LocalMetricField localMetricField) {
			super(parentShell);
			log = DashboardUIPlugin.getInstance().getLog();
			setHelpAvailable(false);
			setTitleImage(DashboardUIPlugin.getInstance().getImageRegistry().get("fielddependency_wizard.png"));
			// the below statement allows us to make the dialog re-sizable
			setShellStyle(getShellStyle() | SWT.RESIZE);
			// set the metric
			try {
				this.metric = (LocalMetric) localMetricField.getParent();
			} catch (Exception ex) {
				throw new RuntimeException("could not extract parent metric for " + localMetricField.getName() + "...", ex);
			}
			// set the field in question
			this.metricField = localMetricField;
			// extract all the metric fields
			try {
				List<LocalElement> fields = this.metric.getFields();
				fieldsMap = new HashMap<String, LocalMetricField>();
				for (LocalElement field : fields) {
					LocalMetricField metricField = (LocalMetricField) field;
					if (metricField.equals(localMetricField) == false // the field should not be the same as the field in question
							&& metricField.isSystem() == false // field should be non system
							&& metricField.isGroupBy() == false && metricField.isTimeWindowField() == false // the field should not be group by or time window field
							&& ACCEPTABLE_DATATYPES.contains(metricField.getDataType()) == true // the field's datatype is acceptable for dependent field selection
					) {
						fieldsMap.put(metricField.getName(), metricField);
					}
				}
			} catch (Exception ex) {
				log.log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not extract field information for " + this.metric.getName() + "...", ex));
				fieldsMap = null;
			}

			try {
				exposedDependentTypes = metricField.getExposedDependentAggregrateTypes();
			} catch (Exception ex) {
				log.log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not extract auxiliary aggregate information for " + this.metricField.getName() + "...", ex));
				exposedDependentTypes = Collections.emptyList();
			}

			// capture the original fields for restore upon cancel
			try {
				originalFields = new HashMap<METRIC_AGGR_TYPE, LocalMetricField>(localMetricField.getDependingFields());
			} catch (Exception ex) {
				log.log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not extract auxiliary field information for " + this.metric.getName() + ", roll back is disabled...", ex));
				this.originalFields = Collections.emptyMap();
			}

			dependentSystemFieldsMap = new HashMap<METRIC_AGGR_TYPE, LocalMetricField>();
			// Extract system fields from the original fields list
			for (Entry<METRIC_AGGR_TYPE, LocalMetricField> entry : originalFields.entrySet()) {
				if (((LocalMetricField) entry.getValue()).isSystem() == true) {
					dependentSystemFieldsMap.put(entry.getKey(), entry.getValue());
				}
			}

			combos = new HashMap<METRIC_AGGR_TYPE, Combo>();
		}

		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText(TITLE);
			shell.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get("fielddependency_16x16.gif"));
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			Composite dialogComposite = new Composite(composite, SWT.NONE);
			GridLayout gridLayout = new GridLayout(3, false);
			dialogComposite.setLayout(gridLayout);
			dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
			final String[] names;
			if (fieldsMap == null || fieldsMap.isEmpty() == true) {
				names = new String[] {/* SYSTEM_FIELD */};
			} else {
				names = new String[fieldsMap.size()/* +1 */];
				fieldsMap.keySet().toArray(names);
				/* names[names.length-1] = SYSTEM_FIELD; */

			}
			try {
				Label lbl = new Label(dialogComposite, SWT.NONE);
				lbl.setText(metricField.getName() + " has aggregation type set as " + metricField.getAggregationFunction());
				lbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1));
				lbl = new Label(dialogComposite, SWT.NONE);
				lbl.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 3, 1));
				for (final METRIC_AGGR_TYPE exposedAggregateType : exposedDependentTypes) {
					// label
					lbl = new Label(dialogComposite, SWT.NONE);
					lbl.setText("Designate ");
					GridData gridData = new GridData(SWT.END, SWT.CENTER, false, false);
					gridData.horizontalIndent = 25;
					lbl.setLayoutData(gridData);
					// combo
					final Combo combo = new Combo(dialogComposite, SWT.NONE | SWT.READ_ONLY);
					combo.setItems(names);
					combo.setEnabled(names.length != 0);
					LocalMetricField dependentField = (LocalMetricField) metricField.getDependingFields().get(exposedAggregateType);
					if (dependentField != null) {
						if (dependentField.isSystem() == false) {
							combo.setText(dependentField.getName());
						} else {
							/* combo.setText(SYSTEM_FIELD); */
						}
					}
					combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
					combo.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}

						@Override
						public void widgetSelected(SelectionEvent e) {
							int idx = combo.getSelectionIndex();
							if (idx != -1) {
								try {
									// remove the existing dependent field
									metricField.removeDependingField(exposedAggregateType);
									// check if the user selected system generated field
									LocalMetricField existingField = fieldsMap.get(names[idx]);
									if (existingField == null) {
										// we are dealing with system generated field
										existingField = dependentSystemFieldsMap.get(exposedAggregateType);
										if (existingField == null) {
											existingField = metric.createSystemField(metricField, exposedAggregateType);
											dependentSystemFieldsMap.put(exposedAggregateType, existingField);
										}
									}
									metricField.addDependingField(exposedAggregateType, existingField);
									validate(true);
								} catch (Exception ex) {
									setErrorMessage("could not add " + names[idx] + " as auxiliary field...");
								}
							}
						}

					});

					combos.put(exposedAggregateType, combo);

					lbl = new Label(dialogComposite, SWT.NONE);
					lbl.setText(" as the " + exposedAggregateType + " field ");
					gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
					lbl.setLayoutData(gridData);
				}
				if (fieldsMap == null) {
					setErrorMessage("could not fetch the aggregate fields...");
				} else if (fieldsMap.isEmpty() == true) {
					setErrorMessage("No suitable aggregate fields were found...");
				} else if (exposedDependentTypes.size() == 0) {
					setErrorMessage("could not fetch all the auxiliary aggregate types...");
				} else {
					validate(false);
					setMessage("Select new fields as auxiliary fields for " + metricField.getName());
				}
			} catch (Exception e) {
				setErrorMessage("could not determine auxiliary aggregate types...");
				e.printStackTrace();
			}
			return composite;
		}

		@Override
		protected Control createButtonBar(Composite parent) {
			Control control = super.createButtonBar(parent);
			if (getButton(IDialogConstants.OK_ID) != null) {
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			}
			if (getButton(RESET_ID) != null) {
				getButton(RESET_ID).setEnabled(dependentSystemFieldsMap.isEmpty());
			}
			return control;
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			Button replaceButton = createButton(parent, RESET_ID, "Reset", false);
			super.createButtonsForButtonBar(parent);
			replaceButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					for (METRIC_AGGR_TYPE exposedAggregateType : exposedDependentTypes) {
						// remove the existing dependent field
						metricField.removeDependingField(exposedAggregateType);
						// check if we have a system generated field
						LocalMetricField existingField = dependentSystemFieldsMap.get(exposedAggregateType);
						if (existingField == null) {
							existingField = metric.createSystemField(metricField, exposedAggregateType);
							dependentSystemFieldsMap.put(exposedAggregateType, existingField);
						}
						metricField.addDependingField(exposedAggregateType, existingField);
						combos.get(exposedAggregateType).deselectAll();
					}
					validate(true);
				}

			});
		}

		private void validate(boolean showPrompt) {
			setErrorMessage(null);
			boolean valid = metricField.checkDependencyFields(true);
			if (valid == false) {
				setErrorMessage(metricField.getValidationMessage().getMessage());
				return;
			}
			// lets check if the exposed dependent fields are system-generated fields
			int numOfSysDependentFldsInUse = 0;

			for (METRIC_AGGR_TYPE exposedDependentType : exposedDependentTypes) {
				if (((LocalMetricField) metricField.getDependingFields().get(exposedDependentType)).isSystem() == true) {
					numOfSysDependentFldsInUse++;
				}
			}
			// do we have any system dependent fields
			if (numOfSysDependentFldsInUse > 0) {
				// we only allow all or none
				if (numOfSysDependentFldsInUse != exposedDependentTypes.size() && showPrompt == true) {
					// setErrorMessage(metricField.getName()+" is using "+numOfSysDependentFldsInUse+" system generated field(s)");
					setMessage("Select new fields as auxiliary fields for " + metricField.getName());
					if (getButton(IDialogConstants.OK_ID) != null) {
						getButton(IDialogConstants.OK_ID).setEnabled(false);
					}
					return;
				}
			}
			if (showPrompt == true) {
				setMessage("All field requirements have been met; press OK to finish.");
			}
			if (getButton(RESET_ID) != null) {
				getButton(RESET_ID).setEnabled(numOfSysDependentFldsInUse == 0);
			}
			if (getButton(IDialogConstants.OK_ID) != null) {
				// check if the current list of dependent fields matches the original fields
				if (metricField.getDependingFields().equals(originalFields) == true) {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		}

		protected void cancelPressed() {
			if (metricField.getDependingFields().equals(originalFields) == false) {
				try {
					metricField.setDependingFields(originalFields);
				} catch (Exception ex) {
					log.log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not reset auxiliary field information for " + this.metric.getName() + "...", ex));
				}
			}
			super.cancelPressed();
		}

		@Override
		protected void okPressed() {
			Collection<LocalMetricField> dependentFields = metricField.getDependingFields().values();
			Collection<LocalMetricField> systemDependentFields = dependentSystemFieldsMap.values();
			systemDependentFields.removeAll(dependentFields);
			for (LocalElement unwantedSystemField : systemDependentFields) {
				try {
					metric.removeElement(LocalMetric.ELEMENT_KEY_FIELD, unwantedSystemField.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
				} catch (Exception ex) {
					log.log(new Status(IStatus.WARNING, DashboardUIPlugin.PLUGIN_ID, "could not remove unwanted system field [" + unwantedSystemField.getName() + "] in " + this.metric.getName() + "...", ex));
				}
			}
			super.okPressed();
		}

		@Override
		public void setErrorMessage(String newErrorMessage) {
			super.setErrorMessage(newErrorMessage);
			if (getButton(IDialogConstants.OK_ID) != null) {
				getButton(IDialogConstants.OK_ID).setEnabled(newErrorMessage == null);
			}
		}

	}
}
