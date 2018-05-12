package in.gov.hartrans.etickets.Models;

import com.android.volley.VolleyError;

import java.util.ArrayList;

/**
 * Created by manojK on 16/11/2016.
 */
public interface orsTripLayout_iResult {
    void notifyError(VolleyError error);
    void notifySuccess(ArrayList<orsTripLayout> orsTL);
}
