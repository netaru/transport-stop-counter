package se.david.health;

import java.util.Optional;

import se.david.api.TransportsResponse;
import se.david.resources.TransportsResource;

import com.codahale.metrics.health.HealthCheck;

public class TransportsHealthCheck extends HealthCheck
{
    private TransportsResource resource;

    public TransportsHealthCheck(TransportsResource resource) { this.resource = resource; }

    @Override
    protected Result check() throws Exception
    {
        TransportsResponse response = resource.getTransports(Optional.of(0), Optional.of(10));
        if (null == response) return Result.unhealthy("TransportsResponse is null");
        return Result.healthy();
    }
}
