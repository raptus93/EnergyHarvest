package node;

import android.widget.ArrayAdapter;
import gui.*;
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

    /** lobby **/
    private LobbyActivity lobby;

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

    public void setLobby(LobbyActivity lobby){
        this.lobby = lobby;
    }

    public void setLogic(QuizLogic logic){
        this.logic = logic;
    }

    public Question getCurrentQuestion(){
        return currentQuestion;
    }

    public void proccess(String message, JSONObject json){
        Log.e("CHALLENGE", message);

        if(message.equals("CHALLENGE_STARTED")){
            ChallengeBridge.getInstance().startChallenge();
        }else if(message.equals("CHALLENGE_INVITE")){

            //TODO: -> erstmal auto-accept für alle challenge invites
            //TODO: -> HIER POP UP


 /*           runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final InvitationDialog dialog = new InvitationDialog(ActivityHolder.getInstance().getActiveActivity());
                    dialog.show();
                }
            });*/

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
        }else if(message.equals("CHALLENGE_FORCE_END")){
            Server.getInstance().endChallenge(
            /** success **/
            new Callback() {
                @Override
                public void callback(Object... input) {
                   Log.e("CHALLENGE", "ENDED");
                    /**
                     * master user sendet -> challenge_end message an server
                     * server sendet an alle challenge teilnehmer, dass die challenge zu ende ist
                     * damit diese, die quiz activity schließen können.
                     * **/
                }
            },
            /** fail. user is not the master **/
            new Callback() {
                @Override
                public void callback(Object... input) {

                }
            });
        }else if(message.equals("CHALLENGE_END")){
            /**
             * hier noch statistiken anzeigen etc....
             * **/


            Log.e("CHALLENGE", "CLOSE DOWN ACTIVITY!");

            /** close the quiz activity **/
            logic.close();
        }else if(message.equals("CHALLENGE_REMOVED_USER")){
            try {
                String username = json.getString("username");

                /** remove from lobby list **/
                if(lobby != null){
                    lobby.getArrayPlayers().remove(username);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(message.equals("CHALLENGE_ADDED_USER")){
            try {
                String username = json.getString("username");

                /** add to lobby list **/
                if(lobby != null){
                    lobby.getArrayPlayers().add(username);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
