package com.tibco.cep.repo.hotdeploy;

import java.io.File;
import java.util.ArrayList;

import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedProject;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Dec 20, 2005
 * Time: 1:42:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryDeployer implements Runnable, HotDeployer {
    Thread          m_pollingThread;
    protected ChangeListener changeListener;
    boolean start = true;
    private long pollingFrequency;

    String repoPath;
    long   m_lastMod;
    long   m_length;

    public DirectoryDeployer (DeployedProject project, ChangeListener changeListener) {
        this.repoPath = project.getRepoPath();
        this.changeListener = changeListener;
        this.pollingFrequency = Long.valueOf(
                project.getProperties().getProperty("be.engine.hotDeploy.pollingfrequency", "5000"));
        File ear = new File(repoPath);
        m_lastMod = ear.lastModified();
        m_length  = ear.length();
        m_pollingThread = new Thread(this, "HotDeployPoller");
    }

    public void start() {
        if (m_pollingThread != null)
//            System.out.println("Starting hot deployer..."); // todo change to logger
            m_pollingThread.start();
    }

    public void run() {
        ///Trace trace = serviceProvider.getTrace();
        while(start) {
            try {
                Thread.sleep(pollingFrequency);
                poll();
            }
            catch(Exception ex) {
                System.out.println(ex.toString());  // todo change to logger
                ///trace.traceException(ex);
                start = false;
            }
        }
    }

    private void poll() {
        File newEar = new File(repoPath);
        long newLastMod = newEar.lastModified();
        long newLength  = newEar.length();

        if(newLastMod != m_lastMod || newLength != m_length) {
            //new ear detected
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                long newAgainLastMod = newEar.lastModified();
                long newAgainLength  = newEar.length();

                if(newAgainLastMod == newLastMod && newAgainLength == newLength) {
                    //stable - assume no change in 1 sec is stable enough to take the file
                    break;
                }
                else {
                    newLastMod = newAgainLastMod;
                    newLength  = newAgainLength;
                }
            }
            m_lastMod = newLastMod;
            m_length  = newLength;

            DirectoryChangeEvent dce = new DirectoryChangeEvent();
            dce.addChangedProject(repoPath);
            changeListener.notify(dce);
        }
    }

    public void stop() {
        start = false;
    }

    class DirectoryChangeEvent implements ChangeListener.ChangeEvent
    {
        ArrayList changedProjects = new ArrayList ();

        public void addChangedProject(String earpath) {
            changedProjects.add(earpath);
        }

        public String [] getChangedProjects() {
            String [] tmp = new String [changedProjects.size()];
            changedProjects.toArray(tmp);
            return tmp;
        }

        public String[] getDeleted() {
            return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String[] getAdded() {
            return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
