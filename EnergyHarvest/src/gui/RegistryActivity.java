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
import node.Callback;

/**
 * @version 1.1.4 (29/04/2014)
 * @author Kjell Bunjes
 *
 */

public class RegistryActivity extends Activity {
	
	private ProgressDialog progressDialog;
	private Toast toastSuccess, toastNameTaken, toastEmailTaken;
	private String username, email, password, passwordRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registry);
		toastSuccess = Toast.makeText(RegistryActivity.this, "Registrierung erfolgreich!", Toast.LENGTH_LONG);
		toastNameTaken = Toast.makeText(RegistryActivity.this, "Benutzername bereits vergeben!", Toast.LENGTH_SHORT);
		toastEmailTaken = Toast.makeText(RegistryActivity.this, "E-mail bereits vergeben!", Toast.LENGTH_SHORT);
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
                /****
                 * REGISTER NEW USER
                 * ****/
                node.Server.getInstance().register(username, email, password,
                        /** success [no input] **/
                        new Callback() {
                            @Override
                            public void callback(Object... input) {
                            	RegistryActivity.this.progressDialog.dismiss();
                            	toastSuccess.show();
                                NavUtils.navigateUpFromSameTask(RegistryActivity.this);
                            }
                        },
                        /** username taken [no input] **/
                        new Callback() {
                            @Override
                            public void callback(Object... input) {
                            	RegistryActivity.this.progressDialog.dismiss();
                                toastNameTaken.show();
                            }
                        },
                        /** email taken [no input] **/
                        new Callback() {
                            @Override
                            public void callback(Object... input) {
                            	RegistryActivity.this.progressDialog.dismiss();
                                toastEmailTaken.show();
                            }
                        }
                );
			}
		}
	}

}
