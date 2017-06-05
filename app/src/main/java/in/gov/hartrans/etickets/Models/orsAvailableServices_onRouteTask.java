package in.gov.hartrans.etickets.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manojK on 03/01/2017.
 */
public class orsAvailableServices_onRouteTask {
    myIresultBasic iResult;
    Context context;
    String json_url = "http://hrtransport.gov.in/ors/api/orsAvailableServices_onRoute";
    public orsAvailableServices_onRouteTask(Context context)
    {
        this.context = context;
        this.iResult = (myIresultBasic) context;
    }
    public void getAvailableServices_onRoute(String searchValue)
    {
        final Map<String, String> params = new HashMap<String,String>();
        params.put("sLeaving",searchValue);
        params.put("sDeparting",searchValue);
        params.put("busType",searchValue);
        params.put("dDate", "01/01/1900" );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, json_url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                iResult.notifySuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iResult.notifyError(error);
                error.printStackTrace();
            }
        });
        myVolleyService.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
