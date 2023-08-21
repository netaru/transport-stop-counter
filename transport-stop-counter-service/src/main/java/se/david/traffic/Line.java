package se.david.traffic;

import java.util.HashSet;
import java.util.Set;

import se.david.server.sl.model.HallplatserOchLinjerLineDto;

public class Line
{
    private HallplatserOchLinjerLineDto line;
    private Set<Stop>                   stops;

    public Line(HallplatserOchLinjerLineDto line)
    {
        this.line = line;
        stops     = new HashSet<>();
    }

    public void addStop(Stop stop) { stops.add(stop); }

    public HallplatserOchLinjerLineDto getLineData() { return line; }

    public Set<Stop> getStops() { return stops; }

    @Override
    public String toString()
    {
        if (stops.size() > 0)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Line: ").append(line.getLineNumber()).append(", stops: ").append(stops.size()).append("\n");
            stops.stream().forEachOrdered((e) -> builder.append("    ").append(e).append("\n"));
            return builder.toString();
        }
        return "Line: " + line.getLineNumber() + ", stops: " + stops.size();
    }
}
