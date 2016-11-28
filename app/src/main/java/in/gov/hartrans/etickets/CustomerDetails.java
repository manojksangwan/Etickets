package in.gov.hartrans.etickets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enstage.wibmo.sdk.WibmoSDK;
import com.enstage.wibmo.sdk.WibmoSDKConfig;
import com.enstage.wibmo.sdk.inapp.pojo.WPayInitRequest;
import com.enstage.wibmo.sdk.inapp.pojo.MerchantInfo;
import com.enstage.wibmo.sdk.inapp.pojo.CustomerInfo;
import com.enstage.wibmo.sdk.inapp.pojo.TransactionInfo;
import com.enstage.wibmo.sdk.inapp.pojo.WPayResponse;
import in.gov.hartrans.etickets.Models.MerchantHandler;
import in.gov.hartrans.etickets.Models.orsAvailableServices;

//import com.hdfcmerchant.PayActivity;


public class CustomerDetails extends AppCompatActivity {
    orsAvailableServices orsAS;
    TextView tv_tripRoute,tv_jTime1;
    ImageView iv_bus;

    private static final String TAG = CustomerDetails.class.getSimpleName();
    private WPayInitRequest wPayInitRequest = null;
    private Activity activity = null;
    private TextView outputView =null;

    private long amount = 14000;
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        activity = this;

        outputView = (TextView) findViewById(R.id.tv_tFare);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // setSupportActionBar(my_toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                processPayWithWibmo();
                //upi_transaction();
            }
        });

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Customer Details");
        getSupportActionBar().setSubtitle(R.string.my_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);

        tv_tripRoute = (TextView)findViewById(R.id.tv_tripRoute);
        tv_jTime1 = (TextView)findViewById(R.id.tv_jTime1);
        iv_bus = (ImageView)findViewById(R.id.iv_bus);

        Intent i = getIntent();
        orsAS = i.getExtras().getParcelable("orsAvailableServices");

        tv_tripRoute.setText(orsAS.getTripRoute());
        tv_jTime1.setText("Depart : " + orsAS.getjTime1().toString());

        if (orsAS.getBusType().equals("Volvo"))
        {iv_bus.setImageResource(R.drawable.bus_volvo);}
        else
        {iv_bus.setImageResource(R.drawable.bus_ordinary);}

        final Context context = getApplicationContext();
        Thread t = new Thread(){
          public void run(){
              //WibmoSDK.setWibmoIntentActionPackage("com.enstage.wibmo.sdk.inapp.staging");
              //WibmoSDKConfig.setWibmoDomain("https://wallet.pc.enstage-sas.com");
              WibmoSDK.init(context);
          }
        };
        t.start();
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
        MerchantHandler.setMerchantDomain("hartrans.gov.in/ors"); //"mytestserver.com"
        //-
        /**/

        wPayInitRequest = new WPayInitRequest();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTxnAmount("" + amount);//implied decimals Rs1=100
        transactionInfo.setTxnCurrency("356");//356 for INR
        transactionInfo.setSupportedPaymentType(new String[]{"*"});
        //"*", "w.ds.pt.card_visa", "w.ds.pt.card_mastercard" or * or "w.ds.pt.card_wallet"
        //transactionInfo.setRestrictedPaymentType(new String[]{WibmoSDK.PAYMENT_TYPE_WALLET_CARD});

        transactionInfo.setTxnDesc("123456789012");//change me
        transactionInfo.setMerAppData("123456");//change me
        transactionInfo.setMerDataField("This is for recon");//change me
        transactionInfo.setChargeLater(false);
        transactionInfo.setTxnAmtKnown(true);


        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerAppId(merAppID);
        merchantInfo.setMerCountryCode(merMerCountryCode);
        merchantInfo.setMerId(merID);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustEmail("customer@somemail.com");//change me [set if available for better ux]
        customerInfo.setCustName("Customer Name");//change me [set if available for better ux]
        customerInfo.setCustDob("20011231");//change me [set if available for better ux]
        customerInfo.setCustMobile("9123412347");//change me [set if available for better ux]

        /*
        //..pass card saved at merchant..
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardnumber("4111111111111111");
        cardInfo.setExpiryMM("12");
        cardInfo.setExpiryYYYY("2015");
        cardInfo.setNameOnCard("Name on Card");
        wPayInitRequest.setCardInfo(cardInfo);
        //..pass card saved at merchant..
        */

        wPayInitRequest.setTransactionInfo(transactionInfo);
        wPayInitRequest.setMerchantInfo(merchantInfo);
        wPayInitRequest.setCustomerInfo(customerInfo);

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

                WPayResponse res = WibmoSDK.processInAppResponseWPay(data);
                Log.i(TAG, "resCode: " + res.getResCode()+
                        "; ResDesc: "+res.getResDesc());

                sb.append("resCode: " + res.getResCode()).append("\n");
                sb.append("resDesc: "+res.getResDesc()).append("\n");

                //success;
                String wPayTxnId = res.getWibmoTxnId();
                sb.append("wPayTxnId: "+wPayTxnId).append("\n");

                String merAppData = res.getMerAppData();
                sb.append("merAppData: "+merAppData).append("\n");

                String merTxnId = res.getMerTxnId();
                sb.append("merTxnId: "+merTxnId).append("\n");

                String msgHash = res.getMsgHash();
                sb.append("msgHash: "+msgHash).append("\n");

                String dataPickUpCode = res.getDataPickUpCode();
                sb.append("dataPickUpCode: "+dataPickUpCode).append("\n");
            } else {
                Log.i(TAG, "requestCode: not ok");
                if(data!=null) {
                    String resCode = data.getStringExtra("ResCode");
                    String resDesc = data.getStringExtra("ResDesc");
                    Log.i(TAG, "resCode: " + resCode+"; ResDesc: "+resDesc);
                    //failed .. more data at resDesc

                    sb.append("resCode: " + resCode).append("\n");
                    sb.append("resDesc: "+resDesc).append("\n");
                } else {
                    //failed
                }
            }//result not ok

            //sb.append("\nTime: "+timeDiff).append(" ms").append("\n");
            sb.append("\nTime: "+timeDiff/1000).append(" sec").append("\n");

            outputView.setText(sb.toString());
        }// requestCode
    }//onActivityResult



    private class MakeMessageHashTask extends AsyncTask<Void, Void, Void> {
        private boolean showError = false;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "", getString(R.string.lable_please_wait), true);
            dialog.setInverseBackgroundForced(true);
            dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);
        }

        @Override
        protected Void doInBackground(Void... data) {
            try {

                if(wPayInitRequest!=null) {
                    wPayInitRequest = MerchantHandler.generateMessageHash(wPayInitRequest);
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
                        activity.getString(R.string.error_generic_try_after_st),
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                if(wPayInitRequest!=null) {
                    startTime = System.currentTimeMillis();
                    WibmoSDK.startForInApp(activity, wPayInitRequest);
                }
            }
        }
    }
