package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:45:24 PM
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.NameValuePair;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class NameValuePairs
        extends AbstractDeploymentDescriptor {


//    public static final String DEPLOYMENT_NAME_VALUE_PAIR = "Deployment";
    //    public static final String DOMAIN_NAME_VALUE_PAIR = "Domain";
    public static final String REPO_INSTANCE_GLOBAL_VARIABLES_DEPLOYMENT_DESCRIPTOR_NAME = "Global Variables";
    public static final String TRA_PROPERTIES_VARIABLES_DEPLOYMENT_DESCRIPTOR_NAME = "Runtime Variables";
    public static final String EXTERNAL_DEPENDENCY_DEPLOYMENT_DESCRIPTOR_NAME = "EXTERNAL_DEPENDENCIES";

    protected List<NameValuePair> nameValuePairs;
    private transient Map<String, NameValuePair> nameToNameValuePair;


    public NameValuePairs() {
        super();
        this.nameValuePairs = new ArrayList<NameValuePair>();
        this.nameToNameValuePair = new HashMap<String, NameValuePair>();
        this.setConfigurableAtDeployment(true);
    }


    public NameValuePairs(String name) {
        super(name);
        this.nameValuePairs = new ArrayList<NameValuePair>();
        this.nameToNameValuePair = new HashMap<String, NameValuePair>();
        this.setConfigurableAtDeployment(true);
    }


    public void addNameValuePair(NameValuePair nameValuePair) {
        if (null != this.getNameValuePair(nameValuePair.getName())) {
            throw new IllegalArgumentException(
                    "Duplicate NameValuePairImpl detected when trying to add NameValuePair " + nameValuePair.getName()
                            + " to NameValuePairs DD " + this.getName());
        } else {
//            ((NameValuePairImpl) nvpair).mNameValuePairs = this;
            this.nameValuePairs.add(nameValuePair);
            this.nameToNameValuePair.put(nameValuePair.getName(), nameValuePair);
        }
    }


    public void fromXiNode(XiNode xinode)
            throws XmlAtomicValueCastException {
        super.fromXiNode(xinode);

        for (final Iterator children = xinode.getChildren(); children.hasNext();) {
            final XiNode child = (XiNode) children.next();
            final ExpandedName name = child.getName();
            if (Constants.DD.XNames.NAME_VALUE_PAIR.equals(name)) {
                final NameValuePair nameValuePair = new NameValuePairImpl();
                nameValuePair.fromXiNode(child);
                this.addNameValuePair(nameValuePair);
            } else if (Constants.DD.XNames.NAME_VALUE_PAIR_INTEGER.equals(name)) {
                final NameValuePair nameValuePair = new NameValuePairInteger();
                nameValuePair.fromXiNode(child);
                this.addNameValuePair(nameValuePair);
            } else if (Constants.DD.XNames.NAME_VALUE_PAIR_BOOLEAN.equals(name)) {
                final NameValuePair nameValuePair = new NameValuePairBoolean();
                nameValuePair.fromXiNode(child);
                this.addNameValuePair(nameValuePair);
            } else if (Constants.DD.XNames.NAME_VALUE_PAIR_PASSWORD.equals(name)) {
                final NameValuePair nameValuePair = new NameValuePairPassword();
                nameValuePair.fromXiNode(child);
                this.addNameValuePair(nameValuePair);
            }
        }
    }


    @Override
    public String getAdministratorComponentClassName() {
        return "com.tibco.administrator.dd.namevaluepairs.NameValuePairsEditor";
    }


    @Override
    public String getDDFactoryClassName() {
        return "com.tibco.archive.helpers.NameValuePairs";
    }


    public List<NameValuePair> getNameValuePairs() {
        return Collections.unmodifiableList(this.nameValuePairs);
    }


    @Override
    public Object getRuntime() {
        final Map<String, String> map = new HashMap<String, String>();
        for (final NameValuePair nameValuePair : this.nameValuePairs) {
            if (!nameValuePair.isRequiresConfiguration()) {
                map.put(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
        return map;
    }


    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.NAME_VALUE_PAIRS;
    }


    public boolean isRequiresConfiguration() {
        for (final NameValuePair nameValuePair : this.nameValuePairs) {
            if (nameValuePair.isRequiresConfiguration()) {
                return true;
            }
        }
        return false;
    }


//    public void merge(DeploymentDescriptor deploymentdescriptor) {
//        super.merge(deploymentdescriptor);
//        this.aw = deploymentdescriptor.getCanConfigureAtDeployment();
//    }
//
//
//    public void merge(DeploymentDescriptor deploymentdescriptor, DeploymentDescriptor deploymentdescriptor1) {
//        if (deploymentdescriptor == null) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Must specify a DeploymentDescriptor when merging with ").append(this).toString());
//        }
//        if (!(deploymentdescriptor instanceof AbstractListDeploymentDescriptor)) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Cannot merge AbstractListDeploymentDescriptor descriptor ").append(getName()).append(" with descriptor ").append(deploymentdescriptor.getName()).append(" of type ").append(deploymentdescriptor.getClass().getName()).toString());
//        }
//        AbstractListDeploymentDescriptor abstractlistdeploymentdescriptor = (AbstractListDeploymentDescriptor) deploymentdescriptor;
//        HashMap hashmap = new HashMap();
//        Iterator iterator = abstractlistdeploymentdescriptor.getNameValuePairs().iterator();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            Configuration configuration = (Configuration) iterator.next();
//            hashmap.put(configuration.getName(), null);
//            Object obj = this.getConfigurationByName(configuration.getName());
//            if (configuration.isDisableConfigureAtDeployment() && obj != null) {
//
//                this.removeConfiguration(((Configuration) (obj)));
//                obj = null;
//            }
//
//
//            if (obj != null && deploymentdescriptor1 != null && configuration.diff(((Configuration) (obj)))) {
//                Configuration configuration2 = ((NameValuePairs) deploymentdescriptor1).getConfigurationByName(configuration.getName());
//
//
//                if (!((Configuration) (obj)).diff(configuration2)) {
//                    this.removeConfiguration(((Configuration) (obj)));
//                    obj = null;
//                }
//
//                if (obj != null && (configuration instanceof NameValuePairPassword) && !(obj instanceof NameValuePairPassword)) {
//
//                    obj = new NameValuePairPassword(configuration.getName(), ((AbstractNVPair) obj).getValue().toCharArray());
//                    this.updateConfigration(((Configuration) (obj)));
//                } else if (obj != null && !(configuration instanceof NameValuePairPassword) && (obj instanceof NameValuePairPassword)) {
//
//                    String s = String.valueOf(((NameValuePairPassword) obj).getClearTextValue());
//                    if (configuration instanceof NameValuePairImpl) {
//                        obj = new NameValuePairImpl(configuration.getName(), s);
//                    } else if (configuration instanceof NameValuePairBoolean) {
//
//                        boolean flag = false;
//
//
//                        try {
//                            flag = Boolean.valueOf(s).booleanValue();
//                        }
//                        catch (Exception exception) {
//
//                            flag = false;
//                        }
//                        obj = new NameValuePairBoolean(configuration.getName(), flag);
//                    } else if (configuration instanceof NameValuePairInteger) {
//
//                        int i = 0;
//
//
//                        try {
//                            i = Integer.valueOf(s).intValue();
//                        }
//                        catch (Exception exception1) {
//
//                            i = 0;
//                        }
//                        obj = new NameValuePairInteger(configuration.getName(), i);
//                    }
//                    this.updateConfigration(((Configuration) (obj)));
//                } else if (obj != null && !configuration.getClass().getName().equals(obj.getClass().getName())) {
//
//                    String s1 = "";
//                    if (obj instanceof NameValuePairImpl) {
//                        s1 = ((NameValuePairImpl) obj).getValue();
//                    } else if (obj instanceof NameValuePairInteger) {
//                        s1 = ((NameValuePairInteger) obj).getValue();
//                    } else if (obj instanceof NameValuePairBoolean) {
//                        s1 = ((NameValuePairBoolean) obj).getValue();
//                    }
//                    if (configuration instanceof NameValuePairImpl) {
//                        obj = new NameValuePairImpl(configuration.getName(), s1);
//                    } else if (configuration instanceof NameValuePairBoolean) {
//
//                        boolean flag1 = false;
//
//
//                        try {
//                            flag1 = Boolean.valueOf(s1).booleanValue();
//                        }
//                        catch (Exception exception2) {
//
//                            flag1 = false;
//                        }
//                        obj = new NameValuePairBoolean(configuration.getName(), flag1);
//                    } else if (configuration instanceof NameValuePairInteger) {
//
//                        int j = 0;
//
//
//                        try {
//                            j = Integer.valueOf(s1).intValue();
//                        }
//                        catch (Exception exception3) {
//
//                            j = 0;
//                        }
//                        obj = new NameValuePairInteger(configuration.getName(), j);
//                    }
//                    this.updateConfigration(((Configuration) (obj)));
//                }
//
//
//            }
//            if (obj == null) {
//                this.addConfiguration(configuration.cloneConfig());
//            }
//        }
//        while (true);
//        iterator = (new ArrayList(this.getNameValuePairs())).iterator();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            Configuration configuration1 = (Configuration) iterator.next();
//            if (!hashmap.containsKey(configuration1.getName())) {
//                this.removeConfiguration(configuration1);
//            }
//        }
//        while (true);
//        this.aw = deploymentdescriptor.getCanConfigureAtDeployment();
//    }
//
//
//    public static NameValuePairs override(NameValuePairs namevaluepairs, NameValuePairs namevaluepairs1) {
//        NameValuePairs namevaluepairs2;
//        if (null != namevaluepairs) {
//            namevaluepairs2 = new NameValuePairs(namevaluepairs.getName());
//        } else if (null != namevaluepairs1) {
//            namevaluepairs2 = new NameValuePairs(namevaluepairs1.getName());
//        } else {
//            return null;
//        }
//        if (null != namevaluepairs1) {
//
//            try {
//                for (Iterator iterator = namevaluepairs1.getNameValuePairs().iterator(); iterator.hasNext(); namevaluepairs2.addNameValuePair((NVPair) iterator.next())) {
//                }
//            }
//
//
//            catch (Throwable throwable) {
//            }
//        }
//
//
//        if (null != namevaluepairs) {
//
//            try {
//                NameValuePair nvpair;
//                for (Iterator iterator1 = namevaluepairs.getNameValuePairs().iterator(); iterator1.hasNext(); namevaluepairs2.addNameValuePair(nvpair)) {
//
//                    nvpair = (NameValuePair) iterator1.next();
//
//                    NameValuePair nvpair1 = namevaluepairs2.getNameValuePair(nvpair.getName());
//                    if (nvpair1 != null) {
//                        namevaluepairs2.removeNameValuePair(nvpair1);
//                    }
//                }
//
//            }
//            catch (Throwable throwable1) {
//            }
//        }
//
//
//        return namevaluepairs2;
//    }


    public String toString() {
        return this.getName() + "=" + this.nameValuePairs;
    }


    public XiNode toXiNode(XiFactory factory) {
        final XiNode rootNode = super.toXiNode(factory);
        if (!this.isConfigurableAtDeployment()) {
            final XiNode xinode1 = factory.createElement(Constants.DD.XNames.DISABLE_CONFIGURE_AT_DEPLOYMENT);
            xinode1.setStringValue(Boolean.TRUE.toString());
            rootNode.appendChild(xinode1);
        }
        for (final NameValuePair nameValuePair : this.nameValuePairs) {
            rootNode.appendChild(nameValuePair.toXiNode(factory));
        }
        return rootNode;
    }


    public void removeNameValuePair(NameValuePair configuration) {
        label0:
        {
            if (this.nameValuePairs.remove(configuration)) {
                this.nameToNameValuePair.remove(configuration.getName());
                break label0;
            }
            String s = configuration.getName();
            Iterator iterator = this.getNameValuePairs().iterator();
            NameValuePair configuration1;
            do {
                if (!iterator.hasNext()) {
                    break label0;
                }
                configuration1 = (NameValuePair) iterator.next();
            }
            while (s == null || !s.equals(configuration1.getName()) || !this.nameValuePairs.remove(configuration1));
            this.nameToNameValuePair.remove(s);
        }
    }


//    public void addNameValuePairMap(NameValuePair configuration) {
//        this.nameToNameValuePair.put(configuration.getName(), configuration);
//    }


    public NameValuePair getNameValuePair(String s) {
        return this.nameToNameValuePair.get(s);
    }


//    public void updateNameValuePair(NameValuePair configuration) {
//        this.nameToNameValuePair.put(configuration.getName(), configuration);
//        for (int i = 0; i < this.nameValuePairs.size(); i++) {
//            NameValuePair configuration1 = this.nameValuePairs.get(i);
//            if (configuration != null && configuration1.getName().equals(configuration.getName())) {
//                this.nameValuePairs.set(i, configuration);
//            }
//        }
//    }


//    public void reset(DeploymentDescriptor deploymentdescriptor)
//            throws ClassCastException {
//        if (deploymentdescriptor == null) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Must specify a DeploymentDescriptor when resetting with ").append(this).toString());
//        }
//        if (!(deploymentdescriptor instanceof AbstractListDeploymentDescriptor)) {
//            throw new ClassCastException(deploymentdescriptor.getClass().getName());
//        }
//        this.nameValuePairs.clear();
//        this.nameToNameValuePair.clear();
//        List list = ((AbstractListDeploymentDescriptor) deploymentdescriptor).getNameValuePairs();
//        int i = list.size();
//        for (int j = 0; j < i; j++) {
//            Configuration configuration = (Configuration) list.get(j);
//            Configuration configuration1 = configuration.cloneConfig();
//            this.nameValuePairs.add(configuration1);
//            this.nameToNameValuePair.put(configuration1.getName(), configuration1);
//        }
//    }
//
//
//    public DeploymentDescriptor cloneConfig() {
//        AbstractListDeploymentDescriptor abstractlistdeploymentdescriptor = (AbstractListDeploymentDescriptor) super.cloneConfig();
//        abstractlistdeploymentdescriptor.mAvailableConfigurations = new ArrayList();
//        abstractlistdeploymentdescriptor.nameToNameValuePair = new HashMap();
//        List list = this.getNameValuePairs();
//        for (int i = 0; i < list.size(); i++) {
//            Configuration configuration = (Configuration) list.get(i);
//            Configuration configuration1 = configuration.cloneConfig();
//            abstractlistdeploymentdescriptor.mAvailableConfigurations.add(configuration1);
//            abstractlistdeploymentdescriptor.nameToNameValuePair.put(configuration1.getName(), configuration1);
//        }
//
//        return abstractlistdeploymentdescriptor;
//    }
//
//
//    public void merge(DeploymentDescriptor deploymentdescriptor) {
//        if (deploymentdescriptor == null) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Must specify a DeploymentDescriptor when merging with ").append(this).toString());
//        }
//        if (!(deploymentdescriptor instanceof AbstractListDeploymentDescriptor)) {
//            throw new IllegalArgumentException((new StringBuilder()).append("Cannot merge AbstractListDeploymentDescriptor descriptor ").append(getName()).append(" with descriptor ").append(deploymentdescriptor.getName()).append(" of type ").append(deploymentdescriptor.getClass().getName()).toString());
//        }
//        AbstractListDeploymentDescriptor abstractlistdeploymentdescriptor = (AbstractListDeploymentDescriptor) deploymentdescriptor;
//        HashMap hashmap = new HashMap();
//        Iterator iterator = abstractlistdeploymentdescriptor.getNameValuePairs().iterator();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            Configuration configuration = (Configuration) iterator.next();
//            hashmap.put(configuration.getName(), null);
//            Configuration configuration2 = this.getConfigurationByName(configuration.getName());
//            if (configuration.isDisableConfigureAtDeployment() && configuration2 != null) {
//                this.removeConfiguration(configuration2);
//                configuration2 = null;
//            }
//            if (configuration2 == null) {
//                this.addConfiguration(configuration.cloneConfig());
//            }
//        } while (true);
//        iterator = (new ArrayList(this.getNameValuePairs())).iterator();
//        do {
//            if (!iterator.hasNext()) {
//                break;
//            }
//            Configuration configuration1 = (Configuration) iterator.next();
//            if (!hashmap.containsKey(configuration1.getName())) {
//                this.removeConfiguration(configuration1);
//            }
//        } while (true);
//    }
//
//
//    public boolean diff(DeploymentDescriptor deploymentdescriptor, boolean flag) {
//        if (!(deploymentdescriptor instanceof AbstractListDeploymentDescriptor)) {
//            return true;
//        }
//        AbstractListDeploymentDescriptor abstractlistdeploymentdescriptor = (AbstractListDeploymentDescriptor) deploymentdescriptor;
//        if (abstractlistdeploymentdescriptor.getNameValuePairs().size() != this.getNameValuePairs().size()) {
//            return true;
//        }
//        for (Iterator iterator = this.getNameValuePairs().iterator(); iterator.hasNext();) {
//            Configuration configuration = (Configuration) iterator.next();
//            Configuration configuration1 = abstractlistdeploymentdescriptor.getConfigurationByName(configuration.getName());
//            if (configuration1 != null) {
//                if ((configuration.isDisableConfigureAtDeployment() || flag) && configuration.diff(configuration1)) {
//                    return true;
//                }
//            } else {
//                return true;
//            }
//        }
//
//        return false;
//    }
}