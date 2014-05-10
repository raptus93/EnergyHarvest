package gui;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import node.Callback;
import node.Clan;
import node.Server;
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

    public static InvitationActivity activity;

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    View footer = getLayoutInflater().inflate(R.layout.invitation_footer, null);
	    ListView listView = getListView();
	    listView.addFooterView(footer);

          /***
           * GET ALL ONLINE MEMBERS FROM YOUR CLAN
           * **/
          Server.getInstance().getOnlineMembersFromClan(new Callback() {

              /** success [input[0] = LinkedList<User>] **/
              @Override
              public void callback(Object... input) {
                  LinkedList<User> users = (LinkedList<User>) input[0];
                  List<InvitationModel> list = new ArrayList<InvitationModel>();
                  
                  for(int i = 0; i < users.size(); i++) {
                	 list.add(new InvitationModel(users.get(i)));
                  }

                  ArrayAdapter<InvitationModel> adapter = new InteractiveArrayAdapter(InvitationActivity.this, list);
                  setListAdapter(adapter);
              }
          });
	  }
	  
	  public void invite(View view) {
		  LinkedList<User> list = new LinkedList<User>();
		  for(int i = 0; i < getListView().getLastVisiblePosition(); i++) {
			  InvitationModel element = (InvitationModel) getListView().getAdapter().getItem(i);
			  Log.i("InviteCheck", "Username: " + element.getName() + " selected: " + element.isSelected());
			  //list.add(element.getUser());
			  if(element.isSelected()) {
				  list.add(element.getUser());
				  Log.i("debugging", "" + element.getUser().toString());
			  }
		  }
		  Server.getInstance().makeChallege(
	                /** success [no input] **/
	                new Callback() {
	                    @Override
	                    public void callback(Object... input) {
	                        try {
	                            Thread.sleep(10000);

	                        } catch (InterruptedException e) {
	                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	                        }

	                        /***
	                         * START THE CHALLENGE
	                         * **/
	                        Server.getInstance().startChallenge(
	                                /** success [no input] **/
	                                new Callback() {
	                                    @Override
	                                    public void callback(Object... input) {

	                                    }
	                                },
	                                /** fail. you are not the challenge creator [no input] **/
	                                new Callback() {
	                                    @Override
	                                    public void callback(Object... input) {

	                                    }
	                                });
	                    }
	                },
	                /** fail. challenge for clan already exists [no input] **/
	                new Callback() {
	                    @Override
	                    public void callback(Object... input) {

	                    }
	                }, list);
	  }
}
