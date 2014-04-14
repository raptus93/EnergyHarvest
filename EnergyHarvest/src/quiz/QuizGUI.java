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
import android.widget.TextView;
import android.widget.Toast;

public class QuizGUI extends Activity implements OnClickListener, Runnable{
	
	private TextView tvQuestion;
	private Button btnAnswerA;
	private Button btnAnswerB;
	private Button btnAnswerC;
	private Button btnAnswerD;
	private QuizLogic logic;
	
	private Handler handler;
	
	Answer chosenAnswer = Answer.A;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        handler = new Handler();
        setupLayout();
        
        //Initializing the Logic
        logic = new QuizLogic();
        
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
        
        //Adding the Listener for the Answer-Buttons
        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);
        
        //All Buttons are black from the beginning
        btnAnswerA.setBackgroundColor(Color.rgb(155, 155, 155));
		btnAnswerB.setBackgroundColor(Color.rgb(155, 155, 155));
		btnAnswerC.setBackgroundColor(Color.rgb(155, 155, 155));
		btnAnswerD.setBackgroundColor(Color.rgb(155, 155, 155));
        
        
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.answerA : chosenAnswer=Answer.A;
			case R.id.answerB : chosenAnswer=Answer.B;
			case R.id.answerC : chosenAnswer=Answer.C;
			case R.id.answerD : chosenAnswer=Answer.D;
		}
		
		blockButtons();
		
		if(logic.checkAnswer(chosenAnswer)){
			Toast.makeText(getApplicationContext(), "correct Answer, BIIIIAAAATCH", Toast.LENGTH_SHORT).show();
			highlight(true, chosenAnswer);
		}
		else{
			Toast.makeText(getApplicationContext(), "WRonG  #-.,.-'''-.,.->   Answer", Toast.LENGTH_SHORT).show();
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
		//Provisorisch!
		btnAnswerA.setBackgroundColor(Color.rgb(155, 155, 155));
		btnAnswerB.setBackgroundColor(Color.rgb(155, 155, 155));
		btnAnswerC.setBackgroundColor(Color.rgb(155, 155, 155));
		btnAnswerD.setBackgroundColor(Color.rgb(155, 155, 155));
		
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
	
	/**
	 * Highlights the chosen answer. Green if right, red otherwise.
	 * @param correct is true, if the answer has been correct, otherwise it is false
	 * @param chosenAnswer
	 */
	public void highlight(boolean correct, Answer chosenAnswer) {
		if(chosenAnswer == Answer.A){
			if(correct){
				//A wird gr�n
				btnAnswerA.setBackgroundColor(Color.GREEN);
			}
			else{
				//A wird rot
				btnAnswerA.setBackgroundColor(Color.RED);
			}
		}
		if(chosenAnswer == Answer.B){
			if(correct){
				//B wird gr�n
				btnAnswerB.setBackgroundColor(Color.GREEN);
			}
			else{
				//B wird rot
				btnAnswerB.setBackgroundColor(Color.RED);
			}
		}
		if(chosenAnswer == Answer.C){
			if(correct){
				//C wird gr�n
				btnAnswerC.setBackgroundColor(Color.GREEN);
			}
			else{
				//C wird rot
				btnAnswerC.setBackgroundColor(Color.RED);
			}
		}
		if(chosenAnswer == Answer.D){
			if(correct){
				//D wird gr�n
				btnAnswerD.setBackgroundColor(Color.GREEN);
			}
			else{
				//D wird rot
				btnAnswerD.setBackgroundColor(Color.RED);	
			}
			
		}
	}
	
	@Override
	public void run() {
		countDown();
		handler.postDelayed(this, 1000);
	}
	
	public void startTime(){
        //Handler adds event to eventqueue delayed 1 second
        handler.postDelayed(this, 1000);
	}
	
	private void countDown() {
		System.out.println("Time-1");
		//time--;
	}
	
}