package in.gov.hartrans.etickets.Models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manojk.ors.R;

import java.util.ArrayList;

/**
 * Created by manojK on 06/11/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<BookingDays> arrayList=new ArrayList<>();
    public RecyclerAdapter(ArrayList<BookingDays> arrayList)
    {
        this.arrayList =arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_available_services_row_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.StationID.setText(arrayList.get(position).getStationID());
        holder.StationName.setText(arrayList.get(position).getStationName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView StationID, StationName;

        public MyViewHolder(View itemView) {
            super(itemView);
            //StationID = (TextView) itemView.findViewById(R.id.stationID);
            //StationName = (TextView) itemView.findViewById(R.id.stationName);
        }
    }
}
