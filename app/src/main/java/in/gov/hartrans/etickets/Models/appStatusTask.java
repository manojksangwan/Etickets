package in.gov.hartrans.etickets.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by manojK on 07/12/2016.
 */
public class appStatusTask {
    myIresultBasic iResult;
    Context context;
    String json_url = "http://hrtransport.in/ors/api/orsAppStatus";
    ArrayList<appStatus> arList = new ArrayList<>();

    public appStatusTask(Context context)
    {
        this.context = context;
        this.iResult = (myIresultBasic) context;
    }
    public void getAppStatus()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + response);
                        /*
                        boolean DidError=true;
                        String ErrorMessage="error";
                        String message;
                        JSONArray ja = null;
                        try {
                            DidError = response.getBoolean("didError");
                            ErrorMessage = response.getString("errorMessage");
                            message = response.getString("message");

                            if (DidError && message.equals("NOT"))
                            {
                                DidError = false;
                            }
                            ja = response.getJSONArray("model");
                            int count = 0;
                            while (count < ja.length())
                            {
                                JSONObject jsonObject = null;
                                jsonObject = ja.getJSONObject(count);
                                appStatus aS  = new appStatus(jsonObject.getBoolean("appAllowBooking"),
                                        jsonObject.getBoolean("allowCCDC"),
                                        jsonObject.getBoolean("allowUPI"),
                                        jsonObject.getString("appMessage"),
                                        jsonObject.getString("appVersion"));

                                arList.add(aS);
                                count++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        */
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
