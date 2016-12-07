package in.gov.hartrans.etickets.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by manojK on 07/12/2016.
 */
public class appStatusTask {
    eTicketInfoUpdate_iResult iResult;
    Context context;
    String json_url = "http://hrtransport.in/ors/api/orsAppStatus";

    public appStatusTask(Context context)
    {
        this.context = context;
        this.iResult = (eTicketInfoUpdate_iResult) context;
    }
    public void getAppStatus()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + response);
                        boolean DidError=true;
                        String ErrorMessage="error";
                        String message;
                        //JSONArray ja = null;
                        try {
                            DidError = response.getBoolean("didError");
                            ErrorMessage = response.getString("errorMessage");
                            message = response.getString("message");

                            if (DidError && message.equals("NOT"))
                            {
                                DidError = false;
                            }
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
                //Toast.makeText(context,"error....", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        myVolleyService.getInstance(context).addToRequestQueue(jsonObjectRequest);
        //return arList;
    }
}
