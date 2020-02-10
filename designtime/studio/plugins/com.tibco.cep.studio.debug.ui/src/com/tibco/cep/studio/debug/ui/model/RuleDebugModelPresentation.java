package com.tibco.cep.studio.debug.ui.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IExpression;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.ITerminate;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IWatchExpression;
import org.eclipse.debug.internal.ui.DefaultLabelProvider;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IDebugModelPresentationExtension;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariable;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;

/*
@author ssailapp
@date Jul 20, 2009 5:37:17 PM
 */

@SuppressWarnings({"rawtypes","restriction","unchecked","unused"})
public class RuleDebugModelPresentation extends LabelProvider implements IDebugModelPresentation,IDebugModelPresentationExtension,IRuleDebugPresentationProvider {
	
	public final static String DISPLAY_QUALIFIED_NAMES= "DISPLAY_QUALIFIED_NAMES"; 
	
	protected HashMap fAttributes= new HashMap(3);

	@Override
	public void computeDetail(IValue value, IValueDetailListener listener) {
		String detail = ""; 
		try {
			detail = value.getValueString();
		} catch (DebugException e) {
		}
		listener.detailComputed(value, detail);
	}

	/**
	 * @see IDebugModelPresentation#setAttribute(String, Object)
	 */
	public void setAttribute(String id, Object value) {
		if (value == null) {
			return;
		}
		synchronized (fAttributes) {
			fAttributes.put(id, value);
		}
	}

	protected boolean isShowQualifiedNames() {
		synchronized (fAttributes) {
			Boolean showQualified= (Boolean) fAttributes.get(DISPLAY_QUALIFIED_NAMES);
			showQualified= showQualified == null ? Boolean.FALSE : showQualified;
			return showQualified.booleanValue();
		}
	}

	protected boolean isShowVariableTypeNames() {
		synchronized (fAttributes) {
			Boolean show= (Boolean) fAttributes.get(DISPLAY_VARIABLE_TYPE_NAMES);
			show= show == null ? Boolean.FALSE : show;
			return show.booleanValue();
		}
	}
	
	@Override
	public String getMarkerType() {
		return IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE;
	}
	
	protected String getQualifiedName(String qualifiedName) {
		if (!isShowQualifiedNames()) {
			return removeQualifierFromGenericName(qualifiedName);
		}
		return qualifiedName;
	}

	@Override
	public String getEditorId(IEditorInput input, Object element) {
//		if (element instanceof IFile || element instanceof ILineBreakpoint) {
//			//return "org.eclipse.ui.DefaultTextEditor";
//			IPath path = input.get
//			return "com.tibco.cep.editors.ui.rulesEditor"; 
//		}
		String fileExtension = null;
		if(input instanceof FileEditorInput) {
			FileEditorInput fEditor = (FileEditorInput) input;
			IPath path = fEditor.getPath();
			if(path != null) {
				fileExtension = path.getFileExtension();
			}
		} else if (input instanceof JarEntryEditorInput) {
			try {
				fileExtension = ((JarEntryEditorInput) input).getStorage().getFullPath().getFileExtension();
			} catch (CoreException e) {
				StudioDebugUIPlugin.log(e);
			}
		}
		if(fileExtension != null) {
			if(fileExtension.equals(CommonIndexUtils.RULE_EXTENSION)) {
				return "com.tibco.cep.editors.ui.rulesEditor"; //$NON-NLS-1$
			} else if(fileExtension.equals(CommonIndexUtils.RULEFUNCTION_EXTENSION)) {
				return "com.tibco.cep.editors.ui.rulefunctionEditor"; //$NON-NLS-1$
			} else if(fileExtension.equals(CommonIndexUtils.EVENT_EXTENSION)) {
				return "com.tibco.cep.event.editors.formeditor"; //$NON-NLS-1$
			} else if(fileExtension.equals(CommonIndexUtils.STATEMACHINE_EXTENSION)) {
				return "com.tibco.cep.studio.ui.statemachine.editor"; //$NON-NLS-1$
			} else if(fileExtension.equals(CommonIndexUtils.PROCESS_EXTENSION)) {
				return "com.tibco.cep.bpmn.ui.editors.BpmnEditor"; //$NON-NLS-1$
			} else if(fileExtension.equals(CommonIndexUtils.JAVA_EXTENSION)) {
				return "org.eclipse.jdt.ui.CompilationUnitEditor"; //$NON-NLS-1$
			}
		}
		return null;
	}

