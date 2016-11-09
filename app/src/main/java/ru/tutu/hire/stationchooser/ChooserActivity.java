package ru.tutu.hire.stationchooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChooserActivity extends AppCompatActivity {

    private Button buttonFrom;
    private Button buttonTo;
    private static final String TAG = "ChooserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        buttonFrom = (Button) findViewById(R.id.buttonFrom);
        buttonFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Intent i = new Intent(ChooserActivity.this, StationListActivity.class);
                i.putExtra("direction", "citiesFrom");
                startActivityForResult(i, 0);
            }
        });

        buttonTo = (Button) findViewById(R.id.buttonTo);
        buttonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooserActivity.this, StationListActivity.class);
                i.putExtra("direction", "citiesTo");
                startActivityForResult(i, 0);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (intent == null) {return;}
        if (resultCode==RESULT_OK) {
            String stationName = intent.getStringExtra("stationName");
            String direction = intent.getStringExtra("direction");

            if (direction.equals("citiesFrom")) {
                buttonFrom.setText(stationName);
            }

            if (direction.equals("citiesTo")) {
                buttonTo.setText(stationName);
            }
        }
    }
}
