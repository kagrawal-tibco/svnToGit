package com.tibco.rta.runtime.model;

import java.io.Serializable;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.query.Browser;

/**
 * This is the root interface in the runtime hierarchy of computed metrics. An {@code RtaNode} 
 * An instance of this type is a {@link MetricNode}
 * They provide a wrapper to {@link com.tibco.rta.Metric} or {@link com.tibco.rta.Fact} instances.
 * These nodes are related to each other in a tree form. Nodes higher up in the tree represent
 * aggregations at higher level dimensions as defined in the {@link DimensionHierarchy}
 * 
 * 
 */

public interface RtaNode extends Serializable {
	
	/**
	 * 
	 * @return The unique identity of this node
	 */
	Key getKey();
	

	/**
	 * 
	 * @return This node's parent node
	 */
	
	RtaNode getParent();
	
	/**
	 * 
	 * @return This node's parent key
	 */
	
	Key getParentKey();
	
	
	/**
	 * An iterator that iterates over this nodes fact nodes.
	 * @return The iterator.
	 */
	Browser<Fact> getFactBrowser();
	
	/**
	 * An iterator that iterates over this nodes immediate children.
	 * @return The iterator.
	 */
	Browser<? extends RtaNode> getChildNodeBrowser();
	
	
	boolean isNew();
	
	RtaNode deepCopy();

	/**
	 * Timestamp in milliseconds when this metric was created.
	 * @return creation time in milliseconds
	 */
	long getCreatedTime();
	
	/**
	 * Timestamp in milliseconds when this metric was updated.
	 * @return modification time in milliseconds.
	 */
	long getLastModifiedTime();


	
}
