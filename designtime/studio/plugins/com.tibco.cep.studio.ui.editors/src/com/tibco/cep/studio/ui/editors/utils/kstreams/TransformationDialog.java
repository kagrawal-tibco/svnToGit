package com.tibco.cep.studio.ui.editors.utils.kstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.wizards.RuleFunctionSelector;

public class TransformationDialog extends Dialog {

	private String project;
	private String currentType;
	private Transformation transformation;

	private Table typeTable;
	private Group inputDetailsGroup;

	private Label propMessageLabel;
	private Composite errorComposite;

	private Composite joinWindowsComposite;

	public TransformationDialog(Shell parentShell, String project, String currentType) {
		super(parentShell);
		this.project = project;
		this.currentType = currentType;
	}

	public TransformationDialog(Shell parentShell, String project, String currentType,
			Transformation currentTransformation) {
		super(parentShell);
		this.project = project;
		this.currentType = currentType;
		this.transformation = currentTransformation;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Control createContents(Composite parent) {
		return super.createContents(parent);
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control ret = super.createButtonBar(parent);
		showErrorMessage(null);
		return ret;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		if (this.transformation != null) {
			getShell().setText("Edit Transformation Inputs");
		} else {
			getShell().setText("Add Transformation");
		}

		Composite composite = (Composite) super.createDialogArea(parent);
		((GridLayout) composite.getLayout()).numColumns = 2;

		createTypesTable(composite);

		inputDetailsGroup = new Group(composite, SWT.BORDER);
		inputDetailsGroup.setText("Inputs");
		GridLayout layout = new GridLayout(1, true);
		inputDetailsGroup.setLayout(layout);
		inputDetailsGroup.setBackground(ColorConstants.white);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.minimumHeight = 220;
		inputDetailsGroup.setLayoutData(data);

		errorComposite = new Composite(composite, SWT.NONE);
		errorComposite.setLayout(new GridLayout(2, false));
		GridData ec = new GridData(GridData.FILL_HORIZONTAL);
		ec.horizontalSpan = 2;
		errorComposite.setLayoutData(ec);
		errorComposite.setVisible(false);
		Label imageLabel = new Label(errorComposite, SWT.NONE);
		imageLabel.setImage(EditorsUIPlugin.getDefault().getImage("/icons/error_mark.png"));
		propMessageLabel = new Label(errorComposite, SWT.NONE);
		propMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Select a tableItem
		// In case of add, select the first row one by default
		// In case of update, select the provided row
		if (transformation != null) {
			TableItem[] tableItems = typeTable.getItems();
			for (TableItem tableItem : tableItems) {
				if (tableItem.getData().equals(transformation.getType())) {
					typeTable.setSelection(tableItem);
					typeTable.setEnabled(false);
					updateInputDetails(transformation);
					break;
				}
			}
		} else {
			typeTable.setSelection(0);
			TableItem firstItem = typeTable.getItem(0);
			String selectedType = firstItem.getData().toString();
			transformation = TransformationBuilder.buildTransformation(selectedType);
			updateInputDetails(transformation);
		}

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

	private void addJoinWindowDetails(Composite parent) {

		Map<String, Object> joinWindowsInput = (Map<String, Object>) transformation.getInputs()
				.get(TransformationConstants.INPUT_JOINWINDOWS);
		Map<String, String> joinWindowsUnits = (Map<String, String>) transformation.getInputs()
				.get(TransformationConstants.INPUT_JOINWINDOWUNITS);

		joinWindowsComposite = new Composite(parent, SWT.BORDER);
		GridLayout gridLayout = new GridLayout(3, false);
		joinWindowsComposite.setLayout(gridLayout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		joinWindowsComposite.setLayoutData(gd);

		String[] labels = new String[] { "Time Difference:", "After:", "Before:", "Grace:" };
		String[] data = new String[] { TransformationConstants.INPUT_JOINWINDOWS_WINDOW,
				TransformationConstants.INPUT_JOINWINDOWS_AFTER, TransformationConstants.INPUT_JOINWINDOWS_BEFORE,
				TransformationConstants.INPUT_JOINWINDOWS_GRACE };

		for (int i = 0; i < data.length; i++) {

			final Label durationLabel = new Label(joinWindowsComposite, SWT.READ_ONLY);
			durationLabel.setText(labels[i]);

			String currentValue = "0";
			if (joinWindowsInput != null && joinWindowsInput.get(data[i]) != null) {
				currentValue = String.valueOf(joinWindowsInput.get(data[i]));
			}			
			createJoinWindowGVControls(joinWindowsComposite, currentValue, joinWindowsInput, data[i]);

			final Combo durationUnitCombo = new Combo(joinWindowsComposite, SWT.READ_ONLY);
			GridData unitGridData = new GridData(GridData.FILL_HORIZONTAL);
			durationUnitCombo.setLayoutData(unitGridData);
			durationUnitCombo.setItems(TransformationConstants.DURATION_TYPES);
			if (joinWindowsUnits != null && joinWindowsUnits.get(data[i]) != null) {
				for (int idx = 0; idx < TransformationConstants.DURATION_TYPES.length; idx++) {
					if (TransformationConstants.DURATION_TYPES[idx].equals(joinWindowsUnits.get(data[i]))) {
						durationUnitCombo.select(idx);
					}
				}
			} else {
				durationUnitCombo.select(3);
			}
			durationUnitCombo.setData(data[i]);
			durationUnitCombo.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					String selectedUnit = TransformationConstants.DURATION_TYPES[durationUnitCombo.getSelectionIndex()];
					String data = durationUnitCombo.getData().toString();
					Map<String, String> joinWindowsInput = (Map<String, String>) transformation.getInputs()
							.get(TransformationConstants.INPUT_JOINWINDOWUNITS);
					if (joinWindowsInput != null) {
						joinWindowsInput.put(data, selectedUnit);
					}
					showErrorMessage(null);
				}
			});
		}
	}

