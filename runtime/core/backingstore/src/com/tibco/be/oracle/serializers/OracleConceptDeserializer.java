package com.tibco.be.oracle.serializers;

import java.io.BufferedReader;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.STRUCT;

import com.tibco.be.oracle.PropertyMap;
import com.tibco.be.oracle.impl.OracleConceptMap;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.DateTimeTupleImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 23, 2006
 * Time: 2:18:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleConceptDeserializer implements ConceptDeserializer {
    boolean error=false;
    String msg;

    OracleConceptMap entityDescription;
    Object [] attributes;
    int cur=0;
    PropertyMap curDesc;
    OracleConnection oracle;
    Datum[] datum;
    boolean inHistory=false;
    Datum[] historyAttributes;
    int historyTimeCursor=0, historyValueCursor=0;
    Datum[] timeSeries;
    Datum[] valSeries;
    boolean inArray=false;
    Datum[] arrayAttributes;
    int arrayCursor=0;
    long lastTime=0;
    STRUCT currHistStruct;
    String nextTz;
    Datum currentDatum;
    /**
     *
     * @param buf
     */
    public OracleConceptDeserializer(OracleConceptMap entityDescription, OracleConnection oracle, Datum[] attributes) {
        this.entityDescription=entityDescription;
        this.oracle=oracle;
        this.attributes=attributes;

    }

    public boolean hasSchemaChanged() {
        return false;
    }

    public ConceptSerializer.ConceptMigrator getConceptMigrator() {
        return null;
    }

    public int getVersion() {
        return 0;
    }

    public boolean isDeleted() {
        return false;
    }

    /**
     *
     * @return
     */
    public Datum[] getOracleAttributes() throws Exception{
        //STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(), oracle, attributes);
        STRUCT s= new STRUCT(entityDescription.getTypeDescriptor(oracle),oracle, attributes);
        return s.getOracleAttributes();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    Datum currentDatum() throws Exception{
        if (inArray) {
            //ARRAY a = (ARRAY) attributes[cur];
            //Datum[] attributes= a.getOracleArray();
            return arrayAttributes[this.arrayCursor];
        } else {
            return (Datum) attributes[cur];
        }
    }

    /**
     *
     */
    public long startConcept() {
        try {
            cur=5;
            return ((oracle.sql.NUMBER) attributes[0]).longValue();
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endConcept() {

    }

    /**
     *
     * @return
     */
    public String getExtId() {
        oracle.sql.CHAR extId= (oracle.sql.CHAR) attributes[1];
        if (extId != null) {
            return extId.stringValue();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public long getId() {
        try {
            oracle.sql.NUMBER id= (oracle.sql.NUMBER) attributes[0];
            if (id != null) {
                return id.longValue();
            }
            return 0L;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return
     */
    public ConceptOrReference startParentConcept() {
        try {
            oracle.sql.STRUCT parent= (oracle.sql.STRUCT) attributes[5];
            if (parent != null) {
                Datum[] attributes= parent.getOracleAttributes();
                if (attributes[0] != null) {
                    return new com.tibco.cep.runtime.model.element.impl.Reference(((oracle.sql.NUMBER)attributes[0]).longValue());
                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     */
    public void endParentConcept() {

    }

    /**
     *
     * @return
     */
    public int startReverseReferences() {
        try {
            oracle.sql.ARRAY reverseRefsArray= (oracle.sql.ARRAY) attributes[6];
            if (reverseRefsArray != null) {
                Datum[] reverseRefs= reverseRefsArray.getOracleArray();
                if (reverseRefs != null) {
                    return reverseRefs.length;
                } else {
                    return 0;
                }
            }
            return 0;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void getReverseReferences(ConceptImpl.RevRefItr itr) {
        try {
            oracle.sql.ARRAY reverseRefsArray= (oracle.sql.ARRAY) attributes[6];
            if (reverseRefsArray != null) {
                Datum[] refs= reverseRefsArray.getOracleArray();
                if (reverseRefsArray != null) {
                    for (int i=0; i < refs.length; i++) {
                        oracle.sql.STRUCT ref= (oracle.sql.STRUCT) refs[i];
                        itr.next();
                        if (ref != null) {
                            Datum[] atts = ref.getOracleAttributes();
                            if (atts != null) {
                                if (atts[0] != null) {
                                    itr.setPropName(((oracle.sql.CHAR) atts[0]).stringValue());
                                    if (atts[1] != null) {
                                        itr.setReverseRef(new com.tibco.cep.runtime.model.element.impl.Reference(((oracle.sql.NUMBER)atts[1]).longValue()));
                                    }
                                }
                            }
                         }
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void endReverseReferences() {

    }

    /**
     *
     * @return
     */
    public int getType() {
        return ConceptSerializer.TYPE_STREAM;
    }

    public boolean startProperty(String propertyName, int propertyIndex) {
        try {
            if (inArray) {
                return arrayAttributes[arrayCursor] != null;
            } else {
                curDesc= entityDescription.getPropertyMap(propertyIndex);
                cur=curDesc.getColumnIndex();
                //curDesc=(PropertyDescriptor) entityDescription.getPropertyDescriptions().get(propertyIndex);
                return (attributes[cur] != null);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }


    public int startPropertyArray(String propertyName, int propertyIndex) {
        try {
            inArray=true;
            curDesc= entityDescription.getPropertyMap(propertyIndex);
            cur=curDesc.getColumnIndex();

            ARRAY dt= (ARRAY) attributes[cur];
            if (dt != null) {
                arrayAttributes = dt.getOracleArray();
                return ((ARRAY)dt).length();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param index
     */
    public boolean startPropertyArrayElement(int index) {
        inArray=true;
        arrayCursor=index;
        return true;
    }

    /**
     *
     */
    public void endPropertyArrayElement() {
        inArray=false;
    }

    /**
     *
     * @param propertyName
     * @param index
     * @return
     */
    public int startPropertyAtom(String propertyName, int index) {
        try {
            if (!inArray) {
                curDesc= entityDescription.getPropertyMap(index);
                cur=curDesc.getColumnIndex();
            }
            STRUCT cur = (STRUCT) currentDatum();
            if (cur != null) {
                Datum[] attributes= cur.getOracleAttributes();
                return ((oracle.sql.NUMBER) attributes[0]).intValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     */
    public void endProperty() {
        curDesc=null;
        inHistory=false;
        historyAttributes=null;
        historyTimeCursor=0;
        historyValueCursor=0;
        inArray=false;
        arrayAttributes=null;
        arrayCursor=0;
        lastTime=0;
        nextTz=null;
    }


    public String getStringProperty() {
        try {
            Object ret=currentDatum();
            if (ret != null) {
                if (ret instanceof oracle.sql.CHAR) {
                    oracle.sql.CHAR tmp=(oracle.sql.CHAR) currentDatum();
                    return tmp.stringValue();
                } else if (ret instanceof oracle.sql.CLOB) {
                    CLOB clob= (CLOB) ret;
                    String str ="";
                    StringBuilder clobcontent = new StringBuilder();
                    BufferedReader re = new BufferedReader(clob.getCharacterStream());
                    while ((str = re.readLine()) != null) {
                        clobcontent.append(str);
                    }
                    clob.getCharacterStream().close();
                    return  clobcontent.toString();
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
        return null;
    }

    public int getIntProperty() {
        try {
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
            if (ret != null) {
                return ret.intValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }


    public boolean getBooleanProperty() {
        try {
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
            if (ret != null) {
                return (ret.intValue() == 0)?false:true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }


    public long getLongProperty() {
        try {
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
            if (ret != null) {
                return ret.longValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     */
    public double getDoubleProperty() {
        try {
            oracle.sql.NUMBER ret=(oracle.sql.NUMBER) currentDatum();
            if (ret != null) {
                return ret.doubleValue();
            } else {
                return 0;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return
     */
    public DateTimeTuple getDateTimeProperty() {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                if (attributes != null) {
                    String tz=null;
                    if (attributes[1] != null) {
                        tz=((oracle.sql.CHAR)attributes[1]).stringValue();
                    }
                    long time=0L;
                    if (attributes[0] != null) {
                        time=((oracle.sql.TIMESTAMP)attributes[0]).timestampValue().getTime();
                    }
                    return new DateTimeTupleImpl(time,tz);
                } else {
                    return new DateTimeTupleImpl(0L, null);
                }
            } else {
                return new DateTimeTupleImpl(0L, null);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     */
    public ConceptOrReference getReferenceConceptProperty() {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                if ((attributes != null) && (attributes[0] != null)) {
                    return new com.tibco.cep.runtime.model.element.impl.Reference(((oracle.sql.NUMBER)attributes[0]).longValue());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public ConceptOrReference getContainedConceptProperty() {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                if ((attributes != null) && (attributes[0] != null)) {
                    return new com.tibco.cep.runtime.model.element.impl.Reference(((oracle.sql.NUMBER)attributes[0]).longValue());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param s
     * @param time
     */
    public void getStringProperty(String[] s, long[]time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= s.length > historySize? historySize: s.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        if (atts[1] != null) {
                            oracle.sql.CHAR sRet=((oracle.sql.CHAR)atts[1]);
                            if (sRet != null) {
                                s[i]=sRet.stringValue();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getIntProperty(int[]val, long[]time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= val.length > historySize? historySize: val.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        if (atts[1] != null) {
                            oracle.sql.NUMBER iRet=((oracle.sql.NUMBER)atts[1]);
                            if (iRet != null)
                                val[i]= iRet.intValue();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getBooleanProperty(boolean[]b, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= b.length > historySize? historySize: b.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        if (atts[1] != null) {
                            oracle.sql.NUMBER iRet=((oracle.sql.NUMBER)atts[1]);
                            if (iRet != null)
                                b[i]= iRet.intValue() == 1;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getLongProperty(long[] l, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= l.length > historySize? historySize: l.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        if (atts[1] != null) {
                            oracle.sql.NUMBER iRet=((oracle.sql.NUMBER)atts[1]);
                            if (iRet != null)
                                l[i]= iRet.longValue();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDoubleProperty(double[] d, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= d.length > historySize? historySize: d.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        if (atts[1] != null) {
                            oracle.sql.NUMBER iRet=((oracle.sql.NUMBER)atts[1]);
                            if (iRet != null)
                                d[i]= iRet.doubleValue();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDateTimeProperty(DateTimeTuple[] date, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= date.length > historySize? historySize: date.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts = ht.getOracleAttributes();
                        time[i] = ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        STRUCT s1 = (oracle.sql.STRUCT) atts[1];
                        if (s1 != null) {
                            Datum[] ds = s1.getOracleAttributes();
                            if ((ds[0] != null) && (ds[1] != null)) {
                                DateTimeTupleImpl ti = new DateTimeTupleImpl(((oracle.sql.TIMESTAMP)ds[0]).timestampValue().getTime(), ((oracle.sql.CHAR)ds[1]).stringValue());
                                date[i] = ti;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDateTimeProperty(long[] date, String[] tz, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= date.length > historySize? historySize: date.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        STRUCT s1= (oracle.sql.STRUCT) atts[1];
                        if (s1 != null) {
                            Datum[] ds= s1.getOracleAttributes();
                            if ((ds != null) && (ds[1] != null) && (ds[0] != null)) {
                                date[i] = ((oracle.sql.TIMESTAMP)ds[0]).timestampValue().getTime();
                                tz[i] = ((oracle.sql.CHAR)ds[1]).stringValue();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getReferenceConceptProperty(ConceptOrReference[] ref, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= ref.length > historySize? historySize: ref.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        STRUCT s1= (oracle.sql.STRUCT) atts[1];
                        if (s1 != null) {
                            Datum[] ds = s1.getOracleAttributes();
                            oracle.sql.NUMBER iRet=((oracle.sql.NUMBER)ds[0]);
                            if (iRet != null)
                                ref[i] = new com.tibco.cep.runtime.model.element.impl.Reference(iRet.longValue());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getContainedConceptProperty(ConceptOrReference[] ref, long[] time) {
        try {
            oracle.sql.STRUCT ret=(oracle.sql.STRUCT) currentDatum();
            if (ret != null) {
                Datum[] attributes= ret.getOracleAttributes();
                int historySize= ((oracle.sql.NUMBER)attributes[0]).intValue();
                ARRAY historyTable= (ARRAY) attributes[1];
                if (historyTable != null) {
                    Datum[] historyTuples = historyTable.getOracleArray();
                    int till= ref.length > historySize? historySize: ref.length;

                    for (int i=0; i < till; i++) {
                        STRUCT ht = (STRUCT) historyTuples[i];
                        Datum[] atts= ht.getOracleAttributes();
                        time[i]= ((oracle.sql.TIMESTAMP)atts[0]).timestampValue().getTime();
                        STRUCT s1= (oracle.sql.STRUCT) atts[1];
                        if (s1 != null) {
                            Datum[] ds= s1.getOracleAttributes();
                            oracle.sql.NUMBER iRet=((oracle.sql.NUMBER)ds[0]);
                            if (iRet != null)
                                ref[i]=new com.tibco.cep.runtime.model.element.impl.Reference(iRet.longValue());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public boolean areNullPropsSerialized() {
        return true;
    }
}
