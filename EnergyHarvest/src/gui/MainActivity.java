package gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import backend.ErrorCode;
import backend.Server;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (29/04/2014)
 * @author Kjell Bunjes
 *
 */

public class MainActivity extends Activity {
	
	private ProgressDialog progressDialog;
	private boolean loginSuccessful;
	private String email, password;
	private ErrorCode errorCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void login(View view) {
		email = ((EditText)findViewById(R.id.login_edit_text_email)).getText().toString();
		password = ((EditText)findViewById(R.id.login_edit_text_password)).getText().toString();
		Log.i("debugging email", email);
		Log.i("debugging password", password);
		if(email.length() == 0 || password.length() == 0) {
			Toast.makeText(MainActivity.this, "Angaben unvollständig!", Toast.LENGTH_SHORT).show();
		}
		else {
			// Checking for internet connection
			boolean isConnected = false;
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
			
			if(isConnected) {
				progressDialog = ProgressDialog.show(this, "Anmeldung", "Bitte warten...");
				new LoginTask().execute("");
			}
			else {
				Toast.makeText(this, "Keine Internetverbindung!", Toast.LENGTH_LONG).show();
			}			
		}		
	}
	
	private class LoginTask extends AsyncTask<String, Void, Boolean> {		
		protected Boolean doInBackground(String...args) {			
			try {
				errorCode = Server.getInstance().login(email, password);				
				loginSuccessful = (errorCode == ErrorCode.SUCCESS) ? true : false;				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		
		protected void onPostExecute(Boolean result) {
			if(MainActivity.this.progressDialog != null) {
				MainActivity.this.progressDialog.dismiss();
				if(loginSuccessful) {
					Toast.makeText(MainActivity.this, "Anmeldung erfolgreich!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MainActivity.this, MenuActivity.class);					
					startActivity(intent);
				}
				else {
					switch(errorCode) {
					case INVALID_EMAIL: Toast.makeText(MainActivity.this, "E-Mail inkorrekt!", Toast.LENGTH_SHORT).show();
					break;
					case WRONG_PASSWORD: Toast.makeText(MainActivity.this, "Passwort inkorrekt!", Toast.LENGTH_SHORT).show();
					break;
					default: Toast.makeText(MainActivity.this, "Anmeldung fehlgeschlagen!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}
	
	public void goToRegistry(View view) {
		Intent intent = new Intent(this, RegistryActivity.class);	    
	    startActivity(intent);
	}
}
