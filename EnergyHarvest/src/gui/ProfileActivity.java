package gui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (29/04/2014)
 * @author Kjell Bunjes
 *
 */

public class ProfileActivity extends Activity {
	
	private ImageView clanLogo;
	private TextView tvName, tvClan, tvEmail, tvID, tvPoints;
	private EditText etName;
	
	private String name, clan, email;
	private int logo, id, points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		// FAKEPROFIL ERSTELLEN
		logo = R.drawable.clan_logo;
		name = "iQew";
		clan = "Infinity";
		email = "Kjell.Bunjes@stud.hshl.de";
		id = 28253;
		points = 3847;
		
		//
		
		clanLogo = (ImageView) findViewById(R.id.profile_logo_clan);
		tvName = (TextView) findViewById(R.id.profile_text_view_name);
		etName = (EditText) findViewById(R.id.profile_edit_text_name);
		tvClan = (TextView) findViewById(R.id.profile_text_view_clan);
		tvEmail = (TextView) findViewById(R.id.profile_text_view_email);
		tvID = (TextView) findViewById(R.id.profile_text_view_id);
		tvPoints = (TextView) findViewById(R.id.profile_text_view_points);
		
		clanLogo.setImageResource(logo);
		etName.setEnabled(false);
		etName.setTextColor(Color.WHITE);
		etName.setText(name);
		tvClan.setText("Clan: " + clan);
		tvEmail.setText("E-mail: " + email);
		tvID.setText("ID: " + id);
		tvPoints.setText("Points: " + points);
		
		etName.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(android.R.color.transparent);
				v.setEnabled(false);
				return false;
			}

		});
	}
	
	public void editName(View view) {
		etName.setEnabled(true);
		etName.requestFocus();
		etName.setBackgroundColor(Color.GRAY);
		
		// Forcing the softkeyboard to show up on focus
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT);
		// Set cursor at the end of the content of the edit text
		etName.setSelection(("" + etName.getText()).length());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
