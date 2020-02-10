package com.jidesoft.decision.cell.editors.custom;

import static com.jidesoft.decision.cell.editors.utils.CellEditorUtils.isAsterixedExpression;

import java.lang.reflect.Array;

import javax.swing.DefaultComboBoxModel;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.EnumConverter;
import com.jidesoft.grid.EditorContext;

@SuppressWarnings("serial")
public class DefaultMultipleEnumCellEditor extends CheckBoxTableComboBoxCellEditor {
	
    private DefaultMultipleEnumConverter _enumConverter;
    
    protected Object[] displayValues;
	protected Object[] expressionValues;

    public DefaultMultipleEnumCellEditor() {
    }

    public DefaultMultipleEnumCellEditor(DefaultMultipleEnumConverter enumConverter) {
        super(enumConverter.getEnumConverter().getObjects(), Array.newInstance(enumConverter.getEnumConverter().getType(), 0).getClass());
        setConverterContext(enumConverter.getContext());
        //setEnumConverter(_enumConverter, _enumConverter.getContext());
    }

    transient private EditorContext _context;

    public DefaultMultipleEnumConverter getEnumConverter() {
        return _enumConverter;
    }

    public void setEnumConverter(DefaultMultipleEnumConverter multiEnumConverter, ConverterContext converterContext) {
        _enumConverter = multiEnumConverter;
         if (multiEnumConverter != null) {
            EnumConverter enumConverter = multiEnumConverter.getEnumConverter();
            if (enumConverter != null) {
                setComboBoxModel(new DefaultComboBoxModel(enumConverter.getObjects()));
                setComboBoxType(Array.newInstance(enumConverter.getType(), 0).getClass());
                setConverter(multiEnumConverter);
               //this.setConverterContext(converterContext);
               
            }
            else {
                setComboBoxType(String[].class);
            }
        }
        else {
            setComboBoxType(String[].class);
        }
    }
    
	public EditorContext getContext() {
        if (_context == null && _enumConverter != null) {
            _context = new EditorContext(_enumConverter.getEnumConverter().getName() + "[]");
        }
        return _context;
    }

    @Override
    public void setCellEditorValue(Object object) {
    	if(object != null) {
    		if (object instanceof String && _enumConverter != null) {
    			object = _enumConverter.fromString("" + object, ConverterContext.getElementConverterContext(_enumConverter.getContext()));
    		}
    		super.setCellEditorValue(object);
    	}
    }

    @Override
    public Object getCellEditorValue() {
    	Object cellEditorValue = super.getCellEditorValue();
        if(cellEditorValue == null) {
        	cellEditorValue = "";
        }
        if (_enumConverter != null) {
    	
        	Object[] displayValues = ((DefaultConverterContext)getConverterContext()).getDisplayValues();
        	Object[] expressionValues = ((DefaultConverterContext)getConverterContext()).getExpressionValues();
        	
        	setDisplayValues(displayValues);
        	setExpressionValues(expressionValues);
        	
        //	if(DecisionTablePlugin.getDefault().getPluginPreferences().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION)) {
        		if(cellEditorValue instanceof String[]) {
        			String[] cellEditorValues = (String[]) cellEditorValue;
        			int i=0;
        			for(String cellValue : cellEditorValues) {
        				if(cellValue.equals("<NA>")) {
        					cellEditorValues[i] = (String)expressionValues[i];
        					displayValues[i] = expressionValues[i];
        				}
        				i++;
        			}
        		}
        	//}
                	
            String cellValueString = _enumConverter.toString(cellEditorValue, ConverterContext.getElementConverterContext(_enumConverter.getContext()));
            if (isAsterixedExpression(cellValueString)) {
    			//Only asterix should be set as expression.
    			cellValueString = "*";

    		}
            if(cellValueString.equals("")) {
            	((DefaultConverterContext)getConverterContext()).setDisplayValues(new String[]{cellValueString});
            	((DefaultConverterContext)getConverterContext()).setExpressionValues(new String[]{cellValueString});
            }
            if (!((DefaultConverterContext)getConverterContext()).dropdown) {
            	((DefaultConverterContext)getConverterContext()).setDisplayValues(new String[]{cellValueString});
            	((DefaultConverterContext)getConverterContext()).setExpressionValues(new String[]{cellValueString});
            }
            ((DefaultConverterContext)getConverterContext()).dropdown = false;
            return cellValueString;
        }
        else {
        	return cellEditorValue;
        }
    }
    
	public void setDisplayValues(Object[] displayValues) {
		this.displayValues = displayValues;
	}

	public Object[] getExpressionValues() {
		return expressionValues;
	}

	public void setExpressionValues(Object[] expressionValues) {
		this.expressionValues = expressionValues;
	}
	
    public Object[] getDisplayValues() {
		return displayValues;
	}
}