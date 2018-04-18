package project.dublin.com.dublin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import project.dublin.com.dublin.Adapters.RecyclerViewHorizontalListAdapter;
import project.dublin.com.dublin.Pojo.FavouriteTrip;

import static project.dublin.com.dublin.Adapters.FavouriteTripsList.favouriteTripdata;

public class FavouriteTripDetails extends AppCompatActivity {
      TextView date,fromlocation,tolocation,comments;
      RecyclerView recyclerview;
    FavouriteTrip favouriteTrip;
    String index;
    RecyclerViewHorizontalListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_trip_details);
        index=getIntent().getStringExtra("position");
         favouriteTrip=favouriteTripdata(Integer.parseInt(index));
        init();
    }
    public void init(){
        date=(TextView)findViewById(R.id.date);
        fromlocation=(TextView)findViewById(R.id.from_location);
        tolocation=(TextView)findViewById(R.id.to_location);
        comments=(TextView)findViewById(R.id.comments);
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new DividerItemDecoration(FavouriteTripDetails.this, LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(FavouriteTripDetails.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(horizontalLayoutManager);
        adapter = new RecyclerViewHorizontalListAdapter(favouriteTrip.getImage(), FavouriteTripDetails.this);
        recyclerview.setAdapter(adapter);
        fromlocation.setText(favouriteTrip.getFromlocation());
        tolocation.setText(favouriteTrip.getTolocation());
        comments.setText(favouriteTrip.getComments());
        date.setText(favouriteTrip.getDate());
    }
}
