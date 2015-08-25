package com.example.fooder;

import util.YelpSettings;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class LocalSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_settings);

		setResources();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.local_settings, menu);
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
	
	public void save(View view) {
		saveSettings();
		finish();
	}
	
	public void setResources(){
		Spinner sort_spinner = (Spinner) findViewById(R.id.sort_spinner2);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.sort_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sort_spinner.setAdapter(adapter);
		
		Spinner distance_spinner = (Spinner) findViewById(R.id.distance_spinner2);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.distance_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distance_spinner.setAdapter(adapter2);
		
		distance_spinner.setSelection(2);
		
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
