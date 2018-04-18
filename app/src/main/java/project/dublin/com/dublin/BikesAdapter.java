package project.dublin.com.dublin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BikesAdapter extends BaseAdapter {
    ArrayList<String> bikes;
    Context context;
    ArrayList<String> status;
    ArrayList<BikesPojo> bikesPojos;
    private static LayoutInflater inflater = null;

    public BikesAdapter(MainActivity mainActivity, ArrayList<String> bikesList, ArrayList<String> statusList, ArrayList<BikesPojo> bikesPojosList) {
// TODO Auto-generated constructor stub
        bikes = bikesList;
        context = mainActivity;
        status = statusList;
        bikesPojos = bikesPojosList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return bikes.size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv_name;
        TextView tv_status;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        final Holder holder = new Holder();
        View view;
        view = inflater.inflate(R.layout.layout_bikes_list_item, null);

        holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
        holder.tv_status = (TextView) view.findViewById(R.id.tv_status);

        holder.tv_name.setText(bikes.get(position));
        holder.tv_status.setText(status.get(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                Intent detailsintent = new Intent(context, DetailsActivity.class);
                detailsintent.putExtra("address", bikesPojos.get(position).getAddress());
                detailsintent.putExtra("available_bike_stands", bikesPojos.get(position).getAvailable_bike_stands());
                detailsintent.putExtra("available_bikes", bikesPojos.get(position).getAvailable_bikes());
                detailsintent.putExtra("bike_stands", bikesPojos.get(position).getBike_stands());
                detailsintent.putExtra("contract_name", bikesPojos.get(position).getContract_name());
                detailsintent.putExtra("last_update", bikesPojos.get(position).getLast_update());
                detailsintent.putExtra("lat", bikesPojos.get(position).getLat());
                detailsintent.putExtra("lng", bikesPojos.get(position).getLng());
                detailsintent.putExtra("name", bikesPojos.get(position).getName());
                detailsintent.putExtra("number", bikesPojos.get(position).getNumber());
                detailsintent.putExtra("status", bikesPojos.get(position).getStatus());
                detailsintent.putExtra("banking", bikesPojos.get(position).isBanking());
                detailsintent.putExtra("bonus", bikesPojos.get(position).isBonus());


                context.startActivity(detailsintent);


            }
        });

        return view;
    }

}
