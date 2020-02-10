package com.tibco.rta.service.rs.test;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/14
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestAdminServiceTest extends AbstractRestServiceTest {

    @Test
    public void testGetSchema() {
        WebTarget webTarget = client.target(ROOT_URL + "/service/admin");
        Invocation invocation = webTarget.queryParam("schemaName", "AMX_3_0").request(MediaType.TEXT_XML).buildGet();
        Response response = invocation.invoke();
        Assert.assertEquals(response.getStatus(), 200);
        String schema = response.readEntity(String.class);
        Assert.assertNotNull(schema);
    }

    @Test(expected = InternalServerErrorException.class)
    public void testGetSchemaNoSchemaName() {
        WebTarget webTarget = client.target(ROOT_URL + "/service/admin");
        Invocation invocation = webTarget.request(MediaType.TEXT_XML).buildGet();
        invocation.invoke(String.class);
    }
}
