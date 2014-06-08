package gui;

import java.util.ArrayList;
import java.util.HashSet;

import node.Building;
import node.Callback;
import node.Clan;
import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.energyharvest.R;

/**
 * @version 1.3.0 (13/04/2014)
 * @author Katja Lange
 *
 */

public class MapActivity extends Activity {
	
	private String category;
	private String conqueror;
	private String element;
	private int points;
	private int numberConqueror;
	private String time;
	
	private int activeBuilding;
	private int activeTab;
	private ImageButton tab_1;
	private ImageButton tab_2;
	private ImageButton tab_3;
	private ImageButton tab_4;
	
	private Building building;
	private TextView tabContent;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
    	tab_1 = (ImageButton)findViewById(R.id.tab_1); // getting the first tab of the map
    	tab_2 = (ImageButton)findViewById(R.id.tab_2); // getting the second tab of the map
    	tab_3 = (ImageButton)findViewById(R.id.tab_3); // getting the third tab of the map
    	tab_4 = (ImageButton)findViewById(R.id.tab_4); // getting the fourth tab of the map
    	
    	tabContent = (TextView)findViewById(R.id.tab_content);
    	
    	element = "fire";
    	activeBuilding = 1;
        changeTab(tab_1);
        
        updateInformation(activeBuilding);
        //tabContent.setText("Anzahl Eroberer: " + numberConqueror);
    }
    
    private void updateInformation(int buildingClicked) {
    	
    	
    	node.Server.getInstance().getBuildingByID(buildingClicked,
    			/** success [no input] **/	
    	    	new Callback() {
			
    					@Override
    					public void callback(Object... input) {
    						// TODO Auto-generated method stub
    						building = (Building) input[0];
    				        
    				        runOnUiThread(new Runnable() {
    		                      @Override
    		                      public void run() {
    		                    	  
    		    						Log.i("info", "entered!");
    		    						ArrayList<Clan> conquerors = new ArrayList<Clan>();
    		    				    	conquerors.add(building.getCat0_clan());
    		    				    	conquerors.add(building.getCat1_clan());
    		    				    	conquerors.add(building.getCat2_clan());
    		    				    	
    		    				    	// Deleting doubled entries
    		    				    	HashSet<Clan> hashSet = new HashSet<Clan>(conquerors);
    		    				        conquerors.clear();
    		    				        conquerors.addAll(hashSet);
    		    				       
    		    				        numberConqueror = conquerors.size();
    		    				       
    		    				        tabContent.setText("Gebäude: " + element + "\n\nAnzahl Eroberer: " + numberConqueror);
    		    				        
    		    				        for(int i = 0; i < numberConqueror; i++) {
    		    				        	Log.i("test", conquerors.get(i).getName());
    		    				        }
    		                      }
    		                  });
    					}
    				},
    	/** fail [no input] **/
    	new Callback() {
			
			@Override
			public void callback(Object... input) {
				// TODO Auto-generated method stub
				Log.e("error", "No Building returned!");
			}
		});
    }

    public void changeBuilding(View view){
    	/*View mapLayout = findViewById(R.id.map_content); // getting the layout of the map
    	
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
    	
    	changeTab(tab_1);
    	changeTabIcons();*/
    	
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
    	
    	updateInformation(activeBuilding);
    	changeTab(tab_1);
    	changeTabIcons();
    }
   
    private void changeTabIcons(){

    	if (activeBuilding == 1){
    		tab_1.setImageResource(R.drawable.icon_fire); // setting the icon on tab_1
    		tab_2.setImageResource(R.drawable.icon_biology);	// setting the icon on tab_2
    		tab_3.setImageResource(R.drawable.icon_physics); // setting the icon on tab_3
    		tab_4.setImageResource(R.drawable.icon_chemistry); // setting the icon on tab_4
    	} else if(activeBuilding == 2){
    		tab_1.setImageResource(R.drawable.icon_water);
    		tab_2.setImageResource(R.drawable.icon_art);
    		tab_3.setImageResource(R.drawable.icon_music);
    		tab_4.setImageResource(R.drawable.icon_literature);
    	} else if(activeBuilding == 3){
    		tab_1.setImageResource(R.drawable.icon_earth);
    		tab_2.setImageResource(R.drawable.icon_movie);
    		tab_3.setImageResource(R.drawable.icon_sports);
    		tab_4.setImageResource(R.drawable.icon_food);    		
    	} else if(activeBuilding == 4){
    		tab_1.setImageResource(R.drawable.icon_air);
    		tab_2.setImageResource(R.drawable.icon_history);
    		tab_3.setImageResource(R.drawable.icon_geography);
    		tab_4.setImageResource(R.drawable.icon_politics);
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
    	
    	if(view.getId() == R.id.tab_1){
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
		if(view.getId() != R.id.tab_1){
			if(view.getId() == R.id.tab_2){    		
    			category = building.getCat0();
    			conqueror = building.getCat0_clan().getName();
    			points = 2000;
    			time = "" + building.getCat0_unlockTime();    		
			}
			else if(view.getId() == R.id.tab_3){    		
				category = building.getCat1();
				conqueror = building.getCat1_clan().getName();
				points = 3000;
				time = "" + building.getCat1_unlockTime();
			}
			else if(view.getId() == R.id.tab_4){    		
				category = building.getCat2();
				conqueror = building.getCat2_clan().getName();
				points = 3000;
				time = "" + building.getCat2_unlockTime();
			}
			tabContent.setText(category + "\n\nEroberer: " + conqueror + "\nPunkte: " + points + "\nZeit: " + time);
    	}
		else {
			if(activeBuilding == 1){
				element = "Feuer";
			} else if(activeBuilding == 2){
				element = "Wasser";
			} else if(activeBuilding == 3){
				element = "Erde";
			} else{
				element = "Luft";
			}
			tabContent.setText("Gebäude: " + element + "\n\nAnzahl Eroberer: " + numberConqueror);
		}
	}
	
	public void changeTab(View view){
		activateTab(view);
		changeTabContent(view);
	}
}
