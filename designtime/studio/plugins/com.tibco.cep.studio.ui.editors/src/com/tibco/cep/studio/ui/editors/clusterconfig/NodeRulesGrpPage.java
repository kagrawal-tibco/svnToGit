package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 16, 2009 2:44:10 PM
 */

public class NodeRulesGrpPage extends NodeGroupsListPage {

	private RulesGrp rulesGrp;
	private ListProviderUi rulesGrpListUi;
	private GroupsListModel listmodel;
	private Text tRuleGrp;
	
	public NodeRulesGrpPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		Composite nameComp = new Composite(comp, SWT.NONE);
		nameComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		nameComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		PanelUiUtil.createLabel(nameComp, "Rules Collection: ");
		tRuleGrp = PanelUiUtil.createText(nameComp);
		tRuleGrp.addListener(SWT.Modify, getNameModifyListener());
		
		listmodel = new GroupsListModel(modelmgr, viewer);
		rulesGrpListUi = new ListProviderUi(comp, null, listmodel); 
		rulesGrpListUi.createListPanel(true, false);
		
		comp.pack();
		parent.pack();
	}
	
	private Listener getNameModifyListener() {
		
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String newName = tRuleGrp.getText();
				String updated = modelmgr.updateRulesGroupName(rulesGrp, newName);
				if (updated.equals("true"))
					BlockUtil.refreshViewerForError(viewer,0,tRuleGrp);
				if (updated.equals("trueError")){
					BlockUtil.refreshViewerForError(viewer,1,tRuleGrp);
				}
			}
		};
		return listener;
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		tRuleGrp.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		if (ssel.size() == 1) { 
			Object selObj = ssel.getFirstElement();
			if (selObj instanceof RulesGrp) {
				rulesGrp = (RulesGrp) selObj;
			} else if (selObj instanceof AgentRulesGrpElement) {
				rulesGrp = ((AgentRulesGrpElement)selObj).rulesGrp;
				tRuleGrp.setEnabled(false);
			}
		} else {
			rulesGrp = null;
		}
		update();
	}
	
	


	protected void update() {
		if (rulesGrp == null)
			return;
		String rules[] = modelmgr.getRuleNames(rulesGrp, false).toArray(new String[0]);
		rulesGrpListUi.setItems(rules);
		tRuleGrp.setText(rulesGrp.id);
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
