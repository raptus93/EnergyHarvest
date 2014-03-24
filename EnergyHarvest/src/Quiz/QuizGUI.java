package Quiz;

import com.example.energyharvest.R;

import android.app.Activity;
import android.os.Bundle;
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
	
	int chosenAnswer = 0;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Initializing the Logic
        logic = new QuizLogic();
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
    }

	@Override
	public void onClick(View v) {
		//Close all Buttons
		
		//Differentiation which Button was clicked (all have the same Listener)
		//PROBLEM DARAN: Zugriff von "oben nach unten"
		
		if(v.getId() == R.id.answerA){
			chosenAnswer=1;
		}
		else if(v.getId() == R.id.answerB){
			chosenAnswer=2;
		}
		else if(v.getId() == R.id.answerC){
			chosenAnswer=3;
		}
		else if(v.getId() == R.id.answerD){
			chosenAnswer=4;
		}
		logic.ping();
	}
	//public int getAnswer();
}
