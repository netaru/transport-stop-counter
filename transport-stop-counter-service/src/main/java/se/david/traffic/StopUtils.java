package se.david.traffic;

import se.david.server.sl.model.HallplatserOchLinjerStopPointDto.StopAreaTypeCodeEnum;

public class StopUtils
{
    public static StopAreaTypeCodeEnum guessStopAreaTypeCodeById(Integer id)
    {
		// My best guess as to which stopIds are matched to what stopTypes
        if (id >= 23000) return StopAreaTypeCodeEnum.BUSTERM;
        if (id >= 22000) return StopAreaTypeCodeEnum.TRAMSTN;
        if (id >= 9000) return StopAreaTypeCodeEnum.BUSTERM;
        if (id >= 8000) return StopAreaTypeCodeEnum.SHIPBER;
        if (id >= 7000) return StopAreaTypeCodeEnum.TRAMSTN;
        if (id >= 5000) return StopAreaTypeCodeEnum.RAILWSTN;
        if (id >= 4000) return StopAreaTypeCodeEnum.TRAMSTN;
        if (id >= 1000) return StopAreaTypeCodeEnum.METROSTN;
        return StopAreaTypeCodeEnum.SHIPBER;
    }
}
