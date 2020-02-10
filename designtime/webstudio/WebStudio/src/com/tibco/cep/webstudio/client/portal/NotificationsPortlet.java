package com.tibco.cep.webstudio.client.portal;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.events.MaximizeClickEvent;
import com.smartgwt.client.widgets.events.MaximizeClickHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class NotificationsPortlet extends WebStudioPortlet {

	public class MultiSeriesChartData extends Record {  
		  
	    public MultiSeriesChartData(String time, float value, String region) {  
	        setAttribute("time", time);  
	        setAttribute("value", value);  
	        setAttribute("region", region);  
	    }  
	  
	    public MultiSeriesChartData[] getData() {  
	        return new MultiSeriesChartData[] {  
	            new MultiSeriesChartData("1/1/2002", 108.88f, "North"),  
	            new MultiSeriesChartData("1/1/2002", 891.93f, "South"),  
	            new MultiSeriesChartData("1/1/2002", 715.13f, "East"),  
	            new MultiSeriesChartData("1/1/2002", 559.34f, "West"),  
	            new MultiSeriesChartData("2/1/2002", 626.63f, "North"),  
	            new MultiSeriesChartData("2/1/2002", 637.73f, "South"),  
	            new MultiSeriesChartData("2/1/2002", 976.97f, "East"),  
	            new MultiSeriesChartData("2/1/2002", 216.27f, "West"),  
	            new MultiSeriesChartData("3/1/2002",  799.18f, "North"),  
	            new MultiSeriesChartData("3/1/2002",  916.38f, "South"),  
	            new MultiSeriesChartData("3/1/2002",  853.82f, "East"),  
	            new MultiSeriesChartData("3/1/2002",  344.79f, "West"),  
	            new MultiSeriesChartData("4/1/2002",  707.1f, "North"),  
	            new MultiSeriesChartData("4/1/2002",  796.73f, "South"),  
	            new MultiSeriesChartData("4/1/2002",  590.05f, "East"),  
	            new MultiSeriesChartData("4/1/2002",  209.03f, "West"),  
	            new MultiSeriesChartData("5/1/2002",  321.58f, "North"),  
	            new MultiSeriesChartData("5/1/2002",  363.25f, "South"),  
	            new MultiSeriesChartData("5/1/2002",  446.66f, "East"),  
	            new MultiSeriesChartData("5/1/2002",  338.79f, "West"),  
	            new MultiSeriesChartData("6/1/2002",  423.43f, "North"),  
	            new MultiSeriesChartData("6/1/2002",  216.04f, "South"),  
	            new MultiSeriesChartData("6/1/2002",  511.3f, "East"),  
	            new MultiSeriesChartData("6/1/2002",  292.49f, "West"),  
	            new MultiSeriesChartData("7/1/2002",  837.1f, "North"),  
	            new MultiSeriesChartData("7/1/2002",  970.83f, "South"),  
	            new MultiSeriesChartData("7/1/2002",  888.2f, "East"),  
	            new MultiSeriesChartData("7/1/2002",  842.14f, "West"),  
	            new MultiSeriesChartData("8/1/2002",  975.65f, "North"),  
	            new MultiSeriesChartData("8/1/2002",  758.96f, "South"),  
	            new MultiSeriesChartData("8/1/2002",  853.26f, "East"),  
	            new MultiSeriesChartData("8/1/2002",  103.42f, "West"),  
	            new MultiSeriesChartData("9/1/2002",  440.78f, "North"),  
	            new MultiSeriesChartData("9/1/2002",  727.4f, "South"),  
	            new MultiSeriesChartData("9/1/2002",  822.97f, "East"),  
	            new MultiSeriesChartData("9/1/2002",  405.61f, "West"),  
	            new MultiSeriesChartData("10/1/2002",  327.79f, "North"),  
	            new MultiSeriesChartData("10/1/2002",  538.75f, "South"),  
	            new MultiSeriesChartData("10/1/2002",  854.46f, "East"),  
	            new MultiSeriesChartData("10/1/2002",  106.61f, "West"),  
	            new MultiSeriesChartData("11/1/2002",  451.64f, "North"),  
	            new MultiSeriesChartData("11/1/2002",  871.23f, "South"),  
	            new MultiSeriesChartData("11/1/2002",  145.49f, "East"),  
	            new MultiSeriesChartData("11/1/2002",  829.97f, "West"),  
	            new MultiSeriesChartData("12/1/2002",  178.88f, "North"),  
	            new MultiSeriesChartData("12/1/2002",  918.7f, "South"),  
	            new MultiSeriesChartData("12/1/2002",  534.44f, "East"),  
	            new MultiSeriesChartData("12/1/2002",  964.85f, "West"),  
	            new MultiSeriesChartData("1/1/2003",  122.72f, "North"),  
	            new MultiSeriesChartData("1/1/2003",  890.61f, "South"),  
	            new MultiSeriesChartData("1/1/2003",  234.3f, "East"),  
	            new MultiSeriesChartData("1/1/2003",  166.02f, "West"),  
	            new MultiSeriesChartData("2/1/2003",  252.96f, "North"),  
	            new MultiSeriesChartData("2/1/2003",  512.08f, "South"),  
	            new MultiSeriesChartData("2/1/2003",  162.72f, "East"),  
	            new MultiSeriesChartData("2/1/2003",  853.91f, "West"),  
	            new MultiSeriesChartData("3/1/2003",  786.04f, "North"),  
	            new MultiSeriesChartData("3/1/2003",  523.83f, "South"),  
	            new MultiSeriesChartData("3/1/2003",  781.93f, "East"),  
	            new MultiSeriesChartData("3/1/2003",  714.54f, "West"),  
	            new MultiSeriesChartData("4/1/2003",  490.04f, "North"),  
	            new MultiSeriesChartData("4/1/2003",  182.19f, "South"),  
	            new MultiSeriesChartData("4/1/2003",  258.83f, "East"),  
	            new MultiSeriesChartData("4/1/2003",  199.57f, "West"),  
	            new MultiSeriesChartData("5/1/2003",  232.65f, "North"),  
	            new MultiSeriesChartData("5/1/2003",  759.15f, "South"),  
	            new MultiSeriesChartData("5/1/2003",  134.91f, "East"),  
	            new MultiSeriesChartData("5/1/2003",  269.29f, "West"),  
	            new MultiSeriesChartData("6/1/2003",  436.18f, "North"),  
	            new MultiSeriesChartData("6/1/2003",  423.31f, "South"),  
	            new MultiSeriesChartData("6/1/2003",  592.31f, "East"),  
	            new MultiSeriesChartData("6/1/2003",  677.62f, "West"),  
	            new MultiSeriesChartData("7/1/2003",  667.57f, "North"),  
	            new MultiSeriesChartData("7/1/2003",  834.87f, "South"),  
	            new MultiSeriesChartData("7/1/2003",  953.77f, "East"),  
	            new MultiSeriesChartData("7/1/2003",  902.08f, "West"),  
	            new MultiSeriesChartData("8/1/2003",  485.39f, "North"),  
	            new MultiSeriesChartData("8/1/2003",  455.7f, "South"),  
	            new MultiSeriesChartData("8/1/2003",  182.68f, "East"),  
	            new MultiSeriesChartData("8/1/2003",  213.83f, "West"),  
	            new MultiSeriesChartData("9/1/2003",  799.83f, "North"),  
	            new MultiSeriesChartData("9/1/2003",  239.27f, "South"),  
	            new MultiSeriesChartData("9/1/2003",  383.04f, "East"),  
	            new MultiSeriesChartData("9/1/2003",  392.67f, "West"),  
	            new MultiSeriesChartData("10/1/2003",  533.71f, "North"),  
	            new MultiSeriesChartData("10/1/2003",  352.66f, "South"),  
	            new MultiSeriesChartData("10/1/2003",  299.48f, "East"),  
	            new MultiSeriesChartData("10/1/2003",  983.99f, "West"),  
	            new MultiSeriesChartData("11/1/2003",  752.46f, "North"),  
	            new MultiSeriesChartData("11/1/2003",  710.61f, "South"),  
	            new MultiSeriesChartData("11/1/2003",  817.26f, "East"),  
	            new MultiSeriesChartData("11/1/2003",  798.84f, "West"),  
	            new MultiSeriesChartData("12/1/2003",  349.16f, "North"),  
	            new MultiSeriesChartData("12/1/2003",  645.47f, "South"),  
	            new MultiSeriesChartData("12/1/2003",  462.25f, "East"),  
	            new MultiSeriesChartData("12/1/2003",  413.88f, "West"),  
	            new MultiSeriesChartData("1/1/2004",  107.5f, "North"),  
	            new MultiSeriesChartData("1/1/2004",  237.2f, "South"),  
	            new MultiSeriesChartData("1/1/2004",  900.95f, "East"),  
	            new MultiSeriesChartData("1/1/2004",  671.39f, "West"),  
	            new MultiSeriesChartData("2/1/2004",  201.79f, "North"),  
	            new MultiSeriesChartData("2/1/2004",  398.4f, "South"),  
	            new MultiSeriesChartData("2/1/2004",  440.5f, "East"),  
	            new MultiSeriesChartData("2/1/2004",  179.28f, "West"),  
	            new MultiSeriesChartData("3/1/2004",  696.97f, "North"),  
	            new MultiSeriesChartData("3/1/2004",  572.3f, "South"),  
	            new MultiSeriesChartData("3/1/2004",  528.65f, "East"),  
	            new MultiSeriesChartData("3/1/2004",  811.09f, "West"),  
	            new MultiSeriesChartData("4/1/2004",  482.62f, "North"),  
	            new MultiSeriesChartData("4/1/2004",  657.75f, "South"),  
	            new MultiSeriesChartData("4/1/2004",  253.78f, "East"),  
	            new MultiSeriesChartData("4/1/2004",  205.98f, "West"),  
	            new MultiSeriesChartData("5/1/2004",  602.92f, "North"),  
	            new MultiSeriesChartData("5/1/2004",  659.21f, "South"),  
	            new MultiSeriesChartData("5/1/2004",  844.88f, "East"),  
	            new MultiSeriesChartData("5/1/2004",  268.72f, "West"),  
	            new MultiSeriesChartData("6/1/2004",  961.68f, "North"),  
	            new MultiSeriesChartData("6/1/2004",  367.78f, "South"),  
	            new MultiSeriesChartData("6/1/2004",  171.6f, "East"),  
	            new MultiSeriesChartData("6/1/2004",  669.74f, "West"),  
	            new MultiSeriesChartData("7/1/2004",  157.36f, "North"),  
	            new MultiSeriesChartData("7/1/2004",  781.19f, "South"),  
	            new MultiSeriesChartData("7/1/2004",  244.19f, "East"),  
	            new MultiSeriesChartData("7/1/2004",  345.07f, "West"),  
	            new MultiSeriesChartData("8/1/2004",  803.01f, "North"),  
	            new MultiSeriesChartData("8/1/2004",  398.78f, "South"),  
	            new MultiSeriesChartData("8/1/2004",  713.52f, "East"),  
	            new MultiSeriesChartData("8/1/2004",  996.36f, "West"),  
	            new MultiSeriesChartData("9/1/2004",  942.33f, "North"),  
	            new MultiSeriesChartData("9/1/2004",  411.54f, "South"),  
	            new MultiSeriesChartData("9/1/2004",  371.09f, "East"),  
	            new MultiSeriesChartData("9/1/2004",  590.91f, "West"),  
	            new MultiSeriesChartData("10/1/2004",  874.63f, "North"),  
	            new MultiSeriesChartData("10/1/2004",  857.04f, "South"),  
	            new MultiSeriesChartData("10/1/2004",  649.05f, "East"),  
	            new MultiSeriesChartData("10/1/2004",  861.61f, "West"),  
	            new MultiSeriesChartData("11/1/2004",  789.86f, "North"),  
	            new MultiSeriesChartData("11/1/2004",  788.52f, "South"),  
	            new MultiSeriesChartData("11/1/2004",  120.13f, "East"),  
	            new MultiSeriesChartData("11/1/2004",  807.02f, "West"),  
	            new MultiSeriesChartData("12/1/2004",  268.58f, "North"),  
	            new MultiSeriesChartData("12/1/2004",  774.83f, "South"),  
	            new MultiSeriesChartData("12/1/2004",  997.72f, "East"),  
	            new MultiSeriesChartData("12/1/2004",  191.29f, "West")  
	        };  
	    }  
	  
	}

	private FacetChart chart; 
	
    public NotificationsPortlet() {
		super();
		initialize();
	}

	protected void initialize() {
		if (initialized) {
			return;
		}
    	setTitle(DASHBOARD_PORTLETS.NOTIFICATIONS.getTitle());

		Canvas chart = createFacetChart();  
    	this.setModularCanvas(chart);
    	this.addRestoreClickHandler(new RestoreClickHandler() {
			
			@Override
			public void onRestoreClick(RestoreClickEvent event) {
				redrawChart();
			}
		});
    	this.addMaximizeClickHandler(new MaximizeClickHandler() {
			
			@Override
			public void onMaximizeClick(MaximizeClickEvent event) {
				redrawChart();
			}
		});
    	initialized = true;
	}

	@Override
	protected void onDraw() {
		redrawChart();
		super.onDraw();
	}

	private Canvas createFacetChart() {
		if(SC.hasAnalytics()) {  
            if(SC.hasDrawing()) {  
                chart = new FacetChart();  
                chart.setData(new MultiSeriesChartData("",0,"").getData());  
                chart.setFacets(new Facet("time", "Period"), new Facet("region", "Region"));  
                chart.setValueProperty("value");  
                chart.setChartType(ChartType.AREA);  
                chart.setTitle("CR Activity");  
  
                final DynamicForm chartSelector = new DynamicForm();  
                final SelectItem chartType = new SelectItem("chartType", "Chart Type");  
                chartType.setValueMap(  
                        ChartType.AREA.getValue(),  
                        ChartType.BAR.getValue(),  
                        ChartType.COLUMN.getValue(),  
                        ChartType.DOUGHNUT.getValue(),  
                        ChartType.LINE.getValue(),  
                        ChartType.PIE.getValue(),  
                        ChartType.RADAR.getValue());  
                chartType.setDefaultToFirstOption(true);  
                chartType.addChangedHandler(new ChangedHandler() {  
                    public void onChanged(ChangedEvent event) {  
                        String selectedChartType = chartType.getValueAsString();  
                        if (ChartType.AREA.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.AREA);  
                        } else if (ChartType.BAR.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.BAR);  
                        } else if (ChartType.COLUMN.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.COLUMN);  
                        } else if (ChartType.DOUGHNUT.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.DOUGHNUT);  
                        } else if (ChartType.LINE.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.LINE);  
                        } else if (ChartType.PIE.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.PIE);  
                        } else if (ChartType.RADAR.getValue().equals(selectedChartType)) {  
                            chart.setChartType(ChartType.RADAR);  
                        }  
                    }  
                });  
                chartSelector.setFields(chartType);  
  
                VLayout layout = new VLayout(15);  
                layout.addMember(chartSelector);  
                layout.addMember(chart);  
                layout.setWidth100();
                layout.setHeight100();
  
                return layout;  
            } else {  
                HTMLFlow htmlFlow = new HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled in this SDK because it requires the optional " +  
                "<a href=\"http://www.smartclient.com/product/index.jsp#drawing\" target=\"_blank\">Drawing module</a>.</p>" +  
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#multiSeriesChart\" target=\"\">here</a> to see this example on smartclient.com</p></div>");  
                htmlFlow.setWidth100();  
                return htmlFlow;  
            }  
        } else {  
            HTMLFlow htmlFlow = new HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled in this SDK because it requires the optional " +  
            "<a href=\"http://www.smartclient.com/product/index.jsp#analytics\" target=\"_blank\">Analytics module</a>.</p>" +  
            "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#multiSeriesChart\" target=\"\">here</a> to see this example on smartclient.com</p></div>");  
            htmlFlow.setWidth100();  
            return htmlFlow;  
        }  
	}

	@Override
	public void maximize() {
		super.maximize();
		redrawChart();
	}

	private void redrawChart() {
		if (chart != null) {
			chart.markForRedraw();
		}
	}

	@Override
	public void restore() {
		super.restore();
		redrawChart();
	}

	@Override
	public int getDefaultHeight() {
		return 350;
	}

	
	
}
