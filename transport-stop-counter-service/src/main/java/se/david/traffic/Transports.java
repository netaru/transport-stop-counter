package se.david.traffic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.david.server.sl.model.HallplatserOchLinjerJourneyPatternPointOnLineDto;
import se.david.server.sl.model.HallplatserOchLinjerLineDto;
import se.david.server.sl.model.HallplatserOchLinjerLineDto.DefaultTransportModeCodeEnum;
import se.david.server.sl.model.HallplatserOchLinjerSiteDto;
import se.david.server.sl.model.HallplatserOchLinjerStopPointDto;
import se.david.server.sl.model.HallplatserOchLinjerStopPointDto.StopAreaTypeCodeEnum;

public class Transports
{
    private Map<DefaultTransportModeCodeEnum, Map<Integer, Line>> lines;

    private Map<Integer, HallplatserOchLinjerSiteDto> sites;

    private Map<Integer, HallplatserOchLinjerStopPointDto> stops;

    private Map<StopAreaTypeCodeEnum, DefaultTransportModeCodeEnum> translator;

    protected Transports(
            Map<DefaultTransportModeCodeEnum, Map<Integer, Line>>   lines,
            Map<Integer, HallplatserOchLinjerSiteDto>               sites,
            Map<Integer, HallplatserOchLinjerStopPointDto>          stops,
            Map<StopAreaTypeCodeEnum, DefaultTransportModeCodeEnum> translator)
    {
        this.lines      = lines;
        this.sites      = sites;
        this.stops      = stops;
        this.translator = translator;
    }

    public void addJourneys(List<HallplatserOchLinjerJourneyPatternPointOnLineDto> journeys)
    {
        journeys.stream().forEachOrdered((e) -> addJourney(e));
    }

    public List<Line> getTransports()
    {
        List<Line> result = new ArrayList<Line>();
        for (var map : lines.values()) { result.addAll(map.values()); }
		// Sort the list largest stop sizes first
        Collections.sort(result, (t1, t2) -> { return t2.getStops().size() - t1.getStops().size(); });
        return result;
    }

    protected void addJourney(HallplatserOchLinjerJourneyPatternPointOnLineDto journey)
    {
        Integer lineNumber  = Integer.parseInt(journey.getLineNumber());
        String  pointNumber = journey.getJourneyPatternPointNumber();
        Stop    stop        = findStop(pointNumber);

        // Type is not known and can therefore not be mapped to a line, skip this journey
        if (stop.getType() == StopAreaTypeCodeEnum.UNKNOWN) { return; }

        Map<Integer, Line> lineMap = lines.get(translator.get(stop.getType()));
        // Line is missing from the LineData?model=line, this in an issue which can occur with FERRY
        // e.g. stopType is BUSTERM but in the LineData the corresponding LineNumber is a FERRY
        if (!lineMap.containsKey(lineNumber)) { return; }
        lineMap.get(lineNumber).addStop(stop);
    }

    protected Stop findStop(String id)
    {
        Integer      idAsInt = Integer.parseInt(id);
        Stop.Builder builder = new Stop.Builder();
		// Use the stop it it exists in the LineData?model=stop
        if (stops.containsKey(idAsInt))
        {
            var value = stops.get(idAsInt);
            builder.id(value.getStopPointNumber())
                    .name(value.getStopPointName())
                    .area(value.getStopAreaNumber())
                    .type(value.getStopAreaTypeCode());
        }
		// MAYBE: Try to find the stop in the sites data? Is this correct? or should we just skip these?
        else if (sites.containsKey(idAsInt))
        {
            var value = sites.get(idAsInt);
            builder.id(value.getSiteId()).name(value.getSiteName()).area(value.getStopAreaNumber());
        }
		// No name can be found just use the identifier
        else { builder.id(id); }

		// If the stop was not present in the stop data we can make a guess as to what TransportStop this is
        if (builder.isUnknown()) { builder.type(StopUtils.guessStopAreaTypeCodeById(idAsInt)); }

        return builder.build();
    }

    public static class Builder
    {
        private Map<DefaultTransportModeCodeEnum, Map<Integer, Line>>   lines;
        private Map<Integer, HallplatserOchLinjerSiteDto>               sites;
        private Map<Integer, HallplatserOchLinjerStopPointDto>          stops;
        private Map<StopAreaTypeCodeEnum, DefaultTransportModeCodeEnum> translator;

        public Builder()
        {
            // MAYBE: Use the API-call url/LineData?model=trans for translations if spec is updated to use the same enum
            translator = new HashMap<>();
            translator.put(StopAreaTypeCodeEnum.BUSTERM, DefaultTransportModeCodeEnum.BUS);
            translator.put(StopAreaTypeCodeEnum.FERRYBER, DefaultTransportModeCodeEnum.FERRY);
            translator.put(StopAreaTypeCodeEnum.METROSTN, DefaultTransportModeCodeEnum.METRO);
            translator.put(StopAreaTypeCodeEnum.SHIPBER, DefaultTransportModeCodeEnum.SHIP);
            translator.put(StopAreaTypeCodeEnum.RAILWSTN, DefaultTransportModeCodeEnum.TRAIN);
            translator.put(StopAreaTypeCodeEnum.TRAMSTN, DefaultTransportModeCodeEnum.TRAM);
        }

        public Builder addSites(List<HallplatserOchLinjerSiteDto> arg)
        {
            if (null == sites) { sites = new HashMap<>(); }
			// Easy access to stops with their stopAreaNumber
            arg.stream().forEachOrdered((e) -> sites.put(Integer.parseInt(e.getStopAreaNumber()), e));
            return this;
        }

        public Builder addStops(List<HallplatserOchLinjerStopPointDto> arg)
        {
            if (null == stops) { stops = new HashMap<>(); }
			// Easy access to stops with their stopNumber
            arg.stream().forEachOrdered((e) -> stops.put(Integer.parseInt(e.getStopPointNumber()), e));
            return this;
        }

        public Builder addLines(List<HallplatserOchLinjerLineDto> arg)
        {
            if (null == lines)
            {
                lines = new HashMap<>();
				// Store lines in their respective TransModeCode since lineNr is only uniq for each type?
                for (var e : DefaultTransportModeCodeEnum.values()) { lines.put(e, new HashMap<>()); }
            }
            arg.stream().forEachOrdered(
                    (e) -> lines.get(e.getDefaultTransportModeCode())
                                       .put(Integer.parseInt(e.getLineNumber()), new Line(e)));
            return this;
        }

        public Transports build() { return new Transports(lines, sites, stops, translator); }


		// Test constructor
        protected Transports build(
                Map<DefaultTransportModeCodeEnum, Map<Integer, Line>> lines,
                Map<Integer, HallplatserOchLinjerSiteDto>             sites,
                Map<Integer, HallplatserOchLinjerStopPointDto>        stops)
        {
            return new Transports(lines, sites, stops, translator);
        }
    }
}
