package gui;

import java.util.ArrayList;

import node.Callback;
import node.Server;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (19/05/2014)
 * @author Robert Verginien Nickel
 *
 */

public class LobbyActivity extends Activity implements OnClickListener{
	
	private Button start;
	private ListView listViewPlayers;
	private ArrayList<String> arrayPlayers = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby);
		
		listViewPlayers = (ListView) findViewById(R.id.listParticipants);
        start = (Button) findViewById(R.id.lobbyStart);
        start.setOnClickListener(this);
       
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayPlayers);
        
        //
        arrayPlayers.add("first");
        arrayPlayers.add("second");
        arrayPlayers.add("third");
        arrayPlayers.add("fourth");
        arrayPlayers.add("fifth");
        //
        
        listViewPlayers.setAdapter(adapter);
	}

	/**
	 * This method sets up the lobby-spectator-view for non-Admins
	 * The Start Button doesn't work and the Text is changed to "Waiting for Admin".
	 * @param isAdmin doesn't change if true, changes Button if false
	 */
	public void setButtonText(boolean isAdmin){
		if(!isAdmin){
			start.setActivated(false);
			start.setText(getResources().getColor(R.string.lobbyStartSpec));
		}
	}
	
	public void addPlayer(String name){
		arrayPlayers.add(name);
	}
	
	public void removePlayer(String name){
		arrayPlayers.remove("name");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lobby, menu);
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
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void onClick(View v) {
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
                }
        );
	}
}
