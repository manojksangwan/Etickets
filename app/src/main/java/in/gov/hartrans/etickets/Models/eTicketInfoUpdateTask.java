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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manojK on 05/12/2016.
 */
public class eTicketInfoUpdateTask {
    eTicketInfoUpdate_iResult iResult;
    Context context;
    ArrayList<orsAvailableServices> arList = new ArrayList<>();
    String json_url = "http://hrtransport.gov.in/ors/api/orsEticketInfoUpdate";

    public eTicketInfoUpdateTask(Context context)
    {
        this.context = context;
        this.iResult = (eTicketInfoUpdate_iResult) context;
    }


    public void eTicketInfo_update(orsAvailableServices orsAS, String caller)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm aa");
        String secureCode = orsAS.getSecureCode();

        final Map<String, String> params = new HashMap<String,String>();
        if (caller.equals("TicketInfo")) {
            params.put("secureCode", "NEW");
            params.put("trip_srno", (orsAS.getTrip_srno()) + "");
            params.put("tripID", (orsAS.getTripID()) + "");
            params.put("jDateTime", df.format(orsAS.getjTime1()));
            params.put("totalSeats", (orsAS.gettSeats()) + "");
            params.put("prfSeats", orsAS.getPrfSeats());
            params.put("pSeat1", (orsAS.getpSeat1()) + "");
            params.put("pSeat2", (orsAS.getpSeat2()) + "");
            params.put("pSeat3", (orsAS.getpSeat3()) + "");
            params.put("pSeat4", (orsAS.getpSeat4()) + "");
        }
        if (caller.equals("PassengerInfo") && !secureCode.equals("NEW") &&  !secureCode.equals("")) {
            params.put("secureCode", orsAS.getSecureCode() );
            params.put("pName", orsAS.getpName());
            params.put("pPhone", orsAS.getpPhone());
            params.put("pEmail", orsAS.getpEmail());
            params.put("iProof", orsAS.getiProof());
            params.put("pName1", orsAS.getpName1());
            params.put("pName2", orsAS.getpName2());
            params.put("pName3", orsAS.getpName3());
            params.put("pName4", orsAS.getpName4());
            params.put("pGender1", orsAS.getpGender1());
            params.put("pGender2", orsAS.getpGender2());
            params.put("pGender3", orsAS.getpGender3());
            params.put("pGender4", orsAS.getpGender4());
            params.put("pAge1", orsAS.getpAge1()+"");
            params.put("pAge2", orsAS.getpAge2()+"");
            params.put("pAge3", orsAS.getpAge3()+"");
            params.put("pAge4", orsAS.getpAge4()+"");
        }
        //Log.d("myApp", "orsAvailableServices Task JSON post-response  " + df.format (orsAS.getjTime1()));
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

                        //Log.d("myApp", "orsTripLayout Task JSON post-response  " + ja);

                        /*
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
                        */
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