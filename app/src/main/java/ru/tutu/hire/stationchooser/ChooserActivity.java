package ru.tutu.hire.stationchooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChooserActivity extends AppCompatActivity {

    private Button buttonFrom;
    private Button buttonTo;
    private Button date;
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

        date = (Button) findViewById(R.id.datePicker);
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");

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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save "to" and "from"
        savedInstanceState.putString("from", buttonFrom.getText().toString());
        savedInstanceState.putString("to", buttonTo.getText().toString());
        savedInstanceState.putString("date", date.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore button texts
        buttonFrom.setText(savedInstanceState.getString("from"));
        buttonTo.setText(savedInstanceState.getString("to"));
        date.setText(savedInstanceState.getString("date"));

    }


}
