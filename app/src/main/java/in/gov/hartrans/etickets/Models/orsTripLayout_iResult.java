package in.gov.hartrans.etickets.Models;

import com.android.volley.VolleyError;

import java.util.ArrayList;

/**
 * Created by manojK on 16/11/2016.
 */
public interface orsTripLayout_iResult {
    public void notifyError(VolleyError error);
    public void notifySuccess(ArrayList<orsTripLayout> orsTL);
}
