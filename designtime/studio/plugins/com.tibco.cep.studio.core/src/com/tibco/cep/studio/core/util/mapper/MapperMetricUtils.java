package com.tibco.cep.studio.core.util.mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.adapters.PropertyDefinitionAdapter;

public class MapperMetricUtils {

    public static class DepFieldInfo {
        private String propertyName;
        int dependingAggrType;
        
        DepFieldInfo(String propertyName, int depAggrType) {
            this.setPropertyName(propertyName);
            this.dependingAggrType = depAggrType;
        }

		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}

		public String getPropertyName() {
			return propertyName;
		}
    }
    
    public static class FieldInfo {
        String propertyName;
        private String codegenName;
        private int type;
        private int aggregationType;
        private DepFieldInfo[] dependingField;
        private boolean isGroupBy = false;
        private boolean isSystem = false;
        private boolean isUserDefined = false;
        private PropertyDefinition propertyDefinition;
        private String targetPropertyName; // If this is a system generated dependent property.
        int fieldPosition = 0;
        int dependingLevelDepth = 0;
        private long groupByPosition = 0;
        
        FieldInfo(String propName, String genName, int type, int aggrType, 
            DepFieldInfo[] depField,  boolean isGB, 
            boolean isSys, boolean isUDef, int fieldPosition, long groupByPosition,
            PropertyDefinition pd) {
            propertyName = propName;
            setCodegenName(genName);
            this.setType(type);
            setAggregationType(aggrType);
            setDependingField(depField);
            setGroupBy(isGB);
            setSystem(isSys);
            setUserDefined(isUDef);
            setPropertyDefinition(pd);
            this.fieldPosition = fieldPosition;
            this.setGroupByPosition(groupByPosition);
        }
        
        public String toString() {
            return "Property : " + propertyName + ", GenName : " + getCodegenName() + 
                ", type : " + getType() + ", dep prop : " + Arrays.toString(getDependingField()) + 
                ", isGroupBy : " + isGroupBy() + ", isSystem : " + isSystem() +
                ", isUserDefined : " + isUserDefined() +
                ", groupByPosition : " + getGroupByPosition();
        }

		public void setSystem(boolean isSystem) {
			this.isSystem = isSystem;
		}

		public boolean isSystem() {
			return isSystem;
		}

		public void setDependingField(DepFieldInfo[] dependingField) {
			this.dependingField = dependingField;
		}

		public DepFieldInfo[] getDependingField() {
			return dependingField;
		}

		public void setCodegenName(String codegenName) {
			this.codegenName = codegenName;
		}

		public String getCodegenName() {
			return codegenName;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}

		public void setGroupBy(boolean isGroupBy) {
			this.isGroupBy = isGroupBy;
		}

		public boolean isGroupBy() {
			return isGroupBy;
		}

		public void setPropertyDefinition(PropertyDefinition propertyDefinition) {
			this.propertyDefinition = propertyDefinition;
		}

		public PropertyDefinition getPropertyDefinition() {
			return propertyDefinition;
		}

		public void setAggregationType(int aggregationType) {
			this.aggregationType = aggregationType;
		}

		public int getAggregationType() {
			return aggregationType;
		}

		public void setUserDefined(boolean isUserDefined) {
			this.isUserDefined = isUserDefined;
		}

		public boolean isUserDefined() {
			return isUserDefined;
		}

		public void setTargetPropertyName(String targetPropertyName) {
			this.targetPropertyName = targetPropertyName;
		}

		public String getTargetPropertyName() {
			return targetPropertyName;
		}

		public void setGroupByPosition(long groupByPosition) {
			this.groupByPosition = groupByPosition;
		}

		public long getGroupByPosition() {
			return groupByPosition;
		}
    }    
    
    public static Map<String, FieldInfo> generateMetricFieldInfo(Concept cept, List computeOrderList) {
        int fieldPosition = 0;
        Collection propDefs = cept.getPropertyDefinitions(false);
        LinkedHashMap<String, FieldInfo> fieldMap = new LinkedHashMap<String, FieldInfo>();
        Map<String, String> targetPropertyMap = new HashMap<String, String>(); // dep field, target field

        // Generate the values for extId - GB1-_-GB2-_-GB3......
        // And construct arguments for the compute method
        // Should we rely on the propDefs.iterator order to determine the positions of arguments?
        for (Iterator it = propDefs.iterator(); it.hasNext();) {
            //FIX THIS - Need to use impl because metric related methods only defined in impl class
            PropertyDefinition pd = (PropertyDefinition) it.next();
            String name = ModelNameUtil.generatedMemberName(pd.getName());
            String className = ModelNameUtil.generatedMemberClassName(pd.getName());
            boolean isGroupBy = ((PropertyDefinitionAdapter)pd).isGroupByField();
            boolean isSystem = false;
            DepFieldInfo[] depFields = null;
            long groupByPosition = 0;

            int type = pd.getType();
            // Only support primitive types
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE || pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                System.out.println("Metric do not support reference types");
                continue;
            }
            if (isGroupBy) {
            	groupByPosition = ((PropertyDefinitionAdapter)pd).getGroupByPosition();
            }
            System.out.println("Processing... " + pd.getName());
            METRIC_AGGR_TYPE aggrType = ((PropertyDefinitionAdapter) pd).getAggregationType();
            Iterator props = pd.getExtendedProperties().values().iterator();
            while(props.hasNext()){
                System.out.println("Ext props : " + props.next());
            }
            // If system is defined, assume it's set to true because
            // this property is only set internally by studio
            if (pd.getExtendedProperties().get("system") != null) {
                isSystem = true;
            }
            switch (aggrType.getValue()) {
                case METRIC_AGGR_TYPE.AVERAGE_VALUE:
                    depFields = new DepFieldInfo[2];
                    String sumFieldPropName = (String) pd.getExtendedProperties().get("dependentfield.Sum");
                    String countFieldPropName = (String) pd.getExtendedProperties().get("dependentfield.Count");
                    DepFieldInfo depFieldSum = new DepFieldInfo(sumFieldPropName, METRIC_AGGR_TYPE.SUM_VALUE);
                    DepFieldInfo depFieldCount = new DepFieldInfo(countFieldPropName, METRIC_AGGR_TYPE.COUNT_VALUE);
                    targetPropertyMap.put(sumFieldPropName, pd.getName());
                    targetPropertyMap.put(countFieldPropName, pd.getName());
                    depFields[0] = depFieldSum;
                    depFields[1] = depFieldCount;
                    break;
                case METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE:
                case METRIC_AGGR_TYPE.VARIANCE_VALUE:
                    depFields = new DepFieldInfo[3];
                    sumFieldPropName = (String) pd.getExtendedProperties().get("dependentfield.Sum");
                    countFieldPropName = (String) pd.getExtendedProperties().get("dependentfield.Count");
                    String sumSqFieldPropName = (String) pd.getExtendedProperties().get("dependentfield.SumOfSquares");
                    depFieldSum = new DepFieldInfo(sumFieldPropName, METRIC_AGGR_TYPE.SUM_VALUE);
                    depFieldCount = new DepFieldInfo(countFieldPropName, METRIC_AGGR_TYPE.COUNT_VALUE);
                    DepFieldInfo depFieldSumSq = new DepFieldInfo(sumSqFieldPropName, METRIC_AGGR_TYPE.SUM_OF_SQUARES_VALUE);
                    targetPropertyMap.put(sumFieldPropName, pd.getName());
                    targetPropertyMap.put(countFieldPropName, pd.getName());
                    targetPropertyMap.put(sumSqFieldPropName, pd.getName());
                    depFields[0] = depFieldSum;
                    depFields[1] = depFieldCount;
                    depFields[2] = depFieldSumSq;
                    break;
            }
            FieldInfo fi = new FieldInfo(pd.getName(), name, type, aggrType.getValue(),
                depFields, isGroupBy, isSystem, false, fieldPosition++, groupByPosition, pd);
            fieldMap.put(pd.getName(), fi);
        }

        propDefs = ((ConceptAdapter)cept).getAllUserDefinedFields();
        for (Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition) it.next();
            String name = ModelNameUtil.generatedMemberName(pd.getName());
            String className = ModelNameUtil.generatedMemberClassName(pd.getName());
            int type = pd.getType();
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE || pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                System.out.println("Metric do not support reference types for user defined fields");
                continue;
            }
            FieldInfo fi = new FieldInfo(pd.getName(), name, type, METRIC_AGGR_TYPE.SET_VALUE,
                null, false, false, true, fieldPosition++, 0, pd);
            fieldMap.put(pd.getName(), fi);
        }        
        if (fieldPosition != fieldMap.size()) {
            System.out.println("Field map size mismatch pos : " + fieldPosition + ", size : " + fieldMap.size());
        }
        for(Iterator<String> it=targetPropertyMap.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            String targetPropName = targetPropertyMap.get(key);
            FieldInfo fi = fieldMap.get(key);
            fi.setTargetPropertyName(targetPropName);
        }
        int[] traverseArray = new int[fieldPosition];
        for(Iterator<FieldInfo> it=fieldMap.values().iterator(); it.hasNext();) {
            FieldInfo fi = it.next();
            computeOrderList.add(fi);
            Arrays.fill(traverseArray, 0);
            fi.dependingLevelDepth = countDependentLevel(fieldMap, fi, traverseArray, "   ");
            System.out.println(fi.propertyName + " has dependency depth of " + fi.dependingLevelDepth);
        }
        Collections.sort(computeOrderList, new ComputeOrderComparator<FieldInfo>());
        return fieldMap;
    }

    private static int countDependentLevel(Map<String, FieldInfo> fieldMap, FieldInfo fi, int[] fieldPos, String ident) {
        DepFieldInfo[] depInfo = fi.getDependingField();
        if (fieldPos[fi.fieldPosition] != 0) {
            System.out.println(ident + fi.propertyName + " has been referenced before in the dependency chain");
            // return from here, otherwise, it will not stop
            return 0;
        } else {
            fieldPos[fi.fieldPosition] = 1;
        }
        if (depInfo == null) {
            System.out.println(ident + fi.propertyName + " has 0 level of dependencies");
            return 0;
        } else {
            // if this field has more than one dependency field
            // pick the deepest one as the depth level
            int maxLevel = 0;
            for (int i=0; i<depInfo.length; i++) {
                FieldInfo depfi = fieldMap.get(depInfo[i].getPropertyName());
                int currLevel = countDependentLevel(fieldMap, depfi, fieldPos, ident + "   ");
                if (currLevel > maxLevel) {
                    maxLevel = currLevel;
                }
            }
            System.out.println(ident + fi.propertyName + " has " + (maxLevel + 1) + " level of dependencies");
            return maxLevel + 1;
        }
    }

    private static class ComputeOrderComparator<F extends FieldInfo> implements Comparator<FieldInfo> {
        ComputeOrderComparator() {
        }

        public int compare(FieldInfo o1, FieldInfo o2) {
            return (o1.dependingLevelDepth - o2.dependingLevelDepth);
        }

        public boolean equals(Object o) {
            System.out.println("Calling equals for EvaluationOrderComparator is not supported");
            return false;
        }
    }
}
