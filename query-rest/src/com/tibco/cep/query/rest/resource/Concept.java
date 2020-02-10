package com.tibco.cep.query.rest.resource;

import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.query.aggregate.as.AsAggregateFunctions;
import com.tibco.cep.query.rest.common.*;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASEntityDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/11/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
//http://localhost:8080/RestQuery/Concept/xx.yy?id=123&&extid=456
@Path("/Concept")
public class Concept {

    @Path("{conceptUri}")
    @GET
    @Produces("application/xml")
    public String filterQuery(@PathParam("conceptUri") String uri, @Context UriInfo info) throws Exception {

        String conceptUri = "/" + uri.replaceAll("\\.", "/");
        MultivaluedMap<String, String> queryParams = info.getQueryParameters();

        String qlimit = null;

        for (Map.Entry<String, List<String>> mEntry : queryParams.entrySet()) {
            System.out.println("Key : " + mEntry.getKey() + "Value : " + mEntry.getValue());
        }

        if (queryParams.containsKey("queryLimit")) {
            qlimit = String.valueOf(queryParams.get("queryLimit"));
            queryParams.remove("queryLimit");
        }

        int limit = qlimit != null ? Integer.parseInt(qlimit.substring(1, qlimit.length() - 1)) : Integer.MAX_VALUE;

        //-------Selection fields------
        String[] projFields = null;
        if(queryParams.containsKey("_fields"))
        {
         String projParams = String.valueOf(queryParams.get("_fields"));
         projFields = projParams.substring(1, projParams.length()-1).split(",");
         queryParams.remove("_fields");
        }
        //------------------------------



        //--------JSON or XML-----
        String retType = String.valueOf(queryParams.get("returnType"));
        String returnType = retType.substring(1, retType.length() - 1);
        queryParams.remove("returnType");
        //------------------------

        FilterBuilder filterBuilder = new RestFilterBuilder<String>(queryParams);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        //----Getting the Dao using the conceptUri----
        ASEntityDao dao = AsAggregateFunctions.findDao(conceptUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        //---Applying the filter to the space-----
        Set allEntries = spaceMap.entrySet(filter);
        Iterator<Map.Entry> cachedAllEntriesIterator = allEntries.iterator();

        TypeWrapper<String> typeWrapper;
        List<String> resultList;

        AbstractTypeWrapperCreator<String> creator;
        String result;

        //---Preparing the resultSet----
        switch (returnType.toLowerCase()) {

            case "xml":
                creator = new XMLWrapperCreator<>(limit,0,projFields, EntityType.Concept);
                typeWrapper = new TypeWrapperGenerator<String>().getTypeWrapper(creator);

                //typeWrapper = new XmlWrapper<String>(limit,0,projFields);
                result = (String) typeWrapper.convert(cachedAllEntriesIterator);

                return result; //resultList.remove(0);

            case "json":
                creator = new JsonWrapperCreator<>(limit,0,projFields, EntityType.Concept);
                typeWrapper = new TypeWrapperGenerator<String>().getTypeWrapper(creator);

               // typeWrapper = new JsonWrapper<String>(limit,0);
                result = (String) typeWrapper.convert(cachedAllEntriesIterator);

//                ObjectMapper mapper = new ObjectMapper();
//                    try {
//                        mapper.writeValue(new File("c:\\user.json"), resultList);
//                    } catch (IOException e) {
//                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                    }
//                    try {
//                       return mapper.writeValueAsString(resultList);
//                    } catch (IOException e) {
//                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                    }
                }

        return null;
    }




        //-------------------------------------------------------------//

        @GET
        @Produces("application/text")
        public String showConceptDefinition () {
            return ("foo");
        }

        @Produces("application/text")
        public String getConcept () {
            //Get the URI parameters out
            //validate the uri parameters
            //they parameters must be id , extid or custom property names from the concept
            return ("foo");
        }
    }
