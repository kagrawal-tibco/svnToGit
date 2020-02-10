package com.tibco.cep.studio.decision.table.configuration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ComboBoxPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.LineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.VerticalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.MixedStateBoolean;
import com.tibco.cep.studio.decision.table.editor.MixedStateCheckBoxCellEditor;
import com.tibco.cep.studio.decision.table.editor.MixedStateCheckBoxPainter;
import com.tibco.cep.studio.decision.table.editor.MultiSelectTableComboCellEditor;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.providers.MultiValueDataConverter;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * This class is responsible for styling the body layer of the decision/exception
 * table.  This includes column styling for conditions/actions, as well as 
 * registering alternative editors for the expression cells (checkbox for
 * boolean properties, drop down for domain models, etc).
 * 
 * @author rhollom
 *
 */
public class DTBodyStyleConfiguration extends DefaultNatTableStyleConfiguration {
	
	private static IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
	
	public class StringToBooleanDisplayConverter extends DefaultBooleanDisplayConverter {

		@Override
		public Object canonicalToDisplayValue(Object canonicalValue) {
			return super.displayToCanonicalValue(canonicalValue);
		}
		
	}
	public class StringToMixedStateBooleanDisplayConverter extends DefaultBooleanDisplayConverter {

		@Override
		public Object displayToCanonicalValue(Object displayValue) {
			if (displayValue instanceof MixedStateBoolean) {
				switch ((MixedStateBoolean)displayValue) {
				case NOT_SET:
					return null;

				case TRUE:
					return Boolean.TRUE;
					
				case FALSE:
					return Boolean.FALSE;
					
				default:
					break;
				}
			}
			return super.displayToCanonicalValue(displayValue);
		}
		
		@Override
		public Object canonicalToDisplayValue(Object canonicalValue) {
			if (canonicalValue == null || canonicalValue.toString().trim().isEmpty()) {
				return MixedStateBoolean.NOT_SET;
			}
			Boolean val = (Boolean) super.displayToCanonicalValue(canonicalValue);
			if (val) {
				return MixedStateBoolean.TRUE;
			}
			return MixedStateBoolean.FALSE;
		}
		
	}
	private final DTBodyLayerStack<TableRule> bodyLayer;
	private ICellPainter checkBoxCellPainter;
	private ICellPainter comboBoxCellPainter;
	private ICellPainter multiComboBoxCellPainter;
	private IDisplayConverter displayConverter;
	private IDisplayConverter multiValueDisplayConverter;

	private IDecisionTableEditor editor = null;
	private TableRuleSet tableRuleSet = null;
	private TableTypes tableType;

//	private static Color darkGray   = new Color(null,  64,  64,  64);
	private static Color lightGreen = new Color(null,  96, 255,  96);
	private static Color lightBlue  = new Color(null, 127, 127, 255);

	public DTBodyStyleConfiguration(DTBodyLayerStack<TableRule> bodyLayer, IDecisionTableEditor editor, TableRuleSet tableRuleSet, TableTypes tableType) {
		this.bodyLayer = bodyLayer;
		this.editor = editor;
		this.tableRuleSet = tableRuleSet;	
		this.tableType=tableType;
		setDefaults();
	}

	private void setDefaults() {
		// Style config
		this.bgColor = GUIHelper.COLOR_WHITE;
		this.fgColor = GUIHelper.COLOR_BLACK;
		this.hAlign = HorizontalAlignmentEnum.LEFT;
		this.vAlign = VerticalAlignmentEnum.MIDDLE;
	}

