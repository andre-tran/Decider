package util;
  
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
  
public class DataBaseHelper extends SQLiteOpenHelper {
  
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
  
    // Database Name
    private static final String DATABASE_NAME = "foodDB";
  
    // Contacts table name
    private static final String TABLE_FOODS = "foods";
  
    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_RATING = "rating";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_URL = "url";
  
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
  
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOODS_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + KEY_ID + " integer primary key autoincrement, " + KEY_NAME + " TEXT, " 
        		+ KEY_RATING + " TEXT, " + KEY_ADDRESS + " TEXT, " + KEY_URL + " TEXT);";
        db.execSQL(CREATE_FOODS_TABLE);       
    }
  
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
  
        // Create tables again
        onCreate(db);
    }
  
    public // Adding new food
    void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
  
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, food.getName()); // Food Name
        values.put(KEY_RATING, food.getRating());
        values.put(KEY_ADDRESS, food.getAddress());
        values.put(KEY_URL, food.getUrl());
        
        Log.v("ADDING TO DB", food.getName() + " " + food.getRating() + " " + food.getAddress() + " " + food.getUrl());
        
        // Inserting Row
        db.insert(TABLE_FOODS, null, values);
        db.close(); // Closing database connection
    }
    
    public Cursor getAllFoods () {
//      SQLiteDatabase db = this.getWritableDatabase();
//    	Cursor mCursor = db.query(TABLE_FOODS, new String[] {KEY_ID, KEY_NAME, KEY_RATING, KEY_ADDRESS}, 
//    		    null, null, null, null, null);
//    		 
//    		  if (mCursor != null) {
//    		   mCursor.moveToFirst();
//    		  }
//    		  return mCursor;
        SQLiteDatabase db = this.getWritableDatabase();
        String buildSQL = "SELECT * FROM " + TABLE_FOODS;
        Cursor cursor = db.rawQuery(buildSQL, null);
        cursor.moveToFirst();
        return cursor;
    }
    
    public LinkedHashMap<String,HashMap<String, String>> getAllFoodsToHashMap(){
    	LinkedHashMap<String,HashMap<String, String>> list = new LinkedHashMap<String,HashMap<String,String>>();
    	SQLiteDatabase db = this.getWritableDatabase();
        String buildSQL = "SELECT * FROM " + TABLE_FOODS;
        Cursor cursor = db.rawQuery(buildSQL, null);
        cursor.moveToFirst();
        
        do {
        	String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
        	String rating = cursor.getString(cursor.getColumnIndex(KEY_RATING));
        	String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
        	Log.v("HASH", name + " " + rating + " " + address);
        	
        	HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("business_name", name);
			temp.put("rating", rating);
			temp.put("address", address);
			list.put(name, temp);
        } while (cursor.moveToNext());
    	return list;
    }
    
    // Getting single food
    public Food getName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
  
        Cursor cursor = db.query(TABLE_FOODS, new String[] { 
                KEY_ID, KEY_NAME }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount() > 0){
			Food food = new Food((cursor.getString(0)), "", "", "");
			// return contact
			return food;
        }
        else
            return null;
    }
    
    public void removeAll(DataBaseHelper db) {
//    	db.delete(KEY_NAME, null, null);
    }
    
    public void removeFood(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_FOODS, KEY_NAME + " = ?",
                new String[] { name });
        db.close();
         
        Log.d("Delete: ", "Food name: " + name);

    }

}
