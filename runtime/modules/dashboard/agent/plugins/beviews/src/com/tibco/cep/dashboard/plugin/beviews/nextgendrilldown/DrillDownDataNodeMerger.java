package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePath;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePathElement;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataNode;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.kernel.service.logging.Logger;

public class DrillDownDataNodeMerger extends DrillDownHelper {

	private static boolean INCLUDE_DATA_ROWS_ONLY = true;

	public DrillDownDataNodeMerger(Logger logger, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, properties, exceptionHandler, messageGenerator);
	}

	public boolean mergeData(DrillDownRequest request, DrillDownDataTree drillDownDataTree, Map<String, List<DrillDownDataNode>> dataNodesMap) {
		int totalCount = drillDownDataTree.getTotal();
		for (Entry<String, List<DrillDownDataNode>> dataNodesEntry : dataNodesMap.entrySet()) {
			List<DrillDownDataNode> dataNodes = dataNodesEntry.getValue();
			int count = count(dataNodes, INCLUDE_DATA_ROWS_ONLY);
//			if (count == 0) {
//				//do nothing if we have no data
//				return true;
//			}
			if (request.getPath() == null) {
				if (count > 0) {
					drillDownDataTree.addRoot(dataNodes.get(0));
				}
			} else {
				DrillDownDataNode leafNode = findLeafNode(drillDownDataTree, request.getPath());
				// are we dealing with adding/removing of group by
				boolean requestHasGroupBy = StringUtil.isEmptyOrBlank(request.getGroupByField()) == false;
				boolean requestHasSortOrder = request.getOrderByList().isEmpty() == false;
				boolean leafHasGroupByData = leafNode.getChildren().isEmpty() == false && leafNode.getChildren().get(0).isDynamicData() == true;
				boolean requestHasStartIndex = request.getStartIndex() > 0;
				if (requestHasStartIndex == false && (requestHasSortOrder == true || requestHasGroupBy == true || (requestHasGroupBy == false && leafHasGroupByData == true))) {
					// we are dealing with a order by, which mean remove all children nodes from leaf node
					// OR
					// we are dealing with a group by, which mean remove all children nodes from leaf node
					// OR
					// we are dealing with a removal of group by, which means remove all children nodes from leaf node
					leafNode.removeAllChildren();
				}
				// add all data nodes as children
				for (DrillDownDataNode dataNode : dataNodes) {
					leafNode.addChild(dataNode);
				}
			}
			//update the total count
			totalCount = totalCount + count;
		}
		// update count
		drillDownDataTree.setTotal(totalCount);
		return true;
	}

	private DrillDownDataNode findLeafNode(DrillDownDataTree drillDownDataTree , DrillDownTreePath path) {
		if (path == null) {
			throw new IllegalArgumentException("path cannot be null");
		}
		for (DrillDownDataNode root : drillDownDataTree.getRoots()) {
			DrillDownDataNode node = traversePath(root, path, path.getRoot());
			if (node != null) {
				return node;
			}
		};
		return null;
	}

	private DrillDownDataNode traversePath(DrillDownDataNode drillDownDataNode, DrillDownTreePath path, DrillDownTreePathElement element) {
		if (element == null) {
			return drillDownDataNode;
		}
		if (drillDownDataNode.getIdentifier().equals(element.getToken()) == true) {
			DrillDownTreePathElement childpath = path.getChild(element);
			if (childpath != null) {
				List<DrillDownDataNode> children = drillDownDataNode.getChildren();
				if (children.isEmpty() == false) {
					for (DrillDownDataNode child : children) {
						DrillDownDataNode hit = traversePath(child, path, childpath);
						if (hit != null) {
							return hit;
						}
					}
				}
				return null;
			}
			return drillDownDataNode;
		}
		return null;
	}

	public boolean removeData(DrillDownRequest request, DrillDownDataTree drillDownDataTree) {
		if (drillDownDataTree != null) {
			if (request.getPath() == null) {
				drillDownDataTree.clear();
			}
			else {
				DrillDownDataNode leafNode = findLeafNode(drillDownDataTree, request.getPath());
				if (leafNode != null) {
					int count = count(leafNode.getChildren(), INCLUDE_DATA_ROWS_ONLY);
					leafNode.removeAllChildren();
					drillDownDataTree.setTotal(drillDownDataTree.getTotal() - count);
				}
			}
		}
		return true;
	}

	private int count(List<DrillDownDataNode> dataNodes, boolean includeDataRowsOnly) {
		int count = 0;
		for (DrillDownDataNode node : dataNodes) {
			if (includeDataRowsOnly == false || node.hasValues() == true || node.isDynamicData() == true) {
				count++;
			}
			count = count + count(node.getChildren(), includeDataRowsOnly);
		}
		return count;
	}
}