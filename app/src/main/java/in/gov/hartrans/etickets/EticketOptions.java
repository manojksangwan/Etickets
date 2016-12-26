package in.gov.hartrans.etickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EticketOptions extends AppCompatActivity {
    Button bt_continue=null;
    AppCompatRadioButton eTicket_online, eTicket_onRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eticket_options);

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.my_eTikcet_options_title);
        getSupportActionBar().setSubtitle(R.string.my_eTikcet_options_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);

        bt_continue = (Button)  findViewById(R.id.bt_continue);
        eTicket_online = (AppCompatRadioButton)  findViewById(R.id.eTicket_online);
        eTicket_onRoute = (AppCompatRadioButton)  findViewById(R.id.eTicket_onRoute);

        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eTicket_online.isChecked())
                {
                    Intent bookEticket = new Intent(EticketOptions.this, BookEticketActivity.class);
                    startActivity(bookEticket);
                }
                if (eTicket_onRoute.isChecked())
                {

                }
            }
        });
    }
}
