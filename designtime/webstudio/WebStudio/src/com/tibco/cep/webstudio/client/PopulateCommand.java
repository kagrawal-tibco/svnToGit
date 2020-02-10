package com.tibco.cep.webstudio.client;

import java.util.List;

import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebViewClient;


/**
 * 
 * @author ggrigore
 * 
 */
public class PopulateCommand extends TSCustomCommand {
	public PopulateCommand()
	{
	}

	public PopulateCommand(TSWebViewClient view, List args) {
		super(view, "basic.server.PopulateCommandImpl", args);
	}
}
