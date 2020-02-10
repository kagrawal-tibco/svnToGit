package com.tibco.cep.runtime.model.process;


import java.util.Set;

import com.tibco.cep.runtime.model.serializers.SerializableLite;

/*
* Author: Suresh Subramani / Date: 2/9/12 / Time: 6:04 PM
*/
public interface MergeTuple extends ObjectBean,SerializableLite {

    String getMergeKey();

    short getExpectedTokenCount();

    short getTokenCount();

    void merge(long processId, String transitionName, boolean isError);

    MergeEntry[] getMergeEntries();

    boolean isComplete();

    //-------------

    interface MergeEntry {
        long getProcessId();

        String getTransitionName();

        long getTimeMillis();
        
        boolean hasError();
        
        boolean isMerged();

		public abstract void setMerged();

		public abstract void setMerged(boolean isMerged);

		public abstract void setTimeMillis(long timeMillis);

		public abstract void setTransitionName(String transitionName);

		public abstract void setProcessId(long processId);
    }

	void setComplete();



	public abstract void setExpectedTokenCount(short expectedTokenCount);

	public abstract void setMergeKey(String mergeKey);

	public abstract void setState(BeanOp state);

	public abstract BeanOp getState();

	public abstract void setMerges(Set<MergeEntry> merges);

	public abstract Set<MergeEntry> getMerges();

}
