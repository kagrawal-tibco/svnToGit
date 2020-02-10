package com.tibco.cep.studio.ui.overview;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.diagramming.AbstractOverview;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;

/**
 * This is a CommonView switches Table/Drawing overview component 
 * @author sasahoo
 *
 */
public class CommonOverview extends AbstractOverview {

	public static final String ID = "com.tibco.cep.studio.ui.overview.commonoverview";
	protected Composite swtAwtComponent;
	protected Overview overview;
	
	protected java.awt.Frame frame = null;
	protected IGraphDrawing editor;
	protected Object diagramManager;
	
	public void clear() {
		this.diagramManager = null;
		this.overviewComponent = null;
	}

	private OverviewListener overviewListener;
	public TSEOverviewComponent overviewComponent;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
        swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
        SwtUtilities.initSwing();
        new SyncXErrorHandler().installHandler();
        SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
		        overview = new Overview();
		        Display.getDefault().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						if (frame == null) {
							frame = SWT_AWT.new_Frame(swtAwtComponent);
					        frame.setVisible(false);
						}
					}
				});
			}
		});
	}
	
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		
		overviewListener = new OverviewListener();
		getSite().getPage().addPartListener(overviewListener);
	}
	
	@Override
	public void dispose() {
		if(overviewListener != null){
			getSite().getPage().removePartListener(overviewListener);
		}
		super.dispose();
	}
	
	public Overview getOverview() {
		return this.overview;
	}

	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}
	
	/**
	 * @return
	 */
	public Container getSwingContainer() {
		Panel panel = new Panel(new BorderLayout()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see java.awt.Container#update(java.awt.Graphics)
			 */
			public void update(java.awt.Graphics g) {
				/* Do not erase the background */
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractOverviewPart#resetOverview()
	 */
	public void resetOverview(JPanel panel){
		if(panel != null){
			overview.setScrollPane((JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, panel));
		}else{
			overview.setScrollPane(null);
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				overview.updateUI();
				overview.validate();
			}
		});
	}
	
	public java.awt.Frame getFrame() {
		return frame;
	}
	
	public void updateOverview(){
		frame.removeAll();
		Container container = getSwingContainer();
		container.setLayout(new BorderLayout());
		container.add(overview);
	}

	/**
	 * @param overview
	 */
	public void setOverview(Overview overview) {
		this.overview = overview;
	}
	
	class OverviewListener implements IPartListener {

		public void partActivated(IWorkbenchPart part) {
			IWorkbenchPage page = part.getSite().getPage();
			if (page != null) {
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart != null) {
					if (activeEditorPart instanceof IGraphDrawing) {
						editor = (IGraphDrawing) activeEditorPart;
						diagramManager = editor.getDiagramManager();
						try {
							if (getFrame() == null) {
								Display.getDefault().asyncExec(new Runnable() {
									
									@Override
									public void run() {
										doRefresh();
									}
								});
							} else {
								doRefresh();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}	
					} else if (activeEditorPart instanceof AbstractDecisionTableEditor) {
//						final AbstractDecisionTableEditor editor = (AbstractDecisionTableEditor)activeEditorPart;
//						SwingUtilities.invokeLater(new Runnable() {
//							public void run() {
//								frame.setVisible(true);
//								frame.removeAll();
//								Container container = getSwingContainer();
//								container.setLayout(new BorderLayout());
//								container.add(overview);
//								resetOverview(editor.getDecisionTablePane());
//							}
//						});

					}else{
						return;
					}
				}else{
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							frame.removeAll();
							frame.setEnabled(false);
							diagramManager = null;
						}
					});
				}
			}
		}

		public void partBroughtToTop(IWorkbenchPart part) {
			// TODO Auto-generated method stub

		}

		public void partClosed(IWorkbenchPart part) {
			// TODO Auto-generated method stub
			diagramManager = null;
		}

		public void partDeactivated(IWorkbenchPart part) {
			// TODO Auto-generated method stub

		}

		public void partOpened(IWorkbenchPart part) {
			// TODO Auto-generated method stub
		}
	}
	
	private void doRefresh() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (frame == null) {
					return;
				}
				frame.setVisible(true);
				if (overviewComponent != null) {
					frame.remove(overviewComponent);
					if( overviewComponent.isVisible() ) {
						overviewComponent.setVisible(false);
					}
					overviewComponent = null;
				}
				frame.removeAll();
				if (diagramManager == null) {
					return;
				}
				overviewComponent = ((IGraphDrawing) diagramManager).getOverviewComponent();
				overviewComponent.setCanvas(((IGraphDrawing) diagramManager).getDrawingCanvas());
				overviewComponent.setVisible(true);
				overviewComponent.setSize(300, 200);
				overviewComponent.setLocation(600, 0);
				overviewComponent.updateUI();
				frame.add(overviewComponent);
			}
		});
	}

	public TSEOverviewComponent getTSOverviewComponent() {
		return overviewComponent;
	}

	public void setTSOverviewComponent(TSEOverviewComponent overviewComponent) {
		this.overviewComponent = overviewComponent;
	}

}