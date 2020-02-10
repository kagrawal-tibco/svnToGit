package{
	import com.tibco.be.views.core.logging.LoggingService;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponentFactory;
	import com.tibco.be.views.core.ui.dashboard.UnknownComponent;
	import com.tibco.be.views.user.components.chart.BEVChartComponent;
	import com.tibco.be.views.user.components.drilldown.tabletree.BEVTableTreeComponent;
	import com.tibco.be.views.user.components.processmodel.BEVProcessModelComponent;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	
	import mx.controls.Alert;
	
	public class ComponentTestController{
		
		private static const COMPONENT_SIZE_RATIO:Number = 3/4;
		private static const BASE_WIDTH:Number = 200;
		private static const BASE_HEIGHT:Number = 140;
		
		private var _view:ComponentTest;
		private var _component:BEVComponent;
		
		private var _configXML:XML =
			<chartmodel componentid="97EB081B-9722-D56E-D1CB-668A54947A8C" componentname="PieTicker" componenttype="ChartComponent">
				<help/>
				<actionconfig disabled="false">
					<text>ROOT</text>
					<actionconfig command="showdialog" disabled="false" defaultitem="false">
						<text>Enlarge</text>
						<configparam name="dialogname">overlay</configparam>
						<dynamicparam name="componentid">currentcomponent.id</dynamicparam>
					</actionconfig>
					<actionconfig disabled="true">
						<text>Quick Edit</text>
					</actionconfig>
					<actionconfig disabled="true">
						<text>Related Charts</text>
					</actionconfig>
					<actionconfig command="delete" disabled="false" defaultitem="false">
						<text>Remove</text>
						<configparam name="command">removecomponent</configparam>
						<dynamicparam name="pageid">currentpage.id</dynamicparam>
						<dynamicparam name="panelid">currentpanel.id</dynamicparam>
						<dynamicparam name="componentid">currentcomponent.id</dynamicparam>
					</actionconfig>
					<actionconfig command="showdialog" disabled="true" defaultitem="false">
						<text>About this chart...</text>
						<configparam name="dialogname">helpdialog</configparam>
						<dynamicparam name="title">currentcomponent.title</dynamicparam>
						<dynamicparam name="help">currentcomponentmodel.help</dynamicparam>
					</actionconfig>
				</actionconfig>
				<visualizationdata componentid="97EB081B-9722-D56E-D1CB-668A54947A8C">
					<datarow id="1" templatetype="header" visualtype="categoryaxis" templateid="categoryaxisvalue">
						<datacolumn id="AAPL">
							<displayvalue>AAPL</displayvalue>
							<value>AAPL</value>
						</datacolumn>
						<datacolumn id="GOOG">
							<displayvalue>GOOG</displayvalue>
							<value>GOOG</value>
						</datacolumn>
						<datacolumn id="BAC">
							<displayvalue>BAC</displayvalue>
							<value>BAC</value>
						</datacolumn>
					</datarow>
				</visualizationdata>
				<chartconfig backgroundcolor="FFFFFF" componentid="97EB081B-9722-D56E-D1CB-668A54947A8C">
					<legendconfig orientation="horizontal" anchor="north"/>
					<plotareaconfig backgroundcolor="FFFFFF" gradientdirection="toptobottom">
						<plotpatternconfig color="E3E3E3" lines="both"/>
					</plotareaconfig>
					<categoryaxisconfig name="Categories" datatype="string" fontsize="10" fontcolor="414141" fontstyle="normal" relativeposition="below">
						<ticklabelconfig fontsize="10" fontcolor="333333" fontstyle="normal" placement="automatic" rotation="0" skipfactor="0"/>
					</categoryaxisconfig>
					<valueaxisconfig name="Values" fontsize="10" fontcolor="414141" fontstyle="normal" relativeposition="left" scale="normal" noofvisualdivisions="5">
						<ticklabelconfig fontsize="10" fontcolor="333333" fontstyle="normal" placement="continous"/>
					</valueaxisconfig>
					<charttypeconfig type="pie">
						<seriesconfig name="EDB09A54-4729-17F4-A615-699DB7A306FF" displayname="pieSeries" anchor="Q1" basecolor="C86E4D" highlightcolor="FFFFFF">
							<valuelabelconfig fontsize="10" fontcolor="333333" fontstyle="normal"/>
							<actionconfig disabled="false">
								<text>ROOT</text>
								<actionconfig command="launchinternallink" disabled="false" defaultitem="false">
									<text>DrillDown</text>
									<dynamicparam name="link">currentdatacolumn.link</dynamicparam>
								</actionconfig>
								<actionconfig disabled="false">
									<text>Links</text>
									<actionconfig command="launchexternallink" disabled="false" defaultitem="false">
										<text>Google</text>
										<param name="componentid">97EB081B-9722-D56E-D1CB-668A54947A8C</param>
										<param name="seriesid">EDB09A54-4729-17F4-A615-699DB7A306FF</param>
										<param name="fieldname">Ticker</param>
										<dynamicparam name="hrefparams">currentdatacolumn.typespec.hrefprms</dynamicparam>
									</actionconfig>
									<actionconfig command="launchexternallink" disabled="false" defaultitem="false">
										<text>Yahoo</text>
										<param name="componentid">97EB081B-9722-D56E-D1CB-668A54947A8C</param>
										<param name="seriesid">EDB09A54-4729-17F4-A615-699DB7A306FF</param>
										<param name="fieldname">Price</param>
										<dynamicparam name="hrefparams">currentdatacolumn.typespec.hrefprms</dynamicparam>
									</actionconfig>
								</actionconfig>
							</actionconfig>
						</seriesconfig>
						<typespecificattribute name="startingangle">0</typespecificattribute>
						<typespecificattribute name="direction">clockwise</typespecificattribute>
						<typespecificattribute name="sectors">0</typespecificattribute>
						<typespecificattribute name="showvalues">true</typespecificattribute>
					</charttypeconfig>
				</chartconfig>
			</chartmodel>;

		//private var _configXML:XML = <textmodel componentid="09835073-8F5C-3C54-4FA4-D4123471FCDF" componentname="Con_Test (132)" componenttype="TypeTable" />;
		private var _dataXML:XML =
			<visualizationdata componentid="97EB081B-9722-D56E-D1CB-668A54947A8C">
				<datarow id="1" templatetype="data" visualtype="pie" templateid="EDB09A54-4729-17F4-A615-699DB7A306FF">
					<datacolumn id="AAPL" templateid="EDB09A54-4729-17F4-A615-699DB7A306FF">
						<displayvalue>618</displayvalue>
						<value>618.0</value>
						<basecolor>C86E4D</basecolor>
						<highlightcolor>FFFFFF</highlightcolor>
						<tooltip>AAPL=618</tooltip>
						<link>/page.html?pagetype=SearchPage&amp;amp;stoken=870eea22-ba87-4779-8967-6c9d8e6ee0cd&amp;amp;componentid=97EB081B-9722-D56E-D1CB-668A54947A8C&amp;amp;seriesid=EDB09A54-4729-17F4-A615-699DB7A306FF&amp;amp;tupid=17</link>
						<typespecificattribute name="hrefprms">tupid=17</typespecificattribute>
					</datacolumn>
					<datacolumn id="GOOG" templateid="EDB09A54-4729-17F4-A615-699DB7A306FF">
						<displayvalue>604</displayvalue>
						<value>604.0</value>
						<basecolor>527880</basecolor>
						<highlightcolor>FFFFFF</highlightcolor>
						<tooltip>GOOG=604</tooltip>
						<link>/page.html?pagetype=SearchPage&amp;amp;stoken=870eea22-ba87-4779-8967-6c9d8e6ee0cd&amp;amp;componentid=97EB081B-9722-D56E-D1CB-668A54947A8C&amp;amp;seriesid=EDB09A54-4729-17F4-A615-699DB7A306FF&amp;amp;tupid=46</link>
						<typespecificattribute name="hrefprms">tupid=46</typespecificattribute>
					</datacolumn>
					<datacolumn id="BAC" templateid="EDB09A54-4729-17F4-A615-699DB7A306FF">
						<displayvalue>8</displayvalue>
						<value>8.0</value>
						<basecolor>C5BC5F</basecolor>
						<highlightcolor>FFFFFF</highlightcolor>
						<tooltip>BAC=8</tooltip>
						<link>/page.html?pagetype=SearchPage&amp;amp;stoken=870eea22-ba87-4779-8967-6c9d8e6ee0cd&amp;amp;componentid=97EB081B-9722-D56E-D1CB-668A54947A8C&amp;amp;seriesid=EDB09A54-4729-17F4-A615-699DB7A306FF&amp;amp;tupid=36</link>
						<typespecificattribute name="hrefprms">tupid=36</typespecificattribute>
					</datacolumn>
				</datarow>
			</visualizationdata>;
		
		public function ComponentTestController(view:ComponentTest){
			_view = view;
		}
		
		public function init():void{
			LoggingService.instance.init();
			_view.configXMLTxtArea.text = _configXML;
			_view.dataXMLTxtArea.text = _dataXML;
			_view.btn_RefreshConfig.addEventListener(MouseEvent.CLICK, handleRefreshConfig_Click);
			_view.btn_UpdateData.addEventListener(MouseEvent.CLICK, handleDataUpdate_Click);
			_view.hs_ComponentHeight.addEventListener(Event.CHANGE, handleComponentHeight_Change);
			_view.hs_ComponentWidth.addEventListener(Event.CHANGE, handleComponentWidth_Change);
			buildAndDisplayComponent();
		}

        public function resolvePageName(node:XML):String{
        	if(node == null) return "unknown";
        	else if(node.name() == "pageconfig") return node.@name;
        	else return resolvePageName(node.parent() as XML);
        }
            
        public function buildAndDisplayComponent():void {
        	try{
	        	//clear previous component
	    		_view.componentHolder.removeAllChildren();
	        	
	        	//build new component
				_component = BEVComponentFactory.instance.getComponent(
					new String(_configXML.@componentid),		//id
					new String(_configXML.@componentname),		//name
					new String(_configXML.@componenttype),		//title
					new String(_configXML.@componenttype),		//type
					new Rectangle(0,0,1,1)			//layout constraints
				) as BEVComponent;
				if(_component == null || _component is UnknownComponent){
					Alert.show(
						"Could not build component!\n" +
						"\tID: " + _configXML.@componentid +
						"\n\tName: " + _configXML.@componentname +
						"\n\tType: " + _configXML.@componenttype,
						"ERROR"
					);
				}	
				if(_component is BEVChartComponent){
					(_component as BEVChartComponent).initProperties();
				}
				
				if(_component is BEVProcessModelComponent || _component is BEVTableTreeComponent){
					_component.percentWidth = 100;
					_component.percentHeight = 100;
					_view.hs_ComponentWidth.enabled = false;
					_view.hs_ComponentHeight.enabled = false;
					if(_component is BEVTableTreeComponent){
						//_configXML = _dataXML;
						//(_component as BEVTableTreeComponent).TEST_ONLY_parseRowViewConfig(_dataXML);
					}
				}
				else{
					_component.width = BASE_WIDTH * _view.hs_ComponentWidth.value;
					_component.height = BASE_HEIGHT * _view.hs_ComponentHeight.value;
					_view.hs_ComponentWidth.enabled = true;
					_view.hs_ComponentHeight.enabled = true;
				}
//				_component.width = _view.componentHolder.width * _view.hs_ComponentWidth.value;
//				_component.height = _view.componentHolder.height * _view.hs_ComponentHeight.value;
//				_component.percentWidth = 100;
//				_component.percentHeight = 100;
//				_component.setStyle("borderStyle", "solid");
//				_component.setStyle("borderThickness", 5);
//				_component.setStyle("borderColor", "green");
				_component.componentConfig = _configXML;
				if(_dataXML != null) _component.componentData = _dataXML;
				
				_view.configXMLTxtArea.text = _configXML.toXMLString();
				_view.dataXMLTxtArea.text = _dataXML.toXMLString();
				_view.componentHolder.addChild(_component);
        	}
        	catch(error:Error){
        		Alert.show(error.message, "ERROR");
        		trace(error.getStackTrace());
        	}
		}
		
		
		private function handleRefreshConfig_Click(event:Event):void{
			if(_component is BEVTableTreeComponent){
				var ttc:BEVTableTreeComponent = (_component as BEVTableTreeComponent);
				_dataXML = XML(_view.dataXMLTxtArea.text);
				ttc.componentData = XML(_dataXML);
				//ttc.TEST_ONLY_parseRowViewConfig(_dataXML);
			}
			_configXML = XML(_view.configXMLTxtArea.text);
			buildAndDisplayComponent();
		}
		
		private function handleDataUpdate_Click(event:Event):void{
			try{
				_dataXML = XML(_view.dataXMLTxtArea.text);
				var xmlStr:String = _dataXML.toXMLString();
				if(_dataXML && xmlStr == ""){
					_dataXML = null;
				}
				if(_dataXML == null || _component is BEVTableTreeComponent){
					_component.componentData = _dataXML;
				}
				else{
					_component.updateData(_dataXML);
				}
			}
			catch(error:Error){
				Alert.show(error.message, "ERROR");
				trace(error.getStackTrace());
			}
		}
		
		private function handleComponentWidth_Change(event:Event):void{
			if(_component == null) return;
			_component.width = (BASE_WIDTH) * _view.hs_ComponentWidth.value;
		}
		
		private function handleComponentHeight_Change(event:Event):void{
			if(_component == null) return;
			_component.height = (BASE_HEIGHT) * _view.hs_ComponentHeight.value;
		}		
			
	}
}