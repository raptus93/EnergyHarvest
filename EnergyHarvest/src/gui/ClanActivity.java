package gui;

import node.Callback;
import node.Clan;
import node.Server;
import node.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.energyharvest.R;

/**
 * @version 1.1.4 (06/05/2014)
 * @author Kjell Bunjes
 *
 */

@SuppressLint("ShowToast") public class ClanActivity extends Activity {
	
	private boolean isClanMember;
	private String clanName;
	private int clanLogo, captures, points;
	private Button buttonTop, buttonBottom;
	private EditText input;

    private Toast leaveClanToast, successToast, userDoesNotExistToast, userAlreadyInClanToast, invalidInputToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_clan);
		
		ActivityHolder.getInstance().setActiveActivity(this);
		
		ImageView logo = (ImageView) findViewById(R.id.clan_logo_clan);
		TextView tvClan = (TextView) findViewById(R.id.clan_text_view_clan);
		TextView tvCaptures = (TextView) findViewById(R.id.clan_text_view_captures);
		TextView tvPoints = (TextView) findViewById(R.id.clan_text_view_points);
		buttonTop = (Button) findViewById(R.id.clan_button_join);
		buttonBottom = (Button) findViewById(R.id.clan_button_create);

        leaveClanToast = Toast.makeText(this, "Clan verlassen!", Toast.LENGTH_SHORT);
        successToast = Toast.makeText(this, "Mitglied hinzugefügt!", Toast.LENGTH_SHORT);
        userDoesNotExistToast = Toast.makeText(this, "Mitglied existiert nicht!", Toast.LENGTH_SHORT);
        userAlreadyInClanToast = Toast.makeText(this, "Mitglied bereits in Clan!", Toast.LENGTH_SHORT);
        invalidInputToast = Toast.makeText(this, "Bitte ID eingeben!", Toast.LENGTH_SHORT);
		
		// GETTING USER INFORMATION
        User user = node.Server.getInstance().getActiveUser();
        Clan clan = user.getClan();
		isClanMember = !clan.getName().equals("No Clan") && !clan.getName().equals("GUESTCLAN");		
		clanName = clan.getName();
		
		// PLACEHOLDER INFORMATION
		clanLogo = R.drawable.clan_logo;
		captures = 2;
		points = 21533;
		
		// FALLS NOCH NICHT IM CLAN, DEFAULT AUFRUFEN, ANSONSTEN INFORMATIONEN LADEN
		if(isClanMember) {
			logo.setImageResource(clanLogo);
			tvClan.setText("Clan: " + clanName);
			tvCaptures.setText("Eroberungen: " + captures);
			tvPoints.setText("Punkte: " + points);
			buttonTop.setText("Hinzufügen");
			buttonBottom.setText("Austreten");
			
			buttonTop.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					btnAddClicked(v);
				}
			});
			
			buttonBottom.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					btnResignClicked(v);
				}
			});
		}
	}
	
	public void btnJoinClicked(View view) {
		/**
		 * Creating popup window
		 */
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.popup_join, null);
		final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Button btnConfirm = (Button)popupView.findViewById(R.id.clan_popup_button_confirm);
		btnConfirm.setOnClickListener(new Button.OnClickListener(){
		     @Override
		     public void onClick(View v) {
		      popupWindow.dismiss();
		      buttonTop.setEnabled(true);
			  buttonBottom.setEnabled(true);
		}});
		
		popupWindow.showAtLocation(buttonTop, Gravity.CENTER, 0, 0);
		buttonTop.setEnabled(false);
		buttonBottom.setEnabled(false);
		
		TextView tvID = (TextView) popupView.findViewById(R.id.clan_popup_text_view_id);
		tvID.setText(tvID.getText().toString() + node.Server.getInstance().getActiveUser().getId());
		
	}
	
	public void btnCreateClicked(View view) {
		Intent intent = new Intent(this, CreationActivity.class);
		startActivity(intent);
	}

	private void btnAddClicked(View view) {
		/**
		 * Creating popup window
		 */
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View popupView = layoutInflater.inflate(R.layout.popup_add, null);
		final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		// Background is set to make popupwindow react to touch/key events
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
		popupWindow.setOutsideTouchable(true);
		popupWindow.setOnDismissListener(new OnDismissListener() {			
			@Override
			public void onDismiss() {				
				buttonTop.setEnabled(true);
		  		buttonBottom.setEnabled(true);
			}
		});
		
		Button btnConfirm = (Button) popupView.findViewById(R.id.clan_popup_button_confirm);
		input = (EditText) popupView.findViewById(R.id.clan_popup_edit_text_id);		
		
		btnConfirm.setOnClickListener(new Button.OnClickListener(){
		     @Override
		     public void onClick(View v) {		    		
			  if(input.getText().toString().length() > 0) {
				  int id = Integer.parseInt(input.getText().toString());
				  /****
	               * INVITE MEMBER
	               * ****/
				  node.Server.getInstance().inviteMember(id,
						  /** success [no input] **/
						  new Callback() {
					
					  	@Override
					  	public void callback(Object... input) {
					  		successToast.show();
					} },
						/** user does not exist [no input] **/
						new Callback() {
					
					@Override
					public void callback(Object... input) {
						userDoesNotExistToast.show();
					} },
						/** user already in clan [no input] **/
						new Callback() {
					
					@Override
					public void callback(Object... input) {
						userAlreadyInClanToast.show();
					} },
						/** active user not in clan [no input] **/
						new Callback() {
					
					@Override
					public void callback(Object... input) {
						// SHOULD NEVER HAPPEN
					}
				});
			  }
		    	 else {
		    		invalidInputToast.show();
		    	 }
			  
		}});
		
		popupWindow.showAtLocation(buttonTop, Gravity.CENTER, 0, 0);
		
		buttonTop.setEnabled(false);
		buttonBottom.setEnabled(false);
	}

	private void btnResignClicked(View view) {
        Server.getInstance().leaveClan(new Callback() {
            @Override
            public void callback(Object... input) {
                Server.getInstance().getActiveUser().setClan(Server.getInstance().GUEST_CLAN);
                leaveClanToast.show();
                finish();
                startActivity(getIntent());
            }
        });
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
