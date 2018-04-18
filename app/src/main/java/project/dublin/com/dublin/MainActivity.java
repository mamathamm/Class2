package project.dublin.com.dublin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView lv_bikes;
    ProgressDialog mProgressDialog;
    public ArrayList<BikesPojo> bikesList;
    public ArrayList<String> bikes;
    public ArrayList<String> status_bikes;
    JSONArray jsonArray = null;

    BikesAdapter list_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        lv_bikes = (ListView) findViewById(R.id.lv_bikes);
        //Calling service for getting details
        new BikesAsyncTask().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profileimagelayout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_profile:
                Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

    private class BikesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            bikesList = new ArrayList<BikesPojo>();
            bikes = new ArrayList<>();
            status_bikes = new ArrayList<>();

            bikesList.clear();
            bikes.clear();
            status_bikes.clear();

            // Making a request to url and getting response

            String url = "https://api.jcdecaux.com/vls/v1/stations?contract=Dublin&apiKey=9fd09f46b66f01b86739c52308447121f8eba5a9";
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);


            if (jsonStr != null) {
                try {

                    jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject c = jsonArray.getJSONObject(i);

                        int number = c.getInt("number");
                        String name = c.getString("name");

                        String address = c.getString("address");

                        JSONObject jsonObject = c.getJSONObject("position");
                        Double lat = jsonObject.getDouble("lat");
                        Double lng = jsonObject.getDouble("lng");

                        boolean banking = c.getBoolean("banking");
                        boolean bonus = c.getBoolean("bonus");
                        String status = c.getString("status");
                        String contract_name = c.getString("contract_name");
                        int bike_stands = c.getInt("bike_stands");
                        int available_bike_stands = c.getInt("available_bike_stands");
                        int available_bikes = c.getInt("available_bikes");
                        String update = c.getString("last_update");
                        String last_update = getDate(Long.parseLong(update));
                        bikes.add(name);
                        status_bikes.add(status);
                        BikesPojo bikObj = new BikesPojo(number, name, address, lat, lng, banking, bonus, status, contract_name, bike_stands, available_bike_stands, available_bikes, last_update);

                        bikesList.add(bikObj);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            mProgressDialog.dismiss();

            list_adapter = new BikesAdapter(MainActivity.this, bikes, status_bikes, bikesList);
            lv_bikes.setAdapter(list_adapter);


        }

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


}
