package se.david.cli;

import se.david.server.sl.ApiClient;
import se.david.server.sl.ApiException;
import se.david.server.sl.Configuration;
import se.david.server.sl.api.HallplatserOchLinjerApi;

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

        HallplatserOchLinjerApi api = new HallplatserOchLinjerApi(client);
        try
        {
            var response = api.lineDataJsonmodeljourGet(namespace.getString("key"));
            System.out.println(response.toString());
        }
        catch (ApiException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
