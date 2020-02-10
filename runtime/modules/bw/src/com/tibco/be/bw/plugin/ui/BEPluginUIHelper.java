package com.tibco.be.bw.plugin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.ae.designerapi.AEResource;
import com.tibco.ae.designerapi.AEResourceUtils;
import com.tibco.ae.designerapi.DesignerDocument;
import com.tibco.ae.designerapi.DesignerError;
import com.tibco.ae.designerapi.DesignerResourceReference;
import com.tibco.ae.designerapi.DesignerResourceStore;
import com.tibco.ae.designerapi.PaletteHelper;
import com.tibco.ae.designerapi.forms.CheckBoxFormField;
import com.tibco.ae.designerapi.forms.ChoiceFormField;
import com.tibco.ae.designerapi.forms.ConfigForm;
import com.tibco.ae.designerapi.forms.ConfigFormField;
import com.tibco.ae.designerapi.forms.ReferenceURIFormField;
import com.tibco.ae.designerapi.forms.TextFormField;
import com.tibco.ae.designerapi.models.ResourceTypeTreeFilter;
import com.tibco.ae.designerapi.models.TreeFilter;
import com.tibco.ae.processapi.BWResource;
import com.tibco.ae.processapi.DesignerErrorsHelper;
import com.tibco.be.bw.plugin.BEBWUtils;
import com.tibco.be.bw.plugin.BEPluginConstants;
import com.tibco.be.util.Filter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.repo.Project;
import com.tibco.io.xml.XMLStreamReader;
import com.tibco.pe.errors.CommonCode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/*
 * User: apuneet
 * Date: Aug 14, 2004
 * Time: 9:10:29 AM
 */

public class BEPluginUIHelper {


    final static String SEPARATOR=".";
    final static String CONCEPTREF_VFILE_TYPE = "tibco.be.semantic.concept";
    final static String RSPREF_VFILE_TYPE = "ae.sharedresource.BERuleServiceProvider";
    final static String RULEFUNCTIONREF_VFILE_TYPE = "tibco.be.semantic.rulefunction";

    protected static final Pattern GLOBAL_VARIABLE_PATTERN = Pattern.compile("%%(.*?)%%");

    protected final static Filter DESTINATION_FILTER = new Filter() {
        public boolean accept(Object o) {
            return o instanceof Destination;
        }
    };


    protected final static Filter RULEFUNCTION_FILTER = new Filter() {
        public boolean accept(Object o) {
            return o instanceof RuleFunction;
        }
    };


    protected final static Filter SIMPLE_EVENT_FILTER = new Filter() {
        public boolean accept(Object o) {
            return (o instanceof Event)
                    && (Event.SIMPLE_EVENT == ((Event) o).getType());
        }
    };


    public BEPluginUIHelper () {
    }


    public String displayName(BWResource resource, String property) {
        return AEResourceUtils.getDisplayNameForProperty(resource.getResourceType()+ SEPARATOR +property);
    }


    public String displayName(BWResource resource,ExpandedName property) {
        return AEResourceUtils.getDisplayNameForProperty(resource.getResourceType()+ SEPARATOR +property.getLocalName());
    }


    public ConfigFormField newEventRefField (BWResource resource){
        final OntologyReferenceFormField refField = new OntologyReferenceFormField(
                BEPluginConstants.EVENTREF.getLocalName(),
                this.displayName(resource, BEPluginConstants.EVENTREF));
        refField.setFilter(SIMPLE_EVENT_FILTER);
        refField.setRequired(true);
        refField.setPickOnly(true);
        return refField;
    }


    public ConfigFormField newDestinationRefField (BWResource resource){
        final OntologyReferenceFormField refField = new OntologyReferenceFormField(
                BEPluginConstants.DESTINATIONREF.getLocalName(),
                this.displayName(resource, BEPluginConstants.DESTINATIONREF));
        refField.setFilter(DESTINATION_FILTER);
        refField.setRequired(false);
        refField.setPickOnly(true);
        return refField;
    }