/*
    private void upi_transaction(){
        Bundle bundle = new Bundle();
        bundle.putString("mid", "HDFC000000000044");
        bundle.putString("merchantKey", "4d5390bef3ef1ee3d4a7e77fd42238cb");
        bundle.putString("merchantTxnID", "73643782");
        bundle.putString("transactionDesc", "Test Payment");
        bundle.putString("currency", "INR");
        bundle.putString("appName", "com.example.manojk.ors");
        bundle.putString("paymentType", "PAY");
        bundle.putString("payeePayAddress", "");
        bundle.putString("payeeAccntNo", "");
        bundle.putString("payeeIFSC", "");
        bundle.putString("payeeAadhaarNo", "");
        bundle.putString("payeeMobileNo", "");
        bundle.putString("expiryTime", "");
        bundle.putString("payerAccntNo", "");
        bundle.putString("payerIFSC", "");
        bundle.putString("payerAadhaarNo", "");
        bundle.putString("payerMobileNo", "");
        bundle.putString("payerPaymentAddress", "Director General State Transport Haryana");
        bundle.putString("subMerchantID", "");
        bundle.putString("payerMMID", "");
        bundle.putString("payeeMMID", "");
        bundle.putString("refurl", "");
        bundle.putString("amount","1122.00");
        bundle.putString("add1", "");
        bundle.putString("add2", "");
        bundle.putString("add3", "");
        bundle.putString("add4", "");
        bundle.putString("add5", "");
        bundle.putString("add6", "");
        bundle.putString("add7", "");
        bundle.putString("add8", "");
        bundle.putString("add9", "");
        bundle.putString("add10", "");

        Intent intent = new Intent(getApplicationContext(), PayActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null)
        {
            Bundle bundle = getIntent().getExtras();
            String pgMeTrnRefNo = bundle.getString("pgMeTrnRefNo");
            String orderNo = bundle.getString("orderNo");
            String txnAmount = bundle.getString("txnAmount");
            String tranAuthdate = bundle.getString("tranAuthdate");
            String statusCode = bundle.getString("status");
            String statusDesc = bundle.getString("statusDesc");
            String responsecode = bundle.getString("responsecode");
            String approvalCode = bundle.getString("approvalCode");
            String payerVA = bundle.getString("payerVA");
            String npciTxnId = bundle.getString("npciTxnId");
            String refId = bundle.getString("refId");
            String add1 = bundle.getString("add1");
            String add2 = bundle.getString("add2");
            String add3 = bundle.getString("add3");
            String add4 = bundle.getString("add4");
            String add5 = bundle.getString("add5");
            String add6 = bundle.getString("add6");
            String add7 = bundle.getString("add7");
            String add8 = bundle.getString("add8");
            String add9 = bundle.getString("add9");
            String add10 = bundle.getString("add10");

            outputView.setText(statusCode + "  " + responsecode);
        }
    }
    */
}
