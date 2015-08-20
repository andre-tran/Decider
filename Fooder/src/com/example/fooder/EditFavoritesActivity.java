package com.example.fooder;

import util.DataBaseHelper;
import util.Food;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditFavoritesActivity extends Activity {
	DataBaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_favorites);
		setTitle("Edit Favorites");
		db = new DataBaseHelper(this);
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
		String foodName = txtName.toString();
		Food fd = db.getName(foodName);

		db.addFood(new Food(foodName));
		
		Toast.makeText(EditFavoritesActivity.this, "Food Added",Toast.LENGTH_LONG).show();
	}
	
	public void save(View view) {
		Intent intent = new Intent(this, DisplayActivity.class);
	    startActivity(intent);
	}
}
