package com.tibco.cep.studio.debug.ui.adapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IEditorStatusLine;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateSimple;
import com.tibco.cep.designtime.model.element.stategraph.StateStart;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.debug.ui.editors.BreakpointLocationVerifierJob;
import com.tibco.cep.studio.debug.ui.model.BreakpointUtils;

/*
@author ssailapp
@date Jul 2, 2009 2:12:00 PM
 */
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class RuleToggleBreakpointAdapter implements IToggleBreakpointsTarget {
	public static String[] validExtensions = StudioCore.getCodeExtensions();											   
	/**
	 * The status line to use to display errors
	 */
	private IEditorStatusLine fStatusLine;

	@Override
	public boolean canToggleLineBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		ITextEditor textEditor = getEditor(part);
		fStatusLine = (IEditorStatusLine) part.getAdapter(IEditorStatusLine.class);
		if (textEditor != null) {
			ITextSelection textSelection = (ITextSelection) selection;
			int lineNumber = textSelection.getStartLine()+1;
			IResource resource = (IResource) textEditor.getEditorInput().getAdapter(IResource.class);
			DesignerProject index = null;
			String elementPath = null;
			if(resource != null) {
				elementPath = IndexUtils.getFullPath(resource);
				index = StudioCorePlugin.getDesignerModelManager().getIndex(resource.getProject());
			} else {
				IStorage storage = (IStorage) textEditor.getEditorInput().getAdapter(IStorage.class);
				IProject project = (IProject) storage.getAdapter(IProject.class);
				index = StudioCorePlugin.getDesignerModelManager().getIndex(project);
				elementPath = storage.getFullPath().removeFileExtension().makeAbsolute().toPortableString();
			}
			if(index == null) { return false; }
			
			CoreOntologyAdapter ontology = new CoreOntologyAdapter(index);
			Object entity = ontology.getEntity(elementPath);
			if(entity == null) {
				Collection<Rule> rules = ontology.getRules();
				for (Rule rule : rules) {
					if(rule.getFullPath().equals(elementPath)) {
						entity = rule;
						break;
					}
				}
			}
			if(entity == null) {
				try {
					entity = ontology.getRuleFunction(elementPath);
				} catch (Exception e) {
				}
			}
			if(entity == null) {
				return false;
			}
			if(entity instanceof Rule) {
				Rule r = (Rule) entity;
				if(textEditor.isDirty() || r.getSource() == null) {
					IDocument doc = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
					String text = doc.get();
					CodeBlock whenblock = CommonRulesParserManager.calculateOffset(RulesParser.WHEN_BLOCK, text);
					if( lineNumber >= whenblock.getStart() && lineNumber <= whenblock.getEnd()) {
						return true;
					}
					CodeBlock thenblock = CommonRulesParserManager.calculateOffset(RulesParser.THEN_BLOCK, text);
					if( lineNumber >= thenblock.getStart() && lineNumber <= thenblock.getEnd()) {
						return true;
					}
				}
				CodeBlock whenblock = r.getWhenCodeBlock();
				if( lineNumber >= whenblock.getStart() && lineNumber <= whenblock.getEnd()) {
					return true;
				}
				CodeBlock thenblock = r.getThenCodeBlock();
				if( lineNumber >= thenblock.getStart() && lineNumber <= thenblock.getEnd()) {
					return true;
				}
			} else if( entity instanceof RuleFunction) {
				RuleFunction r = (RuleFunction) entity;
				if(textEditor.isDirty() || r.getSource() == null) {
					IDocument doc = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
					String text = doc.get();
					CodeBlock bodyblock = CommonRulesParserManager.calculateOffset(RulesParser.BODY_BLOCK, text);
					if( lineNumber >= bodyblock.getStart() && lineNumber <= bodyblock.getEnd()) {
						return true;
					}
					
				}
				CodeBlock bodyblock = r.getBodyCodeBlock();
				if( lineNumber >= bodyblock.getStart() && lineNumber <= bodyblock.getEnd()) {
					return true;
				}
			} else if(entity instanceof Event) {
				Event se = (Event) entity;
				if(se.getType() == Event.SIMPLE_EVENT) {
					CodeBlock expiryblock = se.getExpiryCodeBlock();
					if( lineNumber >= expiryblock.getStart() && lineNumber <= expiryblock.getEnd()) {
						return true;
					}
				}
			} else if(entity instanceof StateComposite) {
				StateComposite ce = (StateComposite) entity;
				CodeBlock timeoutblock = ce.getTimeoutCodeBlock();
				if( lineNumber >= timeoutblock.getStart() && lineNumber <= timeoutblock.getEnd()) {
					return true;
				}
			} else if(entity instanceof State) {
				State se = (State) entity;
				if(se instanceof StateStart || se instanceof StateSimple) {
					CodeBlock entryblock = se.getEntryCodeBlock();
					if( lineNumber >= entryblock.getStart() && lineNumber <= entryblock.getEnd()) {
						return true;
					}
				}
				if( se instanceof StateEnd || se instanceof StateSimple) {
					CodeBlock exitblock = se.getExitCodeBlock();
					if( lineNumber >= exitblock.getStart() && lineNumber <= exitblock.getEnd()) {
						return true;
					}
				}
				if( se instanceof StateSimple) {
					CodeBlock timeoutblock = se.getTimeoutCodeBlock();
					if( lineNumber >= timeoutblock.getStart() && lineNumber <= timeoutblock.getEnd()) {
						return true;
					}
				}
			} 
			
		}
		report("Breakpoint cannot be set at the given position.",true);
		return false;
	}

	@Override
	public void toggleLineBreakpoints(final IWorkbenchPart part,
			final ISelection selection) throws CoreException {
		Job job = new Job("Toggle Line Breakpoint") { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor) {
				try {
					ITextEditor textEditor = getEditor(part);
					if (textEditor != null) {
						IResource resource = (IResource) textEditor
								.getEditorInput().getAdapter(IResource.class);
						String typeName = null;
						if(resource == null) {
							IStorage storage = (IStorage) textEditor.getEditorInput().getAdapter(IStorage.class);
							if(storage != null) {
								resource = (IResource) storage.getAdapter(IProject.class);
								typeName = getTypeName(storage);
							} else {
								return Status.CANCEL_STATUS;
							}
						} else {
							typeName = getTypeName(resource);
						}
						ITextSelection textSelection = (ITextSelection) selection;
						int lineNumber = ((ITextSelection) selection).getStartLine() + 1;
						
						IRuleBreakpoint existingBreakpoint = RuleDebugModel.lineBreakpointExists(typeName, lineNumber);
						if (existingBreakpoint != null) {
							DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(existingBreakpoint, true);
							return Status.OK_STATUS;
						}
//						IBreakpoint[] breakpoints = DebugPlugin.getDefault()
//								.getBreakpointManager().getBreakpoints(
//										DebuggerConstants.ID_RULE_DEBUG_MODEL);
//						for (int i = 0; i < breakpoints.length; i++) {
//							IBreakpoint breakpoint = breakpoints[i];
//							if (resource.equals(breakpoint.getMarker()
//									.getResource())) {
//								if (((ILineBreakpoint) breakpoint)
//										.getLineNumber() == (lineNumber)) {
//									breakpoint.delete();
//									return Status.OK_STATUS;
//								}
//							}
//						}
						Map attributes = new HashMap(10);
						IDocumentProvider documentProvider = textEditor
								.getDocumentProvider();
						if (documentProvider == null) {
							return Status.CANCEL_STATUS;
						}
						IDocument document = documentProvider
								.getDocument(textEditor.getEditorInput());
						try {
							IRegion line = document
									.getLineInformation(lineNumber - 1);
							int start = line.getOffset();
							int end = start + line.getLength() - 1;
//							String typeName = getTypeName(resource);
							attributes.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_USER);
							BreakpointUtils.addBreakpointAttributesWithMemberDetails(attributes, typeName, start, end);
//							if(null == RuleDebugModel.lineBreakpointExists(resource, typeName, lineNumber)){
//							}
							IRuleBreakpoint breakpoint = RuleDebugModel
							.createLineBreakpoint(resource, typeName,
									lineNumber, -1,-1, 0, true,
									attributes);
							new BreakpointLocationVerifierJob(document, breakpoint, lineNumber, true, typeName, resource, textEditor).schedule();
						} catch (BadLocationException ble) {
							StudioDebugUIPlugin.log(ble);
						}
					}
				} catch (CoreException ce) {
					return ce.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
        job.schedule();
	}
	
	public String getTypeName(IResource resource) {
		IPath resourcePath = resource.getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
		return resourcePath.toString();
	}
	
	public String getTypeName(IStorage storage) {
		return storage.getFullPath().removeFileExtension().makeAbsolute().toPortableString();
	}

	@Override
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public void toggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) throws CoreException {
	}

	@Override
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
	}
	
	private ITextEditor getEditor(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			IEditorPart editorPart = (IEditorPart) part;
			IResource resource = (IResource) editorPart.getEditorInput()
					.getAdapter(IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null) {
					if(Arrays.asList(validExtensions).contains(extension)) {
						return (ITextEditor) editorPart.getAdapter(ITextEditor.class);
					}
				}
			} else {
				IStorage storage = (IStorage) editorPart.getEditorInput().getAdapter(IStorage.class);
				if(storage != null ) {
					String extension = storage.getFullPath().getFileExtension();
					if(Arrays.asList(validExtensions).contains(extension)) {
						return (ITextEditor) editorPart.getAdapter(ITextEditor.class);
					}
				}
			}
		}
		return null;
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
