package com.tibco.cep.webstudio.client.process.util;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.util.BooleanCallback;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tomsawyer.util.gwtclient.rpc.TSAsyncCallback;
import com.tomsawyer.web.client.TSWebServices;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;
import com.tomsawyer.web.client.view.data.TSWebViewClientCommandData;

/**
 * This utility class is used to invoke different tom swayer command.
 * 
 * @author dijadhav
 * 
 */
public class ProcessCommandUtil {

	private static ProcessMessages processMessages = (ProcessMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);
	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	private static final String DELETE_SERVICE_URL = "com.tibco.cep.webstudio.server.command.DeleteToolCommandImpl";
	private static final String DELETE_ALLOWED_SERVICE_URL = "com.tibco.cep.webstudio.server.command.ProcessElementDeleteAllowedCommandImpl";
	private static final String UNDO_ALLOWED_SERVICE_URL = "com.tibco.cep.webstudio.server.command.UndoAllowedCommandImpl";
	private static final String PROCESS_SAVE_SERVICE_URL = "com.tibco.cep.webstudio.server.command.ProcessSaveCommandImpl";
	private static final String LOAD_PROCESS_SERVICE_URL = "com.tibco.cep.webstudio.server.command.ProcessLoadCommandImpl";
	private static final String VALIDATE_PROCESS_SERVICE_URL = "com.tibco.cep.webstudio.server.command.ProcessValidationCommandImpl";
	private static final String CLEAR_UNDO_HISTORY_SERVICE_URL = "com.tibco.cep.webstudio.server.command.ClearUndoHistoryCommandImpl";
	private static final String FETCH_PROPERTY_SERVICE_URL = "com.tibco.cep.webstudio.server.command.ProcessFetchPropertiesCommandImpl";
	private static final String REDO_ALLOWED_SERVICE_URL = "com.tibco.cep.webstudio.server.command.RedoAllowedCommandImpl";
	private static final String UNDO_SERVICE_URL = "com.tibco.cep.webstudio.server.command.UndoCommandImpl";
	private static final String REDO_SERVICE_URL = "com.tibco.cep.webstudio.server.command.RedoCommandImpl";
	private static final String UPDATE_PROCESS_VERIABLE_SERVICE_URL = "com.tibco.cep.webstudio.server.command.VariablesUpdateCommandImpl";
	
	
	/**
	 * This method is used to delete the selected element of the process.
	 * 
	 * @param processEditor
	 */
	public static void delete(final AbstractProcessEditor processEditor) {

		// Create and invoke the delete allowed command.
		String drawingViewID = processEditor.getDrawingViewID();
		TSCustomCommand deleteAllowedCommand = getCustomCommand(drawingViewID,
				DELETE_ALLOWED_SERVICE_URL, null, processEditor.getModelId());

		DeleteElementAllowedCallback deleteElementAllowedCallback = new DeleteElementAllowedCallback(
				drawingViewID, processEditor);
		TSWebServices.getWebViewService().invoke(deleteAllowedCommand,
				deleteElementAllowedCallback);
	}

