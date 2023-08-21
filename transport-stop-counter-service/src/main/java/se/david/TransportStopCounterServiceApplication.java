package se.david;

import se.david.cli.RunSLCommand;
import se.david.resources.TransportsResource;
import se.david.server.sl.ApiClient;
import se.david.server.sl.Configuration;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
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
    }
}
