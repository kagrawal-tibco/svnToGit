package com.tibco.rta.service.rs;

import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.service.session.SessionCreationException;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/service/session")
public class RestSessionService extends AbstractRestService {

    @PUT
    @Path("/register")
    public Response registerSession(@QueryParam("sessionId") String sessionId, @QueryParam("sessionName") String sessionName) {
        try {
            ServerSessionRegistry.INSTANCE.addOrCreateSession(sessionId, sessionName);
        } catch (SessionCreationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(200).entity(String.format("Session [%s] registered successfully", sessionName)).build();
    }
}
