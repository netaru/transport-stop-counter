package se.david.traffic;

import se.david.server.sl.ApiClient;
import se.david.server.sl.ApiException;
import se.david.server.sl.api.HallplatserOchLinjerApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportsUtils
{
    private static Logger logger = LoggerFactory.getLogger(TransportsUtils.class);

    public static Transports fetchTransportsFromSL(ApiClient client, String key)
    {
        HallplatserOchLinjerApi api = new HallplatserOchLinjerApi(client);
        try
        {
            var siteResponse = api.lineDataJsonmodelsiteGet(key);
            var stopResponse = api.lineDataJsonmodelstopGet(key);
            var lineResponse = api.lineDataJsonmodellineGet(key);
            var jourResponse = api.lineDataJsonmodeljourGet(key);

            Transports transports = new Transports.Builder()
                                       .addSites(siteResponse.getResponseData().getResult())
                                       .addStops(stopResponse.getResponseData().getResult())
                                       .addLines(lineResponse.getResponseData().getResult())
                                       .build();

            // MAYBE: Move this to the builder? I like having the guarantee that the object is built before adding
            // journeys, this could avoid issues with using the builder.
            transports.addJourneys(jourResponse.getResponseData().getResult());
            return transports;
        }
        catch (ApiException exception)
        {
            logger.error(exception.getMessage());
        }
        return null;
    }
}
