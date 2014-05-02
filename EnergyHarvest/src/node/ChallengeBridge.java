package node;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import org.json.JSONObject;

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

    private ChallengeBridge(Activity activity, Intent intent){
        this.activity = activity;
        this.intent = intent;
    }

    public void startChallenge(){
        activity.startActivity(intent);
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
        }

    }


}
