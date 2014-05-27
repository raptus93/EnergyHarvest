package gui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.energyharvest.R;

public class StatisticsActivity extends Activity{
	
	private ListView listStatistics;
	private ArrayList<String> statistics = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_clan);
		
		listStatistics = (ListView) findViewById(R.id.listStatistics);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statistics);
		listStatistics.setAdapter(adapter);
	}
	
	public void setResults(ArrayList<Boolean> results){
		for(int i = 0; i<results.size(); i++){
			if(results.get(i)){
				statistics.add("Question "+(i+1)+" is correct!");
				//List items noch einfärben, hier grün
			}
			else{
				statistics.add("Question "+(i+1)+" was wrong!");
				//ListItem rot einfärben
			}
		}
	}
}
