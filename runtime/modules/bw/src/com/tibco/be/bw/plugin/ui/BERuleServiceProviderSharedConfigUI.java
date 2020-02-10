package com.tibco.be.bw.plugin.ui;

import java.util.ArrayList;
import java.util.List;

import com.tibco.ae.designerapi.AEResourceUtils;
import com.tibco.ae.designerapi.DesignerDocument;
import com.tibco.ae.designerapi.DesignerError;
import com.tibco.ae.designerapi.PaletteHelper;
import com.tibco.ae.designerapi.forms.ConfigForm;
import com.tibco.ae.designerapi.forms.ConfigFormField;
import com.tibco.ae.designerapi.forms.FieldChangeEvent;
import com.tibco.ae.designerapi.forms.FieldChangeListener;
import com.tibco.ae.designerapi.forms.FileFormField;
import com.tibco.ae.designerapi.forms.TextFormField;
import com.tibco.ae.designerapi.ui.NamedView;
import com.tibco.ae.processapi.BWResource;
import com.tibco.ae.processapi.BWSharedResource;
import com.tibco.ae.processapi.DesignerErrorsHelper;
import com.tibco.be.bw.plugin.BEPluginConstants;
import com.tibco.pe.errors.CommonCode;

/*
 * User: ssubrama
 * Date: Aug 14, 2007
 * Time: 3:16:48 PM
 */

public class BERuleServiceProviderSharedConfigUI extends BWSharedResource implements FieldChangeListener {


    // GUI components to provide database connection, shared among all activities
    public static final String TYPE = "ae.sharedresource.BERuleServiceProvider";
    public static final String PROP_ENGINE_NAME_DISPLAY = AEResourceUtils.getDisplayNameForProperty(TYPE + "." + BEPluginConstants.RSP_ELEMENT_ENGINE_NAME);
    public static final String PROP_START_FLAG_DISPLAY = AEResourceUtils.getDisplayNameForProperty(TYPE + "." + BEPluginConstants.RSP_ELEMENT_START_FLAG);
    public static final String PROP_REPOURL_DISPLAY = AEResourceUtils.getDisplayNameForProperty(TYPE + "." + BEPluginConstants.RSP_ELEMENT_REPOURL);
    public static final String PROP_CDD_DISPLAY = AEResourceUtils.getDisplayNameForProperty(
            TYPE + "." + BEPluginConstants.RSP_ELEMENT_CDD);
    public static final String PROP_PUID_DISPLAY = AEResourceUtils.getDisplayNameForProperty(
            TYPE + "." + BEPluginConstants.RSP_ELEMENT_PUID);

    private BEPluginUIHelper uiHelper;

    //public static final String PROP_TRAPATH_DISPLAY = AEResourceUtils.getDisplayNameForProperty(TYPE + "." + BEPluginConstants.RSP_ELEMENT_TRAPATH);

    
    public BERuleServiceProviderSharedConfigUI() {
        super();
        this.uiHelper = new BEPluginUIHelper();
    }


    public BERuleServiceProviderSharedConfigUI(boolean bCreateModel) {
        super(bCreateModel);
        this.uiHelper = new BEPluginUIHelper();
    }


    @Override
    protected void addProperties(ArrayList arrayList) {
        super.addProperties(arrayList);
        arrayList.add(BEPluginConstants.RSP_ELEMENT_REPOURL);
        arrayList.add(BEPluginConstants.RSP_ELEMENT_CDD);
        arrayList.add(BEPluginConstants.RSP_ELEMENT_PUID);
    }


    @Override
    protected void initModel() throws Exception {
        super.initModel();
        this.setValue(BEPluginConstants.RSP_ELEMENT_REPOURL, "");
        this.setValue(BEPluginConstants.RSP_ELEMENT_CDD, "");
        this.setValue(BEPluginConstants.RSP_ELEMENT_PUID, "");
    }


    public String getResourceType() {
        return TYPE;
    }


