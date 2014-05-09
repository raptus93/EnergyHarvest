package quiz;

import com.example.energyharvest.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import node.ChallengeBridge;
import node.Question;
import node.Question.Answer;
import node.Server;

/**
 * @version 1.1.4 (28/04/2014)
 * @author Robert Verginien Nickel
 *
 */


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

        logic = new QuizLogic();

        logic.registerGUI(this);

        ChallengeBridge.getInstance().setLogic(logic);

        //Starts the Timer once
        logic.startTimer();

        showQuestion(ChallengeBridge.getInstance().getCurrentQuestion());
    }
	
	private void setupLayout(){

		//Initializing the textView and the buttons
		tvQuestion = (TextView) findViewById(R.id.question);
        btnAnswerA = (Button) findViewById(R.id.answerA);
        btnAnswerB = (Button) findViewById(R.id.answerB);
        btnAnswerC = (Button) findViewById(R.id.answerC);
        btnAnswerD = (Button) findViewById(R.id.answerD);
        timePB = (ProgressBar) findViewById(R.id.timeProgressBar);
        timePB.setMax(15);
        timePB.setProgress(15);
        
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

        if(v.isActivated()){

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

            highlight((Button)v);
            blockButtons();
            Server.getInstance().sendAnswer(ChallengeBridge.getInstance().getCurrentQuestion().id, chosenAnswer.ordinal(), Server.getInstance().getActiveUser().getClan().getId());
        }
    }

    public void update(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timePB.setProgress((int) logic.getTimeLeft());
            }
        });

        //next Question, if the time is over
        if(logic.getTimeLeft() <= 0){
            //Send to Server, that no Button was pressed!
            Server.getInstance().sendAnswer(ChallengeBridge.getInstance().getCurrentQuestion().id, Answer.A.ordinal(), Server.getInstance().getActiveUser().getClan().getId());
            blockButtons();
        }
    }

    public void highlight(final Button highlight){
        final int corr = this.getResources().getColor(R.color.answerCorrect);
        final int def = this.getResources().getColor(R.color.answerDefault);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnAnswerA.setBackgroundColor(def);
                btnAnswerB.setBackgroundColor(def);
                btnAnswerC.setBackgroundColor(def);
                btnAnswerD.setBackgroundColor(def);
                highlight.setBackgroundColor(corr);
            }
        });
    }

	/**
	 * all buttons are blocked
	 * this happens, after one answer is chosen
	 */
	public void blockButtons(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnAnswerA.setActivated(false);
                btnAnswerB.setActivated(false);
                btnAnswerC.setActivated(false);
                btnAnswerD.setActivated(false);
            }
        });
	}
	
	/**
	 * the next question and the new answers are shown and
	 * the buttons are enabled
	 */
	public void showQuestion(final Question q){
        Log.e("QUESTION", "GUI SHOW" + q.toString());

        final int defaultColor = this.getResources().getColor(R.color.answerDefault);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logic.resetTimer();

                btnAnswerA.setBackgroundColor(defaultColor);
                btnAnswerB.setBackgroundColor(defaultColor);
                btnAnswerC.setBackgroundColor(defaultColor);
                btnAnswerD.setBackgroundColor(defaultColor);

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
        });
    }

	

}