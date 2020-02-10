package com.tibco.cep.decisionproject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.serversettingsmodel.Authentication;
import com.tibco.cep.serversettingsmodel.AuthenticationURL;
import com.tibco.cep.serversettingsmodel.CheckoutURL;
import com.tibco.cep.serversettingsmodel.ServersettingsmodelFactory;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.decision.table.core.DecisionTableCorePlugin;

/**
 * 
 * @author sasahoo
 * @since 3.0.2
 *
 */
public class DecisionManagerServerSettingsLoader {

	private static DecisionManagerServerSettingsLoader dmServerSettingsLoader;	
	public static final String DECISION_MANAGER_SERVER_SETTINGS = ".DMSERVERSETTINGS";//for server login and checkout URL settings
	private static AuthenticationURL authenticationURL;
	private static CheckoutURL checkoutURL;
	
	synchronized public static DecisionManagerServerSettingsLoader getInstance() {
		if (dmServerSettingsLoader == null) {
			dmServerSettingsLoader = new DecisionManagerServerSettingsLoader();
			loadServerSettings();
		}
		return dmServerSettingsLoader;
	}
	
	/**
	 * Server Settings with Decision manager
	 */
	private static void loadServerSettings() {
		ResourceSet rs = new ResourceSetImpl();
		String file = DecisionTableCorePlugin.getDefault().getStateLocation().append(DECISION_MANAGER_SERVER_SETTINGS).toPortableString();
		File buiSettingFile = new File(file);
		if (buiSettingFile.exists()) {
			URI uri = URI.createFileURI(file);
			if (uri != null) {
				Resource resource = rs.getResource(uri, true);
				if (resource != null) {
					List<EObject> resourceList = resource.getContents();
					for (EObject eobject : resourceList) {
						if (eobject instanceof AuthenticationURL) {
							authenticationURL = (AuthenticationURL) eobject;
						}
						if (eobject instanceof CheckoutURL) {
							checkoutURL = (CheckoutURL) eobject;
						}
					}
				}
			}
		}else{
			authenticationURL =  ServersettingsmodelFactory.eINSTANCE.createAuthenticationURL();
			
			Authentication authentication = ServersettingsmodelFactory.eINSTANCE.createAuthentication();
			authenticationURL.setAuthentication(authentication);
			
			checkoutURL = ServersettingsmodelFactory.eINSTANCE.createCheckoutURL();
			try {
				if (authenticationURL.eResource() == null) {
					ResourceSet resourceSet = new ResourceSetImpl();
					URI resURI = URI.createFileURI(file);
					Resource resource = resourceSet.createResource(resURI);
					resource.getContents().add(authenticationURL);
					resource.getContents().add(authentication);
					resource.getContents().add(checkoutURL);
					resource.save(ModelUtils.getPersistenceOptions()); // G11N encoding changes
				} 
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	
	
	
	/**
	 * save
	 */
	public void save(){
			try {
				ModelUtils.saveEObject(authenticationURL);  // G11N encoding changes
				ModelUtils.saveEObject(checkoutURL);  // G11N encoding changes
//				authenticationURL.eResource().save(null);
//				checkoutURL.eResource().save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * save
	 */
	public void saveCheckOutInfo(){
		try {
			ModelUtils.saveEObject(checkoutURL);  // G11N encoding changes
//			checkoutURL.eResource().save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 */
	public AuthenticationURL getAuthenticationURL() {
		return authenticationURL;
	}

	/**
	 * @return
	 */
	public CheckoutURL getCheckoutURL() {
		return checkoutURL;
	}
}
