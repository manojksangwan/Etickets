package in.gov.hartrans.etickets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import android.support.v7.app.ActionBarActivity;
import java.util.SimpleTimeZone;

import com.android.volley.VolleyError;
import com.enstage.wibmo.sdk.WibmoSDK;
import com.enstage.wibmo.sdk.WibmoSDKConfig;
import com.enstage.wibmo.sdk.inapp.pojo.WPayInitRequest;
import com.enstage.wibmo.sdk.inapp.pojo.MerchantInfo;
import com.enstage.wibmo.sdk.inapp.pojo.CustomerInfo;
import com.enstage.wibmo.sdk.inapp.pojo.TransactionInfo;
import com.enstage.wibmo.sdk.inapp.pojo.WPayResponse;
import com.enstage.wibmo.sdk.inapp.InAppTxnIdCallback;


import in.gov.hartrans.etickets.Models.MerchantHandler;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdateTask;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdate_iResult;
import in.gov.hartrans.etickets.Models.orsAvailableServices;
//import com.hdfcmerchant.PayActivity;




public class CustomerDetails extends AppCompatActivity implements eTicketInfoUpdate_iResult {
    orsAvailableServices orsAS;

    AppCompatTextView header_Title;
    private ProgressDialog dialog;
    ImageView iv_bus;

    private TextView outputView = null, tv_rFare, tv_rCharges, tv_tFare;
    private Button bt_pay_cc_dc;

    private AutoCompleteTextView p_name, p_email, p_phone, iProof;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        dialog = ProgressDialog.show(this, "", "Please wait", true);
        dialog.setInverseBackgroundForced(true);
        dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);



        tv_rFare = (TextView) findViewById(R.id.tv_rFare);
        tv_rCharges = (TextView) findViewById(R.id.tv_rCharges);
        tv_tFare = (TextView) findViewById(R.id.tv_tFare);
        bt_pay_cc_dc = (Button) findViewById(R.id.bt_pay_cc_dc);

        p_name = (AutoCompleteTextView) findViewById(R.id.p_name);
        p_email = (AutoCompleteTextView) findViewById(R.id.p_email);
        p_phone = (AutoCompleteTextView) findViewById(R.id.p_phone);
        iProof = (AutoCompleteTextView) findViewById(R.id.iProof);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // setSupportActionBar(my_toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //reset
                Intent intent = new Intent(CustomerDetails.this, TestPayment.class);
                startActivity(intent);
            }
        });
        fab.setVisibility(View.GONE);

        header_Title = (AppCompatTextView) findViewById(R.id.header_title);
        iv_bus = (ImageView) findViewById(R.id.iv_bus);

        Intent i = getIntent();
        orsAS = i.getExtras().getParcelable("orsAvailableServices");

        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        String h_title;

        h_title ="<em>"+ orsAS.getBusType() +"</em>: <big><strong>"+orsAS.getTripRoute()+"</strong> </big>";
        //h_title+="<br/><small>Via: <i>" + orsAS.getVia() + "</i></small><br/>";
        h_title+="<br/>Departure : " + output.format(orsAS.getjTime1())+"<br/>";
        h_title+="Selected seat(s) :  " + orsAS.getPrfSeats();
        header_Title.setText(Html.fromHtml(h_title));

        if (orsAS.getBusType().equals("Volvo")) {
            iv_bus.setImageResource(R.drawable.bus_volvo);
        } else {
            iv_bus.setImageResource(R.drawable.bus_ordinary);
        }

        tv_rFare.setText(Html.fromHtml("Basic fare \u20B9 <big>" +orsAS.getrFare()+"</big>"));
        tv_rCharges.setText(Html.fromHtml("Reservation Charges \u20B9 <big>" +orsAS.getrCharges()+"</big>"));
        tv_tFare.setText(Html.fromHtml("Total Amount tobe paid \u20B9 <big>" +(orsAS.getrFare()+ orsAS.getrCharges() )+"</big>" ));
        bt_pay_cc_dc.setText(Html.fromHtml("Proceed to pay \u20B9 <big>" +(orsAS.getrFare()+ orsAS.getrCharges() )+"</big> with CC/DC" ));

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Customer Details-" + orsAS.getTripID());
        getSupportActionBar().setSubtitle(R.string.my_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);

        if(dialog.isShowing())
        {
            dialog.dismiss();
        }

        bt_pay_cc_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paywith_cc_dc();
            }
        });
    }



    private void paywith_cc_dc()
    {
        p_name.setError(null);
        p_email.setError(null);
        p_phone.setError(null);
        iProof.setError(null);

        String pName = p_name.getText().toString();
        String pEmail = p_email.getText().toString();
        String pPhone = p_phone.getText().toString();
        String i_proof = iProof.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pName) ) {
            p_name.setError(getString(R.string.error_field_required));
            focusView = p_name;
            cancel = true;
            p_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pEmail) ) {
            p_email.setError(getString(R.string.error_field_required));
            focusView = p_email;
            cancel = true;
            p_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pPhone) ) {
            p_phone.setError(getString(R.string.error_field_required));
            focusView = p_phone;
            cancel = true;
            p_phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(i_proof) ) {
            iProof.setError(getString(R.string.error_field_required));
            focusView = iProof;
            cancel = true;
            iProof.requestFocus();
            return;
        }

        if (cancel) {
            // There was an error; don't attempt to SAVE passenger Information
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            // showProgress(true);
            // mAuthTask = new UserLoginTask(email, mobile_no, password);

            // mAuthTask.execute((Void) null);
            dialog.show();
            orsAS.setpName(pName);
            orsAS.setpEmail(pEmail);
            orsAS.setpPhone(pPhone);
            orsAS.setiProof(i_proof);

            eTicketInfoUpdateTask et_Task = new eTicketInfoUpdateTask(CustomerDetails.this);
            et_Task.eTicketInfo_update(orsAS, "PassengerInfo");

        }
    }
    @Override
    public void notify_eTicketInfoUpdate_Error(VolleyError error) {
        Toast.makeText(CustomerDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void notify_eTicketInfoUpdate_Success(boolean DidError, String ErrorMessage) {
        if (DidError){
            Toast.makeText(CustomerDetails.this, ErrorMessage, Toast.LENGTH_SHORT).show();
        }else {
            orsAS.setSecureCode(ErrorMessage);
            Intent intent = new Intent(CustomerDetails.this, PaywithPayzapp.class);
            intent.putExtra("orsAvailableServices", orsAS);
            startActivity(intent);
            finish();
        }
        dialog.dismiss();
    }
}