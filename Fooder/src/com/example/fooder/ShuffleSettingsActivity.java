package com.example.fooder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class ShuffleSettingsActivity extends Activity {
	CheckBox favorites;
	CheckBox local;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shuffle_settings);
		favorites = (CheckBox) findViewById(R.id.favoriteCheck);
		favorites.setChecked(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shuffle, menu);
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
	
	public void start(View view){
		favorites = (CheckBox) findViewById(R.id.favoriteCheck);
		local = (CheckBox) findViewById(R.id.localCheck);
		Intent intent = new Intent(this, ShuffleActivity.class);
		intent.putExtra("favorites", favorites.isChecked());
		intent.putExtra("local", local.isChecked());
	    startActivity(intent);
	}
}
