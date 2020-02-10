package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.ListProviderUi;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/**
 * @author vpatil
 *
 */
public class NodeEntitySetConfigPage extends NodeGroupsListPage {

	private EntitySetConfig entitySetConfig;
	private ListProviderUi entitySetConfigUi;
	private GroupsListModel listmodel;
	private Text tOutputDir;
	private Button bGenerateLVConfigFiles;
	
	public NodeEntitySetConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	protected void createSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setLayout(new GridLayout(1, false));
		
		PanelUiUtil.createLabel(comp, "Entity Filter Configurations: ");
		listmodel = new GroupsListModel(modelmgr, viewer);
		entitySetConfigUi = new ListProviderUi(comp, null, listmodel); 
		Composite listComp = entitySetConfigUi.createListPanel(true, false);
		
		PanelUiUtil.createLabel(client, "Generate Liveview Files: ");
		bGenerateLVConfigFiles = PanelUiUtil.createCheckBox(client, "");
		bGenerateLVConfigFiles.setLayoutData(new GridData());
		bGenerateLVConfigFiles.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelmgr.updateEntitySetConfigProperty(entitySetConfig, Elements.GENERATE_LV_FILES.localName, String.valueOf(bGenerateLVConfigFiles.getSelection()));
			}
		});
		
		tOutputDir = createPropertyTextField("Output Directory: ", Elements.OUTPUT_PATH.localName);
		
		comp.pack();
		parent.pack();
	}
	
	private Text createPropertyTextField(String label, String modelId) {
		PanelUiUtil.createLabel(client, label);
		Text tField = PanelUiUtil.createText(client);
		tField.addListener(SWT.Modify, getTextModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getTextModifyListener(final Text tField, final String modelId) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateEntitySetConfigProperty(entitySetConfig, modelId, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1) { 
			Object selObj = ssel.getFirstElement();
			if (selObj instanceof EntitySetConfig) {
				entitySetConfig = (EntitySetConfig) selObj;
			} else {
				entitySetConfig = null;
			}
		}
		update();
	}
	
	@Override
	protected void update() {
		if (entitySetConfig != null) {
			String entityFilters[] = modelmgr.getEntityFilterSet(entitySetConfig).toArray(new String[0]);
			entitySetConfigUi.setItems(entityFilters);
			
			tOutputDir.setText(entitySetConfig.outputPath);
			bGenerateLVConfigFiles.setSelection(Boolean.valueOf(entitySetConfig.generateLVFiles));
		}
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
