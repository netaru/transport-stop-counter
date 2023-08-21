package se.david;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TransportStopCounterServiceConfiguration extends Configuration
{
    @NotEmpty
    private String backendUrl;

    @NotEmpty
    private String slKey;

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

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

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration()
    {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClientConfiguration)
    {
        this.jerseyClient = jerseyClientConfiguration;
    }
}
