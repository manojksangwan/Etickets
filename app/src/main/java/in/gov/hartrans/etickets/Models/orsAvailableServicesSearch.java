package in.gov.hartrans.etickets.Models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by manojK on 07/11/2016.
 */
public class orsAvailableServicesSearch implements Parcelable {
    String sLeaving, sDeparting, busType,dDate;
    //Date dDate;

    public orsAvailableServicesSearch(String sLeaving, String sDeparting, String busType, String dDate) {
        this.setsLeaving(sLeaving);
        this.setsDeparting(sDeparting);
        this.setBusType(busType);
        this.setdDate(dDate);
    }

    protected orsAvailableServicesSearch(Parcel in) {
        sLeaving = in.readString();
        sDeparting = in.readString();
        busType = in.readString();
        dDate = in.readString();
    }

    public static final Creator<orsAvailableServicesSearch> CREATOR = new Creator<orsAvailableServicesSearch>() {
        @Override
        public orsAvailableServicesSearch createFromParcel(Parcel in) {
            return new orsAvailableServicesSearch(in);
        }

        @Override
        public orsAvailableServicesSearch[] newArray(int size) {
            return new orsAvailableServicesSearch[size];
        }
    };

    public String getsLeaving() {
        return sLeaving;
    }

    public void setsLeaving(String sLeaving) {
        this.sLeaving = sLeaving;
    }

    public String getsDeparting() {
        return sDeparting;
    }

    public void setsDeparting(String sDeparting) {
        this.sDeparting = sDeparting;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sLeaving);
        dest.writeString(sDeparting);
        dest.writeString(busType);
        dest.writeString(dDate);
    }
}
