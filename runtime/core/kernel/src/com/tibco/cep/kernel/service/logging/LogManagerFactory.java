package com.tibco.cep.kernel.service.logging;

import com.tibco.cep.runtime.util.PlatformUtil;


public class LogManagerFactory {


    protected static volatile LogManager logManager = makeLogManagerSingleton();


    public static LogManager getLogManager() {
        return logManager;
    }


    private static LogManager makeLogManagerSingleton() {
        try {
			if (PlatformUtil.INSTANCE.isRuntimePlatform()|| PlatformUtil.INSTANCE.isWebStudioPlatform()) {
				final Class c = Class.forName(System.getProperty(LogManager.class.getName(), "com.tibco.cep.runtime.service.logging.impl.LogManagerImpl"));
				return (LogManager) c.newInstance();
			} else {
				LogManager lm = new LogManager() {
					
					@Override
					public void setLevel(String logNamePattern, String level) {
						// TODO Auto-generated method stub						
					}
					
					@Override
					public Logger[] getLoggers() {
						return new Logger[]{getLogger()};
					}
					
					@Override
					public Logger getLogger(Class clazz) {
						return getLogger();
					}
					
					@Override
					public Logger getLogger(String name) {
						return getLogger();
					}
					
					@Override
					public void close() {						
					}
					
					public Logger getLogger() {
						return new Logger() {
							
							@Override
							public void setLevel(Level level) {
								// TODO Auto-generated method stub								
							}
							
							@Override
							public void log(Level level, Throwable thrown, String format, Object... args) {
								// TODO Auto-generated method stub								
							}
							
							@Override
							public void log(Level level, Throwable thrown, String msg) {
								// TODO Auto-generated method stub								
							}
							
							@Override
							public void log(Level level, String format, Throwable thrown, Object... args) {
								// TODO Auto-generated method stub								
							}
							
							@Override
							public void log(Level level, String format, Object... args) {
								// TODO Auto-generated method stub								
							}
							
							@Override
							public void log(Level level, String msg) {
								// TODO Auto-generated method stub								
							}
							
							@Override
							public boolean isEnabledFor(Level level) {
								return false;
							}
							
							@Override
							public String getName() {
								return this.getName();
							}
							
							@Override
							public Level getLevel() {
								return this.getLevel();
							}
							
							@Override
							public void close() {
								// TODO Auto-generated method stub								
							}
						};
					}
				};
				return lm;
			}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void setLogManager(LogManager newLogManager) {
        logManager = newLogManager;
    }



}
