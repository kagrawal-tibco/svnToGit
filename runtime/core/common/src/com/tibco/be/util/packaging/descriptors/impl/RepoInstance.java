package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 8, 2010
 * Time: 6:46:05 PM
 */

import com.tibco.be.util.packaging.Constants;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class RepoInstance
        extends AbstractDeploymentDescriptor {


    public RepoInstance() {
        super("TIBCO BusinessWorks and Adapters Deployment Repository Instance");
        this.setRequiresConfiguration(false);
        this.setConfigurableAtDeployment(false);
    }


    @Override
    public String getDDFactoryClassName() {
        return "com.tibco.dd.repo.RepoInstance";
    }


    public ExpandedName getTypeXName() {
        return Constants.RepoInstance.XNames.REPO_INSTANCE;
    }


//    public void fromXiNode(XiNode node)
//            throws XmlAtomicValueCastException {
//        super.fromXiNode(node);
//
//        for (final Iterator children = node.getChildren(); children.hasNext();) {
//            final XiNode child = (XiNode) children.next();
//            Object obj = null;
//            label1: {
//                if (child instanceof Comment) {
//                    String s = child.getStringValue();
//                    if (s.startsWith("<pd:LocalRepoInstanceConfiguration>") && s.endsWith("</pd:LocalRepoInstanceConfiguration>")) {
//                        obj = new LocalRepoInstanceConfiguration();
//                        break label1;
//                    }
//                }
//                final ExpandedName xName = child.getName();
//                if (xName == null) {
//                    continue;
//                }
//                if (xName.equals(HttpRepoInstanceConfiguration.ELEMENT_TYPE)) {
//                    obj = new HttpRepoInstanceConfiguration();
//                    break label1;
//                }
//                if (xName.equals(RvRepoInstanceConfiguration.ELEMENT_TYPE)) {
//                    obj = new RvRepoInstanceConfiguration();
//                    break label1;
//                }
//                if (xName.equals(LocalRepoInstanceConfiguration.ELEMENT_TYPE)) {
//                    obj = new LocalRepoInstanceConfiguration();
//                    break label1;
//                }
//                if (xName == null
//                        || !xName.equals(Constants.RepoInstance.XNames.SELECTED_REPO_INSTANCE_CONFIGURATION_NAME)) {
//                    break label1;
//                }
//                String s1 = child.getStringValue();
//                Iterator iterator1 = getNameValuePairs().iterator();
//                RepoInstanceConfiguration repoinstanceconfiguration;
//                do {
//                    if (!iterator1.hasNext()) {
//                        break label1;
//                    }
//                    repoinstanceconfiguration = (RepoInstanceConfiguration) iterator1.next();
//                } while (!repoinstanceconfiguration.getName().equals(s1));
//                mSelectedConfiguration = repoinstanceconfiguration;
//            }
//
//            if (obj != null) {
//                ((RepoInstanceConfiguration) (obj)).fromXiNode(child);
//                addConfiguration(((Configuration) (obj)));
//            }
//        }
//    }


    public XiNode toXiNode(XiFactory factory) {
        final XiNode rootNode = super.toXiNode(factory);
        rootNode.setNamespace(
                factory.createNamespace(Constants.RepoInstance.NAMESPACE_PREFIX, Constants.RepoInstance.NAMESPACE));
//        for (int i = 0; i < nameValuePairs.size(); i++) {
//            RepoInstanceConfiguration repoinstanceconfiguration = (RepoInstanceConfiguration) nameValuePairs.get(i);
//            rootNode.appendChild(repoinstanceconfiguration.toXiNode(factory));
//        }
//        if (mSelectedConfiguration != null) {
//            XiNode xinode1 = factory.createElement(Constants.RepoInstance.XNames.SELECTED_REPO_INSTANCE_CONFIGURATION_NAME);
//            xinode1.setStringValue(mSelectedConfiguration.getName());
//            rootNode.appendChild(xinode1);
//        }
        return rootNode;
    }


    public boolean isRequiresConfiguration() {
        return false;
//        return (this.getSelectedRepoInstanceConfiguration() == null)
//                || !this.getSelectedRepoInstanceConfiguration().isRequiresConfiguration();
    }


    public Object getRuntime() {
        throw new UnsupportedOperationException();
    }


    public String toString() {
//        if (this.isRequiresConfiguration()) {
        return this.getName();
//        } else {
//            return this.getName() + "=" + this.getRepoURL();
//        }
    }


    public String getAdministratorComponentClassName() {
        return "com.tibco.administrator.dd.repo.RepoInstanceEditor";
    }


    protected String getXsdFileNameStem() {
        return Constants.RepoInstance.XSD_FILE_NAME_STEM;
    }

}
