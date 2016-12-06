package in.gov.hartrans.etickets.Models;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by manojK on 07/11/2016.
 */
public class orsAvailableServicesTask implements Serializable {
    myIResult iResult;
    Context context;
    ArrayList<orsAvailableServices> arList = new ArrayList<>();
    String json_url = "http://hrtransport.in/ors/api/orsAvailableServices";

    public orsAvailableServicesTask(Context context)
    {
        this.context = context;
        this.iResult = (myIResult) context;
    }

    public void getList(orsAvailableServicesSearch orsAVSS)
    {
        final Map<String, String> params = new HashMap<String,String>();
        params.put("sLeaving",orsAVSS.getsLeaving());
        params.put("sDeparting",orsAVSS.getsDeparting());
        params.put("busType",orsAVSS.getBusType());
        params.put("dDate",orsAVSS.getdDate());

//        String pp = "sLeaving=Chandigarh&sDeparting=Delhi&dDate=11-jul-2016";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + response);

                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
                        JSONArray ja = null;
                        try {
                            ja = response.getJSONArray("model");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("myApp", "orsAvailableServices Task JSON post-response  " + ja);

                        int count = 0;
                        while (count < ja.length()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = ja.getJSONObject(count);

                                Date jTime1 = new Date();

                                try {
                                    jTime1 =  formatter.parse(jsonObject.getString("jTime1"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                orsAvailableServices orsAV = new orsAvailableServices(jsonObject.getInt("trip_srno"), jsonObject.getInt("id"), jsonObject.getInt("onlineSeats"), jsonObject.getInt("rKMS"), jsonObject.getInt("rFare"), jsonObject.getInt("depotID"), jsonObject.getInt("reservationCharges"), jsonObject.getInt("rTripID"), jsonObject.getInt("tripID"), jsonObject.getInt("totalSeats"), jsonObject.getInt("availableSeats"), jsonObject.getInt("closeTime"), jsonObject.getString("busType"), jsonObject.getString("tripCode"), jsonObject.getString("leaving"), jsonObject.getString("departing"), jsonObject.getString("via"), jsonObject.getString("rDesc"), jsonObject.getString("boarding"), jsonObject.getString("plateform"), jsonObject.getString("dropping"), jsonObject.getString("tripRoute"), jsonObject.getString("depotShortName"), jTime1);
                                //Log.d("myApp", "class object -response  " + orsAV);
                                arList.add(orsAV);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Log.d("myApp", "ors_availableServices TASK -response  " + arList);
                        iResult.notifySuccess(arList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iResult.notifyError(error);
                //Toast.makeText(context,"error....", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        /*
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                //headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
*/
        myVolleyService.getInstance(context).addToRequestQueue(jsonObjectRequest);
        //return arList;
    }
}
