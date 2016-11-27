package in.gov.hartrans.etickets.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by manojK on 06/11/2016.
 */
public class myVolleyService {
    private static myVolleyService mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private myVolleyService(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized myVolleyService getInstance(Context context){
        if(mInstance==null){
            mInstance = new myVolleyService(context);
        }
        return mInstance;
    }
    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
