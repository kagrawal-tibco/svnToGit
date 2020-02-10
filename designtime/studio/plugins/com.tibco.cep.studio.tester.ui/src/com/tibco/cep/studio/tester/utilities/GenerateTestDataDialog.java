package com.tibco.cep.studio.tester.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.utilities.GenerateTaskModel.GenerationOptions;
import com.tibco.cep.studio.tester.utilities.GenerateTaskModel.GenerationType;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class GenerateTestDataDialog extends Dialog {

	private Entity fOwnerEntity;

	private Table propTable;
	private Label propMessageLabel;
	private GenerateTaskModel generationTask;
	private boolean[] hasErrors = { false, false, false, false, false };
	
	private Group generationOptionsGroup;

	private Composite errorComposite;
	
	public GenerateTestDataDialog(Shell parentShell, Entity ownerEntity) {
		super(parentShell);
		this.fOwnerEntity = ownerEntity;
		this.generationTask = new GenerateTaskModel();
	}

	@Override
	protected Control createContents(Composite parent) {
		return super.createContents(parent);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("Generate Random Test Data");
		Composite composite = (Composite) super.createDialogArea(parent);

		((GridLayout)composite.getLayout()).numColumns = 2;
		Label ownerEntityLabel = new Label(composite, SWT.NULL);
		ownerEntityLabel.setText("Owner Entity:");
		
		Text ownerEntityText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		ownerEntityText.setText(fOwnerEntity.getFullPath());
		ownerEntityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createPropertiesTable(composite);

		Label rowsLabel = new Label(composite, SWT.NULL);
		rowsLabel.setText("Number of rows to generate:");
		final Text numRowsText = new Text(composite, SWT.BORDER);
		numRowsText.setText(String.valueOf(this.generationTask.getNumRows()));
		numRowsText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		numRowsText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				try {
					int i = Integer.valueOf(numRowsText.getText());
					generationTask.setNumRows(i);
					hasErrors[0] = false;
					setMessage(null);
				} catch (NumberFormatException e) {
					hasErrors[0] = true;
					setMessage("Invalid number of rows");
					return;
				}
			}
		});
		generationOptionsGroup = new Group(composite, SWT.NONE);
		generationOptionsGroup.setText("Generation Details");
		GridLayout layout = new GridLayout(2, true);
		generationOptionsGroup.setLayout(layout);
		generationOptionsGroup.setBackground(ColorConstants.white);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.minimumHeight = 270;
		generationOptionsGroup.setLayoutData(data);
		
		errorComposite = new Composite(composite, SWT.NONE);
		errorComposite.setLayout(new GridLayout(2, false));
		GridData ec = new GridData(GridData.FILL_HORIZONTAL);
		ec.horizontalSpan = 2;
		errorComposite.setLayoutData(ec);
		errorComposite.setVisible(false);
		Label imageLabel = new Label(errorComposite, SWT.NONE);
		imageLabel.setImage(EditorsUIPlugin.getDefault().getImage(
				"/icons/error_mark.png"));
		propMessageLabel = new Label(errorComposite, SWT.NONE);
		propMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		return composite;
	}

	private void setMessage(String label) {
		if (label == null || label.trim().length() == 0) {
			errorComposite.setVisible(false);
			return;
		}
		errorComposite.setVisible(true);
		propMessageLabel.setText(label);
	}
	
	private void createPropertiesTable(Composite composite) {
    	propTable = new Table(composite, SWT.CHECK | SWT.BORDER | SWT.FULL_SELECTION);
    	GridData data = new GridData(GridData.FILL_HORIZONTAL);
    	data.horizontalSpan = 2;
    	data.heightHint = 150;
    	data.widthHint = 470;
    	propTable.setLayoutData(data);
    	propTable.setHeaderVisible(true);
    	
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(propTable);
		autoTableLayout.addColumnData(new ColumnWeightData(2));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(2));
