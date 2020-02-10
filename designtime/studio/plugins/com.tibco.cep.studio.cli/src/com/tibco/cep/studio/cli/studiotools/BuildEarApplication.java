package com.tibco.cep.studio.cli.studiotools;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/*
@author ssailapp
@date Feb 16, 2010 6:11:53 PM
 */

public class BuildEarApplication implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[]) context.getArguments().get("application.args");
		BuildEarCLI.main(args);
		return EXIT_OK;
	}

	@Override
	public void stop() {
	}

}
