package com.tibco.cep.bpmn.runtime.activity.gateways;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.tibco.cep.bpmn.runtime.utils.Constants;
import com.tibco.cep.runtime.model.process.MergeTuple;

/*
* Author: Suresh Subramani / Date: 2/9/12 / Time: 6:57 PM
*/
public class DefaultMergeTuple implements MergeTuple {
    String mergeKey;

    short expectedTokenCount;

    LinkedHashSet<MergeEntry> merges;
    
    BeanOp state;

    public DefaultMergeTuple() {
        this.merges = new LinkedHashSet<MergeEntry>(4);
        state = BeanOp.BEAN_CREATED;
    }

    public DefaultMergeTuple(String mergeKey, short expectedTokenCount) {
        this();

        this.mergeKey = mergeKey;
        this.expectedTokenCount = expectedTokenCount;
        this.merges = new LinkedHashSet<MergeEntry>(expectedTokenCount);
    }
    
    @Override
    public String getKey() {
    	return mergeKey;
    }
    
    

    @Override
	public Class getType() {
		return MergeTuple.class;
	}

	@Override
	public BeanOp getBeanOp() {
		return state;
	}

	@Override
    public String getMergeKey() {
        return mergeKey;
    }

    @Override
    public short getExpectedTokenCount() {
        return expectedTokenCount;
    }

    @Override
    public short getTokenCount() {
        return (short) merges.size();
    }
    
    

    @Override
	public Set<MergeEntry> getMerges() {
		return merges;
	}

	@Override
	public void setMerges(Set<MergeEntry> merges) {
		this.merges.addAll(merges);
	}

	@Override
	public BeanOp getState() {
		return state;
	}

	@Override
	public void setState(BeanOp state) {
		this.state = state;
	}

	@Override
	public void setMergeKey(String mergeKey) {
		this.mergeKey = mergeKey;
	}

	@Override
	public void setExpectedTokenCount(short expectedTokenCount) {
		this.expectedTokenCount = expectedTokenCount;
	}

	@Override
    public void merge(long processId, String transitionName,boolean hasError) {
        MergeEntry entry = new DefaultMergeEntry(processId, transitionName,hasError);

        if (Constants.DEBUG) {
            if (isComplete()) {
                throw new IllegalStateException("Merge is already complete");
            }
            else if (merges.contains(entry)) {
                throw new IllegalStateException(
                        "Transition [" + transitionName + "] with process [" + processId + "] has already merged");
            }
        }
        state = BeanOp.BEAN_UPDATED;
        for(MergeEntry e:merges){
        	((MergeEntry)e).setMerged();
        }
        merges.add(entry);
    }

    @Override
    public boolean isComplete() {
        return expectedTokenCount == merges.size();
    }
    
    

    @Override
	public void setComplete() {
		state = BeanOp.BEAN_DELETED;
		
	}

	@Override
    public MergeEntry[] getMergeEntries() {
        return merges.toArray(new MergeEntry[merges.size()]);
    }
	

	@Override
    public void writeExternal(DataOutput out) throws IOException {
        out.writeUTF(mergeKey);
        out.writeUTF(state.toString());
        out.writeShort(expectedTokenCount);
        out.writeShort(merges.size());
        for (MergeEntry merge : merges) {
            out.writeLong(merge.getProcessId());
            out.writeUTF(merge.getTransitionName());
            out.writeLong(merge.getTimeMillis());
            out.writeBoolean(merge.hasError());
        }
    }

	@Override
    public void readExternal(DataInput in) throws IOException {
        mergeKey = in.readUTF();
        state = BeanOp.valueOf(in.readUTF());
        expectedTokenCount = in.readShort();
        short mergeCount = in.readShort();
        for (short i = 0; i < mergeCount; i++) {
            long processId = in.readLong();
            String transitionName = in.readUTF();
            long timeMillis = in.readLong();
            boolean hasError = in.readBoolean();
            MergeEntry entry = new DefaultMergeEntry(processId, transitionName,hasError, timeMillis);
            merges.add(entry);
        }
    }

//    @Override
//    public boolean supportsTypeId() {
//        return false;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public int getTypeId() {
//        return 0;  //To change body of implemented methods use File | Settings | File Templates.
//    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultMergeTuple)) {
            return false;
        }

        DefaultMergeTuple tuple = (DefaultMergeTuple) o;

        if (expectedTokenCount != tuple.expectedTokenCount) {
            return false;
        }
        if (!mergeKey.equals(tuple.mergeKey)) {
            return false;
        }
        if (!merges.equals(tuple.merges)) {
            return false;
        }

        return true;
    }

	@Override
    public int hashCode() {
        int result = mergeKey.hashCode();
        result = 31 * result + (int) expectedTokenCount;
        result = 31 * result + merges.hashCode();
        return result;
    }

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{mergeKey='").append(mergeKey).append('\'');
        sb.append(", expectedTokenCount=").append(expectedTokenCount);
        sb.append(", merges=").append(merges);
        sb.append('}');
        return sb.toString();
    }

    //----------

//	public static void main(String[] args) throws IOException, ClassNotFoundException {
//        MergeTuple tuple = new DefaultMergeTuple("merge-5678", (short) 3);
//        tuple.merge(123, "abc");
//        tuple.merge(456, "def");
//        try {
//            tuple.merge(456, "def");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        tuple.merge(789, "ghi");
//
//        System.out.println(tuple);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        DataOutputStream dos = new DataOutputStream(baos);
//        tuple.writeExternal(dos);
//        dos.close();
//
//        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
//        MergeTuple tuple2 = new DefaultMergeTuple();
//        tuple2.readExternal(dis);
//
//        System.out.println(tuple2);
//
//        System.out.println(tuple.equals(tuple2));
//    }
}
