package com.tibco.cep.ui.monitor.util{
	
	public class RandomXMLGenerator{
		
		private static var datapointTemplate:String = 
			"<datapoint>"+
				"<category>#CAT#</category>"+
				"<value>#VAL#</value>"+
			"</datapoint>";
			
		public static function test():void{
			updateRandom(testTop10);
			var timeValTest:XML = newLogicalTimeValue(testTimeValue);
			var list:XMLList = testTimeBasedData.series.datapoint; 
			list[list.length()] = timeValTest;
			trace(testTimeBasedData.toString());
		}
		
		/**
		 * Updates a random value element with a new value +-%50 of the old.
		 * This function is mainly intended for top 10 panes and table panes.
		 * @param xml The xml to update.  Should be the full xml data for the pane.
		 */
		public static function updateRandom(xml:XML, seriesName:String=""):void{
			var values:XMLList;
			if(seriesName != ""){
				values = xml.series.(@name==seriesName).datapoint.value;
			}
			else{ values = xml.series.datapoint.value;}
			var index:int = Math.floor(Math.random()*values.length());
			var value:Number = Number(values[index]);
			value += (Math.random()-.5)*value;
			values[index] = value;
		}
		
		/**
		 * Creates a new node to append to an existing xml object which plots some metric vs time.
		 * @param xml The latest datapoint node.  The node created will have +-%50 the value of the previous
		 * @return A new XML datapoint node with a randomized value and incremented time
		 */
		public static function newLogicalTimeValue(xml:XML, valueCap:Number=-1):XML{
			var date:Date = new Date();
			date.setTime(xml.category);
			date.seconds += 60;
			var value:Number = Number(xml.value);
			value += (Math.random()-.5)*value;
			if(valueCap > 0 && value > valueCap) value = valueCap;
			var strXml:String = datapointTemplate.replace("#CAT#", date.valueOf()).replace("#VAL#", value);
			return new XML(strXml);
		}
		
		private static var testTimeValue:XML =
			<datapoint>
				<category>1236725648312</category>
				<value>4</value>
			</datapoint>;
			
		private static var testTimeBasedData:XML = 
			<data type="cpu">
				<series>
					<datapoint>
						<category>1236725648312</category>
						<value>4</value>
					</datapoint>
					<datapoint>
						<category>1236725708312</category>
						<value>8</value>
					</datapoint>
					<datapoint>
						<category>1236725768312</category>
						<value>16</value>
					</datapoint>
					<datapoint>
						<category>1236725828312</category>
						<value>32</value>
					</datapoint>
					<datapoint>
						<category>1236725888312</category>
						<value>64</value>
					</datapoint>
					<datapoint>
						<category>1236725948312</category>
						<value>48</value>
					</datapoint>
					<datapoint>
						<category>1236726008312</category>
						<value>56</value>
					</datapoint>
					<datapoint>
						<category>1236726068312</category>
						<value>92</value>
					</datapoint>
					<datapoint>
						<category>1236726128312</category>
						<value>75</value>
					</datapoint>
					<datapoint>
						<category>1236726188312</category>
						<value>60</value>
					</datapoint>
				</series>
			</data>;
		
		private static var testTop10:XML = 
			<data type="bestjqueue">
				<series>
					<datapoint>
						<category>tp_0</category>
						<value>50</value>
					</datapoint>
					<datapoint>
						<category>tp_1</category>
						<value>55</value>
					</datapoint>
					<datapoint>
						<category>tp_2</category>
						<value>60</value>
					</datapoint>
					<datapoint>
						<category>tp_3</category>
						<value>65</value>
					</datapoint>
				</series>
			</data>;
			

	}
}