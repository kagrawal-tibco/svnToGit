package com.tibco.cep.repo.hotdeploy;

import java.util.ArrayList;

import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedBEProject;
import com.tibco.infra.repository.RepoCommitListener;
import com.tibco.objectrepo.vfile.tibrepo.RepoVFileFactory;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Jul 06, 2006
 * Time: 5:56:49 PM
 * To change this template use File | Settings | File Templates.
 */

public class RepoDeployer implements  RepoCommitListener, HotDeployer {
    protected DeployedBEProject project;
    protected ChangeListener changeListener;

    public RepoDeployer (DeployedBEProject project, ChangeListener changeListener) {
        this.project = project;
        this.changeListener = changeListener;
    }

    public void onCommitEvent(com.tibco.infra.repository.RepoEvent[] repoEvents) {
        RepoChangeEvent dce = new RepoChangeEvent();
        dce.addChangedProject(project.getRepoPath());
        changeListener.notify(dce);
    }

    public void onRollbackEvent(com.tibco.infra.repository.RepoEvent[] repoEvents) {
    }

    class RepoChangeEvent implements ChangeListener.ChangeEvent
    {
        ArrayList changedProject = new ArrayList ();

        public void addChangedProject(String repopath) {
            changedProject.add(repopath);
        }

        public String[] getChangedProjects() {
            String [] ret = new String [changedProject.size()];
            changedProject.toArray(ret);
            return ret;
        }

        public String[] getDeleted() {
            return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String[] getAdded() {
            return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public void start() throws Exception {
        ((RepoVFileFactory) project.getVFileFactory()).getRepoClient().addCommitListener(this);
    }

    public void stop() {
        ((RepoVFileFactory) project.getVFileFactory()).getRepoClient().removeCommitListener(this);
    }
}
