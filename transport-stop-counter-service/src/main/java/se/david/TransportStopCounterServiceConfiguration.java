package se.david;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotEmpty;

public class TransportStopCounterServiceConfiguration extends Configuration
{
    @NotEmpty
    private String backendUrl;

    @NotEmpty
    private String slKey;

    @JsonProperty
    public String getBackendUrl()
    {
        return backendUrl;
    }

    @JsonProperty
    public void setBackendUrl(String backendUrl)
    {
        this.backendUrl = backendUrl;
    }

    @JsonProperty
    public String getSlKey()
    {
        return slKey;
    }

    @JsonProperty
    public void setSlKey(String slKey)
    {
        this.slKey = slKey;
    }
}
