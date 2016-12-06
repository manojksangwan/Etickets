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
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.enstage.wibmo.sdk.WibmoSDK;
import com.enstage.wibmo.sdk.WibmoSDKConfig;
import com.enstage.wibmo.sdk.inapp.InAppTxnIdCallback;
import com.enstage.wibmo.sdk.inapp.pojo.CustomerInfo;
import com.enstage.wibmo.sdk.inapp.pojo.MerchantInfo;
import com.enstage.wibmo.sdk.inapp.pojo.TransactionInfo;
import com.enstage.wibmo.sdk.inapp.pojo.WPayInitRequest;
import com.enstage.wibmo.sdk.inapp.pojo.WPayResponse;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import in.gov.hartrans.etickets.Models.MerchantHandler;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdate_iResult;
import in.gov.hartrans.etickets.Models.orsAvailableServices;
import in.gov.hartrans.etickets.Models.wibmoResponse;
import in.gov.hartrans.etickets.Models.wibmoResponseTask;

public class PaywithPayzapp extends AppCompatActivity implements eTicketInfoUpdate_iResult {
    orsAvailableServices orsAS;
    private static final String TAG = PaywithPayzapp.class.getSimpleName();
    private WPayInitRequest wPayInitRequest = null;
    private Activity activity = null;

    private long amount;
    private long startTime;
    private long endTime;

    private String lastWibmoTxnId;
    private WPayResponse wPayResponse;
    private static final TimeZone UTC = new SimpleTimeZone(0, "UTC");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private TextView outputView = null;
    private Button bt_retry=null;

    StringBuilder sb = new StringBuilder();
    private ProgressDialog dialog;

    private boolean proceedtoPay=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paywith_payzapp);

        dialog = ProgressDialog.show(this, "", "Please wait", true);
        dialog.setInverseBackgroundForced(true);
        dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);
        dialog.show();
        Intent i = getIntent();
        orsAS = i.getExtras().getParcelable("orsAvailableServices");
        activity = this;
        outputView = (TextView) findViewById(R.id.tv_output);
        bt_retry = (Button) findViewById(R.id.bt_retry);
        bt_retry.setVisibility(View.INVISIBLE);

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Transaction Response-" + orsAS.getTripID());
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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }

        dialog.dismiss();

        if (proceedtoPay) {
            proceedtoPay=false;
            wPayInitRequest = null;
            wPayResponse = null;
            processPayWithWibmo();
        }
        bt_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_retry.setVisibility(View.INVISIBLE);
                outputView.setText("please wait...");
                sb.setLength(0);

                proceedtoPay=false;
                wPayInitRequest = null;
                wPayResponse = null;
                processPayWithWibmo();
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


            if (resultCode == RESULT_OK) {
                sb.setLength(0);

                wPayResponse = WibmoSDK.processInAppResponseWPay(data);
                //Log.i(TAG, "resCode: " + wPayResponse.getResCode() +"; ResDesc: " + wPayResponse.getResDesc());

                //sb.append("resCode: " + wPayResponse.getResCode()).append("\n");
                sb.append("<font color='green'><big><b>Payment received successfully<b/></big></font><br/>");

                //success;
                String wPayTxnId = wPayResponse.getWibmoTxnId();
                sb.append("Transaction ID : <b>" + wPayTxnId).append("</b><br/>");

                String merAppData = wPayResponse.getMerAppData();
                //sb.append("merAppData: " + merAppData).append("\n");

                String merTxnId = wPayResponse.getMerTxnId();
                //sb.append("merTxnId: " + merTxnId).append("\n");

                String msgHash = wPayResponse.getMsgHash();
                //sb.append("msgHash: " + msgHash).append("\n");

                String dataPickUpCode = wPayResponse.getDataPickUpCode();
                //sb.append("dataPickUpCode: " + dataPickUpCode).append("\n");

                if (wPayResponse.getResCode().equals("000"))
                {
                    sb.append("<font color='red'>eTicket Booked successfully</font><br/><small>eTicket details will be sent through email & sms.</small>").append("<br/>");
                    wibmoResponse wr = new wibmoResponse(
                            wPayResponse.getResCode(),
                            wPayResponse.getResDesc(),
                            wPayTxnId,
                            merAppData,
                            merTxnId,
                            msgHash,
                            dataPickUpCode);
                    wibmoResponseTask wr_Task = new wibmoResponseTask(PaywithPayzapp.this);
                    wr_Task.WibmoPGresponse_update(wr);
                }
            } else {
                Log.i(TAG, "requestCode: not ok");
                if (data != null) {
                    String resCode = data.getStringExtra("ResCode");
                    String resDesc = data.getStringExtra("ResDesc");
                    Log.i(TAG, "resCode: " + resCode + "; ResDesc: " + resDesc);
                    //failed .. more data at resDesc
                    sb.append("resCode: " + resCode).append("\n");
                    sb.append("resDesc: " + resDesc).append("\n");
                    bt_retry.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
            }//result not ok

            //sb.append("\nTime: "+timeDiff).append(" ms").append("\n");
            sb.append("\nTime: " + timeDiff / 1000).append(" sec").append("\n");
            outputView.setText(sb.toString());
            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(PaywithPayzapp.this);
            builder.setMessage(sb.toString())
                    .setTitle("Transaction Response");
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    System.exit(0);
                    finishAffinity();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            */
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
                outputView.setText("We had an error, please try after sometime");
                bt_retry.setVisibility(View.VISIBLE);
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
    @Override
    public void notify_eTicketInfoUpdate_Error(VolleyError error) {
        //Log.d("myApp", "HR Server Response" + error.toString());
        Toast.makeText(PaywithPayzapp.this, error.toString(), Toast.LENGTH_SHORT).show();
        outputView.setText(Html.fromHtml(sb.toString()));
    }

    @Override
    public void notify_eTicketInfoUpdate_Success(boolean DidError, String ErrorMessage) {
        if (DidError){
            //Log.d("myApp", "HR Server Response" + ErrorMessage);
            Toast.makeText(PaywithPayzapp.this, ErrorMessage, Toast.LENGTH_SHORT).show();
            outputView.setText(Html.fromHtml(sb.toString()));
        }else {
            outputView.setText(Html.fromHtml(sb.toString()));
        }
/*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        finish();
*/
        proceedtoPay=false;
    }
}