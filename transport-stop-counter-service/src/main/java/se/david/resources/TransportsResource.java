package se.david.resources;

import com.codahale.metrics.annotation.Timed;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import se.david.api.TransportsResponse;

@Path("/transports")
@Produces(MediaType.APPLICATION_JSON)
public class TransportsResource
{
    @GET
    @Timed
    public TransportsResponse getTransports() throws Exception
    {
        return new TransportsResponse(null);
    }
}
