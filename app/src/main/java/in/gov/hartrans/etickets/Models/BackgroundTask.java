package in.gov.hartrans.etickets.Models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by manojK on 06/11/2016.
 */
public class BackgroundTask {
    Context context;
    ArrayList<BookingDays> arrayList = new ArrayList<>();
    String json_url = "http://hartrans.gov.in/ors/api/bookingdays";

    public BackgroundTask(Context context)
    {
        this.context = context;
    }

    public ArrayList<BookingDays> getList()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Log.d("myApp", "backgroundTask JSON post" + response);

                        int count=0;
                        while (count<response.length())
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                BookingDays bookingDays = new BookingDays(jsonObject.getString("stationID"), jsonObject.getString("stationName"));
                                arrayList.add(bookingDays);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"error....", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        myVolleyService.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return arrayList;
    }
}