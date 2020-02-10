/**
 *(c) Copyright 2011, TIBCO Software Inc.  All rights reserved.
 *
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software
 * Inc.  Except as expressly set forth in such license agreement, this
 * source code, or any portion thereof, may not be used, modified,
 * reproduced, transmitted, or distributed in any form or by any means,
 * electronic or mechanical, without written permission from
 * TIBCO Software Inc.
 */
package com.tibco.cep.studio.ui.wizards;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author abhijit
 *
 */
public class ImportExistingProjectDetailsPage extends WizardPage  {

	private Combo processingUnitCombo;
	private Combo cddCombo;
	private Text httpProperties;
	Map<String, File> allCddFiles = null;

	final static String HTTP_DRIVER_TYPE_NAME = "driverTypeName=\"HTTP\"";
	final static String HTTP_DRIVER_TYPE = "driverType=\"HTTP\"";
	
	
	protected ImportExistingProjectDetailsPage(final String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription(Messages.getString("ImportExistingProjectDetailsPage.Description")); //NON-NLS-1 //$NON-NLS-1$
	}
	
	@Override
	public void createControl(final Composite root) {
		final Composite composite = new Composite(root, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		final Label cddLabel = new Label(composite, SWT.NULL);
		cddLabel.setText(Messages.getString("ImportExistingProjectDetailsPage.CDD"));
		
		cddCombo = new Combo(composite, SWT.DROP_DOWN|SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint=400;
		gd.verticalIndent = 10;
		cddCombo.setLayoutData(gd);
		
		cddCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				processingUnitCombo.removeAll();
				if(cddCombo.getItemCount() == 0) {
					((ImportExistingStudioProjectWizard)getWizard()).setSelectedCdd(null);
					return;
				}
				List<ProcessingUnit> processingUnits = getProcessingUnits();
				if (processingUnits.size() == 0) {
					return;
				}
				for(final ProcessingUnit pu:processingUnits) {
					processingUnitCombo.add(pu.getName());
				}
				processingUnitCombo.select(0);
			}
			
		});
		
		final Label processingUnitLabel = new Label(composite, SWT.NULL);
		processingUnitLabel.setText(Messages.getString("ImportExistingProjectDetailsPage.processingUnit"));
		
		processingUnitCombo = new Combo(composite, SWT.DROP_DOWN|SWT.READ_ONLY);
		processingUnitCombo.setLayoutData(gd);
		processingUnitCombo.addModifyListener(new ModifyListener() {
		
			@Override
			public void modifyText(ModifyEvent e) {
				final Combo eventSource = (Combo) e.widget;
				final int selectionIndex = eventSource.getSelectionIndex();
				if(processingUnitCombo.getItemCount() == 0) {
					((ImportExistingStudioProjectWizard)getWizard()).setSelectedPu(null);
				} else {
					((ImportExistingStudioProjectWizard)getWizard()).setSelectedPu(processingUnitCombo.getItem(selectionIndex));
					List<ProcessingUnit> processingUnits = getProcessingUnits();
					ProcessingUnit processingUnit = null;
					for(ProcessingUnit pu:processingUnits) {
						if(pu.getName().equals(processingUnitCombo.getItem(selectionIndex))) {
							processingUnit = pu;
							break;
						}
					}
					final String props = buildPropertySet(processingUnit);
					httpProperties.setText(props);
					httpProperties.setVisible(true);
					httpProperties.setEnabled(true);
				}
			}
		});
		
		final GridData gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        gridData.verticalIndent = 15;
        gridData.heightHint = 240;
        
