package com.tibco.cep.query.test.model01;

import com.tibco.cep.query.ast.parser.ParserUtil;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.impl.DistinctClauseImpl;
import com.tibco.cep.query.model.impl.NamedSelectContextImpl;
import com.tibco.cep.query.utils.BaseObjectTreeAdaptor;
import com.tibco.cep.query.utils.DotTreeWalker;
import org.antlr.runtime.tree.*;
import org.antlr.stringtemplate.StringTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 2, 2007
 * Time: 4:37:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true )
public class TestParser {
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



    public TestParser() {
    }

    public TestParser(String name) {
    }


    @Test
    public void testParser() {
        try {
            ParserUtil pu = new ParserUtil(new FileInputStream(this.getTestDirectory().getPath() + "/TestB.sql") );
            Tree ast = (Tree) pu.getAST();
            DOTTreeGenerator gen = new DOTTreeGenerator() {
                protected StringTemplate getNodeST(TreeAdaptor adaptor, Object t) {
                    String text = adaptor.getText(t);
                    StringTemplate nodeST = _nodeST.getInstanceOf();
                    String uniqueName = "n"+getNodeNumber(t);
                    nodeST.setAttribute("name", uniqueName);
                    if (text!=null) text = text.replaceAll("(\\\\|\")","\\\\$1");
                    nodeST.setAttribute("text", text);
                    return nodeST;
                }
            };
            StringTemplate st = gen.toDOT(ast, new CommonTreeAdaptor(), com.tibco.cep.query.test.model01.TestParser._treeST, DOTTreeGenerator._edgeST);
            File dotf = new File(this.getTestDirectory().getPath() +"/BeOql.dot");
            FileOutputStream fos = new FileOutputStream(dotf);
            fos.write(st.toString().getBytes());
            fos.flush();
            fos.close();
            System.out.println(st);
            System.out.println(ast.toStringTree());
            //System.out.println(ast.toStringTree());
            final NamedSelectContext sc = new NamedSelectContextImpl(null, "ROOT", null);
            final CommonTree distinctTree = (CommonTree) ((CommonTree)ast).getFirstChildWithType(BEOqlParser.TOK_DISTINCT);
            if (null != distinctTree) {
                new DistinctClauseImpl(sc, distinctTree);
            }
            //ASTWalker.walkSelectClause((CommonTree) ast, sc);
            DotTreeWalker dtw = new DotTreeWalker();
            st = dtw.toDOT(sc, new BaseObjectTreeAdaptor(), com.tibco.cep.query.test.model01.TestParser._treeST, DOTTreeGenerator._edgeST);
            dotf = new File(this.getTestDirectory().getPath() +"/pa.dot");
            fos = new FileOutputStream(dotf);
            fos.write(st.toString().getBytes());
            fos.flush();
            fos.close();

        } catch(Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

     protected File getTestDirectory() throws IOException {
        final File dir = new File(TestNGBase.ROOT_TEST_DIRECTORY_PATH, this.getTestDirectoryName());
        if (!(dir.exists() && dir.isDirectory())) {
            throw new IOException("Invalid test directory name");
        }
        return dir;
    }

    protected String getTestDirectoryName() {
        return "ParserTest";
    }


    protected String getRuleServiceProviderName() {
        return "TestQuery";
    }


    protected String getRuleSessionName() {
        return "BusinessEvents Archive";
    }
}
