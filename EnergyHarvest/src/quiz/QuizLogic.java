package quiz;

import android.os.Handler;
import android.widget.Toast;
import backend.Question;
import backend.Question.Answer;
import backend.AndroidServerInterface;
import backend.QuestionCatalog;
import backend.Server;

/**
 * @version 1.1.4 (28/04/2014)
 * @author Robert Verginien Nickel
 *
 */

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

	@Override
	public void run() {
		if(timeLeft<=8){
			timeLeft--;
			System.out.println("Left time is "+timeLeft);
			gui.update();
			handler.postDelayed(this, 1000);
		}
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public void registerGUI(QuizGUI gui){
		this.gui = gui;
		System.out.println("gui is registered.");
		handler.postDelayed(this, 100);
	}

	public void resetTimer() {
		timeLeft = 0;
		Toast.makeText(gui, "Time reset!", Toast.LENGTH_SHORT).show();
	}

	public void startTimer() {
		handler.post(this);
		System.out.println("Timer started!");
		Toast.makeText(gui, "Timer started!", Toast.LENGTH_SHORT).show();
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

