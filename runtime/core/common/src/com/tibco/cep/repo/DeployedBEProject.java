package com.tibco.cep.repo;


import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.hotdeploy.DirectoryDeployer;
import com.tibco.cep.repo.hotdeploy.HotDeployer;
import com.tibco.cep.repo.hotdeploy.RepoDeployer;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.JavaArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.SMapContentProviderImpl;
import com.tibco.cep.repo.provider.impl.SharedArchiveResourceProviderImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.util.ResourceManager;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.tibrepo.RepoVFileFactory;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 1:37:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeployedBEProject extends BEProject implements DeployedProject {


    JavaArchiveResourceProvider jarProvider;
    SharedArchiveResourceProvider sharedResourceProvider;
    BEArchiveResourceProvider beArchiveProvider;
    SMapContentProvider smapContentProvider;

    String name;
    String version;
    String owner;
    Date createDt;
    Properties env;
    String repoPath;
    ChangeListener changeListener;
    boolean hotDeployEnabled;
    HotDeployer hotDeployer;

    public DeployedBEProject(
            Properties _env,
            ChangeListener _changeListener)
            throws Exception {
        super(_env.getProperty("tibco.repourl"));
        env = _env;
        changeListener = _changeListener;
        repoPath = env.getProperty("tibco.repourl");
        this.vfFactory = (VFileFactory) env.get("tibco.repoFactory");  // BWClient - XML Canon Bug

        jarProvider = new JavaArchiveResourceProviderImpl();
        sharedResourceProvider = new SharedArchiveResourceProviderImpl();
        beArchiveProvider = new BEArchiveResourceProviderImpl();

        providerFactory.registerProvider(jarProvider);
        providerFactory.registerProvider(sharedResourceProvider);
        providerFactory.registerProvider(beArchiveProvider);
        if (Boolean.valueOf(System.getProperty(SystemProperty.DEBUGGER_SERVICE_ENABLED.getPropertyName()).trim())) {
    		smapContentProvider = new SMapContentProviderImpl();
    		providerFactory.registerProvider(smapContentProvider);
        }

    }

    private void checkHotDeployment(RuleServiceProvider serviceProvider) throws Exception {
        hotDeployEnabled = ((BEProperties) env).getBoolean("be.engine.hotDeploy.enabled", false);
        final Logger logger = serviceProvider.getLogger(this.getClass());
        if(hotDeployEnabled) {
            if( ((BEClassLoader)serviceProvider.getTypeManager()).useJDIforHotdeploy()
                    && ((BEProperties)env).getBoolean("tibco.env.be.debug", false)) {
                logger.log(Level.WARN, ResourceManager.getInstance().getMessage("hotdeploy.debugger"));
            }
            else {
                String msg = ((BEClassLoader)serviceProvider.getTypeManager()).classesNotRedefineableMsg();
                if(msg == null) {
                    logger.log(Level.INFO, ResourceManager.getInstance().formatMessage("hotdeploy.enabled",
                            ((BEClassLoader)serviceProvider.getTypeManager()).hotdeployMethod()));
                }
                else {
                    logger.log(Level.WARN, msg);
                    logger.log(Level.WARN, ResourceManager.getInstance().getMessage("hotdeploy.noModify"));
                }
            }
        }
        else {
            logger.log(Level.INFO, ResourceManager.getInstance().getMessage("hotdeploy.disabled"));
        }
    }

    public void load() throws Exception {
        if (vfFactory == null) {
            vfFactory = VFileHelper.createVFileFactory(repoPath, null);
            if (vfFactory.getRootDirectory().getChildCount() == 0)
                throw new Exception("Project " + repoPath + " is empty");
        }

        super.load();
        globalvariables.overwriteGlobalVariables(env);
        globalvariables.validateGlobalVariables();
        this.name = this.globalvariables.getProjectName();
        this.version = this.globalvariables.getProjectVersion();
        this.owner = this.globalvariables.getProjectOwner();
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public Date getCreationDate() {
        return createDt;
    }

    public String getVersion() {
        return version;
    }


    public Collection getDeployedBEArchives() {
        return beArchiveProvider.getBEArchives();
    }


    public BEArchiveResourceProvider getBEArchiveResourceProvider() {
        return beArchiveProvider;
    }


    public SharedArchiveResourceProvider getSharedArchiveResourceProvider() {
        return sharedResourceProvider;
    }

    public JavaArchiveResourceProvider getJavaArchiveResourceProvider() {
        return jarProvider;
    }
    
	public SMapContentProvider getSMapContenProvider() {
		return smapContentProvider;
	}	
    
    
/*
    public static void main(String[] s) {
        try {
            DeployedProject prj = (DeployedProject) new DeployedBEProject().loadProject(s[0]);
            Collection<BEArchiveResource> beArchives = prj.getDeployedBEArchives();
            for (BEArchiveResource res : beArchives) {
                System.out.println(res.getName());
                System.out.println(res.getCacheConfig());
                System.out.println(res.getDeployedRuleSets());
                System.out.println(res.getListenDestinations());

                prj.wait();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    

	public void startHotDeploy(RuleServiceProvider serviceProvider) throws Exception {
        checkHotDeployment(serviceProvider);
        if (hotDeployEnabled) {
            if (this.vfFactory instanceof RepoVFileFactory) {
                hotDeployer = new RepoDeployer(this, changeListener);
            }
            else {
                    hotDeployer = new DirectoryDeployer(this, changeListener);
                }
            hotDeployer.start();
            serviceProvider.getLogger(this.getClass()).log(Level.INFO, "Started Hot Deployer");// + hotDeployer.getClass().getName());
        }
    }

    public void stopHotDeploy(RuleServiceProvider serviceProvider) {
        if (hotDeployEnabled && hotDeployer != null) {
            hotDeployer.stop();
            serviceProvider.getLogger(this.getClass()).log(Level.INFO, "Stopped Hot Deployer...");
        }
    }

    @Override
    public Properties getProperties() {
        return env;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public boolean isCacheEnabled() {
        String standaloneStr =
                System.getProperty(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(),
                        Boolean.FALSE.toString());
        boolean standalone = Boolean.parseBoolean(standaloneStr);
        if(standalone){
            return false;
        }

        boolean cacheEnabled = false;
        GlobalVariables gvs = getGlobalVariables();

        Collection archives = getDeployedBEArchives();
        for(Iterator itb = archives.iterator();itb.hasNext();) {
            BEArchiveResource bar = (BEArchiveResource) itb.next();
            Properties cacheConfig = new Properties();

            for (Iterator it = bar.getCacheConfig().entrySet().iterator(); it.hasNext();) {
                final Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() instanceof java.lang.String) {
                    cacheConfig.put(entry.getKey(), gvs.substituteVariables(entry.getValue().toString()).toString());
                } else {
                    cacheConfig.put(entry.getKey(), entry.getValue());
                }
            }

            if (Constants.PROPERTY_NAME_OM_TANGOSOL.equals(cacheConfig.getProperty(Constants.XNAME_TYPE.localName))) {
                cacheEnabled = true;
            }
        }

        return cacheEnabled;
    }


    public ClusterConfig getClusterConfig() {
        return null;
    }


    public ProcessingUnitConfig getProcessingUnitConfig() {
        return null;
    }

	@Override
	public AbstractOntologyAdapter<EObject> getAbstractOntologyAdapter(AddOnType core) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudioProjectConfiguration getProjectConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
	
}
