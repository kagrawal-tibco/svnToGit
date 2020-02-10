package com.tibco.rta.service.rs.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.rta.service.rs.resource.QueryState;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class QueryStateProvider implements MessageBodyWriter<QueryState> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(QueryState queryState, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(QueryState queryState, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(entityStream, "UTF-8");
        PrintWriter printWriter = new PrintWriter(writer);
        objectMapper.writeValue(printWriter, queryState);
        printWriter.close();
    }
}
