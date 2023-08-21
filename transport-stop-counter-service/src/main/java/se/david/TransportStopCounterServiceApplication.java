package se.david;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class TransportStopCounterServiceApplication extends Application<TransportStopCounterServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TransportStopCounterServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "TransportStopCounterService";
    }

    @Override
    public void initialize(final Bootstrap<TransportStopCounterServiceConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TransportStopCounterServiceConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
