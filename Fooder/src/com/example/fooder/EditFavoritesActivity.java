package com.example.fooder;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DataBaseHelper;
import util.Food;
import util.YelpAPI;
import util.YelpSettings;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditFavoritesActivity extends Activity {
	DataBaseHelper db;
	Spinner sortText = null;
	Spinner distanceText = null;
	
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
		
		setResources();

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
		saveSettings();
		finish();
	}
	
	public void search(View view) {
		EditText txtName=(EditText)this.findViewById(R.id.foodName);
		String foodName = txtName.getText().toString();
		sortText = (Spinner) this.findViewById(R.id.sort_spinner);
		distanceText = (Spinner) this.findViewById(R.id.distance_spinner);
		saveSettings();
				
		if(checkFoodName(foodName)){
			Intent intent = new Intent(this, SearchActivity.class);
			if(foodName.equals(""))
				intent.putExtra("foodName", "food");
			else
				intent.putExtra("foodName", foodName);
			intent.putExtra("sortValue", YelpSettings.getInstance().getSortSetting());
			intent.putExtra("distanceValue", YelpSettings.getInstance().getDistanceSetting());
			
			CheckBox cat = (CheckBox)this.findViewById(R.id.american);
			intent.putExtra("american", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.breakfast);
			intent.putExtra("breakfast", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.italian);
			intent.putExtra("italian", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.japanese);
			intent.putExtra("japanese", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.korean);
			intent.putExtra("korean", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.mexican);
			intent.putExtra("mexican", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.thai);
			intent.putExtra("thai", cat.isChecked());
			cat = (CheckBox)this.findViewById(R.id.vietnamese);
			intent.putExtra("vietnamese", cat.isChecked());
			
			
			startActivity(intent);
		}
	}
	
	public void save(View view) {
		saveSettings();
		Intent intent = new Intent(this, DisplayActivity.class);
	    startActivity(intent);
	}
	
	public void remove(View view) {
		db.removeAll(db);
	}
	
	public boolean checkFoodName(String foodName){
		Food fd = db.getName(foodName);
		if(fd != null){
			Toast.makeText(EditFavoritesActivity.this, "Error: Food Already Registered",Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	public void setResources(){
		Spinner sort_spinner = (Spinner) findViewById(R.id.sort_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.sort_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sort_spinner.setAdapter(adapter);
		
		Spinner distance_spinner = (Spinner) findViewById(R.id.distance_spinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.distance_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distance_spinner.setAdapter(adapter2);
		
		distance_spinner.setSelection(2);
		
		sortText = (Spinner) this.findViewById(R.id.sort_spinner);
		distanceText = (Spinner) this.findViewById(R.id.distance_spinner);
		
		CheckBox temp = (CheckBox) findViewById(R.id.american);
		temp.setChecked(YelpSettings.getInstance().isAmerican());
		temp = (CheckBox) findViewById(R.id.breakfast);
		temp.setChecked(YelpSettings.getInstance().isBreakfast());
		temp = (CheckBox) findViewById(R.id.italian);
		temp.setChecked(YelpSettings.getInstance().isItalian());
		temp = (CheckBox) findViewById(R.id.japanese);
		temp.setChecked(YelpSettings.getInstance().isJapanese());
		temp = (CheckBox) findViewById(R.id.korean);
		temp.setChecked(YelpSettings.getInstance().isKorean());
		temp = (CheckBox) findViewById(R.id.mexican);
		temp.setChecked(YelpSettings.getInstance().isMexican());
		temp = (CheckBox) findViewById(R.id.thai);
		temp.setChecked(YelpSettings.getInstance().isThai());
		temp = (CheckBox) findViewById(R.id.vietnamese);
		temp.setChecked(YelpSettings.getInstance().isVietnamese());

	}
	
	public void saveSettings(){
		YelpSettings.getInstance().setSortSetting(sortText.getSelectedItem().toString());
		YelpSettings.getInstance().setDistanceSetting(distanceText.getSelectedItem().toString());
		
		CheckBox cat = (CheckBox)this.findViewById(R.id.american);
		YelpSettings.getInstance().setAmerican(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.breakfast);
		YelpSettings.getInstance().setBreakfast(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.italian);
		YelpSettings.getInstance().setItalian(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.japanese);
		YelpSettings.getInstance().setJapanese(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.korean);
		YelpSettings.getInstance().setKorean(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.mexican);
		YelpSettings.getInstance().setMexican(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.thai);
		YelpSettings.getInstance().setThai(cat.isChecked());
		cat = (CheckBox)this.findViewById(R.id.vietnamese);
		YelpSettings.getInstance().setVietnamese(cat.isChecked());
	}
}