	@Override
	public IEditorInput getEditorInput(Object element) {
		if(element instanceof IMarker) {
			element = getBreakpoint((IMarker)element);
		}
		if (element instanceof IRuleBreakpoint) {
			final IRuleBreakpoint rbp = (IRuleBreakpoint) element;
			element = ((IRuleBreakpoint)element).getMarker().getResource();
			if(element instanceof IProject) {
				final IProject project = (IProject) element;
				DesignerElement resourceElement = CommonIndexUtils.getElement(project.getName(), rbp.getResourceName());
				if(resourceElement instanceof SharedElement) {
					SharedElement se = (SharedElement) resourceElement;
					element = new JarEntryFile(se.getArchivePath(), se.getEntryPath()+se.getFileName(), project.getName());
				}
			} 
		}
		
		if (element instanceof IFile) {
			return new FileEditorInput((IFile)element);
		}
		if (element instanceof ILineBreakpoint) {
			return new FileEditorInput((IFile)((ILineBreakpoint)element).getMarker().getResource());
		}
		if(element instanceof JarEntryFile) {
			JarEntryFile storage = (JarEntryFile) element;
			return new JarEntryEditorInput(storage,storage.getProjectName());
		}
		return null;
	}
	
	protected IBreakpoint getBreakpoint(IMarker marker) {
		return DebugPlugin.getDefault().getBreakpointManager().getBreakpoint(
				marker);
	}

	
	public Image getImage(Object element) {
		//TODO: Debug model Image
		return null;
	}
	public List<IRuleDebugPresentationProvider> getRuleDebugPresentationProviders() throws CoreException {
		List<IRuleDebugPresentationProvider> providers = new ArrayList<IRuleDebugPresentationProvider>(); 
		IConfigurationElement[] elements= Platform.getExtensionRegistry().getConfigurationElementsFor(StudioDebugUIPlugin.PLUGIN_ID, "debugPresentationProvider"); //$NON-NLS-1$
		for (int i= 0; i < elements.length; i++) {
			IConfigurationElement element= elements[i];
			Object o = element.createExecutableExtension("provider");
			if(o instanceof IRuleDebugPresentationProvider) {
				IRuleDebugPresentationProvider provider = (IRuleDebugPresentationProvider) o; 
				providers.add(provider);
			}
		}
		return providers;
		
	}
	public String getText(Object item) {
		try {
			boolean showQualified= isShowQualifiedNames();
			if (item instanceof RuleDebugVariable) {
				return getVariableText((RuleDebugVariable) item);
			} else if (item instanceof IStackFrame) {
				StringBuffer label= new StringBuffer(getStackFrameText((IStackFrame) item));
				return label.toString();
			} else if (item instanceof IMarker) {
				IBreakpoint breakpoint = getBreakpoint((IMarker)item);
				if (breakpoint != null) {
					return getBreakpointText(breakpoint);
				}
				return null;
			} else if (item instanceof IBreakpoint) {
				return getBreakpointText((IBreakpoint)item);
			} else if (item instanceof IWatchExpression) {
				//return getWatchExpressionText((IWatchExpression)item);
			} else if (item instanceof IExpression) {
				//return getExpressionText((IExpression)item);
			} else {
				StringBuffer label= new StringBuffer();
				if (item instanceof RuleDebugThread) {
					label.append(getThreadText((RuleDebugThread) item, showQualified));
					
				} else if (item instanceof AbstractDebugTarget) {
					label.append(getDebugTargetText((AbstractDebugTarget) item));
				} else if (item instanceof IValue) {
					label.append(getValueText((IValue) item));
				}
				if (item instanceof ITerminate) {
					if (((ITerminate) item).isTerminated()) {
						label.insert(0, StudioDebugUIMessages.getString("StudioDebugUIMessages.thread_terminated"));  
						return label.toString();
					}
				}
				if (item instanceof IDisconnect) {
					if (((IDisconnect) item).isDisconnected()) {
						label.insert(0, StudioDebugUIMessages.getString("StudioDebugUIMessages.thread_disconnected"));  
						return label.toString();
					}
				}
				if (label.length() > 0) {
					return label.toString();
				}
			}
		} catch (CoreException e) {
			return StudioDebugUIMessages.getString("StudioDebugUIMessages.not_responding");  
		}
		return null;
	}
	
