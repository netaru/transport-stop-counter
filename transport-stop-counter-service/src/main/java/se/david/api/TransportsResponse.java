package se.david.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransportsResponse
{
    private List<LineResponse> lines;

    public TransportsResponse() {}

    public TransportsResponse(List<LineResponse> lines) { this.lines = lines; }

    @JsonProperty
    public List<LineResponse> getLines()
    {
        return lines;
    }
}
