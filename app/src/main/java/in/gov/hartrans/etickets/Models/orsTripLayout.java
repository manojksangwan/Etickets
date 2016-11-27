package in.gov.hartrans.etickets.Models;

/**
 * Created by manojK on 16/11/2016.
 */

public class orsTripLayout {
    int LayoutID;
    String TripID, BusType, Layout_Remarks, Layout_c1, Layout_c2, Layout_c3, Layout_c4, Layout_c5, Layout_c6;
    boolean Layout_c1_Reserved,Layout_c2_Reserved, Layout_c3_Reserved, Layout_c4_Reserved, Layout_c5_Reserved, Layout_c6_Reserved;
    boolean Layout_c1_Online,Layout_c2_Online, Layout_c3_Online, Layout_c4_Online, Layout_c5_Online, Layout_c6_Online;
    boolean Layout_c1_Selected,Layout_c2_Selected, Layout_c3_Selected, Layout_c4_Selected, Layout_c5_Selected, Layout_c6_Selected;

    public orsTripLayout(int layoutID, String tripID, String busType, String layout_Remarks,
                         String layout_c1, String layout_c2, String layout_c3, String layout_c4, String layout_c5, String layout_c6,
                         boolean layout_c1_Reserved, boolean layout_c2_Reserved, boolean layout_c3_Reserved, boolean layout_c4_Reserved, boolean layout_c5_Reserved, boolean layout_c6_Reserved,
                         boolean layout_c1_Online, boolean layout_c2_Online, boolean layout_c3_Online, boolean layout_c4_Online, boolean layout_c5_Online, boolean layout_c6_Online) {
        LayoutID = layoutID;
        TripID = tripID;
        BusType = busType;
        Layout_Remarks = layout_Remarks;
        Layout_c1 = layout_c1;
        Layout_c2 = layout_c2;
        Layout_c3 = layout_c3;
        Layout_c4 = layout_c4;
        Layout_c5 = layout_c5;
        Layout_c6 = layout_c6;
        Layout_c1_Reserved = layout_c1_Reserved;
        Layout_c2_Reserved = layout_c2_Reserved;
        Layout_c3_Reserved = layout_c3_Reserved;
        Layout_c4_Reserved = layout_c4_Reserved;
        Layout_c5_Reserved = layout_c5_Reserved;
        Layout_c6_Reserved = layout_c6_Reserved;
        Layout_c1_Online = layout_c1_Online;
        Layout_c2_Online = layout_c2_Online;
        Layout_c3_Online = layout_c3_Online;
        Layout_c4_Online = layout_c4_Online;
        Layout_c5_Online = layout_c5_Online;
        Layout_c6_Online = layout_c6_Online;
        Layout_c1_Selected = false;
        Layout_c2_Selected = false;
        Layout_c3_Selected = false;
        Layout_c4_Selected = false;
        Layout_c5_Selected = false;
        Layout_c6_Selected = false;
    }

    public int getLayoutID() {
        return LayoutID;
    }

