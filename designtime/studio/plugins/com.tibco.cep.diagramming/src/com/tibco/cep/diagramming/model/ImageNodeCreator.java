package com.tibco.cep.diagramming.model;


import com.tibco.cep.diagramming.utils.TSConstants;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;
/**
 * 
 * @author hitesh
 *
 */
public class ImageNodeCreator extends TSNodeBuilder {
	
	private static final long serialVersionUID = 1L;
	private String title;
	private TSEImage image;
	private String type = "";
	
	@SuppressWarnings("rawtypes")
	public ImageNodeCreator(Class loaderClass, String title, String imageName, String type) {
		TSEImage.setLoaderClass(loaderClass);		
		this.title = title;
		this.image = new TSEImage(loaderClass, "/icons/" + imageName);
		this.type = type;
	}
	
	public ImageNodeCreator(String title, String imageName) {
		TSEImage.setLoaderClass(this.getClass());		
		this.title = title;
		this.image = new TSEImage(this.getClass(), "/icons/" + imageName);
	}
	
	public TSENode addNode(TSEGraph graph)
	{
		TSEImageNodeUI ui = new TSEImageNodeUI();
		ui.setImage(this.image);
		TSENode node = super.addNode(graph);
		node.setName(this.title);
		node.setAttribute(TSConstants.NODE_TYPE_ATTR, type);
		node.setResizability(TSENode.RESIZABILITY_TIGHT_FIT);
		node.setUI(ui);			

		return node;
	}	
}
