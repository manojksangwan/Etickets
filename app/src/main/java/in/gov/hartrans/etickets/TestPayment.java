package in.gov.hartrans.etickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
//import com.hdfcmerchant.PayActivity;


public class TestPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Bundle bundle = new Bundle();
                bundle.putString("mid", "HDFC000000000044");
                bundle.putString("merchantKey", "4d5390bef3ef1ee3d4a7e77fd42238cb");
                bundle.putString("merchantTxnID", "73643782");
                bundle.putString("transactionDesc", "Test Payment");
                bundle.putString("currency", "INR");
                bundle.putString("appName", "com.merchant.package");
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
                bundle.putString("payerPaymentAddress", "");
                bundle.putString("subMerchantID", "");
                bundle.putString("payerMMID", "");
                bundle.putString("payeeMMID", "");
                bundle.putString("refurl", "");
                bundle.putString("amount","1.00");
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

                //Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                //intent.putExtras(bundle);
                //startActivityForResult(intent, 1);
            }
        });
    }

}
