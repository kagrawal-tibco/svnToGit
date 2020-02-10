package com.tibco.cep.studio.wizard.as.internal.services.spi;

import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_SERVICE_PERSISTENCE_EXISTING_RESOURCE;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_SERVICE_PERSISTENCE_ORIGINAL_RESOURCE;
import static com.tibco.cep.studio.wizard.as.ASPlugin._FLAG_PERFORM_SYNC_EXEC;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.ASPlugin.IOpenableInShell;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.services.spi.AStageParticipant;

public class PersistenceStageParticipant extends AStageParticipant {

	// stages
	public static final int _STAGE_EXISTING_DESTINATION = 1;

	@Override
	public boolean participate(int stage, IContext context) {
		boolean canContinue = true;
		switch (stage) {
			case _STAGE_EXISTING_DESTINATION:
				canContinue = stageExistingResource(context);
				break;

			default:
				break;
		}
		return canContinue;
	}

	private boolean stageExistingResource(IContext context) {
		boolean canContinue = true;
		boolean existingResource = (Boolean) context.get(_K_CTX_SERVICE_PERSISTENCE_EXISTING_RESOURCE);
//		Shell shell = (Shell) context.get(_K_CTX_UI_CONTROL_SHELL);
		if (existingResource) {
			final Entity existingEntity = (Entity) context.get(_K_CTX_SERVICE_PERSISTENCE_ORIGINAL_RESOURCE);
			final boolean[] replaces = new boolean[1];
			IOpenableInShell openable = new IOpenableInShell() {

				@Override
				public void open(Shell shell) {
					replaces[0]= MessageDialog.openQuestion(shell, Messages.getString("PersistenceStageParticipant.title_question"), Messages.getString("PersistenceStageParticipant.exist_duplicate_resource") + existingEntity.getFullPath() + Messages.getString("PersistenceStageParticipant.exist_duplicate_resource2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}

			};
			ASPlugin.openDialog(null, openable, _FLAG_PERFORM_SYNC_EXEC);
			canContinue = replaces[0];
		}
		return canContinue;
	}
}
