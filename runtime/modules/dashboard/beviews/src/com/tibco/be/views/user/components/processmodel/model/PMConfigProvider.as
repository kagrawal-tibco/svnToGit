package com.tibco.be.views.user.components.processmodel.model{
	
	import com.tibco.be.views.core.utils.HashedTree;
	import com.tibco.be.views.core.utils.HashedTreeNode;
	import com.tibco.be.views.core.utils.IHashedTreeNode;
	import com.tibco.be.views.core.utils.ITransitionsProvider;
	import com.tibco.be.views.user.components.processmodel.view.PMNode;
	
	import flash.utils.Dictionary;

	public class PMConfigProvider implements ITransitionsProvider{
		
		/** Background color for the BEVProcessModelComponent */
		private var _bgColor:uint;
		
		/** A dictionary keyed by PMNode ID that maps to an array of PMEdgeConfigs */
		private var _transitions:Dictionary;
		
		/** The hashed tree structure that stores the configs of the PMNodes */
		private var _nodeTree:HashedTree;
		
		public function PMConfigProvider():void{
			super();
			_transitions = new Dictionary();
			_nodeTree = new HashedTree();
		}
		
		public function get backgroundColor():uint{ return _bgColor; }
		
		public function get roots():Dictionary{ return _nodeTree.roots; }
		
		/**
		 * @return Array of the root PMNodeConfig objects in the tree
		*/
		public function get rootNodeConfigs():Array{
			var nodes:Array = new Array();
			for each(var treeNode:IHashedTreeNode in _nodeTree.roots){
				nodes.push(treeNode.payload);
			}
			return nodes;
		}
		
		/**
		 * @param The ID of the node containing the desired PMNodeConfig
		 * @return The PMNodeConfig for the node with the provided ID
		*/
		public function getConfigFor(nodeId:String):PMNodeConfig{
			var node:IHashedTreeNode = _nodeTree.getNodeById(nodeId);
			return node.payload as PMNodeConfig;
		}
		
		/**
		 * @return A dictionary of IHashedTreeNode objects that contains the children of the node
		 * with the provided ID.
		*/
		public function getChildrenOf(nodeId:String):Dictionary{
			var node:IHashedTreeNode = _nodeTree.getNodeById(nodeId);
			return node.children;
		}
		public function getParentOf(nodeId:String):IHashedTreeNode{
			var node:IHashedTreeNode = _nodeTree.getNodeById(nodeId);
			return node.parent;
		}
		public function getTransitionsFor(nodeId:String):Array{ return _transitions[nodeId] as Array; }
		
		public function setConfig(configXML:XML):void{
			var processConfig:XML = configXML.processconfig[0] as XML;
			if(processConfig == null){ return; }
			_bgColor = parseInt(processConfig.@backgroundcolor, 16);
			_nodeTree = new HashedTree();
			var activityConfigs:XMLList = processConfig.activityconfig;
			for each(var activityConfig:XML in activityConfigs){
				_nodeTree.insert(addNodesAndEdges(activityConfig));
			}
		}
		
		public function updateConfig(processConfig:XML):void{
			_transitions = new Dictionary();
			addAndUpdateConfigs(processConfig);
		}
		
		/**
		 * Adds new (or updates existing) PMNodeConfig and PMEdgeConfig objects to/(in) the process
		 * model's data model.
		 * 
		 * @param processConfig XML containing activityconfig nodes to parse into PMNodeConfig objects.
		 * @param parentNodeId String identifying the parent activityconfig of the given processConfig.
		 * 		This parameter is only used in recursive steps.
		*/
		protected function addAndUpdateConfigs(processConfig:XML, parentNodeId:String=null):void{
			if(processConfig == null){ return; }
			var parentTreeNode:IHashedTreeNode = _nodeTree.getNodeById(parentNodeId);
			for each(var activityConfig:XML in processConfig.activityconfig){
				var node:IHashedTreeNode = _nodeTree.getNodeById(activityConfig.@activityid);
				//add...
				if(node == null){
					addNodesAndEdges(activityConfig, parentTreeNode);
					continue;
				}
				//update...
				var pmNodeCfg:PMNodeConfig = node.payload as PMNodeConfig;
				pmNodeCfg.update(activityConfig);
				_transitions[pmNodeCfg.id] = buildNodePaths(activityConfig.path, parentTreeNode);
				if(activityConfig.processconfig != undefined){
					addAndUpdateConfigs(activityConfig.processconfig[0], pmNodeCfg.id);
				}
			}
		}
		
		/**
		 * Recursively constructs a PMNodeConfig and all of its descendants. PMEdgeConfigs are also
		 * created during the process.
		 * 
		 * @param nodeXML The XML containing the properties to use when creating the PMNodeConfig.
		 * @param nodeParent The tree node to use as the parent for the newly created tree node.
		*/ 
		private function addNodesAndEdges(nodeXML:XML, parentTreeNode:IHashedTreeNode=null):IHashedTreeNode{
			var pmNodeCfg:PMNodeConfig = new PMNodeConfig(nodeXML);
			var treeNode:IHashedTreeNode = new HashedTreeNode(pmNodeCfg.id, parentTreeNode);
			treeNode.payload = pmNodeCfg;
			_nodeTree.insert(treeNode);
			_transitions[pmNodeCfg.id] = buildNodePaths(nodeXML.path, parentTreeNode);
			if(nodeXML.processconfig != undefined && nodeXML.processconfig.activityconfig != undefined){
				for each(var child:XML in nodeXML.processconfig.activityconfig){
					addNodesAndEdges(child, treeNode);
				}
			}
			return treeNode;
		}
		
		private function buildNodePaths(pathConfigs:XMLList, parentNode:IHashedTreeNode):Array{
			var nodesTransitions:Array = new Array();
			if(pathConfigs == null || pathConfigs.length() == 0){ return nodesTransitions; }
			for each(var path:XML in pathConfigs){
				var pathNodes:XMLList = path.pathnode;
				for(var i:int = 0; i < pathNodes.length()-1; i++){
					var fromNode:XML = pathNodes[i] as XML;
					var toNode:XML = pathNodes[i+1] as XML;
					if(fromNode == null || toNode == null){ continue; }
					var edgeConfig:PMEdgeConfig = new PMEdgeConfig(
						 fromNode.@x,
						 fromNode.@y,
						 toNode.@x,
						 toNode.@y,
						 [new String(fromNode.@label), new String(toNode.@label)],
						 new String(fromNode.@icon),
						 new String(toNode.@icon)
					);
					nodesTransitions.push(edgeConfig);
				}
			}
			return nodesTransitions;
		}
		
		public function removeChildrenOf(node:PMNode):Array{
			var treeNode:IHashedTreeNode = _nodeTree.getNodeById(node.id);
			var removedIds:Array = new Array();
			for each(var child:IHashedTreeNode in treeNode.children){
				removedIds = removedIds.concat(_nodeTree.remove(child));
			}
			return removedIds;
		}
		
		public function reset():void{
			_transitions = new Dictionary();
			_nodeTree = new HashedTree(); 
		}
		
	}
}