        httpProperties = new Text(composite, SWT.MULTI|SWT.BORDER | SWT.V_SCROLL);
        httpProperties.setLayoutData(gridData);
        httpProperties.setText("");
        httpProperties.setVisible(false);
		httpProperties.setEditable(false);
		httpProperties.setEnabled(false);
		httpProperties.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_GRAY));
		setControl(composite);
	}
	
	
	private void getAllCddFilesFromProject(File folder, Map<String, File> allCddFiles, File projectLocation) {
		 final File[] listFiles = folder.listFiles();
		 if(listFiles == null) {
			 return;
		 }
		 for(final File file:listFiles) {
			 if(file.isDirectory()) {
				 getAllCddFilesFromProject(file, allCddFiles, projectLocation);
			 } else if (file.isFile() && file.getName().endsWith(".cdd")) {
				 allCddFiles.put(projectLocation.getAbsoluteFile().toURI().relativize(file.getAbsoluteFile().toURI()).toString(), file);
			 }
		 }
	}
	
	public void initializeControls(final File existingStudioProjectArchive) {
		if(cddCombo == null || processingUnitCombo == null) {
			return;
		}
		cddCombo.removeAll();
		allCddFiles = new HashMap<String, File>();
		getAllCddFilesFromProject(existingStudioProjectArchive,allCddFiles, existingStudioProjectArchive);
		if(allCddFiles.isEmpty()) {
			return;
		}
		for(Map.Entry<String, File> entry:allCddFiles.entrySet()) {
			cddCombo.add(entry.getKey());
		}
		cddCombo.select(0);
		List<ProcessingUnit> processingUnits = getProcessingUnits();
		processingUnitCombo.removeAll();
		
		for(final ProcessingUnit pu:processingUnits) {
			processingUnitCombo.add(pu.getName());
		}
		if(!processingUnits.isEmpty()) {
			processingUnitCombo.select(0);

			((ImportExistingStudioProjectWizard)getWizard()).setSelectedPu(processingUnitCombo.getItem(processingUnitCombo.getSelectionIndex()));
			final String props = buildPropertySet(processingUnits.get(0));
			httpProperties.setText(props);
			httpProperties.setVisible(true);
			httpProperties.setEnabled(true);
		}
		
	}

	public boolean isHTTPChannelPresent(final File studioProjectDir) {
		final FileFilter channelFileFilter = new FileFilter(){
			@Override
			public boolean accept(File name) {
				return (name.toString().endsWith(".channel"));
			}
		};
		File channel = getChannelName(studioProjectDir, channelFileFilter);
		if(channel != null) {
			return isHTTPChannel(channel);
		}
		if(studioProjectDir.listFiles()!=null){
			for(final File file:studioProjectDir.listFiles()) {
				if(file.isDirectory()) {
					channel = getChannelName(file, channelFileFilter);
					if(channel != null) {
						return isHTTPChannel(channel);
					}
				}
			}
		}
		return false;
	}

	//hack as we can't read 4.0 channel as a model resource 
	private boolean isHTTPChannel(final File channel) {
		final String fileContents = readFileAsString(channel);
		boolean contains = fileContents.contains(HTTP_DRIVER_TYPE);
		if(!contains) {
			contains = fileContents.contains(HTTP_DRIVER_TYPE_NAME);
		}
		return contains;
	}
	
	private String readFileAsString(File file){
	    byte[] buffer = new byte[(int) file.length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(file));
	        try {
				f.read(buffer);
			} catch (IOException ignore) {
			}
	    } catch (FileNotFoundException ignore) {

	    } finally {
	        if (f != null) {
	        	try { f.close(); } catch (IOException ignored) { }
	        }
	    }
	    return new String(buffer);
	}

	private File getChannelName(File directory, FileFilter channelFileFilter) {
		final File[] fileList = directory.listFiles(channelFileFilter);
		if(fileList!=null){
			if(fileList.length != 0 ) {
				return fileList[0]; //first found CDD will be used
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	private List<ProcessingUnit> getProcessingUnits() {
		final File cddFile = allCddFiles.get(cddCombo.getItem(cddCombo.getSelectionIndex()));
		((ImportExistingStudioProjectWizard)getWizard()).setSelectedCdd(cddFile);
		final ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(null, allCddFiles.get(cddCombo.getItem(cddCombo.getSelectionIndex())).getAbsolutePath());
		try {
			cddModelMgr.parseModel();
			return cddModelMgr.getProcessingUnits();
		} catch(Exception parseException) {
			//ignore;
		}
		return new ArrayList<ClusterConfigModel.ProcessingUnit>();
	}

	/**
	 * @param processingUnit
	 * @return
	 */
	private String buildPropertySet(ProcessingUnit processingUnit) {
		final StringBuffer buf = new StringBuffer();
		for(Map.Entry<String, String> entry:processingUnit.httpProperties.properties.entrySet()) {
			buf.append(entry.getKey());
			buf.append(" : ");
			buf.append(entry.getValue());
			buf.append("\n");
		}
		for(Map.Entry<String, String> entry:processingUnit.httpProperties.ssl.properties.entrySet()) {
			buf.append(entry.getKey());
			buf.append(" : ");
			buf.append(entry.getValue());
			buf.append("\n");
		}
		final StringBuffer ciphers = new StringBuffer();
		for(String cipher:processingUnit.httpProperties.ssl.ciphers) {
			ciphers.append(cipher);
			ciphers.append(" , ");
		}
		if(ciphers.length() > 0) {
			ciphers.replace(ciphers.length()-2, ciphers.length(), " ");
			buf.append("Ciphers");
			buf.append(" : ");
			buf.append(ciphers.toString());
			buf.append("\n");
		}
		final StringBuffer protocols = new StringBuffer();
		for(String protocol:processingUnit.httpProperties.ssl.protocols) {
			protocols.append(protocol);
			protocols.append(" , ");
		}
		if(protocols.length() > 0) {
			protocols.replace(protocols.length()-2, protocols.length(), " ");
			buf.append("Protocols");
			buf.append(" : ");
			buf.append(protocols.toString());
		}
		return buf.toString();
	}
}

