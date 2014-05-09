package gui;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import node.Clan;
import node.User;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.energyharvest.R;

/**
 * Credits: http://www.vogella.com/tutorials/AndroidListView/article.html
 * @version 1.1.4 (07/05/2014)
 * @author Kjell Bunjes
 *
 */

public class InvitationActivity extends ListActivity {
	/** Called when the activity is first created. */

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    View footer = getLayoutInflater().inflate(R.layout.invitation_footer, null);
	    ListView listView = getListView();
	    listView.addFooterView(footer);
	    // create an array of Strings, that will be put to our ListActivity
	    ArrayAdapter<InvitationModel> adapter = new InteractiveArrayAdapter(this,
	        getModel());
	    setListAdapter(adapter);
	  }
	  
	  /** Hier Liste der Clanmitglieder erstellen**/
	  private List<InvitationModel> getModel() {
		LinkedList<User> testList = getFakeList();
	    List<InvitationModel> list = new ArrayList<InvitationModel>();
	    
	    for(User element : testList) {
	    	list.add(get(element.getName()));
	    }
	    return list;
	  }
	  
	  private LinkedList<User> getFakeList() {
		  LinkedList<User> list = new LinkedList<User>();
		  int memberCount = 15;
		  for(int i = 0; i < memberCount; i++) {
			  list.add(new User((100+i), "User_" + i, "user_" + i + "@mail.com", 2000, new Clan(100, "TestClan", "TestLogoURL", memberCount)));
		  }
		  
		  return list;
	  }

	  private InvitationModel get(String s) {
	    return new InvitationModel(s);
	  }
	  
	  public void invite(View view) {		  
		  for(int i = 0; i < getListView().getLastVisiblePosition(); i++) {
			  InvitationModel element = (InvitationModel) getListView().getAdapter().getItem(i);
			  Log.i("InviteCheck", "Username: " + element.getName() + " selected: " + element.isSelected());
		  }
	  }
}
