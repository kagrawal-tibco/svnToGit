package com.tibco.cep.studio.wizard.as.internal.utils;

import static com.tibco.cep.designtime.core.model.PROPERTY_TYPES.STRING;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DEFAULT_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_ROLE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_FILTER;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_NAME;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_SERIALIZER;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_SPACE_NAME;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_TIME_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_EVENT_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_EXPIRE_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_PUT_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_TAKE_EVENT;
import static com.tibco.cep.studio.wizard.as.ASConstants._ADDITIONAL_MANAGEMENT_PROPERTY_NAMES;
import static com.tibco.cep.studio.wizard.as.ASConstants._NAME_SUFFIX_DESTINATION;
import static com.tibco.cep.studio.wizard.as.ASConstants._NAME_SUFFIX_SIMPLE_EVENT;
import static com.tibco.cep.studio.wizard.as.commons.utils.StringUtils.unCapitalize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Destination;

public class ASUtils {

    public static String populateSimpleEventName(String prefix) {
        return prefix + _NAME_SUFFIX_SIMPLE_EVENT;
    }

    public static String populateDestinationName(String prefix) {
        return prefix + _NAME_SUFFIX_DESTINATION;
    }

    public static List<Object[]> getDestinationUISchemas() {
        Object[][] uiSchema = {
                // {Key, LabelName, PropertyName, AdditionalProperty, PropertyType, NeedNonNullControlDecorator, Enabled, UIType(List.class means Combo), InitData, ModelToUIConversionMethod(0:original, 1:CapitalToUpperCase), NeedDataBinding, DisplayName}
                { K_AS_DEST_PROP_NAME, Messages.getString("ASUtils.label_name"), unCapitalize(K_AS_DEST_PROP_NAME), false, String.class, true, true, String.class, "", 0, true, Messages.getString("ASUtils.display_name") }, //$NON-NLS-1$ //$NON-NLS-2$
                { K_AS_DEST_PROP_DEFAULT_EVENT, Messages.getString("ASUtils.label_default_event"), "eventURI", false, String.class, false, false, String.class, "", 0, true, Messages.getString("ASUtils.display_default_event")  }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_AS_DEST_PROP_SERIALIZER, Messages.getString("ASUtils.label_serializer"), "serializerDeserializerClass", false, String.class, false, false, String.class, "", 0, false, Messages.getString("ASUtils.display_serializer")  }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_AS_DEST_PROP_SPACE_NAME, Messages.getString("ASUtils.label_space_name"), unCapitalize(K_AS_DEST_PROP_SPACE_NAME), true, String.class, false, false, String.class, "", 0, false, Messages.getString("ASUtils.display_space_name") }, //$NON-NLS-1$ //$NON-NLS-2$
                { K_AS_DEST_PROP_DISTRIBUTION_ROLE, Messages.getString("ASUtils.label_dist_role"), unCapitalize(K_AS_DEST_PROP_DISTRIBUTION_ROLE), true, String.class, false, true, List.class, new String[]{Messages.getString("ASUtils.dist_role_leech"), Messages.getString("ASUtils.dist_role_seeder")}, 1, true, Messages.getString("ASUtils.display_dist_role") }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_AS_DEST_PROP_FILTER, Messages.getString("ASUtils.label_filter"), unCapitalize(K_AS_DEST_PROP_FILTER), true, String.class, false, true, String.class, "", 0, true, Messages.getString("ASUtils.display_filter") }, //$NON-NLS-1$ //$NON-NLS-2$
                { K_AS_DEST_PROP_CONSUMPTION_MODE, Messages.getString("ASUtils.label_consum_mode"), unCapitalize(K_AS_DEST_PROP_CONSUMPTION_MODE), true, String.class, false, true, List.class, new String[]{Messages.getString("ASUtils.consum_mode_evt_lsnr"), Messages.getString("ASUtils.consum_mode_entry_browser")}, 0, true, Messages.getString("ASUtils.display_consum_mode")  }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_AS_DEST_PROP_BROWSER_TYPE, Messages.getString("ASUtils.label_browser_type"), unCapitalize(K_AS_DEST_PROP_BROWSER_TYPE), true, String.class, false, true, List.class, new String[]{Messages.getString("ASUtils.browser_type_get"), Messages.getString("ASUtils.browser_type_take"), Messages.getString("ASUtils.browser_type_lock")}, 1, true, Messages.getString("ASUtils.display_browser_type")  }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_AS_DEST_PROP_DISTRIBUTION_SCOPE, Messages.getString("ASUtils.label_dist_scope"), unCapitalize(K_AS_DEST_PROP_DISTRIBUTION_SCOPE), true, String.class, false, true, List.class, new String[]{Messages.getString("ASUtils.display_dist_scope_type_all"), Messages.getString("ASUtils.display_dist_scope_type_seeded")}, 0, true, Messages.getString("ASUtils.display_dist_scope")  }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_AS_DEST_PROP_TIME_SCOPE, Messages.getString("ASUtils.label_timescope"), unCapitalize(K_AS_DEST_PROP_TIME_SCOPE), true, String.class, false, true, List.class, new String[]{Messages.getString("ASUtils.display_timescope_type_all"), Messages.getString("ASUtils.display_timescope_type_new"),Messages.getString("ASUtils.display_timescope_type_snapshot"),Messages.getString("ASUtils.display_timescope_type_newevents")}, 0, true, Messages.getString("ASUtils.display_timescope")  }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                { K_V_AS_DEST_PROP_PUT_EVENT, Messages.getString("ASUtils.evt_lsnr_put_evt"), unCapitalize(K_V_AS_DEST_PROP_PUT_EVENT), true, Boolean.class, false, true, Boolean.class, false, 0, true, Messages.getString("ASUtils.display_evt_lsnr_put_evt")  }, //$NON-NLS-1$
                { K_V_AS_DEST_PROP_TAKE_EVENT, Messages.getString("ASUtils.evt_lsnr_take_evt"), unCapitalize(K_V_AS_DEST_PROP_TAKE_EVENT), true, Boolean.class, false, true, Boolean.class, false, 0, true, Messages.getString("ASUtils.display_evt_lsnr_take_evt")  }, //$NON-NLS-1$
                { K_V_AS_DEST_PROP_EXPIRE_EVENT, Messages.getString("ASUtils.evt_lsnr_expire_evt"), unCapitalize(K_V_AS_DEST_PROP_EXPIRE_EVENT), true, Boolean.class, false, true, Boolean.class, false, 0, true, Messages.getString("ASUtils.display_evt_lsnr_expire_evt")  } }; //$NON-NLS-1$
        return Arrays.asList(uiSchema);
    }

    public static List<Object[]> getSimpleEventManagementFieldProperties() {
        Object[][] manageFieldProps = {
            {K_BE_EVENT_PROP_CONSUMPTION_MODE, STRING},
            {K_BE_EVENT_PROP_BROWSER_TYPE, STRING},
            {K_BE_EVENT_PROP_EVENT_TYPE, STRING}
        };
        return Arrays.asList(manageFieldProps);
    }

    public static Object[] findDestinationUISchemaByPropertyKey(List<Object[]> uiSchemas, String propKey) {
        Object[] found = null;
        for (Object[] uiSchema : uiSchemas) {
            String uiSchemaPropKey = getDestinationUIUnitPropertyKey(uiSchema);
            if (propKey.equals(uiSchemaPropKey)) {
                found = uiSchema;
                break;
            }
        }
        return found;
    }

    public static String getDestinationUIUnitPropertyKey(Object[] uiSchema) {
        return (String) uiSchema[0];
    }

    public static String getDestinationUIUnitPropertyLabelName(Object[] uiSchema) {
        return (String) uiSchema[1];
    }

    public static String getDestinationUIUnitPropertyName(Object[] uiSchema) {
        return (String) uiSchema[2];
    }

    public static boolean isDestinationUIUnitAdditionalProperty(Object[] uiSchema) {
        return (Boolean) uiSchema[3];
    }

    public static Class<?> getDestinationUIUnitPropertyType(Object[] uiSchema) {
        return (Class<?>) uiSchema[4];
    }

    public static boolean isDestinationUIUnitNeedNonNullControlDecorator(Object[] uiSchema) {
        return (Boolean) uiSchema[5];
    }

    public static boolean isDestinationUIUnitEnabeld(Object[] uiSchema) {
        return (Boolean) uiSchema[6];
    }

    public static Class<?> getDestinationUIUnitUIType(Object[] uiSchema) {
        return (Class<?>) uiSchema[7];
    }

    public static Object getDestinationUIUnitUIInitialValue(Object[] uiSchema) {
        return uiSchema[8];
    }

    public static int getDestinationUIUnitModelToUIConversionMethod(Object[] uiSchema) {
        return (Integer) uiSchema[9];
    }

    public static String getDestinationUIUnitPropertyDisplayName(Object[] uiSchema) {
        return (String) uiSchema[11];
    }

    public static boolean isNeedDataBinding(Object[] uiSchema) {
        return (Boolean) uiSchema[10];
    }

    public static Label getDestinationUIUnitLabel(Object[] uiUnit) {
        return (Label) uiUnit[0];
    }

    public static Control getDestinationUIUnitValueControl(Object[] uiUnit) {
        return (Control) uiUnit[1];
    }

    public static String getDestinationAdditionalPropertyValue(Destination destination, String propName) {
        String value = null;

        EList<Entity> props = destination.getProperties().getProperties();
        for (Entity entity : props) {
            if (entity instanceof SimpleProperty) {
                SimpleProperty sp = (SimpleProperty) entity;
                if (propName.equalsIgnoreCase(sp.getName())) {
                    value = sp.getValue();
                    break;
                }
            }
        }

        return value;
    }

    public static SimpleProperty getDestinationAdditionalProperty(Destination destination, String propName) {
        SimpleProperty value = null;

        EList<Entity> props = destination.getProperties().getProperties();
        for (Entity entity : props) {
            if (entity instanceof SimpleProperty) {
                SimpleProperty sp = (SimpleProperty) entity;
                if (propName.equalsIgnoreCase(sp.getName())) {
                    value = sp;
                    break;
                }
            }
        }

        return value;
    }

    public static List<Object[]> convertSimpleEventProperties(SimpleEvent event) {
        List<Object[]> result = new ArrayList<Object[]>();
        EList<PropertyDefinition> propDefs = event.getProperties();
        for (PropertyDefinition propDef : propDefs) {
            String name = propDef.getName();
            String type = propDef.getType().toString();
            // AdditionalManagementAttribute, Name, Type
            result.add(new Object[] { false, name, PROPERTY_TYPES.get(type) });
        }
        for (String managementPropName : _ADDITIONAL_MANAGEMENT_PROPERTY_NAMES) {
            result.add(new Object[] { true, managementPropName, PROPERTY_TYPES.STRING });
        }
        return result;
    }

    public static Object[] checkExistingEntity(Entity target, EList<? extends Entity> entities) {
        Object[] results = {false, null};
        String targetName = target.getName();
        for (Entity entity : entities) {
            if (entity.getName().equals(targetName)) {
                results[0] = true;
                results[1] = entity;
            }
        }
        return results;
    }

}