    public void setLayoutID(int layoutID) {
        LayoutID = layoutID;
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

    public String getLayout_Remarks() {
        return Layout_Remarks;
    }

    public void setLayout_Remarks(String layout_Remarks) {
        Layout_Remarks = layout_Remarks;
    }

    public String getLayout_c1() {
        return Layout_c1;
    }

    public void setLayout_c1(String layout_c1) {
        Layout_c1 = layout_c1;
    }

    public String getLayout_c2() {
        return Layout_c2;
    }

    public void setLayout_c2(String layout_c2) {
        Layout_c2 = layout_c2;
    }

    public String getLayout_c3() {
        return Layout_c3;
    }

    public void setLayout_c3(String layout_c3) {
        Layout_c3 = layout_c3;
    }

    public String getLayout_c4() {
        return Layout_c4;
    }

    public void setLayout_c4(String layout_c4) {
        Layout_c4 = layout_c4;
    }

    public String getLayout_c5() {
        return Layout_c5;
    }

    public void setLayout_c5(String layout_c5) {
        Layout_c5 = layout_c5;
    }

    public String getLayout_c6() {
        return Layout_c6;
    }

    public void setLayout_c6(String layout_c6) {
        Layout_c6 = layout_c6;
    }

    public boolean isLayout_c1_Reserved() {
        return Layout_c1_Reserved;
    }

    public void setLayout_c1_Reserved(boolean layout_c1_Reserved) {
        Layout_c1_Reserved = layout_c1_Reserved;
    }

    public boolean isLayout_c2_Reserved() {
        return Layout_c2_Reserved;
    }

    public void setLayout_c2_Reserved(boolean layout_c2_Reserved) {
        Layout_c2_Reserved = layout_c2_Reserved;
    }

    public boolean isLayout_c3_Reserved() {
        return Layout_c3_Reserved;
    }

    public void setLayout_c3_Reserved(boolean layout_c3_Reserved) {
        Layout_c3_Reserved = layout_c3_Reserved;
    }

    public boolean isLayout_c4_Reserved() {
        return Layout_c4_Reserved;
    }

    public void setLayout_c4_Reserved(boolean layout_c4_Reserved) {
        Layout_c4_Reserved = layout_c4_Reserved;
    }

    public boolean isLayout_c5_Reserved() {
        return Layout_c5_Reserved;
    }

    public void setLayout_c5_Reserved(boolean layout_c5_Reserved) {
        Layout_c5_Reserved = layout_c5_Reserved;
    }

    public boolean isLayout_c6_Reserved() {
        return Layout_c6_Reserved;
    }

    public void setLayout_c6_Reserved(boolean layout_c6_Reserved) {
        Layout_c6_Reserved = layout_c6_Reserved;
    }

    public boolean isLayout_c1_Online() {
        return Layout_c1_Online;
    }

    public void setLayout_c1_Online(boolean layout_c1_Online) {
        Layout_c1_Online = layout_c1_Online;
    }

    public boolean isLayout_c2_Online() {
        return Layout_c2_Online;
    }

    public void setLayout_c2_Online(boolean layout_c2_Online) {
        Layout_c2_Online = layout_c2_Online;
    }

    public boolean isLayout_c3_Online() {
        return Layout_c3_Online;
    }

    public void setLayout_c3_Online(boolean layout_c3_Online) {
        Layout_c3_Online = layout_c3_Online;
    }

    public boolean isLayout_c4_Online() {
        return Layout_c4_Online;
    }

    public void setLayout_c4_Online(boolean layout_c4_Online) {
        Layout_c4_Online = layout_c4_Online;
    }

    public boolean isLayout_c5_Online() {
        return Layout_c5_Online;
    }

    public void setLayout_c5_Online(boolean layout_c5_Online) {
        Layout_c5_Online = layout_c5_Online;
    }

    public boolean isLayout_c6_Online() {
        return Layout_c6_Online;
    }

    public void setLayout_c6_Online(boolean layout_c6_Online) {
        Layout_c6_Online = layout_c6_Online;
    }

    public boolean isLayout_c1_Selected() {
        return Layout_c1_Selected;
    }

    public void setLayout_c1_Selected(boolean layout_c1_Selected) {
        Layout_c1_Selected = layout_c1_Selected;
    }

    public boolean isLayout_c2_Selected() {
        return Layout_c2_Selected;
    }

    public void setLayout_c2_Selected(boolean layout_c2_Selected) {
        Layout_c2_Selected = layout_c2_Selected;
    }

    public boolean isLayout_c3_Selected() {
        return Layout_c3_Selected;
    }

    public void setLayout_c3_Selected(boolean layout_c3_Selected) {
        Layout_c3_Selected = layout_c3_Selected;
    }

    public boolean isLayout_c4_Selected() {
        return Layout_c4_Selected;
    }

    public void setLayout_c4_Selected(boolean layout_c4_Selected) {
        Layout_c4_Selected = layout_c4_Selected;
    }

    public boolean isLayout_c5_Selected() {
        return Layout_c5_Selected;
    }

    public void setLayout_c5_Selected(boolean layout_c5_Selected) {
        Layout_c5_Selected = layout_c5_Selected;
    }

    public boolean isLayout_c6_Selected() {
        return Layout_c6_Selected;
    }

    public void setLayout_c6_Selected(boolean layout_c6_Selected) {
        Layout_c6_Selected = layout_c6_Selected;
    }

}