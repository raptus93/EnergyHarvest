package gui;

import android.app.Activity;

/**
 * @version 1.1.4 (10/05/2014)
 * @author Kjell Bunjes
 *
 */

public class ActivityHolder {
	
	public static ActivityHolder instance;
	private Activity activeActivity;
	
	private ActivityHolder() {
		
	}
	
	public static ActivityHolder getInstance() {
		if(instance == null) {
			instance = new ActivityHolder();
		}
		return instance;
	}
	
	public Activity getActiveActivity() {
		return activeActivity;
	}
	
	public void setActiveActivity(Activity a) {
		activeActivity = a;
	}

}