    public void buildConfigurationForm(ConfigForm configForm, DesignerDocument designerDocument) {
        super.buildConfigurationForm(configForm, designerDocument);    //To change body of overridden methods use File | Settings | File Templates.

        ConfigFormField fld;

//        fld = new TextFormField(BEPluginConstants.RSP_ELEMENT_ENGINE_NAME, PROP_ENGINE_NAME_DISPLAY);
//        configForm.addField(fld);
//        fld.setRequired(true);
//        fld.setEnabled(true);
//
//        fld = new CheckBoxFormField(BEPluginConstants.RSP_ELEMENT_START_FLAG, PROP_START_FLAG_DISPLAY,false );
//        configForm.addField(fld);


        fld = new FileFormField(BEPluginConstants.RSP_ELEMENT_REPOURL, PROP_REPOURL_DISPLAY, new String[] {"ear"});
        configForm.addField(fld);
        fld.setRequired(true);
        //fld.setEnabled(false);

        fld = new FileFormField(BEPluginConstants.RSP_ELEMENT_CDD, PROP_CDD_DISPLAY, new String[]{"cdd"});
        configForm.addField(fld);
        fld.setRequired(true);

        fld = new TextFormField(BEPluginConstants.RSP_ELEMENT_PUID, PROP_PUID_DISPLAY);
        configForm.addField(fld);
        fld.setRequired(true);

        //fld = new TextFormField(BEPluginConstants.RSP_ELEMENT_TRAPATH, PROP_TRAPATH_DISPLAY);
        //configForm.addField(fld);
        //fld.setRequired(false);
        //fld.setEnabled(false);
        configForm.addFieldChangeListener(this);

    }

    
    public NamedView[] createConfigViews(DesignerDocument doc)  {
        addFieldChangeListener();
        return super.createConfigViews(doc);
    }


    public void fieldChanged(FieldChangeEvent event) {
        //todo: handle change in URL
    }


    public Object getField(String name) {
        String viewName = BWResource.CONFIGURATION_VIEW_NAME;
        return PaletteHelper.getFormField(viewName, name, this);
    }


    public void afterClose(DesignerDocument designerDocument) {
        removeFieldChangeListener();
        super.afterClose(designerDocument);
    }


    public void wasDeleted(DesignerDocument designerDocument) {
        removeFieldChangeListener();
        super.wasDeleted(designerDocument);
    }


    void removeFieldChangeListener() {
        ConfigForm configForm = PaletteHelper.getForm("Configuration", this);
        if (configForm != null) {
            configForm.removeFieldChangeListener(this);
        }
    }


    void addFieldChangeListener() {
        ConfigForm configForm = PaletteHelper.getForm("Configuration", this);
        if (configForm != null) {
            configForm.removeFieldChangeListener(this);
            configForm.addFieldChangeListener(this);
        }
    }


    public DesignerError[] validateForDeployment() {
        final List<DesignerError> errors = new ArrayList<DesignerError>();

        if ((null == this.getProjectUrl()) || this.getProjectUrl().isEmpty()) {
            errors.add(DesignerErrorsHelper.createDesignerError(this,
                CommonCode.CONFIGURATION_ERROR__SFLD, PROP_REPOURL_DISPLAY));
        }

        if ((null == this.getCddUrl()) || this.getCddUrl().isEmpty()) {
            errors.add(DesignerErrorsHelper.createDesignerError(this,
                CommonCode.CONFIGURATION_ERROR__SFLD, PROP_CDD_DISPLAY));
        }

        if ((null == this.getProcessingUnitId()) || this.getProcessingUnitId().isEmpty()) {
            errors.add(DesignerErrorsHelper.createDesignerError(this,
                CommonCode.CONFIGURATION_ERROR__SFLD, PROP_PUID_DISPLAY));
        }

        return errors.toArray(new DesignerError[errors.size()]);
    }


    public String getProjectUrl() {
        return this.getValue(BEPluginConstants.RSP_ELEMENT_REPOURL);
    }


    public String getCddUrl() {
        return this.getValue(BEPluginConstants.RSP_ELEMENT_CDD);
    }


    public String getProcessingUnitId() {
        return this.getValue(BEPluginConstants.RSP_ELEMENT_PUID);
    }
}
