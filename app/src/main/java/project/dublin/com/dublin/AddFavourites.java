package project.dublin.com.dublin;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import project.dublin.com.dublin.Adapters.RecyclerViewHorizontalListAdapter;
import project.dublin.com.dublin.Pojo.FavouriteTrip;
import project.dublin.com.dublin.Pojo.Images;

public class AddFavourites extends AppCompatActivity {
    Button submit_trip,chooseimage;
    EditText fromlocation,tolocation,comments,datefield;
    ImageView imageview;
    //a list to store all the favouriteTrips from firebase database
    List<FavouriteTrip> favouriteTrips;
    private Uri filePath;
    String image;

    private final int PICK_IMAGE_REQUEST = 71;
    int PICK_IMAGE_MULTIPLE = 1;
    ArrayList<String> imagesEncodedList;
    private RecyclerView RecyclerView;
    private RecyclerViewHorizontalListAdapter Adapter;


    //our database reference object
    DatabaseReference databasefavouriteTrips;
    Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourites);
        init();
    }
    public void init(){
        //getting the reference of databasefavouriteTrips node
        databasefavouriteTrips = FirebaseDatabase.getInstance().getReference("favouriteTrips");
        //list to store artists
        favouriteTrips = new ArrayList<>();
        submit_trip=(Button)findViewById(R.id.submit_trip);
        fromlocation=(EditText)findViewById(R.id.from_location);
        tolocation=(EditText)findViewById(R.id.to_location);
        comments=(EditText)findViewById(R.id.comments);
        chooseimage=(Button)findViewById(R.id.chooseimage);
        imageview=(ImageView)findViewById(R.id.imageview);

        datefield=(EditText)findViewById(R.id.date);
        RecyclerView = findViewById(R.id.idRecyclerViewHorizontalList);
        // add a divider after each item for more clarity
        RecyclerView.addItemDecoration(new DividerItemDecoration(AddFavourites.this, LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(AddFavourites.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.setLayoutManager(horizontalLayoutManager);
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();
            }
        });
        submit_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addfavouritetrip()
                //the method is defined below
                //this method is actually performing the write operation
                addfavouritetrip();
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        datefield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddFavourites.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        myCalendar = Calendar.getInstance();


    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        datefield.setText(sdf.format(myCalendar.getTime()));
    }
    private void ChooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
    }
    /*
    * This method is saving a new favourites to the
    * Firebase Realtime Database
    * */
    private void addfavouritetrip() {
        //getting the values to save
        String Str_fromlocation = fromlocation.getText().toString().trim();
        String Str_tolocation = tolocation.getText().toString().trim();
        String Str_comments = comments.getText().toString().trim();
        String str_date=datefield.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (user!=null){

             if (image!=null) {
                 if (!TextUtils.isEmpty(Str_fromlocation) && !TextUtils.isEmpty(Str_tolocation) && !TextUtils.isEmpty(Str_comments)&&!TextUtils.isEmpty(str_date)) {

                     //getting a unique id using push().getKey() method
                     //it will create a unique id and we will use it as the Primary Key for our Artist
                     String id = databasefavouriteTrips.push().getKey();

                     //creating an Artist Object
                     FavouriteTrip artist = new FavouriteTrip(user.getUid(), Str_fromlocation, Str_tolocation, Str_comments, imagesEncodedList,str_date);

                     //Saving the Artist
                     databasefavouriteTrips.child(id).setValue(artist);

                     //setting edittext to blank again
                     fromlocation.setText("");
                     tolocation.setText("");
                     comments.setText("");
                     datefield.setText("");
                     RecyclerView.setVisibility(View.GONE);
                     //displaying a success toast
                     Toast.makeText(this, "Favourites added", Toast.LENGTH_LONG).show();
                 } else {
                     //if the value is not given displaying a toast
                     Toast.makeText(this, "Please enter all details", Toast.LENGTH_LONG).show();
                 }
             }else{
                 Toast.makeText(this, "please select image to proceed", Toast.LENGTH_SHORT).show();
             }
        }


        //checking if the value is provided

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    image  = cursor.getString(columnIndex);
                    cursor.close();

                } else {

                    RecyclerView.setVisibility(View.VISIBLE);
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                           imageview.setImageBitmap(bitmap);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream .toByteArray();
                            image=Base64.encodeToString(byteArray, Base64.DEFAULT);
                            imagesEncodedList.add(image);



                        }

                        if (imagesEncodedList!=null){
                            Adapter = new RecyclerViewHorizontalListAdapter(imagesEncodedList, AddFavourites.this);
                            RecyclerView.setAdapter(Adapter);
                        }

                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
