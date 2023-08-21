package se.david.api;

import se.david.traffic.Stop;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopResponse
{
    private String id;
    private String name;
    private String areaNumber;
    private String type;

    public StopResponse(Stop s)
    {
        this.id         = s.getId();
        this.name       = s.getName();
        this.areaNumber = s.getAreaNumber();
        this.type       = s.getType().toString();
    }

    @JsonProperty
    public String getId()
    {
        return id;
    }

    @JsonProperty
    public String getName()
    {
        return name;
    }

    @JsonProperty
    public String getAreaNumber()
    {
        return areaNumber;
    }

    @JsonProperty
    public String getType()
    {
        return type;
    }
}
