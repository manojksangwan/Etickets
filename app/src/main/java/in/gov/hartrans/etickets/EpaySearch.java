package in.gov.hartrans.etickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.gov.hartrans.etickets.Models.myIresultBasic;
import in.gov.hartrans.etickets.Models.orsAvailableServices_onRoute;
import in.gov.hartrans.etickets.Models.orsAvailableServices_onRouteTask;

public class EpaySearch extends AppCompatActivity implements myIresultBasic {
    private CoordinatorLayout coordinatorLayout;
    Button bt_search=null;
    AutoCompleteTextView tv_searchValue=null;

    TextView tv_message=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epay_search);

        bt_search = (Button)  findViewById(R.id.bt_search);
        tv_searchValue = (AutoCompleteTextView) findViewById(R.id.tv_SearchValue);
        tv_message = (TextView)  findViewById(R.id.tv_message);
        tv_message.setVisibility(View.INVISIBLE);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.my_ePaySearch_title);
        getSupportActionBar().setSubtitle(R.string.my_eTikcet_options_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = tv_searchValue.getText().toString();
                if (searchValue.equals("") || searchValue.length()>10 || searchValue.length()<4)
                {
                    tv_message.setText("Invalid Bus No./Conductor's mobile No.");
                    tv_message.setVisibility(View.VISIBLE);
                    return;
                }
                tv_message.setVisibility(View.INVISIBLE);
                orsAvailableServices_onRouteTask tsk = new orsAvailableServices_onRouteTask(EpaySearch.this);
                tsk.getAvailableServices_onRoute(searchValue);
            }
        });
    }

    @Override
    public void notifySuccess(JSONObject response) {
        try {
            boolean DidError = true;
            String ErrorMessage = "error";
            DidError = response.getBoolean("didError");
            ErrorMessage = response.getString("errorMessage");
            if (DidError)
            {
                Snackbar snackbar = Snackbar.make(coordinatorLayout,ErrorMessage, Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
            Date jDateTime = new Date();
            JSONArray ja = response.getJSONArray("model");
            JSONObject jo = ja.getJSONObject(0);
            jDateTime =  formatter.parse(jo.getString("jDateTime"));

            orsAvailableServices_onRoute orsAS = new orsAvailableServices_onRoute(
                 jo.getInt("orTimetableID"), jo.getInt("depotID"), jo.getInt("trip_srno"),jo.getInt("trip_id"),
                    jo.getInt("rKMS"),jo.getInt("rFare"),jo.getInt("rTripID"), jo.getString("depotShortName"),
                    jo.getString("depotName"),jo.getString("tripID"), jo.getString("tripCode"),jo.getString("busType"),
                    jo.getString("tripRoute"), jo.getString("leaving"), jo.getString("departing"),jo.getString("via"),
                    jo.getString("rDesc"), jo.getString("busNumber"),jo.getString("cndName"), jo.getString("cndMobileNo"),
                    jo.getString("cndWaybillID"), jDateTime
            );

            Intent intent = new Intent(EpaySearch.this, EpayFareDetails.class);
            intent.putExtra("orsAvailableServices_onRoute", orsAS);
            startActivity(intent);
        }catch (Exception ex)
        {
            Snackbar snackbar = Snackbar.make(coordinatorLayout,ex.getMessage().toString(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
    }

    @Override
    public void notifyError(VolleyError error) {
        //Toast.makeText(this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
        //Snackbar.make(coordinatorLayout, error.getMessage().toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Snackbar snackbar = Snackbar.make(coordinatorLayout, error.getMessage().toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
