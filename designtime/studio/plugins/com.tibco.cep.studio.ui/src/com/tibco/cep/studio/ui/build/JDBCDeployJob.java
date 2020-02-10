package com.tibco.cep.studio.ui.build;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class JDBCDeployJob extends Job {

	private static final String NAME = "JDBC Scripts Generator";

	private static final String CONSOLE_NAME = "{0} "+NAME;

	private JDBCDeployJobManifest manifest;

	private boolean consoleActivated;

	private PrintStream out;

	private PrintStream err;

	public JDBCDeployJob(JDBCDeployJobManifest manifest) {
		super(NAME);
		this.manifest = manifest;
		setPriority(BUILD);
		setUser(true);
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		ByteArrayOutputStream simpleStream = null;
		activateConsole();
		if (consoleActivated == false) {
			simpleStream = new ByteArrayOutputStream();
			out = new PrintStream(simpleStream);
			err = new PrintStream(simpleStream);
		}
		try {
			StudioJDBCDeployment deployment = new StudioJDBCDeployment(manifest, monitor, out, err);
			deployment.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
			err.close();
			if (simpleStream != null) {
				MultiStatus multiStatus = new MultiStatus(StudioUIPlugin.PLUGIN_ID, IStatus.INFO, MessageFormat.format(CONSOLE_NAME, new Object[]{manifest.getProject().getName()}), null);
				multiStatus.add(new Status(IStatus.INFO,StudioUIPlugin.PLUGIN_ID,simpleStream.toString()));
				StudioUIPlugin.log(multiStatus);
			}
		}
		return Status.OK_STATUS;
	}

	private void activateConsole() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (activeWorkbenchWindow != null) {
					IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
					if (activePage != null) {
						try {
							IConsoleView consoleView = (IConsoleView) activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW);
							IOConsole console = createConsole();
							//out put stream
							IOConsoleOutputStream outputStream = console.newOutputStream();
							outputStream.setColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
							out = new PrintStream(outputStream);
							//err stream
							IOConsoleOutputStream errStream = console.newOutputStream();
							errStream.setColor(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
							err = new PrintStream(errStream);
							//display the console
							consoleView.display(console);
							//activate the console
							console.activate();
							consoleActivated = true;
						} catch (PartInitException e) {
							StudioUIPlugin.log("could not activate console view", e);
						}
					}
				}
			}

		});
	}

	private IOConsole createConsole() {
		IOConsole ourConsole = null;
		String consoleName = MessageFormat.format(/*CONSOLE_*/NAME, new Object[]{manifest.getProject().getName()});
		//get the console manager
		IConsoleManager consoleMgr = ConsolePlugin.getDefault().getConsoleManager();
		//get all existing console
		for (IConsole console : consoleMgr.getConsoles()) {
			if (consoleName.equals(console.getName()) == true) {
				ourConsole = (IOConsole) console;
				ourConsole.clearConsole();
				break;
			}
		}
		if (ourConsole == null) {
			ourConsole = new IOConsole(consoleName, null);
			consoleMgr.addConsoles(new IConsole[]{ourConsole});
		}
		return ourConsole;
	}

}
