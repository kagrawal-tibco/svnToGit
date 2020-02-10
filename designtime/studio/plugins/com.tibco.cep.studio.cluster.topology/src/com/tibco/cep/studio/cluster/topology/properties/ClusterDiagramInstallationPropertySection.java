package com.tibco.cep.studio.cluster.topology.properties;

import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.model.impl.BeImpl;
import com.tibco.cep.studio.cluster.topology.model.impl.SoftwareImpl;
import com.tibco.cep.studio.cluster.topology.utils.Messages;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterDiagramInstallationPropertySection extends AbstractClusterTopologyPropertySection implements ModifyListener {
	
	protected Text beverText;
	protected Text traText;
	protected Text behomeText;
    protected Composite composite;
    protected SoftwareImpl software;
    protected BeImpl be;
    
    private static String validFilePattern = "^[^\\/:\"*?<>|]*$";
    private static String validDirectoryPattern = "^[^\\/\"*?<>|]*$";
    private static String validTraPattern = "^[^\\/:\"*?<>|]+$";
    
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.propertySheetPage = (ClusterDiagramPropertySheetPage) tabbedPropertySheetPage;
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		getWidgetFactory().createLabel(composite, Messages.getString("installation.bever"),  SWT.NONE);
		beverText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		beverText.addModifyListener(this);
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		beverText.setLayoutData(gd);

		getWidgetFactory().createLabel(composite, Messages.getString("installation.behome"),  SWT.NONE);
		behomeText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		behomeText.addModifyListener(this);
		
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		behomeText.setLayoutData(gd);

		getWidgetFactory().createLabel(composite, Messages.getString("installation.trafile") + ":",  SWT.NONE);
		traText = getWidgetFactory().createText(composite, "",  SWT.BORDER);
		traText.addModifyListener(this);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		traText.setLayoutData(gd);

	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseNode != null) {
			if(hostResource != null){
				software = hostResource.getSoftware();
				if(software!=null){
					be = getManager().getFactory().createBeImpl(software.getBe().get(0));
					if(be != null){
						/*traText.removeModifyListener(this);
						behomeText.removeModifyListener(this);*/
						beverText.removeModifyListener(this);
						beverText.setText(be.getVersion());
						checkValidBEVersionField(beverText);
						beverText.addModifyListener(this);
						String beHome = be.getHome();
						String beTra = be.getTra();
						if (beHome != null && beHome.trim().length() > 0 ) {
							if(!behomeText.getText().equalsIgnoreCase(beHome)){
								behomeText.setText(beHome);					
								}
								if (beTra == null) {
								beTra = "";
							}
							if (beHome.contains("\\")) {
								if(!beTra.contentEquals(behomeText.getText())) {
									traText.setText(beHome + "\\bin\\be-engine.tra");
									if(be.getTra()!= null){
										if(!be.getTra().equalsIgnoreCase(traText.getText())){
										be.setTra(traText.getText());
										}
									}
							//		editor.setModified(true);
								
								} else {
									traText.setText(beTra);
									if(!be.getTra().equalsIgnoreCase(beTra)){
									be.setTra(beTra);
									}
							//		editor.setModified(true);
								}
							} else if (beHome.contains("/")) {
								//if(!beTra.contains(behomeText.getText())) {
									traText.setText(beHome + "/bin/be-engine.tra");
									if(!be.getTra().equalsIgnoreCase(traText.getText())){
									be.setTra(traText.getText());
									}
				        	//for BE-16364 Site Topology:Updating host installation properties doesn't reflected properly
								/*} else {
									traText.setText(beTra);
									if(!be.getTra().equalsIgnoreCase(beTra)){
									be.setTra(beTra);
									}
							//		editor.setModified(true);
								}*/
							}
						} else if(beHome != null && beHome.equals("")) {
							behomeText.setText("");
							traText.setText("");
							
						}
								
					} 
				}
			}
			/*behomeText.addModifyListener(this);
			traText.addModifyListener(this);*/
		} 
		if (tseEdge != null) { }
		if (tseGraph != null) { }


	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		if (e.getSource() == beverText) {
			if (!beverText.getText().equalsIgnoreCase(be.getVersion())) {
				be.setVersion(beverText.getText());
			}
			checkValidBEVersionField(beverText);
		}
		if (e.getSource() == behomeText) {
			if(!behomeText.getText().trim().equals("") && !behomeText.getText().equalsIgnoreCase(be.getHome())){
				be.setHome(behomeText.getText());
				refresh();
				editor.modified();
			}
		
		if (e.getSource() == traText) {
			String traFileName = traText.getText();
			if(isValidFile(traFileName) && traFileName.endsWith(".tra")) {
				if(be.getTra() != null) {
					if(!be.getTra().equalsIgnoreCase(traText.getText())){
						be.setTra(traText.getText());
						editor.modified();
					}
					traText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					traText.setToolTipText(traFileName);
				}
			} else if(!traFileName.equals("")){
				if(!traFileName.endsWith(".tra")) {
					traText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("invalid_tra", traFileName);
					traText.setToolTipText(problemMessage);
				} else if(!isValidFile(traFileName)){
					traText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = Messages.getString("tra_doesnot_exist", traFileName);
					traText.setToolTipText(problemMessage);
				}
			}
			
		}
		}
		
	}

	private boolean isValidFile(String traFileName) {
		
		String[] fileNamePath = null;
		if(traFileName.contains("/")) {
			 fileNamePath = traFileName.split("/");
		} else if (traFileName.contains("\\")) {
			fileNamePath = traFileName.split("\\\\");
		}
		if(fileNamePath == null){
			Pattern p = Pattern.compile(validFilePattern);
			if(!p.matcher(traFileName).matches()) {
				return false;
			}
		} else {
			for(int i = 1; i<fileNamePath.length-1; i++){
				String fileName = fileNamePath[i];
				Pattern p = Pattern.compile(validFilePattern);
				if(!p.matcher(fileName).matches()) {
					return false;
				}
			}
			Pattern pDir = Pattern.compile(validDirectoryPattern);
			Pattern pTRAFile = Pattern.compile(validTraPattern);
			if(fileNamePath.length != 1) {
				if(!pDir.matcher(fileNamePath[0]).matches()) {
					return false;
				}
			}
			if(!fileNamePath[fileNamePath.length-1].endsWith(".tra")) {
				return false;
			} else {
				String fileName = fileNamePath[fileNamePath.length-1].substring(0, fileNamePath[fileNamePath.length-1].indexOf(".tra"));
				if(!pTRAFile.matcher(fileName).matches()) {
					return false;
				}
			}
		}
		return true;
	}
}