package se.david.traffic;

import java.time.Duration;

import se.david.server.sl.ApiClient;
import se.david.server.sl.ApiException;
import se.david.server.sl.api.HallplatserOchLinjerApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

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

    public static LoadingCache<String, Transports> createSLCache(ApiClient client, String key)
    {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofDays(1))
                .build(new CacheLoader<String, Transports>() {
                    @Override
                    public Transports load(String url) throws Exception
                    {
                        logger.info("Getting new data from SL.");
                        return fetchTransportsFromSL(client, key);
                    }
                });
    }
}
