package se.david;

import java.util.EnumSet;

import se.david.cli.RunSLCommand;
import se.david.health.TransportsHealthCheck;
import se.david.resources.TransportsResource;
import se.david.server.sl.ApiClient;
import se.david.server.sl.Configuration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.ws.rs.client.Client;

public class TransportStopCounterServiceApplication extends Application<TransportStopCounterServiceConfiguration>
{
    public static void main(final String[] args) throws Exception
    {
        new TransportStopCounterServiceApplication().run(args);
    }

    @Override
    public String getName()
    {
        return "TransportStopCounterService";
    }

    @Override
    public void initialize(final Bootstrap<TransportStopCounterServiceConfiguration> bootstrap)
    {
        bootstrap.addCommand(new RunSLCommand());
    }

    @Override
    public void run(final TransportStopCounterServiceConfiguration configuration, final Environment environment)
    {
        Client httpClient = new JerseyClientBuilder(environment)
                                    .using(configuration.getJerseyClientConfiguration())
                                    .build(getName());

        ApiClient apiClient = Configuration.getDefaultApiClient();
        // Replace the default client with a configurable client in dropwizard (gzip: true)
        apiClient.setHttpClient(httpClient);
        apiClient.setBasePath(configuration.getBackendUrl());

        TransportsResource resource = new TransportsResource(apiClient, configuration.getSlKey());
        environment.jersey().register(resource);

        TransportsHealthCheck healthCheck = new TransportsHealthCheck(resource);
        environment.healthChecks().register("transports", healthCheck);

        FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
