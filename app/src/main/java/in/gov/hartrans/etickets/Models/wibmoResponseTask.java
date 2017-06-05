package in.gov.hartrans.etickets.Models;

import android.content.Context;
import android.util.Log;

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
 * Created by manojK on 05/12/2016.
 */
public class wibmoResponseTask {
    eTicketInfoUpdate_iResult iResult;
    Context context;
    String json_url = "http://hrtransport.gov.in/ors/api/WibmoPGresponse";

    public wibmoResponseTask(Context context)
    {
        this.context = context;
        this.iResult = (eTicketInfoUpdate_iResult) context;
    }


    public void WibmoPGresponse_update(wibmoResponse wr)
    {
        final Map<String, String> params = new HashMap<String,String>();
            params.put("resCode", wr.resCode);
            params.put("resDesc", wr.resDesc);
            params.put("wPayTxnId", wr.wPayTxnId);
            params.put("merAppData", wr.merAppData);
            params.put("merTxnId", wr.merTxnId);
            params.put("msgHash", wr.msgHash);
            params.put("dataPickUpCode", wr.dataPickUpCode);


        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + "started");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + response);

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
                //Toast.makeText(context,"error....", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        myVolleyService.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
