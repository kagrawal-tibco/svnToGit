/**
 * 
 */
package com.tibco.cep.decision.table.legacy.validator;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.Ontology;
import com.tibco.cep.decisionproject.persistence.impl.DecisionProjectLoader;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.studio.common.legacy.AbstractResourceVisitor;
import com.tibco.cep.studio.common.legacy.DecisionTableEntryStore;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.common.legacy.OldTableMigrator;
import com.tibco.cep.studio.common.legacy.validator.DefaultLegacyResourceValidatorFactory;
import com.tibco.cep.studio.common.legacy.validator.ILegacyResourceExistenceValidator;
import com.tibco.cep.studio.common.legacy.validator.ILegacyResourceValidatorFactory;
import com.tibco.cep.studio.util.StudioConfig;


/**
 * @author aathalye
 *
 */
public class LegacyOntologyValidator {
	
	//Maintain paths of dt here
	private DecisionTableEntryStore dtStore = new DecisionTableEntryStore();
	
	
	
		
	/**
	 * @param ontology
	 * @param baseProjectPath
	 * @param dpPath
	 * @param compliances
	 * @return
	 * @throws Exception
	 */
	private void validate(Ontology ontology, 
			              String dpPath,
			              String baseProjectPath, 
			              List<LegacyOntCompliance> compliances) throws Exception {
		
		traverseTree(ontology, dpPath, baseProjectPath, compliances);
	}
	
	/**
	 * @param <A>
	 * @param ontology
	 * @param dpPath
	 * @param baseProjectPath
	 * @param compliances
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <A extends AbstractResource> void traverseTree(final Ontology ontology, 
														   String dpPath,
			                                               String baseProjectPath,
			                                               List<LegacyOntCompliance> compliances) throws Exception {
        
        if (ontology == null) {
            throw new IllegalArgumentException("Parameter values cannot be null");
        }

        //Start a pre-order tree traversal.
        Stack<AbstractResourceVisitor<A>> stack =
                new Stack<AbstractResourceVisitor<A>>();
       
        @SuppressWarnings("rawtypes")
		AbstractResourceVisitor<A> prWrapper = new AbstractResourceVisitor(ontology);
        stack.push(prWrapper);

        while (!(stack.isEmpty())) {
            //Get the topmost element
        	AbstractResourceVisitor<A> top = stack.peek();
            //top.setVisited(true);
            //Check if it is visited
            if (!(top.isVisited())) {
                //Set its visited flag to true
                top.setVisited(true);
                //Validate the node
                validate(top, dpPath, baseProjectPath, compliances);
                                
                //Check if it has children
                if (top.hasUnvisitedChildren()) {
                    Iterator<AbstractResourceVisitor<A>> children =
                            top.getUnvisitedChildren();
                    //Push each child onto the stack
                    if (children != null) {
                        while (children.hasNext()) {
                        	AbstractResourceVisitor<A> child = children.next();
                            if (!child.isVisited()) {
                                //Add it to the stack
                                stack.push(child);
                            }
                        }
                    }
                } else {
                    //if no children, pop it off the stack
                    top = stack.pop();
                    top.setVisited(true);
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    private <A extends AbstractResource> void validate(AbstractResourceVisitor<A> dpNode, 
    		 										   String dpPath,
    		                                           String baseProjectPath,
    		                                           List<LegacyOntCompliance> compliances) throws Exception {
        dpNode.setVisited(false);
        //Instantiate appropriate validator here
        ILegacyResourceValidatorFactory factory = DefaultLegacyResourceValidatorFactory.newInstance();
        A wrapped = dpNode.getWrappedResource();
        if (wrapped instanceof Implementation) {
        	//Add the path to the store
        	String folderPath = wrapped.getFolder();
        	String name = wrapped.getName();
        	dtStore.addEntry(name, folderPath);
        } else {
	        Class<A> clazz = (Class<A>)wrapped.getClass();
	        ILegacyResourceExistenceValidator<A> validator = 
	        				factory.create(clazz, dpPath, baseProjectPath);
	        if (validator != null) {
	        	
	        	//TODO optimize this
	    		String validate = StudioConfig.getInstance().getProperty("be.studio.import.dp.validate", "true");
	    		boolean doValidate = Boolean.parseBoolean(validate);
	    		
	    		if (doValidate) {
	    			validator.validate(wrapped, compliances);
	    		}
	        }
        }
    }
    
    /**
     * @return
     */
    public DecisionTableEntryStore getDtStore() {
    	return dtStore;
    }
    
    /**
     * Validate a decision project identified by <b>dpPath</b> with a 
     * project imported identified by <b>baseProjectPath</b>
	 * @param dpPath
	 * @param baseProjectPath
	 * @param compliances
	 * @return
	 * @throws Exception
	 */
	public void validate(String dpPath, 
			             String baseProjectPath,
			             List<LegacyOntCompliance> compliances) throws Exception {
		if (dpPath == null || baseProjectPath == null) {
			throw new IllegalArgumentException("Path for decision project and Studio project cannot be null");
		}
		DecisionProjectLoader dpLoader = DecisionProjectLoader.getInstance();
		//Do The migration here
		String beHome = StudioConfig.getInstance().getProperty("tibco.env.BE_HOME");
		String deleteOrig = StudioConfig.getInstance().getProperty("be.studio.dt.migration.deleteOriginal");
		boolean delete = Boolean.parseBoolean(deleteOrig);
		String transformerPath = new StringBuilder(beHome)
		                         .append(File.separator)
		                         .append("studio")
		                         .append(File.separator)
		                         //.append("packaging")
		                         //.append(File.separator)
		                         .append("bin")
		                         .append(File.separator)
		                         .append("MigrateTable.xsl").toString();
		File transformerFile = new File(transformerPath);
		
		OldTableMigrator.migrateTables(new File(dpPath), transformerFile, "dp", delete);
		OldTableMigrator.migrateTables(new File(dpPath).getParentFile(), transformerFile, "rulefunctionimpl", delete);
		
		dpLoader.loadDecisionProject(dpPath, false);
		DecisionProject dp = dpLoader.getDecisionProject();
		Ontology ontology = dp.getOntology();
		validate(ontology, dpPath, baseProjectPath, compliances);
	}
}
