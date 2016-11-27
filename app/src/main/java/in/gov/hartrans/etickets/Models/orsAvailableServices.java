package in.gov.hartrans.etickets.Models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by manojK on 07/11/2016.
 */
public class orsAvailableServices implements Parcelable {
    int trip_srno, ID, onlineSeats, rKMS, rFare, DepotID, ReservationCharges, rTripID, TripID, totalSeats, availableSeats,closeTime;
    String busType, tripCode, Leaving, Departing, Via, rDesc, boarding, plateform, dropping, TripRoute,depotShortName;
    Date jTime1;

    String prfSeats, pName, pPhone, pEmail, iProof, pName1, pName2, pName3, pName4, pGender1, pGender2, pGender3, pGender4;
    int tSeats, pSeat1, pSeat2, pSeat3, pSeat4, totalFare, rCharges, pAge1, pAge2, pAge3, pAge4;

    public orsAvailableServices(int trip_srno, int ID, int onlineSeats, int rKMS, int rFare, int depotID, int reservationCharges, int rTripID, int tripID, int totalSeats, int availableSeats, int closeTime, String busType, String tripCode, String leaving, String departing, String via, String rDesc, String boarding, String plateform, String dropping, String tripRoute, String depotShortName, Date jTime1) {
        this.setTrip_srno(trip_srno);
        this.setID(ID);
        this.onlineSeats = onlineSeats;
        this.rKMS = rKMS;
        this.rFare = rFare;
        DepotID = depotID;
        ReservationCharges = reservationCharges;
        this.rTripID = rTripID;
        TripID = tripID;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.closeTime = closeTime;
        this.busType = busType;
        this.tripCode = tripCode;
        this.Leaving = leaving;
        this.Departing = departing;
        this.Via = via;
        this.rDesc = rDesc;
        this.boarding = boarding;
        this.plateform = plateform;
        this.dropping = dropping;
        TripRoute = tripRoute;
        this.depotShortName = depotShortName;
        this.setjTime1(jTime1);
    }

    protected orsAvailableServices(Parcel in) {
        trip_srno = in.readInt();
        ID = in.readInt();
        onlineSeats = in.readInt();
        rKMS = in.readInt();
        rFare = in.readInt();
        DepotID = in.readInt();
        ReservationCharges = in.readInt();
        rTripID = in.readInt();
        TripID = in.readInt();
        totalSeats = in.readInt();
        availableSeats = in.readInt();
        closeTime = in.readInt();
        busType = in.readString();
        tripCode = in.readString();
        Leaving = in.readString();
        Departing = in.readString();
        Via = in.readString();
        rDesc = in.readString();
        boarding = in.readString();
        plateform = in.readString();
        dropping = in.readString();
        TripRoute = in.readString();
        depotShortName = in.readString();
        jTime1 = (Date) in.readSerializable();
        prfSeats= in.readString();

        tSeats = in.readInt();
        pSeat1 = in.readInt();
        pSeat2 = in.readInt();
        pSeat3 = in.readInt();
        pSeat4 = in.readInt();
        pName1 = in.readString();
        pName2 = in.readString();
        pName3 = in.readString();
        pName4 = in.readString();
        pGender1 = in.readString();
        pGender2 = in.readString();
        pGender3 = in.readString();
        pGender4 = in.readString();
        pAge1 = in.readInt();
        pAge2 = in.readInt();
        pAge3 = in.readInt();
        pAge4 = in.readInt();
        totalFare = in.readInt();
        rCharges= in.readInt();
        pName = in.readString();
        pPhone = in.readString();
        pEmail = in.readString();
        iProof = in.readString();
    }

    public static final Creator<orsAvailableServices> CREATOR = new Creator<orsAvailableServices>() {
        @Override
        public orsAvailableServices createFromParcel(Parcel in) {
            return new orsAvailableServices(in);
        }

        @Override
        public orsAvailableServices[] newArray(int size) {
            return new orsAvailableServices[size];
        }
    };

    public int getTrip_srno() {
        return trip_srno;
    }

