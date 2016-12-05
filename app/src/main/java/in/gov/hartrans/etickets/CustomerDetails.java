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

    private static final String TAG = CustomerDetails.class.getSimpleName();
    private WPayInitRequest wPayInitRequest = null;
    private Activity activity = null;
    private TextView outputView = null, tv_rFare, tv_rCharges, tv_tFare;
    private Button bt_pay_cc_dc;

    private long amount;
    private long startTime;
    private long endTime;

    private String lastWibmoTxnId;
    private WPayResponse wPayResponse;

    private AutoCompleteTextView p_name, p_email, p_phone, iProof;


    private static final TimeZone UTC = new SimpleTimeZone(0, "UTC");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        activity = this;

        outputView = (TextView) findViewById(R.id.tv_tFare);
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
        final Context context = getApplicationContext();
        Thread t = new Thread() {
            public void run() {
                //WibmoSDK.setWibmoIntentActionPackage("com.enstage.wibmo.sdk.inapp.staging");
                //WibmoSDKConfig.setWibmoDomain("https://wallet.pc.enstage-sas.com");
                WibmoSDK.setWibmoIntentActionPackage("com.enstage.wibmo.sdk.inapp.staging");
                WibmoSDKConfig.setWibmoDomain("https://api.pc.enstage-sas.com");

                //uncomment next two statement for staging setup
                //WibmoSDK.setWibmoIntentActionPackage("com.enstage.wibmo.sdk.inapp.staging");
                //WibmoSDKConfig.setWibmoDomain("https://api.pc.enstage-sas.com");

                WibmoSDK.init(context);
            }
        };
        t.start();

        bt_pay_cc_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paywith_cc_dc();
            }
        });

    }

    private void processPayWithWibmo() {
        /*
        //Prod
        String merID = "MYMERCHANTID"; //"MYMERCHANTID";//change me
        String merAppID = "MYAPPID"; //"MYAPPID";//change me
        String merMerCountryCode = "IN";//change me if req
        MerchantHandler.setMerchantDomain("myprodserver.com"); //"myprodserver.com"
        //-
        /**/

        /**/
        //staging
        String merID = "143720911373862455993"; //"MYMERCHANTID";//change me
        String merAppID = "6455"; //"MYAPPID";//change me
        String merMerCountryCode = "IN";//change me if req
        MerchantHandler.setMerchantDomain("hartrans.gov.in"); //"mytestserver.com"
        //-
        /**/

        wPayInitRequest = new WPayInitRequest();
        wPayInitRequest.setTxnType(WibmoSDK.TRANSACTION_TYPE_WPAY);
        //wPayInitRequest.setTxnType("wpay");

        amount = (orsAS.getTotalFare() + orsAS.getrCharges())*100;
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTxnAmount("" + amount);//implied decimals Rs1=100
        transactionInfo.setTxnCurrency("356");//356 for INR
        transactionInfo.setSupportedPaymentType(new String[]{WibmoSDK.PAYMENT_TYPE_ALL});//WibmoSDK.PAYMENT_TYPE_ALL or WibmoSDK.PAYMENT_TYPE_WALLET_CARD
        transactionInfo.setChargeLater(false);
        transactionInfo.setTxnAmtKnown(true);

        //transactionInfo.setRestrictedPaymentType(new String[]{WibmoSDK.PAYMENT_TYPE_WALLET_CARD});
        transactionInfo.setMerTxnId(orsAS.getSecureCode());
        transactionInfo.setTxnDesc(orsAS.getSecureCode()+"-"+orsAS.getTripID()); //change this to something meaning full to customer
        transactionInfo.setMerAppData(orsAS.getSecureCode()+"-"+orsAS.getTripID());  //change this to something meaning for your app
        transactionInfo.setMerDataField(orsAS.getSecureCode()+"-"+orsAS.getTripID());   //change this to something meaning for your recon

        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerAppId(merAppID);
        merchantInfo.setMerCountryCode(merMerCountryCode);
        merchantInfo.setMerId(merID);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustEmail(orsAS.getpEmail());//change this to user's values if available for better ux
        customerInfo.setCustName(orsAS.getpName());//change this to user's values if available for better ux
        customerInfo.setCustDob("19750606");//change this to user's values if available for better ux
        customerInfo.setCustMobile(orsAS.getpPhone());//change this to user's values if available for better ux

        /*
        //..pass card saved if available
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardnumber("4111111111111111");
        cardInfo.setExpiryMM("12");
        cardInfo.setExpiryYYYY("2015");
        cardInfo.setNameOnCard("Name on Card");

        wPayInitRequest.setCardInfo(cardInfo);
        //..pass card saved if available
        */

        wPayInitRequest.setTransactionInfo(transactionInfo);
        wPayInitRequest.setMerchantInfo(merchantInfo);
        wPayInitRequest.setCustomerInfo(customerInfo);

        InAppTxnIdCallback myCallBack = new InAppTxnIdCallback() {
            @Override
            public boolean recordInit(Context context, String wibmoTxnId, String merTxnId) {
                Log.i(TAG, "We have init done: wibmoTxnId: " + wibmoTxnId + "; merTxnId: " + merTxnId);
                lastWibmoTxnId = wibmoTxnId;
                //TODO save this to DB; send to your server via Intent Service; you will need this for charge or status check req.
                return true;
            }
        };
        WibmoSDK.setInAppTxnIdCallback(myCallBack);

        /*
        //get this data from your server
        wPayInitRequest.setMsgHash("message hash");
        wPayInitRequest.getTransactionInfo().setMerTxnId("merchant txn id");

        //call API
        WibmoSDK.startForInApp(activity, wPayInitRequest);
        */

        new MakeMessageHashTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WibmoSDK.REQUEST_CODE_IAP_PAY) {
            endTime = System.currentTimeMillis();

            long timeDiff = endTime - startTime;

            StringBuilder sb = new StringBuilder();
            if (resultCode == RESULT_OK) {

                wPayResponse = WibmoSDK.processInAppResponseWPay(data);
                Log.i(TAG, "resCode: " + wPayResponse.getResCode() +
                        "; ResDesc: " + wPayResponse.getResDesc());

                sb.append("resCode: " + wPayResponse.getResCode()).append("\n");
                sb.append("resDesc: " + wPayResponse.getResDesc()).append("\n");

                //success;
                String wPayTxnId = wPayResponse.getWibmoTxnId();
                sb.append("wPayTxnId: " + wPayTxnId).append("\n");

                String merAppData = wPayResponse.getMerAppData();
                sb.append("merAppData: " + merAppData).append("\n");

                String merTxnId = wPayResponse.getMerTxnId();
                sb.append("merTxnId: " + merTxnId).append("\n");

                String msgHash = wPayResponse.getMsgHash();
                sb.append("msgHash: " + msgHash).append("\n");

                String dataPickUpCode = wPayResponse.getDataPickUpCode();
                sb.append("dataPickUpCode: " + dataPickUpCode).append("\n");
            } else {
                Log.i(TAG, "requestCode: not ok");
                if (data != null) {
                    String resCode = data.getStringExtra("ResCode");
                    String resDesc = data.getStringExtra("ResDesc");
                    Log.i(TAG, "resCode: " + resCode + "; ResDesc: " + resDesc);
                    //failed .. more data at resDesc

                    sb.append("resCode: " + resCode).append("\n");
                    sb.append("resDesc: " + resDesc).append("\n");
                } else {
                    //failed
                }
            }//result not ok

            //sb.append("\nTime: "+timeDiff).append(" ms").append("\n");
            sb.append("\nTime: " + timeDiff / 1000).append(" sec").append("\n");

            //outputView.setText(sb.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDetails.this);
            builder.setMessage(sb.toString())
                    .setTitle("Transaction Response");
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //finish();
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    System.exit(0);
                    finishAffinity();
                }
            });
            /*
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            */
            // Set other dialog properties


            // Create the AlertDialog
            AlertDialog dialog = builder.create();

            dialog.show();

        }// requestCode
    }//onActivityResult


    private class MakeMessageHashTask extends AsyncTask<Void, Void, Void> {
        private boolean showError = false;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "", "Please wait", true);
            dialog.setInverseBackgroundForced(true);
            dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);
        }

        @Override
        protected Void doInBackground(Void... data) {
            try {
                if (wPayInitRequest != null) {
                    wPayInitRequest = MerchantHandler.generateMessageHash(wPayInitRequest);//wPayInitRequest.setMsgHash("test");
                }
            } catch (Exception ex) {
                Log.e(TAG, "Error: " + ex, ex);
                showError = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();

            if (showError) {
                Toast toast = Toast.makeText(activity,
                        "We had an error, please try after sometime",
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                if (wPayInitRequest != null) {
                    startTime = System.currentTimeMillis();
                    WibmoSDK.startForInApp(activity, wPayInitRequest);
                }
            }
        }
    }

    public void doStatusCheck() {
        Log.v(TAG, "doStatusCheck");

        if (wPayInitRequest == null) {
            Toast toast = Toast.makeText(activity,
                    "Your txn should be started; before you can do status check!",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        AsyncTask<Void, Void, Void> task = new StatusCheckTask();
        task.execute();
    }

    private class StatusCheckTask extends AsyncTask<Void, Void, Void> {
        private boolean showError = false;
        private ProgressDialog dialog;

        private String output;

        @Override
        protected void onPreExecute() {
            outputView.setText("...");

            dialog = ProgressDialog.show(activity, "", "Please wait", true);
            dialog.setInverseBackgroundForced(true);
            dialog.setCancelable(false);
            dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);
        }

        @Override
        protected Void doInBackground(Void... data) {
            try {
                String wibmoTxnIdToPass = null;
                if (wPayResponse != null) {
                    //use txn id from pay res..
                    wibmoTxnIdToPass = wPayResponse.getWibmoTxnId();
                } else {
                    //use txn id from init res..
                    wibmoTxnIdToPass = lastWibmoTxnId;
                }
                output = MerchantHandler.doStatusCheck(wPayInitRequest, wibmoTxnIdToPass);
            } catch (Throwable ex) {
                Log.e(TAG, "Error: " + ex, ex);
                showError = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();

            if (showError) {
                Toast toast = Toast.makeText(activity,
                        "We had an error, please try after sometime",
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                outputView.setText(output);
            }
        }
    }

    public void doChargeCard() {
        Log.v(TAG, "doChargeCard");

        if (wPayInitRequest == null) {
            Toast toast = Toast.makeText(activity,
                    "Your txn should be started; before you can do charge!",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        AsyncTask<Void, Void, Void> task = new ChargeCardTask();
        task.execute();
    }

    private class ChargeCardTask extends AsyncTask<Void, Void, Void> {
        private boolean showError = false;
        private ProgressDialog dialog;

        private String output;

        @Override
        protected void onPreExecute() {
            outputView.setText("...");

            dialog = ProgressDialog.show(activity, "", "Please wait", true);
            dialog.setInverseBackgroundForced(true);
            dialog.setCancelable(false);
            dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);
        }

        @Override
        protected Void doInBackground(Void... data) {
            try {
                String wibmoTxnIdToPass = null;
                if (wPayResponse != null) {
                    //use txn id from pay res..
                    wibmoTxnIdToPass = wPayResponse.getWibmoTxnId();
                } else {
                    //use txn id from init res..
                    wibmoTxnIdToPass = lastWibmoTxnId;
                }
                output = MerchantHandler.doCharge(wPayInitRequest, wibmoTxnIdToPass);
            } catch (Throwable ex) {
                Log.e(TAG, "Error: " + ex, ex);
                showError = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();

            if (showError) {
                Toast toast = Toast.makeText(activity,
                        "We had an error, please try after sometime",
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                outputView.setText(output);
            }
        }
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
    }

    @Override
    public void notify_eTicketInfoUpdate_Success(boolean DidError, String ErrorMessage) {
        if (DidError){
            Toast.makeText(CustomerDetails.this, ErrorMessage, Toast.LENGTH_SHORT).show();
        }else {
            orsAS.setSecureCode(ErrorMessage);
            wPayInitRequest = null;
            wPayResponse = null;
            processPayWithWibmo();
        }
    }
}