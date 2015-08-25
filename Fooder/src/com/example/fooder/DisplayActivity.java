package com.example.fooder;

import java.util.HashMap;
import java.util.LinkedHashMap;

import util.CustomCursorAdapter;
import util.DataBaseHelper;
import util.Food;
import util.HashMapAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DisplayActivity extends Activity {
	LinkedHashMap<String, HashMap<String, String>> foods;
	ListView list;
    private SimpleCursorAdapter dataAdapter;
    String selectedName = "";
    String selectedUrl = "";
    DataBaseHelper db;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		db = new DataBaseHelper(this);      
        Cursor cursor = db.getAllFoods();
        String[] columns = new String[] {DataBaseHelper.KEY_NAME};
        int[] to = new int[] {R.id.business_name};
       
		
		foods = db.getAllFoodsToHashMap();
		HashMapAdapter adapter = new HashMapAdapter(this, foods);
		list = (ListView) findViewById(R.id.lv_name);

		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				view.setSelected(true);
				// selected item
				selectedName = ((TextView) view.findViewById(R.id.business_name)).getText().toString();
				selectedUrl = ((TextView) view.findViewById(R.id.url)).getText().toString();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
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
	
	public void moreInfo(View view){
		if(!selectedName.equals("")){
			Toast.makeText(this, "View More Info", Toast.LENGTH_LONG).show();
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedUrl));
			startActivity(browserIntent);
		}
		else{
			Toast.makeText(this, "Select a Food", Toast.LENGTH_LONG).show();
		}
	}
	
	public void remove(View view){
		if(!selectedName.equals("")){
			db.removeFood(selectedName);
			Toast.makeText(this, "Food Removed", Toast.LENGTH_LONG).show();
			finish();
			startActivity(getIntent());
		}
		else{
			Toast.makeText(this, "Select a Food", Toast.LENGTH_LONG).show();
		}
	}
}
