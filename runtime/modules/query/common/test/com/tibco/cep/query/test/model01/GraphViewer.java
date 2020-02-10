package com.tibco.cep.query.test.model01;

import com.tomsawyer.application.swing.overview.TSEOverviewWindow;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEDeleteSelectedCommand;
import com.tomsawyer.interactive.command.editing.TSEGlobalLayoutCommand;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;
import com.tomsawyer.service.TSServiceException;
import com.tomsawyer.service.client.TSServiceProxy;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;
import com.tomsawyer.service.layout.client.TSLayoutProxy;
import com.tomsawyer.util.TSLicenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * This class defines an application that displays a graph drawing in
 * a frame.  This application allows the user to add nodes and edges
 * to the drawing interactively as well as to select and to delete them.
 */
public class GraphViewer implements ActionListener
{
	/**
	 * This method is a constructor for the application.
	 */
	public GraphViewer()
	{
	}


	/**
     * This method initializes the application by creating a drawing and the
     * needed GUI components to display the drawing on the screen and
     * initializing them.
     */
	public void init()
	{
		// Initialize licensing.
		TSLicenseManager.initTSSLicensing();

		this.initGraphManager();
		this.initGUI();
		this.initTools();
		
		// this.layout();
		
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	layout();
                swingCanvas.fitInCanvas(true);
            }
        });	
        
		this.frame.setVisible(true);
	}

    public TSEGraphManager getGraphManager() {
        return this.graphManager;
    }

    public void setGraphManager(TSEGraphManager graphManager) {
        this.graphManager = graphManager;
    }


    /**
	 * This method is used internally to create a diagram using a graph
	 * manager. This method creates a drawing with a mainframe node in the
	 * center and four terminal nodes fanning out from it.
	 */
	private void initGraphManager()
	{
		if (this.graphManager == null) {
			// creating the graph manager
			this.graphManager = new TSEGraphManager();
	
			// adding a graph to the graph manager
			TSEGraph graph = (TSEGraph) this.graphManager.addGraph();
	
			// adding the nodes to the graph, initializing their
			// positions, and setting their UI objects
			// ...
			
			TSENode node = (TSENode) graph.addNode();
			node.setTag("EMPTY GRAPH...");
		}
	}


	/**
	 * This method creates and initializes the application frame and a
	 * Swing canvas to display the previously created drawing. It also
	 * creates a button panel on the right side of the application
	 * frame to provide user interaction with the drawing.
	 */
	private void initGUI()
	{
		// creating the Swing canvas.
		this.swingCanvas = new TSSwingCanvas(this.graphManager);

		// creating the button panel
		JPanel buttonPanel = this.createButtonPanel();

		// creating the key binding for the Delete key
		this.swingCanvas.registerKeyboardAction(
				this,
				COMMAND_DELETE,
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		// creating and initializing the application frame
		this.frame = new JFrame("Test Resolution Graph Viewer");
		this.frame.setSize(950, 700);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add the components to the application frame

		this.frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		this.frame.getContentPane().add(swingCanvas, BorderLayout.CENTER);

		// setting the application frame as visible and display it
		// on the screen (if we do it now, it won't be laid out
		// this.frame.setVisible(true);
	}



	/**
	 * This method method is internally used to create a buttons panel
	 * with four button laid out vertically. The buttons are initialized
	 * with action commands and the application is added as an action
	 * listener to them.
	 */
	private JPanel createButtonPanel()
	{
		// Creating the four buttons in the panels

		JToggleButton selectButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/select.gif")));
		selectButton.setText("Select");
		selectButton.setToolTipText("Select");
		selectButton.setActionCommand(COMMAND_SELECT);
		selectButton.addActionListener(this);

		JToggleButton panButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/pan.gif")));
		panButton.setText("Pan");
		panButton.setToolTipText("Pan");
		panButton.setActionCommand(COMMAND_PAN);
		panButton.addActionListener(this);

		JToggleButton iZoomButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/interactiveZoom.gif")));
		iZoomButton.setText("Int. Zoom");
		iZoomButton.setToolTipText("Interactive Zoom");
		iZoomButton.setActionCommand(COMMAND_INTERACTIVE_ZOOM);
		iZoomButton.addActionListener(this);

		JToggleButton zoomButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/zoom.gif")));
		zoomButton.setText("Zoom");
		zoomButton.setToolTipText("Marquee Zoom");
		zoomButton.setActionCommand(COMMAND_ZOOM);
		zoomButton.addActionListener(this);

		JToggleButton layoutButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/hierarchical.gif")));
		layoutButton.setText("Layout");
		layoutButton.setToolTipText("Hierarchical Layout");
		layoutButton.setActionCommand(COMMAND_LAYOUT);
		layoutButton.addActionListener(this);
		
		JToggleButton fitInWindowButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/fitInCanvas.gif")));
		fitInWindowButton.setText("Fit in Window");
		fitInWindowButton.setToolTipText("Fit in Window");
		fitInWindowButton.setActionCommand(COMMAND_FITINWINDOW);
		fitInWindowButton.addActionListener(this);
		
		JToggleButton overviewButton = new JToggleButton(
			new ImageIcon(this.getClass().getResource("images/tileGrid.gif")));
		overviewButton.setText("Overview");
		overviewButton.setToolTipText("Overview Window");
		overviewButton.setActionCommand(COMMAND_OVERVIEW);
		overviewButton.addActionListener(this);		
		
		// Adding the buttons to a button group
		ButtonGroup buttonGroup = new ButtonGroup();

		buttonGroup.add(selectButton);
		buttonGroup.add(panButton);
		buttonGroup.add(iZoomButton);
		buttonGroup.add(zoomButton);
		buttonGroup.add(layoutButton);
		buttonGroup.add(fitInWindowButton);
		buttonGroup.add(overviewButton);

		// Creating the button panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 7));

		// Adding the buttons the panel
		panel.add(selectButton);
		panel.add(panButton);
		panel.add(iZoomButton);
		panel.add(zoomButton);
		panel.add(layoutButton);
		panel.add(fitInWindowButton);
		panel.add(overviewButton);

		// Set the select button by default
		selectButton.setSelected(true);

		return panel;
	}


	/**
	 * This method is internally used to initialize the various
	 * too used in this application.
	 */
	private void initTools()
	{
		// Registering the tools with the Swing canvas' tool manager

		TSToolManager toolManager = this.swingCanvas.getToolManager();

		TSViewingToolHelper.registerAll(toolManager);
		TSEditingToolHelper.registerAll(toolManager);

		// Use the select tool by default

		toolManager.switchTool(
			TSViewingToolHelper.getSelectTool(toolManager));
	}
	

	private void initLayoutData() {

		// Creating an object that holds all layout tailoring options 
		if (this.inputData == null) {
			this.inputData = new TSEAllOptionsServiceInputData(this.graphManager);
		}
		
		if (this.layoutProxy == null) {
			this.layoutProxy = new TSLayoutProxy();
		}
		
		if (this.hierarchicalLayoutInputTailor == null) {
			this.hierarchicalLayoutInputTailor =
				new TSHierarchicalLayoutInputTailor(this.inputData);
		}
		
		if (this.layoutInputTailor == null) {
			this.layoutInputTailor = new TSLayoutInputTailor(this.inputData);
			this.layoutInputTailor.setGraphManager(this.graphManager);
			this.layoutInputTailor.setAsCurrentOperation();
		}		
	}
	
	private void configureLayoutOptions(TSEGraph graph) {
		
		if (this.hierarchicalLayoutInputTailor == null) {
			this.initLayoutData();
		}
		
        hierarchicalLayoutInputTailor.setGraph(graph);
        hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
        hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
        hierarchicalLayoutInputTailor.setLevelDirection(
        	TSLayoutConstants.DIRECTION_TOP_TO_BOTTOM);
	}
	
	private void callLayout()
	{
		try
		{
			TSEGlobalLayoutCommand layoutCommand =
				new TSEGlobalLayoutCommand(this.swingCanvas,
						this.layoutProxy,
						this.inputData);
			this.swingCanvas.getCommandManager().
				transmit(layoutCommand);
		}
		catch (TSServiceException exception)
		{
			System.err.println("Layout failed. Error code: " + exception.getErrorCode());
		}
	}
	
	public void layout() {
		this.configureLayoutOptions((TSEGraph) this.graphManager.getMainDisplayGraph());
		this.callLayout();
		// this.swingCanvas.fitInCanvas(true);
	}
	
	public void showOverview() {
		if (this.overviewWindow == null) {
			this.overviewWindow = new TSEOverviewWindow(this.frame, "Overview", this.swingCanvas);
			this.overviewWindow.setSize(300, 300);
			// this.overviewWindow.setLocation(600, 0);
			this.overviewWindow.setVisible(true);		
		}
		else if (this.overviewWindow.isVisible()) {
			this.overviewWindow.setVisible(false);
		}
		else {
			this.overviewWindow.setVisible(true);
		}
	}


	// ---------------------------------------------------------------------
	// Section: Actions
	// ---------------------------------------------------------------------


	/**
	 * This method is internally called when a button or key is pushed.
	 * Depending on which button or key is pushed, the application
	 * switches to the corresponding tool or performs the desired action.
	 */
	public void actionPerformed(ActionEvent event)
	{
		TSToolManager toolManager = this.swingCanvas.getToolManager();
		String command = event.getActionCommand();

		if (command.equals(COMMAND_PAN))
		{
			toolManager.switchTool(
				TSViewingToolHelper.getPanTool(toolManager));
		}
		if (command.equals(COMMAND_INTERACTIVE_ZOOM))
		{
			toolManager.switchTool(
				TSViewingToolHelper.getInteractiveZoomTool(toolManager));
		}
		if (command.equals(COMMAND_ZOOM))
		{
			toolManager.switchTool(
				TSViewingToolHelper.getZoomTool(toolManager));
		}
		if (command.equals(COMMAND_FITINWINDOW))
		{
			this.swingCanvas.fitInCanvas(true);
		}
		if (command.equals(COMMAND_SELECT))
		{
			toolManager.switchTool(
				TSViewingToolHelper.getSelectTool(toolManager));
		}
		if (command.equals(COMMAND_LAYOUT))
		{
			this.layout();
		}
		if (command.equals(COMMAND_OVERVIEW))
		{
			this.showOverview();
		}
		else if (command.equals(COMMAND_DELETE))
		{
			// User clicked on the Delete button

			TSEDeleteSelectedCommand deleteSelected =
				new TSEDeleteSelectedCommand(this.swingCanvas);

			// Executing the command
			this.swingCanvas.getCommandManager().transmit(deleteSelected);
		}
	}


	// ---------------------------------------------------------------------
	// Section: Main method
	// ---------------------------------------------------------------------


	/**
	 * This main method creates and starts an instance of the
	 * application.
	 */
	public static void main(String[] args)
	{
		GraphViewer gv = new GraphViewer();
		gv.init();
	}


	// ---------------------------------------------------------------------
	// Section: Action Commands
	// ---------------------------------------------------------------------


	private final String COMMAND_FITINWINDOW = "COMMAND_FITINWINDOW";
	private final String COMMAND_PAN = "COMMAND_PAN";
	private final String COMMAND_INTERACTIVE_ZOOM = "COMMAND_INTERACTIVE_ZOOM";
	private final String COMMAND_ZOOM = "COMMAND_ZOOM";
	private final String COMMAND_SELECT = "COMMAND_SELECT";
	private final String COMMAND_LAYOUT = "COMMAND_LAYOUT";
	private final String COMMAND_OVERVIEW = "COMMAND_OVERVIEW";
	private final String COMMAND_DELETE = "COMMAND_DELETE";

	private TSEOverviewWindow overviewWindow;
	private TSEAllOptionsServiceInputData inputData;
	private TSLayoutInputTailor layoutInputTailor; 
	private TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor; 	
	private TSServiceProxy layoutProxy;		

	// ---------------------------------------------------------------------
	// Section: Instance Variables
	// ---------------------------------------------------------------------


	/**
	 * This variable stores the graph manager that contains the drawing.
	 */
	private TSEGraphManager graphManager = null;


	/**
	 * This variable stores the Swing canvas used to display the drawing.
	 */
	private TSSwingCanvas swingCanvas = null;


	/**
	 * This variable stores the application frame, in which the Swing
	 * canvas is displayed.
	 */
	private JFrame frame = null;
}

