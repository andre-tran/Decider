package com.example.fooder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.logging.Logger;

import util.DataBaseHelper;
import util.ShakeEventListener;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShuffleActivity extends Activity {
	boolean favorites;
	boolean local;
	int count = 0;
	int iter = 0;
	TextView display;
	ImageView rating;
	TextView address;
	String[] foods;
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	float x1, x2;
	float y1, y2;
	static final int MIN_DISTANCE = 150;
	private ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shuffle);
		Bundle extras = getIntent().getExtras();
		favorites = extras.getBoolean("favorites");
		local = extras.getBoolean("local");

		final DataBaseHelper db = new DataBaseHelper(this);
		Cursor cursor = db.getAllFoods();

		foods = new String[cursor.getCount()];

		do {
			String business_name = cursor.getString(cursor.getColumnIndex("name"));
        	String rating = cursor.getString(cursor.getColumnIndex("rating"));
        	String address = cursor.getString(cursor.getColumnIndex("address"));
        	Log.v("SHUFFLE", business_name + " " + rating + " " + address);
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("business_name", business_name);
			temp.put("rating", rating);
			temp.put("address", address);
			arrayList.add(temp);
			count++;
		} while (cursor.moveToNext());

		Log.i("ANDRE", Integer.toString(cursor.getCount()));
		shuffleArrayList(arrayList);

		display = (TextView) findViewById(R.id.display);
		rating = (ImageView) findViewById(R.id.rating);
		address = (TextView) findViewById(R.id.address);
		nextFood();

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();

		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
			public void onShake() {
				nextFood();
			}
		});
	}

	public boolean onTouchEvent(MotionEvent touchevent) {
		switch (touchevent.getAction()) {
		// when user first touches the screen we get x and y coordinate
		case MotionEvent.ACTION_DOWN: {
			x1 = touchevent.getX();
			y1 = touchevent.getY();
			break;
		}
		case MotionEvent.ACTION_UP: {
			x2 = touchevent.getX();
			y2 = touchevent.getY();
			float deltaX = x2 - x1;
			// if left to right sweep event on screen
			if ((x2 - x1) > MIN_DISTANCE) {
				nextFood();
			}

			// if right to left sweep event on screen
			if ((x1 - x2) > MIN_DISTANCE) {
				nextFood();
			}

//			// if UP to Down sweep event on screen
//			if (y1 < y2) {
//				Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
//			}
//
//			// if Down to UP sweep event on screen
//			if (y1 > y2) {
//				Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
//			}
			break;
		}
		}
		return false;
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

	public void start(View view) {
		nextFood();
	}

	public void nextFood() {
		if (iter < count) {
			display.setText(arrayList.get(iter).get("business_name"));
			setRatingPNG(arrayList.get(iter).get("rating"), rating);
			address.setText(arrayList.get(iter).get("address"));
			iter++;
		} else {
			Toast.makeText(this, "Restarting List", Toast.LENGTH_LONG).show();
			shuffleArrayList(arrayList);
			iter = 0;
		}
	}
	
	public void shuffleArrayList(ArrayList<HashMap<String, String>> ar){
		Collections.shuffle(ar);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}
	
    public void setRatingPNG(String rating, ImageView view){
    	if(rating.equals("1.0")){
    		view.setImageResource(R.drawable.stars1);
    		view.setTag("1.0");
    	}
    	else if(rating.equals("1.5")){
    		view.setImageResource(R.drawable.stars15);
    		view.setTag("1..5");
    	}
    	else if(rating.equals("2.0")){
    		view.setImageResource(R.drawable.stars2);
    		view.setTag("2.0");
    	}
    	else if(rating.equals("2.5")){
    		view.setImageResource(R.drawable.stars25);
    		view.setTag("2.5");
    	}
    	else if(rating.equals("3.0")){
    		view.setImageResource(R.drawable.stars3);
    		view.setTag("3.0");
    	}
    	else if(rating.equals("3.5")){
    		view.setImageResource(R.drawable.stars35);
    		view.setTag("3.5");
    	}
    	else if(rating.equals("4.0")){
    		view.setImageResource(R.drawable.stars4);
    		view.setTag("4.0");
    	}
    	else if(rating.equals("4.5")){
    		view.setImageResource(R.drawable.stars45);
    		view.setTag("4.5");
    	}
    	else if(rating.equals("5.0")){
    		view.setImageResource(R.drawable.stars5);
    		view.setTag("5.0");
    	}
    	else{
    		view.setImageResource(R.drawable.stars0);
    		view.setTag("0.0");
		}
    }
}
