package com.tibco.cep.dashboard.plugin.beviews.querymgr.export;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.NextInLine;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.DrilldownProvider;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentNode;
import com.tibco.cep.dashboard.plugin.beviews.export.TupleFieldExportData;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class QueryExportContentHolder extends ExportContentHolder {

	private static final long serialVersionUID = -4623339802377843564L;

	private int exportDepth;

	private int exportTypeCount;

	private int exportAllCount;

	private QuerySpec querySpec;

	private DrilldownProvider drilldownProvider;

	private int totalCount;

	public QueryExportContentHolder(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, exceptionHandler, messageGenerator);
		totalCount = 0;
	}

	public final int getExportDepth() {
		return exportDepth;
	}

	public final void setExportDepth(int depth) {
		this.exportDepth = depth;

	}

	public final int getExportTypeCount() {
		return exportTypeCount;
	}

	public final void setExportTypeCount(int typeCount) {
		this.exportTypeCount = typeCount;
	}

	public final int getExportAllCount() {
		return exportAllCount;
	}

	public final void setExportAllCount(int allCount) {
		this.exportAllCount = allCount;
	}

	public final void setQuerySpec(QuerySpec querySpec) {
		this.querySpec = querySpec;
	}

	public final void setDrilldownProvider(DrilldownProvider drilldownProvider) {
		this.drilldownProvider = drilldownProvider;
	}

	public void execute() throws Exception {
		int depth = exportDepth;
		// create the root content node based on the incoming query spec
		String typeID = querySpec.getSchema().getTypeID();
		root = new ExportContentNode("0", ExportContentNode.TYPE_HEADER_ROW, getTypeDisplayName(typeID));
		String resultSetID = drilldownProvider.getInstanceData(querySpec, null, null, false);
		List<Tuple> tuples = getTuplesFromResultSet(resultSetID);
		processTuples(root, tuples, --depth);
	}

	private void processTuples(ExportContentNode parent, List<Tuple> tuples, int depth) throws QueryException {
		if (totalCount == exportAllCount){
			return;
		}
		Map<Tuple,ExportContentNode> tupleToTupleContentNodesMap = new LinkedHashMap<Tuple, ExportContentNode>();
		for (Tuple tuple : tuples) {
			if (totalCount < exportAllCount) {
			// create an export format of the tuple
				List<TupleFieldExportData> exportFormat = convertTupleToExportFormat(tuple);
				ExportContentNode tupleContentNode = new ExportContentNode(parent.getContentPath() + "_" + 0, ExportContentNode.TYPE_TUPLE_ROW, exportFormat);
				parent.add(tupleContentNode);
				tupleToTupleContentNodesMap.put(tuple, tupleContentNode);
				logger.log(Level.INFO, "Including "+tuple.getFieldValueByName("@extid"));
				totalCount++;
			}
		}
		if (depth > -1) {
			for (Map.Entry<Tuple, ExportContentNode> entry : tupleToTupleContentNodesMap.entrySet()) {
				List<NextInLine> nextInLines = drilldownProvider.getNextInLines(entry.getKey().getTypeId(), entry.getKey().getId(), null);
				if (nextInLines != null && nextInLines.isEmpty() == false){
					for (NextInLine nextInLine : nextInLines) {
						String resultSetID = drilldownProvider.getInstanceData(entry.getKey().getTypeId(), entry.getKey().getId(), nextInLine.getTypeID(), null, null, null, false);
						List<Tuple> childTuples = getTuplesFromResultSet(resultSetID);
						if (childTuples.isEmpty() == false){
							ExportContentNode childTupleHeaderNode = new ExportContentNode(entry.getValue().getContentPath() + "_" + 0, ExportContentNode.TYPE_HEADER_ROW, nextInLine.getName());
							processTuples(childTupleHeaderNode,childTuples,--depth);
							if (childTupleHeaderNode.getChildCount() > 0) {
								entry.getValue().add(childTupleHeaderNode);
							}
						}
					}
				}
			}
		}
	}

	private List<Tuple> getTuplesFromResultSet(String resultSetID) throws QueryException{
		try {
			return drilldownProvider.getNextSet(resultSetID, exportTypeCount);
		} finally {
			drilldownProvider.close(resultSetID);
		}
	}

	private List<TupleFieldExportData> convertTupleToExportFormat(Tuple tuple) {
		TupleSchema schema = tuple.getSchema();
		int fieldCount = schema.getFieldCount();
		// add the tuple to the parent
		List<TupleFieldExportData> tupleInExportFormat = new ArrayList<TupleFieldExportData>(fieldCount);
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField schemaField = schema.getFieldByPosition(i);
			FieldValue fieldValue = tuple.getFieldValueByID(schemaField.getFieldID());
			tupleInExportFormat.add(new TupleFieldExportData(schemaField.getFieldName(), fieldValue, schemaField.isSystemField()));
		}
		return tupleInExportFormat;
	}

	protected String getTypeDisplayName(String typeid) {
		return EntityCache.getInstance().getEntity(typeid).getName();
	}

}
