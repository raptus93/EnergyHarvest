package gui;

/**
 * Credits: http://www.vogella.com/tutorials/AndroidListView/article.html
 * @version 1.1.4 (07/05/2014)
 * @author Kjell Bunjes
 *
 */

public class InvitationModel {

	  private String name;
	  private boolean selected;

	  public InvitationModel(String name) {
	    this.name = name;
	    selected = false;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }

	} 
