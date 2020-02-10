package com.tibco.cep.dashboard.logging;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class ConsoleLogger implements Logger {

	@Override
	public void close() {
	}

	@Override
	public Level getLevel() {
		return Level.TRACE;
	}

	@Override
	public String getName() {
		return "console";
	}

	@Override
	public boolean isEnabledFor(Level level) {
		return true;
	}

	@Override
	public void log(Level level, String msg) {
		System.out.println(level+"::"+msg);
	}

	@Override
	public void log(Level level, String format, Object... args) {
		System.out.println(level+"::"+String.format(format,args));
	}

	@Override
	public void log(Level level, String format, Throwable thrown, Object... args) {
		System.out.println(level+"::"+String.format(format,args));
		thrown.printStackTrace();
	}

	@Override
	public void log(Level level, Throwable thrown, String msg) {
		System.out.println(level+"::"+msg);
		thrown.printStackTrace();
	}

	@Override
	public void log(Level level, Throwable thrown, String format, Object... args) {
		System.out.println(level+"::"+String.format(format,args));
		thrown.printStackTrace();
	}

	@Override
	public void setLevel(Level level) {

	}

}