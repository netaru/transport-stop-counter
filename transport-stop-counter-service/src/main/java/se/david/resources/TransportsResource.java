package se.david.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.david.api.LineResponse;
import se.david.api.TransportsResponse;
import se.david.server.sl.ApiClient;
import se.david.traffic.Line;
import se.david.traffic.Transports;
import se.david.traffic.TransportsUtils;

import com.codahale.metrics.annotation.Timed;
import com.google.common.cache.LoadingCache;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/transports")
@Produces(MediaType.APPLICATION_JSON)
public class TransportsResource
{
    private final ApiClient apiClient;

    private final LoadingCache<String, Transports> apiCacheSL;

    public TransportsResource(ApiClient apiClient, String slKey)
    {
        this.apiClient  = apiClient;
        this.apiCacheSL = TransportsUtils.createSLCache(apiClient, slKey);
    }

    @GET
    @Timed
    public TransportsResponse getTransports(
            @QueryParam("begin") Optional<Integer> begin,
            @QueryParam("end") Optional<Integer>   end) throws Exception
    {
        Transports         transports = apiCacheSL.get(apiClient.getBasePath());
        List<LineResponse> lines      = new ArrayList<>();
        List<Line>         parsed     = transports.getTransports();

        int beginLimit = Integer.max(begin.orElse(0), 0);
        int endLimit   = Integer.min(end.orElse(10), parsed.size());
        for (int i = beginLimit; i < endLimit; ++i) { lines.add(new LineResponse(parsed.get(i), i)); }
        return new TransportsResponse(lines);
    }
}
