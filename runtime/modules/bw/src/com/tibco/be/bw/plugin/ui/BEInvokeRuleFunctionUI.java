package com.tibco.be.bw.plugin.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tibco.ae.designerapi.DesignerDocument;
import com.tibco.ae.designerapi.DesignerError;
import com.tibco.ae.designerapi.PaletteHelper;
import com.tibco.ae.designerapi.forms.ConfigForm;
import com.tibco.ae.designerapi.forms.FieldChangeEvent;
import com.tibco.ae.designerapi.forms.FieldChangeListener;
import com.tibco.ae.designerapi.ui.NamedView;
import com.tibco.ae.processapi.BWActivityResource;
import com.tibco.be.bw.plugin.BEBWUtils;
import com.tibco.be.bw.plugin.BEPluginConstants;
import com.tibco.cep.studio.core.repo.emf.EMFProject;

/*
 * User: ssubrama
 * Date: Aug 13, 2007
 * Time: 2:05:43 PM
 */

public class BEInvokeRuleFunctionUI extends BWActivityResource implements FieldChangeListener {


    public static final String TYPE = "ae.activities.BEInvokeRuleFunction";

    protected BEPluginUIHelper uiHelper;
    private DesignerDocument m_doc;


    public String getResourceType() {
        return TYPE;
    }


    public BEInvokeRuleFunctionUI() {
        this(true);
    }


    public BEInvokeRuleFunctionUI(boolean b) {
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

        final OntologyReferenceFormField ruleField = (OntologyReferenceFormField)
                configForm.getFieldForProperty(BEPluginConstants.RULEFUNCTIONREF.localName);

        final BERuleServiceProviderSharedConfigUI rspRes = this.uiHelper.getRSPConfig(this);
        
        if (null == rspRes) {
            ruleField.setProject(null);
        } else {
            final String url = this.uiHelper.getSubstitutedValue(this, rspRes.getProjectUrl());
            final EMFProject project = BEBWUtils.getProject(url);
            ruleField.setProject(project);
        }
    }


    public void buildConfigurationForm(ConfigForm form, DesignerDocument doc) {
        super.buildConfigurationForm(form, doc);
        form.addField(uiHelper.newRSPRefField(this));
        //form.addField(uiHelper.newRuleSessionRefField(this));
        form.addField(uiHelper.newRuleFunctionRefField(this));
        form.addFieldChangeListener(this);
     }


    public NamedView[] createConfigViews(DesignerDocument doc)  {
        m_doc=doc;
        addFieldChangeListener();
        return super.createConfigViews(doc);
    }


    public List getHiddenReferences() {
        List refs= super.getHiddenReferences();
        if (refs == null) {
            refs = new LinkedList();
        }
//        uiHelper.addRuleFunctionReference(this,refs);
        uiHelper.addRSPConfigReference(this, refs);
        return refs;
    }


    public boolean referenceChanged(String from, String to) {
        return this.uiHelper.ruleRSPRefChanged(from, to, this)
//                || this.uiHelper.ruleFunctionRefChanged(from, to, this)
                || super.referenceChanged(from, to);
    }


    public DesignerError[] validateForDeployment() {
        final List<DesignerError> errors = new ArrayList<DesignerError>();
        errors.addAll(Arrays.asList(this.uiHelper.validateRspConfigRef(this)));
        errors.addAll(Arrays.asList(this.uiHelper.validateRuleFunctionRef(this)));
        return errors.toArray(new DesignerError[errors.size()]);
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


}
