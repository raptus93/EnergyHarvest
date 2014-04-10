package gui;

import android.util.Log;
import backend.AndroidServerInterface;
import backend.ErrorCode;
import backend.Question;
import backend.QuestionCatalog;
import com.example.energyharvest.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class RegistryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registry);
        Log.i("error","onCreate");
        ErrorCode ec = AndroidServerInterface.login("sergej@admin.de", "123456");
        Log.i("error", ec.toString());

        QuestionCatalog q= AndroidServerInterface.getRandomQuestions(10);

        for(Question que: q.getQuestionList()){
            Log.i("error", que.text);
        }
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
	
	public void goToMenu(View view) {
		Intent intent = new Intent(this, MenuActivity.class);	    
	    startActivity(intent);
	}

}
