package com.example.fooder;

import java.io.IOException;

import util.DataBaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        DataBaseHelper db = new DataBaseHelper(this);
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
