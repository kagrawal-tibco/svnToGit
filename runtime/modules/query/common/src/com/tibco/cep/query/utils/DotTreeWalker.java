package com.tibco.cep.query.utils;

import java.util.HashMap;

import org.antlr.stringtemplate.StringTemplate;

/** A utility class to generate DOT diagrams (graphviz) from
 *  arbitrary trees.  You can pass in your own templates and
 *  can pass in any kind of tree or use Tree interface method.
 *  I wanted this separator so that you don't have to include
 *  ST just to use the org.antlr.runtime.tree.* package.
 *  This is a set of non-static methods so you can subclass
 *  to override.
 */
public class DotTreeWalker {
//    public static StringTemplate _treeST =
//        new StringTemplate(
//            "digraph {\n" +
//            "  ordering=out;\n" +
//            "  ranksep=.4;\n" +
//            "  node [shape=plaintext, fixedsize=true, fontsize=11, fontname=\"Courier\",\n" +
//            "        width=.25, height=.25];\n" +
//            "  edge [arrowsize=.5]\n" +
//            "  $nodes$\n" +
//            "  $edges$\n" +
//            "}\n");
    public static StringTemplate _treeST =
        new StringTemplate(
            "digraph {\n" +
            "  ordering=out;\n" +
            "  ranksep=.4;\n" +
            "  node [shape=plaintext, fixedsize=false, fontsize=10, fontname=\"Helvetica\",\n" +
            "        width=.25, height=.25];\n" +
            "  edge [arrowsize=.5]\n" +
            "  $nodes$\n" +
            "  $edges$\n" +
            "}\n");
    public static StringTemplate _nodeST =
            new StringTemplate("$name$ [label=\"$text$\"];\n");

    public static StringTemplate _edgeST =
            new StringTemplate("$parent$ -> $child$ // \"$parentText$\" -> \"$childText$\"\n");

    /** Track node to number mapping so we can get proper node name back */
    HashMap nodeToNumberMap = new HashMap();

    /** Track node number so we can get unique node names */
    int nodeNumber = 0;

    public StringTemplate toDOT(ObjectTreeNode tree,
                                ObjectTreeAdaptor adaptor,
                                StringTemplate _treeST,
                                StringTemplate _edgeST)
    {
        StringTemplate treeST = _treeST.getInstanceOf();
        nodeNumber = 0;
        toDOTDefineNodes(tree, adaptor, treeST);
        nodeNumber = 0;
        toDOTDefineEdges(tree, adaptor, treeST);
        /*
		if ( adaptor.getChildCount(tree)==0 ) {
            // single node, don't do edge.
            treeST.setAttribute("nodes", adaptor.getText(tree));
        }
        */
        return treeST;
    }

    public StringTemplate toDOT(ObjectTreeNode tree,
                                ObjectTreeAdaptor adaptor)
    {
        return toDOT(tree, adaptor, _treeST, _edgeST);
    }

    /** Generate DOT (graphviz) for a whole tree not just a node.
     *  For example, 3+4*5 should generate:
     *
     * digraph {
     *   node [shape=plaintext, fixedsize=true, fontsize=11, fontname="Courier",
     *         width=.4, height=.2];
     *   edge [arrowsize=.7]
     *   "+"->3
     *   "+"->"*"
     *   "*"->4
     *   "*"->5
     * }
     *
     * Return the ST not a string in case people want to alter.
     *
     * Takes a Tree interface object.
     */
    public StringTemplate toDOT(ObjectTreeNode tree) {
        return toDOT(tree, new BaseObjectTreeAdaptor());
    }

    protected void toDOTDefineNodes(ObjectTreeNode tree,
                                    ObjectTreeAdaptor adaptor,
                                    StringTemplate treeST)
    {
        if ( tree==null ) {
            return;
        }
        int n = adaptor.getChildCount(tree);
        if ( n==0 ) {
            // must have already dumped as child from previous
            // invocation; do nothing
            return;
        }

        // define parent node
        StringTemplate parentNodeST = getNodeST(adaptor, tree);
        treeST.setAttribute("nodes", parentNodeST);

        // for each child, do a "<unique-name> [label=text]" node def
        for (int i = 0; i < n; i++) {
            ObjectTreeNode child = adaptor.getChild(tree, i);
            StringTemplate nodeST = getNodeST(adaptor, child);
            treeST.setAttribute("nodes", nodeST);
            toDOTDefineNodes(child, adaptor, treeST);
        }
    }

    protected void toDOTDefineEdges(ObjectTreeNode tree,
                                    ObjectTreeAdaptor adaptor,
                                    StringTemplate treeST)
    {
        if ( tree==null ) {
            return;
        }
        int n = adaptor.getChildCount(tree);
        if ( n==0 ) {
            // must have already dumped as child from previous
            // invocation; do nothing
            return;
        }

        String parentName = "n"+getNodeNumber(tree);

        // for each child, do a parent -> child edge using unique node names
        String parentText = adaptor.getText(tree);
        for (int i = 0; i < n; i++) {
            ObjectTreeNode child = adaptor.getChild(tree, i);
            String childText = adaptor.getText(child);
            String childName = "n"+getNodeNumber(child);
            StringTemplate edgeST = _edgeST.getInstanceOf();
            edgeST.setAttribute("parent", parentName);
            edgeST.setAttribute("child", childName);
            edgeST.setAttribute("parentText", parentText);
            edgeST.setAttribute("childText", childText);
            treeST.setAttribute("edges", edgeST);
            toDOTDefineEdges(child, adaptor, treeST);
        }
    }

    protected StringTemplate getNodeST(ObjectTreeAdaptor adaptor, ObjectTreeNode t) {
        String text = adaptor.getText(t);
        StringTemplate nodeST = _nodeST.getInstanceOf();
        String uniqueName = "n"+getNodeNumber(t);
        nodeST.setAttribute("name", uniqueName);
        //if (text!=null) text = text.replaceAll("\"", "\\\\\"");
        if (text!=null) text = text.replaceAll("\\\\","\\\\\\\\").replaceAll("\"", "\\\\\"");
        nodeST.setAttribute("text", text);
        return nodeST;
    }

    protected int getNodeNumber(Object t) {
        Integer nI = (Integer)nodeToNumberMap.get(t);
        if ( nI!=null ) {
            return nI.intValue();
        }
        else {
            nodeToNumberMap.put(t, new Integer(nodeNumber));
            nodeNumber++;
            return nodeNumber-1;
        }
    }
}
