package com.tibco.cep.webstudio.client.problems;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.Page;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * 
 * @author sasahoo
 */
public class ProblemsDataSource extends DataSource {
	
	private static DTMessages dtMsgBundle = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	
	public ProblemsDataSource() {
		super();
		DataSourceImageField nofield = new DataSourceImageField("#", "#");
		nofield.setImageURLPrefix(Page.getAppImgDir() + "icons/16/");
		nofield.setAttribute("width", "3%");
		nofield.setCanFilter(false);
		
		DataSourceTextField descfield = new DataSourceTextField("Description", dtMsgBundle.dt_problems_gridHeader_description());
		descfield.setAttribute("width", "25%");
		descfield.setPluralTitle(dtMsgBundle.dt_problems_items());
		descfield.setSummaryFunction(SummaryFunctionType.COUNT);
		
		DataSourceTextField resourcefield = new DataSourceTextField("Resource", dtMsgBundle.dt_problems_gridHeader_resource());
		resourcefield.setAttribute("width", "10%");
		
		DataSourceTextField projectfield = new DataSourceTextField("Project", dtMsgBundle.dt_problems_gridHeader_project());
		projectfield.setAttribute("width", "10%");
        
		DataSourceTextField pathfield = new DataSourceTextField("Path", dtMsgBundle.dt_problems_gridHeader_path());
		pathfield.setAttribute("width", "20%");
        
        DataSourceTextField locfield = new DataSourceTextField("Location", dtMsgBundle.dt_problems_gridHeader_location());
        locfield.setAttribute("width", "17%");
        
        DataSourceTextField typefield = new DataSourceTextField("Type", dtMsgBundle.dt_problems_gridHeader_type());
        typefield.setAttribute("width", "8%");
        
		DataSourceTextField problemTypefield = new DataSourceTextField("ProblemType", dtMsgBundle.dt_problems_gridHeader_problemType());
		problemTypefield.setAttribute("width", "7%");
		
		setFields(nofield, descfield, projectfield, resourcefield, pathfield, locfield, typefield, problemTypefield);
		setCanMultiSort(true);
		setClientOnly(true);
	}
}
