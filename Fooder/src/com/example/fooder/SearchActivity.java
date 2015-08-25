package com.example.fooder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DataBaseHelper;
import util.Food;
import util.HashMapAdapter;
import util.YelpAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	String foodName;
	ListView listView;
	private LinkedHashMap<String,HashMap<String, String>> list;
	String selectedName = "";
	String selectedRating = "";
	String selectedAddress = "";
	String selectedUrl = "";
	DataBaseHelper db;
	ArrayList<HashMap<String, String>> params;
	String sortValue;
	String distanceValue;
	boolean american;
	boolean breakfast;
	boolean italian;
	boolean japanese;
	boolean korean;
	boolean mexican;
	boolean thai;
	boolean vietnamese;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		db = new DataBaseHelper(this);
		Bundle extras = getIntent().getExtras();
		foodName = extras.getString("foodName");
		sortValue = extras.getString("sortValue");
		distanceValue = extras.getString("distanceValue");
		
		american = extras.getBoolean("american");
		breakfast = extras.getBoolean("breakfast");
		italian = extras.getBoolean("italian");
		japanese = extras.getBoolean("japanese");
		korean = extras.getBoolean("korean");
		mexican = extras.getBoolean("mexican");
		thai = extras.getBoolean("thai");
		vietnamese = extras.getBoolean("vietnamese");
		
		listView = (ListView)findViewById(R.id.search_list);
		
		list = new LinkedHashMap<String,HashMap<String,String>>();
		
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		params = new ArrayList<HashMap<String, String>>();
		
		createParams();
		
		YelpAPI yelpApi = new YelpAPI();
	    String response = yelpApi.search(foodName, latitude, longitude, params);
	    System.out.println(response);
	    
	    try {
	        JSONObject json = new JSONObject(response);
	        JSONArray businesses;
	        businesses = json.getJSONArray("businesses");
	        for (int i = 0; i < businesses.length(); i++) {
				JSONObject business = businesses.getJSONObject(i);
				String business_name = business.getString("name");
				JSONObject loc = business.getJSONObject("location");
				String address = loc.getString("display_address");
				address = address.replace("\"", "");
				address = address.replace("[", "");
				address = address.replace("]", "");
				String rating = business.getString("rating");
				String url = business.getString("url");
				System.out.println(business_name + " " + business.getString("rating"));

				if (list.get(business_name) == null) {
					HashMap<String, String> temp = new HashMap<String, String>();
					temp.put("business_name", business_name);
					temp.put("rating", rating);
					temp.put("address", address);
					temp.put("url", url);
					list.put(business_name, temp);

				}
	        }
	        Log.v("DEBUG", "Size of list: " + Integer.toString(list.size()));
			HashMapAdapter adapter=new HashMapAdapter(this, list);
			listView.setAdapter(adapter);
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					view.setSelected(true);
					// selected item
					selectedName = ((TextView) view.findViewById(R.id.business_name)).getText().toString();
					selectedRating = (String) ((ImageView) view.findViewById(R.id.rating)).getTag();
					selectedAddress = ((TextView) view.findViewById(R.id.address)).getText().toString();
					selectedUrl = ((TextView) view.findViewById(R.id.url)).getText().toString();
				}
			});

	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
	
	public void add(View view) {
		if(checkFoodName(selectedName)){
			db.addFood(new Food(selectedName, selectedRating, selectedAddress, selectedUrl));
			Toast.makeText(this, "Food Added",Toast.LENGTH_LONG).show();
		}
		finish();
	}
	
	public boolean checkFoodName(String foodName){
		Food fd = db.getName(foodName);
		if(fd != null || foodName.equals("")){
			Toast.makeText(this, "Error: Food Already Registered",Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	public void createParams(){
		HashMap<String, String> temp = new HashMap<String, String>();
		ArrayList<String> categories = new ArrayList<String>();
		
		Log.v("Sort Value", sortValue);
		if(sortValue.equals("Distance")){
			temp.put("sort", "1");
		} else if(sortValue.equals("Highest Rated")){
			temp.put("sort", "2");
		} else {
			temp.put("sort", "0");
		}
		params.add(temp);
		
		Log.v("Radius Value", distanceValue);
		if(distanceValue.equals("5 Miles")){
			temp.put("radius_filter", "8000");
		} else if(distanceValue.equals("10 Miles")){
			temp.put("radius_filter", "16000");
		}  else if(distanceValue.equals("25 Miles")){
			temp.put("radius_filter", "40000");
		} else {
			temp.put("radius_filter", "1600");
		}
		
		if(american)
			categories.add("newamerican");
		if(breakfast)
			categories.add("breakfast_brunch");
		if(italian)
			categories.add("italian");
		if(japanese)
			categories.add("japanese");
		if(korean)
			categories.add("korean");
		if(mexican)
			categories.add("mexican");
		if(thai)
			categories.add("thai");
		if(vietnamese)
			categories.add("vietnamese");
		
		String cat = "";
		for(String category : categories){
			cat = cat + category + ",";
		}
		if(cat != ""){
			cat = removeLastChar(cat);
			temp.put("category_filter", cat);
		}
	}
	
	public void moreInfo(View view){
		if(!selectedName.equals("")){
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedUrl));
			startActivity(browserIntent);
		}
		else{
			Toast.makeText(this, "Select a Food", Toast.LENGTH_LONG).show();
		}
	}
	
	private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }
}
