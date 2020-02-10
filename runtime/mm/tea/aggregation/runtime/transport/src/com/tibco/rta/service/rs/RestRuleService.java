package com.tibco.rta.service.rs;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.service.rules.RuleService;
import org.xml.sax.InputSource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/2/14
 * Time: 11:00 AM
 *
 */
@Path(("/service/rules"))
public class RestRuleService extends AbstractRestService {

    @GET
    @Path("/show/{ruleName}")
    @Produces(MediaType.TEXT_XML)
    public Response getRuleDef(@PathParam("ruleName") String ruleName) throws Exception {
        if (ruleName == null || ruleName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        RuleService ruleService = ServiceProviderManager.getInstance().getRuleService();
        RuleDef ruleDef = ruleService.getRuleDef(ruleName);

        if (ruleDef == null) {
            return Response.status(500).entity(String.format("Rule name [%s] not found", ruleName)).build();
        }
        
        String serializedRule = SerializationUtils.serializeRule(ruleDef);       
        return Response.status(200).entity(serializedRule).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_XML)
    public Response createRuleDef(InputStream serializedRuleStream) throws Exception {
        if (serializedRuleStream == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        RuleDef ruleDef = SerializationUtils.deserializeRule(new InputSource(serializedRuleStream));
        RuleService ruleService = ServiceProviderManager.getInstance().getRuleService();

        if (ruleService.getRuleDef(ruleDef.getName()) != null) {
            return Response.status(500).entity(String.format("Rule with name = [%s] already exists", ruleDef.getName())).build();
        }
        ruleService.createRule(ruleDef);
        return Response.status(200).entity(String.format("Ruledef [%s] created successfully", ruleDef.getName())).build();
    }
}
