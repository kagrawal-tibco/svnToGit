package com.tibco.cep.webstudio.client.widgets;



public class BPMNProcessEditorFactory {//implements IEditorFactory {

//	private String id;
//	private WebStudioEditorPart editor;
//	private Canvas parentCanvas;

//	public String getID() {
//		return id;
//	}
//
//	public String getDescription() {
//		return "BPMN Process Editor";
//	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.view.IEditorFactory#create(com.tibco.cep.webstudio.client.data.NavigatiorResource, com.tibco.cep.webstudio.client.widgets.WebStudioEditorPart)
	 */
//	@Override
//	public Canvas create(NavigatorResource record, WebStudioEditorPart editor) {
//		setEditor(editor);
//		this.parentCanvas = new Canvas();
//		parentCanvas.setWidth100();
//		parentCanvas.setHeight100();
//		id = parentCanvas.getID();
//		// TODO: create the relevant base GWT widget (or SmartGWT widget) and add to the parent canvas
//		// for example:
//		//		RichTextArea area = new RichTextArea();
//		//		area.setHTML(record.getName());
//		//		parentCanvas.addChild(area);
//		
//		String[] parts = record.getId().split("\\$");
//        String relPath = "";
//        for (int i = 1; i < parts.length; i++) {
//               relPath += "/"+parts[i];
//        }
//        
//        // System.out.println("Client side: sending request to open " + relPath + 
//        //	"from " + WebStudio.get().getCurrentProjectDir());
//
//		this.populate(parentCanvas, WebStudio.get().getCurrentProjectDir(),
//			WebStudio.get().getCurrentProjectName(), relPath);
//		return parentCanvas;
//		return null;
//	}
//
//	@Override
//	public WebStudioEditorPart getEditor() {
//		return editor;
//	}
//
//	@Override
//	public void setEditor(WebStudioEditorPart editor) {
//		this.editor = editor;
//	}
//	
//	public String getEditorId() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	private void populate(Canvas parentCanvas, String projDir, String projName, String resource) {
//		// this will call loadWebProjectViewer() if licensing is OK
//        if (TSSession.getInstance().isLicensed()) {
//            // OK Licensing is initialized.
//            loadWebProjectViewer(projDir, projName, resource);
//            return;
//        }
//        else {
//        	this.checkLicensing(projDir, projName, resource);
//        }
//	}
//
//	private void loadWebProjectViewer(final String projDir, final String projName, final String resourcePath) {
//		final String projectID = "project";
//		final String moduleName = "GraphManager";
//		final String modelID = "model";
//		final String projectLocation = "project/GraphManager.tsp";
//		final String drawingViewID = "view";
//		final String drawingViewName = "View";
//
//		try	{
//
//			TSWebProject project = new TSWebProject(projectID, projectLocation);
//			project.newDefaultModel(moduleName, modelID);
//			project.newDrawingView(moduleName, modelID,	drawingViewID, drawingViewName);
//
//			// Add the button to the south of the client area. When the button
//			// is clicked, a service call is made to refresh all server
//			// side objects from using the defined integrators for the project.
//			// By default, the update button is disabled. When the project
//			// creation is successful, the update button is enabled below.
//			final Button updateButton = new Button("Reload");
//			updateButton.addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					populateViewer(modelID, drawingViewID, projDir, projName, resourcePath);
//				}
//			});
//			
//			DockLayoutPanel layoutPanel = new DockLayoutPanel(Style.Unit.EM);
//			layoutPanel.setWidth("100%");
//			layoutPanel.setHeight("800px");
//			layoutPanel.addSouth(updateButton, 2);
//			
//			// add "property sheet" panel
//			RichTextArea area = new RichTextArea();
//			area.setWidth("100%");
//			area.setHTML("This is the <b>Documentation</b> associated with this: <H3>task.</H3>.");
//			layoutPanel.addSouth(area, 15);			
//			
//			Widget w = TSWebModelViewCoordinators.get(modelID).getView(drawingViewID).getWidget();
//			layoutPanel.add(w);
//
//			// Create the project on both the client and the server. Add a
//			// callback to monitor success or failure of the creation process.
//			updateButton.setEnabled(false);
//			project.create(new TSCallback<TSWebProjectCommandResult>() {
//				public void onSuccess(TSWebProjectCommandResult returnData) {
//					updateButton.setEnabled(true);
//					populateViewer(modelID, drawingViewID, projDir, projName, resourcePath);
//				}
//
//				public void onFailure(Throwable th) {
//					th.printStackTrace();
//					GWT.log("Failed to load project", th);
//				}
//			});
//			
//			parentCanvas.addChild(layoutPanel);
//		}
//		catch (TSWebProjectException e)	{
//			TSErrorDialogBox errorDialog = new TSErrorDialogBox(e);
//			errorDialog.center();
//		}
//	}
//
//	protected void checkLicensing(final String projDir, final String projName, final String resourcePath) {
//		if (TSLicensingModule.getInstance() != null) {
//			// System.getProperty("user.name", "default");
//			// TODO: enter your own username for now:
//			TSLicensingModule.getInstance().checkLicense("build",
//					new TSCallback<Void>() {
//						public void onSuccess(Void v) {
//							// OK Licensing is initialized.
//							loadWebProjectViewer(projDir, projName, resourcePath);
//						}
//
//						public void onFailure(Throwable th) {
//							// Failed to check licensing.
//							new TSErrorDialogBox(th).center();
//						}
//					});
//		}
//		else {
//			// Check this again, the module should have been loaded by now.
//			Timer timer = new Timer() {
//				public void run() {
//					checkLicensing(projDir, projName, resourcePath);
//				}
//			};
//
//			timer.schedule(5);
//		}
//	}	
//	
//	protected void populateViewer(final String modelID,	final String drawingViewID,
//			final String projDir, final String projName, final String resourcePath) {
//		
//		LinkedList args = new LinkedList();
//		args.add(projDir);
//		args.add(projName);
//		args.add(resourcePath);
//		PopulateCommand populate = new PopulateCommand(
//				TSWebModelViewCoordinators.get(modelID).getView(drawingViewID),
//				args);
//		TSWebModelViewCoordinators.get(modelID).invokeCommandAndUpdateAll(populate);
//	}	
	
}
