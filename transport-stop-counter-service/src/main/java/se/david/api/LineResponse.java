package se.david.api;

import java.util.ArrayList;
import java.util.List;

import se.david.traffic.Line;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineResponse
{
    private String lineNumber;

    private String lineDesignation;

    private String transportMode;

    private String transportCode;

    private Integer index;

    private List<StopResponse> stops;

    public LineResponse(Line line, Integer index)
    {
        stops                = new ArrayList<>();
        this.lineNumber      = line.getLineData().getLineNumber();
        this.lineDesignation = line.getLineData().getLineDesignation();
        this.transportMode   = line.getLineData().getDefaultTransportMode();
        this.transportCode   = line.getLineData().getDefaultTransportModeCode().toString();
        this.index           = index;
        for (var s : line.getStops()) { stops.add(new StopResponse(s)); }
    }

    @JsonProperty
    public String getLineNumber()
    {
        return lineNumber;
    }

    @JsonProperty
    public String getLineDesignation()
    {
        return lineDesignation;
    }

    @JsonProperty
    public String getTransportMode()
    {
        return transportMode;
    }

    @JsonProperty
    public String getTransportCode()
    {
        return transportCode;
    }

    @JsonProperty
    public Integer getIndex()
    {
        return index;
    }

    @JsonProperty
    public List<StopResponse> getStops()
    {
        return stops;
    }
}