    public ConfigFormField newEntityNSField (BWResource resource){
        TextFormField refNSField = new TextFormField(BEPluginConstants.ENTITYNS.getLocalName(), displayName(resource,BEPluginConstants.ENTITYNS));
        refNSField.setEnabled(false);
        refNSField.setVisible(false);
        return refNSField;
    }


    public ConfigFormField newEntityNameField (BWResource resource){
        TextFormField refNSField = new TextFormField(BEPluginConstants.ENTITYNAME.getLocalName(), displayName(resource,BEPluginConstants.ENTITYNAME));
        refNSField.setEnabled(false);
        refNSField.setVisible(false);
        return refNSField;
    }


    public ConfigFormField newOutputStyleField (BWResource resource){
        CheckBoxFormField isText = new CheckBoxFormField(BEPluginConstants.IS_TEXT.getLocalName(), displayName(resource,BEPluginConstants.IS_TEXT.getLocalName()));
        isText.setVisible(false);
        return isText;
    }


    public ConfigFormField newEncodingsList (BWResource resource){
        String[] fileEncodingList = XMLStreamReader.getSupportedEncodings();
        String [] encodingList = new String[fileEncodingList.length];

        for (int i = 0; i < fileEncodingList.length; i++) {
            encodingList[i] = fileEncodingList[i];
        }

        ChoiceFormField field=new ChoiceFormField(BEPluginConstants.ENCODING.getLocalName(), displayName(resource,BEPluginConstants.ENCODING),encodingList,true);
        field.setChoicesAndValues(encodingList, encodingList);
        field.setVisible(false);
        return field;
    }


    public ConfigFormField newConceptRefField (BWResource resource){
        ReferenceURIFormField refField = new ReferenceURIFormField(BEPluginConstants.CONCEPTREF.getLocalName(), displayName(resource,BEPluginConstants.CONCEPTREF));
        refField.addFilter(new ResourceTypeTreeFilter(CONCEPTREF_VFILE_TYPE));
        refField.setRequired(true);
        refField.setPickOnly(true);
        return refField;
    }


    void updateNsNmFields(String entityRef, BWResource resource) throws Exception{

        final BERuleServiceProviderSharedConfigUI rspRes = this.getRSPConfig(resource);
        if (null == rspRes) {
            resource.setValue(BEPluginConstants.ENTITYNS.getLocalName(), null);
            resource.setValue(BEPluginConstants.ENTITYNAME.getLocalName(), null);
            return;
        }


        final String[] nsNm = BEBWUtils.getNsNm(entityRef, rspRes.getProjectUrl());

        TextFormField nsField = null;
        TextFormField nameField = null;

        final ConfigForm configForm = PaletteHelper.getForm("Configuration", resource);
        if (configForm != null) {
            nsField = (TextFormField) configForm.getFieldForProperty(BEPluginConstants.ENTITYNS.getLocalName());
            nameField = (TextFormField) configForm.getFieldForProperty(BEPluginConstants.ENTITYNAME.getLocalName());
        }

        if (nsField != null) {
            nsField.setValue(nsNm[0]);
            nameField.setValue(nsNm[1]);
        } else {
            resource.setValue(BEPluginConstants.ENTITYNS.getLocalName(), nsNm[0]);
            resource.setValue(BEPluginConstants.ENTITYNAME.getLocalName(), nsNm[1]);
        }
    }


