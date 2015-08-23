package util;
  
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
  
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
  
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + KEY_ID + " integer primary key autoincrement, " + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);       
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
  
        // Inserting Row
        db.insert(TABLE_FOODS, null, values);
        db.close(); // Closing database connection
    }
    
    public Cursor getAllFoods () {
      SQLiteDatabase db = this.getWritableDatabase();
    	Cursor mCursor = db.query(TABLE_FOODS, new String[] {KEY_ID, KEY_NAME}, 
    		    null, null, null, null, null);
    		 
    		  if (mCursor != null) {
    		   mCursor.moveToFirst();
    		  }
    		  return mCursor;
//        SQLiteDatabase db = this.getWritableDatabase();
//        String buildSQL = "SELECT * FROM " + TABLE_FOODS;
//        Cursor cursor = db.rawQuery(buildSQL, null);
//        cursor.moveToFirst();
//        return cursor;
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
			Food food = new Food((cursor.getString(0)));
			// return contact
			return food;
        }
        else
            return null;
    }
    
    public void removeAll(DataBaseHelper db) {
//    	db.delete(KEY_NAME, null, null);
    }

}
