package gui;

import node.Building;
import node.Callback;
import node.Clan;
import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.energyharvest.R;

/**
 * @version 1.2.0 (13/04/2014)
 * @author Katja Lange
 *
 */

//
//
//
//	ZEILE IN CHANGETABCONTENT AUSKOMMENTIERT!!! (26/05/2014)
//
//
//
//

public class MapActivity extends Activity {
	
	private Building building;
	
	private String category;
	private Clan[] conquerors;
	private int[] unlockTimes;
	private String points;
	
	private int activeBuilding;
	private int activeTab;
	private ImageButton tab_1;
	private ImageButton tab_2;
	private ImageButton tab_3;
	private ImageButton tab_4;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
    	tab_1 = (ImageButton)findViewById(R.id.tab_1); // getting the first tab of the map
    	tab_2 = (ImageButton)findViewById(R.id.tab_2); // getting the second tab of the map
    	tab_3 = (ImageButton)findViewById(R.id.tab_3); // getting the third tab of the map
    	tab_4 = (ImageButton)findViewById(R.id.tab_4); // getting the fourth tab of the map
    	
    	activeBuilding = 1;
        activateTab(tab_1);
        
        conquerors = new Clan[3];
        unlockTimes = new int[3];
    }
    
    private void updateInfo(int id) {
    	node.Server.getInstance().getBuildingByID(id, new Callback() {
			/** success [no input] **/
			@Override
			public void callback(Object... input) {
				// TODO Auto-generated method stub
				building = (Building) input[0];
				conquerors[0] = building.getCat0_clan();
				conquerors[1] = building.getCat1_clan();
				conquerors[2] = building.getCat2_clan();
				unlockTimes[0] = building.getCat0_unlockTime();
				unlockTimes[1] = building.getCat1_unlockTime();
				unlockTimes[2] = building.getCat2_unlockTime();
			}
		}, new Callback() {
			/** fail [no input] **/
			@Override
			public void callback(Object... input) {
				// TODO Auto-generated method stub
				Log.i("testing", "getting building failed");
			}
		});
    }

    public void changeBuilding(View view){
    	View mapLayout = findViewById(R.id.map_content); // getting the layout of the map
    	
    	if (view.getId() == R.id.button_l1){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l1); // setting the background
    		activeBuilding = 1;
    	} else if(view.getId() == R.id.button_l2){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l2);
    		activeBuilding = 2;
    	} else if(view.getId() == R.id.button_l3){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l3);
    		activeBuilding = 3;
    	} else if(view.getId() == R.id.button_l4){
    		mapLayout.setBackgroundResource(R.drawable.map_background_l4);
    		activeBuilding = 4;
    	}
    	
    	activateTab(tab_1);
    	changeTabIcons();
    }
   
    private void changeTabIcons(){

    	if (activeBuilding == 1){
    		tab_1.setImageResource(R.drawable.ic_element_1); // setting the icon on tab_1
    		tab_2.setImageResource(R.drawable.ico1);	// setting the icon on tab_2
    		tab_3.setImageResource(R.drawable.ico2); // setting the icon on tab_3
    		tab_4.setImageResource(R.drawable.ic_cat_l1_1); // setting the icon on tab_4
    	} else if(activeBuilding == 2){
    		tab_1.setImageResource(R.drawable.ic_element_2);
    		tab_2.setImageResource(R.drawable.ic_cat_l2_1);
    		tab_3.setImageResource(R.drawable.ic_cat_l2_1);
    		tab_4.setImageResource(R.drawable.ic_cat_l2_1);
    	} else if(activeBuilding == 3){
    		tab_1.setImageResource(R.drawable.ic_element_3);
    		tab_2.setImageResource(R.drawable.ic_cat_l3_1);
    		tab_3.setImageResource(R.drawable.ic_cat_l3_1);
    		tab_4.setImageResource(R.drawable.ic_cat_l3_1);    		
    	} else if(activeBuilding == 4){
    		tab_1.setImageResource(R.drawable.ic_element_4);
    		tab_2.setImageResource(R.drawable.ic_cat_l4_1);
    		tab_3.setImageResource(R.drawable.ic_cat_l4_1);
    		tab_4.setImageResource(R.drawable.ic_cat_l4_1);
    	}
    }
   
	private void activateTab(View view){
    	if(activeTab == 1){
    		tab_1.setBackgroundColor(color.transparent);
    	} else if(activeTab == 2){
    		tab_2.setBackgroundColor(color.transparent);
    	} else if(activeTab == 3){
    		tab_3.setBackgroundColor(color.transparent);
    	} else if(activeTab == 4){
    		tab_4.setBackgroundColor(color.transparent);
    	}
    	
    	if(view.getId() == R.id.tab_1 || view == null){
    		tab_1.setBackgroundResource(R.drawable.active_tab);
    		activeTab = 1;
    	} else if(view.getId() == R.id.tab_2){
    		tab_2.setBackgroundResource(R.drawable.active_tab);
 		   	activeTab = 2;
    	} else if(view.getId() == R.id.tab_3){
    		tab_3.setBackgroundResource(R.drawable.active_tab);
 		   	activeTab = 3;
    	} else if(view.getId() == R.id.tab_4){
    		tab_4.setBackgroundResource(R.drawable.active_tab);
 		   	activeTab = 4;
    	}
	}
	
	private void changeTabContent(View view){
		TextView tabContent = (TextView)findViewById(R.id.tab_content);
		if(view.getId() == R.id.tab_1){
    		if(activeBuilding == 1){
    			//tabContent.setText("Kategorie: " + category + "\nEroberer: " + conqueror + "\nPunkte: " + points);
    		}
    	} 
	}
	
	public void changeTab(View view){
		activateTab(view);
		changeTabContent(view);
	}
}
