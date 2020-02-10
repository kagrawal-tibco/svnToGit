package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	import com.tibco.be.views.ui.buttons.ExpandCollapseButton;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.RowExpansionEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowConfig;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowNode;
	
	import flash.display.CapsStyle;
	import flash.display.DisplayObject;
	import flash.display.LineScaleMode;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.core.Container;
	import mx.core.ScrollPolicy;
	import mx.events.FlexEvent;
	import mx.utils.StringUtil;

	/**
	 * A generic row in the BEVTableTreeComponent. This parent class is primarily responsible for 
	 * storing children, drawing tree expansion lines, rendering and handling tree expansion clicks,
	 * and defining general row layout for DrillDownRow subtypes.
	*/
	public class TableTreeRow extends Canvas{
		
		public static const DEFAULT_ROW_HEIGHT:int = 24;
		public static const SINGLE_INDENT_WIDTH:int = 25;
		
		protected var _position:int;
		protected var _parentRow:TableTreeRow;
		protected var _childRows:Array;
		protected var _rowDataNode:TableTreeRowNode;
		protected var _rowConfig:TableTreeRowConfig;
		protected var _expandButton:ExpandCollapseButton;
		protected var _indentation:int;
		protected var _content:Container;
		
		protected var _shouldDrawHierarchalConnection:Boolean;
		
		public function TableTreeRow(rowConfig:TableTreeRowConfig, rowDataNode:TableTreeRowNode, parentRow:TableTreeRow, position:int, indentation:int=0, expandable:Boolean=false){
			super();
			_rowConfig = rowConfig;
			_rowDataNode = rowDataNode;
			_parentRow = parentRow;
			_position = position;
			_childRows = [];
			_indentation = indentation;
			_shouldDrawHierarchalConnection = false;
			if(expandable){
				buildExpandButton();
			}
			percentWidth = 100;
			height = DEFAULT_ROW_HEIGHT;
			horizontalScrollPolicy = ScrollPolicy.OFF;
			styleName = "tableTreeRow";
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		public function get rowConfig():TableTreeRowConfig{ return _rowConfig; }
		public function get rowDataNode():TableTreeRowNode{ return _rowDataNode; }
		public function get parentRow():TableTreeRow{ return _parentRow; }
		public function get isExpandable():Boolean{ return _expandButton != null; }
		public function get isExpanded():Boolean{ return _expandButton != null && _expandButton.expanded; }
		public function get path():String{ return _rowDataNode.path; }
		public function get indentation():int{ return _indentation; }
		public function get childRows():Array{ return _childRows; }
		public function get shouldDrawHierarchalConnection():Boolean{ return _shouldDrawHierarchalConnection; }
		public function get hasSiblings():Boolean{ return _parentRow != null && _parentRow.childRows.length > 1; }
		public function get hasNextSiblings():Boolean{ return hasSiblings && _parentRow.childRows.indexOf(this) < _parentRow.childRows.length -1; }
		public function get nextSibling():TableTreeRow{ return hasNextSiblings ? _parentRow.childRows[_parentRow.childRows.indexOf(this)+1] as TableTreeRow:null; }
		public function get contentWidth():Number{ return _content == null ? 0:_content.width; }
		
		public function set isExpanded(value:Boolean):void{ if(_expandButton != null){ _expandButton.expanded = value; } }
		public function set indentation(value:int):void{ _indentation = value; }
		public function set shouldDrawHierarchalConnection(value:Boolean):void{ _shouldDrawHierarchalConnection = value; }
		
		override protected function createChildren():void{
			super.createChildren();
			if(isExpandable){
				_expandButton.x = (_indentation*SINGLE_INDENT_WIDTH) - 3*ExpandCollapseButton.SIZE/2;
				_expandButton.y = DEFAULT_ROW_HEIGHT/2 - Math.floor(ExpandCollapseButton.SIZE/2);
				addChild(_expandButton);
			}
			createContentContainer();
			configureContentContainer();
			addChild(_content);
		}
		
		protected function buildExpandButton():void{
			_expandButton = new ExpandCollapseButton();
		}
		
		//subtypes may override to customize the appearance of the content container
		protected function createContentContainer():void{
			_content = new Container();
		}
		
		//subtypes may override to customize the configuration of the content container
		protected function configureContentContainer():void{
			_content.x = _indentation*SINGLE_INDENT_WIDTH;
			_content.percentWidth = 100;
			_content.percentHeight = 100;
			setContentContainerStyle();
		}
		
		//subtypes may override to customize the style of the content container
		protected function setContentContainerStyle():void{
			_content.styleName = "tableTreeRowContent";
		}
		
		protected function addContent(child:DisplayObject):void{
			_content.addChild(child);
		}
		
		protected function addContentAt(child:DisplayObject, index:int):void{
			_content.addChildAt(child, index);
		}
		
		protected function removeContent(child:DisplayObject):void{
			if(_content.contains(child)){
				_content.removeChild(child);
			}
		}
		
		protected function getContentIndex(child:DisplayObject):int{
			return _content.getChildIndex(child);
		}
		
		protected function getContentItemAt(index:int):DisplayObject{
			if(_content == null || _content.numChildren < index || index < 0){ return null; }
			return _content.getChildAt(index);
		}
		
		public function addChildRow(childRow:TableTreeRow, position:int=-1):void{
			if(childRow != null){
				if(position > -1 && position < _childRows.length){
					_childRows.splice(position, 0, childRow);
				}
				else{
					_childRows.push(childRow);
				}
			}
		}
		
		public function removeChildRow(childRow:TableTreeRow):void{
			for(var i:int = 0; i < _childRows.length; i++){
				if(childRow == _childRows[i]){ _childRows.splice(i, 1); }
			}
		}
		
		public function removeAllChildRows():void{
			if(_childRows != null && childRows.length > 0){
				_childRows.splice(0, _childRows.length);
			}
		}
		
		public function isLastChild(potentialLastChild:TableTreeRow):Boolean{
			return _childRows != null && _childRows.length > 0 && _childRows[_childRows.length-1] == potentialLastChild;
		}
		
		protected function handleCreationComplete(event:FlexEvent):void{
			if(_expandButton){
				_expandButton.addEventListener(ExpandCollapseButton.EXPANSION_TOGGLE_EVENT, handleExpansionButtonChange);
			}
			_content.addEventListener(MouseEvent.ROLL_OVER, handleMouseRollOver);
			_content.addEventListener(MouseEvent.ROLL_OUT, handleMouseRollOut);
			removeEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		/** Invoked upon clicking of the row's expand/collapse button */
		private function handleExpansionButtonChange(event:MouseEvent):void{
			dispatchEvent(new RowExpansionEvent(this));
		}
		
		/** Inovked to handle the result of the expand/collapse process */
		public function handleRowExpansion(rowNodes:Array):void{
			if(rowNodes.length == 0){
				shouldDrawHierarchalConnection = true;
				removeExpansionButton();
			}
			invalidateDisplayList(); //will recalculate column widths of new table rows
		}
		
		public function handleRowCollapse():void{
			
		}
		
		protected function handleMouseRollOver(mouseEvent:MouseEvent):void{
			
		}
		
		protected function handleMouseRollOut(mouseEvent:MouseEvent):void{
			
		}
		
		public function removeExpansionButton():void{
			if(_expandButton != null && contains(_expandButton)){
				removeChild(_expandButton);
				_expandButton = null;
			}
		}
		
		public function setBorderSides(sides:String):void{
			_content.setStyle("borderSides", sides);
		}
		
		public function addBorderSides(sidesToAdd:String):void{
			var sides:Array = sidesToAdd.split(" ");
			var currentSides:Array = String(_content.getStyle("borderSides")).split(" ");
			currentSides = currentSides.concat(sides);
			currentSides = currentSides.filter(
				function(element:*, index:int, arr:Array):Boolean{
					return currentSides.indexOf(element) == index;
				}
			);
			var sidesToSet:String = StringUtil.trim(currentSides.join(" "));
			_content.setStyle("borderSides", currentSides.join(" "));
		}
		
		public function removeBorderSides(sidesToRemove:String):void{
			var sides:Array = sidesToRemove.split(" ");
			var currentSides:Array = String(_content.getStyle("borderSides")).split(" ");
			currentSides = currentSides.filter(
				function(element:*, index:int, arr:Array):Boolean{
					return sides.indexOf(element) < 0;
				}
			);
			var sidesToSet:String = StringUtil.trim(currentSides.join(" "));
			_content.setStyle("borderSides", currentSides.join(" "));
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			drawHirearchalDottedLines();
		}
		
		protected function drawHirearchalDottedLines():void{
			if(_indentation == 0){ return; }
			var i:int;
			var dottedLineX:int = (_indentation*SINGLE_INDENT_WIDTH) - ExpandCollapseButton.SIZE -1;
			graphics.clear();
			graphics.lineStyle(0, 0x0, 1, true, LineScaleMode.NONE, CapsStyle.NONE);
			graphics.moveTo(dottedLineX, 0);
			if(_expandButton != null){
				//dotted above button
				for(i = 1; i < _expandButton.y; i+=3){
					graphics.lineTo(dottedLineX, i);
					graphics.moveTo(dottedLineX, i+2);
				}
				
				//dotted connection from button to content
				graphics.moveTo(_expandButton.x+_expandButton.width, height/2);
				for(i = _expandButton.x+_expandButton.width; i < _indentation*SINGLE_INDENT_WIDTH; i+=3){
					graphics.lineTo(i+1, height/2);
					graphics.moveTo(i+3, height/2);
				}
				
				//dotted below button if not last
				if(_parentRow != null && !_parentRow.isLastChild(this)){
					graphics.moveTo(dottedLineX, _expandButton.y + _expandButton.height);
					for(i = _expandButton.y + _expandButton.height - 1; i < _content.height; i+=3){
						graphics.lineTo(dottedLineX, i);
						graphics.moveTo(dottedLineX, i+2);
					}
				}
			}
			else if(_parentRow != null){
				//top half of dotted line
				for(i = 1; i < height/2; i+=3){
					graphics.lineTo(dottedLineX, i);
					graphics.moveTo(dottedLineX, i+2);
				}
				var isLast:Boolean = _parentRow.isLastChild(this);
				//bottom half of dotted below if not last
				if(!isLast){
					for(; i < height; i+=3){
						graphics.lineTo(dottedLineX, i);
						graphics.moveTo(dottedLineX, i+2);
					}
				}
				//dotted connection to content if last or expand button removed
				if(isLast || _shouldDrawHierarchalConnection){
					graphics.moveTo(dottedLineX, height/2);
					for(i = dottedLineX; i < _indentation*SINGLE_INDENT_WIDTH; i+=3){
						graphics.lineTo(i, height/2);
						graphics.moveTo(i+2, height/2);
					}
				}
			}
			
			//Draw lines indicating hierarchy at ancestral levels
			var ancestor:TableTreeRow = _parentRow;
			while(ancestor != null){
				dottedLineX -= SINGLE_INDENT_WIDTH;
				if(ancestor.hasNextSiblings){
					graphics.moveTo(dottedLineX, 0);
					for(i = 1; i < height; i+=3){
						graphics.lineTo(dottedLineX, i);
						graphics.moveTo(dottedLineX, i+2);
					}
				}
				ancestor = ancestor.parentRow;
			}
			
		} 
	}
}