package com.tibco.cep.webstudio.client.process;

import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tomsawyer.util.gwtclient.ui.TSTooltipPanel;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewCanvas;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewCanvasWidget;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewWidget;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewWidgetFactoryImpl;
import com.tomsawyer.web.client.view.drawing.TSNavigationControlPanel;
import com.tomsawyer.web.client.view.drawing.TSOverviewCanvasWidget;
import com.tomsawyer.web.client.view.drawing.TSOverviewDialogBox;
import com.tomsawyer.web.client.view.print.TSPrintSetupDialogBox;

/**
 * This class is used for custom widgets for TSP.
 *
 * @author sasahoo,dijadhav
 *
 */
public class ProcessDrawingViewWidgetFactoryImpl extends TSDrawingViewWidgetFactoryImpl {

	private  ProcessMessages processMessages = (ProcessMessages) I18nRegistry.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);

	@Override
	public TSDrawingViewCanvas newHTMLCanvas(TSDrawingViewWidget arg0) {
		return new ProcessHTMLCanvasWidget(arg0);
	}

	@Override
	public TSDrawingViewCanvas newImageMapCanvas(TSDrawingViewWidget arg0) {	
		return new ProcessImageMapCanvasWidget(arg0);
	}

	@Override
	public TSNavigationControlPanel newNavigationPanel(
			TSDrawingViewCanvasWidget arg0) {
		return  new ProcessViewNavigationControlPanel(arg0);
	}

	@Override
	public TSOverviewCanvasWidget newOverviewCanvas(
			TSDrawingViewCanvasWidget arg0, boolean arg1) {
		return super.newOverviewCanvas(arg0, arg1);
	}

	@Override
	public TSOverviewDialogBox newOverviewDialog(TSDrawingViewCanvasWidget arg0) {
		return super.newOverviewDialog(arg0);
	}

	@Override
	public TSPrintSetupDialogBox newPrintSetupDialogBox(TSDrawingViewWidget arg0) {
		return super.newPrintSetupDialogBox(arg0);
	}

	@Override
	public TSTooltipPanel newTooltipPanel(TSDrawingViewCanvasWidget arg0) {
		return super.newTooltipPanel(arg0);
	}

	/**
	 * This class is used to create navigation panel.
	 * 
	 * @author dijadhav
	 *
	 */
	public class ProcessViewNavigationControlPanel extends TSNavigationControlPanel {

		public ProcessViewNavigationControlPanel(TSDrawingViewCanvasWidget paramTSDrawingViewCanvasWidget) {
			super(paramTSDrawingViewCanvasWidget);
		}

		@Override
		protected void configureButtons() {
			super.configureButtons();
			panCenterButton.setTitle(processMessages.processNavigationPanelCenterBtn());
			panEastButton.setTitle(processMessages.processNavigationPanelEastBtn());
			panNorthButton.setTitle(processMessages.processNavigationPanelNorthBtn());
			panSouthButton.setTitle(processMessages.processNavigationPanelSouthBtn());
			panWestButton.setTitle(processMessages.processNavigationPanelWestBtn());
			zoomInButton.setTitle(processMessages.processNavigationPanelZoomInBtn());
			zoomOutButton.setTitle(processMessages.processNavigationPanelZoomOutBtn());
			zoomLevel200Button.setTitle(processMessages.processNavigationPanelZoomLevel200());
			zoomLevel150Button.setTitle(processMessages.processNavigationPanelZoomLevel150());
			zoomLevel100Button.setTitle(processMessages.processNavigationPanelZoomLevel100());
			zoomLevel75Button.setTitle(processMessages.processNavigationPanelZoomLevel75());
			zoomLevel50Button.setTitle(processMessages.processNavigationPanelZoomLevel50());
			zoomLevel25Button.setTitle(processMessages.processNavigationPanelZoomLevel25());
			zoomLevel10Button.setTitle(processMessages.processNavigationPanelZoomLevel10());
		}
		
		@Override
		public void setPlacementStrategyFromString(String paramString) {
			super.setPlacementStrategyFromString("Top Right");
		}
		
	}	
}