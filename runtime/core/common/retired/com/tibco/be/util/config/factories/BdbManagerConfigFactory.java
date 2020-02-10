package com.tibco.be.util.config.factories;


import com.tibco.be.util.config.*;
import com.tibco.be.util.config._retired_.BdbManagerConfig;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/*
* User: Nicolas Prade
* Date: Sep 1, 2009
* Time: 5:27:53 PM
*/


public class BdbManagerConfigFactory
        extends AbstractArtifactConfigFactory {


    protected final EvictionConfigFactory evictionConfigFactory;
    protected final PropertyGroupConfigFactory propertyGroupConfigFactory;


    public BdbManagerConfigFactory() {
        this.evictionConfigFactory = new EvictionConfigFactory();
        this.propertyGroupConfigFactory = new PropertyGroupConfigFactory();
    }


    public BdbManagerConfig newConfig(
            IdResolver idResolver,
            XiNode containerNode) {

        if (null == containerNode) {
            return null;
        }

        if (null == idResolver) {
            idResolver = new IdResolver();
        }

        final String checkpointInterval = XiChild.getString(containerNode, ConfigNS.Elements.CHECKPOINT_INTERVAL);
        final String checkpointOpsLimit = XiChild.getString(containerNode, ConfigNS.Elements.CHECKPOINT_OPS_LIMIT);
        final String dbDir = XiChild.getString(containerNode, ConfigNS.Elements.DB_DIR);
        final String deleteRetracted = XiChild.getString(containerNode, ConfigNS.Elements.DELETE_RETRACTED);

        final EvictionConfig eviction = this.evictionConfigFactory.newConfig(
                idResolver, XiChild.getChild(containerNode, ConfigNS.Elements.EVICTION));

        final String propertyCacheSize = XiChild.getString(containerNode, ConfigNS.Elements.PROPERTY_CACHE_SIZE);

        final PropertyGroupConfig propertyGroup = this.propertyGroupConfigFactory.newConfig(
                idResolver, XiChild.getChild(containerNode, ConfigNS.Elements.PROPERTY_GROUP));

        final String skipRecovery = XiChild.getString(containerNode, ConfigNS.Elements.SKIP_RECOVERY);

        final String id = this.parseId(containerNode);
        final String name = this.parseName(containerNode);

        return new BdbManagerConfig(idResolver, id, name, checkpointInterval, checkpointOpsLimit, dbDir,
                deleteRetracted, eviction, propertyCacheSize, propertyGroup, skipRecovery);
    }
}
