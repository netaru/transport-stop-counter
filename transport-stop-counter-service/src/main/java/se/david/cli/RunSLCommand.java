package se.david.cli;

import io.dropwizard.core.cli.Command;
import io.dropwizard.core.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public class RunSLCommand extends Command
{
    public RunSLCommand() { super("SLCommand", "Command to test SL API"); }

    @Override
    public void configure(Subparser parser)
    {
        parser.addArgument("-u", "--url").dest("url").type(String.class).required(true).help("The backend URL to use");
    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception
    {
        System.out.println(getDescription());
    }
}
