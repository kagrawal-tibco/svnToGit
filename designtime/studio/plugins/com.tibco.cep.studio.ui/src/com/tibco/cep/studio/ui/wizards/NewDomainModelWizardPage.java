/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author hitesh
 *
 */
public class NewDomainModelWizardPage extends EntityFileCreationWizard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public NewDomainModelWizardPage(String pageName,
			IStructuredSelection selection, String type) {
		super(pageName, selection, type);
	}

	public NewDomainModelWizardPage(String pageName,
			IStructuredSelection selection, String type, String currentProjectName) {
		super(pageName, selection, type, currentProjectName);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard#checkValidAccess()
	 */
	@Override
	protected boolean checkValidAccess() {
	// ACL Check
		if (Utils.isStandaloneDecisionManger()) {
			List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
			Permit permit = Permit.DENY;
			String operation = "create";
			if (!(roles.isEmpty())) {
				IAction action = ActionsFactory.getAction(ResourceType.DOMAIN, operation);
				permit = SecurityUtil.ensurePermission(this.project.getName(),
						getContainerFullPath().toOSString(),
						ResourceType.DOMAIN, roles, action,
						PermissionType.BERESOURCE);
			}
			if (Permit.DENY.equals(permit)) {
				setErrorMessage(Messages.getString("access.resource.error", operation));
				return false;
			} else if (Permit.ALLOW.equals(permit)) {
				setErrorMessage(null);
				return true;
			}
		}
		return true;
	}
}
