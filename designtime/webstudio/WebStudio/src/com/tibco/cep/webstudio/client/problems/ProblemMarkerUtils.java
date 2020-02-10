package com.tibco.cep.webstudio.client.problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.util.BooleanCallback;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * 
 * @author sasahoo
 *
 */
public class ProblemMarkerUtils {

	private static GlobalMessages msgs = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(ProblemMarkerUtils.class.getName());
	
	/**
	 * @param marker
	 * @param severity
	 * @param warnRecords
	 * @param errRecords
	 */
	public static void addMarkers(ProblemMarker marker, int severity, List<ProblemRecord> warnRecords, 
            List<ProblemRecord> errRecords) {
		ProblemRecord record = new ProblemRecord(marker);
		marker.setSeverity(severity);
		logger.debug("Adding Problem Marker with Severity = " + severity);
		if (severity == ProblemMarker.SEVERITY_WARNING) {
			record.setAttribute("ProblemType", msgs.text_warningsCount());
			if (marker.getExtension().equalsIgnoreCase(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue())) {
				record.setAttribute("#", "dt_warning16x16.png");
			} else if (marker.getExtension().equalsIgnoreCase(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue())) {
				record.setAttribute("#", "rti_warning16x16.png");
			} else {
				record.setAttribute("#", "quickfix_warning.gif");
			}
			warnRecords.add(record);
		} else if (severity == ProblemMarker.SEVERITY_ERROR) {
			record.setAttribute("ProblemType", msgs.text_errorsCount());
			if (marker.getExtension().equalsIgnoreCase(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue())) {
				record.setAttribute("#", "dt_error16x16.png");
			} else if (marker.getExtension().equalsIgnoreCase(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue())) {
				record.setAttribute("#", "rti_error16x16.png");
			} else {
				record.setAttribute("#", "quickfix_error.gif");
			}
			errRecords.add(record);
		}
		cacheMarkers(marker, record);
	}
	
	/**
	 * @param marker
	 * @param record
	 */
	private static void cacheMarkers(ProblemMarker marker, ProblemRecord record) {
		WebStudio.get().getEditorPanel().getProblemsPane().getMarkerRecordsMap().put(marker, record);
		Map<String, List<ProblemMarker>> map =  WebStudio.get().getEditorPanel().getProblemsPane().getUriMarkerMap();
		if (map.containsKey(marker.getURI())) {
			map.get(marker.getURI()).add(marker);
		} else {
			List<ProblemMarker> list = new ArrayList<ProblemMarker>();
			list.add(marker);
			map.put(marker.getURI(), list);
		}
	}
	
	/**
	 * @param URI
	 * @param types
	 * @param all
	 */
	public static void removeMarker(String URI, List<String> types, boolean all) {
		Map<String, List<ProblemMarker>> map =  WebStudio.get().getEditorPanel().getProblemsPane().getUriMarkerMap();
		Map<ProblemMarker, ProblemRecord> markerRecordMap = WebStudio.get().getEditorPanel().getProblemsPane().getMarkerRecordsMap();
		List<ProblemRecord> warnRecords = WebStudio.get().getEditorPanel().getProblemsPane().getWarningRecords();
		List<ProblemRecord> errRecords = WebStudio.get().getEditorPanel().getProblemsPane().getErrorRecords();
		if (map.containsKey(URI)) {
			List<ProblemMarker> markers = map.get(URI);
			Set<ProblemMarker> removedMarkers = new HashSet<ProblemMarker>();
			for (ProblemMarker marker :  markers) {
				if (markerRecordMap.containsKey(marker)) {
					ProblemRecord record = markerRecordMap.get(marker);
					boolean remove = false;
					if (all) {
						remove = true;
					} else {
						if (types.contains(marker.getType())) {
							remove = true;
						}
					}
					if (remove) {
						if (warnRecords.contains(record)) {
							removedMarkers.add(marker);
							markerRecordMap.remove(marker);
							warnRecords.remove(record);
						}
						if (errRecords.contains(record)) {
							removedMarkers.add(marker);
							markerRecordMap.remove(marker);
							errRecords.remove(record);
						}
					}
				}
			}
			markers.removeAll(removedMarkers);
		}
	}

	/**
	 * @param warnRecords
	 * @param errRecords
	 * @param showNoIssuesMsg
	 */	
 	public static void updateProblemRecords(List<ProblemRecord> warnRecords, List<ProblemRecord> errRecords, boolean showNoIssuesMsg) {
		updateProblemRecords(warnRecords, errRecords, showNoIssuesMsg, false);
	}
 	
	/**
	 * @param warnRecords
	 * @param errRecords
	 * @param showNoIssuesMsg
	 * @param isDTAnalyzerProblems
	 */
	public static void updateProblemRecords(List<ProblemRecord> warnRecords, List<ProblemRecord> errRecords, boolean showNoIssuesMsg, boolean isDTPageAnalyzerProblemsView) {
		WebStudio.get().getEditorPanel().setShowPropertiesForAll(true);
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		int warns = warnRecords == null ? 0 : warnRecords.size(); 
		int errors = errRecords == null ? 0 : errRecords.size(); 
		if (warns > 0 ) {
			WebStudio.get().getEditorPanel().getProblemsPane().getWarningRecords().addAll(0, warnRecords);
		}
		if (errors > 0 ) {
			WebStudio.get().getEditorPanel().getProblemsPane().getErrorRecords().addAll(0, errRecords);
		}
		WebStudio.get().getEditorPanel().getProblemsPane().refreshProblemsGrid();
		
		showProblems(errors, warns, showNoIssuesMsg, isDTPageAnalyzerProblemsView);
	}
	
 	public static void showProblems(final int errors, final int warns, boolean showNoIssuesMsg) {
		showProblems(errors, warns, showNoIssuesMsg, false);
	}
 	
	public static void showProblems(final int errors, final int warns, boolean showNoIssuesMsg, boolean isDTPageAnalyzerProblemsView) {
		String showIssuesMsg = null;
		if (isDTPageAnalyzerProblemsView) {
			showIssuesMsg = msgs.wsShowTAIssues(errors, warns);
		} else {
			showIssuesMsg = msgs.wsShowIssues(errors, warns);
		}
		
		String problemMessage = (warns > 0 || errors > 0) ? showIssuesMsg : (showNoIssuesMsg) ? msgs.wsShowNoIssues() : null;

		if (problemMessage != null) {
			CustomSC.say(problemMessage, new BooleanCallback() {
				@Override
				public void execute(Boolean value) {
					if ((warns > 0 || errors > 0) && value) {
						WebStudio.get().getEditorPanel().showProblemPane();
					}
				}
			});
		}
	}
	
}
