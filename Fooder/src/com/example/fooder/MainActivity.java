package com.example.fooder;

import java.io.IOException;

import util.DataBaseHelper;
import util.GPSTracker;
import util.YelpSettings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity{
	GPSTracker gps;   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DataBaseHelper db = new DataBaseHelper(this);
		gps = new GPSTracker(MainActivity.this);
		if(gps.canGetLocation()){                  
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude(); 
            
    		YelpSettings.getInstance().setLatitude(latitude);
    		YelpSettings.getInstance().setLongitude(longitude);

            
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void shuffle(View view) {
		Intent intent = new Intent(this, ShuffleSettingsActivity.class);
	    startActivity(intent);
	}
	
	public void editFavorites(View view) {
		Intent intent = new Intent(this, EditFavoritesActivity.class);
	    startActivity(intent);
	}
	
	public void localSettings(View view) {
		Intent intent = new Intent(this, LocalSettingsActivity.class);
	    startActivity(intent);
	}

}


