package project.dublin.com.dublin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView number_value, name_value, address_value, banking_value, bonus_value, status_value, contract_name_value,
            bike_stands_value, available_bike_stands_value, available_bikes_value, last_update_value;
    double latitude, langitude;
    Button view_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();

        number_value.setText(getIntent().getIntExtra("number", 1)+"");
        name_value.setText(getIntent().getStringExtra("name"));
        address_value.setText(getIntent().getStringExtra("address"));
        banking_value.setText(getIntent().getBooleanExtra("banking", false) + "");
        bonus_value.setText(getIntent().getBooleanExtra("bonus", false) + "");
        status_value.setText(getIntent().getStringExtra("status"));
        contract_name_value.setText(getIntent().getStringExtra("contract_name"));
        bike_stands_value.setText(getIntent().getIntExtra("bike_stands",1)+"");
        available_bike_stands_value.setText(getIntent().getIntExtra("available_bike_stands",1)+"");
        available_bikes_value.setText(getIntent().getIntExtra("available_bikes",1)+"");
        last_update_value.setText(getIntent().getStringExtra("last_update"));

        latitude = getIntent().getDoubleExtra("lat",53.350140);
        langitude = getIntent().getDoubleExtra("lng",-6.266155);

        view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewmap();
            }
        });



    }

    public void init() {
        //Initialize TextViews
        number_value = (TextView) findViewById(R.id.number_value);
        name_value = (TextView) findViewById(R.id.name_value);
        address_value = (TextView) findViewById(R.id.address_value);
        banking_value = (TextView) findViewById(R.id.banking_value);
        bonus_value = (TextView) findViewById(R.id.bonus_value);
        status_value = (TextView) findViewById(R.id.status_value);
        contract_name_value = (TextView) findViewById(R.id.contract_name_value);
        bike_stands_value = (TextView) findViewById(R.id.bike_stands_value);
        available_bike_stands_value = (TextView) findViewById(R.id.available_bike_stands_value);
        available_bikes_value = (TextView) findViewById(R.id.available_bikes_value);
        last_update_value = (TextView) findViewById(R.id.last_update_value);
        view_map=(Button)findViewById(R.id.view_map);


    }

    //View Map Button Functionality
    public void viewmap()
    {
        Intent call_map=new Intent(DetailsActivity.this,MapsActivity.class);
        call_map.putExtra("lat",latitude);
        call_map.putExtra("lng",langitude);
        call_map.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(call_map);
    }

}
