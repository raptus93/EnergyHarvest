package gui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.energyharvest.R;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void goToMap(View view) {
		Intent intent = new Intent(this, MapActivity.class);	    
	    startActivity(intent);
	}
	
	public void goToClan(View view) {
		Intent intent = new Intent(this, ClanActivity.class);	    
	    startActivity(intent);
	}
	
	public void goToProfile(View view) {
		Intent intent = new Intent(this, ProfileActivity.class);	    
	    startActivity(intent);
	}
	
	public void goToTutorial(View view) {
		Intent intent = new Intent(this, TutorialActivity.class);	    
	    startActivity(intent);
	}
	
	public void goToQuiz(View view) {
		Intent intent = new Intent(this, quiz.QuizGUI.class);	    
	    startActivity(intent);
	}

	public void arImageDemo(View view)
	
	{
		Intent i = new Intent(Intent.ACTION_MAIN);
		PackageManager manager = getPackageManager();
		i = manager.getLaunchIntentForPackage("com.valkyrieDemo");
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}
	public void arMapDemo(View view)
	
	{
		Intent i = new Intent(Intent.ACTION_MAIN);
		PackageManager manager = getPackageManager();
		i = manager.getLaunchIntentForPackage("com.SchachtelDemo");
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}
	
}
