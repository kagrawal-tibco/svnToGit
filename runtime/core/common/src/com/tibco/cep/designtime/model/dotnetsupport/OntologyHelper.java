package com.tibco.cep.designtime.model.dotnetsupport;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import com.tibco.be.model.functions.impl.ModelFunctionsFactory;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.RuleFunction;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jan 10, 2006
 * Time: 9:20:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class OntologyHelper {


    private static final HashSet vFileExtensions = new HashSet(Arrays.asList(new String[]{".concept", ".event", ".time", ".ruleset", ".scorecard", ".rulefunction"}));


    public static Ontology loadOntologyFromPath(String path) {
        DefaultMutableOntology ontology = null;
        System.out.println("loadOntology ------------");
        try {

            ontology = new DefaultMutableOntology();
            System.out.println("Calling method loadOntology --------" + path);
            File repoDir = new File(path);
            addFilesAndSubDirs(repoDir, ontology);
            System.out.println("Calling method loadOntology...II");

            ModelFunctionsFactory.loadOntology(ontology);
        } catch (Exception e) {
            System.err.println("Exception occured while loading" + e.getMessage());
            e.printStackTrace();
            //System.exit(1);
        }
        System.err.println("returning ontology" + ontology);
        return ontology;

    }


//    public static void loadModelFunctions(MutableOntology ontology) throws Exception {
//        ModelFunctionsFactory.loadOntology(ontology);
//    }


    private static void addFilesAndSubDirs(File currDir, DefaultMutableOntology ontology) {
        if (!currDir.isDirectory()) {
            return;
        }

        File[] files = currDir.listFiles();

        for (int ii = 0; ii < files.length; ii++) {
            File file = files[ii];
            if (file.isDirectory()) {
                addFilesAndSubDirs(file, ontology);
            } else if (matchesVFileExtension(file)) {
                try {
                    Entity entity = DefaultMutableOntology.deserializeEntity(new BufferedInputStream(new FileInputStream(file)));
                    ((MutableEntity)entity).setOntology(ontology);
                } catch (FileNotFoundException fnfe) {
                    continue;
                }
            }
        }
    }


    private static boolean matchesVFileExtension(File file) {
        String filename = file.getName();
        return vFileExtensions.contains(filename.substring(filename.lastIndexOf('.')));
    }


    //returns null if an IOException is thrown
    public static String serializeEntity(Entity entity) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            entity.serialize(baos);
            String str = baos.toString("UTF-8");
            if (entity instanceof RuleFunction) {
                System.out.println(str);
            }
            return str;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }


//    public static void main(String[] args) {
//        Ontology o = loadOntologyFromPath("C:/Documents and Settings/aamaya.AAMAYA-NB/Desktop/arraytest");
//        Entity e = o.getEntity("/RuleSet", "Rule");
//        //System.err.println(e.getFullPath());
//        Collection c = o.getEntities();
//        for (Iterator it = c.iterator(); it.hasNext();) {
//            Entity ee = (Entity) it.next();
//            System.err.println(ee.getFullPath());
//        }
//
//    }
}
