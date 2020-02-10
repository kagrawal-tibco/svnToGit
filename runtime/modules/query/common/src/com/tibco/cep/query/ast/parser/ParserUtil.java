package com.tibco.cep.query.ast.parser;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.ParseTree;
import org.antlr.runtime.tree.Tree;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.exception.ParseException;

public class ParserUtil {

    public static final String DEFAULT_ENCODING = "UTF-8";

    private byte[] m_buffer;
    private CommonTree m_ast;
    private ParseTree m_ptree;
    private Logger logger = LogManagerFactory.getLogManager().getLogger(ParserUtil.class);

    public ParserUtil(String query) throws Exception {
       this(query.getBytes(DEFAULT_ENCODING));
    }

    public ParserUtil(InputStream is) throws Exception {
    	DataInputStream ds = new DataInputStream(is);
    	this.m_buffer = new byte[ds.available()];
        ds.readFully(m_buffer);
    }

    public ParserUtil(byte[] queryBytes) {
        this.m_buffer = queryBytes;
    }

    public ParserUtil(File file) throws Exception {
        this(new FileInputStream(file));
    }

    public ParserUtil(URL url) throws Exception {
        this(url.openStream());
    }

    public byte[] getBytes() {
        return m_buffer;
    }

    private void parse() throws Exception {
        ANTLRInputStream is = new ANTLRInputStream(new ByteArrayInputStream(m_buffer), DEFAULT_ENCODING);
        BEOqlLexer lexer = new BEOqlLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        // create a debug event listener that builds parse trees
        // ParseTreeBuilder builder = new ParseTreeBuilder("query");
        BEOqlParser parser = new BEOqlParser(cts);
        BEOqlParser.query_return r = null;
        try {
            r = parser.query();
            logger.log(Level.DEBUG, "Query parsed successfully.");
        } catch (RecognitionException re) {
            String[] tokens = new String[]{re.token.getText()};
            throw new ParseException(parser.getErrorMessage(re, tokens), re.line, re.charPositionInLine);
            //System.out.println(parser.getErrorMessage(re,tokens) + " at "+re.line +":"+re.charPositionInLine);
        } catch(Throwable th) {
            if (th instanceof RuntimeException) {
                Throwable cause = ((RuntimeException)th).getCause();
                if (cause instanceof RecognitionException) {
                    RecognitionException re = (RecognitionException)cause;

                    if (re.token != null) {
                        String[] tokens = new String[]{re.token.getText()};
                        throw new ParseException(parser.getErrorMessage(re, tokens), re.line, re.charPositionInLine);
                    } else {
                        throw re;
                    }
                } else {
                    throw (Exception)cause;
                }
            } else {
            	logger.log(Level.ERROR, "Error parsing the query - " + th.getMessage());
            	throw (Exception) th;
            }
        }
        if (r != null) {
	        CommonTree queryTree = (CommonTree) r.getTree();
	        this.m_ast = queryTree;
        } else {
        	logger.log(Level.WARN, "Something failed during query parsing. Empty/null query return.");
        }
    }
    public CommonTree getAST() throws Exception {
        if(m_ast == null) {
             parse();
        }
        return this.m_ast;
    }

    public String toString() {
        try {
            if(m_ast != null) {
              Tree t = (Tree) getAST();
                if(t.getChildCount() > 0) {
                    return printAST(t.toStringTree());
                } else {
                    if (m_buffer != null)
                        return new String(m_buffer);
                    else
                        return "";
                }
            } else {
                if (m_buffer != null)
                    return new String(m_buffer);
                else
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
     public static void logAST(String ast, Logger logger) {
        StringBuffer sb = new StringBuffer();
        int level=0;
        String lines[] = ast.split(" ");
        for (int i = 0; i < lines.length; i++) {
            boolean newline = false;

            if(lines[i].startsWith("(")){
                level++;
                newline = true;
                //lines[i] = Integer.valueOf(level).toString() + ":" + lines[i];
            }
            if(lines[i].startsWith(")")){
                level--;
                newline = true;
            }
            if(newline) {
                logger.log(Level.DEBUG, sb.toString());
                sb = new StringBuffer();
                //sb.append("\n");
            }
            try {
                for (int j = 0; j < level ; j++) {
                    if(newline)
                      sb.append("  ");
                }

                sb.append(" " +lines[i]);
            } finally {

            }
            if(lines[i].startsWith(")")){
                level--;
            }
        }
        return ;
    }
    private static String printAST(String ast) {
        StringBuffer sb = new StringBuffer();
        int level=0;
        String lines[] = ast.split(" ");
        for (int i = 0; i < lines.length; i++) {
            boolean newline = false;

            if(lines[i].startsWith("(")){
                level++;
                newline = true;
                lines[i] = Integer.valueOf(level).toString() + ":" + lines[i];
            }
            if(lines[i].startsWith(")")){
                newline = false;
            }
            if(newline)
                sb.append("\n");
            try {
                for (int j = 0; j < level ; j++) {
                    if(newline)
                      sb.append("  ");
                }

                sb.append(" " +lines[i]);
            } finally {

            }
            if(lines[i].startsWith(")")){
                level--;
            }
        }
        return sb.toString();
    }

}
