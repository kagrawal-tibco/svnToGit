package com.tibco.cep.studio.debug.core.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.event.WatchpointEvent;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.JdiEventListener.ResultState;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession;

/*
 @author ssailapp
 @date Jul 28, 2009 5:33:15 PM
 */

public class EventDispatcher implements Runnable {
	private AbstractSession session;
	private boolean stopped = false;
	private Thread dispatcherThread;
	/**
	 * Queue of debug model events to fire, created
	 * when processing events on the target VM
	 */
	private List<DebugEvent> fDebugEvents = new ArrayList<DebugEvent>(5);

	public EventDispatcher(AbstractSession session) {
		this.session = session;
	}

	public void init() {
	}

	public AbstractSession getSession() {
		return session;
	}

	public void run() {
		dispatcherThread = Thread.currentThread();
		VirtualMachine vm = session.getVM();
		EventQueue eventQueue = vm.eventQueue();
		while (!isStopped()) {
			try {
				EventSet eventSet = null;
				try {
					// Get the next event set.
					eventSet = eventQueue.remove(1500);
				} catch (VMDisconnectedException e) {
					break;
				}
				if (!isStopped() && eventSet != null) {
					dispatch(eventSet);
				}

			} catch (InterruptedException e) {
				break;
			} catch (Exception e) {
				StudioDebugCorePlugin.log(e);
			}
		}
		StudioDebugCorePlugin.debug("Terminating event dispatcher thread\n");
	}

	public boolean isRunning() {
		if (dispatcherThread != null)
			return dispatcherThread.isAlive();
		return false;
	}

	public void stop() {
		stopped = true;
		// dispatcherThread.interrupt();
	}

	public boolean isStopped() {
		return stopped;
	}

	/*private static final ThreadReference getEventThread(Event e) {
		ThreadReference tref = null;
		if (e instanceof LocatableEvent) {
			tref = ((LocatableEvent) e).thread();
		} else if (e instanceof ClassPrepareEvent) {
			tref = ((ClassPrepareEvent) e).thread();
		} else if (e instanceof ThreadStartEvent) {
			tref = ((ThreadStartEvent) e).thread();
		} else if (e instanceof ThreadDeathEvent) {
			tref = ((ThreadDeathEvent) e).thread();
		}
		return tref;
	}*/

	public void dispatch(EventSet eventSet) {
		EventIterator itr = eventSet.eventIterator();
		boolean resume = true;
		while (itr.hasNext()) {
			if (isStopped()) {
				return;
			}
			Event event = itr.nextEvent();
			if (event == null) {
				continue;
			}
			if (isStopped())
				return;
			
			resume = dispatchEvent(eventSet, event) & resume;
			
		}
		
		fireEvents();
		
		if(resume) {
			try {
				eventSet.resume();
				StudioDebugCorePlugin.debug("Resuming eventset:" + eventSet);
			} catch (VMDisconnectedException e) {
			} catch (RuntimeException e) {
				try {
					((RuleDebugElement)session.getDebugTarget()).targetRequestFailed("EventDispatcher Unable to resume event set", e); 
				} catch (DebugException de) {
					StudioDebugCorePlugin.log(de);
				}
			}
		}
	}

	public boolean dispatchEvent(final EventSet eventSet, final Event event) {
		ResultState resume = null;
		final JdiEventListener eventListener = session.getJdiEventListener();
		boolean _resume = false;
		eventListener.onEventSet(eventSet);
		if (event instanceof ClassPrepareEvent) {
			resume = eventListener.onClassPrepare(eventSet,(ClassPrepareEvent) event);
		} else if (event instanceof ClassUnloadEvent) {
			resume = eventListener.onClassUnload(eventSet,(ClassUnloadEvent) event);
		} else if (event instanceof LocatableEvent) {
			if (event instanceof BreakpointEvent) {
				resume = eventListener.onBreakpoint(eventSet,(BreakpointEvent) event);
			} else if (event instanceof ExceptionEvent) {
				resume = eventListener.onException(eventSet,(ExceptionEvent) event);
			} else if (event instanceof StepEvent) {
				resume = eventListener.onStep(eventSet,(StepEvent) event);
			} else if (event instanceof WatchpointEvent) {
				resume = eventListener.onWatchpoint(eventSet,(WatchpointEvent) event);
			} else if (event instanceof MethodEntryEvent) {
				resume = eventListener.onMethodEntry(eventSet,(MethodEntryEvent) event);
			} else if (event instanceof MethodExitEvent) {
				resume = eventListener.onMethodExit(eventSet,(MethodExitEvent) event);
			}
		} else if (event instanceof ThreadStartEvent) {
			resume = eventListener.onThreadStart(eventSet,(ThreadStartEvent) event);
		} else if (event instanceof ThreadDeathEvent) {
			resume = eventListener.onThreadDeath(eventSet,(ThreadDeathEvent) event);
		} else if (event instanceof VMDisconnectEvent) {
			resume = eventListener.onVmDisconnect(eventSet,(VMDisconnectEvent) event);
			stop();
		} else if(event instanceof VMDeathEvent) {
			resume = eventListener.onVMDeathEvent(eventSet,(VMDeathEvent)event);
			stop();
		} else if(event instanceof VMStartEvent ) { 
			resume = eventListener.onVmStartEvent(eventSet,(VMStartEvent)event);
		} else {
			return true;
		}
		if (resume != null) {
			_resume = resume.canResume();
		}
		return _resume;
		
	}
	
	/** 
	 * Adds the given event to the queue of debug events to fire when done
	 * dispatching events from the current event set.
	 * 
	 * @param event the event to queue
	 */
	public void queue(DebugEvent event) {
		fDebugEvents.add(event);
	}
	
	/**
	 * Fires debug events in the event queue, and clears the queue
	 */
	protected void fireEvents() {
		DebugPlugin plugin= DebugPlugin.getDefault();
		if (plugin != null) { //check that not in the process of shutting down
			DebugEvent[] events = (DebugEvent[])fDebugEvents.toArray(new DebugEvent[fDebugEvents.size()]);
			fDebugEvents.clear();
			for(DebugEvent event:events){
				StudioDebugCorePlugin.debug(event.toString());
			}
			plugin.fireDebugEventSet(events);
		}
	}

}
