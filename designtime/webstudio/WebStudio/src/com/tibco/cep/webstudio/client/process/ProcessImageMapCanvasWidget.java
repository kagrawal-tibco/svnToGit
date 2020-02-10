package com.tibco.cep.webstudio.client.process;

import java.io.Serializable;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.server.ui.tools.ProcessElementSelectTool;
import com.tomsawyer.util.shared.TSCallback;
import com.tomsawyer.visualization.gwt.client.widget.canvas.TSCanvasWidget;
import com.tomsawyer.visualization.gwt.client.widget.canvas.tool.TSMouseTool;
import com.tomsawyer.visualization.gwt.client.widget.canvas.tool.TSSelectMouseTool;
import com.tomsawyer.web.client.command.TSToolCommand;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewWidget;
import com.tomsawyer.web.client.view.drawing.TSImageMapCanvasWidget;
import com.tomsawyer.web.client.view.drawing.tool.TSPerspectivesMouseToolHelper;
import com.tomsawyer.web.client.view.drawing.tool.predefinedtool.imagemap.TSImageMapAtTool;
import com.tomsawyer.web.client.view.drawing.tool.predefinedtool.imagemap.TSImageMapBetweenTool;

/**
 * This class is used to create the image widget to hold the process data.
 * 
 * @author sasahoo,dijadhav
 * 
 */
public class ProcessImageMapCanvasWidget extends TSImageMapCanvasWidget {

	public ProcessImageMapCanvasWidget(TSDrawingViewWidget drawingViewWidget) {
		super(drawingViewWidget);
		setSize("100%", "100%");
		enableBlinking(false);
		getElement().getStyle().setOverflow(Overflow.HIDDEN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.client.view.drawing.TSImageMapCanvasWidget#newSelectTool
	 * (java.lang.String)
	 */
	protected TSSelectMouseTool newSelectTool(String toolID) {
		ProcessElementSelectTool processElementSelectTool = new ProcessElementSelectTool(
				this, toolID);
		return processElementSelectTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.client.view.drawing.TSImageMapCanvasWidget#isBlinking()
	 */
	@Override
	public boolean isBlinking() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tomsawyer.web.client.view.drawing.TSImageMapCanvasWidget#
	 * crateBlinkImageLoadHandler()
	 */
	@Override
	protected LoadHandler crateBlinkImageLoadHandler() {
		return new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.client.view.drawing.TSImageMapCanvasWidget#newAtTool
	 * (java.lang.String)
	 */
	protected TSMouseTool newAtTool(String atToolName) {
		return new ProcessAtTool(this, atToolName);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.client.view.drawing.TSImageMapCanvasWidget#newBetweenTool
	 * (java.lang.String)
	 */
	protected TSMouseTool newBetweenTool(String betweenToolName) {
		return new ProcessBetweenTool(this, betweenToolName);
	}

	/**
	 * This class is used to create between tool and invoke tool command.
	 * 
	 * @author dijadhav
	 * 
	 */
	public class ProcessBetweenTool extends TSImageMapBetweenTool {

		public ProcessBetweenTool(TSCanvasWidget tscanvaswidget,
				String betweenToolName) {
			super(tscanvaswidget, betweenToolName);
		}

		@Override
		protected void createAndInvokeToolCommand() {
			TSDrawingViewWidget tsdrawingviewwidget = TSPerspectivesMouseToolHelper
					.getDrawingViewWidget(getCanvas());
			TSToolCommand tstoolcommand = new TSToolCommand(
					tsdrawingviewwidget, getToolID(), hitObjectID1,
					hitObjectID2, 0, 0, 0, 0);
			invokeCommand(tsdrawingviewwidget, tstoolcommand);
		}

	}

	/**
	 * This class is used to add the active tool and invoke the tool action
	 * 
	 * @author dijadhav
	 * 
	 */
	public class ProcessAtTool extends TSImageMapAtTool {

		public ProcessAtTool(TSCanvasWidget tscanvaswidget, String attoolName) {
			super(tscanvaswidget, attoolName);
		}

		@Override
		protected void createAndInvokeToolCommand() {
			TSDrawingViewWidget tsdrawingviewwidget = TSPerspectivesMouseToolHelper
					.getDrawingViewWidget(getCanvas());
			TSToolCommand tstoolcommand = new TSToolCommand(
					tsdrawingviewwidget, getToolID(), hitObjectID, null,
					(int) getMouseX(), (int) getMouseY(), 0, 0);
			invokeCommand(tsdrawingviewwidget, tstoolcommand);
		}

	}

	/**
	 * This method is sued to invoke the tool command.
	 * 
	 * @param tsdrawingviewwidget
	 * @param tstoolcommand
	 */
	private void invokeCommand(final TSDrawingViewWidget tsdrawingviewwidget,
			TSToolCommand tstoolcommand) {
		
		TSPerspectivesMouseToolHelper.invokeToolCommand(tsdrawingviewwidget,
				tstoolcommand, true, true, new TSCallback<Serializable>() {
					@Override
					public void onSuccess(Serializable serializable) {
						AbstractEditor abstractEditor = (AbstractProcessEditor) WebStudio
								.get().getEditorPanel().getSelectedTab();
						abstractEditor.getPane().setCanFocus(true);
						abstractEditor.getPane().focus();
						if (abstractEditor instanceof AbstractProcessEditor) {
							AbstractProcessEditor processEditor = (AbstractProcessEditor) abstractEditor;
							if (null != processEditor) {
								if (!abstractEditor.isDirty()) {
									processEditor.makeDirty();
								}
								processEditor.fetchProperties(
										ProcessConstants.PROCESS_ID,
										ProcessConstants.GENERAL_PROPERTY);
							}
						}
					}

					@Override
					public void onFailure(Throwable throwable) {
						CustomSC.say("Failed to add the process element.");
					}

				});
	}
}