package in.gov.hartrans.etickets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hdfcmerchant.PayActivity;


public class TestPayment extends AppCompatActivity {
    private Activity activity = null;
    private TextView outputView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;
        outputView = (TextView) findViewById(R.id.result);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                upi_transaction();
            }
        });
    }


    private void upi_transaction(){
        Bundle bundle = new Bundle();
        bundle.putString("mid", "HDFC000000000044");
        bundle.putString("merchantKey", "7c448fb4c6eb425e86f8817ae5488533");
        bundle.putString("merchantTxnID", "123456789012");
        bundle.putString("transactionDesc", "pnr123456789");
        bundle.putString("currency", "INR");
        bundle.putString("appName", "in.gov.hartrans.etickets");
        bundle.putString("paymentType", "p2m");
        bundle.putString("transactionType", "collect");
        bundle.putString("payeePayAddress", "Director General State Transport Haryana");
        bundle.putString("payeeAccntNo", "");
        bundle.putString("payeeIFSC", "");
        bundle.putString("payeeAadhaarNo", "");
        bundle.putString("payeeMobileNo", "");
        bundle.putString("merchantCatCode", "");
        bundle.putString("expiryTime", "");
        bundle.putString("payerAccntNo", "");
        bundle.putString("payerIFSC", "");
        bundle.putString("payerAadhaarNo", "");
        bundle.putString("payerMobileNo", "");
        bundle.putString("payerPaymentAddress", "");
        bundle.putString("subMerchantID", "");
        bundle.putString("whitelistedAccnts", "");
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
        try{
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null)
        {
            Bundle bundle = getIntent().getExtras();
/*            String pgMeTrnRefNo = bundle.getString("pgMeTrnRefNo");
            String orderNo = bundle.getString("orderNo");
            String txnAmount = bundle.getString("txnAmount");
            String tranAuthdate = bundle.getString("tranAuthdate");
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
            String statusCode = bundle.getString("status");
            String statusDesc = bundle.getString("statusDesc");
            String responsecode = bundle.getString("responsecode");

            outputView.setText(statusCode + "  " + responsecode);
            */
        }}catch (Exception ex)
        {
            outputView.setText(ex.getMessage());
        }
    }
}