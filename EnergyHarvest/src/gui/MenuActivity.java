package gui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.energyharvest.R;

import node.Callback;
import node.ChallengeBridge;
import node.Server;
import node.User;

import java.util.LinkedList;

/**
 * @version 1.1.3 (13/04/2014)
 * @author Kjell Bunjes
 *
 */

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		ActivityHolder.getInstance().setActiveActivity(this);

        /** init challenge bridge **/
        ChallengeBridge.init(this, new Intent(this, quiz.QuizGUI.class));
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
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		node.Server.getInstance().logout();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void testInvitation(View view) {
		Intent intent = new Intent(this, InvitationActivity.class);
		startActivity(intent);
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
        /***
         * CREATE NEW CHALLENGE
         * **/
        LinkedList<User> users = new LinkedList<User>();
        users.add(new User(14, "0", "0", 0, Server.getInstance().getActiveUser().getClan()));
        users.add(new User(28, "0", "0", 0, Server.getInstance().getActiveUser().getClan()));


        Server.getInstance().makeChallege(
                /** success [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {
                        try {
                            Thread.sleep(10000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                        /***
                         * START THE CHALLENGE
                         * **/
                        Server.getInstance().startChallenge(
                                /** success [no input] **/
                                new Callback() {
                                    @Override
                                    public void callback(Object... input) {

                                    }
                                },
                                /** fail. you are not the challenge creator [no input] **/
                                new Callback() {
                                    @Override
                                    public void callback(Object... input) {

                                    }
                                });
                    }
                },
                /** fail. challenge for clan already exists [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {

                    }
                }, users, 0, 0);
	}
	
	public void goToQuiz(View view) {
//		Intent intent = new Intent(this, quiz.QuizGUI.class);	    
//	    startActivity(intent);
		Intent intent = new Intent(this, LobbyActivity.class);
		startActivity(intent);
	}

	public void goToAr(View view)
	
	{
		Intent i = new Intent(Intent.ACTION_MAIN);
		PackageManager manager = getPackageManager();
		i = manager.getLaunchIntentForPackage("com.eimer");
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}
}
