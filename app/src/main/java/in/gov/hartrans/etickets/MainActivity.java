package in.gov.hartrans.etickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hdfcmerchant.PayActivity;

import in.gov.hartrans.etickets.Models.appStatusTask;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdateTask;
import in.gov.hartrans.etickets.Models.eTicketInfoUpdate_iResult;


public class MainActivity extends AppCompatActivity implements eTicketInfoUpdate_iResult {
    protected boolean _active = true;
    protected int _splashTime = 3000; // time to display the splash screen in ms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

                    appStatusTask app_Task = new appStatusTask(MainActivity.this);
                    app_Task.getAppStatus();
                }
            };
        };
        splashTread.start();
    }

    @Override
    public void notify_eTicketInfoUpdate_Error(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void notify_eTicketInfoUpdate_Success(boolean DidError, String ErrorMessage) {
        if (DidError)
        {
            startActivity(new Intent(MainActivity.this, BookEticketActivity.class));
            finish();
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("DidError", DidError+"");
            bundle.putString("ErrorMessage", ErrorMessage);

            Intent intent = new Intent(getApplicationContext(), orsStatus.class);
            intent.putExtras(bundle);
            startActivity(intent);

            //startActivity(new Intent(MainActivity.this, orsStatus.class));

            finish();
        }
    }
}
