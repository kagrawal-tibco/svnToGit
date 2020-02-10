package com.tibco.cep.runtime.model.element.impl.property.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Iterator;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.PropertyException;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyAtom;
import com.tibco.cep.util.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 23, 2006
 * Time: 8:50:42 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class PropertyAtomImpl extends AbstractPropertyAtom {
    short  m_index = 0;
    long[] m_time = null;

    protected PropertyAtomImpl(int historySize, Object owner) {
        super(owner);
        if(historySize > 0) {
            m_time  = new long[historySize];
            m_time[0] = 0;
        }
    }

    public int getHistorySize() {
        return getMetaProperty().getHistorySize();
    }
    
    @Override
    abstract public PropertyAtomImpl copy(Object newOwner);

    @Override
    protected AbstractPropertyAtom _copy(AbstractPropertyAtom ret) {
    	return _copy((PropertyAtomImpl)ret);
    }
    
    protected PropertyAtomImpl _copy(PropertyAtomImpl ret) {
        ret.m_time   = m_time.clone();
        ret.m_index  = m_index;
        ret.m_status = m_status;
        return ret;
    }

    public Number maxOverTime(long from_no_of_msec_ago) {
        throw new RuntimeException("PropertyAtomImpl.maxOf is not implemented for " + getClass().getSimpleName());
    }

    public Number minOverTime(long from_no_of_msec_ago) {
        throw new RuntimeException("PropertyAtomImpl.minOf is not implemented for " + getClass().getSimpleName());
    }
    
    abstract protected Object valueToObject(int index);

    public String getString(long time) throws PropertyException {
        Object value = getValue(time);
        if(value != null) return value.toString();
        else return null;

    }

    public String getStringAtIdx(int idx) throws PropertyException {
        Object value = getValueAtIdx(idx);
        if(value != null) return value.toString();
        else return null;

    }

    public long getTimeAtIdx(int idx) {
        return m_time[mapIndex(idx)];
    }

    public void serialize_nullprops(ConceptSerializer serializer, int order) {
        try {
            int howMany=howMany(0,0);
            serializer.startPropertyAtom(this.getName(), order, isSet(), m_index, howMany);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize_nullprops(ConceptDeserializer deserializer, int order) {
        short numTuples=(short) deserializer.startPropertyAtom(this.getName(), order);
        if (numTuples > 0) {
            this.setIsSet();
            m_index=(short) (numTuples-1);
        } else {
            this.clearIsSet();
        }
    }

    /**
     *
     * @param serializer
     */
    public void serialize_nonullprops(ConceptSerializer serializer, int order) {
        try {
            int howMany=howMany(0,0);
            //serializer.startProperty(getName(), order, isSet());
            if (isSet()) {
                serializer.startPropertyAtom(this.getName(), order, isSet(), m_index, howMany);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize_nonullprops(ConceptDeserializer deserializer, int order) {
        short numTuples=(short) deserializer.startPropertyAtom(this.getName(), order);
        if (numTuples > 0) {
            this.setIsSet();
            m_index=(short) (numTuples-1);
        } else {
            this.clearIsSet();
        }
    }

    abstract void writeValueBytes(DataOutput output, int howMany) throws Exception;
    
    public void writeValueBytes(DataOutput output) throws Exception {
        //output.writeInt(getHistorySize());
        //output.writeShort(m_index);
        int howMany=howMany(0,0);
        output.writeInt(howMany);
        for (int j=0; j < howMany; j++) {
            output.writeLong(m_time[mapIndex(j)]);
        }
        /**
        for (int i=0; i < m_time.length; i++) {
            output.writeLong(m_time[i]);
        }
         */
        writeValueBytes(output, howMany);
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    public void readFromDataInput(DataInput input) throws Exception {
        boolean isSet= input.readBoolean();
        if (isSet) {
        	readValueBytes(input);
        } else {
            this.clearIsSet();
        }
    }

    abstract void readValueBytes(DataInput input,int historySize) throws Exception;

    protected void readValueBytes(DataInput input) throws Exception {
        this.setIsSet();
        int howMany=input.readInt() ;
        howMany = (howMany > m_time.length) ? m_time.length: howMany;
        m_index= (short) (howMany-1);
        //int historysize = input.readInt();
        //m_index = input.readShort();
        for (int i=0; i < howMany; i++) {
            m_time[i]= input.readLong();
        }
        readValueBytes(input, howMany);

        /**
        if (historysize != getHistorySize()) {
            m_time = new long[historysize];
            for(int i=0; i<historysize; i++) {
                m_time[i] = input.readLong();
            }
            readValueBytes(input, historysize);
        } else {
            for(int i=0; i<historysize; i++) {
                m_time[i] = input.readLong();
            }
            readValueBytes(input, historysize);
        }
         **/
    }
    
    public void getHistoryTimeInterval(long time, long[] interval) throws PropertyException {
        int i=m_index, gidx=-1, lidx=-1; interval[0]=interval[1]=0; // initialize

        if (time>m_time[i]) { interval[0]=interval[1]=m_time[i]; return; }
        for(; i>=0; i--) { // from current to beginning
            if (time<=m_time[i]) { lidx=gidx=i; }
            else { lidx=i; interval[0]=m_time[lidx]; interval[1]=m_time[gidx]; return; }
        }

        for(i=m_time.length-1; i>m_index; i--) { // from last to current index
            if (time<=m_time[i]) { lidx=gidx=i; }
            else { lidx=i; interval[0]=m_time[lidx]; interval[1]=m_time[gidx]; return; }
        }

        //lower bound
        if (lidx==-1) throw new PropertyException(ResourceManager.getInstance().formatMessage("property.unknown.time.exception",
                                                  getName(), getParent(), Long.toString(time)));
        //upper bound
        if (gidx==-1) throw new PropertyException(ResourceManager.getInstance().formatMessage("property.unknown.time.exception",
                                                  getName(), getParent(), Long.toString(time)));
    }

    public long howCurrent () {
        return m_time[m_index];
    }

    public long howOld() {
        if (m_time[m_time.length-1]==0L) return m_time[0];
        if (m_index+1<m_time.length) return m_time[m_index+1];
        return m_time[0];
    }

    public int howMany(long stime, long etime) throws PropertyException {
        if (!isSet()) return 0; // nothing there
        if (stime==0 && etime==0) {
            if (m_time[m_time.length-1]==0L) return m_index + 1;
            return m_time.length; // buffer has rotated; also upgrading to int
        } else {
            int eidx=getIndex(etime);
            int sidx=getIndexEarliest(stime);
            return (eidx<sidx) ? (m_time.length-sidx+eidx+1) : (eidx-sidx+1);
        }
    }

    protected int getIndexEarliest(long time) throws PropertyException {
        for(int i = m_index; i>=0; i--) {
            if(time <= m_time[i]) {
                if (i==0) {
                    if ((m_time.length-1==m_index) || (m_time[m_time.length-1]==0L) || 
                            (m_time[m_time.length-1]<time)) {
                        return i;
                    }
                } else if (m_time[i-1]<time) {
                    return i;
                }
            }
        }
        if( m_time[m_time.length-1] != 0L) {
            for(int i = m_time.length-1; i > m_index; i--) {
                if(time <= m_time[i]) {
                    if ((m_time[i-1]<time) || (i-1==m_index)) {
                        return i;
                    }
                }
            }
        }
        throw new PropertyException(ResourceManager.getInstance().formatMessage("property.unknown.time.exception",
                                                 getName(), getParent(), Long.toString(time)));
    }

    protected int mapIndex(int idx) {
        if (m_time[m_time.length-1]==0L) return idx;
        return (m_index+idx+1)%m_time.length; // buffer has rotated; also upgrading to int
    }

    protected int getIndex(long time) throws PropertyException {
        for(int i = m_index; i>=0; i--) {
            if(time >= m_time[i]) {
                return i;
            }
        }
        if( m_time[m_time.length-1] != 0L) {
            for(int i = m_time.length-1; i > m_index; i--) {
                if(time >= m_time[i]) {
                    return i;
                }
            }
        }
        throw new PropertyException(ResourceManager.getInstance().formatMessage("property.unknown.time.exception",
                                                 getName(), getParent(), Long.toString(time)));
    }

    public Iterator historyIterator() {
        return new HistoryIterator();
    }

    public Iterator backwardHistoryIterator() {
        return new BackwardHistoryIterator();
    }

    public Iterator forwardHistoryIterator() {
        return new ForwardHistoryIterator();
    }

    class HistoryIterator implements Iterator {
        int m_current;
        boolean m_hasNext;

        HistoryIterator() {
            m_current = m_index;
            m_hasNext = ((m_status & IS_SET) != 0);
        }

        public boolean hasNext() {
            return m_hasNext;
        }

        public Object next() {
            History h = new History();
            h.time  = m_time[m_current];
            h.value = valueToObject(m_current);
            if(!setNextIndex()) {
                m_hasNext = false;
            }
            return h;
        }

        private boolean setNextIndex() {
            m_current--;
            if(m_current < 0) {
                m_current = m_time.length-1;
                if(m_current == m_index) {
                    return false;
                }
                else return m_time[m_current] != 0L;
            }
            else if(m_current == m_index) {
                return false;
            }
            return true;
        }

        public void remove() {
            throw new RuntimeException("HistoryIterator.remove() is not supported");
        }
    }

    class BackwardHistoryIterator implements Iterator {
        int     m_current  = m_index;
        boolean m_hasNext  = ((m_status & IS_SET) != 0);
        long    timeLimit  = 0;
        int     indexLimit = 0;

        BackwardHistoryIterator() {}

        BackwardHistoryIterator(long time, int idx) {
            // validate idx
            if (idx > m_time.length-1)
                throw new RuntimeException(ResourceManager.getInstance().formatMessage("property.index.range.exception",
                                           Integer.toString(idx), getName(), getParent()));
            if (0!=time) { timeLimit = time; }

            if (0<idx) indexLimit=idx;
        }

        public boolean hasNext() { return m_hasNext; }

        public Object next() {
            History h = new History();
            h.time  = m_time[m_current];
            h.value = valueToObject(m_current);
            if(!setNextIndex()) { m_hasNext = false; }
            return h;
        }

        private boolean setNextIndex() {
            m_current--;
            if(m_current < 0) { m_current = m_time.length-1; }
            if(m_current == m_index || m_time[m_current] == 0L) { return false; }
            if(indexLimit!=0) { // for index limit
                if(m_time[m_time.length-1]==0L && m_current==indexLimit-1) { return false; }
                if(m_time[m_time.length-1]!=0L && (m_index+indexLimit)%m_time.length==m_current) { return false; }
            }
            if(timeLimit!=0 && m_time[m_current]<timeLimit)     { return false; } // for time limit
            return true;
        }

        public void remove() { throw new RuntimeException("BackwardHistoryIterator.remove() is not supported"); }
    }

    class ForwardHistoryIterator implements Iterator {
        int m_current = (m_index+1 < m_time.length && m_time[m_index+1] != 0L) ? m_index+1 : 0;

        boolean m_hasNext=((m_status & IS_SET) != 0);

        ForwardHistoryIterator() { }

        ForwardHistoryIterator(long time, int idx) {
            // validate idx
            if (idx > m_time.length-1)
                throw new RuntimeException(ResourceManager.getInstance().formatMessage("property.index.range.exception",
                                           Integer.toString(idx), getName(), getParent()));
            if (0!=time) {
                try {
                    m_current=getIndex(time);
                } catch (Exception e) {
                    //todo - why eat the exception?
                    System.out.println(ResourceManager.getInstance().formatMessage("property.time.range.exception",
                                               Long.toString(time), getName(), getParent(),
                                               Long.toString(howOld()), Long.toString(howCurrent())));
                }
            }
            if (0<idx) forwardIdx(idx);
        }

        public void forwardIdx(int idx) {
            m_current = (m_current+idx < m_time.length) ? (m_current+idx) : (m_current+idx-m_time.length);

            // defensive; should never happen though
            if (m_time[m_current] == 0L)
                throw new RuntimeException(ResourceManager.getInstance().formatMessage("property.index.range.exception",
                                           Integer.toString(idx), getName(), getParent()));
            if (m_current == m_index+1)
                m_hasNext = false;
        }

        public boolean hasNext() { return m_hasNext; }

        public Object next() {
            History h = new History();
            h.time  = m_time[m_current];
            h.value = valueToObject(m_current);
            if(!setNextIndex()) { m_hasNext = false; }
            return h;
        }

        private boolean setNextIndex() {
            if (m_current == m_index)        {  return false; }

            m_current++;
            if (m_current > m_time.length-1) { m_current = 0; }

            return true;
        }

        public void remove() { throw new RuntimeException("ForwardHistoryIterator.remove() is not supported"); }
    }
}
