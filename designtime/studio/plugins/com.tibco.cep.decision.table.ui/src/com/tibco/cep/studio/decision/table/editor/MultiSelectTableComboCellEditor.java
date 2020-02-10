package com.tibco.cep.studio.decision.table.editor;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.nebula.widgets.nattable.widget.NatCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.providers.MultiValueDataConverter;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.MultiSelectTableCombo;

/**
 * This class extends the ComboBoxCellEditor implementation to add proper domain model support
 * to the decision table editor.  
 * @author vdhumal
 *
 */
public class MultiSelectTableComboCellEditor extends ComboBoxCellEditor {

	private List<String> dataHeaders = null;
	
	private Object originalCanonicalValue = null;

	public MultiSelectTableComboCellEditor(IComboBoxDataProvider dataProvider, List<String> dataHeaders, int maxVisibleItems) {
		super(dataProvider, maxVisibleItems);
		this.dataHeaders = dataHeaders;
	}

	@Override
	protected Control activateCell(Composite parent, Object originalCanonicalValue) {
		if (displayConverter  instanceof MultiValueDataConverter) {
			MultiValueDataConverter multiValueDataConverter = (MultiValueDataConverter) displayConverter;
			multiValueDataConverter.setShowDropDownMode(true);
		}
		
		this.originalCanonicalValue = originalCanonicalValue;
		return super.activateCell(parent, originalCanonicalValue);
	}
	
	@Override
	public NatCombo createEditorControl(Composite parent) {
		int style = this.editMode == EditModeEnum.INLINE ? SWT.NONE : SWT.BORDER;
		
		freeEdit = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.DT_DOMAIN_IS_CELL_EDITABLE);
		
		if (!this.freeEdit) {
			style |= SWT.READ_ONLY;
		}
		if (this.multiselect) {
			style |= SWT.MULTI;
		}
		if (this.useCheckbox) {
			style |= SWT.CHECK;
		}
		final MultiSelectTableCombo combo = this.iconImage == null ? 
				new MultiSelectTableCombo(parent, dataHeaders, this.cellStyle, this.maxVisibleItems, style, displayConverter, this.originalCanonicalValue)
				: new MultiSelectTableCombo(parent, dataHeaders, this.cellStyle, this.maxVisibleItems, style, this.iconImage, displayConverter, this.originalCanonicalValue);
		
		combo.setCursor(new Cursor(Display.getDefault(), SWT.CURSOR_IBEAM));

		if (multiselect) {
			combo.setMultiselectValueSeparator(this.multiselectValueSeparator);
			combo.setMultiselectTextBracket(this.multiselectTextPrefix, this.multiselectTextSuffix);
		}
		
		addNatComboListener(combo);
		return combo;
	}
}
