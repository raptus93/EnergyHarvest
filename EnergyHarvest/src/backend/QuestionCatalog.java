package backend;

import java.util.LinkedList;

/**
 * Contains 10 random Questions
 * @author Sergej Schefer
 */
public class QuestionCatalog {
	
	private LinkedList<Question> questions;
	private int currentQuestionID = -1;
	
	/**
	 * Stub
	 */
	public QuestionCatalog(){
		
	}
	
	/**
	 * pulls a question from the catalog
	 * @return
	 */
	public Question pullQuestion(){
		currentQuestionID++;
		return questions.get(currentQuestionID);
	}
}