	private void registerCheckBoxEditor(IConfigRegistry configRegistry, ICellPainter checkBoxCellPainter, ICellEditor checkBoxCellEditor) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, checkBoxCellPainter, DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, getCheckBoxDisplayConverter(), DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, checkBoxCellEditor, DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_EDITOR_CONFIG_LABEL);
	}

	protected DefaultBooleanDisplayConverter getCheckBoxDisplayConverter() {
//		return new StringToBooleanDisplayConverter();
		return new StringToMixedStateBooleanDisplayConverter();
	}

	private void registerComboBox(IConfigRegistry configRegistry, ICellPainter comboBoxCellPainter, ICellEditor comboBoxCellEditor, IDisplayConverter displayConverter) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, comboBoxCellPainter, DisplayMode.NORMAL, DecisionTableDesignViewer.COMBO_BOX_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, comboBoxCellEditor, DisplayMode.NORMAL, DecisionTableDesignViewer.COMBO_BOX_EDITOR_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, comboBoxCellEditor, DisplayMode.EDIT, DecisionTableDesignViewer.COMBO_BOX_EDITOR_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, displayConverter, DisplayMode.EDIT, DecisionTableDesignViewer.COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, displayConverter, DisplayMode.NORMAL, DecisionTableDesignViewer.COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
	}

	private void registerMultiComboBox(IConfigRegistry configRegistry, ICellPainter multiComboBoxCellPainter, ICellEditor multiComboBoxCellEditor, IDisplayConverter displayConverter) {
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, multiComboBoxCellPainter, DisplayMode.NORMAL, DecisionTableDesignViewer.MULTI_COMBO_BOX_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, multiComboBoxCellEditor, DisplayMode.NORMAL, DecisionTableDesignViewer.MULTI_COMBO_BOX_EDITOR_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, multiComboBoxCellEditor, DisplayMode.EDIT, DecisionTableDesignViewer.MULTI_COMBO_BOX_EDITOR_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, displayConverter, DisplayMode.EDIT, DecisionTableDesignViewer.MULTI_COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, displayConverter, DisplayMode.NORMAL, DecisionTableDesignViewer.MULTI_COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL);
	}

	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		cellPainter =  new TextPainter(false, true, 3);//new LineBorderDecorator(new TextPainter(false, true, 3)); 
		super.configureRegistry(configRegistry);

		// make cells editable
		IEditableRule editableRule = new DTCellEditableRule(bodyLayer, editor, tableRuleSet,tableType);
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, editableRule, DisplayMode.EDIT);
		checkBoxCellPainter = getCheckBoxPainter();
		if(TableTypes.DECISION_TABLE.equals(this.tableType) && !editor.getDecisionTableDesignViewer().isToggleTextAliasFlag()){
			registerCheckBoxEditor(configRegistry, checkBoxCellPainter, getCheckBoxCellEditor());
		}
		if(TableTypes.EXCEPTION_TABLE.equals(this.tableType) && !editor.getDecisionTableDesignViewer().isExpToggleTextAliasFlag()){
			registerCheckBoxEditor(configRegistry, checkBoxCellPainter, getCheckBoxCellEditor());
		}

		List<String> headers = new ArrayList<String>();
		headers.add(DTDomainUtil.VALUE);
		if(DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION)) {
			headers.add(DTDomainUtil.DESC);
		}
		
		comboBoxCellPainter = new ComboBoxPainter();
		displayConverter = new MultiValueDataConverter(false, DTDomainUtil.SEPARATOR);		
		MultiSelectTableComboCellEditor boxCellEditor = new MultiSelectTableComboCellEditor(bodyLayer.getBodyDataProvider(), headers, 10);
		boxCellEditor.setMultiselect(false);
		boxCellEditor.setFreeEdit(true);
		registerComboBox(configRegistry, comboBoxCellPainter, boxCellEditor, displayConverter);

		multiComboBoxCellPainter = new ComboBoxPainter();
		multiValueDisplayConverter = new MultiValueDataConverter(true, DTDomainUtil.SEPARATOR);
		MultiSelectTableComboCellEditor multiBoxCellEditor = new MultiSelectTableComboCellEditor(bodyLayer.getBodyDataProvider(), headers, 10);
		multiBoxCellEditor.setMultiselect(true);
		multiBoxCellEditor.setFreeEdit(true);
		multiBoxCellEditor.setUseCheckbox(true);
		multiBoxCellEditor.setMultiselectValueSeparator(DTDomainUtil.SEPARATOR);
		multiBoxCellEditor.setMultiselectTextBracket("", "");

		registerMultiComboBox(configRegistry, multiComboBoxCellPainter, multiBoxCellEditor, multiValueDisplayConverter);
		
