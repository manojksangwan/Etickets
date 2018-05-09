package in.gov.hartrans.etickets.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manojK on 25/12/2016.
 */
public class upiResponseTask {
    eTicketInfoUpdate_iResult iResult;
    Context context;
    String json_url = "http://hartrans.gov.in/ors/api/upiPGresponse";
    public upiResponseTask(Context context)
    {
        this.context = context;
        this.iResult = (eTicketInfoUpdate_iResult) context;
    }
    public void upiPGresponse_update(upiResponse wr)
    {
        final Map<String, String> params = new HashMap<String,String>();
        params.put("pgMetrnRefNo", wr.pgMetrnRefNo);
        params.put("orderNo", wr.orderNo);
        params.put("TxnAmount", wr.TxnAmount);
        params.put("trnAuthDate", wr.trnAuthDate);
        params.put("approvalCode", wr.approvalCode);
        params.put("payerVA", wr.payerVA);
        params.put("npciTxnID", wr.npciTxnID);
        params.put("refID", wr.refID);
        params.put("status", wr.status);
        params.put("statusDesc", wr.statusDesc);
        params.put("responseCode", wr.responseCode);

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
