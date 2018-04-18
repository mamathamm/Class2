package project.dublin.com.dublin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    // Creating button.
    Button logout,favourite_trips ;

    // Creating TextView.
    TextView userEmailShow ;

    // Creating FirebaseAuth.
    FirebaseAuth firebaseAuth ;

    // Creating FirebaseAuth.
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Assigning ID's to button and TextView.
        logout = (Button)findViewById(R.id.logout);
        favourite_trips=(Button)findViewById(R.id.favourite_trips);
        userEmailShow = (TextView)findViewById(R.id.user_email);

        // Adding FirebaseAuth instance to FirebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();
        // On activity start check whether there is user previously logged in or not.
        if(firebaseAuth.getCurrentUser() == null){

            // Finishing current Profile activity.
            finish();

            // If user already not log in then Redirect to LoginActivity .
            Intent intent = new Intent(ProfileActivity.this, Login.class);
            startActivity(intent);

            // Showing toast message.
            Toast.makeText(ProfileActivity.this, "Please Log in to continue", Toast.LENGTH_LONG).show();

        }
        // Adding firebaseAuth current user info into firebaseUser object.
        firebaseUser = firebaseAuth.getCurrentUser();

        // Getting logged in user email from firebaseUser.getEmail() method and set into TextView.
        userEmailShow.setText( firebaseUser.getEmail());


        // Adding click listener on logout button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Destroying login season.
                firebaseAuth.signOut();

                // Finishing current User Profile activity.
                finish();
                finishAffinity();

                // Redirect to Login Activity after click on logout button.
                Intent intent = new Intent(ProfileActivity.this, Login.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(ProfileActivity.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();

            }
        });
        favourite_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FavouriteTrips.class);
                startActivity(intent);
            }
        });


    }
    }

