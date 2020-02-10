package com.tibco.rta.service.rs;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.serialize.impl.ModelJSONSerializer;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.service.admin.AdminService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/1/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/service/admin")
public class RestAdminService extends AbstractRestService {

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("/show/{schemaName}")
    public Response getSchema(@PathParam("schemaName")String schemaName) throws Exception {
        AdminService adminService = ServiceProviderManager.getInstance().getAdminService();
        RtaSchema requiredSchema = adminService.getSchema(schemaName);

        if (requiredSchema == null) {
            return Response.status(500).entity(String.format("Schema name [%s] not found", schemaName)).build();
        }
        String serializedSchema = SerializationUtils.serializeSchema(requiredSchema);
        return Response.status(200).entity(serializedSchema).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/schemas")
    public Response getSchemaNames() throws Exception {
        AdminService adminService = ServiceProviderManager.getInstance().getAdminService();
        Collection<RtaSchema> allSchemas = adminService.getAllSchemas();
        Collection<String> schemaNames = new ArrayList<String>(allSchemas.size());

        for (RtaSchema schema : allSchemas) {
            schemaNames.add(schema.getName());
        }
        String serialized = ModelJSONSerializer.INSTANCE.serializeSchemaNames(schemaNames);
        return Response.status(200).entity(serialized).build();
    }
}
