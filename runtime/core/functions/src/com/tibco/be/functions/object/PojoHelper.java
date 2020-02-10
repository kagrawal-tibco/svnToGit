package com.tibco.be.functions.object;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 20, 2007
 * Time: 10:27:00 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Pojo",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.pojo", value=false),
        synopsis = "Functions to create and modify instances of type Pojo")
public class PojoHelper {

    @com.tibco.be.model.functions.BEFunction(
        name = "getEntityByExtId",
        synopsis = "Returns the Entity using <code>extId</code> as the external ID.",
        signature = "Entity getEntityByExtId(String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "The external ID.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = "Instance with the external ID as <code>extId</code>.  Returns <code>null</code> if no\nsuch entity exists in Working Memory."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Entity using <code>extId</code> as the external ID.",
        cautions = "none",
        fndomain = {ACTION},
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
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retracts and deletes the entity using the <code>extId</code> as the external id.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void deleteEntityByExtId(String extId) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        Entity entity = (Entity) session.getObjectManager().getEntity(extId);
        if (entity != null) {
            session.retractObject(entity, true);

        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPojo",
        synopsis = "Returns a Pojo - Plain Old Java Object instance  using <code>obj</code> as the key.\n*                   Programmers if choose to define their own hashcode,\nthen should implement the equals method also. Care must be taken when implementing the equals methods, to see that\nequals matches not only the Object of its type, but also the key type(s) used in the hashCode routine.\nFor example if the Pojo's hashcode routine for Transaction Class was implemented like below\n<code>\nclass Transaction {\n...\n...\n...\nint hashCode() {\nreturn ((Long)txnId).hashCode);\n}\n</code>\nThe equals method must also implement something like this\n<code>\nboolean equals(Object other) {\nif (other instanceof Long) {  //Types passed in the getPojo(Object),\nreturn this.txnId == ((Long)other).longValue;\n}\nelse if (other instanceof Transaction) {\nreturn this.txnId == ((Transaction).txnId;\n}\n}\n</code>",
        signature = "Object getPojo(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object whose hashcode will be used to search in WorkingMemory.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Instance .  Returns <code>null</code> if no such Object exists in Working Memory."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getPojo(Object obj) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return  session.getObjectManager().getObject(obj);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "deletePojo",
        synopsis = "Delete the object that is a Pojo instance in the Working Memory",
        signature = "void deletePojo(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "The object whose hashcode will be used and deleted if such Object exists in Working Memory.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Object.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void deletePojo(Object obj) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        Object o = session.getObjectManager().getObject(obj);
        if (o != null) {
            session.retractObject(o, true);

        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "assertPojo",
        synopsis = "Assert the Pojo to WorkingMemory for rule evaluation.",
        signature = "Object assertPojo(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Returns", desc = "the Object that got asserted, and null if it failed to assert."),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Object.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object assertPojo(Object obj) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        try {
            session.assertObject(obj,true);
            return obj;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "modifiedPojo",
        synopsis = "Instructs the Working Memory that the Pojo is modified and that the conditions which depend on the Pojo be reevaluated",
        signature = "void modifiedPojo(Object obj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "obj", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Object.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void modifiedPojo(Object obj) {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        try {
            session.getWorkingMemory().modifyObject(obj, true, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