	/**
	 * Build the text for an IJavaDebugTarget.
	 */
	protected String getDebugTargetText(AbstractDebugTarget debugTarget) throws DebugException {
		String labelString= debugTarget.getName();
		if(debugTarget instanceof IRuleDebugTarget){
			IRuleDebugTarget rdt = (IRuleDebugTarget) debugTarget;
			if (rdt.isSuspended()) {
				labelString += StudioDebugUIMessages.getString("StudioDebugUIMessages.suspended");  
			}
		}
		return labelString;
	}
	
	protected String getBreakpointText(IBreakpoint breakpoint) {
	    try {
	    	String label = null;
	    	if(breakpoint instanceof IRuleBreakpoint) {
	    		IRuleBreakpoint rbp = (IRuleBreakpoint) breakpoint;
	    		String markerType = rbp.getMarkerType();
	    		List<IRuleDebugPresentationProvider> providers = getRuleDebugPresentationProviders();
	    		for(IRuleDebugPresentationProvider p: providers) {
	    			if(p.getMarkerType().equals(rbp.getMarkerType())){
	    				label = p.getRuleBreakpointText((IRuleBreakpoint)breakpoint);
	    				break;
	    			}
	    		}
	    		//label = getRuleBreakpointText((IRuleBreakpoint)breakpoint);
	    	} else {
				// Should never get here
				return ""; 
			}
			StringBuffer buffer = new StringBuffer();
			if (label != null) {
				buffer.append(label);
			}
			return buffer.toString();
	    } catch (CoreException e) {
	    	// if the breakpoint has been deleted, don't log exception
	    	IMarker marker = breakpoint.getMarker();
			if (marker == null || !marker.exists()) {
	    		return StudioDebugUIMessages.getString("StudioDebugUIMessages.deleted_breakpoint");  
	    	}
	        StudioDebugUIPlugin.log(e);
	        return StudioDebugUIMessages.getString("StudioDebugUIMessages.generic_exception");  
	    }
	}
	
	public String getRuleBreakpointText(IRuleBreakpoint breakpoint) throws CoreException {

		String typeName= breakpoint.getResourceName();
		StringBuffer label= new StringBuffer();
		label.append(getQualifiedName(typeName));
		appendLineNumber(breakpoint, label);
		appendThreadFilter(breakpoint, label);
		appendHitCount(breakpoint, label);
		return label.toString();
	}
	
	protected StringBuffer appendLineNumber(IRuleBreakpoint breakpoint, StringBuffer label) throws CoreException {
		int lineNumber= breakpoint.getLineNumber();
		if (lineNumber > 0) {
			label.append(" ["); 
			label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.breakpoint_line"));  
			label.append(' ');
			label.append(lineNumber);
			label.append(']');

		}
		return label;
	}
	
	protected void appendThreadFilter(IRuleBreakpoint breakpoint, StringBuffer buffer) throws CoreException {
		if (breakpoint.getThreadFilters().length != 0) {
			buffer.append(' ');
			buffer.append("[thread filtered]"); 
		}
	}
	
