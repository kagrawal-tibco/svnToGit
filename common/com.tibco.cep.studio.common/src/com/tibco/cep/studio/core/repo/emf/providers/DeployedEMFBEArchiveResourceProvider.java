package com.tibco.cep.studio.core.repo.emf.providers;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.be.util.config.cdd.MmAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.provider.adapters.CacheAgentToBEArchiveAdapter;
import com.tibco.cep.repo.provider.adapters.DashboardAgentToBEArchiveAdapter;
import com.tibco.cep.repo.provider.adapters.InferenceAgentToBEArchiveAdapter;
import com.tibco.cep.repo.provider.adapters.LiveViewAgentToBEArchiveAdapter;
import com.tibco.cep.repo.provider.adapters.ProcessAgentToBEArchiveAdapter;
import com.tibco.cep.repo.provider.adapters.QueryAgentToBEArchiveAdapter;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.repo.emf.DeployedEMFProject;

/**
 * Runtime provider class for deployed BAR.
 * 
 * <p>
 * Do not add Eclipse UI code here. This will get executed outside eclipse
 * </p>
 * @author aathalye
 *
 */
public class DeployedEMFBEArchiveResourceProvider
        extends EMFBEArchiveResourceProvider {

    static {
        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        final EPackage.Registry i = EPackage.Registry.INSTANCE;
        i.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore", ModelPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore", IndexPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/element", ElementPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/event", EventPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/service/channel", ChannelPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/rule", RulePackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/states", StatesPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/archive", ArchivePackage.eINSTANCE);
    }

    protected static final FilenameFilter FILENAME_FILTER_FOR_IDX_FILE = new FilenameFilter() {
        public boolean accept(
                File dir,
                String name) {
            return name.endsWith(CommonIndexUtils.IDX_EXTENSION);
        }
    };

    protected static final FileFilter FILE_FILTER_FOR_INDEX_DIR = new FileFilter() {
        public boolean accept(File file) {
            return file.isDirectory() && file.getName().equals("index");
        }
    };




    @Override
    public void deserializeBARResource(String uri, InputStream is,
            Project project) throws Exception {

        if (null != is) {
            super.deserializeBARResource(uri, is, project);
        }

        this.beArchives = new ArrayList<BEArchiveResource>();

        final ClusterConfig clusterConfig = ((DeployedEMFProject) project).getClusterConfig();
        final ProcessingUnitConfig puc = ((DeployedEMFProject) project).getProcessingUnitConfig();
        if (puc != null) {
            for (AgentConfig ac: puc.getAgents().getAgent()) {
                final AgentClassConfig aacc = ac.getRef();
                String name = CddTools.getValueFromMixed(ac.getKey());
                if ((null == name) || name.isEmpty()) {
                        name = aacc.getId();
                }
                if (aacc instanceof CacheAgentClassConfig) {
                    this.beArchives.add(new CacheAgentToBEArchiveAdapter(clusterConfig,
                            (CacheAgentClassConfig) aacc, name));
                } else if (aacc instanceof DashboardAgentClassConfig) {
                    this.beArchives.add(new DashboardAgentToBEArchiveAdapter(clusterConfig,
                            (DashboardAgentClassConfig) aacc, name));
                } else if (aacc instanceof InferenceAgentClassConfig) {
                    this.beArchives.add(new InferenceAgentToBEArchiveAdapter(clusterConfig,
                            (InferenceAgentClassConfig) aacc, name));
                } else if (aacc instanceof QueryAgentClassConfig) {
                    this.beArchives.add(new QueryAgentToBEArchiveAdapter(clusterConfig,
                            (QueryAgentClassConfig) aacc, name));
                } else if (aacc instanceof MmAgentClassConfig) {
                    InferenceAgentClassConfig iacc = ((MmAgentClassConfig) aacc).getMmInferenceAgentClass();
                    QueryAgentClassConfig qacc = ((MmAgentClassConfig) aacc).getMmQueryAgentClass();
                    this.beArchives.add(new InferenceAgentToBEArchiveAdapter(clusterConfig,
                            iacc , iacc.getId()));
                    this.beArchives.add(new QueryAgentToBEArchiveAdapter(clusterConfig,
                            qacc, qacc.getId()));
                } else if (aacc instanceof ProcessAgentClassConfig) {
                	this.beArchives.add(new ProcessAgentToBEArchiveAdapter(clusterConfig,
                            (ProcessAgentClassConfig) aacc, name));
                } else if (aacc instanceof LiveViewAgentClassConfig) {
                	this.beArchives.add(new LiveViewAgentToBEArchiveAdapter(clusterConfig,
                            (LiveViewAgentClassConfig) aacc, name));
                }
            }
        }
    }

}