	/**
	 * This method is used to invoke the command which loads the process data.
	 * 
	 * @param processEditor
	 * @param projectName
	 * @param processArtifactPath
	 * @param artifactExtention
	 * @param process
	 * @param toolbarTitleMap 
	 * @param processName
	 */
	public static void load(final AbstractProcessEditor processEditor,
			String processArtifactPath, String artifactExtention, String process) {
		processEditor.showPalette();
		// Create and invoke the process data load command
		LinkedList<String> args = new LinkedList<String>();
		args.add(processEditor.getProjectName());
		args.add(processArtifactPath);
		args.add(artifactExtention);
		args.add(process);
		args.add(Window.Location.getParameter("locale"));
		args.add(String.valueOf(processEditor.isNewArtifact()));
		args.add(processEditor.getProcessName());

		TSCustomCommand populateCommand = getCustomCommand(
				processEditor.getDrawingViewID(), LOAD_PROCESS_SERVICE_URL,
				args, processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(populateCommand,
				new LoadProcessAsyncCallback(processEditor));
	}

	/**
	 * This method is used to invoke the command which saves the process data.
	 * 
	 * @param processEditor
	 * @param processArtifactPath
	 * @param artifactExtention
	 */
	public static void save(final AbstractProcessEditor processEditor,
			String processArtifactPath, String artifactExtention) {

		// Create and invoke save process command
		LinkedList<String> args = new LinkedList<String>();
		args.add(processEditor.getProjectName());
		args.add(processArtifactPath);
		args.add(artifactExtention);

		TSCustomCommand saveCommand = getCustomCommand(
				processEditor.getDrawingViewID(), PROCESS_SAVE_SERVICE_URL,
				args, processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(saveCommand,
				new SaveProcessAsyncCallback(processEditor));
	}

	/**
	 * his method is used to invoke the undo command.
	 * 
	 * @param processEditor
	 */
	public static void undo(final AbstractProcessEditor processEditor) {
		// Create and invoke the undo command.
		LinkedList<String> args = new LinkedList<String>();
		args.add(projectID);
		String drawingViewID = processEditor.getDrawingViewID();
		args.add(drawingViewID);

		TSCustomCommand undoCommand = getCustomCommand(drawingViewID,
				UNDO_ALLOWED_SERVICE_URL, args, processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(undoCommand,
				new UndoAllowedAsyncCallback(processEditor));
	}

	/**
	 * This method is used to invoke the redo command.
	 * 
	 * @param processEditor
	 */
	public static void redo(final AbstractProcessEditor processEditor) {

		// Create and invoke the redo command.
		LinkedList<String> args = new LinkedList<String>();
		args.add(projectID);
		final String drawingViewID = processEditor.getDrawingViewID();
		args.add(drawingViewID);
		TSCustomCommand undoCommand = getCustomCommand(drawingViewID,
				REDO_ALLOWED_SERVICE_URL, args, processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(undoCommand,
				new RedoAllowedAsyncCallback(drawingViewID, processEditor));
	}

	/**
	 * This method is used to invoke the command which fetch the process and its
	 * element property.
	 * 
	 * @param processEditor
	 * @param id
	 * @param propertyType
	 */
	public static void fetchProperty(final AbstractProcessEditor processEditor,
			String id, String propertyType) {
		// Create and invoke fetch property command
		LinkedList<String> args = new LinkedList<String>();
		args.add(id);
		args.add(processEditor.getProcessName());
		args.add(propertyType);

		TSCustomCommand populate = getCustomCommand(
				processEditor.getDrawingViewID(), FETCH_PROPERTY_SERVICE_URL,
				args, processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(populate,
				new FetchPropertyAsyncCallback(processEditor));
	}

	/**
	 * This method is used to invoke the command which clear the undo history of
	 * the process.
	 * 
	 * @param processEditor
	 */
	public static void clearHistory(final AbstractProcessEditor processEditor) {
		// Create and invoke the clear undo history command.
		LinkedList<String> args = new LinkedList<String>();

		TSCustomCommand clearUndoHistoryCommand = getCustomCommand(
				processEditor.getDrawingViewID(),
				CLEAR_UNDO_HISTORY_SERVICE_URL, args,
				processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(clearUndoHistoryCommand,
				new ClearHistoryAsyncCallback());
	}

	/**
	 * This method is used to validate the process data.
	 * 
	 * @param processEditor
	 */
	public static void validate(final AbstractProcessEditor processEditor) {
		// Create and invoke the validate process command.

		TSCustomCommand populate = getCustomCommand(
				processEditor.getDrawingViewID(), VALIDATE_PROCESS_SERVICE_URL,
				null, processEditor.getModelId());
		TSWebServices.getWebViewService().invoke(populate,
				new ValidateProcessAsyncCallBack(processEditor));
	}

	/**
	 * This is the call back function of validation command invocation.On
	 * success of validation it shows the validation errors.On Failure it shows
	 * the failure message.
	 * 
	 * @author dijadhav
	 * 
	 */
	private static final class ValidateProcessAsyncCallBack extends
			TSAsyncCallback<Object> {
		private final AbstractProcessEditor abstractProcessEditor;

		private ValidateProcessAsyncCallBack(
				AbstractProcessEditor abstractProcessEditor) {
			this.abstractProcessEditor = abstractProcessEditor;
		}

		@Override
		public void onSuccess(Object result) {
			TSWebViewClientCommandData data = (TSWebViewClientCommandData) result;
			if (null != data) {
				List<Serializable> validationResult = data.getCommandData();
				if (null != validationResult && !validationResult.isEmpty()) {
					abstractProcessEditor.showProblems(validationResult);
				}
				AbstractProcessEditor.indeterminateProgress("", true);
			}
		}
	}

	private static final class ClearHistoryAsyncCallback extends
			TSAsyncCallback<Object> {
		@Override
		public void onSuccess(Object result) {

		}
	}

	private static final class FetchPropertyAsyncCallback extends
			TSAsyncCallback<Object> {
		private final AbstractProcessEditor processEditor;

		private FetchPropertyAsyncCallback(AbstractProcessEditor processEditor) {
			this.processEditor = processEditor;
		}

		public void onSuccess(Object result) {
			TSWebViewClientCommandData data = (TSWebViewClientCommandData) result;
			if (null != data) {
				for (Object object : data.getCommandData()) {
					if (object != null) {
						processEditor.showProperties((Property) object);
					}
				}
			}
		}
	}

	private static final class RedoAllowedAsyncCallback extends
			TSAsyncCallback<Object> {
		private class RedoAsyncCallback extends TSAsyncCallback<Object> {
			@Override
			public void onSuccess(Object result) {
				if (!processEditor.isDirty()) {
					processEditor.makeDirty();
				}
				processEditor.getPane().focus();
				processEditor.getPane().setCanFocus(true);
				TSWebModelViewCoordinators.get(processEditor.getModelId())
						.getView(drawingViewID).update(true);
			}
		}

		private final String drawingViewID;
		private final AbstractProcessEditor processEditor;

		private RedoAllowedAsyncCallback(String drawingViewID,
				AbstractProcessEditor processEditor) {
			this.drawingViewID = drawingViewID;
			this.processEditor = processEditor;
		}

		@Override
		public void onSuccess(Object result) {
			if (null != result) {
				if (result instanceof Boolean) {
					boolean isAllowed = ((Boolean) result).booleanValue();
					if (isAllowed) {

						// Create and invoke the redo command.
						LinkedList<String> args = new LinkedList<String>();
						args.add(projectID);
						args.add(drawingViewID);

						TSCustomCommand redoCommand = getCustomCommand(
								drawingViewID, REDO_SERVICE_URL, args,
								processEditor.getModelId());
						TSWebServices.getWebViewService().invoke(redoCommand,
								new RedoAsyncCallback());
					}
				}
			}
		}
	}

	/**
	 * This callback is used to handle success and failure of the save process
	 * data.
	 * 
	 * @author dijadhav
	 * 
	 */
	private static final class SaveProcessAsyncCallback extends
			TSAsyncCallback<Object> {
		private final AbstractProcessEditor processEditor;

		private SaveProcessAsyncCallback(AbstractProcessEditor processEditor) {
			this.processEditor = processEditor;
		}

		@Override
		public void onSuccess(Object result) {
			TSWebViewClientCommandData data = (TSWebViewClientCommandData) result;
			for (Object object : data.getCommandData()) {
				if (object != null) {
					processEditor.sendSaveRequest(object);
					processEditor.fetchProperties(ProcessConstants.PROCESS_ID,
							ProcessConstants.GENERAL_PROPERTY);
				}
			}
		}
	}

	/**
	 * This callback is used to handle success and failure of the loading the
	 * process data.
	 * 
	 * @author dijadhav
	 * 
	 */
	private static final class LoadProcessAsyncCallback extends
			TSAsyncCallback<Object> {
		private final AbstractProcessEditor processEditor;

		private LoadProcessAsyncCallback(AbstractProcessEditor processEditor) {
			this.processEditor = processEditor;
		}

		@Override
		public void onSuccess(Object result) {		
			AbstractProcessEditor.publish(processEditor);
			TSWebModelViewCoordinators.get(processEditor.getModelId())
					.getView(processEditor.getDrawingViewID()).update(false);

			if (processEditor.isNewArtifact()) {
				processEditor.onSave();
				processEditor.setNewArtifact(false);
			}

			if (processEditor.isInitialized()) {
				processEditor.setInitialized(false);
			}
			processEditor.fetchProperties(ProcessConstants.PROCESS_ID,
					ProcessConstants.GENERAL_PROPERTY);
			
		}
	}

	private static final class UndoAllowedAsyncCallback extends
			TSAsyncCallback<Object> {
		/**
		 * This callback is used to handle the success and failure of undo
		 * command.
		 * 
		 * @author dijadhav
		 * 
		 */
		private class UndoAsyncCallback extends TSAsyncCallback<Object> {
			@Override
			public void onSuccess(Object result) {
				if (!processEditor.isDirty()) {
					processEditor.makeDirty();
				}
				processEditor.getPane().focus();
				processEditor.getPane().setCanFocus(true);
				TSWebModelViewCoordinators.get(processEditor.getModelId())
						.getView(processEditor.getDrawingViewID())
						.update(false);
				processEditor.fetchProperties(ProcessConstants.PROCESS_ID,
						ProcessConstants.GENERAL_PROPERTY);
			}
		}

		private final AbstractProcessEditor processEditor;

		private UndoAllowedAsyncCallback(AbstractProcessEditor processEditor) {
			this.processEditor = processEditor;
		}

		@Override
		public void onSuccess(Object result) {
			if (null != result) {
				if (result instanceof Boolean) {
					Boolean isAllowed = (Boolean) result;
					if (isAllowed.booleanValue()) {

						// Create and invoke the undo command.
						LinkedList<String> args = new LinkedList<String>();
						args.add(projectID);
						args.add(processEditor.getDrawingViewID());
						TSCustomCommand undoCommand = getCustomCommand(
								processEditor.getDrawingViewID(),
								UNDO_SERVICE_URL, args,
								processEditor.getModelId());
						TSWebServices.getWebViewService().invoke(undoCommand,
								new UndoAsyncCallback());
					}
				}
			}
		}
	}

	/**
	 * This class is call back for command which checks whether the delete is
	 * allowed or not.
	 * 
	 * @author dijadhav
	 * 
	 */
	private static final class DeleteElementAllowedCallback extends
			TSAsyncCallback<Object> {
		private final AbstractProcessEditor processEditor;

		private DeleteElementAllowedCallback(String drawingViewID,
				AbstractProcessEditor processEditor) {
			this.processEditor = processEditor;
		}

		@Override
		public void onSuccess(Object result) {
			TSWebViewClientCommandData data = (TSWebViewClientCommandData) result;
			if (null != data) {
				List<Serializable> serializables = data.getCommandData();
				if (null != serializables && !serializables.isEmpty()) {
					Serializable serializable = serializables.get(0);
					if (serializable instanceof Boolean) {
						Boolean isAllowed = (Boolean) serializable;
						if (isAllowed.booleanValue()) {
							CustomSC.confirm(
									globalMsgBundle.text_confirm(),
									processMessages.processDeleteConfirmation(),
									new DeleteConfirmationCallBack(
											processEditor));
						} else {
							CustomSC.say(processMessages.deleteNotAllowed());
						}
					}

				}

			}
		}
	}

	/**
	 * This is callback is used for confirmation of delete opertaion.
	 * 
	 * @author dijadhav
	 * 
	 */
	private static final class DeleteConfirmationCallBack implements
			BooleanCallback {

		private AbstractProcessEditor processEditor;

		/**
		 * This callback class is used handle the success or failure of delete
		 * command
		 * 
		 * @author dijadhav
		 * 
		 */
		private class DeleteAsycCallback extends TSAsyncCallback<Object> {
			private final String drawingViewID;

			private DeleteAsycCallback(String drawingViewID) {
				this.drawingViewID = drawingViewID;
			}

			@Override
			public void onSuccess(Object result) {
				processEditor.fetchProperties(ProcessConstants.PROCESS_ID,
						ProcessConstants.GENERAL_PROPERTY);
				TSWebModelViewCoordinators.get(processEditor.getModelId())
						.getView(drawingViewID).update(false);
			}

			@Override
			public void onFailure(Throwable throwable) {
				CustomSC.say("Failed to delete the seletced element.");
			}
		}

		private DeleteConfirmationCallBack(AbstractProcessEditor processEditor) {
			this.processEditor = processEditor;
		}

		@Override
		public void execute(Boolean value) {
			if (value) {
				if (!processEditor.isDirty()) {
					processEditor.makeDirty();
				}

				// Create and invoke the delete process element command.
				LinkedList<String> args = new LinkedList<String>();
				args.add(projectID);
				final String drawingViewID = processEditor.getDrawingViewID();
				args.add(drawingViewID);
				TSCustomCommand deleteCommand = getCustomCommand(drawingViewID,
						DELETE_SERVICE_URL, args, processEditor.getModelId());

				TSWebServices.getWebViewService().invoke(deleteCommand,
						new DeleteAsycCallback(drawingViewID));
			}
		}
	}

	/**
	 * This method is used to create the custom command.
	 * 
	 * @param drawingViewId
	 * @param servieImpl
	 * @param args
	 * @param modelID
	 * @return
	 */
	private static TSCustomCommand getCustomCommand(String drawingViewId,
			String servieImpl, List<String> args, String modelID) {
		return new TSCustomCommand(projectID, moduleName, modelID,
				drawingViewId, drawingViewName, servieImpl, args);
	}

	public static void updateProcessVariable(String variablesData) {
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}
		LinkedList<String> args = new LinkedList<String>();
		args.add(variablesData);
		TSCustomCommand populate = new TSCustomCommand(projectID, moduleName,
				processEditor.getModelId(), processEditor.getDrawingViewID(),
				drawingViewName, UPDATE_PROCESS_VERIABLE_SERVICE_URL, args);
		TSWebModelViewCoordinators.get(processEditor.getModelId())
				.invokeCommandAndUpdateAll(populate);
	}
	
}