//		autoTableLayout.addColumnData(new ColumnWeightData(5));
		propTable.setLayout(autoTableLayout);
		
		TableColumn nameColumn = new TableColumn(propTable, SWT.NONE);
		nameColumn.setText("Property Name");
		
    	TableColumn typeColumn = new TableColumn(propTable, SWT.NONE);
    	typeColumn.setText("Type");

    	TableColumn domainColumn = new TableColumn(propTable, SWT.NONE);
    	domainColumn.setText("Domains");

    	populatePropTable();
	}

	private void populatePropTable() {
		List<PropertyDefinition> properties = getEntityProperties();
		generationTask.getOptions().clear();
		for (final PropertyDefinition prop : properties) {
			if (prop.getType() == PROPERTY_TYPES.CONCEPT || prop.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				continue;
			}
			generationTask.getOptions().put(prop, generationTask.new GenerationOptions(prop));
			TableItem tableItem=new TableItem(propTable,SWT.NONE);
			tableItem.setData(prop);
			tableItem.setChecked(true);
			TableEditor editor = new TableEditor (propTable);
			editor.horizontalAlignment = SWT.LEFT;
			editor.grabHorizontal = true;
			editor.minimumWidth = 50;
			
			propTable.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					// Identify the selected row
					TableItem item = (TableItem)e.item;
					if (item == null) return;
					propTable.setSelection(item);
					
					Object data = item.getData();
					if (data instanceof PropertyDefinition) {
						updateGenerationOptions((PropertyDefinition)data);
					}
					
					// update enabled state for all options
					Map<PropertyDefinition, GenerationOptions> options = generationTask.getOptions();
					TableItem[] items = propTable.getItems();
					for (TableItem it : items) {
						Object d = it.getData();
						if (d instanceof PropertyDefinition) {
							options.get(d).setEnabled(it.getChecked());
						}
					}
				}
			});
			
			tableItem.setText(0, prop.getName());
			tableItem.setText(1, prop.getType().getName());
			EList<DomainInstance> domainInstances = prop.getDomainInstances();
			if (domainInstances.size() > 0) {
				StringBuilder builder = new StringBuilder();
				for (DomainInstance domainInstance : domainInstances) {
					builder.append(domainInstance.getResourcePath()).append(':');
				}
				tableItem.setText(2, builder.substring(0, builder.length()-1));
			}
		}		
	}

	protected void updateGenerationOptions(final PropertyDefinition data) {
		Control[] children = this.generationOptionsGroup.getChildren();
		for (Control ch : children) {
			ch.dispose();
		}
		Composite c = new Composite(this.generationOptionsGroup, SWT.NONE);
		c.setLayout(new GridLayout(2, false));
		GridData d = new GridData(GridData.FILL_BOTH);
		d.horizontalSpan = 2;
		c.setLayoutData(d);
		
		// update options based on property type
		GenerationOptions op = this.generationTask.getOptions().get(data);
		if (op == null) {
			op = this.generationTask.new GenerationOptions(data);
			this.generationTask.getOptions().put(data, op);
		}
		final GenerationOptions options = op;
		
		final Label genOptionLabel = new Label(c, SWT.READ_ONLY);
		genOptionLabel.setText("Generation Method:");

		final Combo typeCombo = new Combo(c, SWT.READ_ONLY);
		GridData typeData = new GridData(GridData.FILL_HORIZONTAL);
		typeCombo.setLayoutData(typeData);
		typeCombo.setItems(getGenerationMethods(data));

		final Composite parent = new Composite(c, SWT.NONE);
		parent.setLayout(new GridLayout(3, false));
		GridData dat = new GridData(GridData.FILL_BOTH);
		dat.horizontalSpan = 2;
		parent.setLayoutData(dat);

		typeCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				options.setGenerationType(GenerationType.valueOf(typeCombo.getText().toUpperCase()));
				_updateGenerationOptions(parent, data, options);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		if (options.getGenerationType() != null) {
			typeCombo.setText(options.getGenerationType().getDispName());
		} else {
			typeCombo.select(0);
		}

		_updateGenerationOptions(parent, data, options);
	}

	private void _updateGenerationOptions(Composite parent, final PropertyDefinition data, final GenerationOptions options) {
		for (Control ch : parent.getChildren()) {
			ch.dispose();
		}

		final GenerationType generationType = options.getGenerationType();
		
		if (!(generationType == GenerationType.CONSTANT || generationType == GenerationType.ENUMERATE || data.getDomainInstances().size() > 0) && data.getType() != PROPERTY_TYPES.BOOLEAN) {
			final Label minLabel = new Label(parent, SWT.READ_ONLY);
			
			final Text minText = new Text(parent, SWT.BORDER);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.minimumWidth = 150;
			gridData.widthHint = 150;
			minText.setLayoutData(gridData);
			minText.setText(String.valueOf(options.getMin()));
			minText.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					String text = minText.getText();
					try {
						if (data.getType() == PROPERTY_TYPES.DATE_TIME) {
							SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
							Date format = sdf.parse(text);
							setMessage(format.toString());
						} else if (data.getType() == PROPERTY_TYPES.INTEGER || data.getType() == PROPERTY_TYPES.DOUBLE || data.getType() == PROPERTY_TYPES.LONG || data.getType() == PROPERTY_TYPES.STRING) {
							double val = Double.parseDouble(text);
							if (options.getMax() != null && options.getMax().trim().length() > 0) {
								double max = Double.parseDouble(options.getMax());
								if (val > max) {
									hasErrors[1] = true;
									setMessage("Minimum value for "+ data.getName()+" must be less than the maximum value");
									return;
								}
							}
						}
					} catch (NumberFormatException | ParseException e) {
						hasErrors[1] = true;
						setMessage("Invalid minimum value for "+ data.getName());
						return;
					}
					hasErrors[1] = false;
					setMessage(null);
					options.setMin(text);
				}
			});
			if (data.getType() == PROPERTY_TYPES.DATE_TIME) {
				final Button selectDateButton = new Button(parent, SWT.NONE);
				selectDateButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						DateTimeDialog d = new DateTimeDialog(getShell(), null);
						int open = d.open();
//						if (open == Dialog.OK) {
							String returnCurrentDate = d.ReturnCurrentDate();
							if (returnCurrentDate != null) {
								minText.setText(returnCurrentDate);
							}
//						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			} else {
				((GridData)minText.getLayoutData()).horizontalSpan = 2;
			}
			
			final Label maxLabel = new Label(parent, SWT.READ_ONLY);
			
			final Text maxText = new Text(parent, SWT.BORDER);
			maxText.setText(String.valueOf(options.getMax()));
			GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
			gridData1.minimumWidth = 110;
			gridData1.widthHint = 100;
			maxText.setLayoutData(gridData1);

			maxText.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					String text = maxText.getText();
					try {
						if (data.getType() == PROPERTY_TYPES.DATE_TIME) {
							SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
							Date format = sdf.parse(text);
							setMessage(format.toString());
						} else {
							double val = Double.valueOf(text);
							if (options.getMin() != null && options.getMin().trim().length() > 0) {
								double min = Double.parseDouble(options.getMin());
								if (val < min) {
									hasErrors[2] = true;
									setMessage("Maximum value for "+ data.getName()+" must be greater than the minimum value");
									return;
								}
							}
						}
					} catch (NumberFormatException e) {
						setMessage("Invalid maximum value for "+ data.getName());
						hasErrors[2] = true;
						return;
					} catch (ParseException e) {
						setMessage("Invalid maximum value for "+ data.getName());
						hasErrors[2] = true;
						return;
					}
					hasErrors[2] = false;
					setMessage(null);
					options.setMax(text);
				}
			});
			if (data.getType() == PROPERTY_TYPES.DATE_TIME) {
				final Button selectDateButton = new Button(parent, SWT.NONE);
				selectDateButton.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						DateTimeDialog d = new DateTimeDialog(getShell(), null);
						int open = d.open();
//						if (open == Dialog.OK) { // doesn't return OK?
							String returnCurrentDate = d.ReturnCurrentDate();
							if (returnCurrentDate != null) {
								maxText.setText(returnCurrentDate);
							}
//						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			} else {
				((GridData)maxText.getLayoutData()).horizontalSpan = 2; 
			}
			PROPERTY_TYPES type = data.getType();
			switch (type) {
			case DATE_TIME: {
		        SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);

				if (options.getMin() == null || options.getMin().length() == 0) {
					options.setMin(sdf.format(new Date()));
				}
				if (options.getMax() == null || options.getMax().length() == 0) {
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.YEAR, 1);
					options.setMax(sdf.format(c.getTime()));
				}

				minLabel.setText("Minimum date:");
				maxLabel.setText("Maximum date:");
				break;
			}
				
			case STRING: {
				if (options.getMin() == null || options.getMin().length() == 0) {
					options.setMin("4");
				}
				if (options.getMax() == null || options.getMax().length() == 0) {
					options.setMax("4");
				}
				minLabel.setText("Minimum length:");
				minText.setText(options.getMin());
				maxLabel.setText("Maximum length:");
				maxText.setText(options.getMax());
				break;
			}

			case DOUBLE:{
				if (options.getMin() == null || options.getMin().length() == 0) {
					options.setMin("0.0");
				}
				if (options.getMax() == null || options.getMax().length() == 0) {
					options.setMax("10000.0");
				}
				minLabel.setText("Minimum value:");
				minText.setText(options.getMin());
				maxLabel.setText("Maximum value:");
				maxText.setText(options.getMax());
				break;
			}
			
			case INTEGER:
			case LONG: {
				if (options.getMin() == null || options.getMin().length() == 0) {
					options.setMin("0");
				}
				if (options.getMax() == null || options.getMax().length() == 0) {
					options.setMax("10000");
				}
				minLabel.setText("Minimum value:");
				minText.setText(options.getMin());
				maxLabel.setText("Maximum value:");
				maxText.setText(options.getMax());
				break;
			}
				
			default:
				break;
			}
		}

		if (generationType == GenerationType.PREFIXED || generationType == GenerationType.INCREMENTAL || generationType == GenerationType.CONSTANT) {
			final Label preIncLabel = new Label(parent, SWT.READ_ONLY);
			
			final Text preIncText = new Text(parent, SWT.BORDER);
			preIncText.setText(String.valueOf(options.getMax()));
			preIncText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			if (generationType == GenerationType.PREFIXED) {
				preIncLabel.setText("Prefix:");
				preIncText.setText(options.getPrefix());
			}
			if (generationType == GenerationType.INCREMENTAL) {
				preIncLabel.setText("Increment by:");
				preIncText.setText(options.getIncrementBy());
				if (data.getType() == PROPERTY_TYPES.DATE_TIME) {
			        SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
					options.setMin(sdf.format(new Date()));
				} else {
					options.setMin("0");
				}
				try {
					double d = Double.parseDouble(options.getIncrementBy());
//					options.setMax(String.valueOf(d * this.generationTask.getNumRows()));
				} catch (NumberFormatException e) {
					System.err.println(e);
				}
			}
			if (generationType == GenerationType.CONSTANT) {
				preIncLabel.setText("Constant value:");
				preIncText.setText(options.getPrefix());
			}
			
			preIncText.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (generationType == GenerationType.PREFIXED || generationType == GenerationType.CONSTANT) {
						if (data.getType() == PROPERTY_TYPES.BOOLEAN) {
							boolean b = Boolean.parseBoolean(preIncText.getText());
							if (b) {
								options.setPrefix("true");
							} else {
								options.setPrefix("false");
							}
						} else {
							options.setPrefix(preIncText.getText());
						}
					}
					if (generationType == GenerationType.INCREMENTAL) {
						try {
							Double.parseDouble(preIncText.getText());
						} catch (NumberFormatException e) {
							hasErrors[3] = true;
							setMessage("Invalid increment by value");
							return;
						}
						hasErrors[3] = false;
						setMessage(null);
						options.setIncrementBy(preIncText.getText());
					} else if (generationType == GenerationType.CONSTANT) {
						try {
							if (data.getType() == PROPERTY_TYPES.INTEGER || data.getType() == PROPERTY_TYPES.DOUBLE || data.getType() == PROPERTY_TYPES.LONG) {
								Double.parseDouble(preIncText.getText());
							}
						} catch (NumberFormatException e) {
							hasErrors[3] = true;
							setMessage("Invalid constant value for "+ data.getName());
							return;
						}
					}
				}
			});
			if (data.getType() == PROPERTY_TYPES.DATE_TIME) {
				if (generationType == GenerationType.CONSTANT) {
					final Button selectDateButton = new Button(parent, SWT.NONE);
					selectDateButton.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent arg0) {
							DateTimeDialog d = new DateTimeDialog(getShell(), null);
							int open = d.open();
//						if (open == Dialog.OK) {
							String returnCurrentDate = d.ReturnCurrentDate();
							if (returnCurrentDate != null) {
								preIncText.setText(returnCurrentDate);
							}
//						}
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
						}
					});
				} else if (generationType == GenerationType.INCREMENTAL) {
					final Combo unitCombo = new Combo(parent, SWT.NONE);
					String[] units = new String[] {"Milliseconds", "Seconds", "Minutes", "Hours", "Days", "Months", "Years"};
					unitCombo.setItems(units);
					if (options.getIncrementUnit() == null || options.getIncrementUnit().length() == 0) {
						options.setIncrementUnit("Days");
					}
					unitCombo.setText(options.getIncrementUnit());
					
					unitCombo.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent arg0) {
							options.setIncrementUnit(unitCombo.getText());
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
						}
					});
				}
			} else {
				((GridData)preIncText.getLayoutData()).horizontalSpan = 2;
			}
		}

		final Button allowNullBtn = new Button(parent, SWT.CHECK);
		allowNullBtn.setText("Allow empty values");
		GridData l = new GridData(GridData.FILL_HORIZONTAL);
		l.horizontalSpan = 3;
		allowNullBtn.setLayoutData(l);
		final Label nullProbLbl = new Label(parent, SWT.WRAP);
		nullProbLbl.setText("Probability of generating an empty value");
		GridData ld = new GridData(180, SWT.DEFAULT);
		nullProbLbl.setLayoutData(ld);
		final Text nullProbabilityText = new Text(parent, SWT.BORDER);
		nullProbabilityText.setText(String.valueOf(options.getNullProbability()));
		GridData nd = new GridData();
		nd.minimumWidth = 75;
		nd.widthHint = 100;
		nullProbabilityText.setLayoutData(nd);
		nullProbabilityText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				double val = 0;
				try {
					val = Double.parseDouble(nullProbabilityText.getText());
					if (val > 100 || val < 0) {
						hasErrors[4] = true;
						setMessage("Null probability must be a number between 0 and 100");
						return;
					}
				} catch (NumberFormatException e) {
					hasErrors[4] = true;
					setMessage("Null probability must be a number between 0 and 100");
					return;
				}
				hasErrors[4] = false;
				setMessage(null);
				options.setNullProbability(val);
			}
		});
		allowNullBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				options.setAllowNull(allowNullBtn.getSelection());
				nullProbabilityText.setEnabled(options.isAllowNull());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		allowNullBtn.setSelection(options.isAllowNull());
		
		this.generationOptionsGroup.layout(true);
		parent.layout(true);
	}

	private String[] getGenerationMethods(PropertyDefinition data) {
		PROPERTY_TYPES type = data.getType();
		boolean hasDomains = data.getDomainInstances().size() > 0;
		List<String> types = new ArrayList<>();
		types.add(GenerationType.RANDOM.getDispName());
		types.add(GenerationType.CONSTANT.getDispName());
		if (hasDomains) {
			types.add(GenerationType.ENUMERATE.getDispName());
		} else {
			switch (type) {
			case DATE_TIME:
				types.add(GenerationType.INCREMENTAL.getDispName());
				break;
				
			case STRING:
				types.add(GenerationType.PREFIXED.getDispName());
				break;
				
			case INTEGER:
			case DOUBLE:
			case LONG:
				types.add(GenerationType.INCREMENTAL.getDispName());
				break;
			}
		}
		return types.toArray(new String[types.size()]);
	}

	private List<PropertyDefinition> getEntityProperties() {
		if (this.fOwnerEntity instanceof Concept) {
			return ((Concept) this.fOwnerEntity).getPropertyDefinitions(false);
		}
		if (this.fOwnerEntity instanceof Event) {
			return ((Event) this.fOwnerEntity).getAllUserProperties();
		}
		return Collections.EMPTY_LIST;
	}

	public GenerateTaskModel getGenerationTask() {
		return generationTask;
	}
}
