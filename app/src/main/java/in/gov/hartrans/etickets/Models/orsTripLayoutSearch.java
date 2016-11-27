package in.gov.hartrans.etickets.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manojK on 16/11/2016.
 */
public class orsTripLayoutSearch implements Parcelable {
    String TripID, BusType;

    public orsTripLayoutSearch(String tripID, String busType) {
        TripID = tripID;
        BusType = busType;
    }
    protected orsTripLayoutSearch(Parcel in) {
        TripID = in.readString();
        BusType = in.readString();
    }

    public String getTripID() {
        return TripID;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TripID);
        dest.writeString(BusType);

    }


    public static final Creator<orsTripLayoutSearch> CREATOR = new Creator<orsTripLayoutSearch>() {
        @Override
        public orsTripLayoutSearch createFromParcel(Parcel in) {
            return new orsTripLayoutSearch(in);
        }

        @Override
        public orsTripLayoutSearch[] newArray(int size) {
            return new orsTripLayoutSearch[size];
        }
    };

}
