/**
 *
 */
package com.tibco.cep.repo.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.helpers.NoNamespace;


public abstract class BEArchiveResourceImpl
        implements BEArchiveResource {


    protected XiNode archiveNode;
    protected String name;

    //ArrayList listenDestinations = new ArrayList();
    protected Set<String> deployedRuleUris = new HashSet<String>();
    protected Properties cacheConfig;
    protected Set<ArchiveInputDestinationConfig> inputDestinations;
    protected BEArchiveResourceProvider barProvider;
    protected Constants.ArchiveType type;


    public static BEArchiveResourceImpl newBEArchive(String name) {
        return new BEArchiveResourceImpl(name) {
        };
    }


    public BEArchiveResourceImpl(String name) {
        this.name = name;
        this.cacheConfig = new Properties();
        this.cacheConfig.put("sessionName", name);
        this.cacheConfig.put("omClass", "false");
        this.inputDestinations = new HashSet<ArchiveInputDestinationConfig>();
        this.type = Constants.ArchiveType.INFERENCE;
    }


    public BEArchiveResourceImpl(XiNode archive, BEArchiveResourceProvider barProvider) {
        this.archiveNode = archive;
        this.barProvider = barProvider;
        this.name = archive.getAttributeStringValue(ExpandedName.makeName(NoNamespace.URI, "name"));
        this.cacheConfig = new Properties();
        this.cacheConfig.put("sessionName", name);

        this.deserializeDeployedRuleSets(archive);
        this.deserializeListenDestinations(archive);
        this.deserializeCacheConfig(archive);

        final String typeString = archive.getAttributeStringValue(Constants.XNAME_TYPE);
        if (null == typeString) {
            this.type = Constants.ArchiveType.INFERENCE;
        } else {
            this.type = Constants.ArchiveType.valueOf(typeString);
        }
    }


    private void deserializeCacheConfig(XiNode archive) {
        final XiNode omelement = XiChild.getChild(archive, Constants.Config.XNames.OM);
        final XiNode omType = XiChild.getChild(omelement, Constants.Config.XNames.OM_ENABLE);
        //cacheConfig.put("omEnable", XiChild.getFirstChild(omType).getStringValue());
        //System.out.println("Setting omEnable..." + XiChild.get .getStringValue(omType).getStringValue());
        for (final Iterator r = XiChild.getIterator(omType); r.hasNext();) {
            final XiNode node = (XiNode) r.next();
            if (node.getNodeKind() == XmlNodeKind.ELEMENT) {
                final String name = node.getName().getLocalName();
                if (name.equalsIgnoreCase("omtgAdvancedEntitySettings")) {
                    this.cacheConfig.put(name, node);
                } else {
                    this.cacheConfig.put(name, node.getStringValue());
                }
            }
        }
    }


    private void deserializeListenDestinations(XiNode archive) {

        final Set<ArchiveInputDestinationConfig> set = new HashSet<ArchiveInputDestinationConfig>();

        final XiNode listenDestinations = XiChild.getChild(archive, Constants.Config.XNames.LISTEN_DESTINATIONS);

        for (final Iterator iterator = XiChild.getIterator(listenDestinations); iterator.hasNext();) {
            final XiNode listenDestination = (XiNode) iterator.next();
            final String channel = XiChild.getChild(listenDestination, Constants.Config.XNames.CHANNEL).getStringValue();
            final String destination = channel + "/" + XiChild.getChild(listenDestination, Constants.Config.XNames.DESTINATION).getStringValue();

            String ruleFunction = "";
            String threadingModel = null;
            int workers = 0;
            int qSize = 0;
            int qWeight = 0;

            XiNode node = XiChild.getChild(listenDestination, Constants.Config.XNames.PREPROCESSOR);
            if (node != null) {
                final String rf = node.getStringValue();
                if ((rf != null) && (rf.trim().length() > 0)) {
                    ruleFunction = rf;
                }
            }

            node = XiChild.getChild(listenDestination, Constants.Config.XNames.THREADING_MODEL);
            if (node != null) {
                threadingModel = node.getStringValue();
            }

            node = XiChild.getChild(listenDestination, Constants.Config.XNames.WORKERS);
            if (node != null) {
                workers = Integer.parseInt(node.getStringValue());
            }

            node = XiChild.getChild(listenDestination, Constants.Config.XNames.QUEUE_SIZE);
            if (node != null) {
                qSize = Integer.parseInt(node.getStringValue());
            }

            node = (XiChild.getChild(listenDestination, Constants.Config.XNames.QUEUE_WEIGHT));
            if (node != null) {
                qWeight = Integer.parseInt(node.getStringValue());
            }

            set.add(new InputDestinationConfigImpl(destination, ruleFunction, threadingModel, workers, qSize, qWeight));
        }

        this.inputDestinations = set;
    }


    private void deserializeDeployedRuleSets(XiNode archive) {
        final XiNode rulesets = XiChild.getChild(archive, Constants.Config.XNames.RULESETS);
        for (final Iterator j = XiChild.getIterator(rulesets); j.hasNext();) {
            final String ruleset = ((XiNode) j.next()).getStringValue();
            this.deployedRuleUris.add(ruleset);//todo verify
        }
    }


    public Properties getCacheConfig() {
//            byte [] cacheConfigXML= barProvider.getResourceAsByteArray(BEArchiveResourceProvider.CACHECONFIGXML);
//            if (cacheConfigXML != null){
//                cacheConfig.put(BEArchiveResourceProvider.CACHECONFIGXML, new String(cacheConfigXML));
//            }
        return this.cacheConfig;
    }


    public Set<String> getDeployedRuleUris() {
        return this.deployedRuleUris;
    }


    public Set<ArchiveInputDestinationConfig> getInputDestinations() {
        return this.inputDestinations;
    }


    public String getName() {
        return this.name;
    }


    public List<String> getShutdownFunctionUris() {
        return null;
    }


    public List<String> getStartupFunctionUris() {
        return null;
    }


    public Constants.ArchiveType getType() {
        return this.type;
    }


    public boolean isCacheEnabled(GlobalVariables gv) {
//        for (final Map.Entry<Object, Object> entry : this.getCacheConfig().entrySet()) {
//            if (entry.getValue() instanceof String) {
//                this.cacheConfig.put(entry.getKey(), gv.substituteVariables(entry.getValue().toString()).toString());
//            } else {
//                this.cacheConfig.put(entry.getKey(), entry.getValue());
//            }
//        }
        return Constants.PROPERTY_NAME_OM_TANGOSOL.equals(cacheConfig.getProperty(Constants.XNAME_TYPE.localName));
    }


    public boolean equals(Object o) {
        return (o instanceof BEArchiveResourceImpl)
                && this.name.equals(((BEArchiveResourceImpl) o).name);
    }

    @Override
    public String getReferenceClassName() {
        throw new RuntimeException("Do not call this method");
    }

    public static class InputDestinationConfigImpl
            implements ArchiveInputDestinationConfig {


        String destinationURI, preprocessorURI, threadingModel, threadAffinityRuleFunction;
        int numWorker, queueSize, weight;


        public InputDestinationConfigImpl(
                String destinationURI,
                String preprocessorURI,
                String threadingModel,
                String threadAffinityRuleFunction,
                int numWorker,
                int queueSize,
                int weight) {
            this.destinationURI = destinationURI;
            this.preprocessorURI = preprocessorURI;
            this.threadingModel = threadingModel;
            this.threadAffinityRuleFunction = threadAffinityRuleFunction;
            this.numWorker = numWorker;
            this.queueSize = queueSize;
            this.weight = weight;
        }
        
        public InputDestinationConfigImpl(
                String destinationURI,
                String preprocessorURI,
                String threadingModel,
                int numWorker,
                int queueSize,
                int weight) {
            this(destinationURI, preprocessorURI, threadingModel, null, numWorker, queueSize, weight);
        }


        public String getDestinationURI() {
            return this.destinationURI;
        }


        public String getPreprocessor() {
            return this.preprocessorURI;
        }

        
        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((destinationURI == null) ? 0 : destinationURI.hashCode());
			result = prime * result + numWorker;
			result = prime
					* result
					+ ((preprocessorURI == null) ? 0 : preprocessorURI
							.hashCode());
			result = prime * result + queueSize;
			result = prime
					* result
					+ ((threadingModel == null) ? 0 : threadingModel.hashCode());
			result = prime * result + weight;
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InputDestinationConfigImpl other = (InputDestinationConfigImpl) obj;
			if (destinationURI == null) {
				if (other.destinationURI != null)
					return false;
			} else if (!destinationURI.equals(other.destinationURI))
				return false;
			if (numWorker != other.numWorker)
				return false;
			if (preprocessorURI == null) {
				if (other.preprocessorURI != null)
					return false;
			} else if (!preprocessorURI.equals(other.preprocessorURI))
				return false;
			if (threadAffinityRuleFunction == null) {
				if (other.threadAffinityRuleFunction != null)
					return false;
			} else if (!threadAffinityRuleFunction.equals(other.threadAffinityRuleFunction))
				return false;
			if (queueSize != other.queueSize)
				return false;
			if (threadingModel == null) {
				if (other.threadingModel != null)
					return false;
			} else if (!threadingModel.equals(other.threadingModel))
				return false;
			if (weight != other.weight)
				return false;
			return true;
		}


        public String toString() {
            return "(URI=" + this.destinationURI + " Preprocessor=" + this.preprocessorURI + ")";
        }


        public int getNumWorker() {
            return this.numWorker;
        }


        public int getWeight() {
            return this.weight;
        }


        public int getQueueSize() {
            return this.queueSize;
        }


        public String getThreadingModel() {
            return this.threadingModel;
        }


		public String getThreadAffinityRuleFunction() {
			return this.threadAffinityRuleFunction;
		}
    }
    
    @Override
	public void setName(String name) {
		this.name = name;
		this.cacheConfig.put("sessionName", name);
	}


}