	protected StringBuffer appendHitCount(IRuleBreakpoint breakpoint, StringBuffer label) throws CoreException {
		int hitCount= breakpoint.getHitCount();
		if (hitCount > 0) {
			label.append(" [");  
			label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.breakpoint_hit_count"));  
			label.append(' ');
			label.append(hitCount);
			label.append(']');
		}
		return label;
	}
	
	/**
	 * Build the text for an IJavaThread.
	 */
	protected String getThreadText(RuleDebugThread thread, boolean qualified) throws CoreException {
		StringBuffer key = new StringBuffer("thread_"); 
		String[] args = null;
		IBreakpoint[] breakpoints= thread.getBreakpoints();
		if (thread.isDaemon()) {
			key.append("daemon_"); 
		}
		if (thread.isSystemThread()) {
			key.append("system_"); 
		}
		if (thread.isTerminated()) {
			key.append("terminated"); 
			args = new String[] {thread.getName()}; 
		} else if (thread.isStepping()) {
			key.append("stepping"); 
			args = new String[] {thread.getName()};
		} else if (thread.isPerformingEvaluation() && breakpoints.length == 0) {
			key.append("evaluating"); 
			args = new String[] {thread.getName()};
		} else if (!thread.isSuspended()) { 
//				|| (thread instanceof JDIThread && ((JDIThread)thread).isSuspendedQuiet())) {
			key.append("running"); 
			args = new String[] {thread.getName()};
		} else {
			key.append("suspended"); 
			if (breakpoints.length > 0) {
				IBreakpoint breakpoint= breakpoints[0];
				if(breakpoint instanceof IRuleBreakpoint) {
					IRuleBreakpoint rbp = (IRuleBreakpoint) breakpoint;
					int lineNumber= rbp.getLineNumber();
					if (lineNumber > -1) {
						args = new String[] {thread.getName(), String.valueOf(lineNumber), rbp.getResourceName()};
						key.append("_linebreakpoint");
					}
				}				
			}
	
			if (args == null) {
				// Otherwise, it's just suspended
				args =  new String[] {thread.getName()};
			}
		}
		try {
			return getFormattedString((String)StudioDebugUIMessages.getString(key.toString()), args);
		} catch (IllegalArgumentException e) {
			StudioDebugUIPlugin.log(e);
		} catch (SecurityException e) {
			StudioDebugUIPlugin.log(e);
		} 
		return StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_name"); 
	}
	
	protected String getVariableText(RuleDebugVariable var) throws DebugException {
		String varLabel= StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_name");  
		varLabel= var.getName();
		IValue varValue= null;
		try {
			varValue = var.getValue();
		} catch (DebugException e1) {
		}
		StringBuffer buff= new StringBuffer();
		String typeName= StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_type");  
		try {
			typeName= var.getReferenceTypeName();
		} catch (DebugException exception) {
		}
		buff.append(typeName);
		buff.append(' ');
		if (varLabel != null) {
			buff.append(varLabel);
		}
		if (varValue != null) {		
			String valueString= getValueText(varValue);

			//do not put the equal sign for array partitions
			if (valueString.length() != 0) {
				buff.append("= ");  
				buff.append(valueString);
			}
		}
		return buff.toString();
	}
	
