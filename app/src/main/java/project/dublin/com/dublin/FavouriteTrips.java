package project.dublin.com.dublin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.dublin.com.dublin.Adapters.FavouriteTripsList;
import project.dublin.com.dublin.Pojo.FavouriteTrip;

public class FavouriteTrips extends AppCompatActivity {
    //our database reference object
    DatabaseReference databasefavourites;
    List<FavouriteTrip> favouriteTrips;
    ListView fav_listview;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_trips);
        //getting the reference of databasefavourites node
        databasefavourites = FirebaseDatabase.getInstance().getReference("favouriteTrips");
        favouriteTrips=new ArrayList<FavouriteTrip>();
        fav_listview=(ListView)findViewById(R.id.listview);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mProgressDialog = new ProgressDialog(FavouriteTrips.this);
        mProgressDialog.setMessage("Please wait....");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        //attaching value event listener
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databasefavourites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous favouriteTrip list
                favouriteTrips.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting favouriteTrip
                    FavouriteTrip favouriteTrip = postSnapshot.getValue(FavouriteTrip.class);
                    //adding favouriteTrips to the list
                     if (favouriteTrip.getUserid().equalsIgnoreCase(user.getUid())){
                         favouriteTrips.add(favouriteTrip);
                     }

                }

                //creating adapter
                FavouriteTripsList favouriteTripsListadapter = new FavouriteTripsList(FavouriteTrips.this, favouriteTrips);
                //attaching adapter to the listview
                fav_listview.setAdapter(favouriteTripsListadapter);
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addbuttonlayout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_add was selected
            case R.id.action_add:
                Intent intent=new Intent(FavouriteTrips.this, AddFavourites.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

}
