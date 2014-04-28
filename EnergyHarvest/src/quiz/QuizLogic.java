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

	private int currentQuestion = 0;

	private int correctAnswers = 0;
	private double timeLeft = 7;
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
			Toast.makeText(gui, "Time left: "+timeLeft, Toast.LENGTH_SHORT).show();
			Log.i("debugging", "vor update()");
			gui.update();
			handler.postDelayed(this, 1000);
			Log.i("debugging", "nach dem update()");
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
		handler.postDelayed(this, 1000);
	}

	public void resetTimer() {
		timeLeft = 7;
		Toast.makeText(gui, "Time reset!", Toast.LENGTH_SHORT).show();
	}

	public void startTimer() {
		handler.postDelayed(this, 1000);
		System.out.println("Timer started!");
		Toast.makeText(gui, "Timer started!", Toast.LENGTH_SHORT).show();
	}
}