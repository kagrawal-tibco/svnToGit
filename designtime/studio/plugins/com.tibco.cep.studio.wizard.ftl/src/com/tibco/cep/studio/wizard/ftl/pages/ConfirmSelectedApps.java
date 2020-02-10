package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.model.FTLDataModel;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;


public class ConfirmSelectedApps extends FTLWizardPage {

	private List<FTLDataModel> dataModels;
	

	public ConfirmSelectedApps(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.confirmSelectedApps.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.confirmSelectedApps.desc"));
		setPageComplete(false);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		container.setLayout(layout);

		if (dataModels.size() > 0) {
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 2;
			Label selectedLabel = new Label(container, SWT.LEFT);
			selectedLabel.setText(FTLWizardConstants.PAGE_NAME_SELECT_SELECTED_APP_LABEL);
			selectedLabel.setLayoutData(gd);

			final org.eclipse.swt.widgets.List selectedList = new org.eclipse.swt.widgets.List(container, SWT.BORDER
					| SWT.SINGLE | SWT.V_SCROLL);
			String[] appNames = new String[dataModels.size()];
			for (int i = 0; i < dataModels.size(); i++) {
				FTLDataModel ftlModel = dataModels.get(i);
//				appNames[i] = FTLStringUtil.formatString((ftlModel.getAppName() + UNDERSCORE
//						+ ftlModel.getEndpointName() + UNDERSCORE + ftlModel.getInstanceName() + UNDERSCORE
//						+ ftlModel.getTransport() + UNDERSCORE + ftlModel.getFormatName()));
				appNames[i] = ftlModel.getDestName();
			}
			selectedList.setItems(appNames);
			GridData listGd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
			listGd.horizontalSpan = 2;
			selectedList.setLayoutData(listGd);

			GridData removeGD = new GridData();
			Button removeButton = new Button(container, SWT.NULL);
			removeButton.setLayoutData(removeGD);
			removeButton.setText("Remove");
			removeButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					int selectionIndex = selectedList.getSelectionIndex();
					if(selectionIndex != -1) {
					     selectedList.remove(selectionIndex);
					     dataModels.remove(selectionIndex);
					     selectedList.update();
					}
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
		}

		GridData continueGD = new GridData();
		Button continueButton = new Button(container, SWT.NULL);
		continueButton.setLayoutData(continueGD);
		continueButton.setText("Add more");
		continueButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				SelectAppPage selectAppPage = (SelectAppPage) getWizard().getPage(FTLWizardConstants.PAGE_NAME_APP);
				selectAppPage.prepareData();
				getContainer().showPage(selectAppPage);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		setControl(container);
		setPageComplete(true);
	}

	protected void prepareData() {
		SelectAppPage appPage = (SelectAppPage) getWizard().getPage(FTLWizardConstants.PAGE_NAME_APP);
		FTLDataModel addedDataModel = appPage.getNewModel();
		 dataModels = appPage.getDataModels();

		if(!dataModels.contains(addedDataModel)) {
		      dataModels.add(addedDataModel);
//		      setPrepared(true);
		} else {
			MessageBox mBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK);
			mBox.setMessage(Messages.getString("ftl.wizard.configuration.exist"));
			mBox.open();
//			int choice = mBox.open();
//			if(choice == 64) {
//				setPrepared(true);
//			} else {
//				setPrepared(false);
//			}
		}
		setPrepared(true);
	}
}
