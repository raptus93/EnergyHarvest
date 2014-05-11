package gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.energyharvest.R;
import node.Callback;
import node.NotificationCenter;

/**
 * @version 1.1.4 (29/04/2014)
 * @author Kjell Bunjes
 *
 *
 * Editiert: Sergej Schefer (30/04/2014)
 */

public class MainActivity extends Activity {
	
	private ProgressDialog progressDialog;
	private String email, password;

    /** test login **/
    private Toast toastSuccess, toastEmail, toastPassword;
    private Intent menuIntent;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        /** init stuff **/
        toastSuccess = Toast.makeText(this, "Anmeldung erfolgreich!", Toast.LENGTH_SHORT);
        toastEmail = Toast.makeText(MainActivity.this, "E-Mail inkorrekt!", Toast.LENGTH_SHORT);
        toastPassword = Toast.makeText(MainActivity.this, "Passwort inkorrekt!", Toast.LENGTH_SHORT);
        menuIntent = new Intent(this, MenuActivity.class);

        /** init server **/
        node.Server.getInstance();

        /** init notification center **/
        NotificationCenter.init(this);

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

        final String emailString = email;
        final String passwordString = password;

		if(email.length() == 0 || password.length() == 0) {
			Toast.makeText(MainActivity.this, "Angaben unvollst�ndig!", Toast.LENGTH_SHORT).show();
		}
		else {
			// Checking for internet connection
			boolean isConnected = false;
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
			
			if(isConnected) {
				progressDialog = ProgressDialog.show(this, "Anmeldung", "Bitte warten...");
                /****
                 * LOGIN
                 * ****/
                node.Server.getInstance().login(emailString, passwordString,
                        /** success [no input] **/
                        new Callback() {
                            @Override
                            public void callback(Object... input) {
                                Log.e("SOCKETIO", "Anmeldung erfolgreich!");
                                MainActivity.this.progressDialog.dismiss();
                                toastSuccess.show();
                                startActivity(menuIntent);
                            }
                        },

                        /** wrong password [no input] **/
                        new Callback() {
                            @Override
                            public void callback(Object... input) {
                                MainActivity.this.progressDialog.dismiss();
                                toastPassword.show();
                            }
                        },

                        /** invalid email [no input] **/
                        new Callback() {
                            @Override
                            public void callback(Object... input) {
                                MainActivity.this.progressDialog.dismiss();
                                toastEmail.show();
                            }
                        }
                );
			} else {
				Toast.makeText(this, "Keine Internetverbindung!", Toast.LENGTH_LONG).show();
			}			
		}		
	}

	
	public void goToRegistry(View view) {
		Intent intent = new Intent(this, RegistryActivity.class);	    
	    startActivity(intent);
	}
}
