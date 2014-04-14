package quiz;

import android.os.Handler;
import android.widget.Toast;
import backend.Question;
import backend.Question.Answer;
import backend.AndroidServerInterface;
import backend.QuestionCatalog;
import backend.Server;

public class QuizLogic{

	private int currentQuestion = 0;
	

	private int correctAnswers = 0;
	private QuestionCatalog questionCatalog;
	
	
	
	public void getQuestions(){
		questionCatalog = AndroidServerInterface.getRandomQuestions(10);	
	}
	
	public Question nextQuestion(){
		/*
		vorher:
		currentQuestion++;
		return questionCatalog.pullQuestion();
		nachher:
		*/
		currentQuestion++;
		return questionCatalog.getQuestion(currentQuestion);
	}

	public int getCurrentQuestion() {
		return currentQuestion;
	}

	private void sendNumberOfCorrectAnswers(){
		//Serveraufruf um die Anzahl der richtigen Antworten (correctAnswers) dort zu speichern
	}

	public boolean checkAnswer(Answer chosenAnswer) {
		if(AndroidServerInterface.checkAnswer(questionCatalog.getQuestion(currentQuestion).id, chosenAnswer)){
			correctAnswers++;
			return true;
		}
		else{
			return false;
		}
	}
	

	
	
	
	
}
	
	/*
	private void createPlayerList(){
		//Liste anzeigen mit Checkboxen zur Auswahl der Mitspieler
		//addSpec() aufrufen
	}
	
	private void addSpec(){
		//Liste mit Specs
	}
	*/

