package com.tibco.be.views.core.ui.dashboard{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.alert.AlertComponent;
	import com.tibco.be.views.user.components.chart.BEVChartComponent;
	import com.tibco.be.views.user.components.drilldown.tabletree.BEVTableTreeComponent;
	import com.tibco.be.views.user.components.drilldown.querymanager.BEVQueryManagerComponent;
	import com.tibco.be.views.user.components.pagesetselector.BEVPageSetSelectorComponent;
	import com.tibco.be.views.user.components.processmodel.BEVProcessModelComponent;
	import com.tibco.be.views.user.components.table.BEVTableComponent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.geom.Rectangle;
	import flash.utils.Dictionary;
	import flash.utils.getDefinitionByName;
	//import flash.utils.getDefinitionByName;
	
	/**
	 * SynComponentFactory represents a factory which is used to create instances of components 
	 * based on component definition
	 */ 
	public class BEVComponentFactory{
		
		//**NOTE: Define a dummy variable for each component, else getDefinitionByName throws a
		//class not defined error at runtime
		private static var _dummyAlertComponent:AlertComponent = null;
		private static var _dummyPageSetSelector:BEVPageSetSelectorComponent = null;
		private static var _dummyBEVChart:BEVChartComponent = null;
		private static var _dummyTableComponent:BEVTableComponent = null;
		private static var _dummyProcessModelComponent:BEVProcessModelComponent = null;
		private static var _dummyBEVTableTreeComponent:BEVTableTreeComponent;
		private static var _dummyBEVQueryManagerComponent:BEVQueryManagerComponent;
		
		private static var _instance:BEVComponentFactory;
		
		//**NOTE: Define a dummy variable for each component, else getDefinitionByName throws a
		//class not defined error at runtime
		private static var _componentsconfiguration:XML = 
			<compdefs>
				<compdef type="PageSelectorComponent" name="com.tibco.be.views.user.components.pagesetselector::BEVPageSetSelectorComponent"/>
				<compdef type="ChartComponent" name="com.tibco.be.views.user.components.chart::BEVChartComponent"/>
				<compdef type="ProcessModelComponent" name="com.tibco.be.views.user.components.processmodel::BEVProcessModelComponent"/>
				<compdef type="StateMachineComponent" name="com.tibco.be.views.user.components.processmodel::BEVProcessModelComponent"/>
				<compdef type="TextComponent" name="com.tibco.be.views.user.components.table::BEVTableComponent"/>
				<compdef type="AlertComponent" name="com.tibco.be.views.user.components.alert::AlertComponent"/>
				<compdef type="TypeTable" name="com.tibco.be.views.user.components.drilldown.tabletree::BEVTableTreeComponent"/>
				<compdef type="SearchViewComponent" name="com.tibco.be.views.user.components.drilldown.tabletree::BEVTableTreeComponent"/>
				<compdef type="QueryManagerComponent" name="com.tibco.be.views.user.components.drilldown.querymanager::BEVQueryManagerComponent"/>
			</compdefs>
		
		private var _compTypeToClassDict:Dictionary;
		
		/**
		 * Singleton instance accessor 
		 */ 		
		public static function get instance():BEVComponentFactory{
			if(_instance == null){
				_instance = new BEVComponentFactory();
			}
			return _instance;
		}
		
		/**
		 * Default Constructor
		 */ 
		function BEVComponentFactory():void{
			_compTypeToClassDict = new Dictionary();
			for each(var compDefinition:XML in _componentsconfiguration.compdef){
				var compType:String = compDefinition.@type;
				var compImplClassName:String = new String(compDefinition.@name);
				_compTypeToClassDict[compType] = compImplClassName;
			}
		}
		
		/**
		 * Creates an instance of a component based on the component definition XML 
		 * @param componentDefinition The XML which provides enough information to instantiate a component
		 */ 
		public function getComponentByXML(componentDefinition:XML):BEVComponent{
			var compID:String = new String(componentDefinition.@id);
			var compName:String = new String(componentDefinition.@name);
			var compTitle:String = new String(componentDefinition.@title);	
			var compType:String = new String(componentDefinition.@type);
			var layoutConstraints:Rectangle = null;
			var layoutConstraintsXML:XML = componentDefinition.layoutconstraints[0];
			if(layoutConstraintsXML != null ){
				layoutConstraints = new Rectangle();
				layoutConstraints.width = parseInt(layoutConstraintsXML.@colspan);
				layoutConstraints.height = parseInt(layoutConstraintsXML.@rowspan);
			}
			return getComponent(compID, compName, compTitle, compType, layoutConstraints);
		}
		
		/**
		 * Creates an instance of a component based on the supplied arguments 
		 * @param compID The id of the component
		 * @param compName The name of the component
		 * @param compType The type of the component
		 * @param layoutConstraintes The layout constraints of the component
		 */  
		public function getComponent(compID:String, compName:String, compTitle:String, compType:String, layoutConstraints:Rectangle):BEVComponent{
			var bevComponent:BEVComponent = null;
			var clazzName:String = _compTypeToClassDict[compType];
			if(clazzName == null){
				Logger.log(DefaultLogEvent.WARNING, "could not find component for "+compType+", using default blank implementation...");
				bevComponent = new UnknownComponent();
			}
			else{ 
				bevComponent = new (getDefinitionByName(clazzName) as Class)();
			}
			bevComponent.componentId = compID;
			bevComponent.componentName = compName;
			bevComponent.componentTitle = compTitle;			
			bevComponent.componentType = compType;
			bevComponent.layoutConstraints = layoutConstraints;
			//do not call init
			return bevComponent;
		}
		
		public function cloneComponent(component:BEVComponent):BEVComponent{
			return getComponent(
				component.componentId,
				component.componentName,
				component.componentTitle,
				component.componentType,
				component.layoutConstraints
			);
		}
		
		/**
		 * Reforms a component to a new definition. Typically used when moving from one component type to another
		 * NOT IMPLEMENTED 
		 * @param component The component which is to be reformed 
		 * @param compID The new id of the component
		 * @param compName The new name of the component
		 * @param compType The new type of the component
		 * @param layoutConstraintes The new layout constraints of the component
		 */ 
		public function reformComponent(component:BEVComponent, compID:String, compName:String, compTitle:String, compType:String, layoutConstraints: Rectangle):BEVComponent{
			return component;
		}
	}
}