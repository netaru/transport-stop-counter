package se.david.resources;

import com.codahale.metrics.annotation.Timed;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/transports")
@Produces(MediaType.APPLICATION_JSON)
public class TransportsResource
{
    @GET
    @Timed
    public String getTransports() throws Exception
    {
        return "Test";
    }
}
