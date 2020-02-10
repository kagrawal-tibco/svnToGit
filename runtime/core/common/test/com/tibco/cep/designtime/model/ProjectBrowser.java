package com.tibco.cep.designtime.model;



import com.tibco.cep.repo.*;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleSetEx;
import com.tibco.xml.schema.SmElement;
import java.util.*;

public class ProjectBrowser {


    static Workspace space;
    static Project project;

    public static void main(String[] args) {

        try {
            // Get an instance of Workspace. A Workspace is a collection of projects
            space = BEWorkspace.getInstance();

            //Get a project from the Workspace. This is root folder of the designer create project
            // project = space.getProject("/iProcess/projects/samplerepo");
            //You can also specify an ear such C:/Deployed/Demo.ear. This will also work fine.
            //String s = "C:\\TIBCO\\be\\1.2\\examples\\FraudDetection\\FraudDetectionRepo" ;

            String s = "C:/temp/FraudDetection.ear";

            ArrayList providers = new ArrayList();
            providers.add(new JavaArchiveResourceProvider());
            providers.add(new SharedArchiveResourceProvider());
            providers.add(new BEArchiveResourceProvider());

            long startTime = System.currentTimeMillis();
            System.out.print("Project loading ...");

            project = space.loadProject(providers, s);

            long endTime = System.currentTimeMillis();

            System.out.println("done in  " +  (endTime - startTime) + " ms");

            Ontology o = project.getOntology();

            //Get a Ruleset from the Project. Note the forward slashes.
            //You can also browse for the ruleset from the Ontology using o.getRuleset
            //We cast it to RuleSetEx, since this interface gives us all the entities that a Ruleset
            //has.
            Iterator i = o.getRuleSets().iterator();
            while (i.hasNext()) {
                RuleSetEx rset = (RuleSetEx)i.next();
                System.out.println(rset.getFullPath());

                //This call returns all the entities (Concept, Event, Scorecard, TimeEvent) used by the ruleset.
                //You put a chooser dialog or a combobox for selecting a valid Entity.
                Collection entities = rset.getEntities();


                Iterator r = entities.iterator();
                while (r.hasNext()) {
                    //Once an Entity is selected, call the project.getSmElement to get the SmElement.
                    //You can create a VariableTypeDefintion on the fly for a ruleset, and reference the SmElement,
                    //See your functionMapper to use the VariableType.
                    Entity e = (Entity) r.next();
                    SmElement element = project.getSmElement(e);
                    System.out.println(element);
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



}

