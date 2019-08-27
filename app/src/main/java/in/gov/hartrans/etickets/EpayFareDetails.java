package in.gov.hartrans.etickets;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.gov.hartrans.etickets.Models.eTicketInfoUpdateTask;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdate_iResult;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdate_onRouteTask;
import in.gov.hartrans.etickets.Models.orsAvailableServices;
import in.gov.hartrans.etickets.Models.orsAvailableServices_onRoute;

public class EpayFareDetails extends AppCompatActivity implements eTicketInfoUpdate_iResult {
    WebView web;
    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
    orsAvailableServices_onRoute orsAVS;
    AppCompatTextView header_title=null;
    ImageView iv_bus=null;

    Button bt_pay_cc_dc;
    RadioButton paywith_cc_dc, paywith_upi;
    AutoCompleteTextView tv_customerName, tv_emailID, tv_phoneNo, tv_pNo, tv_totalFare;
    ProgressDialog dialog;
    orsAvailableServices orsAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epay_fare_details);

        dialog = ProgressDialog.show(this, "", "Please wait", true);
        dialog.setInverseBackgroundForced(true);
        dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.my_ePayFareDetails_title);
        getSupportActionBar().setSubtitle(R.string.my_eTikcet_options_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);

        header_title = (AppCompatTextView) findViewById(R.id.header_title);
        iv_bus = (ImageView) findViewById(R.id.iv_bus);

        tv_customerName = (AutoCompleteTextView) findViewById(R.id.tv_customerName);
        tv_emailID = (AutoCompleteTextView) findViewById(R.id.tv_emailID);
        tv_phoneNo = (AutoCompleteTextView) findViewById(R.id.tv_phoneNo);
        tv_pNo = (AutoCompleteTextView) findViewById(R.id.tv_pNo);
        tv_totalFare = (AutoCompleteTextView) findViewById(R.id.tv_totalFare);

        bt_pay_cc_dc = (Button) findViewById(R.id.bt_pay_cc_dc);

        paywith_cc_dc = (RadioButton)findViewById(R.id.paywith_cc_dc);
        paywith_upi = (RadioButton)findViewById(R.id.paywith_upi);


        Intent i = getIntent();
        orsAVS = i.getExtras().getParcelable("orsAvailableServices_onRoute");

        String hdTitle = orsAVS.getrDesc() + "<br/><br/>" + "Conductor: " + orsAVS.getCndName() +"<br/>"+
                "Mobile No. " + orsAVS.getCndMobileNo()  + "<br/>" +
                "<big>Bus No. "+orsAVS.getBusNumber()+"</big><br/>"+
                "Journey Date : " + output.format( new Date());
        header_title.setText(Html.fromHtml(hdTitle));

        if (orsAVS.getBus_Type().equals("Ordinary"))
        {
            iv_bus.setImageResource(R.drawable.bus_ordinary);
        }

        bt_pay_cc_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paywith_cc_dc();
            }
        });
        dialog.dismiss();
    }

    private void paywith_cc_dc()
    {
        tv_customerName.setError(null);
        tv_emailID.setError(null);
        tv_phoneNo.setError(null);
        tv_pNo.setError(null);
        tv_totalFare.setError(null);

        String pName = tv_customerName.getText().toString();
        String pEmail = tv_emailID.getText().toString();
        String pPhone = tv_phoneNo.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pName) ) {
            tv_customerName.setError(getString(R.string.error_field_required));
            tv_customerName.requestFocus();
            focusView = tv_customerName;
            cancel = true;
            return;
        }
        if (TextUtils.isEmpty(pEmail) ) {
            tv_emailID.setError(getString(R.string.error_field_required));
            tv_emailID.requestFocus();
            cancel = true;
            focusView = tv_emailID;
            return;
        }
        if (TextUtils.isEmpty(pPhone) ) {
            tv_phoneNo.setError(getString(R.string.error_field_required));
            cancel = true;
            tv_phoneNo.requestFocus();
            focusView = tv_phoneNo;
            return;
        }

        if (!TextUtils.isDigitsOnly(tv_pNo.getText().toString()) ) {
            tv_pNo.setError(getString(R.string.error_field_required));
            cancel = true;
            tv_pNo.requestFocus();
            focusView = tv_pNo;
            return;
        }
        if (!TextUtils.isDigitsOnly(tv_totalFare.getText().toString()) ) {
            tv_totalFare.setError(getString(R.string.error_field_required));
            cancel = true;
            tv_totalFare.requestFocus();
            focusView = tv_totalFare;
            return;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            int pNo =  Integer.parseInt(tv_pNo.getText().toString());
            int totalFare =  Integer.parseInt(tv_totalFare.getText().toString());

            orsAS = new orsAvailableServices( orsAVS.getTrip_srno(), orsAVS.getTrip_id(), 0,
                    orsAVS.getrKMS(), orsAVS.getrFare(), orsAVS.getDepotID(), 0 , orsAVS.getrTripID(),Integer.parseInt(orsAVS.getTripID()),
                    0, 0, 0, orsAVS.getBus_Type(), orsAVS.getTripCode(), orsAVS.getLeaving(), orsAVS.getDeparting(),
                    orsAVS.getVia(), orsAVS.getrDesc(), "","", "", orsAVS.getTripRoute(), orsAVS.getDepotShortName(), new Date() );
            dialog.show();
            orsAS.settSeats(pNo);
            orsAS.setTotalFare(totalFare);
            orsAS.setSecureCode("NEW");
            orsAS.setpName(pName);
            orsAS.setpPhone(pPhone);
            orsAS.setpEmail(pEmail);
            orsAS.setpName1(orsAVS.getBusNumber());
            orsAS.setpName2(orsAVS.getCndName());
            orsAS.setpName3(orsAVS.getCndMobileNo());
            orsAS.setpName4(orsAVS.getCndWaibyillID());

            eTicketInfoUpdate_onRouteTask et_Task = new eTicketInfoUpdate_onRouteTask(EpayFareDetails.this);
            et_Task.eTicketInfo_update(orsAS);
        }
    }

    @Override
    public void notify_eTicketInfoUpdate_Error(VolleyError error) {
        dialog.dismiss();
        Toast.makeText(EpayFareDetails.this, error.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void notify_eTicketInfoUpdate_Success(boolean DidError, String ErrorMessage) {
        if (DidError){
            Toast.makeText(EpayFareDetails.this, ErrorMessage, Toast.LENGTH_SHORT).show();
        }else {
            orsAS.setSecureCode(ErrorMessage);
            if (paywith_cc_dc.isChecked()) {
                // Intent intent = new Intent(EpayFareDetails.this, PaywithPayzapp.class);
                // intent.putExtra("orsAvailableServices", orsAS);
                // startActivity(intent);
                // finish();

                web = new WebViewHelper().webview(EpayFareDetails.this);
                web.loadUrl("https://ors.hartrans.gov.in/paynowapp?secureCode="+orsAS.getSecureCode());
                // web.loadUrl("http://hartrans.gov.in/ors/paynowapp?secureCode=201810767610");
                setContentView(web);
            }
            if (paywith_upi.isChecked())
            {
                // Intent intent = new Intent(EpayFareDetails.this, PaywithUPI.class);
                // intent.putExtra("orsAvailableServices", orsAS);
                // startActivity(intent);
                finish();
            }
        }
        dialog.dismiss();
    }
}
