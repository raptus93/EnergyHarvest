package node;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
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

    public void proccess(String message, JSONObject json){

        if(message.equals("CHALLENGE_STARTED")){
            ChallengeBridge.getInstance().startChallenge();
        }else if(message.equals("CHALLENGE_INVITE")){

            //TODO: -> erstmal auto-accept für alle challenge invites

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
