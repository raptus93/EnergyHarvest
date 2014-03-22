package backend;

/**
 * @author Sergej Schefer
 */
public class User {
	/* singleton */
	private static User instance;
	
	public static User getInstance(){
		if(instance == null)
			instance = new User();
		return instance;
	}
	
	private User(){}
	
	/* class members */
	private String name;
	private String pw;
	private String email;
	private int id;
	private Clan clan;
	
	public void setName(String name){ this.name = name; }
	public void setPW(String pw){ this.pw = pw; }
	public void setEmail(String email){ this.email = email; }
	public void setID(int id){ this.id = id; }
}
