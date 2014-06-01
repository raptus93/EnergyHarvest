package gui;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
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
	private int points;
	private int numberConqueror;
	private String time;
	
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
        changeTab(tab_1);
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
    		tab_1.setImageResource(R.drawable.icon_wind);
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
		TextView tabContent = (TextView)findViewById(R.id.tab_content);
		if(view.getId() == R.id.tab_1){
    		if(activeBuilding == 1){
    			numberConqueror = 3;
    		} else if(activeBuilding == 2){
    			numberConqueror = 2;  		
    		} else if(activeBuilding == 3){
    			numberConqueror = 1;   		
    		} else if(activeBuilding == 4){
    			numberConqueror = 3;  		
    		}
    		tabContent.setText("Anzahl Eroberer: " + numberConqueror);
    	} else if(view.getId() == R.id.tab_2){
    		if(activeBuilding == 1){
    			category = "Biologie";
    			conqueror = "Developer";
    			points = 2000;
    			time = "00.23.10";

    		} else if(activeBuilding == 2){
    			category = "Kunst";
    			conqueror = "Developer2";
    			points = 3000;    	
    			time = "00.25.30";
    		} else if(activeBuilding == 3){
    			category = "Film und Fernsehen";
    			conqueror = "Developer3";
    			points = 4000;
    			time = "00.28.20";
    		} else if(activeBuilding == 4){
    			category = "Geschichte";
    			conqueror = "Developer4";
    			points = 5000;
    			time = "01.12.30";
    		}
    		tabContent.setText(category + "\n\nEroberer: " + conqueror + "\nPunkte: " + points + "\nZeit: " + time);
    		} else if(view.getId() == R.id.tab_3){
    		if(activeBuilding == 1){
    			category = "Physik";
    			conqueror = "Developer5";
    			points = 6000;
    			time = "01.42.20";
    		} else if(activeBuilding == 2){
    			category = "Musik";
    			conqueror = "Developer6";
    			points = 7000;
    			time = "01.52.30";
    		} else if(activeBuilding == 3){
    			category = "Sport";
    			conqueror = "Developer7";
    			points = 8000;
    			time = "01.12.27";
    		} else if(activeBuilding == 4){
    			category = "Geographie";
    			conqueror = "Developer8";
    			points = 9000;
    			time = "01.05.10";
    		}
    		tabContent.setText(category + "\n\nEroberer: " + conqueror + "\nPunkte: " + points + "\nZeit: " + time);
    	} else if(view.getId() == R.id.tab_4){
    		if(activeBuilding == 1){
    			category = "Chemie";
    			conqueror = "Developer9";
    			points = 10000;
    			time = "01.52.45";
    		} else if(activeBuilding == 2){
    			category = "Literatur";
    			conqueror = "Developer10";
    			points = 11000;
    			time = "01.35.00";
    		} else if(activeBuilding == 3){
    			category = "Essen und Trinken";
    			conqueror = "Developer11";
    			points = 12000;
    			time = "01.55.02";
    		} else if(activeBuilding == 4){
    			category = "Politik";
    			conqueror = "Developer12";
    			points = 13000;
    			time = "01.46.32";
    		}
    		tabContent.setText(category + "\n\nEroberer: " + conqueror + "\nPunkte: " + points + "\nZeit: " + time);
    	}
	}
	
	public void changeTab(View view){
		activateTab(view);
		changeTabContent(view);
	}
}
