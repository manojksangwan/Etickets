package in.gov.hartrans.etickets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;
import in.gov.hartrans.etickets.Models.appStatusTask;
import in.gov.hartrans.etickets.Models.myIresultBasic;


public class MainActivity extends AppCompatActivity implements myIresultBasic {
    protected boolean isInternetAvailable = true;
    protected boolean _active = true;
    protected int _splashTime = 3000; // time to display the splash screen in ms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!appStatus.getInstance(this).isOnline()) {
            Toast.makeText(this, "Internet connection not available.", Toast.LENGTH_LONG).show();
            isInternetAvailable = false;
            // finish();
            // return;
        }

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {
                    if (isInternetAvailable) {
                        appStatusTask app_Task = new appStatusTask(MainActivity.this);
                        app_Task.getAppStatus();
                    }
                    else {
                        finish();
                    }
                }
            }
        };
        splashTread.start();

    }

    @Override
    public void notifySuccess(JSONObject response) {
        try {
            String appVersionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;

            boolean DidError = true;
            String ErrorMessage = "error";
            DidError = response.getBoolean("didError");
            ErrorMessage = response.getString("errorMessage");
            if (DidError)
            {
                Toast.makeText(this, ErrorMessage, Toast.LENGTH_LONG).show();
                finish();
            }
            JSONArray ja = response.getJSONArray("model");
            //Toast.makeText(this, appVersionName +"="+ja.getJSONObject(0).getString("appVersion"), Toast.LENGTH_LONG).show();

            if ( (!ja.getJSONObject(0).getBoolean("appAllowBooking")) || !appVersionName.equals(ja.getJSONObject(0).getString("appVersion")) )
            {
                Intent myORSstatus = new Intent(this, orsStatus.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("DidError", ja.getJSONObject(0).getBoolean("appAllowBooking") + "" );
                mBundle.putString("ErrorMessage", ja.getJSONObject(0).getString("appMessage"));
                myORSstatus.putExtras(mBundle);

                startActivity(myORSstatus);
                finish();
            }
            else {

                //Intent bookEticket = new Intent(this, BookEticketActivity.class);
                //startActivity(bookEticket);

                Intent eTicketOptions = new Intent(this, EticketOptions.class);
                startActivity(eTicketOptions);
                finish();
            }

        }catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            // ex.printStackTrace();
            finish();
        }
    }

    @Override
    public void notifyError(VolleyError error) {
        Toast.makeText(this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
        finish();
    }

    protected void getDeviceDensity(Context context)
    {
        int density= getResources().getDisplayMetrics().densityDpi;
        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:
                Toast.makeText(context, "LDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Toast.makeText(context, "MDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_HIGH:
                Toast.makeText(context, "HDPI", Toast.LENGTH_SHORT).show();
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Toast.makeText(context, "XHDPI", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
