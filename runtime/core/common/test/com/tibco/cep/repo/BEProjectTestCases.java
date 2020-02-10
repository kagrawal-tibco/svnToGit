package com.tibco.cep.repo;

import com.tibco.cep.designtime.model.MutableOntology;
import com.tibco.cep.designtime.model.element.MutableConcept;
import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.cep.designtime.model.rule.DomainEntry;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.MutableDomainEntry;
import com.tibco.cep.designtime.model.rule.decisiongraph.Column;
import com.tibco.cep.designtime.model.rule.decisiongraph.Columns;
import com.tibco.cep.designtime.model.rule.decisiongraph.DecisionTable;
import com.tibco.cep.designtime.model.rule.decisiongraph.MutableDecisionTable;
import com.tibco.cep.designtime.model.rule.impl.DefaultMutableSymbols;
import com.tibco.cep.repo.mutable.MutableProject;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import junit.framework.TestCase;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 25, 2006
 * Time: 4:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEProjectTestCases extends TestCase {

    Workspace space = null;
    MutableProject project = null;
    MutableOntology ontology = null;

    protected void setUp() throws Exception {

        String s = "R2C3:R34C44";
        String vv[] = s.split("[RC:]+");
        System.out.println(vv);
        deleteDir("C:/Perforce/Repos/TestBackup");
        copyDir("C:/Perforce/Repos/Test", "C:/Perforce/Repos/TestBackup");
        space = BEWorkspace.getInstance();
        project = (MutableProject) space.reloadProject("C:/Perforce/Repos/Test", true);
        ontology = (MutableOntology) project.getOntology();
    }

    protected void tearDown() throws Exception {
        deleteDir("C:/Perforce/Repos/Test");
        copyDir("C:/Perforce/Repos/TestBackup", "C:/Perforce/Repos/Test");
    }


    public void testDT() throws Exception {
        try {
            final MutableDecisionTable dt = (MutableDecisionTable) ontology.getEntity("/dt2");

            DefaultMutableSymbols params = (DefaultMutableSymbols) dt.getInputParameters();
            System.out.println("\n* " + params.size() + " INPUTS:");
            for (Iterator it = params.values().iterator(); it.hasNext(); ) {
                final Symbol s = (Symbol) it.next();
                System.out.print("  - "+s.getName() + " (" +s.getType()+")");
                final Domain domain = s.getDomain();
                if (null != domain) {
                    for (Iterator it2 = domain.iterator(); it2.hasNext(); ) {
                        final MutableDomainEntry entry = (MutableDomainEntry) it2.next();
                        System.out.print(" / " + entry.getName());
                        entry.setName(entry.getName() + "_test");
                    }
                    System.out.println(".");
                }
            }
            params = (DefaultMutableSymbols) dt.getOutputParameters();
            System.out.println("\n* " + params.size() + " OUTPUTS:");
            for (Iterator it = params.values().iterator(); it.hasNext(); ) {
                final Symbol s = (Symbol) it.next();
                System.out.print("  - "+s.getName() + " (" +s.getType()+")");
                final Domain domain = s.getDomain();
                if (null != domain) {
                    for (Iterator it2 = domain.iterator(); it2.hasNext(); ) {
                        final DomainEntry entry = (DomainEntry) it2.next();
                        System.out.print(" / " + entry.getName());
                    }
                    System.out.println(".");
                }
            }
            this.showColumns(dt);


            System.out.println("\n* AUTOCOMPLETE\n");
            dt.autocomplete();
            this.showColumns(dt);

            System.out.println("\n* COMPRESS\n");
            dt.compress();
            this.showColumns(dt);

            System.out.println("\n* RULE LANGUAGE:");
            System.out.println(dt.getRuleLanguage());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        System.out.println("Done.");
    }

    private void showColumns(DecisionTable dt) {
        final Columns cols = dt.getColumns();
        System.out.println("\n* " + cols.size() + " COLUMNS:");
        for (Iterator it = cols.iterator(); it.hasNext(); ) {
            final Column c = (Column) it.next();
            Map m = c.getConditionValues();
            System.out.println("  ---");
            System.out.println("    " + m.size() + " condition values:");
            for (Iterator it2 = m.keySet().iterator(); it2.hasNext(); ) {
                final Symbol key = (Symbol) it2.next();
                final DomainEntry value = (DomainEntry) m.get(key);
                final String valueName = (null == value) ? null : value.getName();
                System.out.println("      " + key.getName() + " = " + valueName);
            }
            m = c.getActionValues();
            System.out.println("    " + m.size() + " action values:");
            for (Iterator it2 = m.keySet().iterator(); it2.hasNext(); ) {
                final Symbol key = (Symbol) it2.next();
                final DomainEntry value = (DomainEntry) m.get(key);
                final String valueName = (null == value) ? null : value.getName();
                System.out.println("      " + key.getName() + " = " + valueName);
            }
        }
    }



    public void testGlobalVariables() throws Exception {
        try {
            final GlobalVariablesProvider gvp = (GlobalVariablesProvider) project.getGlobalVariables();
            Collection c = gvp.getVariables();
            GlobalVariableDescriptor gv;
            for (Iterator it = c.iterator(); it.hasNext();) {
                gv = (GlobalVariableDescriptor) it.next();
                System.out.println(gv.getFullName() + " ("+gv.getType()+"): " + gv.getPath() + " - " + gv.getName() + " = " + gv.getValueAsString()
                        + " - mod=" + gv.getModificationTime()
                        + " - dep=" + gv.isDeploymentSettable()
                        + " - srv=" + gv.isServiceSettable());
            }
            gv = gvp.getVariable("Deployment");
            if (! (gv.getName().equals("Deployment")
                    && gv.getType().equals("String")
                    && gv.getValueAsString().equals("Test")
                    && gv.isDeploymentSettable()
                    && (!gv.isServiceSettable())
                    && (gv.getModificationTime() > 0))) {
                throw new Exception("Invalid value");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    public void testConceptCreateSaveDeleteSave() {
        try {
            final MutableConcept concept = ontology.createConcept("/Ontology", "NewConcept", null, false);
            project.save();
            System.out.println("Saved");

            concept.delete();
            project.save();
            System.out.println("Deleted");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    public void testEntitySave() {
        try {
            Entity e = ontology.getEntity("/Rules", "CalculateRate");
            MutableDecisionTable table = (DecisionTable) e;

            Action a = table.newAction();
            a.setExpression("ActWorld");
            a.getCells().appendCell();
            a.getCells().getCell(0).setCellValue("A1");
            a.getCells().getCell(1).setCellValue("A2");

            Condition c = table.newCondition();
            c.setExpression("CondiontExpr");
            c.getCells().appendCell();
            c.setDomain("C1;C2");
            c.getCells().getCell(0).setCellValue("C1");
            c.getCells().getCell(1).setCellValue("C2");

            table.setXLFormatting("DecisionTable".getBytes());

            project.save();
            System.out.println("Saved");


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* *
     * Entity Operations are tested.
     * @throws Exception
     * /
    public void testEntityGets() throws Exception {

        for (Object obj : ontology.getEntities()) {
            System.out.println(obj.getClass().getSimpleName() + " " + ((Entity) obj).getFullPath());
        }

        Entity e = ontology.getEntity("/Rules", "CalculateRate");
        DecisionTable table = (MutableDecisionTable) e;

        for (Iterator r = table.getConditions().iterator(); r.hasNext(); ) {
            Condition c = (Condition)r.next();
            System.out.println(c.getCells());
            List dv = c.getAllDomainValues();
            System.out.println(dv);
        }
        TestCase.assertNotNull(e);
    }

    public void testRuleSetSave() throws Exception {
        Folder f = ontology.getFolder("/Rules");
        MutableRuleSetEx rset = (RuleSetEx)ontology.createRuleSet(f, "TestRuleSet", true);
        MutableDecisionTable table = (DecisionTable)rset.createRule("dt1", true, RuleSetEx.RULE_DECISION_TABLE_TYPE);
        Condition c = table.newCondition();
        c.setExpression("Hi");
        c.setDomain("D");
        c.getCells().getCell(0).setCellValue("tt");

        Action a = table.newAction();
        a.setExpression("expr");
        a.getCells().getCell(0).setCellValue("vv");
        project.save();


    }
     */

    public void testEntityModify() throws Exception {

    }

    public void testEntityCreate() throws Exception {

    }

    public void testEntityRename() throws Exception {

    }

    public void testEntityMove() throws Exception {

    }

    public void testEntityDelete() throws Exception {

    }

    /**
     * Folder Operation Test
     * @throws Exception
     */
    public void testFolderCreate() throws Exception {

    }

    public void testFolderRename() throws Exception {

    }

    public void testFolderMoved() throws Exception {

    }

    public void testFolderDelete() throws Exception {

    }

    /*
    public static void main(String args[]) {

        try {
            BEWorkspace space = BEWorkspace.getInstance();
            BEProject project = space.loadProject("C:/Perforce/Repos/Test");
            Ontology ontology = project.getOntology();

            //Test Modify
            Entity e = ontology.getEntity("/", "RuleSet");
            e.setDescription("As per Nicolas - This is my new Description - " + System.currentTimeMillis());
            //project.updateEntity(e);

            //create test
            e = ontology.createConcept("/", "testconcept", null, false);
            //project.newEntity(e);
            e.setDescription("New Concept");
            //project.updateEntity(e);

            project.save();

            //rename test

            e.setName("bbconcept", false);
            //project.renameEntity(e, "testconcept", "bbconcept");
            project.save();

            //move test
            //project.moveEntity(e, "/", "/test");

            e.setFolderPath("/test");
            //project.moveEntity(e, "/", "/test");
            project.save();
            //delete test
            //e.delete();
            //project.deleteEntity(e);
            //project.save();

            e = ontology.getFolder("/test");
            //e.delete();
            //project.deleteEntity(e);
            e.setName("newName", false);

            //project.renameEntity(e, "test", "newName");
            project.save();

        }

        catch (Exception e) {
            e.printStackTrace();
        }


    }

    */





    static public void unzip(String zipfile, String destdir) {

        final class Expander extends Expand {
            public Expander() {
                project = new org.apache.tools.ant.Project();
                project.init();
                taskType = "unzip";
                taskName = "unzip";
                target = new Target();
            }
        }
        Expander expander = new Expander();
        expander.setSrc(new File(zipfile));
        expander.setDest(new File(destdir));
        expander.execute();

    }

    static public void copyDir(String srcDir, String destDir) {

        final class CopyDir extends Copy {
            public CopyDir() {
                project = new org.apache.tools.ant.Project();
                project.init();
                taskType = "copy";
                taskName = "copy";
                target = new Target();
            }
        }

        CopyDir copytask = new CopyDir();
        copytask.setTodir(new File(destDir));
        FileSet fs = new FileSet();
        fs.setDir(new File(srcDir));
        copytask.addFileset(fs);
        copytask.execute();
    }

    static public void deleteDir(String dir) {

        final class DeleteDir extends Delete {
            public DeleteDir() {

                project = new org.apache.tools.ant.Project();
                project.init();
                taskType = "delte";
                taskName = "delete";
                target = new Target();

            }
        }

        DeleteDir deltask =  new DeleteDir();
        deltask.setDir(new File(dir));
        deltask.execute();
    }
}
