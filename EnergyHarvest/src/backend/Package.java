
/* used across backend & client */

package backend;

import java.util.HashMap;

public class Package implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Type{
		REQUEST_CHECK_LOGIN, REQUEST_FETCH_QUESTIONS, REQUEST_REGISTER_CLAN, REQUEST_INVITE_MEMBER,
		RESPONSE_CHECK_LOGIN, RESPONSE_FETCH_QUESTIONS, RESPONSE_REGISTER_CLAN, RESPONSE_INVITE_MEMBER;
	};
	
	private HashMap<String, Object> content;
	private Type type;
	
	public Package(Type type, HashMap<String, Object> content){
		this.type = type;
		this.content = content;
	}
	
	public HashMap<String, Object> getContent(){
		return content;
	}
	
	public Type getType(){
		return type;
	}

}
