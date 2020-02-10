package com.tibco.cep.runtime.session.impl.locks;

import java.lang.Thread.State;

public class ThreadLockContextResolver extends LockContextResolver<Thread> 
{

	@Override
	public Thread getContext() {
		return Thread.currentThread();
	}

	@Override
	public String getName(Thread context) {
		return context.getName();
	}

	@Override
	public StackTraceElement[] getStackTrace(Thread context) {
		return context.getStackTrace();
	}

	@Override
	public State getThreadState(Thread context) {
		return context.getState();
	}

}
