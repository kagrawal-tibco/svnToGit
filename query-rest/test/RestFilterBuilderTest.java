import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.tibco.cep.query.rest.common.FilterBuilder;
import com.tibco.cep.query.rest.common.RestFilterBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 2/13/14
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestFilterBuilderTest {

    @Test
    public void testEquals() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("value1");
        queryParameters.put("key1", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("key1=value1", filter);
    }

    @Test
    public void testGreaterThanEquals() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3E%3Dvalue1");
        queryParameters.put("key1", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("key1>=value1", filter);
    }

    @Test
    public void testLessThanEquals() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3C%3Dvalue1");
        queryParameters.put("key1", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("key1<=value1", filter);
    }

    @Test
    public void testGreaterThan() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3Evalue1");
        queryParameters.put("key1", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("key1>value1", filter);
    }

    @Test
    public void testLessThan() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3Cvalue1");
        queryParameters.put("key1", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("key1<value1", filter);
    }

    @Test
    public void testEscapeSequence() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%5C%3Cvalue1");
        queryParameters.put("key1", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("key1=<value1", filter);
    }

    @Test
    public void testComplexEquals() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("value1");
        queryParameters.put("key1", valueList);

        valueList = new LinkedList<>();
        valueList.add("value2");
        queryParameters.put("key2", valueList);

        valueList = new LinkedList<>();
        valueList.add("value3");
        queryParameters.put("key3", valueList);


        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("((key3=value3 and key2=value2) and key1=value1)", filter);
    }

    @Test
    public void testComplexGreaterThanEquals() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3E%3Dvalue1");
        queryParameters.put("key1", valueList);

        valueList = new LinkedList<>();
        valueList.add("value2");
        queryParameters.put("key2", valueList);

        valueList = new LinkedList<>();
        valueList.add("%3E%3Dvalue3");
        queryParameters.put("key3", valueList);


        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("((key3>=value3 and key2=value2) and key1>=value1)", filter);
    }

    @Test
    public void testComplexLessThanEquals() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3C%3Dvalue1");
        queryParameters.put("key1", valueList);

        valueList = new LinkedList<>();
        valueList.add("value2");
        queryParameters.put("key2", valueList);

        valueList = new LinkedList<>();
        valueList.add("%3C%3Dvalue3");
        queryParameters.put("key3", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("((key3<=value3 and key2=value2) and key1<=value1)", filter);
    }

    @Test
    public void testComplexGreaterThan() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3Evalue1");
        queryParameters.put("key1", valueList);

        valueList = new LinkedList<>();
        valueList.add("value2");
        queryParameters.put("key2", valueList);

        valueList = new LinkedList<>();
        valueList.add("%3Evalue3");
        queryParameters.put("key3", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("((key3>value3 and key2=value2) and key1>value1)", filter);
    }


    @Test
    public void testComplexLessThan() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%3Cvalue1");
        queryParameters.put("key1", valueList);

        valueList = new LinkedList<>();
        valueList.add("value2");
        queryParameters.put("key2", valueList);

        valueList = new LinkedList<>();
        valueList.add("%3Cvalue3");
        queryParameters.put("key3", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("((key3<value3 and key2=value2) and key1<value1)", filter);
    }

    @Test
    public void testComplexEscapeSequence() {
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<String> valueList = new LinkedList<>();
        valueList.add("%5C%3Cvalue1");
        queryParameters.put("key1", valueList);

        valueList = new LinkedList<>();
        valueList.add("value2");
        queryParameters.put("key2", valueList);

        valueList = new LinkedList<>();
        valueList.add("%5C%3Cvalue3");
        queryParameters.put("key3", valueList);

        FilterBuilder filterBuilder = new RestFilterBuilder(queryParameters);
        filterBuilder.buildFilter();
        String filter = (String) filterBuilder.getFilter();

        Assert.assertEquals("((key3=<value3 and key2=value2) and key1=<value1)", filter);
    }
}

