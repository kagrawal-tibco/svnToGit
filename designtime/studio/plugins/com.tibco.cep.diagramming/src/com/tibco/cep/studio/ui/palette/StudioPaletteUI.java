package com.tibco.cep.studio.ui.palette;

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.palette.actions.PaletteController;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.parts.SeparatorPaletteEntry;
import com.tibco.cep.studio.ui.palette.utils.Messages;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.tool.TSTool;
import com.tomsawyer.interactive.tool.TSToolManager;

/**
 * 
 * @author sasahoo
 * 
 */
public class StudioPaletteUI {

	private static IGraphDrawing editor;
	private static Object diagramManager;
	public static Font unselectedFont = new Font(null, "Tahoma", 8, SWT.NONE);
	public static Color textColor = new Color(null, 33, 93, 198);
	//private static Color defaultColor = new Color(null, 215, 222, 248);
	public static Color defaultColor = new Color(null, 255, 255, 255);

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public static PaletteDrawer createGlobalDrawer(final IWorkbenchPage page,
			Palette palette) {

		PaletteDrawer globalToolsDrawer = new PaletteDrawer(
				Messages.PALLETTE_DRAWER_GLOBAL_TOOL_TITLE,
				Messages.PALLETTE_DRAWER_GLOBAL_TOOL_TOOLTIP,
				"icons/tools.gif", palette, false);
		globalToolsDrawer.setGlobal(true);

		addGlobalPaletteEntry(page, null,
				Messages.PALLETTE_ENTRY_TOOL_SELECT_TITLE,
				Messages.PALLETTE_ENTRY_TOOL_SELECT_TOOLTIP,
				"icons/select.gif", globalToolsDrawer, "SELECT_TOOL");
		addGlobalPaletteEntry(page, null,
				Messages.PALLETTE_ENTRY_TOOL_PAN_TITLE,
				Messages.PALLETTE_ENTRY_TOOL_PAN_TOOLTIP, "icons/pan.gif",
				globalToolsDrawer, "PAN_TOOL");
		addGlobalPaletteEntry(page, null,
				Messages.PALLETTE_ENTRY_TOOL_ZOOM_TITLE,
				Messages.PALLETTE_ENTRY_TOOL_ZOOM_TOOLTIP, "icons/zoom.gif",
				globalToolsDrawer, "ZOOM_TOOL");
		addGlobalPaletteEntry(page, null,
				Messages.PALLETTE_ENTRY_TOOL_INTERACTIVE_ZOOM_TITLE,
				Messages.PALLETTE_ENTRY_TOOL_INTERACTIVE_ZOOM_TOOLTIP, "icons/zoom.gif",
				globalToolsDrawer, "INTERACTIVE_ZOOM_TOOL");
		addGlobalPaletteEntry(page, null,
				Messages.PALLETTE_ENTRY_TOOL_LINK_NAVIGATOR_TITLE,
				Messages.PALLETTE_ENTRY_TOOL_LINK_NAVIGATOR_TOOLTIP, "icons/linkNavigation.gif",
				globalToolsDrawer, "LINK_NAVIGATOR_TOOL");
		
		return globalToolsDrawer;
	}

	/**
	 * 
	 * @param paletteView
	 */
	public static void resetPalette(PaletteView paletteView) {
		if (paletteView.getPalette() == null) {
			return;
		}
		ExpandItem[] items = paletteView.getRootExpandBar().getItems();
		for (ExpandItem item : items) {
			item.dispose();
		}
		Control[] children = paletteView.getRootExpandBar().getChildren();
		for (Control control : children) {
			control.dispose();
		}
		paletteView.createInitialPalette();
	}

	public static void resetSwitchEditorPalette(IEditorSite site, boolean isEmptyPalette) {
		if (site.getPage() != null) {
			IViewPart view = site.getPage().findView(PaletteView.ID);
			if (view != null) {
				PaletteView paletteView = (PaletteView) view;
				if (isEmptyPalette) {
					paletteView.setType(PALETTE.NONE);
				}
				resetPalette(paletteView);
			}
		}
	}

	public enum Tool {
		SELECT_TOOL, PAN_TOOL, ZOOM_TOOL, INTERACTIVE_ZOOM_TOOL, LINK_NAVIGATOR_TOOL
	}

