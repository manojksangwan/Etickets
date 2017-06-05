package in.gov.hartrans.etickets.Models;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manojK on 04/01/2017.
 */
public class eTicketInfoUpdate_onRouteTask {
    eTicketInfoUpdate_iResult iResult;
    Context context;
    ArrayList<orsAvailableServices> arList = new ArrayList<>();
    String json_url = "http://hrtransport.gov.in/ors/api/orsEticketInfoUpdate_onRoute";

    public eTicketInfoUpdate_onRouteTask(Context context)
    {
        this.context = context;
        this.iResult = (eTicketInfoUpdate_iResult) context;
    }


    public void eTicketInfo_update(orsAvailableServices orsAS)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm aa");
        String secureCode = orsAS.getSecureCode();
        final Map<String, String> params = new HashMap<String,String>();
            params.put("secureCode", "NEW");
            params.put("trip_srno", (orsAS.getTrip_srno()) + "");
            params.put("tripID", (orsAS.getTripID()) + "");
            params.put("jDateTime", df.format(orsAS.getjTime1()));
            params.put("totalSeats", (orsAS.gettSeats()) + "");
            params.put("totalFare", (orsAS.getTotalFare()) + "");
            params.put("pName", orsAS.getpName());
            params.put("pPhone", orsAS.getpPhone());
            params.put("pEmail", orsAS.getpEmail());
        params.put("busNumber", orsAS.getpName1());
        params.put("cndName", orsAS.getpName2());
        params.put("cndMobileNo", orsAS.getpName3());
        params.put("cndWaybillID", orsAS.getpName4());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,
                json_url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean DidError=true;
                        String ErrorMessage="error";
                        //JSONArray ja = null;
                        try {
                            DidError = response.getBoolean("didError");
                            ErrorMessage = response.getString("errorMessage");
                            //ja = response.getJSONArray("model");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        iResult.notify_eTicketInfoUpdate_Success(DidError, ErrorMessage );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iResult.notify_eTicketInfoUpdate_Error(error);
                error.printStackTrace();
            }
        });
        myVolleyService.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
