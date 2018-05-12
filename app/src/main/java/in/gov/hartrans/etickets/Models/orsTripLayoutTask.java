package in.gov.hartrans.etickets.Models;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.gov.hartrans.etickets.BuildConfig;

/**
 * Created by manojK on 16/11/2016.
 */
public class orsTripLayoutTask {
    orsTripLayout_iResult iResult;
    Context context;
    ArrayList<orsTripLayout> arList = new ArrayList<>();
    String json_url = BuildConfig.API_URL + "/orsTripLayout";

    public orsTripLayoutTask(Context context)
    {
        this.context = context;
        this.iResult = (orsTripLayout_iResult) context;
    }


    public void getTripLayout(orsTripLayoutSearch orsTLS)
    {
        final Map<String, String> params = new HashMap<String,String>();
        params.put("tripID",orsTLS.getTripID());
        params.put("busType",orsTLS.getBusType());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + response);

                        JSONArray ja = null;
                        try {
                            ja = response.getJSONArray("model");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("myApp", "orsTripLayout Task JSON post-response  " + ja);

                        int count = 0;
                        while (count < ja.length()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = ja.getJSONObject(count);

                                orsTripLayout orsTL = new orsTripLayout(jsonObject.getInt("layoutID"), jsonObject.getString("tripID"), jsonObject.getString("busType"),
                                        jsonObject.getString("layout_Remarks"), jsonObject.getString("layout_c1"), jsonObject.getString("layout_c2"), jsonObject.getString("layout_c3"), jsonObject.getString("layout_c4"), jsonObject.getString("layout_c5"), jsonObject.getString("layout_c6"),
                                        jsonObject.getBoolean("layout_c1_Reserved"), jsonObject.getBoolean("layout_c2_Reserved"), jsonObject.getBoolean("layout_c3_Reserved"), jsonObject.getBoolean("layout_c4_Reserved"), jsonObject.getBoolean("layout_c5_Reserved"), jsonObject.getBoolean("layout_c6_Reserved"),
                                        jsonObject.getBoolean("layout_c1_Online"),jsonObject.getBoolean("layout_c2_Online"),jsonObject.getBoolean("layout_c3_Online"),jsonObject.getBoolean("layout_c4_Online"),jsonObject.getBoolean("layout_c5_Online"),jsonObject.getBoolean("layout_c6_Online")
                                        );
                                arList.add(orsTL);
                                count++;
                            } catch (JSONException e) {
                                count++;
                                e.printStackTrace();
                            }
                        }
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
