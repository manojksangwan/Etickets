package in.gov.hartrans.etickets.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by manojK on 03/01/2017.
 */
public class orsAvailableServices_onRoute  implements Parcelable {
    int orTimetableID, depotID, trip_srno, trip_id, rKMS, rFare, rTripID;
    String depotShortName, depotName, tripID, tripCode, bus_Type, tripRoute, Leaving, Departing, via, rDesc, busNumber, cndName, cndMobileNo, cndWaibyillID;
    Date jDateTime;

    public orsAvailableServices_onRoute(int orTimetableID, int depotID, int trip_srno, int trip_id, int rKMS, int rFare, int rTripID, String depotShortName, String depotName, String tripID, String tripCode, String bus_Type, String tripRoute, String leaving, String departing, String via, String rDesc, String busNumber, String cndName, String cndMobileNo, String cndWaibyillID, Date jDateTime) {
        this.orTimetableID = orTimetableID;
        this.depotID = depotID;
        this.trip_srno = trip_srno;
        this.trip_id = trip_id;
        this.rKMS = rKMS;
        this.rFare = rFare;
        this.rTripID = rTripID;
        this.depotShortName = depotShortName;
        this.depotName = depotName;
        this.tripID = tripID;
        this.tripCode = tripCode;
        this.bus_Type = bus_Type;
        this.tripRoute = tripRoute;
        Leaving = leaving;
        Departing = departing;
        this.via = via;
        this.rDesc = rDesc;
        this.busNumber = busNumber;
        this.cndName = cndName;
        this.cndMobileNo = cndMobileNo;
        this.cndWaibyillID = cndWaibyillID;
        this.jDateTime = jDateTime;
    }

    protected orsAvailableServices_onRoute(Parcel in) {
        orTimetableID = in.readInt();
        depotID = in.readInt();
        trip_srno = in.readInt();
        trip_id = in.readInt();
        rKMS = in.readInt();
        rFare = in.readInt();
        rTripID = in.readInt();
        depotShortName = in.readString();
        depotName = in.readString();
        tripID = in.readString();
        tripCode = in.readString();
        bus_Type = in.readString();
        tripRoute = in.readString();
        Leaving = in.readString();
        Departing = in.readString();
        via = in.readString();
        rDesc = in.readString();
        busNumber = in.readString();
        cndName = in.readString();
        cndMobileNo = in.readString();
        cndWaibyillID = in.readString();
        jDateTime = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orTimetableID);
        dest.writeInt(depotID);
        dest.writeInt(trip_srno);
        dest.writeInt(trip_id);
        dest.writeInt(rKMS);
        dest.writeInt(rFare);
        dest.writeInt(rTripID);
        dest.writeString(depotShortName);
        dest.writeString(depotName);
        dest.writeString(tripID);
        dest.writeString(tripCode);
        dest.writeString(bus_Type);
        dest.writeString(tripRoute);
        dest.writeString(Leaving);
        dest.writeString(Departing);
        dest.writeString(via);
        dest.writeString(rDesc);
        dest.writeString(busNumber);
        dest.writeString(cndName);
        dest.writeString(cndMobileNo);
        dest.writeString(cndWaibyillID);
        dest.writeSerializable(jDateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<orsAvailableServices_onRoute> CREATOR = new Creator<orsAvailableServices_onRoute>() {
        @Override
        public orsAvailableServices_onRoute createFromParcel(Parcel in) {
            return new orsAvailableServices_onRoute(in);
        }

        @Override
        public orsAvailableServices_onRoute[] newArray(int size) {
            return new orsAvailableServices_onRoute[size];
        }
    };

    public int getOrTimetableID() {
        return orTimetableID;
    }

    public void setOrTimetableID(int orTimetableID) {
        this.orTimetableID = orTimetableID;
    }

    public int getDepotID() {
        return depotID;
    }

    public void setDepotID(int depotID) {
        this.depotID = depotID;
    }

    public int getTrip_srno() {
        return trip_srno;
    }

    public void setTrip_srno(int trip_srno) {
        this.trip_srno = trip_srno;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getrKMS() {
        return rKMS;
    }

    public void setrKMS(int rKMS) {
        this.rKMS = rKMS;
    }

    public int getrFare() {
        return rFare;
    }

    public void setrFare(int rFare) {
        this.rFare = rFare;
    }

    public int getrTripID() {
        return rTripID;
    }

    public void setrTripID(int rTripID) {
        this.rTripID = rTripID;
    }

    public String getDepotShortName() {
        return depotShortName;
    }

    public void setDepotShortName(String depotShortName) {
        this.depotShortName = depotShortName;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getBus_Type() {
        return bus_Type;
    }

    public void setBus_Type(String bus_Type) {
        this.bus_Type = bus_Type;
    }

    public String getTripRoute() {
        return tripRoute;
    }

    public void setTripRoute(String tripRoute) {
        this.tripRoute = tripRoute;
    }

    public String getLeaving() {
        return Leaving;
    }

    public void setLeaving(String leaving) {
        Leaving = leaving;
    }

    public String getDeparting() {
        return Departing;
    }

    public void setDeparting(String departing) {
        Departing = departing;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getrDesc() {
        return rDesc;
    }

    public void setrDesc(String rDesc) {
        this.rDesc = rDesc;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getCndName() {
        return cndName;
    }

    public void setCndName(String cndName) {
        this.cndName = cndName;
    }

    public String getCndMobileNo() {
        return cndMobileNo;
    }

    public void setCndMobileNo(String cndMobileNo) {
        this.cndMobileNo = cndMobileNo;
    }

    public String getCndWaibyillID() {
        return cndWaibyillID;
    }

    public void setCndWaibyillID(String cndWaibyillID) {
        this.cndWaibyillID = cndWaibyillID;
    }

    public Date getjDateTime() {
        return jDateTime;
    }

    public void setjDateTime(Date jDateTime) {
        this.jDateTime = jDateTime;
    }
}
