package com.tibco.cep.repo;


import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 6:35:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeployedProject extends Project {


    BEArchiveResourceProvider getBEArchiveResourceProvider();


    SharedArchiveResourceProvider getSharedArchiveResourceProvider();


    JavaArchiveResourceProvider getJavaArchiveResourceProvider();  //todo: No point in storing this. Remove.
    
    
    SMapContentProvider getSMapContenProvider();


    Collection<BEArchiveResource> getDeployedBEArchives();


    public String getName();


    public String getOwner();


    public Date getCreationDate();


    public String getVersion();

    public boolean isCacheEnabled();


    void startHotDeploy(RuleServiceProvider rsp)
            throws Exception;


    void stopHotDeploy(RuleServiceProvider rsp);


    String getRepoPath();

    ClusterConfig getClusterConfig();

    ProcessingUnitConfig getProcessingUnitConfig();
    
    StudioProjectConfiguration getProjectConfiguration();

    Properties getProperties();


	public AbstractOntologyAdapter<EObject> getAbstractOntologyAdapter(AddOnType core);
}
