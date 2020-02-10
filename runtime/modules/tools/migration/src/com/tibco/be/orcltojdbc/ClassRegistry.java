package com.tibco.be.orcltojdbc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;

public class ClassRegistry {
    public final static String NAMESPACE= "www.tibco.com/be/ontology";
    public final static int BE_TYPE_START = 1000;
    public final static int BE_TYPE_INTERNAL_END=1010;
    RuleServiceProvider rsp;
    Map typeIdToClz = new HashMap();
    Map clzToTypeId = new HashMap();
    Map clzNmToTypeId = new HashMap();

    public ClassRegistry(RuleServiceProvider rsp, BEStore store) throws Exception {
        this.rsp = rsp;

        Map typeMap = store.loadClassRegistry();
        int typeId = getMinTypeId(typeMap);
        BEClassLoader clzLoader = (BEClassLoader) rsp.getClassLoader();
        Iterator allClasses = clzLoader.getRegisteredEntities().iterator();
        while (allClasses.hasNext()) {
            Map.Entry entry = (Map.Entry) allClasses.next();
            ExpandedName uri = (ExpandedName) entry.getKey();
            TypeManager.TypeDescriptor type = (TypeManager.TypeDescriptor) entry.getValue();
            if (type.getImplClass() != null) {
                if ((type.getType() != TypeManager.TYPE_RULE)
                        && (type.getType() != TypeManager.TYPE_RULEFUNCTION)) {
                    Class clz = type.getImplClass();
                    int storedTypeId = getTypeId(clz, typeMap);
                    if (storedTypeId == -1) {
                        storedTypeId = ++typeId;
                    }
                    String ns = uri.getNamespaceURI();
                    String URI = ns.substring(NAMESPACE.length());

                    typeIdToClz.put(storedTypeId, clz);
                    clzToTypeId.put(clz, storedTypeId);
                    clzNmToTypeId.put(clz.getName(), storedTypeId);
                }
            }
        }
    }

    protected int getTypeId(Class entityClz, Map typeMap) {
        Integer typeId= (Integer) typeMap.get(entityClz.getName());
        if (typeId != null) {
            return typeId.intValue();
        } else {
            return -1;
        }
    }

    protected int getMinTypeId(Map typeMap) {
        if (typeMap.size() == 0) {
            return BE_TYPE_INTERNAL_END;
        } else {
            int minTypeId=-1;
            Iterator typeIds=typeMap.values().iterator();
            while (typeIds.hasNext()) {
                int typeId= ((Integer) typeIds.next()).intValue();
                if (typeId > minTypeId) {
                    minTypeId=typeId;
                }
            }
            return minTypeId;
        }
    }

    public Class getClass(int typeId) {
        return (Class) typeIdToClz.get(typeId);
    }

    public int getTypeId (String clzName) {
        return (Integer) clzNmToTypeId.get(clzName);
    }
}
