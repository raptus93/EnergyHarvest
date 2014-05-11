package gui;

import node.Callback;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (05/05/2014)
 * @author Kjell Bunjes
 *
 */


public class CreationActivity extends Activity {
	
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	private EditText etName;
	private Toast toastSuccess, toastNameTaken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creation);
		
		ActivityHolder.getInstance().setActiveActivity(this);
		
		// Liste mit vorgegebenen Clanlogos erstellen
		GridView gridview = (GridView) findViewById(R.id.creation_gridview);		
	    final GridAdapter gridadapter = new GridAdapter(this);
	    gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ImageView logo = (ImageView) findViewById(R.id.creation_logo);
				ImageView logoClicked = (ImageView) view;
				logo.setImageDrawable(logoClicked.getDrawable());
			}
			
		});
	    gridview.setAdapter(gridadapter);
	    
	    toastSuccess = Toast.makeText(this, "Clan erstellt!", Toast.LENGTH_SHORT);
	    toastNameTaken = Toast.makeText(this, "Name bereits vergeben!", Toast.LENGTH_SHORT);
	    
	    // Feld für den Clannamen
	    etName = (EditText) findViewById(R.id.creation_edit_text_name);
	    etName.setEnabled(false);
	    etName.setTextColor(Color.WHITE);
	    etName.setText(node.Server.getInstance().getActiveUser().getClan().getName());
	    etName.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				v.setBackgroundResource(android.R.color.transparent);
				v.setEnabled(false);
				return false;
			}

		});
	    
	    // Forcing the softkeyboard not to show up
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	public void createClan(View view) {
		Log.i("debugging", etName.getText().toString());
		/****
         * CREATING NEW CLAN
         * ****/
		node.Server.getInstance().createClan(etName.getText().toString(),
				/** success [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {
                    	//CreationActivity.this.progressDialog.dismiss();
                    	toastSuccess.show();
                    	finish();
                        NavUtils.navigateUpFromSameTask(CreationActivity.this);
                    }
                },
                /** clanname taken [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {
                    	//CreationActivity.this.progressDialog.dismiss();
                        toastNameTaken.show();
                    }
		});		
	}
	
	public void editClanName(View view) {
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
		getMenuInflater().inflate(R.menu.creation, menu);
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
