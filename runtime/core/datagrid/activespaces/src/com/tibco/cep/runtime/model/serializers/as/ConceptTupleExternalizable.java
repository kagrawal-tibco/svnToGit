package com.tibco.cep.runtime.model.serializers.as;

import com.tibco.as.space.DateTime;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.data.ASDateTime;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.property.simple.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 19, 2010
 * Time: 12:12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptTupleExternalizable implements TupleExternalizable<Concept> {

    public static final String ID_FLD           = "_id_";
    public static final String VERSION_FLD      = "_version_";
    public static final String EXT_FLD          = "_extid_";
    public static final String PARENT_FLD       = "_parent_";
    public static final String REVERSEREFS_FLD  = "_revereserefs_";
    public static final String STATE_FLD        = "_states_";

    public static final int STATE_NEW = 0;
    public static final int STATE_MODIFIED = 1;
    public static final int STATE_DELETED = 2;


    /**
     * Serialize a instance of a concept to Tuple.
     * The concept has predefined header fields [K:Key, M: Mandatory, U:User defined Length, N:Nilable]
     * id   -> long [8] [K] [M]
     * version -> int [4] [M]
     * extId -> String [U][N]
     * parent -> long[8] [N] for contained concept
     * reverserefs -> blob [N] [length[Int]:[List of String][List of Longs]
     * state-> byte[M]
     * Properties...
     */
    public Tuple toTuple(Concept cept) throws Exception {

        //Tuple map = Tuple.create();
        ArrayMap map = new ArrayMap();

        map.put(ID_FLD, cept.getId());
        map.put(VERSION_FLD, cept.getVersion());

        Concept parent = cept.getParent();
        map.put(PARENT_FLD, parent != null ? parent.getId() : -1);

        map.put(STATE_FLD, STATE_NEW);

        String extId = cept.getExtId();
        if ((extId != null) && extId.length() > 0)
            map.put(EXT_FLD, extId);

        Property[] props = cept.getProperties();

        for(Property prop : props) {
            if (prop instanceof PropertyAtom) {
                serializePropertyAtom((PropertyAtom)prop, map);
            }
            else if (prop instanceof PropertyArray) {
                serializePropertyArray((PropertyArray)prop, map);
            }
        }

        return map;
    }

    final private void serializePropertyArray(PropertyArray par, Tuple tuple) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(128);
        DataOutputStream dos = new DataOutputStream(bos);
        int length = par.length();
        String name = par.getName();
        dos.writeInt(length);
        for (int i=0; i < length; i++){
            PropertyAtom pa = par.get(i);
            pa.writeToDataOutput(dos);
        }
        dos.flush();
        byte[] buf = bos.toByteArray();
        tuple.put(name, buf);
    }


    final private void serializePropertyAtom(PropertyAtom pa, Tuple tuple) throws Exception{

        String name = pa.getName();

        if (pa instanceof PropertyAtomSimple) {
            serializeSimpleTypedValue(tuple, (PropertyAtomSimple) pa);
        }
        else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(128);
            DataOutputStream dos = new DataOutputStream(bos);
            pa.writeToDataOutput(dos);
            dos.flush();
            byte[] buf = bos.toByteArray();
            tuple.put(name, buf);
        }
    }



    final private void serializeSimpleTypedValue(Tuple tuple, PropertyAtomSimple pas) {

        String name = pas.getName();
        if (pas instanceof PropertyAtomStringSimple) {
            tuple.put(name, ((PropertyAtomStringSimple)pas).getString());
       }
        else if (pas instanceof PropertyAtomIntSimple) {
            tuple.put(name, ((PropertyAtomIntSimple)pas).getInt());
        }
        else if (pas instanceof PropertyAtomLongSimple) {
            tuple.put(name, ((PropertyAtomLongSimple)pas).getLong());
        }
        else if (pas instanceof PropertyAtomDoubleSimple) {
            tuple.put(name, ((PropertyAtomDoubleSimple)pas).getDouble());
        }
        else if (pas instanceof PropertyAtomDateTimeSimple) {
            tuple.put(name, ASDateTime.create( ((PropertyAtomDateTimeSimple)pas).getDateTime()) );
        }
        else if (pas instanceof PropertyAtomBooleanSimple) {
            tuple.put(name, ((PropertyAtomBooleanSimple)pas).getBoolean());
        }
        else if (pas instanceof PropertyAtomConceptReferenceSimple) {
            tuple.put(name, ((PropertyAtomConceptReferenceSimple)pas).getConceptId());
        }
        else if (pas instanceof PropertyAtomContainedConceptSimple) {
            tuple.put(name, ((PropertyAtomContainedConceptSimple)pas).getConceptId());
        }

    }

    
    public Concept fromTuple(Tuple tuple) throws Exception {
        return null;
    }

    static class ArrayMap<K, V> extends Tuple {
        ArrayList list = new ArrayList(128);
        static class Entry<K, V> {
            K key;
            V value;
        }
        
        public Object put(String key, Object value) {
            //Entry<K,V> e = new Entry<K, V>();
            list.add(key);
            list.add(value);

            return null;
        }

        @Override
        public Object get(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Boolean getBoolean(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Character getChar(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Short getShort(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Integer getInt(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Long getLong(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Float getFloat(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Double getDouble(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getString(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public DateTime getDateTime(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public byte[] getBlob(String s) {
            return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object putBoolean(String s, boolean b) {
            list.add(s);
            list.add(b);
            return this;
        }

        @Override
        public Object putChar(String s, char c) {
            list.add(s);
            list.add(c);
            return this;
        }

        @Override
        public Object putShort(String s, short i) {
            list.add(s);
            list.add(i);
            return this;

        }

        @Override
        public Object putInt(String s, int i) {
            list.add(s);
            list.add(i);
            return this;
        }

        @Override
        public Object putLong(String s, long l) {
            list.add(s);
            list.add(l);
            return this;
        }

        @Override
        public Object putFloat(String s, float v) {
            list.add(s);
            list.add(v);
            return this;
        }

        @Override
        public Object putDouble(String s, double v) {
            list.add(s);
            list.add(v);
            return this;
        }

        @Override
        public Object putString(String s, String s1) {
            list.add(s);
            list.add(s1);
            return this;
        }

        @Override
        public Object putDateTime(String s, DateTime datetime) {
            list.add(s);
            list.add(datetime);
            return this;
        }

        @Override
        public Object putBlob(String s, byte[] bytes) {
            list.add(s);
            list.add(bytes);
            return this;
        }

        @Override
        public Object put(String s, boolean b) {
            list.add(s);
            list.add(b);
            return this;
        }

        @Override
        public Object put(String s, char c) {
            list.add(s);
            list.add(c);
            return this;
        }

        @Override
        public Object put(String s, short i) {
            list.add(s);
            list.add(i);
            return this;
        }

        @Override
        public Object put(String s, int i) {
            list.add(s);
            list.add(i);
            return this;
        }

        @Override
        public Object put(String s, long l) {
            list.add(s);
            list.add(l);
            return this;
        }

        @Override
        public Object put(String s, float v) {
            list.add(s);
            list.add(v);
            return this;
        }

        @Override
        public Object put(String s, double v) {
            list.add(s);
            list.add(v);
            return this;
        }

        @Override
        public Object put(String s, String s1) {
            list.add(s);
            list.add(s1);
            return this;
        }

        @Override
        public Object put(String s, DateTime datetime) {
            list.add(s);
            list.add(datetime);
            return this;
        }

        @Override
        public Object put(String s, byte[] bytes) {
            list.add(s);
            list.add(bytes);
            return this;
        }

        @Override
        public void clear() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object remove(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int size() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public byte[] serialize() {
            return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void deserialize(byte[] bytes) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean isNull(String s) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean exists(String s) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Collection<String> getFieldNames() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public FieldDef.FieldType getFieldType(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void putAll(Tuple tuple) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object clone() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean equals(Object o) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int hashCode() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return false;
		}

		@Override
		public boolean containsKey(Object key) {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return false;
		}

		@Override
		public Object get(Object key) {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return null;
		}

		@Override
		public Object remove(Object key) {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return null;
		}

		@Override
		public void putAll(Map<? extends String, ? extends Object> m) {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			
		}

		@Override
		public Set<String> keySet() {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return null;
		}

		@Override
		public Collection<Object> values() {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return null;
		}

		@Override
		public Set<java.util.Map.Entry<String, Object>> entrySet() {
			// TODO Auto-generated method stub (Added to compile with AS 2.0.1 V32)
			return null;
		}
    }


}
