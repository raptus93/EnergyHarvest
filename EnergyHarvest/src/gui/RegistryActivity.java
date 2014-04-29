package gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import backend.ErrorCode;
import backend.Server;

import com.example.energyharvest.R;

/**
 * @version 1.1.3 (13/04/2014)
 * @author Kjell Bunjes
 *
 */

public class RegistryActivity extends Activity {
	
	private ProgressDialog progressDialog;
	private boolean registrySuccessful;
	private ErrorCode errorCode;
	private String username, email, password, passwordRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registry);
		
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registry, menu);
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
	
	public void confirm(View view) {
		username = ((EditText)findViewById(R.id.registry_edit_text_username)).getText().toString();
		email = ((EditText)findViewById(R.id.registry_edit_text_email)).getText().toString();
		password = ((EditText)findViewById(R.id.registry_edit_text_password)).getText().toString();
		passwordRepeat = ((EditText)findViewById(R.id.registry_edit_text_password_repeat)).getText().toString();
		if(username.length() == 0 || email.length() == 0 || password.length() == 0 || passwordRepeat.length() == 0) {
			Toast.makeText(RegistryActivity.this, "Angaben unvollständig!", Toast.LENGTH_SHORT).show();
		}
		else {
			if(!password.equals(passwordRepeat)) {
				Toast.makeText(RegistryActivity.this, "Passwort stimmt nicht überein!", Toast.LENGTH_SHORT).show();
			}
			else {
				progressDialog = ProgressDialog.show(this, "Registrierung", "Bitte warten...");
				new RegistryTask().execute("");
			}
		}
	}
	
	private class RegistryTask extends AsyncTask<String, Void, Object> {
		protected Object doInBackground(String...args) {
			try {				
				errorCode = Server.getInstance().register(username, email, password);
				registrySuccessful = (errorCode == ErrorCode.SUCCESS) ? true : false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		
		protected void onPostExecute(Object result) {
			if(RegistryActivity.this.progressDialog != null) {
				RegistryActivity.this.progressDialog.dismiss();
				if(registrySuccessful) {
					Toast.makeText(RegistryActivity.this, "Registrierung erfolgreich!", Toast.LENGTH_LONG).show();
					NavUtils.navigateUpFromSameTask(RegistryActivity.this);
				}
				else {
					switch(errorCode) {
					case USERNAME_TAKEN: Toast.makeText(RegistryActivity.this, "Benutzername bereits vergeben!", Toast.LENGTH_SHORT).show();
					break;
					case EMAIL_TAKEN: Toast.makeText(RegistryActivity.this, "E-mail bereits vergeben!", Toast.LENGTH_SHORT).show();
					break;
					case EMAIL_AND_USER_NAME_TAKEN: Toast.makeText(RegistryActivity.this, "E-mail und Benutzername bereits vergeben!", Toast.LENGTH_SHORT).show();
					break;
					default: Toast.makeText(RegistryActivity.this, "Registrierung fehlgeschlagen!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}

}
