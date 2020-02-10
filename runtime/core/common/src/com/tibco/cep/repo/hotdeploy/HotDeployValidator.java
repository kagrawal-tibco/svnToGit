package com.tibco.cep.repo.hotdeploy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.channel.impl.ChannelConfigurationImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.nodes.Element;


public class HotDeployValidator {


    private static boolean hasDestinationsChanged(Collection old, Collection latest) {
        if (old.size() != latest.size()) {
            return true;
        }
        for (Object anOld : old) {
            ArchiveInputDestinationConfig oldConfig = (ArchiveInputDestinationConfig) anOld;
            boolean found = false;
            for (Object aLatest : latest) {
                ArchiveInputDestinationConfig newConfig = (ArchiveInputDestinationConfig) aLatest;
                if (oldConfig.getDestinationURI().equals(newConfig.getDestinationURI())) {
                    if (oldConfig.equals(newConfig)) {
                        //todo - need to check channel and destination config!!!!!
                        found = true;
                        break;
                    } else
                        return true;
                }
            }
            if (!found) {
                return true;
            }
        }
        return false;
    }


    static private boolean hasOMConfigChanged(BEArchiveResource checkArchive, BEArchiveResource loadedArchive) {
        if (checkArchive.getCacheConfig().size() != loadedArchive.getCacheConfig().size()) {
            return true;
        }

        for (Map.Entry<Object, Object> e : checkArchive.getCacheConfig().entrySet()) {
            final Object key = e.getKey();
            final Object oldValue = e.getValue();
            final Object newValue = loadedArchive.getCacheConfig().get(key);
            final Map<String, Map> oldRows = new HashMap<String, Map>();
            final Map<String, Map> newRows = new HashMap<String, Map>();
            if (oldValue == null) {
                if (newValue != null) {
                    return true;
                }

            } else if (oldValue instanceof Element) {
                for (Iterator it = XiChild.getIterator((Element) oldValue, ExpandedName.makeName("row")); it.hasNext();) {
                    final Element rowElement = (Element) it.next();
                    Map rowMap = getOMConfigValues(rowElement);
                    String uri = (String) rowMap.get("uri");
                    oldRows.put(uri, rowMap);
                }
                if (newValue instanceof Element) {
                    for (Iterator it = XiChild.getIterator((Element) newValue, ExpandedName.makeName("row")); it.hasNext();) {
                        final Element rowElement = (Element) it.next();
                        Map rowMap = getOMConfigValues(rowElement);
                        String uri = (String) rowMap.get("uri");
                        newRows.put(uri, rowMap);
                    }
                }
                if (!oldRows.equals(newRows)) {
                    return true;
                }

            } else if (!oldValue.equals(newValue)) {
                return true;
            }
        }

        return false;
    }


    private static Map<String, String> getOMConfigValues(Element rowElement) {
        final Map<String, String> val = new HashMap<String, String>();
        val.put("uri", XiChild.getString(rowElement, ExpandedName.makeName("uri")));
        val.put("deployed", XiChild.getString(rowElement, ExpandedName.makeName("deployed")));
        val.put("cacheMode", XiChild.getString(rowElement, ExpandedName.makeName("cacheMode")));
        val.put("recoveryFunction", XiChild.getString(rowElement, ExpandedName.makeName("recoveryFunction")));
        return val;
    }


    public static void checkDeployedBEArchives(
            DeployedProject deployedBEProject,
            DeployedProject hotDeployBEProject)
            throws Exception {
        for (BEArchiveResource oldArchive : deployedBEProject.getDeployedBEArchives()) {
            for (BEArchiveResource newArchive : hotDeployBEProject.getDeployedBEArchives()) {
                if (oldArchive.getName().equals(newArchive.getName())) {
                    //found matched archive
                    if (hasDestinationsChanged(oldArchive.getInputDestinations(), newArchive.getInputDestinations())) {
                        throw new Exception("Input Destinations config is not allowed to change: BE Archive ["
                                + oldArchive.getName() + "] Input Destinations " + oldArchive.getInputDestinations()
                                + " Hot Deployed Input Destinations " + newArchive.getInputDestinations());
                    }
                    if (hasOMConfigChanged(oldArchive, newArchive)) {
                        throw new Exception("Object Manager config is not allowed to change: BE Archive ["
                                + oldArchive.getName() + "] Properties " + oldArchive.getCacheConfig().toString()
                                + " Hot Deployed Properties " + newArchive.getCacheConfig().toString());
                    }
                    break;
                }
            }
        }
    }