//		registerColumnOverrides();
		
		preferenceStyling(configRegistry);
		
		// styling for disabled cells
		Style disableStyle = new Style();
		String disabledBackColor = prefStore.getString(PreferenceConstants.DISABLED_DATA_BACK_GROUND_COLOR);
		String disabledForeColor = prefStore.getString(PreferenceConstants.DISABLED_DATA_FORE_GROUND_COLOR);
		disableStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(disabledForeColor)));
		disableStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(disabledBackColor)));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, disableStyle, DisplayMode.NORMAL, DecisionTableDesignViewer.DISABLE_LABEL);
		
		// styling for analyzer covered cells
		Style analyzerStyle = new Style();
		analyzerStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, lightGreen);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, analyzerStyle, DisplayMode.NORMAL, DecisionTableDesignViewer.ANALYZER_COVERAGE_LABEL);
		
		// styling for commented cells
		//TODO comment styling should work for checkbox and combobox editors as well
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new LineBorderDecorator(new TextPainter(), new BorderStyle(2, lightBlue, BorderStyle.LineStyleEnum.SOLID )),
				DisplayMode.NORMAL, DecisionTableDesignViewer.TEXT_BOX_COMMENT_LABEL );
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new LineBorderDecorator(comboBoxCellPainter, new BorderStyle(2, lightBlue, BorderStyle.LineStyleEnum.SOLID )),
				DisplayMode.NORMAL, DecisionTableDesignViewer.COMBO_BOX_COMMENT_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				getCheckBoxPainter(), DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_COMMENT_LABEL );
	}

	protected ICellEditor getCheckBoxCellEditor() {
		return new MixedStateCheckBoxCellEditor();
	}

	protected CheckBoxPainter getCheckBoxPainter() {
		return new MixedStateCheckBoxPainter();
	}

	private void preferenceStyling(IConfigRegistry configRegistry) {
		String condBackColor = prefStore.getString(PreferenceConstants.COND_DATA_BACK_GROUND_COLOR);
		String condForeColor = prefStore.getString(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR);
		String actionBackColor = prefStore.getString(PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR);
		String actionForeColor = prefStore.getString(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR);
		String conditionFont = prefStore.getString(PreferenceConstants.COND_DATA_FONT);
		String actionFont = prefStore.getString(PreferenceConstants.ACTION_DATA_FONT);
		FontData condFontData = new FontData(conditionFont);
		FontData actionFontData = new FontData(actionFont);
		
		Style defStyle = new Style();
		defStyle.setAttributeValue(
				CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(condForeColor)));
		defStyle.setAttributeValue(
				CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WHITE);
		defStyle.setAttributeValue(
				CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
		defStyle.setAttributeValue(
				CellStyleAttributes.VERTICAL_ALIGNMENT, VerticalAlignmentEnum.MIDDLE);
		defStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(condFontData));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, defStyle, DisplayMode.NORMAL);

		Style condFgStyle = new Style();
		condFgStyle.setAttributeValue(
				CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(condForeColor)));
		condFgStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(condFontData));
		condFgStyle.setAttributeValue(
				CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(condBackColor)));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, condFgStyle,DisplayMode.NORMAL,DecisionTableDesignViewer.CONDITIONS_GROUP_LABEL);

		Style condFgAltStyle = new Style();
		condFgAltStyle.setAttributeValue(
				CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(condForeColor)));
		condFgAltStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(condFontData));
		condFgAltStyle.setAttributeValue(
				CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WHITE);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, condFgAltStyle,DisplayMode.NORMAL,DecisionTableDesignViewer.CONDITIONS_GROUP_ALT_LABEL);

		Style actionFgStyle = new Style();
		actionFgStyle.setAttributeValue(
				CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(actionForeColor)));
		actionFgStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(actionFontData));
		actionFgStyle.setAttributeValue(
				CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(actionBackColor)));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, actionFgStyle,DisplayMode.NORMAL,DecisionTableDesignViewer.ACTIONS_GROUP_LABEL);

		Style actionFgAltStyle = new Style();
		actionFgAltStyle.setAttributeValue(
				CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(condForeColor)));
		actionFgAltStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(actionFontData));
		actionFgAltStyle.setAttributeValue(
				CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WHITE);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, actionFgAltStyle,DisplayMode.NORMAL,DecisionTableDesignViewer.ACTIONS_GROUP_ALT_LABEL);
		
		Style checkBoxAlign = new Style();
		checkBoxAlign.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.CENTER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, checkBoxAlign, DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);
	}


//	private void registerColumnOverrides() {
//		if (ruleSet != null && ruleSet.getColumns() != null) {
//			EList<Column> columns = ruleSet.getColumns().getColumn();
//			for (int i=0; i<columns.size(); i++) {
//				Column col = columns.get(i);
//				// Style action columns differently
//				if (col.getColumnType() == ColumnType.ACTION || col.getColumnType() == ColumnType.CUSTOM_ACTION) {
//					columnLabelAccumulator.registerColumnOverrides(i, DecisionTableSWTFormViewer.ACTIONS_GROUP_LABEL);
//				}
//				Map<String, DomainEntry> domainEntries = DTDomainUtil.getDomainEntries(projectName, col);
//				if (domainEntries != null && domainEntries.size() > 1) {
//					columnLabelAccumulator.registerColumnOverrides(i, DecisionTableSWTFormViewer.COMBO_BOX_CONFIG_LABEL, DecisionTableSWTFormViewer.COMBO_BOX_EDITOR_CONFIG_LABEL);
//					continue;
//				} else if (domainEntries.size() == 1 && !domainEntries.keySet().contains("*")) {
//					columnLabelAccumulator.registerColumnOverrides(i, DecisionTableSWTFormViewer.COMBO_BOX_CONFIG_LABEL, DecisionTableSWTFormViewer.COMBO_BOX_EDITOR_CONFIG_LABEL);
//					continue;
//				}
//				else {
//					// determine whether column type is a boolean type
//					if (col.getPropertyType() == PropertyType.PROPERTY_TYPE_BOOLEAN_VALUE) {
//						columnLabelAccumulator.registerColumnOverrides(i, DecisionTableSWTFormViewer.CHECK_BOX_CONFIG_LABEL, DecisionTableSWTFormViewer.CHECK_BOX_EDITOR_CONFIG_LABEL);
//					}
//				}
//			}
//		}
//	}
	
}