package com.tibco.cep.studio.rms.ui.utils;

import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.CHECKOUT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.COMMIT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.GENERATE_DEPLOYABLE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.LOGIN_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.LOGOUT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.UPDATE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.WORKLIST_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.REVERT_ACTION;
import static com.tibco.cep.studio.ui.StudioUIPlugin.getImageDescriptor;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.client.Pinger;
import com.tibco.cep.studio.rms.client.PingerServiceManager;
import com.tibco.cep.studio.rms.client.ui.AuditTrailItem;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.StatusLineControlContribution;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.StudioUIManager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.StudioStatusLineItem;
import com.tibco.cep.studio.util.StudioConfig;

public class RMSUIUtils {

	public static boolean logged = false;
	public static boolean locked = false;
	public static boolean unlocked = false;
	public static IStatusLineManager statusLineManager = null;
	public static MenuManager manager = null;
	private static TableItem item;
	public final static String offline = "(offline)";
	
	/**
	 * Default value in seconds.
	 */
	private static final int PINGER_DELAY_DEFAULT = 10;
	
	/**
	 * Default heart bean stop time elapsed value in minutes.
	 */
	private static final int PINGER_HEARTBEAT_STOP_ELAPSEDTIME_DEFAULT = 10;
	
	// modified so that if methods of this class is called from command line then IllegalStateException will not come for work bench instance 
	
	public static void setlogged(boolean logged) {
		RMSUIUtils.logged = logged;
	}
	
	public static boolean islogged() {
		return logged;
	}
	
		
	public static boolean islocked() {
		return locked;
	}
	
	public static void setunlocked(boolean unlocked) {
		RMSUIUtils.unlocked = unlocked;
	}
	
	public static boolean isunlocked() {
		return unlocked;
	}
	
	/**
	 * @param auth
	 */
	public static void setAuthenticationStatusLineInfo(String auth) {
		((StudioStatusLineItem)statusLineManager.find("status.auth.id")).setText(auth);
	}
	
