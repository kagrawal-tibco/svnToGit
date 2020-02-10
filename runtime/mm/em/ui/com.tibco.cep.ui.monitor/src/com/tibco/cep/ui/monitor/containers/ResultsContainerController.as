package com.tibco.cep.ui.monitor.containers
{
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.ResultConst;
	import com.tibco.cep.ui.monitor.util.SortFunction;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.display.DisplayObject;
	import flash.utils.Dictionary;
	
	import flexlib.containers.SuperTabNavigator;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.containers.VBox;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.utils.StringUtil;
	
	public class ResultsContainerController
	{
		private var _resultsXML:XML;	//XML Object containing the data as it is send by the server
		private var _resultElement:XML; //XML Object representing the results XML element that is currently being handled 
		
		private var _view:ResultsContainer;
		private var _resultsTab:SuperTabNavigator;
		private var _resultsDataGrid:DataGrid;
		private var _success:Boolean			//true if operation was invoked with SUCCESS, false otherwise
		
		//Error handling constants
		private static const ERROR_MESSAGE_NODE_NAME:String="errormessage";
		
		private static const TOKEN:String = "#";
		
		public function ResultsContainerController(resultsView:ResultsContainer) {
			_view = resultsView;
			_resultsTab = _view.resultsTab;
		}
		
		public function updateData(resultsXML:XML):void {
			_resultsXML = resultsXML;
		}
		
		//'entity' can be any node in the topology tree, e.g. method, process, machine,...
		public function load(entityFullPath:String, operation:String=""):Boolean {
			var operationFullPath:String = operation==""?entityFullPath:entityFullPath +"/"+operation;
			
			try {
				if (_resultsXML.name().toString() == ResultConst.OPERATION) { 
					Logger.logInfo(this,"Parsing results for operation: " + _resultsXML.@name);
					for each (var elem:XML in _resultsXML.children()){
						parseXMLData(elem, operationFullPath);
					}								
				} else {
					parseXMLData(_resultsXML, operationFullPath);
				}
			} catch(error:Error) {
				loadPopUp(operationFullPath, ResultConst.ERROR, operationFullPath + ": " + ResultConst.ERROR_STANDARD);
				Logger.logError(this, ResultConst.ERROR_STANDARD  + ": " + operationFullPath);
				Logger.logError(this, error.getStackTrace());
				_success = false;
			} finally {
				return _success;
			}
		} //load
		
		private function parseXMLData(elem:XML, operationFullPath:String):void {
			_resultElement = elem;
			switch (elem.name().toString()) {
				case ResultConst.DATA:	
					for each (var child:XML in elem.children()) {
						_resultElement = child;	
						switch (child.name().toString()) {
							case ResultConst.TABLE:
								loadResultsTab(operationFullPath);
								break;
							case ResultConst.VALUE:
								loadResultsTab(operationFullPath);
								break;
							default:
								Logger.logError(this, ResultConst.ERROR_UNEXPECT_DATA + ": " + child.name().toString() + " for operation: " + operationFullPath);
								loadPopUp(operationFullPath, ResultConst.ERROR, ResultConst.ERROR_UNEXPECT_DATA + ": " + child.name().toString());
								_success = false;
								break;
						}
					} 
					break;	//case data
				case ResultConst.SUCCESS:
					loadPopUp(operationFullPath, ResultConst.SUCCESS);
					break;
				case ResultConst.INFO:
					loadPopUp(operationFullPath); 
					break;
				case ResultConst.WARNING: 
					loadPopUp(operationFullPath, ResultConst.WARNING);
					break;
				case ResultConst.ERROR:
					loadPopUp(operationFullPath, ResultConst.ERROR);
					break;
				case ResultConst.EVENT:
					if (isErrorEvent()) {
						loadPopUp(operationFullPath, ResultConst.ERROR);
						break;	
					} //else goes to default
				default:
					Logger.logError(this, ResultConst.ERROR_UNEXPECT_DATA + ": " + elem.name().toString() + " for operation: " + operationFullPath);
					loadPopUp(operationFullPath, ResultConst.ERROR, ResultConst.ERROR_UNEXPECT_DATA + ": " + elem.name().toString());
					_success = false;
					break;
			}		
		}
		
		/** if event XML has child nodes errorcode and/or errormessage => it is an error event
		 *	@return true if it is an error event
		 */
		private function isErrorEvent():Boolean {
			return ( _resultElement[ResultConst.ERROR_MSG] != undefined && 
					 _resultElement[ResultConst.ERROR_CODE] != undefined );
		}
			
		private function loadPopUp(operationFullPath:String, logLevel:String=ResultConst.INFO, 
									errMsg:String=ResultConst.ERROR_STANDARD):void {
			var msg:String;
			
			switch (logLevel) {
				case ResultConst.INFO:
					_success = true;
					msg = _resultElement.toString();
					break;
				case ResultConst.WARNING:
					_success = false;
					msg = "WARNING: " + _resultElement.toString();
					break;
				case ResultConst.SUCCESS:
//					_success= (_resultElement.toString() == "true");
					msg = "SUCCESS: "+_resultElement.toString().toUpperCase();
					break;
				default: //error or any other unexpected level (i.e. XML element)
					_success=false;
					msg = buildErrorMsg(operationFullPath,errMsg);
					break;
			}
			Util.PopInvokeOperMessage(msg, operationFullPath);
		}
		
		//XML fomat when sending a FailureResponseEvent 
//		<event Id="160627">
//  		<errorcode>
//				100
//			</errorcode>
//		  	<errormessage>
//				Exception occurred while invoking method: /process/Object Management......
//		  	</errormessage>
//		</event>
		private function buildErrorMsg(operationFullPath:String, errMsg:String=ResultConst.ERROR_STANDARD, errCode:String=""):String {
			//if an unexpected XML element occurs, builds a standard error message 
			if ( (_resultsXML[ResultConst.ERROR] == undefined && _resultsXML[ResultConst.EVENT] == undefined) ) {
						
				_resultsXML.appendChild(createErrElem(errMsg, errCode));
				
				//make "current element" point to the recently added XML error tag
				_resultElement = _resultsXML[ResultConst.ERROR][0]; //[0] is necessary because it returns an XML Collection and the added element is added on top
			}
			
			var msg:String="ERROR!\n\n";
			//the $ token is used to emulate a new line (\n) due to the multiple problems arising by 
			//trying to transmit \n characters in XML strings  
			var errSplit:Array = String(_resultElement[ResultConst.ERROR_MSG]).split("$");
						
			for each (var line:String in errSplit) {
				msg = msg + line + "\n";
			}
//				msg = msg.substring(0, msg.length-1);		//remove the last \n
				
				//add error code
				var code:String = _resultElement[ResultConst.ERROR_CODE].toString();
				if (StringUtil.trim(code).length != 0)
					msg = msg + "(Error " + code + ")";
			
			return msg;	
		}
			
		private function createErrElem(errMsg:String="", errCode:String=""):XML {
			var elem:String  = "<error><errorcode>"+errCode+"</errorcode><errormessage>"+errMsg+"</errormessage></error>";
			return new XML(elem);
		}	
			
		/** replaces every space with _ to construct a valid dictionary key */			
		private function getEntityKey(entityFullPath:String):String { 
			var key:String = entityFullPath.replace(/ /g,"_");
			return key;
		}
		
		private function loadResultsTab(entityFullPath:String):void {
			var key:String = getEntityKey(entityFullPath);
			var tabObj:DisplayObject = _resultsTab.getChildByName(key);
			try {
				if (tabObj!=null) {
					updateTab(tabObj);
				} else { //this operation was not yet invoked by the user => tab does not exist, so it gets created.
					var methodName:Array = entityFullPath.split("/");
					addTab(methodName[methodName.length-1], entityFullPath, key);
				}
			} catch (err:Error) {
				Logger.logError(this, "Exception occurred while adding results tab for operation: " + entityFullPath);
				Logger.logError(this, err.getStackTrace());
			}
		}
		
		private function updateTab(tabObj:DisplayObject):void {
			var results:VBox = (tabObj as VBox);
			results.removeChildAt(1);	//child[0] is Label with method full path. child[1] is DataGrid or Label with the value 
			putResultsInTab(results);
			_resultsTab.selectedIndex = _resultsTab.getChildIndex(tabObj);
		}
		
		/** Creates a new tab and adds it to the results container */
		private function addTab(label:String, methodPath:String, key:String, icon:Class=null):void {
			try{
				if(label=="") label = "Untitled";
				
				var results:VBox = new VBox();
				results.setStyle("closable", true);
				
				//set the tab name to the invoked methodName and tooltip to the method FQN
				results.label = label;
				results.toolTip = methodPath;
				
				//if we wish to add an icon to each tab
				if(icon) {
					results.icon = icon;
				}
				else { //do nothing for now
					//results.icon = document_icon;
				}
				
				//add method fully qualified name to the top of the results tab
				var methodPathLabel:Label = new Label();
				methodPathLabel.text = methodPath; 
				results.addChild(methodPathLabel);	
				
				putResultsInTab(results);		
						
				//if an exception occurs the tab does not get created. An error PopUp is loaded instead.
				results.name = key;				//assign name to the tab (iDisplay object). Used to refresh the tab.
				_resultsTab.addChild(results);
				_resultsTab.selectedIndex = _resultsTab.getChildIndex(results);	//selects the most recently created tab
			}catch (e:Error) {
				_success=false;
				if (e.message.length == 0)	// the exception occurred in this function and not in one of the functions it calls
					e.message = "Exception occurred while creating results tab for operation: " + methodPath;
				loadPopUp(methodPath,ResultConst.ERROR,e.message); 
				Logger.logError(this,e.message);
				trace(e.getStackTrace());
			}
		} //addTab
		
		private function putResultsInTab(results:VBox):void {		
			//tabular data	
			var type:String = _resultElement.name().toString();	
			if( type == ResultConst.TABLE) {
				createResultsGrid();
				if (_resultsDataGrid != null && _resultsDataGrid.columnCount!=0) { 		
					results.addChild( _resultsDataGrid );
					populateResultsGrid(); //todo put this in createResultsGrid()
				} else {
					_resultElement = <value>null</value>		//TODO: CHECK THIS XXXXXXXXXXXXXXXXXXXXXx
					results.addChild(createSingularValue());
				}
				_success=true;
			} //it is a singular value
			else if( type == ResultConst.VALUE) { 
				results.addChild(createSingularValue());
				_success=true;				
			}
			else {
				results=null;
				Logger.logWarning(this, ResultConst.ERROR_UNEXPECT_DATA + ": " + type);
				_success=false;
			}
		}
		
		private function createResultsGrid():void {
			_resultsDataGrid = new DataGrid();
			var cols:Array = new Array();				//contains all the columns of this tabular data
			
			//Event Listener
			_resultsDataGrid.addEventListener(DataGridEvent.HEADER_RELEASE, handleColumnSort);
			
			//table has no rows, thus empty				//TODO: Do this in a way such that it does not open a tab
			try {
				if(_resultElement.row == undefined) {
					_resultsDataGrid=null;
					return;
				}
	
				//sets column titles 
				for each ( var columnconfig:XML in _resultElement.row[0].children() ) {
					var column:DataGridColumn = new DataGridColumn(columnconfig.@name);
					column.dataField = columnconfig.@name;
					//TODO: Improve columns relative width
					if ( (columnconfig.@name).toString().toLowerCase()=="row" ) {
						column.sortCompareFunction = SortFunction.sortNumeric;
						column.width = 50;
						cols.unshift(column);	//make 'row' the first column of the table by adding it to the front of the array
					}
					else {
						column.width = 200;
						cols.push(column);
					}
				} //for
				//sets the columns for this grid
				_resultsDataGrid.columns = cols;
			}
			catch (e:Error) {
				_resultsDataGrid=null;
				throw Error("Exception occurred while creating results grid.");			//todo: put operation name
			}
		} //createResultsGrid		
		
		private function populateResultsGrid():void {
			var rowsData:ArrayCollection = new ArrayCollection();
			var columnNames:XMLList;
			try{
				//iterates over all rows and for each row puts the value corresponding 
				//to each column in the rowsData dictionary. Values are put in rowsData one row at a time. 
				//After each iteration the rowValues dictionary contains the data corresponding to one row.
				//rowsData is used as the dataProvider for the dataGrid 
				for each(var row:XML in _resultElement.children()) {
					columnNames = new XMLList();
					columnNames = row.children().@name;
					var columnValues:XMLList = row.children().@value;
					var rowValues:Dictionary = new Dictionary(true);
					
					for (var i:int = 0; i< columnNames.length(); i++) {
						rowValues[columnNames[i].toString()] = columnValues[i];
					}
					rowsData.addItem(rowValues);
		       } //for_each
				
				// Sort by column "Row", which is the first column in the array.
				var sortByFirstColumn:Sort = new Sort();
				sortByFirstColumn.fields = [new SortField((_resultsDataGrid.columns[0] as DataGridColumn).dataField,true,false,true)];
				rowsData.sort=sortByFirstColumn;
				rowsData.refresh();
		       //populates the DataGrid
		       _resultsDataGrid.dataProvider = rowsData;
		       _resultsDataGrid.visible=true;
		       _resultsDataGrid.showDataTips=true;
//		       _resultsDataGrid.editable = true;
//   		   _resultsDataGrid.selectable = true;
	 		} catch (e:Error) {
	 			_resultsDataGrid=null;
				throw Error("Exception occurred while populating results grid");		//to be handled in the stacked function call
			}
		} //populateResultsGrid
		
		private function createSingularValue():Label{
			var singularValue:Label = new Label();
			singularValue.text = _resultElement;
			return singularValue;
		}
		
		protected function handleColumnSort(event:DataGridEvent):void {
			//Sets the column name that is to be sorted by the SortFunction.sortNumeric class 
			SortFunction.columnDataField = event.dataField;
		}
		
	} //class
} //package