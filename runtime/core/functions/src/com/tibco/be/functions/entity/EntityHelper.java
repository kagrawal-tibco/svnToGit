package com.tibco.be.functions.entity;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * User: ssubrama
 * Date: Sep 1, 2004
 * Time: 8:47:53 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Entity",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.entity", value=false),
        synopsis = "Functions to create and modify instances of type Concept")
public class EntityHelper {

    @com.tibco.be.model.functions.BEFunction(
        name = "getEntityByExtId",
        synopsis = "Returns the Entity using <code>extId</code> as the external ID.",
        signature = "Entity getEntityByExtId(String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The external ID.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = "Instance with the external ID as <code>extId</code>.  Returns <code>null</code> if no\nsuch entity exists in Working Memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Entity using <code>extId</code> as the external ID.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Entity getEntityByExtId(String extId) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return (Entity) session.getObjectManager().getEntity(extId);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "deleteEntityByExtId",
        synopsis = "Retracts and deletes the entity using the <code>extId</code> as the external id.",
        signature = "void deleteEntityByExtId(String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The External ID of the entity.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retracts and deletes the entity using the <code>extId</code> as the external id.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void deleteEntityByExtId(String extId) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        Entity entity = (Entity) session.getObjectManager().getEntity(extId);
        if (entity != null) {
            session.retractObject(entity, true);
        }
    }
}