	/**
	 * Build the text for an IJavaValue.
	 */
	public String getValueText(IValue value) throws DebugException {
		
		String refTypeName= value.getReferenceTypeName();
		String valueString= value.getValueString();
		boolean isString= refTypeName.equals("java.lang.String"); 
//		IJavaType type= value.getJavaType();
//		String signature= null;
//		if (type != null) {
//			signature= type.getSignature();
//		}
//		if ("V".equals(signature)) { 
//			valueString= DebugUIMessages.JDIModelPresentation__No_explicit_return_value__30; 
//		}
//		boolean isObject= isObjectValue(signature);
//		boolean isArray= value instanceof IJavaArray;
		StringBuffer buffer= new StringBuffer();
//		// Always show type name for objects & arrays (but not Strings)
//		if (isObject && !isString && (refTypeName.length() > 0)) {
//			// Don't show type name for instances and references
//			if (!(value instanceof JDIReferenceListValue || value instanceof JDIAllInstancesValue)){
//				String qualTypeName= getQualifiedName(refTypeName);
//				if (isArray) {
//					qualTypeName= adjustTypeNameForArrayIndex(qualTypeName, ((IJavaArray)value).getLength());
//				}
//				buffer.append(qualTypeName);
//				buffer.append(' ');
//			}
//		}
		
		// Put double quotes around Strings
		if (valueString != null && (isString || valueString.length() > 0)) {
			if (isString) {
				buffer.append('"');
			}
			buffer.append(DefaultLabelProvider.escapeSpecialChars(valueString));
			if (isString) {
				buffer.append('"');
//				if(value instanceof IJavaObject){
//					buffer.append(" "); 
//					buffer.append(MessageFormat.format(DebugUIMessages.JDIModelPresentation_118, new String[]{String.valueOf(((IJavaObject)value).getUniqueId())})); 
//				}
			}
			
		}
		
//		// show unsigned value second, if applicable
//		if (isShowUnsignedValues()) {
//			buffer= appendUnsignedText(value, buffer);
//		}
//		// show hex value third, if applicable
//		if (isShowHexValues()) {
//			buffer= appendHexText(value, buffer);
//		}
//		// show byte character value last, if applicable
//		if (isShowCharValues()) {
//			buffer= appendCharText(value, buffer);
//		}
		
		return buffer.toString();
	}
	
	protected String getStackFrameText(IStackFrame stackFrame) throws DebugException {
		RuleDebugStackFrame frame= (RuleDebugStackFrame) stackFrame.getAdapter(RuleDebugStackFrame.class);
		if (frame != null) {
			StringBuffer label= new StringBuffer();
			
			String dec= StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_declaring_type");  
			try {
				dec= frame.getDeclaringTypeName();
			} catch (DebugException exception) {
			}
//			if (frame.isObsolete()) {
//				label.append(DebugUIMessages.JDIModelPresentation__obsolete_method_in__1); 
//				label.append(dec);
//				label.append('>');
//				return label.toString();
//			}
			
			boolean javaStratum= true;
			try {
				javaStratum = frame.getReferenceType().defaultStratum().equals("Java"); 
			} catch (DebugException e) {
			}
			
			if (javaStratum) {
				// receiver name
				String rec= StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_recieving_type");  
				try {
					rec= frame.getReceivingTypeName();
				} catch (DebugException exception) {
				}
				label.append(getQualifiedName(rec));

				// append declaring type name if different
				if (!dec.equals(rec)) {
					label.append('(');
					label.append(getQualifiedName(dec));
					label.append(')');
				}
				// append a dot separator and method name
				label.append('.');
				try {
					label.append(frame.getMethodName());
				} catch (DebugException exception) {
					label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_method_name"));  
				}
				try {
					List args= frame.getArgumentTypeNames();
					if (args.isEmpty()) {
						label.append("()");  
					} else {
						label.append('(');
						Iterator iter= args.iterator();
						while (iter.hasNext()) {
							label.append(getQualifiedName((String) iter.next()));
							if (iter.hasNext()) {
								label.append(", ");  
							} else if (frame.isVarArgs()) {
								label.replace(label.length() - 2, label.length(), "...");  
							}
						}
						label.append(')');
					}
				} catch (DebugException exception) {
					label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_arguments"));  
				}
			} else {
//				if (isShowQualifiedNames()) {
//					label.append(frame.getSourcePath());
//				} else {
//				}
				label.append(frame.getSourceName());
			}

			try {
				int lineNumber= frame.getLineNumber();
				label.append(' ');
				label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.breakpoint_line"));  
				label.append(' ');
				if (lineNumber >= 0) {
					label.append(lineNumber);
				} else {
					label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.not_available")); 
					//TODO:check if RuleDebugFrame can show this
					if (frame.isNative()) {
						label.append(' ');
						label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.native_method"));  
					}
				}
			} catch (DebugException exception) {
				label.append(StudioDebugUIMessages.getString("StudioDebugUIMessages.unknown_line_number"));  
			}
			
//			if (!frame.wereLocalsAvailable()) {
//				label.append(' ');
//				label.append(DebugUIMessages.JDIModelPresentation_local_variables_unavailable); 
//			}
			
			return label.toString();

		}
		return null;
	}
	
	
	