	/**
	 * @param path
	 */
	public static void setProjectPathStatusLineInfo(String path) {
		((StudioStatusLineItem)statusLineManager.find("status.project.path.id")).setText(path);
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static Image getArtifactImage(ArtifactsType type) {
		ImageDescriptor imageDescriptor = getArtifactImageDescriptor(type);
		if (imageDescriptor == null) {
			return null;
		}
		return imageDescriptor.createImage();
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static ImageDescriptor getArtifactImageDescriptor(ArtifactsType type) {
		switch(type) {
			case DECISIONTABLE:
				return getImageDescriptor("icons/decisiontablerulefunctions_16x16.png");
			case DOMAIN:
				return getImageDescriptor("icons/domainModelView_16x15.png");
			case RULE:
				return getImageDescriptor("icons/rules.png");
			case RULEFUNCTION:
				return getImageDescriptor("icons/rule-function.png");
			case RULETEMPLATE:
				return getImageDescriptor("icons/rulesTemplate.png");
			case RULETEMPLATEVIEW:
				return getImageDescriptor("icons/rulesTemplateView.png");
			case RULETEMPLATEINSTANCE:
				return getImageDescriptor("icons/rulesTemplateInstance.png");
			case CONCEPT:
			case METRIC:
				return getImageDescriptor("icons/concept.png");
			case EVENT:
				return getImageDescriptor("icons/event.png");
			case TIMEEVENT:
				return getImageDescriptor("icons/time-event.gif");
			case CHANNEL:
				return getImageDescriptor("icons/channel.png");
			case SCORECARD:
				return getImageDescriptor("icons/scorecard.png");
			case STATEMACHINE:
				return getImageDescriptor("com.tibco.cep.studio.ui.statemachine", "icons/state_machine.png");
			case RVTRANSPORT:
				return getImageDescriptor("com.tibco.cep.sharedresource", "icons/rvtransport.gif");
			case SHAREDHTTP:
				return getImageDescriptor("com.tibco.cep.sharedresource", "icons/httpconnection.gif");
			case SHAREDJMSCON:
				return getImageDescriptor("com.tibco.cep.sharedresource", "icons/jmsconfiguration.gif");
			case SHAREDJDBC:
				return getImageDescriptor("com.tibco.cep.sharedresource", "icons/jdbcconnection.gif");
			case SHAREDJNDICONFIG:
				return getImageDescriptor("com.tibco.cep.sharedresource", "icons/jndiconfiguration.gif");
			case IDENTITY:
				return getImageDescriptor("com.tibco.cep.sharedresource", "icons/identity.gif");
			case BEPROCESS:
				return getImageDescriptor("icons/appicon16x16.gif");
			case GLOBALVARIABLES:
				return getImageDescriptor("icons/global_var.png");
			case SHAREDASCON:
				return getImageDescriptor("com.tibco.cep.sharedresource.ascon", "icons/asconnection.png");
			case SHAREDSB:
				return getImageDescriptor("com.tibco.cep.studio.streambase", "icons/streambase_16x16.png");
			case CDD:
				return getImageDescriptor("com.tibco.cep.studio.ui.editors", "icons/cdd_16x16.gif");
			default:
				return getImageDescriptor("icons/file.png");
		}
	}
	
	/**
	 * @param enable
	 * @param menuName
	 */
	//enable/disable actions in RMS menu
	public static void enableDisableAction(boolean enable, String menuName) {
		Map<String, IAction> actions = StudioUIManager.getInstance().getGlobalActions();
		IAction action = actions.get(menuName);
		if (action != null) {
			action.setEnabled(enable);
		} 
	}
	
	/**
	 * 
	 * @param url
	 */
	public static void checkServerStatus(String url) {
		try {
			//Start pinger
			String pingerDelayString = StudioConfig.getInstance().getProperty("rms.heartbeat.delay", Integer.toString(PINGER_DELAY_DEFAULT));
			int pingerDelay = Integer.parseInt(pingerDelayString);
			if (pingerDelay <= 0) {
				pingerDelay = PINGER_DELAY_DEFAULT;
			}
			
			String pingerHeartbeatElapsedTimeString = StudioConfig.getInstance().getProperty("rms.heatbeat.stop.elapsedtime", Integer.toString(PINGER_HEARTBEAT_STOP_ELAPSEDTIME_DEFAULT));
			final long pingerHeartbeatElapsedTime = Long.parseLong(pingerHeartbeatElapsedTimeString) * 60000;
			
			final Pinger pinger = PingerServiceManager.getPinger(url, pingerDelay);
			pinger.start();
			
			final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
			
			//Start thread
			Runnable runnable = new Runnable() {
				public void run() {
					RMSUIPlugin.debug(RMSUIUtils.class.getName(), "Checking pinger status");
					if (!pinger.isRunning()) {
						System.out.println("Shutting down pinger status thread1");
						pinger.stop();
						executorService.shutdown();
					}
					//Get pinger status only if running
					if (pinger.isRunning() && !pinger.isConnectionSuccessful()) {
						RMSUIPlugin.debug(RMSUIUtils.class.getName(), "Connection Successful {0}", pinger.isConnectionSuccessful());
						disableAllActions();
						//Reset logged in flag
						
						logged = false;
						
						if ((new Date().getTime() - pinger.getHeartbeatStoppedElapsedTime()) > pingerHeartbeatElapsedTime){
							System.out.println("Shutting down pinger status thread2");
							pinger.stop();
							executorService.shutdown();
						}
					} else {
						RMSUIPlugin.debug(RMSUIUtils.class.getName(), "Pinger Running Status {0}", pinger.isRunning());
						if (!logged) {
							//Only enable if not already logged in
							enableDisableAction(true, LOGIN_ACTION);
						}
					}
				}
			};
			executorService.scheduleAtFixedRate(runnable, 3, pinger.getDelay(), TimeUnit.SECONDS);
		} catch (Exception e) {
			RMSUIPlugin.log(e);
		}		
	}
	
	/**
	 * Disable all menu actions atomically
	 */
	private static synchronized void disableAllActions() {
		enableDisableAction(false, LOGOUT_ACTION);
		enableDisableAction(false, LOGIN_ACTION);
		enableDisableAction(false, CHECKOUT_ACTION);
		enableDisableAction(false, COMMIT_ACTION);
		enableDisableAction(false, WORKLIST_ACTION);
		enableDisableAction(false, UPDATE_ACTION);
		enableDisableAction(false, GENERATE_DEPLOYABLE_ACTION);
		enableDisableAction(false, REVERT_ACTION);
	}
	
	public static TableEditor createTableViewer(final TableViewer viewer, 
			                                    final boolean isReadOnly, 
			                                    final boolean isCommentOnly) {
		
		final TableEditor tableEditor = new TableEditor(viewer.getTable()) {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.custom.TableEditor#layout()
			 */
			@Override
			public void layout() {
				//the text area is resized when initially selected
				if (getEditor() != null && !getEditor().isDisposed() && getEditor().isFocusControl()) {
					return;
				}
				super.layout();
			}
			
		};
		tableEditor.horizontalAlignment = SWT.LEFT;
    	tableEditor.grabHorizontal = true;
		viewer.getTable().addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				Point pt = new Point(event.x, event.y);
				item = viewer.getTable().getItem(pt);
				if (item == null) {
					return;
				}
				
				for (int i = 0; i < viewer.getTable().getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						AuditTrailItem hItem = (AuditTrailItem) item.getData();
						if (!isCommentOnly && i == 2) {
							String desc = hItem.comment.trim();
							createTextArea(item, desc, i);
						}
						if (isCommentOnly && i == 0) {
							String desc = hItem.comment.trim();
							createTextArea(item, desc, i);
						}
					}
				}
			}
			
			private void createTextArea(TableItem textItem,	final String desc, int column) {
				item = textItem;
				
				int style = SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP;
				
				style = isReadOnly ? style | SWT.READ_ONLY :style;
				
				final Text text = new Text(viewer.getTable(), style);
				
				text.setBackground(ColorConstants.tooltipBackground);
				text.setText(desc);
				tableEditor.setEditor(text, item, column);
				text.addFocusListener(new FocusListener() {
					
					public void focusLost(FocusEvent e) {
					}
				
					public void focusGained(FocusEvent e) {
						text.setSize(text.getSize().x, 75);
					}
				
				});
				Listener textListener = new Listener () {
					public void handleEvent (final org.eclipse.swt.widgets.Event e) {
						try {
						switch (e.type) {
							case SWT.FocusOut:
								setData();
								text.dispose ();
								break;
						case SWT.Traverse:
							switch (e.detail) {
								case SWT.TRAVERSE_RETURN:
									setData();
									text.dispose ();
								case SWT.TRAVERSE_ESCAPE:
									text.dispose ();
									e.doit = false;
								}
							break;
						}
						switch (e.character) {
							case SWT.CR:
								setData();
								text.dispose ();
								break;	

						}
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					
					private void setData(){
						if(!isReadOnly){
							String oldVal = item.getText();
							String newVal = text.getText();
							if(oldVal.equals(newVal)){
								//saving the comment
								item.setText(isCommentOnly ? 0 : 2, text.getText());
								AuditTrailItem data = (AuditTrailItem) item.getData();
								data.comment =  text.getText();
							}
						}
					}
				};
				text.addListener (SWT.FocusOut, textListener);
				text.addListener (SWT.Traverse, textListener);
				text.addListener (SWT.KeyDown | SWT.KeyUp, textListener);
				text.setFocus();
			}
		});
		tableEditor.grabHorizontal = true;
		
		return tableEditor;
    }
	
