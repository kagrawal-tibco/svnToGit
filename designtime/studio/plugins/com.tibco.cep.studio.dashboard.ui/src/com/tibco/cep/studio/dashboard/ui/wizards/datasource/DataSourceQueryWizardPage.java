package com.tibco.cep.studio.dashboard.ui.wizards.datasource;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryInterpreter;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.queryparam.QueryParamViewer;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class DataSourceQueryWizardPage extends WizardPage {

	private LocalDataSource localDataSource;

	// query controls
	private Document queryDocument;
	private TextViewer queryTextViewer;
	private BEViewsQueryInterpreter queryValidator;

	// query params controls
	private AttributeViewer queryParamsViewer;

	//toolkit to use with attribute viewer
	private FormToolkit toolKit;

	protected DataSourceQueryWizardPage() {
		super(DataSourceQueryWizardPage.class.getName());
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new FillLayout(SWT.VERTICAL));
		pageComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		Group grpQuery = new Group(pageComposite, SWT.NONE);
		grpQuery.setText("Query");
		FillLayout grpQueryLayout = new FillLayout();
		grpQueryLayout.marginHeight = grpQueryLayout.marginWidth = 5;
		grpQuery.setLayout(grpQueryLayout);
		queryTextViewer = new TextViewer(grpQuery, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		TextViewerUndoManager queryTextUndoManager = new TextViewerUndoManager(10);
		// connect the undo manager to the text viewer
		queryTextUndoManager.connect(queryTextViewer);
		// also set the undo manager on the text viewer
		queryTextViewer.setUndoManager(queryTextUndoManager);
		// set up the context menu for the text viewer
		StudioUIUtils.setEditContextMenuSupport(queryTextViewer.getTextWidget(), queryTextUndoManager, false, null);

		queryDocument = new Document();
		queryTextViewer.setDocument(queryDocument);
		// add document listener to validate the document
		queryDocument.addDocumentListener(new IDocumentListener() {

			@Override
			public void documentChanged(DocumentEvent event) {
				validate();
				if (isPageComplete() == true) {
					try {
						updateDataSource();
					} catch (Exception e) {
						DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not update data source", e));
						setErrorMessage("could not update data source");
						setPageComplete(false);
					}
				}
			}

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
				// do nothing

			}
		});

		Group grpParams = new Group(pageComposite, SWT.NONE);
		grpParams.setText("Params");
		FillLayout grpParamsLayout = new FillLayout();
		grpParamsLayout.marginHeight = grpParamsLayout.marginWidth = 5;
		grpParams.setLayout(grpParamsLayout);
		toolKit = new FormToolkit(Display.getCurrent());
		queryParamsViewer = new QueryParamViewer(toolKit, grpParams);

		setControl(pageComposite);

	}

	public void setLocalDataSource(LocalDataSource localDataSource) {
		this.localDataSource = localDataSource;
		// populate the query text
		queryDocument.set(localDataSource.getPropertyValue(LocalDataSource.PROP_KEY_QUERY));
	}

	private String validateQuery(String query) {
		queryValidator.interpret(query);
		if (queryValidator.hasError() == true) {
			return queryValidator.getErrorMessage();
		}
		return "";
	}

	private void validate() {
		if (queryValidator == null) {
			try {
				List<Metric> allMetrics = new LinkedList<Metric>();
				List<Object> allLocalMetrics = localDataSource.getEnumerations(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
				for (Object localMetric : allLocalMetrics) {
					allMetrics.add((Metric) ((LocalMetric) localMetric).getEObject());
				}
				queryValidator = new BEViewsQueryInterpreter(allMetrics);
			} catch (Exception e) {
				DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not create query interpreter", e));
				setErrorMessage("could not create query interpreter");
				setPageComplete(false);
			}
		} else {
			String query = queryTextViewer.getTextWidget().getText();
			if (StringUtil.isEmpty(query) == false) {
				String validationMsg = validateQuery(query);
				if (StringUtil.isEmpty(validationMsg) == false) {
					setErrorMessage(validationMsg);
					setPageComplete(false);
				}
				else {
					setErrorMessage(null);
					setPageComplete(true);
				}
			}
		}
	}

	private void updateDataSource() throws Exception {
		// LocalDataSource localDataSource = (LocalDataSource) localDataSource;
		Metric queriedMetric = queryValidator.getQueriedMetric();
		// query property
		localDataSource.setPropertyValue(LocalDataSource.PROP_KEY_QUERY, queryValidator.getQuery());
		// query source
		if (queriedMetric != null) {
			localDataSource.setElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT, localDataSource.getRoot().getElementByID(BEViewsElementNames.METRIC, queriedMetric.getGUID()));
		} else {
			localDataSource.setElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT, null);
		}
		// update the query params if any
		// remove all unwanted query parameters
		LocalParticle queryParamsParticle = localDataSource.getParticle(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
		if (queryValidator.getVariableNames().isEmpty() == true) {
			queryParamsParticle.removeAll(false);
		} else {
			List<LocalElement> elements = queryParamsParticle.getElements();
			for (LocalElement element : elements) {
				if (queryValidator.getVariableNames().contains(element.getName()) == false) {
					localDataSource.removeElementByID(LocalDataSource.ELEMENT_KEY_QUERY_PARAM, element.getID(), LocalElement.FOLDER_NOT_APPLICABLE);
				}
			}
		}
		// update or add query parameters based on the query
		int i = 0;
		for (String variableName : queryValidator.getVariableNames()) {
			LocalQueryParam queryParameter = (LocalQueryParam) queryParamsParticle.getActiveElement(variableName, LocalElement.FOLDER_NOT_APPLICABLE);
			if (queryParameter == null) {
				queryParameter = (LocalQueryParam) localDataSource.createLocalElement(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
			}
			queryParameter.setName(variableName);
			queryParameter.setDataType(queryValidator.getDataType(variableName).getLiteral());
			queryParameter.setSortingOrder(i);
			i++;
		}
		queryParamsViewer.setLocalElement(localDataSource);
	}

	@Override
	public void dispose() {
		if (toolKit != null) {
			toolKit.dispose();
		}
		super.dispose();
	}

}
