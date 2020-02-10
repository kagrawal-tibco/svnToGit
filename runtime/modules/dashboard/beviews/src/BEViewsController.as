package{
	
	import com.tibco.be.views.core.BEVLocalConnectionManager;
	import com.tibco.be.views.core.kernel.KernelLoader;
	import com.tibco.be.views.core.ui.mediator.DrillDownUIController;
	import com.tibco.be.views.core.ui.mediator.PortletUIController;
	import com.tibco.be.views.utils.URLParser;
	
	import flash.external.ExternalInterface;
	import flash.system.fscommand;
			
	public class BEViewsController{
		
		private var _view:BEViews;
		
		public function BEViewsController(view:BEViews){
			_view = view;
		}
		
		public function init():void{
			var parentWindowHREF:String = ExternalInterface.call("window.location.href.toString");
			var urlParser:URLParser = new URLParser(parentWindowHREF);
//			var urlParser:URLParser = new URLParser("http://localhost:8181/page.html?pagetype=SearchPage&stoken=1aec18c7-4817-4128-871f-cdb355102427&componentid=BC8F0D83-287D-7E1C-5902-7B09BB13FF80&seriesid=68E12483-534A-DB6A-CC21-A25CE60721B1&tupid=4259906551");
			urlParser.parse();
			var pageType:String = urlParser.getQueryParam("pagetype") as String;
			var token:String = urlParser.getQueryParam("stoken") as String; 
			var debugMode:Boolean = Boolean(urlParser.getQueryParam("debug"));
			if(debugMode){
				var connectionName:String = new Date().time.toString();
				BEVLocalConnectionManager.instance.connectionName = connectionName;
				//this should be extracted out. bad practice to have the file name hard coded in the swf
				var link:String = urlParser.fullURLPath + "dashboardmonitor.html?localConnectionName=" + connectionName;
				fscommand("showlink", link);
			}
			var kernelLoader:KernelLoader = null;
			switch(pageType){
				case(PortletUIController.PORTLET_PAGETYPE):
					kernelLoader = new KernelLoader(KernelLoader.EMBEDDED_MODE)
					break;
				case(DrillDownUIController.RELATEDCONCEPT_PAGETYPE):
				case(DrillDownUIController.DRILLDOWN_PAGETYPE):
					//_view.pageTitle = "Business Events Views - Drill Down";
					kernelLoader = new KernelLoader(KernelLoader.CHILD_MODE, null, token);
					break;
				default: //BaseUIController.DASHBOARD_PAGETYPE
					//_view.pageTitle = "Business Events Views";
					kernelLoader = new KernelLoader(KernelLoader.STANDALONE_MODE)
			}
			//pass any additional params to the kernel loader
			kernelLoader.params = urlParser.queryParams;
			kernelLoader.addParam("hostingURL", parentWindowHREF);
			kernelLoader.load();
		}
		
	}
}