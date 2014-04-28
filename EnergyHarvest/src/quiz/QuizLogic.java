package quiz;

import android.os.Handler;
import android.util.Log;
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

	public static final int TIME_TO_ANSWER = 15;

	private int currentQuestion = 0;

	private int correctAnswers = 0;
	private double timeLeft = TIME_TO_ANSWER;
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
		if(timeLeft > 0){
			timeLeft = timeLeft - 1;
			gui.update();
			handler.postDelayed(this, 1000);
		}
		else{
			Toast.makeText(gui, "Time is over!", Toast.LENGTH_SHORT).show();
		}
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public void registerGUI(QuizGUI gui){
		this.gui = gui;
		System.out.println("gui is registered.");
	}

	public void resetTimer() {
		timeLeft = TIME_TO_ANSWER + 1;
		Toast.makeText(gui, "Time reset!", Toast.LENGTH_SHORT).show();
	}

	public void startTimer() {
		handler.postDelayed(this, 1200);
		Toast.makeText(gui, "Timer started!", Toast.LENGTH_SHORT).show();
	}

	public void sendNonClick() {
		// brauche ich erst Dienstags
		
	}
	
	public void incrementCorrectAnswers(){
		correctAnswers++;
	}
}