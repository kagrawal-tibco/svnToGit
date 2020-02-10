package com.tibco.cep.studio.debug.ui.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.texteditor.IMarkerUpdater;
import org.eclipse.ui.texteditor.MarkerUtilities;

import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.DefaultProblemHandler;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

public class RuleBreakpointMarkerUpdater implements IMarkerUpdater {

	public RuleBreakpointMarkerUpdater() {
		
	}

	@Override
	public String[] getAttribute() {
		return new String[] {IMarker.LINE_NUMBER};
	}

	@Override
	public String getMarkerType() {
		
		return IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE;
	}

	@Override
	public boolean updateMarker(IMarker marker, IDocument document,
			Position position) {
		
		if(position.isDeleted()) {
			return false;
		}
		IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint breakpoint = manager.getBreakpoint(marker);
		char[] source = document.get().toCharArray();
		RulesASTNode ruleAST = CommonRulesParserManager.parseRuleCharArray(
				marker.getResource().getProject().getName(), source,
				new DefaultProblemHandler(), true);
		
		if(ruleAST != null) {
			try {
				ValidBreakpointLocationLocator loc= new ValidBreakpointLocationLocator(ruleAST, document.getLineOfOffset(position.getOffset())+1);
				ruleAST.accept(loc);
				if(loc.getLocationType() == ValidBreakpointLocationLocator.LOCATION_NOT_FOUND) {
					return false;
				}
				int line = loc.getLineLocation();
				//if the line number is already good, perform no marker updating
				if(MarkerUtilities.getLineNumber(marker) == line) {
					//if there exists a breakpoint on the line remove this one
					if(isLineBreakpoint(marker)) {
						return lineBreakpointExists(marker.getResource(), ((IRuleBreakpoint)breakpoint).getTypeName(), line, marker) == null;
					}
					return true;
				}
				//if the line info is a valid location with an invalid line number,
				//a line breakpoint must be removed
				if(isLineBreakpoint(marker) && line == -1) {
					return false;
				}
				MarkerUtilities.setLineNumber(marker, line);
				return true;
			} 
			catch (BadLocationException e) {StudioDebugUIPlugin.log(e);} 
			catch (CoreException e) {StudioDebugUIPlugin.log(e);}
		}
		return false;
	}
	
	private boolean isLineBreakpoint(IMarker marker) {
		return MarkerUtilities.isMarkerType(marker, IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE);
	}
	
	/**
	 * Searches for an existing line breakpoint on the specified line in the current type that does not match the id of the specified marker
	 * @param resource the resource to care about
	 * @param typeName the name of the type the breakpoint is in
	 * @param lineNumber the number of the line the breakpoint is on
	 * @param currentmarker the current marker we are comparing to see if it will be moved onto an existing one
	 * @return an existing line breakpoint on the current line of the given resource and type if there is one
	 * @throws CoreException
	 * 
	 */
	private IRuleBreakpoint lineBreakpointExists(IResource resource, String typeName, int lineNumber, IMarker currentmarker) throws CoreException {
		String modelId = RuleDebugModel.getModelIdentifier();
		String markerType= IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE;
		IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
		for (int i = 0; i < breakpoints.length; i++) {
			if (!(breakpoints[i] instanceof IRuleBreakpoint)) {
				continue;
			}
			IRuleBreakpoint breakpoint = (IRuleBreakpoint) breakpoints[i];
			IMarker marker = breakpoint.getMarker();
			if (marker != null && marker.exists() && marker.getType().equals(markerType) && currentmarker.getId() != marker.getId()) {
				String breakpointTypeName = breakpoint.getTypeName();
				if ((breakpointTypeName.equals(typeName)) &&
					breakpoint.getLineNumber() == lineNumber &&
					resource.equals(marker.getResource())) {
						return breakpoint;
				}
			}
		}
		return null;
	}

}
