package com.tibco.be.parser.semantic;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.tibco.be.parser.Token;
import com.tibco.be.parser.tree.DeclarationNode;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.TemplatedDeclarationNode;
import com.tibco.be.parser.tree.TemplatedProductionNode;
import com.tibco.be.parser.tree.TypeNode;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jan 3, 2009
 * Time: 10:15:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmapGenVisitor implements NodeVisitor {
    private SmapStratum smap;
    private String filePath;
    private Map innerClassMap;
    private int sourceOffset;

    public SmapGenVisitor(String filePath, SmapStratum stratum, int sourceOffset) {
        // set up our SMAP generator
        this.smap = stratum;
        this.filePath = filePath.replace('\\', '/');
        String unqualifiedName = unqualify(filePath);
        smap.addFile(unqualifiedName, this.filePath);
        this.sourceOffset = sourceOffset < 0 ? 0 : sourceOffset;
    }

    public Object visitDeclarationNode(DeclarationNode node) {
        doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }

    public Object visitFunctionNode(FunctionNode node) {
        //doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }

    public Object visitNameNode(NameNode node) {
        //doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }

    public Object visitProductionNode(ProductionNode node) {
        doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }


    public Object visitTemplatedProductionNode(TemplatedProductionNode node) {
        return this.visitProductionNode(node);
    }


    public Object visitProductionNodeListNode(ProductionNodeListNode node) {
        doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }

    public Object visitRootNode(RootNode node) {
        //doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }

    public Object visitTypeNode(TypeNode node) {
        //doSmap(node);
        visitNodes(node.getChildren());
        return null;
    }


    @Override
    public Object visitTemplatedDeclarationNode(TemplatedDeclarationNode node) {
        //doSmap(node); //todo?
        visitNodes(node.getChildren());
        return null;
    }


    private void doSmap(
            Node n,
            int inLineCount,
            int outIncrement,
            int skippedLines) {
        Token mark = n.getFirstToken();
        if (mark == null) {
            return;
        }
        //System.out.println("Node:"+n+" outIncrement:"+outIncrement);


            smap.addLineData(
                sourceOffset + mark.beginLine + skippedLines-1,
                filePath,
                inLineCount - skippedLines,
                n.getBeginJavaLine() + skippedLines,
                outIncrement);
    }

    private void doSmap(Node n) {
        if (n == null || n.getFirstToken() == null || n.getLastToken() == null || n.getBeginJavaLine() == -1 || n.getEndJavaLine() == -1) return ;
//        System.out.println("Node:"+n.getClass().getName()+" Text: [" + getSourceText(n)+" ]");
        int obegin = n.getBeginJavaLine();
        int oend = n.getEndJavaLine();
//        System.out.println("Source:{" + n.getFirstToken().beginLine + "," + n.getLastToken().endLine + "}" + "Output:{" + n.getBeginJavaLine() + "," + n.getEndJavaLine() + "}");
        doSmap(n, 1, n.getEndJavaLine() - n.getBeginJavaLine(), 0);
    }

    private void doSmapText(Node n) {
        String text = getSourceText(n);
        int index = 0;
        int next = 0;
        int lineCount = 1;
        int skippedLines = 0;
        boolean slashStarSeen = false;
        boolean beginning = true;

        // Count lines inside text, but skipping comment lines at the
        // beginning of the text.
        while ((next = text.indexOf('\n', index)) > -1) {
            if (beginning) {
                String line = text.substring(index, next).trim();
                if (!slashStarSeen && line.startsWith("/*")) {
                    slashStarSeen = true;
                }
                if (slashStarSeen) {
                    skippedLines++;
                    int endIndex = line.indexOf("*/");
                    if (endIndex >= 0) {
                        // End of /* */ comment
                        slashStarSeen = false;
                        if (endIndex < line.length() - 2) {
                            // Some executable code after comment
                            skippedLines--;
                            beginning = false;
                        }
                    }
                } else if (line.length() == 0 || line.startsWith("//")) {
                    skippedLines++;
                } else {
                    beginning = false;
                }
            }
            lineCount++;
            index = next + 1;
        }

        doSmap(n, lineCount, 1, skippedLines);
    }

    public String getSourceText(Node n) {
        if (n == null || n.getFirstToken() == null || n.getLastToken() == null || n.getBeginJavaLine() == -1 || n.getEndJavaLine() == -1) return "";
        StringBuffer text = new StringBuffer();
        //testing for end.next is safe because if
        //end is the final token, then end.next will be
        //null and the loop will still end normally

        for (Token t = n.getFirstToken(); t != null; t = t.next) {
            int offset = text.length();
            for (Token s = t.specialToken; s != null; s = s.specialToken) {
                text.insert(offset, s.image);
            }
            text.append(t.image);
        }
        return text.toString();

//        return n.toString();
    }

    public SmapStratum visitNodes(Iterator nodes) {
        while (nodes.hasNext()) {
            Node node = (Node) nodes.next();
            node.accept(this);
        }
        return smap;
    }

    public SmapStratum visitNode(Node node) {
        node.accept(this);
        return smap;
    }

    /**
     * Returns an unqualified version of the given file path.
     */
    public static String unqualify(String path) {
        path = path.replace('\\', '/');
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /**
     * Returns a file path corresponding to a potential SMAP input
     * for the given compilation input (JSP file).
     */
    public static String inputSmapPath(String path) {
        return path.substring(0, path.lastIndexOf('.') + 1) + "smap";
    }

    public static void installSmap(String[] smap)
        throws IOException {
        if (smap == null) {
            return;
        }

        for (int i = 0; i < smap.length; i += 2) {
            File outServlet = new File(smap[i]);
            SmapInstaller.install(outServlet, smap[i+1].getBytes());
        }
    }
}
