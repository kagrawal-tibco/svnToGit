package com.tibco.store.persistence.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

//TODO documentation
//TODO return a generic statistic from the present *count methods.
public interface MemoryIndex<T> {
	
	public Collection<MemoryKey> get(T key);
	
	public Collection<MemoryKey> getValues();

	public void putValueToKey(T key, MemoryKey value);

	public Iterator<MemoryKey> removeFromStore(T key);

	public void removeValueForKey(T key, MemoryKey value);

	public Collection<HashSet<MemoryKey>> greaterThan(T fromKey);

	public Collection<HashSet<MemoryKey>> greaterThanEqualTo(T fromKey);

	public Collection<HashSet<MemoryKey>> lessThan(T toKey);

	public Collection<HashSet<MemoryKey>> lessThanEqualTo(T toKey);

    public Collection<HashSet<MemoryKey>> greaterThanAndLessThan(T fromKey, T toKey);

   	public Collection<HashSet<MemoryKey>> greaterThanEqualAndLThan(T fromKey, T toKey);

   	public Collection<HashSet<MemoryKey>> greaterThanEqualAndLThanEqual(T fromKey, T toKey);

    public int equalsCount(T fromKey);

    public int greaterThanCount(T fromKey);

   	public int greaterThanEqualToCount(T fromKey);

   	public int lessThanCount(T toKey);

   	public int lessThanEqualToCount(T toKey);

	public void removeValueReference(MemoryKey value);
	
	public int getTotalKeys();

	public Collection<MemoryKey> getNot(T key);

}
