package com.tibco.be.dbutils;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.Workspace;


/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Feb 11, 2005
 * Time: 1:57:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class RepoAccess {

    protected BEProject project;

    protected RepoAccess(BEProperties beprops, Logger logger) throws Exception {
        String repoUrl = beprops.getString("tibco.repourl");
        String pwd = beprops.getString("tibco.password");
        if (repoUrl != null) {
            final Workspace workspace = (Workspace) Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace")
                    .getMethod("getInstance").invoke(null);
            project = (BEProject) workspace.loadProject(repoUrl);
            if (!project.isValidDesignerProject()) {
                logger.log(Level.FATAL, "Could not find valid Designer project");
                System.exit(1);
            }
        }
    }

    protected Ontology ontology() {
        return project.getOntology();
    }
}
