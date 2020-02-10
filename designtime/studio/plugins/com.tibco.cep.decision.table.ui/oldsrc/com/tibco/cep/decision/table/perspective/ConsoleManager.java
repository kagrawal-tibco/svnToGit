package com.tibco.cep.decision.table.perspective;

 
/**
 * 
 * @author sasahoo
 *
 */
public class ConsoleManager 
{
	private static ConsoleManager consoleDefault = null;
	@SuppressWarnings("unused")
	private String title = null;
//	private MessageConsole messageConsole = null;
	
	public static final int MSG_INFORMATION = 1;
	public static final int MSG_ERROR = 2;
	public static final int MSG_WARNING = 3;
		
	public ConsoleManager(String messageTitle)
	{		
		consoleDefault = this;
		title = messageTitle;
	}
	
	public static ConsoleManager getDefault() {
		return consoleDefault;
	}	
		
	public void println(String msg, int msgKind)
	{		
		if (true) {
			return;
		}
//		if( msg == null ) return;
		
		/* if console-view in Java-perspective is not active, then show it and
		 * then display the message in the console attached to it */		
//		if( !displayConsoleView() )
//		{
//			/*If an exception occurs while displaying in the console, then just diplay atleast the same in a message-box */
//			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", msg);
//			return;
//		}
		
		/* display message on console */	
//		getNewMessageConsoleStream(msgKind).println(msg);				
	}
	
	public void clear()
	{		
//		IDocument document = getMessageConsole().getDocument();
//		if (document != null) {
//			document.set("");
//		}			
	}	
		
//	private boolean displayConsoleView()
//	{
//		try
//		{
//			IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//			if( activeWorkbenchWindow != null )
//			{
//				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
//				if( activePage != null )
//					activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW, null, IWorkbenchPage.VIEW_VISIBLE);
//			}
//			
//		} catch (PartInitException partEx) {			
//			return false;
//		}
//		
//		return true;
//	}
	
//	private MessageConsoleStream getNewMessageConsoleStream(int msgKind)
//	{		
//		int swtColorId = SWT.COLOR_BLACK;
//		
//		switch (msgKind)
//		{
//			case MSG_INFORMATION:
//				swtColorId = SWT.COLOR_BLACK;				
//				break;
//			case MSG_ERROR:
//				swtColorId = SWT.COLOR_RED;
//				break;
//			case MSG_WARNING:
//				swtColorId = SWT.COLOR_DARK_YELLOW;
//				break;
//			default:				
//		}	
//		
//		MessageConsoleStream msgConsoleStream = getMessageConsole().newMessageStream();		
//		msgConsoleStream.setColor(Display.getCurrent().getSystemColor(swtColorId));
//		return msgConsoleStream;
//	}
//	
//	private MessageConsole getMessageConsole()
//	{
//		if( messageConsole == null )
//			createMessageConsoleStream(title);	
//		
//		return messageConsole;
//	}
//		
//	private void createMessageConsoleStream(String title)
//	{
//		messageConsole = new MessageConsole(title, null); 
//		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ messageConsole });
//	}	
}

