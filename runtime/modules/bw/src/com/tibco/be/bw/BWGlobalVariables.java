package com.tibco.be.bw;

import com.tibco.be.util.XiSupport;
import com.tibco.bw.store.RepoAgent;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.objectrepo.object.ObjectProvider;
import com.tibco.pe.plugin.GlobalVariablesUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

public class BWGlobalVariables
        extends GlobalVariablesProviderImpl {

    protected static final ExpandedName NODE_NAME = ExpandedName.makeName("name");

    protected RepoAgent m_repoAgent;

    protected static BWGlobalVariables m_singleton = null;


    public BWGlobalVariables(RepoAgent repoAgent) {
        m_repoAgent = repoAgent;
    }//constr


    public XiNode getVariableAsXiNode(String varName) {
        final XiNode node = XiSupport.getXiFactory().createElement(NODE_NAME, "%%"+varName+"%%");
        final ObjectProvider objectProvider = m_repoAgent.getObjectProvider();
        // BW code says: "ProjectId is totally ill-defined, ill-conceived, poorly documented & strangely exposed."
        final String projectId = objectProvider.getProjectId(m_repoAgent.getVFileFactory());
        return GlobalVariablesUtils.resolveGlobalVariables(node, objectProvider, projectId);
    }//getVariableAsXiNode


    public int getVariableAsInt(String varName, int defaultValue) {
        final String stringValue = getVariableAsString(varName, "" + defaultValue);
        if (null == stringValue) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                return defaultValue;
            }//catch
        }//else
    }//getVariableAsInt


    public long getVariableAsLong(String varName, long defaultValue) {
        final String stringValue = getVariableAsString(varName, "" + defaultValue);
        if (null == stringValue) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(stringValue);
            } catch (NumberFormatException e) {
                return defaultValue;
            }//catch
        }//else
    }//getVariableAsLong


    public double getVariableAsDouble(String varName, double defaultValue) {
        final String stringValue = getVariableAsString(varName, "" + defaultValue);
        if (null == stringValue) {
            return defaultValue;
        } else {
            try {
                return Double.parseDouble(stringValue);
            } catch (NumberFormatException e) {
                return defaultValue;
            }//catch
        }//else
    }//getVariableAsDouble


    public String getVariableAsString(String varName, String defaultValue) {
        final XiNode node = getVariableAsXiNode(varName);
        if (null == node) {
            return defaultValue;
        } else {
            return node.getStringValue();
        }//else
    }//getVariableAsString


    public boolean getVariableAsBoolean(String varName, boolean defaultValue) {
        final String stringValue = getVariableAsString(varName, "" + defaultValue);
        if (null == stringValue) {
            return defaultValue;
        } else {
            return "1".equals(stringValue) || Boolean.valueOf(stringValue);
        }//else
    }//getVariableAsBoolean


}//class

