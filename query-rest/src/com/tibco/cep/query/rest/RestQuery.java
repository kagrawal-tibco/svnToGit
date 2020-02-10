package com.tibco.cep.query.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/5/14
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/RestQueryOld")
public class RestQuery {

        @GET
        @Produces("application/text")
        public String showEmployees() {
          return("foo");
        }

        @Produces("application/text")
        public String showEmployees(String c) {
        return("foo");
        }
}