	public static void goOffline() {
		showUserLogged(offline);
	}
	
    /**
     * @param userName
     */
    public static void showUserLogged(String userName) {
    	StatusLineControlContribution statusLineContribution = 
    		(StatusLineControlContribution)StudioUIManager.getInstance().getGlobalContributionItems().get("com.tibco.cep.studio.rms.ui.auth.statusline");
    	statusLineContribution.setText(userName);
    }
    
    /**
     * 
     * @param artifactType
     * @return
     */
    public static ResourceType convertEnumTypes(ArtifactsType artifactType) {
    	return ResourceType.valueOf(artifactType.getName());
    }
    
	/**
	 * @param artifactPath
	 * @param contents
	 * @param projectName
	 * @param fileName
	 * @param extension
	 * @param revision
	 */
	public static void openArtifactEditor(String artifactPath, 
			                          String contents, 
			                          String projectName, 
			                          String fileName,
			                          String extension, 
			                          String revision) {
		JarEntryFile jarEntryFile = new JarEntryFile(artifactPath, artifactPath.substring(1) + "." + extension, projectName, contents, revision);
		JarEntryEditorInput input = new JarEntryEditorInput(jarEntryFile, projectName);

		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();				
		IEditorDescriptor ed = registry.getDefaultEditor(fileName);
		String editorId = ed.getId();
		try {
			//Handling Shared Resource Artifact editor, opens with default text editor 
			if (CommonUtil.getSharedResourceExtensions().contains(extension)) {
				editorId = "org.eclipse.ui.DefaultTextEditor";
			}
			IEditorPart editor = IDE.openEditor(StudioUIPlugin.getActivePage(), input, editorId);
			if (editor instanceof AbstractStudioResourceEditorPart) {
				((AbstractStudioResourceEditorPart)editor).setEnabled(false);
			}

		} catch (PartInitException e) {
			RMSUIPlugin.debug(e.getMessage());
		}
	}
}