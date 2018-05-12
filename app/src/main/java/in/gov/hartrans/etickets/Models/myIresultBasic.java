package in.gov.hartrans.etickets.Models;

import com.android.volley.VolleyError;
import org.json.JSONObject;

/**
 * Created by manojK on 25/12/2016.
 */
public interface myIresultBasic {
    void notifySuccess(JSONObject response);
    void notifyError(VolleyError error);
}
