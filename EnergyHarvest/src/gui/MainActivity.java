package gui;

import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import backend.ErrorCode;
import backend.Server;

import com.example.energyharvest.R;

/**
 * @version 1.1.3 (12/04/2014)
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
	
	public void login(View view) {
		email = ((EditText)findViewById(R.id.loginEmail)).getText().toString();
		password = ((EditText)findViewById(R.id.loginPassword)).getText().toString();
		if(email.length() == 0 || password.length() == 0) {
			Toast.makeText(MainActivity.this, "Angaben unvollständig!", Toast.LENGTH_SHORT).show();
		}
		else {
			progressDialog = ProgressDialog.show(this, "Anmeldung", "Bitte warten...");
			new LoginTask().execute("");
		}		
	}
	
	private class LoginTask extends AsyncTask<String, Void, Object> {
		protected Object doInBackground(String...args) {
			try {
				
				// TODO: ErrorCode Handling
				errorCode = Server.getInstance().login(email, password);
				loginSuccessful = (errorCode == ErrorCode.SUCCESS) ? true : false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		
		protected void onPostExecute(Object result) {
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
