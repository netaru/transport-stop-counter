package se.david.traffic;

import se.david.server.sl.model.HallplatserOchLinjerStopPointDto.StopAreaTypeCodeEnum;

public class Stop
{
    private String id;
    private String name;
    private String areaNumber;

    private StopAreaTypeCodeEnum type;

    protected Stop(String id, String name, String areaNumber, StopAreaTypeCodeEnum type)
    {
        this.id         = id;
        this.name       = name;
        this.areaNumber = areaNumber;
        this.type       = type;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getAreaNumber() { return areaNumber; }

    public StopAreaTypeCodeEnum getType() { return type; }

    /*
     * Id should be uniq so I guess I can use it for equals and the hashcode
     */
    @Override
    public boolean equals(Object other)
    {
        return id.equals(((Stop) other).id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public String toString()
    {
        return "Id: " + id + ", Name: " + name + ", AreaNumber: " + areaNumber + ", Type: " + type;
    }

    public static class Builder
    {
        private String id;
        private String name;
        private String areaNumber;

        private StopAreaTypeCodeEnum type;

        public Builder()
        {
            id         = "No identifier";
            name       = "Unknown Stop name";
            areaNumber = "No area number";
            type       = StopAreaTypeCodeEnum.UNKNOWN;
        }

        public Builder id(String s)
        {
            this.id = s;
            return this;
        }

        public Builder name(String s)
        {
            this.name = s;
            return this;
        }

        public Builder area(String s)
        {
            this.areaNumber = s;
            return this;
        }

        public Builder type(StopAreaTypeCodeEnum type)
        {
            this.type = type;
            return this;
        }

        public Boolean isUnknown() { return this.type == StopAreaTypeCodeEnum.UNKNOWN; }

        public Stop build() { return new Stop(id, name, areaNumber, type); }
    }
}
