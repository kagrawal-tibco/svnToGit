package com.tibco.cep.bpmn.runtime.templates;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.xml.data.primitive.ExpandedName;

/*
* Author: Suresh Subramani / Date: 8/4/12 / Time: 5:44 PM
*/
public class VersionedProcessNameImpl implements VersionedProcessName {

    String fullPath;
    int version;
    String simpleName;
    private String versionedName;
    private String entityDaoName;
    private ExpandedName expandedName;
    private String javaclassName;

    VersionedProcessNameImpl(String fullPath, int revision) {
        this.fullPath = fullPath;
        this.version = revision;

    }


    /**
     * Return the last component of the entity.
     * Example /Process/OrderOrchestration/InwardPayment.process will return InwardProcess.
     * @return
     */
    @Override

    public String getSimpleName() {
        if (simpleName != null) return simpleName;

        int li = fullPath.lastIndexOf('/');
        simpleName = li > -1 ? fullPath.substring(li+1):fullPath;

        li = fullPath.lastIndexOf("."); //remove .processName
        simpleName = li > -1 ? fullPath.substring(li+1):fullPath;

        return null;
    }


    /**
     * Return the VersionedName converting the "/" => "." and any "." to $ for subprocess.
     * @return
     */
    @Override
    public String getName() {
        return ModelNameUtil.generatedClassNameToModelPath(getJavaClassName());
    }

    @Override
    public String getModeledFQN() {
        return fullPath;
    }

    @Override
    public String getJavaClassName() {

        if (javaclassName != null) return javaclassName;

        String temp = String.format("%s_%d_%s", fullPath.replace('.', '$'), version, ModelNameUtil.PROCESS_MARKER);
        javaclassName =  ModelNameUtil.modelPathToGeneratedClassName(temp);

        return javaclassName;
    }

    @Override
    public String getEntityDaoName() {
        return versionedName; //TODO Work with Ashwin to see if we can map N-ProcessClass to a Single EntityDao.
    }

    @Override
    public String getBackingStoreName() {
        return null;     //TODO work with Vincent.
    }

    @Override
    public ExpandedName getExpandedName() {

        if (expandedName != null) return expandedName;

        
//        String localName = modelPath.substring(modelPath.lastIndexOf('/')+1);
        String className = getJavaClassName();
//        String packageName = className.substring(0, className.lastIndexOf("."));
        String localName = ModelNameUtil.unescapeIdentifier(className.substring(className.lastIndexOf(".")+1));

        String modelPath = ModelNameUtil.generatedClassNameToModelPath(className);
        modelPath = modelPath.substring(0,modelPath.lastIndexOf("/"));
        expandedName =  ExpandedName.makeName(TypeManager.DEFAULT_BE_NAMESPACE_URI + modelPath+"/"+localName, localName);

        return expandedName;
    }

    @Override
    public int getVersion() {
        return version;  
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullPath == null) ? 0 : fullPath.hashCode());
		result = prime * result + version;
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
		VersionedProcessNameImpl other = (VersionedProcessNameImpl) obj;
		if (fullPath == null) {
			if (other.fullPath != null)
				return false;
		} else if (!fullPath.equals(other.fullPath))
			return false;
		if (version != other.version)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "VersionedProcessNameImpl [fullPath=" + fullPath + ", version=" + version + ", simpleName=" + simpleName + ", versionedName=" + versionedName
				+ ", entityDaoName=" + entityDaoName + ", expandedName=" + expandedName + ", javaclassName=" + javaclassName + "]";
	}
    
    
}
