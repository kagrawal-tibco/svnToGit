package com.tibco.rta.service.metric.directive;

import com.tibco.rta.Fact;
import com.tibco.rta.model.DimensionHierarchy;

/**
 * An extension mechanism to make schema specific decisions during runtime.
 * 
 * This interface tells the framework how to handle various metric related conditions
 * There is one directive implementation per schema. These implementations are registered at startup time
 * based on certain Annotations.
 * 
 * At runtime, the framework loads an implementation by providing a schema name.
 * 
 * @author bala
 *
 */


public interface MetricProcessingDirective {
	
	/**
	 * An asset state change can trigger a state change in all computations where this asset is referenced.
	 * This directive informs the framework whether or not to visit all parent nodes where it matches this
	 * asset fact for a given hierarchy.
	 * 
	 * @param dh The dimension for which a directive is called for along with the fact
	 * @param fact the associated asset fact
	 * @return true if parent nodes are to be visited.
	 */
	boolean visitParentNodes(DimensionHierarchy dh, Fact fact);

	/**
	 * An asset delete notification change can trigger deletes in other hierarchies where this asset is referenced.
	 * This directive informs the framework whether or not to delete all related computations where this asset is referenced.
	 * 
	 * @param dh The dimension for which a directive is called for along with the fact
	 * @param fact the associated asset fact (delete)
	 * @return true if child nodes from other hierarchies are to be deleted.
	 */
	boolean deleteChildNodes(DimensionHierarchy dh, Fact fact);
	
	/**
	 * An asset state change can trigger a state change in certain computations.
	 * This directive informs the framework whether or not to process this asset as a normal fact to be used
	 * for metrics computations.
	 * 
	 * @param dh The dimension for which a directive is called for along with the fact
	 * @param fact the associated asset fact
	 * @return true if the asset fact is to be treated as normal for this hierarchy.
	 */
	boolean processNode(DimensionHierarchy dh, Fact fact);

	/**
	 * Sometimes, null as dimension values are valid. This directive informs runtime not to perform null value
	 * validation checks.
	 * 
	 * @param dh The dimension for which a directive is called for along with the fact
	 * @param fact the associated asset fact
	 * @return true if null values are normal for this dimension hierarchy.
	 */
	boolean allowNullDimensionValues(DimensionHierarchy dh, Fact fact);
	
	/**
	 * Asset status changes are propagated to other hierarchies. This directive tells the framework whether or not
	 * to apply those changes only to the current time window computations.
	 * 
	 * @param dh The dimension for which a directive is called for along with the fact
	 * @return true if the asset status changes are to be propagated only to current time window metric computations.
	 */
	boolean shouldApplyAssetStatusToCurrentTimeWindowOnly(DimensionHierarchy dh);
	
	/**
	 * Returns true if the fact is to be treated as a delete message. Every schema might represent deletes in different ways.
	 * This is called by the framework while processing an asset hierarchy. If it returns true the framework deletes the asset from the system
	 * and does not perform further computations on this node, since it would be deleted.
	 * 
	 * @param fact
	 * @return
	 */

	boolean isDeleteFact(Fact fact);
	
}
