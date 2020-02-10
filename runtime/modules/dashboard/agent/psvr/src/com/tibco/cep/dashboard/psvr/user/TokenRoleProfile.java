package com.tibco.cep.dashboard.psvr.user;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.alerts.AlertEvalutorsIndex;
import com.tibco.cep.dashboard.psvr.editors.EditorException;
import com.tibco.cep.dashboard.psvr.editors.EditorTransaction;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGallery;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
//TODO think if we can make tokenToInstanceMap concurrent ?
public class TokenRoleProfile {

	static Logger LOGGER;

	static ExceptionHandler EXCEPTION_HANDLER;

	static MessageGenerator MESSAGE_GENERATOR;

	static TokenRoleProfileProcessor PROCESSOR;

	private static final TokenRoleProfile[] EMPTY_TK_PROFILE_ARRAY = new TokenRoleProfile[0];

	static Map<SecurityToken, Map<String, TokenRoleProfile>> tokenToInstanceMap = new HashMap<SecurityToken, Map<String, TokenRoleProfile>>();

	public static final synchronized TokenRoleProfile getInstance(SecurityToken securityToken) throws MALException, ElementNotFoundException {
		Principal preferredPrincipal = securityToken.getPreferredPrincipal();
		if (preferredPrincipal == null) {
			throw new RuntimeException("invalid.prefferred.principal");
		}
		Map<String, TokenRoleProfile> roleBasedInsightConfigMap = tokenToInstanceMap.get(securityToken);
		if (roleBasedInsightConfigMap == null) {
			roleBasedInsightConfigMap = new HashMap<String, TokenRoleProfile>();
			tokenToInstanceMap.put(securityToken, roleBasedInsightConfigMap);
		}
		String preferredPrincipalName = preferredPrincipal.getName();
		TokenRoleProfile tokenProfile = (TokenRoleProfile) roleBasedInsightConfigMap.get(preferredPrincipalName);
		if (tokenProfile == null) {
			tokenProfile = new TokenRoleProfile(securityToken);
			roleBasedInsightConfigMap.put(preferredPrincipalName, tokenProfile);
		}
		return tokenProfile;
	}

	static final synchronized TokenRoleProfile[] getInstances(SecurityToken securityToken) {
		Map<String, TokenRoleProfile> roleBasedInsightConfigMap = tokenToInstanceMap.get(securityToken);
		if (roleBasedInsightConfigMap != null) {
			TokenRoleProfile[] profiles = new TokenRoleProfile[roleBasedInsightConfigMap.size()];
			roleBasedInsightConfigMap.values().toArray(profiles);
			return profiles;
		}
		return EMPTY_TK_PROFILE_ARRAY;
	}

	private SecurityToken token;

	private Principal preferredPrincipal;

	protected MALSession malsession;

	protected AlertEvalutorsIndex alertEvalutorsIndex;

	protected ViewsConfigHelper viewsConfigHelper;

	protected MALComponentGallery componentGallery;

	protected GalleryGenerator componentGalleryGenerator;

	private EditorTransaction editorTransaction;

	private Map<String, Object> attributes;

	protected TokenRoleProfile(){

	}

	private TokenRoleProfile(SecurityToken token) throws MALException, ElementNotFoundException {
		this.token = token;
		this.preferredPrincipal = this.token.getPreferredPrincipal();
		this.malsession = new MALSession(token);
		alertEvalutorsIndex = new AlertEvalutorsIndex(token);
		loadComponentGallery();
		loadInsightConfigHelper();
		componentGalleryGenerator = new GalleryGenerator(token, viewsConfigHelper, componentGallery);
		attributes = new HashMap<String, Object>();
		if (PROCESSOR != null){
			PROCESSOR.process(token, this);
		}
	}

	private void loadComponentGallery() throws MALException, ElementNotFoundException {
		componentGallery = new MALComponentGallery(malsession);
		Iterator<String> componentIDs = componentGallery.getComponentIDs();
		while (componentIDs.hasNext()) {
			String componentID = (String) componentIDs.next();
			MALComponent component = componentGallery.searchComponent(componentID);
			try {
				alertEvalutorsIndex.indexEvaluators(component);
			} catch (PluginException e) {
				MessageGeneratorArgs args = new MessageGeneratorArgs(token.toString(),token.getUserID(),token.getPreferredPrincipal(),token.getPrincipals(),e,component.toString());
				MESSAGE_GENERATOR.getMessage("tokenroleprofile.gallery.component.alertindexing.failure", args);
				EXCEPTION_HANDLER.handleException(e);
			}
		}
	}

	private void loadInsightConfigHelper() throws MALException, ElementNotFoundException {
		MALView viewsConfig = malsession.getView();
		viewsConfigHelper = new ViewsConfigHelper(token,alertEvalutorsIndex,viewsConfig);
	}

	public EditorTransaction createTransaction(String pageid, String panelid, String componentid) throws MALException, EditorException {
		if (editorTransaction != null) {
			throw new IllegalStateException("A transaction is already in process");
		}
		MALPage page = viewsConfigHelper.getPageByID(pageid);
		if (page == null) {
			throw new EditorException("No page with id as " + pageid + " found in " + viewsConfigHelper);
		}
		MALPanel panel = viewsConfigHelper.getPanelInPage(page, panelid);
		if (panel == null) {
			throw new EditorException("No panel with id as " + panelid + " found in " + URIHelper.getURI(page,true,viewsConfigHelper.getViewsConfig().getName()));
		}
		MALComponent component = viewsConfigHelper.getComponentById(componentid);
		if (component == null) {
			throw new EditorException("No component with id as " + componentid + " found in " + URIHelper.getURI(page,true,viewsConfigHelper.getViewsConfig().getName()));
		}
		//TODO use the plug-in classes to create a editor transaction based on component
		editorTransaction.setViewConfig(viewsConfigHelper.getViewsConfig());
		editorTransaction.setPageBeingEdited(page);
		editorTransaction.setPanelBeingEdited(panel);
		editorTransaction.setComponentBeingEdited(component);
		return editorTransaction;
	}

	public MALSession getMALSession() {
		return this.malsession;
	}

	public MALComponentGallery getComponentGallery() {
		return componentGallery;
	}

	public ViewsConfigHelper getViewsConfigHelper() {
		return viewsConfigHelper;
	}

	public AlertEvalutorsIndex getAlertEvalutorsIndex() {
		return alertEvalutorsIndex;
	}

	public GalleryGenerator getComponentGalleryGenerator() {
		return componentGalleryGenerator;
	}

	public EditorTransaction getTransaction() {
		return editorTransaction;
	}

	public void closeTransaction() throws EditorException {
		if (editorTransaction != null) {
			editorTransaction.close();
			editorTransaction = null;
		}
	}

	public void addAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public final Principal getPreferredPrincipal() {
		return preferredPrincipal;
	}

	protected void destroy() {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Shutting down "+this+"...");
		}
		try {
			closeTransaction();
		} catch (EditorException e) {
			MessageGeneratorArgs args = new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), e, editorTransaction.getComponent());
			String msg = MESSAGE_GENERATOR.getMessage("tokenroleprofile.editortransaction.closing.failure", args);
			EXCEPTION_HANDLER.handleException(LOGGER, msg, e, Level.WARN, Level.DEBUG);
		}
		componentGalleryGenerator.destroy();
		componentGallery.destroy();
		viewsConfigHelper.destroy();
		malsession.close();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());
		sb.append("[token="+token);
		sb.append(",user="+token.getUserID());
		sb.append(",role="+preferredPrincipal);
		sb.append("]");
		return sb.toString();
	}

}