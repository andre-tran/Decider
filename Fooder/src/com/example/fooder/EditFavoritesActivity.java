package com.example.fooder;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DataBaseHelper;
import util.Food;
import util.YelpAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFavoritesActivity extends Activity {
	DataBaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_favorites);
		setTitle("Edit Favorites");
		db = new DataBaseHelper(this);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
		    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_favorites, menu);
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
	
	public void back(View view) {
		finish();
	}
	
	public void add(View view){
		EditText txtName=(EditText)this.findViewById(R.id.foodName);
		String foodName = txtName.getText().toString();

		if(checkFoodName(foodName)){
			db.addFood(new Food(foodName));
			txtName.setText("");
			Toast.makeText(EditFavoritesActivity.this, "Food Added",Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void search(View view) {
		EditText txtName=(EditText)this.findViewById(R.id.foodName);
		String foodName = txtName.getText().toString();
		
		if(checkFoodName(foodName)){
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra("foodName", foodName);
			startActivity(intent);
		}
	}
	
	public void save(View view) {
		Intent intent = new Intent(this, DisplayActivity.class);
	    startActivity(intent);
	}
	
	public void remove(View view) {
		db.removeAll(db);
	}
	
	public boolean checkFoodName(String foodName){
		Food fd = db.getName(foodName);
		if(fd != null || foodName.equals("")){
			Toast.makeText(EditFavoritesActivity.this, "Error: Food Already Registered",Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
//	private final LocationListener locationListener = new LocationListener() {
//	    public void onLocationChanged(Location location) {
//	        longitude = location.getLongitude();
//	        latitude = location.getLatitude();
//	    }
//	}
}