	/**
	 * 
	 * @param page
	 * @param id
	 * @param title
	 * @param tooltip
	 * @param image
	 * @param drawer
	 * @param toolid
	 */
	private static void addGlobalPaletteEntry(final IWorkbenchPage page,
			String id, String title, String tooltip, String image,
			PaletteDrawer drawer, final String toolid) {
		drawer.addPaletteEntry(new PaletteEntry(id, title, tooltip, image, drawer, false) {

			@Override
			protected void stateChanged() {
				if (getState() == STATE_NOT_SELECTED) {
					return;
				}
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart instanceof IGraphDrawing) {
					editor = (IGraphDrawing) activeEditorPart;
				}
				if (editor != null) {

					diagramManager = editor.getDiagramManager();
					TSToolManager toolManager = ((IGraphDrawing) diagramManager)
							.getDrawingCanvas().getToolManager();
					TSTool tool = null;
					switch (Tool.valueOf(toolid)) {
					case SELECT_TOOL:
						tool = toolManager.getTool("SELECT_TOOL");
						break;
					case PAN_TOOL:
						tool = toolManager.getTool("PAN_TOOL");
						break;
					case ZOOM_TOOL:
						tool = toolManager.getTool("ZOOM_TOOL");
						break;
					case INTERACTIVE_ZOOM_TOOL:
						tool = toolManager.getTool("INTERACTIVE_ZOOM_TOOL");
						break;
					case LINK_NAVIGATOR_TOOL:
						tool = toolManager.getTool("LINK_NAVIGATION_TOOL");
						break;
					default:
						// no processing ...
					}
					if (tool != null) {
						toolManager.setActiveTool(tool);
					}
				}

			}

		});
	}

	/**
	 * 
	 * @param page
	 * @param drawer
	 * @param id
	 * @param title
	 * @param tooltip
	 * @param image
	 * @param nodeCreator
	 * @param nodeUI
	 * @param isEdge
	 */
	public static void addPaletteEntry(final IWorkbenchPage page,
			PaletteDrawer drawer, String id, String title, String tooltip,
			String image, final TSNodeBuilder nodeBuilder,
			final TSENodeUI nodeUI, final boolean isEdge) {

		drawer.addPaletteEntry(new PaletteEntry(id, title, tooltip, image, drawer, false) {

			@Override
			protected void stateChanged() {
				if (getState() == STATE_NOT_SELECTED) {
					return;
				}
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart instanceof IGraphDrawing) {
					editor = (IGraphDrawing) activeEditorPart;
				}
				if (editor != null) {
					diagramManager = editor.getDiagramManager();
					TSToolManager toolManager = ((IGraphDrawing) diagramManager).getDrawingCanvas().getToolManager();
					TSEGraphManager graphManager =  ((IGraphDrawing) diagramManager).getGraphManager();
					if (isEdge) {
						TSEPolylineEdgeUI edgeUI = new TSEPolylineEdgeUI();
						edgeUI.setAntiAliasingEnabled(true);
						TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
						graphManager.getEdgeBuilder().setEdgeUI(edgeUI);
						toolManager.setActiveTool(createEdgeTool);
					} else {
						TSECreateNodeTool createTool = TSEditingToolHelper.getCreateNodeTool(toolManager);
						graphManager.setNodeBuilder(nodeBuilder);
						graphManager.getNodeBuilder().setNodeUI(nodeUI);
						toolManager.setActiveTool(createTool);
					}
				}
			}
		});
	}

	/**
	 * 
	 * @param parent
	 */
	public static void createSeparator(Composite parent) {
		Label label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @param image
	 * @return
	 */
	public static CLabel createRectangleButton(final IWorkbenchWindow window,
			boolean isGlobalPalette, Composite buttonParent, String label,
			String toolTipText, String imageName,
			PalettePresentationUpdater listener, Image image) {

		final CLabel button = new CLabel(buttonParent, SWT.NONE);
		button.setText(label);
		button.setFont(unselectedFont);
		button.setBackground(defaultColor);
		button.setForeground(textColor);
		if (isGlobalPalette) {
			if (imageName != null
					&& DiagrammingPlugin.getImageDescriptor(imageName) != null) {
				button.setImage(DiagrammingPlugin.getDefault().getImage(imageName));
			}
		} else {
			button.setImage(image);
		}
		button.setToolTipText(toolTipText);
		button.addMouseListener(new PaletteController(window));
		button.addMouseTrackListener(listener);

		GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginRight = layout.marginWidth = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		button.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(data);
		return button;
	}

	/**
	 * 
	 * @param window
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @return
	 */
	private static CLabel createRectangleButton(final IWorkbenchWindow window,
			Composite buttonParent, String label, String toolTipText,
			String imageName, PalettePresentationUpdater listener) {

		final CLabel button = new CLabel(buttonParent, SWT.NONE);
		button.setText(label);
		button.setFont(unselectedFont);
		button.setBackground(defaultColor);
		button.setForeground(textColor);

		if (imageName != null
				&& DiagrammingPlugin.getImageDescriptor(imageName) != null) {
			button.setImage(DiagrammingPlugin.getDefault().getImage(imageName));
		}
		button.setToolTipText(toolTipText);
		button.addMouseListener(new PaletteController(window));
		button.addMouseTrackListener(listener);

		GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginRight = layout.marginWidth = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		button.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(data);
		return button;
	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @param image
	 * @return
	 */
	public static Button createCheckButton(final IWorkbenchWindow window,
			Composite buttonParent, String label, String toolTipText,
			String imageName, PalettePresentationUpdater listener, Image image) {

		final Button button = new Button(buttonParent, SWT.CHECK);
		button.setText(label);
		button.setFont(unselectedFont);
		button.setBackground(defaultColor);
		button.setForeground(textColor);

		button.setImage(image);
		button.setToolTipText(toolTipText);
	
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(data);
		return button;
	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @param image
	 * @return
	 */
	public static Button createRadioButton(final IWorkbenchWindow window,
			Composite buttonParent, String label, String toolTipText,
			String imageName, PalettePresentationUpdater listener, Image image) {

		final Button button = new Button(buttonParent, SWT.RADIO);
		button.setText(label);
		button.setFont(unselectedFont);
		button.setBackground(defaultColor);
		button.setForeground(textColor);

		button.setImage(image);
		button.setToolTipText(toolTipText);
	
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(data);
		return button;
	}
	
	/**
	 * 
	 * @param window
	 * @param drawer
	 * @param rootExpandBar
	 * @param listener
	 */
	public static void createDrawer(final IWorkbenchWindow window,
			PaletteDrawer drawer, ExpandBar rootExpandBar,
			PalettePresentationUpdater listener) {

		Composite parent = new Composite(rootExpandBar, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = layout.horizontalSpacing = layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);

//		GridData data = new GridData(GridData.FILL_BOTH);
//		data.grabExcessHorizontalSpace = true;
//		data.grabExcessVerticalSpace = true;
//		parent.setLayoutData(data);
		
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(parent);

		List<PaletteEntry> entries = drawer.getPaletteEntries();
		for (PaletteEntry paletteEntry : entries) {
			createPaletteEntry(window, paletteEntry, parent, listener);
		}
		ExpandItem graphItem = new ExpandItem(rootExpandBar, SWT.NONE);
		graphItem.setText(drawer.getTitle());
		if (drawer.getImage() != null) {
			graphItem.setImage(DiagrammingPlugin.getDefault().getImage(
					drawer.getImage()));
		}
		graphItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		graphItem.setControl(parent);
		graphItem.setExpanded(true);
		graphItem.setData(drawer);
		
		parent.layout(true);
	}

	/**
	 * 
	 * @param window
	 * @param paletteEntry
	 * @param parent
	 * @param listener
	 */
	private static void createPaletteEntry(final IWorkbenchWindow window,
			PaletteEntry paletteEntry, Composite parent,
			PalettePresentationUpdater listener) {
		if (paletteEntry instanceof SeparatorPaletteEntry) {
			createSeparator(parent);
			return;
		}
		CLabel entry = createRectangleButton(window, parent, paletteEntry
				.getTitle(), paletteEntry.getToolTip(),
				paletteEntry.getImage(), listener);
		entry.setData(paletteEntry);

	}
}