	private void createTypesTable(Composite composite) {
		typeTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		data.heightHint = 100;
		data.widthHint = 400;
		typeTable.setLayoutData(data);
		typeTable.setHeaderVisible(false);

		TableLayout layout = new TableLayout(true);
		typeTable.setLayout(layout);

		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(typeTable);
		autoTableLayout.addColumnData(new ColumnWeightData(2));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(2));
		typeTable.setLayout(autoTableLayout);

		TableColumn typeColumn = new TableColumn(typeTable, SWT.NONE);
		typeColumn.setText("Type");

		populateTypeTable(currentType);
	}

	private String[] getTypes(String currentType) {
		List<String> list = new ArrayList<String>();
		if (KSTREAM_TYPES.GROUP_BY_KEY.getName().equals(currentType)) {
			for (KGROUPEDSTREAM_TYPES transformationTypes : KGROUPEDSTREAM_TYPES.values()) {
				list.add(transformationTypes.getName());
			}
		} else if (KGROUPEDSTREAM_TYPES.AGGREGATE.getName().equals(currentType)
				|| KGROUPEDSTREAM_TYPES.COUNT.getName().equals(currentType)
				|| KGROUPEDSTREAM_TYPES.REDUCE.getName().equals(currentType)) {
			for (KTABLE_TYPES transformationTypes : KTABLE_TYPES.values()) {
				list.add(transformationTypes.getName());
			}
		} else {
			for (KSTREAM_TYPES transformationTypes : KSTREAM_TYPES.values()) {
				list.add(transformationTypes.getName());
			}
		}
		return list.toArray(new String[list.size()]);
	}

	private void populateTypeTable(String currentType) {

		String[] transformationTypes = getTypes(currentType);
		for (String transformationType : transformationTypes) {
			TableItem tableItem = new TableItem(typeTable, SWT.NONE);
			tableItem.setText(transformationType);
			tableItem.setData(transformationType);
		}

		typeTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Identify the selected row
				TableItem item = (TableItem) e.item;
				if (item == null)
					return;
				String selectedType = (String) item.getData();
				transformation = TransformationBuilder.buildTransformation(selectedType);
				updateInputDetails(transformation);
			}
		});
	}

	protected void updateInputDetails(Transformation t) {
		Control[] children = inputDetailsGroup.getChildren();
		for (Control ch : children) {
			ch.dispose();
		}

//	    ScrolledComposite groupScroll = new ScrolledComposite(inputDetailsGroup, SWT.V_SCROLL);
//	    groupScroll.setLayout(new GridLayout(1, false));
//	    groupScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite composite = new Composite(inputDetailsGroup, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		GridData d = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(d);

		String type = t.getType();
		if (KSTREAM_TYPES.FILTER.getName().equals(type) || KSTREAM_TYPES.FILTER_NOT.getName().equals(type)) {
			String textData = TransformationConstants.INPUT_PREDICATE;
			createRuleFunctionInputControls(composite, textData, "boolean", 2, 2);
		} else if (KSTREAM_TYPES.FLAT_MAP_VALUES.getName().equals(type)
				|| KSTREAM_TYPES.MAP_VALUES.getName().equals(type)) {
			String textData = TransformationConstants.INPUT_VALUEMAPPER;
			createRuleFunctionInputControls(composite, textData, "String|int|long|double|Object", 1, 2);
		} else if (KGROUPEDSTREAM_TYPES.AGGREGATE.getName().equals(type)) {
			String initializerTextData = TransformationConstants.INPUT_INITIALIZER;
			createRuleFunctionInputControls(composite, initializerTextData, "Object", 0, 0);

			String textData = TransformationConstants.INPUT_AGGREGATOR;
			createRuleFunctionInputControls(composite, textData, "String|int|long|double|Object", 3, 3);
		} else if (KGROUPEDSTREAM_TYPES.REDUCE.getName().equals(type)) {
			String textData = TransformationConstants.INPUT_REDUCER;
			createRuleFunctionInputControls(composite, textData, "String|int|long|double|Object", 2, 2);
		} else if (KSTREAM_TYPES.JOIN.getName().equals(type) || KSTREAM_TYPES.LEFT_JOIN.getName().equals(type)
				|| KSTREAM_TYPES.OUTER_JOIN.getName().equals(type)) {

			String topicTextData = TransformationConstants.INPUT_JOIN_TOPIC;
			createTextInputControls(composite, topicTextData);

			String textData = TransformationConstants.INPUT_VALUE_JOINER;
			createRuleFunctionInputControls(composite, textData, "String|int|long|double|Object", 2, 2);

			addJoinWindowDetails(composite);
		} else if (KSTREAM_TYPES.SELECT_KEY.getName().equals(type)) {
			String textData = TransformationConstants.INPUT_KEYVALUEMAPPER;
			createRuleFunctionInputControls(composite, textData, "String|int|long|double|Object", 2, 2);
		}

		showErrorMessage(null);

//	    groupScroll.setContent(composite);
//	    groupScroll.setExpandHorizontal(true);
//	    groupScroll.setExpandVertical(true);
//	    groupScroll.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		inputDetailsGroup.layout(true);
	}

	private void showErrorMessage(String errorMessage) {

		String type = this.transformation.getType();
		if (errorMessage == null) {
			if (KSTREAM_TYPES.FILTER.getName().equals(type) || KSTREAM_TYPES.FILTER_NOT.getName().equals(type)) {
				errorMessage = transformation.getInputs().get(TransformationConstants.INPUT_PREDICATE) == null
						? "Missing Required Input: " + TransformationConstants.INPUT_PREDICATE + " (RuleFunction)"
						: null;
			} else if (KSTREAM_TYPES.FLAT_MAP_VALUES.getName().equals(type)
					|| KSTREAM_TYPES.MAP_VALUES.getName().equals(type)) {
				errorMessage = transformation.getInputs().get(TransformationConstants.INPUT_VALUEMAPPER) == null
						? "Missing Required Input: " + TransformationConstants.INPUT_VALUEMAPPER + " (RuleFunction)"
						: null;
			} else if (KGROUPEDSTREAM_TYPES.AGGREGATE.getName().equals(type)) {
				// order is important
				errorMessage = transformation.getInputs().get(TransformationConstants.INPUT_INITIALIZER) == null
						? "Missing Required Input: " + TransformationConstants.INPUT_INITIALIZER + " (RuleFunction)"
						: errorMessage;
				errorMessage = errorMessage == null
						&& transformation.getInputs().get(TransformationConstants.INPUT_AGGREGATOR) == null
								? "Missing Required Input: " + TransformationConstants.INPUT_AGGREGATOR
										+ " (RuleFunction)"
								: null;
			} else if (KGROUPEDSTREAM_TYPES.REDUCE.getName().equals(type)) {
				errorMessage = transformation.getInputs().get(TransformationConstants.INPUT_REDUCER) == null
						? "Missing Required Input: " + TransformationConstants.INPUT_REDUCER + " (RuleFunction)"
						: null;
			} else if (KSTREAM_TYPES.JOIN.getName().equals(type) || KSTREAM_TYPES.LEFT_JOIN.getName().equals(type)
					|| KSTREAM_TYPES.OUTER_JOIN.getName().equals(type)) {
				// order is important
				Object topic = transformation.getInputs().get(TransformationConstants.INPUT_JOIN_TOPIC);
				errorMessage = topic == null || topic.toString().isEmpty()
						? "Missing Required Input: " + TransformationConstants.INPUT_JOIN_TOPIC
						: null;
				errorMessage = errorMessage == null && GvUtil.isGlobalVar(topic.toString()) && GvUtil.getGvDefinedValue(project, topic.toString()) == null
						? "Invalid Global Variable" : errorMessage;
				errorMessage = errorMessage == null
						&& transformation.getInputs().get(TransformationConstants.INPUT_VALUE_JOINER) == null
								? "Missing Required Input: " + TransformationConstants.INPUT_VALUE_JOINER
										+ " (RuleFunction)"
								: errorMessage;
				Map<String, Object> joinWindowsInput = (Map<String, Object>) transformation.getInputs()
						.get(TransformationConstants.INPUT_JOINWINDOWS);
				if (joinWindowsInput == null
						|| joinWindowsInput.get(TransformationConstants.INPUT_JOINWINDOWS_WINDOW) == null) {
					errorMessage = errorMessage == null ? "Missing Required Input: Time Difference" : errorMessage;
				} else {
					// need this workaround json serialize/de-serialize with undefined types
					String value = String.valueOf(joinWindowsInput.get(TransformationConstants.INPUT_JOINWINDOWS_WINDOW));
					if(GvUtil.isGlobalVar(value)) {
						if(GvUtil.getGvDefinedValue(project, value) == null
								|| !GvUtil.getGvType(project, value).equals("Integer")) {
							errorMessage = "Invalid Global Variable";
						} else {
							value = GvUtil.getGvDefinedValue(project, value);
						}
					}		
					
					long duration = 0;
					try {
						duration = Long.parseLong(value);
					} catch (NumberFormatException ex) {
						errorMessage = errorMessage == null ? "Invalid input, must be a number" : errorMessage;
					}
					
					errorMessage = errorMessage == null && duration <= 0 ? "Time Difference must be greater than 0"
							: errorMessage;
				}
			} else if (KSTREAM_TYPES.SELECT_KEY.getName().equals(type)) {
				errorMessage = transformation.getInputs().get(TransformationConstants.INPUT_KEYVALUEMAPPER) == null
						? "Missing Required Input: " + TransformationConstants.INPUT_KEYVALUEMAPPER + " (RuleFunction)"
						: null;
			}
		}
		if (errorMessage != null) {
			setMessage(errorMessage);
			if (getButton(IDialogConstants.OK_ID) != null)
				getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			setMessage(null);
			if (getButton(IDialogConstants.OK_ID) != null)
				getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
	}

	/**
	 * @param c
	 * @param lableName
	 * @param textData
	 */
	private void createTextInputControls(Composite c, String textData) {
		final Label genOptionLabel = new Label(c, SWT.READ_ONLY);
		String lableName = textData + ":";
		genOptionLabel.setText(lableName);
		
		String topic = "";
		if (transformation.getInputs().get(textData) != null) {
			topic = transformation.getInputs().get(textData).toString();
		}
		GvField gvField = GvUiUtil.createTextGv(c, topic);
		
		gvField.setFieldListener(SWT.Modify, getListener(gvField.getField(), transformation.getInputs(), textData));

		gvField.setGvListener(getListener(gvField.getGvText(), transformation.getInputs(), textData));
		
		final Label emptyLable = new Label(c, SWT.READ_ONLY);
		emptyLable.setText("");
	}
	
	private Listener getListener(final Control field, Map<String, Object> inputMap, String inputName) {
		return new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if (field instanceof Text) {
					String value = ((Text)field).getText();
					inputMap.put(inputName, value);
					showErrorMessage(null);
				}
			}
		};
	}
	
	private void createJoinWindowGVControls(Composite parent, String defaultValue, Map<String, Object> inputMap, String keyName) {
		GvField gvField = GvUiUtil.createTextGv(parent, defaultValue);
		gvField.setFieldListener(SWT.Modify, getJoinWindowsListener(gvField.getField(), inputMap, keyName));
		gvField.setGvListener(getJoinWindowsListener(gvField.getGvText(), inputMap, keyName));
	}
	
	private Listener getJoinWindowsListener(final Control field, Map<String, Object> inputMap, String inputName) {
		return new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if (field instanceof Text) {
					String value = ((Text)field).getText();
					inputMap.put(inputName, value);
					showErrorMessage(null);

				}
			}
		};
	}
	


	/**
	 * @param c
	 * @param lableName
	 * @param textData
	 */
	private void createRuleFunctionInputControls(Composite c, String textData, String returnType, int minArgs,
			int maxArgs) {
		final Label genOptionLabel = new Label(c, SWT.READ_ONLY);
		String lableName = textData + " (RuleFunction):";
		genOptionLabel.setText(lableName);

		final Text text = new Text(c, SWT.BORDER);
		text.setEditable(false);
		text.setData(textData);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (transformation.getInputs().get(textData) != null) {
			text.setText(transformation.getInputs().get(textData).toString());
		}
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String rfURI = text.getText();
				if (rfURI != null && !rfURI.isEmpty()) {
					transformation.getInputs().put(text.getData().toString(), rfURI);
				}
				showErrorMessage(null);
			}
		});

		Button browseButton = new Button(c, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RuleFunctionSelector picker = new RuleFunctionSelector(Display.getDefault().getActiveShell(), project,
						"", false);
				picker.addFilter(new KafkaStreamsRfFilter(returnType, minArgs, maxArgs));
				if (picker.open() == Dialog.OK) {
					text.setText(picker.getFirstResult().toString());
				}
			}
		});
	}

	public Transformation getTransformation() {
		return transformation;
	}
}
