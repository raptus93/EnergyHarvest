package quiz;

import quiz.QuizLogic;
import backend.Question;
import backend.Question.Answer;

import com.example.energyharvest.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService.Engine;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizGUI extends Activity implements OnClickListener{
	
	private TextView tvQuestion;
	private Button btnAnswerA;
	private Button btnAnswerB;
	private Button btnAnswerC;
	private Button btnAnswerD;
	private ProgressBar timePB;
	
	private QuizLogic logic;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        
        //Sets up the Views
        setupLayout();
        
        //Initializing the Logic
        logic = new QuizLogic();
        
        //register this as observer
        logic.registerGUI(this);
        
        //Get 10 Questions from the server
        logic.getQuestions();
        
        //Create the first Question
        showNextQuestion();
    }
	
	private void setupLayout(){
		
		 //Initializing the textView and the buttons
		tvQuestion = (TextView) findViewById(R.id.question);
        btnAnswerA = (Button) findViewById(R.id.answerA);
        btnAnswerB = (Button) findViewById(R.id.answerB);
        btnAnswerC = (Button) findViewById(R.id.answerC);
        btnAnswerD = (Button) findViewById(R.id.answerD);
        timePB = (ProgressBar) findViewById(R.id.timeProgressBar);
        timePB.setMax(10); //later on this is 7
        
        //Adding the Listener for the Answer-Buttons
        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		//Decision, what Button has been clicked
		Answer chosenAnswer = Answer.A;
		if (v == btnAnswerB){
			chosenAnswer = Answer.B;
		}
		else if (v == btnAnswerC){
			chosenAnswer = Answer.C;	
		}
		else if (v == btnAnswerD){
			chosenAnswer = Answer.D;
		}
		
		//blockButtons();
		
		//Check the correctness at the server, highlight depending on the result
		if(logic.checkAnswer(chosenAnswer)){
			highlight(true, chosenAnswer);
		}
		else{
			highlight(false, chosenAnswer);
		}
		
		if(logic.getCurrentQuestion()<10){
			showNextQuestion();
		}
		else{
			//quit the quiz
			Toast.makeText(getApplicationContext(), "10 Questions are asked. Quit.", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	/**
	 * all buttons are blocked
	 * this happens, after one answer is chosen
	 */
	public void blockButtons(){
		btnAnswerA.setActivated(false);
		btnAnswerB.setActivated(false);
		btnAnswerC.setActivated(false);
		btnAnswerD.setActivated(false);
	}
	
	
	/**
	 * the next question and the new answers are shown and
	 * the buttons are enabled
	 */
	public void showNextQuestion(){
		logic.resetTimer();
		logic.startTimer();
		
		btnAnswerA.setBackgroundColor(this.getResources().getColor(R.color.answerDefault));
		btnAnswerB.setBackgroundColor(this.getResources().getColor(R.color.answerDefault));
		btnAnswerC.setBackgroundColor(this.getResources().getColor(R.color.answerDefault));
		btnAnswerD.setBackgroundColor(this.getResources().getColor(R.color.answerDefault));
		
		Question q = logic.nextQuestion();
		
		tvQuestion.setText(q.text);
		btnAnswerA.setText(q.answerA);
		btnAnswerB.setText(q.answerB);
		btnAnswerC.setText(q.answerC);
		btnAnswerD.setText(q.answerD);
		
		btnAnswerA.setActivated(true);
		btnAnswerB.setActivated(true);
		btnAnswerC.setActivated(true);
		btnAnswerD.setActivated(true);
	}
	
	public void update(){
		System.out.println("Gui says: Left time is "+logic.getTimeLeft());
		//timePB.setProgress((int) Math.round(100 * logic.getTimeLeft() / 7));
		timePB.setProgress((int) logic.getTimeLeft());
	}
	
	/**
	 * Highlights the chosen answer. Green if right, red otherwise.
	 * @param correct is true, if the answer has been correct, otherwise it is false
	 * @param chosenAnswer
	 */
	public void highlight(boolean correct, Answer chosenAnswer) {
		
		if(chosenAnswer == Answer.A){
			if(correct){
				btnAnswerA.setBackgroundColor(this.getResources().getColor(R.color.answerCorrect));
				//btnAnswerA.setBackgroundColor(0xFF04B404);
				Toast.makeText(getApplicationContext(), "A GREEN", Toast.LENGTH_SHORT).show();
			}
			else{
				btnAnswerA.setBackgroundColor(this.getResources().getColor(R.color.answerWrong));
				//btnAnswerA.setBackgroundColor(0xFFB40404);
				Toast.makeText(getApplicationContext(), "A RED", Toast.LENGTH_SHORT).show();
			}
		}
		if(chosenAnswer == Answer.B){
			if(correct){
				btnAnswerB.setBackgroundColor(this.getResources().getColor(R.color.answerCorrect));
				Toast.makeText(getApplicationContext(), "B GREEN", Toast.LENGTH_SHORT).show();
			}
			else{
				btnAnswerB.setBackgroundColor(this.getResources().getColor(R.color.answerWrong));
				Toast.makeText(getApplicationContext(), "B RED", Toast.LENGTH_SHORT).show();
			}
		}
		if(chosenAnswer == Answer.C){
			if(correct){
				btnAnswerC.setBackgroundColor(this.getResources().getColor(R.color.answerCorrect));
				Toast.makeText(getApplicationContext(), "C GREEN", Toast.LENGTH_SHORT).show();
			}
			else{
				btnAnswerC.setBackgroundColor(this.getResources().getColor(R.color.answerWrong));
				Toast.makeText(getApplicationContext(), "C RED", Toast.LENGTH_SHORT).show();
			}
		}
		if(chosenAnswer == Answer.D){
			if(correct){
				btnAnswerD.setBackgroundColor(this.getResources().getColor(R.color.answerCorrect));
				Toast.makeText(getApplicationContext(), "D GREEN", Toast.LENGTH_SHORT).show();
			}
			else{
				btnAnswerD.setBackgroundColor(this.getResources().getColor(R.color.answerWrong));
				Toast.makeText(getApplicationContext(), "D RED", Toast.LENGTH_SHORT).show();
			}	
		}
		
		/*try{
			Thread.sleep(1200);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}*/
	}
	
	

}