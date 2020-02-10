package com.tibco.cep.diagramming.utils;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.Timer;

import org.eclipse.swt.SWT;

public class SyncXErrorHandler {

	private boolean x11ErrorHandlerFixInstalled;

	public SyncXErrorHandler() {
		x11ErrorHandlerFixInstalled = false;
	}
	
	public void installHandler() {
		if( !x11ErrorHandlerFixInstalled && "gtk".equals( SWT.getPlatform() ) ) {
			x11ErrorHandlerFixInstalled = true;
			EventQueue.invokeLater( new Runnable() {
				public void run() {
					initX11ErrorHandlerFix();
				}
			} );
		}
	}
	
	@SuppressWarnings({ "all" })
	private void initX11ErrorHandlerFix() {
		assert EventQueue.isDispatchThread();

		try {
			// get XlibWrapper.SetToolkitErrorHandler() and XSetErrorHandler() methods
			Class xlibwrapperClass = Class.forName( "sun.awt.X11.XlibWrapper" );
			final Method setToolkitErrorHandlerMethod = xlibwrapperClass.getDeclaredMethod( "SetToolkitErrorHandler", null );
			final Method setErrorHandlerMethod = xlibwrapperClass.getDeclaredMethod( "XSetErrorHandler", new Class[] { Long.TYPE } );
			setToolkitErrorHandlerMethod.setAccessible( true );
			setErrorHandlerMethod.setAccessible( true );

			// get XToolkit.saved_error_handler field
			Class xtoolkitClass = Class.forName( "sun.awt.X11.XToolkit" );
			final Field savedErrorHandlerField = xtoolkitClass.getDeclaredField( "saved_error_handler" );
			savedErrorHandlerField.setAccessible( true );

			// determine the current error handler and the value of XLibWrapper.ToolkitErrorHandler
			// (XlibWrapper.SetToolkitErrorHandler() sets the X11 error handler to
			// XLibWrapper.ToolkitErrorHandler and returns the old error handler)
			final Object defaultErrorHandler = setToolkitErrorHandlerMethod.invoke( null, null );
			final Object toolkitErrorHandler = setToolkitErrorHandlerMethod.invoke( null, null );
			setErrorHandlerMethod.invoke( null, new Object[] { defaultErrorHandler } );

			// create timer that watches XToolkit.saved_error_handler whether its value is equal
			// to XLibWrapper.ToolkitErrorHandler, which indicates the start of the trouble
			Timer timer = new Timer( 200, new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					try {
						Object savedErrorHandler = savedErrorHandlerField.get( null );
						if( toolkitErrorHandler.equals( savedErrorHandler ) ) {
							// Last saved error handler in XToolkit.WITH_XERROR_HANDLER
							// is XLibWrapper.ToolkitErrorHandler, which will cause
							// the StackOverflowError when the next X11 error occurs.
							// Workaround: restore the default error handler.
							// Also update XToolkit.saved_error_handler so that
							// this is done only once.
							setErrorHandlerMethod.invoke( null, new Object[] { defaultErrorHandler } );
							savedErrorHandlerField.setLong( null, ((Long)defaultErrorHandler).longValue() );
						}
					} catch( Exception ex ) {
						// ignore
					}
					
				}
			} );
			timer.start();
		} catch( Exception ex ) {
			// ignore
		}
	}
	
}
