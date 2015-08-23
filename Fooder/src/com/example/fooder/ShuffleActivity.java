package com.example.fooder;

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
import android.widget.TextView;
import android.widget.Toast;

public class ShuffleActivity extends Activity {
	boolean favorites;
	boolean local;
	int count = 0;
	int iter = 0;
	TextView display;
	String[] foods;
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	float x1, x2;
	float y1, y2;

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
			String uname = cursor.getString(cursor.getColumnIndex("name"));
			foods[count] = uname;
			count++;
		} while (cursor.moveToNext());

		Log.i("ANDRE", Integer.toString(cursor.getCount()));
		shuffleArray(foods);

		display = (TextView) findViewById(R.id.display);
		display.setText(foods[iter]);
		iter++;

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

			// if left to right sweep event on screen
			if (x1 < x2) {
				nextFood();
			}

			// if right to left sweep event on screen
			if (x1 > x2) {
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
			display.setText(foods[iter]);
			iter++;
		} else {
			Toast.makeText(this, "Restarting List", Toast.LENGTH_LONG).show();
			shuffleArray(foods);
			iter = 0;
		}
	}

	public void check(View view) {
		if (favorites)
			Toast.makeText(this, "Favorites", Toast.LENGTH_LONG).show();
		if (local)
			Toast.makeText(this, "Local", Toast.LENGTH_LONG).show();
	}

	public void shuffleArray(String[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			String a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
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
}
