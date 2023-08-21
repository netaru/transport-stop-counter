package se.david.traffic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.david.server.sl.model.HallplatserOchLinjerJourneyPatternPointOnLineDto;
import se.david.server.sl.model.HallplatserOchLinjerLineDto;
import se.david.server.sl.model.HallplatserOchLinjerLineDto.DefaultTransportModeCodeEnum;
import se.david.server.sl.model.HallplatserOchLinjerSiteDto;
import se.david.server.sl.model.HallplatserOchLinjerStopPointDto;
import se.david.server.sl.model.HallplatserOchLinjerStopPointDto.StopAreaTypeCodeEnum;

import org.junit.Before;
import org.junit.Test;

public class TransportsTest
{
    Transports transports;

    @Before
    public void setup()
    {
        Map<Integer, HallplatserOchLinjerSiteDto>      sites = mock(Map.class);
        Map<Integer, HallplatserOchLinjerStopPointDto> stops = mock(Map.class);

        when(stops.containsKey(1)).thenReturn(true);
        when(stops.get(1)).thenReturn(buildStop("1", "First station", "1", StopAreaTypeCodeEnum.BUSTERM));

        when(sites.containsKey(2)).thenReturn(true);
        when(sites.get(2)).thenReturn(buildSite("2", "First site by the sea", "2"));

        transports = new Transports.Builder().build(buildLines(), sites, stops);
    }

    @Test
    public void addJourneyTest()
    {
        HallplatserOchLinjerJourneyPatternPointOnLineDto journey =
                mock(HallplatserOchLinjerJourneyPatternPointOnLineDto.class);
        when(journey.getLineNumber()).thenReturn("1");
        when(journey.getJourneyPatternPointNumber()).thenReturn("1");

        transports.addJourney(journey);
        List<Line> lines = transports.getTransports();
        assertEquals(lines.size(), 3);
        assertEquals(lines.get(0).getStops().size(), 1);
        assertEquals(((Stop) lines.get(0).getStops().toArray()[0]).getName(), "First station");
        assertEquals(lines.get(1).getStops().size(), 0);
        assertEquals(lines.get(2).getStops().size(), 0);
    }

    @Test
    public void findStopTest()
    {
        Stop s1 = transports.findStop("1");
        assertEquals(s1.getId(), "1");
        assertEquals(s1.getType(), StopAreaTypeCodeEnum.BUSTERM);

        Stop s2 = transports.findStop("2");
        assertEquals(s2.getId(), "2");
        assertEquals(s2.getType(), StopAreaTypeCodeEnum.SHIPBER);

        Stop s3 = transports.findStop("5023");
        assertEquals(s3.getId(), "5023");
        assertEquals(s3.getType(), StopAreaTypeCodeEnum.RAILWSTN);
    }

    private HallplatserOchLinjerStopPointDto buildStop(String id, String name, String area, StopAreaTypeCodeEnum type)
    {
        HallplatserOchLinjerStopPointDto stop = new HallplatserOchLinjerStopPointDto();
        stop.setStopPointNumber(id);
        stop.setStopPointName(name);
        stop.setStopAreaNumber(area);
        stop.setStopAreaTypeCode(type);
        return stop;
    }

    private HallplatserOchLinjerSiteDto buildSite(String id, String name, String area)
    {
        HallplatserOchLinjerSiteDto site = new HallplatserOchLinjerSiteDto();
        site.setSiteId(id);
        site.setSiteName(name);
        site.setStopAreaNumber(area);
        return site;
    }

    private Map<DefaultTransportModeCodeEnum, Map<Integer, Line>> buildLines()
    {
        Map<DefaultTransportModeCodeEnum, Map<Integer, Line>> lines =
                new HashMap<DefaultTransportModeCodeEnum, Map<Integer, Line>>();

        lines.put(DefaultTransportModeCodeEnum.BUS, createBuses());
        return lines;
    }

    private Map<Integer, Line> createBuses()
    {
        Map<Integer, Line> buses = new HashMap<Integer, Line>();
        buses.put(1, new Line(createLine("1", "1")));
        buses.put(2, new Line(createLine("2", "2")));
        buses.put(3, new Line(createLine("2", "3EXTRA")));
        return buses;
    }

    private HallplatserOchLinjerLineDto createLine(String lineNumber, String lineDesignation)
    {
        HallplatserOchLinjerLineDto result = new HallplatserOchLinjerLineDto();
        result.setLineNumber(lineNumber);
        result.setLineDesignation(lineDesignation);
        result.setDefaultTransportMode("");
        result.setDefaultTransportModeCode(DefaultTransportModeCodeEnum.BUS);
        return result;
    }
}
