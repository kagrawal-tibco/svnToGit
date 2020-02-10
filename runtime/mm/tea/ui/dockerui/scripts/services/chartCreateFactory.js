/**
 * Created by priyanka on 25/05/15
 */
beConfigModule.factory('chartCreateFactory',['utilService',function(utilService){  
	 	Highcharts.setOptions({
	 		global: {
	 			useUTC: false
	 		}
	 	});
    	var pieChart =  {
    	        credits: {
                    enabled: false
    	        },
    	        chart: {
    	        	height: 280,
    	        	 events:{
    	             }
    	        },    	        
                isUpdated: 0,
    	        title: {
    	        	text: 'Instances',
                    style: {
                        "color": "#333333",
                        "fontSize": "18px",
                        "fontWeight": "bold"
                    },
                    margin: 5,
                    events:{
   	             	}
    	        },
    	        subtitle:{
    	        	text:'Running',
    	        	align: "center",
    	        	floating: false,
    	        	style: { "color": "#555555" , "fontWeight":"bold" },
    	        	useHTML: false,
    	        	verticalAlign: "middle",
    	        	x: 0,
    	        	y: null
    	        },
    	        tooltip: {
    	        	formatter: function() {
                        return false;
                    }
    	        },
    	        plotOptions: {
    	        	pie: {
    	        		allowPointSelect: false,
    	        		showInLegend: true,
    	        		cursor: 'pointer',
    	        		dataLabels: {
    	        			connectorColor: 'transparent',
    	        			distance: 5,
    	        			style: {
                                fontWeight: 'bold',
                                fontSize: '12px'
                            },
                            enabled: false,
                            format: ''
    	        		}
    	        	}
    	        },
    	        series: [{
    	        	type: 'pie',
    	        	name: 'Instances',
    	        	size: '100%',
    	        	innerSize: '85%',
    	        	data: [
    	                    {
    	                        color: "#449A44",
    	                        name: "Ok"
    	                    },
    	                    {
    	                        color: "orange",//#939393
    	                        name: "Warning"
    	                    },
    	                    {
    	                    	color: "#FB5555",
    	                        name: "Critical"
    	                    },
    	                    {
    	                    	color: "#FDFD6A",
    	                        name: "Stopped"
    	                    },
    	                    {
    	                    	color: "gray",
    	                        name: "Other"
    	                    }
    	                ]
    	        }],
    	        setData : function setDonutChartData(dataArray){    	        	
    	            for (var index = 0; index < dataArray.length; index++) {
    	                          this.series[0].data[index]['y'] = dataArray[index];
    	                      }
    	          }
    		};
    	  
    	  var memoryPoolChart = {
    			  credits: {
                      enabled: false
                  },
                  title: {
                      text: "Memory Pool"
                  },
                  legend: {
                      padding: 2,
                      margin: 2
                  },
                  margin:2,
                  xAxis: {
                      title: {
                          text: 'Time'
                      },
                      type: 'datetime',
                      minRange: 5000
                  },
                  scrollbar: {
                      enabled: false
                  },
                  yAxis: {
                	  title: {
                          text: 'Memory'
                      },
                      plotLines: [{
                          value: 0,
                          width: 1,
                          color: '#808080'
                      }]
                  },
                  chart: {
                	  type: 'spline'  ,
                	  height : 210
                  },
                  tooltip: {
                      formatter: function () {
                          return '<b>' + this.series.name + '</b><br/>' +
                              Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                              Highcharts.numberFormat(this.y, 0) +"bytes";
                      }
                  },
                  isUpdated:0,
                  chartId : 'memorypool',
                  series: [
                           	{                     
    	                       name: 'Used Memory',
    	                       data: [],
    	                       animation: true
    	                    },
    	                    {                     
    	                       name: 'Committed Memory',
    	                       data: [],
    	                       animation: true
    	                    }
                  ],
                  setData:  function setDonutChartData(i,seriesData) {
                	  this.series[i].data = seriesData;
                      this.isUpdated += 1;
                  },
                  getData: function getData(i) {
                	  return angular.copy(this.series[i].data);
                  }
    	            
    	  }
          
          var memoryGroupChart = {
    			  credits: {
                      enabled: false
                  },
                  chart: {
                      type: 'bar',
                      height: 40,
                      width:150,
                      backgroundColor: null
                  },
                  title: {
                      text: ''
                  },
                  xAxis: {
                      categories: [
                          '',
                          ''
                      ],
                      lineColor: 'transparent',
                      tickLength: 0
                  },
                  yAxis: [{
                      min: 0,
                      title: {
                          text: ''
                      },
                      labels: {
                          enabled: false
                      },
                      gridLineWidth: 0
                  }],
                  legend: {
                      enabled: false,
                      shadow: false
                  },
                  tooltip: {
                      formatter: function () {
                          var indexS = this.series.index;
                          var series = this.point.series.chart.series;
                          var out = '<b>';

                          switch (indexS) {
                          	case 0:
                          		out += Highcharts.numberFormat(series[1].yData[0], 2) +"%";
                          		break;
                          	case 1:
                          		out += Highcharts.numberFormat(series[1].yData[0], 2) +"%";
                          		break;
                          }
                      
                          return out;
                          
                      }
                  },
                  plotOptions: {
                      bar: {
                          stacking: 'normal'
                      }
                  },
                  series: [{                     
                      name: 'free memory',
                      data: [],
                      animation: true,
                      stack: 'memory',
                      color: '#F8F8F8'
                  },
                  {
                      name: 'memory',
                      animation: true,
                      data: [],
                      stack: 'memory',
                      color: '#468847'
                  }],
                  isUpdated:0,
                  chartId : '',
                  setData:  function setDonutChartData(i,seriesData) {
                      this.series[i].data = seriesData;
                      this.isUpdated += 1;
                  },
                  getData: function getData(i) {
                      return angular.copy(this.series[i].data);
                  }
    	            
    	  }
          var cpuGroupChart = angular.copy(memoryGroupChart);
          cpuGroupChart.isUpdated=0;
          cpuGroupChart.chartId='';
          cpuGroupChart.series =  [{
              name: 'free CPU',
              animation: true,
              data: [],
              stack: 'cpu',
              color: '#F8F8F8'
          }, {
              name: 'CPU',
              animation: true,
              data: [],
              stack: 'cpu',
              color: '#ffab2e'
          },
          {
              name: 'backup',
              data: [],
              stack: 'backup',
              color: '#ffab2e',
              visible: false
          }];          
          cpuGroupChart.title.text = "";
          cpuGroupChart.getData = function getData(i) {
              return angular.copy(this.series[i].data);
          };
          cpuGroupChart.setData = function setDonutChartData(i,seriesData) {
        	  this.series[i].data = seriesData;
              this.isUpdated += 1;
          };
          cpuGroupChart.tooltip= {
              formatter: function () {
                  var indexS = this.series.index;
                  var series = this.point.series.chart.series;
                  var out = '<b>';

                  switch (indexS) {
                  	case 0:
                  		out += Highcharts.numberFormat(series[2].yData[0], 2) +"%";
                  		break;
                  	case 1:
                  		out += Highcharts.numberFormat(series[2].yData[0], 2) +"%";
                  		break;
                  }
              
                  return out;
                  
              }
          };
          
    	  return {
  			getChart: function(){                  
  				return angular.copy(pieChart);
  			},
  			getMemoryPoolChartForInstance:function(){
  				return angular.copy(memoryPoolChart);
  			},
  			getMemoryChartForGroup:function(){
  				return angular.copy(memoryGroupChart);
  			},
  			getCpuChartForGroup:function(){
  				return angular.copy(cpuGroupChart);
  			},
  			getChartOptions:function(chartConfig){
  				var chart = angular.copy(utilService.getDefaultChartOptions());
				chart.isUpdated=0;
				chart.chartId=chartConfig.id;
				chart.title.text = '';
				chart.xAxis.title.text=chartConfig.xconfig.title;
				chart.yAxis.title.text=chartConfig.yconfig.title;
				chart.xconfig=chartConfig.xconfig;
				chart.yconfig=chartConfig.yconfig;
				if(chartConfig.yconfig.precision>0){
					chart.yAxis.labels= {
			            format: '{value:.'+chartConfig.yconfig.precision+'f}'
			        };
				}
				if(chartConfig.xconfig.type != 'time'){
					chart.xAxis.type='category';
					chart.xAxis.minRange = 0;
					chart.xAxis.categories = [];
					chart.xAxis.tickInterval=0;
				}
				if(chartConfig.yconfig.type == 'time'){
					chart.yAxis.type='datetime';
				}
				chart.chart.type=chartConfig.chartType;
				
				for(var i=0;i<chartConfig.series.length;i++)
				{	
					var series;
					if(chartConfig.series[i].color != null){
						series={name:chartConfig.series[i].displayName,
								color:chartConfig.series[i].color,
							data:[],
							animation:true,
							index:i};
					}else{
						series={name:chartConfig.series[i].displayName,
								data:[],
								animation:true,
								index:i};
					}
					chart.series.push(series);
				}
				chart.tooltip.formatter = function () {
					return '<b>' + this.series.name + '</b><br/>' +
					utilService.getFormattedTextForChartTooltip(this.series.chart.userOptions.xconfig.type,this.x,chartConfig.xconfig.precision) + '<br/>' +
					utilService.getFormattedTextForChartTooltip(this.series.chart.userOptions.yconfig.type,this.y,chartConfig.yconfig.precision);
				};
				return chart;
  			}
  		}
}]);