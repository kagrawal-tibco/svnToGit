package com.tibco.cep.webstudio.server.ui;

import java.util.HashMap;
import java.util.Map;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tomsawyer.view.drawing.TSModelDrawingView;

public class ProcessWebDiagramDataRepository {

	private Map<String, Process> projectProcessMap = new HashMap<String, Process>();
	private Map<String, TSModelDrawingView> viewMap = new HashMap<String, TSModelDrawingView>();

	private static ProcessWebDiagramDataRepository instance;

	private ProcessWebDiagramDataRepository() {
	}

	public static ProcessWebDiagramDataRepository getInstance() {
		if (instance == null) {
			instance = new ProcessWebDiagramDataRepository();
		}
		return instance;
	}

	/**
	 * 
	 * @param viewId
	 * @param view
	 */
	public void addView(String viewId, TSModelDrawingView view) {
		viewMap.put(viewId, view);
	}

	/**
	 * 
	 * @param viewId
	 * @return
	 */
	public TSModelDrawingView getView(String viewId) {
		return viewMap.get(viewId);
	}

	/**
	 * 
	 * @param project
	 * @param artifactPath
	 * @param model
	 */
	public void addProcess(String viewId, Process process) {
		projectProcessMap.put(viewId, process);
	}

	/**
	 * 
	 * @param project
	 * @param artifactPath
	 * @param model
	 */
	public Process getProcess(String viewId) {
		return projectProcessMap.get(viewId);
	}
}
