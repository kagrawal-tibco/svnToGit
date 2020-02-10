/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import com.tibco.cep.webstudio.client.datasources.AbstractRestDS;
import com.tibco.cep.webstudio.client.datasources.RMSSyncToRepositoryArtifactsDS;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Dialog box for allowing the user to select and sync the selected artifacts with the underlying repository
 * 
 * @author vpatil
 */
public class RMSSyncToRepository extends RMSCheckoutDialog {
	
	@Override
	protected void setDialogTitle() {
		String title = globalMsgBundle.menu_rmsSyncToRepository();
		setDialogTitle(title);
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "synchronize" + WebStudioMenubar.ICON_SUFFIX);
	}
	
	@Override
	protected ServerEndpoints getServerEndpoint() {
		return ServerEndpoints.RMS_POST_PROJECT_REPOSITORY_SYNC;
	}
	
	@Override
	protected void setupDataSourceForrProjectArtifacts() {
		RMSSyncToRepositoryArtifactsDS.getInstance().clearRequestParameters();
		RMSSyncToRepositoryArtifactsDS.getInstance().setAdditionalURLPath("projects/" + getSelectedProject());
		RMSSyncToRepositoryArtifactsDS.getInstance().setHttpMethod(HttpMethod.GET);
	}
	
	@Override
	protected AbstractRestDS getDataSourceForProjectArtifacts() {
		return RMSSyncToRepositoryArtifactsDS.getInstance();
	}

}