    boolean ruleFunctionRefChanged (String from, String to, BWResource resource) {
            try {
                if (from.equals(resource.getValue(BEPluginConstants.RULEFUNCTIONREF.getLocalName()))) {
                    resource.setValue(BEPluginConstants.RULEFUNCTIONREF.getLocalName(), to);
                    return true;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }


    public boolean ruleRSPRefChanged(String from, String to, BWResource resource) {
        try {
            if (from.equals(resource.getValue(BEPluginConstants.RSPREF.getLocalName()))) {
                resource.setValue(BEPluginConstants.RSPREF.getLocalName(), to);
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    void copyConceptFromOntology (String conceptRef,BWResource resource) throws Exception{
        if (conceptRef == null) {
            conceptRef = resource.getValue(BEPluginConstants.CONCEPTREF.getLocalName());
        }
        Ontology o= DesignerOntologyMap.getOntologyFor(resource.getDesignerDocument(),false);
        ConfigForm configForm = PaletteHelper.getForm("Configuration", resource);

        TextFormField entityNSField  = (TextFormField) configForm.getFieldForProperty(BEPluginConstants.ENTITYNS.getLocalName());
        TextFormField entityNameField  = (TextFormField) configForm.getFieldForProperty(BEPluginConstants.ENTITYNAME.getLocalName());

        if ((conceptRef != null) && (!conceptRef.trim().equals(""))) {
            Concept c = o.getConcept(conceptRef.substring(0,conceptRef.lastIndexOf('.')));
            String uri=c.getFullPath();

            if (entityNSField != null) {
                entityNSField.setValue(uri);
                entityNameField.setValue(c.getName());
            } else {
                resource.setValue(BEPluginConstants.ENTITYNS.getLocalName(),uri);
                resource.setValue(BEPluginConstants.ENTITYNAME.getLocalName(),c.getName());
            }
        } else {
            if (entityNSField  != null) {
                entityNSField .setValue(null);
                entityNameField.setValue(null);
            }  else {
                resource.setValue(BEPluginConstants.ENTITYNS.getLocalName(),null);
                resource.setValue(BEPluginConstants.ENTITYNAME.getLocalName(),null);
            }
        }
    }


    private DesignerResourceReference asRef(BWResource resource,String property) {
        DesignerResourceReference retVal = null;
        String uri = resource.getValue(property);
        if (uri != null && uri.equals("") == false) {
            retVal = new DesignerResourceReference(uri);
        }
        return retVal;
    }


    void enableEncodings (BWResource resource) {
        ConfigForm configForm = PaletteHelper.getForm("Configuration", resource);
        if (configForm != null) {
            CheckBoxFormField isText= (CheckBoxFormField) configForm.getFieldForProperty(BEPluginConstants.IS_TEXT.getLocalName());
            if (isText != null) {
                ChoiceFormField field =(ChoiceFormField) configForm.getFieldForProperty(BEPluginConstants.ENCODING.getLocalName());
                if (field != null) {
                    field.setVisible(isText.isSelected());
                }
            }
        }
    }


    void addEventReferences (BWResource resource,List references) {
        DesignerResourceReference ref = asRef(resource,BEPluginConstants.EVENTREF.getLocalName());
        if (null != ref) {
            references.add(ref);
        }
    }


    void addDestinationReference (BWResource resource, List references) {
        DesignerResourceReference ref = asRef(resource, BEPluginConstants.DESTINATIONREF.getLocalName());
        if (null != ref) {
            references.add(ref);
        }//if
    }//addDestinationReference

    void addConceptReferences (BWResource resource,List references) {
        DesignerResourceReference ref = asRef(resource,BEPluginConstants.CONCEPTREF.getLocalName());
        if (null != ref) {
            references.add(ref);
        }
    }


    void addRuleFunctionReference(BWResource resource, List references) {
        DesignerResourceReference ref = asRef(resource,BEPluginConstants.RULEFUNCTIONREF.getLocalName());
        if (null != ref) {
            references.add(ref);
        }
    }


    public void addRSPConfigReference(BWResource resource, List references) {
        final DesignerResourceReference ref = this.asRef(resource, BEPluginConstants.RSPREF.getLocalName());
        if (null != ref) {
        	references.add(ref);
        }//if
    }//addRSPConfigReference


    boolean eventRefChanged (String from, String to, BWResource resource) {
        try {
            if (from.equals(resource.getValue(BEPluginConstants.EVENTREF.getLocalName()))) {
                resource.setValue(BEPluginConstants.EVENTREF.getLocalName(), to);
                updateNsNmFields(to, resource);
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    boolean destinationRefChanged (String from, String to, BWResource resource) {
        try {
            final String propertyName = BEPluginConstants.DESTINATIONREF.getLocalName();
            if (from.equals(resource.getValue(propertyName))) {
                resource.setValue(propertyName, to);
                return true;
            }//if
        } catch (Exception e){
            e.printStackTrace();
        }//catch
        return false;
    }//destinationRefChanged


    public String getSubstitutedValue(AEResource aeresource, String originalValue) throws Exception {
        if (null == originalValue) {
            return null;
        }

        final DesignerResourceStore resourceStore = aeresource.getDesignerDocument().getResourceStore();

        final Matcher matcher = GLOBAL_VARIABLE_PATTERN.matcher(originalValue);
        final StringBuffer substitutedText = new StringBuffer();
        while (matcher.find()) {
            final String variableValue = resourceStore.getGlobalVar(matcher.group(1));
            matcher.appendReplacement(
                    substitutedText,
                    variableValue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
        }//while
        matcher.appendTail(substitutedText);

        return substitutedText.toString();
    }


    String getValueAsString(BWResource resource,ExpandedName property) {
        BWResource res= (BWResource) resource;
        XiNode configData=res.getActivityModel().getConfigurationXML();
        return XiChild.getString(configData,property );
    }


    boolean conceptRefChanged (BWResource resource,String from, String to) {
        try {
            if (from.equals(resource.getValue(BEPluginConstants.CONCEPTREF.getLocalName()))) {
                resource.setValue(BEPluginConstants.CONCEPTREF.getLocalName(),to);
                copyConceptFromOntology(to,resource);
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    boolean isNill(BWResource resource,String property) {
        Object v=resource.getValue(property);
        if (v == null) {
            return true;
        }
        if (v instanceof String) {
            if (((String)v).trim() == null) {
                return true;
            }
        }
        return false;
    }


    DesignerError [] validateEventRef (BWResource resource) {
        final List<DesignerError> errors = new ArrayList<DesignerError>();
        final String propName = BEPluginConstants.EVENTREF.getLocalName();
        if (this.isNill(resource, propName)) {
            errors.add(DesignerErrorsHelper.createDesignerError(resource,
                    CommonCode.CONFIGURATION_ERROR_EMPTY__SFLD, displayName(resource, propName)));
        } else {
            try {
                final BERuleServiceProviderSharedConfigUI rspRes = this.getRSPConfig(resource);
                final Project project = BEBWUtils.getProject(rspRes.getProjectUrl());
                if (null != project) {
                    final Entity entity = project.getOntology().getEntity(resource.getValue(propName));
                    if (!(entity instanceof Event)) {
                        errors.add(DesignerErrorsHelper.createDesignerError(resource,
                            CommonCode.CONFIGURATION_ERROR__SFLD_SVALTYPE,
                            this.displayName(resource, propName)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errors.toArray(new DesignerError[errors.size()]);
    }


    DesignerError [] validateConceptRef (BWResource resource) {
        ArrayList allErrors = new ArrayList();
        if (isNill(resource,BEPluginConstants.CONCEPTREF.getLocalName())) {
            allErrors.add(DesignerErrorsHelper.createDesignerError(resource, CommonCode.CONFIGURATION_ERROR_EMPTY__SFLD, displayName(resource,"conceptRef")));
        }
        return (DesignerError[])allErrors.toArray(new DesignerError[0]);
    }


    public ConfigFormField newRSPRefField (BWResource resource){
        ReferenceURIFormField refField = new ReferenceURIFormField(BEPluginConstants.RSPREF.getLocalName(), displayName(resource,BEPluginConstants.RSPREF));
        refField.addFilter(new ResourceTypeTreeFilter(RSPREF_VFILE_TYPE));
        //refField.addFilter(new EventFilter());
        refField.setRequired(true);
        refField.setPickOnly(true);
        return refField;
    }


    public ConfigFormField newRuleSessionRefField (BWResource resource){
        ReferenceURIFormField refField = new ReferenceURIFormField(BEPluginConstants.RULESESSIONREF.getLocalName(), displayName(resource,BEPluginConstants.RULESESSIONREF));
        refField.addFilter(new RuleSessionFilter(resource));
        //refField.addFilter(new EventFilter());
        refField.setRequired(true);
        refField.setPickOnly(true);
        return refField;
    }


    public ConfigFormField newRuleFunctionRefField (BWResource resource){
        final OntologyReferenceFormField refField = new OntologyReferenceFormField(
                BEPluginConstants.RULEFUNCTIONREF.getLocalName(),
                displayName(resource,BEPluginConstants.RULEFUNCTIONREF));
        refField.setFilter(RULEFUNCTION_FILTER);
        refField.setRequired(true);
        refField.setPickOnly(true);
        return refField;
    }


    public BERuleServiceProviderSharedConfigUI getRSPConfig(BWResource resource) {
        final String rspRef = resource.getValue(BEPluginConstants.RSPREF.getLocalName());
        if (null == rspRef) {
            return null;
        }
        return (BERuleServiceProviderSharedConfigUI)
                 AEResourceUtils.findRelativeResource(resource.getRoot(), rspRef);
    }


    public DesignerError[] validateRspConfigRef(BWResource resource) {
        final List<DesignerError> errors = new ArrayList<DesignerError>();
        if (this.isNill(resource, BEPluginConstants.RSPREF.getLocalName())) {
            errors.add(DesignerErrorsHelper.createDesignerError(resource,
                    CommonCode.CONFIGURATION_ERROR_EMPTY__SFLD,
                    this.displayName(resource, BEPluginConstants.RSPREF.getLocalName())));
        } else if (null == this.getRSPConfig(resource)) {
            errors.add(DesignerErrorsHelper.createDesignerError(resource,
                    CommonCode.CONFIGURATION_ERROR__SFLD_SVALTYPE,
                    this.displayName(resource, BEPluginConstants.RSPREF.getLocalName())));
        }
        return errors.toArray(new DesignerError[errors.size()]);
    }


    public DesignerError[] validateRuleFunctionRef(BWResource resource) {
        final List<DesignerError> errors = new ArrayList<DesignerError>();
        final String propName = BEPluginConstants.RULEFUNCTIONREF.getLocalName();

        if (this.isNill(resource, propName)) {
            errors.add(DesignerErrorsHelper.createDesignerError(resource,
                    CommonCode.CONFIGURATION_ERROR_EMPTY__SFLD, displayName(resource, propName)));

        } else {
            try {
                final BERuleServiceProviderSharedConfigUI rspRes = this.getRSPConfig(resource);
                final Project project = BEBWUtils.getProject(rspRes.getProjectUrl());
                if (null != project) {
                    final Entity entity = project.getOntology().getEntity(resource.getValue(propName));
                    if (!(entity instanceof RuleFunction)) {
                        errors.add(DesignerErrorsHelper.createDesignerError(resource,
                            CommonCode.CONFIGURATION_ERROR__SFLD_SVALTYPE,
                            this.displayName(resource, propName)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errors.toArray(new DesignerError[errors.size()]);
    }


    static class EventFilter implements TreeFilter {
        public boolean allowNode(Object o) {
            Entity entity = DesignerOntologyMap.getModelElement(o);
            if (entity instanceof Event) {
                Event e= (Event) o;
                // Allow only JMS and RV Channels
                if ((e.getChannelURI() !=  null) && (e.getChannelURI().trim().length() > 0)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }


    static class DestinationFilter implements TreeFilter {
        public boolean allowNode(Object o) {
            Entity e = DesignerOntologyMap.getModelElement(o);
            if (e instanceof Destination) {
                final Destination d = (Destination) e;
                // Allow only JMS and RV Channels? The above does not.
                final DriverConfig dc = d.getDriverConfig();
                if (null != dc) {
                    if (!dc.getDriverType().equals("Local")) {
                        return (null != dc.getChannel());
                    }
                }//dc
            }//if
            return false;
        }//allowNode
    }//class DestinationFilter


    static class RuleSessionFilter implements TreeFilter {

        BWResource resource;

        public RuleSessionFilter(BWResource resource){
            this.resource = resource;
        }

        public boolean allowNode(Object object) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }


    //TODO - Implement this for 4.0
    static class DesignerOntologyMap {

        public static Ontology getOntologyFor(DesignerDocument document, boolean b) {
            return null;  //To change body of created methods use File | Settings | File Templates.
        }

        public static Entity getModelElement(Object o) {
            return null;
        }
    }


}




