package in.gov.hartrans.etickets.Models;

import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by manojK on 09/11/2016.
 */
public interface myIResult {
    public void notifySuccess(JSONObject response);
    public void notifySuccess(JSONArray response);

    public void notifyError(VolleyError error);
    public void notifySuccess(ArrayList<orsAvailableServices> orsAS);
}
