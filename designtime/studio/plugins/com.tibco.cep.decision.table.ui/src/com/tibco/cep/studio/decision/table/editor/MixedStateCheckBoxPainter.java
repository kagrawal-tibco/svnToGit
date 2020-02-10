package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

public class MixedStateCheckBoxPainter extends CheckBoxPainter {
	
	private final Image uncheckedImage;
    private final Image checkedImg;
    private final Image unsetImage;

	public MixedStateCheckBoxPainter() {
        this.checkedImg = GUIHelper.getImage("checked"); //$NON-NLS-1$
        this.uncheckedImage = DecisionTableUIPlugin.getInstance().getImage("icons/unchecked.gif");
        this.unsetImage = GUIHelper.getImage("unchecked"); //$NON-NLS-1$
    }
	
    @Override
    protected Image getImage(ILayerCell cell, IConfigRegistry configRegistry) {
    	MixedStateBoolean convertDataType = convert(cell, configRegistry);
    	switch (convertDataType) {
		case TRUE:
			return checkedImg;

		case FALSE:
			return uncheckedImage;
			
		case NOT_SET:
			return unsetImage;
			
		default:
			break;
		}
    	return super.getImage(cell, configRegistry);
    }
    
    protected MixedStateBoolean convert(ILayerCell cell, IConfigRegistry configRegistry) {
        if (cell.getDataValue() instanceof MixedStateBoolean) {
            return (MixedStateBoolean) cell.getDataValue();
        }
        IDisplayConverter displayConverter = configRegistry.getConfigAttribute(
                CellConfigAttributes.DISPLAY_CONVERTER,
                cell.getDisplayMode(),
                cell.getConfigLabels().getLabels());
        MixedStateBoolean convertedValue = null;
        if (displayConverter != null) {
            convertedValue =
                    (MixedStateBoolean) displayConverter.canonicalToDisplayValue(
                            cell, configRegistry, cell.getDataValue());
        }
        if (convertedValue == null) {
            convertedValue = MixedStateBoolean.NOT_SET;
        }
        return convertedValue;
    }
    
}
