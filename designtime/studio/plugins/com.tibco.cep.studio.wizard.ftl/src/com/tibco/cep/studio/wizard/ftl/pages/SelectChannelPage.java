package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.tibco.cep.studio.wizard.ftl.FTLNewWizard;
import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;


public class SelectChannelPage extends FTLWizardPage {

	private String[] channelItems;
	private String selectChannelName;

	public String getSelectChannelName() {
		return selectChannelName;
	}

	public void setSelectChannelName(String selectChannelName) {
		this.selectChannelName = selectChannelName;
	}

	public SelectChannelPage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("ftl.wizard.page.selectChannel.title"));
		this.setDescription(Messages.getString("ftl.wizard.page.selectChannel.desc"));
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		prepareData();

		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);
		Label label = new Label(container, SWT.LEFT);
		label.setText(FTLWizardConstants.PAGE_NAME_CHANNEL_LABEL);
		final List list = new List(container, SWT.BORDER | SWT.SINGLE
				| SWT.V_SCROLL);
		list.setItems(channelItems);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		list.setLayoutData(gd);
		list.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(list.getSelectionIndex() > -1) {
					 selectChannelName = channelItems[list.getSelectionIndex()];
					 updateStatus(null);
				} else {
					 updateStatus(Messages.getString("ftl.wizard.page.selectChannel.tip"));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(list.getSelectionIndex() > -1) {
					 selectChannelName = channelItems[list.getSelectionIndex()];
					 getContainer().showPage(getNextPage());
				}
			}
		});

		setControl(container);

	}

	@Override
	protected void prepareData() {
		Set<String> channelStr = ((FTLNewWizard)getWizard()).getChannelMap().keySet();
		channelItems = new String[channelStr.size()];
		channelItems = channelStr.toArray(channelItems);		
		setPrepared(true);
	}
	
	

}
