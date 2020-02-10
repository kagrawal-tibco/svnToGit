package com.tibco.cep.bpmn.debug;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IUpdate;
import org.osgi.framework.Bundle;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.IGraphEditor;
import com.tibco.cep.bpmn.ui.editor.IGraphInfo;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;

public class GraphToggleBreakpointAction extends Action implements IUpdate {
	private IWorkbenchPart fPart;
	private IGraphInfo fGraphInfo;
	
	/**
	 *  Anonymous class implementation for proxy {@link xorg.eclipse.debug.internal.ui.actions.IToggleBreakpointsTargetManagerListener}
	 *  using local interface {@link IToggleGraphBreakpointsTargetManagerListener}
	 */
	private IToggleGraphBreakpointsTargetManagerListener fListener = new IToggleGraphBreakpointsTargetManagerListener() {
	    public void preferredTargetsChanged() {
	        update();	        
	    }
	};
	
	/**
	 * Create the proxy listener for {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager}
	 */
	private Object fProxyListener = createToggleBreakpointsTargetManagerListener();
	
	/**
	 * get the DebugUIPlugin internal class {@link xorg.eclipse.debug.internal.ui.actions.IToggleBreakpointsTargetManagerListener}
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private Class getToggleBreakpointsTargetManagerListenerClass() throws ClassNotFoundException {
		Bundle b = Platform.getBundle("org.eclipse.debug.ui");
		return  b.loadClass("org.eclipse.debug.ui.actions.IToggleBreakpointsTargetManagerListener");
		
	}
	
	/**
	 * Get {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager} method {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager#addChangedListener(xorg.eclipse.debug.internal.ui.actions.IToggleBreakpointsTargetManagerListener)}
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Method getAddChangedListenerMethod() throws Exception {
		Bundle b = Platform.getBundle("org.eclipse.debug.ui");
		Class tbtm = b.loadClass("org.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager");
		Class lsnrClazz = b.loadClass("org.eclipse.debug.ui.actions.IToggleBreakpointsTargetManagerListener");
//		Method getDefaultMethod = tbtm.getDeclaredMethod("getDefault");
//		Object tbtmInstance = getDefaultMethod.invoke(null, (Object[])null);
		Method gtbtMethod = tbtm.getMethod("addChangedListener", lsnrClazz);
		return gtbtMethod;
	}
	
	
	/**
	 * Get {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager} method {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager#addChangedListener(xorg.eclipse.debug.internal.ui.actions.IToggleBreakpointsTargetManagerListener)}
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private Method getRemoveChangedListenerMethod() throws Exception {
		Bundle b = Platform.getBundle("org.eclipse.debug.ui");
		Class<?> tbtm = b.loadClass("org.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager");
		Class lsnrClazz = b.loadClass("org.eclipse.debug.ui.actions.IToggleBreakpointsTargetManagerListener");
//		Method getDefaultMethod = tbtm.getDeclaredMethod("getDefault");
//		Object tbtmInstance = getDefaultMethod.invoke(null, (Object[])null);
		Method gtbtMethod = tbtm.getMethod("removeChangedListener", lsnrClazz);
		return gtbtMethod;
	}

	/**
	 * Get {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager} method {@link xorg.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager#getToggleBreakpointsTarget(IWorkbenchPart, ISelection)}
	 * @param part
	 * @param selection
	 * @return
	 * @throws Exception
	 */
	private IToggleBreakpointsTarget getToggleBreakpointsTarget(IWorkbenchPart part,ISelection selection) throws Exception {
		Bundle b = Platform.getBundle("org.eclipse.debug.ui");
		Class<?> tbtm = b.loadClass("org.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager");
		Method getDefaultMethod = tbtm.getDeclaredMethod("getDefault");
		Object tbtmInstance = getDefaultMethod.invoke(null, (Object[])null);
		Method gtbtMethod = tbtm.getMethod("getToggleBreakpointsTarget", IWorkbenchPart.class,ISelection.class);
		return (IToggleBreakpointsTarget) gtbtMethod.invoke(tbtmInstance, part,selection);
	}
	
