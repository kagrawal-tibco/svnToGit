package com.tibco.be.bw.plugin;

import com.tibco.be.bw.plugin.ui.BERuleServiceProviderSharedConfigUI;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.bw.store.RepoAgent;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.Project;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.emf.DeployedEMFProject;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.object.ObjectFactory;
import com.tibco.objectrepo.object.ObjectProvider;
import com.tibco.objectrepo.object.ObjectReadException;
import com.tibco.pe.plugin.GlobalVariablesUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 15, 2004
 * Time: 2:45:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEBWUtils {

    public static final String RSP_VFILE_EXTENSION = "sharedrsp";
    public static final ExpandedName XNAME_CONFIG = ExpandedName.makeName("config");

    public static final Object GUARD = new Object();

    protected static final ConcurrentHashMap<String, EMFProject> URL_TO_PROJECT = new ConcurrentHashMap<String,EMFProject>();


    public static SmElement getElement(String repoUrl, RepoAgent repoAgent, XiNode config) {
        String en= XiChild.getString(config,BEPluginConstants.ENTITYNAME);
        String ns= XiChild.getString(config,BEPluginConstants.ENTITYNS);
        return getElement(repoUrl, repoAgent, ns, en);
    }


    public static SmElement getElement(String repoUrl, RepoAgent repoAgent, String ns, String en) {
        //System.out.println("Searching SmElement For:" + en + "::ns=" + ns);
        if ((null != repoUrl) && (!repoUrl.trim().isEmpty()) && (ns != null) && (!ns.trim().equals(""))){
            ns=RDFTnsFlavor.BE_NAMESPACE+ns;
            final EMFProject project;
            try {
                project = getProject(repoUrl);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            final EMFTnsCache cache = (EMFTnsCache) project.getTnsCache();
            final SmNamespaceProvider snp = cache.getSmNamespaceProvider();
            SmNamespace found = snp.getNamespace(ns);
//            for (Iterator itr = found.getComponents(SmElement.ELEMENT_TYPE); itr.hasNext();)
//            {
//                SmComponent comp = (SmComponent) itr.next();
//                System.out.println("comp = " + comp.getName());
//            }
            return (SmElement) found.getComponent(SmComponent.ELEMENT_TYPE,en);
        }
        return null;
    }


    public static EMFProject getProject(String repoUrl) throws Exception {
        if ((null == repoUrl) || repoUrl.isEmpty()) {
            return null;
        }
        synchronized (GUARD) {
            EMFProject project = URL_TO_PROJECT.get(repoUrl);

            if (null == project) {
                synchronized (RDFTnsFlavor.TNS_CACHE_INIT_CONTEXT) {

                    // About to load a DeployedEMFProject, so we need to unset the BW context.
                    final String tnsCacheInitContext = System.getProperty(RDFTnsFlavor.TNS_CACHE_INIT_CONTEXT);
                    System.setProperty(RDFTnsFlavor.TNS_CACHE_INIT_CONTEXT, "");

                    try {
                        final Properties props = new Properties();
                        props.setProperty("tibco.repourl", repoUrl);
                        project = new DeployedEMFProject(props, new ChangeListener() {
                            @Override
                            public void notify(ChangeEvent e) {
                            }
                        });
                        project.load();
                    }
                    catch (Exception e) {
                        return null;

                    }
                    finally {
                        // Reset to initial context.
                        if (null != tnsCacheInitContext) {
                            System.setProperty(RDFTnsFlavor.TNS_CACHE_INIT_CONTEXT, tnsCacheInitContext);
                        }
                    }

                }

                URL_TO_PROJECT.put(repoUrl, project);
            }

            return project;
        }
    }


    public static String[] getNsNm(String entityRef, String rspRef, RepoAgent repoAgent) throws Exception {
        if ((null == entityRef) || entityRef.isEmpty()) {
            return new String[]{"", ""};
        } else {
            final String repoUrl = getRepoUrl(rspRef, repoAgent);
            return getNsNm(entityRef, repoUrl);
        }
    }

    
    public static String[] getNsNm(String entityRef, String repoUrl) throws Exception {
        final String[] nsNm = new String[]{"", ""};

        final Entity e;
        if ((null == entityRef) || entityRef.isEmpty()) {
            e = null;

        } else {
            final Project project = BEBWUtils.getProject(repoUrl);
            if (null == project) {
                e = null;
            } else {
                e = project.getOntology().getEntity(entityRef);
            }
        }

        if (e != null) {
            nsNm[0] = e.getFullPath();
            nsNm[1] = e.getName();
        }

        return nsNm;
    }


    public static String getRepoUrl(String rspRef, RepoAgent repoAgent)  throws ObjectRepoException {
        final XiNode node = getRuntimeConfig(rspRef, repoAgent);
        if (null == node) {
            return null;
        }

        final String repoUrl = XiChild.getString(node, BEPluginConstants.XNAME_REPOURL);
        return repoUrl;
    }

    public static RspConfigValues getRspConfigValues(String rspRef, RepoAgent repoAgent)  throws ObjectRepoException {
        final XiNode node = getRuntimeConfig(rspRef, repoAgent);
        if (null == node) {
            return new RspConfigValues(null, null, null);
        }
        
        final String repoUrl = XiChild.getString(node, BEPluginConstants.XNAME_REPOURL);
        final String cddUrl = XiChild.getString(node, BEPluginConstants.XNAME_CDD);
        final String puid = XiChild.getString(node, BEPluginConstants.XNAME_PUID);
        return new RspConfigValues(cddUrl, puid, repoUrl);
    }



    public static RspConfigValues getRspConfigValues(XiNode config, RepoAgent repoAgent)  throws ObjectRepoException {
        final ObjectProvider provider = repoAgent.getObjectProvider();
        config = GlobalVariablesUtils.resolveGlobalVariables(config, provider,
                provider.getProjectId(repoAgent.getVFileFactory()));
        final String rspRef = XiChild.getString(config, BEPluginConstants.RSPREF);
        return getRspConfigValues(rspRef, repoAgent);
    }


    protected static XiNode getRuntimeConfig(String rspRef, RepoAgent repoAgent) throws ObjectRepoException {
        if ((null == rspRef) || rspRef.trim().isEmpty()) {
            return null;
        }

        final ObjectProvider provider = repoAgent.getObjectProvider();
        final ObjectFactory factory = provider.getFactory(XiNode.class, BEBWUtils.RSP_VFILE_EXTENSION);
        if (null == factory) {
            final RSPXiNodeObjectFactory objFactory = new RSPXiNodeObjectFactory();
            provider.registerFactory(BEBWUtils.RSP_VFILE_EXTENSION, XiNode.class.getName(), objFactory, true);
        }

        final String uri = repoAgent.getRepoURI(rspRef);
        Object o;
        try {
            o = repoAgent.getObjectProvider().getShallow(uri, Object.class);
        } catch (ObjectReadException e) {
            o = null;
        }
        
        XiNode config;
        if (o instanceof BERuleServiceProviderSharedConfigUI) {
            config = ((BERuleServiceProviderSharedConfigUI) o).getConfiguration();
        } else {
            config = (XiNode) provider.getObject(uri, XiNode.class, true);
            config = XiChild.getChild(config, BEBWUtils.XNAME_CONFIG);            
        }
        config = GlobalVariablesUtils.resolveGlobalVariables(config, provider,
                provider.getProjectId(repoAgent.getVFileFactory()));
        return config;
    }
    
}
