package in.gov.hartrans.etickets.Models;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.gov.hartrans.etickets.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by manojK on 07/11/2016.
 */
public class orsAvailableServicesAdapter  extends RecyclerView.Adapter<orsAvailableServicesAdapter.MyViewHolder>{
    ArrayList<orsAvailableServices> arrayList=new ArrayList<>();
    public orsAvailableServicesAdapter(ArrayList<orsAvailableServices> arrayList)
    {
        this.arrayList =arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_available_services_row_item,parent,false);
        view.getBackground().setAlpha(160);
        //view.setAlpha(.5f);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String TripRouteVia ="<em>"+ arrayList.get(position).getBusType() +"</em>: <strong>"+arrayList.get(position).getTripRoute()+"</strong>";
        TripRouteVia+="<br/><small>Via: <i>" + arrayList.get(position).getVia() + "</i>";
        TripRouteVia+=" Route KMs: " + arrayList.get(position).getrKMS()+"</small>";

        holder.TripRoute.setText(Html.fromHtml(TripRouteVia));
        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        holder.TripID.setText(arrayList.get(position).getTripID()+"");
        holder.Jtime1.setText(output.format(arrayList.get(position).getjTime1()));
        holder.rFare.setText(Html.fromHtml("Seats: "+arrayList.get(position).getAvailableSeats()+"<br/>\u20B9 &nbsp;"+arrayList.get(position).getrFare()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TripID, TripRoute, Jtime1, rFare;
        public MyViewHolder(View itemView) {
            super(itemView);
            TripID = (TextView)itemView.findViewById(R.id.tripID);
            Jtime1 = (TextView)itemView.findViewById(R.id.jTime1);
            TripRoute = (TextView)itemView.findViewById(R.id.tripRoute);
            rFare = (TextView)itemView.findViewById(R.id.rFare);
        }
    }
}