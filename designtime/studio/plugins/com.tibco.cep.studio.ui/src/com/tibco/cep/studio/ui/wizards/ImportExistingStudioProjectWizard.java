package com.tibco.cep.studio.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.actions.ImportExistingStudioProjectOperation;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class ImportExistingStudioProjectWizard extends Wizard implements IImportWizard {
	
	private ImportExistingStudioProjectPage mainPage;
    private ImportExistingProjectDetailsPage inputPage;
	private File selectedCdd;
	private String selectedPu;
	private XPATH_VERSION xpathVersion;

	public ImportExistingStudioProjectWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		
		if (!mainPage.getLocationPath().toFile().getParentFile().exists()) {
			mainPage.getLocationPath().toFile().getParentFile().mkdirs();
		}
		
		IProject newProj = mainPage.getProjectHandle();
        if (newProj == null)
            return false;
        if (!newProj.exists()) {
        	createImportedStudioProject(newProj);
        }
        return true;
	}
	 
	private void createImportedStudioProject(IProject newProj) {

		File fileToImport = mainPage.getExistingStudioProjectArchive();
		File targetLocation = new File(mainPage.getLocationPath().toOSString());
		final Map<String, String> httpProperties = retriveHTTPPropertiesFromCDD();
		WorkspaceModifyOperation op = new ImportExistingStudioProjectOperation(
				newProj, fileToImport, targetLocation, httpProperties, mainPage.importIntoWorkspace(), getXpathVersion());
		try {
			getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Switching to Studio Development perspective if current one is any other perspective
		StudioResourceUtils.switchStudioPerspective();
	}

	/**
	 * @return httpProperties
	 */
	private Map<String, String> retriveHTTPPropertiesFromCDD() {
		Map<String, String> httpProperties = new HashMap<String, String>();
		if (selectedCdd == null) {
			return httpProperties;
		}
		final ClusterConfigModelMgr cddModelMgr = new ClusterConfigModelMgr(null, selectedCdd.getAbsolutePath());
		cddModelMgr.parseModel();
		List<ProcessingUnit> processingUnits = cddModelMgr.getProcessingUnits();
		
		for (final ProcessingUnit pu : processingUnits) {
			if (pu.getName().equals(selectedPu)) {
				LinkedHashMap<String, String> properties = pu.httpProperties.properties;
				for (Map.Entry<String, String> entry : properties.entrySet()) {
					final String key = entry.getKey();
					if (key.equals(Elements.CONNECTION_TIMEOUT.localName)) {
						httpProperties.put("be.http.connectionTimeout", entry.getValue());
					} else if (key.equals(Elements.ACCEPT_COUNT.localName)) {
						httpProperties.put("be.http.acceptCount", entry.getValue());
					} else if (key.equals(Elements.CONNECTION_LINGER.localName)) {
						httpProperties.put("be.http.connectionLinger", entry.getValue());
					} else if (key.equals(Elements.SOCKET_OUTPUT_BUFFER_SIZE.localName)) {
						httpProperties.put("be.http.socketBufferSize", entry.getValue());
					} else if (key.equals(Elements.MAX_PROCESSORS.localName)) {
						httpProperties.put("be.http.maxProcessors", entry.getValue());
					} else if (key.equals(Elements.TCP_NO_DELAY.localName)) {
						httpProperties.put("be.http.tcpNoDelay", entry.getValue());
					} else if (key.equals(Elements.DOCUMENT_PAGE.localName)) {
						httpProperties.put("be.http.docPage", entry.getValue());
					} else if (key.equals(Elements.DOCUMENT_ROOT.localName)) {
						httpProperties.put("be.http.docRoot", entry.getValue());
					} else if (key.equals(Elements.STALE_CONNECTION_CHECK.localName)) {
						httpProperties.put("be.http.async.staleConnectionCheck", entry.getValue());
					} 
				}
				for (Map.Entry<String, String> entry : pu.httpProperties.ssl.properties.entrySet()) {
					final String key = entry.getKey();
					if (key.equals(Elements.KEY_MANAGER_ALGORITHM.localName)) {
						httpProperties.put("be.http.ssl_server_keymanageralgorithm", entry.getValue());
					} else if (key.equals(Elements.TRUST_MANAGER_ALGORITHM.localName)) {
						httpProperties.put("be.http.ssl_server_trustmanageralgorithm", entry.getValue());
					}
				}
				final StringBuffer ciphers = new StringBuffer();
				for (String cipher : pu.httpProperties.ssl.ciphers) {
					ciphers.append(cipher);
					ciphers.append(",");
				}
				if (ciphers.length() > 0) {
					ciphers.replace(ciphers.length()-1, ciphers.length(), "");
					httpProperties.put("be.http.ssl_server_ciphers", ciphers.toString());
				} else {	// BE-27283 - set default value for be.http.ssl_server_ciphers
					httpProperties.put("be.http.ssl_server_ciphers", StudioCore.HTTP_SSL_SERVER_CIPHERS);
				}
				final StringBuffer protocols = new StringBuffer();
				for (String protocol : pu.httpProperties.ssl.protocols) {
					// BE-27283 - replace TLSv1 w/ TLSv1.3 to disable/disallow support for weaker ciphers
					if (protocol.equalsIgnoreCase("TLSv1")) {
						protocols.append("TLSv1.3");
					} else {
						protocols.append(protocol);
					}
					protocols.append(",");
				}
				if (protocols.length() > 0) {
					protocols.replace(protocols.length() - 1, protocols.length(), "");
					httpProperties.put("be.http.ssl_server_enabledprotocols", protocols.toString());
				} else {	// BE-27283 - set default value for be.http.ssl_server_enabledprotocols
					httpProperties.put("be.http.ssl_server_enabledprotocols", StudioCore.HTTP_SSL_SERVER_ENABLED_PROTOCOLS);
				}
				break;
			}
		}
		return httpProperties;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("ExistingStudioImportWizard.Title")); //NON-NLS-1 //$NON-NLS-1$
		setNeedsProgressMonitor(true);
		mainPage = new ImportExistingStudioProjectPage(Messages.getString("ExistingStudioImportWizard.ImportDesc")); //NON-NLS-1 //$NON-NLS-1$
		inputPage = new ImportExistingProjectDetailsPage(Messages.getString("ExistingStudioImportWizard.SelectProcessingUnitDetails"));
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(mainPage);
        addPage(inputPage);
    }

	protected void resultError() {
		mainPage.setErrorMessage(Messages.getString("new_project_creation_fail"));
	}
	
	public void setSelectedCdd(final File selectedCdd) {
		this.selectedCdd = selectedCdd;
	}

	public void setSelectedPu(final String selectedPu) {
		this.selectedPu = selectedPu;
	}
	
	
	public XPATH_VERSION getXpathVersion() {
		return xpathVersion;
	}

	public void setXpathVersion(XPATH_VERSION xpathVersion) {
		this.xpathVersion = xpathVersion;
	}

	public boolean canFinish() {
		//fix for BE#11956
		if(mainPage.getProjectHandle() != null && mainPage.isPageComplete() ) {
			IWizardPage nextPage = mainPage.getNextPage();
			if(nextPage == null) {
				return true;
			}else if(nextPage.isPageComplete()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		final IWizardPage nextPage = super.getNextPage(page);
		
		if(nextPage instanceof ImportExistingProjectDetailsPage) {
			((ImportExistingProjectDetailsPage)nextPage).initializeControls(mainPage.getExistingStudioProjectArchive());
			boolean httpChannelPresent = ((ImportExistingProjectDetailsPage)nextPage).isHTTPChannelPresent(mainPage.getExistingStudioProjectArchive());
			if(!httpChannelPresent) {
				return null;
			}
		} 
		return nextPage;
	}
}
