package gui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (29/04/2014)
 * @author Kjell Bunjes
 *
 */

public class ClanActivity extends Activity {
	
	private boolean isClanMember;
	private String clanName;
	private int clanLogo, captures, points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_clan);
		
		ImageView logo = (ImageView) findViewById(R.id.clan_logo_clan);
		TextView tvClan = (TextView) findViewById(R.id.clan_text_view_clan);
		TextView tvCaptures = (TextView) findViewById(R.id.clan_text_view_captures);
		TextView tvPoints = (TextView) findViewById(R.id.clan_text_view_points);
		Button button1 = (Button) findViewById(R.id.clan_button_join);
		Button button2 = (Button) findViewById(R.id.clan_button_create);
		
		// CREATING PLACEHOLDER CLAN INFORMATION
		isClanMember = true;
		clanLogo = R.drawable.clan_logo;
		clanName = "Infinity";
		captures = 2;
		points = 21533;
		
		// FALLS NOCH NICHT IM CLAN, DEFAULT AUFRUFEN, ANSONSTEN INFORMATIONEN LADEN
		if(isClanMember) {
			Toast.makeText(this, "Benutzer ist Clanmitglied!", Toast.LENGTH_SHORT).show();
			logo.setImageResource(clanLogo);
			tvClan.setText("Clan: " + clanName);
			tvCaptures.setText("Eroberungen: " + captures);
			tvPoints.setText("Punkte: " + points);
			button1.setText("Hinzufügen");
			button2.setText("Austreten");
			
			button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnAddClicked(v);
				}
			});
			
			button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnResignClicked(v);
				}
			});
		}
		else {
						
		}
	}
	
	public void btnJoinClicked(View view) {
		Toast.makeText(this, "Join Clicked!", Toast.LENGTH_SHORT).show();
	}
	
	public void btnCreateClicked(View view) {
		Toast.makeText(this, "Create Clicked!", Toast.LENGTH_SHORT).show();
	}

	private void btnAddClicked(View view) {
		Toast.makeText(this, "Add Clicked!", Toast.LENGTH_SHORT).show();
	}

	private void btnResignClicked(View view) {
		Toast.makeText(this, "Resign Clicked!", Toast.LENGTH_SHORT).show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clan, menu);
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

}
