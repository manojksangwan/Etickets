package in.gov.hartrans.etickets.Models;

import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by manojK on 09/11/2016.
 */
public interface myIResult {
    void notifySuccess(JSONObject response);
    void notifySuccess(JSONArray response);

    void notifyError(VolleyError error);
    void notifySuccess(ArrayList<orsAvailableServices> orsAS);
}
