package com.tibco.cep.query.utils;


import java.util.HashMap;

import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.drawing.TSPolygonShape;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;
import com.tomsawyer.licensing.TSLicenseManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Dec 13, 2007
 * Time: 2:31:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TSGraphWalker {

    /** Track node to number mapping so we can get proper node name back */
    HashMap nodeToNumberMap = new HashMap();

    /** Track node number so we can get unique node names */
    int nodeNumber = 0;
    private TSEGraphManager graphManager;


    public TSGraphWalker() {
		TSLicenseManager.setUserName(System.getProperty("user.name"));
        TSLicenseManager.initTSSLicensing();
        this.graphManager = new TSEGraphManager();
        this.graphManager.emptyTopology();
    }

    public TSEGraphManager toGraph(ObjectTreeNode tree,ObjectTreeAdaptor adaptor) {
        if ( tree==null ) {
            return null;
        }
        TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
        toGraph(tree,adaptor,graph);
        return this.graphManager;
    }

    private TSENode toGraph(ObjectTreeNode tree, ObjectTreeAdaptor adaptor, TSEGraph graph) {

        TSENode tsnode = null;

        int n = adaptor.getChildCount(tree);
        if ( n==0 ) {
            String nodeText = adaptor.getText(tree);
            tsnode =  getTSNode(tree,nodeText);
        } else {
            String nodeText = adaptor.getText(tree);
            tsnode = getTSNode(tree,nodeText);

            // for each child, do a "<unique-name> [label=text]" node def
            for (int i = 0; i < n; i++) {
                ObjectTreeNode child = adaptor.getChild(tree, i);
                TSENode ts = toGraph(child, adaptor, graph);
                TSEEdge edge = (TSEEdge) this.graphManager.addEdge(tsnode, ts);
            }
        }
        return tsnode;
    }







    protected TSENode getTSNode(ObjectTreeNode node,String tag) {
        TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
        TSENode tsNode = (TSENode) graph.addNode();
        TSENodeUI nodeUI;
        tsNode.setUserObject(node);
        if (tag!=null) tag = tag.replaceAll("\\\\","\\\\\\\\").replaceAll("\"", "\\\\\"");
        tsNode.setTag(tag);
        // calculate the right size, but make the width a bit bigger because
        // oval node shapes may cut off the text towards its ends.
        tsNode.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
        double width = tsNode.getWidth();
        tsNode.setResizability(TSESolidObject.RESIZABILITY_NO_FIT);
        tsNode.setWidth(width + 25);
        tsNode.setShape(new TSPolygonShape());
        nodeUI = new TSEShapeNodeUI();
        tsNode.setShape(TSPolygonShape.getInstance(TSPolygonShape.ROUNDED_RECTANGLE));        
        nodeUI.setTextAntiAliasingEnabled(true);
        nodeUI.setFillColor(new TSEColor(245, 243, 178));
        return tsNode;
    }




    protected TSEdge getTSEdge(TSENode sourceNode,
                               TSConnector sourceConnector,
                               TSENode targetNode,
                               TSConnector targetConnector,
                               String label,
                               ObjectTreeNode node
                               ) {
        TSEEdge edge = (TSEEdge) this.graphManager.addEdge(sourceNode, targetNode);
        edge.setUserObject(node);
        if (label != null)
        {
            TSEEdgeLabel edgeLabel = (TSEEdgeLabel) edge.addLabel();
            edgeLabel.setTag(label);
        }

        return null;
    }

}
