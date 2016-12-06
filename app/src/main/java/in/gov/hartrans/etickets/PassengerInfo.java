package in.gov.hartrans.etickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;

import in.gov.hartrans.etickets.Models.*;


public class PassengerInfo extends AppCompatActivity {
    orsAvailableServices orsAS;
    TextInputLayout til_pname1,til_pname2,til_pname3,til_pname4;
    RelativeLayout passenger2, passenger3, passenger4;


    AutoCompleteTextView p_name1, p_name2, p_name3, p_name4, p_age1, p_age2, p_age3, p_age4;
    RadioButton male_pGender1, male_pGender2, male_pGender3, male_pGender4, female_pGender1, female_pGender2, female_pGender3, female_pGender4;

    AppCompatTextView header_Title;
    private ProgressDialog dialog;
    ImageView iv_bus;

    private Button bt_continue=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_info);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // setSupportActionBar(toolbar);

        bt_continue=(Button) findViewById(R.id.bt_continue);
        p_name1 = (AutoCompleteTextView) findViewById(R.id.p_name1);
        p_name2 = (AutoCompleteTextView) findViewById(R.id.p_name2);
        p_name3 = (AutoCompleteTextView) findViewById(R.id.p_name3);
        p_name4 = (AutoCompleteTextView) findViewById(R.id.p_name4);

        p_age1 = (AutoCompleteTextView) findViewById(R.id.p_age1);
        p_age2 = (AutoCompleteTextView) findViewById(R.id.p_age2);
        p_age3 = (AutoCompleteTextView) findViewById(R.id.p_age3);
        p_age4 = (AutoCompleteTextView) findViewById(R.id.p_age4);

        male_pGender1 = (RadioButton) findViewById(R.id.male_pGender1);
        male_pGender2 = (RadioButton) findViewById(R.id.male_pGender2);
        male_pGender3 = (RadioButton) findViewById(R.id.male_pGender3);
        male_pGender4 = (RadioButton) findViewById(R.id.male_pGender4);

        female_pGender1 = (RadioButton) findViewById(R.id.female_pGender1);
        female_pGender2 = (RadioButton) findViewById(R.id.female_pGender2);
        female_pGender3 = (RadioButton) findViewById(R.id.female_pGender3);
        female_pGender4 = (RadioButton) findViewById(R.id.female_pGender4);

        header_Title = (AppCompatTextView) findViewById(R.id.header_title);
        iv_bus = (ImageView) findViewById(R.id.iv_bus);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPassengerInfo();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPassengerInfo();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        Intent i = getIntent();
        orsAS = i.getExtras().getParcelable("orsAvailableServices");

        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        String h_title;

        h_title ="<em>"+ orsAS.getBusType() +"</em>: <big><strong>"+orsAS.getTripRoute()+"</strong> </big>";
        //h_title+="<br/><small>Via: <i>" + orsAS.getVia() + "</i></small><br/>";
        h_title+="<br/>Departure : " + output.format(orsAS.getjTime1())+"<br/>";
        h_title+="Selected seat(s) :  " + orsAS.getPrfSeats();
        header_Title.setText(Html.fromHtml(h_title));
        iv_bus = (ImageView) findViewById(R.id.iv_bus);

        if (orsAS.getBusType().equals("Volvo")) {
            iv_bus.setImageResource(R.drawable.bus_volvo);
        } else {
            iv_bus.setImageResource(R.drawable.bus_ordinary);
        }


        til_pname1 = (TextInputLayout)findViewById(R.id.til_p_name1);
        til_pname2 = (TextInputLayout)findViewById(R.id.til_p_name2);
        til_pname3 = (TextInputLayout)findViewById(R.id.til_p_name3);
        til_pname4 = (TextInputLayout)findViewById(R.id.til_p_name4);

        til_pname1.setHint("Passenger Name (Seat No. "+orsAS.getpSeat1()+")");
        til_pname2.setHint("Passenger Name (Seat No. "+orsAS.getpSeat2()+")");
        til_pname3.setHint("Passenger Name (Seat No. "+orsAS.getpSeat3()+")");
        til_pname4.setHint("Passenger Name (Seat No. "+orsAS.getpSeat4()+")");
        //tv_pname1.bringToFront();

        if (orsAS.gettSeats()==1) {
            passenger2 = (RelativeLayout) findViewById(R.id.passenger2);
            passenger3 = (RelativeLayout) findViewById(R.id.passenger3);
            passenger4 = (RelativeLayout) findViewById(R.id.passenger4);
            passenger2.setVisibility(View.GONE);
            passenger3.setVisibility(View.GONE);
            passenger4.setVisibility(View.GONE);
        }
        if (orsAS.gettSeats()==2) {
            passenger3 = (RelativeLayout) findViewById(R.id.passenger3);
            passenger4 = (RelativeLayout) findViewById(R.id.passenger4);
            passenger3.setVisibility(View.GONE);
            passenger4.setVisibility(View.GONE);
        }
        if (orsAS.gettSeats()==3) {
            passenger4 = (RelativeLayout) findViewById(R.id.passenger4);
            passenger4.setVisibility(View.GONE);
        }

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Passenger Info - " + orsAS.getTripID());
        getSupportActionBar().setSubtitle(R.string.my_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);
    }

    private void addPassengerInfo()
    {
        p_name1.setError(null);
        p_name2.setError(null);
        p_name3.setError(null);
        p_name4.setError(null);

        p_age1.setError(null);
        p_age2.setError(null);
        p_age3.setError(null);
        p_age4.setError(null);

        male_pGender1.setError(null);
        male_pGender2.setError(null);
        male_pGender3.setError(null);
        male_pGender4.setError(null);

        String pName1 = p_name1.getText().toString();
        String pName2 = p_name2.getText().toString();
        String pName3 = p_name3.getText().toString();
        String pName4 = p_name4.getText().toString();

        String pAge1 = p_age1.getText().toString();
        String pAge2 = p_age2.getText().toString();
        String pAge3 = p_age3.getText().toString();
        String pAge4 = p_age4.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pName1) ) {
            p_name1.setError(getString(R.string.error_field_required));
            focusView = p_name1;
            cancel = true;
            p_name1.requestFocus();
            return;
        }
        if (!isAgeValid(pAge1) ) {
            p_age1.setError(getString(R.string.error_field_required));
            focusView = p_age1;
            cancel = true;
            p_age1.requestFocus();
            return;
        }
        if (!(male_pGender1.isChecked() || female_pGender1.isChecked()))
        {
            male_pGender1.setError(getString(R.string.error_field_required));
            focusView = male_pGender1;
            cancel = true;
            male_pGender1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pName2) && orsAS.gettSeats()>=2 ) {
            p_name2.setError(getString(R.string.error_field_required));
            focusView = p_name2;
            cancel = true;
            p_name2.requestFocus();
            return;
        }
        if (!isAgeValid(pAge2) && orsAS.gettSeats()>=2 ) {
            p_age2.setError(getString(R.string.error_field_required));
            focusView = p_age2;
            cancel = true;
            p_age2.requestFocus();
            return;
        }
        if (!(male_pGender2.isChecked() || female_pGender2.isChecked()) && orsAS.gettSeats()>=2)
        {
            male_pGender2.setError(getString(R.string.error_field_required));
            focusView = male_pGender2;
            cancel = true;
            male_pGender2.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pName3) && orsAS.gettSeats()>=3 ) {
            p_name3.setError(getString(R.string.error_field_required));
            focusView = p_name3;
            cancel = true;
            p_name3.requestFocus();
            return;
        }
        if (!isAgeValid(pAge3) && orsAS.gettSeats()>=3 ) {
            p_age3.setError(getString(R.string.error_field_required));
            focusView = p_age3;
            cancel = true;
            p_age3.requestFocus();
            return;
        }
        if (!(male_pGender3.isChecked() || female_pGender3.isChecked()) && orsAS.gettSeats()>=3)
        {
            male_pGender3.setError(getString(R.string.error_field_required));
            focusView = male_pGender3;
            cancel = true;
            male_pGender3.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pName4) && orsAS.gettSeats()>=4 ) {
            p_name4.setError(getString(R.string.error_field_required));
            focusView = p_name4;
            cancel = true;
            p_name4.requestFocus();
            return;
        }
        if (!isAgeValid(pAge4) && orsAS.gettSeats()>=4 ) {
            p_age4.setError(getString(R.string.error_field_required));
            focusView = p_age4;
            cancel = true;
            p_age4.requestFocus();
            return;
        }
        if (!(male_pGender4.isChecked() || female_pGender4.isChecked()) && orsAS.gettSeats()>=4)
        {
            male_pGender4.setError(getString(R.string.error_field_required));
            focusView = male_pGender4;
            cancel = true;
            male_pGender4.requestFocus();
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
            orsAS.setpName1(pName1);
            orsAS.setpName2(pName2);
            orsAS.setpName3(pName3);
            orsAS.setpName4(pName4);

            orsAS.setpAge1(Integer.parseInt(TextUtils.isEmpty(pAge1)?"0":pAge1));
            orsAS.setpAge2(Integer.parseInt(TextUtils.isEmpty(pAge2)?"0":pAge2));
            orsAS.setpAge3(Integer.parseInt(TextUtils.isEmpty(pAge3)?"0":pAge3));
            orsAS.setpAge4(Integer.parseInt(TextUtils.isEmpty(pAge4)?"0":pAge4));

            orsAS.setpGender1(male_pGender1.isChecked()?"M": (female_pGender1.isChecked()?"F":""));
            orsAS.setpGender2(male_pGender2.isChecked()?"M": (female_pGender2.isChecked()?"F":""));
            orsAS.setpGender3(male_pGender3.isChecked()?"M": (female_pGender3.isChecked()?"F":""));
            orsAS.setpGender4(male_pGender4.isChecked()?"M": (female_pGender4.isChecked()?"F":""));

            //dialog.dismiss();
            Intent intent = new Intent(PassengerInfo.this, CustomerDetails.class);
            intent.putExtra("orsAvailableServices", orsAS);
            startActivity(intent);
            finish();
        }
    }
    private boolean isAgeValid(String pAge) {
        if ( TextUtils.isEmpty(pAge) ){return false;}
        int page;
        try {
            page = Integer.parseInt(pAge);
        }catch (Exception ex)
        {return false;}
        return !(page>=100 || page<=0);
    }
}
