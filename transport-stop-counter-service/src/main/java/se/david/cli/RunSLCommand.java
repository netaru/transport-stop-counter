package se.david.cli;

import java.util.List;

import se.david.server.sl.ApiClient;
import se.david.server.sl.Configuration;
import se.david.traffic.Line;
import se.david.traffic.TransportsUtils;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.core.cli.Command;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public class RunSLCommand extends Command
{
    public RunSLCommand() { super("SLCommand", "Command to test SL API"); }

    @Override
    public void configure(Subparser parser)
    {
        parser.addArgument("-u", "--url").dest("url").type(String.class).required(true).help("The backend URL to use");
        parser.addArgument("-k", "--key").dest("key").type(String.class).required(true).help("The API key to use");
    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception
    {
        ApiClient client = Configuration.getDefaultApiClient();
        client.setBasePath(namespace.getString("url"));
        JerseyClientConfiguration config     = new JerseyClientConfiguration();
        Environment               env        = new Environment(getName());
        Client                    httpClient = new JerseyClientBuilder(env).using(config).build(getName());
        client.setHttpClient(httpClient);

        List<Line> transports =
                TransportsUtils.fetchTransportsFromSL(client, namespace.getString("key")).getTransports();
        System.out.println("Transports size: " + transports.size());
        for (int i = 0; i < 10; ++i) { System.out.println(transports.get(i)); }
    }
}
