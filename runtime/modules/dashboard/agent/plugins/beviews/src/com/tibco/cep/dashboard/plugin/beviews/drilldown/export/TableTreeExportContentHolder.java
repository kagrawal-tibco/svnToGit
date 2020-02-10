package com.tibco.cep.dashboard.plugin.beviews.drilldown.export;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableCellModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModelException;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableRequest;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.export.TupleFieldExportData;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh
 * 
 */
@SuppressWarnings("serial")
public abstract class TableTreeExportContentHolder extends ExportContentHolder {

	protected TableTreeExportContentHolder(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, exceptionHandler, messageGenerator);
	}

	/**
	 * @param parentModel
	 * @param tupleIndex
	 * @return
	 * @throws TableModelException
	 */
	protected List<TupleFieldExportData> getTupleData(TableModel parentModel, int tupleIndex) throws TableModelException {
		List<TupleFieldExportData> tupleData = new ArrayList<TupleFieldExportData>();
		for (int columnIndex = 0; columnIndex < parentModel.getColumnCount(); columnIndex++) {
			TableCellModel tableCellModel = parentModel.getCellValueAt(tupleIndex, columnIndex);
			tupleData.add(new TupleFieldExportData(String.valueOf(parentModel.getColumnNames().get(columnIndex)), tableCellModel.getExportValue(), false));
		}
		return tupleData;
	}

	public abstract void saveContent(TableModel parentModel, TableModel[] childModels, TableTree tableTree, TableRequest tableRequest) throws TableModelException;

}
