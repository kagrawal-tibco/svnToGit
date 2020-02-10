package com.tibco.be.bw.plugin.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tibco.ae.designerapi.AEResource;
import com.tibco.ae.designerapi.DesignerDocument;
import com.tibco.ae.designerapi.DesignerError;
import com.tibco.ae.designerapi.PaletteHelper;
import com.tibco.ae.designerapi.forms.ConfigForm;
import com.tibco.ae.designerapi.forms.FieldChangeEvent;
import com.tibco.ae.designerapi.forms.FieldChangeListener;
import com.tibco.ae.designerapi.ui.NamedView;
import com.tibco.ae.processapi.BasicEventSourceResource;
import com.tibco.be.bw.plugin.BEBWUtils;
import com.tibco.be.bw.plugin.BEPluginConstants;
import com.tibco.cep.studio.core.repo.emf.EMFProject;

/*
 * User: apuneet
 * Date: Aug 14, 2004
 * Time: 11:44:29 AM
 */

public class BEReceiveEventUI extends BasicEventSourceResource implements FieldChangeListener{


    public static final String TYPE = "ae.activities.BEReceiveEvent";

    protected BEPluginUIHelper uiHelper;


    public String getResourceType() {
        return TYPE;
    }


    public BEReceiveEventUI() {
        super();
        uiHelper = new BEPluginUIHelper();
    }


    public BEReceiveEventUI(boolean b) {
        super(b);
        uiHelper = new BEPluginUIHelper();
    }


    public void afterInspect(DesignerDocument doc) {
        super.afterInspect(doc);
        try {
            this.syncProjectFromRspConfig();
        } catch (Exception e) {
            e.printStackTrace(); //TODO: Display sensible error
        }
    }


    public void fieldChanged(FieldChangeEvent event) {
        try {
            if (event.getPropertyName().equals(BEPluginConstants.RSPREF.getLocalName())) {
                this.syncProjectFromRspConfig();
            } else if (event.getPropertyName().equals(BEPluginConstants.EVENTREF.getLocalName())) {
                this.uiHelper.updateNsNmFields((String) event.getNewValue(), this);
            } else if (event.getPropertyName().equals(BEPluginConstants.IS_TEXT.getLocalName())) {
                this.uiHelper.enableEncodings(this);
            }
        } catch (Exception e) {
            e.printStackTrace(); //TODO: Display sensible error
        }
    }


    private void syncProjectFromRspConfig() throws Exception {
        final ConfigForm configForm = PaletteHelper.getForm("Configuration", this);
        if (null == configForm) {
            return;
        }

        final OntologyReferenceFormField eventField = (OntologyReferenceFormField)
                configForm.getFieldForProperty(BEPluginConstants.EVENTREF.localName);
        final OntologyReferenceFormField destnField = (OntologyReferenceFormField)
                configForm.getFieldForProperty(BEPluginConstants.DESTINATIONREF.localName);

        final BERuleServiceProviderSharedConfigUI rspRes = this.uiHelper.getRSPConfig(this);

        if (null == rspRes) {
            eventField.setProject(null);
            destnField.setProject(null);
        } else {
            final String url = this.uiHelper.getSubstitutedValue(this, rspRes.getProjectUrl());
            final EMFProject project = BEBWUtils.getProject(url);
            eventField.setProject(project);
            destnField.setProject(project);
        }
   }


    public void buildConfigurationForm(ConfigForm form, DesignerDocument doc) {
        super.buildConfigurationForm(form, doc);
        form.addField(this.uiHelper.newRSPRefField(this));
        form.addField(uiHelper.newEventRefField(this));
        form.addField(uiHelper.newDestinationRefField(this));
        form.addField(uiHelper.newEntityNSField(this));
        form.addField(uiHelper.newEntityNameField(this));
        form.addField(uiHelper.newOutputStyleField(this));
      //form.addField(uiHelper.newEncodingsList());
        form.addFieldChangeListener(this);
     }//buildConfigurationForm


    public NamedView[] createConfigViews(DesignerDocument doc) {
        addFieldChangeListener();
        return super.createConfigViews(doc);
    }//createConfigViews


    public List getHiddenReferences() {
        List refs = super.getHiddenReferences();
        if (refs == null) {
            refs = new LinkedList();
        }
        this.uiHelper.addRSPConfigReference(this, refs);
//        uiHelper.addDestinationReference(this, refs);
//        uiHelper.addEventReferences(this, refs);
        return refs;
    }//getHiddenReferences


    public boolean referenceChanged(String from, String to) {
        return this.uiHelper.ruleRSPRefChanged(from, to, this)
//                || this.uiHelper.eventRefChanged(from, to, this)
//                || this.uiHelper.destinationRefChanged(from, to, this)
                || super.referenceChanged(from, to);
    }//referenceChanged


    public DesignerError[] validateForDeployment() {
        final List<DesignerError> errors = new ArrayList<DesignerError>();
        errors.addAll(Arrays.asList(this.uiHelper.validateRspConfigRef(this)));
        errors.addAll(Arrays.asList(this.uiHelper.validateEventRef(this)));
        return errors.toArray(new DesignerError[errors.size()]);
    }


    public boolean canBeProcessStarter() {
        return true;
    }


    public boolean canBeSignalIn() {
        return false;
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


    @Override
    public AEResource[] getExportPartners() {
        final AEResource[] resources = super.getExportPartners();
        final BERuleServiceProviderSharedConfigUI rspC = this.uiHelper.getRSPConfig(this);
        if (null == rspC) {
            return resources;
        } else {
            final AEResource[] moreResources = Arrays.copyOf(resources, resources.length + 1);
            moreResources[resources.length] = rspC;
            return moreResources;
        }
    }


    @Override
    public AEResource[] getPartnerResources() {
        final AEResource[] resources = super.getPartnerResources();
        final BERuleServiceProviderSharedConfigUI runtimeConfig = this.uiHelper.getRSPConfig(this);
        if (null == runtimeConfig) {
            return resources;
        } else {
            final AEResource[] moreResources = Arrays.copyOf(resources, resources.length + 1);
            moreResources[resources.length] = runtimeConfig;
            return moreResources;
        }
    }


}

