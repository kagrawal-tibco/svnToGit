package com.tibco.cep.studio.dashboard.ui.chartcomponent.preview;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;

public class ChartPreview extends Composite {

	private static final String PREVIEW_HTML_PATH = "/views/web-root/cp.html";

	private StackLayout stackLayout;

	private Browser browser;
	private boolean browserReady;

	private String queuedConfig;
	private String queuedData;
	private String queuedConfigAndData;

	private boolean disablePermanently;
	private DisabledChartPreview disabledPreview;

	public ChartPreview(Composite parent, int style) {
		super(parent, style);
		//create stack layout
		stackLayout = new StackLayout();
		//set the layout
		setLayout(stackLayout);
		//create browser
		browser = new Browser(this, SWT.NONE);
		browser.addProgressListener(new PreviewLoadingProgressListener());
		// create disabled chart preview
		disabledPreview = new DisabledChartPreview(this, SWT.NONE);
		// set by default the browser as the top control
		stackLayout.topControl = browser;
	}

	public void load() {
		if (isDisposed() == true) {
			return;
		}
		String beHome = System.getProperty("BE_HOME");
		if (beHome == null || beHome.trim().length() == 0) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,DashboardChartPlugin.PLUGIN_ID, "No BE_HOME variable defined"));
			disable(true, "No BE_HOME defined");
		}
		File beHomeDir = new File(beHome);
		if (beHomeDir.exists() == false) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,DashboardChartPlugin.PLUGIN_ID, beHomeDir+" does not exist"));
			disable(true, "Invalid BE_HOME defined");
		}
		if (beHomeDir.isDirectory() == false) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,DashboardChartPlugin.PLUGIN_ID, beHomeDir+" is not a directory"));
			disable(true, "Invalid BE_HOME defined");
		}
		File previewHTMLFile = new File(beHomeDir, PREVIEW_HTML_PATH);
		if (previewHTMLFile.exists() == false) {
			DashboardChartPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,DashboardChartPlugin.PLUGIN_ID, previewHTMLFile.getAbsolutePath()+" does not exist"));
			disable(true, "could not load chart preview widget");
		}
		System.err.println("Chart Preview initialized using "+previewHTMLFile.getAbsolutePath());
		browser.setUrl(previewHTMLFile.toURI().toString());
	}

	public void setConfigXML(String configXML){
		if (isDisposed() == true) {
			return;
		}
		if(browserReady){
			if (disablePermanently == false) {
				bringBrowserToFront();
				browser.execute("setChartConfig('" + configXML + "');");
			}
		}
		else{
			queuedConfig = configXML;
		}
	}

	public void setDataXML(String dataXML) {
		if (isDisposed() == true) {
			return;
		}
		if(browserReady){
			if (disablePermanently == false) {
				bringBrowserToFront();
				browser.execute("updateChartData('" + dataXML + "');");
			}
		}
		else{
			queuedData = dataXML;
		}
	}

	public void setCompleteXML(String completeXML) {
		if (isDisposed() == true) {
			return;
		}
		if(browserReady){
			if (disablePermanently == false) {
				bringBrowserToFront();
				browser.execute("setChartConfigAndData('" + completeXML + "');");
			}
		}
		else{
			queuedConfigAndData = completeXML;
		}
	}

	public void disable(boolean permanent, String message){
		if (isDisposed() == true) {
			return;
		}
		this.disablePermanently = permanent;
		bringDisabledPreviewToFront();
		disabledPreview.setMessage(message);
	}

	private void bringBrowserToFront() {
		if (stackLayout.topControl != browser) {
			stackLayout.topControl = browser;
			layout();
		}
	}

	private void bringDisabledPreviewToFront() {
		if (stackLayout.topControl != disabledPreview) {
			stackLayout.topControl = disabledPreview;
			layout();
		}
	}

	private void syncPreview(){
		bringBrowserToFront();
		if(queuedConfigAndData != null){
			setCompleteXML(queuedConfigAndData);
		}
		else if(queuedConfig != null){
			setConfigXML(queuedConfig);
			if(queuedData != null){
				setDataXML(queuedData);
			}
		}
		else if(queuedData != null){
			setDataXML(queuedData);
		}
		queuedConfigAndData = queuedConfig = queuedData = null;
	}

	class PreviewLoadingProgressListener implements ProgressListener {

		@Override
		public void changed(ProgressEvent event) {
//			System.err.println("changed: " + event.toString());
		}

		@Override
		public void completed(ProgressEvent event) {
			//**This is called twice on completion, thus update charts is done via the progress changed event
//			System.err.println("Completed: " + event.toString());
			browser.removeProgressListener(this);
			browserReady = true;
			syncPreview();
		}

	}

	class DisabledChartPreview extends Composite {

		Label msgLbl;

		public DisabledChartPreview(Composite parent, int style) {
			super(parent, style);
			Color whiteColor = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
			setBackground(whiteColor);
			GridLayout layout = new GridLayout();
			setLayout(layout);
			msgLbl = new Label(this, SWT.WRAP | SWT.CENTER);
			msgLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
			msgLbl.setBackground(whiteColor);
		}

		void setMessage(String message) {
			msgLbl.setText(message);
			layout();
		}
	}

}