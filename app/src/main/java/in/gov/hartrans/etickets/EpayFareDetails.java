package in.gov.hartrans.etickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AutoCompleteTextView;

import in.gov.hartrans.etickets.Models.orsAvailableServices_onRoute;

public class EpayFareDetails extends AppCompatActivity {

    orsAvailableServices_onRoute orsAVS;
    AppCompatTextView header_title=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epay_fare_details);

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.my_ePayFareDetails_title);
        getSupportActionBar().setSubtitle(R.string.my_eTikcet_options_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);

        header_title = (AppCompatTextView) findViewById(R.id.header_title);

        Intent i = getIntent();
        orsAVS = i.getExtras().getParcelable("orsAvailableServices_onRoute");

        String hdTitle = orsAVS.getrDesc() + "<br/>" + orsAVS.getBusNumber();
        header_title.setText(Html.fromHtml(hdTitle));
    }
}
