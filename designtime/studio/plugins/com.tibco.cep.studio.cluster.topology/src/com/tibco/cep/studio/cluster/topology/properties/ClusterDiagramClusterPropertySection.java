package com.tibco.cep.studio.cluster.topology.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.cluster.topology.wizards.ObjectCacheCDDResourceSelector;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;

/**
 * 
 * @author ggrigore
 * 
 */
public class ClusterDiagramClusterPropertySection extends
		AbstractClusterTopologyPropertySection implements ModifyListener {

	protected Text nameText;
	protected Text beverText;
	protected Text cddProjText;
	protected Text cddMasterText;
	protected Text earMasterText;
	protected Composite composite;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));

		getWidgetFactory().createLabel(composite,
				Messages.getString("cluster.name"), SWT.NONE);
		nameText = getWidgetFactory().createText(composite, "",
				SWT.BORDER | SWT.READ_ONLY);
		nameText.addModifyListener(this);
		GridData gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		nameText.setEnabled(false);

		getWidgetFactory().createLabel(composite,
				Messages.getString("cluster.project.cdd"), SWT.NONE);
		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		cddProjText = getWidgetFactory()
				.createText(browseComposite, "", SWT.BORDER);
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 617;
		cddProjText.setLayoutData(gd);
		cddProjText.addModifyListener(this);

		Button cddBrowseButton = new Button(browseComposite, SWT.NONE);
		cddBrowseButton.setText(Messages.getString("Browse"));
		cddBrowseButton.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				Set<String> extn = new HashSet<String>();
				extn.add("cdd");
				ObjectCacheCDDResourceSelector picker = new ObjectCacheCDDResourceSelector(Display
						.getDefault().getActiveShell(), "Select CDD",
						"Select CDD to be associated with",
						editor.getProject(), cddProjText.getText(), extn);
				if (picker.open() == Dialog.OK) {
					if (picker.getFirstResult() != null) {
						String cdd = picker.getFirstResult().toString();
						cdd = cdd.replace("\\", File.separator);
						cddProjText.setText(cdd);
					}
				}
			}
		});

		getWidgetFactory().createLabel(composite,
				Messages.getString("cluster.bever"), SWT.NONE);
		beverText = getWidgetFactory().createText(composite, "",
				SWT.BORDER /*| SWT.READ_ONLY*/);
		beverText.addModifyListener(this);

		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 615;
		beverText.setLayoutData(gd);
		beverText.setEnabled(false);

		getWidgetFactory().createLabel(composite,
				Messages.getString("cluster.cdd"), SWT.NONE);
		cddMasterText = getWidgetFactory()
				.createText(composite, "", SWT.BORDER);
		cddMasterText.addModifyListener(this);
		
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 617;
		cddMasterText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite,
				Messages.getString("cluster.ear"), SWT.NONE);
		browseComposite = getWidgetFactory().createComposite(composite);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);

		earMasterText = getWidgetFactory()
				.createText(browseComposite, "", SWT.BORDER);
		earMasterText.addModifyListener(this);
		gd = new GridData(/* GridData.FILL_HORIZONTAL */);
		gd.widthHint = 617;
		earMasterText.setLayoutData(gd);
	}

	/*
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh
	 * ()
	 */
	public void refresh() {
		if (tseNode != null) {
			if (cluster != null) {
				String projectCdd = cluster.getProjectCdd();
				if (projectCdd != null && projectCdd.trim().length() > 0) {
					projectCdd = projectCdd.replace("\\", File.separator);
					cddProjText.setText(projectCdd);
					try {
						if (new File(projectCdd).exists()) {
							ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(getEditor().getProject(), projectCdd);
							cddModelMgr.parseModel();
							String clusterName = cddModelMgr.getClusterName();
							if (!cluster.getName().equalsIgnoreCase(clusterName)) {
								cluster.setName(clusterName);
							}
							nameText.setText(clusterName);
							TSENodeLabel clusterNameLabel = getSelectedObjectLabel();
							if (clusterNameLabel != null) {
								clusterNameLabel.setName(clusterName);
								getManager().refreshLabel(clusterNameLabel);
							}
						} else {
							cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = Messages.getString("file_not_exist", projectCdd);
							cddProjText.setToolTipText(problemMessage);
						}
					} catch (Exception ex) {
						cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = Messages.getString("invalid_cdd", projectCdd);
						cddProjText.setToolTipText(problemMessage);
					}
				} else {
					nameText.setText(cluster.getName());
				}
				beverText.removeModifyListener(this);
				
				beverText.setText(cluster.getRunVersion().getBeRuntime().getVersion());
				
				checkValidBEVersionField(beverText);
				
				beverText.addModifyListener(this);
				
				String cddMaster = cluster.getMasterFiles().getCddMaster();
				cddMaster = cddMaster.replace("\\",File.separator);
				cddMasterText.setText(cddMaster);
				if (!cddMaster.endsWith(".cdd")) {
					cddMasterText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("invalid_cdd", cddMaster);
					cddMasterText.setToolTipText(problemMessage);
				}
				
				String earMaster = cluster.getMasterFiles().getEarMaster();
				earMaster = earMaster.replace("\\", File.separator);
				earMasterText.setText(earMaster);
				if (!earMaster.endsWith(".ear")) {
					earMasterText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("invalid_ear", earMaster);
					earMasterText.setToolTipText(problemMessage);
				}
				
			}
			
			
		}

		if (tseEdge != null) {
		}
		if (tseGraph != null) {
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}

	@Override
	public void modifyText(ModifyEvent e) {
		if (e.getSource() == nameText) {
			if (!nameText.getText().equalsIgnoreCase(cluster.getName())) {
				cluster.setName(nameText.getText());
				TSENodeLabel clusterNameLabel = getSelectedObjectLabel();
				if (clusterNameLabel != null) {
					clusterNameLabel.setName(nameText.getText());
					getManager().refreshLabel(clusterNameLabel);
				}
			}
		}
		if (e.getSource() == cddProjText) {
			cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			cddProjText.setToolTipText("");
			String cddProject = cddProjText.getText();
			if (cddProject != null && !cddProject.equalsIgnoreCase(cluster.getProjectCdd())) {
				if (cddProject != null && cddProject.trim().length() > 0) {
					try {
						if (new File(cddProject).exists()) {
							cluster.setProjectCdd(cddProject);
							ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(getEditor().getProject(), cddProject);
							cddModelMgr.parseModel();
							String clusterName = cddModelMgr.getClusterName();
							cluster.setName(clusterName);
							nameText.setText(clusterName);
							TSENodeLabel clusterNameLabel = getSelectedObjectLabel();
							if (clusterNameLabel != null) {
								clusterNameLabel.setName(clusterName);
								getManager().refreshLabel(clusterNameLabel);
							}
						} else {
							cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = Messages.getString("file_not_exist", cddProject);
							cddProjText.setToolTipText(problemMessage);
						}
						
					} catch (Exception ex) {
						cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = Messages.getString("invalid_cdd", cddProject);
						cddProjText.setToolTipText(problemMessage);
					}
				} else {
					cluster.setProjectCdd("");
				} 
				if(!this.isObjectCacheCDDFile(ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(cluster.getProjectCdd())))) {
					cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("cluster.project.cdd.invalid", cddProject);
					cddProjText.setToolTipText(problemMessage);
				}
			}
			/*if(cddProject != null && !cddProject.isEmpty() && new File(cddProject).exists()) {
				if(!this.isObjectCacheCDDFile(ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(cluster.getProjectCdd())))) {
					cddProjText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("cluster.project.cdd.invalid.object.management", cddProject);
					cddProjText.setToolTipText(problemMessage);
				}
			}*/
		}
		if (e.getSource() == earMasterText) {
			earMasterText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			earMasterText.setToolTipText("");
			String earMaster = earMasterText.getText();
			if (!earMaster.isEmpty()) {
				if (earMaster.endsWith(".ear") /*&& new File (earMaster).exists()*/) {
					//TODO
				} else {
					earMasterText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("empty_ear", earMaster);
					earMasterText.setToolTipText(problemMessage);
					return;
				}
			}
			if (!earMaster.equalsIgnoreCase(cluster.getMasterFiles().getEarMaster())) {
				cluster.getMasterFiles().setEarMaster(earMasterText.getText());
			}
		}
		if (e.getSource() == cddMasterText) {
			cddMasterText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			cddMasterText.setToolTipText("");
			String cddMaster = cddMasterText.getText();
			if (!cddMaster.isEmpty()) {
				if (cddMaster.endsWith(".cdd")/* && new File (cddMaster).exists()*/) {
					//TODO
				} else {
					cddMasterText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("empty_cdd", cddMaster);
					cddMasterText.setToolTipText(problemMessage);
					return;
				}
			}
			if (!cddMaster.equalsIgnoreCase(cluster.getMasterFiles().getCddMaster())) {
				cluster.getMasterFiles().setCddMaster(cddMasterText.getText());
			}
		}
		if (e.getSource() == beverText) {
			if (!beverText.getText().equalsIgnoreCase(
					cluster.getRunVersion().getBeRuntime().getVersion())) {
				cluster.getRunVersion().getBeRuntime().setVersion(
						beverText.getText());
			}
			checkValidBEVersionField(beverText);
		}
	}
	
	private boolean isObjectCacheCDDFile(IFile file) {
		if(file != null) {
			String filePath = file.getLocation().toOSString();
			if (filePath == null || new File(filePath) == null || new File(filePath).length() == 0)
				return false;
			try {
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				fact.setNamespaceAware(true);
				DocumentBuilder builder = fact.newDocumentBuilder();
				FileInputStream fis = new FileInputStream(filePath);
				Document doc = builder.parse(fis);
				Element root = doc.getDocumentElement();
				NodeList fileNodeList = root.getChildNodes();
				for (int n=0; n<fileNodeList.getLength(); n++) {
					Node fileNode = fileNodeList.item(n);
					if (fileNode == null || !isValidFileNode(fileNode)) {
						continue;
					}
					String fileNodeName = fileNode.getLocalName();
					if(fileNodeName.equals("object-management")) {
						Node objectManagementTypeNode = fileNode.getFirstChild();
						while(!isValidFileNode(objectManagementTypeNode)) {
							objectManagementTypeNode = objectManagementTypeNode.getNextSibling();
						}
						String objectManagementType = objectManagementTypeNode.getLocalName();
						if(objectManagementType.equals("cache-manager") || objectManagementType.equals("memory-manager")) {
							return true;
						} else {
							return false;
						}
					}
				}
				return false;
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				return false;
			} catch (SAXException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}
 
 private static boolean isValidFileNode(Node node) {
		if (node != null)
			return (isValidFileNode(node.getLocalName()));
		return false;
	}
	
	private static boolean isValidFileNode(String name) {
		if (name==null)
			return false;
		String ignList[] = { "#text", "#comment" };
		for (String ign: ignList) {
			if (ign.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}
}