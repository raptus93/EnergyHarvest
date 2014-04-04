package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import backend.Question;
import backend.QuestionCatalog;
import backend.Server;

import com.example.energyharvest.R;

public class MainActivity extends Activity {

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
	
	public void goToMenu(View view) {
		Intent intent = new Intent(this, MenuActivity.class);	    
	    startActivity(intent);
	}
	
	public void goToRegistry(View view) {
		Intent intent = new Intent(this, RegistryActivity.class);	    
	    startActivity(intent);
	}

}
