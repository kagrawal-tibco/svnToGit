package com.tibco.cep.diagramming.views;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;


/**
 * This outlines the graphical editor in Overview window
 * 
 * @author hitesh,sasahoo
 * 
 */

public class DrawingOverview extends ViewPart {

	public static final String ID = "com.tibco.cep.diagramming.views.drawing.overview";

	private Composite swtAwtComponent;

	private static java.awt.Frame frame = null;

	private static IWorkbenchPage page;
	private static IGraphDrawing editor;
	private static Object diagramManager;
	public static TSEOverviewComponent overviewComponent;
	
	static {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			if (window != null) {
				IWorkbenchPage page = window.getActivePage();
				if (page != null) {
					OverviewListener overViewListener = new OverviewListener();
					page.addPartListener(overViewListener);
				}
			}
		}
	}

	public void createPartControl(Composite parent) {
		this.swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		frame = SWT_AWT.new_Frame(swtAwtComponent);
		new SyncXErrorHandler().installHandler();
	}

	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
	}

	public java.awt.Frame getFrame() {
		return frame;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	static class OverviewListener implements IPartListener {

		public void partActivated(IWorkbenchPart part) {
			if (page != null) {
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart != null) {
					if (activeEditorPart instanceof IGraphDrawing) {
						editor = (IGraphDrawing) activeEditorPart;
					} else {
						return;
					}
					diagramManager = editor.getDiagramManager();
					try {
					SwingUtilities.invokeLater(new Runnable() {
					public void run() {
//						TSEOverviewComponent overviewComponent = ((IGraphDrawing) diagramManager).getOverviewComponent();
						if (overviewComponent != null && overviewComponent.isVisible()) {
							overviewComponent.setVisible(false);
							overviewComponent = null;
						}
						overviewComponent = new TSEOverviewComponent(((IGraphDrawing) diagramManager).getDrawingCanvas());
						overviewComponent.setVisible(true);
						overviewComponent.setSize(300, 200);
						overviewComponent.setLocation(600, 0);
						overviewComponent.updateUI();
						frame.add(overviewComponent);
						}
					});
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}
		}

		public void partBroughtToTop(IWorkbenchPart part) {
			// TODO Auto-generated method stub

		}

		public void partClosed(IWorkbenchPart part) {
			// TODO Auto-generated method stub

		}

		public void partDeactivated(IWorkbenchPart part) {
			// TODO Auto-generated method stub

		}

		public void partOpened(IWorkbenchPart part) {
			// TODO Auto-generated method stub
		}
	}
}