	/**
	 * Get {@link xDebugUIPlugin} instance
	 * @return
	 */
	Object getToggleBreakpointsTargetManagerInstance() {
		Class<?> tbtm;
		try {
			Bundle b = Platform.getBundle("org.eclipse.debug.ui");
			tbtm = b.loadClass("org.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager");
			Method getDefaultMethod = tbtm.getDeclaredMethod("getDefault");
			Object tbtmInstance = getDefaultMethod.invoke(null, (Object[])null);
			return tbtmInstance;
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
			return null;
		}
	}
	
	/**
	 * Add proxy listener {@link IToggleGraphBreakpointsTargetManagerListener} to {@link ToggleGraphBreakpointsTargetManager#addChangedListener(IToggleGraphBreakpointsTargetManagerListener)}
	 * @throws Exception
	 */
	private void addProxyListener() throws Exception {
		Method m = getAddChangedListenerMethod();
		if(fProxyListener != null)
			m.invoke(getToggleBreakpointsTargetManagerInstance(),fProxyListener);
	}
	
	/**
	 * Add proxy listener {@link IToggleGraphBreakpointsTargetManagerListener} to {@link ToggleGraphBreakpointsTargetManager#removeChangedListener(IToggleGraphBreakpointsTargetManagerListener)}
	 * @throws Exception
	 */
	private void removeProxyListener() throws Exception {
		Method m = getRemoveChangedListenerMethod();
		if(fProxyListener != null)
			m.invoke(getToggleBreakpointsTargetManagerInstance(),fProxyListener);
	}
	
	/**
	 * Create {@link IToggleBreakpointsTargetManagerListenerProxy}
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object createToggleBreakpointsTargetManagerListener() {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Class proxyClass = getToggleBreakpointsTargetManagerListenerClass();
			Object ip = IToggleBreakpointsTargetManagerListenerProxy.newInstance(
					map, fListener, new Class[] { proxyClass });
			return ip;
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
			return null;
		}
	}
	
	/**
	 * Dynamic Proxy class for {@link IToggleBreakpointsTargetManagerListenerProxy}
	 * @author pdhar
	 *
	 */
	public static class IToggleBreakpointsTargetManagerListenerProxy implements InvocationHandler {

		private Map<String, Object> map;
		private Object obj;

		/**
		 * Create dynamic proxy instance 
		 * @param map
		 * @param obj
		 * @param interfaces
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public static Object newInstance(Map<String, Object> map, Object obj, Class[] interfaces) {
			ClassLoader clr = BpmnUIPlugin.class.getClassLoader();
			return Proxy.newProxyInstance(clr, interfaces, new IToggleBreakpointsTargetManagerListenerProxy(map, obj));
		}

		/**
		 * constructor
		 * @param map
		 * @param obj
		 */
		public IToggleBreakpointsTargetManagerListenerProxy(Map<String, Object> map, Object obj) {
			this.map = map;
			this.obj = obj;
		}
		

