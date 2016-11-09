package ru.tutu.hire.stationchooser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by wazzy on 08.11.16.
 */

public class StationActivity extends AppCompatActivity {

    private  TextView name;
    private TextView address;
    private Station station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        try {
            String s = getIntent().getStringExtra("station");
            station = new Station(new JSONObject(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        name = (TextView) findViewById(R.id.station_name);
        address = (TextView) findViewById(R.id.address);

        name.setText(station.getStationTitle());

        StringBuilder addressString = new StringBuilder();
        addressString.append(station.getCityTitle()).append(", ");
        if (!station.getDistrictTitle().isEmpty()) {
            addressString.append(station.getDistrictTitle()).append(", ");
        }
        addressString.append(station.getCountryTitle());

        address.setText(addressString.toString());
    }
}