    public static boolean isDeployedRulesChanged(
            DeployedProject deployedBEProject,
            DeployedProject hotDeployBEProject) {
        for (BEArchiveResource oldArchive : deployedBEProject.getDeployedBEArchives()) {
            for (BEArchiveResource newArchive : hotDeployBEProject.getDeployedBEArchives()) {
                if (oldArchive.getName().equals(newArchive.getName())) {
                    if (!oldArchive.getDeployedRuleUris().equals(newArchive.getDeployedRuleUris())) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }


    private static String printChannels(Collection channels) {
        final StringBuffer ret = new StringBuffer("[");
        boolean first = true;
        for (Object channel : channels) {
            if (first) {
                first = false;
            } else {
                ret.append(" ");
            }
            ret.append(((Channel) channel).getFullPath());
        }
        return ret.append("]").toString();
    }


    public static void checkChannelAndDestinationConfig(
            DeployedProject deployedBEProject,
            DeployedProject hotDeployBEProject)
            throws Exception {
    	
    	AbstractOntologyAdapter<EObject> depOntology = deployedBEProject.getAbstractOntologyAdapter(AddOnType.CORE);
    	depOntology.setName(null);
    	AbstractOntologyAdapter<EObject> depHDOntology = hotDeployBEProject.getAbstractOntologyAdapter(AddOnType.CORE);
    	depHDOntology.setName(null);
    	

        if (depOntology.getChannels().size() != depHDOntology.getChannels().size()) {
            throw new Exception("Adding or Deleting channel is not allowed: Deployed Channels " +
                    printChannels(deployedBEProject.getOntology().getChannels()) + " Hot Deployed Channels " +
                    printChannels(hotDeployBEProject.getOntology().getChannels()));
        }

        for (Object o : depOntology.getChannels()) {
            final Channel oldChannel = (Channel) o;
            boolean found = false;
            for (Object o1 :depHDOntology.getChannels()) {
                final Channel newChannel = (Channel) o1;
                if (newChannel.getFullPath().equals(oldChannel.getFullPath())) {
                    found = true;
                    final ChannelConfigurationImpl oldConfig = 
                        new ChannelConfigurationImpl(deployedBEProject.getGlobalVariables(), oldChannel);
                    final ChannelConfigurationImpl newConfig = 
                        new ChannelConfigurationImpl(deployedBEProject.getGlobalVariables(), newChannel);                    final String msg = ChannelConfigurationImpl.diff(oldConfig, newConfig);
                    if (msg != null) {
                        throw new Exception("Channel configuration is not allowed to change: Channel ["
                                + oldChannel.getFullPath() + "] " + msg);
                    } else {
                        break;
                    }
                }
            }
            if (!found) {
                throw new Exception("Deleted channel [" + oldChannel.getFullPath() + "] in hot deployed project");
            }
        }
    }


    public static Map<String, BEArchiveResource> getNewBEArchives(
            DeployedProject deployedBEProject,
            DeployedProject hotDeployBEProject) {

        final Map<String, BEArchiveResource> addedArchives = new HashMap<String, BEArchiveResource>();

        for (BEArchiveResource arch : hotDeployBEProject.getDeployedBEArchives()) {
            addedArchives.put(arch.getName(), arch);
        }
        for (BEArchiveResource arch : deployedBEProject.getDeployedBEArchives()) {
            addedArchives.remove(arch.getName());
        }

        return addedArchives;
    }


    public static Map<String, BEArchiveResource> getDeletedBEArchives(
            DeployedProject deployedBEProject,
            DeployedProject hotDeployBEProject) {

        Map<String, BEArchiveResource> deletedArchives = new HashMap<String, BEArchiveResource>();

        for (BEArchiveResource arch : deployedBEProject.getDeployedBEArchives()) {
            deletedArchives.put(arch.getName(), arch);
        }
        for (BEArchiveResource arch : hotDeployBEProject.getDeployedBEArchives()) {
            deletedArchives.remove(arch.getName());
        }

        return deletedArchives;
    }

}
