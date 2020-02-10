package com.tibco.cep.webstudio.server.command;

import com.tomsawyer.model.TSModel;
import com.tomsawyer.web.server.command.TSUndoCommandImpl;

/**
 * This class is used to undo the previous action.
 * 
 * @author dijadhav
 * 
 */
public class UndoCommandImpl extends TSUndoCommandImpl {

	@Override
	public boolean canUndoRedo(TSModel tsmodel) {
		return tsmodel.getCommandManager().canUndo();
	}

	@Override
	public void doUndoRedo(TSModel tsmodel) {
		tsmodel.getCommandManager().undo();
	}
	
}
