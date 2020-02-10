package com.tibco.rta.service.rs;

import com.tibco.rta.Fact;
import com.tibco.rta.common.service.transport.http.DefaultMessageContext;
import com.tibco.rta.log.Level;
import com.tibco.rta.runtime.model.serialize.RuntimeModelJSONDeserializer;
import com.tibco.rta.service.metric.MetricCalculationServiceDelegate;
import org.xml.sax.InputSource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@Path("/service/metric")
public class RestMetricService extends AbstractRestService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/assertFacts")
	public Response assertFacts(InputStream serializedFactStream) {
		List<Fact> facts;
		if (serializedFactStream == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		try {
			InputSource inputSource = new InputSource(serializedFactStream);
			facts = new RuntimeModelJSONDeserializer().deserializeFacts(inputSource);

			MetricCalculationServiceDelegate metricServiceDelegate = MetricCalculationServiceDelegate.INSTANCE;
			metricServiceDelegate.assertFact(new DefaultMessageContext(System.currentTimeMillis()), facts);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.status(200).entity(String.format("Submitted facts count [%d] successfully", facts.size())).build();
	}
}