	/**
	 * Return the simple generic name from a qualified generic name
	 */
	public String removeQualifierFromGenericName(String qualifiedName) {
		if (qualifiedName.endsWith("...")) {  
			// handle variable argument name
			return removeQualifierFromGenericName(qualifiedName.substring(0, qualifiedName.length() - 3)) + "...";  
		}
		if (qualifiedName.endsWith("[]")) {  
			// handle array type
			return removeQualifierFromGenericName(qualifiedName.substring(0, qualifiedName.length() - 2)) + "[]";  
		}
		// check if the type has parameters
		int parameterStart= qualifiedName.indexOf('<');
		if (parameterStart == -1) {
			return getSimpleName(qualifiedName);
		}
		// get the list of the parameters and generates their simple name
		List parameters= getNameList(qualifiedName.substring(parameterStart + 1, qualifiedName.length() - 1));
		StringBuffer name= new StringBuffer(getSimpleName(qualifiedName.substring(0, parameterStart)));
		name.append('<');
		Iterator iterator= parameters.iterator();
		if (iterator.hasNext()) {
			name.append(removeQualifierFromGenericName((String)iterator.next()));
			while (iterator.hasNext()) {
				name.append(',').append(removeQualifierFromGenericName((String)iterator.next()));
			}
		}
		name.append('>');
		return name.toString();
	}
	
	/**
	 * Return the simple name from a qualified name (non-generic)
	 */
	private String getSimpleName(String qualifiedName) {
		int index = qualifiedName.lastIndexOf('.');
		if (index >= 0) {
			return qualifiedName.substring(index + 1);
		}
		return qualifiedName;
	}

	/**
	 * Decompose a comma separated list of generic names (String) to a list of generic names (List)
	 */
	private List getNameList(String listName) {
		List names= new ArrayList();
		StringTokenizer tokenizer= new StringTokenizer(listName, ",<>", true);  
		int enclosingLevel= 0;
		int startPos= 0;
		int currentPos= 0;
		while (tokenizer.hasMoreTokens()) {
			String token= tokenizer.nextToken();
			switch (token.charAt(0)) {
				case ',':
					if (enclosingLevel == 0) {
						names.add(listName.substring(startPos, currentPos));
						startPos= currentPos + 1;
					}
					break;
				case '<':
					enclosingLevel++;
					break;
				case '>':
					enclosingLevel--;
					break;
			}
			currentPos += token.length();
		}
		names.add(listName.substring(startPos));
		return names;
	}

	/**
	 * Plug in the single argument to the resource String for the key to get a formatted resource String
	 */
	public static String getFormattedString(String key, String arg) {
		return getFormattedString(key, new String[] {arg});
	}

	/**
	 * Plug in the arguments to the resource String for the key to get a formatted resource String
	 */
	public static String getFormattedString(String string, Object[] args) {
		return MessageFormat.format(string, args);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.IDebugModelPresentationExtension#requiresUIThread(java.lang.Object)
	 */
	public synchronized boolean requiresUIThread(Object element) {
		return !isInitialized();
	}

	private boolean isInitialized() {
		return true;
	}

}
