package com.tibco.cep.webstudio.client.decisiontable.constraint;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_COLUMN_NAME;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_CONFLICTING_ACTIONS_RULEID;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_DUPLICATE_RULEID;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_RANGE_MAX_VALUE;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_RANGE_MIN_VALUE;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_RANGE_PROBLEM;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_RULE_COMBINATION_PROBLEM;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableConstraintConstants.MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.addMarkers;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.removeMarker;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.xml.client.Element;
import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.IAutofixableProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RANGE_TYPE_PROBLEM;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RuleCombinationProblemEvent;
import com.tibco.cep.studio.util.logger.problems.UncoveredDomainEntryProblemEvent;
import com.tibco.cep.webstudio.client.decisiontable.AbstractTableAnalyzerAction;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableProblemMarker;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableAnalyzeAction extends AbstractTableAnalyzerAction implements HttpSuccessHandler, HttpFailureHandler {
	
	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	public List<ProblemEvent> fProblemEvents = new ArrayList<ProblemEvent>();
	private String decisionTable;
	private String projectName;
	private String path;
	private String uri;
	private int currentPage;
	protected HttpRequest request;
	
	/**
	 * @param fCurrentEditor
	 * @param fCurrentOptimizedTable
	 * @param fComponents
	 * @param fRangeColumnValues
	 */
	public DecisionTableAnalyzeAction(DecisionTableEditor fCurrentEditor) {
		super(fCurrentEditor, null, null);
		decisionTable = fCurrentEditor.getTable().getFolder() + fCurrentEditor.getTable().getName();
		projectName = fCurrentEditor.getProjectName();
		path = fCurrentEditor.getTable().getFolder();
		uri = projectName + path + fCurrentEditor.getTable().getName();
		currentPage = fCurrentEditor.getCurrentPage();
		this.request = new HttpRequest();
	}

	@Override
	public void run() {
		if (!fCurrentEditor.isAnalyzed()) {
			indeterminateProgress(dtMessages.wsdtanalyze(), false);
		} else {
			indeterminateProgress(dtMessages.wsdtanalyzerefreshProblems(), false);
		}
		analyze();
	}

	public void analyze() {
		String artifactPath = decisionTable;
		String artifactExtention = "rulefunctionimpl";

		ArtifactUtil.addHandlers(this);

		this.request.clearRequestParameters();
		this.request.setMethod(HttpMethod.GET);
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, artifactExtention));
		this.request.addRequestParameters(new RequestParameter("currentPage", currentPage));
		
		String url = ServerEndpoints.RMS_DECISION_TABLE_ANALYZE_ACTION.getURL();
		request.submit(url);		
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DECISION_TABLE_ANALYZE_ACTION.getURL()) != -1) {			
			final Map<String, List<ProblemEvent>> pageToProblemsMap = DecisionTableAnalyzerResponseHandler.loadProblems(event.getData());
			List<ProblemRecord> warnrecords = new ArrayList<ProblemRecord>();
			List<ProblemRecord> errorrecords = new ArrayList<ProblemRecord>();
			Set<String> pageNums = pageToProblemsMap.keySet();
			for (String pageNum : pageNums) {
				List<ProblemEvent> problemEvents = pageToProblemsMap.get(pageNum);
				for (ProblemEvent problemEvent : problemEvents) {
					try {
						processAnalyzerProblemEvent(problemEvent, fCurrentEditor.getTable(), pageNum, warnrecords, errorrecords);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			for (ProblemRecord warnrecord : warnrecords) {				
				enrichProblemRecord(warnrecord);
			}
			for (ProblemRecord errorrecord : errorrecords) {
				enrichProblemRecord(errorrecord);
			}
			List<String> types = new ArrayList<String>();
			types.add(ANALYZE_PROBLEM_TYPES.ANALYZE_FIXABLE_MARKER_NAME.getLiteral());
			types.add(ANALYZE_PROBLEM_TYPES.ANALYZE_RULECOMB_MARKER_NAME.getLiteral());
			removeMarker(uri, types, false);
			updateProblemRecords(warnrecords, errorrecords, true, !fCurrentEditor.getTable().getDecisionTable().isShowAll());
			fCurrentEditor.setAnalyzed(true);
			indeterminateProgress("", true);
			removeHandlers(this);			
		}		
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		ArtifactUtil.removeHandlers(this);
		LoadingMask.clearMask();
		Element docElement = event.getData();
		String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		showError(responseMessage);		
	}
		
	/**
	 * @param problemEvent
	 * @param table
	 * @param warnrecords
	 * @param errrecords
	 * @throws Exception
	 */
	private void processAnalyzerProblemEvent(ProblemEvent problemEvent, 
			                                Table table,
			                                String pageNum,
			                                List<ProblemRecord> warnrecords, 
			                                List<ProblemRecord> errrecords) throws Exception {
		String message = problemEvent.getMessage();
		int location = problemEvent.getLocation();
		int severity = problemEvent.getSeverity();

		//Else ignore it
		if (problemEvent instanceof IAutofixableProblemEvent) {
			ProblemMarker marker = new DecisionTableProblemMarker(table.getName(), 
						uri,  path, ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase(), 
						ANALYZE_PROBLEM_TYPES.ANALYZE_FIXABLE_MARKER_NAME.getLiteral(), pageNum);
			Map<String, Object> attributes = marker.getAttributes();
			marker.setMessage(message);
			marker.setProject(projectName);
			marker.getAttributes().put(ProblemMarker.SEVERITY, severity);
			if (location >= 0) {
				marker.setLocation(String.valueOf(location));
			}
			if (problemEvent instanceof RangeProblemEvent) {
				RangeProblemEvent rangeProblemEvent = (RangeProblemEvent)problemEvent;

				attributes.put(MARKER_ATTR_COLUMN_NAME, 
						rangeProblemEvent.getGuiltyColumnName());
				attributes.put(MARKER_ATTR_RANGE_MIN_VALUE,
						rangeProblemEvent.getMin());
				attributes.put(MARKER_ATTR_RANGE_MAX_VALUE,
						rangeProblemEvent.getMax());
				attributes.put(MARKER_ATTR_RANGE_PROBLEM,
						rangeProblemEvent.getRangeTypeProblem().toString());
			}
			if (problemEvent instanceof DuplicateRuleProblemEvent) {
				DuplicateRuleProblemEvent duplicateRulePE = 
						(DuplicateRuleProblemEvent)problemEvent;
				attributes.put(MARKER_ATTR_DUPLICATE_RULEID,
						duplicateRulePE.getDuplicateRuleID());
			}
			if (problemEvent instanceof ConflictingActionsProblemEvent) {
				ConflictingActionsProblemEvent conflictingRulesPE = 
						(ConflictingActionsProblemEvent)problemEvent;
				attributes.put(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID,
						conflictingRulesPE.getProblemRuleId());
			}
			if (problemEvent instanceof UncoveredDomainEntryProblemEvent) {
				UncoveredDomainEntryProblemEvent domainEntryPE = 
						(UncoveredDomainEntryProblemEvent)problemEvent;
				attributes.put(MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY,
						domainEntryPE.getUncoveredDomainValue());
				attributes.put(MARKER_ATTR_COLUMN_NAME, 
						domainEntryPE.getColumnName());
			}
			addMarkers(marker, severity, warnrecords, errrecords);
		} else {
			if (problemEvent instanceof RuleCombinationProblemEvent) {
				ProblemMarker marker = new DecisionTableProblemMarker(table.getName(), uri, path, 
						ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase(),ANALYZE_PROBLEM_TYPES.ANALYZE_RULECOMB_MARKER_NAME.getLiteral(), pageNum);
				marker.setMessage(message);
				marker.setProject(projectName);
				marker.getAttributes().put(ProblemMarker.SEVERITY, severity);
				if (location >= 0) {
					marker.setLocation(String.valueOf(location));
				}
				marker.getAttributes().put(MARKER_ATTR_RULE_COMBINATION_PROBLEM, true);
				addMarkers(marker, severity, warnrecords, errrecords);
			} else if (problemEvent instanceof MissingEqualsCriterionProblemEvent) {
				ProblemMarker marker = new DecisionTableProblemMarker(table.getName(), uri, path, 
						ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase(),ANALYZE_PROBLEM_TYPES.ANALYZE_RULECOMB_MARKER_NAME.getLiteral(), pageNum);
				marker.setMessage(message);
				marker.setProject(projectName);
				marker.getAttributes().put(ProblemMarker.SEVERITY, severity);
				if (location >= 0) {
					marker.setLocation(String.valueOf(location));
				}
				marker.getAttributes().put(MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM, true);
				addMarkers(marker, severity, warnrecords, errrecords);
			} else if (problemEvent instanceof OverlappingRangeProblemEvent) {
				ProblemMarker marker =  new DecisionTableProblemMarker(table.getName(), uri, path, 
						ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase(),ANALYZE_PROBLEM_TYPES.ANALYZE_RULECOMB_MARKER_NAME.getLiteral(), pageNum);
				marker.setMessage(message);
				marker.setProject(projectName);
				if (location >= 0) {
					marker.setLocation(String.valueOf(location));
				}
				marker.getAttributes().put(ProblemMarker.SEVERITY, severity);
				OverlappingRangeProblemEvent overlappingRulesPE = 
						(OverlappingRangeProblemEvent)problemEvent;
				marker.getAttributes().put(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID,
						overlappingRulesPE.getProblemRuleId());
				addMarkers(marker, severity, warnrecords, errrecords);
			}

		}
	}

	private void enrichProblemRecord(ProblemRecord record) {
		ProblemMarker marker = record.getMarker();
		StringBuffer strBuff = new StringBuffer();
		boolean showAllPages = fCurrentEditor.getTable().getDecisionTable().isShowAll();
		if (!showAllPages && (marker instanceof DecisionTableProblemMarker)) {
			DecisionTableProblemMarker dtProblemMarker = (DecisionTableProblemMarker) marker;
			if (dtProblemMarker.getPageNum() != null) {
				strBuff.append(dtMessages.dt_pagination_page() + " - ")
						.append(dtProblemMarker.getPageNum())
						.append(", ");
			} 
		}
		
		if (marker.getLocation() != null) {
			strBuff.append(dtMessages.dtPropertyTabRule() + " - ")
					.append(marker.getLocation());
		}
		record.setAttribute(ProblemMarker.LOCATION, strBuff.toString());		
		record.setAttribute(ProblemMarker.RESOURCE, marker.getResource());
	}
	
	private char[] getRangeCharacters(Range range) {
		//Return the complement of set based on rangekind
		//For problem analysis we need complement range characters
		Expression expression = range.cell.getExpression();
		//Get the first entry inside the operationRangeKind Map
		//Set<Entry<String, Byte>> entrySet = expression.operandRangeKinds.entrySet();
		//Entry<String, Byte>[] rangeKind = entrySet.toArray(new Entry[entrySet.size()]);
		int rangeKind = expression.getRangeKind();
		switch (rangeKind) {
		case Operator.RANGE_BOUNDED: {
			return new char[] { '(', ')' };
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED:
		{
			return new char[] { '(', ']' };
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED:
		{
			return new char[] { '[', ')' };
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED:
		{
			return new char[] { '[', ']' };
		}
		}
		return new char[] { '(', ')' };
	}


	
	private class Range implements Comparable<Range> {

		public Object min;
		public Object max;
		public String columnName;
		public Cell cell;

		public Range(Object min, Object max, String columnName, Cell cell) {
			this.min = min;
			this.max = max;
			this.columnName = columnName;
			this.cell = cell;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Range)) {
				return false;
			}
			Range range = (Range) obj;
			return (range.min.equals(min) && range.max.equals(max)
					&& range.columnName.equals(columnName) && cell.getExpression().getRangeKind() == range.cell.getExpression().getRangeKind()); // also
																																// check
																																// cell?
		}

		public int compareTo(Range r) {
			if (this.min instanceof Long && r.min instanceof Long) {
				Long min=(Long)r.min;
				Long m= (Long)this.min;
				return m> min ? -1 : 1;
			} else if (this.min instanceof Integer && r.min instanceof Integer) {
				Integer min=(Integer)r.min;
				Integer m= (Integer)this.min;
				return m> min ? -1 : 1;
			} else if (this.min instanceof Date && r.min instanceof Date) {
				return ((Date) this.min).compareTo((Date) r.min);
			}
			return 0;
		}
		
		public void extendRange(Range r) {
			extendRange(r.min, r.max);
		}

		public void extendRange(Object opMin, Object opMax) {

			if (this.min instanceof Long && opMin instanceof Long) {
				Long min=(Long)opMin;
				Long m= (Long)this.min;
				if(m> min){
					this.min=opMin;
				}
			} else if (this.min instanceof Integer && opMin instanceof Integer) {
				Integer min=(Integer)opMin;
				Integer m= (Integer)this.min;
				if(m> min){
					this.min=opMin;
				}
			} else if (this.min instanceof Date && opMin instanceof Date) {
				if(((Date) this.min).compareTo((Date)opMin)>0){
					this.min=opMin;
				}
			}
			


			if (this.max instanceof Long && opMax instanceof Long) {
				Long max=(Long)opMax;
				Long m= (Long)this.max;
				if(m<max){
					this.max=opMax;
				}
			} else if (this.max instanceof Integer && opMax instanceof Integer) {
				Integer max=(Integer)opMax;
				Integer m= (Integer)this.max;
				if(m< max){
					this.max=opMax;
				}
			} else if (this.max instanceof Date && opMax instanceof Date) {
				if(((Date) this.max).compareTo((Date)opMax)<0){
					this.max=opMax;
				}
			}
		
		
		
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			char[] rangeCharacters = getRangeCharacters(this);
		
			if ((min instanceof Integer && Integer.valueOf(min.toString()) == Integer.MIN_VALUE)
					|| (min instanceof Long &&  Long.valueOf(min.toString()) == Long.MIN_VALUE)) {
				buffer.append(rangeCharacters[0]);
				buffer.append("< ");
				buffer.append(max);
				buffer.append(rangeCharacters[1]);
				return buffer.toString();
			}
			if ((max instanceof Integer && Integer.valueOf(max.toString())== Integer.MAX_VALUE)
					|| (max instanceof Long && Long.valueOf(max.toString())== Long.MAX_VALUE)) {
				buffer.append(rangeCharacters[0]);
				buffer.append("> ");
				buffer.append(min);
				buffer.append(rangeCharacters[1]);
				return buffer.toString();
			}
			buffer.append(rangeCharacters[0]);
			buffer.append(min);
			buffer.append(", ");
			buffer.append(max);
			buffer.append(rangeCharacters[1]);
			return buffer.toString();
		}

	}
	
	public void reportProblem(String resource, String message,
			int lineNumber, int start, int length, int severity) {
		
	//		ProblemEvent problemEvent = new ProblemEvent(null, message, resource,
	//				lineNumber, severity);
			//Do not add this to the collection for the time-being
	//fProblemEvents.add(problemEvent);
	}

	public void reportRangeProblem(String resource, String message,
			String columnName, int minValue, int maxValue,
			RANGE_TYPE_PROBLEM rangeTypeProblem, int lineNumber, int severity) {
		
		ProblemEvent problemEvent = new RangeProblemEvent(null, null, message,
				null, resource, minValue, maxValue, columnName,
				rangeTypeProblem, lineNumber, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void  reportOverlappingRangeProblem(final String resource,
            final String message, 
            int ruleId, 
            final int severity) {
		
		ProblemEvent problemEvent = new OverlappingRangeProblemEvent(null, message, resource,
				ruleId,  severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportRuleCombinationProblem(final String resource,
			                                    final String message, 
			                                    String location, 
			                                    final int severity) {

		ProblemEvent problemEvent = new RuleCombinationProblemEvent(null, message, resource,
				Integer.parseInt(location),  severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportDuplicatesProblem(final String resource,
			final String message, final int duplicateRuleID, final int severity) {

		
		ProblemEvent problemEvent = new DuplicateRuleProblemEvent(null,
				message, resource, duplicateRuleID, -1, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void reportConflictingActionsProblem(final String resource,
			                                       final String message, 
			                                       final int problemRuleID, final int severity) {
		ProblemEvent problemEvent = new ConflictingActionsProblemEvent(null,
				message, resource, problemRuleID, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void reportUncoveredDomainEntriesProblem(final String resource,
			final String message, final String domainValue, final String columnName, final int severity) {

		ProblemEvent problemEvent = new UncoveredDomainEntryProblemEvent(null,
				message, resource, domainValue, columnName, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void reportUncoveredEqualsProblem(final String resource,
			                                    final String message, 
			                                    final Integer expression,
			                                    final String columnName,
			                                    final int severity) {
		ProblemEvent problemEvent = new MissingEqualsCriterionProblemEvent(
				null, message, resource, expression, columnName, severity);
		fProblemEvents.add(problemEvent);
	}
}