package quiz;

import quiz.QuizLogic;
import backend.Question;
import backend.Question.Answer;

import com.example.energyharvest.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class QuizGUI extends Activity implements OnClickListener{
	
	TextView tvQuestion;
	Button btnAnswerA;
	Button btnAnswerB;
	Button btnAnswerC;
	Button btnAnswerD;
	QuizLogic logic;
	
	Question.Answer chosenAnswer = Answer.A;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Initializing the Logic
        logic = new QuizLogic();
        logic.getQuestions();
        
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
        
        //Create the first Question
        showNextQuestion();
    }
	
	////////////////////////////////////////////////////////////////////////////////////
	
	
	//Die folgende Methode wird benötigt, um die Activity in fullscreen zu haben, Standardmethode.
	
	
	////////////////////////////////////////////////////////////////////////////////////
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
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////
	
	
	//Auskommentiert, da die Methode den Absturz verursacht. Ursache ist mir noch unklar :D


	////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onClick(View v) {		
		/*if(v.getId() == R.id.answerA){
			chosenAnswer=Answer.A;
		}
		else if(v.getId() == R.id.answerB){
			chosenAnswer=Answer.A;
		}
		else if(v.getId() == R.id.answerC){
			chosenAnswer=Answer.A;
		}
		else if(v.getId() == R.id.answerD){
			chosenAnswer=Answer.A;
		}
		logic.checkAnswer(chosenAnswer, this);*/
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
	 * is called by the logic
	 * the new question and the new answers are shown and
	 * the button are enabled
	 */
	public void showNextQuestion(){
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
				//A wird grün
				btnAnswerA.setBackgroundColor(Color.GREEN);
			}
			//A wird rot
			btnAnswerA.setBackgroundColor(Color.RED);
		}
		if(chosenAnswer == Answer.B){
			if(correct){
				//B wird grün
				btnAnswerB.setBackgroundColor(Color.GREEN);
			}
			//B wird rot
			btnAnswerB.setBackgroundColor(Color.RED);
		}
		if(chosenAnswer == Answer.C){
			if(correct){
				//C wird grün
				btnAnswerC.setBackgroundColor(Color.GREEN);
			}
			//C wird rot
			btnAnswerC.setBackgroundColor(Color.RED);
		}
		if(chosenAnswer == Answer.D){
			if(correct){
				//D wird grün
				btnAnswerD.setBackgroundColor(Color.GREEN);
			}
			//D wird rot
			btnAnswerD.setBackgroundColor(Color.RED);
		}
	}
}
