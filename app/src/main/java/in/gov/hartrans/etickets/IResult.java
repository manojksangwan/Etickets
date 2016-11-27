package in.gov.hartrans.etickets;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by manojK on 18/08/2016.
 */
public interface IResult {
    public void notifySuccess(String requestType, JSONObject response);
    public void notifySuccess(String requestType, JSONArray response);
    public void notifyError(String requestType, VolleyError error);
}