    public void setTrip_srno(int trip_srno) {
        this.trip_srno = trip_srno;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOnlineSeats() {
        return onlineSeats;
    }

    public void setOnlineSeats(int onlineSeats) {
        this.onlineSeats = onlineSeats;
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

    public int getDepotID() {
        return DepotID;
    }

    public void setDepotID(int depotID) {
        DepotID = depotID;
    }

    public int getReservationCharges() {
        return ReservationCharges;
    }

    public void setReservationCharges(int reservationCharges) {
        ReservationCharges = reservationCharges;
    }

    public int getrTripID() {
        return rTripID;
    }

    public void setrTripID(int rTripID) {
        this.rTripID = rTripID;
    }

    public int getTripID() {
        return TripID;
    }

    public void setTripID(int tripID) {
        TripID = tripID;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
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
        return Via;
    }

    public void setVia(String via) {
        Via = via;
    }

    public String getrDesc() {
        return rDesc;
    }

    public void setrDesc(String rDesc) {
        this.rDesc = rDesc;
    }

    public String getBoarding() {
        return boarding;
    }

    public void setBoarding(String boarding) {
        this.boarding = boarding;
    }

    public String getPlateform() {
        return plateform;
    }

    public void setPlateform(String plateform) {
        this.plateform = plateform;
    }

    public String getDropping() {
        return dropping;
    }

    public void setDropping(String dropping) {
        this.dropping = dropping;
    }

    public String getTripRoute() {
        return TripRoute;
    }

    public void setTripRoute(String tripRoute) {
        TripRoute = tripRoute;
    }

    public String getDepotShortName() {
        return depotShortName;
    }

    public void setDepotShortName(String depotShortName) {
        this.depotShortName = depotShortName;
    }

    public Date getjTime1() {
        return jTime1;
    }

    public void setjTime1(Date jTime1) {
        this.jTime1 = jTime1;
    }

    public String getPrfSeats() {
        return prfSeats;
    }

    public void setPrfSeats(String prfSeats) {
        this.prfSeats = prfSeats;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPhone() {
        return pPhone;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getiProof() {
        return iProof;
    }

    public void setiProof(String iProof) {
        this.iProof = iProof;
    }

    public String getpName1() {
        return pName1;
    }

    public void setpName1(String pName1) {
        this.pName1 = pName1;
    }

    public String getpName2() {
        return pName2;
    }

    public void setpName2(String pName2) {
        this.pName2 = pName2;
    }

    public String getpName3() {
        return pName3;
    }

    public void setpName3(String pName3) {
        this.pName3 = pName3;
    }

    public String getpName4() {
        return pName4;
    }

    public void setpName4(String pName4) {
        this.pName4 = pName4;
    }

    public String getpGender1() {
        return pGender1;
    }

    public void setpGender1(String pGender1) {
        this.pGender1 = pGender1;
    }

    public String getpGender2() {
        return pGender2;
    }

    public void setpGender2(String pGender2) {
        this.pGender2 = pGender2;
    }

    public String getpGender3() {
        return pGender3;
    }

    public void setpGender3(String pGender3) {
        this.pGender3 = pGender3;
    }

    public String getpGender4() {
        return pGender4;
    }

    public void setpGender4(String pGender4) {
        this.pGender4 = pGender4;
    }

    public int getpSeat1() {
        return pSeat1;
    }

    public void setpSeat1(int pSeat1) {
        this.pSeat1 = pSeat1;
    }

    public int getpSeat2() {
        return pSeat2;
    }

    public void setpSeat2(int pSeat2) {
        this.pSeat2 = pSeat2;
    }

    public int getpSeat3() {
        return pSeat3;
    }

    public void setpSeat3(int pSeat3) {
        this.pSeat3 = pSeat3;
    }

    public int getpSeat4() {
        return pSeat4;
    }

    public void setpSeat4(int pSeat4) {
        this.pSeat4 = pSeat4;
    }

    public int getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }

    public int getrCharges() {
        return rCharges;
    }

    public void setrCharges(int rCharges) {
        this.rCharges = rCharges;
    }

    public int gettSeats() {
        return tSeats;
    }

    public void settSeats(int tSeats) {
        this.tSeats = tSeats;
    }

    public int getpAge1() {
        return pAge1;
    }

    public void setpAge1(int pAge1) {
        this.pAge1 = pAge1;
    }

    public int getpAge2() {
        return pAge2;
    }

    public void setpAge2(int pAge2) {
        this.pAge2 = pAge2;
    }

    public int getpAge3() {
        return pAge3;
    }

    public void setpAge3(int pAge3) {
        this.pAge3 = pAge3;
    }

    public int getpAge4() {
        return pAge4;
    }

    public void setpAge4(int pAge4) {
        this.pAge4 = pAge4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(trip_srno);
        dest.writeInt(ID);
        dest.writeInt(onlineSeats);
        dest.writeInt(rKMS);
        dest.writeInt(rFare);
        dest.writeInt(DepotID);
        dest.writeInt(ReservationCharges);
        dest.writeInt(rTripID);
        dest.writeInt(TripID);
        dest.writeInt(totalSeats);
        dest.writeInt(availableSeats);
        dest.writeInt(closeTime);
        dest.writeString(busType);
        dest.writeString(tripCode);
        dest.writeString(Leaving);
        dest.writeString(Departing);
        dest.writeString(Via);
        dest.writeString(rDesc);
        dest.writeString(boarding);
        dest.writeString(plateform);
        dest.writeString(dropping);
        dest.writeString(TripRoute);
        dest.writeString(depotShortName);
        dest.writeSerializable(jTime1);

        dest.writeString(prfSeats);
        dest.writeInt(tSeats);
        dest.writeInt(pSeat1);
        dest.writeInt(pSeat2);
        dest.writeInt(pSeat3);
        dest.writeInt(pSeat4);
        dest.writeString(pName1);
        dest.writeString(pName2);
        dest.writeString(pName3);
        dest.writeString(pName4);
        dest.writeString(pGender1);
        dest.writeString(pGender2);
        dest.writeString(pGender3);
        dest.writeString(pGender4);
        dest.writeInt(pAge1);
        dest.writeInt(pAge2);
        dest.writeInt(pAge3);
        dest.writeInt(pAge4);
        dest.writeInt(totalFare);
        dest.writeInt(rCharges);
        dest.writeString(pName);
        dest.writeString(pPhone);
        dest.writeString(pEmail);
        dest.writeString(iProof);
    }
}
