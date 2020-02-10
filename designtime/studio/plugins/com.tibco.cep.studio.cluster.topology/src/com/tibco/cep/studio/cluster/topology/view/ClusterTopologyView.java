package com.tibco.cep.studio.cluster.topology.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;
import com.tibco.cep.studio.cluster.topology.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ClusterTopologyView extends AbstractClusterTopologyView implements IClusterTopologyViewConstants{

	@Override
	protected void createFormParts(final ScrolledForm form, final FormToolkit toolkit) {
		Composite sectionClient = toolkit.createComposite(form.getBody(),SWT.EMBEDDED);
		sectionClient.setLayout(new FillLayout());
		processViewPage = toolkit.createPageBook(sectionClient, SWT.NONE);
		
		//create process page for Process view.
		createDeploymentPage(toolkit, processViewPage.createPage(DEPLOYMENT_PAGE));
		processViewPage.showPage(DEPLOYMENT_PAGE);
		
		toolkit.paintBordersFor(sectionClient);
	}

	/**
	 * 
	 * @param toolkit
	 * @param sectionClient
	 */
	private void createDeploymentPage(FormToolkit toolkit, Composite sectionClient) {
		sectionClient.setLayout(new GridLayout(2, false));
		Composite buttonSection = toolkit.createComposite(sectionClient,SWT.NONE);

		//Adding Tabs
		ClusterTopologyPageTabComponent pageTab = new ClusterTopologyPageTabComponent(buttonSection);
		pageTab.createDeploymentPageButtons();

		Composite detailsComposite = toolkit.createComposite(sectionClient);
		detailsComposite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 450;
		data.heightHint = 50;
		detailsComposite.setLayoutData(data);

		Composite composite = new Composite(detailsComposite, SWT.BORDER);
		composite.setLayout(new FillLayout());
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 20;
		composite.setLayoutData(data);

		CLabel activityClabel = new CLabel(composite, SWT.NONE);
		activityClabel.setText(Messages.getString("cluster.view.page.activity.title"));
		activityClabel.setBackground(titleHeaderColor, gradPercents);
		activityClabel.setImage(ClusterTopologyPlugin.getDefault().getImage("icons/iconProcess16x16.gif"));

		Composite detailsSubcomposite = toolkit.createComposite(detailsComposite);
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 350;
		data.heightHint = 50;
		detailsSubcomposite.setLayoutData(data);
		detailsSubcomposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		detailsSubcomposite.setLayout(new FillLayout());

		ScrolledPageBook activityPage = toolkit.createPageBook(detailsSubcomposite, SWT.NONE);
        
		//create activity pages here.
		createDeploymentConfigurationPage(toolkit, activityPage.createPage(DEPLOYMENT_CONFIGURATION_PAGE));
		createActivityInputPage(toolkit, activityPage.createPage(DEPLOYMENT_INPUT_PAGE));
		createActivityOuputPage(toolkit,  activityPage.createPage(DEPLOYMENT_OUTPUT_PAGE));

		//add tab listeners and associate pages with tab 
		new ClusterTopologyPageTabListener(pageTab,activityPage);  

		//default configuration page.
		activityPage.showPage(DEPLOYMENT_CONFIGURATION_PAGE);
	}

	/**
	 * 
	 * @param toolkit
	 * @param configPage
	 */
	private void createDeploymentConfigurationPage(FormToolkit toolkit, Composite configPage) {
		configPage.setLayout(new GridLayout(2, false));
		toolkit.createLabel(configPage, Messages.getString("Name"));
		Text activityName = toolkit.createText(configPage, ""); 
		activityName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		activityName.setEnabled(false);
		toolkit.createLabel(configPage,Messages.getString("Description"));
		Text activityDesc = toolkit.createText(configPage, "");
		activityDesc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		activityDesc.setEnabled(false);
	}

	/**
	 * 
	 * @param toolkit
	 * @param inputPage
	 */
	private void createActivityInputPage(FormToolkit toolkit,Composite inputPage) {
		inputPage.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 350;
		data.heightHint = 60;
		Composite propertyPage2 = toolkit.createComposite(inputPage,SWT.EMBEDDED);
		propertyPage2.setLayoutData(data);
		getSwingContainer(propertyPage2);
//		XMLMapperForm inputMapper = new XMLMapperForm();
//		panel.add(inputMapper,BorderLayout.CENTER);
//		try {
//			XMLMapperListeners.updateMapperPanel(null, inputMapper.getCache(), inputMapper.getBePanel(), inputMapper.getEditor());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 
	 * @param toolkit
	 * @param outputPage
	 */
	private void createActivityOuputPage(FormToolkit toolkit,Composite outputPage) {
		outputPage.setLayout(new GridLayout(1, false));
		outputPage.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 350;
		data.heightHint = 60;
		Composite propertyPage2 = toolkit.createComposite(outputPage,SWT.NONE);
		propertyPage2.setLayoutData(data);
	}
}