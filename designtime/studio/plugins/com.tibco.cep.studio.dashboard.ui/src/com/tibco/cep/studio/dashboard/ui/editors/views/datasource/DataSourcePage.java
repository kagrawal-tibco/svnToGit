package com.tibco.cep.studio.dashboard.ui.editors.views.datasource;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryInterpreter;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.queryparam.QueryParamViewer;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 *
 * @author rgupta
 */
public class DataSourcePage extends AbstractEntityEditorPage {

	private AttributeViewer _fViewer;

	private BEViewsQueryInterpreter queryValidator;

	//private Text queryTextArea;

	// ntamhank Oct 20 2010 Fix for BE-7635
	// JFace TextViewer which replaces SWT Text
	private TextViewer queryTextViewer;

	private Label statusLabel;

	private Document queryDocument;

	public DataSourcePage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

//	@Override
//	public void init(IEditorSite site, IEditorInput input) {
//		super.init(site, input);
//		try {
//			LocalElement localDataSource = getLocalElement();
//			loadModel(localDataSource);
//			prepareQueryValidator(localDataSource);
//		} catch (Exception e) {
//			DashboardEditorsPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardEditorsPlugin.PLUGIN_ID, "could not initialize " + input.getName(), e));
//		}
//	}

	@Override
	protected String getElementTypeName() {
		return "Data Source";
	}
	
	@Override
	protected void createProperties(IManagedForm mform, Composite client) throws Exception {
		createDescriptionSection(mform, client);
		createMiddleSection(mform, client);
		createFieldSection(mform, client);
	}
	
