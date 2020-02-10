/**
 * 
 */
package com.tibco.cep.studio.core.validation.dt;

import static com.tibco.cep.studio.core.StudioCorePlugin.debug;
import static com.tibco.cep.studio.core.StudioCorePlugin.log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;


/**
 * @author aathalye
 *
 */
public class TableModelErrorsRecorder implements IMarkerErrorRecorder<DecisionTableSyntaxProblemParticipant> {
	
	public static final String TABLE_SYNTAX_ERROR_MARKER = "com.tibco.cep.decision.table.ui.syntaxProblem";
	/**
	 * The eclipse resource on which to report errors
	 */
	private IResource resource;
	
	private static final String CLASS = TableModelErrorsRecorder.class.getName();
	
	public TableModelErrorsRecorder(final IResource resource) {
		this.resource = resource;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.language.RuleErrorRecorder#reportError(com.tibco.be.parser.RuleError, com.tibco.cep.decision.table.model.dtmodel.Table, com.tibco.cep.decision.table.model.dtmodel.TableRuleSet, com.tibco.cep.decision.table.model.dtmodel.TableRule, com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable)
	 */
	
	public void reportError(RuleError rulErr, Table table, TableRuleSet trs,
			TableRule row, TableRuleVariable col) {
		if (col != null && !markerExists(col.getId(), rulErr.getMessage())) {
			//Only then add it
			addMarker(resource, trs, rulErr.getMessage(), row, col, rulErr.getType());
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.language.RuleErrorRecorder#reportError(com.tibco.be.parser.RuleError)
	 */
	
	public void reportError(RuleError rulErr) {
		addMarker(resource, null, rulErr.getMessage(), null, null, rulErr.getType());
	}
	
	/**
	 * 
	 * @param resource
	 * @param message
	 * @param rowLocation -> The id of the {@link TableRule}
	 * @param severity
	 */
	private void addMarker(IResource resource, 
			               TableRuleSet trs,
			               String message,
			               TableRule problemRow,
			               TableRuleVariable trv,
			               int severity) {
		try {
			IMarker marker = resource.createMarker(TABLE_SYNTAX_ERROR_MARKER);
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put(IMarker.MESSAGE, message);
			attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			if (trv != null) {
				//Get Column name and report it
				String problemColumnId = trv.getColId();
				debug(CLASS, "Problem Column Id {0}", problemColumnId);
				Column problemColumn = getColumn(trs, problemColumnId);
				String problemColumnName = problemColumn.getName();
				debug(CLASS, "Problem Column Name {0}", problemColumnName);
				ColumnType problemColumnType = problemColumn.getColumnType();
				debug(CLASS, "Problem Column Type {0}", problemColumnType);
				String problemColumnTypeString = (problemColumnType != null) ? problemColumnType.getLiteral() : "Column";
				String problemRowLocation = problemRow.getId();
				if (problemRowLocation != null) {
					attributes.put(IMarker.LOCATION, "[" + problemRowLocation + ", " + problemColumnTypeString + ": " + problemColumnName + "]");
				}
			}
			if (trv != null) {
				attributes.put(MARKER_TRV_ATTR, trv.getId());
			}
			marker.setAttributes(attributes);
		} catch (CoreException e) {
			log(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#clear()
	 */
	public void clear() {
		//Clear any previous markers
		try {
			resource.deleteMarkers(TABLE_SYNTAX_ERROR_MARKER, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	private Column getColumn(TableRuleSet tableRuleSet, String columnId) {
		Columns columns = tableRuleSet.getColumns();
		//Search for this column
		Column column = columns.search(columnId);
		if (column != null) {
			return column;
		}
		//Why should it come here?
		return null;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#reportAllErrors()
	 */
	public void reportAllErrors() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.IMarkerErrorRecorder#getExistingMarkers()
	 */
	public IMarker[] getExistingMarkers() {
		try {
			if (resource != null && resource.exists()) {
				return resource.findMarkers(TABLE_SYNTAX_ERROR_MARKER, false, IResource.DEPTH_ZERO);
			}
		} catch (CoreException e) {
			log(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#clearError(com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant)
	 */
	public void clearError(DecisionTableSyntaxProblemParticipant problemParticipant) {
		//Get its location and such
		String id = problemParticipant.getId();
		try {
			IMarker[] allMarkers = 
				resource.findMarkers(TABLE_SYNTAX_ERROR_MARKER, false, IResource.DEPTH_ZERO);
			List<IMarker> markerList = Arrays.asList(allMarkers);
			ListIterator<IMarker> iterator = markerList.listIterator();
			while (iterator.hasNext()) {
				IMarker marker = iterator.next();
				String trvId = (String)marker.getAttribute(MARKER_TRV_ATTR);
				if (id.equals(trvId)) {
					//Remove this marker
					marker.delete();
				}
			}
		} catch (CoreException e) {
			log(e);
		}
	}
	
	private boolean markerExists(String trvId, String message) {
		try {
			IMarker[] markers = resource.findMarkers(TABLE_SYNTAX_ERROR_MARKER, false, IResource.DEPTH_ZERO);
			for (IMarker marker : markers) {
				String attrTrvId = (String)marker.getAttribute(MARKER_TRV_ATTR);
				if (attrTrvId.equals(trvId)) {
					//Check if message is identical
					if (marker.getAttribute(IMarker.MESSAGE).equals(message)) {
						//Marker exists
						return true;
					}
				}
			}
		} catch (CoreException e) {
			log(e);
		}
		return false;
	}
}
