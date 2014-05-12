package gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.energyharvest.R;

/**
 * @version 1.1.4 (13/04/2014)
 * @author Katja Lange
 *
 */

public class MapActivity extends Activity {
	
	private int activeBuilding;
	private int activeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        activeBuilding = 1;
        activeButton = 1;
    }

    public void updateMap(View view){
    	
    	View mapLayout = findViewById(R.id.map_content); // getting the layout of the map
    	ImageButton tab_1 = (ImageButton)findViewById(R.id.tab_1); // getting the first tab of the map
    	ImageButton tab_2 = (ImageButton)findViewById(R.id.tab_2); // getting the second tab of the map
    	ImageButton tab_3 = (ImageButton)findViewById(R.id.tab_3); // getting the third tab of the map
    	ImageButton tab_4 = (ImageButton)findViewById(R.id.tab_4); // getting the fourth tab of the map
    	
    	if (view.getId() == R.id.button_l1){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l1); // setting the background
    		tab_1.setImageResource(R.drawable.ic_element_1); // setting the icon on tab_1
    		tab_2.setImageResource(R.drawable.ico1);	// setting the icon on tab_2
    		tab_3.setImageResource(R.drawable.ico2); // setting the icon on tab_3
    		tab_4.setImageResource(R.drawable.ic_cat_l1_1); // setting the icon on tab_4
    	} else if(view.getId() == R.id.button_l2){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l2);
    		tab_1.setImageResource(R.drawable.ic_element_2);
    		tab_2.setImageResource(R.drawable.ic_cat_l2_1);
    		tab_3.setImageResource(R.drawable.ic_cat_l2_1);
    		tab_4.setImageResource(R.drawable.ic_cat_l2_1);
    	} else if(view.getId() == R.id.button_l3){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l3);
    		tab_1.setImageResource(R.drawable.ic_element_3);
    		tab_2.setImageResource(R.drawable.ic_cat_l3_1);
    		tab_3.setImageResource(R.drawable.ic_cat_l3_1);
    		tab_4.setImageResource(R.drawable.ic_cat_l3_1);
    	} else if(view.getId() == R.id.button_l4){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l4);
    		tab_1.setImageResource(R.drawable.ic_element_4);
    		tab_2.setImageResource(R.drawable.ic_cat_l4_1);
    		tab_3.setImageResource(R.drawable.ic_cat_l4_1);
    		tab_4.setImageResource(R.drawable.ic_cat_l4_1);
    	}
    }
   
    
    
    public void UpdateTabContent(){
	   
    }
   
   
}
