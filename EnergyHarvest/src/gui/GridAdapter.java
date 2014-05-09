package gui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.energyharvest.R;

/**
 * 
 * Taken from: https://www.linux.com/learn/tutorials/748050-how-to-set-up-a-list-of-clickable-images-in-android-app-development-with-gridview (05/05/2014)
 *
 */

public class GridAdapter extends BaseAdapter {
	private Context context;
	private Integer[] imageIds = {
            R.drawable.clan_logo, R.drawable.clan_logo_default, R.drawable.logo, R.drawable.clan_logo, R.drawable.clan_logo, R.drawable.clan_logo, 
            R.drawable.clan_logo, R.drawable.clan_logo, R.drawable.clan_logo, R.drawable.clan_logo, 
	};
	
	public GridAdapter(Context c) {
		context = c;
	}
	
	public int getCount() {
		return imageIds.length;
	}
	
	public Object getItem(int position) {
		return imageIds[position];
	}
	
	public long getItemId(int position) {
		return 0;
	}
	
	public View getView(int position, View view, ViewGroup parent) {
		ImageView iview;
		if (view == null) {
			iview = new ImageView(context);
			iview.setLayoutParams(new GridView.LayoutParams(175,175));
			iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iview.setPadding(5, 5, 5, 5);
		} else {
			iview = (ImageView) view;	
		}
		iview.setImageResource(imageIds[position]);
		return iview;
	}
}