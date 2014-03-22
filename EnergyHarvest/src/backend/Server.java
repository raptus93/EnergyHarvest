package backend;

import java.util.HashMap;

/**
 * 
 * @author Sergej Schefer
 */
public class Server {

	private boolean session = false;

	public Server(){
		
	}
	
	public Package sendPackage(Package p){
		/* request_package -> server | server -> response_package*/
		return null;
	}
	
	public boolean login(String email, String pw){
		HashMap<String, Object> request = new HashMap<String, Object>();
		request.put("email", email); 
		request.put("password", pw);
		
		return session ? (Boolean) sendPackage(new Package(Package.Type.REQUEST_CHECK_LOGIN, request)).getContent().get("response") : session;
	}
	
	public boolean checkAnswer(Question question, Question.Answer answer){
		return true;
	}
	
	public QuestionCatalog getRandomQuestions(){
		return new QuestionCatalog();
	}
	
	public boolean register(String name, String email, String pw){
		return true;
	}
	
	public boolean createClan(String name){
		return true;
	}
	
	public boolean inviteMember(int id){
		return true;
	}
}
