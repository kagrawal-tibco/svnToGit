package com.tibco.cep.studio.debug.core.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Field;
import com.sun.jdi.InvalidStackFrameException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.impl.IMappedResourcePosition;
import com.tibco.cep.studio.debug.core.model.var.AbstractDebugVariable;
import com.tibco.cep.studio.debug.core.model.var.IRuleDebugLocalVariable;
import com.tibco.cep.studio.debug.core.model.var.IRuleDebugThisVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugFieldVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugThisVariable;
import com.tibco.cep.studio.debug.core.model.var.RuleDebugVariableFactory;
import com.tibco.cep.studio.debug.core.model.var.ScorecardVariable;
import com.tibco.cep.studio.debug.smap.SourceMapper;

/*
@author ssailapp
@date Jul 25, 2009 2:06:16 PM
 */

/**
 * @author pdhar
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RuleDebugStackFrame extends RuleDebugElement implements IStackFrame {

	protected RuleDebugThread fThread;
	protected List<IVariable> fVariables;
	protected StackFrame fStackFrame;
	protected Map<Value,AbstractDebugVariable> varMap = new HashMap<Value,AbstractDebugVariable>();

	/**
	 * This frame's depth in the call stack (0 == bottom of stack).
	 * A new frame is indicated by -2.
	 * An invalid frame is indicated by -1.
	 */
	protected int fDepth = -2;
	protected Location fLocation;
	protected boolean fRefreshVariables;
	private ObjectReference fThisObject;
	private boolean fLocalsAvailable;
	/**
	 * The name of the type of the object that received the method call associated
	 * with this stack frame.  Cached lazily on first access.
	 */
	private String fReceivingTypeName;
	private RtcAgendaFrame fRtcAgendaFrame;
	
	public RuleDebugStackFrame(RuleDebugThread thread, StackFrame sf, int depth) {
		super(thread.getDebugTarget());
		setThread(thread);
		bind(sf, depth);
	}
	

	/**
	 * Binds this frame to the given underlying frame on the target VM
	 * or returns a new frame representing the given frame.
	 * A frame can only be re-bound to an underlying frame
	 * if it refers to the same depth on the stack in the same method.
	 * 
	 * @param frame underlying frame, or <code>null</code>
	 * @param depth depth in the call stack, or -1 to indicate the
	 *  frame should become invalid
	 *  @param return a frame to refer to the given frame or <code>null</code>
	 */
	protected RuleDebugStackFrame bind(StackFrame frame, int depth) {
		synchronized (fThread) {
			if (fDepth == -2) {
				// first initialization
				fStackFrame = frame;
				fDepth = depth;
				fLocation = frame.location();
				if(!( this instanceof RtcAgendaFrame)) {
					fRtcAgendaFrame = new RtcAgendaFrame(fThread,frame,depth);
				}
				return this;
			} else if (depth == -1) {
				// mark as invalid
				fDepth = -1;
				fStackFrame = null;
				fRtcAgendaFrame = null;
				return null;
			} else if (fDepth == depth) {
				Location location = frame.location();
				Method method = location.method();
				if (method.equals(fLocation.method())) {
					try {
						if (method.declaringType().defaultStratum().equals("Java") ||
						    equals(getSourceName(location), getSourceName(fLocation))) {
							fStackFrame = frame;
							fLocation = location;
							clearCachedData();
							if(!( this instanceof RtcAgendaFrame)) {
								if(fRtcAgendaFrame == null) {
									setRtcAgendaFrame(new RtcAgendaFrame(fThread,frame,depth));
								} else {
									fRtcAgendaFrame.bind(frame, depth);
								}
							}
							return this;
						}
					} catch (DebugException e) {
					}
				}
			}
			// invalidate this frame
			bind(null, -1);
			// return a new frame
			return new RuleDebugStackFrame(fThread, frame, depth);
		}

	}
	
	/**
	 * Clears the cached data of this stack frame.
	 * The underlying stack frame has changed in such a way
	 * that the cached data may not be valid.
	 */
	private void clearCachedData() {
		fThisObject= null;
		fReceivingTypeName= null;	
	}
	
	private boolean equals(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		} else {
			return o1.equals(o2);
		}
	}
	

	
	protected List<IVariable> getVariables0() throws DebugException {
		synchronized (fThread) {
			if (fVariables == null) {
				// throw exception if native method, so variable view will update
				// with information message
				if (getUnderlyingMethod().isNative()) {
//					requestFailed("Variable information unavailable for native methods", null); 
					return new ArrayList<IVariable>();
				}
				Method method= getUnderlyingMethod();
				fVariables= new ArrayList<IVariable>();	
				// #isStatic() does not claim to throw any exceptions - so it is not try/catch coded
				if (method.isStatic()) {
					// add statics
					List allFields= null;
					ReferenceType declaringType = method.declaringType();
					try {
						allFields= declaringType.allFields();
					} catch (RuntimeException e) {
						targetRequestFailed(MessageFormat.format("Exception retrieving fields {0}",(Object)new String[] {e.toString()}), e); 
						// execution will not reach this line, as 
						// #targetRequestFailed will throw an exception					
						return Collections.EMPTY_LIST;
					}
					if (allFields != null) {
						Iterator fields= allFields.iterator();
						while (fields.hasNext()) {
							Field field= (Field) fields.next();
							StudioDebugCorePlugin.debug("Getting field:"+field.name());
							if (field.isStatic()) {
								if(!getVarMap().containsKey(declaringType.getValue(field))) {
									fVariables.add(new RuleDebugFieldVariable(this,fThread,field.name(), field, declaringType));
								}
							}
						}
						Collections.sort(fVariables, new Comparator<Object>() {
							public int compare(Object a, Object b) {
								RuleDebugFieldVariable v1= (RuleDebugFieldVariable)a;
								RuleDebugFieldVariable v2= (RuleDebugFieldVariable)b;
								return v1.getName().compareToIgnoreCase(v2.getName());
							}
						});
					}
				} else {
					// add "this"
					ObjectReference t= getUnderlyingThisObject();
					if (t != null) {
						if(!getVarMap().containsKey(t)) {
							StudioDebugCorePlugin.debug("Getting this object");
							RuleDebugThisVariable thisVar = new RuleDebugThisVariable(this,fThread,"this",t);
//							thisVar.init();
							fVariables.add(thisVar);
						}
					}
				}
				fVariables.addAll(RuleDebugVariableFactory.getScorecardVariables(this, fThread));
				// add locals
				Iterator<LocalVariable> variables= getUnderlyingVisibleVariables().iterator();
				while (variables.hasNext()) {
					LocalVariable var= variables.next();
					//Value jdiValue = null;
					//jdiValue = getUnderlyingStackFrame().getValue(var);
					StudioDebugCorePlugin.debug("Getting local variable:"+var.name());
					AbstractDebugVariable rdv = RuleDebugVariableFactory.getVarRow(this,fThread,var);
					fVariables.add(rdv);
					
				}
				
			} else if (fRefreshVariables) {
				updateVariables();
			}
			fRefreshVariables = false;
			return fVariables;
		}
	}
	
	/**
	 * Incrementally updates this stack frames variables.
	 * 
	 * @see JDIDebugElement#targetRequestFailed(String, RuntimeException)
	 */
	protected void updateVariables() throws DebugException {
		if (fVariables == null) {
			return;
		}
		Method method= getUnderlyingMethod();
		int index= 0;
		if (!method.isStatic()) {
			// update "this"
			ObjectReference thisObject;
			thisObject= getUnderlyingThisObject();
			IRuleDebugThisVariable oldThisObject= null;
			if (!fVariables.isEmpty() && fVariables.get(0) instanceof IRuleDebugThisVariable) {
				oldThisObject= (IRuleDebugThisVariable) fVariables.get(0);
			}
			if (thisObject == null && oldThisObject != null) {
				// removal of 'this'
				fVariables.remove(0);
				index= 0;
			} else {
				if (oldThisObject == null && thisObject != null) {
					// creation of 'this'
					RuleDebugThisVariable thisVar = new RuleDebugThisVariable(this,fThread,"this",thisObject);
					oldThisObject= thisVar;
					fVariables.add(0, oldThisObject);
					index= 1;
				} else {
					if (oldThisObject != null) {
						// 'this' still exists, replace with new 'this' if a different receiver
						if (!oldThisObject.retrieveValue().equals(thisObject)) {
							getVarMap().remove(oldThisObject);
							fVariables.remove(0);
							fVariables.add(0, new RuleDebugThisVariable(this,fThread,"this",thisObject));
						}
						index= 1;
					}
				}
			}
		}
		for (IVariable var: fVariables) {
			if(var instanceof ScorecardVariable) {
				((ScorecardVariable) var).clearChildren();
				index++;
			}
		}
		List<LocalVariable> locals= null;
		try {
			locals= getUnderlyingStackFrame().visibleVariables();
		} catch (AbsentInformationException e) {
			locals= Collections.EMPTY_LIST;
		} catch (NativeMethodException e) {
			locals= Collections.EMPTY_LIST;
		} catch (InvalidStackFrameException e) {
			locals= Collections.EMPTY_LIST;
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving visible variables.",new Object[] {e.toString()}), e); 
			// execution will not reach this line, as 
			// #targetRequestFailed will throw an exception			
			return;
		}
		
		int localIndex= -1;
		while (index < fVariables.size()) {
			Object var= fVariables.get(index);
			if (var instanceof IRuleDebugLocalVariable) {
				IRuleDebugLocalVariable local= (IRuleDebugLocalVariable) fVariables.get(index);
				localIndex= locals.indexOf(local.getLocal());
				if (localIndex >= 0) {
					// update variable with new underling JDI LocalVariable
					local.setLocal((LocalVariable) locals.get(localIndex));
					locals.remove(localIndex);
					index++;
				} else {
					if(!(var instanceof ScorecardVariable)){
						// remove variable
						fVariables.remove(index);
					}
				}
			} else {
				//field variable of a static frame
				index++;
			}
		}
		// add any new locals
		Iterator<LocalVariable> newOnes= locals.iterator();
		while (newOnes.hasNext()) {
			LocalVariable var= newOnes.next();
			//Value jdiValue = getUnderlyingStackFrame().getValue(var);
			AbstractDebugVariable rdv = RuleDebugVariableFactory.getVarRow(this, fThread, var);
			fVariables.add(rdv);
		}
		

	}
	
	
	
	
	/**
	 * Returns the underlying method associated with this stack frame,
	 * retrieving the method is necessary.
	 */
	public Method getUnderlyingMethod() {
		synchronized (fThread) {
			return fLocation.method();
		}
	}
	
	/**
	 * Returns the source from the default stratum of the given location
	 * or <code>null</code> if not available (missing attribute).
	 */
	private String getSourceName(Location location) throws DebugException {
		try {
			AbstractDebugTarget target  = ((AbstractDebugTarget)getDebugTarget());
			List<ISourceInfoProvider> providers = target.getSourceInfoProviders();
			IPath path = null;
			for(ISourceInfoProvider provider:providers) {
				path = provider.getSourcePath(this);
				if(path != null) {
					return path.makeAbsolute().toPortableString();
				}
			}
			if(path == null) {
				String javaSourceName = getDeclaringTypeName();
				path =  new Path(javaSourceName);
				return path.makeAbsolute().toPortableString();
			} 
//	        IPath path = getMappedSourcePath(location);
//	        if(path != null) {
//	        	return path.makeRelative().toPortableString();
//	        }
		} catch (NativeMethodException e) {
			return null;
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving source name debug attribute.", new Object[] {e.toString()}), e); 
		}
		return null;
	}
	
	/**
	 * Returns the line from the default stratum of the given location
	 * or <code>null</code> if not available (missing attribute).
	 */
	private int getSourceLine() throws DebugException {
		try {
			AbstractDebugTarget target  = ((AbstractDebugTarget)getDebugTarget());
//			String javaSourceName = getDeclaringTypeName();
			List<ISourceInfoProvider> providers = target.getSourceInfoProviders();
			IResourcePosition position = null;
			for(ISourceInfoProvider provider:providers) {
				position = provider.getResourcePosition(this);
				if(position != null) {
					return position.getLineNumber();
				}
			}
//			IDebugTarget target = getDebugTarget();
//			String javaSourceName = getDeclaringTypeName();
//			SourceMapper mapper = ((AbstractDebugTarget)target).getSourceMapper();
//			String resourceName = mapper.getEntityName(javaSourceName);
//			if(resourceName != null) {
//				MappedResourcePosition pos = mapper.getBEPosition(javaSourceName, fLocation.lineNumber());
//				if(pos != null) {
//					return pos.getLineNumber();
//				}
//			}
			return fLocation.lineNumber();			
		}
		catch (NativeMethodException e) {
			return -1;
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving source name debug attribute.", new Object[] {e.toString()}), e); 
		}
		return -1;
	}
	
	public IPath getMappedSourcePath() throws DebugException {
		return getMappedSourcePath(fLocation);
	}
	
	
	
	private IPath getMappedSourcePath(Location location) throws DebugException {
		try {
			IDebugTarget target = getDebugTarget();
			String javaSourceName = getDeclaringTypeName();
			SourceMapper mapper = ((AbstractDebugTarget)target).getSourceMapper();
			String resourceName =  mapper.getEntityName(javaSourceName);
			if(resourceName == null) {
				return new Path(javaSourceName);
			}
			return ((AbstractDebugTarget)target).getEntityResourcePath(resourceName);
		} catch (NativeMethodException e) {
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format(
					"{0} occurred retrieving source path debug attribute.",
					new Object[] { e.toString() }), e);
		}
		return null;
	}


	public int getMappedSourceLine() throws DebugException {
		return getMappedSourceLine(fLocation);
	}
	
	private int getMappedSourceLine(Location location) throws DebugException {
		try {
			IDebugTarget target = getDebugTarget();
			String javaSourceName = getDeclaringTypeName();
			SourceMapper mapper = ((AbstractDebugTarget)target).getSourceMapper();
			String resourceName = mapper.getEntityName(javaSourceName);
			if (resourceName != null) {
				IMappedResourcePosition pos = mapper.getBEPosition(
						javaSourceName, location.lineNumber());
				if (pos != null) {
					return pos.getLineNumber();
				}
			}
		} catch (NativeMethodException e) {
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format(
					"{0} occurred retrieving source name debug attribute.",
					new Object[] { e.toString() }), e);
		}
		return -1;		
	}
	
	
	
	/**
	 * @see IJavaStackFrame#getDeclaringTypeName()
	 */
	public String getDeclaringTypeName() throws DebugException {
		synchronized (fThread) {
			try {
				if (isObsolete()) {
					return  "<unknown declaring type>"; 
				}
				return getUnderlyingMethod().declaringType().name();
			} catch (RuntimeException e) {
				if (getThread().isSuspended()) {
					targetRequestFailed(MessageFormat.format("{0} occured retrieving declaring type", new Object[] {e.toString()}), e); 
				}
				return "unknown"; 
			}
		}
	}
	
	/**
	 * Retrieves visible variables in this stack frame
	 * handling any exceptions. Returns an empty list if there are no
	 * variables.
	 * 
	 * @see JDIDebugElement#targetRequestFailed(String, RuntimeException)
	 */
	protected List<LocalVariable> getUnderlyingVisibleVariables() throws DebugException {
		synchronized (fThread) {
			List variables= Collections.EMPTY_LIST;
			try {
				variables= getUnderlyingStackFrame().visibleVariables();
			} catch (AbsentInformationException e) {
				setLocalsAvailable(false);
			} catch (NativeMethodException e) {
				setLocalsAvailable(false);
			} catch (RuntimeException e) {
				targetRequestFailed(MessageFormat.format("{0} occurred retrieving visible variables.",new Object[] {e.toString()}), e); 
			}
			return variables;
		}
	}

	/**
	 * Retrieves 'this' from the underlying stack frame.
	 * Returns <code>null</code> for static stack frames.
	 * 
	 * @see JDIDebugElement#targetRequestFailed(String, RuntimeException)
	 */
	public ObjectReference getUnderlyingThisObject() throws DebugException {
		synchronized (fThread) {
			if ((fStackFrame == null || fThisObject == null) && !isStatic()) {
				try {
					fThisObject = getUnderlyingStackFrame().thisObject();
				} catch(InvalidStackFrameException e){ 
					requestFailed("Invalid Stack Frame", e, IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME);
				} catch (RuntimeException e) {
					targetRequestFailed(MessageFormat.format("{0} occurred retrieving ''this'' from stack frame.",new Object[] {e.toString()}), e); 
					// execution will not reach this line, as 
					// #targetRequestFailed will throw an exception			
					return null;
				}
			}
			return fThisObject;
		}
	}
	
	public boolean isStatic() {
		return getUnderlyingMethod().isStatic();
	}
	
	/**
	 * Returns this stack frame's underlying JDI frame.
	 * 
	 * @exception DebugException if this stack frame does
	 *  not currently have an underlying frame (is in an
	 *  interim state where this frame's thread has been
	 *  resumed, and is not yet suspended).
	 */
	public StackFrame getUnderlyingStackFrame() throws DebugException {
		synchronized (fThread) {
			if (fStackFrame == null) {
				if (fDepth == -1) {
					//throw new DebugException(new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), -1, "Invalid stack frame", null));
					requestFailed("Invalid Stack Frame", null, IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME);
				}
				if (fThread.isSuspended()) {
					// re-index stack frames - See Bug 47198
					fThread.computeStackFrames();
					if (fDepth == -1) {
						// If depth is -1, then this is an invalid frame
						//throw new DebugException(new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), -1, "Invalid stack frame", null));
						requestFailed("Invalid Stack Frame", null, IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME);
					}
				} else {
					//throw new DebugException(new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), -1, "Invalid stack frame", null));
					requestFailed("Invalid Stack Frame", null, IRuleDebugThread.STACK_FRAME_INVALID_ON_THREAD_RESUME);
				}
			}
			return fStackFrame;
		}
	}
	
	/**
	 * Sets the underlying JDI StackFrame. Called by a thread
	 * when incrementally updating after a step has completed.
	 * 
	 * @param frame The underlying stack frame
	 */
	protected void setUnderlyingStackFrame(StackFrame frame) {
		synchronized (fThread) {
			fStackFrame = frame;
			if (frame == null) {
				fRefreshVariables = true;
			}
		}
	}
	
	public boolean wereLocalsAvailable() {
		return fLocalsAvailable;
	}
	
	/**
	 * Sets whether locals were available. If the setting is
	 * not the same as the current value, a change event is
	 * fired such that a UI client can update.
	 * 
	 * @param available whether local variable information is
	 * 	available for this stack frame.
	 */
	private void setLocalsAvailable(boolean available) {
		if (available != fLocalsAvailable) {
			fLocalsAvailable = available;
			fireChangeEvent(DebugEvent.STATE);
		}
	}

	@Override
	public int getCharEnd() throws DebugException {
		return -1;
	}

	@Override
	public int getCharStart() throws DebugException {
		return -1;
	}
	
	public String getMethodName() throws DebugException {
		try {
			return getUnderlyingMethod().name();	
		} catch (RuntimeException e) {
			if (getThread().isSuspended()) {
				targetRequestFailed(MessageFormat.format("{0} occurred retrieving method name.", new Object[] {e.toString()}), e); 
			}
			return "<unknown method>"; 
		}
	}

	@Override
	public int getLineNumber() throws DebugException {
		synchronized (fThread) {
			try {
				return getSourceLine(); 
			} catch (RuntimeException e) {
				if (getThread().isSuspended()) {
					targetRequestFailed(MessageFormat.format("{0} occurred retrieving line number.", new Object[] {e.toString()}), e); 
				}
			}
		}
		return -1;
	}

	@Override
	public String getName() throws DebugException {
		StringBuilder builder = new StringBuilder();
		builder.append(getMethodName())
				.append("(")
				.append(getSourceName())
				.append(":")
				.append(getLineNumber())
				.append(")");
		return builder.toString();
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return null;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}
	
	@Override
	public IThread getThread() {
		return fThread;
	}
	
	public void setThread(RuleDebugThread thread) {
		fThread = thread;
	}
	

	@Override
	public IVariable[] getVariables() throws DebugException {
		if(getThread().isSuspended() && !getThread().isTerminated()) {
			List<IVariable> list = getVariables0();
			return (IVariable[])list.toArray(new IVariable[list.size()]);
		} else {
			return new IVariable[0];
		}
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return getVariables0().size() > 0;
	}
	
	protected boolean isTopStackFrame() throws DebugException {
		IStackFrame tos = getThread().getTopStackFrame();
		return tos != null && tos.equals(this);
	}

	@Override
	public boolean canStepInto() {
		try {
			return exists() && isTopStackFrame() && !isObsolete() && getThread().canStepInto();
		} catch (DebugException e) {
			logError(e);
			return false;
		}
	}

	@Override
	public boolean canStepOver() {
		return exists() && !isObsolete() && getThread().canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		try {
			if (!exists() || isObsolete() || !getThread().canStepReturn()) {
				return false;
			}
			List frames = ((RuleDebugThread)getThread()).computeStackFrames();
			if (frames != null && !frames.isEmpty()) {
				boolean bottomFrame = this.equals(frames.get(frames.size() - 1));
				boolean aboveObsoleteFrame= false;
				if (!bottomFrame) {
					int index= frames.indexOf(this);
					if (index < frames.size() -1 && ((RuleDebugStackFrame)frames.get(index + 1)).isObsolete()) {
						aboveObsoleteFrame= true;
					}
				}
				return !bottomFrame && !aboveObsoleteFrame;
			}
		} catch (DebugException e) {
			logError(e);
		}
		return false;
	}

	@Override
	public boolean isStepping() {
		return getThread().isStepping();
	}

	@Override
	public void stepInto() throws DebugException {
		getThread().stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		getThread().stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		getThread().stepReturn();
	}

	@Override
	public boolean canResume() {
		return getThread().canResume();
	}

	@Override
	public boolean canSuspend() {
		return getThread().canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return getThread().isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		getThread().resume();
	}

	@Override
	public void suspend() throws DebugException {
		getThread().suspend();
	}

	@Override
	public boolean canTerminate() {
		return exists() && getThread().canTerminate() || getDebugTarget().canTerminate();
		//return getThread().canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return getThread().isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		getThread().terminate();
	}

	public String getSourceName() throws DebugException {
		synchronized (fThread) {
			return getSourceName(fLocation); 
		}
	}
	
	public boolean isObsolete() {
		if (!DebuggerSupport.isJdiVersionGreaterThanOrEqual(new int[] {1,4})) {
			// If no hot code replace has occurred, this frame
			// cannot be obsolete.
			return false;
		}
		// if this frame's thread is not suspended, the obsolete status cannot
		// change until it suspends again
		synchronized (fThread) {
			if (getThread().isSuspended()) {
				return getUnderlyingMethod().isObsolete();
			}
			return false;
		}
	}
	
	public boolean exists() {
		synchronized (fThread) {
			return fDepth != -1;
		}
	}
	
	/*public boolean equals(Object obj) {
		if (obj instanceof RuleDebugStackFrame) {
			RuleDebugStackFrame sf = (RuleDebugStackFrame)obj;
			try {
				if(sf.getSourceName() == null) {
					return false;
				}
				return sf.getSourceName().equals(getSourceName()) &&
					sf.getLineNumber() == getLineNumber() &&
					sf.fDepth == fDepth;
			} catch (DebugException e) {
			}
		}
		return false;
	}*/
	
	public int hashCode() {
		try {
			if(getSourceName() != null) {
				return getSourceName().hashCode() + fDepth;
			}
			return "Unknown Source".hashCode() + fDepth;
		} catch (DebugException e) {
			return "Unknown Source".hashCode() + fDepth;
		}
	}
	
	/**
	 * Returns this stack frame's unique identifier within its thread
	 * 
	 * @return this stack frame's unique identifier within its thread
	 */
	public int getIdentifier() {
		return fDepth;
	}
	
	/**
	 * 
	 * @return
	 */
	public StackFrame getFrame() {
		return fStackFrame;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == RuleDebugStackFrame.class) {
			return this;
		} else if(adapter == Location.class) {
			return fLocation;
		}
		return super.getAdapter(adapter);
	}
	
	/**
	 * @see IJavaStackFrame#isNative()
	 */
	public boolean isNative() throws DebugException {
		return getUnderlyingMethod().isNative();
	}
	

	/**
	 * @return
	 * @throws DebugException
	 */
	public boolean isVarArgs() throws DebugException {
		return getUnderlyingMethod().isVarArgs();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaStackFrame#getReferenceType()
	 */
	public ReferenceType getReferenceType() throws DebugException {
		Method method = getUnderlyingMethod();
		try {
			return method.declaringType();
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("Exception retreiving declaring type {0}",(Object) new String[] {e.toString()}), e); 
		}
		return null;
	}
	
	public String getReceivingTypeName() throws DebugException {
		if (fStackFrame == null || fReceivingTypeName == null) {
			try {
				if (isObsolete()) {
					fReceivingTypeName="<unknown receiving type>"; 
				} else {
					
					ObjectReference thisObject = getUnderlyingThisObject();
					if (thisObject == null) {
						fReceivingTypeName = getDeclaringTypeName();
					} else {
						fReceivingTypeName = thisObject.referenceType().name();
					}
				}
			} catch (RuntimeException e) {
				if (getThread().isSuspended()) {
					targetRequestFailed(MessageFormat.format("Exception retrieving receiving type {0}", new Object[] {e.toString()}), e); 
				}
				return "<unknown recieving type>"; 
			}
		}
		return fReceivingTypeName;
	}
	
	public List getArgumentTypeNames() throws DebugException {
		try {
			Method underlyingMethod= getUnderlyingMethod();
			String genericSignature= underlyingMethod.genericSignature();
			if (genericSignature == null) {
				// no generic signature
				return underlyingMethod.argumentTypeNames();
			}
			// generic signature
			String[] parameterTypes= JavaSignature.getParameterTypes(genericSignature);
			List argumentTypeNames= new ArrayList();
			for (int i= 0; i < parameterTypes.length; i++) {
				argumentTypeNames.add(JavaSignature.toString(parameterTypes[i]).replace('/', '.'));
			}
			return argumentTypeNames;
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("Exception retrieving argument type names {0}", (Object)new String[] {e.toString()}), e); 
			// execution will never reach this line, as
			// #targetRequestFailed will throw an exception			
			return null;
		}
	}


	public Map<Value, AbstractDebugVariable> getVarMap() {
		return this.varMap;
	}
	
	/**
	 * @return the fRtcAgendaFrame
	 */
	public RtcAgendaFrame getRtcAgendaFrame() {
		return fRtcAgendaFrame;
	}

	/**
	 * @param rtcAgendaFrame the fRtcAgendaFrame to set
	 */
	public void setRtcAgendaFrame(RtcAgendaFrame rtcAgendaFrame) {
		fRtcAgendaFrame = rtcAgendaFrame;
	}
	
	public String toString() {
		if(fThread != null) {
			synchronized (fThread) {
				try {
					return getName();
				} catch (DebugException e) {
					return super.toString();
				}
			}
		}
		return super.toString();
	}

}
