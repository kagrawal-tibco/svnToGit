package com.tibco.cep.studio.debug.ui.editors;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IEditorStatusLine;

import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.DefaultProblemHandler;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.adapter.RuleToggleBreakpointAdapter;

/**
 * Job used to verify the position of a breakpoint
 */
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class BreakpointLocationVerifierJob extends Job {

	/**
	 * The document which contains the code source.
	 */
	private IDocument fDocument;
	
	/**
	 * The temporary breakpoint that has been set. Can be <code>null</code> if the callee was not able
	 * to check if a breakpoint was already set at this position.
	 */	
	private IRuleBreakpoint fBreakpoint;
	
	/**
	 * The number of the line where the breakpoint has been requested.
	 */
	private int fLineNumber;
	
	/**
	 * The qualified type name of the class where the temporary breakpoint as been set.
	 * Can be <code>null</code> if fBreakpoint is null.
	 */	
	private String fTypeName;
	
	/**
	 * The type in which should be set the breakpoint.
	 */
//	private IType fType;
	
	/**
	 * Indicate if the search for a valid location should be limited to a line
	 * or expanded to field and method declaration.
	 */
	private boolean fBestMatch;

	/**
	 * The resource in which should be set the breakpoint.
	 */
	private IResource fResource;
	
	/**
	 * The current IEditorPart
	 */
	private IEditorPart fEditorPart;
	
	/**
	 * The status line to use to display errors
	 */
	private IEditorStatusLine fStatusLine;
	
	public BreakpointLocationVerifierJob(IDocument document, IRuleBreakpoint breakpoint, int lineNumber, boolean bestMatch, String typeName, IResource resource, IEditorPart editorPart) {
		super("Breakpoint location validation"); 
		fDocument= document;
		fBreakpoint= breakpoint;
		fLineNumber= lineNumber;
		fBestMatch= bestMatch;
		fTypeName= typeName;
//		fType= type;
		fResource= resource;
		fEditorPart= editorPart;
		fStatusLine= (IEditorStatusLine) editorPart.getAdapter(IEditorStatusLine.class);
		setSystem(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IStatus run(IProgressMonitor monitor) {
		char[] source = fDocument.get().toCharArray();
		RulesASTNode ruleAST = CommonRulesParserManager.parseRuleCharArray(fResource.getProject()
				.getName(), source, new DefaultProblemHandler(), true);
		ValidBreakpointLocationLocator locator= new ValidBreakpointLocationLocator(ruleAST, fLineNumber);
		ruleAST.accept(locator);
		int lineNumber= locator.getLineLocation();		
		try {
			switch (locator.getLocationType()) {
				case ValidBreakpointLocationLocator.LOCATION_LINE:
					return manageLineBreakpoint(fTypeName, lineNumber);
				case ValidBreakpointLocationLocator.LOCATION_METHOD:
					if (fBreakpoint != null) {
						DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(fBreakpoint, true);
					}
					new RuleToggleBreakpointAdapter().toggleMethodBreakpoints(fEditorPart, new TextSelection(locator.getMemberOffset(), 0));
					break;
				case ValidBreakpointLocationLocator.LOCATION_FIELD:
					if (fBreakpoint != null) {
						DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(fBreakpoint, true);
					}
					new RuleToggleBreakpointAdapter().toggleWatchpoints(fEditorPart, new TextSelection(locator.getMemberOffset(), 0));
					break;
				default:
					// cannot find a valid location
					report("Breakpoint cannot be set at the given position",true); 
					if (fBreakpoint != null) {
						DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(fBreakpoint, true);
					}
					return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.ERROR, "Breakpoint cannot be set at the given position", null); 
			}
		} catch (CoreException e) {
			StudioDebugUIPlugin.log(e);
		}
		return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.OK, "Breakpoint set", null); 
		
	}
	
	/**
	 * Determines the placement of the line breakpoint, and ensures that duplicates are not created
	 * and that notification is sent in the event of collisions
	 * @param typeName the fully qualified name of the type to add the line breakpoint to
	 * @param lineNumber the number we wish to put the breakpoint on
	 * @return the status of the line breakpoint placement
	 */
	public IStatus manageLineBreakpoint(String typeName, int lineNumber) {
		try {
			boolean differentLineNumber= lineNumber != fLineNumber;
			IRuleBreakpoint breakpoint= RuleDebugModel.lineBreakpointExists(fResource, typeName, lineNumber);
			if(breakpoint == null) {
				breakpoint = RuleDebugModel.lineBreakpointExists(typeName, lineNumber);
			}
			boolean breakpointExist= breakpoint != null;
			if (fBreakpoint == null) {
				if (breakpointExist) {
					if (differentLineNumber) {
						// There is already a breakpoint on the valid line.
						report(MessageFormat.format("A breakpoint already exists at the next valid location -- line {0}", new Object[]{Integer.toString(lineNumber)}),true);
						return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.ERROR, "Breakpoint cannot be set at the given position", null); 
					}
					// There is already a breakpoint on the valid line, but it's also the requested line.
					// Removing the existing breakpoint.
					DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(breakpoint, true);
					return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.OK,"Breakpoint removed.", null); 
				}
				createNewBreakpoint(lineNumber, typeName);
				return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.OK, "Breakpoint set", null); 
			}
			if (differentLineNumber) {
				if (breakpointExist) {
					// there is already a breakpoint on the valid line.
					DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(fBreakpoint, true);
					report(MessageFormat.format("A breakpoint already exists at the next valid location -- line {0}", new Object[]{Integer.toString(lineNumber)}),true); 
					return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.ERROR, "Breakpoint cannot be set at the given position", null); 
				}
				replaceBreakpoint(lineNumber, typeName);
				return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.WARNING, "The breakpoint has been moved to a valid position.", null); 
			}
			if (!typeName.equals(fTypeName)) {
				replaceBreakpoint(lineNumber, typeName);
				return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.WARNING, "The breakpoint has been set to the right type.", null); 
			}
		} catch (CoreException e) {
			StudioDebugUIPlugin.log(e);
		}
		return new Status(IStatus.OK, StudioDebugUIPlugin.getUniqueIdentifier(), IStatus.OK, "Breakpoint set", null); 
	}
	
	/**
	 * Remove the temporary breakpoint and create a new breakpoint at the right position.
	 */
	private void replaceBreakpoint(int lineNumber, String typeName) throws CoreException {
		createNewBreakpoint(lineNumber, typeName);
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(fBreakpoint, true);
	}

	/**
	 * Create a new breakpoint at the right position.
	 */
	private void createNewBreakpoint(int lineNumber, String typeName) throws CoreException {
		Map newAttributes = new HashMap(10);
//		if (fType != null) {
//			try {
//				IRegion line= fDocument.getLineInformation(lineNumber - 1);
//				int start= line.getOffset();
//				int end= start + line.getLength() - 1;
//				BreakpointUtils.addJavaBreakpointAttributesWithMemberDetails(newAttributes, fType, start, end);
//			} catch (BadLocationException ble) {
//				StudioDebugUIPlugin.log(ble);
//			}
//		}
		newAttributes.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_USER);
		RuleDebugModel.createLineBreakpoint(fResource, typeName, lineNumber, -1, -1, 0, true, newAttributes);
	}

	/**
	 * Reports any status to the current active workbench shell
	 * @param message the message to display
	 */
	protected void report(final String message,final boolean autoClear) {
		StudioDebugUIPlugin.getStandardDisplay().asyncExec(new Runnable() {
			public void run() {
				if (fStatusLine != null) {
					fStatusLine.setMessage(true, message, null);
				}
				if (message != null && StudioDebugUIPlugin.getActiveWorkbenchShell() != null) {
					Display.getCurrent().beep();
				}
			}
		});
		
		if(autoClear) {
			Job clearStatusJob  = new Job("Clear status line"){
				
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					report("",false);
					return Status.OK_STATUS;
				}
			};
			clearStatusJob.schedule(30000);
		}
	}
}