	/**
	 * Create description section for data source
	 * 
	 * @param mform
	 * @param client
	 * @throws Exception
	 */
	private void createDescriptionSection(IManagedForm mform, Composite client) throws Exception {

		FormToolkit toolkit = mform.getToolkit();
		
		// create description section
		Section descSection = toolkit.createSection(client, ExpandableComposite.TITLE_BAR);
		descSection.setText(Messages.getString("datasource.property.description"));

		// set layout data for description section
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		descSection.setLayoutData(gd);

		// set the layout on the description section
		descSection.setLayout(new FillLayout());

		// create composite in the section
		Composite composite = toolkit.createComposite(descSection);

		// set layout in the section client
		composite.setLayout(new GridLayout(2, false));

		// create description text
		final Text descriptionText = toolkit.createText(composite, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);

		GridData descTextGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		descTextGridData.heightHint = 50;
		descriptionText.setLayoutData(descTextGridData);

		descriptionText.setText(getLocalElement().getDescription());

		// Add listener for description modification
		descriptionText.addModifyListener(new ModifyListener() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				String description = descriptionText.getText();
				if (description != null && description.equals(getLocalElement().getDescription()) == false) {
					getLocalElement().setDescription(description);
				}
			}
		});

		toolkit.paintBordersFor(composite);
		// set the composite in the description section
		descSection.setClient(composite);
	}

	@Override
	protected void createMiddleSection(IManagedForm mform, Composite client) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		// create query section
		Section middleSection = createSection(mform, client, "Query");
		middleSection.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		// the query with status text composite
		Composite subClient = toolkit.createComposite(middleSection, SWT.NULL);
		GridLayout gLayout = new GridLayout(1, false);
		gLayout.marginHeight = gLayout.marginWidth = 5;
		subClient.setLayout(gLayout);
		subClient.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		statusLabel = toolkit.createLabel(subClient, "");
		statusLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		statusLabel.setLayoutData(gd);

		//queryTextArea = toolkit.createText(subClient, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);

		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		//gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		gd.widthHint = 200;
		gd.heightHint = 100;

		// Jface stuff
		//queryTextViewer = new TextViewer(subClient, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		queryTextViewer = new TextViewer(subClient, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		//queryTextArea.setLayoutData(gd);
		//gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		//gd.widthHint = 600;
		//gd.heightHint = 35;
		queryTextViewer.getTextWidget().setLayoutData(gd);

		// add modify listener
		/*queryTextArea.addModifyListener(new ModifyListener() {


			public void modifyText(ModifyEvent ev) {
				if (processSWTEvents == false){
					return;
				}
				String query = queryTextArea.getText();
				if (StringUtil.isEmpty(query) == false) {
					String validationMsg = validateQuery(query);
					statusLabel.setText(validationMsg);
					try {
						updateDataSource();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});*/

		// ntamhank Oct 20 2010 Fix for BE-7635: UndoManager same as RF Form Viewer
		TextViewerUndoManager queryTextUndoManager =  new TextViewerUndoManager(10);
		//connect the undo manager to the text viewer
		queryTextUndoManager.connect(queryTextViewer);
		//also set the undo manager on the text viewer
		queryTextViewer.setUndoManager(queryTextUndoManager);

		//set up the context menu for the text viewer
		StudioUIUtils.setEditContextMenuSupport(queryTextViewer.getTextWidget(), queryTextUndoManager, false, null);
		//do not use the default key support. It ends up conflicting with the global actions causing paste() called twice (once by global and once by our custom key support)
		//StudioUIUtils.setKeySupport(queryTextViewer.getTextWidget(), queryTextUndoManager);
		toolkit.paintBordersFor(subClient);
		middleSection.setClient(subClient);
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		Section section = createSection(mform, parent, "Query Parameters");

		_fViewer = createFieldViewer(mform, section);
		getSite().setSelectionProvider(_fViewer.getTableViewer());

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 100;
		_fViewer.setLayoutData(gd);

		mform.getToolkit().paintBordersFor(_fViewer.getContent());
		section.setClient(_fViewer.getContent());
	}

	/**
	 * {@inheritDoc}
	 */
	protected AttributeViewer createFieldViewer(IManagedForm mform, Composite client) throws Exception {
		return new QueryParamViewer(mform.getToolkit(), client);
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		// load all source elements
		localElement.getChildren(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
		// load all query parameters
		localElement.getChildren(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
		//call super to let generic properties to get populated
		super.doPopulateControl(localElement);
		//prepare the query validator
		prepareQueryValidator(localElement);
		// populate the query text
		queryDocument = new Document(localElement.getPropertyValue(LocalDataSource.PROP_KEY_QUERY));
		queryTextViewer.setDocument(queryDocument);
		queryDocument.addDocumentListener(new DataSourcePageDocumentListener());

		// update the status label
		//String validationMsg = validateQuery(queryTextArea.getText());
		String validationMsg = validateQuery(queryTextViewer.getTextWidget().getText());
		statusLabel.setText(validationMsg);
		// update the field viewer
		_fViewer.setLocalElement(localElement);
	}

	private void prepareQueryValidator(LocalElement localDataSource) throws Exception {
		List<Metric> allMetrics = new LinkedList<Metric>();
		List<Object> allLocalMetrics = localDataSource.getEnumerations(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
		for (Object localMetric : allLocalMetrics) {
			allMetrics.add((Metric) ((LocalMetric) localMetric).getEObject());
		}
		queryValidator = new BEViewsQueryInterpreter(allMetrics);
	}

	private String validateQuery(String query) {
		queryValidator.interpret(query);
		if (queryValidator.hasError() == true) {
			return queryValidator.getErrorMessage();
		}
		return "";
	}

	private void updateDataSource() throws Exception {
		LocalDataSource localDataSource = (LocalDataSource) getLocalElement();
		Metric queriedMetric = queryValidator.getQueriedMetric();
		// query property
		localDataSource.setPropertyValue(LocalDataSource.PROP_KEY_QUERY, queryValidator.getQuery());
		// query source
		if (queriedMetric != null) {
			localDataSource.setElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT, getLocalElement().getRoot().getElementByID(BEViewsElementNames.METRIC, queriedMetric.getGUID()));
		} else {
			localDataSource.setElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT, null);
		}
		// update the query params if any
		// remove all unwanted query parameters
		LocalParticle queryParamsParticle = localDataSource.getParticle(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
		// if (queryValidator.getVariableNames().isEmpty() == true){
		// queryParamsParticle.removeAll(false);
		// }
		// else {
		List<LocalElement> elements = queryParamsParticle.getElements();
		for (LocalElement element : elements) {
			if (queryValidator.getVariableNames().contains(element.getName()) == false) {
				localDataSource.removeElementByID(LocalDataSource.ELEMENT_KEY_QUERY_PARAM, element.getID(), LocalElement.FOLDER_NOT_APPLICABLE);
			}
		}
		// }
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
		_fViewer.setLocalElement(localDataSource);
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		LocalElement localElement = getLocalElement();
		try {
			if (element instanceof LocalMetric){
				//we have a metric change , prepare the query validator
				prepareQueryValidator(localElement);
				populateControl(getLocalElement());
			}
			else if (change == IResourceDelta.CHANGED && element.getID().equals(localElement.getID()) == true){
				//we have been re-factored
				//refresh and transfer the new persisted eobject
				getLocalElement().refresh(element.getEObject());
				// load all source elements
				getLocalElement().refresh(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
				// load all query parameters
				getLocalElement().refresh(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
				//populate the control
				populateControl(getLocalElement());
			}
		} catch (Exception e) {
			throw new RuntimeException("could not refresh editor. Please reopen the editor...",e);
		}
	}

	TextViewer getQueryTextViewer() {
		return queryTextViewer;
	}

	/**
	 *
	 * @author ntamhank
	 *	added IDocumentListner for listening to changes in Datasource query IDocument
	 */
	private class DataSourcePageDocumentListener implements IDocumentListener{

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {

		}
		@Override
		public void documentChanged(DocumentEvent event) {
			if (processSWTEvents == false){
				return;
			}
			String query = queryTextViewer.getTextWidget().getText();
			if (StringUtil.isEmpty(query) == false) {
				String validationMsg = validateQuery(query);
				statusLabel.setText(validationMsg);
				try {
					updateDataSource();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				statusLabel.setText("");
			}

		}

	}
}