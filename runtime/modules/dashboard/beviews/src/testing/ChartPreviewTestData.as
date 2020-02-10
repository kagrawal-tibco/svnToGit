package testing{
	
	public class ChartPreviewTestData{
		
		public static var COMPLETE_XML:XML =
			<chartmodel componentid="9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459" componentname="Chart" componenttype="ChartComponent">
			  <description/>
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
			      <text>About this Chart...</text>
			      <configparam name="dialogname">helpdialog</configparam>
			      <dynamicparam name="title">currentcomponent.title</dynamicparam>
			      <dynamicparam name="help">currentcomponentmodel.help</dynamicparam>
			    </actionconfig>
			  </actionconfig>
			  <visualizationdata componentid="9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459">
			    <datarow id="1" templatetype="header" visualtype="categoryaxis" templateid="categoryaxisvalue">
			      <datacolumn id="1">
			        <displayvalue>1</displayvalue>
			        <value>1</value>
			      </datacolumn>
			      <datacolumn id="2">
			        <displayvalue>2</displayvalue>
			        <value>2</value>
			      </datacolumn>
			      <datacolumn id="3">
			        <displayvalue>3</displayvalue>
			        <value>3</value>
			      </datacolumn>
			      <datacolumn id="4">
			        <displayvalue>4</displayvalue>
			        <value>4</value>
			      </datacolumn>
			    </datarow>
				<datarow id="1" templatetype="data" visualtype="horizontalbar" templateid="7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040">
				  <datacolumn id="1" templateid="7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040">
					<displayvalue>90</displayvalue>
					<value>90</value>
					<tooltip>1=90</tooltip>
					<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=9&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link>
					<typespecificattribute name="hrefprms">tupid=9</typespecificattribute>
				  </datacolumn>
				  <datacolumn id="2" templateid="7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040">
					<displayvalue>100</displayvalue>
					<value>100</value>
					<tooltip>2=100</tooltip>
					<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=12&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link>
					<typespecificattribute name="hrefprms">tupid=12</typespecificattribute>
				  </datacolumn>
				  <datacolumn id="3" templateid="7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040">
					<displayvalue>80</displayvalue>
					<value>80</value>
					<tooltip>3=80</tooltip>
					<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=17&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link>
					<typespecificattribute name="hrefprms">tupid=17</typespecificattribute>
				  </datacolumn>
				  <datacolumn id="4" templateid="7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040">
					<displayvalue>30</displayvalue>
					<value>30</value>
					<basecolor>ff0000</basecolor>
					<highlightcolor>FFFFFF</highlightcolor>
					<tooltip>4=30</tooltip>
					<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=6e2bd1ed-b6b9-47d6-bcbe-4b96e6273d43&amp;tupid=4&amp;componentid=9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459&amp;seriesid=7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040</link>
					<typespecificattribute name="hrefprms">tupid=4</typespecificattribute>
					<font color="FFFFFF" style="normal"/>
				  </datacolumn>
				</datarow>
			  </visualizationdata>
			  <chartconfig backgroundcolor="C3CBD7" componentid="9458E4AD-A029-CDA2-C4F5-0A6D7F7AC459">
			    <legendconfig orientation="horizontal" anchor="north"/>
			    <plotareaconfig backgroundcolor="E8F0F0" gradientdirection="toptobottom">
			      <plotpatternconfig color="A9BACA" lines="both"/>
			    </plotareaconfig>
			    <categoryaxisconfig name="Category" datatype="string" fontsize="12" fontcolor="000000" fontstyle="italic" relativeposition="below" showticklabels="false">
			      <ticklabelconfig fontsize="12" fontcolor="000000" fontstyle="italic" placement="automatic" rotation="0" skipfactor="0"/>
			    </categoryaxisconfig>
			    <valueaxisconfig name="Value" fontsize="12" fontcolor="000000" fontstyle="italic" relativeposition="left" showticklabels="false" scale="normal" noofvisualdivisions="7">
			      <ticklabelconfig fontsize="12" fontcolor="000000" fontstyle="italic" placement="continous"/>
			    </valueaxisconfig>
			    <charttypeconfig type="horizontalbar">
			      <seriesconfig name="7FAEAA54-43D1-9C9A-DD90-2A7EF6A73040" displayname="Series_" anchor="Q1" basecolor="C86E4D" highlightcolor="FFFFFF">
			        <valuelabelconfig fontsize="10" fontcolor="000000" fontstyle="normal"/>
			        <actionconfig disabled="false">
			          <text>ROOT</text>
			          <actionconfig command="launchinternallink" disabled="false" defaultitem="false">
			            <text>DrillDown</text>
			            <dynamicparam name="link">currentdatacolumn.link</dynamicparam>
			          </actionconfig>
			          <actionconfig command="launchexternallink" disabled="true" defaultitem="false">
			            <text>Links</text>
			          </actionconfig>
			        </actionconfig>
			      </seriesconfig>
			      <typespecificattribute name="width">10</typespecificattribute>
			      <typespecificattribute name="topcapthickness">0.5</typespecificattribute>
			      <typespecificattribute name="topcap">true</typespecificattribute>
			      <typespecificattribute name="topcapcolor">000000</typespecificattribute>
			      <typespecificattribute name="overlappercentage">0</typespecificattribute>
			      <typespecificattribute name="showvalues">true</typespecificattribute>
			    </charttypeconfig>
			  </chartconfig>
			</chartmodel>;
		
		public static var CONFIG_XML:XML =
			<chartmodel componentid="33405D83-4B27-3461-2597-5B1C721D61F0" componentname="VRange_Default" componenttype="ChartComponent">
				<description/>
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
						<text>About this Chart...</text>
						<configparam name="dialogname">helpdialog</configparam>
						<dynamicparam name="title">currentcomponent.title</dynamicparam>
						<dynamicparam name="help">currentcomponentmodel.help</dynamicparam>
					</actionconfig>
				</actionconfig>
				<visualizationdata componentid="33405D83-4B27-3461-2597-5B1C721D61F0">
					<datarow id="1" templatetype="header" visualtype="categoryaxis" templateid="categoryaxisvalue">
						<datacolumn id="Dave">
							<displayvalue>Dave</displayvalue>
							<value>Dave</value>
						</datacolumn>
					</datarow>
				</visualizationdata>
				<chartconfig backgroundcolor="FFFFFF" componentid="33405D83-4B27-3461-2597-5B1C721D61F0">
					<plotareaconfig backgroundcolor="FFFFFF" gradientdirection="toptobottom">
						<plotpatternconfig color="CCCCCC" lines="both"/>
					</plotareaconfig>
					<categoryaxisconfig name="Category" datatype="string" fontsize="12" fontcolor="000000" fontstyle="normal" relativeposition="below" showticklabels="false">
						<ticklabelconfig fontsize="10" fontcolor="000000" fontstyle="bold" placement="automatic" rotation="0" skipfactor="0"/>
					</categoryaxisconfig>
					<valueaxisconfig name="Something long in english" fontsize="12" fontcolor="000000" fontstyle="normal" relativeposition="left" showticklabels="false" scale="normal" noofvisualdivisions="5">
						<ticklabelconfig fontsize="10" fontcolor="000000" fontstyle="normal" placement="continous"/>
					</valueaxisconfig>
					<charttypeconfig type="verticalrange">
						<seriesconfig name="minvalue" displayname="min" anchor="Q1" basecolor="C86E4D" highlightcolor="FFFFFF">
							<valuelabelconfig fontsize="10" fontcolor="000000" fontstyle="normal"/>
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
										<param name="componentid">33405D83-4B27-3461-2597-5B1C721D61F0</param>
										<param name="seriesid">0CC77FFB-59A1-1866-E0FB-905D16DE83F0</param>
										<param name="fieldname">category</param>
										<dynamicparam name="hrefparams">currentdatacolumn.typespec.hrefprms</dynamicparam>
									</actionconfig>
								</actionconfig>
							</actionconfig>
						</seriesconfig>
						<seriesconfig name="currentvalue" displayname="avg" anchor="Q1" basecolor="527880" highlightcolor="FFFFFF">
							<valuelabelconfig fontsize="10" fontcolor="000000" fontstyle="bold"/>
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
										<param name="componentid">33405D83-4B27-3461-2597-5B1C721D61F0</param>
										<param name="seriesid">02AB04D6-E467-1622-2FED-0167FEE4828B</param>
										<param name="fieldname">category</param>
										<dynamicparam name="hrefparams">currentdatacolumn.typespec.hrefprms</dynamicparam>
									</actionconfig>
								</actionconfig>
							</actionconfig>
						</seriesconfig>
						<seriesconfig name="maxvalue" displayname="max" anchor="Q1" basecolor="C5BC5F" highlightcolor="FFFFFF">
							<valuelabelconfig fontsize="12" fontcolor="000000" fontstyle="normal"/>
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
										<param name="componentid">33405D83-4B27-3461-2597-5B1C721D61F0</param>
										<param name="seriesid">B490BA29-3F15-0B9B-1691-16DD8A37E808</param>
										<param name="fieldname">category</param>
										<dynamicparam name="hrefparams">currentdatacolumn.typespec.hrefprms</dynamicparam>
									</actionconfig>
								</actionconfig>
							</actionconfig>
						</seriesconfig>
						<typespecificattribute name="plotshape">circle</typespecificattribute>
						<typespecificattribute name="plotshapedimension">3</typespecificattribute>
						<typespecificattribute name="whiskerthickness">4</typespecificattribute>
						<typespecificattribute name="whiskerwidth">5</typespecificattribute>
						<typespecificattribute name="barwidth">3</typespecificattribute>
						<typespecificattribute name="showvalues">true</typespecificattribute>
					</charttypeconfig>
				</chartconfig>
			</chartmodel>;

		public static var DATA_XML:XML = 
			<visualizationdata componentid="33405D83-4B27-3461-2597-5B1C721D61F0">
				<datarow id="1" templatetype="data" visualtype="verticalrange" templateid="minvalue">
					<datacolumn id="Dave" templateid="0CC77FFB-59A1-1866-E0FB-905D16DE83F0">
						<displayvalue>2</displayvalue>
						<value>2.0</value>
						<tooltip>Dave=2</tooltip>
						<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=d61feef0-51d2-4748-8bcb-3f8300889525&amp;tupid=28&amp;componentid=33405D83-4B27-3461-2597-5B1C721D61F0&amp;seriesid=0CC77FFB-59A1-1866-E0FB-905D16DE83F0</link>
						<typespecificattribute name="hrefprms">tupid=28</typespecificattribute>
					</datacolumn>
				</datarow>
				<datarow id="2" templatetype="data" visualtype="verticalrange" templateid="currentvalue">
					<datacolumn id="Dave" templateid="02AB04D6-E467-1622-2FED-0167FEE4828B">
						<displayvalue>6</displayvalue>
						<value>6.0</value>
						<tooltip>Dave=6</tooltip>
						<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=d61feef0-51d2-4748-8bcb-3f8300889525&amp;tupid=28&amp;componentid=33405D83-4B27-3461-2597-5B1C721D61F0&amp;seriesid=02AB04D6-E467-1622-2FED-0167FEE4828B</link>
						<typespecificattribute name="hrefprms">tupid=28</typespecificattribute>
					</datacolumn>
				</datarow>
				<datarow id="3" templatetype="data" visualtype="verticalrange" templateid="maxvalue">
					<datacolumn id="Dave" templateid="B490BA29-3F15-0B9B-1691-16DD8A37E808">
						<displayvalue>8</displayvalue>
						<value>8.0</value>
						<tooltip>Dave=8</tooltip>
						<link>/searchpageloader.html?uri=/dashboard/controller&amp;stoken=d61feef0-51d2-4748-8bcb-3f8300889525&amp;tupid=28&amp;componentid=33405D83-4B27-3461-2597-5B1C721D61F0&amp;seriesid=B490BA29-3F15-0B9B-1691-16DD8A37E808</link>
						<typespecificattribute name="hrefprms">tupid=28</typespecificattribute>
					</datacolumn>
				</datarow>
			</visualizationdata>;
			
		public function ChartPreviewTestData(){
			
		}

	}
}