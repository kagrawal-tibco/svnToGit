package com.tibco.be.migration.expimp.providers.csv;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Calendar;

import com.tibco.be.dbutils.PropertyTypes;
import com.tibco.be.migration.expimp.providers.bdb.BDBExporter_v2_0;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 27, 2008
 * Time: 5:10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVPropertyDeserializer {

    protected Concept concept = null;
    static Field g_m_status_field = null;
    static byte g_IS_SET = 0;
    static {
        try {
            Field [] flds = PropertyImpl.class.getDeclaredFields();
            //Field.setAccessible(flds, true);
            for (int i=0; i < flds.length ; i++)  {
               Field f = flds[i];
               if (f.getType() == java.lang.Byte.TYPE) {
                   if (f.getName() == "m_status"){
                       f.setAccessible(true);
                       g_m_status_field = f;
                   }
                   else if ((f.getName() == "IS_SET") && Modifier.isStatic(f.getModifiers())) {
                       f.setAccessible(true);
                       g_IS_SET = f.getByte(null);
                   }
                }
            }
        } catch (IllegalArgumentException iae) {
            assert false : iae.getStackTrace();
        } catch (IllegalAccessException iae1) {
            assert false : iae1.getStackTrace();
        } catch (Exception e) {
            assert false : e.getStackTrace();
        }        
    }
    private byte bTemp = 0;
    private String statsMsg = null;
    public Concept deserialize(Logger logger, String[] columns, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        //#"conceptId\propertyName\Type\isSet\arrayInde\value\historysize\currentIndex\[{HistoryTS\HistoryValue}...]"
        //HashMap cmap = new HashMap(); // list of concepts changed ( parent , contained concept )
        final long id = Long.parseLong(columns[0]);
        String propName = null;
        int type = -1;
        boolean isSet = false;
        int arrayIndex = -1;
        String value = null;
        int historySize = 0;
        int currentIndex = 0;

        if ((null == this.concept) || (this.concept.getId() != id)) {
            // Avoid loading the concept from the store since that would void any previously loaded properties.
            // Note: assumes properties are grouped by owner Concept
            this.concept = (Concept) entityStore.getById(id);
        }

        if (this.concept == null)
            return null;

        if (columns[1] != null)
            propName = columns[1];
        else {
            logger.log(Level.ERROR, "Property name is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        if (inputVersion.startsWith("1.4") && "TransitionStatuses".equals(propName)) {
            // TODO: need to set concept statemachine property. for now, just ignore it and return null;
            return null;
        }

        if (columns[2] != null)
            type = Integer.valueOf(columns[2]).intValue();
        else {
            logger.log(Level.ERROR, "Property type is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        if (columns[3] != null)
            isSet = Boolean.valueOf(columns[3]).booleanValue();
        else  {
            logger.log(Level.ERROR, "Property isSet is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        if (columns[4] != null)
            arrayIndex = Integer.valueOf(columns[4]).intValue();
        else {
            logger.log(Level.ERROR, "Property arrayIndex is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        if (columns[5] != null)
            value = columns[5];
        else {
            logger.log(Level.ERROR, "Property value is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        if (columns[6] != null)
            historySize = Integer.valueOf(columns[6]).intValue();
        else {
            logger.log(Level.ERROR, "Property historySize is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        if (columns[7] != null)
            currentIndex = Integer.valueOf(columns[7]).intValue();
        else if (historySize == 0)
            currentIndex = 0;
        else {
            logger.log(Level.ERROR, "Property currentIndex is null! Concept id: %s. Ignored.", columns[0]);
            return null;
        }

        // handle historySize > 0
        Object[][] history = null;
        if (historySize > 0) {
            final int indexOfFirstHistoryValue = 8;
            final int numHistoryColumns = columns.length - indexOfFirstHistoryValue;
            final int usedHistorySize = numHistoryColumns / 2;
            history = new Object[usedHistorySize][2];
            int i = 0;
            // Reads the (old) values located after the current index
            for (int col = indexOfFirstHistoryValue + (currentIndex + 1) * 2;
                 (col < indexOfFirstHistoryValue + numHistoryColumns - 1) && (i < usedHistorySize); i++) {
                history[i][0] = new Long(columns[col]);
                col++;
                history[i][1] = columns[col];
                col++;
            }
            // Reads the values from 0 to the current index
            for (int col = indexOfFirstHistoryValue; (col <= indexOfFirstHistoryValue + currentIndex * 2); i++) {
                history[i][0] = new Long(columns[col]);
                col++;
                history[i][1] = columns[col];
                col++;
            }
        }

        Property prop = this.concept.getProperty(propName);
        // TODO: remove this block once code generation part is fixed.
        // Handle statemachine property differently
        if (prop == null && type == PropertyTypes.propertyTypes_atomContainedConcept && propName.indexOf('$') > -1) { // this is a statemachine property
            prop = this.getProperty(propName, logger);
            if (prop == null) {
                statsMsg = "Property not found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                        + ", Property: (name: " + propName + ", type: " + type + ", isSet:" + isSet + ", value:" + value + "). Ignored.";
                logger.log(Level.ERROR, statsMsg);
                return null;
            }
        }

        if (prop instanceof PropertyAtomConceptReference) {
            final PropertyAtomConceptReference pac = (PropertyAtomConceptReference) this.concept.getPropertyAtom(propName);
            //((PropertyAtomImpl) pac).clearIsSet();
            bTemp = g_m_status_field.getByte(pac);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(pac, bTemp);
            if (history == null) {
                 if (value == null) { // the concept property value was not set
                    pac.setConcept(null);
                } else {
                    EntityStore rcEntityStore = entityStore.getDbStore().getEntityStoreForClass(pac.getType().getName());
                    ConceptImpl rc = (ConceptImpl) rcEntityStore.getById(new Long(value).longValue());
                    if (rc == null) {
                        statsMsg = "ConceptReference no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                + ", Property: (name: " + propName + ", type : AtomConceptReference, isSet: " + isSet + ", value(concept reference): " + value
                                + ", history: " + historySize + "). Ignored.";
                        logger.log(Level.ERROR, statsMsg);
                    }
                    pac.setConcept(rc);
                }
            } else {
                for (int j = 0; j < history.length; j++) {
                    if (history[j][1] == null) { // the concept property value was not set
                    pac.setConcept(null, ((Long) history[j][0]).longValue());
                    } else {
                        EntityStore rcEntityStore = entityStore.getDbStore().getEntityStoreForClass(pac.getType().getName());
                        ConceptImpl rc = (ConceptImpl) rcEntityStore.getById(new Long((String) history[j][1]).longValue());
                        if (rc == null) {
                            statsMsg = "ConceptReference no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                    + ", Property: (name: " + propName + ", type : AtomConceptReference, isSet: " + isSet + ", value(concept reference): " + value
                                    + ", history: " + historySize + "). Ignored.";
                            logger.log(Level.ERROR, statsMsg);
                        }
                        pac.setConcept(rc, ((Long) history[j][0]).longValue());
                    }
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) pac).setIsSet();
                bTemp = g_m_status_field.getByte(pac);
                bTemp |= g_IS_SET;
                g_m_status_field.setByte(pac, bTemp);
            }
        } else if (prop instanceof PropertyArrayConceptReference) {
            if (arrayIndex < 0 && !isSet || value == null) { // 0 sized property array, value was not set
                return null;
            }
            final PropertyArrayConceptReference pac = (PropertyArrayConceptReference) this.concept.getPropertyArray(propName);
            pac.set(arrayIndex, null); // Creates space for the array value.
            final PropertyAtomConceptReference p = ((PropertyAtomConceptReference) pac.get(arrayIndex));
            //((PropertyAtomImpl) p).clearIsSet();
            bTemp = g_m_status_field.getByte(p);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(p, bTemp);
            if (history == null) {
                if (value == null) { // the concept property value was not set
                    p.setConcept(null);
                } else {
                    EntityStore rcEntityStore = entityStore.getDbStore().getEntityStoreForClass(p.getType().getName());
                    ConceptImpl rc = (ConceptImpl) rcEntityStore.getById(new Long(value).longValue());
                    if (rc == null) {
                        statsMsg = "ConceptReference no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                + ", Property: (name: " + propName + ", type : ArrayConceptReference, isSet: " + isSet + ", value(concept reference): " + value
                                + ", history: " + historySize + "). Ignored.";
                        logger.log(Level.ERROR, statsMsg);
                    }
                    p.setConcept(rc);
                }
            } else {
                for (int j = 0; j < history.length; j++) {
                    if (history[j][1] == null) { // the concept property value was not set
                        p.setConcept(null, ((Long) history[j][0]).longValue());
                    } else {
                        EntityStore rcEntityStore = entityStore.getDbStore().getEntityStoreForClass(p.getType().getName());
                        ConceptImpl rc = (ConceptImpl) rcEntityStore.getById(new Long((String) history[j][1]).longValue());
                        if (rc == null) {
                            statsMsg = "ConceptReference no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                    + ", Property: (name: " + propName + ", type : ArrayConceptReference, isSet: " + isSet + ", value(concept reference): " + value
                                    + ", history: " + historySize + "). Ignored.";
                            logger.log(Level.ERROR, statsMsg);
                        }
                        p.setConcept(rc, ((Long) history[j][0]).longValue());
                    }
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) p).setIsSet();
                byte temp = g_m_status_field.getByte(p);
                temp |= g_IS_SET;
                g_m_status_field.setByte(p, temp);
            }
        } else if (prop instanceof PropertyAtomContainedConcept) {
            final PropertyAtomContainedConcept pac = (PropertyAtomContainedConcept) prop; //this.concept.getPropertyAtom(propName);
            //((PropertyAtomImpl) pac).clearIsSet();
            bTemp = g_m_status_field.getByte(pac);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(pac, bTemp);
            if (history == null) {
                if (value == null) { // the concept property value was not set
                    pac.setContainedConcept(null);
                } else {
                    String pName = pac.getType().getName();
                    EntityStore ccEntityStore = entityStore.getDbStore().getEntityStoreForClass(pName);
                    ContainedConcept cc = (ContainedConcept) ccEntityStore.getById(new Long(value).longValue());
                    if (cc == null) {
                        statsMsg = "ContainedConcept no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                + ", Property: (name: " + propName + ", type : AtomContainedConcept, isSet: " + isSet + ", value(contained  concept): " + value
                                + ", history: " + historySize + "). Ignored.";
                        logger.log(Level.ERROR, statsMsg);
                    }
                    pac.setContainedConcept(cc);
                }
            } else {
                for (int j = 0; j < history.length; j++) {
                    if (history[j][1] == null) { // the concept property value was not set
                        pac.setContainedConcept(null, ((Long) history[j][0]).longValue());
                    } else {
                        EntityStore ccEntityStore = entityStore.getDbStore().getEntityStoreForClass(pac.getType().getName());
                        ContainedConcept cc = (ContainedConcept)
                                ccEntityStore.getById(new Long((String) history[j][1]).longValue());
                        if (cc == null) {
                            statsMsg = "ContainedConcept no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                    + ", Property: (name: " + propName + ", type : AtomContainedConcept, isSet: " + isSet + ", value(contained  concept): " + value
                                    + ", history: " + historySize + "). Ignored.";
                            logger.log(Level.ERROR, statsMsg);
                        }
                        pac.setContainedConcept(cc, ((Long) history[j][0]).longValue());
                    }
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) pac).setIsSet();
                byte temp = g_m_status_field.getByte(pac);
                temp |= g_IS_SET;
                g_m_status_field.setByte(pac, temp);
            }
        } else if (prop instanceof PropertyArrayContainedConcept) {
            if (arrayIndex < 0 && !isSet || value == null) { // 0 sized property array, value was not set
                return null;
            }
            final PropertyArrayContainedConcept pac = (PropertyArrayContainedConcept) this.concept.getPropertyArray(propName);
            pac.set(arrayIndex, null); // Creates space for the array value.
            final PropertyAtomContainedConcept p = ((PropertyAtomContainedConcept) pac.get(arrayIndex));
            //((PropertyAtomImpl) p).clearIsSet();
            bTemp = g_m_status_field.getByte(p);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(p, bTemp);
            if (history == null) {
                if (value == null) { // the concept property value was not set
                    p.setContainedConcept(null);
                } else {
                    EntityStore ccEntityStore = entityStore.getDbStore().getEntityStoreForClass(p.getType().getName());
                    ContainedConcept cc = (ContainedConcept) ccEntityStore.getById(new Long(value).longValue());
                    if (cc == null) {
                        statsMsg = "ContainedConcept no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                + ", Property: (name: " + propName + ", type : AtomContainedConcept, isSet: " + isSet + ", value(contained  concept): " + value
                                + ", history: " + historySize + "). Ignored.";
                        logger.log(Level.ERROR, statsMsg);
                    }
                    p.setContainedConcept(cc);
                }
            } else {
                for (int j = 0; j < history.length; j++) {
                    if (history[j][1] == null) { // the concept property value was not set
                        p.setContainedConcept(null, ((Long) history[j][0]).longValue());
                    } else {
                        EntityStore ccEntityStore = entityStore.getDbStore().getEntityStoreForClass(p.getType().getName());
                        ContainedConcept cc = (ContainedConcept) ccEntityStore.getById(new Long((String) history[j][1]).longValue());
                        if (cc == null) {
                            statsMsg = "ContainedConcept no found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                                    + ", Property: (name: " + propName + ", type : AtomContainedConcept, isSet: " + isSet + ", value(contained  concept): " + value
                                    + ", history: " + historySize + "). Ignored.";
                            logger.log(Level.ERROR, statsMsg);
                        }
                        p.setContainedConcept(cc, ((Long) history[j][0]).longValue());
                    }
                }
                if (isSet) { // setIsSet
                    //((PropertyAtomImpl) p).setIsSet();
                    bTemp = g_m_status_field.getByte(p);
                    bTemp |= g_IS_SET;
                    g_m_status_field.setByte(p, bTemp);
                }
            }
        } else if (prop instanceof PropertyAtomDateTime) {
            final PropertyAtomDateTime p = ((PropertyAtomDateTime) this.concept.getPropertyAtom(propName));
            //((PropertyAtomImpl) p).clearIsSet();
            bTemp = g_m_status_field.getByte(p);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(p, bTemp);
            if (history == null) {
                //p.setValue(this.parseDate(value, inputVersion));
                p.setValue(value);
            } else {
                for (int j = 0; j < history.length; j++) {
                    //p.setValue(this.parseDate((String) history[j][1], inputVersion), ((Long) history[j][0]).longValue());
                    Calendar cal=(Calendar) BDBExporter_v2_0.xsd2java_dt_conv.convertSimpleType((String) history[j][1]);
                    p.setValue(cal, ((Long) history[j][0]).longValue());
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) p).setIsSet();
                bTemp = g_m_status_field.getByte(p);
                bTemp |= g_IS_SET;
                g_m_status_field.setByte(p, bTemp);
            }
        } else if (prop instanceof PropertyArrayDateTime) {
            if (arrayIndex < 0 && !isSet || value == null) { // 0 sized property array, value was not set
                return null;
            }
            final PropertyArrayDateTime pac = (PropertyArrayDateTime) this.concept.getPropertyArray(propName);
            pac.set(arrayIndex, null); // Creates space for the array value.
            final PropertyAtomDateTime p = ((PropertyAtomDateTime) pac.get(arrayIndex));
            //((PropertyAtomImpl) p).clearIsSet();
            bTemp = g_m_status_field.getByte(p);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(p, bTemp);
            if (history == null) {
                //p.setValue(this.parseDate(value, inputVersion));
                p.setValue(value);
            } else {
                for (int j = 0; j < history.length; j++) {
                    //p.setValue(this.parseDate((String) history[j][1], inputVersion), ((Long) history[j][0]).longValue());
                    Calendar cal=(Calendar) BDBExporter_v2_0.xsd2java_dt_conv.convertSimpleType((String) history[j][1]);
                    p.setValue(cal, ((Long) history[j][0]).longValue());
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) p).setIsSet();
                bTemp = g_m_status_field.getByte(p);
                bTemp |= g_IS_SET;
                g_m_status_field.setByte(p, bTemp);
            }

        } else if (prop instanceof PropertyAtom) {
            final PropertyAtom propAtom = this.concept.getPropertyAtom(propName);
            //((PropertyAtomImpl) propAtom).clearIsSet();
            bTemp = g_m_status_field.getByte(propAtom);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(propAtom, bTemp);
            if (history == null) {
                // Per Puneet: replace any possible NaN long value string to a null string.
                if (value.equals("NaN") && (type == PropertyTypes.propertyTypes_arrayDouble || type == PropertyTypes.propertyTypes_atomDouble)) {
                    String nullValue = "0.00";
                    propAtom.setValue(nullValue);
                } else {
                    propAtom.setValue(value);
                }
            } else {
                for (int j = 0; j < history.length; j++) {
                    // replace any possible NaN long value string to a null string.
                    if (history[j][1] != null && history[j][1].equals("NaN") && (type == PropertyTypes.propertyTypes_arrayDouble || type == PropertyTypes.propertyTypes_atomDouble)) {
                        String nullValue = "0.00";
                        propAtom.setValue(nullValue, ((Long) history[j][0]).longValue()); // is supposed to be null. But see what PropertyAtomDoubleImpl.setValue(null, time) does?
                    } else {
                        propAtom.setValue(history[j][1], ((Long) history[j][0]).longValue());
                    }
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) propAtom).setIsSet();
                bTemp = g_m_status_field.getByte(propAtom);
                bTemp |= g_IS_SET;
                g_m_status_field.setByte(propAtom, bTemp);
            }
        } else {  // PropertyArray
            if (arrayIndex < 0 && !isSet || value == null) { // 0 sized property array, value was not set
                return null;
            }
            final PropertyArray pac = this.concept.getPropertyArray(propName);
            if (pac == null) {
                statsMsg = "PropertyArray not found! Concept: " + this.concept.getClass().getName() + ", id: " + id
                            + "Property: (name: " + propName + ", type: " + type + ", isSet:" + isSet + ", value:" + value + "). Ignored.";
                logger.log(Level.ERROR, statsMsg);
                return null;
            }
            pac.add();
            final PropertyAtom p = ((PropertyAtom) pac.get(arrayIndex));
            //((PropertyAtomImpl) p).clearIsSet();
            bTemp = g_m_status_field.getByte(p);
            bTemp &= ~g_IS_SET;
            g_m_status_field.setByte(p, bTemp);
            if (history == null) {
                // Per Puneet: replace any possible NaN long value string to a null string.
                if (value.equals("NaN") && (type == PropertyTypes.propertyTypes_arrayDouble || type == PropertyTypes.propertyTypes_atomDouble)) {
                    String nullValue = "0.00";
                    p.setValue(nullValue);
                } else {
                    p.setValue(value);
                }
            } else {
                for (int j = 0; j < history.length; j++) {
                    // replace any possible NaN long value string to a null string.
                    if (history[j][1].equals("NaN") && (type == PropertyTypes.propertyTypes_arrayDouble || type == PropertyTypes.propertyTypes_atomDouble)) {
                        String nullValue = "0.00";
                        p.setValue(nullValue, ((Long) history[j][0]).longValue());
                    } else {
                        p.setValue(history[j][1], ((Long) history[j][0]).longValue());
                    }
                }
            }
            if (isSet) { // setIsSet
                //((PropertyAtomImpl) p).setIsSet();
                bTemp = g_m_status_field.getByte(p);
                bTemp |= g_IS_SET;
                g_m_status_field.setByte(p, bTemp);
            }
        }
        
        //cmap.put(new Long(this.concept.getId()), this.concept);
        return this.concept;
    }


    public CSVPropertyDeserializer() {
    }

    // TODO: remove this block once code generation part is fixed.
    /**
     * There is a change in getProperty generated concept class which expects '.' instead of '$' in propName of a SM
     * See ConceptClassGenerator_V2.getAllStateMachineProperties(Concept cept, HashMap propertyToMember)
     * temperay workaround is to make up the expected property name from propName and class/supperClass name.
     */
    private Property getProperty(String propName, Logger logger) {
        String names[] = propName.split("\\$");
        String cNameInPropName = names[0];
        String smName = names[1];
        String gcName = ModelNameUtil.generatedClassNameToModelPath(concept.getClass().getName()); // generated class name without gengered package prefix
        String cName = gcName.substring(gcName.lastIndexOf('/') + 1);

        if (!cName.equals(cNameInPropName)) { // it's the statemachine in super class
            Class supCls = concept.getClass().getSuperclass(); // get supper class
            // try to get property in the ancestor class, stop at the very top levele generated class
            while (supCls.getName().startsWith(ModelNameUtil.GENERATED_PACKAGE_PREFIX)) {
                gcName = ModelNameUtil.generatedClassNameToModelPath(supCls.getName());
                cName = gcName.substring(gcName.lastIndexOf('/') + 1);
                if (cName.equals(cNameInPropName)) { // it's the statemachine in super class
                    logger.log(Level.DEBUG, "Found statemachine with name: %s.%s, in class: %s",
                            gcName, smName, supCls.getName());
                    return this.concept.getProperty(gcName + "." + smName);
                }
                logger.log(Level.DEBUG, "Property still not found in supper concept: %s, statemachine name: %s.%s",
                        supCls.getName(), gcName, smName);
                supCls = supCls.getSuperclass();
            }
        }
        else {
            return this.concept.getProperty(gcName + "." + smName);
        }

        return null;
    }

}
