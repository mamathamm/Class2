package project.dublin.com.dublin.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.dublin.com.dublin.FavouriteTripDetails;
import project.dublin.com.dublin.Pojo.FavouriteTrip;
import project.dublin.com.dublin.R;



public class FavouriteTripsList extends ArrayAdapter<FavouriteTrip> {
    private Activity context;
    private static  List<FavouriteTrip> favouriteTrips;
    public FavouriteTripsList(Activity context, List<FavouriteTrip> favouriteTrips) {
        super(context, R.layout.favlist_model, favouriteTrips);
        this.context = context;
        this.favouriteTrips = favouriteTrips;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.favlist_model, null, true);

        TextView location = (TextView) listViewItem.findViewById(R.id.location);
        TextView comments = (TextView) listViewItem.findViewById(R.id.comments);
        ImageView trip_image=(ImageView)listViewItem.findViewById(R.id.trip_image);
        final FavouriteTrip favouriteTrip = favouriteTrips.get(position);
        location.setText(favouriteTrip.getFromlocation()+" - "+favouriteTrip.getTolocation());
        comments.setText(favouriteTrip.getComments());
        if (favouriteTrip.getImage()!=null){
            for (int i=0;i<favouriteTrip.getImage().size();i++){
                if (i==0){
                    byte[] decodedString = Base64.decode(favouriteTrip.getImage().get(i), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    trip_image.setImageBitmap(decodedByte);
                }

            }

        }
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, FavouriteTripDetails.class);
                intent.putExtra("position",""+position);
                context.startActivity(intent);
            }
        });

        return listViewItem;
    }
    public static FavouriteTrip favouriteTripdata(int position){
        FavouriteTrip favouriteTrip=favouriteTrips.get(position);
        return  favouriteTrip;
    }
}
