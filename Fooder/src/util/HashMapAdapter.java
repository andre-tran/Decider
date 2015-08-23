package util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.example.fooder.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HashMapAdapter extends BaseAdapter {
    private LinkedHashMap<String,HashMap<String, String>> list;
	Activity activity;
    TextView business_name_view;
	ImageView rating_view;
	TextView address_view;
    private String[] mKeys;
    
    public HashMapAdapter(Activity activity, LinkedHashMap<String,HashMap<String, String>> data){
		this.activity = activity;
        list  = data;
        mKeys = list.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        String key = mKeys[pos];

        LayoutInflater inflater=activity.getLayoutInflater();
		
//		if(convertView == null){
			convertView=inflater.inflate(R.layout.search_layout, parent, false);
			
			business_name_view=(TextView) convertView.findViewById(R.id.business_name);
			rating_view=(ImageView) convertView.findViewById(R.id.rating);
			address_view=(TextView) convertView.findViewById(R.id.address);
//		}
		
		HashMap<String, String> map=list.get(key);
		business_name_view.setText(map.get("business_name"));
		setRatingPNG(map.get("rating"), rating_view);
		address_view.setText(map.get("address"));
		
//		new DownloadImageTask(rating_view).execute(map.get("rating"));

		
		return convertView;
    }
    
    public void setRatingPNG(String rating, ImageView view){
    	if(rating.equals("1.0"))
    		view.setImageResource(R.drawable.stars1);
    	else if(rating.equals("1.5"))
    		view.setImageResource(R.drawable.stars15);
    	else if(rating.equals("2.0"))
    		view.setImageResource(R.drawable.stars2);
    	else if(rating.equals("2.5"))
    		view.setImageResource(R.drawable.stars25);
    	else if(rating.equals("3.0"))
    		view.setImageResource(R.drawable.stars3);
    	else if(rating.equals("3.5"))
    		view.setImageResource(R.drawable.stars35);
    	else if(rating.equals("4.0"))
    		view.setImageResource(R.drawable.stars4);
    	else if(rating.equals("4.5"))
    		view.setImageResource(R.drawable.stars45);
    	else if(rating.equals("5.0"))
    		view.setImageResource(R.drawable.stars5);
    	else
    		view.setImageResource(R.drawable.stars0);
    }
    
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	        bmImage.setVisibility(View.VISIBLE);
	    }
	}
}
