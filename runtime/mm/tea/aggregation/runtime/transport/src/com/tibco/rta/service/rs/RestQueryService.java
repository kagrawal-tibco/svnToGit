package com.tibco.rta.service.rs;

import com.tibco.rta.log.Level;
import com.tibco.rta.model.serialize.impl.ModelJSONSerializer;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.query.QueryServiceDelegate;
import com.tibco.rta.service.rs.resource.QueryState;
import org.xml.sax.InputSource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.util.List;

@Path("/service/query")
public class RestQueryService extends AbstractRestService {

    @Context
    private UriInfo uriInfo;

    /**
     *
     * Register streaming query using mandatory session id and session name.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/register/streaming")
    public Response createQuery(InputStream serializedQueryDef, @QueryParam("sessionId") String sessionID,
                                @QueryParam("sessionName") String sessionName) {


        if (sessionName == null || sessionName.isEmpty()) {
            String errorMessage = "Query registration can only happen with a named session";
            return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
        }
        if (serializedQueryDef == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        QueryDef queryDef;
        try {
            queryDef = SerializationUtils.deserializeQuery(new InputSource(serializedQueryDef));
            QueryServiceDelegate queryServiceDelegate = QueryServiceDelegate.INSTANCE;
            queryServiceDelegate.registerQuery(sessionID, queryDef);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        Response.ResponseBuilder responseBuilder = Response.status(Status.OK);
        return responseBuilder.entity(String.format("Streaming query [%s] registered successfully", queryDef.getName())).build();
    }

    /**
     *
     * Register snapshot query using serialized query definition.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register/snapshot")
    public Response createQuery(InputStream serializedQueryDef) {

        if (serializedQueryDef == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String correlationId;
        try {
            QueryDef queryDef = SerializationUtils.deserializeQuery(new InputSource(serializedQueryDef));
            QueryServiceDelegate queryServiceDelegate = QueryServiceDelegate.INSTANCE;
            correlationId = queryServiceDelegate.registerQuery(queryDef);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        Response.ResponseBuilder responseBuilder = Response.status(Status.OK);
        return responseBuilder.entity(buildQueryState(correlationId)).build();
    }

    private QueryState buildQueryState(String browserId) {
        String baseUri = uriInfo.getBaseUri().toString();
        String baseServiceUri = "service/query";
        String hasNextPath = "/hasNext";
        String nextPath = "/next";

        StringBuilder baseUriBuilder = new StringBuilder(baseUri).append(baseServiceUri);
        StringBuilder paramsBuilder = new StringBuilder("/").append(browserId);

        String hasNextUri = new StringBuilder(baseUriBuilder).append(hasNextPath).append(paramsBuilder).toString();
        String nextUri = new StringBuilder(baseUriBuilder).append(nextPath).append(paramsBuilder).toString();

        return new QueryState(hasNextUri, nextUri);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/next/{browserId}")
    public Response nextResult(@PathParam("browserId") String browserId) {
        if (browserId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        QueryServiceDelegate queryServiceDelegate = QueryServiceDelegate.INSTANCE;
        List<QueryResultTuple> resultLists;
        String serializeQueryResults;
        try {
            resultLists = queryServiceDelegate.getNext(browserId);
            serializeQueryResults =
                    ModelJSONSerializer.INSTANCE.serializeQueryResults(new QueryResultTupleCollection<QueryResultTuple>(resultLists));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(serializeQueryResults).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hasNext/{browserId}")
    public Response hasNextResult(@PathParam("browserId") String browserId) {
        if (browserId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        QueryServiceDelegate queryServiceDelegate = QueryServiceDelegate.INSTANCE;
        Boolean available;
        try {
            available = queryServiceDelegate.hasNext(browserId);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(available.toString()).build();
    }


    @POST
    @Path("/unregister")
    public Response unregisterQuery(@QueryParam("queryName") String queryName, @QueryParam("sessionId") String sessionId) {
        QueryServiceDelegate queryServiceDelegate = QueryServiceDelegate.INSTANCE;
        try {
            queryServiceDelegate.unregisterQuery(sessionId, queryName);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Status.OK).entity(String.format("Query [%s] unregistered successfully", queryName)).build();
    }
}
