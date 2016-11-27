package in.gov.hartrans.etickets.Models;

/**
 * Created by manojK on 06/11/2016.
 */
public class BookingDays {
    private String StationID, StationName;

    public BookingDays(String stationID, String stationName) {
        this.setStationID(stationID);
        this.setStationName(stationName);
    }

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String stationID) {
        StationID = stationID;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

}
