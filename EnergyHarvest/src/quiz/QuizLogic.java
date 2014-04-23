package quiz;

import android.os.Handler;
import android.widget.Toast;
import backend.Question;
import backend.Question.Answer;
import backend.AndroidServerInterface;
import backend.QuestionCatalog;
import backend.Server;

public class QuizLogic implements Runnable{

	private int currentQuestion = 0;

	private int correctAnswers = 0;
	private double timeLeft;
	private QuestionCatalog questionCatalog;
	private Handler handler = new Handler();
	//Observer:
	private QuizGUI gui;
	
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

	public boolean checkAnswer(Answer chosenAnswer) {
		if(AndroidServerInterface.checkAnswer(questionCatalog.getQuestion(currentQuestion).id, chosenAnswer)){
			correctAnswers++;
			return true;
		}
		else{
			return false;
		}
	}
	
	private void sendNumberOfCorrectAnswers(){
		//Serveraufruf um die Anzahl der richtigen Antworten (correctAnswers) dort zu speichern
	}

	@Override
	public void run() {
		countDown();
		handler.postDelayed(this, 100);
	}
	
	private void countDown() {
		//every 0.1 seconds the time is reduced about 0.1 seconds
		timeLeft = timeLeft - 0.1;
		gui.update();
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public void registerGUI(QuizGUI gui){
		this.gui = gui;
		handler.postDelayed(this, 100);
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

