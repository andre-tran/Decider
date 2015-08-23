package com.example.fooder;

import util.CustomCursorAdapter;
import util.DataBaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DisplayActivity extends Activity {
	ListView list;
    private CustomCursorAdapter customAdapter;
    private SimpleCursorAdapter dataAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		final DataBaseHelper db = new DataBaseHelper(this);
        
        Cursor cursor = db.getAllFoods();
        
        String[] columns = new String[] {DataBaseHelper.KEY_NAME};
        
        int[] to = new int[] {R.id.name};
       
        dataAdapter = new SimpleCursorAdapter(
        	    this, R.layout.food_layout, 
        	    cursor, 
        	    columns, 
        	    to,
        	    0);
        list = (ListView) findViewById(R.id.lv_name);
        list.setAdapter(dataAdapter);

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
}
