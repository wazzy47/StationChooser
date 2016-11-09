package ru.tutu.hire.stationchooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import android.widget.SearchView;

/**
 * Created by wazzy on 06.11.16.
 */



public class StationListActivity extends AppCompatActivity {

    private static final String TAG = "StationListActivity";

    private Button searchButton;

    private ExpandableListView expListView;
    private ExpandableListAdapter expListAdapter;

    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    private String direction;
    private JSONObject json;
    private ArrayList<ArrayList<JSONObject>> stationsJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        direction = (String) getIntent().getSerializableExtra("direction");

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchField = (EditText) findViewById(R.id.search_view);
                makeAList(searchField.getText().toString());
            }
        });

        makeAList("");

    }

    public void makeAList(String searchString) {

        try {

            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            stationsJSON = new ArrayList<ArrayList<JSONObject>>();

            json = new JSONObject(loadJSONFromAsset());
            JSONArray cities = json.getJSONArray(direction);

            for (int cityIndex=0; cityIndex<cities.length(); ++cityIndex) {
                JSONObject city = cities.getJSONObject(cityIndex);
                JSONArray stations = city.getJSONArray("stations");

                ArrayList<String> stationsArr= new ArrayList<String>();
                ArrayList<JSONObject> stationsCity = new ArrayList<JSONObject>();

                for (int stationIndex=0; stationIndex<stations.length(); ++stationIndex) {
                    JSONObject station = stations.getJSONObject(stationIndex);
                    String stationString = station.getString("stationTitle");
                    if (stationString.toLowerCase().contains(searchString)) {
                        stationsArr.add(stationString);
                        stationsCity.add(station);
                    }

                }

                if (!(stationsArr.isEmpty())) {
                    String header = city.getString("countryTitle") + ", " + city.getString("cityTitle");
                    listDataHeader.add(header);
                    listDataChild.put(header, stationsArr);
                    stationsJSON.add(stationsCity);
                }

                //mapCityStations.put(city.getString("countryTitle") + " " + city.getString("cityTitle"), stationsArr);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        expListView = (ExpandableListView) findViewById(R.id.countries_and_cities_list);
        expListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(expListAdapter);
        expListView.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        String stationName = ((TextView) v.findViewById(R.id.station)).getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("stationName", stationName);
                        intent.putExtra("direction", direction);
                        setResult(RESULT_OK, intent);
                        finish();
                        return true;
                    }
                }
        );

        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);
                    // http://stackoverflow.com/questions/2353074/android-long-click-on-the-child-views-of-a-expandablelistview
                    // states that this will work the same as in onChildClickListener

                    Intent intent = new Intent(StationListActivity.this, StationActivity.class);

                    try {
                        JSONObject station = stationsJSON.get(groupPosition).get(childPosition);
                        intent.putExtra("station", station.toString()
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);

                    return true;
                }

                return false;
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("allStations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            try {
                is.read(buffer);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
