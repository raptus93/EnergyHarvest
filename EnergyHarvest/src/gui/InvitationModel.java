package gui;

import node.User;

/**
 * Credits: http://www.vogella.com/tutorials/AndroidListView/article.html
 * @version 1.1.4 (07/05/2014)
 * @author Kjell Bunjes
 *
 */

public class InvitationModel {

	  private String name;
	  private boolean selected;
	  private User user;

	  public InvitationModel(User user) {
	    this.user = user;
	    name = user.getName();
	    selected = false;
	  }

	  public String getName() {
	    return name;
	  }

	  public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }
	  
	  public User getUser() {
		  return user;
	  }

	} 
