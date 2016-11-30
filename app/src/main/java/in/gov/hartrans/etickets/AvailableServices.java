package in.gov.hartrans.etickets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import in.gov.hartrans.etickets.Models.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailableServices extends AppCompatActivity implements myIResult {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<orsAvailableServices> arList = new ArrayList<>();
    ArrayList<orsAvailableServices> asList = new ArrayList<>();
    AppCompatTextView header_Title;
    private ProgressDialog dialog;
    ImageView iv_bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_services);

        dialog = ProgressDialog.show(this, "", "Please wait", true);
        dialog.setInverseBackgroundForced(true);
        dialog.setProgressStyle(android.R.attr.progressBarStyleInverse);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        // custom toolbar settings
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.my_available_services_title);
        getSupportActionBar().setSubtitle(R.string.my_subtitle);
        getSupportActionBar().setIcon(R.mipmap.ic_toolbar);


        Intent i = getIntent();
        orsAvailableServicesSearch orsAVSS = i.getExtras().getParcelable("orsAvailableServicesSearch");

        //Toast.makeText(AvailableServices.this, orsAVSS.getdDate(), Toast.LENGTH_SHORT).show();

        //myVolleyService.getInstance(this).addToRequestQueue(jsonrequestobject);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        orsAvailableServicesTask orsAS = new orsAvailableServicesTask(AvailableServices.this);
        orsAS.getList(orsAVSS);

        //recyclerView.addItemDecoration(new);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AvailableServices.this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        /*
                        TextView tvItemID = (TextView) view.findViewById(R.id.tripID);

                        if (view.getId()==tvItemID.getId())
                        {
                            Toast.makeText(AvailableServices.this, view.getId()+ " onItem Click "+position + " tripID" + ((TextView) view.findViewById(R.id.tripID)).getText(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(AvailableServices.this, "onItem Click "+position + " tripID" + ((TextView) view.findViewById(R.id.tripID)).getText(), Toast.LENGTH_SHORT).show();
                        */

                        //String TripID = ((TextView) view.findViewById(R.id.tripID)).getText().toString();
                        //String BusType = arList.get(position).getBusType();

                        //orsTripLayoutSearch orsTLS = new orsTripLayoutSearch(TripID, BusType);
                        Intent intent = new Intent(AvailableServices.this, TripLayout.class);
                        //intent.putExtra("orsTripLayoutSearch", orsTLS);
                        intent.putExtra("orsAvailableServices", arList.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //Toast.makeText(AvailableServices.this, "onItem Long Click " + view.getId(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.book_eticket:
                Toast.makeText(AvailableServices.this, "Book eTicket clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void notifySuccess(ArrayList<orsAvailableServices> orsAS){
        //Log.d("myApp", "ors_availableServices TASK -response  " + orsAS);
        adapter = new orsAvailableServicesAdapter(orsAS);
        arList = orsAS;
        header_Title = (AppCompatTextView) findViewById(R.id.header_title);
        iv_bus = (ImageView) findViewById(R.id.iv_bus);

        String txt="";
        if (!arList.isEmpty())
        {
            txt = "Place of Boarding <b>" + arList.get(0).getBoarding() + "</b>";
            if (arList.get(0).getPlateform().length()>1)
            {
                txt+=" Plateform No. <b>" +arList.get(0).getPlateform() +"</b>";
            }
            txt+="<br/>" + "<font color='red' size='1'>Reservation Charges <b>Rs." + arList.get(0).getReservationCharges() + "</b> per seat extra.</font>";
            recyclerView.setAdapter(adapter);
        }else {
            txt = "<font color='red' size='3'><br/>Not available for online booking.<br/></font>";
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) header_Title.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            header_Title.setLayoutParams(layoutParams);
        }

        header_Title.setText(Html.fromHtml(txt));
        if (arList.get(0).getBusType().equals("Volvo")) {
            iv_bus.setImageResource(R.drawable.bus_volvo);
        } else {
            iv_bus.setImageResource(R.drawable.bus_ordinary);
        }
        dialog.dismiss();
    }
    @Override
    public void notifySuccess(JSONObject response){}
    @Override
    public void notifySuccess(JSONArray response){}
    @Override
    public void notifyError(VolleyError error){dialog.dismiss();}
}