		/* (non-Javadoc)
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
		 */
		public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
			try {
				Method om = obj.getClass().getMethod(m.getName(), m.getParameterTypes());
				return om.invoke(obj, args);
			} catch (Exception e) { // ignore }
//				Object result;
				String methodName = m.getName();
				if (methodName.startsWith("get")) {
					String name = methodName.substring(methodName.indexOf("get") + 3);
					return map.get(name);
				} else if (methodName.startsWith("set")) {
					String name = methodName.substring(methodName.indexOf("set") + 3);
					map.put(name, args[0]);
					return null;
				} else if (methodName.startsWith("is")) {
					String name = methodName.substring(methodName.indexOf("is") + 2);
					return (map.get(name));
				}
				return null;
			}

		}
	}

	/**
	 * Graph Toggle Breakpoint Action
	 * @param part
	 * @param ginfo
	 */
	public GraphToggleBreakpointAction(IWorkbenchPart part,IGraphInfo ginfo) {
		super("Toggle Brea&kpoint");
		fPart = part;
		fGraphInfo = ginfo;
		try {
			addProxyListener();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		//ToggleGraphBreakpointsTargetManager.getDefault().addChangedListener(fListener);

	}
	
	@Override
	public void run() {
		if (fGraphInfo == null)
			return;

		TSConstPoint point = fGraphInfo.getLocationLastMouseButtonActivity();

		// Test if line is valid
		if (point == null)
			return;

		try {
			@SuppressWarnings("unused")
			IGraphEditor gEditor = (IGraphEditor) fPart.getAdapter(IGraphEditor.class);
			ISelection selection = getGraphSelection(fGraphInfo, point);
			IToggleBreakpointsTarget toggleTarget = getToggleBreakpointsTarget(fPart, selection);
			// IToggleBreakpointsTarget toggleTarget =
			// ToggleGraphBreakpointsTargetManager.getDefault().getToggleBreakpointsTarget(fPart,
			// selection);
			if (toggleTarget == null) {
				return;
			}

			if (toggleTarget instanceof IToggleBreakpointsTargetExtension) {
				IToggleBreakpointsTargetExtension extension = (IToggleBreakpointsTargetExtension) toggleTarget;
				if (extension.canToggleBreakpoints(fPart, selection)) {
					extension.toggleBreakpoints(fPart, selection);
					return;
				}
			}
			if (toggleTarget.canToggleLineBreakpoints(fPart, selection)) {
				toggleTarget.toggleLineBreakpoints(fPart, selection);
			} else if (toggleTarget.canToggleWatchpoints(fPart, selection)) {
				toggleTarget.toggleWatchpoints(fPart, selection);
			} else if (toggleTarget.canToggleMethodBreakpoints(fPart, selection)) {
				toggleTarget.toggleMethodBreakpoints(fPart, selection);
			}
		} catch (CoreException e) {
			BpmnUIPlugin.log(e);
		} catch (BadLocationException e) {
			BpmnUIPlugin.log(e);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

	}

	private ISelection getGraphSelection(IGraphInfo gInfo, TSConstPoint point) throws BadLocationException {
		return gInfo.getSelectionAt(point);
	}
	
	@SuppressWarnings({ "unused" })
	private IToggleBreakpointsTarget getToggleBreakPointBreakpointsTarget(IWorkbenchPart part,ISelection selection) 
				throws Exception {
		Bundle b = Platform.getBundle("org.eclipse.debug.ui");
		Class<?> tbtm = b.loadClass("org.eclipse.debug.internal.ui.actions.ToggleBreakpointsTargetManager");
		Method getDefaultMethod = tbtm.getDeclaredMethod("getDefault");
		Object tbtmInstance = getDefaultMethod.invoke(null,(Object[]) null);
		Method gtbtMethod = tbtm.getMethod("getToggleBreakpointsTarget", IWorkbenchPart.class,ISelection.class);
		return (IToggleBreakpointsTarget) gtbtMethod.invoke(tbtmInstance, part,selection);
	}

	@Override
	public void update() {
		if (fGraphInfo != null) {
			TSConstPoint point = fGraphInfo.getLocationLastMouseButtonActivity();
		    if (point != null) {
		        try {
		        	
		        	@SuppressWarnings("unused")
					IGraphEditor gEditor = (IGraphEditor) fPart.getAdapter(IGraphEditor.class);
					ISelection selection = getGraphSelection(fGraphInfo, point);
						IToggleBreakpointsTarget adapter = getToggleBreakpointsTarget(fPart,selection);
						if (adapter == null) {
							setEnabled(false);
							return;
						}
						if (adapter instanceof IToggleBreakpointsTargetExtension) {
							IToggleBreakpointsTargetExtension extension = (IToggleBreakpointsTargetExtension) adapter;
							if (extension.canToggleBreakpoints(fPart, selection)) {
								setEnabled(true);
								return;
							}
						}
						if (adapter.canToggleLineBreakpoints(fPart, selection) ||
								adapter.canToggleWatchpoints(fPart, selection) ||
								adapter.canToggleMethodBreakpoints(fPart, selection)) 
						{
							setEnabled(true);
							return;
						}
//                    IToggleBreakpointsTarget adapter = 
//                        ToggleGraphBreakpointsTargetManager.getDefault().getToggleBreakpointsTarget(fPart, selection);
                } catch (BadLocationException e) {
                    reportException(e);
			    } catch (Exception e) {
			    	// TODO Auto-generated catch block
			    	e.printStackTrace();
			    }
			}
		}
		setEnabled(false);
		
		
	}
	
	/**
	 * Report an error to the user.
	 * 
	 * @param e underlying exception
	 */
	private void reportException(Exception e) {
		BpmnUIPlugin.errorDialog(fPart.getSite().getShell(), "Error:", "Unable to toggle breakpoint", e); //
	}
	
	public void dispose() {
		fPart = null;
		fGraphInfo = null;
		try {
			removeProxyListener();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
//	    ToggleGraphBreakpointsTargetManager.getDefault().removeChangedListener(fListener);
		
	}

}
