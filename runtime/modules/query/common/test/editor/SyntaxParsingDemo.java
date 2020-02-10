package editor;

/*
 * @(#)SyntaxParsingDemo.java 5/5/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.editor.CodeEditor;
import com.jidesoft.editor.CodeInspector;
import com.jidesoft.editor.margin.BraceMatchingMarginPainter;
import com.jidesoft.editor.margin.CodeFoldingMargin;
import com.jidesoft.editor.marker.Marker;
import com.jidesoft.editor.marker.MarkerArea;
import com.jidesoft.editor.marker.MarkerModel;
import com.jidesoft.editor.status.CodeEditorStatusBar;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.tibco.cep.query.ast.parser.BEOqlLexer;
import com.tibco.cep.query.ast.parser.BEOqlParser;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.io.*;

/**
 * Demoed Component: {@link com.jidesoft.editor.CodeEditor} <br> Required jar files:
 * jide-common.jar, jide-editor.jar, jide-components.jar, jide-shortcut.jar, jide-editor.jar <br>
 * Required L&F: any L&F
 */
public class SyntaxParsingDemo extends AbstractDemo {
    public CodeEditor _editor;

    public SyntaxParsingDemo() {
    }

    public String getName() {
        return "OQLSyntax Parser Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_CODE_EDITOR;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_BETA;
    }

    @Override
    public String getDescription() {
        return "This is an example to use a grammar parser to detect warnings and errors in the source code and use MarkerArea provided by JIDE Code Editor " +
                "to display those warnings and errors.\n" +
                "Demoed classes:\n" +
                "CodeEditor";
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        try {
            _editor = new CodeEditor();
            StringBuffer buf = readInputStream(SyntaxParsingDemo.class.getClassLoader().getResourceAsStream("editor/sample.oql"));
            _editor.setTokenMarker(new OQLTokenMarker());
            _editor.setText(buf.toString());
            _editor.setPreferredSize(new Dimension(600, 500));
            panel.add(_editor, BorderLayout.CENTER);
            panel.add(new CodeEditorStatusBar(_editor), BorderLayout.AFTER_LAST_LINE);
            panel.add(new MarkerArea(_editor), BorderLayout.AFTER_LINE_ENDS);

            _editor.addCodeInspector(new OQLCodeInspector());
            _editor.setAutoInspecting(true);
            CodeFoldingMargin margin = new CodeFoldingMargin();
            margin.addMarginPainter(new BraceMatchingMarginPainter());
            _editor.getMarginArea().addMarginComponent(margin);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return panel;
    }

    private static StringBuffer readInputStream(InputStream in) throws IOException {
        Reader reader = new InputStreamReader(in);
        char[] buf = new char[1024];
        StringBuffer buffer = new StringBuffer();
        int read;
        while ((read = reader.read(buf)) != -1) {
            buffer.append(buf, 0, read);
        }
        reader.close();
        return buffer;
    }

    @Override
    public String getDemoFolder() {
        return "E1. CodeEditor";
    }

    static public void main(String[] s) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        showAsFrame(new SyntaxParsingDemo());
    }

    private static class OQLCodeInspector implements CodeInspector {
        BEOqlParser parser;
        BEOqlLexer lexer;

        public void inspect(final CodeEditor codeEditor,final DocumentEvent event,final  MarkerModel markerModel) {
            try {
                markerModel.setAdjusting(true);
                markerModel.clearMarkers();
                ANTLRInputStream is = new ANTLRInputStream(new ByteArrayInputStream(codeEditor.getText().getBytes()));
                lexer = new BEOqlLexer(is);
                CommonTokenStream cts = new CommonTokenStream(lexer);
                parser = new BEOqlParser(cts);
                BEOqlParser.query_return r = null;
                    r = parser.query();
                CommonTree queryTree = (CommonTree) r.getTree();                
//                CommonTree ftree = null;
//                CommonTree ltree = null;
//                for(int i=0; i < queryTree.getChildCount(); i++) {
//                        ltree = (CommonTree) queryTree.getChild(i);
//                        codeEditor.getFoldingModel().setAdjusting(true);
//                        codeEditor.getFoldingModel().addFoldingSpan(ltree.startIndex,ltree.stopIndex, "...");
//                        codeEditor.getFoldingModel().setAdjusting(false);
//                }
            }
            catch (RecognitionException re) {
                    String[] tokens = new String[]{re.token.getText()};
                    markerModel.addMarker(
                            codeEditor.getLineStartOffset(re.line-1) + re.charPositionInLine,
                            codeEditor.getLineStartOffset(re.line-1) + re.charPositionInLine + re.token.getText().length(),
                            Marker.TYPE_ERROR, parser.getErrorMessage(re, tokens));

                    System.out.println(parser.getErrorMessage(re,tokens) + " at "+re.line +":"+re.charPositionInLine);
            }
            catch (IOException e) {
                e.printStackTrace();
            } finally {
                Runnable runnable = new Runnable() {
                    public void run() {
                        markerModel.setAdjusting(false);
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        }
    }

//    private static class PhpCodeInspector implements CodeInspector {
//        public void inspect(final CodeEditor codeEditor, final DocumentEvent evt, final MarkerModel markerModel) {
//            PHPParser parser = new PHPParser();
//            parser.setPhp5Enabled(true);
//            parser.addParserListener(new PHPParserListener() {
//                public void parseError(PHPParseErrorEvent e) {
//                    markerModel.addMarker(
//                            codeEditor.getLineStartOffset(e.getBeginLine() - 1) + e.getBeginColumn() - 1,
//                            codeEditor.getLineStartOffset(e.getEndLine() - 1) + e.getEndColumn() - 1,
//                            Marker.TYPE_ERROR, e.getMessage());
//                    System.out.println("Error: " + e.getMessage() + " " + e.getBeginLine() + "," + e.getBeginColumn() + "->" + e.getEndColumn() + "," + codeEditor.getLineStartOffset(e.getBeginLine() - 1));
//                }
//
//                public void parseMessage(PHPParseMessageEvent e) {
//                    markerModel.addMarker(
//                            codeEditor.getLineStartOffset(e.getBeginLine() - 1) + e.getBeginColumn() - 1,
//                            codeEditor.getLineStartOffset(e.getEndLine() - 1) + e.getEndColumn() - 1,
//                            Marker.TYPE_WARNING, e.getMessage());
//                    System.out.println("Warning: " + e.getMessage() + " " + e.getBeginLine() + "," + e.getBeginColumn() + "->" + e.getEndColumn() + "," + codeEditor.getLineStartOffset(e.getBeginLine() - 1));
//                }
//            });
//
//            long start = System.currentTimeMillis();
//            try {
//                markerModel.setAdjusting(true);
//                markerModel.clearMarkers();
//                parser.parse(codeEditor.getText());
//            }
//            catch (ParseException e) {
//                e.printStackTrace();
//            }
//            finally {
//                Runnable runnable = new Runnable() {
//                    public void run() {
//                        markerModel.setAdjusting(false);
//                    }
//                };
//                SwingUtilities.invokeLater(runnable);
//            }
//            System.out.println("Parsing took " + (System.currentTimeMillis() - start) + " ms");
//        }
//    }
}

