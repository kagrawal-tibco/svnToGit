package com.jidesoft.decision;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.MultilineTableCellRenderer;
import com.jidesoft.grid.SortableTableModel;
import com.tibco.cep.decision.table.editors.DecisionTableDesignViewer;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.ExpressionBodyUtil;

public class RuleVariableCellRenderer extends MultilineTableCellRenderer {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8160363157474667249L;
	
	DecisionTableDesignViewer dv;
	

    public RuleVariableCellRenderer(DecisionTableDesignViewer dtdv) {
		super();
		dv = dtdv;
//		setLineWrap(true);
//		setWrapStyleWord(true);
//		setRows(1);
	}

    public void dispose() {
    	dv = null;
    }
    
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        FilterableTableModel fModel = (FilterableTableModel)table.getModel(); 
        DecisionTableModel dtModel = (DecisionTableModel)((SortableTableModel)fModel.getActualModel()).getActualModel();
        TableTypes tblType = dtModel.getDecisionDataModel().getTableType();
        Component label = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (TableTypes.EXCEPTION_TABLE == tblType) {
        	boolean expShowTextAlias = dv.getExpTextAliasFlag();
        	if (value instanceof TableRuleVariable) {
        		TableRuleVariable rv = (TableRuleVariable) value;
        		String expr = rv.getExpr();
        		String convExpr = ExpressionBodyUtil.getRuleVariableConverterExpBody(expr, expShowTextAlias, table.getColumnName(column));
        		label.getAccessibleContext().getAccessibleEditableText().setTextContents(convExpr);
        	}
        }
        if (value instanceof TableRuleVariable) {
            TableRuleVariable ruleVariable = (TableRuleVariable) value;
            label.setEnabled(ruleVariable.isEnabled());
            ((JComponent) label).setToolTipText(ruleVariable.getComment());
            if (RuleVariableConverter.isGraphicsAliasVisible()) {
                ConverterContext converterContext = getConverterContext();
                if (converterContext.getUserObject() instanceof Argument) {
                    Argument argument = ((Argument) converterContext.getUserObject());
                    String graphicalPath = argument.getProperty().getGraphicalPath();
                    ((JLabel) label).setIcon(getImageIcon(graphicalPath));
                }
            }
        }
        return label;
    }
    
    
	public static Icon getImageIcon(String path) {
        IPath defaultPath = Platform.getLocation();
        File dir = new File(defaultPath + "/icons");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return new ImageIcon(dir.toString() + "/" + path);
            }
        }
        return null;
    }
}
