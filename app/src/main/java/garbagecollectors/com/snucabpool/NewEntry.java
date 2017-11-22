package garbagecollectors.com.snucabpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.app.DialogFragment;

public class NewEntry extends AppCompatActivity  {

    int count=0;
    String source, destination,time;
    String AM_PM ;
    Button buttonstartSetDialog,buttonChangeDate;
    TextView text;
    static String date;

    TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        text = (TextView) findViewById(R.id.searched_address);//Check

        buttonChangeDate = (Button) findViewById(R.id.SetTime);
        buttonChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);
            }
        });

        buttonstartSetDialog = (Button) findViewById(R.id.btnChangeDate);
        buttonstartSetDialog.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });
    }

    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                NewEntry.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Choose Time");
        timePickerDialog.show();

    }

    OnTimeSetListener onTimeSetListener
            = new OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            int HourOfDay=calSet.get(Calendar.HOUR_OF_DAY);
            int minOfDay=calSet.get(Calendar.MINUTE);
            if(hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            time=HourOfDay+":"+minOfDay+" "+AM_PM;
            //text.setText(time);
        }};

    public void findSource(View view) {

        count=0;
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);

        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                if(count==0){
                    source=(place.getName()+",\n"+
                            place.getAddress() +"\n" + place.getPhoneNumber());//check
                    //((TextView) findViewById(R.id.searched_address)).setText(source);
                }
                else{
                    destination=(place.getName()+",\n"+
                            place.getAddress() +"\n" + place.getPhoneNumber());//check
                    //((TextView) findViewById(R.id.searched_address)).setText(destination);
                }


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void findDestination(View view) {
        count=1;
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    //Method for SAVE Button
    public void finalSave(View view){

    }
}
