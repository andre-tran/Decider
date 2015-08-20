package util;
 
import com.example.fooder.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
  
public class CustomCursorAdapter extends CursorAdapter {
  
    @SuppressWarnings("deprecation")
    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }
  
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.row, parent, false);
  
        return retView;
    }
  
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views
  
        TextView col1 = (TextView) view.findViewById(R.id.col1);
        col1.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        
    }
}
