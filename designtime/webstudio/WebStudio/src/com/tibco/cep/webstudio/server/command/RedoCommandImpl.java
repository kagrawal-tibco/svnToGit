package com.tibco.cep.webstudio.server.command;

import com.tomsawyer.model.TSModel;
import com.tomsawyer.web.server.command.TSRedoCommandImpl;

/**
 * This class is used to redo the previous action.
 * 
 * @author dijadhav
 * 
 */
public class RedoCommandImpl extends TSRedoCommandImpl {

	@Override
	public boolean canUndoRedo(TSModel tsmodel) {
		return tsmodel.getCommandManager().canRedo();
	}

	@Override
	public void doUndoRedo(TSModel tsmodel) {
		tsmodel.getCommandManager().redo();
	}
}
