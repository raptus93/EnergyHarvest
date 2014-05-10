package quiz;

import quiz.QuizGUI;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import node.ChallengeBridge;
import node.Question;

/**
 * @version 1.1.4 (28/04/2014)
 * @author Robert Verginien Nickel
 *
 */

public class QuizLogic implements Runnable{

    public static final int TIME_TO_ANSWER = 15;
    private double timeLeft = TIME_TO_ANSWER;
    private Handler handler = new Handler();

    private QuizGUI gui;

    public Question getCurrentQuestion() {
        return ChallengeBridge.getInstance().getCurrentQuestion();
    }

    public void pushQuestion(Question q){
        if(gui != null){
            gui.showQuestion(q);
        }else{
            Log.e("QUESTION", "GUI IS NULL");
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
    }

    public void resetTimer() {
        timeLeft = TIME_TO_ANSWER + 1;
        Toast.makeText(gui, "Time reset!", Toast.LENGTH_SHORT).show();
    }

    public void startTimer() {
        handler.postDelayed(this, 1200);
        Toast.makeText(gui, "Timer started!", Toast.LENGTH_SHORT).show();
    }

}