package node;

import gui.InvitationActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.energyharvest.R;

import quiz.QuizGUI;
import quiz.QuizLogic;

public class ChallengeBridge {

    /** singleton **/

    private static ChallengeBridge challengeBridge;

    public static ChallengeBridge getInstance(){
        return challengeBridge;
    }

    public static void init(Activity activity, Intent intent){
        if(challengeBridge == null){
            challengeBridge = new ChallengeBridge(activity, intent);
        }
    }

    /** class members **/

    private Activity activity;
    private Intent intent;

    /** for pushing the questions down to the gui **/
    private QuizLogic logic;
    private Question currentQuestion;

    private ChallengeBridge(Activity activity, Intent intent){
        this.activity = activity;
        this.intent = intent;
    }

    public void startChallenge(){
        activity.startActivity(intent);
    }

    public void setQuestion(Question q){
        currentQuestion = q;

        if(logic != null){
            logic.pushQuestion(q);
        }
    }

    public void setLogic(QuizLogic logic){
        this.logic = logic;
    }

    public Question getCurrentQuestion(){
        return currentQuestion;
    }
    
    private void createAndShowPopup() {
    	/**
		 * Creating popup window
		 */
    	InvitationActivity act = new InvitationActivity();
		LayoutInflater layoutInflater = (LayoutInflater) act.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.popup_join, null);
		final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Button btnConfirm = (Button)popupView.findViewById(R.id.clan_popup_button_confirm);
		btnConfirm.setOnClickListener(new Button.OnClickListener(){
		     @Override
		     public void onClick(View v) {
		      popupWindow.dismiss();
		}});
		
		//popupWindow.showAtLocation(buttonTop, Gravity.CENTER, 0, 0);
		popupWindow.showAtLocation(null, Gravity.CENTER, 0, 0);
    }

    public void proccess(String message, JSONObject json){

        if(message.equals("CHALLENGE_STARTED")){
            ChallengeBridge.getInstance().startChallenge();
        }else if(message.equals("CHALLENGE_INVITE")){

            //TODO: -> erstmal auto-accept für alle challenge invites
            //TODO: -> HIER POP UP
        	createAndShowPopup();

            /***
             * ACCEPT / DECLINE THE CHALLENGE
             * **/
            Server.getInstance().challengeResponse(true,
                /** success [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {

                    }
                },
                /** fail [no input] **/
                new Callback() {
                    @Override
                    public void callback(Object... input) {

                    }
                });
        }else if(message.equals("CHALLENGE_QUESTION")){
            try {
                JSONObject question = json.getJSONObject("question");

                int id = question.getInt("id");
                String text = question.getString("question");
                String answerA = question.getString("answer_a");
                String answerB = question.getString("answer_b");
                String answerC = question.getString("answer_c");
                String answerD = question.getString("answer_d");

                setQuestion(new Question(id, text, answerA, answerB, answerC, answerD));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
